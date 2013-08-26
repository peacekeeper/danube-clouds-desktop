package danube.clouds.desktop.ui.parties.mycloud;

import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import nextapp.echo.app.Button;
import nextapp.echo.app.Panel;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.command.BrowserRedirectCommand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xdi2.client.exceptions.Xdi2ClientException;
import xdi2.connector.facebook.api.FacebookApi;
import xdi2.connector.facebook.mapping.FacebookMapping;
import xdi2.core.constants.XDIAuthenticationConstants;
import xdi2.core.constants.XDIDictionaryConstants;
import xdi2.core.features.nodetypes.XdiAbstractInstanceUnordered;
import xdi2.core.xri3.XDI3Segment;
import xdi2.core.xri3.XDI3Statement;
import xdi2.core.xri3.XDI3SubSegment;
import xdi2.messaging.Message;
import danube.clouds.desktop.DanubeCloudsDesktopApplication;
import danube.clouds.desktop.external.ExternalCall;
import danube.clouds.desktop.external.ExternalCallReceiver;
import danube.clouds.desktop.parties.impl.GlobalRegistryParty;
import danube.clouds.desktop.parties.impl.MyCloudParty;
import danube.clouds.desktop.parties.impl.RegistrarParty;
import danube.clouds.desktop.ui.MessageDialog;
import danube.clouds.desktop.xdi.XdiEndpoint;

public class FacebookConnectorPanel extends Panel implements ExternalCallReceiver {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(FacebookConnectorPanel.class);

	protected ResourceBundle resourceBundle;

	private XdiEndpoint xdiEndpoint;
	private XDI3Segment contextNodeXri;

	private FacebookApi facebookApi;
	private FacebookMapping facebookMapping;

	public FacebookConnectorPanel() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	@Override
	public void init() {

		super.init();

		this.facebookApi = new FacebookApi("420250631345354", "c2feeda99926ab3c6096beaa8e6eca73");
		this.facebookMapping = new FacebookMapping();
	}

	@Override
	public void dispose() {

		super.dispose();
	}

	private void refresh() {

		try {

			// refresh UI

		} catch (Exception ex) {

			MessageDialog.problem("Sorry, a problem occurred while retrieving your Personal Data: " + ex.getMessage(), ex);
			return;
		}
	}

	public void setData(XdiEndpoint xdiEndpoint, XDI3Segment contextNodeXri) {

		// refresh

		this.xdiEndpoint = xdiEndpoint;
		this.contextNodeXri = contextNodeXri;

		this.refresh();
	}

	private void onConnectFacebookActionPerformed(ActionEvent e) {

		MyCloudParty cloudParty = DanubeCloudsDesktopApplication.getApp().getMyCloudParty();

		if (cloudParty == null) {

			MessageDialog.warning("Please open a Cloud.");
			return;
		}

		// start OAuth

		HttpServletRequest request = WebContainerServlet.getActiveConnection().getRequest();

		String redirectUri = request.getRequestURL().toString();
		redirectUri = redirectUri.substring(0, redirectUri.lastIndexOf("/clouds"));
		if (! redirectUri.endsWith("/")) redirectUri += "/";
		redirectUri += "clouds/facebookConnectorPanel";

		XDI3Segment userXri = this.xdiEndpoint.getCloudNumber();

		try {

			DanubeCloudsDesktopApplication.getApp().enqueueCommand(new BrowserRedirectCommand(this.facebookApi.startOAuth(request, redirectUri, userXri)));
			return;
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, a problem occurred: " + ex.getMessage(), ex);
			return;
		}
	}

	@Override
	public void onExternalCallRaw(DanubeCloudsDesktopApplication application, ExternalCall externalCall) {

		// error from OAuth?

		if (externalCall.getParameterMap().get("error") != null) {

			String errorDescription = (String) externalCall.getParameterMap().get("error_description");
			if (errorDescription == null) errorDescription = (String) externalCall.getParameterMap().get("error_reason");
			if (errorDescription == null) errorDescription = (String) externalCall.getParameterMap().get("error");

			MessageDialog.warning("Sorry, a problem occurred: " + errorDescription);
			return;
		}

		// callback from OAuth?

		if (externalCall.getParameterMap().get("code") != null) {

			XDI3Segment userXri = FacebookConnectorPanel.this.xdiEndpoint.getCloudNumber();

			try {

				FacebookConnectorPanel.this.facebookApi.checkState(externalCall.getParameterMap(), userXri);

				String facebookAccessToken = FacebookConnectorPanel.this.facebookApi.exchangeCodeForAccessToken(externalCall.getRequestURL(), externalCall.getParameterMap());
				if (facebookAccessToken == null) throw new Exception("No access token received.");

				String facebookUserId = FacebookConnectorPanel.this.facebookApi.retrieveUserId(facebookAccessToken);
				XDI3Segment facebookUserIdXri = FacebookConnectorPanel.this.facebookMapping.facebookUserIdToFacebookUserIdXri(facebookUserId);

				this.storeFacebookUserIdXriAndFacebookAccessToken(userXri, facebookUserIdXri, facebookAccessToken);
				this.registerCloudSynonym(facebookAccessToken, facebookUserId);

				// done

				MessageDialog.info("Successfully connected to Facebook. Access token stored in graph.");
				return;
			} catch (final Exception ex) {

				MessageDialog.problem("Sorry, a problem occurred: " + ex.getMessage(), ex);
				return;
			}
		}
	}

	@Override
	public void onExternalCallApplication(DanubeCloudsDesktopApplication application, ExternalCall externalCall) {

	}

	public void storeFacebookUserIdXriAndFacebookAccessToken(XDI3Segment userXri, XDI3Segment facebookUserIdXri, String facebookAccessToken) throws Xdi2ClientException {

		// store facebook user id and facebook access token

		Message message = this.xdiEndpoint.prepareMessage(this.xdiEndpoint.getCloudNumber());
		message.createSetOperation(XDI3Statement.fromRelationComponents(XDI3Segment.create("" + this.contextNodeXri + userXri), XDIDictionaryConstants.XRI_S_REF, XDI3Segment.create("" + this.contextNodeXri + facebookUserIdXri)));
		message.createSetOperation(XDI3Statement.fromLiteralComponents(XDI3Segment.create("" + this.contextNodeXri + facebookUserIdXri + XDIAuthenticationConstants.XRI_S_OAUTH_TOKEN), facebookAccessToken));

		this.xdiEndpoint.send(message);
	}

	public void registerCloudSynonym(String facebookAccessToken, String facebookUserId) {

		GlobalRegistryParty globalRegistryParty = DanubeCloudsDesktopApplication.getApp().getGlobalRegistryParty();
		RegistrarParty registrarParty = DanubeCloudsDesktopApplication.getApp().getRegistrarParty();

		// register the cloud synonym

		XDI3Segment cloudNumber = FacebookConnectorPanel.this.xdiEndpoint.getCloudNumber();
		XDI3Segment cloudSynonym = cloudSynonym(facebookUserId);

		try {

			globalRegistryParty.registerCloudSynonym(registrarParty, cloudNumber, cloudSynonym);
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, we could not register the Cloud Synonym: " + ex.getMessage(), ex);
			return;
		}
	}

	private static XDI3Segment cloudSynonym(String facebookUserId) {

		XDI3SubSegment arcXri = XdiAbstractInstanceUnordered.createArcXriFromHash(facebookUserId, false);			

		return XDI3Segment.create("=facebook" + arcXri.toString().substring(1).replace(":", ".") + "." +  UUID.randomUUID().toString().replace("-", "."));
	}

	/*	private class FacebookConnectorRunnable implements Runnable {

		private String accessToken;
		private String facebookUserId;

		private FacebookConnectorRunnable(String accessToken, String facebookUserId) {

			this.accessToken = accessToken;
			this.facebookUserId = facebookUserId;
		}
	}*/

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		Button button1 = new Button();
		button1.setStyleName("Default");
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/danube/clouds/desktop/resource/image/connect-facebook.png");
		button1.setIcon(imageReference1);
		button1.setText("Connect to Facebook");
		button1.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onConnectFacebookActionPerformed(e);
			}
		});
		add(button1);
	}
}

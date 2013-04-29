package danube.discoverydemo.ui.parties.cloud;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nextapp.echo.app.Button;
import nextapp.echo.app.Panel;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.TaskQueueHandle;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.command.BrowserRedirectCommand;

import org.json.JSONObject;

import xdi2.client.exceptions.Xdi2ClientException;
import xdi2.connector.facebook.api.FacebookApi;
import xdi2.core.ContextNode;
import xdi2.core.features.nodetypes.XdiAbstractAttribute;
import xdi2.core.features.nodetypes.XdiAbstractInstanceUnordered;
import xdi2.core.features.nodetypes.XdiAttribute;
import xdi2.core.util.StatementUtil;
import xdi2.core.xri3.XDI3Segment;
import xdi2.core.xri3.XDI3SubSegment;
import xdi2.messaging.Message;
import xdi2.messaging.MessageResult;
import danube.discoverydemo.DiscoveryDemoApplication;
import danube.discoverydemo.parties.impl.GlobalRegistryParty;
import danube.discoverydemo.parties.impl.GlobalRegistryParty.RegisterCloudSynonymResult;
import danube.discoverydemo.parties.impl.RegistrarParty;
import danube.discoverydemo.servlet.external.ExternalCallReceiver;
import danube.discoverydemo.ui.MessageDialog;
import danube.discoverydemo.xdi.XdiEndpoint;

public class FacebookConnectorPanel extends Panel implements ExternalCallReceiver {

	private static final long serialVersionUID = 1L;

	protected ResourceBundle resourceBundle;

	private XdiEndpoint xdiEndpoint;
	private XDI3Segment xdiAttributeXri;
	private XdiAttribute xdiAttribute;

	private FacebookApi facebookApi;

	public FacebookConnectorPanel() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	@Override
	public void init() {

		super.init();

		this.facebookApi = new FacebookApi("420250631345354", "c2feeda99926ab3c6096beaa8e6eca73");
	}

	@Override
	public void dispose() {

		super.dispose();
	}

	private void refresh() {

		try {

			// refresh data

			if (this.xdiAttribute == null) this.xdiGet();

			// refresh UI

		} catch (Exception ex) {

			MessageDialog.problem("Sorry, a problem occurred while retrieving your Personal Data: " + ex.getMessage(), ex);
			return;
		}
	}

	private void xdiGet() throws Xdi2ClientException {

		// $get

		Message message = this.xdiEndpoint.prepareMessage(null);
		message.createGetOperation(this.xdiAttributeXri);

		MessageResult messageResult = this.xdiEndpoint.send(message);

		ContextNode contextNode = messageResult.getGraph().getDeepContextNode(this.xdiAttributeXri);

		this.xdiAttribute = contextNode == null ? null : XdiAbstractAttribute.fromContextNode(contextNode);
	}

	private void xdiSet(String value) throws Xdi2ClientException {

		// set

		Message message = this.xdiEndpoint.prepareMessage(null);
		message.createSetOperation(StatementUtil.fromLiteralComponents(this.xdiAttributeXri, value));

		this.xdiEndpoint.send(message);
	}

	public void setData(XdiEndpoint xdiEndpoint, XDI3Segment xdiAttributeXri, XdiAttribute xdiAttribute) {

		// refresh

		this.xdiEndpoint = xdiEndpoint;
		this.xdiAttributeXri = xdiAttributeXri;
		this.xdiAttribute = xdiAttribute;

		this.refresh();
	}

	private void onConnectFacebookActionPerformed(ActionEvent e) {

		HttpServletRequest request = WebContainerServlet.getActiveConnection().getRequest();

		String redirectUri = request.getRequestURL().toString();
		redirectUri = redirectUri.substring(0, redirectUri.lastIndexOf("/clouds"));
		if (! redirectUri.endsWith("/")) redirectUri += "/";
		redirectUri += "external/facebookConnectorPanel";

		XDI3Segment userXri = this.xdiEndpoint.getCloudNumber();

		try {

			DiscoveryDemoApplication.getApp().enqueueCommand(new BrowserRedirectCommand(this.facebookApi.startOAuth(request, redirectUri, userXri)));
			return;
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, a problem occurred: " + ex.getMessage(), ex);
			return;
		}
	}

	@Override
	public void onExternalCall(DiscoveryDemoApplication application, HttpServletRequest request, HttpServletResponse response) throws IOException {

		TaskQueueHandle taskQueueHandle = application.getTaskQueueHandle();

		// error from OAuth?

		if (request.getParameter("error") != null) {

			String errorDescription = request.getParameter("error_description");
			if (errorDescription == null) errorDescription = request.getParameter("error_reason");
			if (errorDescription == null) errorDescription = request.getParameter("error");

			request.setAttribute("error", "OAuth error: " + errorDescription);
		}

		// callback from OAuth?

		if (request.getParameter("code") != null) {

			XDI3Segment userXri = FacebookConnectorPanel.this.xdiEndpoint.getCloudNumber();

			try {

				FacebookConnectorPanel.this.facebookApi.checkState(request, userXri);

				String accessToken = FacebookConnectorPanel.this.facebookApi.exchangeCodeForAccessToken(request);
				if (accessToken == null) throw new Exception("No access token received.");

				JSONObject user = FacebookConnectorPanel.this.facebookApi.getUser(accessToken);

				application.enqueueTask(taskQueueHandle, new FacebookConnectorRunnable(accessToken, user.getString("id")));

				response.sendRedirect("/");
				return;
			} catch (final Exception ex) {

				application.enqueueTask(taskQueueHandle, new Runnable() {

					public void run() {

						MessageDialog.problem("Sorry, a problem occurred: " + ex.getMessage(), ex);
						return;
					}
				});

				response.sendRedirect("/");
				return;
			}
		}
	}

	private class FacebookConnectorRunnable implements Runnable {

		private String accessToken;
		private String facebookUserId;

		private FacebookConnectorRunnable(String accessToken, String facebookUserId) {

			this.accessToken = accessToken;
			this.facebookUserId = facebookUserId;
		}

		public void run() {

			GlobalRegistryParty globalRegistryParty = DiscoveryDemoApplication.getApp().getGlobalRegistryParty();
			RegistrarParty registrarParty = DiscoveryDemoApplication.getApp().getRegistrarParty();

			// store access token

			try {

				FacebookConnectorPanel.this.xdiSet(this.accessToken);

				MessageDialog.info("Successfully connected to Facebook!");
			} catch (Xdi2ClientException ex) {

				MessageDialog.problem("Sorry, a problem occurred while storing the access token: " + ex.getMessage(), ex);
				return;
			}

			// register the cloud synonym

			XDI3Segment cloudNumber = FacebookConnectorPanel.this.xdiEndpoint.getCloudNumber();
			XDI3Segment cloudSynonym = this.cloudSynonym();

			RegisterCloudSynonymResult registerCloudSynonymResult;

			try {

				registerCloudSynonymResult = globalRegistryParty.registerCloudSynonym(registrarParty, cloudNumber, cloudSynonym);
			} catch (Exception ex) {

				MessageDialog.problem("Sorry, we could not register the Cloud Synonym: " + ex.getMessage(), ex);
				return;
			}

			// done

			MessageDialog.info("Cloud Synonym " + registerCloudSynonymResult.getCloudSynonym() + " has been registered with Cloud Number " + registerCloudSynonymResult.getCloudNumber());
		}

		private XDI3Segment cloudSynonym() {

			XDI3SubSegment arcXri = XdiAbstractInstanceUnordered.createArcXriFromHash(this.facebookUserId, false);			

			return XDI3Segment.create(arcXri.toString().substring(1).replace(":", ".") + UUID.randomUUID().toString().replace("-", "."));
		}
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		Button button1 = new Button();
		button1.setStyleName("Default");
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/danube/discoverydemo/resource/image/connect-facebook.png");
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

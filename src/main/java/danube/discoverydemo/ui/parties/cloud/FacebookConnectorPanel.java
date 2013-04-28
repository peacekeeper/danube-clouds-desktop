package danube.discoverydemo.ui.parties.cloud;

import java.io.IOException;
import java.util.ResourceBundle;

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
import xdi2.client.exceptions.Xdi2ClientException;
import xdi2.connector.facebook.api.FacebookApi;
import xdi2.core.ContextNode;
import xdi2.core.features.nodetypes.XdiAbstractAttribute;
import xdi2.core.features.nodetypes.XdiAttribute;
import xdi2.core.util.StatementUtil;
import xdi2.core.xri3.XDI3Segment;
import xdi2.messaging.Message;
import xdi2.messaging.MessageResult;
import danube.discoverydemo.DiscoveryDemoApplication;
import danube.discoverydemo.servlet.external.ExternalCallReceiver;
import danube.discoverydemo.ui.MessageDialog;
import danube.discoverydemo.xdi.XdiEndpoint;

public class FacebookConnectorPanel extends Panel implements ExternalCallReceiver {

	private static final long serialVersionUID = 1L;

	protected ResourceBundle resourceBundle;

	private XdiEndpoint endpoint;
	private XDI3Segment xdiAttributeXri;
	private XdiAttribute xdiAttribute;

	private FacebookApi facebookApi;

	/**
	 * Creates a new <code>FacebookConnectorPanel</code>.
	 */
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

		Message message = this.endpoint.prepareMessage(null);
		message.createGetOperation(this.xdiAttributeXri);

		MessageResult messageResult = this.endpoint.send(message);

		ContextNode contextNode = messageResult.getGraph().getDeepContextNode(this.xdiAttributeXri);

		this.xdiAttribute = contextNode == null ? null : XdiAbstractAttribute.fromContextNode(contextNode);
	}

	private void xdiSet(String value) throws Xdi2ClientException {

		// set

		Message message = this.endpoint.prepareMessage(null);
		message.createSetOperation(StatementUtil.fromLiteralComponents(this.xdiAttributeXri, value));

		this.endpoint.send(message);
	}

	public void setEndpointAndXdiAttributeXri(XdiEndpoint endpoint, XDI3Segment xdiAttributeXri, XdiAttribute xdiAttribute) {

		// refresh

		this.endpoint = endpoint;
		this.xdiAttributeXri = xdiAttributeXri;
		this.xdiAttribute = xdiAttribute;

		this.refresh();
	}

	private void onConnectFacebookActionPerformed(ActionEvent e) {

		HttpServletRequest request = WebContainerServlet.getActiveConnection().getRequest();

		String redirectUri = request.getRequestURL().toString();
		redirectUri = redirectUri.substring(0, redirectUri.lastIndexOf("/danube-discoverydemo"));
		if (! redirectUri.endsWith("/")) redirectUri += "/";
		redirectUri += "external/facebookConnectorPanel";

		XDI3Segment userXri = this.endpoint.getCloudNumber();

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

			XDI3Segment userXri = FacebookConnectorPanel.this.endpoint.getCloudNumber();

			try {

				FacebookConnectorPanel.this.facebookApi.checkState(request, userXri);

				final String accessToken = FacebookConnectorPanel.this.facebookApi.exchangeCodeForAccessToken(request);
				if (accessToken == null) throw new Exception("No access token received.");

				application.enqueueTask(taskQueueHandle, new Runnable() {

					public void run() {

						try {

							FacebookConnectorPanel.this.xdiSet(accessToken);
							
							MessageDialog.info("Successfully connected to Facebook!");
						} catch (Xdi2ClientException ex) {

							MessageDialog.problem("Sorry, a problem occurred: " + ex.getMessage(), ex);
							return;
						}
					}
				});

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

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		Button button1 = new Button();
		button1.setStyleName("PlainWhite");
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

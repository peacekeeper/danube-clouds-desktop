package danube.discoverydemo.ui.parties.mycloud;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nextapp.echo.app.Button;
import nextapp.echo.app.Panel;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import xdi2.core.features.nodetypes.XdiAttribute;
import xdi2.core.xri3.XDI3Segment;
import danube.discoverydemo.DiscoveryDemoApplication;
import danube.discoverydemo.servlet.external.ExternalCallReceiver;
import danube.discoverydemo.xdi.XdiEndpoint;

public class PersonalConnectorPanel extends Panel implements ExternalCallReceiver {

	private static final long serialVersionUID = 1L;

	protected ResourceBundle resourceBundle;

	private XdiEndpoint endpoint;
	private XDI3Segment xdiAttributeXri;
	private XdiAttribute xdiAttribute;

	/**
	 * Creates a new <code>PersonalConnectorPanel</code>.
	 */
	public PersonalConnectorPanel() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	@Override
	public void init() {

		super.init();
	}

	@Override
	public void dispose() {

		super.dispose();
	}

	private void onConnectPersonalActionPerformed(ActionEvent e) {

	}

	@Override
	public void onExternalCall(DiscoveryDemoApplication application, HttpServletRequest request, HttpServletResponse response) throws IOException {

	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		Button button1 = new Button();
		button1.setStyleName("Default");
		button1.setEnabled(false);
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/danube/discoverydemo/resource/image/connect-personal.png");
		button1.setIcon(imageReference1);
		button1.setText("Connect to Personal");
		button1.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onConnectPersonalActionPerformed(e);
			}
		});
		add(button1);
	}
}

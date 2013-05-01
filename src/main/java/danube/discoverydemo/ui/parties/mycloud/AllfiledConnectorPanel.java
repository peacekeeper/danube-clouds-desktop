package danube.discoverydemo.ui.parties.mycloud;

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

public class AllfiledConnectorPanel extends Panel implements ExternalCallReceiver {

	private static final long serialVersionUID = 1L;

	protected ResourceBundle resourceBundle;

	private XdiEndpoint endpoint;
	private XDI3Segment xdiAttributeXri;
	private XdiAttribute xdiAttribute;

	/**
	 * Creates a new <code>AllfiledConnectorPanel</code>.
	 */
	public AllfiledConnectorPanel() {
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

	private void onConnectAllfiledActionPerformed(ActionEvent e) {

	}

	@Override
	public void onExternalCall(DiscoveryDemoApplication application, HttpServletRequest request, HttpServletResponse response) {
		
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
				"/danube/discoverydemo/resource/image/connect-allfiled.png");
		button1.setIcon(imageReference1);
		button1.setText("Connect to Allfiled");
		button1.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onConnectAllfiledActionPerformed(e);
			}
		});
		add(button1);
	}
}

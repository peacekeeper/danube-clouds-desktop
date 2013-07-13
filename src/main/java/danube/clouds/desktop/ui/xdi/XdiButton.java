package danube.clouds.desktop.ui.xdi;

import java.util.ResourceBundle;

import nextapp.echo.app.Button;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import xdi2.core.xri3.XDI3Segment;
import danube.clouds.desktop.ui.MainWindow;
import danube.clouds.desktop.xdi.XdiEndpoint;

public class XdiButton extends Button {

	private static final long serialVersionUID = 1L;

	protected ResourceBundle resourceBundle;

	private XDI3Segment fromCloudNumber;
	private XdiEndpoint xdiEndpoint;
	private XDI3Segment address;

	public XdiButton() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	public void setData(XDI3Segment fromCloudNumber, XdiEndpoint xdiEndpoint, XDI3Segment address) {

		this.fromCloudNumber = fromCloudNumber;
		this.xdiEndpoint = xdiEndpoint;
		this.address = address;
	}

	private void onXdiActionPerformed(ActionEvent e) {

		XdiWindowPane xdiWindowPane = new XdiWindowPane();
		xdiWindowPane.setData(this.fromCloudNumber, this.xdiEndpoint, this.address);

		MainWindow.findMainContentPane(this).add(xdiWindowPane);
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setStyleName("Plain");
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/danube/clouds/desktop/resource/image/xdi.png");
		this.setIcon(imageReference1);
		this.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onXdiActionPerformed(e);
			}
		});
	}
}

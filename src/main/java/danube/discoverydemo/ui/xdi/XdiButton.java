package danube.discoverydemo.ui.xdi;

import java.util.ResourceBundle;

import nextapp.echo.app.Button;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import xdi2.core.xri3.XDI3Segment;
import danube.discoverydemo.ui.MainWindow;
import danube.discoverydemo.xdi.XdiEndpoint;
import nextapp.echo.app.ResourceImageReference;

public class XdiButton extends Button {

	private static final long serialVersionUID = 1L;

	protected ResourceBundle resourceBundle;

	private XdiEndpoint xdiEndpoint;
	private XDI3Segment address;

	public XdiButton() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	public void setData(XdiEndpoint xdiEndpoint, XDI3Segment address) {

		this.xdiEndpoint = xdiEndpoint;
		this.address = address;
	}

	private void onXdiActionPerformed(ActionEvent e) {

		XdiWindowPane xdiWindowPane = new XdiWindowPane();
		xdiWindowPane.setData(this.xdiEndpoint, this.address);

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
				"/danube/discoverydemo/resource/image/xdi.png");
		this.setIcon(imageReference1);
		this.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onXdiActionPerformed(e);
			}
		});
	}
}

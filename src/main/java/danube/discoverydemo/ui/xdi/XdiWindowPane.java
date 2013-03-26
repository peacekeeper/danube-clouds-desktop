package danube.discoverydemo.ui.xdi;

import java.util.ResourceBundle;

import nextapp.echo.app.Extent;
import nextapp.echo.app.WindowPane;
import danube.discoverydemo.xdi.XdiEndpoint;

public class XdiWindowPane extends WindowPane {

	private static final long serialVersionUID = 4136493581013444404L;

	protected ResourceBundle resourceBundle;

	private XdiContentPane xdiContentPane;

	/**
	 * Creates a new <code>ConfigureAPIsWindowPane</code>.
	 */
	public XdiWindowPane() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	public void setEndpoint(XdiEndpoint endpoint) {

		this.xdiContentPane.setXdiEndpoint(endpoint);
	}

	public XdiEndpoint getEndpoint() {

		return this.xdiContentPane.getXdiEndpoint();
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setStyleName("Gray");
		this.setTitle("XDI Data");
		this.setHeight(new Extent(600, Extent.PX));
		this.setMinimizeEnabled(false);
		this.setMaximizeEnabled(true);
		this.setClosable(true);
		this.setWidth(new Extent(800, Extent.PX));
		xdiContentPane = new XdiContentPane();
		add(xdiContentPane);
	}
}

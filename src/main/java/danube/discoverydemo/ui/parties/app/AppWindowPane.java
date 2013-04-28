package danube.discoverydemo.ui.parties.app;

import java.util.ResourceBundle;

import nextapp.echo.app.Extent;
import nextapp.echo.app.WindowPane;
import danube.discoverydemo.parties.impl.AppParty;
import danube.discoverydemo.ui.parties.peerregistry.PeerRegistryContentPane;
import danube.discoverydemo.ui.parties.app.AppContentPane;

public class AppWindowPane extends WindowPane {

	private static final long serialVersionUID = 4111493581013444404L;

	protected ResourceBundle resourceBundle;

	private AppContentPane appContentPane;

	/**
	 * Creates a new <code>AppWindowPane</code>.
	 */
	public AppWindowPane() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	public void setAppParty(AppParty appParty) {

		this.appContentPane.setAppParty(appParty);
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setStyleName("Trans");
		this.setTitle("App");
		this.setHeight(new Extent(600, Extent.PX));
		this.setMinimizeEnabled(false);
		this.setMaximizeEnabled(true);
		this.setClosable(true);
		this.setWidth(new Extent(1000, Extent.PX));
		appContentPane = new AppContentPane();
		add(appContentPane);
	}
}

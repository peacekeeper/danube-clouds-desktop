package danube.discoverydemo.ui.parties.peerregistry;

import java.util.ResourceBundle;

import nextapp.echo.app.Extent;
import nextapp.echo.app.WindowPane;
import danube.discoverydemo.parties.PeerRegistryParty;
import danube.discoverydemo.ui.parties.peerregistry.PeerRegistryContentPane;

public class PeerRegistryWindowPane extends WindowPane {

	private static final long serialVersionUID = 4111493581013444404L;

	protected ResourceBundle resourceBundle;

	private PeerRegistryContentPane peerRegistryContentPane;

	/**
	 * Creates a new <code>AppWindowPane</code>.
	 */
	public PeerRegistryWindowPane() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	public void setPeerRegistryParty(PeerRegistryParty peerRegistryParty) {

		this.peerRegistryContentPane.setPeerRegistryParty(peerRegistryParty);
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setStyleName("Trans");
		this.setTitle("Global Registry");
		this.setHeight(new Extent(600, Extent.PX));
		this.setMinimizeEnabled(false);
		this.setMaximizeEnabled(true);
		this.setClosable(true);
		this.setWidth(new Extent(1000, Extent.PX));
		peerRegistryContentPane = new PeerRegistryContentPane();
		add(peerRegistryContentPane);
	}
}

package danube.discoverydemo.ui.parties.globalregistry;

import java.util.ResourceBundle;

import nextapp.echo.app.Extent;
import nextapp.echo.app.WindowPane;

public class GlobalRegistryWindowPane extends WindowPane {

	private static final long serialVersionUID = 4111493581013444404L;

	protected ResourceBundle resourceBundle;

	private GlobalRegistryContentPane globalRegistryContentPane;

	/**
	 * Creates a new <code>GlobalRegistryWindowPane</code>.
	 */
	public GlobalRegistryWindowPane() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setStyleName("Trans");
		this.setTitle("XDI.org Registry");
		this.setHeight(new Extent(600, Extent.PX));
		this.setMinimizeEnabled(false);
		this.setMaximizeEnabled(true);
		this.setClosable(true);
		this.setWidth(new Extent(1000, Extent.PX));
		globalRegistryContentPane = new GlobalRegistryContentPane();
		add(globalRegistryContentPane);
	}
}

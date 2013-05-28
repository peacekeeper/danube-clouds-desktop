package danube.discoverydemo.ui.parties.registrar;

import java.util.ResourceBundle;

import nextapp.echo.app.Extent;
import nextapp.echo.app.WindowPane;

public class RegistrarWindowPane extends WindowPane {

	private static final long serialVersionUID = 4111493581013444404L;

	protected ResourceBundle resourceBundle;

	private RegistrarContentPane registrarContentPane;

	/**
	 * Creates a new <code>AppWindowPane</code>.
	 */
	public RegistrarWindowPane() {
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
		this.setTitle("Registrar");
		this.setHeight(new Extent(600, Extent.PX));
		this.setMinimizeEnabled(false);
		this.setMaximizeEnabled(true);
		this.setClosable(true);
		this.setWidth(new Extent(1000, Extent.PX));
		registrarContentPane = new RegistrarContentPane();
		add(registrarContentPane);
	}
}

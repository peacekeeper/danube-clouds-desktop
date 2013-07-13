package danube.clouds.desktop.ui.apps.directxdi;

import java.util.ResourceBundle;

import nextapp.echo.app.Extent;
import nextapp.echo.app.WindowPane;

public class DirectXdiAppWindowPane extends WindowPane {

	private static final long serialVersionUID = 4111493581013444404L;

	protected ResourceBundle resourceBundle;

	public DirectXdiAppWindowPane() {
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
		this.setTitle("Direct XDI App");
		this.setHeight(new Extent(700, Extent.PX));
		this.setMinimizeEnabled(false);
		this.setMaximizeEnabled(true);
		this.setClosable(true);
		this.setWidth(new Extent(1000, Extent.PX));
		DirectXdiAppContentPane appContentPane = new DirectXdiAppContentPane();
		add(appContentPane);
	}
}

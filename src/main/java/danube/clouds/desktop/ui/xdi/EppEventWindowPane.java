package danube.clouds.desktop.ui.xdi;

import ibrokerkit.epptools4java.EppEvent;

import java.util.ResourceBundle;

import nextapp.echo.app.Extent;
import nextapp.echo.app.WindowPane;

public class EppEventWindowPane extends WindowPane {

	private static final long serialVersionUID = 4136493581013444404L;

	protected ResourceBundle resourceBundle;

	private EppEventContentPane eppEventContentPane;

	public EppEventWindowPane() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	public void setData(EppEvent eppEvent) {

		this.eppEventContentPane.setData(eppEvent);
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setStyleName("Gray");
		this.setTitle("XDI Message");
		this.setHeight(new Extent(600, Extent.PX));
		this.setMinimizeEnabled(false);
		this.setMaximizeEnabled(true);
		this.setClosable(true);
		this.setWidth(new Extent(1000, Extent.PX));
		eppEventContentPane = new EppEventContentPane();
		add(eppEventContentPane);
	}
}

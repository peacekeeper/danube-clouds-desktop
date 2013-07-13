package danube.clouds.desktop.ui.xdi;

import java.util.ResourceBundle;

import nextapp.echo.app.Extent;
import nextapp.echo.app.WindowPane;
import xdi2.client.events.XDISendEvent;

public class SendEventWindowPane extends WindowPane {

	private static final long serialVersionUID = 4136493581013444404L;

	protected ResourceBundle resourceBundle;

	private SendEventContentPane sendEventContentPane;

	public SendEventWindowPane() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	public void setData(XDISendEvent sendEvent) {

		this.sendEventContentPane.setData(sendEvent);
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
		sendEventContentPane = new SendEventContentPane();
		add(sendEventContentPane);
	}
}

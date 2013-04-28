package danube.discoverydemo.ui.xdi;

import java.util.ResourceBundle;

import nextapp.echo.app.Extent;
import nextapp.echo.app.WindowPane;
import xdi2.client.events.XDISendEvent;
import danube.discoverydemo.ui.xdi.SendEventContentPane;

public class SendEventWindowPane extends WindowPane {

	private static final long serialVersionUID = 4136493581013444404L;

	protected ResourceBundle resourceBundle;

	private SendEventContentPane sendEventContentPane;

	/**
	 * Creates a new <code>ConfigureAPIsWindowPane</code>.
	 */
	public SendEventWindowPane() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	public void setSendEvent(XDISendEvent sendEvent) {

		this.sendEventContentPane.setSendEvent(sendEvent);
	}

	public XDISendEvent getSendEvent() {

		return this.sendEventContentPane.getSendEvent();
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setStyleName("Gray");
		this.setTitle("XDI Transaction");
		this.setHeight(new Extent(600, Extent.PX));
		this.setMinimizeEnabled(false);
		this.setMaximizeEnabled(true);
		this.setClosable(true);
		this.setWidth(new Extent(950, Extent.PX));
		sendEventContentPane = new SendEventContentPane();
		add(sendEventContentPane);
	}
}

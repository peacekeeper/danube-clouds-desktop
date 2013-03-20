package danube.discoverydemo.ui.parties.csp;

import java.util.ResourceBundle;

import nextapp.echo.app.Extent;
import nextapp.echo.app.WindowPane;
import danube.discoverydemo.parties.CloudServiceProviderParty;

public class CloudServiceProviderWindowPane extends WindowPane {

	private static final long serialVersionUID = 4111493581013444404L;

	protected ResourceBundle resourceBundle;

	private CloudServiceProviderContentPane accountRootContentPane;

	/**
	 * Creates a new <code>ConfigureAPIsWindowPane</code>.
	 */
	public CloudServiceProviderWindowPane() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	public void setCloudServiceProviderParty(CloudServiceProviderParty cloudServiceProviderParty) {

		this.accountRootContentPane.setCloudServiceProviderParty(cloudServiceProviderParty);
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setStyleName("Red");
		this.setTitle("Account Root");
		this.setHeight(new Extent(600, Extent.PX));
		this.setMinimizeEnabled(false);
		this.setMaximizeEnabled(true);
		this.setClosable(true);
		this.setWidth(new Extent(1000, Extent.PX));
		accountRootContentPane = new CloudServiceProviderContentPane();
		add(accountRootContentPane);
	}
}

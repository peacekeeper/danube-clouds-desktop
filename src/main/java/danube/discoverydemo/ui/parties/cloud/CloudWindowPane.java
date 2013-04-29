package danube.discoverydemo.ui.parties.cloud;

import java.util.ResourceBundle;

import nextapp.echo.app.Extent;
import nextapp.echo.app.WindowPane;
import danube.discoverydemo.parties.impl.GlobalRegistryParty.RegisterCloudNameResult;
import danube.discoverydemo.ui.parties.cloud.CloudContentPane;

public class CloudWindowPane extends WindowPane {

	private static final long serialVersionUID = 4111493581013444404L;

	protected ResourceBundle resourceBundle;

	private CloudContentPane cloudContentPane;

	public CloudWindowPane() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	public void setData(RegisterCloudNameResult registerCloudNameResult) {

		this.cloudContentPane.setData(registerCloudNameResult);
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setStyleName("Red");
		this.setTitle("My Cloud");
		this.setHeight(new Extent(700, Extent.PX));
		this.setMinimizeEnabled(false);
		this.setMaximizeEnabled(true);
		this.setClosable(true);
		this.setWidth(new Extent(1000, Extent.PX));
		cloudContentPane = new CloudContentPane();
		add(cloudContentPane);
	}
}

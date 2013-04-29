package danube.discoverydemo.ui.cloud;

import java.util.ResourceBundle;

import nextapp.echo.app.Extent;
import nextapp.echo.app.WindowPane;
import xdi2.core.features.nodetypes.XdiEntity;
import danube.discoverydemo.parties.CloudParty;

public class CloudDataWindowPane extends WindowPane {

	private static final long serialVersionUID = 4111493581013444404L;

	protected ResourceBundle resourceBundle;

	private CloudDataContentPane dataContentPane;

	public CloudDataWindowPane() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	public void setData(CloudParty cloudParty, XdiEntity xdiEntity, boolean readonly) {

		this.dataContentPane.setData(cloudParty, xdiEntity, readonly);
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setStyleName("Red");
		this.setTitle("My Cloud");
		this.setHeight(new Extent(600, Extent.PX));
		this.setMinimizeEnabled(false);
		this.setMaximizeEnabled(true);
		this.setClosable(true);
		this.setWidth(new Extent(1000, Extent.PX));
		dataContentPane = new CloudDataContentPane();
		add(dataContentPane);
	}
}
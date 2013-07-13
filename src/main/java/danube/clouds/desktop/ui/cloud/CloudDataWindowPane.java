package danube.clouds.desktop.ui.cloud;

import java.util.ResourceBundle;

import nextapp.echo.app.Extent;
import nextapp.echo.app.WindowPane;
import xdi2.core.features.nodetypes.XdiEntity;
import danube.clouds.desktop.parties.CloudParty;
import danube.clouds.desktop.parties.Party;

public class CloudDataWindowPane extends WindowPane {

	private static final long serialVersionUID = 4111493581013444404L;

	protected ResourceBundle resourceBundle;

	private CloudDataContentPane cloudDataContentPane;

	public CloudDataWindowPane() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	public void setData(Party fromParty, CloudParty cloudParty, XdiEntity xdiEntity, boolean readOnly) {

		this.cloudDataContentPane.setData(fromParty, cloudParty, xdiEntity, readOnly);
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setStyleName("Red");
		this.setTitle("Cloud Data");
		this.setHeight(new Extent(800, Extent.PX));
		this.setMinimizeEnabled(false);
		this.setMaximizeEnabled(true);
		this.setClosable(true);
		this.setWidth(new Extent(1000, Extent.PX));
		cloudDataContentPane = new CloudDataContentPane();
		add(cloudDataContentPane);
	}
}

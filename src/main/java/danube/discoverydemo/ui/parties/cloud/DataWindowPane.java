package danube.discoverydemo.ui.parties.cloud;

import java.util.ResourceBundle;

import nextapp.echo.app.Extent;
import nextapp.echo.app.WindowPane;
import xdi2.core.features.nodetypes.XdiEntity;
import xdi2.core.xri3.XDI3Segment;
import danube.discoverydemo.xdi.XdiEndpoint;

public class DataWindowPane extends WindowPane {

	private static final long serialVersionUID = 4111493581013444404L;

	protected ResourceBundle resourceBundle;

	private DataContentPane dataContentPane;

	public DataWindowPane() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	public void setData(XdiEndpoint xdiEndpoint, XDI3Segment contextNodeXri, XdiEntity xdiEntity) {

		this.dataContentPane.setData(xdiEndpoint, contextNodeXri, xdiEntity);
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setStyleName("Red");
		this.setTitle("Personal Cloud");
		this.setHeight(new Extent(600, Extent.PX));
		this.setMinimizeEnabled(false);
		this.setMaximizeEnabled(true);
		this.setClosable(true);
		this.setWidth(new Extent(1000, Extent.PX));
		dataContentPane = new DataContentPane();
		add(dataContentPane);
	}
}

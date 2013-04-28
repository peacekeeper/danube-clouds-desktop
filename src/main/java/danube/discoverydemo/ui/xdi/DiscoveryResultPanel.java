package danube.discoverydemo.ui.xdi;

import java.util.ResourceBundle;

import nextapp.echo.app.Column;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Grid;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.SplitPane;
import xdi2.discovery.XDIDiscoveryResult;

public class DiscoveryResultPanel extends Panel {

	private static final long serialVersionUID = -8974342563665273260L;

	protected ResourceBundle resourceBundle;

	private XDIDiscoveryResult discoveryResult;

	private Label xdiLabel;

	private Label cloudNumberLabel;

	private Label endpointLabel;

	public DiscoveryResultPanel() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	@Override
	public void init() {

		super.init();
	}

	@Override
	public void dispose() {

		super.dispose();
	}

	private void refresh() {

		this.xdiLabel.setText(this.discoveryResult.getXri().toString());
		this.cloudNumberLabel.setText(this.discoveryResult.getCloudNumber().toString());
		this.endpointLabel.setText(this.discoveryResult.getEndpointUri());
	}

	public void setDiscoveryResult(XDIDiscoveryResult discoveryResult) {

		// refresh

		this.discoveryResult = discoveryResult;

		this.refresh();
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setInsets(new Insets(new Extent(10, Extent.PX)));
		Grid grid1 = new Grid();
		grid1.setWidth(new Extent(100, Extent.PERCENT));
		grid1.setInsets(new Insets(new Extent(5, Extent.PX)));
		grid1.setColumnWidth(0, new Extent(150, Extent.PX));
		grid1.setSize(2);
		add(grid1);
		Label label2 = new Label();
		label2.setStyleName("Default");
		label2.setText("Identifier:");
		grid1.add(label2);
		xdiLabel = new Label();
		xdiLabel.setStyleName("Bold");
		xdiLabel.setText("...");
		grid1.add(xdiLabel);
		Label label3 = new Label();
		label3.setStyleName("Default");
		label3.setText("Cloud Number:");
		grid1.add(label3);
		cloudNumberLabel = new Label();
		cloudNumberLabel.setStyleName("Bold");
		cloudNumberLabel.setText("...");
		grid1.add(cloudNumberLabel);
		Label label5 = new Label();
		label5.setStyleName("Default");
		label5.setText("Endpoint:");
		grid1.add(label5);
		endpointLabel = new Label();
		endpointLabel.setStyleName("Bold");
		endpointLabel.setText("...");
		grid1.add(endpointLabel);
	}
}

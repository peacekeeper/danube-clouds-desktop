package danube.discoverydemo.ui.xdi;

import java.util.ResourceBundle;

import nextapp.echo.app.Border;
import nextapp.echo.app.Color;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Grid;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.layout.GridLayoutData;
import xdi2.client.http.XDIHttpClient;
import danube.discoverydemo.xdi.XdiEndpoint;

public class XdiEndpointPanel extends Panel {

	private static final long serialVersionUID = -8974342563665273260L;

	protected ResourceBundle resourceBundle;

	private XdiEndpoint xdiEndpoint;

	private Label xriLabel;
	private Label cloudNumberLabel;
	private Label endpointUriLabel;

	public XdiEndpointPanel() {
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

		if (this.xdiEndpoint != null) {

			this.xriLabel.setText(this.xdiEndpoint.getXri() == null ? "-" : this.xdiEndpoint.getXri().toString());
			this.cloudNumberLabel.setText(this.xdiEndpoint.getCloudNumber() == null ? "-" : this.xdiEndpoint.getCloudNumber().toString());
			this.endpointUriLabel.setText(! (this.xdiEndpoint.getXdiClient() instanceof XDIHttpClient) ? "-" : ((XDIHttpClient) this.xdiEndpoint.getXdiClient()).getEndpointUri().toString());
		}
	}

	public void setData(XdiEndpoint xdiEndpoint) {

		// refresh

		this.xdiEndpoint = xdiEndpoint;

		this.refresh();
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setInsets(new Insets(new Extent(10, Extent.PX)));
		this.setBorder(new Border(new Extent(4, Extent.PX), Color.BLACK,
				Border.STYLE_SOLID));
		Grid grid1 = new Grid();
		grid1.setWidth(new Extent(100, Extent.PERCENT));
		grid1.setInsets(new Insets(new Extent(5, Extent.PX)));
		grid1.setColumnWidth(0, new Extent(150, Extent.PX));
		grid1.setColumnWidth(2, new Extent(32, Extent.PX));
		grid1.setSize(2);
		add(grid1);
		Label label2 = new Label();
		label2.setStyleName("Default");
		label2.setText("Identifier:");
		grid1.add(label2);
		xriLabel = new Label();
		xriLabel.setStyleName("Bold");
		xriLabel.setText("...");
		grid1.add(xriLabel);
		Label label3 = new Label();
		label3.setStyleName("Default");
		label3.setText("Cloud Number:");
		grid1.add(label3);
		cloudNumberLabel = new Label();
		cloudNumberLabel.setStyleName("Bold");
		cloudNumberLabel.setText("...");
		GridLayoutData cloudNumberLabelLayoutData = new GridLayoutData();
		cloudNumberLabelLayoutData.setColumnSpan(2);
		cloudNumberLabel.setLayoutData(cloudNumberLabelLayoutData);
		grid1.add(cloudNumberLabel);
		Label label5 = new Label();
		label5.setStyleName("Default");
		label5.setText("Endpoint URI:");
		grid1.add(label5);
		endpointUriLabel = new Label();
		endpointUriLabel.setStyleName("Bold");
		endpointUriLabel.setText("...");
		GridLayoutData endpointUriLabelLayoutData = new GridLayoutData();
		endpointUriLabelLayoutData.setColumnSpan(2);
		endpointUriLabel.setLayoutData(endpointUriLabelLayoutData);
		grid1.add(endpointUriLabel);
	}
}

package danube.discoverydemo.ui.parties.cloud;

import java.util.ResourceBundle;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.layout.RowLayoutData;
import nextapp.echo.app.layout.SplitPaneLayoutData;
import danube.discoverydemo.events.ApplicationEvent;
import danube.discoverydemo.events.ApplicationListener;
import danube.discoverydemo.events.ApplicationXdiEndpointOpenedEvent;
import danube.discoverydemo.parties.CloudParty;
import danube.discoverydemo.ui.MessageDialog;
import danube.discoverydemo.ui.xdi.XdiEndpointPanel;
import danube.discoverydemo.xdi.XdiEndpoint;
import echopoint.ImageIcon;

public class CloudContentPane extends ContentPane implements ApplicationListener {

	private static final long serialVersionUID = 5781883512857770059L;

	protected ResourceBundle resourceBundle;

	private CloudParty cloudParty;

	private XdiEndpointPanel xdiEndpointPanel;
	private XdiEntityColumn xdiEntityColumn;

	/**
	 * Creates a new <code>ConfigureAPIsContentPane</code>.
	 */
	public CloudContentPane() {
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

		try {

			this.xdiEndpointPanel.setEndpoint(this.cloudParty.getXdiEndpoint());
			this.xdiEntityColumn.setEndpointAndContextNodeXri(this.cloudParty.getXdiEndpoint(), this.cloudParty.getXdiEndpoint().getCloudNumber(), null);
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, a problem occurred while retrieving your Personal Data: " + ex.getMessage(), ex);
			return;
		}
	}

	public void setCloudParty(CloudParty cloudParty) {

		this.cloudParty = cloudParty;

		this.refresh();
	}

	@Override
	public void onApplicationEvent(ApplicationEvent applicationEvent) {

		if (applicationEvent instanceof ApplicationXdiEndpointOpenedEvent) {

			XdiEndpoint xdiEndpoint = ((ApplicationXdiEndpointOpenedEvent) applicationEvent).getXdiEndpoint();

			this.xdiEntityColumn.setEndpointAndContextNodeXri(xdiEndpoint, xdiEndpoint.getCloudNumber(), null);
		}
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setInsets(new Insets(new Extent(10, Extent.PX)));
		SplitPane splitPane1 = new SplitPane();
		splitPane1.setStyleName("Default");
		splitPane1.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPane1.setResizable(false);
		splitPane1.setSeparatorVisible(false);
		add(splitPane1);
		Row row2 = new Row();
		SplitPaneLayoutData row2LayoutData = new SplitPaneLayoutData();
		row2LayoutData.setMinimumSize(new Extent(70, Extent.PX));
		row2LayoutData.setMaximumSize(new Extent(70, Extent.PX));
		row2.setLayoutData(row2LayoutData);
		splitPane1.add(row2);
		Row row3 = new Row();
		row3.setCellSpacing(new Extent(10, Extent.PX));
		RowLayoutData row3LayoutData = new RowLayoutData();
		row3LayoutData.setWidth(new Extent(50, Extent.PERCENT));
		row3.setLayoutData(row3LayoutData);
		row2.add(row3);
		ImageIcon imageIcon2 = new ImageIcon();
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/danube/discoverydemo/resource/image/cloud.png");
		imageIcon2.setIcon(imageReference1);
		imageIcon2.setHeight(new Extent(48, Extent.PX));
		imageIcon2.setWidth(new Extent(48, Extent.PX));
		row3.add(imageIcon2);
		Label label2 = new Label();
		label2.setStyleName("Header");
		label2.setText("Cloud");
		row3.add(label2);
		Column column1 = new Column();
		splitPane1.add(column1);
		Row row1 = new Row();
		row1.setAlignment(new Alignment(Alignment.RIGHT, Alignment.DEFAULT));
		row1.setCellSpacing(new Extent(10, Extent.PX));
		RowLayoutData row1LayoutData = new RowLayoutData();
		row1LayoutData.setWidth(new Extent(50, Extent.PERCENT));
		row1.setLayoutData(row1LayoutData);
		column1.add(row1);
		xdiEndpointPanel = new XdiEndpointPanel();
		row1.add(xdiEndpointPanel);
		XriSignInPanel xriSignInPanel1 = new XriSignInPanel();
		column1.add(xriSignInPanel1);
		xdiEntityColumn = new XdiEntityColumn();
		column1.add(xdiEntityColumn);
	}
}

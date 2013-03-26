package danube.discoverydemo.ui.xdi;

import java.util.ResourceBundle;

import nextapp.echo.app.Border;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Grid;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Row;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.layout.RowLayoutData;
import nextapp.echo.app.layout.SplitPaneLayoutData;
import xdi2.client.local.XDILocalClient;
import xdi2.messaging.target.impl.graph.GraphMessagingTarget;
import danube.discoverydemo.DiscoveryDemoApplication;
import danube.discoverydemo.ui.MessageDialog;
import danube.discoverydemo.xdi.XdiEndpoint;
import danube.discoverydemo.xdi.events.XdiListener;
import danube.discoverydemo.xdi.events.XdiResolutionEvent;
import danube.discoverydemo.xdi.events.XdiTransactionEvent;
import danube.discoverydemo.ui.xdi.GraphContentPane;

public class XdiContentPane extends ContentPane implements XdiListener {

	private static final long serialVersionUID = -6760462770679963055L;

	protected ResourceBundle resourceBundle;

	private XdiEndpoint xdiEndpoint;

	private Label identifierLabel;
	private GraphContentPane graphContentPane;

	/**
	 * Creates a new <code>XdiContentPane</code>.
	 */
	public XdiContentPane() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	@Override
	public void init() {

		super.init();

		// add us as listener

		DiscoveryDemoApplication.getApp().getXdi().addXdiListener(this);
	}

	@Override
	public void dispose() {

		super.dispose();

		// remove us as listener

		DiscoveryDemoApplication.getApp().getXdi().removeXdiListener(this);
	}

	private void refresh() {

		try {

			XDILocalClient xdiClient = (XDILocalClient) this.getXdiEndpoint().getXdiClient();
			GraphMessagingTarget messagingTarget = (GraphMessagingTarget) xdiClient.getMessagingTarget();

			this.identifierLabel.setText(this.getXdiEndpoint().getIdentifier());
			this.graphContentPane.setGraph(messagingTarget.getGraph());
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, a problem occurred while retrieving your Personal Data: " + ex.getMessage(), ex);
			return;
		}
	}

	public void setXdiEndpoint(XdiEndpoint xdiEndpoint) {

		this.xdiEndpoint = xdiEndpoint;

		this.refresh();
	}

	public XdiEndpoint getXdiEndpoint() {

		return this.xdiEndpoint;
	}

	@Override
	public void onXdiTransaction(XdiTransactionEvent xdiTransactionEvent) {

		if (xdiTransactionEvent.getXdiEndpoint() == this.getXdiEndpoint()) this.refresh();
	}

	@Override
	public void onXdiResolution(XdiResolutionEvent xdiResolutionEvent) {
		
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
		splitPane1.setSeparatorHeight(new Extent(10, Extent.PX));
		splitPane1.setSeparatorVisible(false);
		add(splitPane1);
		Column column1 = new Column();
		column1.setCellSpacing(new Extent(10, Extent.PX));
		SplitPaneLayoutData column1LayoutData = new SplitPaneLayoutData();
		column1LayoutData.setMinimumSize(new Extent(110, Extent.PX));
		column1LayoutData.setMaximumSize(new Extent(110, Extent.PX));
		column1LayoutData.setOverflow(SplitPaneLayoutData.OVERFLOW_HIDDEN);
		column1.setLayoutData(column1LayoutData);
		splitPane1.add(column1);
		Column column2 = new Column();
		column2.setCellSpacing(new Extent(5, Extent.PX));
		column1.add(column2);
		Grid grid1 = new Grid();
		grid1.setOrientation(Grid.ORIENTATION_HORIZONTAL);
		grid1.setColumnWidth(0, new Extent(120, Extent.PX));
		grid1.setSize(2);
		column2.add(grid1);
		Label label3 = new Label();
		label3.setStyleName("Default");
		label3.setText("Endpoint Identifier:");
		grid1.add(label3);
		identifierLabel = new Label();
		identifierLabel.setStyleName("Bold");
		identifierLabel.setText("...");
		grid1.add(identifierLabel);
		graphContentPane = new GraphContentPane();
		splitPane1.add(graphContentPane);
	}
}

package danube.discoverydemo.ui.xdi;

import java.util.ResourceBundle;

import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Grid;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.layout.SplitPaneLayoutData;
import xdi2.client.XDIClientListener;
import xdi2.client.events.XDIDiscoverEvent;
import xdi2.client.events.XDISendEvent;
import xdi2.core.constants.XDIConstants;
import xdi2.messaging.Message;
import xdi2.messaging.MessageResult;
import danube.discoverydemo.DiscoveryDemoApplication;
import danube.discoverydemo.ui.MessageDialog;
import danube.discoverydemo.xdi.XdiEndpoint;

public class XdiContentPane extends ContentPane implements XDIClientListener {

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

		DiscoveryDemoApplication.getApp().getEvents().addClientListener(this);
	}

	@Override
	public void dispose() {

		super.dispose();

		// remove us as listener

		DiscoveryDemoApplication.getApp().getEvents().removeClientListener(this);
	}

	private void refresh() {

		try {

			Message message = this.getXdiEndpoint().prepareMessage();
			message.createGetOperation(XDIConstants.XRI_S_ROOT);

			MessageResult messageResult = this.getXdiEndpoint().send(message);

			this.identifierLabel.setText(this.getXdiEndpoint().getXri().toString());
			this.graphContentPane.setGraph(messageResult.getGraph());
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, a problem occurred while sending an XDI message: " + ex.getMessage(), ex);
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
	public void onSend(XDISendEvent sendEvent) {

		if (sendEvent.getSource() == this.getXdiEndpoint().getXdiClient()) this.refresh();
	}

	@Override
	public void onDiscover(XDIDiscoverEvent discoverEvent) {

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

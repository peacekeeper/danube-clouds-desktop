package danube.discoverydemo.ui.xdi;

import java.util.ResourceBundle;

import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.layout.SplitPaneLayoutData;
import xdi2.client.XDIClientListener;
import xdi2.client.events.XDIDiscoverEvent;
import xdi2.client.events.XDISendEvent;
import xdi2.core.xri3.XDI3Segment;
import xdi2.messaging.Message;
import xdi2.messaging.MessageResult;
import danube.discoverydemo.DiscoveryDemoApplication;
import danube.discoverydemo.ui.MessageDialog;
import danube.discoverydemo.xdi.XdiEndpoint;
import nextapp.echo.app.Label;
import danube.discoverydemo.ui.xdi.XdiEndpointPanel;
import nextapp.echo.app.Row;
import nextapp.echo.app.Font;
import danube.discoverydemo.ui.xdi.GraphContentPane;

public class XdiContentPane extends ContentPane implements XDIClientListener {

	private static final long serialVersionUID = -6760462770679963055L;

	protected ResourceBundle resourceBundle;

	private XdiEndpoint xdiEndpoint;
	private XDI3Segment contextNodeXri;

	private XdiEndpointPanel xdiEndpointPanel;
	private Label contextNodeXriLabel;
	private GraphContentPane graphContentPane;

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

			this.xdiEndpointPanel.setData(this.xdiEndpoint);
			this.contextNodeXriLabel.setText(this.contextNodeXri.toString());

			Message message = this.xdiEndpoint.prepareMessage(this.xdiEndpoint.getCloudNumber());
			message.createGetOperation(this.contextNodeXri);

			MessageResult messageResult = this.xdiEndpoint.send(message);

			this.graphContentPane.setGraph(messageResult.getGraph());
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, a problem occurred while sending an XDI message: " + ex.getMessage(), ex);
			return;
		}
	}

	public void setData(XdiEndpoint xdiEndpoint, XDI3Segment contextNodeXri) {

		this.xdiEndpoint = xdiEndpoint;
		this.contextNodeXri = contextNodeXri;

		this.refresh();
	}

	@Override
	public void onSend(XDISendEvent sendEvent) {

		if (sendEvent.getSource() == this.xdiEndpoint.getXdiClient()) this.refresh();
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
		column1.setCellSpacing(new Extent(20, Extent.PX));
		SplitPaneLayoutData column1LayoutData = new SplitPaneLayoutData();
		column1LayoutData.setMinimumSize(new Extent(170, Extent.PX));
		column1LayoutData.setMaximumSize(new Extent(170, Extent.PX));
		column1LayoutData.setOverflow(SplitPaneLayoutData.OVERFLOW_HIDDEN);
		column1.setLayoutData(column1LayoutData);
		splitPane1.add(column1);
		xdiEndpointPanel = new XdiEndpointPanel();
		column1.add(xdiEndpointPanel);
		Row row1 = new Row();
		row1.setCellSpacing(new Extent(10, Extent.PX));
		column1.add(row1);
		Label label1 = new Label();
		label1.setStyleName("Default");
		label1.setText("Address:");
		row1.add(label1);
		contextNodeXriLabel = new Label();
		contextNodeXriLabel.setStyleName("Default");
		contextNodeXriLabel.setText("...");
		contextNodeXriLabel.setFont(new Font(null, Font.BOLD, new Extent(10,
				Extent.PT)));
		row1.add(contextNodeXriLabel);
		graphContentPane = new GraphContentPane();
		splitPane1.add(graphContentPane);
	}
}

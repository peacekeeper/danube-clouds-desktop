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

public class XdiContentPane extends ContentPane implements XDIClientListener {

	private static final long serialVersionUID = -6760462770679963055L;

	protected ResourceBundle resourceBundle;

	private XdiEndpoint xdiEndpoint;
	private XDI3Segment address;

	private GraphContentPane graphContentPane;

	private XdiEndpointPanel xdiEndpointPanel;

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
			
			Message message = this.xdiEndpoint.prepareMessage(null);
			message.createGetOperation(this.address);

			MessageResult messageResult = this.xdiEndpoint.send(message);

			this.graphContentPane.setGraph(messageResult.getGraph());
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, a problem occurred while sending an XDI message: " + ex.getMessage(), ex);
			return;
		}
	}

	public void setData(XdiEndpoint xdiEndpoint, XDI3Segment address) {

		this.xdiEndpoint = xdiEndpoint;
		this.address = address;

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
		column1.setCellSpacing(new Extent(10, Extent.PX));
		SplitPaneLayoutData column1LayoutData = new SplitPaneLayoutData();
		column1LayoutData.setMinimumSize(new Extent(170, Extent.PX));
		column1LayoutData.setMaximumSize(new Extent(170, Extent.PX));
		column1LayoutData.setOverflow(SplitPaneLayoutData.OVERFLOW_HIDDEN);
		column1.setLayoutData(column1LayoutData);
		splitPane1.add(column1);
		xdiEndpointPanel = new XdiEndpointPanel();
		column1.add(xdiEndpointPanel);
		graphContentPane = new GraphContentPane();
		splitPane1.add(graphContentPane);
	}
}

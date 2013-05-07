package danube.discoverydemo.ui.xdi;

import java.util.ResourceBundle;

import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Row;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.layout.SplitPaneLayoutData;
import xdi2.core.xri3.XDI3Segment;
import xdi2.messaging.Message;
import xdi2.messaging.MessageResult;
import danube.discoverydemo.ui.MessageDialog;
import danube.discoverydemo.xdi.XdiEndpoint;

public class XdiContentPane extends ContentPane {

	private static final long serialVersionUID = -6760462770679963055L;

	protected ResourceBundle resourceBundle;

	private XDI3Segment fromCloudNumber;
	private XdiEndpoint xdiEndpoint;
	private XDI3Segment contextNodeXri;

	private XdiEndpointPanel xdiEndpointPanel;
	private GraphContentPane graphContentPane;

	private Label contextNodeXriLabel;

	public XdiContentPane() {
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

			this.xdiEndpointPanel.setData(this.xdiEndpoint);
			this.contextNodeXriLabel.setText(this.contextNodeXri.toString());

			Message message = this.xdiEndpoint.prepareMessage(this.fromCloudNumber);
			message.createGetOperation(this.contextNodeXri);

			MessageResult messageResult = this.xdiEndpoint.send(message);

			this.graphContentPane.setData(messageResult.getGraph());
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, a problem occurred while sending an XDI message: " + ex.getMessage(), ex);
			return;
		}
	}

	public void setData(XDI3Segment fromCloudNumber, XdiEndpoint xdiEndpoint, XDI3Segment contextNodeXri) {

		this.fromCloudNumber = fromCloudNumber;
		this.xdiEndpoint = xdiEndpoint;
		this.contextNodeXri = contextNodeXri;

		this.refresh();
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
		column1LayoutData.setMinimumSize(new Extent(160, Extent.PX));
		column1LayoutData.setMaximumSize(new Extent(160, Extent.PX));
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
		label1.setText("Context:");
		row1.add(label1);
		contextNodeXriLabel = new Label();
		contextNodeXriLabel.setStyleName("Bold");
		contextNodeXriLabel.setText("...");
		row1.add(contextNodeXriLabel);
		graphContentPane = new GraphContentPane();
		splitPane1.add(graphContentPane);
	}
}

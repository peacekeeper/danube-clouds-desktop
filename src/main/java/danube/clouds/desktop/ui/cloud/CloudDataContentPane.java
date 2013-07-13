package danube.clouds.desktop.ui.cloud;

import java.util.ResourceBundle;

import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.layout.SplitPaneLayoutData;
import nextapp.echo.extras.app.TabPane;
import nextapp.echo.extras.app.layout.TabPaneLayoutData;
import xdi2.client.exceptions.Xdi2ClientException;
import xdi2.core.ContextNode;
import xdi2.core.features.nodetypes.XdiAbstractEntity;
import xdi2.core.features.nodetypes.XdiAttributeSingleton;
import xdi2.core.features.nodetypes.XdiEntity;
import xdi2.core.xri3.XDI3Segment;
import xdi2.messaging.Message;
import xdi2.messaging.MessageResult;
import xdi2.messaging.constants.XDIMessagingConstants;
import danube.clouds.desktop.parties.CloudParty;
import danube.clouds.desktop.parties.Party;
import danube.clouds.desktop.ui.MessageDialog;
import danube.clouds.desktop.xdi.XdiEndpoint;
import echopoint.ImageIcon;

public class CloudDataContentPane extends ContentPane {

	private static final long serialVersionUID = 5781883512857770059L;

	protected ResourceBundle resourceBundle;

	private XDI3Segment fromCloudNumber;
	private XdiEndpoint xdiEndpoint;
	private XDI3Segment contextNodeXri;
	private XdiEntity xdiEntity;

	private XdiEntityColumn xdiEntityColumn;
	private ContentPane contentPane;
	private Column xdiEntityCheckBoxesColumn;

	public CloudDataContentPane() {
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

	public void setData(Party fromParty, CloudParty cloudParty, XdiEntity xdiEntity, boolean readOnly) {

		// refresh

		XDI3Segment fromCloudNumber = fromParty == null ? null : fromParty.getCloudNumber();

		this.fromCloudNumber = fromCloudNumber;
		this.xdiEndpoint = cloudParty.getXdiEndpoint();
		this.contextNodeXri = cloudParty.getCloudNumber();
		this.xdiEntity = xdiEntity;

		// refresh data

		try {

			if (this.xdiEntity == null) {

				this.xdiGet();
			}

			if (this.xdiEntity == null) throw new NullPointerException();
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, a problem occurred while retrieving the Cloud Data: " + ex.getMessage(), ex);
			return;
		}

		// refresh UI

		this.xdiEntityColumn.setData(fromParty, cloudParty, this.xdiEntity);
		this.xdiEntityColumn.setReadOnly(readOnly);

		if (readOnly) {

			this.contentPane.removeAll();
			this.contentPane.add(this.xdiEntityColumn);
		}

		this.xdiEntityCheckBoxesColumn.removeAll();
		this.addXdiAttributeCheckBoxPanel(XDI3Segment.create("@respect<+major.announcements>"), this.xdiEntity, "Send me major announcements about the launch of Respect Network (average = once a quarter)");
		this.addXdiAttributeCheckBoxPanel(XDI3Segment.create("@respect<+monthly.progress>"), this.xdiEntity, "Send me monthly progress reports");
		this.addXdiAttributeCheckBoxPanel(XDI3Segment.create("@respect<+user.list>"), this.xdiEntity, "Put me on the Respect Network User Discussion List");
		this.addXdiAttributeCheckBoxPanel(XDI3Segment.create("@respect<+business.list>"), this.xdiEntity, "Put me on the Respect Network Business Discussion List");
		this.addXdiAttributeCheckBoxPanel(XDI3Segment.create("@respect<+developer.list>"), this.xdiEntity, "Put me on the Respect Network Developer Discussion List");
		this.addXdiAttributeCheckBoxPanel(XDI3Segment.create("@respect<+cloudserviceprovider.list>"), this.xdiEntity, "Put me on the Respect Network Cloud Service Provider Discussion List");
	}

	private void xdiGet() throws Xdi2ClientException {

		// $get

		Message message = this.xdiEndpoint.prepareOperation(this.fromCloudNumber, XDIMessagingConstants.XRI_S_GET, this.contextNodeXri);
		MessageResult messageResult = this.xdiEndpoint.send(message);

		ContextNode contextNode = messageResult.getGraph().getDeepContextNode(this.contextNodeXri);
		if (contextNode == null) this.xdiEntity = null;

		this.xdiEntity = XdiAbstractEntity.fromContextNode(contextNode);
	}

	private void addXdiAttributeCheckBoxPanel(XDI3Segment xdiAttributeXri, XdiEntity xdiEntity, String label) {

		ContextNode contextNode = xdiEntity.getContextNode().setDeepContextNode(xdiAttributeXri);
		XDI3Segment contextNodeXri = contextNode.getXri();

		XdiAttributeSingleton xdiAttribute = contextNode == null ? null : XdiAttributeSingleton.fromContextNode(contextNode);

		XdiAttributeCheckBoxPanel xdiAttributeCheckBoxPanel = new XdiAttributeCheckBoxPanel();
		xdiAttributeCheckBoxPanel.setData(this.fromCloudNumber, this.xdiEndpoint, contextNodeXri, xdiAttributeXri, xdiAttribute, label);
		xdiAttributeCheckBoxPanel.setReadOnly(false);

		this.xdiEntityCheckBoxesColumn.add(xdiAttributeCheckBoxPanel);
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
		row2.setCellSpacing(new Extent(10, Extent.PX));
		SplitPaneLayoutData row2LayoutData = new SplitPaneLayoutData();
		row2LayoutData.setMinimumSize(new Extent(70, Extent.PX));
		row2LayoutData.setMaximumSize(new Extent(70, Extent.PX));
		row2.setLayoutData(row2LayoutData);
		splitPane1.add(row2);
		ImageIcon imageIcon2 = new ImageIcon();
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/danube/clouds/desktop/resource/image/cloud.png");
		imageIcon2.setIcon(imageReference1);
		imageIcon2.setHeight(new Extent(48, Extent.PX));
		imageIcon2.setWidth(new Extent(48, Extent.PX));
		row2.add(imageIcon2);
		Label label2 = new Label();
		label2.setStyleName("Header");
		label2.setText("Cloud Data");
		row2.add(label2);
		contentPane = new ContentPane();
		splitPane1.add(contentPane);
		TabPane tabPane1 = new TabPane();
		tabPane1.setStyleName("Default");
		contentPane.add(tabPane1);
		ContentPane contentPane3 = new ContentPane();
		TabPaneLayoutData contentPane3LayoutData = new TabPaneLayoutData();
		contentPane3LayoutData.setTitle("Personal Data");
		contentPane3.setLayoutData(contentPane3LayoutData);
		tabPane1.add(contentPane3);
		xdiEntityColumn = new XdiEntityColumn();
		contentPane3.add(xdiEntityColumn);
		ContentPane contentPane4 = new ContentPane();
		TabPaneLayoutData contentPane4LayoutData = new TabPaneLayoutData();
		contentPane4LayoutData.setTitle("Respect Network Communications");
		contentPane4.setLayoutData(contentPane4LayoutData);
		tabPane1.add(contentPane4);
		xdiEntityCheckBoxesColumn = new Column();
		xdiEntityCheckBoxesColumn.setInsets(new Insets(
				new Extent(10, Extent.PX)));
		xdiEntityCheckBoxesColumn.setCellSpacing(new Extent(10, Extent.PX));
		contentPane4.add(xdiEntityCheckBoxesColumn);
	}
}

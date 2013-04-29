package danube.discoverydemo.ui.parties.cloud;

import java.util.ResourceBundle;

import nextapp.echo.app.Button;
import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.SplitPaneLayoutData;
import xdi2.connector.facebook.mapping.FacebookMapping;
import xdi2.core.constants.XDIPolicyConstants;
import xdi2.core.xri3.XDI3Segment;
import danube.discoverydemo.DiscoveryDemoApplication;
import danube.discoverydemo.parties.impl.CloudParty;
import danube.discoverydemo.ui.MainWindow;
import danube.discoverydemo.ui.MessageDialog;
import danube.discoverydemo.ui.xdi.XdiEndpointPanel;
import echopoint.ImageIcon;

public class CloudContentPane extends ContentPane {

	private static final long serialVersionUID = 5781883512857770059L;

	protected ResourceBundle resourceBundle;

	private XdiEndpointPanel xdiEndpointPanel;
	private FacebookConnectorPanel facebookConnectorPanel;
	private AllfiledConnectorPanel allfiledConnectorPanel;
	private PersonalConnectorPanel personalConnectorPanel;

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

	public void refresh() {

		CloudParty cloudParty = DiscoveryDemoApplication.getApp().getCloudParty();

		if (cloudParty != null) {

			try {

				this.xdiEndpointPanel.setData(cloudParty.getXdiEndpoint());
				this.facebookConnectorPanel.setData(cloudParty.getXdiEndpoint(), XDI3Segment.create("" + FacebookMapping.XRI_S_FACEBOOK_CONTEXT + cloudParty.getXdiEndpoint().getCloudNumber() + XDIPolicyConstants.XRI_S_OAUTH_TOKEN), null);
				//this.personalConnectorPanel.setData(cloudParty.getXdiEndpoint(), XDI3Segment.create("" + PersonalMapping.XRI_S_PERSONA:_CONTEXT + cloudParty.getXdiEndpoint().getCloudNumber() + XDIPolicyConstants.XRI_S_OAUTH_TOKEN), null);
				//this.allfiledConnectorPanel.setData(cloudParty.getXdiEndpoint(), XDI3Segment.create("" + AllfiledMapping.XRI_S_ALLFILED_CONTEXT + cloudParty.getXdiEndpoint().getCloudNumber() + XDIPolicyConstants.XRI_S_OAUTH_TOKEN), null);
			} catch (Exception ex) {

				MessageDialog.problem("Sorry, a problem occurred while retrieving your Personal Data: " + ex.getMessage(), ex);
				return;
			}
		}
	}

	private void onDataActionPerformed(ActionEvent e) {

		CloudParty cloudParty = DiscoveryDemoApplication.getApp().getCloudParty();

		if (cloudParty == null) {

			MessageDialog.warning("Please open a Cloud.");
			return;
		}

		DataWindowPane dataWindowPane = new DataWindowPane();
		dataWindowPane.setData(cloudParty.getXdiEndpoint(), cloudParty.getCloudNumber(), null);

		MainWindow.findMainContentPane(this).add(dataWindowPane);
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
				"/danube/discoverydemo/resource/image/cloud.png");
		imageIcon2.setIcon(imageReference1);
		imageIcon2.setHeight(new Extent(48, Extent.PX));
		imageIcon2.setWidth(new Extent(48, Extent.PX));
		row2.add(imageIcon2);
		Label label2 = new Label();
		label2.setStyleName("Header");
		label2.setText("Cloud");
		row2.add(label2);
		Column column1 = new Column();
		column1.setCellSpacing(new Extent(10, Extent.PX));
		splitPane1.add(column1);
		XriSignInPanel xriSignInPanel1 = new XriSignInPanel();
		column1.add(xriSignInPanel1);
		xdiEndpointPanel = new XdiEndpointPanel();
		column1.add(xdiEndpointPanel);
		Row row1 = new Row();
		row1.setCellSpacing(new Extent(10, Extent.PX));
		column1.add(row1);
		Button button1 = new Button();
		button1.setStyleName("Default");
		button1.setText("Manage Personal Data");
		button1.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onDataActionPerformed(e);
			}
		});
		row1.add(button1);
		facebookConnectorPanel = new FacebookConnectorPanel();
		facebookConnectorPanel.setId("facebookConnectorPanel");
		row1.add(facebookConnectorPanel);
		allfiledConnectorPanel = new AllfiledConnectorPanel();
		row1.add(allfiledConnectorPanel);
		personalConnectorPanel = new PersonalConnectorPanel();
		row1.add(personalConnectorPanel);
	}
}

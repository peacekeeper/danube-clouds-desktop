package danube.clouds.desktop.ui.parties.globalregistry;

import java.util.ResourceBundle;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.layout.ColumnLayoutData;
import nextapp.echo.app.layout.SplitPaneLayoutData;
import danube.clouds.desktop.DanubeCloudsDesktopApplication;
import danube.clouds.desktop.parties.impl.GlobalRegistryParty;
import danube.clouds.desktop.ui.xdi.XdiEndpointPanel;
import echopoint.ImageIcon;

public class GlobalRegistryContentPane extends ContentPane {

	private static final long serialVersionUID = 5781883512857770059L;

	protected ResourceBundle resourceBundle;

	private GlobalRegistryParty globalRegistryParty;

	private XdiEndpointPanel xdiEndpointPanel;

	public GlobalRegistryContentPane() {
		super();

		// Add design-time configured components.
		initComponents();

		this.globalRegistryParty = DanubeCloudsDesktopApplication.getApp().getGlobalRegistryParty();
	}

	@Override
	public void init() {

		super.init();

		this.xdiEndpointPanel.setData(this.globalRegistryParty.getXdiEndpoint());
	}

	@Override
	public void dispose() {

		super.dispose();
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
				"/danube/clouds/desktop/resource/image/globalregistry.png");
		imageIcon2.setIcon(imageReference1);
		imageIcon2.setHeight(new Extent(48, Extent.PX));
		imageIcon2.setWidth(new Extent(48, Extent.PX));
		row2.add(imageIcon2);
		Label label2 = new Label();
		label2.setStyleName("Header");
		label2.setText("XDI.org Registry");
		row2.add(label2);
		SplitPane splitPane2 = new SplitPane();
		splitPane2.setStyleName("Default");
		splitPane2.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPane2.setResizable(false);
		splitPane2.setSeparatorVisible(false);
		splitPane1.add(splitPane2);
		xdiEndpointPanel = new XdiEndpointPanel();
		splitPane2.add(xdiEndpointPanel);
		Row row1 = new Row();
		row1.setAlignment(new Alignment(Alignment.RIGHT, Alignment.DEFAULT));
		SplitPaneLayoutData row1LayoutData = new SplitPaneLayoutData();
		row1LayoutData.setMinimumSize(new Extent(100, Extent.PX));
		row1LayoutData.setMaximumSize(new Extent(100, Extent.PX));
		row1.setLayoutData(row1LayoutData);
		splitPane2.add(row1);
		ImageIcon imageIcon3 = new ImageIcon();
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/danube/clouds/desktop/resource/image/logo-xdiorg.png");
		imageIcon3.setIcon(imageReference2);
		imageIcon3.setHeight(new Extent(77, Extent.PX));
		imageIcon3.setWidth(new Extent(307, Extent.PX));
		ColumnLayoutData imageIcon3LayoutData = new ColumnLayoutData();
		imageIcon3LayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.BOTTOM));
		imageIcon3.setLayoutData(imageIcon3LayoutData);
		row1.add(imageIcon3);
	}
}

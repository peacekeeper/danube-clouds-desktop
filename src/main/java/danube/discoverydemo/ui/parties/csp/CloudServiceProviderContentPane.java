package danube.discoverydemo.ui.parties.csp;

import java.util.ResourceBundle;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Button;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.RowLayoutData;
import nextapp.echo.app.layout.SplitPaneLayoutData;
import danube.discoverydemo.parties.CloudServiceProviderParty;
import echopoint.ImageIcon;

public class CloudServiceProviderContentPane extends ContentPane {

	private static final long serialVersionUID = 5781883512857770059L;

	protected ResourceBundle resourceBundle;

	private CloudServiceProviderParty cloudServiceProviderParty;

	private Label uuidLabel;

	/**
	 * Creates a new <code>ConfigureAPIsContentPane</code>.
	 */
	public CloudServiceProviderContentPane() {
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

	}

	public void setCloudServiceProviderParty(CloudServiceProviderParty cloudServiceProviderParty) {

		// refresh

		this.cloudServiceProviderParty = cloudServiceProviderParty;

		this.refresh();
	}

	private void onUuidButtonActionPerformed(ActionEvent e) {

		String uuid = this.cloudServiceProviderParty.generateGui();

		this.uuidLabel.setText(uuid);
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
				"/danube/discoverydemo/web/resource/image/accountroot.png");
		imageIcon2.setIcon(imageReference1);
		imageIcon2.setHeight(new Extent(48, Extent.PX));
		imageIcon2.setWidth(new Extent(48, Extent.PX));
		row3.add(imageIcon2);
		Label label2 = new Label();
		label2.setStyleName("Header");
		label2.setText("Cloud Service Provider");
		row3.add(label2);
		Row row1 = new Row();
		row1.setAlignment(new Alignment(Alignment.RIGHT, Alignment.DEFAULT));
		row1.setCellSpacing(new Extent(10, Extent.PX));
		RowLayoutData row1LayoutData = new RowLayoutData();
		row1LayoutData.setWidth(new Extent(50, Extent.PERCENT));
		row1.setLayoutData(row1LayoutData);
		row2.add(row1);
		Button uuidButton = new Button();
		uuidButton.setStyleName("Default");
		uuidButton.setText("button1");
		uuidButton.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onUuidButtonActionPerformed(e);
			}
		});
		row1.add(uuidButton);
		Label label1 = new Label();
		label1.setStyleName("Default");
		label1.setText("UUID:");
		row1.add(label1);
		uuidLabel = new Label();
		uuidLabel.setStyleName("Default");
		uuidLabel.setText("label3");
		row1.add(uuidLabel);
	}
}

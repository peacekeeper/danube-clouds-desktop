package danube.discoverydemo.ui;

import java.util.ResourceBundle;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Button;
import nextapp.echo.app.CheckBox;
import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.FillImage;
import nextapp.echo.app.IllegalChildException;
import nextapp.echo.app.Insets;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.SplitPaneLayoutData;
import danube.discoverydemo.DiscoveryDemoApplication;
import danube.discoverydemo.events.ApplicationEvent;
import danube.discoverydemo.events.ApplicationListener;
import danube.discoverydemo.ui.log.LogWindowPane;
import danube.discoverydemo.ui.parties.csp.CloudServiceProviderWindowPane;
import echopoint.ImageIcon;

public class MainContentPane extends ContentPane implements ApplicationListener {

	private static final long serialVersionUID = 3164240822381021756L;

	protected ResourceBundle resourceBundle;

	private CheckBox logWindowCheckBox;
	private CheckBox developerModeCheckBox;

	/**
	 * Creates a new <code>MainContentPane</code>.
	 */
	public MainContentPane() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	@Override
	public void init() {

		super.init();

		// add us as listener

		DiscoveryDemoApplication.getApp().addApplicationListener(this);
	}

	@Override
	public void dispose() {

		super.dispose();

		// remove us as listener

		DiscoveryDemoApplication.getApp().removeApplicationListener(this);
	}

	@Override
	public void add(Component component, int n) throws IllegalChildException {

		super.add(component, n);

		if (component instanceof MessageDialog) {

			((MessageDialog) component).setZIndex(Integer.MAX_VALUE);
		} if (component instanceof WindowPane) {

			((WindowPane) component).setZIndex(Integer.MAX_VALUE - 1);
		}
	}

	public boolean isDeveloperModeSelected() {

		return this.developerModeCheckBox.isSelected();
	}

	public void onApplicationEvent(ApplicationEvent applicationEvent) {

	}

	private void onCloudServiceProviderPartyActionPerformed(ActionEvent e) {

		CloudServiceProviderWindowPane cloudServiceProviderWindowPane = new CloudServiceProviderWindowPane();
		cloudServiceProviderWindowPane.setCloudServiceProviderParty(((DiscoveryDemoApplication) this.getApplicationInstance()).getCloudServiceProviderParty()); 

		this.add(cloudServiceProviderWindowPane);
	}

	private void onLogWindowActionPerformed(ActionEvent e) {

		if (this.logWindowCheckBox.isSelected()) {

			MainWindow.findChildComponentByClass(this, LogWindowPane.class).setVisible(true);
		} else {

			MainWindow.findChildComponentByClass(this, LogWindowPane.class).setVisible(false);
		}
	}

	private void onDeveloperModeActionPerformed(ActionEvent e) {

		if (this.developerModeCheckBox.isSelected()) {

			MainWindow.findChildComponentById(this, "transactionEventPanelsContentPane").setVisible(true);
			for (Component component : MainWindow.findChildComponentsByClass(this, DeveloperModeComponent.class)) component.setVisible(true);
		} else {

			MainWindow.findChildComponentById(this, "transactionEventPanelsContentPane").setVisible(false);
			for (Component component : MainWindow.findChildComponentsByClass(this, DeveloperModeComponent.class)) component.setVisible(false);
		}
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/danube/discoverydemo/web/resource/image/mainback-gray.jpg");
		this.setBackgroundImage(new FillImage(imageReference1));
		SplitPane splitPane1 = new SplitPane();
		splitPane1.setStyleName("Default");
		splitPane1.setSeparatorVisible(false);
		add(splitPane1);
		Column column1 = new Column();
		splitPane1.add(column1);
		Row row4 = new Row();
		row4.setCellSpacing(new Extent(10, Extent.PX));
		column1.add(row4);
		Button button1 = new Button();
		button1.setStyleName("PlainWhite");
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/danube/discoverydemo/web/resource/image/accountroot.png");
		button1.setIcon(imageReference2);
		button1.setText("Cloud Service Provider");
		button1.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onCloudServiceProviderPartyActionPerformed(e);
			}
		});
		row4.add(button1);
		Column column2 = new Column();
		column2.setInsets(new Insets(new Extent(10, Extent.PX)));
		SplitPaneLayoutData column2LayoutData = new SplitPaneLayoutData();
		column2LayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		column2LayoutData.setMinimumSize(new Extent(400, Extent.PX));
		column2LayoutData.setMaximumSize(new Extent(400, Extent.PX));
		column2.setLayoutData(column2LayoutData);
		splitPane1.add(column2);
		ImageIcon imageIcon1 = new ImageIcon();
		ResourceImageReference imageReference3 = new ResourceImageReference(
				"/danube/discoverydemo/web/resource/image/pds-logo.png");
		imageIcon1.setIcon(imageReference3);
		imageIcon1.setHeight(new Extent(45, Extent.PX));
		imageIcon1.setWidth(new Extent(337, Extent.PX));
		imageIcon1.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(
				10, Extent.PX), new Extent(0, Extent.PX), new Extent(0,
						Extent.PX)));
		column2.add(imageIcon1);
		Row row2 = new Row();
		row2.setAlignment(new Alignment(Alignment.RIGHT, Alignment.DEFAULT));
		row2.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(0,
				Extent.PX), new Extent(0, Extent.PX), new Extent(10, Extent.PX)));
		row2.setCellSpacing(new Extent(10, Extent.PX));
		column2.add(row2);
		ImageIcon imageIcon2 = new ImageIcon();
		ResourceImageReference imageReference4 = new ResourceImageReference(
				"/danube/discoverydemo/web/resource/image/projectdanube.png");
		imageIcon2.setIcon(imageReference4);
		imageIcon2.setHeight(new Extent(68, Extent.PX));
		imageIcon2.setWidth(new Extent(68, Extent.PX));
		row2.add(imageIcon2);
		Column column3 = new Column();
		column3.setCellSpacing(new Extent(10, Extent.PX));
		row2.add(column3);
		logWindowCheckBox = new CheckBox();
		logWindowCheckBox.setSelected(false);
		logWindowCheckBox.setText("Show Log Window");
		logWindowCheckBox.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onLogWindowActionPerformed(e);
			}
		});
		column3.add(logWindowCheckBox);
		developerModeCheckBox = new CheckBox();
		developerModeCheckBox.setSelected(false);
		developerModeCheckBox.setText("Enable Developer Mode");
		developerModeCheckBox.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onDeveloperModeActionPerformed(e);
			}
		});
		column3.add(developerModeCheckBox);
		LogWindowPane logWindowPane1 = new LogWindowPane();
		logWindowPane1.setVisible(false);
		add(logWindowPane1);
	}
}

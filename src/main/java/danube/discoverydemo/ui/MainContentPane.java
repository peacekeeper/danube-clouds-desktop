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
import danube.discoverydemo.parties.AppParty;
import danube.discoverydemo.parties.CloudServiceProviderParty;
import danube.discoverydemo.parties.GlobalRegistryParty;
import danube.discoverydemo.parties.PeerRegistryParty;
import danube.discoverydemo.parties.RegistrarParty;
import danube.discoverydemo.ui.log.LogWindowPane;
import danube.discoverydemo.ui.parties.app.AppWindowPane;
import danube.discoverydemo.ui.parties.cloudserviceprovider.CloudServiceProviderWindowPane;
import danube.discoverydemo.ui.parties.globalregistry.GlobalRegistryWindowPane;
import danube.discoverydemo.ui.parties.peerregistry.PeerRegistryWindowPane;
import danube.discoverydemo.ui.parties.registrar.RegistrarWindowPane;
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

	private void onCloudServiceProviderPartyActionPerformed(ActionEvent e) {

		CloudServiceProviderParty cloudServiceProviderParty = DiscoveryDemoApplication.getApp().getCloudServiceProviderParty();

		CloudServiceProviderWindowPane cloudServiceProviderWindowPane = new CloudServiceProviderWindowPane();
		cloudServiceProviderWindowPane.setCloudServiceProviderParty(cloudServiceProviderParty); 

		this.add(cloudServiceProviderWindowPane);
	}

	private void onRegistrarActionPerformed(ActionEvent e) {

		RegistrarParty registrarParty = DiscoveryDemoApplication.getApp().getRegistrarParty();

		RegistrarWindowPane registrarWindowPane = new RegistrarWindowPane();
		registrarWindowPane.setRegistrarParty(registrarParty);

		this.add(registrarWindowPane);
	}

	private void onGlobalRegistryActionPerformed(ActionEvent e) {

		GlobalRegistryParty globalRegistryParty = DiscoveryDemoApplication.getApp().getGlobalRegistryParty();

		GlobalRegistryWindowPane globalRegistryWindowPane = new GlobalRegistryWindowPane();
		globalRegistryWindowPane.setGlobalRegistryParty(globalRegistryParty);

		this.add(globalRegistryWindowPane);
	}

	private void onPeerRegistryActionPerformed(ActionEvent e) {

		PeerRegistryParty peerRegistryParty = DiscoveryDemoApplication.getApp().getPeerRegistryParty();

		PeerRegistryWindowPane peerRegistryWindowPane = new PeerRegistryWindowPane();
		peerRegistryWindowPane.setPeerRegistryParty(peerRegistryParty);

		this.add(peerRegistryWindowPane);
	}

	private void onAppActionPerformed(ActionEvent e) {

		AppParty appParty = DiscoveryDemoApplication.getApp().getAppParty();

		AppWindowPane appWindowPane = new AppWindowPane();
		appWindowPane.setAppParty(appParty);

		this.add(appWindowPane);
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/danube/discoverydemo/resource/image/mainback-gray.jpg");
		this.setBackgroundImage(new FillImage(imageReference1));
		SplitPane splitPane1 = new SplitPane();
		splitPane1.setStyleName("Default");
		splitPane1.setSeparatorVisible(false);
		add(splitPane1);
		Column column1 = new Column();
		splitPane1.add(column1);
		Row row4 = new Row();
		row4.setInsets(new Insets(new Extent(20, Extent.PX)));
		row4.setCellSpacing(new Extent(10, Extent.PX));
		column1.add(row4);
		Button button1 = new Button();
		button1.setStyleName("PlainWhite");
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/danube/discoverydemo/resource/image/cloudserviceprovider.png");
		button1.setIcon(imageReference2);
		button1.setText("Cloud Service Provider");
		button1.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onCloudServiceProviderPartyActionPerformed(e);
			}
		});
		row4.add(button1);
		Button button5 = new Button();
		button5.setStyleName("PlainWhite");
		ResourceImageReference imageReference3 = new ResourceImageReference(
				"/danube/discoverydemo/resource/image/registrar.png");
		button5.setIcon(imageReference3);
		button5.setText("Registrar");
		button5.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onRegistrarActionPerformed(e);
			}
		});
		row4.add(button5);
		Button button2 = new Button();
		button2.setStyleName("PlainWhite");
		ResourceImageReference imageReference4 = new ResourceImageReference(
				"/danube/discoverydemo/resource/image/globalregistry.png");
		button2.setIcon(imageReference4);
		button2.setText("Global Registry");
		button2.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onGlobalRegistryActionPerformed(e);
			}
		});
		row4.add(button2);
		Button button3 = new Button();
		button3.setStyleName("PlainWhite");
		ResourceImageReference imageReference5 = new ResourceImageReference(
				"/danube/discoverydemo/resource/image/peerregistry.png");
		button3.setIcon(imageReference5);
		button3.setText("Peer Registry");
		button3.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onPeerRegistryActionPerformed(e);
			}
		});
		row4.add(button3);
		Button button4 = new Button();
		button4.setStyleName("PlainWhite");
		ResourceImageReference imageReference6 = new ResourceImageReference(
				"/danube/discoverydemo/resource/image/app.png");
		button4.setIcon(imageReference6);
		button4.setText("App");
		button4.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onAppActionPerformed(e);
			}
		});
		row4.add(button4);
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
		ResourceImageReference imageReference7 = new ResourceImageReference(
				"/danube/discoverydemo/resource/image/pds-logo.png");
		imageIcon1.setIcon(imageReference7);
		imageIcon1.setHeight(new Extent(45, Extent.PX));
		imageIcon1.setVisible(false);
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
		ResourceImageReference imageReference8 = new ResourceImageReference(
				"/danube/discoverydemo/resource/image/projectdanube.png");
		imageIcon2.setIcon(imageReference8);
		imageIcon2.setHeight(new Extent(68, Extent.PX));
		imageIcon2.setVisible(false);
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

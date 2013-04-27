package danube.discoverydemo.ui;

import java.util.ResourceBundle;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Button;
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
import danube.discoverydemo.ui.log.LogContentPane;
import danube.discoverydemo.ui.parties.app.AppWindowPane;
import danube.discoverydemo.ui.parties.cloud.CloudWindowPane;
import danube.discoverydemo.ui.parties.cloudserviceprovider.CloudServiceProviderWindowPane;
import danube.discoverydemo.ui.parties.globalregistry.GlobalRegistryWindowPane;
import danube.discoverydemo.ui.parties.peerregistry.PeerRegistryWindowPane;
import danube.discoverydemo.ui.parties.registrar.RegistrarWindowPane;
import echopoint.ImageIcon;
import nextapp.echo.app.Color;
import nextapp.echo.app.CheckBox;

public class MainContentPane extends ContentPane implements ApplicationListener {

	private static final long serialVersionUID = 3164240822381021756L;

	protected ResourceBundle resourceBundle;

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

	public void onApplicationEvent(ApplicationEvent applicationEvent) {

	}

	private void onCloudServiceProviderPartyActionPerformed(ActionEvent e) {

		CloudServiceProviderParty cloudServiceProviderParty = DiscoveryDemoApplication.getApp().getCloudServiceProviderParty();

		CloudServiceProviderWindowPane cloudServiceProviderWindowPane = new CloudServiceProviderWindowPane();
		cloudServiceProviderWindowPane.setCloudServiceProviderParty(cloudServiceProviderParty); 

		this.add(cloudServiceProviderWindowPane);
	}

	private void onCloudPartyActionPerformed(ActionEvent e) {

		CloudWindowPane cloudWindowPane = new CloudWindowPane();

		this.add(cloudWindowPane);
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
		this.setBackground(Color.BLACK);
		SplitPane splitPane2 = new SplitPane();
		splitPane2.setStyleName("Default");
		splitPane2.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPane2.setSeparatorVisible(false);
		add(splitPane2);
		SplitPane splitPane1 = new SplitPane();
		splitPane1.setStyleName("Default");
		splitPane1.setOrientation(SplitPane.ORIENTATION_HORIZONTAL_LEFT_RIGHT);
		splitPane1.setSeparatorVisible(false);
		SplitPaneLayoutData splitPane1LayoutData = new SplitPaneLayoutData();
		splitPane1LayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		splitPane1.setLayoutData(splitPane1LayoutData);
		splitPane2.add(splitPane1);
		Column column1 = new Column();
		SplitPaneLayoutData column1LayoutData = new SplitPaneLayoutData();
		column1LayoutData.setAlignment(new Alignment(Alignment.LEFT,
				Alignment.DEFAULT));
		column1.setLayoutData(column1LayoutData);
		splitPane1.add(column1);
		Row row4 = new Row();
		row4.setInsets(new Insets(new Extent(20, Extent.PX)));
		row4.setCellSpacing(new Extent(10, Extent.PX));
		column1.add(row4);
		Button button1 = new Button();
		button1.setStyleName("PlainWhite");
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/danube/discoverydemo/resource/image/cloudserviceprovider.png");
		button1.setIcon(imageReference1);
		button1.setText("Cloud Service Provider");
		button1.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onCloudServiceProviderPartyActionPerformed(e);
			}
		});
		row4.add(button1);
		Button button6 = new Button();
		button6.setStyleName("PlainWhite");
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/danube/discoverydemo/resource/image/cloud.png");
		button6.setIcon(imageReference2);
		button6.setText("Cloud");
		button6.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onCloudPartyActionPerformed(e);
			}
		});
		row4.add(button6);
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
		ImageIcon imageIcon2 = new ImageIcon();
		ResourceImageReference imageReference7 = new ResourceImageReference(
				"/danube/discoverydemo/resource/image/projectdanube.png");
		imageIcon2.setIcon(imageReference7);
		imageIcon2.setHeight(new Extent(72, Extent.PX));
		imageIcon2.setWidth(new Extent(303, Extent.PX));
		column2.add(imageIcon2);
		LogContentPane logContentPane1 = new LogContentPane();
		logContentPane1.setBackground(Color.WHITE);
		SplitPaneLayoutData logContentPane1LayoutData = new SplitPaneLayoutData();
		logContentPane1LayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.BOTTOM));
		logContentPane1LayoutData.setMinimumSize(new Extent(150, Extent.PX));
		logContentPane1LayoutData.setMaximumSize(new Extent(150, Extent.PX));
		logContentPane1.setLayoutData(logContentPane1LayoutData);
		splitPane2.add(logContentPane1);
	}
}

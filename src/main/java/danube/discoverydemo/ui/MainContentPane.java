package danube.discoverydemo.ui;

import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Element;
import nextapp.echo.app.Alignment;
import nextapp.echo.app.Border;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.IllegalChildException;
import nextapp.echo.app.Insets;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.TaskQueueHandle;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.SplitPaneLayoutData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import danube.discoverydemo.DiscoveryDemoApplication;
import danube.discoverydemo.parties.impl.GlobalRegistryParty.RegisterCloudNameResult;
import danube.discoverydemo.servlet.external.ExternalCallReceiver;
import danube.discoverydemo.ui.apps.directxdi.DirectXdiAppWindowPane;
import danube.discoverydemo.ui.apps.discovery.DiscoveryAppWindowPane;
import danube.discoverydemo.ui.log.LogContentPane;
import danube.discoverydemo.ui.parties.cloudserviceprovider.CloudServiceProviderWindowPane;
import danube.discoverydemo.ui.parties.globalregistry.GlobalRegistryWindowPane;
import danube.discoverydemo.ui.parties.mycloud.MyCloudWindowPane;
import danube.discoverydemo.ui.parties.othercloud.OtherCloudWindowPane;
import danube.discoverydemo.ui.parties.registrar.RegistrarWindowPane;
import echopoint.ImageIcon;

public class MainContentPane extends ContentPane implements ExternalCallReceiver {

	private static final long serialVersionUID = 3164240822381021756L;

	private static final Logger log = LoggerFactory.getLogger(MainContentPane.class);

	protected ResourceBundle resourceBundle;

	public MainContentPane() {
		super();

		// Add design-time configured components.
		initComponents();

		this.setId("main");
	}

	@Override
	public void init() {

		super.init();
	}

	@Override
	public void dispose() {

		super.dispose();
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

	private void onCloudServiceProviderPartyActionPerformed(ActionEvent e) {

		CloudServiceProviderWindowPane cloudServiceProviderWindowPane = new CloudServiceProviderWindowPane();

		this.add(cloudServiceProviderWindowPane);
	}

	private void onMyCloudPartyActionPerformed(ActionEvent e) {

		MyCloudWindowPane myCloudWindowPane = new MyCloudWindowPane();

		this.add(myCloudWindowPane);
	}

	private void onOtherCloudPartyActionPerformed(ActionEvent e) {

		OtherCloudWindowPane otherCloudWindowPane = new OtherCloudWindowPane();

		this.add(otherCloudWindowPane);
	}

	private void onRegistrarActionPerformed(ActionEvent e) {

		RegistrarWindowPane registrarWindowPane = new RegistrarWindowPane();

		this.add(registrarWindowPane);
	}

	private void onGlobalRegistryActionPerformed(ActionEvent e) {

		GlobalRegistryWindowPane globalRegistryWindowPane = new GlobalRegistryWindowPane();

		this.add(globalRegistryWindowPane);
	}

	private void onPeerRegistryActionPerformed(ActionEvent e) {

		/*		PeerRegistryWindowPane peerRegistryWindowPane = new PeerRegistryWindowPane();

		this.add(peerRegistryWindowPane);*/
		MessageDialog.warning("Not implemented.");
	}

	private void onDiscoveryAppActionPerformed(ActionEvent e) {

		DiscoveryAppWindowPane appWindowPane = new DiscoveryAppWindowPane();

		this.add(appWindowPane);
	}

	private void onDirectXdiAppActionPerformed(ActionEvent e) {

		DirectXdiAppWindowPane appWindowPane = new DirectXdiAppWindowPane();

		this.add(appWindowPane);
	}

	@Override
	public void onExternalCall(DiscoveryDemoApplication application, HttpServletRequest request, HttpServletResponse response) throws IOException {

		String cloudNumberString = request.getQueryString();
		if (cloudNumberString == null) return;
		if (cloudNumberString.startsWith("?")) cloudNumberString = cloudNumberString.substring(1);
		final String finalCloudNumberString = cloudNumberString;

		Map<String, Object> cloudCache2 = DiscoveryDemoApplication.getAppFromSession(request.getSession()).getServlet().getCloudCache2();
		TaskQueueHandle taskQueueHandle = DiscoveryDemoApplication.getAppFromSession(request.getSession()).getTaskQueueHandle();

		log.info("CACHE GET: " + cloudNumberString);
		Object cloudNumberElement = cloudCache2.get(cloudNumberString);

		if (cloudNumberElement == null) {
			application.enqueueTask(taskQueueHandle, new Runnable() {

				public void run() {

					MessageDialog.warning("Cloud Number " + finalCloudNumberString + " not found.");
					return;
				}
			});

			return;
		}

		RegisterCloudNameResult registerCloudNameResult = (RegisterCloudNameResult) cloudNumberElement;

		MyCloudWindowPane cloudWindowPane = new MyCloudWindowPane();
		cloudWindowPane.setData(registerCloudNameResult);

		this.add(cloudWindowPane);
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
		Row row1 = new Row();
		row1.setBorder(new Border(new Border.Side[] {
				new Border.Side(new Extent(1, Extent.PX), Color.BLACK,
						Border.STYLE_SOLID),
						new Border.Side(new Extent(1, Extent.PX), Color.BLACK,
								Border.STYLE_SOLID),
								new Border.Side(new Extent(2, Extent.PX), Color.WHITE,
										Border.STYLE_SOLID),
										new Border.Side(new Extent(1, Extent.PX), Color.BLACK,
												Border.STYLE_SOLID) }));
		column1.add(row1);
		ImageIcon imageIcon3 = new ImageIcon();
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/danube/discoverydemo/resource/image/title.png");
		imageIcon3.setIcon(imageReference1);
		imageIcon3.setHeight(new Extent(76, Extent.PX));
		imageIcon3.setWidth(new Extent(696, Extent.PX));
		row1.add(imageIcon3);
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
		Button button6 = new Button();
		button6.setStyleName("PlainWhite");
		ResourceImageReference imageReference6 = new ResourceImageReference(
				"/danube/discoverydemo/resource/image/cloud.png");
		button6.setIcon(imageReference6);
		button6.setText("My Cloud");
		button6.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onMyCloudPartyActionPerformed(e);
			}
		});
		row4.add(button6);
		Button button8 = new Button();
		button8.setStyleName("PlainWhite");
		button8.setIcon(imageReference6);
		button8.setText("Other Cloud");
		button8.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onOtherCloudPartyActionPerformed(e);
			}
		});
		row4.add(button8);
		Button button4 = new Button();
		button4.setStyleName("PlainWhite");
		ResourceImageReference imageReference7 = new ResourceImageReference(
				"/danube/discoverydemo/resource/image/app.png");
		button4.setIcon(imageReference7);
		button4.setText("Discovery");
		button4.setVisible(false);
		button4.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onDiscoveryAppActionPerformed(e);
			}
		});
		row4.add(button4);
		Button button7 = new Button();
		button7.setStyleName("PlainWhite");
		button7.setIcon(imageReference7);
		button7.setText("Direct XDI");
		button7.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onDirectXdiAppActionPerformed(e);
			}
		});
		row4.add(button7);
		Column column2 = new Column();
		column2.setInsets(new Insets(new Extent(10, Extent.PX)));
		SplitPaneLayoutData column2LayoutData = new SplitPaneLayoutData();
		column2LayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		column2LayoutData.setMinimumSize(new Extent(350, Extent.PX));
		column2LayoutData.setMaximumSize(new Extent(350, Extent.PX));
		column2.setLayoutData(column2LayoutData);
		splitPane1.add(column2);
		ImageIcon imageIcon2 = new ImageIcon();
		ResourceImageReference imageReference8 = new ResourceImageReference(
				"/danube/discoverydemo/resource/image/projectdanube.png");
		imageIcon2.setIcon(imageReference8);
		imageIcon2.setHeight(new Extent(72, Extent.PX));
		imageIcon2.setWidth(new Extent(303, Extent.PX));
		column2.add(imageIcon2);
		LogContentPane logContentPane1 = new LogContentPane();
		logContentPane1.setBackground(new Color(0xcccccc));
		SplitPaneLayoutData logContentPane1LayoutData = new SplitPaneLayoutData();
		logContentPane1LayoutData.setMinimumSize(new Extent(200, Extent.PX));
		logContentPane1LayoutData.setMaximumSize(new Extent(200, Extent.PX));
		logContentPane1.setLayoutData(logContentPane1LayoutData);
		splitPane2.add(logContentPane1);
	}
}

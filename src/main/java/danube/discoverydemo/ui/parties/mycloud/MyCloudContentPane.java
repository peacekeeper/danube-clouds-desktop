package danube.discoverydemo.ui.parties.mycloud;

import java.util.ResourceBundle;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Button;
import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Font;
import nextapp.echo.app.Grid;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.PasswordField;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.TextField;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.SplitPaneLayoutData;
import xdi2.connector.facebook.mapping.FacebookMapping;
import xdi2.core.constants.XDIPolicyConstants;
import xdi2.core.xri3.XDI3Segment;
import xdi2.discovery.XDIDiscoveryResult;
import danube.discoverydemo.DiscoveryDemoApplication;
import danube.discoverydemo.parties.impl.AnonymousParty;
import danube.discoverydemo.parties.impl.MyCloudParty;
import danube.discoverydemo.parties.impl.CloudServiceProviderParty;
import danube.discoverydemo.parties.impl.CloudServiceProviderParty.RegisterCloudResult;
import danube.discoverydemo.parties.impl.GlobalRegistryParty;
import danube.discoverydemo.parties.impl.GlobalRegistryParty.RegisterCloudNameResult;
import danube.discoverydemo.parties.impl.RegistrarParty;
import danube.discoverydemo.ui.MainWindow;
import danube.discoverydemo.ui.MessageDialog;
import danube.discoverydemo.ui.xdi.XdiEndpointPanel;
import echopoint.ImageIcon;
import danube.discoverydemo.ui.parties.mycloud.AllfiledConnectorPanel;
import danube.discoverydemo.ui.parties.mycloud.FacebookConnectorPanel;
import danube.discoverydemo.ui.parties.mycloud.PersonalConnectorPanel;
import nextapp.echo.app.layout.RowLayoutData;

public class MyCloudContentPane extends ContentPane {

	private static final long serialVersionUID = 5781883512857770059L;

	protected ResourceBundle resourceBundle;

	private RegisterCloudNameResult registerCloudNameResult;

	private Column signInColumn;
	private Column registerColumn;
	private TextField secretTokenTextField;
	private FacebookConnectorPanel facebookConnectorPanel;
	private AllfiledConnectorPanel allfiledConnectorPanel;
	private PersonalConnectorPanel personalConnectorPanel;
	private XdiEndpointPanel xdiEndpointPanel;
	private TextField cloudNameTextField;
	private PasswordField secretTokenField;
	private Label cloudNameLabel;
	private Label cloudNumberLabel;
	private Label endpointUriLabel;
	private Button managePersonalDataButton;

	public MyCloudContentPane() {
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

		MyCloudParty cloudParty = DiscoveryDemoApplication.getApp().getMyCloudParty();

		if (cloudParty != null) {

			try {

				this.xdiEndpointPanel.setData(cloudParty.getXdiEndpoint());

				this.managePersonalDataButton.setEnabled(true);
				this.facebookConnectorPanel.setEnabled(true);
				this.personalConnectorPanel.setEnabled(true);
				this.allfiledConnectorPanel.setEnabled(true);

				this.facebookConnectorPanel.setData(cloudParty.getXdiEndpoint(), XDI3Segment.create("" + FacebookMapping.XRI_S_FACEBOOK_CONTEXT + cloudParty.getXdiEndpoint().getCloudNumber() + XDIPolicyConstants.XRI_S_OAUTH_TOKEN), null);
				//this.personalConnectorPanel.setData(cloudParty.getXdiEndpoint(), XDI3Segment.create("" + PersonalMapping.XRI_S_PERSONA:_CONTEXT + cloudParty.getXdiEndpoint().getCloudNumber() + XDIPolicyConstants.XRI_S_OAUTH_TOKEN), null);
				//this.allfiledConnectorPanel.setData(cloudParty.getXdiEndpoint(), XDI3Segment.create("" + AllfiledMapping.XRI_S_ALLFILED_CONTEXT + cloudParty.getXdiEndpoint().getCloudNumber() + XDIPolicyConstants.XRI_S_OAUTH_TOKEN), null);
			} catch (Exception ex) {

				MessageDialog.problem("Sorry, a problem occurred while retrieving your Personal Data: " + ex.getMessage(), ex);
				return;
			}
		}
	}

	public void setData(RegisterCloudNameResult registerCloudNameResult) {

		if (registerCloudNameResult != null) {

			this.registerCloudNameResult = registerCloudNameResult;

			this.registerColumn.setVisible(true);
			this.signInColumn.setVisible(false);

			this.cloudNameLabel.setText(registerCloudNameResult.getCloudName().toString());
			this.cloudNumberLabel.setText(registerCloudNameResult.getCloudNumber().toString());
			this.endpointUriLabel.setText(registerCloudNameResult.getEndpointUri());
		} else {

			this.registerColumn.setVisible(false);
			this.signInColumn.setVisible(true);
		}
	}

	private void onRegisterCloudActionPerformed(ActionEvent e) {

		RegistrarParty registrarParty = DiscoveryDemoApplication.getApp().getRegistrarParty();
		CloudServiceProviderParty cloudServiceProviderParty = DiscoveryDemoApplication.getApp().getCloudServiceProviderParty();

		// check input

		String cloudName = this.registerCloudNameResult.getCloudName().toString();
		String cloudNumber = this.registerCloudNameResult.getCloudNumber().toString();
		String endpointUri = this.registerCloudNameResult.getEndpointUri();
		String secretToken = this.secretTokenTextField.getText();

		if (cloudNumber == null || cloudNumber.isEmpty() || endpointUri == null || endpointUri.isEmpty()) {

			MessageDialog.warning("Please register a Cloud Name first!");
			return;
		}

		if (secretToken == null || secretToken.isEmpty()) {

			MessageDialog.warning("Please enter a Secret Token first!");
			return;
		}

		cloudName = "=dev." + cloudName;

		// register the cloud

		RegisterCloudResult registerCloudResult;

		try {

			registerCloudResult = cloudServiceProviderParty.registerCloud(registrarParty, XDI3Segment.create(cloudName), XDI3Segment.create(cloudNumber), endpointUri, secretToken);
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, we could not register the Cloud: " + ex.getMessage(), ex);
			return;
		}

		// done

		MessageDialog.info("Cloud has been registered with XDI endpoint " + registerCloudResult.getEndpointUri());

		this.setData(null);
	}

	private void onOpenActionPerformed(ActionEvent e) {

		String cloudName = this.cloudNameTextField.getText();
		String secretToken = this.secretTokenField.getText();
		if (cloudName == null || cloudName.trim().equals("")) return;
		if (secretToken == null || secretToken.trim().equals("")) return;

		// discovery

		AnonymousParty anonymousParty = DiscoveryDemoApplication.getApp().getAnonymousParty();
		GlobalRegistryParty globalRegistryParty = DiscoveryDemoApplication.getApp().getGlobalRegistryParty();

		XDI3Segment xri = XDI3Segment.create(cloudName);
		XDIDiscoveryResult discoveryResult;

		try {

			discoveryResult = globalRegistryParty.discoverFromXri(anonymousParty, xri);
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, we could not discover the Personal Cloud: " + ex.getMessage(), ex);
			return;
		}

		// create new cloud party

		String endpointUri = discoveryResult.getEndpointUri();
		XDI3Segment cloudNumber = discoveryResult.getCloudNumber();

		MyCloudParty cloudParty = MyCloudParty.create(endpointUri, xri, cloudNumber, secretToken);

		DiscoveryDemoApplication.getApp().setMyCloudParty(cloudParty);

		// check the secret token

		try {

			cloudParty.checkSecretToken(cloudParty);
		} catch (Exception ex) {

			DiscoveryDemoApplication.getApp().setMyCloudParty(null);

			MessageDialog.problem("Sorry, the secret token is invalid: " + ex.getMessage(), ex);
			return;
		}

		// done

		this.refresh();
	}

	private void onDataActionPerformed(ActionEvent e) {

		MyCloudParty cloudParty = DiscoveryDemoApplication.getApp().getMyCloudParty();

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
		label2.setText("My Cloud");
		row2.add(label2);
		Column column1 = new Column();
		column1.setCellSpacing(new Extent(20, Extent.PX));
		splitPane1.add(column1);
		registerColumn = new Column();
		registerColumn.setVisible(false);
		column1.add(registerColumn);
		Row row4 = new Row();
		row4.setCellSpacing(new Extent(20, Extent.PX));
		registerColumn.add(row4);
		ImageIcon imageIcon3 = new ImageIcon();
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/danube/discoverydemo/resource/image/cloud_big_register.png");
		imageIcon3.setIcon(imageReference2);
		imageIcon3.setHeight(new Extent(200, Extent.PX));
		imageIcon3.setWidth(new Extent(200, Extent.PX));
		row4.add(imageIcon3);
		Column column2 = new Column();
		column2.setCellSpacing(new Extent(10, Extent.PX));
		row4.add(column2);
		Label label3 = new Label();
		label3.setStyleName("Header");
		label3.setText("My Cloud Registration");
		column2.add(label3);
		Label label7 = new Label();
		label7.setStyleName("Default");
		label7.setText("Welcome. Please complete registration of your Cloud.");
		column2.add(label7);
		Grid grid1 = new Grid();
		grid1.setInsets(new Insets(new Extent(5, Extent.PX)));
		grid1.setColumnWidth(0, new Extent(150, Extent.PX));
		grid1.setSize(2);
		column2.add(grid1);
		Label label1 = new Label();
		label1.setStyleName("Default");
		label1.setText("Cloud Name:");
		grid1.add(label1);
		cloudNameLabel = new Label();
		cloudNameLabel.setStyleName("Default");
		cloudNameLabel.setText("...");
		cloudNameLabel.setFont(new Font(null, Font.BOLD, new Extent(10,
				Extent.PT)));
		grid1.add(cloudNameLabel);
		Label label4 = new Label();
		label4.setStyleName("Default");
		label4.setText("Cloud Number:");
		grid1.add(label4);
		cloudNumberLabel = new Label();
		cloudNumberLabel.setStyleName("Default");
		cloudNumberLabel.setText("...");
		cloudNumberLabel.setFont(new Font(null, Font.BOLD, new Extent(10,
				Extent.PT)));
		grid1.add(cloudNumberLabel);
		Label label6 = new Label();
		label6.setStyleName("Default");
		label6.setText("XDI Endpoint:");
		grid1.add(label6);
		endpointUriLabel = new Label();
		endpointUriLabel.setStyleName("Default");
		endpointUriLabel.setText("...");
		endpointUriLabel.setFont(new Font(null, Font.BOLD, new Extent(10,
				Extent.PT)));
		grid1.add(endpointUriLabel);
		Row row5 = new Row();
		row5.setCellSpacing(new Extent(10, Extent.PX));
		column2.add(row5);
		Label label5 = new Label();
		label5.setStyleName("Default");
		label5.setText("Secret Token:");
		row5.add(label5);
		secretTokenTextField = new TextField();
		secretTokenTextField.setStyleName("Default");
		row5.add(secretTokenTextField);
		Row row7 = new Row();
		row7.setAlignment(new Alignment(Alignment.RIGHT, Alignment.DEFAULT));
		column2.add(row7);
		Button button2 = new Button();
		button2.setStyleName("Default");
		button2.setText("Register My Cloud");
		button2.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onRegisterCloudActionPerformed(e);
			}
		});
		row7.add(button2);
		signInColumn = new Column();
		signInColumn.setCellSpacing(new Extent(20, Extent.PX));
		column1.add(signInColumn);
		Row row3 = new Row();
		row3.setCellSpacing(new Extent(10, Extent.PX));
		signInColumn.add(row3);
		ImageIcon imageIcon4 = new ImageIcon();
		ResourceImageReference imageReference3 = new ResourceImageReference(
				"/danube/discoverydemo/resource/image/cloud_big_login.png");
		imageIcon4.setIcon(imageReference3);
		imageIcon4.setHeight(new Extent(200, Extent.PX));
		imageIcon4.setWidth(new Extent(200, Extent.PX));
		row3.add(imageIcon4);
		Column column3 = new Column();
		column3.setCellSpacing(new Extent(10, Extent.PX));
		row3.add(column3);
		Label label8 = new Label();
		label8.setStyleName("Header");
		label8.setText("My Cloud Sign-In");
		column3.add(label8);
		Label label9 = new Label();
		label9.setStyleName("Default");
		label9.setText("Welcome. Please enter your Cloud Name and Secret Token.");
		column3.add(label9);
		Grid grid2 = new Grid();
		grid2.setWidth(new Extent(100, Extent.PERCENT));
		grid2.setColumnWidth(0, new Extent(150, Extent.PX));
		column3.add(grid2);
		Label label10 = new Label();
		label10.setStyleName("Default");
		label10.setText("Cloud Name:");
		grid2.add(label10);
		cloudNameTextField = new TextField();
		cloudNameTextField.setStyleName("Default");
		cloudNameTextField.setWidth(new Extent(100, Extent.PERCENT));
		cloudNameTextField.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onOpenActionPerformed(e);
			}
		});
		grid2.add(cloudNameTextField);
		Label label11 = new Label();
		label11.setStyleName("Default");
		label11.setText("Secret Token:");
		grid2.add(label11);
		secretTokenField = new PasswordField();
		secretTokenField.setStyleName("Default");
		secretTokenField.setWidth(new Extent(100, Extent.PERCENT));
		grid2.add(secretTokenField);
		Row row6 = new Row();
		row6.setAlignment(new Alignment(Alignment.RIGHT, Alignment.DEFAULT));
		row6.setCellSpacing(new Extent(10, Extent.PX));
		SplitPaneLayoutData row6LayoutData = new SplitPaneLayoutData();
		row6LayoutData.setMinimumSize(new Extent(40, Extent.PX));
		row6LayoutData.setMaximumSize(new Extent(40, Extent.PX));
		row6LayoutData.setOverflow(SplitPaneLayoutData.OVERFLOW_HIDDEN);
		row6.setLayoutData(row6LayoutData);
		column3.add(row6);
		Button button3 = new Button();
		button3.setStyleName("Default");
		button3.setText("Open My Cloud");
		button3.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onOpenActionPerformed(e);
			}
		});
		row6.add(button3);
		xdiEndpointPanel = new XdiEndpointPanel();
		signInColumn.add(xdiEndpointPanel);
		Row row1 = new Row();
		row1.setCellSpacing(new Extent(10, Extent.PX));
		signInColumn.add(row1);
		managePersonalDataButton = new Button();
		managePersonalDataButton.setStyleName("Default");
		managePersonalDataButton.setEnabled(false);
		ResourceImageReference imageReference4 = new ResourceImageReference(
				"/danube/discoverydemo/resource/image/connect-cloud.png");
		managePersonalDataButton.setIcon(imageReference4);
		managePersonalDataButton.setText("Manage Personal Data");
		managePersonalDataButton.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onDataActionPerformed(e);
			}
		});
		row1.add(managePersonalDataButton);
		facebookConnectorPanel = new FacebookConnectorPanel();
		facebookConnectorPanel.setId("facebookConnectorPanel");
		facebookConnectorPanel.setEnabled(false);
		row1.add(facebookConnectorPanel);
		allfiledConnectorPanel = new AllfiledConnectorPanel();
		allfiledConnectorPanel.setEnabled(false);
		row1.add(allfiledConnectorPanel);
		personalConnectorPanel = new PersonalConnectorPanel();
		personalConnectorPanel.setEnabled(false);
		row1.add(personalConnectorPanel);
	}
}

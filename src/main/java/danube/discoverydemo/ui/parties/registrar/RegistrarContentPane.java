package danube.discoverydemo.ui.parties.registrar;

import java.util.ResourceBundle;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Button;
import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Font;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.TextField;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.RowLayoutData;
import nextapp.echo.app.layout.SplitPaneLayoutData;
import xdi2.core.xri3.XDI3Segment;
import danube.discoverydemo.DiscoveryDemoApplication;
import danube.discoverydemo.parties.impl.CloudServiceProviderParty;
import danube.discoverydemo.parties.impl.CloudServiceProviderParty.RegisterCloudNameResult;
import danube.discoverydemo.parties.impl.CloudServiceProviderParty.RegisterCloudResult;
import danube.discoverydemo.parties.impl.RegistrarParty;
import danube.discoverydemo.ui.MessageDialog;
import danube.discoverydemo.ui.xdi.XdiEndpointPanel;
import echopoint.ImageIcon;

public class RegistrarContentPane extends ContentPane {

	private static final long serialVersionUID = 5781883512857770059L;

	protected ResourceBundle resourceBundle;

	private RegistrarParty registrarParty;

	private TextField cloudNameTextField;
	private TextField emailTextField;
	private Label cloudNumberLabel;
	private Label endpointUriLabel;
	private TextField secretTokenTextField;
	private XdiEndpointPanel xdiEndpointPanel;

	public RegistrarContentPane() {
		super();

		// Add design-time configured components.
		initComponents();

		this.registrarParty = DiscoveryDemoApplication.getApp().getRegistrarParty();
	}

	@Override
	public void init() {

		super.init();

		this.xdiEndpointPanel.setData(this.registrarParty.getXdiEndpoint());
	}

	@Override
	public void dispose() {

		super.dispose();
	}

	private void onRegisterCloudNameActionPerformed(ActionEvent e) {

		CloudServiceProviderParty cloudServiceProviderParty = DiscoveryDemoApplication.getApp().getCloudServiceProviderParty();

		// check input

		String cloudName = this.cloudNameTextField.getText();
		String email = this.emailTextField.getText();

		if (cloudName == null || cloudName.isEmpty()) {

			MessageDialog.warning("Please enter a Cloud Name!");
			return;
		}

		if (email == null || email.isEmpty()) {

			MessageDialog.warning("Please enter an E-Mail address!");
			return;
		}

		cloudName = "=dev." + cloudName;

		// register the cloud name

		RegisterCloudNameResult registerCloudNameResult;

		try {

			registerCloudNameResult = cloudServiceProviderParty.registerCloudName(this.registrarParty, XDI3Segment.create(cloudName), email);
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, we could not register the Cloud Name: " + ex.getMessage(), ex);
			return;
		}

		// update UI

		if (registerCloudNameResult != null) {

			this.cloudNumberLabel.setText(registerCloudNameResult.getCloudNumber().toString());
			this.endpointUriLabel.setText(registerCloudNameResult.getEndpointUri());

			MessageDialog.info("Cloud Name " + cloudName + " has been registered with Cloud Number " + registerCloudNameResult.getCloudNumber() + " and E-Mail Address " + email);
		}
	}

	private void onRegisterCloudActionPerformed(ActionEvent e) {

		CloudServiceProviderParty cloudServiceProviderParty = DiscoveryDemoApplication.getApp().getCloudServiceProviderParty();

		// check input

		String cloudName = this.cloudNameTextField.getText();
		String cloudNumber = this.cloudNumberLabel.getText();
		String endpointUri= this.endpointUriLabel.getText();
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

			registerCloudResult = cloudServiceProviderParty.registerCloud(this.registrarParty, XDI3Segment.create(cloudName), XDI3Segment.create(cloudNumber), endpointUri, secretToken);
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, we could not register the Cloud: " + ex.getMessage(), ex);
			return;
		}

		MessageDialog.info("Cloud has been registered with endpoint URI " + registerCloudResult.getEndpointUri());
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
				"/danube/discoverydemo/resource/image/registrar.png");
		imageIcon2.setIcon(imageReference1);
		imageIcon2.setHeight(new Extent(48, Extent.PX));
		imageIcon2.setWidth(new Extent(48, Extent.PX));
		row2.add(imageIcon2);
		Label label2 = new Label();
		label2.setStyleName("Header");
		label2.setText("Registrar");
		row2.add(label2);
		Column column1 = new Column();
		column1.setCellSpacing(new Extent(10, Extent.PX));
		splitPane1.add(column1);
		xdiEndpointPanel = new XdiEndpointPanel();
		column1.add(xdiEndpointPanel);
		Row row1 = new Row();
		row1.setCellSpacing(new Extent(20, Extent.PX));
		column1.add(row1);
		ImageIcon imageIcon4 = new ImageIcon();
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/danube/discoverydemo/resource/image/cloud_big_name.png");
		imageIcon4.setIcon(imageReference2);
		imageIcon4.setHeight(new Extent(200, Extent.PX));
		imageIcon4.setWidth(new Extent(200, Extent.PX));
		row1.add(imageIcon4);
		Column column3 = new Column();
		column3.setCellSpacing(new Extent(10, Extent.PX));
		RowLayoutData column3LayoutData = new RowLayoutData();
		column3LayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		column3.setLayoutData(column3LayoutData);
		row1.add(column3);
		Row row6 = new Row();
		row6.setCellSpacing(new Extent(10, Extent.PX));
		column3.add(row6);
		Label label1 = new Label();
		label1.setStyleName("Default");
		label1.setText("Cloud Name:");
		row6.add(label1);
		Label label3 = new Label();
		label3.setStyleName("Default");
		label3.setText("=dev.");
		label3.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		row6.add(label3);
		cloudNameTextField = new TextField();
		cloudNameTextField.setStyleName("Default");
		row6.add(cloudNameTextField);
		Row row8 = new Row();
		row8.setCellSpacing(new Extent(10, Extent.PX));
		column3.add(row8);
		Label label7 = new Label();
		label7.setStyleName("Default");
		label7.setText("E-Mail:");
		row8.add(label7);
		emailTextField = new TextField();
		emailTextField.setStyleName("Default");
		emailTextField.setText("test@test.com");
		row8.add(emailTextField);
		Button button1 = new Button();
		button1.setStyleName("Default");
		button1.setText("Register Cloud Name");
		button1.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onRegisterCloudNameActionPerformed(e);
			}
		});
		column3.add(button1);
		Row row3 = new Row();
		row3.setCellSpacing(new Extent(10, Extent.PX));
		column3.add(row3);
		Label label4 = new Label();
		label4.setStyleName("Default");
		label4.setText("Cloud Number:");
		row3.add(label4);
		cloudNumberLabel = new Label();
		cloudNumberLabel.setStyleName("Default");
		cloudNumberLabel.setText("...");
		cloudNumberLabel.setFont(new Font(null, Font.BOLD, new Extent(10,
				Extent.PT)));
		row3.add(cloudNumberLabel);
		Row row4 = new Row();
		row4.setCellSpacing(new Extent(20, Extent.PX));
		column1.add(row4);
		ImageIcon imageIcon3 = new ImageIcon();
		ResourceImageReference imageReference3 = new ResourceImageReference(
				"/danube/discoverydemo/resource/image/cloud_big_register.png");
		imageIcon3.setIcon(imageReference3);
		imageIcon3.setHeight(new Extent(200, Extent.PX));
		imageIcon3.setWidth(new Extent(200, Extent.PX));
		row4.add(imageIcon3);
		Column column2 = new Column();
		column2.setCellSpacing(new Extent(10, Extent.PX));
		row4.add(column2);
		Row row7 = new Row();
		row7.setCellSpacing(new Extent(10, Extent.PX));
		column2.add(row7);
		Label label6 = new Label();
		label6.setStyleName("Default");
		label6.setText("Endpoint URI:");
		row7.add(label6);
		endpointUriLabel = new Label();
		endpointUriLabel.setStyleName("Default");
		endpointUriLabel.setText("...");
		endpointUriLabel.setFont(new Font(null, Font.BOLD, new Extent(10,
				Extent.PT)));
		row7.add(endpointUriLabel);
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
		Button button2 = new Button();
		button2.setStyleName("Default");
		button2.setText("Register Cloud");
		button2.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onRegisterCloudActionPerformed(e);
			}
		});
		column2.add(button2);
	}
}

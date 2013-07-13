package danube.clouds.desktop.ui.parties.registrar;

import java.util.ResourceBundle;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import nextapp.echo.app.Alignment;
import nextapp.echo.app.Button;
import nextapp.echo.app.CheckBox;
import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Font;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.PasswordField;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.TextField;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.ColumnLayoutData;
import nextapp.echo.app.layout.RowLayoutData;
import nextapp.echo.app.layout.SplitPaneLayoutData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xdi2.core.xri3.XDI3Segment;
import danube.clouds.desktop.DanubeCloudsDesktopApplication;
import danube.clouds.desktop.parties.impl.CloudServiceProviderParty;
import danube.clouds.desktop.parties.impl.GlobalRegistryParty;
import danube.clouds.desktop.parties.impl.RegistrarParty;
import danube.clouds.desktop.parties.impl.GlobalRegistryParty.RegisterCloudNameResult;
import danube.clouds.desktop.ui.MessageDialog;
import danube.clouds.desktop.ui.xdi.XdiEndpointPanel;
import echopoint.ImageIcon;

public class RegistrarContentPane extends ContentPane {

	private static final long serialVersionUID = 5781883512857770059L;

	private static final Logger log = LoggerFactory.getLogger(RegistrarContentPane.class);

	protected ResourceBundle resourceBundle;

	private RegistrarParty registrarParty;

	private Row registrarSecretTokenRow;
	private TextField cloudNameTextField;
	private TextField emailTextField;
	private Label cloudNumberLabel;
	private Column mainColumn;
	private PasswordField registrarSecretTokenTextField;
	private CheckBox agreeCheckBox;
	private Column fixColumn;
	private TextField fixCloudNameTextField;
	private XdiEndpointPanel xdiEndpointPanel;
	private TextField fixCloudNumberTextField;
	private TextField fixEndpointUriTextField;

	public RegistrarContentPane() {
		super();

		// Add design-time configured components.
		initComponents();

		this.registrarParty = DanubeCloudsDesktopApplication.getApp().getRegistrarParty();
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

		GlobalRegistryParty globalRegistryParty = DanubeCloudsDesktopApplication.getApp().getGlobalRegistryParty();
		CloudServiceProviderParty cloudServiceProviderParty = DanubeCloudsDesktopApplication.getApp().getCloudServiceProviderParty();

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

		if (! this.agreeCheckBox.isSelected()) {

			MessageDialog.warning("Agreement to the Respect Trust Framework and its One-Person-One-Account rule is requirement.");
			return;
		}

		// register the cloud name

		RegisterCloudNameResult registerCloudNameResult;

		try {

			registerCloudNameResult = globalRegistryParty.registerCloudName(this.registrarParty, cloudServiceProviderParty, XDI3Segment.create(cloudName), email);
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, we could not register the Cloud Name: " + ex.getMessage(), ex);
			return;
		}

		// cache the registration

		Cache cloudCache = DanubeCloudsDesktopApplication.getApp().getServlet().getCloudCache();
		cloudCache.put(new Element(registerCloudNameResult.getCloudNumber().toString(), registerCloudNameResult));
		log.info("CACHE PUT: " + registerCloudNameResult.getCloudNumber().toString());

		// send e-mail

		try {

			this.registrarParty.sendEmail(registerCloudNameResult, email);
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, we could not send the confirmation e-mail: " + ex.getMessage(), ex);
			return;
		}

		// update UI

		if (registerCloudNameResult != null) {

			this.cloudNumberLabel.setText(registerCloudNameResult.getCloudNumber().toString());

			MessageDialog.info("Cloud Name " + cloudName + " has been registered with Cloud Number " + registerCloudNameResult.getCloudNumber() + " and E-Mail Address " + email);
		}
	}

	private void onFixCloudNameActionPerformed(ActionEvent e) {

		// check input

		String fixCloudName = this.fixCloudNameTextField.getText();
		String fixCloudNumber = this.fixCloudNumberTextField.getText();
		String fixEndpointUri = this.fixEndpointUriTextField.getText();

		if (fixCloudName == null || fixCloudName.isEmpty()) {

			MessageDialog.warning("Please enter a Cloud Name!");
			return;
		}

		if (fixCloudNumber == null || fixCloudNumber.isEmpty()) {

			MessageDialog.warning("Please enter a Cloud Number!");
			return;
		}

		if (fixEndpointUri == null || fixEndpointUri.isEmpty()) {

			MessageDialog.warning("Please enter an Endpoint URI!");
			return;
		}

		// fix the registration

		RegisterCloudNameResult registerCloudNameResult;

		registerCloudNameResult = new RegisterCloudNameResult(XDI3Segment.create(fixCloudName), XDI3Segment.create(fixCloudNumber), fixEndpointUri);

		// cache the registration

		Cache cloudCache = DanubeCloudsDesktopApplication.getApp().getServlet().getCloudCache();
		cloudCache.put(new Element(registerCloudNameResult.getCloudNumber().toString(), registerCloudNameResult));
		log.info("CACHE PUT: " + registerCloudNameResult.getCloudNumber().toString());

		// update UI

		this.cloudNumberLabel.setText(registerCloudNameResult.getCloudNumber().toString());

		MessageDialog.info("Cloud Name " + fixCloudName + " has been fixed with Cloud Number " + registerCloudNameResult.getCloudNumber());
	}

	private void onRegistrarSecretTokenActionPerformed(ActionEvent e) {

		if ("danube".equals(this.registrarSecretTokenTextField.getText())) {

			this.registrarSecretTokenRow.setVisible(false);
			this.mainColumn.setVisible(true);
		}

		if ("danubefix".equals(this.registrarSecretTokenTextField.getText())) {

			this.registrarSecretTokenRow.setVisible(false);
			this.fixColumn.setVisible(true);
		}
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
				"/danube/clouds/desktop/resource/image/registrar.png");
		imageIcon2.setIcon(imageReference1);
		imageIcon2.setHeight(new Extent(48, Extent.PX));
		imageIcon2.setWidth(new Extent(48, Extent.PX));
		row2.add(imageIcon2);
		Label label2 = new Label();
		label2.setStyleName("Header");
		label2.setText("Registrar");
		row2.add(label2);
		SplitPane splitPane2 = new SplitPane();
		splitPane2.setStyleName("Default");
		splitPane2.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPane2.setResizable(false);
		splitPane2.setSeparatorVisible(false);
		splitPane1.add(splitPane2);
		Column column2 = new Column();
		column2.setCellSpacing(new Extent(10, Extent.PX));
		splitPane2.add(column2);
		xdiEndpointPanel = new XdiEndpointPanel();
		column2.add(xdiEndpointPanel);
		mainColumn = new Column();
		mainColumn.setVisible(false);
		mainColumn.setCellSpacing(new Extent(20, Extent.PX));
		column2.add(mainColumn);
		Row row1 = new Row();
		row1.setCellSpacing(new Extent(20, Extent.PX));
		mainColumn.add(row1);
		ImageIcon imageIcon4 = new ImageIcon();
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/danube/clouds/desktop/resource/image/cloud_big_name.png");
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
		cloudNameTextField = new TextField();
		cloudNameTextField.setStyleName("Default");
		cloudNameTextField.setText("=yourname");
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
		emailTextField.setText("your@email.com");
		emailTextField.setWidth(new Extent(300, Extent.PX));
		row8.add(emailTextField);
		agreeCheckBox = new CheckBox();
		agreeCheckBox
				.setText("I agree to comply with the Respect Trust Framework and its One-Person-One-Account rule.");
		column3.add(agreeCheckBox);
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
		fixColumn = new Column();
		fixColumn.setVisible(false);
		fixColumn.setCellSpacing(new Extent(20, Extent.PX));
		column2.add(fixColumn);
		Row row5 = new Row();
		row5.setCellSpacing(new Extent(20, Extent.PX));
		fixColumn.add(row5);
		ImageIcon imageIcon6 = new ImageIcon();
		imageIcon6.setIcon(imageReference2);
		imageIcon6.setHeight(new Extent(200, Extent.PX));
		imageIcon6.setWidth(new Extent(200, Extent.PX));
		row5.add(imageIcon6);
		Column column4 = new Column();
		column4.setCellSpacing(new Extent(10, Extent.PX));
		RowLayoutData column4LayoutData = new RowLayoutData();
		column4LayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		column4.setLayoutData(column4LayoutData);
		row5.add(column4);
		Row row7 = new Row();
		row7.setCellSpacing(new Extent(10, Extent.PX));
		column4.add(row7);
		Label label3 = new Label();
		label3.setStyleName("Default");
		label3.setText("Fix Cloud Name:");
		row7.add(label3);
		fixCloudNameTextField = new TextField();
		fixCloudNameTextField.setStyleName("Default");
		row7.add(fixCloudNameTextField);
		Row row9 = new Row();
		row9.setCellSpacing(new Extent(10, Extent.PX));
		column4.add(row9);
		Label label6 = new Label();
		label6.setStyleName("Default");
		label6.setText("Fix Cloud Number:");
		row9.add(label6);
		fixCloudNumberTextField = new TextField();
		fixCloudNumberTextField.setStyleName("Default");
		row9.add(fixCloudNumberTextField);
		Row row10 = new Row();
		row10.setCellSpacing(new Extent(10, Extent.PX));
		column4.add(row10);
		Label label8 = new Label();
		label8.setStyleName("Default");
		label8.setText("Fix Endpoint URI:");
		row10.add(label8);
		fixEndpointUriTextField = new TextField();
		fixEndpointUriTextField.setStyleName("Default");
		row10.add(fixEndpointUriTextField);
		Button button2 = new Button();
		button2.setStyleName("Default");
		button2.setText("Fix Cloud Name");
		button2.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onFixCloudNameActionPerformed(e);
			}
		});
		column4.add(button2);
		Row row4 = new Row();
		row4.setAlignment(new Alignment(Alignment.RIGHT, Alignment.DEFAULT));
		SplitPaneLayoutData row4LayoutData = new SplitPaneLayoutData();
		row4LayoutData.setMinimumSize(new Extent(100, Extent.PX));
		row4LayoutData.setMaximumSize(new Extent(100, Extent.PX));
		row4.setLayoutData(row4LayoutData);
		splitPane2.add(row4);
		registrarSecretTokenRow = new Row();
		registrarSecretTokenRow.setAlignment(new Alignment(Alignment.LEFT,
				Alignment.DEFAULT));
		registrarSecretTokenRow.setCellSpacing(new Extent(10, Extent.PX));
		RowLayoutData registrarSecretTokenRowLayoutData = new RowLayoutData();
		registrarSecretTokenRowLayoutData.setWidth(new Extent(100,
				Extent.PERCENT));
		registrarSecretTokenRow
				.setLayoutData(registrarSecretTokenRowLayoutData);
		row4.add(registrarSecretTokenRow);
		Label label5 = new Label();
		label5.setStyleName("Default");
		label5.setText("Secret Token:");
		registrarSecretTokenRow.add(label5);
		registrarSecretTokenTextField = new PasswordField();
		registrarSecretTokenTextField.setStyleName("Default");
		registrarSecretTokenTextField.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onRegistrarSecretTokenActionPerformed(e);
			}
		});
		registrarSecretTokenRow.add(registrarSecretTokenTextField);
		ImageIcon imageIcon5 = new ImageIcon();
		imageIcon5.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		ResourceImageReference imageReference3 = new ResourceImageReference(
				"/danube/clouds/desktop/resource/image/logo-respectnetwork.png");
		imageIcon5.setIcon(imageReference3);
		imageIcon5.setHeight(new Extent(96, Extent.PX));
		imageIcon5.setWidth(new Extent(275, Extent.PX));
		ColumnLayoutData imageIcon5LayoutData = new ColumnLayoutData();
		imageIcon5LayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		imageIcon5.setLayoutData(imageIcon5LayoutData);
		row4.add(imageIcon5);
	}
}

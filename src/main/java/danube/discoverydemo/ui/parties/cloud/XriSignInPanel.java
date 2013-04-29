package danube.discoverydemo.ui.parties.cloud;


import java.util.ResourceBundle;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Button;
import nextapp.echo.app.Column;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Grid;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.PasswordField;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.TextField;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.SplitPaneLayoutData;
import xdi2.core.xri3.XDI3Segment;
import xdi2.discovery.XDIDiscoveryResult;
import danube.discoverydemo.DiscoveryDemoApplication;
import danube.discoverydemo.parties.impl.ClientParty;
import danube.discoverydemo.parties.impl.CloudParty;
import danube.discoverydemo.parties.impl.GlobalRegistryParty;
import danube.discoverydemo.ui.MainWindow;
import danube.discoverydemo.ui.MessageDialog;
import echopoint.ImageIcon;

public class XriSignInPanel extends Panel {

	private static final long serialVersionUID = 46284183174314347L;

	protected ResourceBundle resourceBundle;

	private TextField cloudNameTextField;
	private PasswordField secretTokenField;

	public XriSignInPanel() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	@Override
	public void init() {

		super.init();
	}

	private void onOpenActionPerformed(ActionEvent e) {

		String cloudName = this.cloudNameTextField.getText();
		String secretToken = this.secretTokenField.getText();
		if (cloudName == null || cloudName.trim().equals("")) return;
		if (secretToken == null || secretToken.trim().equals("")) return;

		// discovery

		ClientParty clientParty = DiscoveryDemoApplication.getApp().getClientParty();
		GlobalRegistryParty globalRegistryParty = DiscoveryDemoApplication.getApp().getGlobalRegistryParty();

		XDI3Segment xri = XDI3Segment.create(cloudName);
		XDIDiscoveryResult discoveryResult;

		try {

			discoveryResult = globalRegistryParty.discoverFromXri(clientParty, xri);
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, we could not discover the Personal Cloud: " + ex.getMessage(), ex);
			return;
		}

		// create new cloud party

		String endpointUri = discoveryResult.getEndpointUri();
		XDI3Segment cloudNumber = discoveryResult.getCloudNumber();

		CloudParty cloudParty = CloudParty.create(endpointUri, xri, cloudNumber, secretToken);

		// check the secret token

		try {

			cloudParty.checkSecretToken(cloudParty);
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, the secret token is invalid: " + ex.getMessage(), ex);
			return;
		}

		DiscoveryDemoApplication.getApp().setCloudParty(cloudParty);

		// done

		CloudContentPane cloudContentPane = (CloudContentPane) MainWindow.findParentComponentByClass(this, CloudContentPane.class);
		cloudContentPane.refresh();
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setInsets(new Insets(new Extent(10, Extent.PX)));
		Row row1 = new Row();
		row1.setCellSpacing(new Extent(10, Extent.PX));
		add(row1);
		ImageIcon imageIcon4 = new ImageIcon();
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/danube/discoverydemo/resource/image/cloud_big_login.png");
		imageIcon4.setIcon(imageReference1);
		imageIcon4.setHeight(new Extent(200, Extent.PX));
		imageIcon4.setWidth(new Extent(200, Extent.PX));
		row1.add(imageIcon4);
		Column column2 = new Column();
		column2.setCellSpacing(new Extent(10, Extent.PX));
		row1.add(column2);
		Label label2 = new Label();
		label2.setStyleName("Header");
		label2.setText("Cloud Name Sign-In");
		column2.add(label2);
		Label label4 = new Label();
		label4.setStyleName("Default");
		label4.setText("Welcome. Please enter your Cloud Name and Secret Token.");
		column2.add(label4);
		Grid grid2 = new Grid();
		grid2.setWidth(new Extent(100, Extent.PERCENT));
		grid2.setColumnWidth(0, new Extent(150, Extent.PX));
		column2.add(grid2);
		Label label1 = new Label();
		label1.setStyleName("Default");
		label1.setText("Cloud Name:");
		grid2.add(label1);
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
		Label label3 = new Label();
		label3.setStyleName("Default");
		label3.setText("Secret Token:");
		grid2.add(label3);
		secretTokenField = new PasswordField();
		secretTokenField.setStyleName("Default");
		secretTokenField.setWidth(new Extent(100, Extent.PERCENT));
		grid2.add(secretTokenField);
		Row row2 = new Row();
		row2.setAlignment(new Alignment(Alignment.RIGHT, Alignment.DEFAULT));
		row2.setCellSpacing(new Extent(10, Extent.PX));
		SplitPaneLayoutData row2LayoutData = new SplitPaneLayoutData();
		row2LayoutData.setMinimumSize(new Extent(40, Extent.PX));
		row2LayoutData.setMaximumSize(new Extent(40, Extent.PX));
		row2LayoutData.setOverflow(SplitPaneLayoutData.OVERFLOW_HIDDEN);
		row2.setLayoutData(row2LayoutData);
		column2.add(row2);
		Button button2 = new Button();
		button2.setStyleName("Default");
		button2.setText("Open Personal Cloud");
		button2.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onOpenActionPerformed(e);
			}
		});
		row2.add(button2);
	}
}

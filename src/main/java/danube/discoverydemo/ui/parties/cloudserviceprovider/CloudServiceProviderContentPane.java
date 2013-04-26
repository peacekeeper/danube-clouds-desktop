package danube.discoverydemo.ui.parties.cloudserviceprovider;

import java.util.ResourceBundle;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Button;
import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Font;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.RadioButton;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.TextField;
import nextapp.echo.app.button.ButtonGroup;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.RowLayoutData;
import nextapp.echo.app.layout.SplitPaneLayoutData;
import xdi2.client.exceptions.Xdi2ClientException;
import xdi2.core.xri3.XDI3Segment;
import xdi2.messaging.MessageEnvelope;
import danube.discoverydemo.DiscoveryDemoApplication;
import danube.discoverydemo.parties.CloudServiceProviderParty;
import danube.discoverydemo.parties.GlobalRegistryParty;
import danube.discoverydemo.ui.MessageDialog;
import danube.discoverydemo.xdi.Xdi;
import danube.discoverydemo.xdi.message.PeerRootAddressRegistrationMessageEnvelopeFactory;
import echopoint.ImageIcon;

public class CloudServiceProviderContentPane extends ContentPane {

	private static final long serialVersionUID = 5781883512857770059L;

	private static final String DEFAULT_ENDPOINTURI = "http://example.com/pcloud";
	private static final String DEFAULT_LINKCONTRACTXRI = "=+registrar$($do)$(!1)";
	
	protected ResourceBundle resourceBundle;

	private CloudServiceProviderParty cloudServiceProviderParty;

	private TextField endpointUriTextField;
	private TextField linkContractXriTextField;
	private Label uuidLabel;
	private Label globalCloudNumberLabel;
	private RadioButton personalRadioButton;
	private RadioButton organizationalRadioButton;
	private Label cloudPeerRootAddressLabel;

	/**
	 * Creates a new <code>CloudServiceProviderContentPane</code>.
	 */
	public CloudServiceProviderContentPane() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	@Override
	public void init() {

		super.init();
		
		this.endpointUriTextField.setText(DEFAULT_ENDPOINTURI);
		this.linkContractXriTextField.setText(DEFAULT_LINKCONTRACTXRI);
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

	private void onCreateGlobalCloudNumberActionPerformed(ActionEvent e) {

		if (this.uuidLabel.getText() == null) {

			MessageDialog.warning("Please generate a UUID first.");
			return;
		}

		String globalCloudNumber;

		if (this.personalRadioButton.isSelected()) {

			globalCloudNumber = "=!(" + this.uuidLabel.getText() + ")";
		} else {

			globalCloudNumber = "=@(" + this.uuidLabel.getText() + ")";
		}

		this.globalCloudNumberLabel.setText(globalCloudNumber);
	}

	private void onTransformToPeerRootAddressActionPerformed(ActionEvent e) {

		if (this.globalCloudNumberLabel.getText() == null) {

			MessageDialog.warning("Please create a Global Cloud Number first.");
			return;
		}

		String peerRootAddress = "(" + this.globalCloudNumberLabel.getText() + ")";

		this.cloudPeerRootAddressLabel.setText(peerRootAddress);
	}

	private void onRegisterActionPerformed(ActionEvent e) {

		if (this.endpointUriTextField.getText() == null) {

			MessageDialog.warning("Please enter an endpoint URI for the Personal Cloud to be registered.");
			return;
		}

		if (this.cloudPeerRootAddressLabel.getText() == null) {

			MessageDialog.warning("Please create a Peer Root for the Personal Cloud to be registered.");
			return;
		}

		String endpointUri = this.endpointUriTextField.getText();
		XDI3Segment linkContractXri = XDI3Segment.create(this.linkContractXriTextField.getText());
		XDI3Segment cloudPeerRootAddress = XDI3Segment.create(this.cloudPeerRootAddressLabel.getText());

		CloudServiceProviderParty cloudServiceProviderParty = this.cloudServiceProviderParty; 
		GlobalRegistryParty globalRegistryParty = DiscoveryDemoApplication.getApp().getGlobalRegistryParty();

		PeerRootAddressRegistrationMessageEnvelopeFactory peerRootAddressRegistrationMessageEnvelopeFactory = new PeerRootAddressRegistrationMessageEnvelopeFactory();
		peerRootAddressRegistrationMessageEnvelopeFactory.setRegistrar(cloudServiceProviderParty.getCanonical());
		peerRootAddressRegistrationMessageEnvelopeFactory.setRegistry(globalRegistryParty.getCanonical());
		peerRootAddressRegistrationMessageEnvelopeFactory.setCloudPeerRootAddress(cloudPeerRootAddress);
		peerRootAddressRegistrationMessageEnvelopeFactory.setEndpointUri(endpointUri);
		peerRootAddressRegistrationMessageEnvelopeFactory.setLinkContractXri(linkContractXri);

		MessageEnvelope messageEnvelope = peerRootAddressRegistrationMessageEnvelopeFactory.create();

		Xdi xdi = DiscoveryDemoApplication.getApp().getXdi();

		try {

			xdi.send(globalRegistryParty.getXdiEndpoint(), messageEnvelope);
		} catch (Xdi2ClientException ex) {

			MessageDialog.problem(ex.getMessage(), ex);
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
				"/danube/discoverydemo/resource/image/cloudserviceprovider.png");
		imageIcon2.setIcon(imageReference1);
		imageIcon2.setHeight(new Extent(48, Extent.PX));
		imageIcon2.setWidth(new Extent(48, Extent.PX));
		row2.add(imageIcon2);
		Label label2 = new Label();
		label2.setStyleName("Header");
		label2.setText("Cloud Service Provider");
		row2.add(label2);
		Column column1 = new Column();
		column1.setCellSpacing(new Extent(10, Extent.PX));
		splitPane1.add(column1);
		Row row3 = new Row();
		column1.add(row3);
		Label label3 = new Label();
		label3.setStyleName("Default");
		label3.setText("Endpoint URI:");
		row3.add(label3);
		endpointUriTextField = new TextField();
		endpointUriTextField.setStyleName("Default");
		endpointUriTextField.setWidth(new Extent(350, Extent.PX));
		endpointUriTextField.setInsets(new Insets(new Extent(10, Extent.PX),
				new Extent(5, Extent.PX)));
		RowLayoutData endpointUriTextFieldLayoutData = new RowLayoutData();
		endpointUriTextFieldLayoutData.setInsets(new Insets(new Extent(10,
				Extent.PX), new Extent(0, Extent.PX), new Extent(0, Extent.PX),
				new Extent(0, Extent.PX)));
		endpointUriTextField.setLayoutData(endpointUriTextFieldLayoutData);
		row3.add(endpointUriTextField);
		Row row5 = new Row();
		row5.setCellSpacing(new Extent(10, Extent.PX));
		column1.add(row5);
		Label label6 = new Label();
		label6.setStyleName("Default");
		label6.setText("Link Contract:");
		row5.add(label6);
		linkContractXriTextField = new TextField();
		linkContractXriTextField.setStyleName("Default");
		row5.add(linkContractXriTextField);
		Column column2 = new Column();
		column2.setCellSpacing(new Extent(10, Extent.PX));
		column1.add(column2);
		Row row7 = new Row();
		column2.add(row7);
		Button uuidButton = new Button();
		uuidButton.setStyleName("Default");
		uuidButton.setText("Generate UUID");
		uuidButton.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onUuidButtonActionPerformed(e);
			}
		});
		row7.add(uuidButton);
		Row row1 = new Row();
		row1.setAlignment(new Alignment(Alignment.LEFT, Alignment.DEFAULT));
		row1.setCellSpacing(new Extent(10, Extent.PX));
		RowLayoutData row1LayoutData = new RowLayoutData();
		row1LayoutData.setWidth(new Extent(50, Extent.PERCENT));
		row1.setLayoutData(row1LayoutData);
		column2.add(row1);
		Label label1 = new Label();
		label1.setStyleName("Default");
		label1.setText("UUID:");
		row1.add(label1);
		uuidLabel = new Label();
		uuidLabel.setStyleName("Default");
		uuidLabel.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		row1.add(uuidLabel);
		Row row6 = new Row();
		row6.setCellSpacing(new Extent(10, Extent.PX));
		column2.add(row6);
		personalRadioButton = new RadioButton();
		personalRadioButton.setSelected(true);
		personalRadioButton.setText("personal");
		ButtonGroup globalCloudNumberGroup = new ButtonGroup();
		personalRadioButton.setGroup(globalCloudNumberGroup);
		row6.add(personalRadioButton);
		organizationalRadioButton = new RadioButton();
		organizationalRadioButton.setText("organizational");
		organizationalRadioButton.setGroup(globalCloudNumberGroup);
		row6.add(organizationalRadioButton);
		Button button1 = new Button();
		button1.setStyleName("Default");
		button1.setText("Create Global Cloud Number");
		button1.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onCreateGlobalCloudNumberActionPerformed(e);
			}
		});
		row6.add(button1);
		Row row4 = new Row();
		column2.add(row4);
		Label label4 = new Label();
		label4.setStyleName("Default");
		label4.setText("Global Cloud Number:");
		row4.add(label4);
		globalCloudNumberLabel = new Label();
		globalCloudNumberLabel.setStyleName("Default");
		globalCloudNumberLabel.setFont(new Font(null, Font.BOLD, new Extent(10,
				Extent.PT)));
		RowLayoutData globalCloudNumberLabelLayoutData = new RowLayoutData();
		globalCloudNumberLabelLayoutData.setInsets(new Insets(new Extent(10,
				Extent.PX), new Extent(0, Extent.PX), new Extent(0, Extent.PX),
				new Extent(0, Extent.PX)));
		globalCloudNumberLabel.setLayoutData(globalCloudNumberLabelLayoutData);
		row4.add(globalCloudNumberLabel);
		Row row8 = new Row();
		column2.add(row8);
		Button button2 = new Button();
		button2.setStyleName("Default");
		button2.setText("Transform to Peer Root Address");
		button2.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onTransformToPeerRootAddressActionPerformed(e);
			}
		});
		row8.add(button2);
		Row row9 = new Row();
		row9.setCellSpacing(new Extent(10, Extent.PX));
		column2.add(row9);
		Label label5 = new Label();
		label5.setStyleName("Default");
		label5.setText("Cloud Peer Root Address:");
		row9.add(label5);
		cloudPeerRootAddressLabel = new Label();
		cloudPeerRootAddressLabel.setStyleName("Default");
		cloudPeerRootAddressLabel.setFont(new Font(null, Font.BOLD, new Extent(
				10, Extent.PT)));
		row9.add(cloudPeerRootAddressLabel);
		Row row10 = new Row();
		column2.add(row10);
		Button button3 = new Button();
		button3.setStyleName("Default");
		button3.setText("Register in Global Registry");
		button3.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onRegisterActionPerformed(e);
			}
		});
		row10.add(button3);
	}
}

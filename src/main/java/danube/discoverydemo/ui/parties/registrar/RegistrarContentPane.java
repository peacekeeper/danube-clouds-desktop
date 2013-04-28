package danube.discoverydemo.ui.parties.registrar;

import ibrokerkit.iname4java.store.Xri;
import ibrokerkit.iname4java.store.XriStore;
import ibrokerkit.iname4java.store.impl.grs.GrsXriData;

import java.net.URLEncoder;
import java.util.ResourceBundle;

import nextapp.echo.app.Button;
import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.TextField;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.SplitPaneLayoutData;

import org.openxri.xml.Service;

import xdi2.core.features.roots.XdiPeerRoot;
import xdi2.core.util.XRI2Util;
import xdi2.core.xri3.XDI3Segment;
import xdi2.core.xri3.XDI3SubSegment;
import danube.discoverydemo.DiscoveryDemoApplication;
import danube.discoverydemo.parties.RegistrarParty;
import danube.discoverydemo.ui.MessageDialog;
import danube.discoverydemo.ui.xdi.XdiContentPane;
import danube.discoverydemo.xdi.XdiEndpoint;
import danube.discoverydemo.xdi.message.CloudRegistrationMessageEnvelopeFactory;
import echopoint.ImageIcon;

public class RegistrarContentPane extends ContentPane {

	private static final long serialVersionUID = 5781883512857770059L;

	protected ResourceBundle resourceBundle;

	private TextField cloudNameTextField;

	private Label cloudNumberLabel;

	private TextField cloudSecretTokenTextField;

	/**
	 * Creates a new <code>AppContentPane</code>.
	 */
	public RegistrarContentPane() {
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

	private void onRegisterCloudNameActionPerformed(ActionEvent e) {

		String cloudName = this.cloudNameTextField.getText();

		if (cloudName == null || cloudName.isEmpty()) {

			MessageDialog.warning("Please enter a Cloud Name first!");
			return;
		}

		cloudName = "=dev." + cloudName;

		XriStore xriStore = DiscoveryDemoApplication.getApp().getServlet().getXriStore();

		try {

			GrsXriData xriData = new GrsXriData();

			xriData.setUserIdentifier(cloudName);
			xriData.setName("Respect Network");
			xriData.setOrganization("Respect Network");
			xriData.setStreet(new String[] { "Street 1" });
			xriData.setPostalCode("11111");
			xriData.setCity("City");
			xriData.setCountryCode("US");
			xriData.setPrimaryVoice("+1.0000000");
			xriData.setPrimaryEmail("dummy@dummy.com");

			Xri xri = xriStore.registerXri(null, cloudName, xriData, 2);

			String uri = "http://mycloud.neustar.biz:14440/users/" + URLEncoder.encode(XRI2Util.canonicalIdToCloudNumber(xri.getCanonicalID().getValue()).toString(), "UTF-8");
			Service service = new Service();
			service.addURI(uri);
			service.addType("$xdi");

			xri.addService(service);

			this.cloudNumberLabel.setText(XRI2Util.canonicalIdToCloudNumber(xri.getCanonicalID().getValue()).toString());
		} catch (Exception ex) {

			MessageDialog.problem(ex.getMessage(), ex);
		}
	}

	private void onRegisterCloudActionPerformed(ActionEvent e) {

		String cloudname = this.cloudNameTextField.getText();
		String cloudnumber = this.cloudNumberLabel.getText();
		String cloudSecretToken = this.cloudSecretTokenTextField.getText();

		if (cloudnumber == null || cloudnumber.isEmpty()) {

			MessageDialog.warning("Please register a Cloud Name first!");
			return;
		}

		if (cloudSecretToken == null || cloudSecretToken.isEmpty()) {

			MessageDialog.warning("Please enter a Secret Token first!");
			return;
		}

		cloudname = "=dev." + cloudname;

		XDI3SubSegment cloudnamePeerRoot = XdiPeerRoot.createPeerRootArcXri(XDI3Segment.create(cloudname));
		XDI3SubSegment cloudnumberPeerRoot = XdiPeerRoot.createPeerRootArcXri(XDI3Segment.create(cloudnumber));

		CloudRegistrationMessageEnvelopeFactory cloudRegistrationMessageEnvelopeFactory = new CloudRegistrationMessageEnvelopeFactory();

		cloudRegistrationMessageEnvelopeFactory.setRegistrar(XDI3Segment.create("[@]!299089fd-9d81-3c59-2990-89fd9d813c59"));
		cloudRegistrationMessageEnvelopeFactory.setRegistry(XDI3Segment.create("[=]"));
		cloudRegistrationMessageEnvelopeFactory.setLinkContractXri(XDI3Segment.create("$do"));
		cloudRegistrationMessageEnvelopeFactory.setLinkContractSecretToken("s3cret");
		cloudRegistrationMessageEnvelopeFactory.setCloudnamePeerRoot(cloudnamePeerRoot);
		cloudRegistrationMessageEnvelopeFactory.setCloudnumberPeerRoot(cloudnumberPeerRoot);
		cloudRegistrationMessageEnvelopeFactory.setCloudSecretToken(cloudSecretToken);

		// send it

		XdiEndpoint xdiEndpoint = DiscoveryDemoApplication.getApp().getGlobalRegistryParty().getXdiEndpoint();

		try {

			xdiEndpoint.send(cloudRegistrationMessageEnvelopeFactory.create());
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, a problem occurred: " + ex.getMessage(), ex);
			return;
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
		Row row1 = new Row();
		row1.setCellSpacing(new Extent(10, Extent.PX));
		column1.add(row1);
		Label label1 = new Label();
		label1.setStyleName("Default");
		label1.setText("Cloud Name:");
		row1.add(label1);
		Label label3 = new Label();
		label3.setStyleName("Default");
		label3.setText("=dev.");
		row1.add(label3);
		cloudNameTextField = new TextField();
		cloudNameTextField.setStyleName("Default");
		row1.add(cloudNameTextField);
		Button button1 = new Button();
		button1.setStyleName("Default");
		button1.setText("Register Cloud Name");
		button1.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onRegisterCloudNameActionPerformed(e);
			}
		});
		row1.add(button1);
		Row row3 = new Row();
		row3.setCellSpacing(new Extent(10, Extent.PX));
		column1.add(row3);
		Label label4 = new Label();
		label4.setStyleName("Default");
		label4.setText("Cloud Number:");
		row3.add(label4);
		cloudNumberLabel = new Label();
		cloudNumberLabel.setStyleName("Default");
		cloudNumberLabel.setText("...");
		row3.add(cloudNumberLabel);
		Row row5 = new Row();
		row5.setCellSpacing(new Extent(10, Extent.PX));
		column1.add(row5);
		Label label5 = new Label();
		label5.setStyleName("Default");
		label5.setText("Secret Token");
		row5.add(label5);
		cloudSecretTokenTextField = new TextField();
		cloudSecretTokenTextField.setStyleName("Default");
		row5.add(cloudSecretTokenTextField);
		Row row4 = new Row();
		column1.add(row4);
		Button button2 = new Button();
		button2.setStyleName("Default");
		button2.setText("Register Cloud");
		button2.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onRegisterCloudActionPerformed(e);
			}
		});
		row4.add(button2);
	}
}

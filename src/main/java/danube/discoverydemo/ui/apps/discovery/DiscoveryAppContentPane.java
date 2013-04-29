package danube.discoverydemo.ui.apps.discovery;

import java.util.ResourceBundle;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Button;
import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Grid;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.SelectField;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.TextField;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.GridLayoutData;
import nextapp.echo.app.layout.RowLayoutData;
import nextapp.echo.app.layout.SplitPaneLayoutData;
import nextapp.echo.app.list.DefaultListModel;
import xdi2.core.xri3.XDI3Segment;
import xdi2.discovery.XDIDiscoveryResult;
import danube.discoverydemo.DiscoveryDemoApplication;
import danube.discoverydemo.parties.RegistryParty;
import danube.discoverydemo.parties.impl.ClientParty;
import danube.discoverydemo.ui.MessageDialog;
import danube.discoverydemo.ui.xdi.DiscoveryResultPanel;
import echopoint.ImageIcon;

public class DiscoveryAppContentPane extends ContentPane {

	private static final long serialVersionUID = 5781883512857770059L;

	protected ResourceBundle resourceBundle;

	private TextField xriTextField;
	private TextField endpointUriTextField;
	private DiscoveryResultPanel discoveryResultPanel;

	private SelectField registrySelectField;
	public DiscoveryAppContentPane() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	@Override
	public void init() {

		super.init();

		this.registrySelectField.removeAll();

		this.registrySelectField.setModel(new DefaultListModel(DiscoveryDemoApplication.getApp().getRegistryParties().toArray()));
	}

	@Override
	public void dispose() {

		super.dispose();
	}

	private void onDiscoverFromXriActionPerformed(ActionEvent e) {

		ClientParty clientParty = DiscoveryDemoApplication.getApp().getClientParty();
		RegistryParty registryParty = (RegistryParty) this.registrySelectField.getSelectedItem();

		try {

			XDI3Segment xri = XDI3Segment.create(this.xriTextField.getText());

			XDIDiscoveryResult discoveryResult = registryParty.discoverFromXri(clientParty, xri);

			this.discoveryResultPanel.setData(discoveryResult);
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, we could not discover the Personal Cloud: " + ex.getMessage(), ex);
			return;
		}
	}

	private void onDiscoverFromEndpointUriActionPerformed(ActionEvent e) {

		RegistryParty registryParty = DiscoveryDemoApplication.getApp().getGlobalRegistryParty();
		ClientParty clientParty = DiscoveryDemoApplication.getApp().getClientParty();

		try {

			String endpointUri = this.endpointUriTextField.getText();

			XDIDiscoveryResult discoveryResult = registryParty.discoverFromEndpointUri(clientParty, endpointUri);

			this.discoveryResultPanel.setData(discoveryResult);
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, we could not discover the Personal Cloud: " + ex.getMessage(), ex);
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
				"/danube/discoverydemo/resource/image/app.png");
		imageIcon2.setIcon(imageReference1);
		imageIcon2.setHeight(new Extent(48, Extent.PX));
		imageIcon2.setWidth(new Extent(48, Extent.PX));
		row2.add(imageIcon2);
		Label label2 = new Label();
		label2.setStyleName("Header");
		label2.setText("Discovery App");
		row2.add(label2);
		Column column1 = new Column();
		column1.setCellSpacing(new Extent(10, Extent.PX));
		splitPane1.add(column1);
		Row row1 = new Row();
		row1.setCellSpacing(new Extent(20, Extent.PX));
		column1.add(row1);
		ImageIcon imageIcon4 = new ImageIcon();
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/danube/discoverydemo/resource/image/cloud_big_discover.png");
		imageIcon4.setIcon(imageReference2);
		imageIcon4.setHeight(new Extent(200, Extent.PX));
		imageIcon4.setWidth(new Extent(200, Extent.PX));
		row1.add(imageIcon4);
		Column column3 = new Column();
		column3.setCellSpacing(new Extent(20, Extent.PX));
		RowLayoutData column3LayoutData = new RowLayoutData();
		column3LayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		column3.setLayoutData(column3LayoutData);
		row1.add(column3);
		Row row3 = new Row();
		row3.setCellSpacing(new Extent(10, Extent.PX));
		column3.add(row3);
		Label label5 = new Label();
		label5.setStyleName("Default");
		label5.setText("Registry:");
		row3.add(label5);
		registrySelectField = new SelectField();
		registrySelectField.setInsets(new Insets(new Extent(5, Extent.PX)));
		row3.add(registrySelectField);
		Column column2 = new Column();
		column2.setCellSpacing(new Extent(10, Extent.PX));
		column3.add(column2);
		Row row6 = new Row();
		row6.setCellSpacing(new Extent(10, Extent.PX));
		column2.add(row6);
		Label label1 = new Label();
		label1.setStyleName("Default");
		label1.setText("Cloud Name / Cloud Number:");
		row6.add(label1);
		xriTextField = new TextField();
		xriTextField.setStyleName("Default");
		row6.add(xriTextField);
		Button button1 = new Button();
		button1.setStyleName("Default");
		button1.setText("Discover");
		button1.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onDiscoverFromXriActionPerformed(e);
			}
		});
		column2.add(button1);
		Column column4 = new Column();
		column4.setCellSpacing(new Extent(10, Extent.PX));
		column3.add(column4);
		Row row7 = new Row();
		row7.setCellSpacing(new Extent(10, Extent.PX));
		column4.add(row7);
		Label label4 = new Label();
		label4.setStyleName("Default");
		label4.setText("XDI Endpoint URI:");
		row7.add(label4);
		endpointUriTextField = new TextField();
		endpointUriTextField.setStyleName("Default");
		row7.add(endpointUriTextField);
		Button button2 = new Button();
		button2.setStyleName("Default");
		button2.setText("Discover");
		button2.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onDiscoverFromEndpointUriActionPerformed(e);
			}
		});
		column4.add(button2);
		discoveryResultPanel = new DiscoveryResultPanel();
		column1.add(discoveryResultPanel);
	}
}

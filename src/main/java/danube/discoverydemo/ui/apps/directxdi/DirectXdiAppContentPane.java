package danube.discoverydemo.ui.apps.directxdi;

import java.io.StringReader;
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
import nextapp.echo.app.TextArea;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.GridLayoutData;
import nextapp.echo.app.layout.RowLayoutData;
import nextapp.echo.app.layout.SplitPaneLayoutData;
import nextapp.echo.app.list.DefaultListModel;
import xdi2.core.Graph;
import xdi2.core.constants.XDIConstants;
import xdi2.core.impl.memory.MemoryGraphFactory;
import xdi2.core.io.MimeType;
import xdi2.core.io.XDIReaderRegistry;
import xdi2.core.util.CopyUtil;
import xdi2.messaging.MessageEnvelope;
import xdi2.messaging.constants.XDIMessagingConstants;
import danube.discoverydemo.DiscoveryDemoApplication;
import danube.discoverydemo.parties.Party;
import danube.discoverydemo.parties.RemoteParty;
import danube.discoverydemo.ui.MessageDialog;
import danube.discoverydemo.xdi.XdiUtil;
import echopoint.ImageIcon;

public class DirectXdiAppContentPane extends ContentPane {

	private static final long serialVersionUID = 5781883512857770059L;

	protected ResourceBundle resourceBundle;

	private SelectField fromSelectField;
	private SelectField toSelectField;
	private TextArea xdiTextArea;

	public DirectXdiAppContentPane() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	@Override
	public void init() {

		super.init();

		this.fromSelectField.removeAll();
		this.toSelectField.removeAll();

		this.fromSelectField.setModel(new DefaultListModel(DiscoveryDemoApplication.getApp().getParties().toArray()));
		this.toSelectField.setModel(new DefaultListModel(DiscoveryDemoApplication.getApp().getRemoteParties().toArray()));
	}

	@Override
	public void dispose() {

		super.dispose();
	}

	private void onPrepareXdiActionPerformed(ActionEvent e) {

		Party fromParty = (Party) this.fromSelectField.getSelectedItem();
		RemoteParty toParty = (RemoteParty) this.toSelectField.getSelectedItem();

		MessageEnvelope messageEnvelope = toParty.getXdiEndpoint().prepareOperation(fromParty.getCloudNumber(), XDIMessagingConstants.XRI_S_GET, XDIConstants.XRI_S_ROOT).getMessageEnvelope();
		Graph tempGraph = MemoryGraphFactory.getInstance().openGraph();
		CopyUtil.copyGraph(messageEnvelope.getGraph(), tempGraph, new XdiUtil.SecretTokenCensoringCopyStrategy());

		this.xdiTextArea.setText(tempGraph.toString(new MimeType("text/xdi;ordered=1;inner=1")));
	}

	private void onSendXdiActionPerformed(ActionEvent e) {

		RemoteParty toParty = (RemoteParty) this.toSelectField.getSelectedItem();

		MessageEnvelope messageEnvelope = new MessageEnvelope();

		try {

			Graph tempGraph = MemoryGraphFactory.getInstance().openGraph();
			XDIReaderRegistry.getAuto().read(tempGraph, new StringReader(this.xdiTextArea.getText()));
			CopyUtil.copyGraph(tempGraph, messageEnvelope.getGraph(), new XdiUtil.SecretTokenInsertingCopyStrategy(toParty.getXdiEndpoint().getSecretToken()));

			toParty.getXdiEndpoint().send(messageEnvelope);
		} catch (Exception ex) {

			MessageDialog.problem("Problem while sending direct XDI: " + ex.getMessage(), ex);
			return;
		}

		/*		SendEventWindowPane sendEventWindowPane = new SendEventWindowPane();
		sendEventWindowPane.setSendEvent(sendEvent);

		MainWindow.findMainContentPane(this).add(sendEventWindowPane);*/
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
		label2.setText("Direct XDI App");
		row2.add(label2);
		Column column1 = new Column();
		column1.setCellSpacing(new Extent(10, Extent.PX));
		splitPane1.add(column1);
		Row row1 = new Row();
		row1.setCellSpacing(new Extent(20, Extent.PX));
		column1.add(row1);
		ImageIcon imageIcon4 = new ImageIcon();
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/danube/discoverydemo/resource/image/cloud_big_direct.png");
		imageIcon4.setIcon(imageReference2);
		imageIcon4.setHeight(new Extent(200, Extent.PX));
		imageIcon4.setWidth(new Extent(200, Extent.PX));
		row1.add(imageIcon4);
		Column column3 = new Column();
		column3.setCellSpacing(new Extent(20, Extent.PX));
		RowLayoutData column3LayoutData = new RowLayoutData();
		column3LayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.TOP));
		column3LayoutData.setWidth(new Extent(500, Extent.PX));
		column3.setLayoutData(column3LayoutData);
		row1.add(column3);
		Grid grid1 = new Grid();
		grid1.setSize(2);
		column3.add(grid1);
		Label label1 = new Label();
		label1.setStyleName("Default");
		label1.setText("From:");
		GridLayoutData label1LayoutData = new GridLayoutData();
		label1LayoutData.setInsets(new Insets(new Extent(10, Extent.PX)));
		label1.setLayoutData(label1LayoutData);
		grid1.add(label1);
		fromSelectField = new SelectField();
		fromSelectField.setInsets(new Insets(new Extent(5, Extent.PX)));
		grid1.add(fromSelectField);
		Label label3 = new Label();
		label3.setStyleName("Default");
		label3.setText("To:");
		GridLayoutData label3LayoutData = new GridLayoutData();
		label3LayoutData.setInsets(new Insets(new Extent(10, Extent.PX)));
		label3.setLayoutData(label3LayoutData);
		grid1.add(label3);
		toSelectField = new SelectField();
		toSelectField.setInsets(new Insets(new Extent(5, Extent.PX)));
		grid1.add(toSelectField);
		Button button1 = new Button();
		button1.setStyleName("Default");
		button1.setText("Prepare XDI Message");
		button1.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onPrepareXdiActionPerformed(e);
			}
		});
		column3.add(button1);
		Button button3 = new Button();
		button3.setStyleName("Default");
		button3.setText("Send XDI Message");
		button3.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onSendXdiActionPerformed(e);
			}
		});
		column3.add(button3);
		xdiTextArea = new TextArea();
		xdiTextArea.setStyleName("Default");
		xdiTextArea.setHorizontalScroll(new Extent(2000, Extent.PX));
		xdiTextArea.setHeight(new Extent(300, Extent.PX));
		xdiTextArea.setWidth(new Extent(95, Extent.PERCENT));
		column1.add(xdiTextArea);
	}
}

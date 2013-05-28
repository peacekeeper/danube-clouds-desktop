package danube.discoverydemo.ui.xdi;

import ibrokerkit.epptools4java.EppEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.EventObject;
import java.util.ResourceBundle;

import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.layout.SplitPaneLayoutData;
import nextapp.echo.extras.app.TabPane;
import nextapp.echo.extras.app.layout.TabPaneLayoutData;

import com.neulevel.epp.transport.tcp.EppChannelTcp;

public class EppEventContentPane extends ContentPane  {

	private static final long serialVersionUID = 5781883512857770059L;

	private static final DateFormat DATEFORMAT = new SimpleDateFormat("HH:mm:ss.SSS");

	protected ResourceBundle resourceBundle;

	private EppEvent eppEvent;

	private ContentPane commandTab;
	private ContentPane responseTab;
	private Label beginTimestampLabel;
	private Label endTimestampLabel;
	private Label durationLabel;
	private Label channelLabel;
	private StringContentPane commandStringContentPane;

	private StringContentPane responseStringContentPane;

	private Label transactionLabel;

	public EppEventContentPane() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	@Override
	public void init() {

		super.init();
	}

	private void refresh(EventObject event) {

		this.beginTimestampLabel.setText(DATEFORMAT.format(this.eppEvent.getBeginTimestamp()));
		this.endTimestampLabel.setText(DATEFORMAT.format(this.eppEvent.getEndTimestamp()));
		this.durationLabel.setText(Long.toString(this.eppEvent.getEndTimestamp().getTime() - this.eppEvent.getBeginTimestamp().getTime()) + " ms");

		this.channelLabel.setText(((EppChannelTcp) this.eppEvent.getEppChannel()).getSocket().getInetAddress().getHostName()); 
		this.transactionLabel.setText(this.eppEvent.getEppCommand().getClientTransactionId());
		
		this.commandStringContentPane.setData(this.eppEvent.getEppCommand().toString());
		this.responseStringContentPane.setData(this.eppEvent.getEppResponse().toString());
	}

	public void setData(EppEvent eppEvent) {

		this.eppEvent = eppEvent;

		this.refresh(null);
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
		Column column1 = new Column();
		column1.setCellSpacing(new Extent(10, Extent.PX));
		SplitPaneLayoutData column1LayoutData = new SplitPaneLayoutData();
		column1LayoutData.setMinimumSize(new Extent(75, Extent.PX));
		column1LayoutData.setMaximumSize(new Extent(75, Extent.PX));
		column1LayoutData.setOverflow(SplitPaneLayoutData.OVERFLOW_HIDDEN);
		column1.setLayoutData(column1LayoutData);
		splitPane1.add(column1);
		Row row2 = new Row();
		row2.setCellSpacing(new Extent(10, Extent.PX));
		column1.add(row2);
		Label label2 = new Label();
		label2.setStyleName("Default");
		label2.setText("Start:");
		row2.add(label2);
		beginTimestampLabel = new Label();
		beginTimestampLabel.setStyleName("Bold");
		beginTimestampLabel.setText("...");
		row2.add(beginTimestampLabel);
		Label label3 = new Label();
		label3.setStyleName("Default");
		label3.setText("End:");
		row2.add(label3);
		endTimestampLabel = new Label();
		endTimestampLabel.setStyleName("Bold");
		endTimestampLabel.setText("...");
		row2.add(endTimestampLabel);
		Label label4 = new Label();
		label4.setStyleName("Default");
		label4.setText("Duration:");
		row2.add(label4);
		durationLabel = new Label();
		durationLabel.setStyleName("Bold");
		durationLabel.setText("...");
		row2.add(durationLabel);
		Row row3 = new Row();
		row3.setCellSpacing(new Extent(10, Extent.PX));
		column1.add(row3);
		Label label5 = new Label();
		label5.setStyleName("Default");
		label5.setText("Channel:");
		row3.add(label5);
		channelLabel = new Label();
		channelLabel.setStyleName("Bold");
		channelLabel.setText("...");
		row3.add(channelLabel);
		Row row1 = new Row();
		row1.setCellSpacing(new Extent(10, Extent.PX));
		column1.add(row1);
		Label label1 = new Label();
		label1.setStyleName("Default");
		label1.setText("Transaction:");
		row1.add(label1);
		transactionLabel = new Label();
		transactionLabel.setStyleName("Bold");
		transactionLabel.setText("...");
		row1.add(transactionLabel);
		TabPane tabPane1 = new TabPane();
		tabPane1.setStyleName("Default");
		splitPane1.add(tabPane1);
		commandTab = new ContentPane();
		TabPaneLayoutData commandTabLayoutData = new TabPaneLayoutData();
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/danube/discoverydemo/resource/image/xdi-request.png");
		commandTabLayoutData.setIcon(imageReference1);
		commandTabLayoutData.setTitle("EPP Command");
		commandTab.setLayoutData(commandTabLayoutData);
		tabPane1.add(commandTab);
		commandStringContentPane = new StringContentPane();
		commandTab.add(commandStringContentPane);
		responseTab = new ContentPane();
		TabPaneLayoutData responseTabLayoutData = new TabPaneLayoutData();
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/danube/discoverydemo/resource/image/xdi-response.png");
		responseTabLayoutData.setIcon(imageReference2);
		responseTabLayoutData.setTitle("EPP Response");
		responseTab.setLayoutData(responseTabLayoutData);
		tabPane1.add(responseTab);
		responseStringContentPane = new StringContentPane();
		responseTab.add(responseStringContentPane);
	}
}

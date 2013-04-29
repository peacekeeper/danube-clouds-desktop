package danube.discoverydemo.ui.xdi;

import ibrokerkit.epptools4java.EppEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EventObject;
import java.util.ResourceBundle;

import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Font;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.layout.SplitPaneLayoutData;
import nextapp.echo.extras.app.TabPane;
import nextapp.echo.extras.app.ToolTipContainer;
import nextapp.echo.extras.app.layout.TabPaneLayoutData;

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
	private Label endpointUriLabel;
	private Label fromLabel;
	private Label toLabel;
	private Label toToolTipLabel;
	private Label fromToolTipLabel;

	private StringContentPane commandStringContentPane;

	private StringContentPane responseStringContentPane;

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

		this.beginTimestampLabel.setText(DATEFORMAT.format(new Date()));
		this.endTimestampLabel.setText(DATEFORMAT.format(new Date()));
		this.durationLabel.setText(Long.toString(new Date().getTime() - new Date().getTime()) + " ms");

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
		splitPane1.setSeparatorHeight(new Extent(10, Extent.PX));
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
		Row row1 = new Row();
		row1.setCellSpacing(new Extent(10, Extent.PX));
		column1.add(row1);
		Label label1 = new Label();
		label1.setStyleName("Default");
		label1.setText("From:");
		row1.add(label1);
		ToolTipContainer toolTipContainer1 = new ToolTipContainer();
		row1.add(toolTipContainer1);
		fromLabel = new Label();
		fromLabel.setStyleName("Default");
		fromLabel.setText("...");
		fromLabel.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		toolTipContainer1.add(fromLabel);
		Panel panel9 = new Panel();
		panel9.setStyleName("Tooltip");
		toolTipContainer1.add(panel9);
		fromToolTipLabel = new Label();
		fromToolTipLabel.setStyleName("Default");
		fromToolTipLabel.setText("TOOLTIP HERE");
		panel9.add(fromToolTipLabel);
		Label label6 = new Label();
		label6.setStyleName("Default");
		label6.setText("To:");
		row1.add(label6);
		ToolTipContainer toolTipContainer2 = new ToolTipContainer();
		row1.add(toolTipContainer2);
		toLabel = new Label();
		toLabel.setStyleName("Default");
		toLabel.setText("...");
		toLabel.setFont(new Font(null, Font.BOLD, new Extent(10, Extent.PT)));
		toolTipContainer2.add(toLabel);
		Panel panel10 = new Panel();
		panel10.setStyleName("Tooltip");
		toolTipContainer2.add(panel10);
		toToolTipLabel = new Label();
		toToolTipLabel.setStyleName("Default");
		toToolTipLabel.setText("TOOLTIP HERE");
		panel10.add(toToolTipLabel);
		Row row3 = new Row();
		row3.setCellSpacing(new Extent(10, Extent.PX));
		column1.add(row3);
		Label label5 = new Label();
		label5.setStyleName("Default");
		label5.setText("Endpoint:");
		row3.add(label5);
		endpointUriLabel = new Label();
		endpointUriLabel.setStyleName("Default");
		endpointUriLabel.setText("...");
		endpointUriLabel.setFont(new Font(null, Font.BOLD, new Extent(10,
				Extent.PT)));
		row3.add(endpointUriLabel);
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

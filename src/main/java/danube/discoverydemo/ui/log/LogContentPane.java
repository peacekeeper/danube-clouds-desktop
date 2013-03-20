package danube.discoverydemo.ui.log;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.extras.app.TabPane;
import nextapp.echo.extras.app.layout.TabPaneLayoutData;
import nextapp.echo.webcontainer.WebContainerServlet;
import danube.discoverydemo.DiscoveryDemoApplication;
import danube.discoverydemo.logger.LogEntry;
import danube.discoverydemo.logger.Logger;
import danube.discoverydemo.logger.events.LogEvent;
import danube.discoverydemo.logger.events.LogListener;
import danube.discoverydemo.ui.MainWindow;
import danube.discoverydemo.ui.html.HtmlLabel;
import danube.discoverydemo.ui.xdi.TransactionEventPanel;
import danube.discoverydemo.util.HtmlUtil;
import danube.discoverydemo.xdi.events.XdiListener;
import danube.discoverydemo.xdi.events.XdiResolutionEndpointEvent;
import danube.discoverydemo.xdi.events.XdiResolutionEvent;
import danube.discoverydemo.xdi.events.XdiResolutionInameEvent;
import danube.discoverydemo.xdi.events.XdiResolutionInumberEvent;
import danube.discoverydemo.xdi.events.XdiTransactionEvent;

public class LogContentPane extends ContentPane implements LogListener, XdiListener {

	private static final long serialVersionUID = -3506230103141402132L;

	private static final DateFormat DATEFORMAT = new SimpleDateFormat("HH:mm:ss");

	protected ResourceBundle resourceBundle;

	private HtmlLabel htmlLabel;
	private Column transactionEventPanelsColumn;

	/**
	 * Creates a new <code>LogContentPane</code>.
	 */
	public LogContentPane() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	@Override
	public void init() {

		super.init();

		MainWindow.findChildComponentById(this, "transactionEventPanelsContentPane").setVisible(MainWindow.findMainContentPane(this).isDeveloperModeSelected());

		// add us as listener

		DiscoveryDemoApplication.getApp().getLogger().addLogListener(this);
		DiscoveryDemoApplication.getApp().getXdi().addXdiListener(this);
	}

	@Override
	public void dispose() {

		super.dispose();

		// remove us as listener

		DiscoveryDemoApplication.getApp().getLogger().removeLogListener(this);
		DiscoveryDemoApplication.getApp().getXdi().removeXdiListener(this);
	}

	@Override
	public void onLogEvent(LogEvent logEvent) {

		LogEntry logEntry = logEvent.getLogEntry();

		StringBuffer line = new StringBuffer();
		line.append(DATEFORMAT.format(new Date()) + " ");
		line.append("<span style=\"color:");
		if (logEntry.getLevel().equals("INFO")) line.append("#4fa4f1");
		if (logEntry.getLevel().equals("WARNING")) line.append("#fe605f");
		if (logEntry.getLevel().equals("PROBLEM")) line.append("#fe605f");
		line.append("\">");
		line.append(HtmlUtil.htmlEncode(logEntry.getLevel(), true, false));
		line.append("</span>: ");
		line.append(HtmlUtil.htmlEncode(logEntry.getMessage(), true, false));
		line.append("<br>");

		String html = this.htmlLabel.getHtml();
		html = html.replace("<!-- $$$ -->", "<!-- $$$ -->" + line.toString());
		this.htmlLabel.setHtml(html);
	}

	public void onXdiTransaction(XdiTransactionEvent xdiTransactionEvent) {

		if (WebContainerServlet.getActiveConnection().getUserInstance().getApplicationInstance() != this.getApplicationInstance()) return;

		this.addTransactionEventPanel(xdiTransactionEvent);
	}

	public void onXdiResolution(XdiResolutionEvent xdiResolutionEvent) {

		Logger logger = DiscoveryDemoApplication.getApp().getLogger();

		if (xdiResolutionEvent instanceof XdiResolutionInameEvent) {

			logger.info("The I-Name " + ((XdiResolutionInameEvent) xdiResolutionEvent).getIname() + " has been resolved to the I-Number " + ((XdiResolutionInameEvent) xdiResolutionEvent).getInumber() + ".", null);
		} else if (xdiResolutionEvent instanceof XdiResolutionInumberEvent) {

			logger.info("The I-Number " + ((XdiResolutionInumberEvent) xdiResolutionEvent).getInumber() + " has been resolved to the XDI Endpoint" + ((XdiResolutionInumberEvent) xdiResolutionEvent).getEndpoint() + ".", null);
		} else if (xdiResolutionEvent instanceof XdiResolutionEndpointEvent) {

			logger.info("The XDI endpoint " + ((XdiResolutionEndpointEvent) xdiResolutionEvent).getEndpoint() + " has been resolved to the I-Number " + ((XdiResolutionEndpointEvent) xdiResolutionEvent).getInumber() + ".", null);
		}
	}

	private void addTransactionEventPanel(final XdiTransactionEvent transactionEvent) {

		TransactionEventPanel transactionEventPanel = new TransactionEventPanel();
		transactionEventPanel.setTransactionEvent(transactionEvent);

		this.transactionEventPanelsColumn.add(transactionEventPanel, 0);
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setOverflow(2);
		this.setInsets(new Insets(new Extent(10, Extent.PX)));
		TabPane tabPane1 = new TabPane();
		tabPane1.setStyleName("Default");
		add(tabPane1);
		ContentPane contentPane2 = new ContentPane();
		contentPane2.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(
				5, Extent.PX), new Extent(0, Extent.PX), new Extent(0,
						Extent.PX)));
		TabPaneLayoutData contentPane2LayoutData = new TabPaneLayoutData();
		contentPane2LayoutData.setTitle("Events");
		contentPane2.setLayoutData(contentPane2LayoutData);
		tabPane1.add(contentPane2);
		htmlLabel = new HtmlLabel();
		htmlLabel
		.setHtml("<div style=\"white-space:nowrap;font-family:monospace;\"><!-- $$$ --></div>");
		contentPane2.add(htmlLabel);
		ContentPane contentPane3 = new ContentPane();
		contentPane3.setId("transactionEventPanelsContentPane");
		contentPane3.setVisible(false);
		TabPaneLayoutData contentPane3LayoutData = new TabPaneLayoutData();
		contentPane3LayoutData.setTitle("XDI Transactions");
		contentPane3.setLayoutData(contentPane3LayoutData);
		tabPane1.add(contentPane3);
		transactionEventPanelsColumn = new Column();
		contentPane3.add(transactionEventPanelsColumn);
	}
}

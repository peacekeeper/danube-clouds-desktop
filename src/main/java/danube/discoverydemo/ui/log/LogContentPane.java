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
import xdi2.client.XDIClientListener;
import xdi2.client.events.XDIDiscoverEvent;
import xdi2.client.events.XDIDiscoverFromEndpointUriEvent;
import xdi2.client.events.XDIDiscoverFromXriEvent;
import xdi2.client.events.XDISendEvent;
import danube.discoverydemo.DiscoveryDemoApplication;
import danube.discoverydemo.events.LogEvent;
import danube.discoverydemo.events.LogListener;
import danube.discoverydemo.logger.LogEntry;
import danube.discoverydemo.logger.Logger;
import danube.discoverydemo.ui.html.HtmlLabel;
import danube.discoverydemo.ui.xdi.SendEventPanel;
import danube.discoverydemo.util.HtmlUtil;

public class LogContentPane extends ContentPane implements LogListener, XDIClientListener {

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

		// add us as listener

		DiscoveryDemoApplication.getApp().getEvents().addLogListener(this);
		DiscoveryDemoApplication.getApp().getEvents().addClientListener(this);
	}

	@Override
	public void dispose() {

		super.dispose();

		// remove us as listener

		DiscoveryDemoApplication.getApp().getEvents().removeLogListener(this);
		DiscoveryDemoApplication.getApp().getEvents().removeClientListener(this);
	}

	@Override
	public void onLog(LogEvent logEvent) {

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

	public void onSend(XDISendEvent sendEvent) {

		if (WebContainerServlet.getActiveConnection().getUserInstance().getApplicationInstance() != this.getApplicationInstance()) return;

		this.addSendEventPanel(sendEvent);
	}

	public void onDiscover(XDIDiscoverEvent discoverEvent) {

		Logger logger = DiscoveryDemoApplication.getApp().getLogger();

		if (discoverEvent instanceof XDIDiscoverFromXriEvent) {

			logger.info("The Cloud Name " + ((XDIDiscoverFromXriEvent) discoverEvent).getXri() + " has been resolved to the XDI Endpoint " + ((XDIDiscoverFromXriEvent) discoverEvent).getDiscoveryResult().getEndpointUri() + ".", null);
		} else if (discoverEvent instanceof XDIDiscoverFromEndpointUriEvent) {

			logger.info("The XDI endpoint " + ((XDIDiscoverFromEndpointUriEvent) discoverEvent).getEndpointUri() + " has been resolved to the Cloud Number " + ((XDIDiscoverFromEndpointUriEvent) discoverEvent).getDiscoveryResult().getCloudNumber() + ".", null);
		}
	}

	private void addSendEventPanel(XDISendEvent sendEvent) {

		SendEventPanel sendEventPanel = new SendEventPanel();
		sendEventPanel.setSendEvent(sendEvent);

		this.transactionEventPanelsColumn.add(sendEventPanel, 0);
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
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
		TabPaneLayoutData contentPane3LayoutData = new TabPaneLayoutData();
		contentPane3LayoutData.setTitle("XDI Transactions");
		contentPane3.setLayoutData(contentPane3LayoutData);
		tabPane1.add(contentPane3);
		transactionEventPanelsColumn = new Column();
		contentPane3.add(transactionEventPanelsColumn);
	}
}

package danube.discoverydemo.ui.log;


import ibrokerkit.epptools4java.EppEvent;
import ibrokerkit.epptools4java.EppListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import nextapp.echo.app.Color;
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
import danube.discoverydemo.util.HtmlUtil;

public class LogContentPane extends ContentPane implements LogListener, XDIClientListener, EppListener {

	private static final long serialVersionUID = -3506230103141402132L;

	private static final DateFormat DATEFORMAT = new SimpleDateFormat("HH:mm:ss");

	protected ResourceBundle resourceBundle;

	private HtmlLabel htmlLabel;
	private Column sendEventPanelsColumn;

	private Column eppEventPanelsColumn;

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
		DiscoveryDemoApplication.getApp().getServlet().getEppTools().addEppListener(this);
	}

	@Override
	public void dispose() {

		super.dispose();

		// remove us as listener

		DiscoveryDemoApplication.getApp().getEvents().removeLogListener(this);
		DiscoveryDemoApplication.getApp().getEvents().removeClientListener(this);
		DiscoveryDemoApplication.getApp().getServlet().getEppTools().removeEppListener(this);
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

	@Override
	public void onSend(EppEvent eppEvent) {

		if (WebContainerServlet.getActiveConnection().getUserInstance().getApplicationInstance() != this.getApplicationInstance()) return;

		this.addEppEventPanel(eppEvent);
	}

	private void addSendEventPanel(XDISendEvent sendEvent) {

		SendEventPanel sendEventPanel = new SendEventPanel();
		sendEventPanel.setData(sendEvent);

		this.sendEventPanelsColumn.add(sendEventPanel, 0);
	}

	private void addEppEventPanel(EppEvent eppEvent) {

		EppEventPanel eppEventPanel = new EppEventPanel();
		eppEventPanel.setData(eppEvent);

		this.eppEventPanelsColumn.add(eppEventPanel, 0);
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
		tabPane1.setBackground(new Color(0xcccccc));
		add(tabPane1);
		ContentPane contentPane2 = new ContentPane();
		contentPane2.setInsets(new Insets(new Extent(0, Extent.PX), new Extent(
				5, Extent.PX), new Extent(0, Extent.PX), new Extent(0,
				Extent.PX)));
		TabPaneLayoutData contentPane2LayoutData = new TabPaneLayoutData();
		contentPane2LayoutData.setTitle("Event Log");
		contentPane2LayoutData.setRolloverForeground(Color.BLACK);
		contentPane2LayoutData.setActiveForeground(Color.BLACK);
		contentPane2LayoutData.setInactiveForeground(Color.BLACK);
		contentPane2.setLayoutData(contentPane2LayoutData);
		tabPane1.add(contentPane2);
		htmlLabel = new HtmlLabel();
		htmlLabel
				.setHtml("<div style=\"white-space:nowrap;font-family:monospace;\"><!-- $$$ --></div>");
		htmlLabel.setForeground(Color.BLACK);
		contentPane2.add(htmlLabel);
		ContentPane contentPane3 = new ContentPane();
		TabPaneLayoutData contentPane3LayoutData = new TabPaneLayoutData();
		contentPane3LayoutData.setTitle("XDI Log");
		contentPane3LayoutData.setActiveForeground(Color.BLACK);
		contentPane3LayoutData.setRolloverForeground(Color.BLACK);
		contentPane3LayoutData.setInactiveForeground(Color.BLACK);
		contentPane3.setLayoutData(contentPane3LayoutData);
		tabPane1.add(contentPane3);
		sendEventPanelsColumn = new Column();
		contentPane3.add(sendEventPanelsColumn);
		ContentPane contentPane4 = new ContentPane();
		TabPaneLayoutData contentPane4LayoutData = new TabPaneLayoutData();
		contentPane4LayoutData.setTitle("EPP Log");
		contentPane4LayoutData.setActiveForeground(Color.BLACK);
		contentPane4LayoutData.setRolloverForeground(Color.BLACK);
		contentPane4LayoutData.setInactiveForeground(Color.BLACK);
		contentPane4.setLayoutData(contentPane4LayoutData);
		tabPane1.add(contentPane4);
		eppEventPanelsColumn = new Column();
		contentPane4.add(eppEventPanelsColumn);
	}
}

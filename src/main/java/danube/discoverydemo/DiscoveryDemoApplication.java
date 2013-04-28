package danube.discoverydemo;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.TaskQueueHandle;
import nextapp.echo.app.Window;
import nextapp.echo.webcontainer.WebContainerServlet;
import danube.discoverydemo.events.ApplicationXdiEndpointClosedEvent;
import danube.discoverydemo.events.ApplicationXdiEndpointOpenedEvent;
import danube.discoverydemo.events.Events;
import danube.discoverydemo.logger.Logger;
import danube.discoverydemo.parties.impl.AppParty;
import danube.discoverydemo.parties.impl.CloudParty;
import danube.discoverydemo.parties.impl.CloudServiceProviderParty;
import danube.discoverydemo.parties.impl.GlobalRegistryParty;
import danube.discoverydemo.parties.impl.PeerRegistryParty;
import danube.discoverydemo.parties.impl.RegistrarParty;
import danube.discoverydemo.resource.style.Styles;
import danube.discoverydemo.ui.MainContentPane;
import danube.discoverydemo.ui.MainWindow;
import danube.discoverydemo.xdi.XdiEndpoint;

/**
 * Application instance implementation.
 */
public class DiscoveryDemoApplication extends ApplicationInstance {

	private static final long serialVersionUID = -8129396233829232511L;

	private DiscoveryDemoServlet servlet;

	private MainWindow mainWindow;
	private TaskQueueHandle taskQueueHandle;
	private Map<String, Object> attributes;
	private XdiEndpoint xdiEndpoint;

	private CloudServiceProviderParty cloudServiceProviderParty;
	private RegistrarParty registrarParty;
	private GlobalRegistryParty globalRegistryParty;
	private PeerRegistryParty peerRegistryParty;
	private CloudParty cloudParty;
	private AppParty appParty;

	private Logger logger;
	private Events events;

	public DiscoveryDemoApplication(DiscoveryDemoServlet servlet) {

		this.servlet = servlet;
	}

	public static DiscoveryDemoApplication getApp() {

		return (DiscoveryDemoApplication) getActive();
	}

	public static DiscoveryDemoApplication getAppFromSession(HttpSession session) {

		return (DiscoveryDemoApplication) session.getAttribute("__echoapp");
	}

	@Override
	public Window init() {

		// init session and window

		HttpSession session = WebContainerServlet.getActiveConnection().getRequest().getSession();
		session.setAttribute("__echoapp", this);
		this.setStyleSheet(Styles.DEFAULT_STYLE_SHEET);
		this.mainWindow = new MainWindow();
		this.mainWindow.setTitle("XDI Discovery Demo");
		this.mainWindow.setContent(new MainContentPane());
		this.taskQueueHandle = this.createTaskQueue();
		this.attributes = new HashMap<String, Object> ();

		// init logger and events

		this.logger = new Logger();
		this.events = new Events();

		// init parties

		this.cloudServiceProviderParty = CloudServiceProviderParty.create();
		this.registrarParty = RegistrarParty.create();
		this.globalRegistryParty = GlobalRegistryParty.create();
		this.peerRegistryParty = PeerRegistryParty.create(null, null, null, null);
		this.cloudParty = null;
		this.appParty = AppParty.create();

		// done

		return this.mainWindow;
	}

	@Override
	public void dispose() {

		super.dispose();

		this.removeTaskQueue(this.taskQueueHandle);
	}

	public DiscoveryDemoServlet getServlet() {

		return this.servlet;
	}

	/*
	 * Session and window
	 */

	public MainWindow getMainWindow() {

		return this.mainWindow;
	}

	public TaskQueueHandle getTaskQueueHandle() {

		return this.taskQueueHandle;
	}

	public void setAttribute(String name, Object value) {

		this.attributes.put(name, value);
	}

	public Object getAttribute(String name) {

		return this.attributes.get(name);
	}

	/*
	 * Open endpoint
	 */

	public void openEndpoint(XdiEndpoint endpoint) {

		try {

			if (this.xdiEndpoint != null) this.closeEndpoint();

			String remoteAddr = WebContainerServlet.getActiveConnection().getRequest().getRemoteAddr();

			this.xdiEndpoint = endpoint;

			this.getEvents().fireApplicationEvent(new ApplicationXdiEndpointOpenedEvent(this, this.xdiEndpoint));

			this.logger.info("Your Personal Cloud has been opened from " + remoteAddr + ".", null);
		} catch (Exception ex) {

			if (this.isEndpointOpen()) this.closeEndpoint();
		}
	}

	public void closeEndpoint() {

		try {

			if (this.xdiEndpoint == null) return;

			this.getEvents().fireApplicationEvent(new ApplicationXdiEndpointClosedEvent(this, this.xdiEndpoint));
		} catch (Exception ex) {

		} finally {

			this.logger.info("Your Personal Cloud has been closed.", null);

			this.xdiEndpoint = null;
		}
	}

	public XdiEndpoint getOpenEndpoint() {

		return this.xdiEndpoint;
	}

	public boolean isEndpointOpen() {

		return this.xdiEndpoint != null;
	}

	/*
	 * Parties
	 */

	public CloudServiceProviderParty getCloudServiceProviderParty() {

		return this.cloudServiceProviderParty;
	}

	public RegistrarParty getRegistrarParty() {

		return this.registrarParty;
	}

	public GlobalRegistryParty getGlobalRegistryParty() {

		return this.globalRegistryParty;
	}

	public PeerRegistryParty getPeerRegistryParty() {

		return this.peerRegistryParty;
	}

	public AppParty getAppParty() {

		return this.appParty;
	}

	public CloudParty getCloudParty() {

		return this.cloudParty;
	}

	public void setCloudParty(CloudParty cloudParty) {

		this.cloudParty = cloudParty;
	}

	public Logger getLogger() {

		return this.logger;
	}

	public Events getEvents() {

		return this.events;
	}
}

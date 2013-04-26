package danube.discoverydemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.TaskQueueHandle;
import nextapp.echo.app.Window;
import nextapp.echo.webcontainer.WebContainerServlet;
import xdi2.core.xri3.XDI3Segment;
import danube.discoverydemo.events.ApplicationEvent;
import danube.discoverydemo.events.ApplicationListener;
import danube.discoverydemo.events.ApplicationXdiEndpointClosedEvent;
import danube.discoverydemo.events.ApplicationXdiEndpointOpenedEvent;
import danube.discoverydemo.logger.Logger;
import danube.discoverydemo.parties.AppParty;
import danube.discoverydemo.parties.CloudServiceProviderParty;
import danube.discoverydemo.parties.GlobalRegistryParty;
import danube.discoverydemo.parties.PeerRegistryParty;
import danube.discoverydemo.parties.RegistrarParty;
import danube.discoverydemo.resource.style.Styles;
import danube.discoverydemo.ui.MainContentPane;
import danube.discoverydemo.ui.MainWindow;
import danube.discoverydemo.xdi.Xdi;
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
	private AppParty appParty;

	private transient Logger logger;
	private transient Xdi xdi;

	private List<ApplicationListener> applicationListeners;

	public DiscoveryDemoApplication(DiscoveryDemoServlet servlet) {

		this.servlet = servlet;

		this.initLogger();
		this.initXdi();

		this.applicationListeners = new ArrayList<ApplicationListener> ();
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

		// init parties

		this.cloudServiceProviderParty = CloudServiceProviderParty.create(XDI3Segment.create("@example.csp"));
		this.registrarParty = RegistrarParty.create(this.getXdi(), XDI3Segment.create("@example.registrar"));
		this.globalRegistryParty = GlobalRegistryParty.create(this.getXdi());
		this.peerRegistryParty = PeerRegistryParty.create(this.getXdi(), XDI3Segment.create("@example.registry"));
		this.appParty = new AppParty();

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

	public void openEndpoint(XdiEndpoint endpoint) throws Exception {

		try {

			if (this.xdiEndpoint != null) this.closeEndpoint();

			String remoteAddr = WebContainerServlet.getActiveConnection().getRequest().getRemoteAddr();

			this.xdiEndpoint = endpoint;

			this.fireApplicationEvent(new ApplicationXdiEndpointOpenedEvent(this, this.xdiEndpoint));

			this.logger.info("Your Personal Cloud has been opened from " + remoteAddr + ".", null);
		} catch (Exception ex) {

			if (this.isEndpointOpen()) this.closeEndpoint();
			throw ex;
		}
	}

	public void closeEndpoint() {

		try {

			if (this.xdiEndpoint == null) return;

			this.fireApplicationEvent(new ApplicationXdiEndpointClosedEvent(this, this.xdiEndpoint));
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

	/*
	 * Logger and Xdi
	 */

	private void initLogger() {

		try {

			this.logger = new Logger();
		} catch (Exception ex) {

			throw new RuntimeException("Cannot initialize Logger component: " + ex.getMessage(), ex);
		}
	}

	private void initXdi() {

		try {

			this.xdi = new Xdi();
		} catch (Exception ex) {

			throw new RuntimeException("Cannot initialize Xdi component: " + ex.getMessage(), ex);
		}
	}

	public Logger getLogger() {

		if (this.logger == null) this.initLogger();

		return this.logger;
	}

	public Xdi getXdi() {

		if (this.xdi == null) this.initXdi();

		return this.xdi;
	}

	/*
	 * Events
	 */

	public void addApplicationListener(ApplicationListener applicationListener) {

		if (this.applicationListeners.contains(applicationListener)) return;
		this.applicationListeners.add(applicationListener);
	}

	public void removeApplicationListener(ApplicationListener applicationListener) {

		this.applicationListeners.remove(applicationListener);
	}

	public void fireApplicationEvent(ApplicationEvent applicationEvent) {

		for (Iterator<ApplicationListener> applicationListeners = this.applicationListeners.iterator(); applicationListeners.hasNext(); ) {

			ApplicationListener applicationListener = applicationListeners.next();
			applicationListener.onApplicationEvent(applicationEvent);
		}
	}
}

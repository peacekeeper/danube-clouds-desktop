package danube.discoverydemo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import xdi2.core.xri3.XDI3Segment;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.TaskQueueHandle;
import nextapp.echo.app.Window;
import nextapp.echo.webcontainer.WebContainerServlet;
import danube.discoverydemo.events.Events;
import danube.discoverydemo.logger.Logger;
import danube.discoverydemo.parties.Party;
import danube.discoverydemo.parties.RegistryParty;
import danube.discoverydemo.parties.RemoteParty;
import danube.discoverydemo.parties.impl.ClientParty;
import danube.discoverydemo.parties.impl.CloudParty;
import danube.discoverydemo.parties.impl.CloudServiceProviderParty;
import danube.discoverydemo.parties.impl.GlobalRegistryParty;
import danube.discoverydemo.parties.impl.PeerRegistryParty;
import danube.discoverydemo.parties.impl.RegistrarParty;
import danube.discoverydemo.resource.style.Styles;
import danube.discoverydemo.ui.MainContentPane;
import danube.discoverydemo.ui.MainWindow;

/**
 * Application instance implementation.
 */
public class DiscoveryDemoApplication extends ApplicationInstance {

	private static final long serialVersionUID = -8129396233829232511L;

	private DiscoveryDemoServlet servlet;

	private MainWindow mainWindow;
	private TaskQueueHandle taskQueueHandle;
	private Map<String, Object> attributes;

	private CloudServiceProviderParty cloudServiceProviderParty;
	private RegistrarParty registrarParty;
	private GlobalRegistryParty globalRegistryParty;
	private PeerRegistryParty peerRegistryParty;
	private CloudParty cloudParty;
	private ClientParty clientParty;

	private Set<Party> parties;
	private Set<RemoteParty> remoteParties;
	private Set<RegistryParty> registryParties;

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
		this.mainWindow.setTitle("Cloud Name Management");
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
		this.clientParty = ClientParty.create();

		this.parties = new HashSet<Party> (Arrays.asList(new Party[] { this.cloudServiceProviderParty, this.registrarParty, this.globalRegistryParty, this.peerRegistryParty, this.cloudParty }));
		this.remoteParties = new HashSet<RemoteParty> (Arrays.asList(new RemoteParty[] { this.cloudServiceProviderParty, this.registrarParty, this.globalRegistryParty, this.peerRegistryParty }));
		this.registryParties = new HashSet<RegistryParty> (Arrays.asList(new RegistryParty[] { this.globalRegistryParty, this.peerRegistryParty }));

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

	public ClientParty getClientParty() {

		return this.clientParty;
	}

	public CloudParty getCloudParty() {

		return this.cloudParty;
	}

	public void setCloudParty(CloudParty cloudParty) {

		this.parties.remove(this.cloudParty);
		this.cloudParty = cloudParty;
		this.parties.add(cloudParty);
	}

	public Set<Party> getParties() {

		return this.parties;
	}

	public Set<RemoteParty> getRemoteParties() {

		return this.remoteParties;
	}

	public Set<RegistryParty> getRegistryParties() {

		return this.registryParties;
	}

	public Party getPartyByCloudNumber(XDI3Segment cloudNumber) {

		for (Party party : this.getParties()) {

			if (cloudNumber.equals(party.getCloudNumber())) return party;
		}

		return null;
	}

	/*
	 * Logger and Events
	 */

	public Logger getLogger() {

		return this.logger;
	}

	public Events getEvents() {

		return this.events;
	}
}

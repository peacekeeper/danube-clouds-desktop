package danube.discoverydemo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.TaskQueueHandle;
import nextapp.echo.app.Window;
import nextapp.echo.webcontainer.WebContainerServlet;
import xdi2.core.xri3.XDI3Segment;
import danube.discoverydemo.events.Events;
import danube.discoverydemo.logger.Logger;
import danube.discoverydemo.parties.Party;
import danube.discoverydemo.parties.RegistryParty;
import danube.discoverydemo.parties.RemoteParty;
import danube.discoverydemo.parties.impl.AnonymousParty;
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
	private AnonymousParty anonymousParty;

	private Set<Party> parties;

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
		this.anonymousParty = AnonymousParty.create();

		this.parties = new LinkedHashSet<Party> (Arrays.asList(new Party[] { this.cloudServiceProviderParty, this.registrarParty, this.globalRegistryParty, this.peerRegistryParty, this.anonymousParty }));

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

	public AnonymousParty getAnonymousParty() {

		return this.anonymousParty;
	}

	public CloudParty getCloudParty() {

		return this.cloudParty;
	}

	public void setCloudParty(CloudParty cloudParty) {

		this.parties.remove(this.cloudParty);
		this.cloudParty = cloudParty;
		if (cloudParty != null) this.parties.add(cloudParty);
	}

	public Set<Party> getParties() {

		return this.parties;
	}

	public Set<RemoteParty> getRemoteParties() {

		Set<RemoteParty> remoteParties = new LinkedHashSet<RemoteParty> ();
		for (Party party : this.getParties()) if (party instanceof RemoteParty) remoteParties.add((RemoteParty) party);

		return remoteParties;
	}

	public Set<RegistryParty> getRegistryParties() {

		Set<RegistryParty> registryParties = new LinkedHashSet<RegistryParty> ();
		for (Party party : this.getParties()) if (party instanceof RegistryParty) registryParties.add((RegistryParty) party);

		return registryParties;
	}

	public Party getPartyByCloudNumber(XDI3Segment cloudNumber) {

		if (cloudNumber == null) return null;

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

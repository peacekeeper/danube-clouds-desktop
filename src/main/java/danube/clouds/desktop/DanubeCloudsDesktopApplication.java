package danube.clouds.desktop;

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
import danube.clouds.desktop.events.Events;
import danube.clouds.desktop.logger.Logger;
import danube.clouds.desktop.parties.Party;
import danube.clouds.desktop.parties.RegistryParty;
import danube.clouds.desktop.parties.RemoteParty;
import danube.clouds.desktop.parties.impl.AnonymousParty;
import danube.clouds.desktop.parties.impl.CloudServiceProviderParty;
import danube.clouds.desktop.parties.impl.GlobalRegistryParty;
import danube.clouds.desktop.parties.impl.MyCloudParty;
import danube.clouds.desktop.parties.impl.OtherCloudParty;
import danube.clouds.desktop.parties.impl.PeerRegistryParty;
import danube.clouds.desktop.parties.impl.RegistrarParty;
import danube.clouds.desktop.resource.style.Styles;
import danube.clouds.desktop.ui.MainContentPane;
import danube.clouds.desktop.ui.MainWindow;

/**
 * Application instance implementation.
 */
public class DanubeCloudsDesktopApplication extends ApplicationInstance {

	private static final long serialVersionUID = -8129396233829232511L;

	private DanubeCloudsDesktopServlet servlet;

	private MainWindow mainWindow;
	private TaskQueueHandle taskQueueHandle;
	private Map<String, Object> attributes;

	private CloudServiceProviderParty cloudServiceProviderParty;
	private RegistrarParty registrarParty;
	private GlobalRegistryParty globalRegistryParty;
	private PeerRegistryParty peerRegistryParty;
	private MyCloudParty myCloudParty;
	private OtherCloudParty otherCloudParty;
	private AnonymousParty anonymousParty;

	private Set<Party> parties;

	private Logger logger;
	private Events events;

	public DanubeCloudsDesktopApplication(DanubeCloudsDesktopServlet servlet) {

		this.servlet = servlet;
	}

	public static DanubeCloudsDesktopApplication getApp() {

		return (DanubeCloudsDesktopApplication) getActive();
	}

	public static DanubeCloudsDesktopApplication getAppFromSession(HttpSession session) {

		return (DanubeCloudsDesktopApplication) session.getAttribute("__echoapp");
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
		this.myCloudParty = null;
		this.otherCloudParty = null;
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

	public DanubeCloudsDesktopServlet getServlet() {

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

	public MyCloudParty getMyCloudParty() {

		return this.myCloudParty;
	}

	public void setMyCloudParty(MyCloudParty myCloudParty) {

		this.parties.remove(this.myCloudParty);
		this.myCloudParty = myCloudParty;
		if (myCloudParty != null) this.parties.add(myCloudParty);
	}

	public OtherCloudParty getOtherCloudParty() {

		return this.otherCloudParty;
	}

	public void setOtherCloudParty(OtherCloudParty otherCloudParty) {

		this.parties.remove(this.otherCloudParty);
		this.otherCloudParty = otherCloudParty;
		if (otherCloudParty != null) this.parties.add(otherCloudParty);
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

package danube.discoverydemo;


import ibrokerkit.epptools4java.EppTools;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.TaskQueueHandle;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;
import nextapp.echo.webcontainer.service.StaticTextService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import danube.discoverydemo.external.ExternalCall;
import danube.discoverydemo.external.ExternalCallReceiver;
import danube.discoverydemo.ui.MainWindow;

public class DiscoveryDemoServlet extends WebContainerServlet {

	private static final long serialVersionUID = -7856586634363745175L;

	private static final Logger log = LoggerFactory.getLogger(DiscoveryDemoServlet.class);

	private Properties properties;
	private EppTools eppTools;
	private ibrokerkit.iname4java.store.XriStore xriStore;
	private ibrokerkit.ibrokerstore.store.Store ibrokerStore;
	private Cache cloudCache = CacheManager.create(DiscoveryDemoServlet.class.getResourceAsStream("ehcache.xml")).getCache("DiscoveryDemoServlet_CLOUD_CACHE");
	private Map<String, Object> cloudCache2 = new HashMap<String, Object> ();

	@Override
	public ApplicationInstance newApplicationInstance() {

		return new DiscoveryDemoApplication(this);
	}

	public void init(ServletConfig servletConfig) throws ServletException {

		super.init(servletConfig);

		log.info("Initializing...");

		this.addInitScript(JavaScriptService.forResource("CustomWaitIndicator", "danube/discoverydemo/resource/js/CustomWaitIndicator.js"));
		this.addInitStyleSheet(StaticTextService.forResource("discoverydemo.css", "text/css", "danube/discoverydemo/resource/style/discoverydemo.css"));

		log.info("Initializing complete.");
	}

	@Override
	protected void process(final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException {

		// external call on stack

/*		final DiscoveryDemoApplication application = DiscoveryDemoApplication.getAppFromSession(request.getSession());
		Stack<ExternalCall> stack = ExternalCall.getStackFromSession(request.getSession());
		ExternalCall externalCall;

		if (application != null && stack != null && ! stack.isEmpty()) {

			while (! stack.isEmpty()) {

				externalCall = stack.pop();
				log.debug("POP EXTERNAL CALL: " + externalCall);

				ExternalCallReceiver externalCallReceiver = findExternalCallReceiver(application, externalCall);

				if (externalCallReceiver != null) {

					onExternalCallApplication(application, externalCall, externalCallReceiver);
				}
			}
		}
*/
		// process the request

		super.process(request, response);

		// check for new external call

	/*	if ((externalCall = ExternalCall.fromRequest(request)) != null) {

			if (application == null) {

				stack.add(externalCall);
				log.debug("PUSH EXTERNAL CALL: " + externalCall);
			} else {

				ExternalCallReceiver externalCallReceiver = findExternalCallReceiver(application, externalCall);

				if (externalCallReceiver != null) {

					onExternalCallRaw(application, externalCall, externalCallReceiver, request, response);
				}
			}
		}*/
	}

	public static ExternalCallReceiver findExternalCallReceiver(DiscoveryDemoApplication application, ExternalCall externalCall) {

		if (application == null) return null;

		MainWindow mainWindow = application.getMainWindow();
		if (mainWindow == null) return null;

		ExternalCallReceiver externalCallReceiver;
		externalCallReceiver = (ExternalCallReceiver) MainWindow.findChildComponentByClassName(mainWindow, externalCall.getPath());
		if (externalCallReceiver == null) externalCallReceiver = (ExternalCallReceiver) MainWindow.findChildComponentById(mainWindow, externalCall.getPath());
		if (externalCallReceiver == null) externalCallReceiver = (ExternalCallReceiver) MainWindow.findChildComponentByRenderId(mainWindow, externalCall.getPath());

		return externalCallReceiver;
	}

	public static void onExternalCallApplication(final DiscoveryDemoApplication application, final ExternalCall externalCall, final ExternalCallReceiver externalCallReceiver) {

		try {

			TaskQueueHandle taskQueueHandle = application.getTaskQueueHandle();

			application.enqueueTask(taskQueueHandle, new Runnable() {

				public void run() {

					if (log.isDebugEnabled()) log.debug("RUN EXTERNAL CALL (APPLICATION): " + externalCall + " ON " + externalCallReceiver.getClass().getName());

					externalCallReceiver.onExternalCallApplication(application, externalCall);
				}
			});
		} catch (Exception ex) {

			log.error(ex.getMessage(), ex);
		} finally {

			/*try {

				if (! response.isCommitted()) response.sendRedirect("/");
			} catch (IOException ex) {

				log.error(ex.getMessage(), ex);
			}*/
		}
	}

	public static void onExternalCallRaw(final DiscoveryDemoApplication application, final ExternalCall externalCall, final ExternalCallReceiver externalCallReceiver, final HttpServletRequest request, final HttpServletResponse response) {

		if (log.isDebugEnabled()) log.debug("RUN EXTERNAL CALL (RAW): " + externalCall + " ON " + externalCallReceiver.getClass().getName());

		externalCallReceiver.onExternalCallRaw(application, request, response);
	}

	public Properties getProperties() {

		return this.properties;
	}

	public void setProperties(Properties properties) {

		this.properties = properties;
	}

	public EppTools getEppTools() {

		return this.eppTools;
	}

	public void setEppTools(EppTools eppTools) {

		this.eppTools = eppTools;
	}

	public ibrokerkit.ibrokerstore.store.Store getIbrokerStore() {

		return this.ibrokerStore;
	}

	public void setIbrokerStore(ibrokerkit.ibrokerstore.store.Store ibrokerStore) {

		this.ibrokerStore = ibrokerStore;
	}

	public ibrokerkit.iname4java.store.XriStore getXriStore() {

		return this.xriStore;
	}

	public void setXriStore(ibrokerkit.iname4java.store.XriStore xriStore) {

		this.xriStore = xriStore;
	}

	public Cache getCloudCache() {

		return this.cloudCache;
	}

	public void setCloudCache(Cache cloudCache) {

		this.cloudCache = cloudCache;
	}

	public Map<String, Object> getCloudCache2() {

		return this.cloudCache2;
	}

	public void setCloudCache2(Map<String, Object> cloudCache2) {

		this.cloudCache2 = cloudCache2;
	}
}

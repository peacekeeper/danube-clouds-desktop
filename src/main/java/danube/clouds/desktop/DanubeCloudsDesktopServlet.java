package danube.clouds.desktop;


import ibrokerkit.epptools4java.EppTools;

import java.io.IOException;
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

import danube.clouds.desktop.external.ExternalCall;
import danube.clouds.desktop.external.ExternalCallReceiver;
import danube.clouds.desktop.ui.MainWindow;

public class DanubeCloudsDesktopServlet extends WebContainerServlet {

	private static final long serialVersionUID = -7856586634363745175L;

	private static final Logger log = LoggerFactory.getLogger(DanubeCloudsDesktopServlet.class);

	private Properties properties;
	private EppTools eppTools;
	private ibrokerkit.iname4java.store.XriStore xriStore;
	private ibrokerkit.ibrokerstore.store.Store ibrokerStore;
	private Cache cloudCache = CacheManager.create(DanubeCloudsDesktopServlet.class.getResourceAsStream("ehcache.xml")).getCache("DanubeCloudsDesktopServlet_CLOUD_CACHE");

	@Override
	public ApplicationInstance newApplicationInstance() {

		return new DanubeCloudsDesktopApplication(this);
	}

	public void init(ServletConfig servletConfig) throws ServletException {

		super.init(servletConfig);

		log.info("Initializing...");

		this.addInitScript(JavaScriptService.forResource("CustomWaitIndicator", "danube/clouds/desktop/resource/js/CustomWaitIndicator.js"));
		this.addInitStyleSheet(StaticTextService.forResource("desktop.css", "text/css", "danube/clouds/desktop/resource/style/desktop.css"));

		log.info("Initializing complete.");
	}

	@Override
	protected void process(final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException {

		final DanubeCloudsDesktopApplication application = DanubeCloudsDesktopApplication.getAppFromSession(request.getSession());
		Stack<ExternalCall> stack = ExternalCall.getStackFromSession(request.getSession());
		ExternalCall externalCall = null;

		// check for new external call

		if ((externalCall = ExternalCall.fromRequest(request)) != null) {

			stack.push(externalCall);
			if (log.isDebugEnabled()) log.debug("PUSH EXTERNAL CALL: " + externalCall);

			ExternalCallReceiver externalCallReceiver = findExternalCallReceiver(application, externalCall);

			if (application != null && externalCallReceiver != null) {

				onExternalCallRaw(application, externalCall, externalCallReceiver);
			}

			response.sendRedirect("/");
			return;
		}

		// check for external call on stack

		if (application != null && stack != null && ! stack.isEmpty()) {

			while (! stack.isEmpty()) {

				externalCall = stack.pop();
				if (log.isDebugEnabled()) log.debug("POP EXTERNAL CALL: " + externalCall);

				ExternalCallReceiver externalCallReceiver = findExternalCallReceiver(application, externalCall);

				if (externalCallReceiver != null) {

					onExternalCallApplication(application, externalCall, externalCallReceiver);
				}
			}
		}

		// process the request

		super.process(request, response);
	}

	public static ExternalCallReceiver findExternalCallReceiver(DanubeCloudsDesktopApplication application, ExternalCall externalCall) {

		if (application == null) return null;

		MainWindow mainWindow = application.getMainWindow();
		if (mainWindow == null) return null;

		ExternalCallReceiver externalCallReceiver;
		externalCallReceiver = (ExternalCallReceiver) MainWindow.findChildComponentByClassName(mainWindow, externalCall.getPath());
		if (externalCallReceiver == null) externalCallReceiver = (ExternalCallReceiver) MainWindow.findChildComponentById(mainWindow, externalCall.getPath());
		if (externalCallReceiver == null) externalCallReceiver = (ExternalCallReceiver) MainWindow.findChildComponentByRenderId(mainWindow, externalCall.getPath());

		return externalCallReceiver;
	}

	public static void onExternalCallApplication(final DanubeCloudsDesktopApplication application, final ExternalCall externalCall, final ExternalCallReceiver externalCallReceiver) {

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
		}
	}

	public static void onExternalCallRaw(final DanubeCloudsDesktopApplication application, final ExternalCall externalCall, final ExternalCallReceiver externalCallReceiver) {

		try {

			TaskQueueHandle taskQueueHandle = application.getTaskQueueHandle();

			application.enqueueTask(taskQueueHandle, new Runnable() {

				public void run() {

					if (log.isDebugEnabled()) log.debug("RUN EXTERNAL CALL (RAW): " + externalCall + " ON " + externalCallReceiver.getClass().getName());

					externalCallReceiver.onExternalCallRaw(application, externalCall);
				}
			});
		} catch (Exception ex) {

			log.error(ex.getMessage(), ex);
		}
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
}

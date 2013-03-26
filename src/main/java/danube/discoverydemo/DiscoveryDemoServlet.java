package danube.discoverydemo;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;
import nextapp.echo.webcontainer.service.StaticTextService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiscoveryDemoServlet extends WebContainerServlet {

	private static final long serialVersionUID = -7856586634363745175L;

	private static final Logger log = LoggerFactory.getLogger(DiscoveryDemoServlet.class);

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
}

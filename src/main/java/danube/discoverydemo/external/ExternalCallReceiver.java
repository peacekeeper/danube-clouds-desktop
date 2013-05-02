package danube.discoverydemo.external;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import danube.discoverydemo.DiscoveryDemoApplication;

public interface ExternalCallReceiver {

	public void onExternalCallRaw(DiscoveryDemoApplication application, HttpServletRequest request, HttpServletResponse response);
	public void onExternalCallApplication(DiscoveryDemoApplication application, ExternalCall externalCall);
}

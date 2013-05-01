package danube.discoverydemo.servlet.external;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import danube.discoverydemo.DiscoveryDemoApplication;

public interface ExternalCallReceiver {

	public void onExternalCall(DiscoveryDemoApplication application, HttpServletRequest request, HttpServletResponse response);
}

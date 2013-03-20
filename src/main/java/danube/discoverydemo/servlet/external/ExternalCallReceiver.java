package danube.discoverydemo.servlet.external;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import danube.discoverydemo.DiscoveryDemoApplication;

public interface ExternalCallReceiver {

	public void onExternalCall(DiscoveryDemoApplication pdsApplication, HttpServletRequest request, HttpServletResponse response) throws IOException;
}

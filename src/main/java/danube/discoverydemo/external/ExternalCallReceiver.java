package danube.discoverydemo.external;

import danube.discoverydemo.DiscoveryDemoApplication;

public interface ExternalCallReceiver {

	public void onExternalCallRaw(DiscoveryDemoApplication application, ExternalCall externalCall);
	public void onExternalCallApplication(DiscoveryDemoApplication application, ExternalCall externalCall);
}

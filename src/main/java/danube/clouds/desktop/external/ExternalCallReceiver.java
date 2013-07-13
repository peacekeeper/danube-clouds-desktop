package danube.clouds.desktop.external;

import danube.clouds.desktop.DanubeCloudsDesktopApplication;

public interface ExternalCallReceiver {

	public void onExternalCallRaw(DanubeCloudsDesktopApplication application, ExternalCall externalCall);
	public void onExternalCallApplication(DanubeCloudsDesktopApplication application, ExternalCall externalCall);
}

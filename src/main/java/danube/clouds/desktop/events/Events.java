package danube.clouds.desktop.events;

import java.util.ArrayList;
import java.util.List;

import xdi2.client.XDIClientListener;
import xdi2.client.events.XDIDiscoverEvent;
import xdi2.client.events.XDISendEvent;

public class Events implements XDIClientListener {

	private final List<ApplicationListener> applicationListeners;
	private List<LogListener> logListeners;
	private final List<XDIClientListener> clientListeners;

	public Events() {

		this.applicationListeners = new ArrayList<ApplicationListener> ();
		this.logListeners = new ArrayList<LogListener> ();
		this.clientListeners = new ArrayList<XDIClientListener> ();
	}

	/*
	 * Application events
	 */

	public void addApplicationListener(ApplicationListener applicationListener) {

		if (this.applicationListeners.contains(applicationListener)) return;
		this.applicationListeners.add(applicationListener);
	}

	public void removeApplicationListener(ApplicationListener applicationListener) {

		this.applicationListeners.remove(applicationListener);
	}

	public void fireApplicationEvent(ApplicationEvent applicationEvent) {

		for (ApplicationListener applicationListener : this.applicationListeners) applicationListener.onApplicationEvent(applicationEvent);
	}

	/*
	 * Log events
	 */

	public void addLogListener(LogListener logListener) {

		if (this.logListeners.contains(logListener)) return;
		this.logListeners.add(logListener);
	}

	public void removeLogListener(LogListener logListener) {

		this.logListeners.remove(logListener);
	}

	public void fireLogEvent(LogEvent logEvent) {

		for (LogListener logListener : this.logListeners) logListener.onLog(logEvent);
	}

	/*
	 * XDI events
	 */

	public void addClientListener(XDIClientListener xdiClientListener) {

		if (this.clientListeners.contains(xdiClientListener)) return;
		this.clientListeners.add(xdiClientListener);
	}

	public void removeClientListener(XDIClientListener xdiClientListener) {

		this.clientListeners.remove(xdiClientListener);
	}

	public void fireSendEvent(XDISendEvent sendEvent) {

		for (XDIClientListener clientListener : this.clientListeners) clientListener.onSend(sendEvent);
	}

	public void fireXDIDiscoverEvent(XDIDiscoverEvent discoverEvent) {

		for (XDIClientListener clientListener : this.clientListeners) clientListener.onDiscover(discoverEvent);
	}

	@Override
	public void onSend(XDISendEvent sendEvent) {

		this.fireSendEvent(sendEvent);
	}

	@Override
	public void onDiscover(XDIDiscoverEvent discoverEvent) {

		this.fireXDIDiscoverEvent(discoverEvent);
	}
}

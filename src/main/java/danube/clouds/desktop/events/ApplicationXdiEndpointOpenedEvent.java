package danube.clouds.desktop.events;

import danube.clouds.desktop.xdi.XdiEndpoint;

public class ApplicationXdiEndpointOpenedEvent extends ApplicationEvent {

	private static final long serialVersionUID = -7396499183586044715L;

	private XdiEndpoint xdiEndpoint;

	public ApplicationXdiEndpointOpenedEvent(Object source, XdiEndpoint xdiEndpoint) {

		super(source);

		this.xdiEndpoint = xdiEndpoint;
	}

	public XdiEndpoint getXdiEndpoint() {

		return this.xdiEndpoint;
	}
}

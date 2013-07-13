package danube.clouds.desktop.events;

import danube.clouds.desktop.xdi.XdiEndpoint;

public class ApplicationXdiEndpointClosedEvent extends ApplicationEvent {

	private static final long serialVersionUID = -2741247681465378631L;

	private XdiEndpoint xdiEndpoint;

	public ApplicationXdiEndpointClosedEvent(Object source, XdiEndpoint xdiEndpoint) {

		super(source);

		this.xdiEndpoint = xdiEndpoint;
	}

	public XdiEndpoint getXdiEndpoint() {

		return this.xdiEndpoint;
	}
}

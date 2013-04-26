package danube.discoverydemo.xdi.events;

import xdi2.discovery.XDIDiscoveryResult;

public class XdiDiscoveryXdiEndpointEvent extends XdiDiscoveryEvent {

	private static final long serialVersionUID = 2394795056513289889L;

	private String xdiEndpoint;

	public XdiDiscoveryXdiEndpointEvent(Object source, String xdiEndpoint, XDIDiscoveryResult xdiDiscoveryResult) {

		super(source, xdiDiscoveryResult);

		this.xdiEndpoint = xdiEndpoint;
	}

	public String getXdiEndpoint() {

		return this.xdiEndpoint;
	}

	public void setXdiEndpoint(String cloudName) {

		this.xdiEndpoint = cloudName;
	}
}

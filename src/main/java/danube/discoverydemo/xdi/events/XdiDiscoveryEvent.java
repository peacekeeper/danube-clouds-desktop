package danube.discoverydemo.xdi.events;

import xdi2.discovery.XDIDiscoveryResult;

public abstract class XdiDiscoveryEvent extends XdiEvent {

	private static final long serialVersionUID = -9221664294927183588L;

	private XDIDiscoveryResult xdiDiscoveryResult;

	public XdiDiscoveryEvent(Object source, XDIDiscoveryResult xdiDiscoveryResult) {

		super(source);

		this.xdiDiscoveryResult = xdiDiscoveryResult;
	}

	public XDIDiscoveryResult getXdiDiscoveryResult() {

		return this.xdiDiscoveryResult;
	}

	public void setXdiDiscoveryResult(XDIDiscoveryResult xdiDiscoveryResult) {

		this.xdiDiscoveryResult = xdiDiscoveryResult;
	}
}

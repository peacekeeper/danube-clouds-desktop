package danube.discoverydemo.parties.impl;

import xdi2.core.xri3.XDI3Segment;
import danube.discoverydemo.parties.RemoteParty;
import danube.discoverydemo.xdi.XdiEndpoint;

public abstract class AbstractRemoteParty extends AbstractParty implements RemoteParty {

	private XdiEndpoint xdiEndpoint;

	public AbstractRemoteParty(XdiEndpoint xdiEndpoint) {

		super(null);

		this.xdiEndpoint = xdiEndpoint;
	}

	@Override
	public XdiEndpoint getXdiEndpoint() {

		return this.xdiEndpoint;
	}

	@Override
	public XDI3Segment getCloudNumber() {

		return this.getXdiEndpoint().getCloudNumber();
	}
}

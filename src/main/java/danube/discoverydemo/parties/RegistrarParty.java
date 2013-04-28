package danube.discoverydemo.parties;

import danube.discoverydemo.xdi.XdiEndpoint;

public class RegistrarParty {

	private XdiEndpoint xdiEndpoint;

	private RegistrarParty(XdiEndpoint xdiEndpoint) {

		this.xdiEndpoint = xdiEndpoint;
	}

	public static RegistrarParty create() {

		return new RegistrarParty(null);
	}

	public XdiEndpoint getXdiEndpoint() {

		return this.xdiEndpoint;
	}
}

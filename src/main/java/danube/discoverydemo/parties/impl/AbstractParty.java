package danube.discoverydemo.parties.impl;

import danube.discoverydemo.parties.Party;
import danube.discoverydemo.xdi.XdiEndpoint;

public class AbstractParty implements Party {

	private XdiEndpoint xdiEndpoint;

	public AbstractParty(XdiEndpoint xdiEndpoint) {

		this.xdiEndpoint = xdiEndpoint;
	}

	@Override
	public XdiEndpoint getXdiEndpoint() {

		return this.xdiEndpoint;
	}
}

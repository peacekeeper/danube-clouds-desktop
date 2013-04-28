package danube.discoverydemo.parties.impl;

import xdi2.core.xri3.XDI3Segment;
import danube.discoverydemo.parties.Party;

public abstract class AbstractParty implements Party {

	private XDI3Segment cloudNumber;

	public AbstractParty(XDI3Segment cloudNumber) {

		this.cloudNumber = cloudNumber;
	}

	public XDI3Segment getCloudNumber() {

		return this.cloudNumber;
	}
}

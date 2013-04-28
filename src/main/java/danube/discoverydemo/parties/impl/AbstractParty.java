package danube.discoverydemo.parties.impl;

import xdi2.core.xri3.XDI3Segment;
import danube.discoverydemo.parties.Party;

public abstract class AbstractParty implements Party {

	private String name;
	private XDI3Segment cloudNumber;

	public AbstractParty(String name, XDI3Segment cloudNumber) {

		this.name = name;
		this.cloudNumber = cloudNumber;
	}

	public String getName() {

		return this.name;
	}

	public XDI3Segment getCloudNumber() {

		return this.cloudNumber;
	}

	/*
	 * Object methods
	 */

	@Override
	public String toString() {

		return this.getName();
	}
}

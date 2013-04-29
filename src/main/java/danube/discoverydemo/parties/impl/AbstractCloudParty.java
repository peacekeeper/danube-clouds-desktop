package danube.discoverydemo.parties.impl;

import danube.discoverydemo.parties.CloudParty;
import danube.discoverydemo.xdi.XdiEndpoint;

public abstract class AbstractCloudParty extends AbstractRemoteParty implements CloudParty {

	public AbstractCloudParty(String name, XdiEndpoint xdiEndpoint) {

		super(name, xdiEndpoint);
	}
}

package danube.clouds.desktop.parties.impl;

import danube.clouds.desktop.parties.CloudParty;
import danube.clouds.desktop.xdi.XdiEndpoint;

public abstract class AbstractCloudParty extends AbstractRemoteParty implements CloudParty {

	public AbstractCloudParty(String name, XdiEndpoint xdiEndpoint) {

		super(name, xdiEndpoint);
	}
}

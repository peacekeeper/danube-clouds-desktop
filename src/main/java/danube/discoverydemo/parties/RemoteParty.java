package danube.discoverydemo.parties;

import danube.discoverydemo.xdi.XdiEndpoint;

public interface RemoteParty extends Party {

	public XdiEndpoint getXdiEndpoint();
}

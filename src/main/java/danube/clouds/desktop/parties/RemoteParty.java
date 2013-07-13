package danube.clouds.desktop.parties;

import danube.clouds.desktop.xdi.XdiEndpoint;

public interface RemoteParty extends Party {

	public XdiEndpoint getXdiEndpoint();
}

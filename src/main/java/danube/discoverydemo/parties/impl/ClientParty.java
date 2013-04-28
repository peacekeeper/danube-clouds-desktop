package danube.discoverydemo.parties.impl;

import xdi2.core.xri3.XDI3Segment;
import xdi2.messaging.constants.XDIMessagingConstants;
import danube.discoverydemo.parties.Party;

public class ClientParty extends AbstractParty implements Party {

	public ClientParty(XDI3Segment cloudNumber) {

		super(cloudNumber);
	}

	public static ClientParty create() {

		return new ClientParty(XDIMessagingConstants.XRI_S_ANONYMOUS);
	}
}

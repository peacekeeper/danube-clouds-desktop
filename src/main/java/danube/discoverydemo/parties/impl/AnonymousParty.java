package danube.discoverydemo.parties.impl;

import xdi2.core.xri3.XDI3Segment;
import xdi2.messaging.constants.XDIMessagingConstants;
import danube.discoverydemo.parties.Party;

public class AnonymousParty extends AbstractParty implements Party {

	public AnonymousParty(XDI3Segment cloudNumber) {

		super("Anonymous", cloudNumber);
	}

	public static AnonymousParty create() {

		return new AnonymousParty(XDIMessagingConstants.XRI_S_ANONYMOUS);
	}
}

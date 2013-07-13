package danube.clouds.desktop.parties.impl;

import xdi2.core.xri3.XDI3Segment;
import xdi2.messaging.constants.XDIMessagingConstants;
import danube.clouds.desktop.parties.Party;

public class AnonymousParty extends AbstractParty implements Party {

	public AnonymousParty(XDI3Segment cloudNumber) {

		super("Anonymous", cloudNumber);
	}

	public static AnonymousParty create() {

		return new AnonymousParty(XDIMessagingConstants.XRI_S_ANONYMOUS);
	}
}

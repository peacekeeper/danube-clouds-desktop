package danube.discoverydemo.parties.impl;

import xdi2.client.exceptions.Xdi2ClientException;
import xdi2.core.constants.XDIPolicyConstants;
import xdi2.core.exceptions.Xdi2RuntimeException;
import xdi2.core.xri3.XDI3Segment;
import xdi2.messaging.Message;
import xdi2.messaging.MessageResult;
import xdi2.messaging.constants.XDIMessagingConstants;
import danube.discoverydemo.parties.Party;
import danube.discoverydemo.parties.RemoteParty;
import danube.discoverydemo.xdi.XdiEndpoint;

public abstract class AbstractRemoteParty extends AbstractParty implements RemoteParty {

	private XdiEndpoint xdiEndpoint;

	public AbstractRemoteParty(XdiEndpoint xdiEndpoint) {

		super(null);

		this.xdiEndpoint = xdiEndpoint;
	}

	@Override
	public XdiEndpoint getXdiEndpoint() {

		return this.xdiEndpoint;
	}

	@Override
	public XDI3Segment getCloudNumber() {

		return this.getXdiEndpoint().getCloudNumber();
	}

	public void checkSecretToken(Party fromParty) throws Xdi2ClientException {

		// $get

		Message message = this.getXdiEndpoint().prepareOperation(fromParty.getCloudNumber(), XDIMessagingConstants.XRI_S_GET, XDIPolicyConstants.XRI_S_SECRET_TOKEN);
		MessageResult messageResult = this.getXdiEndpoint().send(message);

		if (messageResult.isEmpty()) throw new Xdi2RuntimeException("Incorrect secret token.");
	}
}

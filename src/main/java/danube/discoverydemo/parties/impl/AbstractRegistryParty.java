package danube.discoverydemo.parties.impl;

import xdi2.client.exceptions.Xdi2ClientException;
import xdi2.client.http.XDIHttpClient;
import xdi2.core.constants.XDIConstants;
import xdi2.core.constants.XDIDictionaryConstants;
import xdi2.core.exceptions.Xdi2RuntimeException;
import xdi2.core.features.roots.XdiPeerRoot;
import xdi2.core.util.StatementUtil;
import xdi2.core.xri3.XDI3Segment;
import xdi2.discovery.XDIDiscoveryResult;
import xdi2.messaging.Message;
import xdi2.messaging.MessageResult;
import danube.discoverydemo.DiscoveryDemoApplication;
import danube.discoverydemo.parties.Party;
import danube.discoverydemo.parties.RegistryParty;
import danube.discoverydemo.xdi.XdiEndpoint;

public abstract class AbstractRegistryParty extends AbstractRemoteParty implements RegistryParty {

	public AbstractRegistryParty(String name, XdiEndpoint xdiEndpoint) {

		super(name, xdiEndpoint);
	}

	@Override
	public XDIDiscoveryResult discoverFromXri(Party fromParty, XDI3Segment xri) throws Xdi2ClientException {

		// assemble message

		Message message = this.getXdiEndpoint().prepareMessage(fromParty.getCloudNumber());

		message.createGetOperation(XDI3Segment.create(XdiPeerRoot.createPeerRootArcXri(xri)));

		// send it

		MessageResult messageResult = this.getXdiEndpoint().send(message);

		XDIDiscoveryResult discoveryResult = XDIDiscoveryResult.fromXriAndMessageResult(xri, messageResult);
		if (discoveryResult == null) throw new Xdi2RuntimeException("No XDI discovery result.");
		if (discoveryResult.getCloudNumber() == null) throw new Xdi2RuntimeException("No XDI Cloud Number.");
		if (discoveryResult.getEndpointUri() == null) throw new Xdi2RuntimeException("No XDI endpoint URI.");

		return discoveryResult;
	}

	@Override
	public XDIDiscoveryResult discoverFromEndpointUri(Party fromParty, String endpointUri) throws Xdi2ClientException {

		AnonymousParty anonymousParty = DiscoveryDemoApplication.getApp().getAnonymousParty();

		// temp party

		XDIHttpClient tempXdiClient = new XDIHttpClient(endpointUri);
		tempXdiClient.addClientListener(DiscoveryDemoApplication.getApp().getEvents());

		XdiEndpoint tempXdiEndpoint = new XdiEndpoint(tempXdiClient, anonymousParty.getCloudNumber(), anonymousParty.getCloudNumber(), null);

		// assemble message

		Message message = tempXdiEndpoint.prepareMessage(fromParty.getCloudNumber());

		message.createGetOperation(StatementUtil.fromComponents(XDIConstants.XRI_S_ROOT, XDIDictionaryConstants.XRI_S_IS_REF, XDIConstants.XRI_S_VARIABLE));

		// send it

		MessageResult messageResult = tempXdiEndpoint.send(message);

		XDI3Segment xri = messageResult.getGraph().getDeepRelation(XDIConstants.XRI_S_ROOT, XDIDictionaryConstants.XRI_S_IS_REF).getTargetContextNodeXri();

		XDIDiscoveryResult discoveryResult = XDIDiscoveryResult.fromXriAndMessageResult(xri, messageResult);
		if (discoveryResult == null) throw new Xdi2RuntimeException("No XDI discovery result.");
		if (discoveryResult.getCloudNumber() == null) throw new Xdi2RuntimeException("No XDI Cloud Number.");
		if (discoveryResult.getEndpointUri() == null) throw new Xdi2RuntimeException("No XDI endpoint URI.");

		return discoveryResult;
	}
}

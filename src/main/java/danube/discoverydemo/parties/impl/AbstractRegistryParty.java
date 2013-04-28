package danube.discoverydemo.parties.impl;

import xdi2.client.exceptions.Xdi2ClientException;
import xdi2.core.constants.XDIConstants;
import xdi2.core.constants.XDIDictionaryConstants;
import xdi2.core.util.StatementUtil;
import xdi2.core.xri3.XDI3Segment;
import xdi2.discovery.XDIDiscoveryResult;
import xdi2.messaging.Message;
import xdi2.messaging.MessageResult;
import danube.discoverydemo.parties.RegistryParty;
import danube.discoverydemo.ui.MessageDialog;
import danube.discoverydemo.xdi.XdiEndpoint;

public abstract class AbstractRegistryParty extends AbstractParty implements RegistryParty {

	public AbstractRegistryParty(XdiEndpoint xdiEndpoint) {

		super(xdiEndpoint);
	}

	public XDIDiscoveryResult discoverFromXri(XDI3Segment xri) throws Xdi2ClientException {

		// assemble message

		Message message = this.getXdiEndpoint().prepareMessage(this.getXdiEndpoint());

		message.createGetOperation(xri);

		// send it

		MessageResult messageResult = this.getXdiEndpoint().send(message);

		return XDIDiscoveryResult.fromXriAndMessageResult(xri, messageResult);
	}

	public XDIDiscoveryResult discoverFromEndpointUri(String endpointUri) throws Xdi2ClientException {

		// assemble message

		Message message = this.getXdiEndpoint().prepareMessage(this.getXdiEndpoint());

		message.createGetOperation(StatementUtil.fromComponents(XDIConstants.XRI_S_ROOT, XDIDictionaryConstants.XRI_S_IS_REF, XDIConstants.XRI_S_VARIABLE));

		// send it

		MessageResult messageResult = this.getXdiEndpoint().send(message);

		XDI3Segment xri = messageResult.getGraph().getDeepRelation(XDIConstants.XRI_S_ROOT, XDIDictionaryConstants.XRI_S_IS_REF).getTargetContextNodeXri();

		return XDIDiscoveryResult.fromXriAndMessageResult(xri, messageResult);
	}
}

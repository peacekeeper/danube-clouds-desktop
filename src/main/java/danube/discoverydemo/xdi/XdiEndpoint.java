package danube.discoverydemo.xdi;

import java.util.Date;
import java.util.Iterator;

import xdi2.client.XDIClient;
import xdi2.client.exceptions.Xdi2ClientException;
import xdi2.core.constants.XDILinkContractConstants;
import xdi2.core.constants.XDIPolicyConstants;
import xdi2.core.exceptions.Xdi2RuntimeException;
import xdi2.core.features.roots.XdiPeerRoot;
import xdi2.core.xri3.XDI3Segment;
import xdi2.core.xri3.XDI3Statement;
import xdi2.messaging.Message;
import xdi2.messaging.MessageEnvelope;
import xdi2.messaging.MessageResult;
import xdi2.messaging.Operation;
import xdi2.messaging.constants.XDIMessagingConstants;

public class XdiEndpoint {

	private final XDIClient xdiClient;
	private final XDI3Segment xri;
	private final XDI3Segment cloudNumber;
	private final String secretToken;

	public XdiEndpoint(XDIClient xdiClient, XDI3Segment xri, XDI3Segment cloudNumber, String secretToken) { 

		this.xdiClient = xdiClient;
		this.xri = xri;
		this.cloudNumber = cloudNumber;
		this.secretToken = secretToken;
	}

	public XDIClient getXdiClient() {

		return this.xdiClient;
	}

	public XDI3Segment getXri() {

		return this.xri;
	}

	public XDI3Segment getCloudNumber() {

		return this.cloudNumber;
	}

	public String getSecretToken() {

		return this.secretToken;
	}

	public void checkSecretToken(XdiEndpoint fromXdiEndpoint) throws Xdi2ClientException {

		// $get

		Message message = this.prepareOperation(fromXdiEndpoint, XDIMessagingConstants.XRI_S_GET, XDIPolicyConstants.XRI_S_SECRET_TOKEN);
		MessageResult messageResult = this.send(message);

		if (messageResult.isEmpty()) throw new Xdi2RuntimeException("Incorrect password.");
	}

	/* 
	 * Messaging methods
	 */

	public Message prepareMessage(XdiEndpoint fromXdiEndpoint) {

		if (fromXdiEndpoint == null) fromXdiEndpoint = this;
		
		XDI3Segment senderXri = fromXdiEndpoint.getCloudNumber();

		MessageEnvelope messageEnvelope = new MessageEnvelope();
		Message message = messageEnvelope.getMessage(senderXri, true);

		message.setTimestamp(new Date());

		message.getContextNode().createRelation(XDILinkContractConstants.XRI_S_DO, XDILinkContractConstants.XRI_S_DO);

		if (fromXdiEndpoint != null && fromXdiEndpoint.getCloudNumber() != null) {

			message.setFromAddress(XDI3Segment.create(XdiPeerRoot.createPeerRootArcXri(fromXdiEndpoint.getCloudNumber())));
		}

		if (this.getCloudNumber() != null) {

			message.setToAddress(XDI3Segment.create(XdiPeerRoot.createPeerRootArcXri(this.getCloudNumber())));
		}

		if (this.getSecretToken() != null) {

			message.getContextNode().setDeepLiteral(XDIPolicyConstants.XRI_S_SECRET_TOKEN, this.getSecretToken());
		}

		return message;
	}

	public Message prepareOperation(XdiEndpoint fromXdiEndpoint, XDI3Segment operationXri, XDI3Segment targetXri) {

		Message message = this.prepareMessage(fromXdiEndpoint);

		message.createOperation(operationXri, targetXri);

		return message;
	}

	public Message prepareOperation(XdiEndpoint fromXdiEndpoint, XDI3Segment operationXri, Iterator<XDI3Statement> targetStatements) {

		Message message = this.prepareMessage(fromXdiEndpoint);

		message.createOperation(operationXri, targetStatements);

		return message;
	}

	public Message prepareOperation(XdiEndpoint fromXdiEndpoint, XDI3Segment operationXri, XDI3Statement targetStatement) {

		Message message = this.prepareMessage(fromXdiEndpoint);

		message.createOperation(operationXri, targetStatement);

		return message;
	}

	/*
	 * Sending methods
	 */

	public MessageResult send(Operation operation) throws Xdi2ClientException {

		return this.send(operation.getMessage());
	}

	public MessageResult send(Message message) throws Xdi2ClientException {

		return this.send(message.getMessageEnvelope());
	}

	public MessageResult send(MessageEnvelope messageEnvelope) throws Xdi2ClientException {

		return this.getXdiClient().send(messageEnvelope, null);
	}
}
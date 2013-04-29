package danube.discoverydemo.xdi;

import java.util.Date;
import java.util.Iterator;

import xdi2.client.XDIClient;
import xdi2.client.exceptions.Xdi2ClientException;
import xdi2.core.constants.XDILinkContractConstants;
import xdi2.core.constants.XDIPolicyConstants;
import xdi2.core.features.roots.XdiPeerRoot;
import xdi2.core.xri3.XDI3Segment;
import xdi2.core.xri3.XDI3Statement;
import xdi2.messaging.Message;
import xdi2.messaging.MessageEnvelope;
import xdi2.messaging.MessageResult;
import xdi2.messaging.Operation;

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

	/* 
	 * Messaging methods
	 */

	public Message prepareMessage(XDI3Segment fromCloudNumber) {

		if (fromCloudNumber == null) fromCloudNumber = this.getCloudNumber();

		MessageEnvelope messageEnvelope = new MessageEnvelope();
		Message message = messageEnvelope.getMessage(fromCloudNumber, true);

		message.setTimestamp(new Date());
		message.setFromAddress(XDI3Segment.create(XdiPeerRoot.createPeerRootArcXri(fromCloudNumber)));
		message.setToAddress(XDI3Segment.create(XdiPeerRoot.createPeerRootArcXri(this.getCloudNumber())));
		message.setLinkContractXri(XDILinkContractConstants.XRI_S_DO);

		if (this.getSecretToken() != null) {

			message.getContextNode().setDeepLiteral(XDIPolicyConstants.XRI_S_SECRET_TOKEN, this.getSecretToken());
		}

		return message;
	}

	public Message prepareOperation(XDI3Segment fromCloudNumber, XDI3Segment operationXri, XDI3Segment targetXri) {

		Message message = this.prepareMessage(fromCloudNumber);

		message.createOperation(operationXri, targetXri);

		return message;
	}

	public Message prepareOperation(XDI3Segment fromCloudNumber, XDI3Segment operationXri, Iterator<XDI3Statement> targetStatements) {

		Message message = this.prepareMessage(fromCloudNumber);

		message.createOperation(operationXri, targetStatements);

		return message;
	}

	public Message prepareOperation(XDI3Segment fromCloudNumber, XDI3Segment operationXri, XDI3Statement targetStatement) {

		Message message = this.prepareMessage(fromCloudNumber);

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
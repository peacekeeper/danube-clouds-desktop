package danube.discoverydemo.xdi;

import java.util.Date;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xdi2.client.XDIClient;
import xdi2.client.exceptions.Xdi2ClientException;
import xdi2.core.ContextNode;
import xdi2.core.constants.XDILinkContractConstants;
import xdi2.core.features.roots.PeerRoot;
import xdi2.core.xri3.XDI3Segment;
import xdi2.core.xri3.XDI3Statement;
import xdi2.messaging.Message;
import xdi2.messaging.MessageEnvelope;
import xdi2.messaging.MessageResult;
import xdi2.messaging.Operation;
import xdi2.messaging.constants.XDIMessagingConstants;
import danube.discoverydemo.xdi.events.XdiTransactionEvent;
import danube.discoverydemo.xdi.events.XdiTransactionFailureEvent;
import danube.discoverydemo.xdi.events.XdiTransactionSuccessEvent;

public class XdiEndpoint {

	private static final Logger log = LoggerFactory.getLogger(XdiEndpoint.class.getName());

	private final Xdi xdi;
	private final XDIClient xdiClient;
	private final String identifier;
	private final XDI3Segment canonical;
	private final String secretToken;

	XdiEndpoint(Xdi xdi, XDIClient xdiClient, String identifier, XDI3Segment canonical, String secretToken) { 

		this.xdi = xdi;
		this.xdiClient = xdiClient;
		this.identifier = identifier;
		this.canonical = canonical;
		this.secretToken = secretToken;
	}

	public Xdi getXdi() {

		return this.xdi;
	}

	public XDIClient getXdiClient() {

		return this.xdiClient;
	}

	public String getIdentifier() {

		return this.identifier;
	}

	public XDI3Segment getCanonical() {

		return this.canonical;
	}

	public String getSecretToken() {

		return this.secretToken;
	}

	public void checkSecretToken() throws XdiException {

		// $get

		/* TODO		XRI3 operationAddress = new XRI3("" + this.canonical + "/$password");
		Operation operation = this.prepareOperation(XDIMessagingConstants.XRI_GET, operationAddress);
		MessageResult messageResult = this.send(operation);

		if (! Boolean.TRUE.equals(messageResult.getBoolean())) {

			throw new XdiException("Incorrect password.");
		}*/
	}

	public XdiTransactionEvent directXdi(MessageEnvelope messageEnvelope) throws XdiException {

		// do XDI transaction

		MessageResult messageResult = null;
		Date beginTimestamp = new Date();
		XdiTransactionEvent transactionEvent;

		try {

			messageResult = XdiEndpoint.this.xdiClient.send(messageEnvelope, null);

			transactionEvent = new XdiTransactionSuccessEvent(this, this, messageEnvelope, messageResult, beginTimestamp, new Date());
			this.xdi.fireXdiTransactionEvent(transactionEvent);
		} catch (Exception ex) {

			if (! (ex instanceof XdiException)) ex = new XdiException("Problem during XDI Transaction: " + ex.getMessage(), ex);
			transactionEvent = new XdiTransactionFailureEvent(this, this, messageEnvelope, messageResult, beginTimestamp, new Date(), ex);
			this.xdi.fireXdiTransactionEvent(transactionEvent);
		}

		return transactionEvent;
	}

	/* 
	 * Messaging methods
	 */

	public Message prepareMessage() {

		MessageEnvelope messageEnvelope = new MessageEnvelope();
		Message message = messageEnvelope.getMessage(this.canonical, true);

		message.getContextNode().createRelation(XDILinkContractConstants.XRI_S_DO, XDILinkContractConstants.XRI_S_DO);

		if (this.canonical != null) {

			message.setToAddress(XDI3Segment.create("" + PeerRoot.createPeerRootXri(this.canonical)));
		}

		if (this.secretToken != null) {

			ContextNode secretTokenContextNode = message.getContextNode().createContextNodes(XDIMessagingConstants.XRI_S_SECRET_TOKEN);
			secretTokenContextNode.createLiteral(this.secretToken);
		}

		return message;
	}

	public Message prepareOperation(XDI3Segment operationXri, XDI3Segment targetXri) {

		Message message = this.prepareMessage();

		message.createOperation(operationXri, targetXri);

		return message;
	}

	public Message prepareOperation(XDI3Segment operationXri, Iterator<XDI3Statement> targetStatements) {

		Message message = this.prepareMessage();

		message.createOperation(operationXri, targetStatements);

		return message;
	}

	public Message prepareOperation(XDI3Segment operationXri, XDI3Statement targetStatement) {

		Message message = this.prepareMessage();

		message.createOperation(operationXri, targetStatement);

		return message;
	}

	public Message prepareOperations(XDI3Segment operationXri, XDI3Segment[] targetXris) {

		Message message = this.prepareMessage();

		for (XDI3Segment targetXri : targetXris) {

			message.createOperation(operationXri, targetXri);
		}

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

		// send the message envelope

		MessageResult messageResult = this.xdi.send(this, messageEnvelope);

		// done

		return messageResult;
	}
}
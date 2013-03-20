package danube.discoverydemo.xdi.events;

import java.util.Date;

import danube.discoverydemo.xdi.XdiEndpoint;

import xdi2.messaging.MessageEnvelope;
import xdi2.messaging.MessageResult;

public class XdiTransactionSuccessEvent extends XdiTransactionEvent {

	private static final long serialVersionUID = -547735780296539623L;

	private MessageResult messageResult;

	public XdiTransactionSuccessEvent(Object source, XdiEndpoint endpoint, MessageEnvelope messageEnvelope, MessageResult messageResult, Date beginTimestamp, Date endTimestamp) {

		super(source, endpoint, messageEnvelope, messageResult, beginTimestamp, endTimestamp);

		this.messageResult = messageResult;
	}

	public MessageResult getMessageResult() {

		return this.messageResult;
	}
}

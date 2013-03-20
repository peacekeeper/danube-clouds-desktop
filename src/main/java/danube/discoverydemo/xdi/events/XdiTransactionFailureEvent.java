package danube.discoverydemo.xdi.events;

import java.util.Date;

import danube.discoverydemo.xdi.XdiEndpoint;

import xdi2.messaging.MessageEnvelope;
import xdi2.messaging.MessageResult;

public class XdiTransactionFailureEvent extends XdiTransactionEvent {

	private static final long serialVersionUID = -547735780296539623L;

	private Exception exception;

	public XdiTransactionFailureEvent(Object source, XdiEndpoint endpoint, MessageEnvelope messageEnvelope, MessageResult messageResult, Date beginTimestamp, Date endTimestamp, Exception exception) {

		super(source, endpoint, messageEnvelope, messageResult, beginTimestamp, endTimestamp);

		this.exception = exception;
	}

	public Exception getException() {

		return this.exception;
	}
}

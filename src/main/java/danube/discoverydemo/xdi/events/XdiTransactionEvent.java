package danube.discoverydemo.xdi.events;

import java.util.Date;

import xdi2.messaging.MessageEnvelope;
import xdi2.messaging.MessageResult;
import danube.discoverydemo.xdi.XdiEndpoint;

public class XdiTransactionEvent extends XdiEvent {

	private static final long serialVersionUID = 5301716219045375638L;

	private XdiEndpoint endpoint;
	private MessageEnvelope messageEnvelope;
	private MessageResult messageResult;
	private Date beginTimestamp;
	private Date endTimestamp;

	public XdiTransactionEvent(Object source, XdiEndpoint endpoint, MessageEnvelope messageEnvelope, MessageResult messageResult, Date beginTimestamp, Date endTimestamp) {

		super(source);

		this.endpoint = endpoint;
		this.messageEnvelope = messageEnvelope;
		this.messageResult = messageResult;
		this.beginTimestamp = beginTimestamp;
		this.endTimestamp = endTimestamp;
	}

	public XdiEndpoint getEndpoint() {

		return this.endpoint;
	}

	public void setEndpoint(XdiEndpoint endpoint) {

		this.endpoint = endpoint;
	}



	public MessageEnvelope getMessageEnvelope() {

		return this.messageEnvelope;
	}

	public MessageResult getMessageResult() {

		return this.messageResult;
	}

	public Date getBeginTimestamp() {

		return this.beginTimestamp;
	}

	public Date getEndTimestamp() {

		return this.endTimestamp;
	}
}

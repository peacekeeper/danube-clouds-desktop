package danube.discoverydemo.xdi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xdi2.client.XDIClient;
import xdi2.client.exceptions.Xdi2ClientException;
import xdi2.client.http.XDIHttpClient;
import xdi2.core.xri3.XDI3Segment;
import xdi2.discovery.XDIDiscovery;
import xdi2.discovery.XDIDiscoveryResult;
import xdi2.messaging.Message;
import xdi2.messaging.MessageEnvelope;
import xdi2.messaging.MessageResult;
import xdi2.messaging.Operation;
import danube.discoverydemo.xdi.events.XdiDiscoveryCloudNameEvent;
import danube.discoverydemo.xdi.events.XdiDiscoveryEvent;
import danube.discoverydemo.xdi.events.XdiListener;
import danube.discoverydemo.xdi.events.XdiTransactionEvent;
import danube.discoverydemo.xdi.events.XdiTransactionFailureEvent;
import danube.discoverydemo.xdi.events.XdiTransactionSuccessEvent;

/**
 * Support for sending XDI messages.
 */
public class Xdi {

	private static final Logger log = LoggerFactory.getLogger(Xdi.class.getName());

	private final List<XdiListener> xdiListeners;

	public Xdi() {

		this.xdiListeners = new ArrayList<XdiListener> ();
	}

	/*
	 * Endpoint methods
	 */

	public XdiEndpoint resolveEndpointManually(XDIClient xdiClient, String identifier, XDI3Segment canonical, String secretToken) {

		log.trace("resolveEndpointManually(" + xdiClient + "," + identifier + "," + canonical + ",XXX)");

		// instantiate endpoint

		XdiEndpoint endpoint = new XdiEndpoint(
				this, 
				xdiClient, 
				identifier, 
				canonical, 
				secretToken);

		// done

		return endpoint;
	}

	public XdiEndpoint resolveEndpointManually(String endpointUrl, String identifier, XDI3Segment canonical, String secretToken) {

		log.trace("resolveEndpointManually(" + endpointUrl + "," + identifier + "," + canonical + ",XXX)");

		// instantiate endpoint

		XdiEndpoint endpoint = new XdiEndpoint(
				this, 
				new XDIHttpClient(endpointUrl), 
				identifier, 
				canonical, 
				secretToken);

		// done

		return endpoint;
	}

	public XdiEndpoint resolveEndpointByCloudName(String cloudName, String secretToken) throws Xdi2ClientException {

		log.trace("resolveEndpointByCloudName(" + cloudName + ",XXX)");

		// discovery

		XDIDiscovery xdiDiscovery = new XDIDiscovery(new XDIHttpClient("http://mycloud.neustar.biz:12220/"));
		XDIDiscoveryResult xdiDiscoveryResult = xdiDiscovery.discover(cloudName);

		// instantiate endpoint

		XdiEndpoint endpoint = new XdiEndpoint(
				this, 
				new XDIHttpClient(xdiDiscoveryResult.getEndpointUri()), 
				cloudName, 
				xdiDiscoveryResult.getCloudNumber(), 
				secretToken);

		// fire event

		this.fireXdiDiscoveryEvent(new XdiDiscoveryCloudNameEvent(this, cloudName, xdiDiscoveryResult));

		// done

		return endpoint;
	}

	/*
	 * Sending methods
	 */

	public MessageResult send(XdiEndpoint endpoint, Operation operation) throws Xdi2ClientException {

		return this.send(endpoint, operation.getMessage());
	}

	public MessageResult send(XdiEndpoint endpoint, Message message) throws Xdi2ClientException {

		return this.send(endpoint, message.getMessageEnvelope());
	}

	public MessageResult send(XdiEndpoint endpoint, MessageEnvelope messageEnvelope) throws Xdi2ClientException {

		// send the message envelope

		MessageResult messageResult = null;
		Date beginTimestamp = new Date();

		try {

			messageResult = endpoint.getXdiClient().send(messageEnvelope, null);

			this.fireXdiTransactionEvent(new XdiTransactionSuccessEvent(this, endpoint, messageEnvelope, messageResult, beginTimestamp, new Date()));
		} catch (Xdi2ClientException ex) {

			messageResult = ((Xdi2ClientException) ex).getErrorMessageResult();

			this.fireXdiTransactionEvent(new XdiTransactionFailureEvent(this, endpoint, messageEnvelope, messageResult, beginTimestamp, new Date(), ex));
			throw ex;
		}

		// done

		return messageResult;
	}

	/*
	 * Listener methods
	 */

	public void addXdiListener(XdiListener xdiListener) {

		if (this.xdiListeners.contains(xdiListener)) return;
		this.xdiListeners.add(xdiListener);
	}

	public void removeXdiListener(XdiListener xdiListener) {

		this.xdiListeners.remove(xdiListener);
	}

	public void fireXdiTransactionEvent(XdiTransactionEvent xdiTransactionEvent) {

		for (XdiListener xdiListener : this.xdiListeners) xdiListener.onXdiTransaction(xdiTransactionEvent);
	}

	public void fireXdiDiscoveryEvent(XdiDiscoveryEvent xdiDiscoveryEvent) {

		for (XdiListener xdiListener : this.xdiListeners) xdiListener.onXdiDiscovery(xdiDiscoveryEvent);
	}
}

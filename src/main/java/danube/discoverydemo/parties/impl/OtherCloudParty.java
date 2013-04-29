package danube.discoverydemo.parties.impl;

import xdi2.client.http.XDIHttpClient;
import xdi2.core.xri3.XDI3Segment;
import danube.discoverydemo.DiscoveryDemoApplication;
import danube.discoverydemo.parties.Party;
import danube.discoverydemo.xdi.XdiEndpoint;

public class OtherCloudParty extends AbstractRemoteParty implements Party {

	private OtherCloudParty(XdiEndpoint xdiEndpoint) {

		super("Other Cloud", xdiEndpoint);
	}

	public static OtherCloudParty create(String endpointUri, XDI3Segment xri, XDI3Segment cloudNumber, String secretToken) {

		XDIHttpClient xdiClient = new XDIHttpClient(endpointUri);
		xdiClient.addClientListener(DiscoveryDemoApplication.getApp().getEvents());

		XdiEndpoint xdiEndpoint = new XdiEndpoint(
				xdiClient, 
				xri, 
				cloudNumber, 
				secretToken
				);

		return new OtherCloudParty(xdiEndpoint);
	}
}
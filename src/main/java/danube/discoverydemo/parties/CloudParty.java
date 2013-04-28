package danube.discoverydemo.parties;

import xdi2.client.http.XDIHttpClient;
import xdi2.core.xri3.XDI3Segment;
import danube.discoverydemo.DiscoveryDemoApplication;
import danube.discoverydemo.xdi.XdiEndpoint;

public class CloudParty {

	private XdiEndpoint xdiEndpoint;

	private CloudParty(XdiEndpoint xdiEndpoint) {

		this.xdiEndpoint = xdiEndpoint;
	}

	public static CloudParty create(String endpointUri, XDI3Segment xri, XDI3Segment cloudNumber, String secretToken) {

		XDIHttpClient xdiClient = new XDIHttpClient(endpointUri);
		xdiClient.addClientListener(DiscoveryDemoApplication.getApp().getEvents());

		XdiEndpoint xdiEndpoint = new XdiEndpoint(
				xdiClient, 
				xri, 
				cloudNumber, 
				secretToken
				);

		return new CloudParty(xdiEndpoint);
	}

	public XdiEndpoint getXdiEndpoint() {

		return this.xdiEndpoint;
	}
}

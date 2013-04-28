package danube.discoverydemo.parties;

import xdi2.client.XDIClient;
import xdi2.client.http.XDIHttpClient;
import xdi2.core.xri3.XDI3Segment;
import danube.discoverydemo.xdi.XdiEndpoint;

public class PeerRegistryParty {

	private XdiEndpoint xdiEndpoint;

	private PeerRegistryParty(XdiEndpoint xdiEndpoint) {

		this.xdiEndpoint = xdiEndpoint;
	}

	public static PeerRegistryParty create(String endpointUri, String identifier, XDI3Segment canonical, String secretToken) {

		XDIClient xdiClient = new XDIHttpClient(endpointUri);

		XdiEndpoint xdiEndpoint = new XdiEndpoint(
				xdiClient, 
				identifier, 
				canonical, 
				secretToken
				);

		return new PeerRegistryParty(xdiEndpoint);
	}

	public XdiEndpoint getXdiEndpoint() {

		return this.xdiEndpoint;
	}
}

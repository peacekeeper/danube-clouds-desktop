package danube.clouds.desktop.parties.impl;

import xdi2.client.XDIClient;
import xdi2.client.http.XDIHttpClient;
import xdi2.core.xri3.XDI3Segment;
import danube.clouds.desktop.parties.RegistryParty;
import danube.clouds.desktop.xdi.XdiEndpoint;

public class PeerRegistryParty extends AbstractRegistryParty implements RegistryParty {

	private PeerRegistryParty(XdiEndpoint xdiEndpoint) {

		super("Peer Registry", xdiEndpoint);
	}

	public static PeerRegistryParty create(String endpointUri, XDI3Segment xri, XDI3Segment cloudNumber, String secretToken) {

		XDIClient xdiClient = new XDIHttpClient(endpointUri);

		XdiEndpoint xdiEndpoint = new XdiEndpoint(
				xdiClient, 
				xri, 
				cloudNumber, 
				secretToken
				);

		return new PeerRegistryParty(xdiEndpoint);
	}
}

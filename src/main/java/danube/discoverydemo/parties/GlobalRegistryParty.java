package danube.discoverydemo.parties;

import xdi2.client.XDIClient;
import xdi2.client.http.XDIHttpClient;
import xdi2.core.xri3.XDI3Segment;
import danube.discoverydemo.xdi.XdiEndpoint;

public class GlobalRegistryParty {

	private XdiEndpoint xdiEndpoint;

	private GlobalRegistryParty(XdiEndpoint xdiEndpoint) {

		this.xdiEndpoint = xdiEndpoint;
	}

	public static GlobalRegistryParty create() {

		XDIClient xdiClient = new XDIHttpClient("http://mycloud.neustar.biz:12220/");

		XdiEndpoint xdiEndpoint = new XdiEndpoint(
				xdiClient, 
				"=", 
				XDI3Segment.create("="), 
				null
				);

		return new GlobalRegistryParty(xdiEndpoint);
	}

	public XdiEndpoint getXdiEndpoint() {

		return this.xdiEndpoint;
	}
}

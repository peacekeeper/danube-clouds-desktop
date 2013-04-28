package danube.discoverydemo.parties;

import xdi2.client.http.XDIHttpClient;
import xdi2.core.xri3.XDI3Segment;
import xdi2.discovery.XDIDiscovery;
import danube.discoverydemo.DiscoveryDemoApplication;
import danube.discoverydemo.xdi.XdiEndpoint;

public class GlobalRegistryParty {

	private XdiEndpoint xdiEndpoint;
	private XDIDiscovery xdiDiscovery;

	private GlobalRegistryParty(XdiEndpoint xdiEndpoint, XDIDiscovery xdiDiscovery) {

		this.xdiEndpoint = xdiEndpoint;
		this.xdiDiscovery = xdiDiscovery;
	}

	public static GlobalRegistryParty create() {

		XDIHttpClient xdiClient = new XDIHttpClient("http://mycloud.neustar.biz:12220/");
		xdiClient.addClientListener(DiscoveryDemoApplication.getApp().getEvents());

		XdiEndpoint xdiEndpoint = new XdiEndpoint(
				xdiClient, 
				XDI3Segment.create("="), 
				XDI3Segment.create("="), 
				null
				);

		XDIDiscovery xdiDiscovery = new XDIDiscovery(xdiClient);

		return new GlobalRegistryParty(xdiEndpoint, xdiDiscovery);
	}

	public XdiEndpoint getXdiEndpoint() {

		return this.xdiEndpoint;
	}

	public XDIDiscovery getXDIDiscovery() {

		return this.xdiDiscovery;
	}
}

package danube.discoverydemo.parties.impl;

import xdi2.client.http.XDIHttpClient;
import xdi2.core.xri3.XDI3Segment;
import danube.discoverydemo.DiscoveryDemoApplication;
import danube.discoverydemo.parties.RegistryParty;
import danube.discoverydemo.xdi.XdiEndpoint;

public class GlobalRegistryParty extends AbstractRegistryParty implements RegistryParty {

	private GlobalRegistryParty(XdiEndpoint xdiEndpoint) {

		super(xdiEndpoint);
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

		return new GlobalRegistryParty(xdiEndpoint);
	}
}

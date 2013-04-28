package danube.discoverydemo.parties;

import xdi2.client.XDIClient;
import xdi2.client.http.XDIHttpClient;
import xdi2.core.xri3.XDI3Segment;
import danube.discoverydemo.xdi.XdiEndpoint;

public class CloudServiceProviderParty {

	private XdiEndpoint xdiEndpoint;

	private CloudServiceProviderParty(XdiEndpoint xdiEndpoint) {

		this.xdiEndpoint = xdiEndpoint;
	}

	public static CloudServiceProviderParty create() {

		XDIClient xdiClient = new XDIHttpClient("http://mycloud.neustar.biz:14440/registry");

		XdiEndpoint xdiEndpoint = new XdiEndpoint(
				xdiClient, 
				XDI3Segment.create("@neustar"), 
				XDI3Segment.create("[@]!:uuid:0baea650-823b-2475-0bae-a650823b2475"), 
				"s3cret"
				);

		return new CloudServiceProviderParty(xdiEndpoint);
	}

	public XdiEndpoint getXdiEndpoint() {

		return this.xdiEndpoint;
	}
}

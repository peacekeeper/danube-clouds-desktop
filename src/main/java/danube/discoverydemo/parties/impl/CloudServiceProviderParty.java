package danube.discoverydemo.parties.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import xdi2.client.XDIClient;
import xdi2.client.http.XDIHttpClient;
import xdi2.core.xri3.XDI3Segment;
import danube.discoverydemo.DiscoveryDemoApplication;
import danube.discoverydemo.parties.RemoteParty;
import danube.discoverydemo.xdi.XdiEndpoint;

public class CloudServiceProviderParty extends AbstractRemoteParty implements RemoteParty {

	private CloudServiceProviderParty(XdiEndpoint xdiEndpoint) {

		super(xdiEndpoint);
	}

	public static CloudServiceProviderParty create() {

		XDIClient xdiClient = new XDIHttpClient("http://mycloud.neustar.biz:14440/registry");
		xdiClient.addClientListener(DiscoveryDemoApplication.getApp().getEvents());

		XdiEndpoint xdiEndpoint = new XdiEndpoint(
				xdiClient, 
				XDI3Segment.create("@neustar"), 
				XDI3Segment.create("[@]!:uuid:0baea650-823b-2475-0bae-a650823b2475"), 
				"s3cret"
				);

		return new CloudServiceProviderParty(xdiEndpoint);
	}

	public String createCloudEndpointUri(XDI3Segment cloudNumber) {

		try {

			return "http://mycloud.neustar.biz:14440/users/" + URLEncoder.encode(cloudNumber.toString(), "UTF-8");
		} catch (UnsupportedEncodingException ex) {

			throw new RuntimeException(ex.getMessage(), ex);
		}
	}
}

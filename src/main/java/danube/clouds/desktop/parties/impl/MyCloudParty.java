package danube.clouds.desktop.parties.impl;

import xdi2.client.http.XDIHttpClient;
import xdi2.core.xri3.XDI3Segment;
import danube.clouds.desktop.DanubeCloudsDesktopApplication;
import danube.clouds.desktop.parties.CloudParty;
import danube.clouds.desktop.xdi.XdiEndpoint;

public class MyCloudParty extends AbstractCloudParty implements CloudParty {

	private MyCloudParty(XdiEndpoint xdiEndpoint) {

		super("My Cloud", xdiEndpoint);
	}

	public static MyCloudParty create(String endpointUri, XDI3Segment xri, XDI3Segment cloudNumber, String secretToken) {

		XDIHttpClient xdiClient = new XDIHttpClient(endpointUri);
		xdiClient.addClientListener(DanubeCloudsDesktopApplication.getApp().getEvents());

		XdiEndpoint xdiEndpoint = new XdiEndpoint(
				xdiClient, 
				xri, 
				cloudNumber, 
				secretToken
				);

		return new MyCloudParty(xdiEndpoint);
	}
}

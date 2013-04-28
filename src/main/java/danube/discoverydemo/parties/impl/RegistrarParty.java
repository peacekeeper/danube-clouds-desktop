package danube.discoverydemo.parties.impl;

import xdi2.client.XDIClient;
import xdi2.client.http.XDIHttpClient;
import xdi2.core.xri3.XDI3Segment;
import danube.discoverydemo.parties.Party;
import danube.discoverydemo.xdi.XdiEndpoint;

public class RegistrarParty extends AbstractRemoteParty implements Party {

	private RegistrarParty(XdiEndpoint xdiEndpoint) {

		super(xdiEndpoint);
	}

	public static RegistrarParty create() {

		XDIClient xdiClient = new XDIHttpClient("http://registrar.projectdanube.org/");

		XdiEndpoint xdiEndpoint = new XdiEndpoint(
				xdiClient, 
				XDI3Segment.create("@respect"), 
				XDI3Segment.create("[@]!:uuid:299089fd-9d81-3c59-2990-89fd9d813c59"), 
				"s3cret"
				);

		return new RegistrarParty(xdiEndpoint);
	}
}

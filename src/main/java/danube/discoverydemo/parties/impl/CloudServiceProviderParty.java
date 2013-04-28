package danube.discoverydemo.parties.impl;

import ibrokerkit.iname4java.store.Xri;
import ibrokerkit.iname4java.store.XriStore;
import ibrokerkit.iname4java.store.XriStoreException;
import ibrokerkit.iname4java.store.impl.grs.GrsXriData;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;

import org.openxri.xml.Service;

import xdi2.client.XDIClient;
import xdi2.client.exceptions.Xdi2ClientException;
import xdi2.client.http.XDIHttpClient;
import xdi2.core.constants.XDIPolicyConstants;
import xdi2.core.features.roots.XdiPeerRoot;
import xdi2.core.util.StatementUtil;
import xdi2.core.util.XRI2Util;
import xdi2.core.xri3.XDI3Segment;
import xdi2.core.xri3.XDI3Statement;
import xdi2.core.xri3.XDI3SubSegment;
import xdi2.messaging.Message;
import danube.discoverydemo.DiscoveryDemoApplication;
import danube.discoverydemo.parties.Party;
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

	public RegisterCloudNameResult registerCloudName(Party fromParty, XDI3Segment cloudName, String email) throws XriStoreException {

		XriStore xriStore = DiscoveryDemoApplication.getApp().getServlet().getXriStore();

		GrsXriData xriData = new GrsXriData();

		xriData.setUserIdentifier(cloudName.toString());
		xriData.setName("Respect Network");
		xriData.setOrganization("Respect Network");
		xriData.setStreet(new String[] { "Street 1" });
		xriData.setPostalCode("11111");
		xriData.setCity("City");
		xriData.setCountryCode("US");
		xriData.setPrimaryVoice("+1.0000000");
		xriData.setPrimaryEmail(email);

		Xri xri = xriStore.registerXri(null, cloudName.toString(), xriData, 2);

		XDI3Segment cloudNumber = XRI2Util.canonicalIdToCloudNumber(xri.getCanonicalID().getValue());
		String endpointUri = this.createCloudEndpointUri(cloudNumber);

		Service service = new Service();
		service.addURI(endpointUri);
		service.addType("$xdi");

		xri.addService(service);

		return new RegisterCloudNameResult(cloudNumber, endpointUri);
	}

	public RegisterCloudResult registerCloud(Party fromParty, XDI3Segment cloudName, XDI3Segment cloudNumber, String endpointUri, String secretToken) throws Xdi2ClientException {

		XDI3SubSegment cloudNamePeerRoot = XdiPeerRoot.createPeerRootArcXri(cloudName);
		XDI3SubSegment cloudNumberPeerRoot = XdiPeerRoot.createPeerRootArcXri(cloudNumber);

		// assemble message

		Message message = this.getXdiEndpoint().prepareMessage(fromParty.getCloudNumber());

		XDI3Statement[] statements = new XDI3Statement[] {

				XDI3Statement.create("" + cloudNamePeerRoot + "/" + "$is" + "/" + cloudNumberPeerRoot),
				XDI3Statement.create("" + cloudNumberPeerRoot + XDIPolicyConstants.XRI_S_SECRET_TOKEN + "/" + "&" + "/" + StatementUtil.statementObjectToString(secretToken)),
				XDI3Statement.create("" + cloudNumberPeerRoot + "$xdi<$uri>&" + "/" + "&" + "/" + StatementUtil.statementObjectToString(endpointUri))
		};

		message.createSetOperation(Arrays.asList(statements).iterator());

		// send it

		this.getXdiEndpoint().send(message);

		return new RegisterCloudResult(cloudNumber, endpointUri);
	}

	public static class RegisterCloudNameResult {

		private XDI3Segment cloudNumber;
		private String endpointUri;

		public RegisterCloudNameResult(XDI3Segment cloudNumber, String endpointUri) {

			this.cloudNumber = cloudNumber;
			this.endpointUri = endpointUri;
		}

		public XDI3Segment getCloudNumber() {

			return this.cloudNumber;
		}

		public String getEndpointUri() {

			return this.endpointUri;
		}
	}

	public static class RegisterCloudResult {

		private XDI3Segment cloudNumber;
		private String endpointUri;

		public RegisterCloudResult(XDI3Segment cloudNumber, String endpointUri) {

			this.cloudNumber = cloudNumber;
			this.endpointUri = endpointUri;
		}

		public XDI3Segment getCloudNumber() {

			return this.cloudNumber;
		}

		public String getEndpointUri() {

			return this.endpointUri;
		}
	}
}

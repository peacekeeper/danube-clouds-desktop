package danube.discoverydemo.parties.impl;

import ibrokerkit.iname4java.store.Xri;
import ibrokerkit.iname4java.store.XriStore;
import ibrokerkit.iname4java.store.XriStoreException;
import ibrokerkit.iname4java.store.impl.grs.GrsXriData;

import org.openxri.xml.Service;

import xdi2.client.http.XDIHttpClient;
import xdi2.core.util.XRI2Util;
import xdi2.core.xri3.XDI3Segment;
import danube.discoverydemo.DiscoveryDemoApplication;
import danube.discoverydemo.parties.Party;
import danube.discoverydemo.parties.RegistryParty;
import danube.discoverydemo.xdi.XdiEndpoint;

public class GlobalRegistryParty extends AbstractRegistryParty implements RegistryParty {

	private XriStore xriStore;

	private GlobalRegistryParty(XdiEndpoint xdiEndpoint, XriStore xriStore) {

		super("Global Registry", xdiEndpoint);

		this.xriStore = xriStore;
	}

	public static GlobalRegistryParty create() {

		XriStore xriStore = DiscoveryDemoApplication.getApp().getServlet().getXriStore();

		XDIHttpClient xdiClient = new XDIHttpClient("http://mycloud.neustar.biz:12220/");
		xdiClient.addClientListener(DiscoveryDemoApplication.getApp().getEvents());

		XdiEndpoint xdiEndpoint = new XdiEndpoint(
				xdiClient, 
				XDI3Segment.create("[=]"), 
				XDI3Segment.create("[=]"), 
				null
				);

		return new GlobalRegistryParty(xdiEndpoint, xriStore);
	}

	public RegisterCloudNameResult registerCloudName(Party fromParty, CloudServiceProviderParty cloudServiceProviderParty, XDI3Segment cloudName, String email) throws XriStoreException {

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

		Xri xri = this.xriStore.registerXri(null, cloudName.toString(), xriData, 1);

		XDI3Segment cloudNumber = XRI2Util.canonicalIdToCloudNumber(xri.getCanonicalID().getValue());
		String endpointUri = cloudServiceProviderParty.createCloudEndpointUri(cloudNumber);

		Service service = new Service();
		service.addURI(endpointUri);
		service.addType("$xdi");

		xri.addService(service);

		return new RegisterCloudNameResult(cloudName, cloudNumber, endpointUri);
	}

	public RegisterCloudSynonymResult registerCloudSynonym(Party fromParty, XDI3Segment cloudNumber, XDI3Segment cloudSynonym) throws XriStoreException {

		GrsXriData xriData = new GrsXriData();
		xriData.setUserIdentifier(cloudNumber.toString());

		Xri xri = this.xriStore.findXri(XRI2Util.cloudNumberToCanonicalId(cloudNumber));
		this.xriStore.registerXriSynonym(null, cloudSynonym.toString(), xri, xriData, 1);

		return new RegisterCloudSynonymResult(cloudNumber, cloudSynonym);
	}

	public static class RegisterCloudNameResult {

		private XDI3Segment cloudName;
		private XDI3Segment cloudNumber;
		private String endpointUri;

		public RegisterCloudNameResult(XDI3Segment cloudName, XDI3Segment cloudNumber, String endpointUri) {

			this.cloudName = cloudName;
			this.cloudNumber = cloudNumber;
			this.endpointUri = endpointUri;
		}

		public XDI3Segment getCloudName() {

			return this.cloudName;
		}

		public XDI3Segment getCloudNumber() {

			return this.cloudNumber;
		}

		public String getEndpointUri() {

			return this.endpointUri;
		}
	}

	public static class RegisterCloudSynonymResult {

		private XDI3Segment cloudNumber;
		private XDI3Segment cloudSynonym;

		public RegisterCloudSynonymResult(XDI3Segment cloudNumber, XDI3Segment cloudSynonym) {

			this.cloudNumber = cloudNumber;
			this.cloudSynonym = cloudSynonym;
		}

		public XDI3Segment getCloudNumber() {

			return this.cloudNumber;
		}

		public XDI3Segment getCloudSynonym() {

			return this.cloudSynonym;
		}
	}
}

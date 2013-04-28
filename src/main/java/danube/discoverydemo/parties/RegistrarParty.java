package danube.discoverydemo.parties;

import ibrokerkit.iname4java.store.Xri;
import ibrokerkit.iname4java.store.XriStore;
import ibrokerkit.iname4java.store.impl.grs.GrsXriData;

import java.util.Arrays;

import org.openxri.xml.Service;

import xdi2.client.XDIClient;
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
import danube.discoverydemo.ui.MessageDialog;
import danube.discoverydemo.xdi.XdiEndpoint;

public class RegistrarParty {

	private XdiEndpoint xdiEndpoint;

	private RegistrarParty(XdiEndpoint xdiEndpoint) {

		this.xdiEndpoint = xdiEndpoint;
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

	public XdiEndpoint getXdiEndpoint() {

		return this.xdiEndpoint;
	}

	public RegisterCloudNameResult registerCloudName(CloudServiceProviderParty cloudServiceProviderParty, XDI3Segment cloudName, String email) {

		XriStore xriStore = DiscoveryDemoApplication.getApp().getServlet().getXriStore();

		try {

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
			String endpointUri = cloudServiceProviderParty.createCloudEndpointUri(cloudNumber);

			Service service = new Service();
			service.addURI(endpointUri);
			service.addType("$xdi");

			xri.addService(service);

			return new RegisterCloudNameResult(cloudNumber, endpointUri);
		} catch (Exception ex) {

			MessageDialog.problem(ex.getMessage(), ex);
			return null;
		}
	}

	public RegisterCloudResult registerCloud(CloudServiceProviderParty cloudServiceProviderParty, XDI3Segment cloudName, XDI3Segment cloudNumber, String endpointUri, String secretToken) {

		XDI3SubSegment cloudNamePeerRoot = XdiPeerRoot.createPeerRootArcXri(cloudName);
		XDI3SubSegment cloudNumberPeerRoot = XdiPeerRoot.createPeerRootArcXri(cloudNumber);

		// assemble message

		Message message = cloudServiceProviderParty.getXdiEndpoint().prepareMessage(this.getXdiEndpoint());

		XDI3Statement[] statements = new XDI3Statement[] {

				XDI3Statement.create("" + cloudNamePeerRoot + "/" + "$is" + "/" + cloudNumberPeerRoot),
				XDI3Statement.create("" + cloudNumberPeerRoot + XDIPolicyConstants.XRI_S_SECRET_TOKEN + "/" + "&" + "/" + StatementUtil.statementObjectToString(secretToken)),
				XDI3Statement.create("" + cloudNumberPeerRoot + "$xdi<$uri>&" + "/" + "&" + "/" + StatementUtil.statementObjectToString(endpointUri))
		};

		message.createSetOperation(Arrays.asList(statements).iterator());

		// send it

		try {

			cloudServiceProviderParty.getXdiEndpoint().send(message);

			return new RegisterCloudResult(cloudNumber, endpointUri);
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, a problem occurred: " + ex.getMessage(), ex);
			return null;
		}
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

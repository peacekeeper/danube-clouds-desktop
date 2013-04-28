package danube.discoverydemo.parties;

import java.util.Arrays;

import xdi2.core.constants.XDIPolicyConstants;
import xdi2.core.features.roots.XdiPeerRoot;
import xdi2.core.util.StatementUtil;
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

		return new RegistrarParty(null);
	}

	public XdiEndpoint getXdiEndpoint() {

		return this.xdiEndpoint;
	}

	public void registerCloud(CloudServiceProviderParty cloudServiceProviderParty, String cloudName, String cloudNumber, String secretToken) {

		XDI3SubSegment cloudNamePeerRoot = XdiPeerRoot.createPeerRootArcXri(XDI3Segment.create(cloudName));
		XDI3SubSegment cloudNumberPeerRoot = XdiPeerRoot.createPeerRootArcXri(XDI3Segment.create(cloudNumber));

		// assemble message

		Message message = cloudServiceProviderParty.getXdiEndpoint().prepareMessage(this.getXdiEndpoint());

		XDI3Statement[] statements = new XDI3Statement[] {

				XDI3Statement.create("" + cloudNamePeerRoot + "/" + "$is" + "/" + cloudNumberPeerRoot),
				XDI3Statement.create("" + cloudNumberPeerRoot + XDIPolicyConstants.XRI_S_SECRET_TOKEN + "/" + "&" + "/" + StatementUtil.statementObjectToString(secretToken))
		};

		message.createSetOperation(Arrays.asList(statements).iterator());

		// send it

		XdiEndpoint xdiEndpoint = DiscoveryDemoApplication.getApp().getGlobalRegistryParty().getXdiEndpoint();

		try {

			xdiEndpoint.send(message);
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, a problem occurred: " + ex.getMessage(), ex);
			return;
		}
	}
}

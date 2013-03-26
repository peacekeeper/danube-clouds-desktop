package danube.discoverydemo.parties;

import xdi2.client.XDIClient;
import xdi2.client.local.XDILocalClient;
import xdi2.core.Graph;
import xdi2.core.impl.memory.MemoryGraphFactory;
import xdi2.core.xri3.XDI3Segment;
import xdi2.messaging.target.impl.graph.GraphMessagingTarget;
import danube.discoverydemo.xdi.Xdi;
import danube.discoverydemo.xdi.XdiEndpoint;

public class PeerRegistryParty {

	private Graph graph;
	private XdiEndpoint xdiEndpoint;
	private XDI3Segment canonical;

	private PeerRegistryParty(Graph graph, XdiEndpoint xdiEndpoint, XDI3Segment canonical) {

		this.graph = graph;
		this.xdiEndpoint = xdiEndpoint;
		this.canonical = canonical;
	}

	public static PeerRegistryParty create(Xdi xdi, XDI3Segment canonical) {

		Graph graph = MemoryGraphFactory.getInstance().openGraph();

		GraphMessagingTarget messagingTarget = new GraphMessagingTarget();
		messagingTarget.setGraph(graph);

		XDIClient xdiClient = new XDILocalClient(messagingTarget);

		XdiEndpoint xdiEndpoint = xdi.resolveEndpointManually(xdiClient, canonical.toString(), canonical, null);

		return new PeerRegistryParty(graph, xdiEndpoint, canonical);
	}

	public Graph getGraph() {

		return this.graph;
	}

	public XdiEndpoint getXdiEndpoint() {

		return this.xdiEndpoint;
	}

	public XDI3Segment getCanonical() {

		return this.canonical;
	}
}

package danube.discoverydemo.parties;

import xdi2.client.XDIClient;
import xdi2.client.local.XDILocalClient;
import xdi2.core.Graph;
import xdi2.core.impl.memory.MemoryGraphFactory;
import xdi2.core.xri3.XDI3Segment;
import xdi2.messaging.target.impl.graph.GraphMessagingTarget;
import danube.discoverydemo.xdi.Xdi;
import danube.discoverydemo.xdi.XdiEndpoint;

public class GlobalRegistryParty {

	private Graph graph;
	private XdiEndpoint xdiEndpoint;

	private GlobalRegistryParty(Graph graph, XdiEndpoint xdiEndpoint) {

		this.graph = graph;
		this.xdiEndpoint = xdiEndpoint;
	}

	public static GlobalRegistryParty create(Xdi xdi) {

		Graph graph = MemoryGraphFactory.getInstance().openGraph();

		GraphMessagingTarget messagingTarget = new GraphMessagingTarget();
		messagingTarget.setGraph(graph);

		XDIClient xdiClient = new XDILocalClient(messagingTarget);

		XdiEndpoint xdiEndpoint = xdi.resolveEndpointManually(xdiClient, "GRS", XDI3Segment.create("="), null);

		return new GlobalRegistryParty(graph, xdiEndpoint);
	}

	public Graph getGraph() {

		return this.graph;
	}

	public XdiEndpoint getXdiEndpoint() {

		return this.xdiEndpoint;
	}
}

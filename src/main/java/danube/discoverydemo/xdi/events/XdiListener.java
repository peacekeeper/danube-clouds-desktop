package danube.discoverydemo.xdi.events;

public interface XdiListener {

	public void onXdiTransaction(XdiTransactionEvent xdiTransactionEvent);
	public void onXdiDiscovery(XdiDiscoveryEvent xdiDiscoveryEvent);
}

package danube.discoverydemo.xdi.events;

import xdi2.discovery.XDIDiscoveryResult;

public class XdiDiscoveryCloudNameEvent extends XdiDiscoveryEvent {

	private static final long serialVersionUID = 9017841745773244109L;

	private String cloudName;

	public XdiDiscoveryCloudNameEvent(Object source, String cloudName, XDIDiscoveryResult xdiDiscoveryResult) {

		super(source, xdiDiscoveryResult);

		this.cloudName = cloudName;
	}

	public String getCloudName() {

		return this.cloudName;
	}

	public void setCloudName(String cloudName) {

		this.cloudName = cloudName;
	}
}

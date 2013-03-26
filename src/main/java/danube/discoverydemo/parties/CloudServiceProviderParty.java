package danube.discoverydemo.parties;

import java.util.UUID;

import xdi2.core.xri3.XDI3Segment;

public class CloudServiceProviderParty {

	private XDI3Segment canonical;

	private CloudServiceProviderParty(XDI3Segment canonical) {

		this.canonical = canonical;
	}

	public static CloudServiceProviderParty create(XDI3Segment canonical) {

		return new CloudServiceProviderParty(canonical);
	}

	public String generateGui() {

		return UUID.randomUUID().toString();
	}

	public XDI3Segment getCanonical() {

		return this.canonical;
	}
}

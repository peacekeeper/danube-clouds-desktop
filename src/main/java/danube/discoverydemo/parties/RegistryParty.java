package danube.discoverydemo.parties;

import xdi2.core.xri3.XDI3Segment;
import xdi2.discovery.XDIDiscoveryResult;

public interface RegistryParty extends Party {

	public XDIDiscoveryResult discoverFromXri(XDI3Segment xri);
	public XDIDiscoveryResult discoverFromEndpointUri(String endpointUri);
}

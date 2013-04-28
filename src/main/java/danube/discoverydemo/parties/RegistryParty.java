package danube.discoverydemo.parties;

import xdi2.client.exceptions.Xdi2ClientException;
import xdi2.core.xri3.XDI3Segment;
import xdi2.discovery.XDIDiscoveryResult;

public interface RegistryParty extends RemoteParty {

	public XDIDiscoveryResult discoverFromXri(Party fromParty, XDI3Segment xri) throws Xdi2ClientException;
	public XDIDiscoveryResult discoverFromEndpointUri(Party fromParty, String endpointUri) throws Xdi2ClientException;
}

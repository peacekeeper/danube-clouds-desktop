package danube.clouds.desktop.xdi.message;

import java.util.Arrays;
import java.util.Date;

import xdi2.core.features.nodetypes.XdiPeerRoot;
import xdi2.core.xri3.XDI3Segment;
import xdi2.core.xri3.XDI3Statement;
import xdi2.messaging.Message;
import xdi2.messaging.MessageEnvelope;

public class PeerRootAddressRegistrationMessageEnvelopeFactory {

	private XDI3Segment registrar;
	private XDI3Segment registry;
	private XDI3Segment cloudPeerRootAddress;
	private String endpointUri;
	private XDI3Segment linkContractXri;

	public MessageEnvelope create() {

		MessageEnvelope messageEnvelope = new MessageEnvelope();

		Message message = messageEnvelope.getMessage(this.getRegistrar(), true);
		message.setFromAddress(XDI3Segment.create("" + XdiPeerRoot.createPeerRootArcXri(this.getRegistrar())));
		message.setToAddress(XDI3Segment.create("" + XdiPeerRoot.createPeerRootArcXri(this.getRegistry())));
		message.setTimestamp(new Date());
		message.setLinkContractXri(this.getLinkContractXri());

		XDI3Statement[] statements = new XDI3Statement[] {

				XDI3Statement.create("" + this.getCloudPeerRootAddress() + "/" + "+registrar" + "/" + this.getRegistrar()),
				XDI3Statement.create("" + this.getRegistrar() + "/" + "$is+registrar" + "/" + this.getCloudPeerRootAddress()),
				XDI3Statement.create("" + this.getRegistrar() + "$do" + "/" + "$all" + "/" + this.getCloudPeerRootAddress()),
				XDI3Statement.create("" + this.getCloudPeerRootAddress() + "[$uri]!1&" + "/" + "&" + "/" + this.getEndpointUri()),
				XDI3Statement.create("" + this.getCloudPeerRootAddress() + "<$uri>" + "/" + "$ref" + "/" + this.getCloudPeerRootAddress() + "[$uri]!1")
		};

		message.createSetOperation(Arrays.asList(statements).iterator());

		return messageEnvelope;
	}

	public XDI3Segment getRegistrar() {

		return this.registrar;
	}

	public void setRegistrar(XDI3Segment registrar) {

		this.registrar = registrar;
	}

	public XDI3Segment getRegistry() {

		return this.registry;
	}

	public void setRegistry(XDI3Segment registry) {

		this.registry = registry;
	}

	public XDI3Segment getCloudPeerRootAddress() {

		return this.cloudPeerRootAddress;
	}

	public void setCloudPeerRootAddress(XDI3Segment cloudPeerRootAddress) {

		this.cloudPeerRootAddress = cloudPeerRootAddress;
	}

	public String getEndpointUri() {

		return this.endpointUri;
	}

	public void setEndpointUri(String endpointUri) {

		this.endpointUri = endpointUri;
	}

	public XDI3Segment getLinkContractXri() {

		return this.linkContractXri;
	}

	public void setLinkContractXri(XDI3Segment linkContractXri) {

		this.linkContractXri = linkContractXri;
	}
}

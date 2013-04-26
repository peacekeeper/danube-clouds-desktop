package danube.discoverydemo.xdi.message;

import java.util.Arrays;
import java.util.Date;

import xdi2.core.constants.XDIPolicyConstants;
import xdi2.core.features.roots.XdiPeerRoot;
import xdi2.core.util.StatementUtil;
import xdi2.core.xri3.XDI3Segment;
import xdi2.core.xri3.XDI3Statement;
import xdi2.core.xri3.XDI3SubSegment;
import xdi2.messaging.Message;
import xdi2.messaging.MessageEnvelope;

public class CloudRegistrationMessageEnvelopeFactory {

	private XDI3Segment registrar;
	private XDI3Segment registry;
	private XDI3Segment linkContractXri;
	private String linkContractSecretToken;
	private XDI3SubSegment cloudnamePeerRoot;
	private XDI3SubSegment cloudnumberPeerRoot;
	private String cloudSecretToken;

	public MessageEnvelope create() {

		MessageEnvelope messageEnvelope = new MessageEnvelope();

		Message message = messageEnvelope.getMessage(this.getRegistrar(), true);
		message.setFromAddress(XDI3Segment.create("" + XdiPeerRoot.createPeerRootArcXri(this.getRegistrar())));
		message.setToAddress(XDI3Segment.create("" + XdiPeerRoot.createPeerRootArcXri(this.getRegistry())));
		message.setTimestamp(new Date());
		message.setLinkContractXri(this.getLinkContractXri());
		message.getContextNode().setDeepLiteral(XDIPolicyConstants.XRI_S_SECRET_TOKEN, this.getLinkContractSecretToken());

		XDI3Statement[] statements = new XDI3Statement[] {

				XDI3Statement.create("" + this.getCloudnamePeerRoot() + "/" + "$is" + "/" + this.cloudnumberPeerRoot),
				XDI3Statement.create("" + this.getCloudnumberPeerRoot() + XDIPolicyConstants.XRI_S_SECRET_TOKEN + "/" + "&" + "/" + StatementUtil.statementObjectToString(this.getCloudSecretToken()))
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

	public XDI3Segment getLinkContractXri() {

		return this.linkContractXri;
	}

	public void setLinkContractXri(XDI3Segment linkContractXri) {

		this.linkContractXri = linkContractXri;
	}

	public String getLinkContractSecretToken() {

		return this.linkContractSecretToken;
	}

	public void setLinkContractSecretToken(String linkContractSecretToken) {

		this.linkContractSecretToken = linkContractSecretToken;
	}

	public XDI3SubSegment getCloudnamePeerRoot() {

		return this.cloudnamePeerRoot;
	}

	public void setCloudnamePeerRoot(XDI3SubSegment cloudnamePeerRoot) {

		this.cloudnamePeerRoot = cloudnamePeerRoot;
	}

	public XDI3SubSegment getCloudnumberPeerRoot() {

		return this.cloudnumberPeerRoot;
	}

	public void setCloudnumberPeerRoot(XDI3SubSegment cloudnumberPeerRoot) {

		this.cloudnumberPeerRoot = cloudnumberPeerRoot;
	}

	public String getCloudSecretToken() {

		return this.cloudSecretToken;
	}

	public void setCloudSecretToken(String cloudSecretToken) {

		this.cloudSecretToken = cloudSecretToken;
	}
}

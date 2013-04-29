package danube.discoverydemo.ui.cloud;

import java.util.ResourceBundle;

import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import xdi2.client.exceptions.Xdi2ClientException;
import xdi2.core.ContextNode;
import xdi2.core.features.equivalence.Equivalence;
import xdi2.core.features.nodetypes.XdiAbstractAttribute;
import xdi2.core.features.nodetypes.XdiAbstractEntity;
import xdi2.core.features.nodetypes.XdiAttribute;
import xdi2.core.features.nodetypes.XdiAttributeClass;
import xdi2.core.features.nodetypes.XdiEntity;
import xdi2.core.xri3.XDI3Segment;
import xdi2.messaging.Message;
import xdi2.messaging.MessageResult;
import xdi2.messaging.constants.XDIMessagingConstants;
import danube.discoverydemo.dictionary.PersonDictionary;
import danube.discoverydemo.parties.CloudParty;
import danube.discoverydemo.parties.Party;
import danube.discoverydemo.ui.MainWindow;
import danube.discoverydemo.ui.MessageDialog;
import danube.discoverydemo.xdi.XdiEndpoint;

public class XdiEntityColumn extends Column {

	private static final long serialVersionUID = -5106531864010407671L;

	protected ResourceBundle resourceBundle;

	private XDI3Segment fromCloudNumber;
	private XdiEndpoint xdiEndpoint;
	private XDI3Segment contextNodeXri;
	private XdiEntity xdiEntity;

	private boolean readOnly;

	public XdiEntityColumn() {
		super();

		this.readOnly = false;

		// Add design-time configured components.
		initComponents();
	}

	private void refresh() {

		try {

			// refresh data

			if (this.xdiEntity == null) {

				this.xdiGet();
			}

			// refresh UI

			this.removeAll();

			for (XDI3Segment personDictionaryXri : PersonDictionary.DICTIONARY_PERSON_LIST) {

				String label = PersonDictionary.DICTIONARY_PERSON_MAP.get(personDictionaryXri);

				ContextNode contextNode = this.xdiEntity.getContextNode().setDeepContextNode(personDictionaryXri);
				XDI3Segment contextNodeXri = contextNode.getXri();

				ContextNode referenceContextNode = Equivalence.getReferenceContextNode(contextNode);
				if (referenceContextNode != null) contextNode = referenceContextNode;

				if (contextNode != null && XdiAttributeClass.isValid(contextNode)) {

					this.addXdiAttributeClassPanel(contextNodeXri, personDictionaryXri, XdiAttributeClass.fromContextNode(contextNode), label);
				} else if (XdiAbstractAttribute.isValid(contextNode)) {

					this.addXdiAttributePanel(contextNodeXri, personDictionaryXri, XdiAbstractAttribute.fromContextNode(contextNode), label);
				}
			}
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, a problem occurred while retrieving your Personal Data: " + ex.getMessage(), ex);
			return;
		}
	}

	private void xdiGet() throws Xdi2ClientException {

		// $get

		Message message = this.xdiEndpoint.prepareOperation(this.fromCloudNumber, XDIMessagingConstants.XRI_S_GET, this.contextNodeXri);
		MessageResult messageResult = this.xdiEndpoint.send(message);

		ContextNode contextNode = messageResult.getGraph().getDeepContextNode(this.contextNodeXri);
		if (contextNode == null) this.xdiEntity = null;

		this.xdiEntity = XdiAbstractEntity.fromContextNode(contextNode);
	}

	private void addXdiAttributeClassPanel(XDI3Segment contextNodeXri, XDI3Segment xdiAttributeClassXri, XdiAttributeClass xdiAttributeClass, String label) {

		XdiAttributeClassPanel xdiAttributeClassPanel = new XdiAttributeClassPanel();
		xdiAttributeClassPanel.setEndpointAndContextNodeXriAndClassXri(this.xdiEndpoint, contextNodeXri, xdiAttributeClassXri, xdiAttributeClass, label);
		xdiAttributeClassPanel.setReadOnly(this.readOnly);

		this.add(xdiAttributeClassPanel);
	}

	private void addXdiAttributePanel(XDI3Segment contextNodeXri, XDI3Segment xdiAttributeXri, XdiAttribute xdiAttribute, String label) {

		XdiAttributePanel xdiAttributePanel = new XdiAttributePanel();
		xdiAttributePanel.setData(this.fromCloudNumber, this.xdiEndpoint, contextNodeXri, xdiAttributeXri, xdiAttribute, label);
		xdiAttributePanel.setReadOnly(this.readOnly);

		this.add(xdiAttributePanel);
	}

	public void setData(XDI3Segment fromCloudNumber, XdiEndpoint xdiEndpoint, XDI3Segment contextNodeXri, XdiEntity xdiEntity) {

		// refresh

		this.fromCloudNumber = fromCloudNumber;
		this.xdiEndpoint = xdiEndpoint;
		this.contextNodeXri = contextNodeXri;
		this.xdiEntity = xdiEntity;

		this.refresh();
	}

	public void setData(Party fromParty, CloudParty cloudParty, XdiEntity xdiEntity) {

		this.setData(fromParty == null ? null : fromParty.getCloudNumber(), cloudParty.getXdiEndpoint(), cloudParty.getCloudNumber(), xdiEntity);
	}

	public void setReadOnly(boolean readOnly) {

		this.readOnly = readOnly;

		for (XdiAttributeClassPanel component : MainWindow.findChildComponentsByClass(this, XdiAttributeClassPanel.class)) {

			component.setReadOnly(readOnly);
		}

		for (XdiAttributePanel component : MainWindow.findChildComponentsByClass(this, XdiAttributePanel.class)) {

			component.setReadOnly(readOnly);
		}
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
	}
}

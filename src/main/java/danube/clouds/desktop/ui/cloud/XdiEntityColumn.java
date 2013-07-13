package danube.clouds.desktop.ui.cloud;

import java.util.ResourceBundle;

import nextapp.echo.app.Button;
import nextapp.echo.app.Column;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.RowLayoutData;
import xdi2.client.exceptions.Xdi2ClientException;
import xdi2.core.ContextNode;
import xdi2.core.features.equivalence.Equivalence;
import xdi2.core.features.nodetypes.XdiAbstractAttribute;
import xdi2.core.features.nodetypes.XdiAbstractEntity;
import xdi2.core.features.nodetypes.XdiAttribute;
import xdi2.core.features.nodetypes.XdiAttributeClass;
import xdi2.core.features.nodetypes.XdiEntity;
import xdi2.core.xri3.XDI3Segment;
import xdi2.core.xri3.XDI3Statement;
import xdi2.messaging.Message;
import xdi2.messaging.MessageResult;
import xdi2.messaging.constants.XDIMessagingConstants;
import danube.clouds.desktop.dictionary.PersonDictionary;
import danube.clouds.desktop.parties.CloudParty;
import danube.clouds.desktop.parties.Party;
import danube.clouds.desktop.ui.MainWindow;
import danube.clouds.desktop.ui.MessageDialog;
import danube.clouds.desktop.xdi.XdiEndpoint;

public class XdiEntityColumn extends Column {

	private static final long serialVersionUID = -5106531864010407671L;

	protected ResourceBundle resourceBundle;

	private XDI3Segment fromCloudNumber;
	private XdiEndpoint xdiEndpoint;
	private XDI3Segment contextNodeXri;
	private XdiEntity xdiEntity;

	private boolean readOnly;

	private Row linkContractsRow;

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

			if (this.xdiEntity == null) throw new NullPointerException();

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

			MessageDialog.problem("Sorry, a problem occurred while retrieving the Cloud Data: " + ex.getMessage(), ex);
			return;
		} finally {

			this.add(this.linkContractsRow);
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

	private void xdiSetPublic() throws Xdi2ClientException {

		// $set

		Message message = this.xdiEndpoint.prepareOperation(this.fromCloudNumber, XDIMessagingConstants.XRI_S_SET, XDI3Statement.create("$public$do/$get/()"));
		this.xdiEndpoint.send(message);
	}

	private void xdiDelPublic() throws Xdi2ClientException {

		// $del

		Message message = this.xdiEndpoint.prepareOperation(this.fromCloudNumber, XDIMessagingConstants.XRI_S_DEL, XDI3Statement.create("$public$do/$get/()"));
		this.xdiEndpoint.send(message);
	}

	private void addXdiAttributeClassPanel(XDI3Segment contextNodeXri, XDI3Segment xdiAttributeClassXri, XdiAttributeClass xdiAttributeClass, String label) {

		XdiAttributeClassPanel xdiAttributeClassPanel = new XdiAttributeClassPanel();
		xdiAttributeClassPanel.setData(this.xdiEndpoint, contextNodeXri, xdiAttributeClassXri, xdiAttributeClass, label);
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

		this.linkContractsRow.setVisible(! readOnly);
	}

	public void onMakePublicActionPerformed(ActionEvent e) {

		try {

			this.xdiSetPublic();
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, a problem occurred while setting the public Link Contract: " + ex.getMessage(), ex);
			return;
		}

		MessageDialog.info("Public Link Contract enabled!");
	}

	public void onMakePrivateActionPerformed(ActionEvent e) {

		try {

			this.xdiDelPublic();
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, a problem occurred while deleting the public Link Contract: " + ex.getMessage(), ex);
			return;
		}

		MessageDialog.info("Public Link Contract removed!");
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		linkContractsRow = new Row();
		linkContractsRow.setInsets(new Insets(new Extent(10, Extent.PX)));
		linkContractsRow.setCellSpacing(new Extent(10, Extent.PX));
		add(linkContractsRow);
		Label label12 = new Label();
		label12.setStyleName("Default");
		label12.setText("Link Contract:");
		RowLayoutData label12LayoutData = new RowLayoutData();
		label12LayoutData.setInsets(new Insets(new Extent(0, Extent.PX),
				new Extent(0, Extent.PX), new Extent(10, Extent.PX),
				new Extent(0, Extent.PX)));
		label12.setLayoutData(label12LayoutData);
		linkContractsRow.add(label12);
		Button button2 = new Button();
		button2.setStyleName("Default");
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/danube/clouds/desktop/resource/image/linkcontracts_on.png");
		button2.setIcon(imageReference1);
		button2.setText("Make Public");
		button2.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onMakePublicActionPerformed(e);
			}
		});
		linkContractsRow.add(button2);
		Button button1 = new Button();
		button1.setStyleName("Default");
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/danube/clouds/desktop/resource/image/linkcontracts_off.png");
		button1.setIcon(imageReference2);
		button1.setText("Make Private");
		button1.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onMakePrivateActionPerformed(e);
			}
		});
		linkContractsRow.add(button1);
	}
}

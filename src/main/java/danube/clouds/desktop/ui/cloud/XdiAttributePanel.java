package danube.clouds.desktop.ui.cloud;

import java.util.ResourceBundle;

import nextapp.echo.app.Button;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.TextField;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.RowLayoutData;
import xdi2.client.exceptions.Xdi2ClientException;
import xdi2.connector.facebook.mapping.FacebookMapping;
import xdi2.core.ContextNode;
import xdi2.core.Literal;
import xdi2.core.constants.XDIDictionaryConstants;
import xdi2.core.exceptions.Xdi2RuntimeException;
import xdi2.core.features.equivalence.Equivalence;
import xdi2.core.features.nodetypes.XdiAbstractAttribute;
import xdi2.core.features.nodetypes.XdiAttribute;
import xdi2.core.features.nodetypes.XdiValue;
import xdi2.core.xri3.XDI3Segment;
import xdi2.core.xri3.XDI3Statement;
import xdi2.messaging.Message;
import xdi2.messaging.MessageResult;
import danube.clouds.desktop.ui.MessageDialog;
import danube.clouds.desktop.ui.xdi.XdiButton;
import danube.clouds.desktop.xdi.XdiEndpoint;

public class XdiAttributePanel extends Panel {

	private static final long serialVersionUID = -5082464847478633075L;

	protected ResourceBundle resourceBundle;

	private XDI3Segment fromCloudNumber;
	private XdiEndpoint xdiEndpoint;
	private XDI3Segment contextNodeXri;
	private XDI3Segment xdiAttributeXri;
	private XdiAttribute xdiAttribute;
	private String label;

	private boolean readOnly;

	private Label xdiAttributeLabel;
	private Label valueLabel;
	private TextField valueTextField;
	private Button editButton;
	private Button updateButton;
	private Button deleteButton;
	private Button linkPersonalButton;
	private Button linkAllfiledButton;
	private Button linkFacebookButtonRef;
	private Button linkFacebookButtonRep;
	private XdiButton xdiButton;

	private Button unlinkButton;

	public XdiAttributePanel() {
		super();

		this.readOnly = false;

		// Add design-time configured components.
		initComponents();
	}

	@Override
	public void init() {

		super.init();
	}

	@Override
	public void dispose() {

		super.dispose();
	}

	private void invalidate() {

		this.xdiAttribute = null;
	}

	private void refresh() {

		try {

			// refresh data

			if (this.xdiAttribute == null) {

				this.xdiGet();
			}

			// refresh UI

			this.xdiAttributeLabel.setText(this.label);
			this.xdiButton.setData(this.fromCloudNumber, this.xdiEndpoint, this.contextNodeXri);
			this.linkFacebookButtonRef.setEnabled(FacebookMapping.getInstance().xdiDataXriToFacebookDataXri(this.xdiAttributeXri) != null);
			this.linkFacebookButtonRep.setEnabled(FacebookMapping.getInstance().xdiDataXriToFacebookDataXri(this.xdiAttributeXri) != null);
			this.linkPersonalButton.setEnabled(false);
			this.linkAllfiledButton.setEnabled(false);

			XdiValue xdiValue = this.xdiAttribute == null ? null : this.xdiAttribute.getXdiValue(false);
			Literal literal = xdiValue == null ? null : xdiValue.getContextNode().getLiteral();
			String value = literal == null ? null : literal.getLiteralDataString();

			this.valueLabel.setText(value);
			this.valueTextField.setText(value);
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, a problem occurred while retrieving Cloud Data: " + ex.getMessage(), ex);
			return;
		}
	}

	private void xdiGet() throws Xdi2ClientException {

		// $get

		Message message = this.xdiEndpoint.prepareMessage(this.fromCloudNumber);
		message.createGetOperation(this.contextNodeXri);

		MessageResult messageResult = this.xdiEndpoint.send(message);

		ContextNode contextNode = messageResult.getGraph().getDeepContextNode(this.contextNodeXri);
		ContextNode referenceContextNode = contextNode == null ? null : Equivalence.getReferenceContextNode(contextNode);
		if (referenceContextNode != null) contextNode = referenceContextNode;

		this.xdiAttribute = contextNode == null ? null : XdiAbstractAttribute.fromContextNode(contextNode);
	}

	private void xdiSet(String value) throws Xdi2ClientException {

		// $set

		Message message = this.xdiEndpoint.prepareMessage(this.fromCloudNumber);
		message.createSetOperation(XDI3Statement.fromLiteralComponents(XDI3Segment.create(this.contextNodeXri + "&"), value));

		this.xdiEndpoint.send(message);
	}

	private void xdiSetFacebook(XDI3Segment predicate) throws Xdi2ClientException {

		// $set

		XDI3Segment facebookDataXri = FacebookMapping.getInstance().xdiDataXriToFacebookDataXri(this.xdiAttributeXri);
		if (facebookDataXri == null) throw new Xdi2RuntimeException("No mapping for Facebook available: " + this.xdiAttributeXri);

		XDI3Segment facebookContextNodeXri = XDI3Segment.create("" + FacebookMapping.XRI_S_FACEBOOK_CONTEXT + this.xdiEndpoint.getCloudNumber() + facebookDataXri);

		Message message = this.xdiEndpoint.prepareMessage(this.fromCloudNumber);
		message.createSetOperation(XDI3Statement.fromComponents(this.contextNodeXri, predicate, facebookContextNodeXri));

		this.xdiEndpoint.send(message);
	}

	/*	private void xdiAddPersonal() throws Xdi2ClientException {

		// $add

		XDI3Segment personalDataXri = PersonalMapping.getInstance().xdiDataXriToPersonalDataXri(this.attributeXri);
		if (personalDataXri == null) throw new Xdi2RuntimeException("No mapping for Personal available: " + this.attributeXri);

		XDI3Segment personalContextNodeXri = new XDI3Segment("" + PersonalMapping.XRI_S_PERSONAL_CONTEXT + this.endpoint.getCanonical() + personalDataXri);

		Message message = this.endpoint.prepareMessage(this.fromCloudNumber);
		message.createAddOperation(StatementUtil.fromComponents(this.contextNodeXri, XDIDictionaryConstants.XRI_S_IS, personalContextNodeXri));

		this.endpoint.send(message);
	}*/

	/*	private void xdiAddAllfiled() throws Xdi2ClientException {

		// $add

		XDI3Segment allfiledDataXri = AllfiledMapping.getInstance().xdiDataXriToAllfiledDataXri(this.attributeXri);
		if (allfiledDataXri == null) throw new Xdi2RuntimeException("No mapping for Allfield available: " + this.attributeXri);

		XDI3Segment allfiledContextNodeXri = new XDI3Segment("" + AllfiledMapping.XRI_S_ALLFILED_CONTEXT + this.endpoint.getCanonical() + allfiledDataXri);

		Message message = this.endpoint.prepareMessage(this.fromCloudNumber);
		message.createAddOperation(StatementUtil.fromComponents(this.contextNodeXri, XDIDictionaryConstants.XRI_S_IS, allfiledContextNodeXri));

		this.endpoint.send(message);
	}*/

	private void xdiDelLinks() throws Xdi2ClientException {

		// $del

		Message message = this.xdiEndpoint.prepareMessage(this.fromCloudNumber);
		message.createDelOperation(XDI3Statement.fromRelationComponents(this.contextNodeXri, XDIDictionaryConstants.XRI_S_REF, XDI3Segment.create("{}")));

		this.xdiEndpoint.send(message);

		message = this.xdiEndpoint.prepareMessage(this.fromCloudNumber);
		message.createDelOperation(XDI3Statement.fromRelationComponents(this.contextNodeXri, XDIDictionaryConstants.XRI_S_REP, XDI3Segment.create("{}")));

		this.xdiEndpoint.send(message);
	}

	private void xdiDel() throws Xdi2ClientException {

		// $del

		Message message = this.xdiEndpoint.prepareMessage(this.fromCloudNumber);
		message.createDelOperation(this.contextNodeXri);

		this.xdiEndpoint.send(message);
	}

	public void setData(XDI3Segment fromCloudNumber, XdiEndpoint endpoint, XDI3Segment contextNodeXri, XDI3Segment xdiAttributeXri, XdiAttribute xdiAttribute, String label) {

		// refresh

		this.fromCloudNumber = fromCloudNumber;
		this.xdiEndpoint = endpoint;
		this.contextNodeXri = contextNodeXri;
		this.xdiAttributeXri = xdiAttributeXri;
		this.xdiAttribute = xdiAttribute;
		this.label = label;

		this.refresh();
	}

	public void setReadOnly(boolean readOnly) {

		if (readOnly && (! this.readOnly)) {

			this.valueLabel.setVisible(true);
			this.valueTextField.setVisible(false);
			this.editButton.setVisible(false);
			this.updateButton.setVisible(false);
			this.deleteButton.setVisible(false);
			this.linkFacebookButtonRef.setVisible(false);
			this.linkFacebookButtonRep.setVisible(false);
			this.linkPersonalButton.setVisible(false);
			this.linkAllfiledButton.setVisible(false);
			this.unlinkButton.setVisible(false);
		} else if ((! readOnly) && this.readOnly){

			this.valueLabel.setVisible(true);
			this.valueTextField.setVisible(false);
			this.editButton.setVisible(true);
			this.updateButton.setVisible(false);
			this.deleteButton.setVisible(true);
			this.linkFacebookButtonRef.setVisible(true);
			this.linkFacebookButtonRep.setVisible(true);
			this.linkPersonalButton.setVisible(true);
			this.linkAllfiledButton.setVisible(true);
			this.unlinkButton.setVisible(true);
		}

		this.readOnly = readOnly;
	}


	private void onEditActionPerformed(ActionEvent e) {

		this.valueTextField.setText(this.valueLabel.getText());

		this.valueLabel.setVisible(false);
		this.valueTextField.setVisible(true);
		this.editButton.setVisible(false);
		this.updateButton.setVisible(true);
	}

	private void onUpdateActionPerformed(ActionEvent e) {

		try {

			this.xdiSet(this.valueTextField.getText());
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, a problem occurred while storing Cloud Data: " + ex.getMessage(), ex);
			return;
		}

		this.valueLabel.setVisible(true);
		this.valueTextField.setVisible(false);
		this.editButton.setVisible(true);
		this.updateButton.setVisible(false);

		// refresh

		this.invalidate();
		this.refresh();
	}

	private void onDeleteActionPerformed(ActionEvent e) {

		if (this.valueLabel.isVisible()) {

			try {

				this.xdiDel();
			} catch (Exception ex) {

				MessageDialog.problem("Sorry, a problem occurred while storing Cloud Data: " + ex.getMessage(), ex);
				return;
			}
		} else {

			this.valueLabel.setVisible(true);
			this.valueTextField.setVisible(false);
			this.editButton.setVisible(true);
			this.updateButton.setVisible(false);
		}

		// refresh

		this.invalidate();
		this.refresh();
	}

	private void onLinkFacebookActionPerformed(ActionEvent e) {

		try {

			this.xdiDel();

			if (((Button) e.getSource()).getText().contains("$ref"))
				this.xdiSetFacebook(XDIDictionaryConstants.XRI_S_REF);
			else if (((Button) e.getSource()).getText().contains("$rep"))
				this.xdiSetFacebook(XDIDictionaryConstants.XRI_S_REP);
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, a problem occurred while storing Cloud Data: " + ex.getMessage(), ex);
			return;
		}

		MessageDialog.info(this.label + " linked to Facebook.");

		// refresh

		this.invalidate();
		this.refresh();
	}

	private void onLinkPersonalActionPerformed(ActionEvent e) {

		/*		try {

			this.xdiDel();
			this.xdiAddPersonal();
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, a problem occurred while storing Cloud Data: " + ex.getMessage(), ex);
			return;
		}

		MessageDialog.info(this.label + " linked to Personal.");*/

		// refresh

		this.invalidate();
		this.refresh();
	}

	private void onLinkAllfiledActionPerformed(ActionEvent e) {

		/*		try {

			this.xdiDel();
			this.xdiAddAllfiled();
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, a problem occurred while storing Cloud Data: " + ex.getMessage(), ex);
			return;
		}

		MessageDialog.info(this.label + " linked to Allfiled.");*/

		// refresh

		this.invalidate();
		this.refresh();
	}

	private void onUnlinkActionPerformed(ActionEvent e) {

		try {

			this.xdiDelLinks();
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, a problem occurred while storing Cloud Data: " + ex.getMessage(), ex);
			return;
		}

		MessageDialog.info(this.label + " unlinked.");

		// refresh

		this.invalidate();
		this.refresh();
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setInsets(new Insets(new Extent(30, Extent.PX), new Extent(0,
				Extent.PX), new Extent(0, Extent.PX), new Extent(5, Extent.PX)));
		Row row1 = new Row();
		row1.setCellSpacing(new Extent(10, Extent.PX));
		add(row1);
		Panel panel2 = new Panel();
		RowLayoutData panel2LayoutData = new RowLayoutData();
		panel2LayoutData.setWidth(new Extent(400, Extent.PX));
		panel2.setLayoutData(panel2LayoutData);
		row1.add(panel2);
		Row row2 = new Row();
		row2.setCellSpacing(new Extent(5, Extent.PX));
		panel2.add(row2);
		xdiButton = new XdiButton();
		row2.add(xdiButton);
		xdiAttributeLabel = new Label();
		xdiAttributeLabel.setStyleName("Bold");
		xdiAttributeLabel.setText("...");
		RowLayoutData xdiAttributeLabelLayoutData = new RowLayoutData();
		xdiAttributeLabelLayoutData.setWidth(new Extent(120, Extent.PX));
		xdiAttributeLabel.setLayoutData(xdiAttributeLabelLayoutData);
		row2.add(xdiAttributeLabel);
		valueLabel = new Label();
		valueLabel.setStyleName("Default");
		valueLabel.setText("...");
		row2.add(valueLabel);
		valueTextField = new TextField();
		valueTextField.setStyleName("Default");
		valueTextField.setVisible(false);
		valueTextField.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onUpdateActionPerformed(e);
			}
		});
		row2.add(valueTextField);
		editButton = new Button();
		editButton.setStyleName("Plain");
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/danube/clouds/desktop/resource/image/op-edit.png");
		editButton.setIcon(imageReference1);
		editButton.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onEditActionPerformed(e);
			}
		});
		row1.add(editButton);
		updateButton = new Button();
		updateButton.setStyleName("Plain");
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/danube/clouds/desktop/resource/image/op-ok.png");
		updateButton.setIcon(imageReference2);
		updateButton.setVisible(false);
		updateButton.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onUpdateActionPerformed(e);
			}
		});
		row1.add(updateButton);
		deleteButton = new Button();
		deleteButton.setStyleName("Plain");
		ResourceImageReference imageReference3 = new ResourceImageReference(
				"/danube/clouds/desktop/resource/image/op-cancel.png");
		deleteButton.setIcon(imageReference3);
		deleteButton.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onDeleteActionPerformed(e);
			}
		});
		row1.add(deleteButton);
		linkFacebookButtonRef = new Button();
		linkFacebookButtonRef.setStyleName("Default");
		ResourceImageReference imageReference4 = new ResourceImageReference(
				"/danube/clouds/desktop/resource/image/connect-facebook.png");
		linkFacebookButtonRef.setIcon(imageReference4);
		linkFacebookButtonRef.setText("$ref");
		linkFacebookButtonRef.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onLinkFacebookActionPerformed(e);
			}
		});
		row1.add(linkFacebookButtonRef);
		linkFacebookButtonRep = new Button();
		linkFacebookButtonRep.setStyleName("Default");
		linkFacebookButtonRep.setIcon(imageReference4);
		linkFacebookButtonRep.setText("$rep");
		linkFacebookButtonRep.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onLinkFacebookActionPerformed(e);
			}
		});
		row1.add(linkFacebookButtonRep);
		linkPersonalButton = new Button();
		linkPersonalButton.setStyleName("Default");
		ResourceImageReference imageReference5 = new ResourceImageReference(
				"/danube/clouds/desktop/resource/image/connect-personal.png");
		linkPersonalButton.setIcon(imageReference5);
		linkPersonalButton.setText("$ref");
		linkPersonalButton.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onLinkPersonalActionPerformed(e);
			}
		});
		row1.add(linkPersonalButton);
		linkAllfiledButton = new Button();
		linkAllfiledButton.setStyleName("Default");
		ResourceImageReference imageReference6 = new ResourceImageReference(
				"/danube/clouds/desktop/resource/image/connect-allfiled.png");
		linkAllfiledButton.setIcon(imageReference6);
		linkAllfiledButton.setText("$ref");
		linkAllfiledButton.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onLinkAllfiledActionPerformed(e);
			}
		});
		row1.add(linkAllfiledButton);
		unlinkButton = new Button();
		unlinkButton.setStyleName("Default");
		unlinkButton.setText("Unlink");
		unlinkButton.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onUnlinkActionPerformed(e);
			}
		});
		row1.add(unlinkButton);
	}
}

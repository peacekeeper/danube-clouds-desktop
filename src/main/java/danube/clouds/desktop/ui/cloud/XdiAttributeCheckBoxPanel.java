package danube.clouds.desktop.ui.cloud;

import java.util.ResourceBundle;

import nextapp.echo.app.CheckBox;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Panel;
import nextapp.echo.app.Row;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.RowLayoutData;
import xdi2.client.exceptions.Xdi2ClientException;
import xdi2.core.ContextNode;
import xdi2.core.Literal;
import xdi2.core.features.equivalence.Equivalence;
import xdi2.core.features.nodetypes.XdiAbstractAttribute;
import xdi2.core.features.nodetypes.XdiAttribute;
import xdi2.core.features.nodetypes.XdiValue;
import xdi2.core.util.StatementUtil;
import xdi2.core.xri3.XDI3Segment;
import xdi2.messaging.Message;
import xdi2.messaging.MessageResult;
import danube.clouds.desktop.ui.MessageDialog;
import danube.clouds.desktop.ui.xdi.XdiButton;
import danube.clouds.desktop.xdi.XdiEndpoint;

public class XdiAttributeCheckBoxPanel extends Panel {

	private static final long serialVersionUID = -5082464847478633075L;

	protected ResourceBundle resourceBundle;

	private XDI3Segment fromCloudNumber;
	private XdiEndpoint xdiEndpoint;
	private XDI3Segment contextNodeXri;
	private XDI3Segment xdiAttributeXri;
	private XdiAttribute xdiAttribute;
	private String label;
	private XdiButton xdiButton;
	private CheckBox valueCheckBox;

	private boolean readOnly;

	public XdiAttributeCheckBoxPanel() {
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

			this.xdiButton.setData(this.fromCloudNumber, this.xdiEndpoint, this.contextNodeXri);
			this.valueCheckBox.setText(this.label);

			XdiValue xdiValue = this.xdiAttribute == null ? null : this.xdiAttribute.getXdiValue(false);
			Literal literal = xdiValue == null ? null : xdiValue.getContextNode().getLiteral();
			String value = literal == null ? null : literal.getLiteralData();

			this.valueCheckBox.setSelected(Boolean.parseBoolean(value));
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
		message.createSetOperation(StatementUtil.fromLiteralComponents(XDI3Segment.create(this.contextNodeXri + "&"), value));

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

		this.valueCheckBox.setEnabled(! readOnly);
	}

	private void onUpdateActionPerformed(ActionEvent e) {

		try {

			if (this.valueCheckBox.isSelected()) {

				this.xdiSet(Boolean.TRUE.toString());
			} else {
				
				this.xdiSet(Boolean.FALSE.toString());
			}
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, a problem occurred while storing Cloud Data: " + ex.getMessage(), ex);
			return;
		}

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
		valueCheckBox = new CheckBox();
		valueCheckBox.setText("...");
		valueCheckBox.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onUpdateActionPerformed(e);
			}
		});
		row2.add(valueCheckBox);
	}
}

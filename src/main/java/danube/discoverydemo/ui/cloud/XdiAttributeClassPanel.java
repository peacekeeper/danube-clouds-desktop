package danube.discoverydemo.ui.cloud;

import java.util.Iterator;
import java.util.ResourceBundle;

import nextapp.echo.app.Button;
import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.TextField;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.RowLayoutData;
import xdi2.client.exceptions.Xdi2ClientException;
import xdi2.core.ContextNode;
import xdi2.core.features.nodetypes.XdiAbstractInstanceUnordered;
import xdi2.core.features.nodetypes.XdiAttribute;
import xdi2.core.features.nodetypes.XdiAttributeClass;
import xdi2.core.features.nodetypes.XdiAttributeInstance;
import xdi2.core.util.StatementUtil;
import xdi2.core.xri3.XDI3Segment;
import xdi2.messaging.Message;
import xdi2.messaging.MessageResult;
import danube.discoverydemo.ui.MainWindow;
import danube.discoverydemo.ui.MessageDialog;
import danube.discoverydemo.ui.xdi.XdiEndpointPanel;
import danube.discoverydemo.xdi.XdiEndpoint;

public class XdiAttributeClassPanel extends Panel {

	private static final long serialVersionUID = -5082464847478633075L;

	protected ResourceBundle resourceBundle;

	private XDI3Segment fromCloudNumber;
	private XdiEndpoint endpoint;
	private XDI3Segment contextNodeXri;
	private XDI3Segment xdiAttributeClassXri;
	private XdiAttributeClass xdiAttributeClass;
	private String label;

	private boolean readOnly;

	private XdiEndpointPanel xdiPanel;
	private Label xdiAttributeClassLabel;
	private Column xdiAttributesColumn;
	private TextField addTextField;
	private Button cancelButton;
	private Button addButton;


	/**
	 * Creates a new <code>AccountPersonaPanel</code>.
	 */
	public XdiAttributeClassPanel() {
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

	private void refresh() {

		try {

			// refresh data

			if (this.xdiAttributeClass == null) this.xdiGet();

			// refresh UI

			this.xdiPanel.setData(this.endpoint);
			this.xdiAttributeClassLabel.setText(this.label);

			this.xdiAttributesColumn.removeAll();

			for (Iterator<? extends XdiAttributeInstance> xdiAttributeInstances = this.xdiAttributeClass.getXdiInstancesUnordered(); xdiAttributeInstances.hasNext(); ) {

				XdiAttributeInstance xdiAttributeInstance = xdiAttributeInstances.next();

				this.addXdiAttributePanel(xdiAttributeInstance.getContextNode().getXri(), XDI3Segment.create("" + xdiAttributeInstance.getContextNode().getArcXri()), xdiAttributeInstance, this.label);
			}
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, a problem occurred while retrieving your Personal Data: " + ex.getMessage(), ex);
			return;
		}
	}

	private void xdiGet() throws Xdi2ClientException {

		// $get

		Message message = this.endpoint.prepareMessage(this.fromCloudNumber);
		message.createGetOperation(this.contextNodeXri);

		MessageResult messageResult = this.endpoint.send(message);

		ContextNode contextNode = messageResult.getGraph().getDeepContextNode(this.contextNodeXri);
		this.xdiAttributeClass = contextNode == null ? null : XdiAttributeClass.fromContextNode(contextNode);
	}

	private void xdiAdd(String value) throws Xdi2ClientException {

		// $add

		XDI3Segment xdiAttributeMemberXri = XDI3Segment.create("" + this.contextNodeXri + XdiAbstractInstanceUnordered.createArcXriFromRandom(true));

		Message message = this.endpoint.prepareMessage(this.fromCloudNumber);
		message.createSetOperation(StatementUtil.fromLiteralComponents(xdiAttributeMemberXri, value));

		this.endpoint.send(message);
	}

	public void setData(XdiEndpoint endpoint, XDI3Segment contextNodeXri, XDI3Segment xdiAttributeClassXri, XdiAttributeClass xdiAttributeClass, String label) {

		// refresh

		this.endpoint = endpoint;
		this.contextNodeXri = contextNodeXri;
		this.xdiAttributeClassXri = xdiAttributeClassXri;
		this.xdiAttributeClass = xdiAttributeClass;
		this.label = label;

		this.refresh();
	}

	public void setReadOnly(boolean readOnly) {

		if (readOnly && (! this.readOnly)) {

			this.addTextField.setVisible(false);
			this.addButton.setVisible(false);
			this.cancelButton.setVisible(false);
		} else if ((! readOnly) && this.readOnly){

			this.addTextField.setVisible(false);
			this.addButton.setVisible(true);
			this.cancelButton.setVisible(false);
		}

		this.readOnly = readOnly;

		for (XdiAttributePanel component : MainWindow.findChildComponentsByClass(this, XdiAttributePanel.class)) {

			component.setReadOnly(readOnly);
		}
	}

	private void addXdiAttributePanel(XDI3Segment contextNodeXri, XDI3Segment attributeXri, XdiAttribute xdiAttribute, String label) {

		XdiAttributePanel xdiAttributePanel = new XdiAttributePanel();
		xdiAttributePanel.setData(this.fromCloudNumber, this.endpoint, contextNodeXri, attributeXri, xdiAttribute, label);
		xdiAttributePanel.setReadOnly(this.readOnly);

		this.xdiAttributesColumn.add(xdiAttributePanel);
	}

	private void onAddActionPerformed(ActionEvent e) {

		if (this.addTextField.isVisible()) {

			String value = this.addTextField.getText();
			if (value == null || value.trim().equals("")) return;

			try {

				this.xdiAdd(value);
			} catch (Exception ex) {

				MessageDialog.problem("Sorry, a problem occurred while storing your Personal Data: " + ex.getMessage(), ex);
				return;
			}

			this.addTextField.setVisible(false);
			this.cancelButton.setVisible(false);
		} else {

			this.addTextField.setText("");

			this.addTextField.setVisible(true);
			this.cancelButton.setVisible(true);
		}
	}

	private void onCancelActionPerformed(ActionEvent e) {

		this.addTextField.setVisible(false);
		this.cancelButton.setVisible(false);
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		Column column1 = new Column();
		column1.setCellSpacing(new Extent(10, Extent.PX));
		add(column1);
		Row row2 = new Row();
		row2.setCellSpacing(new Extent(10, Extent.PX));
		column1.add(row2);
		xdiPanel = new XdiEndpointPanel();
		row2.add(xdiPanel);
		xdiAttributeClassLabel = new Label();
		xdiAttributeClassLabel.setStyleName("Bold");
		xdiAttributeClassLabel.setText("...");
		RowLayoutData xdiAttributeClassLabelLayoutData = new RowLayoutData();
		xdiAttributeClassLabelLayoutData.setWidth(new Extent(120, Extent.PX));
		xdiAttributeClassLabel.setLayoutData(xdiAttributeClassLabelLayoutData);
		row2.add(xdiAttributeClassLabel);
		addTextField = new TextField();
		addTextField.setStyleName("Default");
		addTextField.setVisible(false);
		addTextField.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onAddActionPerformed(e);
			}
		});
		row2.add(addTextField);
		addButton = new Button();
		addButton.setStyleName("Plain");
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/danube/discoverydemo/resource/image/op-add.png");
		addButton.setIcon(imageReference1);
		addButton.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onAddActionPerformed(e);
			}
		});
		row2.add(addButton);
		cancelButton = new Button();
		cancelButton.setStyleName("Plain");
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/danube/discoverydemo/resource/image/op-cancel.png");
		cancelButton.setIcon(imageReference2);
		cancelButton.setVisible(false);
		cancelButton.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onCancelActionPerformed(e);
			}
		});
		row2.add(cancelButton);
		xdiAttributesColumn = new Column();
		column1.add(xdiAttributesColumn);
	}
}

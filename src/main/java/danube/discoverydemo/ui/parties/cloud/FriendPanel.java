package danube.discoverydemo.ui.parties.cloud;

import java.util.ResourceBundle;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Button;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Panel;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.RowLayoutData;
import xdi2.core.util.StatementUtil;
import xdi2.core.xri3.XDI3Segment;
import xdi2.messaging.Message;
import danube.discoverydemo.ui.MessageDialog;
import danube.discoverydemo.ui.xdi.XdiPanel;
import danube.discoverydemo.xdi.XdiEndpoint;
import echopoint.ImageIcon;

public class FriendPanel extends Panel {

	private static final long serialVersionUID = -6674403250232180782L;

	protected ResourceBundle resourceBundle;

	private XdiEndpoint endpoint;
	private XDI3Segment contextNodeXri;
	private XDI3Segment targetContextNodeXri;

	private XdiPanel xdiPanel;
	private Button friendButton;
	private Button deleteButton;

	private FriendPanelDelegate friendPanelDelegate;

	/**
	 * Creates a new <code>AccountPersonaPanel</code>.
	 */
	public FriendPanel() {
		super();

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

			String friend = this.targetContextNodeXri.toString();

			this.xdiPanel.setEndpoint(this.endpoint);
			this.friendButton.setText(friend);
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, a problem occurred while retrieving your Personal Data: " + ex.getMessage(), ex);
			return;
		}
	}

	public void setEndpointAndContextNodeXriAndTargetContextNodeXri(XdiEndpoint endpoint, XDI3Segment contextNodeXri, XDI3Segment targetContextNodeXri) {

		// refresh

		this.endpoint = endpoint;
		this.contextNodeXri = contextNodeXri;
		this.targetContextNodeXri = targetContextNodeXri;

		this.refresh();
	}

	public void setFriendPanelDelegate(FriendPanelDelegate friendPanelDelegate) {

		this.friendPanelDelegate = friendPanelDelegate;
	}

	public FriendPanelDelegate getFriendPanelDelegate() {

		return this.friendPanelDelegate;
	}

	private void onFriendActionPerformed(ActionEvent e) {

		if (this.friendPanelDelegate != null) {

			this.friendPanelDelegate.onFriendActionPerformed(e);
		}
	}

	private void onDeleteActionPerformed(ActionEvent e) {

		try {

			// $del

			Message message = this.endpoint.prepareMessage();
			message.createDelOperation(StatementUtil.fromRelationComponents(this.contextNodeXri, XDI3Segment.create("+friend"), this.targetContextNodeXri));

			this.endpoint.send(message);
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, a problem occurred while storing your Personal Data: " + ex.getMessage(), ex);
			return;
		}
	}

	public static interface FriendPanelDelegate {

		public void onFriendActionPerformed(ActionEvent e);
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		Row row1 = new Row();
		row1.setCellSpacing(new Extent(10, Extent.PX));
		add(row1);
		xdiPanel = new XdiPanel();
		RowLayoutData xdiPanelLayoutData = new RowLayoutData();
		xdiPanelLayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.CENTER));
		xdiPanel.setLayoutData(xdiPanelLayoutData);
		row1.add(xdiPanel);
		ImageIcon imageIcon1 = new ImageIcon();
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/danube/desktop/web/resource/image/friend.png");
		imageIcon1.setIcon(imageReference1);
		imageIcon1.setHeight(new Extent(48, Extent.PX));
		imageIcon1.setWidth(new Extent(48, Extent.PX));
		row1.add(imageIcon1);
		friendButton = new Button();
		friendButton.setStyleName("Plain");
		friendButton.setText("...");
		friendButton.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onFriendActionPerformed(e);
			}
		});
		row1.add(friendButton);
		deleteButton = new Button();
		deleteButton.setStyleName("Plain");
		ResourceImageReference imageReference2 = new ResourceImageReference(
				"/danube/desktop/web/resource/image/op-cancel.png");
		deleteButton.setIcon(imageReference2);
		RowLayoutData deleteButtonLayoutData = new RowLayoutData();
		deleteButtonLayoutData.setAlignment(new Alignment(Alignment.DEFAULT,
				Alignment.CENTER));
		deleteButton.setLayoutData(deleteButtonLayoutData);
		deleteButton.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onDeleteActionPerformed(e);
			}
		});
		row1.add(deleteButton);
	}
}

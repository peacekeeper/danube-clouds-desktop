package danube.discoverydemo.ui.xdi;

import java.util.ResourceBundle;

import nextapp.echo.app.Border;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Grid;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Row;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.layout.RowLayoutData;
import nextapp.echo.app.layout.SplitPaneLayoutData;
import xdi2.core.constants.XDIConstants;
import xdi2.messaging.Message;
import xdi2.messaging.MessageResult;
import xdi2.messaging.constants.XDIMessagingConstants;
import danube.discoverydemo.DiscoveryDemoApplication;
import danube.discoverydemo.ui.MessageDialog;
import danube.discoverydemo.xdi.XdiEndpoint;

public class XdiContentPane extends ContentPane {

	private static final long serialVersionUID = -6760462770679963055L;

	protected ResourceBundle resourceBundle;

	private XdiEndpoint endpoint;

	private Label identifierLabel;

	private GraphContentPane graphContentPane;

	/**
	 * Creates a new <code>XdiContentPane</code>.
	 */
	public XdiContentPane() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	/**
	 * Returns the user's application instance, cast to its specific type.
	 *
	 * @return The user's application instance.
	 */
	protected DiscoveryDemoApplication getApplication() {
		return (DiscoveryDemoApplication) getApplicationInstance();
	}

	private void refresh() {

		try {

			Message message = this.endpoint.prepareOperation(XDIMessagingConstants.XRI_S_GET, XDIConstants.XRI_S_ROOT);
			MessageResult messageResult = this.endpoint.send(message);

			this.identifierLabel.setText(this.getEndpoint().getIdentifier());
			this.graphContentPane.setGraph(messageResult.getGraph());
		} catch (Exception ex) {

			MessageDialog.problem("Sorry, a problem occurred while retrieving your Personal Data: " + ex.getMessage(), ex);
			return;
		}
	}

	public void setEndpoint(XdiEndpoint endpoint) {

		this.endpoint = endpoint;

		this.refresh();
	}

	public XdiEndpoint getEndpoint() {

		return this.endpoint;
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setInsets(new Insets(new Extent(10, Extent.PX)));
		SplitPane splitPane1 = new SplitPane();
		splitPane1.setStyleName("Default");
		splitPane1.setOrientation(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		splitPane1.setSeparatorHeight(new Extent(10, Extent.PX));
		splitPane1.setSeparatorVisible(false);
		add(splitPane1);
		Column column1 = new Column();
		column1.setCellSpacing(new Extent(10, Extent.PX));
		SplitPaneLayoutData column1LayoutData = new SplitPaneLayoutData();
		column1LayoutData.setMinimumSize(new Extent(110, Extent.PX));
		column1LayoutData.setMaximumSize(new Extent(110, Extent.PX));
		column1LayoutData.setOverflow(SplitPaneLayoutData.OVERFLOW_HIDDEN);
		column1.setLayoutData(column1LayoutData);
		splitPane1.add(column1);
		Row row1 = new Row();
		row1.setCellSpacing(new Extent(10, Extent.PX));
		row1.setBorder(new Border(new Extent(3, Extent.PX), Color.BLACK,
				Border.STYLE_SOLID));
		column1.add(row1);
		Label label1 = new Label();
		label1.setStyleName("Default");
		label1.setText("This window displays the raw XDI data of an object in your Personal Cloud.");
		RowLayoutData label1LayoutData = new RowLayoutData();
		label1LayoutData.setInsets(new Insets(new Extent(10, Extent.PX)));
		label1.setLayoutData(label1LayoutData);
		row1.add(label1);
		Column column2 = new Column();
		column2.setCellSpacing(new Extent(5, Extent.PX));
		column1.add(column2);
		Grid grid1 = new Grid();
		grid1.setOrientation(Grid.ORIENTATION_HORIZONTAL);
		grid1.setColumnWidth(0, new Extent(120, Extent.PX));
		grid1.setSize(2);
		column2.add(grid1);
		Label label3 = new Label();
		label3.setStyleName("Default");
		label3.setText("Endpoint Identifier:");
		grid1.add(label3);
		identifierLabel = new Label();
		identifierLabel.setStyleName("Bold");
		identifierLabel.setText("...");
		grid1.add(identifierLabel);
		graphContentPane = new GraphContentPane();
		splitPane1.add(graphContentPane);
	}
}

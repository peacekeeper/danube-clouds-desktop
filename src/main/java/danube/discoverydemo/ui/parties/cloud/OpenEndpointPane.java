package danube.discoverydemo.ui.parties.cloud;

import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.TimeZone;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Button;
import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.FillImage;
import nextapp.echo.app.Grid;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.SplitPaneLayoutData;
import xdi2.core.xri3.XDI3Segment;
import danube.discoverydemo.DiscoveryDemoApplication;
import danube.discoverydemo.ui.xdi.XdiPanel;
import danube.discoverydemo.xdi.XdiEndpoint;

public class OpenEndpointPane extends ContentPane {

	private static final long serialVersionUID = -8974342563665273260L;

	private static final SimpleDateFormat DATEFORMAT;

	static {

		DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		DATEFORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	protected ResourceBundle resourceBundle;

	private XdiEndpoint endpoint;

	private Label identifierTextField;
	private Label canonicalTextField;
	private Label endpointTextField;
	private XdiPanel xdiPanel;

	/**
	 * Creates a new <code>LoggedInContentPane</code>.
	 */
	public OpenEndpointPane() {
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

		this.identifierTextField.setText(this.endpoint.getIdentifier());
		this.canonicalTextField.setText(this.endpoint.getCloudNumber().toString());
		this.endpointTextField.setText(this.endpoint.getXdiClient().toString());

		this.xdiPanel.setEndpoint(this.endpoint);
	}

	public void setEndpoint(XdiEndpoint endpoint) {

		// refresh

		this.endpoint = endpoint;

		this.refresh();
	}

	private void onCloseActionPerformed(ActionEvent e) {

//		DiscoveryDemoApplication.getApp().closeEndpoint();
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
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/danube/desktop/web/resource/image/separator.png");
		splitPane1.setSeparatorVerticalImage(new FillImage(imageReference1));
		splitPane1.setSeparatorHeight(new Extent(10, Extent.PX));
		splitPane1.setSeparatorVisible(true);
		add(splitPane1);
		Column column1 = new Column();
		column1.setCellSpacing(new Extent(10, Extent.PX));
		splitPane1.add(column1);
		Grid grid1 = new Grid();
		grid1.setWidth(new Extent(100, Extent.PERCENT));
		grid1.setInsets(new Insets(new Extent(5, Extent.PX)));
		column1.add(grid1);
		Label label2 = new Label();
		label2.setStyleName("Default");
		label2.setText("Identifier:");
		grid1.add(label2);
		identifierTextField = new Label();
		identifierTextField.setStyleName("Bold");
		identifierTextField.setText("...");
		grid1.add(identifierTextField);
		Label label3 = new Label();
		label3.setStyleName("Default");
		label3.setText("Canonical:");
		grid1.add(label3);
		canonicalTextField = new Label();
		canonicalTextField.setStyleName("Bold");
		canonicalTextField.setText("...");
		grid1.add(canonicalTextField);
		Label label5 = new Label();
		label5.setStyleName("Default");
		label5.setText("Endpoint:");
		grid1.add(label5);
		endpointTextField = new Label();
		endpointTextField.setStyleName("Bold");
		endpointTextField.setText("...");
		grid1.add(endpointTextField);
		Row row1 = new Row();
		row1.setAlignment(new Alignment(Alignment.RIGHT, Alignment.DEFAULT));
		row1.setCellSpacing(new Extent(10, Extent.PX));
		SplitPaneLayoutData row1LayoutData = new SplitPaneLayoutData();
		row1LayoutData.setMinimumSize(new Extent(40, Extent.PX));
		row1LayoutData.setMaximumSize(new Extent(40, Extent.PX));
		row1LayoutData.setOverflow(SplitPaneLayoutData.OVERFLOW_HIDDEN);
		row1.setLayoutData(row1LayoutData);
		splitPane1.add(row1);
		xdiPanel = new XdiPanel();
		row1.add(xdiPanel);
		Button button1 = new Button();
		button1.setStyleName("Default");
		button1.setText("Close Personal Cloud");
		button1.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onCloseActionPerformed(e);
			}
		});
		row1.add(button1);
	}
}

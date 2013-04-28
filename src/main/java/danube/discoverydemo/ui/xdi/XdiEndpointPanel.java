package danube.discoverydemo.ui.xdi;

import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.TimeZone;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Button;
import nextapp.echo.app.Column;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Grid;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.Row;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.SplitPaneLayoutData;
import xdi2.client.http.XDIHttpClient;
import danube.discoverydemo.ui.MainWindow;
import danube.discoverydemo.xdi.XdiEndpoint;

public class XdiEndpointPanel extends Panel {

	private static final long serialVersionUID = -8974342563665273260L;

	protected ResourceBundle resourceBundle;

	private XdiEndpoint endpoint;

	private Label xriLabel;
	private Label cloudNumberLabel;
	private Label endpointUriLabel;

	public XdiEndpointPanel() {
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

		this.xriLabel.setText(this.endpoint.getXri().toString());
		this.cloudNumberLabel.setText(this.endpoint.getCloudNumber().toString());
		this.endpointUriLabel.setText(((XDIHttpClient) this.endpoint.getXdiClient()).getEndpointUri().toString());
	}

	public void setData(XdiEndpoint endpoint) {

		// refresh

		this.endpoint = endpoint;

		this.refresh();
	}

	private void onXdiActionPerformed(ActionEvent e) {

		XdiWindowPane xdiWindowPane = new XdiWindowPane();
		xdiWindowPane.setEndpoint(this.endpoint);

		MainWindow.findMainContentPane(this).add(xdiWindowPane);
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
		splitPane1.setResizable(false);
		splitPane1.setSeparatorHeight(new Extent(10, Extent.PX));
		splitPane1.setSeparatorVisible(false);
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
		xriLabel = new Label();
		xriLabel.setStyleName("Bold");
		xriLabel.setText("...");
		grid1.add(xriLabel);
		Label label3 = new Label();
		label3.setStyleName("Default");
		label3.setText("Cloud Number:");
		grid1.add(label3);
		cloudNumberLabel = new Label();
		cloudNumberLabel.setStyleName("Bold");
		cloudNumberLabel.setText("...");
		grid1.add(cloudNumberLabel);
		Label label5 = new Label();
		label5.setStyleName("Default");
		label5.setText("Endpoint URI:");
		grid1.add(label5);
		endpointUriLabel = new Label();
		endpointUriLabel.setStyleName("Bold");
		endpointUriLabel.setText("...");
		grid1.add(endpointUriLabel);
		Row row1 = new Row();
		row1.setAlignment(new Alignment(Alignment.RIGHT, Alignment.DEFAULT));
		row1.setCellSpacing(new Extent(10, Extent.PX));
		SplitPaneLayoutData row1LayoutData = new SplitPaneLayoutData();
		row1LayoutData.setMinimumSize(new Extent(40, Extent.PX));
		row1LayoutData.setMaximumSize(new Extent(40, Extent.PX));
		row1LayoutData.setOverflow(SplitPaneLayoutData.OVERFLOW_HIDDEN);
		row1.setLayoutData(row1LayoutData);
		splitPane1.add(row1);
		Button button1 = new Button();
		button1.setStyleName("Default");
		button1.setText("button1");
		button1.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onXdiActionPerformed(e);
			}
		});
		row1.add(button1);
	}
}

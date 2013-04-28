package danube.discoverydemo.ui.xdi;

import java.util.ResourceBundle;

import nextapp.echo.app.Button;
import nextapp.echo.app.Panel;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import danube.discoverydemo.ui.DeveloperModeComponent;
import danube.discoverydemo.ui.MainWindow;
import danube.discoverydemo.xdi.XdiEndpoint;
import nextapp.echo.app.Label;
import nextapp.echo.app.Row;
import nextapp.echo.app.layout.RowLayoutData;
import nextapp.echo.app.Alignment;

public class XdiEndpointPanel extends Panel implements DeveloperModeComponent {

	private static final long serialVersionUID = -5082464847478633075L;

	protected ResourceBundle resourceBundle;

	private XdiEndpoint endpoint;

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

	public void setEndpoint(XdiEndpoint endpoint) {

		this.endpoint = endpoint;
	}

	public XdiEndpoint getEndpoint() {

		return this.endpoint;
	}

	private void onButtonActionPerformed(ActionEvent e) {

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
		this.setVisible(false);
		Row row1 = new Row();
		add(row1);
		Label label1 = new Label();
		label1.setStyleName("Default");
		label1.setText("Cloud Number:");
		row1.add(label1);
		cloudNumberLabel = new Label();
		cloudNumberLabel.setStyleName("Default");
		cloudNumberLabel.setText("...");
		row1.add(cloudNumberLabel);
		Label label2 = new Label();
		label2.setStyleName("Default");
		label2.setText("Endpoint URI:");
		row1.add(label2);
		endpointUriLabel = new Label();
		endpointUriLabel.setStyleName("Default");
		endpointUriLabel.setText("...");
		row1.add(endpointUriLabel);
		Button button1 = new Button();
		button1.setStyleName("Plain");
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/danube/discoverydemo/resource/image/xdi.png");
		button1.setIcon(imageReference1);
		RowLayoutData button1LayoutData = new RowLayoutData();
		button1LayoutData.setAlignment(new Alignment(Alignment.RIGHT,
				Alignment.DEFAULT));
		button1.setLayoutData(button1LayoutData);
		button1.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onButtonActionPerformed(e);
			}
		});
		row1.add(button1);
	}
}

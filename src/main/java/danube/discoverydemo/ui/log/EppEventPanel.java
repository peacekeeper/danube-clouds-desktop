package danube.discoverydemo.ui.log;

import ibrokerkit.epptools4java.EppEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Font;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import danube.discoverydemo.ui.MainWindow;
import danube.discoverydemo.ui.xdi.EppEventWindowPane;

public class EppEventPanel extends Panel {

	private static final long serialVersionUID = -5082464847478633075L;

	private static final DateFormat DATEFORMAT = new SimpleDateFormat("HH:mm:ss.SSS");

	protected ResourceBundle resourceBundle;

	private EppEvent eppEvent;

	private Label timestampLabel;
	private Button button;
	private Label summaryLabel;

	/**
	 * Creates a new <code>ClaimPanel</code>.
	 */
	public EppEventPanel() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	@Override
	public void dispose() {

		super.dispose();
	}

	private void refresh() {

		this.timestampLabel.setText(DATEFORMAT.format(this.eppEvent.getBeginTimestamp()));
		this.summaryLabel.setText(this.eppEvent.getEppCommand().getClientTransactionId());
	}

	public void setData(EppEvent eppEvent) {

		this.eppEvent = eppEvent;

		this.refresh();
	}

	private void onButtonActionPerformed(ActionEvent e) {

		EppEventWindowPane eppEventWindowPane = new EppEventWindowPane();
		eppEventWindowPane.setData(this.eppEvent);

		MainWindow.findMainContentPane(this).add(eppEventWindowPane);
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
		timestampLabel = new Label();
		timestampLabel.setStyleName("Default");
		timestampLabel.setText("...");
		timestampLabel.setFont(new Font(new Font.Typeface("Courier New",
				new Font.Typeface(" monospace")), Font.PLAIN, new Extent(10,
				Extent.PT)));
		timestampLabel.setForeground(Color.BLACK);
		row1.add(timestampLabel);
		button = new Button();
		button.setStyleName("Plain");
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/danube/discoverydemo/resource/image/eppsuccess.png");
		button.setIcon(imageReference1);
		button.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;
	
			public void actionPerformed(ActionEvent e) {
				onButtonActionPerformed(e);
			}
		});
		row1.add(button);
		summaryLabel = new Label();
		summaryLabel.setStyleName("Default");
		summaryLabel.setText("...");
		summaryLabel.setForeground(Color.BLACK);
		row1.add(summaryLabel);
	}
}

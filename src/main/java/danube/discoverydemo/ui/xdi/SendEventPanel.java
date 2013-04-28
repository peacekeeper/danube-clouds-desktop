package danube.discoverydemo.ui.xdi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.ResourceBundle;

import nextapp.echo.app.Button;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Font;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.Row;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import xdi2.client.events.XDISendErrorEvent;
import xdi2.client.events.XDISendEvent;
import xdi2.client.events.XDISendSuccessEvent;
import xdi2.messaging.Operation;
import danube.discoverydemo.ui.MainWindow;

public class SendEventPanel extends Panel {

	private static final long serialVersionUID = -5082464847478633075L;

	private static final ImageReference IMAGEREFERENCE_SUCCESS = new ResourceImageReference("/danube/discoverydemo/resource/image/transactionsuccess.png");
	private static final ImageReference IMAGEREFERENCE_FAILURE = new ResourceImageReference("/danube/discoverydemo/resource/image/transactionfailure.png");

	private static final DateFormat DATEFORMAT = new SimpleDateFormat("HH:mm:ss.SSS");

	protected ResourceBundle resourceBundle;

	private XDISendEvent sendEvent;

	private Label timestampLabel;
	private Button button;
	private Label summaryLabel;

	/**
	 * Creates a new <code>ClaimPanel</code>.
	 */
	public SendEventPanel() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	@Override
	public void dispose() {

		super.dispose();
	}

	private void refresh() {

		StringBuffer buffer = new StringBuffer();
		Iterator<Operation> operations = this.sendEvent.getMessageEnvelope().getOperations();

		while (operations.hasNext()) {

			Operation operation = operations.next();
			if (buffer.length() > 0) buffer.append(", ");
			buffer.append(operation.getOperationXri().toString());
		}

		if (this.sendEvent instanceof XDISendSuccessEvent) {

			this.button.setIcon(IMAGEREFERENCE_SUCCESS);

			if (buffer.length() > 0) buffer.append(" / ");
			buffer.append(Integer.toString(((XDISendSuccessEvent) this.sendEvent).getMessageResult().getGraph().getRootContextNode().getAllStatementCount()) + " result statements.");
		} else if (this.sendEvent instanceof XDISendErrorEvent) {

			this.button.setIcon(IMAGEREFERENCE_FAILURE);

			if (buffer.length() > 0) buffer.append(" / ");
			buffer.append(((XDISendErrorEvent) this.sendEvent).getMessageResult().getErrorString());
		}

		this.timestampLabel.setText(DATEFORMAT.format(this.sendEvent.getBeginTimestamp()));
		this.summaryLabel.setText(buffer.toString());
	}

	public void setSendEvent(XDISendEvent sendEvent) {

		this.sendEvent = sendEvent;

		this.refresh();
	}

	public XDISendEvent getSendEvent() {

		return this.sendEvent;
	}

	private void onButtonActionPerformed(ActionEvent e) {

		SendEventWindowPane sendEventWindowPane = new SendEventWindowPane();
		sendEventWindowPane.setSendEvent(this.sendEvent);

		MainWindow.findMainContentPane(this).add(sendEventWindowPane);
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
		row1.add(timestampLabel);
		button = new Button();
		button.setStyleName("Plain");
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
		row1.add(summaryLabel);
	}
}

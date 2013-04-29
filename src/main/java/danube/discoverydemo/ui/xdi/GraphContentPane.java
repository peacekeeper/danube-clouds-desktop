package danube.discoverydemo.ui.xdi;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;
import java.util.ResourceBundle;

import nextapp.echo.app.Button;
import nextapp.echo.app.CheckBox;
import nextapp.echo.app.Column;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Row;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.event.ChangeEvent;
import nextapp.echo.app.event.ChangeListener;
import xdi2.core.Graph;
import xdi2.core.impl.memory.MemoryGraphFactory;
import xdi2.core.io.XDIWriterRegistry;
import xdi2.core.util.CopyUtil;
import danube.discoverydemo.DiscoveryDemoApplication;
import danube.discoverydemo.ui.MessageDialog;
import danube.discoverydemo.ui.html.HtmlLabel;
import danube.discoverydemo.util.HtmlUtil;
import danube.discoverydemo.xdi.XdiUtil;

public class GraphContentPane extends ContentPane {

	private static final long serialVersionUID = 1L;

	protected ResourceBundle resourceBundle;

	private Graph graph;
	private String format;
	private String originalHtml;

	private HtmlLabel htmlLabel;
	private CheckBox impliedCheckbox;
	private CheckBox orderedCheckbox;
	private CheckBox innerCheckbox;
	private CheckBox prettyCheckbox;

	public GraphContentPane() {
		super();

		// Add design-time configured components.
		initComponents();

		this.format = "XDI DISPLAY";
		this.originalHtml = this.htmlLabel.getHtml();
	}

	private void refresh() {

		StringWriter writer = new StringWriter();

		try {

			Properties parameters = new Properties();
			parameters.put(XDIWriterRegistry.PARAMETER_IMPLIED, this.impliedCheckbox.isSelected() ? "1" : "0");
			parameters.put(XDIWriterRegistry.PARAMETER_ORDERED, this.orderedCheckbox.isSelected() ? "1" : "0");
			parameters.put(XDIWriterRegistry.PARAMETER_INNER, this.innerCheckbox.isSelected() ? "1" : "0");
			parameters.put(XDIWriterRegistry.PARAMETER_PRETTY, this.prettyCheckbox.isSelected() ? "1" : "0");

			XDIWriterRegistry.forFormat(this.format, parameters).write(this.graph, writer);

			String html = this.originalHtml;
			html = html.replace("<!-- $$$ -->", HtmlUtil.htmlEncode(writer.getBuffer().toString(), true, false));
			this.htmlLabel.setHtml(html);
		} catch (IOException ex) {

			MessageDialog.problem("Sorry, a problem occurred while serializing the XDI graph:" + ex.getMessage(), ex);

			this.htmlLabel.setHtml("");
		}
	}

	public void setGraph(Graph graph) {

		this.graph = MemoryGraphFactory.getInstance().openGraph();
		CopyUtil.copyGraph(graph, this.graph, new XdiUtil.SecretTokenCensoringCopyStrategy());

		this.refresh();
	}

	public Graph getGraph() {

		return this.graph;
	}

	private void onXDIDISPLAYActionPerformed(ActionEvent e) {

		this.format = "XDI DISPLAY";
		this.refresh();
	}

	private void onXDIJSONActionPerformed(ActionEvent e) {

		this.format = "XDI/JSON";
		this.refresh();
	}

	private void onCheckboxActionPerformed(ActionEvent e) {

		this.refresh();
	}

	private void onCheckboxStateChanged(ChangeEvent e) {

		this.refresh();
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setInsets(new Insets(new Extent(10, Extent.PX)));
		Column column1 = new Column();
		column1.setCellSpacing(new Extent(10, Extent.PX));
		add(column1);
		Row row1 = new Row();
		row1.setCellSpacing(new Extent(10, Extent.PX));
		column1.add(row1);
		Button button2 = new Button();
		button2.setStyleName("Default");
		button2.setText("XDI DISPLAY");
		button2.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onXDIDISPLAYActionPerformed(e);
			}
		});
		row1.add(button2);
		Button button1 = new Button();
		button1.setStyleName("Default");
		button1.setText("XDI/JSON");
		button1.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onXDIJSONActionPerformed(e);
			}
		});
		row1.add(button1);
		impliedCheckbox = new CheckBox();
		impliedCheckbox.setText("implied=1");
		impliedCheckbox.setSelected(false);
		impliedCheckbox.addChangeListener(new ChangeListener() {
			private static final long serialVersionUID = 1L;

			public void stateChanged(ChangeEvent e) {
				onCheckboxStateChanged(e);
			}
		});
		impliedCheckbox.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onCheckboxActionPerformed(e);
			}
		});
		row1.add(impliedCheckbox);
		orderedCheckbox = new CheckBox();
		orderedCheckbox.setSelected(true);
		orderedCheckbox.setText("ordered=1");
		orderedCheckbox.addChangeListener(new ChangeListener() {
			private static final long serialVersionUID = 1L;

			public void stateChanged(ChangeEvent e) {
				onCheckboxStateChanged(e);
			}
		});
		orderedCheckbox.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onCheckboxActionPerformed(e);
			}
		});
		row1.add(orderedCheckbox);
		innerCheckbox = new CheckBox();
		innerCheckbox.setSelected(true);
		innerCheckbox.setText("inner=1");
		innerCheckbox.addChangeListener(new ChangeListener() {
			private static final long serialVersionUID = 1L;

			public void stateChanged(ChangeEvent e) {
				onCheckboxStateChanged(e);
			}
		});
		innerCheckbox.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onCheckboxActionPerformed(e);
			}
		});
		row1.add(innerCheckbox);
		prettyCheckbox = new CheckBox();
		prettyCheckbox.setSelected(false);
		prettyCheckbox.setText("pretty=1");
		prettyCheckbox.addChangeListener(new ChangeListener() {
			private static final long serialVersionUID = 1L;

			public void stateChanged(ChangeEvent e) {
				onCheckboxStateChanged(e);
			}
		});
		prettyCheckbox.addActionListener(new ActionListener() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				onCheckboxActionPerformed(e);
			}
		});
		row1.add(prettyCheckbox);
		htmlLabel = new HtmlLabel();
		htmlLabel
		.setHtml("<div style=\"white-space:nowrap;font-family:monospace;\"><pre><!-- $$$ --></pre></div>");
		column1.add(htmlLabel);
	}
}

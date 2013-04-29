package danube.discoverydemo.ui.xdi;

import java.util.ResourceBundle;

import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import danube.discoverydemo.ui.html.HtmlLabel;
import danube.discoverydemo.util.HtmlUtil;

public class StringContentPane extends ContentPane {

	private static final long serialVersionUID = 1L;

	protected ResourceBundle resourceBundle;

	private String string;
	private String format;
	private String originalHtml;

	private HtmlLabel htmlLabel;

	public StringContentPane() {
		super();

		// Add design-time configured components.
		initComponents();

		this.format = "XDI DISPLAY";
		this.originalHtml = this.htmlLabel.getHtml();
	}

	private void refresh() {

		String html = this.originalHtml;
		html = html.replace("<!-- $$$ -->", HtmlUtil.htmlEncode(this.string, true, false));
		this.htmlLabel.setHtml(html);
	}

	public void setData(String string) {

		this.string = string;

		this.refresh();
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setInsets(new Insets(new Extent(10, Extent.PX)));
		htmlLabel = new HtmlLabel();
		htmlLabel
				.setHtml("<div style=\"white-space:nowrap;font-family:monospace;\"><pre><!-- $$$ --></pre></div>");
		add(htmlLabel);
	}
}

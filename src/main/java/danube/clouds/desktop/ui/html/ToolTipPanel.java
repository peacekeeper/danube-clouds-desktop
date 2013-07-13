package danube.clouds.desktop.ui.html;

import java.util.ResourceBundle;

import nextapp.echo.app.Extent;
import nextapp.echo.app.Label;
import nextapp.echo.app.Panel;
import nextapp.echo.app.ResourceImageReference;
import danube.clouds.desktop.DanubeCloudsDesktopApplication;
import echopoint.ImageIcon;
import echopoint.jquery.TooltipContainer;

public class ToolTipPanel extends TooltipContainer {

	private static final long serialVersionUID = 1L;

	protected ResourceBundle resourceBundle;

	/**
	 * Creates a new <code>TooltipPanel</code>.
	 */
	public ToolTipPanel() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	/**
	 * Returns the user's application instance, cast to its specific type.
	 *
	 * @return The user's application instance.
	 */
	protected DanubeCloudsDesktopApplication getApplication() {
		return (DanubeCloudsDesktopApplication) getApplicationInstance();
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		ImageIcon imageIcon7 = new ImageIcon();
		ResourceImageReference imageReference1 = new ResourceImageReference(
				"/danube/clouds/desktop/resource/image/tooltip.png");
		imageIcon7.setIcon(imageReference1);
		imageIcon7.setHeight(new Extent(24, Extent.PX));
		imageIcon7.setWidth(new Extent(24, Extent.PX));
		add(imageIcon7);
		Panel panel9 = new Panel();
		panel9.setStyleName("Tooltip");
		add(panel9);
		Label label1 = new Label();
		label1.setStyleName("Default");
		label1.setText("TOOLTIP HERE");
		panel9.add(label1);
	}
}

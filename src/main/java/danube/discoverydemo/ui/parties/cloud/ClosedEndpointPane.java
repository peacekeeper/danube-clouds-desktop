package danube.discoverydemo.ui.parties.cloud;


import java.util.ResourceBundle;

import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Panel;
import nextapp.echo.extras.app.TabPane;
import nextapp.echo.extras.app.layout.TabPaneLayoutData;

public class ClosedEndpointPane extends ContentPane {

	private static final long serialVersionUID = 46284183174314347L;

	protected ResourceBundle resourceBundle;

	private TabPane signInPanelTabPane;

	/**
	 * Creates a new <code>LoginContentPane</code>.
	 */
	public ClosedEndpointPane() {
		super();

		// Add design-time configured components.
		initComponents();
	}

	@Override
	public void init() {

		super.init();

		// SignInMethods

		this.signInPanelTabPane.removeAll();

		Panel signInPanel = new XriSignInPanel();

		TabPaneLayoutData tabPaneLayoutData = new TabPaneLayoutData();
		tabPaneLayoutData.setTitle("Personal Cloud");
		signInPanel.setLayoutData(tabPaneLayoutData);

		this.signInPanelTabPane.add(signInPanel);
	}

	/**
	 * Configures initial state of component.
	 * WARNING: AUTO-GENERATED METHOD.
	 * Contents will be overwritten.
	 */
	private void initComponents() {
		this.setInsets(new Insets(new Extent(10, Extent.PX)));
		signInPanelTabPane = new TabPane();
		signInPanelTabPane.setStyleName("Default");
		add(signInPanelTabPane);
	}
}

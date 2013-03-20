package edu.wpi.cs.wpisuitetng.modules.dropbox.view;

import javax.swing.JToolBar;

/**
 * The main view for the Dropbox toolbar 
 */
@SuppressWarnings("serial")
public class ToolbarView extends JToolBar {
	
	/** Contains the GUI components for the toolbar */
	private ToolbarPanel toolbarPanel;

	/**
	 * Construct the view and the inner panel
	 */
	public ToolbarView() {
		// Prevent this toolbar from being moved
		setFloatable(false);

		// Add the panel containing the toolbar buttons
		toolbarPanel = new ToolbarPanel();
		add(toolbarPanel);
	}
}

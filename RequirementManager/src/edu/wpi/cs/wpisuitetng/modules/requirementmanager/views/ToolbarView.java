package edu.wpi.cs.wpisuitetng.modules.requirementmanager.views;

import javax.swing.JToolBar;

@SuppressWarnings("serial")
public class ToolbarView extends JToolBar {
	
	/** The panel containing toolbar buttons */
	private final ToolbarPanel toolbarPanel;

	/**
	 * Construct this view and all components in it.
	 * @param boardModel 
	 */
	public ToolbarView() {
		
		// Prevent this toolbar from being moved
		setFloatable(false);
		
		// Add the panel containing the toolbar buttons
		toolbarPanel = new ToolbarPanel();
		add(toolbarPanel);
	}
}

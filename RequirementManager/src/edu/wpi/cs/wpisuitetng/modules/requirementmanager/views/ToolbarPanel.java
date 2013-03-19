package edu.wpi.cs.wpisuitetng.modules.requirementmanager.views;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ToolbarPanel extends JPanel {
	
	/**
	 * Construct the panel.
	 */
	public ToolbarPanel() {
		
		// Make this panel transparent, we want to see the JToolbar gradient beneath it
		this.setOpaque(false);
	}
}

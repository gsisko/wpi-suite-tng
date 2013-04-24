package edu.wpi.cs.wpisuitetng.modules.dropbox.view;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Contains the GUI components to be displayed in the toolbar
 * for the Dropbox module 
 */
public class ToolbarPanel extends JPanel {
	
	/** Refreshes the list of filenames */
	private final JButton btnRefresh;
	
	private final JButton btnAddFile;

	/**
	 * Constructs the panel and adds GUI components
	 */
	public ToolbarPanel() {

		// Make this panel transparent, we want to see the JToolbar gradient beneath it
		this.setOpaque(false);

		// Construct the refresh button and add it to this panel
		btnRefresh = new JButton("Refresh");
		btnAddFile = new JButton("Add File");
		
		// Add the button to this panel
		add(btnRefresh);
		add(btnAddFile);
	}
	
	public JButton getRefreshButton() {
		return btnRefresh;
	}
	
	public JButton getAddFileButton() {
		return btnAddFile;
	}
}

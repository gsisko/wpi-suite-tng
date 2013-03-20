package edu.wpi.cs.wpisuitetng.modules.dropbox.view;

import javax.swing.JPanel;

/**
 * The main GUI view for the Dropbox module 
 */
@SuppressWarnings("serial")
public class MainView extends JPanel {

	/** A panel containing the main GUI */
	private final DropboxPanel dropboxPanel;
	
	/**
	 * Constructs the view and adds the inner panel
	 */
	public MainView() {
		dropboxPanel = new DropboxPanel();
		add(dropboxPanel);
	}
}

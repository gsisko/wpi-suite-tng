package edu.wpi.cs.wpisuitetng.modules.dropbox.view;

import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.dropbox.controller.GetFileController;
import edu.wpi.cs.wpisuitetng.modules.dropbox.model.FileListModel;

/**
 * The main GUI view for the Dropbox module 
 */
@SuppressWarnings("serial")
public class MainView extends JPanel {

	/** A panel containing the main GUI */
	private final DropboxPanel dropboxPanel;
	
	private final FileListModel model;
	
	/**
	 * Constructs the view and adds the inner panel
	 */
	public MainView(FileListModel model) {
		this.model = model;
		dropboxPanel = new DropboxPanel(model);
		add(dropboxPanel);
		
		dropboxPanel.getFilesList().addMouseListener(new GetFileController(model));
	}
}

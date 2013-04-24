package edu.wpi.cs.wpisuitetng.modules.dropbox.view;

import javax.swing.JToolBar;

import edu.wpi.cs.wpisuitetng.modules.dropbox.controller.AddFileController;
import edu.wpi.cs.wpisuitetng.modules.dropbox.controller.GetFileIndexController;
import edu.wpi.cs.wpisuitetng.modules.dropbox.model.FileListModel;

/**
 * The main view for the Dropbox toolbar 
 */
@SuppressWarnings("serial")
public class ToolbarView extends JToolBar {
	
	/** Contains the GUI components for the toolbar */
	private ToolbarPanel toolbarPanel;
	
	private final FileListModel model;

	/**
	 * Construct the view and the inner panel
	 */
	public ToolbarView(FileListModel model) {
		
		// Keep track of the model
		this.model = model;
		
		// Prevent this toolbar from being moved
		setFloatable(false);

		// Add the panel containing the toolbar buttons
		toolbarPanel = new ToolbarPanel();
		add(toolbarPanel);
		
		// Add controllers to the buttons
		toolbarPanel.getAddFileButton().addActionListener(new AddFileController());
		toolbarPanel.getRefreshButton().addActionListener(new GetFileIndexController(model));
	}
}

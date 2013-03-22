package edu.wpi.cs.wpisuitetng.modules.dropbox.view;

import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
		
		// Listen for double click events on files in the list
		dropboxPanel.getFilesList().addMouseListener(new GetFileController(model, dropboxPanel));
		
		dropboxPanel.getSaveLocallyButton().addActionListener(new GetFileController(model, dropboxPanel));
		
		// Enable the save locally button when a file is selected
		dropboxPanel.getFilesList().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (dropboxPanel.getFilesList().isSelectionEmpty()) {
					dropboxPanel.getSaveLocallyButton().setEnabled(false);
				}
				else {
					dropboxPanel.getSaveLocallyButton().setEnabled(true);
				}
			}
			
		});
	}
}

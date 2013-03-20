package edu.wpi.cs.wpisuitetng.modules.dropbox.view;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetng.modules.dropbox.model.FileListModel;

/**
 * A JPanel to display the contents of the dropbox and allow
 * new files to be added and existing files to be removed 
 */
@SuppressWarnings("serial")
public class DropboxPanel extends JPanel {
	
	/** Displays the filenames */
	private final JList lstFiles;
	
	/** The model for the filenames list */
	private final FileListModel lstFilesModel;

	/**
	 * Constructs the panel and adds components
	 */
	public DropboxPanel() {
		
		// Construct the list model
		lstFilesModel = new FileListModel();
		
		// Construct the list and adjust the font
		lstFiles = new JList(lstFilesModel);
		lstFiles.setFont(lstFiles.getFont().deriveFont(11));
		
		// Use the BoxLayout manager for this panel
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		// Place the filenames list in a scroll pane
		JScrollPane lstScrollPane = new JScrollPane(lstFiles);
		lstScrollPane.setPreferredSize(new Dimension(500,400));
		
		// Add the components to the panel
		add(Box.createVerticalStrut(20)); // leave a 20 pixel gap
		add(lstScrollPane);
	}
}

package edu.wpi.cs.wpisuitetng.modules.dropbox.view;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
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
	private final FileListModel model;
	
	/**
	 * Constructs the panel and adds components
	 * @param model containing the list of file names in the dropbox
	 */
	public DropboxPanel(FileListModel model) {
		
		this.model = model;
		
		// Construct the list and adjust the font
		lstFiles = new JList(model);
		lstFiles.setFont(lstFiles.getFont().deriveFont(11));
		
		// Use the BoxLayout manager for this panel
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setAlignmentX(CENTER_ALIGNMENT);
		
		// Place the filenames list in a scroll pane
		JScrollPane lstScrollPane = new JScrollPane(lstFiles);
		lstScrollPane.setPreferredSize(new Dimension(400,250));
		
		// Add the components to the panel
		add(Box.createVerticalStrut(20)); // leave a 20 pixel gap
		add(lstScrollPane);
	}
	
	public JList getFilesList() {
		return lstFiles;
	}
}

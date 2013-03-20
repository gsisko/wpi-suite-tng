package edu.wpi.cs.wpisuitetng.modules.dropbox.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

/**
 * A list model to maintain the list of filenames to be displayed
 * in the GUI
 */
@SuppressWarnings("serial")
public class FileListModel extends AbstractListModel {
	
	/** A list of filenames */
	private List<String> fileNames;
	
	/**
	 * Constructs a new model that is empty
	 */
	public FileListModel() {
		fileNames = new ArrayList<String>();
		fileNames.add("File 1");
		fileNames.add("File 2");
		fileNames.add("File 3");
	}

	/*
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return fileNames.size();
	}

	/*
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public Object getElementAt(int index) {
		return fileNames.get(index);
	}

}

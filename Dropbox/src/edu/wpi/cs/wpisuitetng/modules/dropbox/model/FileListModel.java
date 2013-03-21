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
	private FileIndex model;
	
	/**
	 * Constructs a new model that is empty
	 */
	public FileListModel() {
		model = new FileIndex();
	}
	
	public void setModel(FileIndex model) {
		this.model = model;
		this.fireContentsChanged(this, 0, getSize());
	}
	
	public FileIndex getModel() {
		return model;
	}

	/*
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return model.getFileNames().size();
	}

	/*
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public Object getElementAt(int index) {
		return model.getFileNames().get(index);
	}

}

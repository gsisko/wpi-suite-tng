/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *		Robert Dabrowski
 *		Danielle LaRose
 *		Edison Jimenez
 *		Christian Gonzalez
 *		Mike Calder
 *		John Bosworth
 *		Paula Rudy
 *		Gabe Isko
 *		Bangyan Zhang
 *		Cassie Hudson
 *		Robert Smieja
 *		Alex Solomon
 *		Brian Hetherman
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Note;

@SuppressWarnings({"serial","rawtypes"})
public class NoteListModel extends AbstractListModel {
	
	/** The list of messages on the board */
	private List<Note> notes;
	
	/**
	 * Constructs a new board with no messages.
	 */
	public NoteListModel() {
		notes = new ArrayList<Note>();
	}

	/**
	 * Adds the given message to the board
	 * 
	 * @param newMessage the new message to add
	 */
	public void addMessage(Note newMessage) {
		// Add the message
		notes.add(newMessage);
		
		// Notify the model that it has changed so the GUI will be udpated
		this.fireIntervalAdded(this, 0, 0);
	}
	
	/**
	 * Adds the given array of messages to the board
	 * 
	 * @param notes the array of messages to add
	 */
	public void addMessages(Note[] notes) {
		for (int i = 0; i < notes.length; i++) {
			this.notes.add(notes[i]);
		}
		this.fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
	}
	
	/**
	 * Removes all messages from this model
	 * 
	 * NOTE: One cannot simply construct a new instance of
	 * the model, because other classes in this module have
	 * references to it. Hence, we manually remove each message
	 * from the model.
	 */
	public void emptyModel() {
		int oldSize = getSize();
		Iterator<Note> iterator = notes.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		this.fireIntervalRemoved(this, 0, Math.max(oldSize - 1, 0));
	}
	
	/* 
	 * Returns the message at the given index. This method is called
	 * internally by the JList in BoardPanel. Note this method returns
	 * elements in reverse order, so newest messages are returned first.
	 * 
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public Note getElementAt(int index) {
		return notes.get(notes.size() - 1 - index);
	}

	/*
	 * Returns the number of messages in the model. Also used internally
	 * by the JList in BoardPanel.
	 * 
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return notes.size();
	}
}

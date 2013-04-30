/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team 5 D13
 * 
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.note;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Note;

@SuppressWarnings({"serial","rawtypes"})
public class NoteListModel extends AbstractListModel {
	
	/** The list of messages on the board */
	private List<Note> notes;
	
	/** Constructs a new model with no messages.
	 */
	public NoteListModel() {
		notes = new ArrayList<Note>();
	}

	/** Adds the given note to the board
	 * @param newMessage the new note to add
	 */
	public void addMessage(Note newMessage) {
		// Add the message
		notes.add(newMessage);
		
		// Notify the model that it has changed so the GUI will be updated
		this.fireIntervalAdded(this, 0, 0);
	}
	
	/** Adds the given array of notes to the board
	 * @param notes the array of notes to add
	 */
	public void addMessages(Note[] notes) {
		for (int i = 0; i < notes.length; i++) {
			this.notes.add(notes[i]);
		}
		this.fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
	}
	
	/** Removes all notes from this model
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
	
	/** Returns the note at the given index. Note this method returns
	 * elements in reverse order, so newest notes are returned first.
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public Note getElementAt(int index) {
		return notes.get(notes.size() - 1 - index);
	}

	/** Returns the number of notes in the model. 
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return notes.size();
	}
}

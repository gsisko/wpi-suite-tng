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

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Note;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementTab.Mode;

/**
 * This panel is added to the RequirementTabPanel and 
 * contains all the gui components involving notes:
 * -a panel to hold the list of notes
 * -a text area for a user to input a new note
 * -a save button to save the new note
 */
@SuppressWarnings({"serial"})
public class NoteTab extends JPanel {

	//The fillable components
	private  JTextArea txtMessage;//The message text field 
	private JScrollPane scrollMessage; // ScrollPane that the message box will be held in 

	//The save button
	private JButton saveButton;

	//The variables to hold information about the current instance of the panel
	private Note currentNote;//Stores the note currently open for editing or creation

	// The parent 
	private RequirementTab parent; //Stores the RequirementPanel that contains the panel

	//A boolean indicating if input is enabled on the form 
	protected boolean inputEnabled;

	//The noteListModel. This holds the notes to be displayed in the "noteList" panel
	private NoteListModel noteListModel;

	//The panel to hold all the NotePanels (containing all the notes) to display
	private ListOfNotePanel noteList;

	//A scroll pane to hold the "noteList"
	JScrollPane listScrollPane;

	/**
	 * The constructor for NotePanel;
	 * Construct the panel, the components, and add the
	 * components to the panel.
	 * @param reqPanelParent	The parent of this tab
	 */
	public NoteTab(RequirementTab reqPanelParent) {

		parent = reqPanelParent; //Set the RequirementPanel that contains this instance of this panel

		// Create and set the layout manager
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		//Set an empty border for spacing
		setBorder(BorderFactory.createEmptyBorder(5, 3, 3, 3)); 

		// Construct the noteListmodel
		noteListModel = new NoteListModel();

		ArrayList<Note> notes = parent.getCurrentRequirement().getNotes();
		for (int i = 0; i < notes.size(); i++) {
			noteListModel.addMessage(notes.get(i));
		}

		//Construct the noteList, using the previously defined empty model
		noteList  = new ListOfNotePanel(noteListModel);

		// Put the noteList in a scroll pane
		listScrollPane = new JScrollPane(noteList);

		// Construct the other components to be displayed
		txtMessage = new JTextArea("", 1, 1);
		saveButton = new JButton("Add Note");

		// Set the txtMessage component to wrap
		txtMessage.setLineWrap(true);
		txtMessage.setWrapStyleWord(true);

		// Put txtMessage in a scroll pane
		scrollMessage = new JScrollPane(txtMessage);
		
		// Add key listener to txtMessage
		txtMessage.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {
				if (!txtMessage.getText().equals("")) {
					parent.getAttributePanel().changeField(txtMessage, 9, true);
				} else {
					parent.getAttributePanel().changeField(txtMessage, 9, false);
				}
			}
		});

		// Set the dimensions of the panel elements
		listScrollPane.setPreferredSize(new Dimension(580, 300));
		scrollMessage.setPreferredSize(new Dimension(580, 100));
		saveButton.setPreferredSize(new Dimension(120, 40));

		//Disable the txtMessage and saveButton if this is a new requirement
		if ((parent.getMode()) == Mode.CREATE)
		{
			saveButton.setEnabled(false);
			getNoteMessage().setEnabled(false);
		}


		// Add components
		this.add(listScrollPane); //add the noteList, in the listScrollPane, to the panel
		this.add(Box.createRigidArea(new Dimension(0,6))); //Put some vertical space between these components
		this.add(scrollMessage); // add the txtMessage box (in the scroll pane)  to the panel
		this.add(Box.createRigidArea(new Dimension(0,6)));
		this.add(saveButton); // adds the save button to the panel
		this.add(Box.createRigidArea(new Dimension(0,6)));

		saveButton.setAlignmentX(CENTER_ALIGNMENT); //Set the horizontal alignment of the save button to the center of this panel


	}


	public void setUp() {
		// Set controller for save button
		Boolean restoreEnableStateBool = saveButton.isEnabled(); //store the enable state of the save button, since adding an action defaults the enable to true
		saveButton.setAction(new SaveNoteAction(parent.getParent().getController()));
		saveButton.setEnabled(restoreEnableStateBool);//restore the previously stored enable state
	}

	/**
	 * Sets whether input is enabled for this panel and its children. This should be used instead of 
	 * JComponent#setEnabled because setEnabled does not affect its children.
	 * 
	 * @param enabled Whether or not input is enabled.
	 */
	protected void setInputEnabled(boolean enabled){
		inputEnabled = enabled;
		saveButton.setEnabled(enabled);
		getNoteMessage().setEnabled(enabled);
	}

	/**
	 * Returns a boolean representing whether or not input is enabled for the NotePanel.
	 * @return inputEnabled A boolean representing whether or not input is enabled for the NotePanel.
	 */
	public boolean getInputEnabled() {
		return inputEnabled;
	}

	/**
	 * This returns the JTextArea "txtMessage"
	 * @return the txtMessage JTextArea
	 */
	public JTextArea getNoteMessage() {
		JViewport viewport = scrollMessage.getViewport(); 
		return (JTextArea) viewport.getView();
	}

	/**
	 * This returns the JButton saveButton
	 * @return the saveButton JButton
	 */
	public JButton getSaveButton() {
		return saveButton;
	}

	/**
	 * This returns the Note "currentNote" 
	 * @return the currentNote Note
	 */
	public Note getCurrentNote() {
		return currentNote;
	}

	/**
	 * This sets the current note
	 * @param currentNote What to set the current note to
	 */
	public void setCurrentNote(Note currentNote) {
		this.currentNote = currentNote;
	}

	/**
	 * This returns the ListOfNotePanel that displays the stored notes,
	 * each in their own notePanel.
	 * @return the noteList ListOfNotePanel
	 */
	public ListOfNotePanel getNoteList() {
		return noteList;
	}
	/**
	 * This returns the NoteListModel "noteListModel",
	 * which stores the saved notes that are associated with the
	 * current requirement being displayed
	 * @return the noteListModel NoteListModel
	 */
	public NoteListModel getNoteListModel() {
		return noteListModel;
	}

	/**
	 * This adds a new note to the noteListModel,
	 * and then recreates and redisplays the noteList
	 * panel.
	 * @param newNote the note to be added
	 */
	public void addNoteToList(Note newNote){
		this.removeAll();
		noteListModel.addMessage(newNote);
		noteList  = new ListOfNotePanel(noteListModel);

		listScrollPane = new JScrollPane(noteList);

		listScrollPane.setPreferredSize(new Dimension(580, 300));		

		// Add components
		this.add(listScrollPane); //add the noteList, in the listScrollPane, to the panel
		this.add(Box.createRigidArea(new Dimension(0,6))); //Put some vertical space between these components
		this.add(scrollMessage); // add the txtMessage box (in the scroll pane)  to the panel
		this.add(Box.createRigidArea(new Dimension(0,6)));
		this.add(saveButton); // adds the save button to the panel
		this.add(Box.createRigidArea(new Dimension(0,6)));

	}

}

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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.JTextFieldLimit;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementTab;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementTab.Mode;

/** This panel is added to the RequirementTabPanel and 
 * contains all the GUI components involving notes:
 * -a panel to hold the list of notes
 * -a text area for a user to input a new note
 * -a save button to save the new note
 */
@SuppressWarnings({"serial"})
public class NoteTab extends JPanel implements ActionListener {

	/** The message text field used to input a new Note message */
	private  JTextArea txtMessage;
	
	/**  ScrollPane that the "txtMessage" JTextArea will be held in  */
	private JScrollPane scrollMessage; 
	
	/** The save button for saving a new note */
	private JButton saveButton;

	/** Stores the note currently open in this panel for editing or creation */
	private Note currentNote;

	/** Stores the RequirementPanel that contains the NoteTab panel within the RequirementTabPanel*/
	private RequirementTab parent; 

	/** A boolean indicating if input is enabled on this form  */
	protected boolean inputEnabled;

	/** The noteListModel. This holds the notes to be displayed in the "noteList" panel */
	private NoteListModel noteListModel;

	/** The panel to hold all the NotePanels (containing all the notes) to display */
	private ListOfNotePanel noteList;

	/** A scroll pane to hold the "noteList */
	JScrollPane listScrollPane;

	/** The constructor for NotePanel;
	 * Construct the panel, the components, and add the
	 * components to the panel.
	 * @param reqPanelParent	The RequirementTab parent of this tab
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
		txtMessage.setDocument(new JTextFieldLimit(100000));
		
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
				setSaveButtonWhenMessageIsValid();//Set the save button enabled if there is something to save, disabled if not
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
		else
			setSaveButtonWhenMessageIsValid();//Set the save button enabled if there is something to save, disabled if not
			
		// Add components
		this.add(listScrollPane); //add the noteList, in the listScrollPane, to the panel
		this.add(Box.createRigidArea(new Dimension(0,6))); //Put some vertical space between these components
		this.add(scrollMessage); // add the txtMessage box (in the scroll pane)  to the panel
		this.add(Box.createRigidArea(new Dimension(0,6)));
		this.add(saveButton); // adds the save button to the panel
		this.add(Box.createRigidArea(new Dimension(0,6)));
		
		saveButton.setAlignmentX(CENTER_ALIGNMENT); //Set the horizontal alignment of the save button to the center of this panel
	}

	/** Sets up the listener for the save button
	 */
	public void setUp() {
		// Set controller for save button
		Boolean restoreEnableStateBool = saveButton.isEnabled(); //store the enable state of the save button, since adding an action defaults the enable to true
		saveButton.setAction(new SaveNoteAction(parent.getParent().getController()));
		saveButton.addActionListener(this);
		saveButton.setEnabled(restoreEnableStateBool);//restore the previously stored enable state
	}

	/** Sets whether input is enabled for this panel and its children. This should be used instead of 
	 * JComponent#setEnabled because setEnabled does not affect its children.
	 * 
	 * @param enabled Whether or not input is enabled.
	 */
	protected void setInputEnabled(boolean enabled){
		inputEnabled = enabled;
		saveButton.setEnabled(enabled);
		getNoteMessage().setEnabled(enabled);
	}

	/** Returns a boolean representing whether or not input is enabled for the NotePanel.
	 * @return inputEnabled A boolean representing whether or not input is enabled for the NotePanel.
	 */
	public boolean getInputEnabled() {
		return inputEnabled;
	}
	
	/** Checks the txtMessage field for validity (non-emptiness) and sets the save button appropriately     
	 *  @return True if the txtMessage field is valid (non-empty), false otherwise
	 */
	public boolean setSaveButtonWhenMessageIsValid(){
		boolean messageGood = true;	// Initialize flag

		if (txtMessage.getText().length()==0)// Check if the note message ("txtMessage") box is empty
			messageGood = false;
				
		saveButton.setEnabled( messageGood);//Set the save button enabled/disabled appropriately
		return messageGood;
	}
	
	/** This returns the JTextArea "txtMessage"
	 * @return the txtMessage JTextArea
	 */
	public JTextArea getNoteMessage() {
		JViewport viewport = scrollMessage.getViewport(); 
		return (JTextArea) viewport.getView();
	}

	/** This returns the JButton saveButton
	 * @return the saveButton JButton
	 */
	public JButton getSaveButton() {
		return saveButton;
	}

	/** This returns the Note "currentNote" 
	 * @return the currentNote Note
	 */
	public Note getCurrentNote() {
		return currentNote;
	}

	/** This sets the current note
	 * @param currentNote What to set the current note to
	 */
	public void setCurrentNote(Note currentNote) {
		this.currentNote = currentNote;
	}

	/** This returns the ListOfNotePanel that displays the stored notes,
	 * each in their own notePanel.
	 * @return the noteList ListOfNotePanel
	 */
	public ListOfNotePanel getNoteList() {
		return noteList;
	}
	
	/** This returns the NoteListModel "noteListModel",
	 * which stores the saved notes that are associated with the
	 * current requirement being displayed
	 * @return the noteListModel NoteListModel
	 */
	public NoteListModel getNoteListModel() {
		return noteListModel;
	}

	/** This adds a new note to the noteListModel,
	 * and then recreates and redisplays the noteList
	 * panel to show the changes.
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

	/** Tell the txtMessage field that it should not have changes
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		parent.getAttributePanel().changeField(txtMessage, 9, false);
	}
}

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Note;

@SuppressWarnings({"serial","rawtypes","unchecked"})
public class NotePanel extends JPanel {
	
	//The fillable component
	private  JTextArea txtMessage;//The message text field 

	//Setting up button
	private JButton saveButton;

	//The variables to hold information about the current instance of the panel
	private Note currentNote;//Stores the note currently open for editing or creation

	// the parent might change to a Requirement View depending on how the UI is implemented
	private RequirementPanel parent; //Stores the RequirementPanel that contains the panel

	//A boolean indicating if input is enabled on the form 
	protected boolean inputEnabled;
	
	private JList noteList;
	
	private NoteListModel noteListModel;
	
	/**
	 * The constructor for NotePanel;
	 * Construct the panel, the components, and add the
	 * components to the panel.
	 * @param view	The parent RequirementView that contains this panel
	 * @param requirement	The requirement that is currently open for editing or creation.
	 * @param editMode	The mode of the current panel. This is "CREATE" when we are creating a new requirement, and "EDIT" when we are editing an existing requirement.
	 */
	public NotePanel(RequirementPanel reqPanelParent) {

		parent = reqPanelParent; //Set the RequirementPanel that contains this instance of this panel

		// Set the layout manager
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));//Create the layout

		// Construct list box
		// Construct the list box model
		noteListModel = new NoteListModel();

		// Construct the components to be displayed
		noteList = new JList(noteListModel);
		
		ArrayList<Note> notes = parent.getCurrentRequirement().getNotes();
		for (int i = 0; i < notes.size(); i++) {
			noteListModel.addMessage(notes.get(i));
		}
		
		// Put the listbox in a scroll pane
		JScrollPane listScrollPane = new JScrollPane(noteList);

		// Construct components to be displayed
		// a list component here
		txtMessage = new JTextArea("", 1, 1);
		saveButton = new JButton("Add Note");

		//Set the txtMessage component to wrap
		txtMessage.setLineWrap(true);
		txtMessage.setWrapStyleWord(true);

		listScrollPane.setMaximumSize(new Dimension(580, 300));
		listScrollPane.setMinimumSize(new Dimension(580, 300));
		txtMessage.setMinimumSize(new Dimension(580, 100));
		txtMessage.setMaximumSize(new Dimension(580, 100));
		saveButton.setMaximumSize(new Dimension(120, 40));
		saveButton.setMinimumSize(new Dimension(120, 40));
		
		// Add components
		this.add(listScrollPane);
		this.add(Box.createRigidArea(new Dimension(0,6)));
		this.add(txtMessage); // add the txtMessage box to the panel
		this.add(Box.createRigidArea(new Dimension(0,6)));
		this.add(saveButton); // adds the save button to the panel
		this.add(Box.createRigidArea(new Dimension(0,6)));
		
		saveButton.setAlignmentX(CENTER_ALIGNMENT);

	}

	public void setUp() {
		// Set controller for save button
		saveButton.setAction(new SaveNoteAction(parent.getParent().getController()));
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
		txtMessage.setEnabled(enabled);

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
		return txtMessage;
	}
	
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
	 * This sets the Note "currentNote" 
	 * @param Note currentNote the currentNote to set
	 */
	public void setCurrentNote(Note currentNote) {
		this.currentNote = currentNote;
	}

	public JList getNoteList() {
		return noteList;
	}
	
	public NoteListModel getNoteListModel() {
		return noteListModel;
	}

}

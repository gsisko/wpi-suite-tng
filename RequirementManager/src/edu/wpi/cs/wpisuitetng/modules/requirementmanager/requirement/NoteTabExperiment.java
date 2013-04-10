package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement;

import java.awt.Dimension;
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

@SuppressWarnings({"serial"})
public class NoteTabExperiment extends JPanel {
	
	//The fillable component
	private  JTextArea txtMessage;//The message text field 
	private JScrollPane scrollMessage; // ScrollPane that the message box will be held in 

	//Setting up button
	private JButton saveButton;

	//The variables to hold information about the current instance of the panel
	private Note currentNote;//Stores the note currently open for editing or creation

	// the parent might change to a Requirement View depending on how the UI is implemented
	private RequirementTab parent; //Stores the RequirementPanel that contains the panel

	//A boolean indicating if input is enabled on the form 
	protected boolean inputEnabled;
	
	private NoteListModel noteListModel;
	
	private ListOfNotePanel noteList;
	
	/**
	 * The constructor for NotePanel;
	 * Construct the panel, the components, and add the
	 * components to the panel.
	 * @param reqPanelParent	The parent of this tab
	 */
	public NoteTabExperiment(RequirementTab reqPanelParent) {

		parent = reqPanelParent; //Set the RequirementPanel that contains this instance of this panel

		// Set the layout manager
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));//Create the layout
		
		setBorder(BorderFactory.createEmptyBorder(5, 3, 3, 3)); 

		// Construct list box
		// Construct the list box model
		noteListModel = new NoteListModel();

		noteList  = new ListOfNotePanel();

		// Put the listbox in a scroll pane
		JScrollPane listScrollPane = new JScrollPane(noteList);

		// Construct components to be displayed
		// a list component here
		txtMessage = new JTextArea("", 1, 1);
		saveButton = new JButton("Add Note");

		// Set the txtMessage component to wrap
		txtMessage.setLineWrap(true);
		txtMessage.setWrapStyleWord(true);

		// Put txtMessage in a scroll pane
		scrollMessage = new JScrollPane(txtMessage);
		scrollMessage.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		// Set dimensions of the panel elements

		listScrollPane.setPreferredSize(new Dimension(580, 300));
		scrollMessage.setPreferredSize(new Dimension(580, 100));
		saveButton.setPreferredSize(new Dimension(120, 40));
		
		
		
		if ((parent.getMode()) == Mode.CREATE)
		{
			saveButton.setEnabled(false);
			getNoteMessage().setEnabled(false);
		}
	
		
		// Add components
		this.add(listScrollPane);
		this.add(Box.createRigidArea(new Dimension(0,6)));
		this.add(scrollMessage); // add the txtMessage box (in the scroll pane)  to the panel
		this.add(Box.createRigidArea(new Dimension(0,6)));
		this.add(saveButton); // adds the save button to the panel
		this.add(Box.createRigidArea(new Dimension(0,6)));
		
		saveButton.setAlignmentX(CENTER_ALIGNMENT);
		

	}


	public void setUp() {
		// Set controller for save button
		Boolean restoreEnableStateBool = saveButton.isEnabled();
		saveButton.setAction(new SaveNoteAction(parent.getParent().getController()));
		saveButton.setEnabled(restoreEnableStateBool);
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

	public ListOfNotePanel getNoteList() {
		return noteList;
	}
	
	public NoteListModel getNoteListModel() {
		return noteListModel;
	}

}

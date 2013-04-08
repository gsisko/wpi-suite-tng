package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Note;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementTab.Mode;

@SuppressWarnings({"serial","rawtypes","unchecked"})
public class NoteTab extends JPanel {
	
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
	public NoteTab(RequirementTab reqPanelParent) {

		parent = reqPanelParent; //Set the RequirementPanel that contains this instance of this panel

		// Set the layout manager
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));//Create the layout
		
		setBorder(BorderFactory.createEmptyBorder(5, 3, 3, 3)); 

		// Construct list box
		// Construct the list box model
		noteListModel = new NoteListModel();

		// Construct the components to be displayed
		noteList = new JList(noteListModel);
		
		//prevent people from selecting notes in the list by changing the default cell renderer to
		//always render components as not selected and not focused
		noteList.setCellRenderer(new DefaultListCellRenderer() {
		    public Component getListCellRendererComponent(JList list, Object value, int index,
		            boolean isSelected, boolean cellHasFocus) {
		        super.getListCellRendererComponent(list, value, index, false, false);
		        JPanel panel = new JPanel();
		        JLabel title = new JLabel(((Note)value).toString());
		        JTextArea content =  new JTextArea("", 1, 1);
		        content.setLineWrap(true);
		        content.setWrapStyleWord(true);
		        content.setText(((Note)value).getMessage());
		        
		        content.setPreferredSize(new Dimension(580, 100));

				panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
				title.setFont(title.getFont().deriveFont(9));
				title.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
				content.setFont(content.getFont().deriveFont(9));
				content.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

				//content.setMaximumSize(new Dimension(noteList.getWidth(),1000));
				//title.setMaximumSize(new Dimension(noteList.getWidth(),1000));
				//panel.setMaximumSize(new Dimension(noteList.getWidth(),1000));
				
				panel.add(title);
				panel.add(content);
				panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0), BorderFactory.createLineBorder(Color.black, 1)), BorderFactory.createEmptyBorder(8, 8, 8, 8)));
		 
		        return panel;		 
		        //return this;
		    }
		});
		
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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ListOfNotePanel extends JPanel {
	
	//The list of stored notes to be displayed, passed in as a NoteListModel
	private NoteListModel noteListModel;

	
	/**
	 * The constructor for this panel.
	 * This takes a NoteListModel ("newModel"),
	 * constructs a NotePanel for each note within that model,
	 * and adds each to this panel.
	 * @param newModel The NoteListModel containing the stored notes to be displayed
	 */
	public ListOfNotePanel(NoteListModel newModel) {
		
		// Create and set the layout manager for this panel
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		//Create and set an empty border for spacing purposes
		setBorder(BorderFactory.createEmptyBorder(5, 3, 3, 3)); 

		//Set the noteListModel to the NoteListModel passed in as "newModel"
		noteListModel = newModel;
		
		//For each note in the noteListModel....
		for (int i = 0; i<noteListModel.getSize(); i++)
		{
			String message = noteListModel.getElementAt(i).getMessage(); //grab the message portion of the note
			NotePanel panel = new NotePanel(noteListModel.getElementAt(i).toString(),message); //create a new NotePanel to hold the note
			
			this.add(panel);//actually add this notePanel to this ListOfNotePanel
		}

		this.setBackground(Color.WHITE); //set the background color of this panel to white
		

		this.setPreferredSize(new Dimension(325, 10000)); //Set the preferred size of this panel 

	}
	
	
	
}

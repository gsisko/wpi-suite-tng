package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ListOfNotePanel extends JPanel {
	
	//The list of stored notes to be displayed, passed in as a NoteListModel
	private NoteListModel noteListModel;
	private int totalHeight;

	
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
		
		totalHeight = 0; //This stores the total height of all the notePanels added in the next section. This is used later  for setting the preferred size of this panel.
		
		//For each note in the noteListModel....
		for (int i = 0; i<noteListModel.getSize(); i++)
		{
			String message = noteListModel.getElementAt(i).getMessage(); //grab the message portion of the note
			NotePanel panel = new NotePanel(noteListModel.getElementAt(i).toString(),message ); //create a new NotePanel to hold the note
			
			//This section ensures that this NotePanel will never be stretched to a larger height than is necessary
			
			//Count the maximum number of lines that this note will hold when resized to it's smallest allowed width by the JSplitPane in the containing RequirementTab
			int maxNumLinesInMessage; //stores the maximum number of lines
			if (message.length() <=22) //if the message has less than 22 characters (the minimum amount of characters in a line when this panel is at it's smallest width
				maxNumLinesInMessage = 1; //...then only count one line
			else
				maxNumLinesInMessage = (message.length() / 22 ); //count the lines
			maxNumLinesInMessage+=2; //add 2 to account for partial lines and borders
			
			//Count the number of newline characters in the message
			String newLine = "\n";
			int lastIndex = 0;
			int count =0;

			while(lastIndex != -1){

			       lastIndex = message.indexOf(newLine,lastIndex);

			       if( lastIndex != -1){
			             count ++;
			             lastIndex+=newLine.length();
			      }
			}
			maxNumLinesInMessage += count; //add the number of newline characters in the message to the maxNumLinesInMessage
			
			totalHeight += (maxNumLinesInMessage * 20); //add the maximum height of this panel to the running total
			
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); //Grab the screen size of the monitor this program is being displayed on 
			
			panel.setMaximumSize(new Dimension(((int)(dim.width) -600), (maxNumLinesInMessage * 20))); //Set the maximum size of this panel based on previously calculated values (a line of text being 20 units high, and width being calculated by maximum possible width of this program - the minimum width of the RequirementAttributePanel)
			panel.revalidate(); //necessary to actually resize the panel appropriately
			panel.repaint();//necessary to actually resize the panel appropriately
			
			//End sizing section
			
			this.add(panel);//actually add this notePanel to this ListOfNotePanel
		}

		this.setBackground(Color.WHITE); //set the background color of this panel to white
		
		this.setPreferredSize(new Dimension(50, totalHeight)); //Set the preferred size of this panel based on previously calculated values. This give the scroll pane that will hold this panel the necessary information needed to add scroll-ability when appropriate
		this.revalidate();//necessary to actually resize the panel appropriately
		this.repaint();//necessary to actually resize the panel appropriately
	}
	
	
	
}

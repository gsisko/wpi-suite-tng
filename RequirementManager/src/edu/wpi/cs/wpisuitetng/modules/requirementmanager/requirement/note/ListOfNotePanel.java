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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.note;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * This panel is used to create and store a list of NotePanels (one for each note in the NoteListModel).
 */
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
		
		double totalHeight=0; //This stores a running total of the heights of the note panels- this is used later to set the preferred size of this panel appropriately so that the scroll pane that will contain this panel can scroll appropriately
		
		//For each note in the noteListModel....
		for (int i = 0; i<noteListModel.getSize(); i++)
		{
			String message = noteListModel.getElementAt(i).getMessage(); //grab the message portion of the note
			NotePanel panel = new NotePanel(noteListModel.getElementAt(i).toString(),message); //create a new NotePanel to hold the note
			totalHeight += panel.getSize().getHeight();//add this panel's height to the running total
			this.add(panel);//actually add this notePanel to this ListOfNotePanel
		}

		this.setBackground(Color.WHITE); //set the background color of this panel to white
		

		this.setPreferredSize(new Dimension(325, (int)totalHeight)); //Set the preferred size of this panel 
		
		//Add resize functionality to keep the maximum height current
		final JPanel referenceToThis = this;// a reference to this panel to use within the following constructor
		//Construct and add a new component listener to listen for a resize event
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {  //on resize...
				((ListOfNotePanel) referenceToThis).resizeFunction();//call the resize function
			}
		});
		//end resize functionality
	}

	/**
	 * This function is called when the ListOfNotePanel is resized or when 
	 * any of the NotePanels are resized
	 */
	public void resizeFunction(){
		double newTotalHeight=0;//This stores the new running total of the heights of the note panels- this is used later to set the preferred size of this panel appropriately so that the scroll pane that will contain this panel can scroll appropriately
		for (Component c : this.getComponents()) {//For each component in this panel
			newTotalHeight += c.getMaximumSize().getHeight();//add that panel's height to the running total
		}
		this.setPreferredSize(new Dimension(325, (int)newTotalHeight)); //Set the preferred size of this panel appropriately so that the scroll pane that will contain this panel can scroll appropriately 
	}
	
}

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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.changeset.RequirementEvent;

/** This panel contains a list of events associated with a particular Requirement,
 *  each event in it's own EventPanel.
 *  Please note that the ListOfEventPanel cannot react to changes in the database 
 *  or the ArrayList of RequirementEvents within it (the "events" variable) without being
 *  re-created, so a new ListOfEventPanel should be created whenever changes occur.
 */
@SuppressWarnings("serial")
public class ListOfEventPanel extends JPanel {
	
	/** An ArrayList of RequirementEvents that will hold the list of RequirementEvents associated with this requirement */ 
	private ArrayList<RequirementEvent> events;
	
	/** The basic constructor. This constructs the ListOfEventPanel,
	 * adding a new EventPanel for every event in the current requirement
	 * (passed in as "currentRequirement"). Please note that the ListOfEventPanel
	 * cannot react to changes in the database or the "events" variable without being
	 * re-created, so a new ListOfEventPanel should be created whenever changes occur.
	 * 
	 * @param currentRequirement The Requirement whose events are to be displayed in this panel
	 */
	public ListOfEventPanel(Requirement currentRequirement) {
		// Create and set the layout manager for this panel
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		//Create and set an empty border for spacing purposes
		setBorder(BorderFactory.createEmptyBorder(5, 3, 3, 3)); 

		//Set the noteListModel to the NoteListModel passed in as "newModel"
		events = currentRequirement.getEvents();
		
		/** This stores a running total of the heights of the note panels-
		 *  this is used later to set the preferred size of this panel appropriately
		 *  so that the scroll pane that will contain this ListOfEventPanel can scroll appropriately
		 */
		double totalHeight=0; 
		
		//For each note in the noteListModel....
		for (int i = events.size()-1; i>=0; i--)
		{
			String message = events.get(i).getBodyString(); //grab the message portion of the note
			EventPanel panel = new EventPanel(events.get(i).getLabelString(),message); //create a new NotePanel to hold the note
			totalHeight += panel.getSize().getHeight();//add this panel's height to the running total
			this.add(panel);//actually add this notePanel to this ListOfNotePanel
		}

		this.setBackground(Color.WHITE); //set the background color of this panel to white
		
		this.setPreferredSize(new Dimension(325, (int)totalHeight)); //Set the preferred size of this panel 
		
		//Add resize functionality to keep the maximum height current
		/** A reference to this ListOfEventPanel to use within the componentResized componentListener */
		final JPanel referenceToThis = this;
		//Construct and add a new component listener to listen for a resize event
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {  //on resize...
				((ListOfEventPanel) referenceToThis).resizeFunction();//call the resize function
			}
		});
		//end resize functionality
	}

	/** This function is called when the ListOfEventPanel is resized or when 
	 * any of the EventPanels within the ListOfEventPanel is resized 
	 */
	public void resizeFunction(){
		double newTotalHeight=0;//This stores the new running total of the heights of the event panels- this is used later to set the preferred size of this panel appropriately so that the scroll pane that will contain this panel can scroll appropriately
		for (Component c : this.getComponents()) {//For each component in this panel
			newTotalHeight += c.getMaximumSize().getHeight();//add that panel's height to the running total
		}
		this.setPreferredSize(new Dimension(325, (int)newTotalHeight)); //Set the preferred size of this panel appropriately so that the scroll pane that will contain this panel can scroll appropriately 
	}
	
}

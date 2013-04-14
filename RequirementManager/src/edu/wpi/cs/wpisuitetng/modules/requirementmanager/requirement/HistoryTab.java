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
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementEvent;


@SuppressWarnings({"serial"})
public class HistoryTab extends JPanel {

	// The parent 
	private RequirementTab parent; //Stores the RequirementPanel that contains the panel

	//The panel to hold all the NotePanels (containing all the notes) to display
	private ListOfEventPanel eventList;

	//A scroll pane to hold the "noteList"
	JScrollPane listScrollPane;

	/**
	 * The constructor for NotePanel;
	 * Construct the panel, the components, and add the
	 * components to the panel.
	 * @param reqPanelParent	The parent of this tab
	 */
	public HistoryTab(RequirementTab reqPanelParent) {

		parent = reqPanelParent; //Set the RequirementPanel that contains this instance of this panel

		// Create and set the layout manager
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		//Set an empty border for spacing
		setBorder(BorderFactory.createEmptyBorder(5, 3, 3, 3)); 

		//Construct the noteList, using the previously defined empty model
		eventList  = new ListOfEventPanel(parent.getCurrentRequirement());

		// Put the noteList in a scroll pane
		listScrollPane = new JScrollPane(eventList);

		// Set the dimensions of the panel elements
		listScrollPane.setPreferredSize(new Dimension(580, 300));

		// Add components
		this.add(listScrollPane); //add the noteList, in the listScrollPane, to the panel
	}

	public ListOfEventPanel getEventList() {
		return eventList;
	}

	

	public void addEventToList(RequirementEvent newEvent){
		this.removeAll();
		parent.getCurrentRequirement().getEvents().add(newEvent);
		eventList  = new ListOfEventPanel(parent.getCurrentRequirement());

		listScrollPane = new JScrollPane(eventList);

		listScrollPane.setPreferredSize(new Dimension(580, 300));		

		// Add components
		this.add(listScrollPane); //add the noteList, in the listScrollPane, to the panel

	}

}

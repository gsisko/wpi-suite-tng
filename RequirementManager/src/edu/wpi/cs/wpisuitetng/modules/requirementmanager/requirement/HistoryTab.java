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

import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.changeset.RequirementEvent;

/** This class is a JPanel that holds the GUI for displaying the history of a requirement.
 * It is kept inside the RequirementTabPanel JTabbedPane within the RequirementTab JPanel.
 * It contains a ListOfEventPanel ("eventList"), in a JScrollPane ("listScrollPane"), that
 * holds all the EventPanels (containing all the events associated with this requirement)
 * to display.
 */
@SuppressWarnings({"serial"})
public class HistoryTab extends JPanel {

	/** The parent RequirementTab - this stores a reference to the RequirementPanel that contains this HistoryTab panel (within the RequirementTabPanel) */
	private RequirementTab parent;

	/** The panel to hold all the EventPanels (containing all the events) to display */
	private ListOfEventPanel eventList;

	/** A scroll pane to hold the "eventList" */
	JScrollPane listScrollPane;

	/** The constructor for HistoryTab;
	 * Construct the panel, the components, and add the
	 * components to the panel.
	 * 
	 * @param reqPanelParent	The RequirementTab parent of this tab
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

	/**
	 * @return eventList The ListOfEventPanel that holds all the EventPanels (containing all the events) to display in this HistoryTab
	 */
	public ListOfEventPanel getEventList() {
		return eventList;
	}

	/** This method refreshes the eventList (the ListOfEventPanel that holds all the EventPanels, 
	 *  containing all the events, to display in this HistoryTab). This is used because a
	 *  ListOfEventPanel must be recreated to show changes in the list of events associated 
	 *  with this requirement.
	 */
	public void refreshEventsList() {
		this.removeAll();//Remove all the components from this HistoryTab panel
		eventList  = new ListOfEventPanel(parent.getCurrentRequirement()); //Create a new ListOfEventPanel
		listScrollPane = new JScrollPane(eventList);// Put the new ListOfEventPanel in the scroll pane
		listScrollPane.setPreferredSize(new Dimension(580, 300));//Re-set the preferred size so that the scroll and resize functionality will function properly
		this.add(listScrollPane); // Re-add the new components; add the noteList, in the listScrollPane, to the panel
	}

	/** This method, like {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement#refreshEventsList()},
	 *  refreshes the eventList (the ListOfEventPanel that holds all the EventPanels, containing all the events,
	 *  to display in this HistoryTab).
	 *  Unlike refreshEventsList, however, this method also allows you to specifically add a new 
	 *  Event to the list of events associated with this requirement. 
	 *  This method, like refreshEventsList, is used because a ListOfEventPanel must be recreated
	 *  to show changes in the list of events associated with this requirement.
	 * 
	 * @param newEvent The RequirementEvent to add to the list of events associated with this requirement.
	 */
	public void addEventToList(RequirementEvent newEvent){
		this.removeAll();//Remove all the components from this HistoryTab panel
		parent.getCurrentRequirement().getEvents().add(newEvent);//Add the event, passed in as "newEvent", to the list of events associated with this requirement
		eventList  = new ListOfEventPanel(parent.getCurrentRequirement()); //Create a new ListOfEventPanel
		listScrollPane = new JScrollPane(eventList);// Put the new ListOfEventPanel in the scroll pane
		listScrollPane.setPreferredSize(new Dimension(580, 300));//Re-set the preferred size so that the scroll and resize functionality will function properly		
		this.add(listScrollPane); // Re-add the new components; add the noteList, in the listScrollPane, to the panel
	}

}

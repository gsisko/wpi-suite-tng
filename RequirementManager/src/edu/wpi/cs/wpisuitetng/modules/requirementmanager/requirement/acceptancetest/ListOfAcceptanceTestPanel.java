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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.acceptancetest;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/** This panel is used to create and display a list of AcceptanceTestPanels (one for each acceptance test in the AcceptanceTestListModel).
 */
@SuppressWarnings("serial")
public class ListOfAcceptanceTestPanel extends JPanel {
	
	/** The list of stored acceptance tests to be displayed, passed in as a AcceptanceTestListModel */
	private AcceptanceTestListModel acceptanceTestListModel;
	
	/** The constructor for ListOfAcceptanceTestPanel.
	 * This takes a AcceptanceTestListModel ("newModel"),
	 * constructs an AcceptanceTestPanel for each acceptanceTest within that model,
	 * and adds each to this panel.
	 * @param newModel The AcceptanceListModel containing the stored acceptance tests to be displayed
	 */
	public ListOfAcceptanceTestPanel(AcceptanceTestListModel newModel) {	
		// Create and set the layout manager for this panel
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		//Create and set an empty border for spacing purposes
		setBorder(BorderFactory.createEmptyBorder(5, 3, 3, 3)); 

		//Set the acceptanceTestListModel to the AcceptanceListModel passed in as "newModel"
		acceptanceTestListModel = newModel;
		
		double totalHeight=0; //This stores a running total of the heights of the acceptance test panels- this is used later to set the preferred size of this panel appropriately so that the scroll pane that will contain this panel can scroll appropriately
		
		//For each acceptance test in the AcceptanceListModel....
		for (int i = 0; i<acceptanceTestListModel.getSize(); i++)
		{
			String description = acceptanceTestListModel.getElementAt(i).getDescription(); //grab the description
			String title = acceptanceTestListModel.getElementAt(i).getAcceptanceTestTitle();  //grab the title
			AcceptanceTestResult result = acceptanceTestListModel.getElementAt(i).getAcceptanceTestResult(); // Grab the result
			AcceptanceTestPanel panel = new AcceptanceTestPanel(title,description, result); //create a new AcceptanceTestPanel to hold this acceptance test
			panel.settxtDescription(description);
			totalHeight += panel.getSize().getHeight();//add this panel's height to the running total
			panel.setup();
			this.add(panel);//actually add this acceptanceTestPanel to this ListOfAcceptanceTestPanel
		}

		this.setBackground(Color.WHITE); //set the background color of this panel to white
		
		this.setPreferredSize(new Dimension(325, (int)totalHeight)); //Set the preferred size of this panel 
		
		//Add resize functionality to keep the maximum height current
		/** A reference to this ListOfAcceptanceTestPanel to use within the resize component listener */
		final JPanel referenceToThis = this;
		//Construct and add a new component listener to listen for a resize event
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {  //on resize...
				((ListOfAcceptanceTestPanel) referenceToThis).resizeFunction();//call the resize function
			}
		});
		//end resize functionality
	}

	/** This function is called when the ListOfAcceptanceTestPanel is resized or when 
	 * the description field in any of the AcceptanceTestPanels is resized 
	 * (edited to make it different than it's current size).
	 */
	public void resizeFunction(){
		double newTotalHeight=0;//This stores the new running total of the heights of the acceptance test panels- this is used later to set the preferred size of this panel appropriately so that the scroll pane that will contain this panel can scroll appropriately
		for (Component c : this.getComponents()) {//For each component in this panel
			newTotalHeight += c.getMaximumSize().getHeight();//add that panel's height to the running total
		}
		this.setPreferredSize(new Dimension(325, (int)newTotalHeight)); //Set the preferred size of this panel appropriately so that the scroll pane that will contain this panel can scroll appropriately 
	}
	
}

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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.acceptancetest;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * This panel is used to create and store a list of AcceptanceTestPanels (one for each acceptance test in the AcceptanceTestListModel).
 */
@SuppressWarnings("serial")
public class ListOfAcceptanceTestPanel extends JPanel {
	
	//The list of stored acceptance tests to be displayed, passed in as a AcceptanceTestListModel
	private AcceptanceTestListModel acceptanceTestListModel;
	

	/**
	 * The constructor for this panel.
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
			String message = acceptanceTestListModel.getElementAt(i).getMessage(); //grab the message portion of the acceptance test
			AcceptanceTestPanel panel = new AcceptanceTestPanel(acceptanceTestListModel.getElementAt(i).toString(),message); //create a new AcceptanceTestPanel to hold the acceptance test
			totalHeight += panel.getSize().getHeight();//add this panel's height to the running total
			this.add(panel);//actually add this acceptanceTestPanel to this ListOfAcceptanceTestPanel
		}

		this.setBackground(Color.WHITE); //set the background color of this panel to white
		

		this.setPreferredSize(new Dimension(325, (int)totalHeight)); //Set the preferred size of this panel 
		
		//Add resize functionality to keep the maximum height current
		final JPanel referenceToThis = this;// a reference to this panel to use within the following constructor
		//Construct and add a new component listener to listen for a resize event
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {  //on resize...
				double newTotalHeight=0;//This stores the new running total of the heights of the acceptance test panels- this is used later to set the preferred size of this panel appropriately so that the scroll pane that will contain this panel can scroll appropriately
				for (Component c : referenceToThis.getComponents()) {//For each component in this panel
					newTotalHeight += c.getMaximumSize().getHeight();//add that panel's height to the running total
				}
				referenceToThis.setPreferredSize(new Dimension(325, (int)newTotalHeight)); //Set the preferred size of this panel appropriately so that the scroll pane that will contain this panel can scroll appropriately 
			}
		});
		//end resize functionality
	}

	
	
}

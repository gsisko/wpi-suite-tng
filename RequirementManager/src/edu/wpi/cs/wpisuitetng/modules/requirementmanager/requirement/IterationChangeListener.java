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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementStatus;

/** An action listener specifically made to watch an Iteration selection combo 
 *  box and on changes between any iteration and the backlog, change the status
 *  of the requirement appropriately.            */
public class IterationChangeListener implements ActionListener {

	/** The panel with the Iteration drop down box to be watched */
	RequirementAttributePanel raPanel;
	
	/** Basic constructor
	 * 
	 * @param raPanel The panel with the box to watch
	 */
	public IterationChangeListener(RequirementAttributePanel raPanel){
		this.raPanel = raPanel;
	}

	/** Watches the "Iteration" box for changes and sets up the "status" field
	 *  of the requirement appropriately	 */
	public void actionPerformed(ActionEvent e) {
		System.out.println("The assigned iteration has been changed; the status will be changed accordingly.");
		
		@SuppressWarnings("rawtypes") // This warning is necessary because of the current version of Java
		String selected = (String)  ((JComboBox)e.getSource()).getSelectedItem();

		// Check to see if the selected item is the backlog, set status appropriately
		if (selected.equals("")){
			raPanel.getCurrentRequirement().setStatus(RequirementStatus.Open);
			raPanel.updateStatusSettings("Open");
		} else {
			raPanel.getCurrentRequirement().setStatus(RequirementStatus.InProgress);
			raPanel.updateStatusSettings("InProgress");
		}
	}
}

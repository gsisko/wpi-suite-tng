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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.observers.RetrieveRequirementRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.RequirementListPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Controller to handle retrieving one requirement from the server
 */
public class RetrieveRequirementController extends MouseAdapter {

	/** The results panel */
	protected RequirementListPanel view;

	/**
	 * Construct the controller
	 * 
	 * @param view the parent view 
	 */
	public RetrieveRequirementController(RequirementListPanel view) {
		this.view = view;
	}

	/**
	 * @see java.awt.event.MouseAdapter#mouseClicked(MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent me) {
		if (me.getClickCount() == 2) { /* respond to double clicks */

			// Get a reference to the results JTable from the mouse event
			JTable resultsTable = (JTable) me.getSource();

			// Determine the row the user clicked on
			int row = resultsTable.rowAtPoint(me.getPoint());

			// make sure the user actually clicked on a row
			if (row > -1) {
				String requirementId = (String) resultsTable.getValueAt(row, 0);

				// Create and send a request for the requirement with the given ID
				Request request;
				request = Network.getInstance().makeRequest("requirementmanager/requirement/" + requirementId, HttpMethod.GET);
				request.addObserver(new RetrieveRequirementRequestObserver(this));
				request.send();
			}
		}
	}

	/**
	 * Called by {@link RetrieveRequirementRequestObserver} when the response
	 * is received from the server.
	 * @param requirement the requirement that was retrieved
	 */
	public void showRequirement(Requirement requirement) {
		// if a user has double-clicked on a requirement, set UI fields appropriately
		view.getTabController().addEditRequirementTab(requirement);
	}

	/**
	 * Called by {@link RetrieveRequirementRequestObserver} when an error
	 * occurred retrieving the requirement from the server.
	 */
	public void errorRetrievingRequirement(String error) {
		JOptionPane.showMessageDialog(view, 
				"An error occurred opening the requirement you selected. " + error, "Error opening requirement", 
				JOptionPane.ERROR_MESSAGE);
	}
}

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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.janeway.gui.widgets.JPlaceholderTextField;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabController;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Controller to handle retrieving a requirement from the core
 */
public class LookupRequirementController implements ActionListener {

	/** The main tab controller */
	protected MainTabController tabController;

	/** The parent view */
	protected ToolbarView parentView;

	/** The list field on the main toolbar */
	protected JPlaceholderTextField listField;

	/** A flag to prevent multiple lookups from occurring at the same time */
	protected boolean waitingForResponse = false;

	/**
	 * Constructs the controller
	 * @param tabController the tab controller, to be used to add a view requirement tab to the window
	 * @param listField the list field in the main toolbar
	 */
	public LookupRequirementController(MainTabController tabController, JPlaceholderTextField listField, ToolbarView parentView) {
		this.tabController = tabController;
		this.listField = listField;
		this.parentView = parentView;
	}

	/**
	 * Send a request when the user hits the enter key while typing in the
	 * list requirement field
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JTextField source = (JTextField) e.getSource();
		if (!waitingForResponse) { /* proceed if there is not already a request in progress */
			waitingForResponse = true; // we are now in the process of making a request

			// Validate the requirement ID that was entered into the list field
			Integer id;
			try {
				id = Integer.parseInt(source.getText());
			}
			catch (NumberFormatException nfe) { // Invalid id, alert the user
				listField.clearText();
				JOptionPane.showMessageDialog(parentView, "The requirement ID you entered is not valid.", "Invalid Requirement ID", JOptionPane.WARNING_MESSAGE);
				return;
			}

			// Generate the request
			Request request;
			request = Network.getInstance().makeRequest("requirementmanager/requirement/" + id, HttpMethod.GET);
			request.addObserver(new LookupRequestObserver(this));
			request.send();
		}
	}

	/**
	 * Method called by the observer when the response is received
	 * @param requirement the requirement that was received
	 */
	public void receivedResponse(Requirement requirement) {
		// Make a new requirement view to display the requirement that was received
		tabController.addEditRequirementTab(requirement);
		
		// Reset the list field
		listField.clearText();

		// Clear the waiting flag
		waitingForResponse = false;
	}

	/**
	 * Method called by the observer if no requirement was received
	 */
	public void requestFailed() {
		listField.clearText();
		waitingForResponse = false;
		JOptionPane.showMessageDialog(parentView, "A requirement with the ID you provided was not found.", "Requirement Not Found", JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * Returns the waiting flag
	 * @return the waiting flag
	 */
	public boolean getWaitingFlag() {
		return waitingForResponse;
	}
}

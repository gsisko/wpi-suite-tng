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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.FilterBuilderPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.FilterBuilderPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.FilterListPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListRequirementsView;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/** Full process of deleting filters from Press of the delete button
 *  to sending the delete message
 */
public class DeleteFilterController implements ActionListener {
	/**  The view that this controller is watching */
	private final ListRequirementsView theView;
	/**  Table of filters that might need to be deleted*/
	private JTable filters;

	/** Default constructor. Also pulls filter table to 
	 *  for referencing later.
	 * 
	 * @param view The view that this controller is watching
	 */
	public DeleteFilterController(ListRequirementsView view){
		this.theView = view;

	}



	/** Action listener for when the "Delete" button is pressed
	 *  AND more than one Filter in the list is highlighted.
	 * 
	 * @param buttonPress The input that triggers the controller 
	 */
	public void actionPerformed(ActionEvent buttonPress) {		
		this.filters = theView.getListPanel().getFilterPanel().getResultsTable();
		// get highlighted rows 
		int[] rowNumbers = filters.getSelectedRows();

		// get array of row numbers, if there are any highlighted rows
		for(int i = 0; i < rowNumbers.length; i++){			
			// Getting the ID from the current highlighted row
			String filterId = (String) filters.getValueAt(rowNumbers[i], 0);

			// Create and send a request for the Filter with the given ID
			// Send message for each filter ID for deletion
			Request request;
			request = Network.getInstance().makeRequest("requirementmanager/filter/" + filterId, HttpMethod.DELETE);
			request.addObserver(new DeleteFilterObserver(this));
			request.send();
		}
		// Remove anything in the filter builder panel whenever the delete button is pressed
		FilterBuilderPanel resetBuilderFields = 	theView.getListPanel().getBuilderPanel();
		resetBuilderFields.setInputEnabled(false);
		resetBuilderFields.setCurrentMode(Mode.CREATE);

		// Set the cancel button back to New Filter if it was in cancel mode 
		FilterListPanel resetListCancelButtons = theView.getListPanel().getFilterPanel();
		resetListCancelButtons.getBtnCreate().setText("New Filter"); 
		resetListCancelButtons.setBtnCreateIsCancel(false);
	}


	/** Getter for ListRequirementsView
	 * 
	 * @return ListRequirementsView that this controller is in
	 */
	public ListRequirementsView getTheView() {
		return theView;
	}
}

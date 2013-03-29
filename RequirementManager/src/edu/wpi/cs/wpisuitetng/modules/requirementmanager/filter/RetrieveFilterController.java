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


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.Filter;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.FilterBuilderPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.FilterBuilderPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.FilterListPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Controller to handle retrieving one requirement from the server
 */
public class RetrieveFilterController extends MouseAdapter {

	/** The results panel */
        private final ListPanel view;
        private final FilterBuilderPanel builder;

	/**
	 * Construct the controller
	 * 
	 * @param view the parent view 
	 */
	public RetrieveFilterController(ListPanel listView){
	    	this.view =listView;
	    	this.builder = listView.getBuilderPanel();
	}

	/**
	 * @see java.awt.event.MouseAdapter#mouseClicked(MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent me) {
		if (me.getClickCount() >= 2) { /* respond to double clicks */

			// Get a reference to the results JTable from the mouse event
			JTable resultsTable = (JTable) me.getSource();

			// Determine the row the user clicked on
			int row = resultsTable.rowAtPoint(me.getPoint());

			// make sure the user actually clicked on a row
			if (row > -1) {
				String filterId = (String) resultsTable.getValueAt(row, 0);

				// Create and send a request for the requirement with the given ID
				Request request;
				request = Network.getInstance().makeRequest("requirementmanager/filter/" + filterId, HttpMethod.GET);
				request.addObserver(new RetrieveFilterObserver(this));
				request.send();
			}
		}
	}

	/**
	 * Called by {@link RetrieveFilterRequestObserver} when the response
	 * is received from the server.
	 * @param filter the filter that was retrieved
	 */
	public void showFilter(Filter filter) {
		// if a user has double-clicked on a filter, set UI fields appropriately
		
		FilterListPanel panel = view.getFilterPanel();
				
		//Set edit mode
		builder.setCurrentMode(Mode.EDIT);
		builder.getButton().setText("Update");
		builder.getButton().setEnabled(true);
		
		//Type
		builder.getFilterType().setSelectedItem(filter.getType().toString());
		builder.getFilterType().setEnabled(true);
		
		//Comparator
		builder.getFilterOperator().setSelectedItem(filter.getComparator().toString());
		builder.getFilterOperator().setEnabled(true);
		
		//Value
		builder.getFilterValue().setText(filter.getValue());
		builder.getFilterValue().setEnabled(true);
		
		//Value?
		builder.getFilterValueBox().setSelectedItem(filter.getValue());
		builder.getFilterValueBox().setEnabled(true);
		
		//Active
		if(filter.isUseFilter()){
			builder.getStatus().setSelectedIndex(0);
		} else{
			builder.getStatus().setSelectedIndex(1);
		}
		builder.getStatus().setEnabled(true);
		
		//Set current filter to the one retrieved
		builder.setCurrentFilter(filter);
		
		//Update button
		panel.getBtnCreate().setText("New Filter");
		panel.setBtnCreateIsCancel(false);
	}

	/**
	 * Called by {@link RetrieveFilterRequestObserver} when an error
	 * occurred filter the filter from the server.
	 */
	public void errorRetrievingFilter(String error) {
		JOptionPane.showMessageDialog(builder, 
				"An error occurred opening the filter you selected. " + error, "Error opening filter", 
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * @return the panel
	 */
	public FilterListPanel getPanel() {
		return view.getFilterPanel();
	}
}

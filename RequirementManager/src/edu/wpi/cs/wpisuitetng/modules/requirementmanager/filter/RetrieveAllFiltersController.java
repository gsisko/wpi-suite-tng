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

import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter.RetrieveAllFiltersObserver;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.*;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.*;
import static edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.FilterType.*;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Controller to handle retrieving all filters from the server and
 * displaying them in the {@link SearchFiltersView}
 */
public class RetrieveAllFiltersController {

	/** The search filters view */
    private final FilterListPanel filterList;
    @SuppressWarnings("unused")
	private final FilterBuilderPanel builder;
    private final ListRequirementsView view;
    
	/** The filters data retrieved from the server */
	protected Filter[] data = null;

	/**
	 * Constructs a new RetrieveAllFiltersController
	 * 
	 * @param view the search filters view
	 */
	public RetrieveAllFiltersController(ListRequirementsView view){
	    	this.filterList = view.getListPanel().getFilterPanel();
	    	this.builder = view.getListPanel().getBuilderPanel();
	    	this.view = view;
	}

	/**
	 * Sends a request for all of the filters
	 */
	public void refreshData() {
		final RequestObserver requestObserver = new RetrieveAllFiltersObserver(this);
		Request request;
		request = Network.getInstance().makeRequest("filtermanager/filter", HttpMethod.GET);
		request.addObserver(requestObserver);
		request.send();
	}

	/**
	 * This method is called by the {@link RetrieveAllFiltersRequestObserver} when the
	 * response is received
	 * 
	 * @param filters an array of filters returned by the server
	 */
	public void receivedData(Filter[] filters) {
		// empty the table
		String[] emptyColumns = {};
		Object[][] emptyData = {};
		
		filterList.getModel().setColumnNames(emptyColumns);
		filterList.getModel().setData(emptyData);
		filterList.getModel().fireTableStructureChanged();
		
		// Add the list of filters to the FilterListPanel object
		filterList.setLocalFilters(filters);
		
		if (filters.length > 0) {
			// save the data
			this.data = filters;
			
			// set the column names
			String[] columnNames = {"ID", "Type", "Comparator", "Value", "Use Filter"};
			
			// put the data in the table
			Object[][] entries = new Object[filters.length][columnNames.length];
			for (int i = 0; i < filters.length; i++) {
				entries[i][0] = String.valueOf(filters[i].getUniqueID());
				entries[i][1] = filters[i].getType().toString();
				entries[i][2] = filters[i].getComparator().toString();
				entries[i][3] = filters[i].getValue();
				entries[i][4] = filters[i].isUseFilter();
			}
			
			// fill the table
			filterList.getModel().setColumnNames(columnNames);
			filterList.getModel().setData(entries);
			filterList.getModel().fireTableStructureChanged();
		}
		else {
			// do nothing, there are no filters
		}
	}

	/**
	 * This method is called by the {@link RetrieveAllFiltersRequestObserver} when an
	 * error occurs retrieving the filters from the server.
	 */
	public void errorReceivingData(String error) {
		JOptionPane.showMessageDialog(view, "An error occurred retrieving filters from the server. " + error, 
				"Error Communicating with Server", JOptionPane.ERROR_MESSAGE);
	}
}

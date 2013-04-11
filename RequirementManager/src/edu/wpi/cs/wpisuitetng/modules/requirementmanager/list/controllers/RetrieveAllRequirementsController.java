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

import java.util.ArrayList;

import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter.FilterListTab;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.Filter;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.observers.RetrieveAllRequirementsRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.RequirementListPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Controller to handle retrieving all requirements from the server and
 * displaying them in the {@link SearchRequirementsView}
 */
public class RetrieveAllRequirementsController {

	/** The search requirements view */
	//protected ListPanel view;

	/** The panel where the results will display*/
	protected RequirementListPanel resultsPanel;

	/** The panel that contains all the filters*/
	protected FilterListTab filterPanel;
	
	/** The panel that contains all of the lists */
	protected ListView view;

	/** The requirements data retrieved from the server */
	protected Requirement[] data = null;
	
	/**
	 * Constructs a new RetrieveAllRequirementsController
	 * 
	 * @param view the search requirements view
	 */
	public RetrieveAllRequirementsController(ListView view) {
		this.view = view;
		this.resultsPanel = view.getListTab().getResultsPanel();
		this.filterPanel = view.getListTab().getTabPanel().getFilterList();
	}

	/**
	 * Sends a request for all of the requirements
	 */
	public void refreshData() {		
		final RequestObserver requestObserver = new RetrieveAllRequirementsRequestObserver(this);
		Request request;
		request = Network.getInstance().makeRequest("requirementmanager/requirement", HttpMethod.GET);
		request.addObserver(requestObserver);
		request.send();
	}

	/**
	 * This method is called by the {@link RetrieveAllRequirementsRequestObserver} when the
	 * response is received. This method will now also take into account active filters
	 * when determining what requirements to show. 
	 * 
	 * @param requirements an array of requirements returned by the server
	 */
	public void receivedData(Requirement[] requirements) {

		//Array of all the user's filters that are used 
		//(could also be list of all filters and use could be checked here) 
		ArrayList<Filter> filters = filterPanel.getActiveFilters();

		//Array to keep track of which requirements should be filtered
		ArrayList<Requirement> isFiltered = new ArrayList<Requirement>();

		// empty the table
		String[] emptyColumns = {};
		Object[][] emptyData = {};
		
		resultsPanel.getModel().setColumnNames(emptyColumns);
		resultsPanel.getModel().setData(emptyData);
		resultsPanel.getModel().fireTableStructureChanged();

		view.setAllRequirements(requirements);

		// Filtering Phase
		if (requirements.length > 0) {
			// save the data
			this.data = requirements;

			if(filters != null && filters.size() > 0){

				for (int i = 0; i < requirements.length; i++) {
					//if (requirements[i].getStatus() == Deleted) numOfFiltered++;
					boolean passAllFilters = true; // Must reset to true before going into filter loop
					for(int x = 0; x < filters.size(); x++){
						Filter currentFilter = filters.get(x); //get current filter

						if(!currentFilter.passesFilter(requirements[i])){
							passAllFilters = false;
							x=filters.size();
						}
					}
					if(passAllFilters){
						isFiltered.add(requirements[i]);
					}
				}
			}
			else {
				for (int i = 0; i < requirements.length; i++) {
					isFiltered.add(requirements[i]);
				}
			}
		}	
		
		Requirement[] displayedRequirements = new Requirement[isFiltered.size()];
		isFiltered.toArray(displayedRequirements);
		view.setDisplayedRequirements(displayedRequirements);
		
		// Transferring Phase
		// Put the requirements that passed the filters
		if (isFiltered.size() > 0){
			// set the column names
			String[] columnNames = {"ID", "Name", "Description", "Iteration", "Type", "Status", "Priority", "ReleaseNumber", "Estimate", "ActualEffort"};

			// put the data in the table
			Object[][] entries = new Object[isFiltered.size() ][columnNames.length];
			for (int i = 0; i < isFiltered.size(); i++) {				
				//if value at the index i is true, then the filters were all passed
				entries[i][0] = String.valueOf(isFiltered.get(i).getId());
				entries[i][1] = isFiltered.get(i).getName();
				entries[i][2] = isFiltered.get(i).getDescription();
				if (isFiltered.get(i).getAssignedIteration() == 0) {
					entries[i][3] = "Backlog";
				}
				else entries[i][3] = "Hello World";
				
				// Process "NoType" case
				if (isFiltered.get(i).getType().toString().equals("NoType")){
					entries[i][4] = "";					
				} else {
					entries[i][4] = isFiltered.get(i).getType().toString();;
				}				
				entries[i][5] = isFiltered.get(i).getStatus().toString();
				// Process "NoPriority" case
				if (isFiltered.get(i).getPriority().toString().equals("NoPriority")){
					entries[i][6] = "";					
				} else {
					entries[i][6] = isFiltered.get(i).getPriority().toString();
				}
				// Process invalid release number case
				if (isFiltered.get(i).getReleaseNumber() == -1) {
					entries[i][7] = "none";
				} else {
					entries[i][7] = String.valueOf(isFiltered.get(i).getReleaseNumber());
				}
				entries[i][8] = String.valueOf(isFiltered.get(i).getEstimate());
				entries[i][9] = String.valueOf(isFiltered.get(i).getActualEffort());
			}

			// fill the table
			resultsPanel.getModel().setColumnNames(columnNames);
			resultsPanel.getModel().setData(entries);
			try{
			resultsPanel.getModel().fireTableStructureChanged();
			}
			catch(NullPointerException e){
			    return;
			}
			
			//TODO: Move into a reset columns function?
			//Set default widths of all columns
			//ID
			resultsPanel.getResultsTable().getColumnModel().getColumn(0).setPreferredWidth(50);
			//Name
			resultsPanel.getResultsTable().getColumnModel().getColumn(1).setPreferredWidth(150);
			//Description -- "no more description displayed"
			resultsPanel.getResultsTable().getColumnModel().getColumn(2).setMaxWidth(0);
			resultsPanel.getResultsTable().getColumnModel().getColumn(2).setMinWidth(0);
			resultsPanel.getResultsTable().getColumnModel().getColumn(2).setWidth(0);
			resultsPanel.getResultsTable().getColumnModel().getColumn(2).setPreferredWidth(0);
			//Iteration
			resultsPanel.getResultsTable().getColumnModel().getColumn(3).setPreferredWidth(110);
			//Type
			resultsPanel.getResultsTable().getColumnModel().getColumn(4).setPreferredWidth(110);
			//Status
			resultsPanel.getResultsTable().getColumnModel().getColumn(5).setPreferredWidth(100);
			//Priority
			resultsPanel.getResultsTable().getColumnModel().getColumn(6).setPreferredWidth(80);
			//ReleaseNumber
			resultsPanel.getResultsTable().getColumnModel().getColumn(7).setPreferredWidth(150);
			//Estimate
			resultsPanel.getResultsTable().getColumnModel().getColumn(8).setPreferredWidth(90);
			//ActualEffort
			resultsPanel.getResultsTable().getColumnModel().getColumn(9).setPreferredWidth(110);
			
		}
		System.out.println("Existing requirements retrieved successfully.");
	}

	/**
	 * This method is called by the {@link RetrieveAllRequirementsRequestObserver} when an
	 * error occurs retrieving the requirements from the server.
	 */
	public void errorReceivingData(String error) {
		JOptionPane.showMessageDialog(resultsPanel, "An error occurred retrieving requirements from the server. " + error, 
				"Error Communicating with Server", JOptionPane.ERROR_MESSAGE);
	}

	/**A getter required for testing
	 * 
	 * @return resultsPanel
	 */
	public RequirementListPanel getResultsPanel(){
	    return resultsPanel;
	}
	
	/**A getter for use in tests
	 * 
	 * @return the filterPanel
	 */
	public FilterListTab getFilterPanel(){
	    return filterPanel;
	}

}





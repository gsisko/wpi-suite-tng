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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.RowSorter.SortKey;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter.FilterListTab;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.Filter;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.observers.RetrieveAllRequirementsRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.RequirementListPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/** Controller to handle retrieving all requirements from the server and
 * displaying them in the {@link SearchRequirementsView}
 */
public class RetrieveAllRequirementsController {

	/** The panel where the results will display*/
	protected RequirementListPanel resultsPanel;

	/** The panel that contains all the filters*/
	protected FilterListTab filterPanel;

	/** The panel that contains all of the lists */
	protected ListView view;

	/** The requirements data retrieved from the server */
	protected Requirement[] data = null;

	/**Initialize to keep track of table column order */
	protected String[] columnOrder;

	/** ArrayList of integers initialized to keep track of table column width */
	protected ArrayList<Integer> columnWidth  = new ArrayList<Integer>();

	/** ArrayList of strings initialized to keep track of table column width */
	protected ArrayList<String> columnHeader  = new ArrayList<String>();

	/**Used to keep track of table column sort */
	protected java.util.List<? extends SortKey> columnSort;

	/** boolean to designate if there is existing data in the list */
	protected boolean hasPreviousData;

	/** Count the number of refreshes happening */
	private int refreshes = 0;

	/** Constructs a new RetrieveAllRequirementsController
	 * @param view the search requirements view
	 */
	public RetrieveAllRequirementsController(ListView view) {
		this.view = view;
		resultsPanel = view.getListTab().getResultsPanel();
		filterPanel = view.getListTab().getTabPanel().getFilterList();
	}

	/** Sends a request for all of the requirements
	 */
	public void refreshData() {		

		// Set "isRefreshing" to ++ , subtract at the end of refreshes
		refreshes++;

		final RequestObserver requestObserver = new RetrieveAllRequirementsRequestObserver(this);
		Request request;
		request = Network.getInstance().makeRequest("requirementmanager/requirement", HttpMethod.GET);
		request.addObserver(requestObserver);
		request.send();
	}

	/**This method is called by the {@link RetrieveAllRequirementsRequestObserver} when the
	 * response is received. This method will now also take into account active filters
	 * when determining what requirements to show. 
	 * 
	 * @param requirements an array of requirements returned by the server
	 */
	public void receivedData(Requirement[] requirements) {
		// If requirements exist
		if (requirements.length > 0) {
			hasPreviousData = true;
		} else {
			hasPreviousData = false;
		}

		// If the box is not selected (user doesn't want default), and
		// if there is previous data, and
		// if there are rows in the table,
		// get the column widths
		if ((!(view.getCheckBoxStatus())) 
				&& hasPreviousData 
				&& (resultsPanel.getResultsTable().getRowCount() != 0)) {
			columnHeader = getTableName();
			columnWidth = getTableWidth();
			columnSort = getTableSort();
		}

		//Array of all the user's filters that are used 
		//(could also be list of all filters and use could be checked here) 
		ArrayList<Filter> filters = filterPanel.getActiveFilters();

		//Array to keep track of which requirements should be filtered
		ArrayList<Requirement> isFiltered = new ArrayList<Requirement>();

		//Empty the table
		String[] emptyColumns = {};
		Object[][] emptyData = {};

		resultsPanel.getModel().setColumnNames(emptyColumns);
		resultsPanel.getModel().setData(emptyData);
		resultsPanel.getModel().fireTableStructureChanged();

		view.setAllRequirements(requirements);

		// Filtering Phase
		if (requirements.length > 0) {
			//Save the data
			data = requirements;

			if(filters != null && filters.size() > 0) {

				for (int i = 0; i < requirements.length; i++) {
					boolean passAllFilters = true; // Must reset to true before going into filter loop
					for(int x = 0; x < filters.size(); x++){
						Filter currentFilter = filters.get(x); //Get current filter

						if(!currentFilter.passesFilter(requirements[i])){
							passAllFilters = false;
							x=filters.size();
						}
					}
					if(passAllFilters) {
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

		// Set "isRefreshing" to ++ , subtract at the end of refreshes
		refreshes--;

		// Transferring Phase
		// Put the requirements that passed the filters
		if (isFiltered.size() > 0) {
			// Set the column names
			String[] columnNames = {"ID", "Name", "Iteration", "Type", "Status", "Priority", "ReleaseNumber", "Estimate", "ActualEffort"};
			Object[][] entries = new Object[isFiltered.size() ][columnNames.length];

			setColumnEntries(columnNames, entries, isFiltered);

			// Fill the table
			resultsPanel.getModel().setColumnNames(columnNames);
			resultsPanel.getModel().setData(entries);

			try{
				resultsPanel.getModel().fireTableStructureChanged();
			}
			catch(NullPointerException e){
				return;
			}

			// If the box is checked, use defaults, else set custom
			if (view.getCheckBoxStatus()) {
				defaultColumnWidths();
			} else {
				setTableName(columnHeader);
				setTableWidth(columnWidth);
				setTableSort(columnSort);
			}
		}

	}

	/** Set default widths of all columns
	 */
	public void defaultColumnWidths(){
		//ID
		resultsPanel.getResultsTable().getColumnModel().getColumn(0).setPreferredWidth(50);
		//Name
		resultsPanel.getResultsTable().getColumnModel().getColumn(1).setPreferredWidth(150);
		//Iteration
		resultsPanel.getResultsTable().getColumnModel().getColumn(2).setPreferredWidth(110);
		//Type
		resultsPanel.getResultsTable().getColumnModel().getColumn(3).setPreferredWidth(110);
		//Status
		resultsPanel.getResultsTable().getColumnModel().getColumn(4).setPreferredWidth(100);
		//Priority
		resultsPanel.getResultsTable().getColumnModel().getColumn(5).setPreferredWidth(80);
		//ReleaseNumber
		resultsPanel.getResultsTable().getColumnModel().getColumn(6).setPreferredWidth(150);
		//Estimate
		resultsPanel.getResultsTable().getColumnModel().getColumn(7).setPreferredWidth(90);
		//ActualEffort
		resultsPanel.getResultsTable().getColumnModel().getColumn(8).setPreferredWidth(110);
	}

	/** Put the data in the table using default view
	 * 
	 * @param columnNames the name of each column name
	 * @param enteries each cell of the table
	 * @param isFiltered requirement that passed the filter
	 */
	public void setColumnEntries(String[] columnNames, Object[][] entries, ArrayList<Requirement> isFiltered) {

		for (int i = 0; i < isFiltered.size(); i++) {				
			//if value at the index i is true, then the filters were all passed
			entries[i][0] = String.valueOf(isFiltered.get(i).getId());
			entries[i][1] = isFiltered.get(i).getName();
			entries[i][2] = getIterationName(isFiltered.get(i));
			// Process "NoType" case
			if (isFiltered.get(i).getType().toString().equals("NoType")){
				entries[i][3] = "";					
			} else {
				entries[i][3] = isFiltered.get(i).getType().toString();
			}				
			entries[i][4] = isFiltered.get(i).getStatus().toString();
			// Process "NoPriority" case
			if (isFiltered.get(i).getPriority().toString().equals("NoPriority")){
				entries[i][5] = "";					
			} else {
				entries[i][5] = isFiltered.get(i).getPriority().toString();
				if (entries[i][5]=="High"){

				}
				else if (entries[i][5]=="Medium"){

				}
				else if (entries[i][5]=="LOW"){

				}
				else{

				}

			}
			entries[i][6] = isFiltered.get(i).getReleaseNumber();
			entries[i][7] = String.valueOf(isFiltered.get(i).getEstimate());
			entries[i][8] = String.valueOf(isFiltered.get(i).getActualEffort());
		}
	}

	/** A getter to get the current column headers of the table 
	 * @return columnHeader the ArrayList of headers for the table
	 */
	public ArrayList<String> getTableName(){
		columnHeader  = resultsPanel.getTableName();

		return columnHeader;
	}

	/** Put the data in the table using default view
	 * 
	 * @param columnNames the names of each column of the table
	 * @param entries each cell of the table
	 */
	public void setTableName(ArrayList<String> columnNames) {
		int nameSize = columnNames.size();
		int headerSize = resultsPanel.getResultsTable().getColumnCount();
		String headerJ = "";

		for (int i = 0; i < nameSize; i++) {
			for (int j = 0; j < headerSize; j++) {
				headerJ = resultsPanel.getResultsTable().getColumnModel().getColumn(j).getHeaderValue().toString();
				if (columnNames.get(i).equals(headerJ)) {
					resultsPanel.getResultsTable().moveColumn(j, i);
					j = headerSize; // used to break out of inner for loop
				}
			}
		}
	}

	/**A getter to get the current width of the table 
	 * @return columnWidth return the ArrayList of column Widths
	 */
	public ArrayList<Integer> getTableWidth(){
		columnWidth  = new ArrayList<Integer>();

		// Save the width of each column
		for (int i = 0; i < 9; i++){
			columnWidth.add(resultsPanel.getResultsTable().getColumnModel().getColumn(i).getWidth());
		}

		return columnWidth;
	}

	/** Set custom widths of all columns
	 * @param columnWidth the ArrayList of columnWidths
	 */
	public void setTableWidth(ArrayList<Integer> columnWidth){
		// Set the width of each column
		for (int i = 0; i < 9; i++){
			resultsPanel.getResultsTable().getColumnModel().getColumn(i).setPreferredWidth(columnWidth.get(i));
		}
	}

	/**A getter to get the current sorts of the table 
	 * @return columnSort return the ArrayList of column Sorts
	 */
	public java.util.List<? extends SortKey> getTableSort(){
		//get Sorts
		columnSort = resultsPanel.getResultsTable().getRowSorter().getSortKeys();

		return columnSort;
	}

	/**Set custom Sorts of all columns
	 * @param columnSort the ArrayList of columnSorts
	 */
	public void setTableSort(java.util.List<? extends SortKey> columnSort){

		resultsPanel.getResultsTable().getRowSorter().setSortKeys(columnSort);
	}


	/** This method is called by the {@link RetrieveAllRequirementsRequestObserver} when an
	 * error occurs retrieving the requirements from the server.
	 */
	public void errorReceivingData(String error) {
		JOptionPane.showMessageDialog(resultsPanel, "An error occurred retrieving requirements from the server. " + error, 
				"Error Communicating with Server", JOptionPane.ERROR_MESSAGE);
	}

	/** Get the name of the iteration
	 * @param requirement the current requirement
	 * @return
	 */
	private String getIterationName(Requirement requirement) {
		for (Iteration i : view.getAllIterations()) {
			if (requirement.getIteration() == i.getID()) {
				return i.getName();
			}
		}
		return "";
	}

	/**A getter required for testing
	 * @return resultsPanel
	 */
	public RequirementListPanel getResultsPanel(){
		return resultsPanel;
	}

	/**A getter for use in tests
	 * @return the filterPanel
	 */
	public FilterListTab getFilterPanel(){
		return filterPanel;
	}

	/**
	 * @return the refreshes
	 */
	public int getRefreshes() {
		return refreshes;
	}

	/**
	 * @param refreshes the refreshes to set
	 */
	public void setRefreshes(int refreshes) {
		this.refreshes = refreshes;
	}

}

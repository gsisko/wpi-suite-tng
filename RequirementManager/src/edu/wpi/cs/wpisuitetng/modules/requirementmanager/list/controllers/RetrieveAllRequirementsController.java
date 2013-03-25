package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.Filter;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.FilterType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.OperatorType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.observers.RetrieveAllRequirementsRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.FilterListPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListRequirementsView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ResultsPanel;
import static edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementStatus.*;
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
	protected ResultsPanel resultsPanel;
	
	/** The panel that contains all the filters*/
	protected FilterListPanel filterPanel;
	
	
	/** The requirements data retrieved from the server */
	protected Requirement[] data = null;

	/**
	 * Constructs a new RetrieveAllRequirementsController
	 * 
	 * @param view the search requirements view
	 */
	public RetrieveAllRequirementsController(ListRequirementsView view) {
		this.resultsPanel = view.getListPanel().getResultsPanel();
		this.filterPanel = view.getListPanel().getFilterPanel();
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
	 * response is received
	 * 
	 * @param requirements an array of requirements returned by the server
	 */
	public void receivedData(Requirement[] requirements) {
		
		ArrayList<Filter> filters = filterPanel.getActiveFilters(); //function may not be writen yet
		ArrayList<Boolean> isFiltered;
		
		// empty the table
		String[] emptyColumns = {};
		Object[][] emptyData = {};
		resultsPanel.getModel().setColumnNames(emptyColumns);
		resultsPanel.getModel().setData(emptyData);
		resultsPanel.getModel().fireTableStructureChanged();
		
		if (requirements.length > 0) {
			// save the data
			this.data = requirements;
			
			// get the number of requirements whose status is Deleted
			int numOfFiltered = 0;
			Filter currentFilter;
			boolean temp;
			for (int i = 0; i < requirements.length; i++) {
				//if (requirements[i].getStatus() == Deleted) numOfFiltered++;
				for(int x = 0; x < filters.size() ; x++){
					
					//get current filter
					currentFilter = filters.get(x); 
					
					//if the filter is used
					if(currentFilter.isUseFilter()){
						//determine the filter type
						switch(currentFilter.getType()){
						//use the filter helper with the associated filter value, operator, and requirement value
						//use the result to indicate if the requirement being looked at should be filtered out
						case ID:
							temp = filterHelper(currentFilter.getValue(),currentFilter.getComparator(),requirements[i].getId());
							if(temp){
								numOfFiltered++;
								isFiltered.set(i, temp);
							}
							break;
						case NAME:
							temp = filterHelper(currentFilter.getValue(),currentFilter.getComparator(),requirements[i].getName());
							if(temp){
								numOfFiltered++;
								isFiltered.set(i, temp);
							}
							break;
						case DESCRIPTION:
							temp = filterHelper(currentFilter.getValue(),currentFilter.getComparator(),requirements[i].getDescription());
							if(temp){
								numOfFiltered++;
								isFiltered.set(i, temp);
							}
							break;
						case TYPE:
							temp = filterHelper(currentFilter.getValue(),currentFilter.getComparator(),requirements[i].getType());
							if(temp){
								numOfFiltered++;
								isFiltered.set(i, temp);
							}
							break;
						case STATUS:
							temp = filterHelper(currentFilter.getValue(),currentFilter.getComparator(),requirements[i].getStatus());
							if(temp){
								numOfFiltered++;
								isFiltered.set(i, temp);
							}
							break;
						case PRIORITY:
							temp = filterHelper(currentFilter.getValue(),currentFilter.getComparator(),requirements[i].getPriority());
							if(temp){
								numOfFiltered++;
								isFiltered.set(i, temp);
							}
							break;
						case RELEASE_NUMBER:
							temp = filterHelper(currentFilter.getValue(),currentFilter.getComparator(),requirements[i].getReleaseNumber());
							if(temp){
								numOfFiltered++;
								isFiltered.set(i, temp);
							}
							break;
						case ESTIMATE:
							temp = filterHelper(currentFilter.getValue(),currentFilter.getComparator(),requirements[i].getEstimate());
							if(temp){
								numOfFiltered++;
								isFiltered.set(i, temp);
							}
							break;
						case ACTUAL_EFFORT:
							temp = filterHelper(currentFilter.getValue(),currentFilter.getComparator(),requirements[i].getActualEffort());
							if(temp){
								numOfFiltered++;
								isFiltered.set(i, temp);
							}
							break;
						case OTHER:
							break;
							
						}
					}
				}
			}
			
			if (requirements.length > numOfFiltered){

				// set the column names
				String[] columnNames = {"ID", "Name", "Description", "Type", "Status", "Priority", "ReleaseNum", "Estimate", "ActualEffort"};
				
				// put the data in the table
				Object[][] entries = new Object[requirements.length - numOfFiltered][columnNames.length];
				int j = 0;
				for (int i = 0; i < requirements.length; i++) {
					
					//if value at the index i is true, then the filters were all passed
					if (isFiltered.get(i)){//   requirements[i].getStatus() != Deleted) {
						entries[j][0] = String.valueOf(requirements[i].getId());
						entries[j][1] = requirements[i].getName();
						entries[j][2] = requirements[i].getDescription();
						entries[j][3] = requirements[i].getType().toString();
						entries[j][4] = requirements[i].getStatus().toString();
						entries[j][5] = requirements[i].getPriority().toString();
						if (requirements[i].getReleaseNumber() == -1) {
							entries[j][6] = "none";
						} else {
							entries[j][6] = String.valueOf(requirements[i].getReleaseNumber());
						}
						entries[j][7] = String.valueOf(requirements[i].getEstimate());
						entries[j][8] = String.valueOf(requirements[i].getActualEffort());
						j++; 
					}
				}
				
				// fill the table
				resultsPanel.getModel().setColumnNames(columnNames);
				resultsPanel.getModel().setData(entries);
				resultsPanel.getModel().fireTableStructureChanged();
				
			}
		}
		else {
			// do nothing, there are no non-Deleted requirements
		}
	}

	/**
	 * This method is called by the {@link RetrieveAllRequirementsRequestObserver} when an
	 * error occurs retrieving the requirements from the server.
	 */
	public void errorReceivingData(String error) {
		JOptionPane.showMessageDialog(resultsPanel, "An error occurred retrieving requirements from the server. " + error, 
				"Error Communicating with Server", JOptionPane.ERROR_MESSAGE);
	}
	
	
	/** work in progress
	 * 
	 * @param filterValue
	 * @param op
	 * @param requirementValue
	 * @return
	 */
	public boolean filterHelper(Object filterValue, OperatorType op, Object requirementValue){
		switch(op){
		case GreaterThan:
			return (int) requirementValue > (int) filterValue;
		case GreaterThanOrEqualTo:
			return (int) requirementValue >= (int) filterValue;
		case LessThan:
			return (int) requirementValue < (int) filterValue;
		case LessThanOrEqualTo:
			return (int) requirementValue <= (int) filterValue;
		case EqualTo:
			return requirementValue.equals(filterValue);
		case NotEqualTo:
			return !(requirementValue.equals(filterValue));
		case Contains:
			return requirementValue.toString().contains(filterValue.toString());
		case DoesNotContain:
			return !(requirementValue.toString().contains(filterValue.toString()));
		case OTHER:
			return true;
		}
		return true;
	}
}





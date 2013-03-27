package edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter;

import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter.RetrieveAllFiltersObserver;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.*;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.*;
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
    private final FilterListPanel panel;
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
	    	this.panel = view.getListPanel().getFilterPanel();
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
		/*
		// empty the table
		String[] emptyColumns = {};
		Object[][] emptyData = {};
		view.getModel().setColumnNames(emptyColumns);
		view.getModel().setData(emptyData);
		view.getModel().fireTableStructureChanged();
		
		if (filters.length > 0) {
			// save the data
			this.data = filters;
			
			// get the number of filters whose status is Deleted
			int numOfDeleted = 0;
			for (int i = 0; i < filters.length; i++) {
				if (filters[i].getStatus() == Deleted) numOfDeleted++;
			}
			
			if (filters.length > numOfDeleted){

				// set the column names
				String[] columnNames = {"ID", "Name", "Description", "Type", "Status", "Priority", "ReleaseNum", "Estimate", "ActualEffort"};
				
				// put the data in the table
				Object[][] entries = new Object[filters.length - numOfDeleted][columnNames.length];
				int j = 0;
				for (int i = 0; i < filters.length; i++) {
					if (filters[i].getStatus() != Deleted) {
						entries[j][0] = String.valueOf(filters[i].getId());
						entries[j][1] = filters[i].getName();
						entries[j][2] = filters[i].getDescription();
						entries[j][3] = filters[i].getType().toString();
						entries[j][4] = filters[i].getStatus().toString();
						entries[j][5] = filters[i].getPriority().toString();
						if (filters[i].getReleaseNumber() == -1) {
							entries[j][6] = "none";
						} else {
							entries[j][6] = String.valueOf(filters[i].getReleaseNumber());
						}
						entries[j][7] = String.valueOf(filters[i].getEstimate());
						entries[j][8] = String.valueOf(filters[i].getActualEffort());
						j++; 
					}
				}
				
				// fill the table
				view.getModel().setColumnNames(columnNames);
				view.getModel().setData(entries);
				view.getModel().fireTableStructureChanged();
				
			}
		}
		else {
			// do nothing, there are no non-Deleted filters
		}*/
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

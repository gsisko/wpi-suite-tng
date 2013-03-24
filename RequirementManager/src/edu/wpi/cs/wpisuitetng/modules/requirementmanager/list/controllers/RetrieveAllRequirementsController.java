package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers;

import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.observers.RetrieveAllRequirementsRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListRequirementsView;
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
	protected ListPanel view;

	/** The requirements data retrieved from the server */
	protected Requirement[] data = null;

	/**
	 * Constructs a new RetrieveAllRequirementsController
	 * 
	 * @param view the search requirements view
	 */
	public RetrieveAllRequirementsController(ListRequirementsView view) {
		this.view = view.getListPanel();
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
		
		// empty the table
		String[] emptyColumns = {};
		Object[][] emptyData = {};
		view.getModel().setColumnNames(emptyColumns);
		view.getModel().setData(emptyData);
		view.getModel().fireTableStructureChanged();
		
		if (requirements.length > 0) {
			// save the data
			this.data = requirements;
			
			// get the number of requirements whose status is Deleted
			int numOfDeleted = 0;
			for (int i = 0; i < requirements.length; i++) {
				if (requirements[i].getStatus() == Deleted) numOfDeleted++;
			}
			
			if (requirements.length > numOfDeleted){

				// set the column names
				String[] columnNames = {"ID", "Name", "Description", "Type", "Status", "Priority", "ReleaseNum", "Estimate", "ActualEffort"};
				
				// put the data in the table
				Object[][] entries = new Object[requirements.length - numOfDeleted][columnNames.length];
				int j = 0;
				for (int i = 0; i < requirements.length; i++) {
					if (requirements[i].getStatus() != Deleted) {
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
				view.getModel().setColumnNames(columnNames);
				view.getModel().setData(entries);
				view.getModel().fireTableStructureChanged();
				
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
		JOptionPane.showMessageDialog(view, "An error occurred retrieving requirements from the server. " + error, 
				"Error Communicating with Server", JOptionPane.ERROR_MESSAGE);
	}
}

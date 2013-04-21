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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement;

import java.util.ArrayList;

import javax.swing.JOptionPane;

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

/**
 * Controller to handle retrieving all sub requirements from the server and
 * displaying them in the {@link SearchRequirementsView}
 */
public class RetrieveRequirmentsIntoSubController {

	/** The panel where the results will display*/
	protected SubRequirementTab subtab;

	/** The requirements data retrieved from the server */
	protected Requirement[] data;

	private RequirementView view;



	/**
	 * Constructs a new RetrieveAllRequirementsController
	 * 
	 * @param view the search requirements view
	 */
	public RetrieveRequirmentsIntoSubController(RequirementView view) {
		this.view = view;
		//this.subtab = view.getRequirementTabPanel().getSubRequirementPanel();
	}

	/**
	 * Sends a request for all of the requirements
	 */
	public void refreshData() {		
		final RequestObserver requestObserver = new RetrieveRequirementsIntoSubObserver(this);
		Request request;
		request = Network.getInstance().makeRequest("requirementmanager/requirement", HttpMethod.GET);
		request.addObserver(requestObserver);
		request.send();
	}

	/**
	 * This method is called by the {@link RetrieveAllRequirementsRequestObserver} when the
	 * response is receiv This method will now also take into account active filters
	 * when determining what requirements to show. 
	 * 
	 * @param requirements an array of requirements returned by the server
	 */
	public void receivedData(Requirement[] requirements) {


		//Array to keep track of which requirements should be filtered
		ArrayList<Requirement> isSub = new ArrayList<Requirement>();

		//isSub = this.subtab.getParent().getCurrentRequirement().getSubrequirement();
		isSub.add(new Requirement());
		// empty the table
		//		String[] emptyColumns = {};
		//		Object[][] emptyData = {};
		//		String[] columnNames = {"ID", "Name", "Description", "Iteration", "Type", "Status", "Priority", "ReleaseNumber", "Estimate", "ActualEffort"};
		//		subtab.getModel().setColumnNames(columnNames);
		//		subtab.getModel().setData(emptyData);
		//		subtab.getModel().fireTableStructureChanged();

		view.setSubRequirements(requirements);

		if (requirements.length > 0) {
			// save the data

			this.data = requirements;

		}	

		view.setDisplayedRequirements(requirements);


		// set the column names
		String[] columnNames = {"ID", "Name", "Description", "Iteration", "Type", "Status", "Priority", "ReleaseNumber", "Estimate", "ActualEffort"};

		// put the data in the table
		Object[][] entries = new Object[isSub.size()][columnNames.length];
		for (int i = 0; i < isSub.size(); i++) {				
			//if value at the index i is true, then the filters were all passed
			entries[i][0] = String.valueOf(isSub.get(i).getId());
			entries[i][1] = isSub.get(i).getName();
			entries[i][2] = isSub.get(i).getDescription();
			//entries[i][3] = getIterationName(isSub.get(i));
			entries[i][3] = "sss";
			// Process "NoType" case
			if (isSub.get(i).getType().toString().equals("NoType")){
				entries[i][4] = "";					
			} else {
				entries[i][4] = isSub.get(i).getType().toString();
			}				
			entries[i][5] = isSub.get(i).getStatus().toString();
			// Process "NoPriority" case
			if (isSub.get(i).getPriority().toString().equals("NoPriority")){
				entries[i][6] = "";					
			} else {
				entries[i][6] = isSub.get(i).getPriority().toString();
			}
			entries[i][7] = isSub.get(i).getReleaseNumber();
			entries[i][8] = String.valueOf(isSub.get(i).getEstimate());
			entries[i][9] = String.valueOf(isSub.get(i).getActualEffort());
		}

		// fill the table
		subtab.getModel().setColumnNames(columnNames);
		subtab.getModel().setData(entries);
		try{
			subtab.getModel().fireTableStructureChanged();
		}
		catch(NullPointerException e){
			return;
		}

		//Set default widths of all columns
		//ID
		subtab.getResultsTable().getColumnModel().getColumn(0).setPreferredWidth(50);
		//Name
		subtab.getResultsTable().getColumnModel().getColumn(1).setPreferredWidth(150);
		//Description -- "no more description displayed"
		subtab.getResultsTable().getColumnModel().getColumn(2).setMaxWidth(0);
		subtab.getResultsTable().getColumnModel().getColumn(2).setMinWidth(0);
		subtab.getResultsTable().getColumnModel().getColumn(2).setWidth(0);
		subtab.getResultsTable().getColumnModel().getColumn(2).setPreferredWidth(0);
		//Iteration
		subtab.getResultsTable().getColumnModel().getColumn(3).setPreferredWidth(110);
		//Type
		subtab.getResultsTable().getColumnModel().getColumn(4).setPreferredWidth(110);
		//Status
		subtab.getResultsTable().getColumnModel().getColumn(5).setPreferredWidth(100);
		//Priority
		subtab.getResultsTable().getColumnModel().getColumn(6).setPreferredWidth(80);
		//ReleaseNumber
		subtab.getResultsTable().getColumnModel().getColumn(7).setPreferredWidth(150);
		//Estimate
		subtab.getResultsTable().getColumnModel().getColumn(8).setPreferredWidth(90);
		//ActualEffort
		subtab.getResultsTable().getColumnModel().getColumn(9).setPreferredWidth(110);
	}


	private String getIterationName(Requirement requirement) {

		for (Iteration i : subtab.getParent().getAllIterations()) {
			if (requirement.getIteration() == i.getID()) {
				return i.getName();
			}
		}
		return "";
	}

	/**set subtab
	 * 
	 */

	public void setSubtab()
	{
		//this.subtab = view.getRequirementTabPanel().getSubRequirementPanel();
		this.subtab = new SubRequirementTab(view.getRequirementPanel());
	}
	/**
	 * This method is called by the {@link RetrieveAllRequirementsRequestObserver} when an
	 * error occurs retrieving the requirements from the server.
	 */
	public void errorReceivingData(String error) {
		JOptionPane.showMessageDialog(subtab, "An error occurred retrieving requirements from the server. " + error, 
				"Error Communicating with Server", JOptionPane.ERROR_MESSAGE);
	}

	/**A getter required for testing
	 * 
	 * @return subtab
	 */
	public SubRequirementTab getsubtab(){
		return subtab;
	}

}





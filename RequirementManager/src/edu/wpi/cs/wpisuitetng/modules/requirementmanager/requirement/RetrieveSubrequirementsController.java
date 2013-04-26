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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.observers.RetrieveAllRequirementsRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementTab.Mode;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Controller to handle retrieving all sub requirements from the server and
 * displaying them in the {@link SearchRequirementsView}
 */
public class RetrieveSubrequirementsController {

	/** The panel where the results will display*/
	protected SubRequirementTab subtab;

	private RequirementView view;



	/**
	 * Constructs a new RetrieveAllRequirementsController
	 * 
	 * @param view the search requirements view
	 */
	public RetrieveSubrequirementsController(RequirementView view) {
		this.view = view;
		subtab = view.getRequirementTabPanel().getSubRequirementPanel();
	}

	/**
	 * Sends a request for all of the requirements
	 */
	public void refreshData() {		
		final RequestObserver requestObserver = new RetrieveSubrequirementsObserver(this);
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

		if (view.getRequirementPanel().getMode() == Mode.CREATE)
			return;
		
		Requirement parentRequirement = view.getRequirementPanel().getCurrentRequirement();

		//Array to keep track of which requirements should be filtered
		ArrayList<Requirement> subrequirements = new ArrayList<Requirement>();

		// empty the table
		String[] emptyColumns = {};
		Object[][] emptyData = {};
		subtab.getModel().setColumnNames(emptyColumns);
		subtab.getModel().setData(emptyData);
		subtab.getModel().fireTableStructureChanged();


		//subrequirements.add(new Requirement());

		for (int i = 0; i < requirements.length; i++) {
			if (parentRequirement.getSubrequirements().contains(requirements[i].getId())) {
				subrequirements.add(requirements[i]);
			}
		}

		if (subrequirements.size() > 0) {

			// set the column names
			String[] columnNames = {"ID", "Name", "Description", "Iteration", "Type", "Status", "Priority", "ReleaseNumber", "Estimate", "ActualEffort"};

			// put the data in the table
			Object[][] entries = new Object[subrequirements.size()][columnNames.length];
			for (int i = 0; i < subrequirements.size(); i++) {				
				//if value at the index i is true, then the filters were all passed
				entries[i][0] = String.valueOf(subrequirements.get(i).getId());
				entries[i][1] = subrequirements.get(i).getName();
				entries[i][2] = subrequirements.get(i).getDescription();
				entries[i][3] = getIterationName(parentRequirement);
				// Process "NoType" case
				if (subrequirements.get(i).getType().toString().equals("NoType")){
					entries[i][4] = "";					
				} else {
					entries[i][4] = subrequirements.get(i).getType().toString();
				}				
				entries[i][5] = subrequirements.get(i).getStatus().toString();
				// Process "NoPriority" case
				if (subrequirements.get(i).getPriority().toString().equals("NoPriority")){
					entries[i][6] = "";					
				} else {
					entries[i][6] = subrequirements.get(i).getPriority().toString();
				}
				entries[i][7] = subrequirements.get(i).getReleaseNumber();
				entries[i][8] = String.valueOf(subrequirements.get(i).getEstimate());
				entries[i][9] = String.valueOf(subrequirements.get(i).getActualEffort());
			}

			// fill the table
			subtab.getModel().setColumnNames(columnNames);
			subtab.getModel().setData(entries);
			subtab.getModel().fireTableStructureChanged();


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
	}


	private String getIterationName(Requirement requirement) {

		for (Iteration i : subtab.getParent().getAllIterations()) {
			if (requirement.getIteration() == i.getID()) {
				return i.getName();
			}
		}
		return "";
	}

	/**
	 * set subtab
	 */

	public void setSubtab()
	{
		//this.subtab = view.getRequirementTabPanel().getSubRequirementPanel();
		subtab = new SubRequirementTab(view.getRequirementPanel());
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





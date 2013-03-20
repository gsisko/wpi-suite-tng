package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.views.RequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.observers.RetrieveRequirementRequestObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Controller to handle retrieving one requirement from the server
 */
public class RetrieveRequirementController extends MouseAdapter {

	/** The results panel */
	protected RequirementPanel view;

	/**
	 * Construct the controller
	 * 
	 * @param view the parent view 
	 */
	public RetrieveRequirementController(RequirementPanel view) {
		this.view = view;
	}

	/**
	 * @see java.awt.event.MouseAdapter#mouseClicked(MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent me) {
		if (me.getClickCount() == 2) { /* respond to single clicks */

			// Get a reference to the results JTable from the mouse event
			JTable resultsTable = (JTable) me.getSource();

			// Determine the row the user clicked on
			int row = resultsTable.rowAtPoint(me.getPoint());

			// make sure the user actually clicked on a row
			if (row > -1) {
				String requirementId = (String) resultsTable.getValueAt(row, 0);

				// Create and send a request for the requirement with the given ID
				Request request;
				request = Network.getInstance().makeRequest("requirementmanager/requirement/"/* + requirementId*/, HttpMethod.GET);
				request.addObserver(new RetrieveRequirementRequestObserver(this, Integer.parseInt(requirementId)));
				request.send();
			}
		}
	}

	/**
	 * Called by {@link RetrieveRequirementRequestObserver} when the response
	 * is received from the server.
	 * @param requirement the requirement that was retrieved
	 */
	public void showRequirement(Requirement requirement) {
		view.setCreateNew(false);

		view.getRequirementName().setText(requirement.getName());
		view.getRequirementDescription().setText(requirement.getDescription());
		view.getRequirementStatus().setSelectedItem(requirement.getStatus().toString());
		view.getRequirementPriority().setSelectedItem(requirement.getPriority().toString());
		view.getRequirementReleaseNumber().setText(Integer.toString(requirement.getReleaseNumber()));
		view.getRequirementEstimate().setText(Integer.toString(requirement.getEstimate()));
		view.getRequirementActualEffort().setText(Integer.toString(requirement.getActualEffort()));
		
		view.getSaveButton().setText("Update");
		view.getSaveButton().setEnabled(true);

		view.getRequirementName().setEnabled(true);
		view.getRequirementDescription().setEnabled(true);
		view.getRequirementStatus().setEnabled(true);
		view.getRequirementPriority().setEnabled(true);
		view.getRequirementReleaseNumber().setEnabled(true);
		view.getRequirementEstimate().setEnabled(true);
		view.getRequirementActualEffort().setEnabled(true);
	}

	/**
	 * Called by {@link RetrieveRequirementRequestObserver} when an error
	 * occurred retrieving the requirement from the server.
	 */
	public void errorRetrievingRequirement(String error) {
		JOptionPane.showMessageDialog(view, 
				"An error occurred opening the requirement you selected. " + error, "Error opening requirement", 
				JOptionPane.ERROR_MESSAGE);
	}
}

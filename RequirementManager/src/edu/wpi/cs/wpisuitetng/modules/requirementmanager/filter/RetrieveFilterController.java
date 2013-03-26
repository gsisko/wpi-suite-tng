package edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.Filter;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.*;
//import static edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.FilterPanel.Mode.*;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter.RetrieveFilterObserver;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.FilterBuilderPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.FilterListPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListRequirementsView;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import static edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.FilterType.*;

/**
 * Controller to handle retrieving one requirement from the server
 */
public class RetrieveFilterController extends MouseAdapter {

	/** The results panel */
        private final FilterListPanel panel;
        private final FilterBuilderPanel builder;

	/**
	 * Construct the controller
	 * 
	 * @param view the parent view 
	 */
	public RetrieveFilterController(ListRequirementsView view){
	    	this.panel = view.getListPanel().getFilterPanel();
	    	this.builder = view.getListPanel().getBuilderPanel();
	}

	/**
	 * @see java.awt.event.MouseAdapter#mouseClicked(MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent me) {
		if (me.getClickCount() == 2) { /* respond to double clicks */

			// Get a reference to the results JTable from the mouse event
			JTable resultsTable = (JTable) me.getSource();

			// Determine the row the user clicked on
			int row = resultsTable.rowAtPoint(me.getPoint());

			// make sure the user actually clicked on a row
			if (row > -1) {
				String requirementId = (String) resultsTable.getValueAt(row, 0);

				// Create and send a request for the requirement with the given ID
				Request request;
				request = Network.getInstance().makeRequest("requirementmanager/requirement/" + requirementId, HttpMethod.GET);
				request.addObserver(new RetrieveFilterObserver(this));
				request.send();
			}
		}
	}

	/**
	 * Called by {@link RetrieveFilterRequestObserver} when the response
	 * is received from the server.
	 * @param requirement the requirement that was retrieved
	 */
	public void showFilter(Filter requirement) {
		// if a user has double-clicked on a requirement, set UI fields appropriately
		
		builder.setMode(EDIT);
		/*
		view.getFilterName().setText(requirement.getName());
		view.getFilterDescription().setText(requirement.getDescription());
		view.getFilterType().setSelectedItem(requirement.getType().toString());
		view.getFilterStatus().setSelectedItem(requirement.getStatus().toString());
		view.getFilterPriority().setSelectedItem(requirement.getPriority().toString());
		if (requirement.getReleaseNumber() == -1) {
			view.getFilterReleaseNumber().setText("");
		} else {
			view.getFilterReleaseNumber().setText(Integer.toString(requirement.getReleaseNumber()));
		}
		view.getFilterEstimate().setText(Integer.toString(requirement.getEstimate()));
		view.getFilterActualEffort().setText(Integer.toString(requirement.getActualEffort()));
		
		view.getSaveButton().setText("Update");
		view.getSaveButton().setEnabled(true);

		view.getFilterName().setEnabled(true);
		view.getFilterDescription().setEnabled(true);
		view.getFilterType().setEnabled(true);
		view.getFilterStatus().setEnabled(true);
		view.getFilterPriority().setEnabled(true);
		view.getFilterReleaseNumber().setEnabled(true);
		if (requirement.getStatus() == InProgress || requirement.getStatus() == Complete) {
			view.getFilterEstimate().setEnabled(false);
		} else {
			view.getFilterEstimate().setEnabled(true);
		}
		builder.getFilterActualEffort().setEnabled(true);
		
		builder.setCurrentFilter(requirement);*/
	}

	/**
	 * Called by {@link RetrieveFilterRequestObserver} when an error
	 * occurred retrieving the requirement from the server.
	 */
	public void errorRetrievingFilter(String error) {
		JOptionPane.showMessageDialog(builder, 
				"An error occurred opening the requirement you selected. " + error, "Error opening requirement", 
				JOptionPane.ERROR_MESSAGE);
	}
}

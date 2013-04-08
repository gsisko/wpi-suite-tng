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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.iteration;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.DeleteModelController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.RetrieveAllModelsController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.RetrieveModelController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.ResultsTableModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IListPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListTab;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.NewModelAction;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Iteration;

/**
 * Panel to contain the list of Iterations that have been saved by the user
 */
@SuppressWarnings("serial")
public class IterationListTab extends JPanel implements IListPanel {

	/** The table of results */
	protected JTable resultsTable;
	protected JButton btnCreate;
	protected JButton btnDelete;
	private boolean btnCreateIsCancel;

	private Iteration[] localIterations = {};
	
	private RetrieveAllModelsController retrieveAllController;
	private DeleteModelController deleteController;
	private RetrieveModelController retrieveController;

	/** The model containing the data to be displayed in the results table */
	protected ResultsTableModel resultsTableModel;

	private final ListTab parent;
	
	/**
	 * Construct the panel
	 */
	public IterationListTab(ListTab view) {
		parent = view;
		this.setBtnCreateIsCancel(false);
		// Set the layout manager
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		// Construct the table model
		resultsTableModel = new ResultsTableModel();

		// Construct the table and configure it
		resultsTable = new JTable(resultsTableModel);
		resultsTable.setAutoCreateRowSorter(true);
		resultsTable.setFillsViewportHeight(true);
		resultsTable.setDefaultRenderer(Date.class, new DefaultTableCellRenderer() {

		    SimpleDateFormat f = new SimpleDateFormat("MM/dd/yy");

		    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		        if( value instanceof Date) {
		            value = f.format(value);
		        }
		        return super.getTableCellRendererComponent(table, value, isSelected,
		                hasFocus, row, column);
		    }
		}
		);
		

		// Put the table in a scroll pane
		JScrollPane resultsScrollPane = new JScrollPane(resultsTable);
		resultsScrollPane.setPreferredSize(new Dimension(175,250));
		this.add(resultsScrollPane);
		resultsScrollPane.setAlignmentX(CENTER_ALIGNMENT);

		this.add(Box.createRigidArea(new Dimension(0,6)));

		btnCreate = new JButton ("New Iteration");
		btnDelete = new JButton ("Delete");

		btnCreate.setMaximumSize(new Dimension(120, 40));
		btnCreate.setMinimumSize(new Dimension(120, 40));
		btnDelete.setMaximumSize(new Dimension(120, 40));
		btnDelete.setMinimumSize(new Dimension(120, 40));

		this.add(btnCreate);
		this.add(Box.createRigidArea(new Dimension(0,6)));
		this.add(btnDelete);
		this.add(Box.createRigidArea(new Dimension(0,6)));
		btnCreate.setAlignmentX(CENTER_ALIGNMENT);
		btnDelete.setAlignmentX(CENTER_ALIGNMENT);

		retrieveAllController = new RetrieveAllModelsController(this, parent.getIterationBuilderPanel(), "iteration");
		deleteController = new DeleteModelController(this, parent.getIterationBuilderPanel(), "iteration");
		retrieveController = new RetrieveModelController(this, parent.getIterationBuilderPanel(), "iteration");
		
		// Add a listener for row clicks
		resultsTable.addMouseListener(retrieveController);

		
		// Sets up listener system. Once pressed, changes to CancelIterationAction listener, then back to this.
		btnCreate.addActionListener(new NewModelAction(this, parent.getIterationBuilderPanel()));
		btnDelete.addActionListener(deleteController);
		
		
	}

	/**This method returns an ArrayList of active Iterations
	 * 
	 * @return activeIterations An ArrayList of the active Iterations
	 */
	public ArrayList<Iteration> getIterations() {
		ArrayList<Iteration> allIterations = new ArrayList<Iteration>();

		for(int i = 0; i < localIterations.length; i++){
			allIterations.add(localIterations[i]);
		}

		return allIterations;
	}

	/**
	 * @return the data model for the table
	 */
	public ResultsTableModel getModel() {
		return resultsTableModel;
	}

	/**
	 * @return the results table
	 */
	public JTable getResultsTable() {
		return resultsTable;
	}

	/**
	 * Replace the results table with the given table
	 * @param newTable the new results table
	 */
	public void setResultsTable(JTable newTable) {
		resultsTable = newTable;
	}

	/**
	 * @return the parent
	 */
	public ListTab getParent() {
		return parent;
	}

	/**
	 * @return the localIterations
	 */
	public Iteration[] getLocalIterations() {
		return localIterations;
	}

	/**
	 * @param localIterations the localIterations to set
	 */
	public void setLocalIterations(Iteration[] localIterations) {
		this.localIterations = localIterations;
	}

	public JButton getBtnCreate(){
		return btnCreate;
	}

	/**
	 * @return the btnCreateIsCancel
	 */
	public boolean isBtnCreateIsCancel() {
		return btnCreateIsCancel;
	}

	/**
	 * @param btnCreateIsCancel the btnCreateIsCancel to set
	 */
	public void setBtnCreateIsCancel(boolean btnCreateIsCancel) {
		this.btnCreateIsCancel = btnCreateIsCancel;
	}

	/**
	 * Set the cancel button back to New Iteration if it was in cancel mode
	 */
	public void setCancelBtnToNew() {
		this.getBtnCreate().setText("New Iteration"); 
		this.setBtnCreateIsCancel(false);
	}
	
	/**
	 * Set the new iteration button to cancel
	 */
	public void setNewBtnToCancel(){
		getBtnCreate().setText("Cancel"); 
		setBtnCreateIsCancel(true);
	}

	/** 
	 * Toggles between "New Model" and "Cancel" mode 
	 */
	public void toggleNewCancelMode() {
		btnCreateIsCancel = !btnCreateIsCancel;
		if(btnCreateIsCancel)
			this.getBtnCreate().setText("Cancel"); 			
		else
			this.getBtnCreate().setText("New Iteration");
	}

	/** Begins refresh process, allows the panels to start triggering
	 *  their own controllers and chains of controllers
	 * 
	 * @return true on success, false on failure
	 */
	public boolean refreshAll() {
		retrieveAllController.refreshData();
		return true;
	}

	/** Gets the unique identifier of the list entry that was double clicked
	 * 
	 * @return The unique identifier, either name or ID number
	 */
	public String getSelectedUniqueIdentifier(MouseEvent me) {

		// Determine the row the user clicked on
		int row = resultsTable.rowAtPoint(me.getPoint());

		String iterationId = null;

		// make sure the user actually clicked on a row
		if (row > -1) {
			iterationId = resultsTable.getValueAt(row, 0).toString();
		}

		return iterationId;
	}

	/** Takes whatever model(s) is(are) stored in the the current panel,
	 *  and returns the unique identifier(s) in an array. Generally
	 *  pulls the highlighted identifiers from a table view.
	 * 
	 * @return An array of unique identifiers in the form of strings
	 */
	public String[] getUniqueIdentifiers() {
		
		// get highlighted rows 
		int[] rowNumbers = resultsTable.getSelectedRows();

		String[] ids = new String [rowNumbers.length];

		// get array of row numbers, if there are any highlighted rows
		for(int i = 0; i < rowNumbers.length; i++){			
			// Getting the name from the current highlighted row
			ids[i] = resultsTable.getValueAt(rowNumbers[i], 0).toString();
		}

		return ids;
	}

	/** 
	 * Show the models in the list view
	 *  Do nothing in builder
	 * 
	 * @param jsonString An array of models in the form of a JSON string
	 */
	public void showRecievedModels(String jsonString) {
		// empty the table
		String[] emptyColumns = {};
		Object[][] emptyData = {};

		// Fire blanks so that the old contents are removed
		this.getModel().setColumnNames(emptyColumns);
		this.getModel().setData(emptyData);
		this.getModel().fireTableStructureChanged();
		
		
		Iteration[] iterations = Iteration.fromJSONArray(jsonString);
		// Add the list of iterations to the IterationListPanel object
		this.setLocalIterations(iterations);

		parent.getParent().setAllIterations(iterations);
		
		Iteration[] displayedIterations = new Iteration[iterations.length-1];
		for (int i = 0; i < displayedIterations.length; i++) {
			displayedIterations[i] = iterations[i+1];
		}
		
		if (displayedIterations.length > 0) {
			// set the column names
			String[] columnNames = {"Id", "Name", "StartDate", "EndDate"};

			// put the data in the table
			Object[][] entries = new Object[displayedIterations.length][columnNames.length];
			for (int i = 0; i < displayedIterations.length; i++) {
				entries[i][0] = displayedIterations[i].getID();
				entries[i][1] = displayedIterations[i].getName();
				entries[i][2] = displayedIterations[i].getStartDate();
				entries[i][3] = displayedIterations[i].getEndDate();

			}

			// fill the table
			this.getModel().setColumnNames(columnNames);
			this.getModel().setData(entries);
			this.getModel().fireTableStructureChanged();
			
			//Hide the Id column
			resultsTable.getColumn("Id").setMinWidth(0);
			resultsTable.getColumn("Id").setMaxWidth(0);
			resultsTable.getColumn("Id").setWidth(0);
			
			//Set preferred column widths
			//Name
			resultsTable.getColumnModel().getColumn(1).setPreferredWidth(75);
			//StartDate
			resultsTable.getColumnModel().getColumn(2).setPreferredWidth(80);
			//EndDate
			resultsTable.getColumnModel().getColumn(3).setPreferredWidth(75);
			
			
			return; // end now
		}
	}

	/**
	 * @return the retrieveAllController
	 */
	public RetrieveAllModelsController getRetrieveAllController() {
		return retrieveAllController;
	}

	/**
	 * @param retrieveAllController the retrieveAllController to set
	 */
	public void setRetrieveAllController(RetrieveAllModelsController retrieveAllController) {
		this.retrieveAllController = retrieveAllController;
	}
	
	@Override
	public void refreshRequirements() {
		parent.getParent().getController().refreshData();
	}
	
}


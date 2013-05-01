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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.iteration;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.DeleteModelController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.RetrieveAllModelsController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.RetrieveModelController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.ResultsTableModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ActivateDeleteButton;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IListPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListTab;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.NewModelAction;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ActiveIterationTableCellRenderer;

/** Panel to contain the list of Iterations that have been saved by the user
 */
@SuppressWarnings("serial")
public class IterationListTab extends JPanel implements IListPanel {

	/** The table of results */
	protected JTable resultsTable;
	/** The "Create Iteration" button */
	protected JButton btnCreate;
	/** The "Delete" button */
	protected JButton btnDelete;
	/** The status of the create button */
	private boolean btnCreateIsCancel;

	/** Local storage of the iterations */
	private Iteration[] localIterations = {};
	
	/** The controller for retrieving all the iterations */
	private RetrieveAllModelsController retrieveAllController;
	/** The controller for deleting iterations */
	private DeleteModelController deleteController;
	/** The controler for retrieving a specific controller */
	private RetrieveModelController retrieveController;

	/** The model containing the data to be displayed in the results table */
	protected ResultsTableModel resultsTableModel;

	/** The parent that this panel lives in */
	private final ListTab parent;
	
	/** Whether or not this has recieved data */
	private boolean recievedData = false;
	
	/** Construct the panel 
	 * @param view the parent of this panel
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
		resultsTable.addMouseListener(new ActivateDeleteButton(this)); // Watches for highlighting
		resultsTable.setDefaultRenderer(String.class, new ActiveIterationTableCellRenderer());
		resultsTable.getTableHeader().setReorderingAllowed(false);

		// Put the table in a scroll pane
		JScrollPane resultsScrollPane = new JScrollPane(resultsTable);
		resultsScrollPane.setPreferredSize(new Dimension(175,250));
		this.add(resultsScrollPane);
		resultsScrollPane.setAlignmentX(CENTER_ALIGNMENT);

		this.add(Box.createRigidArea(new Dimension(0,6)));

		btnCreate = new JButton ("New Iteration");
		btnDelete = new JButton ("Delete");
		setDeleteEnabled(false); // Initialize

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

	/** This method returns an ArrayList of active Iterations	
	 * @return activeIterations An ArrayList of the active Iterations
	 */
	public ArrayList<Iteration> getIterations() {
		ArrayList<Iteration> allIterations = new ArrayList<Iteration>();

		for(int i = 0; i < localIterations.length; i++){
			allIterations.add(localIterations[i]);
		}

		return allIterations;
	}

	/** Get the results table model
	 * @return resultsTableModel The ResultsTableModel data model for the table
	 */
	public ResultsTableModel getModel() {
		return resultsTableModel;
	}

	/** Get the "resultsTable" JTable
	 * @return resultsTable The "resultsTable" JTable
	 */
	public JTable getResultsTable() {
		return resultsTable;
	}

	/** Replace the results table with the given JTable
	 * @param newTable The new "resultsTable" JTable
	 */
	public void setResultsTable(JTable newTable) {
		resultsTable = newTable;
	}

	/** Get the "parent" ListTab
	 * @return parent The ListTab "parent" of this panel
	 */
	public ListTab getParent() {
		return parent;
	}

	/** Get the local copy of the iterations (an array of Iterations)
	 * @return localIterations The "localIterations" array of Iterations
	 */
	public Iteration[] getLocalIterations() {
		return localIterations;
	}

	/** Set the local copy of the iterations ("localIterations", an array of Iterations)
	 * @param localIterations The "localIterations" array of Iterations to set
	 */
	public void setLocalIterations(Iteration[] localIterations) {
		this.localIterations = localIterations;
	}

	/** Get the create button ("btnCreate", a JButton)
	 * @return btnCreate The "btnCreate" JButton
	 */
	public JButton getBtnCreate(){
		return btnCreate;
	}

	/** Get the status of the create button
	 * @return btnCreateIsCancel The "btnCreateIsCancel" boolean representing the status of the create/cancel button ("btnCreate")
	 */
	public boolean isBtnCreateIsCancel() {
		return btnCreateIsCancel;
	}

	/** Set the "btnCreateIsCancel" boolean
	 * @param btnCreateIsCancel The "btnCreateIsCancel" (a boolean representing the status of the create/cancel button "btnCreate") to set
	 */
	public void setBtnCreateIsCancel(boolean btnCreateIsCancel) {
		this.btnCreateIsCancel = btnCreateIsCancel;
	}

	/** Set the cancel button back to New Iteration if it was in cancel mode
	 * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IListPanel#setCancelBtnToNew()
	 */
	public void setCancelBtnToNew() {
		this.getBtnCreate().setText("New Iteration"); 
		this.setBtnCreateIsCancel(false);
	}
	
	/** Set the new iteration button to cancel
	 * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IListPanel#setNewBtnToCancel()
	 */
	public void setNewBtnToCancel(){
		getBtnCreate().setText("Cancel"); 
		setBtnCreateIsCancel(true);
	}

	/** Toggles between "New Model" and "Cancel" mode 
	 * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IListPanel#toggleNewCancelMode()
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
	 * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IListPanel#refreshAll()
	 */
	public boolean refreshAll() {
		recievedData = false;
		retrieveAllController.refreshData();
		int count = 0;
		while(!recievedData && count<500){
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			count++;
		}
		return true;
	}

	/** Gets the unique identifier of the list entry that was double clicked
	 * 
	 * @param me MouseEvent
	 * @return The unique identifier, either name or ID number (in String form)
	 * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IListPanel#getSelectedUniqueIdentifier(MouseEvent)
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
	 * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IListPanel#getSelectedUniqueIdentifiers()
	 */
	public String[] getSelectedUniqueIdentifiers() {
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

	/** Show the models in the list view
	 *  Do nothing in builder
	 * 
	 * @param jsonString An array of models in the form of a JSON string
	 * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IListPanel#showRecievedModels(String)
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
		
		System.arraycopy(iterations, 1, displayedIterations, 0, iterations.length - 1);
		
		if (displayedIterations.length > 0) {
			// set the column names
			String[] columnNames = {"Id", "Name", "StartDate", "EndDate"};

			// put the data in the table
			Object[][] entries = new Object[displayedIterations.length][columnNames.length];
			for (int i = 0; i < displayedIterations.length; i++) {
				entries[i][0] = displayedIterations[i].getID();
				entries[i][1] = displayedIterations[i].getName();
				entries[i][2] = displayedIterations[i].getStartDate().toString();
				entries[i][3] = displayedIterations[i].getEndDate().toString();

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
			
			recievedData = true;
			return; // end now
		}
		recievedData = true;
		setDeleteEnabled(false);
	}

	/** Get the retrieveAllController RetrieveAllModelsController
	 * @return retrieveAllController The "retrieveAllController" RetrieveAllModelsController
	 */
	public RetrieveAllModelsController getRetrieveAllController() {
		return retrieveAllController;
	}

	/** Set the retrieveAllController RetrieveAllModelsController
	 * @param retrieveAllController The "retrieveAllController" (an instance of RetrieveAllModelsController) to set
	 */
	public void setRetrieveAllController(RetrieveAllModelsController retrieveAllController) {
		this.retrieveAllController = retrieveAllController;
	}
	
	/** Refreshes the requirements in the main list view
	 * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IListPanel#refreshRequirements()
	 */
	public void refreshRequirements() {
		parent.getParent().getController().refreshData();
	}

	/** Sets the delete button to either activated or deactivated 
	 * 
	 * @param setActive True to activate and false to deactivate
	 * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IListPanel#setDeleteEnabled(boolean)
	 */
	public void setDeleteEnabled(boolean setActive) {
		btnDelete.setEnabled(setActive);		
	}
	
	/** Checks if the selected items can ALL be deleted or not. An iteration may 
	 *  not be deleted when it has sub-requirements, so that is the condition 
	 *  checked for here
	 *  
	 * @return false if any item selected cannot be deleted.
	 * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IListPanel#areSelectedItemsDeletable()
	 */
	public boolean areSelectedItemsDeletable(){
		// Gets the ID's of the selected Iterations
		String[] iterationIDs = getSelectedUniqueIdentifiers();
		
		// Go through all of the ID's
		for (String id: iterationIDs){
			// For the current ID, find the iteration whose ID is the current one
			for (Iteration iter : parent.getParent().getAllIterations()) {				
				// Check to see if the filter references a currently valid Iteration
				if (id.equals(iter.getID() + "") ){
					// If the selected Iteration has requirements assigned to it, it can't be deleted
					if (iter.getRequirementsContained().size() > 0){
						// This means the delete button will be deactivated
						return false; 
					}
				}
			}		
		}	
		// All selected iterations passed so the Delete button is turned on
		return true;
	}
}


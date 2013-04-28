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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views;

import java.awt.BorderLayout;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.IEditableListPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.ListSaveModelController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.ResultsTableModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.*;

/** Panel to hold the results of a list of requirements
 */
@SuppressWarnings({"serial","unchecked","rawtypes"})
public class RequirementListPanel extends JPanel implements IEditableListPanel {

	/** The table of results */
	protected JTable resultsTable;

	/** The model containing the data to be displayed in the results table */
	protected ResultsTableModel resultsTableModel;

	/** The main tab controller */
	protected final MainTabController tabController;

	/** Array of Boolean flags for whether or not Requirements need saving */
	private Boolean[][] needsSaving;

	/** Array of Boolean flags for whether or not cells are valid */
	private Boolean[][] isValid;

	/** Array of Boolean flags for whether or not the cells are editable */
	private Boolean[][] isEditable;
	
	/** Boolean for whether or not the table is in edit mode */
	private boolean inEditMode;

	/** ArrayList of listeners on resultsTable column heads */
	private ArrayList<MouseListener> columnHeadListeners;

	/** The SaveListModelController that does the saving */
	private ListSaveModelController listSaveModelController;

	/** the parent that holds this panel */
	ListTab parent;

	/**Construct the panel
	 * @param tabController The main tab controller
	 */
	public RequirementListPanel(MainTabController tabController, final ListTab parent) {
		this.tabController = tabController;
		columnHeadListeners = new ArrayList<MouseListener>();

		this.parent = parent;
		// Set the layout
		this.setLayout(new BorderLayout());

		// Construct the table model
		resultsTableModel = new ResultsTableModel();

		// Construct the table and configure it
		resultsTable = new JTable(resultsTableModel);
		resultsTable.setAutoCreateRowSorter(true);
		resultsTable.setFillsViewportHeight(true);

		resultsTable.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if (resultsTableModel.isEditable()) {

					Requirement[] reqs = parent.getParent().getDisplayedRequirements();
					int row = e.getFirstRow();

					Requirement requirement = null;
					for (Requirement r : reqs) {
						if ((r.getId() + "").equals(resultsTableModel.getValueAt(row, getColumnIndex("ID", getTableName()))))
							requirement = r;	
					}

					int column = e.getColumn();
					String columnName = resultsTableModel.getColumnName(column);
					Object data = resultsTableModel.getValueAt(row, column);
					boolean isChanged = false;
					boolean isInvalid = false;

					Requirement currentRequirement = getCurrentRequirement(row);
					RequirementStatus currentStatus = currentRequirement.getStatus();

					if (columnName.equals("Name")) {
						if (!requirement.getName().equals((String)data)) {
							isChanged = true;

							if (currentStatus == RequirementStatus.Complete || currentStatus == RequirementStatus.Deleted)
								isInvalid = true;
						}
						if (((String)data).equals(""))
							isInvalid = true;
					}

					else if (columnName.equals("Iteration")) {
						String iteration = (String) data;
						if (!getIterationName(requirement.getIteration()).equals(iteration)) {
							isChanged = true;

							if (currentStatus == RequirementStatus.Complete || currentStatus == RequirementStatus.Deleted)
								isInvalid = true;

							int statusColumn = getColumnIndex("Status");
							if (iteration.equals("")) {
								if (requirement.getStatus() == RequirementStatus.New)
									resultsTable.setValueAt("New", row, statusColumn);
								else
									resultsTable.setValueAt("Open", row, statusColumn);
							}
							else
								resultsTable.setValueAt("InProgress", row, statusColumn);

							if (currentRequirement.getEstimate() == 0) {
								int estimateColumn = getColumnIndex("Estimate");
								isValid[row][estimateColumn] = false;
							}
						}
					}

					else if (columnName.equals("Type")) {
						if (requirement.getType() != RequirementType.toType((String)data)) {
							isChanged = true;

							if (currentStatus == RequirementStatus.Complete || currentStatus == RequirementStatus.Deleted)
								isInvalid = true;
						}
					}

					else if (columnName.equals("Status")) {
						if (requirement.getStatus() != RequirementStatus.valueOf((String)data))
							isChanged = true;
					}

					else if (columnName.equals("Priority")) {
						if (requirement.getPriority() != RequirementPriority.toPriority((String)data)) {
							isChanged = true;

							if (currentStatus == RequirementStatus.Complete || currentStatus == RequirementStatus.Deleted)
								isInvalid = true;
						}
					}

					else if (columnName.equals("ReleaseNumber")) {
						if (!requirement.getReleaseNumber().equals((String)data)) {
							isChanged = true;

							if (currentStatus == RequirementStatus.Complete || currentStatus == RequirementStatus.Deleted)
								isInvalid = true;
						}
					}

					else if (columnName.equals("Estimate")) {
						String estimate = (String) data;
						if (estimate.equals("") || estimate.contains("-") || estimate.contains("."))
							isInvalid = true;
						else {
							try {
								if (requirement.getEstimate() != Integer.parseInt((String)data)) {
									isChanged = true;

									if (currentStatus == RequirementStatus.Deleted)
										isInvalid = true;

									int iterationId = currentRequirement.getIteration();
									if (iterationId != 0)
										isInvalid = true;
								}
							} catch (Exception ex) {
								isInvalid = true;
							}
						}
					}

					else if (columnName.equals("ActualEffort")) {
						String actualEffort = (String) data;
						if (actualEffort.equals("") || actualEffort.contains("-") || actualEffort.contains("."))
							isInvalid = true;
						else {
							try {
								if (requirement.getActualEffort() != Integer.parseInt((String)data)) {
									isChanged = true;

									if (currentStatus == RequirementStatus.Complete || currentStatus == RequirementStatus.Deleted)
										isInvalid = true;
								}
							} catch (Exception ex) {
								isInvalid = true;
							}
						}
					}

					else {
						System.err.println("Table change listener failed fatally");
						return;
					}

					needsSaving[row][column] = isChanged;
					isValid[row][column] = !isInvalid;

					resultsTable.setDefaultRenderer(String.class, new ResultsTableCellRenderer(needsSaving, isValid, isEditable));			
				}
				updateSaveButton();
			}

		});

		// Put the table in a scroll pane
		JScrollPane resultsScrollPane = new JScrollPane(resultsTable);

		this.add(resultsScrollPane, BorderLayout.CENTER);

		// Setup controller
		listSaveModelController = new ListSaveModelController(this, "requirement");
	}

	/**
	 * @return the main tab controller
	 */
	public MainTabController getTabController() {
		return tabController;
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

	/** Replace the results table with the given table
	 * @param newTable the new results table
	 */
	public void setResultsTable(JTable newTable) {
		resultsTable = newTable;
	}


	/** 
	 * place combox for type
	 */
	public void setComboxforType()
	{
		int typeColumnNum = this.getColumnIndex("Type", getTableName());

		TableColumn typeColumn = resultsTable.getColumnModel().getColumn(typeColumnNum);

		JComboBox typebox = new JComboBox();
		typebox.addItem("");
		typebox.addItem("Epic");
		typebox.addItem("Theme");
		typebox.addItem("UserStory");
		typebox.addItem("NonFunctional");
		typebox.addItem("Scenario");
		typeColumn.setCellEditor(new DefaultCellEditor(typebox));
	}

	/** 
	 * place combox for iteration
	 */
	public void setComboxforIteration()
	{
		int typeColumnNum = this.getColumnIndex("Iteration", getTableName());

		TableColumn typeColumn = resultsTable.getColumnModel().getColumn(typeColumnNum);

		JComboBox typebox = new JComboBox();
		Iteration[] allIterations = parent.getParent().getAllIterations();
		for (Iteration anIter: allIterations){
			typebox.addItem(anIter.getName());
		}

		typeColumn.setCellEditor(new DefaultCellEditor(typebox));
	}


	/** 
	 * place combox for type
	 */
	public void setComboxforStatus()
	{
		int statusColumn = this.getColumnIndex("Status", getTableName());

		TableColumn typeColumn = resultsTable.getColumnModel().getColumn(statusColumn);

		JComboBox typebox = new JComboBox();
		typebox.addItem("New");
		typebox.addItem("InProgress");
		typebox.addItem("Open");
		typebox.addItem("Complete");
		typebox.addItem("Deleted");
		typeColumn.setCellEditor(new DefaultCellEditor(typebox));


	}

	/** 
	 * place combox for type
	 */
	public void setComboxforPriority()
	{
		int priorityColumn = this.getColumnIndex("Priority", getTableName());

		TableColumn typeColumn = resultsTable.getColumnModel().getColumn(priorityColumn);

		JComboBox typebox = new JComboBox();
		typebox.addItem("");
		typebox.addItem("Low");
		typebox.addItem("Medium");
		typebox.addItem("High");

		typeColumn.setCellEditor(new DefaultCellEditor(typebox));

	}

	/** Disables the sorting of the JTable */
	public void disableSorting(){
		columnHeadListeners.clear();
		// Removes mouse listeners from table header
		for(MouseListener listener : resultsTable.getTableHeader().getMouseListeners()) {
			columnHeadListeners.add(listener);
			resultsTable.getTableHeader().removeMouseListener(listener);
		}
		resultsTable.getTableHeader().setResizingAllowed(false); // Disables column resizing
		resultsTable.getTableHeader().setReorderingAllowed(false); // Disables column ordering
	}

	/** Enables the sorting of the JTable */
	public void enableSorting() {
		// Adds mouse listeners back to table header
		for (MouseListener listener : columnHeadListeners) {
			resultsTable.getTableHeader().addMouseListener(listener);
		}
		resultsTable.getTableHeader().setResizingAllowed(true); // Allows columns to be resized
		resultsTable.getTableHeader().setReorderingAllowed(true); // Allows columns to be reordered
	}

	/** Gets the array of boolean flags of what models
	 *  need saving
	 * 
	 * @return a Boolean array of what models need saving
	 */
	public Boolean[][] getNeedsSaveFlags() {
		return needsSaving;
	}

	/** Gets the JSOn version of the model at 
	 *  the given index
	 *  
	 * @param rowNumber The index of the model
	 * @return  The JSON version of the model
	 */
	public String getModelAsJson(int rowNumber) {
		// Get the names of the columns
		ArrayList<String> columnNames = this.getTableName();

		// Get the ID of the requirement being edited
		String id = (String) resultsTable.getValueAt(rowNumber, this.getColumnIndex("ID", columnNames));

		// Get the original version of the requirement
		Requirement[] reqs = parent.getParent().getDisplayedRequirements();
		Requirement toUpdate = null;
		for (Requirement aReq: reqs){
			if (Integer.toString(aReq.getId()).equals(id) ){
				toUpdate = aReq;
			}
		}

		// Check to see if the retrieve worked
		if (toUpdate == null){
			System.err.print("Fatal Save Problem: Unable to get the model.");
			return "";
		}

		// Start saving the rest of the fields
		toUpdate.setName((String) resultsTable.getValueAt(rowNumber, this.getColumnIndex("Name", columnNames)));
		toUpdate.setType( RequirementType.toType((String) resultsTable.getValueAt(rowNumber, this.getColumnIndex("Type", columnNames))));
		toUpdate.setStatus( RequirementStatus.toStatus((String) resultsTable.getValueAt(rowNumber, this.getColumnIndex("Status", columnNames))));
		toUpdate.setPriority(RequirementPriority.toPriority((String) resultsTable.getValueAt(rowNumber, this.getColumnIndex("Priority", columnNames))));
		toUpdate.setReleaseNumber((String)resultsTable.getValueAt(rowNumber, this.getColumnIndex("ReleaseNumber", columnNames)));
		toUpdate.setEstimate( Integer.parseInt( (String) resultsTable.getValueAt(rowNumber, this.getColumnIndex("Estimate", columnNames))));
		toUpdate.setActualEffort(Integer.parseInt((String) resultsTable.getValueAt(rowNumber, this.getColumnIndex("ActualEffort", columnNames))));

		int iterationID = this.getIterationID((String) resultsTable.getValueAt(rowNumber, this.getColumnIndex("Iteration", columnNames)));
		toUpdate.setIteration(iterationID);

		return toUpdate.toJSON();

	}

	protected Requirement getCurrentRequirement(int row) {

		Requirement currentRequirement = new Requirement();


		// Start saving the rest of the fields
		currentRequirement.setName((String) resultsTable.getValueAt(row, this.getColumnIndex("Name")));
		currentRequirement.setType( RequirementType.toType((String) resultsTable.getValueAt(row, this.getColumnIndex("Type"))));
		currentRequirement.setStatus( RequirementStatus.toStatus((String) resultsTable.getValueAt(row, this.getColumnIndex("Status"))));
		currentRequirement.setPriority(RequirementPriority.toPriority((String) resultsTable.getValueAt(row, this.getColumnIndex("Priority"))));
		currentRequirement.setReleaseNumber((String)resultsTable.getValueAt(row, this.getColumnIndex("ReleaseNumber")));
		try {
			currentRequirement.setEstimate( Integer.parseInt( (String) resultsTable.getValueAt(row, this.getColumnIndex("Estimate"))));
		} catch (Exception e) {
			currentRequirement.setEstimate(-1);
		}
		try {
			currentRequirement.setActualEffort(Integer.parseInt((String) resultsTable.getValueAt(row, this.getColumnIndex("ActualEffort"))));
		} catch (Exception e) {
			currentRequirement.setActualEffort(-1);
		}

		int iterationID = this.getIterationID((String) resultsTable.getValueAt(row, this.getColumnIndex("Iteration")));
		currentRequirement.setIteration(iterationID);

		return currentRequirement;
	}

	/** Gets the id of the iteration with the given name
	 * 
	 * @param iterName the name of the iteration
	 * @return the id of the iteration
	 */
	private int getIterationID(String iterName){
		Iteration[] allIterations = parent.getParent().getAllIterations();
		for (Iteration anIter: allIterations){
			if (anIter.getName().equals(iterName)){
				return anIter.getID();
			}
		}
		System.err.print("Failed to find the iteration");
		return -1; // failure
	}


	/** Gets the name of the iteration with the given id
	 * 
	 * @param iterId the id of the iteration
	 * @return the name of the iteration
	 */
	private String getIterationName(int iterId){
		Iteration[] allIterations = parent.getParent().getAllIterations();
		for (Iteration anIter: allIterations){
			if (anIter.getID() == iterId){
				return anIter.getName();
			}
		}
		System.err.print("Failed to find the iteration");
		return ""; // failure
	}



	/** Gets the column index of the column with the given name
	 * 
	 * @param name The name to check for
	 * @return the column index, returns -1 upon failure
	 */
	private int getColumnIndex(String name, ArrayList<String> columnNames){
		for (int Column = 0; Column < columnNames.size(); Column++ ){
			if (columnNames.get(Column).equals(name)){
				return Column;
			}
		}
		System.err.println("That column doesn't exist!");
		return -1;
	}


	/** Gets the column index of the column with the given name
	 * 
	 * @param name The name to check for
	 * @return the column index, returns -1 upon failure
	 */
	private int getColumnIndex(String name){
		return getColumnIndex(name, getTableName());
	}


	/** Sets up any arrays of flags or other settings needed
	 *  before editing can start 
	 */
	public void setUpForEditing(){
		// Clear the boolean arrays
		needsSaving = new Boolean[resultsTable.getRowCount()][resultsTable.getColumnCount()];
		for (int i = 0; i < resultsTable.getRowCount(); i++){
			for (int j = 0; j < resultsTable.getColumnCount(); j++){
				needsSaving[i][j] = Boolean.valueOf(false); // Set entries to false
			}		
		}
		isValid = new Boolean[resultsTable.getRowCount()][resultsTable.getColumnCount()];
		for (int i = 0; i < resultsTable.getRowCount(); i++){
			for (int j = 0; j < resultsTable.getColumnCount(); j++){
				isValid[i][j] = Boolean.valueOf(true); // Set entries to true
			}		
		}
		isEditable = new Boolean[resultsTable.getRowCount()][resultsTable.getColumnCount()];
		for (int i = 0; i < resultsTable.getRowCount(); i++){
			for (int j = 0; j < resultsTable.getColumnCount(); j++){
				isEditable[i][j] = Boolean.valueOf(true); // Set entries to true
			}		
		}
		for (int i = 0; i < resultsTable.getRowCount(); i++) {
			isEditable[i][getColumnIndex("ID")] = Boolean.valueOf(false);

			RequirementStatus status = RequirementStatus.toStatus((String)resultsTable.getValueAt(i, getColumnIndex("Status")));
			if (status == RequirementStatus.Complete || status == RequirementStatus.Deleted) {
				for (int j = 0; j < resultsTable.getColumnCount(); j++) {
					if (j != getColumnIndex("Status"))
						isEditable[i][j] = Boolean.valueOf(false);
				}
			}
			else if (status == RequirementStatus.InProgress) {
				isEditable[i][getColumnIndex("Estimate")] = Boolean.valueOf(false);
			}
			else if (resultsTable.getValueAt(i, getColumnIndex("Estimate")).equals("0")) {
				isEditable[i][getColumnIndex("Iteration")] = Boolean.valueOf(false);
			}

		}
		getModel().setEditable(true);
		getModel().setIsEditable(isEditable);
		resultsTable.setDefaultRenderer(String.class, new ResultsTableCellRenderer(needsSaving, isValid, isEditable));
		setComboxforType();
		setComboxforStatus();
		setComboxforPriority();
		setComboxforIteration();
		disableSorting();
		parent.getParent().getBtnSave().setEnabled(false);
	}



	/** Gets the unique identifier of the model at 
	 *  the given index
	 * 
	 * @param i The index of the model
	 * @return  The unique identifier of the model
	 */
	public String getUniqueIdAtIndex(int i) {
		return (String) resultsTable.getValueAt(i, this.getColumnIndex("ID"));
	}

	/** Change settings of table to indicate that the 
	 *  save was completed and normal operations 
	 *  should resume.
	 */
	public void savesComplete() {
		System.out.println("Save complete");
	}



	/**
	 * @return the listSaveModelController
	 */
	public ListSaveModelController getListSaveModelController() {
		return listSaveModelController;
	}

	/** A getter to get the current column headers of the table 
	 * @return columnHeader the ArrayList of headers for the table
	 */
	public ArrayList<String> getTableName(){
		ArrayList<String> columnHeader  = new ArrayList<String>();

		// Set each value of the column header
		for (int i = 0; i < 9; i++){
			columnHeader.add( resultsTable.getColumnModel().getColumn(i).getHeaderValue().toString());
		}

		return columnHeader;
	}


	/** Trigger a reset of all lists	 */
	public void refreshAll() {
		parent.getParent().refreshData();
	}

	/** Way to trigger a pop-up or enable/disable certain 
	 *  buttons when a  save is not successful.
	 */
	public void failedToSave() {
		System.out.println("sadface ): ");
	}

	/**
	 * @return the isValid
	 */
	public Boolean[][] getIsValid() {
		return isValid;
	}

	/**
	 * @param isValid the isValid to set
	 */
	public void setIsValid(Boolean[][] isValid) {
		this.isValid = isValid;
	}

	/**
	 * @return the isEditable
	 */
	public Boolean[][] getIsEditable() {
		return isEditable;
	}

	/**
	 * @param isEditable the isEditable to set
	 */
	public void setIsEditable(Boolean[][] isEditable) {
		this.isEditable = isEditable;
	}
	
	/** Turns on the save button when all cells are
	 *  valid and at least one has been changed. 
	 */
	public void updateSaveButton() {
		// Check for validity and disables the save button if there is invalidity
		for (Boolean[] Vrows : isValid)
			for (Boolean Vcells: Vrows)
				if (!Vcells.booleanValue()){
					parent.getParent().getBtnSave().setEnabled(false);
					// The save button has been disabled because there is invalidity in the table
					return;
				}
		// Check for Changes, and turn the save button in if changes were made
		for (Boolean[] Nrows : needsSaving)
			for (Boolean Ncells: Nrows)
				if (!Ncells.booleanValue()){
					parent.getParent().getBtnSave().setEnabled(true);
					// The save button has been disabled because there is invalidity in the table
					return;			
				}
		// Double check that un-editable boxes are not changed
	}

	/**
	 * @return the inEditMode
	 */
	public boolean isInEditMode() {
		return inEditMode;
	}

	/**
	 * @param inEditMode the inEditMode to set
	 */
	public void setInEditMode(boolean inEditMode) {
		this.inEditMode = inEditMode;
	}
}

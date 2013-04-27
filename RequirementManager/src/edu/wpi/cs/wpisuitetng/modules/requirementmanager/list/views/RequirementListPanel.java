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
import java.util.Date;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.IEditableListPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.ListSaveModelController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.ResultsTableModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.*;
/** Panel to hold the results of a list of requirements
 */
@SuppressWarnings({"serial"})
public class RequirementListPanel extends JPanel implements IEditableListPanel {
	
	/** The table of results */
	protected JTable resultsTable;
	
	/** The model containing the data to be displayed in the results table */
	protected ResultsTableModel resultsTableModel;
	
	/** The main tab controller */
	protected final MainTabController tabController;

	/** Array of Boolean flags for whether or not Requirements need saving */
	private Boolean[] needsSaving;
	
	/** ArrayList of listeners on resultsTable column heads */
	private ArrayList<MouseListener> columnHeadListeners;
	
	/** The SaveListModelController that does the saving */
	private ListSaveModelController listSaveModelController;
	
	/** the parent that holds this panel */
	ListTab parent;
	
	/**Construct the panel
	 * @param tabController The main tab controller
	 */
	public RequirementListPanel(MainTabController tabController, ListTab parent) {
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
		resultsTable.setDefaultRenderer(Date.class, new DateTableCellRenderer());

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
	@SuppressWarnings("unchecked")
	public void setComboxforType()
	{
	TableColumn typeColumn = resultsTable.getColumnModel().getColumn(3);
	@SuppressWarnings("rawtypes")
	JComboBox typebox = new JComboBox();
	typebox.addItem("Epic");
	typebox.addItem("Theme");
	typebox.addItem("UserStory");
	typebox.addItem("NonFunctional");
	typebox.addItem("Scenario");
	typeColumn.setCellEditor(new DefaultCellEditor(typebox));
	
		
	}
	
	/** 
	 * place combox for type
	 */
	@SuppressWarnings("unchecked")
	public void setComboxforStatus()
	{
	TableColumn typeColumn = resultsTable.getColumnModel().getColumn(4);
	@SuppressWarnings("rawtypes")
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
	@SuppressWarnings("unchecked")
	public void setComboxforPriority()
	{
	TableColumn typeColumn = resultsTable.getColumnModel().getColumn(5);
	@SuppressWarnings("rawtypes")
	JComboBox typebox = new JComboBox();
	typebox.addItem("High");
	typebox.addItem("Medium");
	typebox.addItem("Low");
	typeColumn.setCellEditor(new DefaultCellEditor(typebox));
	
		
	}
	
	/**numeric validation
	 * 
	 */
	public void NumValidationforEffort()
	{
	 TableColumn estimateColum = resultsTable.getColumnModel().getColumn(8);
	 estimateColum.setCellEditor(new NumValidation());
	}
	
	/**numeric validation
	 * 
	 */
	public void NumValidationforEstimate()
	{
	 TableColumn estimateColum = resultsTable.getColumnModel().getColumn(7);
	 estimateColum.setCellEditor(new NumValidation());
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
	public Boolean[] getNeedsSaveFlags() {
		return needsSaving;
	}

	/** Gets the JSOn version of the model at 
	 *  the given index
	 *  
	 * @param i The index of the model
	 * @return  The JSON version of the model
	 */
	public String getModelAsJson(int i) {
		// Get the names of the columns
		ArrayList<String> columnNames = this.getTableName();
		
		// Get the ID of the requirement being edited
		String id = (String) resultsTable.getValueAt(i, this.getColumnIndex("ID", columnNames));
		
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
		toUpdate.setName((String) resultsTable.getValueAt(i, this.getColumnIndex("Name", columnNames)));
		toUpdate.setType( RequirementType.toType((String) resultsTable.getValueAt(i, this.getColumnIndex("Type", columnNames))));
		toUpdate.setStatus( RequirementStatus.toStatus((String) resultsTable.getValueAt(i, this.getColumnIndex("Status", columnNames))));
		toUpdate.setPriority(RequirementPriority.toPriority((String) resultsTable.getValueAt(i, this.getColumnIndex("Priority", columnNames))));
		toUpdate.setReleaseNumber((String)resultsTable.getValueAt(i, this.getColumnIndex("ReleaseNumber", columnNames)));
		toUpdate.setEstimate( Integer.parseInt( (String) resultsTable.getValueAt(i, this.getColumnIndex("Estimate", columnNames))));
		toUpdate.setActualEffort(Integer.parseInt((String) resultsTable.getValueAt(i, this.getColumnIndex("ActualEffort", columnNames))));
		
		int iterationID = this.getIterationID((String) resultsTable.getValueAt(i, this.getColumnIndex("Iteration", columnNames)));
		toUpdate.setId(iterationID);
		
		return toUpdate.toJSON();

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
	
	
	
	
	/** Sets up any arrays of flags or other settings needed
	 *  before editing can start 
	 */
	public void setUpForEditing(){
		needsSaving = new Boolean[resultsTable.getRowCount()];
		for (int i = 0; i < resultsTable.getRowCount(); i++){
			needsSaving[i] = Boolean.valueOf(false); // Set entries false
		}		
	}
	
	
	
	/** Gets the unique identifier of the model at 
	 *  the given index
	 * 
	 * @param i The index of the model
	 * @return  The unique identifier of the model
	 */
	public String getUniqueIdAtIndex(int i) {
		// TODO Auto-generated method stub
		return null;
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
}

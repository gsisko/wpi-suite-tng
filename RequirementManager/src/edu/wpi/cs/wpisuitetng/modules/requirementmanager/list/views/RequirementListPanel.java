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
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.IEditableListPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.ResultsTableModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabController;

/** Panel to hold the results of a list of requirements
 */
@SuppressWarnings({"serial","rawtypes","unchecked"})
public class RequirementListPanel extends JPanel implements IEditableListPanel {
	
	/** The table of results */
	protected JTable resultsTable;

	/**  a saved version of the row sorter on the Jtable */
	private RowSorter theSorter;
	
	/** The model containing the data to be displayed in the results table */
	protected ResultsTableModel resultsTableModel;
	
	/** The main tab controller */
	protected final MainTabController tabController;

	/** Array of Boolean flags for whether or not Requirements need saving */
	private Boolean[] needsSaving;
	
	
	/**Construct the panel
	 * @param tabController The main tab controller
	 */
	public RequirementListPanel(MainTabController tabController) {
		this.tabController = tabController;
		
		// Set the layout
		this.setLayout(new BorderLayout());
		
		// Construct the table model
		resultsTableModel = new ResultsTableModel();
		
		// Construct the table and configure it
		resultsTable = new JTable(resultsTableModel);
		resultsTable.setAutoCreateRowSorter(true);
		resultsTable.setFillsViewportHeight(true);
		resultsTable.setDefaultRenderer(Date.class, new DateTableCellRenderer());
		resultsTable.setRowSorter(null);
		resultsTable.setAutoCreateRowSorter(true);

		// Put the table in a scroll pane
		JScrollPane resultsScrollPane = new JScrollPane(resultsTable);
		
		this.add(resultsScrollPane, BorderLayout.CENTER);
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
	
	/** Disables the sorting of the JTable */
	public void disableSorting(){
		theSorter = resultsTable.getRowSorter(); // save the row sorter
	//	resultsTable.setRowSorter(null); // disable the row sorting AND resets the sort settings	
	//	resultsTable.setAutoCreateRowSorter(false); // not sure if it works
		resultsTable.getTableHeader().setReorderingAllowed(false); // Disables row ordering
	}

	/** Enables the sorting of the JTable */
	public void enableSorting() {
		resultsTable.setRowSorter(theSorter); 
	//	resultsTable.setAutoCreateRowSorter(true); // not sure if it works
		resultsTable.getTableHeader().setReorderingAllowed(true); // Alows columns to be reordered
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
		// TODO Auto-generated method stub
		return null;
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

	/** Way to trigger a pop-up or enable/disable certain 
	 *  buttons when a  save is not successful.
	 */
	public void failedToSave() {
		// TODO Auto-generated method stub
		
	}

	/** Change settings of table to indicate that the 
	 *  save was completed and normal operations 
	 *  should resume.
	 */
	public void savesComplete() {
		// TODO Auto-generated method stub
		
	}

	/** Trigger a reset of all lists	 */
	public void refreshAll() {
		// TODO Auto-generated method stub	
	}
}

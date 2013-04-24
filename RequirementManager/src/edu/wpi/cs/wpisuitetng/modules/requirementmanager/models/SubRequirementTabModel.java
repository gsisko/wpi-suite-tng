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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models;

import javax.swing.table.AbstractTableModel;

/**
 * A model to manage the data displayed in the {@link RequirementListPanel}
 */
@SuppressWarnings("serial")
public class SubRequirementTabModel extends AbstractTableModel {

	// The names to be displayed in the column headers
	private String[] columnNames = {};
	
	// The data to be displayed in the table
	private Object[][] data = {};
	
	/**
	 * Basic constructor.
	 */
	public SubRequirementTabModel() {
		
	}
	
	/**
	 * Set the data to be displayed in the table
	 * 
	 * @param data A two-dimensional array of objects containing the data
	 */
	public void setData(Object[][] data) {
		this.data = data;
	}
	
	/**
	 * Set the column names to be displayed in the table
	 * 
	 * @param columnNames An array of strings containing the column names
	 */
	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}

	/**
	 * @return the number of Columns in the table
	 */
	@Override
	public int getColumnCount() {
		return getColumnNames().length;
	}

	/**
	 * @return the number of rows in the table
	 */
	@Override
	public int getRowCount() {
		return getData().length;
	}

	
	/**
	 * @param row The row number of the value you wish to get
	 * @param col The column number of the value you wish to get
	 * 
	 * @return the value at the requested row and column
	 */
	@Override
	public Object getValueAt(int row, int col) {
		return getData()[row][col];
	}
	
	/**
	 * @param col The column number that you wish to get the name of
	 * 
	 * @return The name of the column you requested
	 */
	@Override
	public String getColumnName(int col) {
		return getColumnNames()[col];
	}

	/**
	 * @param col The column number that you wish to get the class of
	 * 
	 * @return the class of the column number you requested
	 */
	@Override
	public Class<?> getColumnClass(int col) {
		return getValueAt(0, col).getClass();
	}
	
	/**
	 * @param row The row number of the value you wish to know if it's editable
	 * @param col The column number of the value you wish to know if it's editable
	 * 
	 * @return false, none of the Results Table Model is editable
	 */
	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}

	
	/**
	 * @return an array of column names;
	 */
	public String[] getColumnNames() {
		return columnNames;
	}

	/**
	 * 
	 * @return The data
	 */
	public Object[][] getData() {
		return data;
	}
}

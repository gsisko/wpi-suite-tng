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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models;

import javax.swing.table.AbstractTableModel;

/**
 * A model to manage the data displayed in the {@link RequirementListPanel}
 */
@SuppressWarnings("serial")
public class ResultsTableModel extends AbstractTableModel {

	// The names to be displayed in the column headers
	private String[] columnNames = {};
	
	// The data to be displayed in the table
	private Object[][] data = {};
	
	/**
	 * Basic constructor.
	 */
	public ResultsTableModel() {
		
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
	 * @param the row number of the value you wish to get
	 * @param The column number of the value you wish to get
	 * 
	 * @return the value at the requested row and column
	 */
	@Override
	public Object getValueAt(int row, int col) {
		return getData()[row][col];
	}
	
	/**
	 * @param the column number that you wish to get the name of
	 * 
	 * @return the name of the column you requested
	 */
	@Override
	public String getColumnName(int col) {
		return getColumnNames()[col];
	}

	/**
	 * @param the column number that you wish to get the class of
	 * 
	 * @return the class of the column number you requested
	 */
	@Override
	public Class<?> getColumnClass(int col) {
		return getValueAt(0, col).getClass();
	}
	
	/**
	 * @param the row number of the value you wish to know if it's editable
	 * @param The column number of the value you wish to know if it's editable
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
	 * @return
	 */
	public Object[][] getData() {
		return data;
	}
}

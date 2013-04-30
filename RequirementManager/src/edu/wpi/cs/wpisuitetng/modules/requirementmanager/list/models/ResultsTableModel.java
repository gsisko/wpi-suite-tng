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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models;

import javax.swing.table.AbstractTableModel;

/** A model to manage the data displayed in the {@link RequirementListPanel}
 */
@SuppressWarnings("serial")
public class ResultsTableModel extends AbstractTableModel {

	/** The names to be displayed in the column headers */
	private String[] columnNames = {};

	/** The data to be displayed in the table */
	private Object[][] data = {};

	/** Boolean for whether or not the table is editable */
	private boolean editable = false;

	/** Array of Boolean flags for whether or not the cells are editable */
	private Boolean[][] isEditable = {};

	/**Set the data to be displayed in the table 
	 * @param data A two-dimensional array of objects containing the data
	 */
	public void setData(Object[][] data) {
		this.data = data;
	}

	/** Set the column names to be displayed in the table
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
	 * @return the value at the requested row and column
	 */
	@Override
	public Object getValueAt(int row, int col) {
		return getData()[row][col];
	}

	/**
	 * @param value The value to set the cell to
	 * @param row The row number of the cell edited
	 * @param col The column number of the cell edited
	 */
	public void setValueAt(Object value, int row, int col) {
		data[row][col] = value;
		fireTableCellUpdated(row, col);
	}

	/**
	 * @param col The column number that you wish to get the name of
	 * @return The name of the column you requested
	 */
	@Override
	public String getColumnName(int col) {
		return getColumnNames()[col];
	}

	/**
	 * @param col The column number that you wish to get the class of
	 * @return the class of the column number you requested
	 */
	@Override
	public Class<?> getColumnClass(int col) {
		return getValueAt(0, col).getClass();
	}

	/**
	 * @param row The row number of the value you wish to know if it's editable
	 * @param col The column number of the value you wish to know if it's editable
	 * @return false, none of the Results Table Model is editable
	 */
	@Override
	public boolean isCellEditable(int row, int col) {
		if (editable)
			return isEditable[row][col];
		else
			return false;
	}

	/**
	 * @return an array of column names;
	 */
	public String[] getColumnNames() {
		return columnNames;
	}

	/**
	 * @return The data in the table
	 */
	public Object[][] getData() {
		return data;
	}

	/**
	 * @return the editable
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * @param editable the editable to set
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
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

}

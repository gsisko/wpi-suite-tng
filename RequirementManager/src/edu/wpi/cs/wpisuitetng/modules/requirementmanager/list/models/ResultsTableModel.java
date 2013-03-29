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
 * A model to manage the data displayed in the {@link ResultsPanel}
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

	@Override
	public int getColumnCount() {
		return getColumnNames().length;
	}

	@Override
	public int getRowCount() {
		return getData().length;
	}

	@Override
	public Object getValueAt(int row, int col) {
		return getData()[row][col];
	}
	
	@Override
	public String getColumnName(int col) {
		return getColumnNames()[col];
	}

	@Override
	public Class<?> getColumnClass(int col) {
		return getValueAt(0, col).getClass();
	}
	
	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	public Object[][] getData() {
		return data;
	}
}

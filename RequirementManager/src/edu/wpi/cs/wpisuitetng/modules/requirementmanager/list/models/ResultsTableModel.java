package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models;

import javax.swing.table.AbstractTableModel;

/**
 * A model to manage the data displayed in the {@link ResultsPanel}
 */
@SuppressWarnings("serial")
public class ResultsTableModel extends AbstractTableModel {

	/** The names to be displayed in the column headers */
	private String[] columnNames = {};
	
	/** The data to be displayed in the table */
	private Object[][] data = {};
	
	/**
	 * Constructor.
	 */
	public ResultsTableModel() {
		
	}
	
	/**
	 * Set the data to be displayed in the table
	 * @param data a two-dimensional array of objects containing the data
	 */
	public void setData(Object[][] data) {
		this.data = data;
	}
	
	/**
	 * Set the column names to be displayed in the table
	 * @param columnNames an array of strings containing the column names
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

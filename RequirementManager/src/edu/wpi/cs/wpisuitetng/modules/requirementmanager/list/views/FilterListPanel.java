/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Edison Jimenez
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.Filter;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.ResultsTableModel;

/**
 * Panel to contain the list of filters that have been saved by the user
 */
@SuppressWarnings("serial")
public class FilterListPanel extends JPanel {
	
	/** The table of results */
	protected JTable resultsTable;
	
	protected JButton btnCreate;
	protected JButton btnDelete;
	
	/** The model containing the data to be displayed in the results table */
	protected ResultsTableModel resultsTableModel;
	
	private final ListPanel parent;
	/**
	 * Construct the panel
	 */
	public FilterListPanel(ListPanel view) {
		parent = view;
		
		// Set the layout manager and give the panel a border
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder("Filters"));

		// Construct the table model
		resultsTableModel = new ResultsTableModel();

		// Construct the table and configure it
		resultsTable = new JTable(resultsTableModel);
		resultsTable.setAutoCreateRowSorter(true);
		resultsTable.setFillsViewportHeight(true);
		resultsTable.setDefaultRenderer(Date.class, new DateTableCellRenderer());

		// Add a listener for row clicks
		//resultsTable.addMouseListener(new RetrieveFilterController(this));

		// Put the table in a scroll pane
		JScrollPane resultsScrollPane = new JScrollPane(resultsTable);
		resultsScrollPane.setPreferredSize(new Dimension(175,325));
		
		// TODO implement the rest of the controls to display saved filters
		// and store saved filters in the ConfigManager
		
		add(resultsScrollPane, BorderLayout.CENTER);
		
		btnCreate = new JButton ("New Filter");
		btnDelete = new JButton ("Delete");
		
		add(btnCreate, BorderLayout.NORTH);
		add(btnDelete, BorderLayout.SOUTH);
		
		// TODO: add listeners
		
		
	}
	
	public ArrayList<Filter> getActiveFilters() {
		return null;
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
	public ListPanel getParent() {
		return parent;
	}
}


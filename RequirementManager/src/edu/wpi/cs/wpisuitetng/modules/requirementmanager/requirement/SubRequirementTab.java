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
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement;

import java.awt.BorderLayout;
import java.util.Date;

import javax.swing.BorderFactory;

import javax.swing.BoxLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.ResultsTableModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.DateTableCellRenderer;


@SuppressWarnings("serial")
public class SubRequirementTab extends JPanel {

	private RequirementTab parent;
	
	protected ResultsTableModel submodel;
	
	protected JTable subtable;

	private RetrieveSubrequirementsController subController;
	
	public SubRequirementTab(RequirementTab rparent){
		setParent(rparent);
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBorder(BorderFactory.createEmptyBorder(5, 3, 3, 3)); 
		submodel = new ResultsTableModel();
		subtable = new JTable(submodel);
		subtable.setAutoCreateRowSorter(true);
		subtable.setFillsViewportHeight(true);
		subtable.setDefaultRenderer(Date.class, new DateTableCellRenderer());
		
		// Put the table in a scroll pane
		JScrollPane subScrollPane = new JScrollPane(subtable);
		
		this.add(subScrollPane, BorderLayout.CENTER);
		
	}
	
	public void setUp() {
		this.subController = new RetrieveSubrequirementsController(this.getParent().getParent());
		this.subController.refreshData();
	}

	public RequirementTab getParent() {
		return parent;
	}

	public void setParent(RequirementTab parent) {
		this.parent = parent;
	}

	public ResultsTableModel getModel() {
		return submodel;
	}

	public JTable getResultsTable() {
		return subtable;
	}

	/**
	 * @return the subController
	 */
	public RetrieveSubrequirementsController getSubController() {
		return subController;
	}

	/**
	 * @param subController the subController to set
	 */
	public void setSubController(RetrieveSubrequirementsController subController) {
		this.subController = subController;
	}
	
}

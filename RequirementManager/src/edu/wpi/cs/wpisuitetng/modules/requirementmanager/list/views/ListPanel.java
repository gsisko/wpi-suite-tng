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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter.RetrieveAllFiltersController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabController;

/**
 * Panel to hold the three portions of the requirement list interface. The
 * list of saved filters is displayed in {@link FilterListPanel}, the filter
 * builder is displayed in {@link FilterBuilderPanel}, and the results of
 * the list are displayed in {@link ResultsPanel}.
 */
@SuppressWarnings("serial")
public class ListPanel extends JPanel {

	/** Panel containing the filter building interface */
	protected FilterBuilderPanel builderPanel;

	/** Panel containing the list of filters saved by the user */
	protected FilterListPanel filtersPanel;

	/** Panel containing the results of the requirement list */
	protected ResultsPanel resultsPanel;

	/** The layout manager for this panel */
	protected SpringLayout layout;
	
	/** The main tab controller */
	protected MainTabController tabController;
	
	private RetrieveAllFiltersController retriever;
	
	private ListRequirementsView parent; 

	/**
	 * Constructs the list panel and sets up the layout for the sub-panels
	 * @param tabController The main tab controller
	 */
	public ListPanel(MainTabController tabController, ListRequirementsView view) {
		this.tabController = tabController;
		this.parent = view;
		
		// Set the layout manager of this panel
		this.layout = new SpringLayout();
		this.setLayout(layout);

		// Construct the panels that compose the list view
		this.builderPanel = new FilterBuilderPanel(this);
		JScrollPane builderScrollPane = new JScrollPane(builderPanel);
		this.filtersPanel = new FilterListPanel(this);
		JScrollPane filtersScrollPane = new JScrollPane(filtersPanel);
		this.resultsPanel = new ResultsPanel(tabController);

		// Constrain the filtersPanel
		layout.putConstraint(SpringLayout.NORTH, filtersScrollPane, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, filtersScrollPane, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, filtersScrollPane, 0, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.EAST, filtersScrollPane, 200, SpringLayout.WEST, filtersScrollPane);
		
		// Constrain the builderPanel
		layout.putConstraint(SpringLayout.NORTH, builderScrollPane, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, builderScrollPane, 0, SpringLayout.EAST, filtersScrollPane);
		layout.putConstraint(SpringLayout.EAST, builderScrollPane, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, builderScrollPane, 85, SpringLayout.NORTH, builderScrollPane);
		
		// Constrain the resultsPanel
		layout.putConstraint(SpringLayout.NORTH, resultsPanel, 0, SpringLayout.SOUTH, builderScrollPane);
		layout.putConstraint(SpringLayout.WEST, resultsPanel, 0, SpringLayout.EAST, filtersScrollPane);
		layout.putConstraint(SpringLayout.EAST, resultsPanel, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, resultsPanel, 0, SpringLayout.SOUTH, this);

		// Add the panels
		this.add(filtersScrollPane);
		this.add(builderScrollPane);
		this.add(resultsPanel);
		

	}
	
	public ResultsPanel getResultsPanel(){
		return resultsPanel;
	}


	public MainTabController getTabController() {
		return tabController;
	}

	public FilterBuilderPanel getBuilderPanel(){
	    return builderPanel;
	}
	
	public FilterListPanel getFilterPanel(){
	    return filtersPanel;
	}

	/**
	 * @return the retriever
	 */
	public RetrieveAllFiltersController getRetriever() {
		return retriever;
	}

	/**
	 * @param retriever the retriever to set
	 */
	public void setRetriever(RetrieveAllFiltersController retriever) {
		this.retriever = retriever;
	}

	/**
	 * @return the listView
	 */
	public ListRequirementsView getParent() {
		return parent;
	}

	/**
	 * @param listView the listView to set
	 */
	public void setParent(ListRequirementsView listView) {
		this.parent = listView;
	}
}

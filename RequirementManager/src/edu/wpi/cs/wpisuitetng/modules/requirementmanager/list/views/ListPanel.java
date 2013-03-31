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

	public enum Mode {
		FILTER,
		ITERATION
	};

	/** Panel containing the filter building interface */
	protected FilterBuilderPanel filterBuilderPanel;
	/** Panel containing the filter building interface */
	protected IterationBuilderPanel iterationBuilderPanel;

	/** Panel containing the results of the requirement list */
	protected ResultsPanel resultsPanel;

	/** Panel containing tabs instead of filter */
	protected ListTabView filtersTabPanel;

	/** The layout manager for this panel */
	protected SpringLayout layout;

	/** The main tab controller */
	protected MainTabController tabController;

	private RetrieveAllFiltersController retriever;

	private ListRequirementsView parent; 

	private JScrollPane builderScrollPane;

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
		this.filterBuilderPanel = new FilterBuilderPanel(this);
		this.iterationBuilderPanel = new IterationBuilderPanel(this);
		this.builderScrollPane = new JScrollPane(filterBuilderPanel);

		//Testing adding tab views
		this.filtersTabPanel = new ListTabView(this);
		JScrollPane listScrollPane = new JScrollPane(filtersTabPanel);
		this.resultsPanel = new ResultsPanel(tabController);


		// Constrain the filtersPanel
		layout.putConstraint(SpringLayout.NORTH, listScrollPane, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, listScrollPane, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, listScrollPane, 0, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.EAST, listScrollPane, 200, SpringLayout.WEST, listScrollPane);

		// Constrain the filterBuilderPanel
		layout.putConstraint(SpringLayout.NORTH, builderScrollPane, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, builderScrollPane, 0, SpringLayout.EAST, listScrollPane);
		layout.putConstraint(SpringLayout.EAST, builderScrollPane, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, builderScrollPane, 85, SpringLayout.NORTH, builderScrollPane);

		// Constrain the resultsPanel
		layout.putConstraint(SpringLayout.NORTH, resultsPanel, 0, SpringLayout.SOUTH, builderScrollPane);
		layout.putConstraint(SpringLayout.WEST, resultsPanel, 0, SpringLayout.EAST, listScrollPane);
		layout.putConstraint(SpringLayout.EAST, resultsPanel, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, resultsPanel, 0, SpringLayout.SOUTH, this);

		//Constrain our new filterTabPanel
		layout.putConstraint(SpringLayout.NORTH, filtersTabPanel, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, filtersTabPanel, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, filtersTabPanel, 0, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.EAST, filtersTabPanel, 200, SpringLayout.WEST, listScrollPane);

		// Add the panels
		this.add(listScrollPane);
		this.add(builderScrollPane);
		this.add(resultsPanel);
	}

	public ResultsPanel getResultsPanel(){
		return resultsPanel;
	}


	public MainTabController getTabController() {
		return tabController;
	}

	public FilterBuilderPanel getFilterBuilderPanel(){
		return filterBuilderPanel;
	}

	public IterationBuilderPanel getIterationBuilderPanel(){
		return iterationBuilderPanel;
	}

	public FilterListPanel getFilterPanel(){
		return filtersTabPanel.getFilterList();
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

	public void setMode(Mode newMode) {
		if (newMode == Mode.FILTER) {
			this.builderScrollPane.setViewportView(filterBuilderPanel);
		} else {
			this.builderScrollPane.setViewportView(iterationBuilderPanel);
		}
	}
}

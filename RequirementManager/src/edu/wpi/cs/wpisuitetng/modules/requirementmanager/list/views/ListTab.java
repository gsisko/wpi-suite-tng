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

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter.FilterBuilderPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter.FilterListTab;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.iteration.IterationBuilderPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabController;

/** Panel to hold the three portions of the requirement list interface. The
 * list of saved filters is displayed in {@link FilterListTab}, the filter
 * builder is displayed in {@link FilterBuilderPanel}, and the results of
 * the list are displayed in {@link RequirementListPanel}.
 */
@SuppressWarnings("serial")
public class ListTab extends JPanel {

	public enum Mode {
		FILTER,
		ITERATION,
		EDIT
	};

	/** Panel containing filter/iteration builders and requirements list */
	protected JPanel rightPanel;

	/** Panel containing listPanels */
	protected JPanel leftPanel;

	/** Panel containing the filter building interface */
	protected FilterBuilderPanel filterBuilderPanel;

	/** Panel containing the iteration building interface */
	protected IterationBuilderPanel iterationBuilderPanel;
	
	/** Panel containing the Editing Mode interface */
	protected EditModeBuilderPanel editModeBuilderPanel;

	/** Panel containing the results of the requirement list */
	protected RequirementListPanel resultsPanel;

	/** Panel containing tabs for the left panel*/
	protected ListTabPanel tabPanel;

	/** layout for the left panel */
	protected SpringLayout leftLayout;

	/** layout for the right panel */
	protected SpringLayout rightLayout;

	/** layout for the panel */
	protected SpringLayout layout;

	/** layout for the whole panel */
	protected JSplitPane splitPane;

	/** The main tab controller */
	protected MainTabController tabController;

	private ListView parent; 

	private JScrollPane builderScrollPane;

	private Mode currentMode;

	/** Constructs the list panel and sets up the layout for the sub-panels
	 * @param tabController The main tab controller
	 */
	public ListTab(MainTabController tabController, ListView view) {
		this.tabController = tabController;
		parent = view;
		currentMode = Mode.ITERATION;

		// Set the layout manager of this panel
		layout = new SpringLayout();
		this.setLayout(layout);

		splitPane = new JSplitPane();
		leftPanel = new JPanel();
		leftPanel.setMinimumSize(new Dimension (260, 500));
		rightPanel = new JPanel();
		rightPanel.setMinimumSize(new Dimension(500, 500));

		splitPane.setOneTouchExpandable(false);
		splitPane.setDividerLocation(260);
		splitPane.setContinuousLayout(true);

		// Construct the panels that compose the list view
		filterBuilderPanel = new FilterBuilderPanel(this);
		iterationBuilderPanel = new IterationBuilderPanel(this);
		editModeBuilderPanel = new EditModeBuilderPanel(this);
		tabPanel = new ListTabPanel(this);
		filterBuilderPanel.setupControllersAndListeners();
		iterationBuilderPanel.setupControllersAndListeners();
		
		JScrollPane listScrollPane = new JScrollPane(tabPanel);
		builderScrollPane = new JScrollPane(iterationBuilderPanel);
		resultsPanel = new RequirementListPanel(tabController, this);


		// Construct the layout manager and add constraints
		rightLayout = new SpringLayout();
		rightPanel.setLayout(rightLayout);
		leftLayout = new SpringLayout();
		leftPanel.setLayout(leftLayout);

		// Constrain the FilterBuilderPanel and IteationBuilderPanel
		rightLayout.putConstraint(SpringLayout.NORTH, builderScrollPane, 0, SpringLayout.NORTH, rightPanel);
		rightLayout.putConstraint(SpringLayout.WEST, builderScrollPane, 0, SpringLayout.WEST, rightPanel);
		rightLayout.putConstraint(SpringLayout.EAST, builderScrollPane, 0, SpringLayout.EAST, rightPanel);
		rightLayout.putConstraint(SpringLayout.SOUTH, builderScrollPane, 90, SpringLayout.NORTH, builderScrollPane);

		// Constrain the resultsPanel
		rightLayout.putConstraint(SpringLayout.NORTH, resultsPanel, 0, SpringLayout.SOUTH, builderScrollPane);
		rightLayout.putConstraint(SpringLayout.WEST, resultsPanel, 0, SpringLayout.WEST, rightPanel);
		rightLayout.putConstraint(SpringLayout.EAST, resultsPanel, 0, SpringLayout.EAST, rightPanel);
		rightLayout.putConstraint(SpringLayout.SOUTH, resultsPanel, 0, SpringLayout.SOUTH, rightPanel);

		// Constrain the listScrollPane
		leftLayout.putConstraint(SpringLayout.NORTH, listScrollPane, 0, SpringLayout.NORTH, leftPanel);
		leftLayout.putConstraint(SpringLayout.WEST, listScrollPane, 0, SpringLayout.WEST, leftPanel);
		leftLayout.putConstraint(SpringLayout.EAST, listScrollPane, 0, SpringLayout.EAST, leftPanel);
		leftLayout.putConstraint(SpringLayout.SOUTH, listScrollPane, 0, SpringLayout.SOUTH, leftPanel);

		// Constrain the splitPane
		layout.putConstraint(SpringLayout.NORTH, splitPane, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, splitPane, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, splitPane, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, splitPane, 0, SpringLayout.SOUTH, this);

		// Add the panels
		leftPanel.add(listScrollPane);
		rightPanel.add(builderScrollPane);
		rightPanel.add(resultsPanel);
		splitPane.setLeftComponent(leftPanel);
		splitPane.setRightComponent(rightPanel);
		this.add(splitPane);
	}

	public RequirementListPanel getResultsPanel(){
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

	public EditModeBuilderPanel getEditModeBuilderPanel() {
		return editModeBuilderPanel;
	}

	public ListTabPanel getTabPanel(){
		return tabPanel;
	}

	/**
	 * @return the listView
	 */
	public ListView getParent() {
		return parent;
	}

	/**
	 * @param listView the listView to set
	 */
	public void setParent(ListView listView) {
		parent = listView;
	}

	public void setMode(Mode newMode) {
		if (currentMode != newMode) {
			if (newMode == Mode.FILTER) {
				builderScrollPane.setViewportView(filterBuilderPanel);
			} else {
				builderScrollPane.setViewportView(iterationBuilderPanel);
			}
			currentMode = newMode;
		}
	}
	
	public Mode getMode() {
		return currentMode;
	}

	/**
	 * @return the splitPane
	 */
	public JSplitPane getSplitPane() {
		return splitPane;
	}

	/**
	 * @param splitPane the splitPane to set
	 */
	public void setSplitPane(JSplitPane splitPane) {
		this.splitPane = splitPane;
	}

}

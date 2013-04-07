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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.IToolbarGroupProvider;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.RefreshRequirementsAction;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.RetrieveAllModelsController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.RetrieveAllRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.RetrieveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.charts.*;

/**
 * View that contains the entire requirement listing interface
 */
@SuppressWarnings("serial")
public class ListView extends JPanel implements IToolbarGroupProvider {
	
	/** Panel containing the list interface */
	protected ListTab mainPanel;
	
	/** The layout manager for this panel */
	protected SpringLayout layout;
	
	/** The panel containing buttons for the tool bar */
	protected ToolbarGroupView buttonGroup;
	
	/** The refresh button that reloads the results of the list/filter */
	protected JButton btnRefresh;
	
	/** The display pie chart button that loads the pie chart tab */
	protected JButton btnDisplayPieChart;

	/** Controller to handle list and filter requests from the user */
	protected RetrieveAllRequirementsController controller;
	
	protected RetrieveAllModelsController filterController;
	protected RetrieveAllModelsController iterationController;
	
	/** The main tab controller */
	protected MainTabController tabController;
	
	/**
	 * Construct the view
	 * @param tabController The main tab controller
	 * @param tab The Tab containing this view
	 */
	public ListView(final MainTabController tabController) {
		this.tabController = tabController;
		
		mainPanel = new ListTab(tabController, this);
		
		// Construct the layout manager and add constraints
		layout = new SpringLayout();
		this.setLayout(layout);
		layout.putConstraint(SpringLayout.NORTH, mainPanel, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, mainPanel, 0, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.WEST, mainPanel, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, mainPanel, 0, SpringLayout.EAST, this);
		
		// Add the mainPanel to this view
		this.add(mainPanel);
		
		// Initialize the controllers
		controller = new RetrieveAllRequirementsController(this);
		filterController = new RetrieveAllModelsController(mainPanel.getTabPanel().getFilterList(), mainPanel.getFilterBuilderPanel(), "filter");
		iterationController = new RetrieveAllModelsController(mainPanel.getTabPanel().getIterationList(), mainPanel.getIterationBuilderPanel(), "iteration");
		
		// Add a listener for row clicks in the actual table
		mainPanel.getResultsPanel().getResultsTable().addMouseListener(new RetrieveRequirementController(this.getListTab().getResultsPanel()));
		
		
		// Instantiate the button panel
		buttonGroup = new ToolbarGroupView("Options for Requirements");
		
		// Instantiate the refresh button
		btnRefresh = new JButton();
		btnRefresh.setAction(new RefreshRequirementsAction(controller));
		buttonGroup.getContent().add(btnRefresh);
		buttonGroup.setPreferredWidth(250);
		
		btnRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				filterController.refreshData();
				iterationController.refreshData();
			}
		});
		
		// Instantiate the refresh button
		btnDisplayPieChart = new JButton("Display Charts");
		buttonGroup.getContent().add(new JLabel(" "));
		buttonGroup.getContent().add(btnDisplayPieChart);

		btnDisplayPieChart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tabController.addTab("Charts", new ImageIcon(), new PieChartView(mainPanel), "Charts for this project's requirements");
			}
		});
	}
	
	public void refreshData() {
		// Load initial data
		filterController.refreshData();
		iterationController.refreshData();
	}
	
	public RetrieveAllRequirementsController getController() {
		return controller;
	}
	
	public RetrieveAllModelsController getFilterController() {
		return filterController;
	}
	
	public RetrieveAllModelsController getIterationController() {
		return iterationController;
	}
	
	public ListTab getListTab() {
		return mainPanel;
	}

	@Override
	public ToolbarGroupView getGroup() {
		return buttonGroup;
	}
}

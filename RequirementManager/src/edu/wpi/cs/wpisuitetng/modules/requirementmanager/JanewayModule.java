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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.toolbar.ToolbarController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.toolbar.ToolbarView;

/** The Requirement Manager module added to the Janeway client.
 */
public class JanewayModule implements IJanewayModule {
	
	/** The tabs used by this module */
	private ArrayList<JanewayTabModel> tabs;
	
	/** The controllers used by this module */
	public final MainTabController mainTabController;
	/** The controller that controls the toolbar */
	public final ToolbarController toolbarController;
	
	/** The main panel that holds everything */
	public final MainTabPanel mainTabPanel;
	/** The toolbar itself */
	public final ToolbarView toolbarView;
	
	/** Construct a new RequirementManager module
	 */
	public JanewayModule() {
		// Setup main tab view and controller
		mainTabPanel = new MainTabPanel(this);
		mainTabController = mainTabPanel.getMainTabController();
		
		// Setup tool bar view and controller
		toolbarView = new ToolbarView(mainTabController);
		toolbarController = new ToolbarController(toolbarView, mainTabController);
		
		// Add the refresh button
		toolbarController.setRelevantTabGroup(((ListView)mainTabPanel.getComponentAt(0)).getGroup());
		
		// Sets up an array that holds all the tabs
		tabs = new ArrayList<JanewayTabModel>();
		JanewayTabModel tab = new JanewayTabModel("Requirement Manager", new ImageIcon(), toolbarView, mainTabPanel);
		tabs.add(tab);
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#getName()
	 */
	public String getName() {
		return "Requirement Manager";
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#getTabs()
	 */
	public List<JanewayTabModel> getTabs() {
		return tabs;
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#invokeWhenSelected()
	 */
	public void invokeWhenSelected() {
		ListView tmpListView = (ListView)mainTabPanel.getComponentAt(0);
		if (!tmpListView.getListTab().getResultsPanel().isInEditMode())
			tmpListView.refreshData();
	}
	
	/**Getter for the Toolbar View
	 * @return the toolbar view
	 */
	public ToolbarView getToolbarView()
	{
		return toolbarView;
	}
}

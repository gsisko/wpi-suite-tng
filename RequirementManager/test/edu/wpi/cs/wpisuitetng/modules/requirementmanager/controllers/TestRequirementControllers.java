/* Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Team 5
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.controllers;

import static org.junit.Assert.*;

import java.awt.Canvas;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter.FilterListTab;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.RetrieveAllRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.RequirementListPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.Tab;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

public class TestRequirementControllers {

	RetrieveAllRequirementsController controller;

	RequirementListPanel view;
	FilterListTab filter;

	ListView rView;
	MainTabPanel mainView;
	Tab tab;
	ListView listView;
	MainTabController tabController;

	@Before
	public void setup() throws Exception{
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(new NetworkConfiguration("http://wpisuitetng"));

		mainView = new MainTabPanel();
		tab = new Tab(mainView, new Canvas());
		tabController = new MainTabController(mainView);
		listView = new ListView(tabController);
		view = new RequirementListPanel(tabController);

		//rView = new ListRequirementsView(new MainTabController(mainView), tab);

		controller = new RetrieveAllRequirementsController(listView);//new RetrieveAllRequirementsController(rView);
	}

	@Test
	public void contructorSetsViewFieldCorrectly()
	{
		/** This test used the wrong constructor and is now broken, fix soon */
		//assertEquals(view, controller.getResultsPanel());
		//assertEquals(filter, controller.getFilterPanel());
	}
}

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

package edu.wpi.modules.requirementmanager.controllers;

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
    
    @Before
    public void setup() throws Exception{
	Network.initNetwork(new MockNetwork());
	Network.getInstance().setDefaultNetworkConfiguration(new NetworkConfiguration("http://wpisuitetng"));
	
	view = new RequirementListPanel(new MainTabController(new MainTabPanel()));
	mainView = new MainTabPanel();
	tab = new Tab(mainView, new Canvas());
	
	//rView = new ListRequirementsView(new MainTabController(mainView), tab);
	
	controller = new RetrieveAllRequirementsController(view, filter);//new RetrieveAllRequirementsController(rView);
    }
    
    @Test
    public void contructorSetsViewFieldCorrectly()
    {
	assertEquals(view, controller.getResultsPanel());
	assertEquals(filter, controller.getFilterPanel());
    }
}

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

import java.awt.Button;
import java.awt.Canvas;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter.FilterListPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.RetrieveAllRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListRequirementsView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ResultsPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.Tab;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

public class TestRequirementControllers {
    
    RetrieveAllRequirementsController controller;
    
    ResultsPanel view;
    FilterListPanel filter;
    
    ListRequirementsView rView;
    MainTabView mainView;
    Tab tab;
    
    @Before
    public void setup() throws Exception{
	Network.initNetwork(new MockNetwork());
	Network.getInstance().setDefaultNetworkConfiguration(new NetworkConfiguration("http://wpisuitetng"));
	
	view = new ResultsPanel(new MainTabController(new MainTabView()));
	mainView = new MainTabView();
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

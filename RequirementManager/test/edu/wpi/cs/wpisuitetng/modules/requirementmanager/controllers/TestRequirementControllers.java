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

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.RetrieveAllRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.Filter;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.FilterType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.OperatorType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementTab;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

public class TestRequirementControllers {

	RetrieveAllRequirementsController controller;

	Requirement requirementTester,reqTest;
	
	Filter testFilter, testOtherFilter;

	MainTabPanel mainTabPanel;
	ListView listView;
	MainTabController tabController;
	
	RequirementView rView;
	RequirementTab rTab, rTab2;

	@Before
	public void setup() throws Exception{
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(new NetworkConfiguration("http://wpisuitetng"));

		mainTabPanel = new MainTabPanel();
		tabController = new MainTabController(mainTabPanel);
		listView = new ListView(tabController);
		
		listView.setAllFilters(null);
		testFilter = new Filter(FilterType.Type, OperatorType.EqualTo, RequirementType.Epic, true);
		testFilter.setType(FilterType.Type);
		testFilter.setComparator(OperatorType.EqualTo);
		testFilter.setValue(RequirementType.Epic);
		testFilter.setUseFilter(true);

		testOtherFilter = new Filter(FilterType.Type, OperatorType.EqualTo, RequirementType.Epic, true);
		testOtherFilter.setType(FilterType.Id);
		testOtherFilter.setComparator(OperatorType.GreaterThan);
		testOtherFilter.setValue(3);
		testOtherFilter.setUseFilter(true);
		Filter[] filterArray = {testFilter, testOtherFilter};
		listView.setAllFilters(filterArray);

		controller = new RetrieveAllRequirementsController(listView);
		
		requirementTester = new Requirement();
		requirementTester.setId(10);
		requirementTester.setPriority(RequirementPriority.High);
		requirementTester.setType(RequirementType.Epic);
		    
		reqTest = new Requirement("test", "test", RequirementType.Epic, RequirementPriority.High, 1);
		reqTest.setType(RequirementType.NonFunctional);
		requirementTester.setPriority(RequirementPriority.Low);
		
		rView = new RequirementView(tabController);
	}

	@Test
	public void contructorSetsViewFieldCorrectly()
	{
	    assertEquals(controller.getResultsPanel(), listView.getListTab().getResultsPanel());
	    assertEquals(controller.getFilterPanel(), listView.getListTab().getTabPanel().getFilterList());
	    
	    controller.refreshData();

	    Requirement[] reqArray = {requirementTester, reqTest};
	    
	    //controller.errorReceivingData("Do I Work?");
	    controller.receivedData(reqArray);
	}
	
	@Test
	public void testRequirementAttributePanel(){
	    rTab = new RequirementTab(rView, reqTest, RequirementTab.Mode.CREATE);
	    rTab2 = new RequirementTab(rView, reqTest, RequirementTab.Mode.EDIT);
	    
	    rTab2.setMode(RequirementTab.Mode.CREATE);
	    assertEquals(RequirementTab.Mode.CREATE, rTab.getMode());
	    assertEquals(RequirementTab.Mode.CREATE, rTab2.getMode());
	    
	    assertFalse((rTab.getRequirement().identify(requirementTester)).booleanValue());
	    assertTrue((rTab2.getRequirement().identify(reqTest)).booleanValue());
	}
	
}

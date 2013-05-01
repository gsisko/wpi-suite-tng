/** Copyright (c) 2013 -- WPI Suite
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

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.JanewayModule;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter.FilterBuilderPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.RetrieveAllRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.Filter;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.FilterType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.OperatorType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IBuilderPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListTab;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementTab;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.SaveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;
import java.util.Date;

@SuppressWarnings("unchecked")
public class TestRequirementControllers {

	RetrieveAllRequirementsController controller;
	SaveRequirementController saveController;

	Requirement requirementTester,reqTest;
	
	Filter testFilter, testOtherFilter;
	Iteration testIteration, testOtherIteration;
	
	
	MainTabPanel mainTabPanel;
	ListView listView;
	ListTab listTab;
	MainTabController tabController;
	
	RequirementView rView;
	RequirementTab rTab, rTab2;
	
	FilterBuilderPanel filterBuilderTest;

	@Before
	public void setup() throws Exception{
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(new NetworkConfiguration("http://wpisuitetng"));

		mainTabPanel = new MainTabPanel(new JanewayModule());
		tabController = new MainTabController(mainTabPanel);
		listView = new ListView(tabController);
		
		testIteration = new Iteration("Test", new Date(400), new Date(500));
		testOtherIteration = new Iteration();
		testIteration.setName("Test Other");
		testIteration.setStartDate(new Date(300));
		testIteration.setEndDate(new Date(500));
		Iteration[] iterationArray = {testIteration, testOtherIteration};
		
		testFilter = new Filter(FilterType.Type, OperatorType.EqualTo, RequirementType.Epic, true);
		testFilter.setType(FilterType.Type);
		testFilter.setComparator(OperatorType.EqualTo);
		testFilter.setValue(RequirementType.Epic);
		testFilter.setUseFilter(true);

		testOtherFilter = new Filter(FilterType.Type, OperatorType.EqualTo, RequirementType.Epic, true);
		testOtherFilter.setType(FilterType.Status);
		testOtherFilter.setComparator(OperatorType.GreaterThan);
		testOtherFilter.setValue(3);
		testOtherFilter.setUseFilter(true);
		Filter[] filterArray = {testFilter, testOtherFilter};
		listView.setAllFilters(filterArray);
		listView.setAllIterations(iterationArray);

		controller = new RetrieveAllRequirementsController(listView);
		
		requirementTester = new Requirement();
		requirementTester.setId(10);
		requirementTester.setPriority(RequirementPriority.High);
		requirementTester.setType(RequirementType.Epic);
		    
		reqTest = new Requirement("test", "test", RequirementType.Epic, RequirementPriority.High, "1",0);
		reqTest.setType(RequirementType.NonFunctional);
		requirementTester.setPriority(RequirementPriority.Low);
		
		rView = new RequirementView(tabController);
		
		listTab = new ListTab(tabController, listView);
		filterBuilderTest = new FilterBuilderPanel(listTab);
		
		saveController = new SaveRequirementController(rView);
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
	
	@Test
	public void tesFilterBuilderPanel(){
	    filterBuilderTest.resetFields();
	    filterBuilderTest.setCurType("Type");
	    assertEquals("Type", filterBuilderTest.getCurType());
	    filterBuilderTest.setCurrentMode(IBuilderPanel.Mode.CREATE);
	    assertEquals(IBuilderPanel.Mode.CREATE, filterBuilderTest.getCurrentMode());
	    filterBuilderTest.setCurrentFilter(testFilter);
	    assertTrue(testFilter.identify(filterBuilderTest.getCurrentFilter()));
	    
	    
	    assertEquals(2, filterBuilderTest.getIterationNames().length);
	    
	    
	    //String json = filterBuilderTest.convertCurrentModelToJSON();
	    //filterBuilderTest.translateAndDisplayModel(json);

	    filterBuilderTest.setModeAndBtn(IBuilderPanel.Mode.CREATE);
	    filterBuilderTest.setModeAndBtn(IBuilderPanel.Mode.EDIT);
	    
	    filterBuilderTest.setInputEnabled(true);
	    filterBuilderTest.toggleNewCancelMode();

	    
	}
	
	@Test
	public void testSaveController(){
	    saveController.getView().getAttributePanel().setCurrentRequirement(reqTest);
	    saveController.getView().getAttributePanel().setMode(RequirementTab.Mode.EDIT);
	    saveController.getView().setMode(RequirementTab.Mode.EDIT);
	    
	    saveController.getView().getIterationBox().addItem(testIteration);
	    saveController.getView().getAttributePanel().getRequirementName().setText("This is a Test!!");
	    saveController.getView().getAttributePanel().getRequirementDescription().setText("This is a Test!!");
	    saveController.save();
	    
	    saveController.getView().getTabPanel().getNotePanel().getNoteMessage().setText("This is a Test Note");
	    
	    saveController.saveNote();
	    
	    assertEquals(true, true);
	}
}

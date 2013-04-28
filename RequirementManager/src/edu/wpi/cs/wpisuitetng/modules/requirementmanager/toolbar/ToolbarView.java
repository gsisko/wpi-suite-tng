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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.toolbar;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.janeway.gui.widgets.JPlaceholderTextField;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.charts.ChartView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListTab;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabController;

/**
 * This is the view that contains buttons to create requirements and display pei charts.
 * Always has a group of global commands (Create Requirement, List).
 */
@SuppressWarnings("serial")
public class ToolbarView extends DefaultToolbarView {

	private JButton createRequirement;
	private JButton btnDisplayPieChart;
	private JPlaceholderTextField listField;
	private ToolbarGroupView toolbarGroup;
	private MainTabController tabController;
	private JButton helpButton;
	
	/** Panel containing the list interface */
	protected ListTab mainPanel;
	
	/**
	 * Create a ToolbarView.
	 * @param tabController The MainTabController this view should open tabs with
	 */
	public ToolbarView(final MainTabController tabController) {
		this.setTabController(tabController);
		
		mainPanel = ((ListView) tabController.getView().getComponentAt(0)).getListTab();
		
		// Construct the content panel
		JPanel content = new JPanel();
		SpringLayout layout  = new SpringLayout();
		content.setLayout(layout);
		content.setOpaque(false);
				
		// Construct the create requirement button
		createRequirement = new JButton();
		createRequirement.setAction(new CreateRequirementAction(tabController));
		
		// Construct the list field
		listField = new JPlaceholderTextField("Lookup by ID", 13);
		listField.addActionListener(new LookupRequirementController(tabController, listField, this));
		
		// Instantiate the charts button
		btnDisplayPieChart = new JButton("Display Charts");
		btnDisplayPieChart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {				
				tabController.addTab("Charts", new ImageIcon(), new ChartView(mainPanel), "Charts for this project's requirements");
			}
		});
		btnDisplayPieChart.setPreferredSize(new Dimension(140, 25));
		
		//Create the help button
		helpButton = new JButton("User Manual");
		helpButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
            		try {
            			// Builds the uri using the default file-separator for the system
            			String separator = System.getProperty("file.separator");
            		    String uri = new java.io.File( "." ).getCanonicalPath();
            		    uri += separator;
            		    uri += "RequirementManagerUserManualWebsite";
            		    uri += separator;
            		    uri += "index.html";
            		    File file = new File(uri);
            		    URI userManualURI = file.toURI();
            		    Desktop.getDesktop().browse(userManualURI);
            		} catch (IOException e1){
            		    // TODO Auto-generated catch block
            		    e1.printStackTrace();
            		}
    		}
		});
		helpButton.setPreferredSize(new Dimension(140, 25));
		
		// Add buttons to the content panel
		content.add(createRequirement);
		content.add(listField);
		content.add(btnDisplayPieChart);
		content.add(helpButton);

		// Configure the layout of the buttons on the content panel
		layout.putConstraint(SpringLayout.NORTH, createRequirement, 5, SpringLayout.NORTH, content); //Create Requirement button to top of panel
		layout.putConstraint(SpringLayout.WEST, createRequirement, 5, SpringLayout.WEST, content); //Create Requirement button to left of panel
		layout.putConstraint(SpringLayout.WEST, btnDisplayPieChart, 10, SpringLayout.EAST, createRequirement); //Display Charts next to Create Requirement
		layout.putConstraint(SpringLayout.NORTH, btnDisplayPieChart, 0, SpringLayout.NORTH, createRequirement); //Display Chart to top of panel
		layout.putConstraint(SpringLayout.SOUTH, btnDisplayPieChart, 0, SpringLayout.SOUTH, createRequirement); //Align bot of Create Requirements and Display Charts
		layout.putConstraint(SpringLayout.NORTH, helpButton, 12, SpringLayout.SOUTH, createRequirement); //Align Help button to bottom of Create Requirements
		layout.putConstraint(SpringLayout.EAST, helpButton, 0, SpringLayout.EAST, btnDisplayPieChart); //Align Help button to right of panel
		layout.putConstraint(SpringLayout.NORTH, listField, 15, SpringLayout.SOUTH, createRequirement); //Align Lookup ID to bot of Create Requirements
		layout.putConstraint(SpringLayout.WEST, listField, 0, SpringLayout.WEST, createRequirement); //Align Lookup ID to left side of Create Requirements

		// Construct a new toolbar group to be added to the end of the toolbar
		toolbarGroup = new ToolbarGroupView("Home", content);
		
		// Calculate the width of the toolbar
		Double toolbarGroupWidth = createRequirement.getPreferredSize().getWidth() + btnDisplayPieChart.getPreferredSize().getWidth()+ 40;
		toolbarGroup.setPreferredWidth(toolbarGroupWidth.intValue());
		addGroup(toolbarGroup);
	}
	
	public ToolbarGroupView getToolbarGroup() {
		return toolbarGroup;
	}

	/**
	 * @return the tabController
	 */
	public MainTabController getTabController() {
		return tabController;
	}

	/**
	 * @param tabController the tabController to set
	 */
	public void setTabController(MainTabController tabController) {
		this.tabController = tabController;
	}
	
	/**Getter for create requirement button
	 * @return the create requirement button
	 */
	public JButton getCreateRequirementButton()
	{
		return createRequirement;
	}
	/**Getter for the lookup by ID box
	 * @return the lookup by ID box
	 */
	public JPlaceholderTextField getIDbox()
	{
		return listField;
	}
	

}

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
 * The Requirement tab's toolbar panel.
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
		this.tabController = tabController;
		
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
		listField = new JPlaceholderTextField("Lookup Requirement by ID", 15);
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
            		    String uri = new java.io.File( "." ).getCanonicalPath();
            		    uri += "\\RequirementManagerUserManualWebsite\\index.html";
            		    File file = new File(uri);
            		    URI userManualURI = file.toURI();
            		    System.out.println("current uri: " + uri);
            		    Desktop.getDesktop().browse(userManualURI);
            		} catch (IOException e1){
            		    // TODO Auto-generated catch block
            		    e1.printStackTrace();
            		}
    		}
		});
		
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
		layout.putConstraint(SpringLayout.EAST, helpButton, 0, SpringLayout.EAST, btnDisplayPieChart); //Align Help button to left of panel
		layout.putConstraint(SpringLayout.NORTH, listField, 15, SpringLayout.SOUTH, createRequirement); //Align Lookup ID to bot of Create Requirements
		layout.putConstraint(SpringLayout.WEST, listField, 0, SpringLayout.WEST, createRequirement); //Align Lookup ID to right side of Create Requirements

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

}

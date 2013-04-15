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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
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
	
	/** Panel containing the list interface */
//	protected ListTab mainPanel;
	
	/**
	 * Create a ToolbarView.
	 * @param tabController The MainTabController this view should open tabs with
	 */
	public ToolbarView(final MainTabController tabController) {
		this.tabController = tabController;
		
//		mainPanel = new ListTab(tabController, this);

		// Construct the content panel
		JPanel content = new JPanel();
		SpringLayout layout  = new SpringLayout();
		content.setLayout(layout);
		content.setOpaque(false);
				
		// Construct the create requirement button
		createRequirement = new JButton();
		createRequirement.setAction(new CreateRequirementAction(tabController));
		createRequirement.setPreferredSize(new Dimension(120, 40));
		
		// Construct the list field
		listField = new JPlaceholderTextField("Lookup by ID", 15);
		listField.addActionListener(new LookupRequirementController(tabController, listField, this));
		listField.setPreferredSize(new Dimension(120, 40));
		
		// Add buttons to the content panel
		content.add(createRequirement);
		content.add(listField);
		
		// Instantiate the charts button
		btnDisplayPieChart = new JButton("Display Charts");
		btnDisplayPieChart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tabController.addTab("Charts", new ImageIcon(), new ChartView(((ListView) tabController.getView().getTabComponentAt(0)).getListTab()), "Charts for this project's requirements");
			}
		});
		btnDisplayPieChart.setPreferredSize(new Dimension(120, 40));
		
		content.add(btnDisplayPieChart);

		// Configure the layout of the buttons on the content panel
		layout.putConstraint(SpringLayout.NORTH, createRequirement, 5, SpringLayout.NORTH, content);
		layout.putConstraint(SpringLayout.WEST, createRequirement, 15, SpringLayout.WEST, content);
		layout.putConstraint(SpringLayout.SOUTH, createRequirement, (int)listField.getPreferredSize().height+15 , SpringLayout.SOUTH, content);
		layout.putConstraint(SpringLayout.EAST, createRequirement, (int)15+15+btnDisplayPieChart.getPreferredSize().width, SpringLayout.EAST, content);
		
		layout.putConstraint(SpringLayout.NORTH, btnDisplayPieChart, 5, SpringLayout.NORTH, content);
		layout.putConstraint(SpringLayout.EAST, btnDisplayPieChart, 15, SpringLayout.EAST, content);
		layout.putConstraint(SpringLayout.WEST, btnDisplayPieChart, 5, SpringLayout.WEST, content);
		layout.putConstraint(SpringLayout.SOUTH, btnDisplayPieChart, (int)15+listField.getHeight(), SpringLayout.SOUTH, content);

		layout.putConstraint(SpringLayout.NORTH, listField, 15, SpringLayout.SOUTH, createRequirement);
		layout.putConstraint(SpringLayout.EAST, listField, 7, SpringLayout.EAST, content);
		layout.putConstraint(SpringLayout.WEST, listField, 15, SpringLayout.WEST, btnDisplayPieChart);
		layout.putConstraint(SpringLayout.SOUTH, listField, 15, SpringLayout.NORTH, content);
		
		// Construct a new toolbar group to be added to the end of the toolbar
		toolbarGroup = new ToolbarGroupView("Home", content);
		
		// Calculate the width of the toolbar
		Double toolbarGroupWidth = createRequirement.getPreferredSize().getWidth() + btnDisplayPieChart.getPreferredSize().getWidth()+ 80;
		toolbarGroup.setPreferredWidth(toolbarGroupWidth.intValue());
		addGroup(toolbarGroup);
	}
	
	public ToolbarGroupView getToolbarGroup() {
		return toolbarGroup;
	}

}

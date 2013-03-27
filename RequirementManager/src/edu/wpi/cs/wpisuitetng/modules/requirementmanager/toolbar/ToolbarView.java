/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Cassiopia Hudson
 ******************************************************************************/


package edu.wpi.cs.wpisuitetng.modules.requirementmanager.toolbar;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.janeway.gui.widgets.JPlaceholderTextField;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabController;

/**
 * The Requirement tab's toolbar panel.
 * Always has a group of global commands (Create Requirement, List).
 */
@SuppressWarnings("serial")
public class ToolbarView extends DefaultToolbarView {

	private JButton createRequirement;
	private JButton listRequirements;
	private JPlaceholderTextField listField;
	
	/**
	 * Create a ToolbarView.
	 * @param tabController The MainTabController this view should open tabs with
	 */
	public ToolbarView(MainTabController tabController) {

		// Construct the content panel
		JPanel content = new JPanel();
		SpringLayout layout  = new SpringLayout();
		content.setLayout(layout);
		content.setOpaque(false);
				
		// Construct the create requirement button
		createRequirement = new JButton();
		createRequirement.setAction(new CreateRequirementAction(tabController));
		
		// Construct the list button
		listRequirements = new JButton("List Requirements");
		listRequirements.setAction(new ListRequirementsAction(tabController));
		
		// Construct the list field
		listField = new JPlaceholderTextField("Lookup by ID", 15);
		listField.addActionListener(new LookupRequirementController(tabController, listField, this));
		
		// Configure the layout of the buttons on the content panel
		layout.putConstraint(SpringLayout.NORTH, createRequirement, 5, SpringLayout.NORTH, content);
		layout.putConstraint(SpringLayout.WEST, createRequirement, 8, SpringLayout.WEST, content);
		layout.putConstraint(SpringLayout.WEST, listRequirements, 10, SpringLayout.EAST, createRequirement);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, listRequirements, 0, SpringLayout.VERTICAL_CENTER, createRequirement);
		layout.putConstraint(SpringLayout.NORTH, listField, 15, SpringLayout.SOUTH, createRequirement);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, listField, 5, SpringLayout.EAST, createRequirement);
		
		// Add buttons to the content panel
		content.add(createRequirement);
		content.add(listRequirements);
		content.add(listField);
		
		// Construct a new toolbar group to be added to the end of the toolbar
		ToolbarGroupView toolbarGroup = new ToolbarGroupView("Home", content);
		
		// Calculate the width of the toolbar
		Double toolbarGroupWidth = createRequirement.getPreferredSize().getWidth() + listRequirements.getPreferredSize().getWidth() + 40; // 40 accounts for margins between the buttons
		toolbarGroup.setPreferredWidth(toolbarGroupWidth.intValue());
		addGroup(toolbarGroup);
	}

}

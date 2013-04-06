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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.IToolbarGroupProvider;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabController;

/**
 * View that contains the entire requirement listing interface
 */
@SuppressWarnings("serial")
public class PieChartView extends JPanel implements IToolbarGroupProvider {
	
	/** Panel containing the list interface */
	protected ListTab mainPanel;
	
	/** The layout manager for this panel */
	protected SpringLayout layout;
	
	/** The panel containing buttons for the tool bar */
	protected ToolbarGroupView buttonGroup;
	
	/** The refresh button that reloads the results of the list/filter */
	protected JButton btnDisplayPieChart;
	
	/** The main tab controller */
	protected MainTabController tabController;
	
	/**
	 * Construct the view
	 * @param tabController The main tab controller
	 * @param tab The Tab containing this view
	 */
	public PieChartView(MainTabController tabController) {
		this.tabController = tabController;
		
		// Construct the layout manager and add constraints
		layout = new SpringLayout();
		this.setLayout(layout);
		layout.putConstraint(SpringLayout.NORTH, mainPanel, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, mainPanel, 0, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.WEST, mainPanel, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, mainPanel, 0, SpringLayout.EAST, this);
		
		// Add the mainPanel to this view
		this.add(mainPanel);
			
		// Instantiate the button panel
		buttonGroup = new ToolbarGroupView("Pie Chart");
	
		// Instantiate the Display Pie Chart button
		btnDisplayPieChart = new JButton();
		//btnDisplayPieChart.setAction(new RefreshRequirementsAction(controller));
		buttonGroup.getContent().add(btnDisplayPieChart);
		buttonGroup.setPreferredWidth(200);
		
		add(btnDisplayPieChart);
		
		btnDisplayPieChart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO: get Tab
			}
		});		
	}
	
	public ListTab getListPanel() {
		return mainPanel;
	}

	@Override
	public ToolbarGroupView getGroup() {
		return buttonGroup;
	}
}

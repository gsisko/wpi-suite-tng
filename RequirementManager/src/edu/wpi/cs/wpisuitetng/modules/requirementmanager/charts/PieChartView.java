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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.charts;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.IToolbarGroupProvider;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabController;

/**
 * View that contains the pie chart 
 */

@SuppressWarnings("serial")
public class PieChartView extends JPanel implements IToolbarGroupProvider{
	
	/** The panel containing buttons for the tool bar */
	protected ToolbarGroupView buttonGroup;
	
	/** The refresh button that reloads the results of the list/filter */
	protected JButton btnRefresh;
	
	/** The panel containing the actual pie chart */
	private PieChartPanel piePanel;
	
	/** The MainTabController holding this view */
	private MainTabController tabController;
	
	/** The panel containing various chart options on the left of the view */
	private JPanel optionsPanel;
	
	/**
	 * Construct the view
	 */
	public PieChartView(MainTabController controller) {
		this.tabController = controller;
		this.piePanel = new PieChartPanel();
		this.buttonGroup = new ToolbarGroupView("All Charts");
		this.btnRefresh = new JButton("Refresh");
		buttonGroup.getContent().add(btnRefresh);
		buttonGroup.setPreferredWidth(150);
		
		btnRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refreshData();
			}
		});
		
		this.optionsPanel = new JPanel();
		
		//TODO: Move this into a new file with a new class?
		this.optionsPanel.setLayout(new BorderLayout());
		this.optionsPanel.add(new JLabel("Fill me up with options please"), BorderLayout.NORTH);
		optionsPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		// Construct the layout 
		this.setLayout(new BorderLayout());
		
		//Add the panels to the layout
		this.add(piePanel, BorderLayout.CENTER);
		this.add(optionsPanel, BorderLayout.WEST);
	}

	/** Refresh and reload data in the pie chart */
	public void refreshData() {
		//TODO: Implement for PieChart Data?
		// Load initial data
		piePanel.refreshChart();
	}
	
	/** Returns the piePanel */
	public PieChartPanel getPiePanel() {
		return piePanel;
	}

	/**
	 * @return the parent
	 */
	public MainTabController getTabController() {
		return tabController;
	}

	/** Returns the button group to place on the top of the toolbar */
	@Override
	public ToolbarGroupView getGroup() {
		// TODO Auto-generated method stub
		return buttonGroup;
	}
	
}
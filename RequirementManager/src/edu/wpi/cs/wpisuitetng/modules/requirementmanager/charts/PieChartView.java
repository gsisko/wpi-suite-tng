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

import javax.swing.JButton;
import javax.swing.JPanel;

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
	MainTabController parent;
	
	/**
	 * Construct the view
	 */
	public PieChartView(MainTabController parent){
		super();
		
		this.parent = parent;
		this.piePanel = new PieChartPanel();
		this.buttonGroup = new ToolbarGroupView("Pie Chart Refresh");
		this.btnRefresh = new JButton();
	}

	/** Refresh and reload data in the pie chart */
	public void refreshData() {
		//TODO: Implement for PieChart Data?
		// Load initial data
//		filterController.refreshData();
//		iterationController.refreshData();
	}

	/** Returns the button group to place on the top of the toolbar */
	@Override
	public ToolbarGroupView getGroup() {
		// TODO Auto-generated method stub
		return buttonGroup;
	}}

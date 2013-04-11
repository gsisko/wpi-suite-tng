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

import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.IToolbarGroupProvider;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListTab;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/**
 * View that contains the pie chart 
 */

@SuppressWarnings("serial")
public class PieChartView extends JPanel implements IToolbarGroupProvider{

	/** The panel containing buttons for the tool bar */
	protected ToolbarGroupView buttonGroup;

	/** The refresh button that reloads the results of the list/filter */
	protected JButton btnRefresh;
	
	/** The panel containing the actual status pie chart */
	private PieChartPanel statusPiePanel;
	
	/** The panel containing the actual iteration pie chart */
	private PieChartPanel iterationPiePanel;

	/** The panel containing the actual pie chart */
	private PieChartPanel piePanel;

	/** The panel containing various chart options on the left of the view */
	private ChartOptionsPanel optionsPanel; 
	
	private boolean isFiltered;
	@SuppressWarnings("unused")
	private String chartType;

	private ListTab view;

	/**
	 * Construct the view and initialize necessary internal variables
	 * @param view A ListTab that this view will be used in
	 */
	public PieChartView(ListTab view) {
		this.statusPiePanel = new PieChartPanel();
		this.iterationPiePanel = new PieChartPanel();
		this.buttonGroup = new ToolbarGroupView("All Charts");
		this.view = view;
		this.btnRefresh = new JButton("Refresh");
		this.isFiltered = false;
		this.chartType = "Requirement Status";
		buttonGroup.getContent().add(btnRefresh);
		buttonGroup.setPreferredWidth(150);

		btnRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refreshData();
			}
		});

		this.optionsPanel = new ChartOptionsPanel(piePanel, this);

		// Construct the layout 
		this.setLayout(new BorderLayout());

		//Add the panels to the layout
		this.add(iterationPiePanel, BorderLayout.CENTER);
		iterationPiePanel.setVisible(false);
		this.add(statusPiePanel, BorderLayout.CENTER);
		statusPiePanel.setVisible(true);
		

		this.add(optionsPanel, BorderLayout.WEST);
	}

	/** Refresh and reload data in the pie chart */
	public void refreshData() {
		// Load initial data
		view.getParent().refreshData();
		
		Requirement[] requirements;
		if(!isFiltered)
			requirements = view.getParent().getAllRequirements();
		else
			requirements = view.getParent().getDisplayedRequirements();
		Iteration[] iterations = view.getParent().getAllIterations();
		
		//if(chartType == "Requirement Status")
			statusPiePanel.refreshStatusChart(requirements);
		//else if(chartType == "Requirement Iteration")*/
			iterationPiePanel.refreshIterationChart(requirements, iterations);
	}

	/** Returns the piePanel */
	public PieChartPanel getPiePanel() {
		return piePanel;
	}
	
	/** Returns the piePanel */
	public void setDataTypeVisible(String dataType) {
		if(dataType=="Requirement Status"){
			this.remove(iterationPiePanel);
			this.add(statusPiePanel, BorderLayout.CENTER);
        	statusPiePanel.setVisible(true);
        	this.repaint();
		}
		else if(dataType=="Requirement Iteration"){
			this.remove(statusPiePanel);
			this.add(iterationPiePanel, BorderLayout.CENTER);
    		iterationPiePanel.setVisible(true);
    		this.repaint();
        }
	}
	
	public void setDataFiltered(String filterStatus) {
		if(filterStatus=="Applied")
        	isFiltered = true;
        else if(filterStatus=="Not Applied")
        	isFiltered = false;
	}

	/** Returns the button group to place on the top of the toolbar */
	@Override
	public ToolbarGroupView getGroup() {
		// TODO Auto-generated method stub
		return buttonGroup;
	}

}
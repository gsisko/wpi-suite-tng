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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IListPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementStatus;

/** Panel that contains the bar chart specified by the user in the ChartOptionsPanel.
 * @author Team 5 D13
 */
@SuppressWarnings("serial")
public class BarChartPanel extends JPanel implements IListPanel {

	private CategoryDataset dataset;
	private ChartPanel chartPanel;
	private JFreeChart chart;
	private boolean isFiltered;

	/** Default constructor for BarChartPanel*/
	public BarChartPanel() {

		// This will create the dataset
		dataset = createDataset();

		// based on the dataset we create the chart
		chart = createChart(dataset, "Sample Chart: Favorite OS");

		// we put the chart into a panel
		chartPanel = new ChartPanel(chart);

		// default size
		chartPanel.setPreferredSize(new Dimension(1300, 500));

		//Disable right clicking
		//Be careful, this will re-enable the chart editor!
		chartPanel.setPopupMenu(null);

		//Disable click+drag to zoom
		chartPanel.setDomainZoomable(false);
		chartPanel.setRangeZoomable(false);
		
		// Construct the layout
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

		//Add the chart to our layout
		add(chartPanel, BorderLayout.CENTER);

		//Are we using filtered data?
		isFiltered = true;
	}

	/** Creates the sample dataset 
	 * @return a sample set of data for a bar chart
	 */
	private CategoryDataset createDataset() {

		// row keys...
		String series1 = "Linux";
		String series2 = "Mac";
		String series3 = "Windows";
		String series4 = "DOS";
		
		// column keys...
		String category1 = "";

		//Add the actual data to the set and return it
		DefaultCategoryDataset result = new DefaultCategoryDataset();
		result.addValue(29, series1, category1);
		result.addValue(20, series2, category1);
		result.addValue(51, series3, category1);
		result.addValue(2, series4, category1);
		
		return result;
	}

	/** Creates a chart using specified data and title
	 * 
	 * @param dataset set of data to use to make the bar chart
	 * @param title title of the chart
	 * @return JFreeChart object containing the chart with the title and data
	 */
	private JFreeChart createChart(CategoryDataset dataset, String title) {

		JFreeChart chart = ChartFactory.createBarChart3D(title, //chart title
				"", //category axis label
				"", //value axis label  
				dataset, // data
				PlotOrientation.VERTICAL, //orientation
				true, //legend
				true, //Tooltips
				false); //URLs

		CategoryPlot plot = (CategoryPlot) chart.getPlot();

		//Customize this chart!
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		// disable bar outlines and other cosmetic tweaks
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);
		renderer.setMaximumBarWidth(.35); // set maximum width to 35% of chart

		// set up gradient paints for series...
		GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.red, 
				0.0f, 0.0f, new Color(0, 0, 64));
		GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, Color.blue, 
				0.0f, 0.0f, new Color(0, 64, 0));
		GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, Color.green, 
				0.0f, 0.0f, new Color(64, 0, 0));
		GradientPaint gp3 = new GradientPaint(0.0f, 0.0f, Color.yellow, 
				0.0f, 0.0f, new Color(0, 0, 64));
		GradientPaint gp4 = new GradientPaint(0.0f, 0.0f, Color.orange, 
				0.0f, 0.0f, new Color(0, 64, 0));
		GradientPaint gp5 = new GradientPaint(0.0f, 0.0f, Color.cyan, 
				0.0f, 0.0f, new Color(64, 0, 0));
		
		renderer.setSeriesPaint(0, gp0);
		renderer.setSeriesPaint(1, gp1);
		renderer.setSeriesPaint(2, gp2);
		renderer.setSeriesPaint(3, gp3);
		renderer.setSeriesPaint(4, gp4);
		renderer.setSeriesPaint(5, gp5);		
		
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(
				CategoryLabelPositions.createUpRotationLabelPositions(
						Math.PI / 6.0));

		return chart;
	}

	/** Function to refresh and redraw bar chart with Status of requirements
	 * @param requirements array of requirements to use to generate chart
	 */
	public void refreshStatusChart(Requirement[] requirements) {
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		for (RequirementStatus rs : RequirementStatus.values()) {
			int count = 0;
			for (int i = 0; i < requirements.length; i++) {
				if (rs == requirements[i].getStatus()) count++;
			}
			if (count > 0) data.addValue(count, rs.toString(), "");
		}
		
		dataset = data;
		if (isFiltered){
			chart = createChart(dataset, "Status of Displayed Requirements");
		} else {
			chart = createChart(dataset, "Status of All Requirements");
		}
		chart.getPlot().setBackgroundPaint(new Color(255,255,255));
		chart.getPlot().setOutlineVisible(false);
		chartPanel.setChart(chart);
	}

	/** Function to refresh and redraw bar chart with Iterations 
	 * 
	 * @param requirements array of requirements to use to generate chart
	 * @param iterations array of iterations to use to generate chart
	 */
	public void refreshIterationChart(Requirement[] requirements, Iteration[] iterations) {
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		for (Iteration iter : iterations) {
			int count = 0;
			for (int i = 0; i < requirements.length; i++) {
				if (iter.getID() == requirements[i].getIteration()) count++;
			}
			if (count > 0) {
				if (iter.getName().equals(""))
					data.addValue(count, "Backlog", "");
				else
					data.setValue(count, iter.getName(), "");
			}

		}
		
		dataset = data;
		if (isFiltered){
			chart = createChart(dataset, "Iterations of Displayed Requirements");
		} else {
			chart = createChart(dataset, "Iterations of All Requirements");
		}
		chart.getPlot().setBackgroundPaint(new Color(255,255,255));
		chart.getPlot().setOutlineVisible(false);
		chartPanel.setChart(chart);
		
	}

	/** Function to refresh and redraw bar chart with Assigned Users 
	 * @param requirements array of requirements to use to obtain the assigned users for the chart
	 */
	public void refreshAssignedUsersCountChart(Requirement[] requirements) {
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		
		//ArrayList to contain all users assigned to iterations
		ArrayList<String> allUsers = new ArrayList<String>();
		
		//HashMap that contains how many times each user has been assigned
		HashMap<String, Integer> requirementCountMap = new HashMap<String, Integer>();
		
		for (int i = 0; i < requirements.length; i++){
			allUsers.addAll(requirements[i].getUserNames()); //Combine all users into a giant list
			
			//Add one if the user appears in the list
			for(String user : requirements[i].getUserNames()){
				if (requirementCountMap.get(user) == null){ //Check if user has an entry
					requirementCountMap.put(user, 1);  //If not this is the first entry
				} else {
					requirementCountMap.put(user, requirementCountMap.get(user) + 1); //Otherwise increment the count by one
				}
			}
		}
		
		//Add data to the chart after we have gone through each requirement for each user
		for(int i = 0; i < allUsers.size(); i++){
			data.addValue(requirementCountMap.get(allUsers.get(i)), allUsers.get(i) , "");	
		}
		
		dataset = data;
		if (isFiltered){
			chart = createChart(dataset, "Number of Requirements per User for Displayed Requirements");
		} else { 
			chart = createChart(dataset, "Number of Requirements per User for All Requirements");
		}
		
		chart.getPlot().setBackgroundPaint(new Color(255,255,255));
		chart.getPlot().setOutlineVisible(false);
		chartPanel.setChart(chart);
	}
	
	/** Function to refresh and redraw bar chart with Assigned Users 
	 * @param requirements array of requirements to use to obtain the assigned users for the chart
	 */
	public void refreshAssignedUsersEstimateChart(Requirement[] requirements) {
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		
		//ArrayList to contain all users assigned to iterations
		ArrayList<String> allUsers = new ArrayList<String>();
		
		//HashMap that contains how many times each user has been assigned
		HashMap<String, Integer> estimateCountMap = new HashMap<String, Integer>();
		
		for (int i = 0; i < requirements.length; i++){
			allUsers.addAll(requirements[i].getUserNames()); //Combine all users into a giant list
			
			//Add one if the user appears in the list
			for(String user : requirements[i].getUserNames()){
				if (estimateCountMap.get(user) == null){ //Check if user has an entry
					estimateCountMap.put(user, requirements[i].getEstimate());  //If not this is the first entry
				} else {
					estimateCountMap.put(user, estimateCountMap.get(user) + requirements[i].getEstimate()); //Otherwise increment the count by one
				}
			}
		}
		
		//Add data to the chart after we have gone through each requirement for each user
		for(int i = 0; i < allUsers.size(); i++){
			data.addValue(estimateCountMap.get(allUsers.get(i)), allUsers.get(i) , "");	
		}
		
		dataset = data;
		if (isFiltered){	
			chart = createChart(dataset, "Displayed Total Estimate per User");
		} else {
			chart = createChart(dataset, "Total Estimate per User");
		}
		
		chart.getPlot().setBackgroundPaint(new Color(255,255,255));
		chart.getPlot().setOutlineVisible(false);
		chartPanel.setChart(chart);
	}
	
	/** Function to toggle whether to use active filters to get chart information 
	 * @param isFiltered boolean representing if filters should be used
	 */
	public void enableFilter(boolean isFiltered){
		this.isFiltered = isFiltered;
	}

	/**
	 * Method getSelectedUniqueIdentifiers.
	 * @return String[]
	 * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IListPanel#getSelectedUniqueIdentifiers()
	 */
	@Override
	public String[] getSelectedUniqueIdentifiers() {
		return null;
	}

	/**
	 * Method setNewBtnToCancel.
	 * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IListPanel#setNewBtnToCancel()
	 */
	@Override
	public void setNewBtnToCancel() {
	}

	/**
	 * Method setCancelBtnToNew.
	 * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IListPanel#setCancelBtnToNew()
	 */
	@Override
	public void setCancelBtnToNew() {
	}

	/**
	 * Method refreshAll.
	 * @return boolean
	 * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IListPanel#refreshAll()
	 */
	@Override
	public boolean refreshAll() {
		return false;
	}

	/**
	 * Method toggleNewCancelMode.
	 * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IListPanel#toggleNewCancelMode()
	 */
	@Override
	public void toggleNewCancelMode() {
	}

	/**
	 * Method getSelectedUniqueIdentifier.
	 * @param me MouseEvent
	 * @return String
	 * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IListPanel#getSelectedUniqueIdentifier(MouseEvent)
	 */
	@Override
	public String getSelectedUniqueIdentifier(MouseEvent me) {
		return null;
	}

	/**
	 * Method showRecievedModels.
	 * @param jsonString String
	 * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IListPanel#showRecievedModels(String)
	 */
	@Override
	public void showRecievedModels(String jsonString) {
	}

	/**
	 * Method refreshRequirements.
	 * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IListPanel#refreshRequirements()
	 */
	@Override
	public void refreshRequirements() {
	}

	/**
	 * Method setDeleteEnabled.
	 * @param setActive boolean
	 * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IListPanel#setDeleteEnabled(boolean)
	 */
	@Override
	public void setDeleteEnabled(boolean setActive) {
	}

	/**
	 * Method areSelectedItemsDeletable.
	 * @return boolean
	 * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IListPanel#areSelectedItemsDeletable()
	 */
	@Override
	public boolean areSelectedItemsDeletable() {
		return false;
	}

}

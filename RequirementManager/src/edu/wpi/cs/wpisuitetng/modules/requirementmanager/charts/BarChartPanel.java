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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
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

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementStatus;

/**
 * Panel that contains the bar chart specified by the user in the ChartOptionsPanel.
 */
@SuppressWarnings("serial")
public class BarChartPanel extends JPanel {

	private CategoryDataset dataset;
	private ChartPanel chartPanel;
	private JFreeChart chart;
	private boolean isFiltered;

	/** Default constructor for BarChartPanel*/
	public BarChartPanel() {

		// This will create the dataset
		this.dataset = createDataset();

		// based on the dataset we create the chart
		this.chart = createChart(dataset, "Sample Chart: Favorite OS");

		// we put the chart into a panel
		this.chartPanel = new ChartPanel(chart);

		// default size
		chartPanel.setPreferredSize(new Dimension(1300, 500));

		//Disable right clicking
		//Be careful, this will re-enable the chart editor!
		chartPanel.setPopupMenu(null);

		//Disable click+drag to zoom
		this.chartPanel.setDomainZoomable(false);
		this.chartPanel.setRangeZoomable(false);
		
		// Construct the layout
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

		//Add the chart to our layout
		add(chartPanel, BorderLayout.CENTER);

		//Are we using filtered data?
		this.isFiltered = true;
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

		//Customize this chart! TODO: Change this?
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
		
		this.dataset = data;
		if (isFiltered){
			this.chart = createChart(dataset, "Status of Displayed Requirements");
		} else {
			this.chart = createChart(dataset, "Status of All Requirements");
		}
		this.chart.getPlot().setBackgroundPaint(new Color(255,255,255));
		this.chart.getPlot().setOutlineVisible(false);
		this.chartPanel.setChart(this.chart);
	}

	/** Function to refresh and redraw bar chart with Iterations 
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
		
		this.dataset = data;
		if (isFiltered){
			this.chart = createChart(dataset, "Iterations of Displayed Requirements");
		} else {
			this.chart = createChart(dataset, "Iterations of All Requirements");
		}
		this.chart.getPlot().setBackgroundPaint(new Color(255,255,255));
		this.chart.getPlot().setOutlineVisible(false);
		this.chartPanel.setChart(this.chart);
		
	}

	/** Function to refresh and redraw bar chart with Assigned Users 
	 * @param requirements array of requirements to use to obtain the assigned users for the chart
	 */
	public void refreshAssignedUsersChart(Requirement[] requirements) {
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		
		//ArrayList to contain all users assigned to iterations
		ArrayList<User> allUsers = new ArrayList<User>();
		
		//HashMap that contains how many times each user has been assigned
		HashMap<User, Integer> requirementCountMap = new HashMap<User, Integer>();
		
		for (int i = 0; i < requirements.length; i++){
			allUsers.addAll(requirements[i].getUsers()); //Combine all users into a giant list
			
			//Add one if the user appears in the list
			for(User user : requirements[i].getUsers()){
				if (requirementCountMap.get(user) == null){ //Check if user has an entry
					requirementCountMap.put(user, 1);  //If not this is the first entry
				} else {
					requirementCountMap.put(user, requirementCountMap.get(user) + 1); //Otherwise increment the count by one
				}
			}
		}
		
		//Add data to the chart after we have gone through each requirement for each user
		for(int i = 0; i < allUsers.size(); i++){
			data.addValue(requirementCountMap.get(allUsers.get(i)), allUsers.get(i).getName() , "");	
		}
		
		this.dataset = data;
		if (isFiltered){
			this.chart = createChart(dataset, "Number of Users Assigned for Displayed Requirements");
		} else {
			this.chart = createChart(dataset, "Number of Users Assigned for All Requirements");
		}
		
		this.chart.getPlot().setBackgroundPaint(new Color(255,255,255));
		this.chart.getPlot().setOutlineVisible(false);
		this.chartPanel.setChart(this.chart);
	}
	
	/** Function to refresh and redraw bar chart with Assigned Users 
	 * @param requirements array of requirements to use to obtain the assigned users for the chart
	 */
	public void refreshAssignedUsersEstimateChart(Requirement[] requirements) {
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		
		//ArrayList to contain all users assigned to iterations
		ArrayList<User> allUsers = new ArrayList<User>();
		
		//HashMap that contains how many times each user has been assigned
		HashMap<User, Integer> estimateCountMap = new HashMap<User, Integer>();
		
		for (int i = 0; i < requirements.length; i++){
			allUsers.addAll(requirements[i].getUsers()); //Combine all users into a giant list
			
			//Add one if the user appears in the list
			for(User user : requirements[i].getUsers()){
				if (estimateCountMap.get(user) == null){ //Check if user has an entry
					estimateCountMap.put(user, requirements[i].getEstimate());  //If not this is the first entry
				} else {
					estimateCountMap.put(user, estimateCountMap.get(user) + requirements[i].getEstimate()); //Otherwise increment the count by one
				}
			}
		}
		
		//Add data to the chart after we have gone through each requirement for each user
		for(int i = 0; i < allUsers.size(); i++){
			data.addValue(estimateCountMap.get(allUsers.get(i)), allUsers.get(i).getName() , "");	
		}
		
		this.dataset = data;
		if (isFiltered){	
			this.chart = createChart(dataset, "Estimate for Users of Displayed Requirements");
		} else {
			this.chart = createChart(dataset, "Estimate for Users of All Requirements");
		}
		
		this.chart.getPlot().setBackgroundPaint(new Color(255,255,255));
		this.chart.getPlot().setOutlineVisible(false);
		this.chartPanel.setChart(this.chart);
	}
	
	/** Function to toggle whether to use active filters to get chart information 
	 * @param isFiltered boolean representing if filters should be used
	 */
	public void enableFilter(boolean isFiltered){
		this.isFiltered = isFiltered;
	}

}

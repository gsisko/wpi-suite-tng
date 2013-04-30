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
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementStatus;

/** Panel that contains the pie chart specified by the user in the ChartOptionsPanel.
 */
@SuppressWarnings("serial")
public class PieChartPanel extends JPanel {

	private PieDataset dataset;
	private ChartPanel chartPanel;
	private JFreeChart chart;
	private boolean isFiltered;

	/** Default constructor for PieChartPanel*/
	public PieChartPanel() {

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
	private PieDataset createDataset() {
		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("Linux", 29);
		result.setValue("Mac", 20);
		result.setValue("Windows", 51);
		result.setValue("DOS", 2);
		return result;
	}

	/** Creates a chart using specified data and title
	 * 
	 * @param dataset set of data to use to make the bar chart
	 * @param title title of the chart
	 * @return JFreeChart object containing the chart with the title and data
	 */
	private JFreeChart createChart(PieDataset dataset, String title) {

		JFreeChart chart = ChartFactory.createPieChart3D(title, // chart title
				dataset, // data
				true, // include legend
				true, //Tooltips
				false); //URLs

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.8f);

		return chart;
	}

	/** Function to refresh and redraw pie chart with Status of requirements
	 * @param requirements array of requirements to use to generate chart
	 */
	public void refreshStatusChart(Requirement[] requirements) {
		DefaultPieDataset data = new DefaultPieDataset();
		for (RequirementStatus rs : RequirementStatus.values()) {
			int count = 0;
			for (int i = 0; i < requirements.length; i++) {
				if (rs == requirements[i].getStatus()) count++;
			}
			if (count > 0) data.setValue(rs.toString(), count);
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
		DefaultPieDataset data = new DefaultPieDataset();
		for (Iteration iter : iterations) {
			int count = 0;
			for (int i = 0; i < requirements.length; i++) {
				if (iter.getID() == requirements[i].getIteration()) count++;
			}
			if (count > 0) {
				if (iter.getName().equals(""))
					data.setValue("Backlog", count);
				else
					data.setValue(iter.getName(), count);
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
		DefaultPieDataset data = new DefaultPieDataset();

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
			data.setValue(allUsers.get(i), requirementCountMap.get(allUsers.get(i)));
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
		DefaultPieDataset data = new DefaultPieDataset();

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
			data.setValue(allUsers.get(i), estimateCountMap.get(allUsers.get(i)));
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

	public void enableFilter(boolean isFiltered){
		this.isFiltered = isFiltered;
	}

}

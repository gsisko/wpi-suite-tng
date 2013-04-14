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

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementStatus;

/**
 * Panel that contains the pie chart specified by the user in the ChartOptionsPanel.
 */
@SuppressWarnings("serial")
public class BarChartPanel extends JPanel {

	private CategoryDataset dataset;
	private ChartPanel chartPanel;
	private JFreeChart chart;
	private boolean isFiltered;

	/** Default constructor for PieChartPanel*/
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

		// Construct the layout
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

		//Add the chart to our layout
		add(chartPanel, BorderLayout.CENTER);

		//Are we using filtered data?
		this.isFiltered = true;
	}

	/** Creates the sample dataset */
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

	/** Creates a chart */
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

		// disable bar outlines...
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);

		// set up gradient paints for series...
		GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.blue, 
				0.0f, 0.0f, new Color(0, 0, 64));
		GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, Color.green, 
				0.0f, 0.0f, new Color(0, 64, 0));
		GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, Color.red, 
				0.0f, 0.0f, new Color(64, 0, 0));
		renderer.setSeriesPaint(0, gp0);
		renderer.setSeriesPaint(1, gp1);
		renderer.setSeriesPaint(2, gp2);

		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(
				CategoryLabelPositions.createUpRotationLabelPositions(
						Math.PI / 6.0));

		return chart;
	}

	/** Function to refresh and redraw pie chart with Status */
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

	/** Function to refresh and redraw pie chart with Iterations */
	public void refreshIterationChart(Requirement[] requirements, Iteration[] iterations) {
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		for (Iteration iter : iterations) {
			int count = 0;
			for (int i = 0; i < requirements.length; i++) {
				if (iter.getID() == requirements[i].getAssignedIteration()) count++;
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

	public void enableFilter(boolean isFiltered){
		this.isFiltered = isFiltered;
	}

}

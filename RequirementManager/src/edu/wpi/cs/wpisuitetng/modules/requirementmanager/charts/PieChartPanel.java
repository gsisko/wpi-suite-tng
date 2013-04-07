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
import java.awt.Dimension;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

@SuppressWarnings("serial")
public class PieChartPanel extends JPanel {

	private PieDataset dataset;
	private ChartPanel chartPanel;
	private JFreeChart chart;

	/** Default constructor for PieChartPanel*/
	public PieChartPanel() {
		
		// This will create the dataset
		this.dataset = createDataset();
		
		// based on the dataset we create the chart
		this.chart = createChart(dataset, "Sample Chart: Favorite OS");
		
		// we put the chart into a panel
		this.chartPanel = new ChartPanel(chart);
		
		// default size
		chartPanel.setPreferredSize(new Dimension(500, 500));
		
		// Construct the layout
		this.setLayout(new BorderLayout());
		
		//Add the chart to our layout
		add(chartPanel, BorderLayout.CENTER);
	}
	
	/** Creates the sample dataset */
	private PieDataset createDataset() {
		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("Linux", 29);
		result.setValue("Mac", 20);
		result.setValue("Windows", 51);
		result.setValue("DOS", 2);
		return result;
	}

	/** Creates a chart */
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

	/** Function to refresh and redraw pie chart */
	public void refreshChart() {
		// TODO: ?
	}

}

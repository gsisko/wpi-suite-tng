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

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;


/**
 * Panel that contains options for the currently displayed chart.
 */

@SuppressWarnings("serial")
public class ChartOptionsPanel extends JPanel {

	/** Chart that will have it's options set by this panel */
	private PieChartPanel chart;

	/**
	 * Construct the panel and initialize necessary internal variables
	 * @param chart A PieChartPanel that will have it's options set by this Panel
	 */
	public ChartOptionsPanel(PieChartPanel chart) {

		this.chart = chart;

		//Add buttons and items to the panel
		this.add(new JLabel("Fill me up with options please"), BorderLayout.NORTH);
		//TODO: Add combo box for switching between iteration and requirement status?
		
		// Set Layout
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

	}
	
}

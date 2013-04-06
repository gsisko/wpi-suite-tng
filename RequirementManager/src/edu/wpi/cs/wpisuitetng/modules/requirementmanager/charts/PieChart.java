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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;
//import javax.swing.JFrame;

@SuppressWarnings("serial")
class PieChart extends JComponent {
	
	/** Contains the actual 'slices' of the pie chart to render */
	private Slice[] slices = { 	new Slice(5, Color.black),
			new Slice(33, Color.green),
			new Slice(20, Color.yellow),
			new Slice(15, Color.red) 
			};
	
	/** Contains the actual data values to put into the graph */
	private int[] data;  

	public PieChart() {
//		slices 
	}

	/** Forces the chart model to refresh data */
	
	public void refresh(){
		
		//Clear slices
		slices = null;
		
		//Loop through data and make slices
		for(int i = 0; i < data.length; i++){
			slices[i] = new Slice(data[i], Color.green);
		}
		
	}
	
	/** Actually render the chart. Needs to be called by parent panel! */
	@Override
	public void paint(Graphics g) {
//		super.paint(g);
		drawPie((Graphics2D) g, getBounds(), slices);
	}

	void drawPie(Graphics2D g, Rectangle area, Slice[] slices) {
		double total = 0.0D;
		
		for (int i = 0; i < slices.length; i++) {
			total += slices[i].value;
		}
		
		double curValue = 0.0D;
		int startAngle = 0;
		for (int i = 0; i < slices.length; i++) {
			startAngle = (int) (curValue * 360 / total);
			int arcAngle = (int) (slices[i].value * 360 / total);
			g.setColor(slices[i].color);
			g.fillArc(area.x, area.y, area.width, area.height, startAngle,
					arcAngle);
			curValue += slices[i].value;
		}
	}
	
	public int[] getData(){
		return data;
	}
	
	public void setData(int[] data){
		this.data = data;
	}
}

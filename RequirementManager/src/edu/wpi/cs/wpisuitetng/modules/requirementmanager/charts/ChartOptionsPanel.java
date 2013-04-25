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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

/** Panel that contains options for the currently displayed chart.
 */
@SuppressWarnings({"serial","rawtypes","unchecked"})
public class ChartOptionsPanel extends JPanel{
	
	//The labels
	/** The label for the chartTypeBox */
	private  JLabel chartTypeLabel;
	/** The label for the chartDataBox */
	private  JLabel chartDataLabel;
	/** The label for the filtersOptionsBox */
	private  JLabel filtersLabel;

	//The fillable components
	/** The combo box used to select which chart type to display */
	private  JComboBox chartTypeBox;
	/** The combo box used to select which data to graph */
	private  JComboBox chartDataBox;
	/** The combo box used to select the filters options (IE whether to apply the active filters to the data) */
	private  JComboBox filtersOptionsBox;

	//The layout manager
	/** The layout manager for this panel */
	protected BoxLayout layout; 

	/** Stores the PieChartView that contains the ChartOptionsPanel */
	private ChartView parent;
	
	/** A boolean indicating if input is enabled on the form  */
	protected boolean inputEnabled;

	/** Construct the panel and initialize necessary internal variables
	 * @param parentView The ChartView that contains this panel
	 */
	public ChartOptionsPanel(ChartView parentView){
				
		this.parent = parentView; //Set the parent

		inputEnabled = true;// Indicate that input is enabled

		//Create and set the layout manager that controls the positions of the components
		layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);//Create the layout
		setLayout(layout); //Set the layout

		//create and set a compound border
		Border compound = BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED), BorderFactory.createEmptyBorder(15, 10, 15, 10));
		setBorder(compound);

		//Construct the labels
		chartTypeLabel = new JLabel("Type of chart:");
		chartDataLabel = new JLabel("Data to display:");
		filtersLabel = new JLabel("Active filters:");

		//Create the strings for the boxes
		String[] typeStrings = { "Pie Chart", "Bar Chart"};
		String[] dataStrings = { "Requirement Status", "Requirement Iteration", "Number of Users Assigned To Requirements", "Total Estimate for Each User"};
		String[] filtersStrings = { "Applied", "Not Applied"};

		//Construct the boxes 
		chartTypeBox = new JComboBox(typeStrings);
		chartDataBox = new JComboBox(dataStrings);
		filtersOptionsBox = new JComboBox(filtersStrings);
		
		//Add action listeners to the boxes so data is refreshed when a change is made
		chartTypeBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	parent.setChartType((String) ((JComboBox)e.getSource()).getSelectedItem());
            }
        });  
		
		chartDataBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	parent.setDataTypeVisible((String) ((JComboBox)e.getSource()).getSelectedItem());
            }
        });  
		
		filtersOptionsBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	parent.setDataFiltered((String) ((JComboBox)e.getSource()).getSelectedItem());
            }
        });  
		
		//Set the initial selections for the boxes
		chartTypeBox.setSelectedIndex(0);
		chartDataBox.setSelectedIndex(0);
		filtersOptionsBox.setSelectedIndex(0);
		
		//Set the alignments of the components
		chartTypeLabel.setAlignmentX(LEFT_ALIGNMENT);		
		chartDataLabel.setAlignmentX(LEFT_ALIGNMENT);
		filtersLabel.setAlignmentX(LEFT_ALIGNMENT);
		
		chartTypeBox.setAlignmentX(LEFT_ALIGNMENT);		
		chartDataBox.setAlignmentX(LEFT_ALIGNMENT);
		filtersOptionsBox.setAlignmentX(LEFT_ALIGNMENT);
		
		//Set the sizing of the boxes
		chartTypeBox.setMaximumSize(new Dimension(120, 25));
		chartDataBox.setMaximumSize(chartDataBox.getPreferredSize());
		filtersOptionsBox.setMaximumSize(new Dimension(120, 25));
		
		//Add the components with spacing in between them
		this.add(chartTypeLabel);
		this.add(Box.createRigidArea(new Dimension(0,3)));
		this.add(chartTypeBox);
		this.add(Box.createRigidArea(new Dimension(0,25)));
		this.add(chartDataLabel);
		this.add(Box.createRigidArea(new Dimension(0,3)));
		this.add(chartDataBox);
		this.add(Box.createRigidArea(new Dimension(0,25)));
		this.add(filtersLabel);
		this.add(Box.createRigidArea(new Dimension(0,3)));
		this.add(filtersOptionsBox);
		this.add(Box.createRigidArea(new Dimension(0,25)));

		setInputEnabled(inputEnabled);
	}

	/** Sets whether input is enabled for this panel and its children. This should be used instead of 
	 * JComponent#setEnabled because setEnabled does not affect its children.
	 * 
	 * @param enabled Whether or not input is enabled.
	 */
	protected void setInputEnabled(boolean enabled){
		inputEnabled = enabled;

		enable(chartTypeBox, enabled);
		enable(chartDataBox, enabled);
		enable(filtersOptionsBox, enabled);
	}

	/**
	 * @return the chartTypeBox
	 */
	public JComboBox getChartTypeBox() {
		return chartTypeBox;
	}

	/**
	 * @param chartTypeBox the chartTypeBox to set
	 */
	public void setChartTypeBox(JComboBox chartTypeBox) {
		this.chartTypeBox = chartTypeBox;
	}

	/**
	 * @return the chartDataBox
	 */
	public JComboBox getChartDataBox() {
		return chartDataBox;
	}

	/**
	 * @param chartDataBox the chartDataBox to set
	 */
	public void setChartDataBox(JComboBox chartDataBox) {
		this.chartDataBox = chartDataBox;
	}

	/**
	 * @return the filtersOptionsBox
	 */
	public JComboBox getFiltersOptionsBox() {
		return filtersOptionsBox;
	}

	/**
	 * @param filtersOptionsBox the filtersOptionsBox to set
	 */
	public void setFiltersOptionsBox(JComboBox filtersOptionsBox) {
		this.filtersOptionsBox = filtersOptionsBox;
	}

	/** Method to get the parent of this ChartOptionsPanel
	 * @return the parent
	 */
	public ChartView getParent() {
		return parent;
	}
	
	
	/** Method to get whether input is enabled for this panel
	 * @return A boolean representing whether or not input is enabled for this panel.
	 */
	public boolean getInputEnabled() {
		return inputEnabled;
	}
	
	/** Set the given box to enabled and to the correct color
	 * 
	 * @param box Which box to enable
	 * @param enabled True to enable and False to disable
	 */
	public void enable(JComboBox box, boolean enabled) {
		if (enabled) {
			box.setEnabled(true);
			box.setBackground(Color.WHITE);
		}
		else {
			box.setEnabled(false);
			box.setBackground(new Color(238,238,238));
		}
	}
}
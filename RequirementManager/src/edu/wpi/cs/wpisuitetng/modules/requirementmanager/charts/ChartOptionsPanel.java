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
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableCellRenderer;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.Filter;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.ResultsTableModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ActiveFilterTableCellRenderer;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Iteration;

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
	
	private JLabel filterlistlabel;
	

	//The fillable components
	/** The combo box used to select which chart type to display */
	private  JComboBox chartTypeBox;
	/** The combo box used to select which data to graph */
	private  JComboBox chartDataBox;
	/** The combo box used to select the filters options (IE whether to apply the active filters to the data) */
	private  JComboBox filtersOptionsBox;
	
	private  ResultsTableModel filterTableModel;
	
	private  JTable filtertable;
	//The layout manager
	/** The layout manager for this panel */
	protected BoxLayout layout; 

	//private Filter[] localFilters = {};

	/** Stores the PieChartView that contains the ChartOptionsPanel */
	private ChartView parent;
	
	/** A boolean indicating if input is enabled on the form  */
	protected boolean inputEnabled;


	/** Construct the panel and initialize necessary internal variables
	 * @param parentView The ChartView that contains this panel
	 */
	public ChartOptionsPanel(ChartView parentView){
				
		parent = parentView; //Set the parent

		inputEnabled = true;// Indicate that input is enabled

		//Create and set the layout manager that controls the positions of the components
		layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);//Create the layout
		setLayout(layout); //Set the layout

		//create and set a compound border
		Border compound = BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED), BorderFactory.createEmptyBorder(15, 10, 15, 10));
		setBorder(compound);

		// Construct the table model
		filterTableModel = new ResultsTableModel();

				// Construct the table and configure it
		
		filtertable = new JTable(filterTableModel);
		filtertable.setAutoCreateRowSorter(true); 
		filtertable.setFillsViewportHeight(true);
		filtertable.setDefaultRenderer(String.class, new DefaultTableCellRenderer());
		filtertable.setDefaultRenderer(String.class, new ActiveFilterTableCellRenderer());
		for(MouseListener listener : filtertable.getMouseListeners()){
			filtertable.removeMouseListener(listener);
		}
		for(MouseListener listener : filtertable.getTableHeader().getMouseListeners()){
			filtertable.getTableHeader().removeMouseListener(listener);
		}
		for(MouseMotionListener listener : filtertable.getMouseMotionListeners()){
			filtertable.removeMouseMotionListener(listener);
		}
		filtertable.getTableHeader().setResizingAllowed(false);
		filtertable.getTableHeader().setReorderingAllowed(false);
		
		
		buildTable();
		
		JScrollPane resultsScrollPane = new JScrollPane(filtertable);
		
		
		resultsScrollPane.setPreferredSize(new Dimension(175,250));

		resultsScrollPane.setAlignmentX(CENTER_ALIGNMENT);
		
		//Construct the labels
		chartTypeLabel = new JLabel("Type of chart:");
		chartDataLabel = new JLabel("Data to display:");
		filtersLabel = new JLabel("Active filters:");
		filterlistlabel = new JLabel("List of Active Filters");

		//Create the strings for the boxes
		String[] typeStrings = { "Pie Chart", "Bar Chart"};
		String[] dataStrings = { "Requirement Status", "Requirement Iteration", "Requirements per User", "Estimate per User"};
		String[] filtersStrings = {"Not Applied", "Applied"};

		//Construct the boxes 
		chartTypeBox = new JComboBox(typeStrings);
		chartDataBox = new JComboBox(dataStrings);
		filtersOptionsBox = new JComboBox(filtersStrings);
		
		//Add action listeners to the boxes so data is refreshed when a change is made
		chartTypeBox.addPopupMenuListener(new PopupMenuListener() {
            public void update(){
            	parent.setChartType((String)chartTypeBox.getSelectedItem());
            }

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				update();
			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				update();
			}
        });  
		
		chartDataBox.addPopupMenuListener(new PopupMenuListener() {
            public void update(){
            	parent.setDataTypeVisible((String)chartDataBox.getSelectedItem());
            }

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				update();
			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				update();
			}
        });  
		
		filtersOptionsBox.addPopupMenuListener(new PopupMenuListener() {
            public void update(){
            	parent.setDataFiltered((String)filtersOptionsBox.getSelectedItem());
            }

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				update();
			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				update();
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
		filterlistlabel.setAlignmentX(LEFT_ALIGNMENT);
		
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
		this.add(filterlistlabel);
		this.add(Box.createRigidArea(new Dimension(0,3)));
		this.add(resultsScrollPane);

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
	
	public void setChartData(String[] options) {
		this.chartDataBox.setModel(new DefaultComboBoxModel(options));
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
	
	public void setFiltersOptions(String[] options) {
		this.filtersOptionsBox.setModel(new DefaultComboBoxModel(options));
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
	
	/** Show the filters in the list view
	 * 
	 * @param jsonString An array of models in the form of a JSON string
	 */
	public void buildTable() {
		// Setup data structures
		String[] emptyColumns = {};
		Object[][] emptyData = {};

		// Fire blanks so that the old contents are removed
		this.getModel().setColumnNames(emptyColumns);
		this.getModel().setData(emptyData);
		this.getModel().fireTableStructureChanged();

		Filter[] filters = parent.getView().getParent().getAllFilters();
		ArrayList<Filter> activeFilters = new ArrayList<Filter>();
		for (int i = 0; i < filters.length; i++) {
			if (filters[i].isUseFilter()) {
				activeFilters.add(filters[i]);
			}
		}

		// Add the list of filters to the FilterListPanel object
		if (filters.length > 0 && activeFilters != null && activeFilters.size()>0) {
			// set the column names
			String[] columnNames = {"Id", "Type", "Op", "Value", "Active"};

			// put the data in the table
			Object[][] entries = new Object[activeFilters.size()][columnNames.length];
			for (int i = 0; i < activeFilters.size(); i++) {
				if (activeFilters.get(i).isUseFilter()) {
					entries[i][0] = String.valueOf(activeFilters.get(i).getUniqueID());
					entries[i][1] = activeFilters.get(i).getType().toString();

					if (activeFilters.get(i).getComparator().toString().equals("Contains")) {
						entries[i][2] = "c";
					} else if (activeFilters.get(i).getComparator().toString().equals("DoesNotContain")) {
						entries[i][2] = "!c";
					} else {
						entries[i][2] = activeFilters.get(i).getComparator().toString();
					}

					String typeString = activeFilters.get(i).getType().toString();
					if (typeString.equals("Iteration")) {
						String strId = activeFilters.get(i).getValue();
						while (parent.getView().getParent().getAllIterations().length == 0)
							for (Iteration iter : parent.getView().getParent().getAllIterations()) {
								if (strId.equals(iter.getID() + "")) {
									entries[i][3] = iter.getName();
								}
							}
					}
					else {
						entries[i][3] = activeFilters.get(i).getValue();
					}

					if (activeFilters.get(i).isUseFilter()) {
						entries[i][4] = "yes";
					} else {
						entries[i][4] = "no";
					}
				}
			}
			// fill the table
			this.getModel().setColumnNames(columnNames);
			this.getModel().setData(entries);
			this.getModel().fireTableStructureChanged();
			

			//Hide the Id column
			filtertable.getColumn("Id").setMinWidth(0);
			filtertable.getColumn("Id").setMaxWidth(0);
			filtertable.getColumn("Id").setWidth(0);

			//Set preferred column widths
			//Type
			filtertable.getColumnModel().getColumn(1).setPreferredWidth(150);
			//Op
			filtertable.getColumnModel().getColumn(2).setPreferredWidth(50);
			//Value
			filtertable.getColumnModel().getColumn(3).setPreferredWidth(75);
			//Active
			filtertable.getColumnModel().getColumn(4).setPreferredWidth(75);
		}
	}

	/**
	 * @return the data model for the table
	 */
	public ResultsTableModel getModel() {
		return filterTableModel;
	}
}
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

/**
 * Panel that contains options for the currently displayed chart.
 */
@SuppressWarnings({"serial","rawtypes","unchecked"})
public class ChartOptionsPanel extends JPanel{
	
	/** Chart panel that will have it's options set by this panel */
	private PieChartPanel chart;

	//The labels
	private  JLabel chartTypeLabel; //The label for the chartTypeBox
	private  JLabel chartDataLabel;//The label for the chartDataBox
	private  JLabel filtersLabel; //The label for the filtersOptionsBox

	//The fillable components
	private  JComboBox chartTypeBox;//The combo box used to select which chart type to display
	private  JComboBox chartDataBox;//The combo box used to select which data to graph
	private  JComboBox filtersOptionsBox;//The combo box used to select the filters options (IE whether to apply the active filters to the data)

	//The layout manager
	protected BoxLayout layout; //The layout for this panel

	private PieChartView parent; //Stores the PieChartView that contains the panel
	protected boolean inputEnabled;//A boolean indicating if input is enabled on the form 

	/**
	 * Construct the panel and initialize necessary internal variables
	 * @param chart A PieChartPanel that will have it's options set by this Panel
	 * @param parentView The PieChartView that contains this panel
	 */
	public ChartOptionsPanel(PieChartPanel chart, PieChartView parentView){
		
		this.chart = chart;//Set the chart
		
		parent = parentView; //Set the parent

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
		String[] typeStrings = { "Pie Chart"};
		String[] dataStrings = { "Requirement Status", "Requirement Iteration"};
		String[] filtersStrings = { "Applied", "Not Applied"};

		//Construct the boxes 
		chartTypeBox = new JComboBox(typeStrings);
		chartTypeBox.setEnabled(false);
		chartDataBox = new JComboBox(dataStrings);
		filtersOptionsBox = new JComboBox(filtersStrings);
		
		chartDataBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	parent.setDataTypeVisible((String) ((JComboBox)e.getSource()).getSelectedItem());
            	parent.refreshData();
            }
        });  
		
		filtersOptionsBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	parent.setDataFiltered((String) ((JComboBox)e.getSource()).getSelectedItem());
            	parent.refreshData();
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
		chartDataBox.setMaximumSize(new Dimension(175, 25));
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

	/**
	 * Enables or disables a given JComponent and sets is color accordingly
	 * 
	 * @param box Box to be enabled / disabled
	 * @param enabled True for enable, false for disable
	 */
	public void toggleEnabled(JComponent box, boolean enabled) {
		if (enabled) {
			box.setEnabled(true);
			box.setBackground(Color.WHITE);
		}
		else {
			box.setEnabled(false);
			box.setBackground(new Color(238,238,238));
		}
	}

	/**
	 * Sets whether input is enabled for this panel and its children. This should be used instead of 
	 * JComponent#setEnabled because setEnabled does not affect its children.
	 * 
	 * @param enabled Whether or not input is enabled.
	 */
	protected void setInputEnabled(boolean enabled){
		inputEnabled = enabled;

		toggleEnabled(chartTypeBox, enabled);
		toggleEnabled(chartDataBox, enabled);
		toggleEnabled(filtersOptionsBox, enabled);

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

	/**
	 * @return the parent
	 */
	public PieChartView getParent() {
		return parent;
	}
	
	
	/**
	 * Returns a boolean representing whether or not input is enabled for this panel.
	 * @return the inputEnabled boolean 	A boolean representing whether or not input is enabled for this panel.
	 */
	public boolean getInputEnabled() {
		return inputEnabled;
	}

	/**
	 * @return the chart
	 */
	public PieChartPanel getChart() {
		return chart;
	}

	/**
	 * @param chart the chart to set
	 */
	public void setChart(PieChartPanel chart) {
		this.chart = chart;
	}
}
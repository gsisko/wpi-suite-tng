package edu.wpi.cs.wpisuitetng.modules.requirementmanager.iteration;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import net.sourceforge.jdatepicker.JDatePicker;


public class IterationBuilderPanel {
	public enum Mode {
		CREATE,
		EDIT
	}
/**
 * Panel to contain the filter builder for defect searching
 */
@SuppressWarnings("serial")
public class FilterBuilderPanel extends JPanel implements ActionListener{


	//the labels
	private final JLabel StartDate; 
	private final JLabel EndDate;
	private final JLabel Name;

	//the fillable components
	private JTextField txtValue;
	private final JDatePicker enddatePicker;
	private final JDatePicker startdatePicker;

	//button
	private final JButton btnSearch;

	private final IterationListPanel parent;
	private final IterationListPanel grandpa;

	private Mode currentMode;

	private String curType = "Id";

	/**
	 * Construct the panel
	 */
	public FilterBuilderPanel(IterationListPanel view) {
		parent = view;
		grandpa = null;
		currentMode = Mode.CREATE;
		//create title
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBorder(BorderFactory.createTitledBorder("Iteration Builder"));

		//construct the panels
		JLabel NameLabel = new JLabel("Name:");
		JLabel startdateLabel = new JLabel("Start Date:");
		JLabel nddateLabel = new JLabel("End Date:");
		btnSearch= new JButton("Search");

		//construct the components
		txtValue = new JTextField();
		txtValue.setEnabled(false);

		//set initial conditions
		JDatePicker 
		
		// The action listener for this is below

		btnUpdate.addActionListener(new SaveIterationController(parent.getParent()));
		btnUpdate.setEnabled(false);

		//set the layout
		setLayout(new GridBagLayout());
		GridBagConstraints FilterBuilderConstraints = new GridBagConstraints();
		FilterBuilderConstraints.anchor= GridBagConstraints.NORTH;

		//adjust location
		btnSave.setAlignmentX(Component.RIGHT_ALIGNMENT);

		//Start Date
		//Set the constraints for the "typeLabel" and add it to the view
		FilterBuilderConstraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		FilterBuilderConstraints.ipady = 0;//This tells the layout to reset the vertical ipad from the previously defined 20 units to now 0 units
		FilterBuilderConstraints.anchor = GridBagConstraints.CENTER; //This sets the anchor of the field, here we have told it to anchor the component to the top center of it's field
		FilterBuilderConstraints.insets = new Insets(0,25,0,0);  //Set the top padding to 10 units  of blank space
		FilterBuilderConstraints.gridx = 0;//Set the x coord of the cell of the layout we are describing
		FilterBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		add(typeLabel, FilterBuilderConstraints);//Actually add the "typenLabel" to the layout given the previous constraints
		//Set the constraints for the "endBox"  and add it to the view
		FilterBuilderConstraints.fill = GridBagConstraints.CENTER;//This sets the constraints of this field so that the item will stretch both horizontally and vertically to fill it's area
		FilterBuilderConstraints.gridx = 1;//Set the x coord of the cell of the layout we are describing
		FilterBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		add(typeBox, FilterBuilderConstraints);//Actually add the "typeBox" to the layout given the previous constraints
		//end Start Date

		//End Date
		//Set the constraints for the "typeLabel" and add it to the view
		FilterBuilderConstraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		FilterBuilderConstraints.ipady = 0;//This tells the layout to reset the vertical ipad from the previously defined 20 units to now 0 units
		FilterBuilderConstraints.anchor = GridBagConstraints.CENTER; //This sets the anchor of the field, here we have told it to anchor the component to the top center of it's field
		FilterBuilderConstraints.insets = new Insets(0,25,0,0);  //Set the top padding to 10 units  of blank space
		FilterBuilderConstraints.gridx = 0;//Set the x coord of the cell of the layout we are describing
		FilterBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		add(typeLabel, FilterBuilderConstraints);//Actually add the "typenLabel" to the layout given the previous constraints
		//Set the constraints for the "endBox"  and add it to the view
		FilterBuilderConstraints.fill = GridBagConstraints.CENTER;//This sets the constraints of this field so that the item will stretch both horizontally and vertically to fill it's area
		FilterBuilderConstraints.gridx = 1;//Set the x coord of the cell of the layout we are describing
		FilterBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		add(typeBox, FilterBuilderConstraints);//Actually add the "typeBox" to the layout given the previous constraints
		//end End Date

		
		//value:
		//Set the constraints for the "value" and add it to the view
		FilterBuilderConstraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		FilterBuilderConstraints.anchor = GridBagConstraints.CENTER; //This sets the anchor of the field, here we have told it to anchor the component to the top center of it's field
		FilterBuilderConstraints.insets = new Insets(0,25,0,0);
		FilterBuilderConstraints.gridx = 4;//Set the x coord of the cell of the layout we are describing
		FilterBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		add(valueLabel, FilterBuilderConstraints);//Actually add the "valueLabel" to the layout given the previous constraints
		//Set the constraints for the "value" and add it to the view
		FilterBuilderConstraints.fill = GridBagConstraints.CENTER;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		FilterBuilderConstraints.gridx = 5;//Set the x coord of the cell of the layout we are describing
		FilterBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		FilterBuilderConstraints.ipadx=80;
		add(txtValue, FilterBuilderConstraints);//Actually add the "txtValue" to the layout given the previous constraints
		add(valueBox, FilterBuilderConstraints);//Actually add the "valueBox" to the layout given the previous constraints
		//end value

		//Search button:
		//Set the constraints for the "Save" and add it to the view
		FilterBuilderConstraints.fill = GridBagConstraints.NONE;//This sets the constraints of this field so that the item will not stretch to fill it's area
		FilterBuilderConstraints.anchor = GridBagConstraints.CENTER; //This sets the anchor of the field, here we have told it to anchor the component to the bottom right of it's field
		FilterBuilderConstraints.gridx = 8;//Set the x coord of the cell of the layout we are describing
		FilterBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		btnSave.setPreferredSize(new Dimension (10,30));
		add(btnSave, FilterBuilderConstraints);//Actually add the "Save" to the layout given the previous constraints
		//end Save button

	}
	public JButton getButton()
	{
		return btnSave;
	}
	public JComboBox<String> getFilterType()
	{
		return typeBox;
	}

	public JComboBox<String> getFilterOperator()
	{
		return comparatorBox;
	}

	public JTextField getFilterValue()
	{
		return txtValue;
	}

	public JComboBox<String> getFilterValueBox()
	{
		return valueBox;
	}


	public JComboBox<String> getStatus()
	{
		return userFilterBox;
	}

	/**
	 * @return the currentMode
	 */
	public Mode getCurrentMode() {
		return currentMode;
	}

	/**
	 * @param currentMode the currentMode to set
	 */
	public void setCurrentMode(Mode currentMode) {
		this.currentMode = currentMode;
	}

	public Filter getCurrentFilter()
	{
		return currentFilter;
	}

	public void setCurrentFilter(Filter newFilter)
	{
		currentFilter = newFilter;
	}
	/**
	 * @return the grandpa
	 */
	
}}



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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.SaveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.views.JNumberTextField;
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter.SaveFilterController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.RefreshRequirementsAction;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.RetrieveAllRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.RetrieveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.ResultsTableModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.DateTableCellRenderer;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.Filter;

/**
 * Panel to contain the filter builder for defect searching
 */
@SuppressWarnings("serial")
public class FilterBuilderPanel extends JPanel {

	// enum to say whether or not you are creating
	public enum Mode {
		CREATE,
		EDIT
	}
	
	//the labels
	private final JLabel typeLabel; 
	private final JLabel comparatorLabel;
	private final JLabel valueLabel;
	private final JLabel userFilterLabel;

	//the fillable components
	private final JComboBox<String> typeBox;
	private final JComboBox<String> comparatorBox;
	private final JTextField txtValue;
	private final JComboBox<String> userFilterBox;
	private Filter currentFilter;
	private Mode mode;
	
	//button
	private final JButton Save;
	
	private final ListPanel parent;
	/**
	 * Construct the panel
	 */
	@SuppressWarnings("deprecation")
	public FilterBuilderPanel(ListPanel view) {
		parent = view;
		mode = Mode.CREATE;
		//create title
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBorder(BorderFactory.createTitledBorder("Filter Builder"));
		
		//construct the panels
		typeLabel = new JLabel("Type:");
		comparatorLabel = new JLabel("Comparator:");
		valueLabel = new JLabel("Value:");
		userFilterLabel = new JLabel("Status:");
		Save= new JButton("Save");

		//construct the components
		txtValue = new JTextField();
		txtValue.setEnabled(true);
		
		

		//create strings for the boxes
		String[] typeStrings = { "Id", "Name", "Description","Type", "Status","Priority","ReleaseNumber","Estimate","ActualEffort"};
		String[] comparatorStrings = {"Greater than","GreaterThanOrEqualTo","LessThan","LessThanOrEqualTo","EqualTo","NotEqualTo","Contains","DoesNotContain"};
		String[] userFilterStrings ={"Active","Inactive"};
		
		//construct the boxes
		typeBox = new JComboBox<String>(typeStrings);
		comparatorBox = new JComboBox<String>(comparatorStrings);
		userFilterBox = new JComboBox<String>(userFilterStrings);
		
		//set initial conditions
		typeBox.setSelectedIndex(0);
		typeBox.setEnabled(true);
		comparatorBox.setSelectedIndex(0);
		comparatorBox.setEnabled(true);
		userFilterBox.setSelectedIndex(0);
		userFilterBox.setEnabled(true);

		//Save.addActionListener(new SaveFilterController(parent));
		Save.setEnabled(true);

		//set the layout
		setLayout(new GridBagLayout());
		GridBagConstraints FilterBuilderConstraints = new GridBagConstraints();
		FilterBuilderConstraints.anchor= GridBagConstraints.NORTH;

		//adjust location
		Save.setAlignmentX(Component.RIGHT_ALIGNMENT);

		//type
		//Set the constraints for the "typeLabel" and add it to the view
		FilterBuilderConstraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		FilterBuilderConstraints.ipady = 0;//This tells the layout to reset the vertical ipad from the previously defined 20 units to now 0 units
		//FilterBuilderConstraints.weightx = 0.25;//This is the weight of this field, which tells the layout manager how big this field should be in proportion to the other components
		FilterBuilderConstraints.anchor = GridBagConstraints.FIRST_LINE_END; //This sets the anchor of the field, here we have told it to anchor the component to the top center of it's field
		FilterBuilderConstraints.insets = new Insets(0,25,0,0);  //Set the top padding to 10 units  of blank space
		FilterBuilderConstraints.gridx = 0;//Set the x coord of the cell of the layout we are describing
		FilterBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		//FilterBuilderConstraints.ipadx=1;
		add(typeLabel, FilterBuilderConstraints);//Actually add the "descriptionLabel" to the layout given the previous constraints
		//Set the constraints for the "typeBox"  and add it to the view
		FilterBuilderConstraints.fill = GridBagConstraints.FIRST_LINE_START;//This sets the constraints of this field so that the item will stretch both horizontally and vertically to fill it's area
		//FilterBuilderConstraints.weightx = 0.75;//This is the weight of this field, which tells the layout manager how big this field should be in proportion to the other components
		//FilterBuilderConstraints.insets = new Insets(10,5,0,0); //Set the padding; here, there will be 10 units of blank space padding on the top and 5 on the left side
		FilterBuilderConstraints.gridx = 1;//Set the x coord of the cell of the layout we are describing
	//	FilterBuilderConstraints.gridwidth = 1;   //Tell the layout that this field will fill 2 columns
		FilterBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		//FilterBuilderConstraints.ipadx=15;
		add(typeBox, FilterBuilderConstraints);//Actually add the "typeBox" to the layout given the previous constraints
		//end Type

		//comparator
		//Set the constraints for the "comparatorLabel" and add it to the view
		FilterBuilderConstraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		FilterBuilderConstraints.ipady = 0;//This tell	s the layout to reset the vertical ipad from the previously defined 20 units to now 0 units
		//FilterBuilderConstraints.weightx = 0.25;//This is the weight of this field, which tells the layout manager how big this field should be in proportion to the other components
		FilterBuilderConstraints.anchor = GridBagConstraints.FIRST_LINE_END; //This sets the anchor of the field, here we have told it to anchor the component to the top center of it's field
		FilterBuilderConstraints.insets = new Insets(0,25,0,0);
		FilterBuilderConstraints.gridx = 2;//Set the x coord of the cell of the layout we are describing
		FilterBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		//FilterBuilderConstraints.ipadx=1;
		add(comparatorLabel, FilterBuilderConstraints);//Actually add the "descriptionLabel" to the layout given the previous constraints
		//Set the constraints for the "comparator"  and add it to the view
		FilterBuilderConstraints.fill = GridBagConstraints.FIRST_LINE_START;//This sets the constraints of this field so that the item will stretch both horizontally and vertically to fill it's area
	//	FilterBuilderConstraints.weightx = 0.75;//This is the weight of this field, which tells the layout manager how big this field should be in proportion to the other components
		//FilterBuilderConstraints.insets = new Insets(10,5,0,0); //Set the padding; here, there will be 10 units of blank space padding on the top and 5 on the left side
		FilterBuilderConstraints.gridx = 3;//Set the x coord of the cell of the layout we are describing
	//	FilterBuilderConstraints.gridwidth = 2;   //Tell the layout that this field will fill 2 columns
		FilterBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		//FilterBuilderConstraints.ipadx=15;
		add(comparatorBox, FilterBuilderConstraints);//Actually add the "comparatorBox" to the layout given the previous constraints
		//end comparator

		//userfilter
		//Set the constraints for the "userfilterLabel" and add it to the view
		FilterBuilderConstraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		FilterBuilderConstraints.ipady = 0;//This tells the layout to reset the vertical ipad from the previously defined 20 units to now 0 units
	//	FilterBuilderConstraints.weightx = 0.25;//This is the weight of this field, which tells the layout manager how big this field should be in proportion to the other components
		FilterBuilderConstraints.anchor = GridBagConstraints.FIRST_LINE_START; //This sets the anchor of the field, here we have told it to anchor the component to the top center of it's field
		FilterBuilderConstraints.insets = new Insets(0,25,0,0);
		//FilterBuilderConstraints.insets = new Insets(10,0,0,0);  //Set the top padding to 10 units  of blank space
		FilterBuilderConstraints.gridx = 4;//Set the x coord of the cell of the layout we are describing
		FilterBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		//FilterBuilderConstraints.ipadx=1;
		add(userFilterLabel, FilterBuilderConstraints);//Actually add the "descriptionLabel" to the layout given the previous constraints
		//Set the constraints for the "userfilter"  and add it to the view
		FilterBuilderConstraints.fill = GridBagConstraints.FIRST_LINE_START;//This sets the constraints of this field so that the item will stretch both horizontally and vertically to fill it's area
	//	FilterBuilderConstraints.weightx = 0.75;//This is the weight of this field, which tells the layout manager how big this field should be in proportion to the other components
		//FilterBuilderConstraints.insets = new Insets(10,5,0,0); //Set the padding; here, there will be 10 units of blank space padding on the top and 5 on the left side
		FilterBuilderConstraints.gridx = 5;//Set the x coord of the cell of the layout we are describing
	//	FilterBuilderConstraints.gridwidth = 2;   //Tell the layout that this field will fill 2 columns
		FilterBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		//FilterBuilderConstraints.ipadx=15;
		add(userFilterBox, FilterBuilderConstraints);//Actually add the "userFilterBox" to the layout given the previous constraints
		//end userfilter

		//value:
		//Set the constraints for the "value" and add it to the view
		FilterBuilderConstraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		//FilterBuilderConstraints.weightx = 0.25;//This is the weight of this field, which tells the layout manager how big this field should be in proportion to the other components
		FilterBuilderConstraints.anchor = GridBagConstraints.FIRST_LINE_START; //This sets the anchor of the field, here we have told it to anchor the component to the top center of it's field
		FilterBuilderConstraints.insets = new Insets(0,25,0,0);
		//FilterBuilderConstraints.insets = new Insets(10,0,0,0);  //Set the top padding to 10 units  of blank space
		FilterBuilderConstraints.gridx = 6;//Set the x coord of the cell of the layout we are describing
		FilterBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		//FilterBuilderConstraints.ipadx=1;
		add(valueLabel, FilterBuilderConstraints);//Actually add the "releaseNumLabel" to the layout given the previous constraints
		//Set the constraints for the "value" and add it to the view
		FilterBuilderConstraints.fill = GridBagConstraints.FIRST_LINE_START;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		//FilterBuilderConstraints.weightx = 0.75;//This is the weight of this field, which tells the layout manager how big this field should be in proportion to the other components
		//FilterBuilderConstraints.insets = new Insets(10,5,0,0); //Set the padding; here, there will be 10 units of blank space padding on the top and 5 on the left side
		FilterBuilderConstraints.gridx = 7;//Set the x coord of the cell of the layout we are describing
	//	FilterBuilderConstraints.gridwidth = 2;   //Tell the layout that this field will fill 2 columns
		FilterBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		FilterBuilderConstraints.ipadx=80;
		add(txtValue, FilterBuilderConstraints);//Actually add the "txtValue" to the layout given the previous constraints
		//end value
		
		//Save button:
		//Set the constraints for the "Save" and add it to the view
		FilterBuilderConstraints.fill = GridBagConstraints.NONE;//This sets the constraints of this field so that the item will not stretch to fill it's area
		//FilterBuilderConstraints.weighty = 0.5;//This is the weight of this field, which tells the layout manager how big this field should be in proportion to the other components
		FilterBuilderConstraints.anchor = GridBagConstraints.FIRST_LINE_START; //This sets the anchor of the field, here we have told it to anchor the component to the bottom right of it's field
		//FilterBuilderConstraints.insets = new Insets(10,0,0,0);//Set the top padding to 10 units
		FilterBuilderConstraints.gridx = 8;//Set the x coord of the cell of the layout we are describing
		FilterBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		Save.setPreferredSize(new Dimension (15,20));
		add(Save, FilterBuilderConstraints);//Actually add the "Save" to the layout given the previous constraints
		//end Save button

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
	
	public JComboBox<String> getStatus() //?
	{
	    return userFilterBox;
	}
	
	public Mode getMode()
	{
	    return mode;
	}
	
	public void setMode(Mode editMode)
	{
	    mode = editMode;
	}
	
	public Filter getCurrentFilter()
	{
	    return currentFilter;
	}
	
	public void setCurrentFilter(Filter newFilter)
	{
	    currentFilter = newFilter;
	}

}

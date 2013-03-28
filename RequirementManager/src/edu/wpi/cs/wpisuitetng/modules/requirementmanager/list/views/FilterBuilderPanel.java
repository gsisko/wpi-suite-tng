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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter.SaveFilterController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.Filter;
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter.SaveFilterController;

/**
 * Panel to contain the filter builder for defect searching
 */
@SuppressWarnings("serial")
public class FilterBuilderPanel extends JPanel implements ActionListener{

	// enum to say whether or not you are creating
	public enum Mode {
		CREATE,
		EDIT
	}

	//the labels
	private final JLabel typeLabel; 
	private final JLabel comparatorLabel;
	private final JLabel valueLabel;

	//the fillable components
	private final JComboBox<String> typeBox;
	private final JComboBox<String> comparatorBox;
	private final JTextField txtValue;
	private final JComboBox<String> userFilterBox;

	//button
	private final JButton btnSave;
	
	private final ListPanel parent;
	private final FilterListPanel grandpa;
	
	private Filter currentFilter;
	private Mode currentMode;
	/**
	 * Construct the panel
	 */
	public FilterBuilderPanel(ListPanel view) {
		parent = view;
		grandpa = null;
		currentMode = Mode.CREATE;
		//create title
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBorder(BorderFactory.createTitledBorder("Filter Builder"));

		//construct the panels
		typeLabel = new JLabel("Type:");
		comparatorLabel = new JLabel("Comparator:");
		valueLabel = new JLabel("Value:");
		btnSave= new JButton("Create");

		//construct the components
		txtValue = new JTextField();
		txtValue.setEnabled(false);



		//create strings for the boxes
		String[] typeStrings = { "Id", "Name", "Description","Type", "Status","Priority","ReleaseNumber","Estimate","ActualEffort"};
		String[] comparatorStrings = {"GreaterThan","GreaterThanOrEqualTo","LessThan","LessThanOrEqualTo","EqualTo","NotEqualTo","Contains","DoesNotContain"};
		String[] userFilterStrings ={"Active","Inactive"};

		//construct the boxes
		typeBox = new JComboBox<String>(typeStrings);
		comparatorBox = new JComboBox<String>(comparatorStrings);
		userFilterBox = new JComboBox<String>(userFilterStrings);

		//set initial conditions
		typeBox.setSelectedIndex(0);
		typeBox.setEnabled(false);
		comparatorBox.setSelectedIndex(0);
		comparatorBox.setEnabled(false);
		userFilterBox.setSelectedIndex(0);
		userFilterBox.setEnabled(false);
		
		typeBox.addActionListener(this);

		btnSave.addActionListener(new SaveFilterController(parent.getParent()));
		btnSave.setEnabled(false);

		//set the layout
		setLayout(new GridBagLayout());
		GridBagConstraints FilterBuilderConstraints = new GridBagConstraints();
		FilterBuilderConstraints.anchor= GridBagConstraints.NORTH;

		//adjust location
		btnSave.setAlignmentX(Component.RIGHT_ALIGNMENT);

		//type
		//Set the constraints for the "typeLabel" and add it to the view
		FilterBuilderConstraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		FilterBuilderConstraints.ipady = 0;//This tells the layout to reset the vertical ipad from the previously defined 20 units to now 0 units
		FilterBuilderConstraints.anchor = GridBagConstraints.CENTER; //This sets the anchor of the field, here we have told it to anchor the component to the top center of it's field
		FilterBuilderConstraints.insets = new Insets(0,25,0,0);  //Set the top padding to 10 units  of blank space
		FilterBuilderConstraints.gridx = 0;//Set the x coord of the cell of the layout we are describing
		FilterBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		add(typeLabel, FilterBuilderConstraints);//Actually add the "typenLabel" to the layout given the previous constraints
		//Set the constraints for the "typeBox"  and add it to the view
		FilterBuilderConstraints.fill = GridBagConstraints.CENTER;//This sets the constraints of this field so that the item will stretch both horizontally and vertically to fill it's area
		FilterBuilderConstraints.gridx = 1;//Set the x coord of the cell of the layout we are describing
		FilterBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		add(typeBox, FilterBuilderConstraints);//Actually add the "typeBox" to the layout given the previous constraints
		//end Type

		//comparator
		//Set the constraints for the "comparatorLabel" and add it to the view
		FilterBuilderConstraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		FilterBuilderConstraints.ipady = 0;//This tell	s the layout to reset the vertical ipad from the previously defined 20 units to now 0 units
		FilterBuilderConstraints.anchor = GridBagConstraints.CENTER; //This sets the anchor of the field, here we have told it to anchor the component to the top center of it's field
		FilterBuilderConstraints.insets = new Insets(0,25,0,0);
		FilterBuilderConstraints.gridx = 2;//Set the x coord of the cell of the layout we are describing
		FilterBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		add(comparatorLabel, FilterBuilderConstraints);//Actually add the "comparatorLabel" to the layout given the previous constraints
		//Set the constraints for the "comparator"  and add it to the view
		FilterBuilderConstraints.fill = GridBagConstraints.CENTER;//This sets the constraints of this field so that the item will stretch both horizontally and vertically to fill it's area
		FilterBuilderConstraints.gridx = 3;//Set the x coord of the cell of the layout we are describing
		FilterBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		add(comparatorBox, FilterBuilderConstraints);//Actually add the "comparatorBox" to the layout given the previous constraints
		//end comparator

		//userfilter
		//Set the constraints for the "userfilter"  and add it to the view
		FilterBuilderConstraints.fill = GridBagConstraints.CENTER;//This sets the constraints of this field so that the item will stretch both horizontally and vertically to fill it's area
		FilterBuilderConstraints.gridx = 7;//Set the x coord of the cell of the layout we are describing
		FilterBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		add(userFilterBox, FilterBuilderConstraints);//Actually add the "userFilterBox" to the layout given the previous constraints
		//end userfilter

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
		//end value

		//Save button:
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
	public FilterListPanel getGrandpa() {
		return grandpa;
	}
	
	public void actionPerformed(ActionEvent e) {
		@SuppressWarnings("unchecked")
		JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
		
        String selected = (String) comboBox.getSelectedItem();
        System.out.println("Selected Item  = " + selected);
        
        String[] comparatorStrings = null;
        
        if(selected=="Id" ||selected=="ReleaseNumber" ||selected=="Estimate" ||selected=="ActualEffort" )
        	comparatorStrings = new String[]{"GreaterThan","GreaterThanOrEqualTo","LessThan","LessThanOrEqualTo","EqualTo","NotEqualTo"};
        else if(selected=="Name" ||selected=="Description" )
        	comparatorStrings = new String[]{"EqualTo","NotEqualTo","Contains","DoesNotContain"};
        else if(selected=="Type" ||selected=="Status"  ||selected=="Priority")
        	comparatorStrings = new String[]{"EqualTo","NotEqualTo"};
		
		
		
		DefaultComboBoxModel<String> cbm = new DefaultComboBoxModel<String>(comparatorStrings);
		comparatorBox.setModel(cbm);
		
	}
	
	/** Enables or disables all fields in the builder panel
	 * 
	 * @param setTo True activates the fields and false deactivates them
	 */
	public void setInputEnabled(boolean setTo){
	      this.getFilterType().setEnabled(setTo);
	      this.getFilterOperator().setEnabled(setTo);
	      this.getStatus().setEnabled(setTo);
	      this.getFilterValue().setEnabled(setTo);
	      this.getFilterValue().setText("");
	      this.getButton().setEnabled(setTo);
	      this.getButton().setText("Create");      
	}
	
	

}

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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.iteration;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.SaveModelController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.Filter;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.FilterType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.OperatorType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IBuilderPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.views.JNumberTextField;
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter.SaveFilterController;

/**
 * Panel to contain the filter builder for defect searching
 */
@SuppressWarnings("serial")
public class IterationBuilderPanel extends JPanel implements ActionListener, IBuilderPanel{

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
	private JTextField txtValue;
	private JNumberTextField txtNumValue;
	private final JComboBox<String> valueBox;
	private final JComboBox<String> userFilterBox;

	//button
	private final JButton btnSave;

	private final ListPanel parent;

	private Filter currentFilter;
	
	/** EDIT or CREATE mode */
	private Mode currentMode;

	private String curType = "Id";
	
	/** Keeps track of active/inactive state of builder */
	private boolean isBuilderActive = false;
	
	private SaveModelController saveController;

	/**
	 * Construct the panel
	 */
	public IterationBuilderPanel(ListPanel view) {
		parent = view;
		currentMode = Mode.CREATE;
		//create title
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBorder(BorderFactory.createTitledBorder("Iteration Builder"));

		//construct the panels
		typeLabel = new JLabel("Type:");
		comparatorLabel = new JLabel("Operator:");
		valueLabel = new JLabel("Value:");
		btnSave= new JButton("Create");

		//construct the components
		txtValue = new JTextField();
		txtValue.setEnabled(false);
		txtValue.setVisible(false);
		
		//construct the Number Text Field
		txtNumValue = new JNumberTextField();
		txtNumValue.setEnabled(false);
		txtNumValue.setAllowNegative(false);


		//create strings for the boxes
		String[] typeStrings = { "Id", "Name", "Description","Type", "Status","Priority","ReleaseNumber","Estimate","ActualEffort"};
		String[] comparatorStrings = {"=", "!=", ">","<",">=","<=","Contains","DoesNotContain"};
		String[] userFilterStrings ={"Active","Inactive"};

		//construct the boxes
		typeBox = new JComboBox<String>(typeStrings);
		comparatorBox = new JComboBox<String>(comparatorStrings);
		userFilterBox = new JComboBox<String>(userFilterStrings);
		valueBox = new JComboBox<String>();
		valueBox.setVisible(false);

		//set initial conditions
		typeBox.setSelectedIndex(0);
		typeBox.setEnabled(false);
		comparatorBox.setSelectedIndex(0);
		comparatorBox.setEnabled(false);
		userFilterBox.setSelectedIndex(0);
		userFilterBox.setEnabled(false);

		// The action listener for this is below
		typeBox.addActionListener(this);

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
		add(valueBox, FilterBuilderConstraints);//Actually add the "valueBox" to the layout given the previous constraints
		add(txtNumValue, FilterBuilderConstraints);//Actually add the "txtNumValue" to the layout given the previous constraints
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
		
		currentFilter = new Filter();
	}
	
	public void setUp() {
		saveController = new SaveModelController(parent.getFilterPanel(),this,"filter");
		btnSave.addActionListener(saveController);
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

	public JNumberTextField getFilterNumValue()
	{
		return txtNumValue;
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

	/** Watches for changes in the "FilterType JCombo box
	 *  and updates the comparator drop down box to only
	 *  all the user to pick valid operators
	 * 
	 * @param e The input resulting from the action
	 */
	public void actionPerformed(ActionEvent e) {
	
		@SuppressWarnings("unchecked")
		JComboBox<String> comboBox = (JComboBox<String>) e.getSource();

		String selected = (String) comboBox.getSelectedItem();

		String[] comparatorStrings = null;
		String[] valueStrings = null;

		// Limit the options for comparators by the FilterType
		if(selected=="Id" ||selected=="ReleaseNumber" ||selected=="Estimate" ||selected=="ActualEffort" )
			comparatorStrings = new String[]{"=", "!=", ">","<",">=","<=",};
		else if(selected=="Name" ||selected=="Description" )
			comparatorStrings = new String[]{"=","!=","Contains","DoesNotContain"};
		else if(selected=="Type" ||selected=="Status"  ||selected=="Priority"){
			comparatorStrings = new String[]{"=","!="};
			if(selected=="Type" )
				valueStrings=new String[]{"","Epic","Theme","UserStory","NonFunctional","Scenario"};
			if(selected=="Status" )
				valueStrings=new String[]{"New","InProgress","Open","Complete","Deleted"};
			if(selected=="Priority")
				valueStrings=new String[]{"","High","Medium","Low"};
			DefaultComboBoxModel<String> valb = new DefaultComboBoxModel<String>(valueStrings);
			valueBox.setModel(valb);
		}

		DefaultComboBoxModel<String> compbox = new DefaultComboBoxModel<String>(comparatorStrings);
		comparatorBox.setModel(compbox);

		if(selected=="Type" ||selected=="Status"  ||selected=="Priority"){
			txtValue.setVisible(false);
			txtNumValue.setVisible(false);
			valueBox.setVisible(true);
		}
		else if(selected=="Name" ||selected=="Description"){
			valueBox.setVisible(false);
			txtNumValue.setVisible(false);
			txtValue.setVisible(true);
		}
		else{ // id, estimate, actual value, or release number
			valueBox.setVisible(false);
			txtValue.setVisible(false);
			txtNumValue.setVisible(true);
		}
		// reset values 
		this.getFilterValue().setText("");
		this.getFilterNumValue().setText("");
		
		curType = selected;
	}


	/** Enables or disables all fields in the builder panel. Not intended for
	 *  use by controllers trying to load in data to the FilterBuilderPanel
	 * 
	 * @param setTo True activates the fields and false deactivates them
	 */
	public void setInputEnabled(boolean setTo){
		isBuilderActive = setTo;
		
		// Reset the JCombo boxes
		this.getFilterOperator().setSelectedIndex(0);
		this.getFilterType().setSelectedIndex(0);
		this.getStatus().setSelectedIndex(0);	    
		
		// Enable/Disable
		this.getFilterType().setEnabled(setTo);
		this.getFilterOperator().setEnabled(setTo);
		this.getStatus().setEnabled(setTo);
		this.getFilterValue().setEnabled(setTo);
		this.getFilterNumValue().setEnabled(setTo);
		
		this.getFilterValueBox().setEnabled(setTo);
		this.getFilterValue().setText("");
		this.getFilterNumValue().setText("");
				
		this.getButton().setEnabled(setTo);

		// Reset value field
		this.getFilterValue().setText("");

		// Ensure that the button is set correctly
		this.getButton().setText("Create");   
		
		valueBox.setVisible(false);
		txtValue.setVisible(false);
		txtNumValue.setVisible(true);
	}
	

	/** Sets clears and resets any fields in the current builder panel
	 *  and also resets the mode to "CREATE" if applicable. 
	 */
	public void clearAndReset() {
		currentMode = Mode.CREATE; // default for this function
		setInputEnabled(false);
		isBuilderActive = false;
	}
	
	/** Toggles between active and inactive modes mode */
	public void toggleNewCancelMode() {
		currentMode = Mode.CREATE; // default for this function
		isBuilderActive = !isBuilderActive;
		setInputEnabled(isBuilderActive);
		this.getStatus().setEnabled(false);
	}

	/** Gets the model from the panel in the form of a JSON string
	 *  that is ready to be sent as a message over the network
	 * 
	 * *NOTE: can be used for passing messages between views!
	 * 
	 * @return JSON string of the model to be sent
	 */
	public String getModelMessage() {
		String curtype = this.getFilterType().getSelectedItem().toString();
    	if (curtype != "Type" 
    			&& curtype != "Status" 
    			&& curtype != "Priority" 
    			&& 	this.getFilterValue().getText().length() == 0   
    			&&  this.getFilterNumValue().getText().length() == 0) {
    		
    		
    		JOptionPane.showMessageDialog(null, "Value cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
    		return null;
    	}
    	
		Filter newFilter = new Filter();
		
		if (this.getCurrentMode() == Mode.EDIT) newFilter.setUniqueID(currentFilter.getUniqueID());
		FilterType type = FilterType.toType(this.getFilterType().getSelectedItem().toString());
		newFilter.setType(type);
		newFilter.setComparator(OperatorType.toType(this.getFilterOperator().getSelectedItem().toString()));
		
		if(type == FilterType.toType("Type")||type == FilterType.toType("Status")||type == FilterType.toType("Priority"))
			newFilter.setValue(this.getFilterValueBox().getSelectedItem().toString());
		else if (type == FilterType.toType("Name") || type == FilterType.toType("Description"))
			newFilter.setValue(this.getFilterValue().getText());
		else
			newFilter.setValue(this.getFilterNumValue().getText());
		
		if(this.getStatus().getSelectedIndex() == 1)
			newFilter.setUseFilter(false);
		else
			newFilter.setUseFilter(true);
		
		return newFilter.toJSON();
		
	}

	
	/** Takes a JSON string that holds an array of models and uploads them
	 *  to the builder panel. Also sets the modes
	 *  
	 * @param jsonArray An array of models in JSON string form
	 */
	public void translateAndDisplayModel(String jsonString) {
		isBuilderActive = true;
		setInputEnabled(true);
		
		Filter filter = Filter.fromJSONArray(jsonString)[0];
		//Set edit mode
		this.setCurrentMode(Mode.EDIT);
		this.getButton().setText("Update");

		//Type
		this.getFilterType().setSelectedItem(filter.getType().toString());
		this.getFilterType().setEnabled(true);

		//Comparator
		this.getFilterOperator().setSelectedItem(filter.getComparator().toString());
		this.getFilterOperator().setEnabled(true);

		//Value
		this.getFilterValue().setText(filter.getValue());
		this.getFilterValue().setEnabled(true);

		//NumValue
		this.getFilterNumValue().setText(filter.getValue());
		this.getFilterNumValue().setEnabled(true);
		
		//Value?
		this.getFilterValueBox().setSelectedItem(filter.getValue());
		this.getFilterValueBox().setEnabled(true);

		//Active
		if(filter.isUseFilter()){
			this.getStatus().setSelectedIndex(0);
		} else{
			this.getStatus().setSelectedIndex(1);
		}
		this.getStatus().setEnabled(true);

		//Set current filter to the one retrieved
		this.setCurrentFilter(filter);
	}
	
	

}

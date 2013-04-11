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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
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
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListTab;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.views.JNumberTextField;

/** This is the builder panel for Filters. It is located in the list view on the 
 *  RequirementManager module above the list of requirements and right of the list 
 *  of filters. This builder will be switched to when the Filter list view
 *  tab is selected.  */
@SuppressWarnings({"serial","rawtypes","unchecked"})
public class FilterBuilderPanel extends JPanel implements ActionListener, IBuilderPanel {

	/** The "Type" label. Located before the FilterType drop down menu */
	private final JLabel typeLabel; 
	/** The "Operator" label. Located before the FilterOperator drop down menu */
	private final JLabel operatorLabel;
	/** The "Value" label. Located before the FilterValue box/drop down menu */
	private final JLabel valueLabel;

	/** The filter type drop down menu. Set with all defined filter types */
	private final JComboBox typeBox;
	/** The filter operator drop down menu. Set with all defined filter operators applicable to the current type*/
	private final JComboBox operatorBox;
	/** The filter status drop down menu. Options include "Active" and "Inactive" in a drop down menu*/
	private final JComboBox statusBox;

	/** Regular text field that is active only for certain filter types*/
	private JTextField txtValue;
	/** Number text field that is active only for certain filter types*/
	private JNumberTextField numValue;
	/** Drop down menu that is active only for certain filter types and filled only with applicable options*/
	private final JComboBox valueBox;

	/** The save/create button */
	private final JButton btnSave;

	/** The "parent" that this panel lives in */
	private final ListTab parent;

	/** The filter that that is being edited or created currently  */
	private Filter currentFilter;

	/** EDIT or CREATE mode */
	private Mode currentMode;

	/** The current type of filter that is being built */
	private String curType = "Id";
	/** Keeps track of active/inactive state of builder */
	private boolean isBuilderActive = false;
	
	/** This controller is activated when the save button is pressed */
	private SaveModelController saveController;

	/** Construct the panel and all of its components
	 *
	 * @param view The ListTab that this panel will live in
	 */
	public FilterBuilderPanel(ListTab view) {
		parent = view;
		currentMode = Mode.CREATE;

		//Add a titled boarder to this panel
		setBorder(BorderFactory.createTitledBorder("Filter Builder"));

		//construct the panels
		typeLabel = new JLabel("Type:");
		operatorLabel = new JLabel("Operator:");
		valueLabel = new JLabel("Value:");
		btnSave= new JButton("Create");

		//construct the components
		txtValue = new JTextField();
		enable(txtValue, true);
		txtValue.setVisible(false);

		//construct the Number Text Field
		numValue = new JNumberTextField();
		enable(numValue, false);
		numValue.setAllowNegative(false);


		//create strings for the boxes
		String[] typeStrings = { "Id", "Name", "Description","Type", "Status","Priority","Iteration","ReleaseNumber","Estimate","ActualEffort"};
		String[] comparatorStrings = {"=", "!=", ">","<",">=","<="};
		String[] userFilterStrings ={"Active","Inactive"};

		//construct the boxes
		typeBox = new JComboBox(typeStrings);
		operatorBox = new JComboBox(comparatorStrings);
		statusBox = new JComboBox(userFilterStrings);
		valueBox = new JComboBox();
		valueBox.setVisible(false);

		//set initial conditions
		typeBox.setSelectedIndex(0);
		enable(typeBox, false);
		operatorBox.setSelectedIndex(0);
		enable(operatorBox, false);
		statusBox.setSelectedIndex(0);
		enable(statusBox, false);

		// The action listener for this is below
		typeBox.addActionListener(this);

		btnSave.setEnabled(false);

		//set the layout
		setLayout(new GridBagLayout());
		GridBagConstraints FilterBuilderConstraints = new GridBagConstraints();

		btnSave.setPreferredSize(new Dimension (100,30));

		//type
		//Set the constraints for the "typeLabel" and add it to the view
		FilterBuilderConstraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		FilterBuilderConstraints.anchor = GridBagConstraints.LINE_END; //This sets the anchor of the field, here we have told it to anchor the component to the center right of it's field
		FilterBuilderConstraints.insets = new Insets(10,10,10,0); //Set the top padding to 10 units of blank space, set left padding to 10 units, bottom padding to 10 units
		FilterBuilderConstraints.gridx = 0;//Set the x coord of the cell of the layout we are describing
		FilterBuilderConstraints.gridy = 0;//Set the y coord of the cell of the layout we are describing
		add(typeLabel, FilterBuilderConstraints);//Actually add the "typenLabel" to the layout given the previous constraints
		//Set the constraints for the "typeBox"  and add it to the view
		FilterBuilderConstraints.anchor = GridBagConstraints.LINE_START;//This sets the anchor of the field, here we have told it to anchor the component to the center left of it's field
		FilterBuilderConstraints.insets = new Insets(10,10,10,25); //Set the top padding to 10 units of blank space, set left padding to 10 units, bottom padding to 10 units, right padding to 25 units
		FilterBuilderConstraints.gridx = 1;
		add(typeBox, FilterBuilderConstraints);//Actually add the "typeBox" to the layout given the previous constraints
		//end Type

		//comparator
		//Set the constraints for the "operatorLabel" and add it to the view
		FilterBuilderConstraints.anchor = GridBagConstraints.LINE_END;
		FilterBuilderConstraints.insets = new Insets(10,10,10,0);

		FilterBuilderConstraints.gridx = 2;
		FilterBuilderConstraints.gridy = 0;
		add(operatorLabel, FilterBuilderConstraints);//Actually add the "operatorLabel" to the layout given the previous constraints
		//Set the constraints for the "comparator"  and add it to the view
		FilterBuilderConstraints.anchor = GridBagConstraints.LINE_START;
		FilterBuilderConstraints.insets = new Insets(10,10,10,25);
		FilterBuilderConstraints.gridx = 3;
		FilterBuilderConstraints.gridy = 0;
		add(operatorBox, FilterBuilderConstraints);//Actually add the "operatorBox" to the layout given the previous constraints
		//end comparator



		//value:
		//Set the constraints for the "value" and add it to the view
		FilterBuilderConstraints.anchor = GridBagConstraints.LINE_END;
		FilterBuilderConstraints.insets = new Insets(10,10,10,0);
		FilterBuilderConstraints.gridx = 4;
		FilterBuilderConstraints.gridy = 0;
		add(valueLabel, FilterBuilderConstraints);//Actually add the "valueLabel" to the layout given the previous constraints
		//Set the constraints for the "value" and add it to the view
		FilterBuilderConstraints.anchor = GridBagConstraints.LINE_START;
		FilterBuilderConstraints.insets = new Insets(10,10,10,25);
		FilterBuilderConstraints.ipadx=80;//pad this field horizontally by 80 units
		FilterBuilderConstraints.gridx = 5;
		FilterBuilderConstraints.gridy = 0;
		add(txtValue, FilterBuilderConstraints);//Actually add the "txtValue" to the layout given the previous constraints
		add(numValue, FilterBuilderConstraints);//Actually add the "numValue" to the layout given the previous constraints
		FilterBuilderConstraints.ipadx=5;//pad this field horizontally by 5 units
		add(valueBox, FilterBuilderConstraints);//Actually add the "valueBox" to the layout given the previous constraints
		//end value

		//userfilter
		//Set the constraints for the "userfilter"  and add it to the view
		FilterBuilderConstraints.anchor = GridBagConstraints.LINE_END;
		FilterBuilderConstraints.insets = new Insets(10,10,10,40);
		FilterBuilderConstraints.ipadx=0;//This resets the horizontal padding from the previously defined 5 units back to 0 units
		FilterBuilderConstraints.gridx = 6;
		FilterBuilderConstraints.gridy = 0;
		add(statusBox, FilterBuilderConstraints);//Actually add the "userFilterBox" to the layout given the previous constraints
		//end userfilter

		//Save button:
		//Set the constraints for the "Save" and add it to the view
		FilterBuilderConstraints.fill = GridBagConstraints.NONE;//This sets the constraints of this field so that the item will not stretch to fill it's area
		FilterBuilderConstraints.anchor = GridBagConstraints.LINE_END;
		FilterBuilderConstraints.gridx = 7;//Set the x coord of the cell of the layout we are describing
		FilterBuilderConstraints.gridy = 0;//Set the y coord of the cell of the layout we are describing
		add(btnSave, FilterBuilderConstraints);//Actually add the "Save" to the layout given the previous constraints
		//end Save button

		currentFilter = new Filter();
		 setCurType("Id");
	}

	/** Watches the "Type" box for changes and sets up the "value" field
	 *  to be a number box when numbers are expected, a drop down when the 
	 *  options are finite (enumerators), and a string for the rest of the time.
	 *  Also sets up the operator boxes in a similar fashion This reduces the
	 *  possibility of user error.	 */
	public void actionPerformed(ActionEvent e) {

		JComboBox comboBox = (JComboBox) e.getSource();

		String selected = (String) comboBox.getSelectedItem();

		String[] comparatorStrings = null;
		String[] valueStrings = null;

		// Limit the options for comparators by the FilterType
		if(selected=="Id" ||selected=="ReleaseNumber" ||selected=="Estimate" ||selected=="ActualEffort" )
			comparatorStrings = new String[]{"=", "!=", ">","<",">=","<=",};
		else if(selected=="Name" ||selected=="Description" )
			comparatorStrings = new String[]{"=","!=","Contains","DoesNotContain"};

		// This section is for enumerators, which need specific operators and values
		else if(selected=="Type" ||selected=="Status"  ||selected=="Priority"||selected=="Iteration"){
			comparatorStrings = new String[]{"=","!="};
			if(selected=="Type" )
				valueStrings=new String[]{"","Epic","Theme","UserStory","NonFunctional","Scenario"};
			if(selected=="Status" )
				valueStrings=new String[]{"New","InProgress","Open","Complete","Deleted"};
			if(selected=="Priority")
				valueStrings=new String[]{"","High","Medium","Low"};
			String[] IterationArr = {"Backlog"};
			if(selected=="Iteration") {
				IterationArr = getIterationNames();
				valueStrings = IterationArr;
			}
			DefaultComboBoxModel  valb = new DefaultComboBoxModel (valueStrings);
			valueBox.setModel(valb);
		}

		DefaultComboBoxModel  compbox = new DefaultComboBoxModel (comparatorStrings);
		operatorBox.setModel(compbox);

		// Sets the visibility of the appropriate value boxes so that the correct one is used after
		// resetting all of them
		txtValue.setVisible(false);
		numValue.setVisible(false);
		valueBox.setVisible(false);

		if(selected=="Type" ||selected=="Status"  ||selected=="Priority" || selected == "Iteration")
			valueBox.setVisible(true);
		else if(selected=="Name" ||selected=="Description")
			txtValue.setVisible(true);
		else // id, estimate, actual value, or release number
			numValue.setVisible(true);

		// reset values 
		this.getFilterValue().setText("");
		this.getFilterNumValue().setText("");

		// Save the value of the current filter type selected
		setCurType(selected);
	}

	public JButton getButton()
	{
		return btnSave;
	}

	public JComboBox getFilterType()
	{
		return typeBox;
	}

	public JComboBox getFilterOperator()
	{
		return operatorBox;
	}

	public JTextField getFilterValue()
	{
		return txtValue;
	}

	public JNumberTextField getFilterNumValue()
	{
		return numValue;
	}

	public JComboBox getFilterValueBox()
	{
		return valueBox;
	}


	public JComboBox getStatus()
	{
		return statusBox;
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

	/** Takes a JSON string that holds an array of models and uploads them
	 *  to the builder panel. Also sets the modes
	 *  
	 * @param jsonString An array of models in JSON string form
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
		enable(this.getFilterType(), true);

		//Comparator
		this.getFilterOperator().setSelectedItem(filter.getComparator().toString());
		enable(this.getFilterOperator(),true);

		//Value
		this.getFilterValue().setText(filter.getValue());
		enable(this.getFilterValue(), true);

		//NumValue
		this.getFilterNumValue().setText(filter.getValue());
		enable(this.getFilterNumValue(), true);

		//Value
		if (filter.getType().toString().equals("Iteration")) {
			Iteration[] allIterations = this.parent.getParent().getAllIterations();
			for (Iteration i : allIterations) {
				if (filter.getValue().equals(i.getID() + ""))
					this.getFilterValueBox().setSelectedItem(i.getName());
			}
		}
		else this.getFilterValueBox().setSelectedItem(filter.getValue());
		enable(this.getFilterValueBox(), true);

		//Active
		if(filter.isUseFilter()){
			this.getStatus().setSelectedIndex(0);
		} else{
			this.getStatus().setSelectedIndex(1);
		}
		enable(this.getStatus(), true);

		//Set current filter to the one retrieved
		this.setCurrentFilter(filter);
	}

	/** Set the given box to the given enable status as well 
	 *  set the box to the correct color
	 * 
	 * @param box   The box that needs enabling and colors
	 * @param enabled  True to enable and False to disable
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

	/** Set the given box to the given enable status as well 
	 *  set the box to the correct color
	 * 
	 * @param box   The box that needs enabling and colors
	 * @param enabled  True to enable and False to disable
	 */
	public void enable(JTextField box, boolean enabled) {
		if (enabled) {
			box.setEnabled(true);
			box.setBackground(Color.WHITE);
		}
		else {
			box.setEnabled(false);
			box.setBackground(new Color(238,238,238));
		}
	}
	
	/** Set the given box to the given enable status as well 
	 *  set the box to the correct color
	 * 
	 * @param box   The box that needs enabling and colors
	 * @param enabled  True to enable and False to disable
	 */
	public void enable(JNumberTextField box, boolean enabled) {
		if (enabled) {
			box.setEnabled(true);
			box.setBackground(Color.WHITE);
		}
		else {
			box.setEnabled(false);
			box.setBackground(new Color(238,238,238));
		}
	}


	/** Sets up the controllers and action listeners. This should be where all
	 *  controllers and action listeners are initialized because the controllers
	 *  require references that are not not fully initialized when the 
	 *  constructor for this class is called.
	 */
	public void setupControllersAndListeners() {
		saveController = new SaveModelController(parent.getTabPanel().getFilterList(),this,"filter");
		btnSave.addActionListener(saveController);
	}

	/** Sets the mode of the builder panel to the given mode. ALSO changes
	 *  the text in the button 
	 *  Mode.CREATE or Mode.EDIT
	 * 
	 * @param mode The mode that the builder panel should be in
	 */
	public void setModeAndBtn(Mode mode) {
		currentMode = mode;
		// Ensure that the button text is set correctly
		if (currentMode == Mode.CREATE)
			btnSave.setText("Create");
		else
			btnSave.setText("Save");
	}


	/** Enables or disables all fields in the builder panel. 
	 * 
	 * @param setTo True activates the fields and false deactivates them
	 */
	public void setInputEnabled(boolean setTo){
		// Record whether enabled/disabled
		isBuilderActive = setTo;

		// Enable/Disable
		enable(this.getFilterType(), setTo);
		enable(this.getFilterOperator(), setTo);
		enable(this.getStatus(), setTo);
		enable(this.getFilterValue(), setTo);
		enable(this.getFilterNumValue(), setTo);
		enable(this.getFilterValueBox(), setTo);

		// Enable the button		
		this.getButton().setEnabled(setTo);
	}

	/** Toggles between active and inactive modes mode */
	public void toggleNewCancelMode() {
		currentMode = Mode.CREATE; // default for this function
		isBuilderActive = !isBuilderActive;
		setInputEnabled(isBuilderActive);
		enable(this.getStatus(),false);
	}

	/** Resets the values of the fields/drop downs in the builder panel  */
	public void resetFields() {
		// Reset Type field
		typeBox.setSelectedIndex(0);
		//Reset Operator field
		operatorBox.setSelectedIndex(0);

		// Reset all layers of the value field
		txtValue.setText("");
		numValue.setText("");

		// Reset status field
		statusBox.setSelectedIndex(0);

		//Reset buttons
		btnSave.setText("Create");
		this.revalidate();
		this.repaint();
	}
	
	/** Gets the model from the panel in the form of a JSON string
	 *  that is ready to be sent as a message over the network
	 * 
	 * *NOTE: can be used for passing messages between views!
	 * 
	 * @return JSON string of the model to be sent, null if a message cannot be made
	 */
	public String convertCurrentModelToJSON() {
		setCurType(this.getFilterType().getSelectedItem().toString());
		// Check conditions that verify that the value field has something
		if (getCurType() != "Type" && getCurType() != "Status" && getCurType() != "Priority" && getCurType() != "Iteration"
				&& 	this.getFilterValue().getText().length() == 0   
				&&  this.getFilterNumValue().getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "Value cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		// Instantiate a filter to hold the data
		Filter newFilter = new Filter();

		// If the current model is being edited, the unique ID needs to be preserved
		if (this.getCurrentMode() == Mode.EDIT) newFilter.setUniqueID(currentFilter.getUniqueID());

		// Set the fields.
		newFilter.setComparator(OperatorType.toType(this.getFilterOperator().getSelectedItem().toString()));
		FilterType type = FilterType.toType(this.getFilterType().getSelectedItem().toString());
		newFilter.setType(type);

		// The selected type determines how the filter value should be obtained
		if(type == FilterType.toType("Type")||type == FilterType.toType("Status")||type == FilterType.toType("Priority"))
			newFilter.setValue(this.getFilterValueBox().getSelectedItem().toString());
		else if (type == FilterType.toType("Name") || type == FilterType.toType("Description"))
			newFilter.setValue(this.getFilterValue().getText());
		else if (type == FilterType.toType("Iteration")) {
			String chosen = this.getFilterValueBox().getSelectedItem().toString();
			Iteration[] allIterations = this.parent.getParent().getAllIterations();
			for (int i = 0; i < allIterations.length; i++) {
				if (chosen.equals(allIterations[i].getName()))
					newFilter.setValue(allIterations[i].getID() + "");
			}
		}
		else
			newFilter.setValue(this.getFilterNumValue().getText());

		// The status drop down box has two values, index 1 = inactive and index 0 = active
		if(this.getStatus().getSelectedIndex() == 1)
			newFilter.setUseFilter(false);
		else
			newFilter.setUseFilter(true);

		// Convert the filter to a JSON string and send it away
		return newFilter.toJSON();

	}

	/** Takes a JSON string that holds an array of models and uploads them
	 *  to the builder panel. 
	 *  
	 * @param jsonString An array of models in JSON string form
	 */
	public void displayModelFromJSONArray(String jsonString) {
		// Translate the filter from a JSONArray	
		Filter filter = Filter.fromJSONArray(jsonString)[0];

		// Store the filter in the builder
		currentFilter = filter;

		//Type
		typeBox.setSelectedItem(filter.getType().toString());

		//Comparator
		operatorBox.setSelectedItem(filter.getComparator().toString());

		//Value
		txtValue.setText(filter.getValue());

		//NumValue
		numValue.setText(filter.getValue());

		//Value box
		if (filter.getType().equals(FilterType.Iteration)) {
			Iteration[] allIterations = this.parent.getParent().getAllIterations();
			for (int i = 0; i < allIterations.length; i++) {
				if (filter.getValue().equals(allIterations[i].getID() + ""))
					valueBox.setSelectedItem(allIterations[i].getName());
			}
		}
		else valueBox.setSelectedItem(filter.getValue());


		//Active
		if(filter.isUseFilter()){
			this.getStatus().setSelectedIndex(0);
		} else{
			this.getStatus().setSelectedIndex(1);
		}

		this.revalidate();
		this.repaint();
	}

	/*** Get a list of string iteration names
	 * 
	 * @return an array of strings that represent the names of the iterations
	 */
	public String[] getIterationNames(){
		Iteration[] allIterations = this.parent.getParent().getAllIterations();
		String[] names = new String[allIterations.length];
		for (int i = 0; i < allIterations.length; ++i) {
			names[i] = (allIterations[i].getName());
		}

		return names;
	}

	/** Gets the type of the current filter
	 * @return the curType
	 */
	public String getCurType() {
		return curType;
	}

	/** Sets the type of the current filter
	 * @param curType the curType to set
	 */
	public void setCurType(String curType) {
		this.curType = curType;
	}


}
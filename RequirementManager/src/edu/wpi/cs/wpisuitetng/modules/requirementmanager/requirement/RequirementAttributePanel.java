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
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementTab.Mode;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.listeners.BoxChangeListener;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.listeners.FieldChangeListener;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.listeners.IterationChangeListener;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.listeners.ValidNameDescriptionListener;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.views.JNumberTextField;

/** This panel holds the fields necessary for making a Requirement, as well as 
 *  the controllers necessary for saving, retrieving, viewing etc.
 */
@SuppressWarnings({"serial","rawtypes","unchecked"})
public class RequirementAttributePanel extends JPanel {

	//The labels
	private  JLabel nameLabel; //The label for the name text field ("txtName")
	private  JLabel descriptionLabel;//The label for the description text area ("txtDescription")
	private  JLabel typeLabel; //The label for the type combo box ("typeBox")
	private  JLabel statusLabel; //The label for the status combo box ("statusBox")
	private  JLabel priorityLabel; //The label for the priority combo box ("priorityBox")
	private  JLabel iterationLabel; //The label for the iteration combo box ("iterationBox")
	private  JLabel releaseNumLabel; //The label for the release number text field ("txtReleaseNum")
	private  JLabel estimateLabel; //The label for the estimate text field ("txtEstimate")
	private  JLabel actualEffortLabel; //The label for the actual effort text field ("txtActualEffort")
	private  JLabel warningName;
	private  JLabel warningDescription;
	//The fillable components
	private  JTextField txtName;//The name text field 
	private  JTextArea txtDescription;//The description text area
	private  JComboBox typeBox;//The type combo box
	private  JComboBox statusBox;//The status combo box
	private  JComboBox priorityBox;//The priority combo box
	private  JComboBox iterationBox;//The iteration combo box
	private  JTextField txtReleaseNumber;//The release number text field
	private  JNumberTextField txtEstimate;//The estimate text field
	private  JNumberTextField txtActualEffort;//The actual effort text field

	private Requirement currentRequirement;//Stores the requirement currently open for editing or creation
	private RequirementTab parent; //Stores the RequirementTab that contains the panel
	protected boolean inputEnabled;//A boolean indicating if input is enabled on the form 
	private Mode mode;// The variable to store the enum indicating whether or not you are creating at the time
	private boolean[] fieldsChanged;	// Have any fields been changed?
	private JButton saveButton;
	private Boolean validNameAndDescription;


	// Listeners
	ValidNameDescriptionListener nameAndDescriptionValidityListener;




	//The layout manager
	protected GridBagLayout layout; //The layout for the inner panel ("innerPanel")

	String[] iterationArr = { "Backlog"};
	String[] iterationStrings = iterationArr;

	//The constraints
	private GridBagConstraints attributePanelConstraints;//The constraints variable for the layout of the innerPanel

	public RequirementAttributePanel(RequirementTab parentTab, Requirement requirement, Mode editMode){

		parent = parentTab;//Set the RequirementView that contains this instance of this panel
		currentRequirement = requirement; //Set the requirement that is currently open for editing or creation
		mode = editMode;//Set the indicated mode

		// Indicate that input is enabled
		inputEnabled = true;
		fieldsChanged = new boolean[10];
		setFieldsChanged(false);

		validNameAndDescription = new Boolean(true);

		//Create and set the layout manager that controls the positions of the components
		layout = new GridBagLayout();//Create the layout
		setLayout(layout); //Set the layout

		//add a border of blank space
		setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30)); 

		addComponents();//Add the components to the panel
		populateFields();//Update the fields to those given in the currentRequirement, if necessary.

	}

	/**
	 * Adds the components to both the innerPanel and 
	 * this instance of a RequirementPanel and places
	 * constraints on the components within the innerPanel
	 * for the GridBagLayout.
	 */
	protected void addComponents(){
		//Construct the labels
		nameLabel = new JLabel("Name:");
		descriptionLabel = new JLabel("Description:");
		typeLabel = new JLabel("Type:");
		statusLabel = new JLabel("Status:");
		priorityLabel = new JLabel("Priority:");
		iterationLabel = new JLabel("Iteration:");
		releaseNumLabel = new JLabel("ReleaseNumber:");
		estimateLabel = new JLabel("Estimate:");
		actualEffortLabel = new JLabel("ActualEffort:");
		warningName = new JLabel("Name must be between 0 and 100 characters");
		warningDescription = new JLabel("Description cannot be blank");

		//Construct the misc. components
		txtName = new JTextField("");
		txtDescription = new JTextArea("", 2, 2);
		txtReleaseNumber = new JTextField("");
		txtEstimate = new JNumberTextField();
		txtActualEffort = new JNumberTextField();
		txtReleaseNumber.setMaximumSize(new Dimension(34, 25));
		txtReleaseNumber.setMinimumSize(new Dimension(34, 25));
		txtReleaseNumber.setPreferredSize(new Dimension(34, 25));
		txtEstimate.setAllowNegative(false);
		txtEstimate.setMaximumSize(new Dimension(30, 25));
		txtEstimate.setMinimumSize(new Dimension(30, 25));
		txtEstimate.setPreferredSize(new Dimension(30, 25));
		txtActualEffort.setAllowNegative(false);
		txtActualEffort.setMaximumSize(new Dimension(30, 25));
		txtActualEffort.setMinimumSize(new Dimension(30, 25));
		txtActualEffort.setPreferredSize(new Dimension(30, 25));

		//Set the character limit for the release number
		txtReleaseNumber.setDocument(new JTextFieldLimit(12));

		//Set the txtDescription component to wrap
		txtDescription.setLineWrap(true);
		txtDescription.setWrapStyleWord(true);

		//Create the strings for the boxes
		String[] typeStrings = { "", "Epic", "Theme", "UserStory", "NonFunctional", "Scenario" };
		String[] statusStrings = { "New", "InProgress", "Open", "Complete", "Deleted" };
		String[] priorityStrings = { "", "High", "Medium", "Low"};

		//iterationArr = getIterationNamesCr();
		iterationStrings = iterationArr;

		//Construct the boxes 
		typeBox = new JComboBox(typeStrings);
		statusBox = new JComboBox(statusStrings);
		priorityBox = new JComboBox(priorityStrings);
		iterationBox = new JComboBox(iterationStrings);

		//Set the color for the warning labels
		warningName.setForeground(Color.red);
		warningDescription.setForeground(Color.red);

		//Set the sizes for the warning labels
		warningName.setPreferredSize(new Dimension(410, 10));
		warningDescription.setPreferredSize(new Dimension(270, 10));

		//Set the estimate and actual effort to 0 since this is a new requirement
		txtEstimate.setText("0");
		txtActualEffort.setText("0");

		//Set the initial selections for the boxes
		typeBox.setSelectedIndex(0);
		statusBox.setSelectedIndex(0);
		priorityBox.setSelectedIndex(0);
		iterationBox.setSelectedIndex(0);

		//Enables the fields upon creation
		setInputEnabled(inputEnabled);

		//Set the following fields to be initially grayed out
		disableFieldsOnCreation();

		//Set up the description scroll pane
		JScrollPane scrollPane = new JScrollPane(txtDescription);// Put the txtDescription in a scroll pane
		scrollPane.setPreferredSize(new Dimension(450,250)); //Set the initial size of the txtDescription scroll panel

		//In this last section we adjust the size and alignments of all the components and add them to the innerPanel.
		//Please read all the comments in this section if you are having trouble understanding what is going on.

		//create the constraints variable for the layout of the innerPanel
		attributePanelConstraints = new GridBagConstraints(); 

		//A quick note about constraints:
		//A constraint variable is a single instance of an object that stores constraints,
		//  so if you set variables within it, they persist to the next time it is used.
		//This means you *must* reset variables within the constraint if you do not want
		//  the previously stored value to effect the next component that uses it.

		//Name:
		//Set the constraints for "nameLabel" and add it to the innerPanel
		attributePanelConstraints.weightx = 0.07;//This sets the horizontal (x axis) "weight" of the component, which tells the layout how big to make this component in respect to the other components on it's line
		attributePanelConstraints.anchor = GridBagConstraints.FIRST_LINE_END;//This sets the anchor of the field, here we have told it to anchor the component to the top right of it's field
		attributePanelConstraints.insets = new Insets(15,5,0,0);  //Set the top padding to 15 units of blank space, left padding to 5 units of space
		attributePanelConstraints.gridx = 0; //set the x coord of the cell of the layout we are describing
		attributePanelConstraints.gridy = 0;//set the y coord of the cell of the layout we are describing
		add(nameLabel, attributePanelConstraints);//Actually add the "nameLabel" to the layout given the previous constraints
		//Set the constraints for "txtName" and add it to the innerPanel
		attributePanelConstraints.weightx = 0.77;
		attributePanelConstraints.fill = GridBagConstraints.HORIZONTAL;//This tells the layout to stretch this field horizontally to fit it's cell
		attributePanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;//Anchor the component to the top left center of it's field
		attributePanelConstraints.gridx = 1;
		attributePanelConstraints.gridwidth = 3; //This tells the layout that this component will be 3 cells wide
		attributePanelConstraints.gridy = 0;
		add(txtName, attributePanelConstraints);
		//end Name

		//Description:		
		//Set the constraints for the "descriptionLabel" and add it to the innerPanel
		attributePanelConstraints.weightx = 0.07;
		attributePanelConstraints.fill = GridBagConstraints.NONE;//This tells the layout to *not* stretch this field horizontally (or vertically, for that matter) to fit it's cell
		attributePanelConstraints.anchor = GridBagConstraints.FIRST_LINE_END; 
		attributePanelConstraints.insets = new Insets(15,5,0,0);  //Set the top padding to 15 units of blank space, left padding to 5 units of space
		attributePanelConstraints.gridx = 0;
		attributePanelConstraints.gridwidth = 1;//This tells the layout to reset the amount of cells the component will be wide, from the previous 3 cells wide down to the default of 1
		attributePanelConstraints.gridy = 2;
		add(descriptionLabel, attributePanelConstraints);
		//Set the constraints for the "scrollPane" containing the "txtDescription" and add it to the innerPanel
		attributePanelConstraints.weightx = 0.77;
		attributePanelConstraints.fill = GridBagConstraints.HORIZONTAL;
		attributePanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		attributePanelConstraints.gridx = 1;
		attributePanelConstraints.gridwidth = 3;
		attributePanelConstraints.gridy = 2;
		add(scrollPane, attributePanelConstraints);
		//end Description

		//Type:
		//Set the constraints for the "typeLabel" and add it to the innerPanel
		attributePanelConstraints.weightx = 0.07;
		attributePanelConstraints.fill = GridBagConstraints.NONE;
		attributePanelConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
		attributePanelConstraints.insets = new Insets(15,15,0,0); 
		attributePanelConstraints.gridx = 0;
		attributePanelConstraints.gridwidth = 1;
		attributePanelConstraints.gridy = 4;
		add(typeLabel, attributePanelConstraints);
		//Set the constraints for the "typeBox"  and add it to the innerPanel
		attributePanelConstraints.weightx = 0.43;
		attributePanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		attributePanelConstraints.insets = new Insets(15,5,10,0);
		attributePanelConstraints.gridx = 1;
		attributePanelConstraints.gridy = 4;
		add(typeBox, attributePanelConstraints);
		//end Type

		//Priority:
		//Set the constraints for the "priorityLabel" and add it to the innerPanel
		attributePanelConstraints.weightx = 0.07;
		attributePanelConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
		attributePanelConstraints.insets = new Insets(15,15,0,0);
		attributePanelConstraints.gridx = 0;
		attributePanelConstraints.gridy = 5;
		add(priorityLabel, attributePanelConstraints);
		//Set the constraints for the "priorityBox" and add it to the innerPanel
		attributePanelConstraints.weightx = 0.43;
		attributePanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		attributePanelConstraints.insets = new Insets(15,5,10,0);
		attributePanelConstraints.gridx = 1;
		attributePanelConstraints.gridy = 5;
		add(priorityBox, attributePanelConstraints);
		//end Priority

		//Release number:
		//Set the constraints for the "releaseNumLabel" and add it to the innerPanel
		attributePanelConstraints.weightx = 0.07;
		attributePanelConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
		attributePanelConstraints.insets = new Insets(15,15,0,0);
		attributePanelConstraints.gridx = 0;
		attributePanelConstraints.gridy = 6;
		add(releaseNumLabel, attributePanelConstraints);
		//Set the constraints for the "txtReleaseNum" and add it to the innerPanel
		attributePanelConstraints.weightx = 0.43;
		attributePanelConstraints.ipadx = 80;//This tells the layout to pad the interior of this field horizontally with 80 units of space
		attributePanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		attributePanelConstraints.insets = new Insets(15,5,10,0);
		attributePanelConstraints.gridx = 1;
		attributePanelConstraints.gridy = 6;
		add(txtReleaseNumber, attributePanelConstraints);
		//end Release number


		//Status:		
		//Set the constraints for the "statusLabel" and add it to the innerPanel
		attributePanelConstraints.weightx = 0.07;
		attributePanelConstraints.anchor = GridBagConstraints.FIRST_LINE_END; 
		attributePanelConstraints.insets = new Insets(15,5,0,0);
		attributePanelConstraints.ipadx = 0;//This tells the layout to reset the horizontal ipad from the previously defined 80 units to now 0 units
		attributePanelConstraints.gridx = 2;
		attributePanelConstraints.gridy = 4;
		add(statusLabel, attributePanelConstraints);
		//Set the constraints for the "statusBox" and add it to the innerPanel
		attributePanelConstraints.weightx = 0.43;
		attributePanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		attributePanelConstraints.gridx = 3;
		attributePanelConstraints.gridy = 4;
		add(statusBox, attributePanelConstraints);
		//end Status

		//Estimate:		
		//Set the constraints for the "estimateLabel" and add it to the innerPanel
		attributePanelConstraints.weightx = 0.07;
		attributePanelConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
		attributePanelConstraints.gridx = 2;
		attributePanelConstraints.gridy =5;
		add(estimateLabel, attributePanelConstraints);
		//Set the constraints for the "txtEstimate" and add it to the innerPanel
		attributePanelConstraints.ipadx = 80;
		attributePanelConstraints.weightx = 0.43;
		attributePanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		attributePanelConstraints.gridx = 3;
		attributePanelConstraints.gridy = 5;
		add(txtEstimate, attributePanelConstraints);
		//end Estimate

		//Actual effort:
		//Set the constraints for the "actualEffortLabel" and add it to the innerPanel
		attributePanelConstraints.ipadx = 0;//This tells the layout to reset the horizontal ipad from the previously defined 80 units to now 0 units
		attributePanelConstraints.weightx = 0.07;
		attributePanelConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
		attributePanelConstraints.gridx = 2;
		attributePanelConstraints.gridy = 6;
		add(actualEffortLabel, attributePanelConstraints);
		//Set the constraints for the "txtActualEffort" and add it to the innerPanel
		attributePanelConstraints.ipadx = 80;
		attributePanelConstraints.weightx = 0.43;
		attributePanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		attributePanelConstraints.gridx = 3;
		attributePanelConstraints.gridy = 6;
		add(txtActualEffort, attributePanelConstraints);
		//end Actual effort

		//Iteration
		//Set the constraints for the "iterationLabel" and add it to the innerPanel
		attributePanelConstraints.ipadx = 0;//This tells the layout to reset the horizontal ipad from the previously defined 80 units to now 0 units
		attributePanelConstraints.weightx = 0.07;
		attributePanelConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
		attributePanelConstraints.gridx = 2;
		attributePanelConstraints.gridy = 7;
		add(iterationLabel, attributePanelConstraints);
		//Set the constraints for the "iterationBox" and add it to the innerPanel
		attributePanelConstraints.weightx = 0.43;
		attributePanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		attributePanelConstraints.gridx = 3;
		attributePanelConstraints.gridy = 7;
		add(iterationBox, attributePanelConstraints);
		//end Iteration

		//Warning labels
		attributePanelConstraints.anchor = GridBagConstraints.LINE_START;//This sets the anchor of the field, here we have told it to anchor the component to the top right of it's field
		attributePanelConstraints.insets = new Insets(0,5,0,0);
		attributePanelConstraints.gridx = 1; //set the x coord of the cell of the layout we are describing
		attributePanelConstraints.gridwidth = 3; //This tells the layout that this component will be 2 cells wide
		attributePanelConstraints.gridy = 1;//set the y coord of the cell of the layout we are describing
		add(warningName, attributePanelConstraints);//Actually add the "nameLabel" to the layout given the previous constraints
		attributePanelConstraints.gridx = 1; //set the x coord of the cell of the layout we are describing
		attributePanelConstraints.gridy = 3;//set the y coord of the cell of the layout we are describing
		add(warningDescription, attributePanelConstraints);//Actually add the "nameLabel" to the layout given the previous constraints
		//end warning labels




	}





	/**
	 * Returns a boolean representing whether or not input is enabled for the RequirementPanel.
	 * @return the inputEnabled boolean 	A boolean representing whether or not input is enabled for the RequirementPanel.
	 */
	public boolean getInputEnabled() {
		return inputEnabled;
	}

	/**
	 * This returns the JTextField "txtName"
	 * @return the txtName JTextField
	 */
	public JTextField getRequirementName() {
		return txtName;
	}

	/**
	 * This returns the JTextArea "txtDescription"
	 * @return the txtDescription JTextArea
	 */
	public JTextArea getRequirementDescription() {
		return txtDescription;
	}


	/**
	 * This returns the JComboBox "typeBox"
	 * @return the typeBox JComboBox
	 */
	public JComboBox getRequirementType() {
		return typeBox;
	}

	/**
	 * This returns the JComboBox "statusBox"
	 * @return the statusBox JComboBox
	 */
	public JComboBox getRequirementStatus() {
		return statusBox;
	}

	/**
	 * This returns the JComboBox "priorityBox"
	 * @return the priorityBox JComboBox
	 */
	public JComboBox getRequirementPriority() {
		return priorityBox;
	}

	/**
	 * This returns the JTextField "txtReleaseNum"
	 * @return the txtReleaseNum JTextField
	 */
	public JTextField getRequirementReleaseNumber() {
		return txtReleaseNumber;
	}

	/**
	 * This returns the JTextField "txtEstimate"
	 * @return the txtEstimate JTextField
	 */
	public JTextField getRequirementEstimate() {
		return txtEstimate;
	}

	/**
	 * This returns the JTextField "txtActualEffort"
	 * @return the txtActualEffort JTextField
	 */
	public JTextField getRequirementActualEffort() {
		return txtActualEffort;
	}

	/**
	 * This returns the "mode" of this panel (Mode.EDIT or Mode.CREATE)
	 * @return the mode Mode
	 */
	public Mode getMode() {
		return mode;
	}

	/**
	 * This sets the Mode "mode" of this panel
	 * @param m Mode to set mode to (Mode.EDIT or Mode.CREATE)
	 */
	public void setMode(Mode m) {
		mode = m;
	}

	/**
	 * This returns the Requirement "currentRequirement" 
	 * @return the currentRequirement Requirement
	 */
	public Requirement getCurrentRequirement() {
		return currentRequirement;
	}

	/**
	 * This sets the Requirement "currentRequirement" 
	 * @param currentRequirement What to set the current requirement to
	 */
	public void setCurrentRequirement(Requirement currentRequirement) {
		this.currentRequirement = currentRequirement;
	}

	/**
	 * This returns the "parent" (a RequirementView) of this panel
	 * @return the parent RequirementTab
	 */
	public RequirementTab getParent() {
		return parent;
	}

	/**
	 * @return the iterationBox
	 */
	public JComboBox getIterationBox() {
		return iterationBox;
	}

	/**
	 * @param iterationBox the iterationBox to set
	 */
	public void setIterationBox(JComboBox iterationBox) {
		this.iterationBox = iterationBox;
	}

	/**
	 * @return the fieldsChanged
	 */
	public boolean isFieldsChanged() {
		for (int i = 0; i < this.fieldsChanged.length; i++){
			if (fieldsChanged[i])
				return true;
		}
		return false;
	}

	/**
	 * @param fieldsChanged the fieldsChanged to set
	 */
	public void setFieldsChanged(boolean fieldsChanged) {
		for (int i = 0; i < this.fieldsChanged.length; ++i) {
			this.fieldsChanged[i] = fieldsChanged;
		}
	}

	/** Setter for the saveButton
	 *  
	 * @param saveButton The button that should be referenced as the save button. Located in tool bar
	 */
	public void setSaveButton(JButton saveButton) {
		this.saveButton = saveButton;
	}

	/** Takes a JComponent and sets its color if its values were changed
	 * 
	 * @param obj The component that is being watched
	 * @param i   The index in the boolean array that indicates changes
	 * @param toSet True or false for activation
	 */
	public void changeField(JComponent obj, int i, boolean toSet) {
		if (toSet) {
			obj.setBackground(new Color(248,253,188));
			fieldsChanged[i] = true;
		} else {
			toggleComponentEnabled(obj, obj.isEnabled());
			fieldsChanged[i] = false;
		}

		if (saveButton != null && nameAndDescriptionValidityListener != null){
			saveButton.setEnabled(isFieldsChanged() && setSaveButtonWhenNameAndDescriptionAreValid());
		}
	}
	/** Sets up the controllers and listeners for this panel   */
	public void setupControllersAndListeners() {
		// Add a listener to check the Name and Description boxes for validity
		nameAndDescriptionValidityListener = new ValidNameDescriptionListener(this);
		txtName.addKeyListener(nameAndDescriptionValidityListener);
		txtDescription.addKeyListener(nameAndDescriptionValidityListener);

		// Add change listeners that turn fields yellow when changed
		txtName.addKeyListener(new FieldChangeListener(this, txtName,    "Name",0));
		txtDescription.addKeyListener(new FieldChangeListener(this, txtDescription,     "Description",1));
		txtReleaseNumber.addKeyListener(new FieldChangeListener(this, txtReleaseNumber, "ReleaseNumber",2));
		txtEstimate.addKeyListener(new KeyListener(){
			/** Unused */
			public void keyTyped(KeyEvent e) {	}

			/** Unused */
			public void keyPressed(KeyEvent e) {	}

			/** When an estimate is entered and is 0 or nonexistent, the iteration box is disabled
			 *  so that the user cannot assign the requirement to an iteration without first filling
			 *  out a valid estimate. 
			 */
			public void keyReleased(KeyEvent e) {
				// When estimate is invalid, deactivate the iteration box
				if (txtEstimate.getText().equals("") || Integer.parseInt(txtEstimate.getText()) == 0  ){
					iterationBox.setEnabled(false);
				} else {
					iterationBox.setEnabled(true);
				}

				// Check the old value and set the box yellow as necessary
				if (txtEstimate.getText().equals("")){
					changeField(txtEstimate, 3, true);
				} else if (Integer.parseInt(txtEstimate.getText()) != currentRequirement.getEstimate()) {
					changeField(txtEstimate, 3, true);
				} else {
					changeField(txtEstimate, 3, false);
				}		
			}
		});
		txtActualEffort.addKeyListener(new KeyListener(){
			/** Unused */
			public void keyTyped(KeyEvent e) {  }

			/** Unused */
			public void keyPressed(KeyEvent e) {	}

			/** Checks the actual effort box for changes and turns it yellow when changes are made */
			public void keyReleased(KeyEvent e) {
				// Check the old value and set the box yellow as necessary
				if (txtActualEffort.getText().equals("")){
					changeField(txtActualEffort, 4, true);
				} else if (Integer.parseInt(txtActualEffort.getText()) != currentRequirement.getActualEffort()) {
					changeField(txtActualEffort, 4, true);
				} else {
					changeField(txtActualEffort, 4, false);
				}			
			}	
		});

		typeBox.addPopupMenuListener(new BoxChangeListener(this, typeBox,  "Type",5 ));
		priorityBox.addPopupMenuListener(new BoxChangeListener(this, priorityBox,      "Priority",7 ));
		statusBox.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				//		System.out.println("Status Box: Changed!");


				// Check the old value and set the box yellow as necessary
				if (!statusBox.getSelectedItem().toString().equals(currentRequirement.getStatus() + "")) {
					changeField(statusBox, 6, true);
					System.out.println("  Result: activate");
				} else {
					System.out.println("  Result: deactivate");
					changeField(statusBox, 6, false);
				}
				// Only valid in EDIT mode
				if (mode.equals(Mode.EDIT) ){
					
				}
			}

		});



		// Add a listener to the iteration box that changes the Req's status when the iteration is changed
		iterationBox.addItemListener(new IterationChangeListener(this));
	}
	/** Checks the name and description fields for changes and sets the warning labels and 
	 *  save button status appropriately            
	 *  
	 *   @return True if the name and description fields are valid, false otherwise
	 */
	public boolean setSaveButtonWhenNameAndDescriptionAreValid(){
		// Initialize flags
		boolean nameGood = true;
		boolean desGood = true;

		// Check the name box
		if ((txtName.getText().length()>=100)||(txtName.getText().length()<1)){
			warningName.setText("Name must be between 0 and 100 characters");
			nameGood = false;
		} else {
			// reset the warning if necessary
			warningName.setText("");
		}

		// Check the description box
		if (txtDescription.getText().length() < 1){
			warningDescription.setText("Description cannot be blank");
			desGood = false;
		} else {
			// reset the warning if necessary
			warningDescription.setText("");
		}

		// If either are false, keep it disabled
		validNameAndDescription = Boolean.valueOf(desGood && nameGood);
		saveButton.setEnabled( validNameAndDescription.booleanValue() && isFieldsChanged());	
		return validNameAndDescription;
	}	

	/**
	 * Gets a requirement from the current text fields
	 * TODO error check
	 * 
	 * @return Requirement made from the text fields
	 */
	public Requirement getRequirement()
	{
		// get the fields from the UI
		String name = this.getRequirementName().getText();
		String description = this.getRequirementDescription().getText();
		String releaseNumber = this.getRequirementReleaseNumber().getText();
		RequirementPriority priority = RequirementPriority.toPriority(this.getRequirementPriority().getSelectedItem().toString());
		RequirementType type = RequirementType.toType(this.getRequirementType().getSelectedItem().toString());


		Requirement toReturn = new Requirement(name, description, type, priority,  releaseNumber, 0);

		return toReturn;

	}


	/**
	 * Sets whether input is enabled for this panel and its children. This should be used instead of 
	 * JComponent#setEnabled because setEnabled does not affect its children.
	 * 
	 * @param enabled Whether or not input is enabled.
	 */
	protected void setInputEnabled(boolean enabled){
		inputEnabled = enabled;

		toggleComponentEnabled(txtName, enabled);
		toggleComponentEnabled(txtDescription, enabled);
		toggleComponentEnabled(typeBox, enabled);
		toggleComponentEnabled(statusBox, enabled);
		toggleComponentEnabled(priorityBox, enabled);
		toggleComponentEnabled(txtReleaseNumber, enabled);
		if (currentRequirement.getStatus() == RequirementStatus.InProgress)
			toggleComponentEnabled(txtEstimate, false);
		else
			toggleComponentEnabled(txtEstimate, enabled);
		toggleComponentEnabled(txtActualEffort, enabled);
		toggleComponentEnabled(iterationBox, enabled);

		// Time to refresh this box if we are enabling all the boxes
		if (enabled){
			fillIterationSelectionBox();
		}
	}

	/**
	 * Sets the appropriate fields disabled upon creation
	 * 
	 */
	protected void disableFieldsOnCreation(){

		if (mode == Mode.CREATE) {
			//Set the following fields to be initially grayed out
			toggleComponentEnabled(txtEstimate, false);
			toggleComponentEnabled(txtActualEffort, false);
			toggleComponentEnabled(statusBox, false);
			toggleComponentEnabled(iterationBox, false);
		}
	}


	/**
	 * Updates the RequirementPanel's model ("currentRequirement") to contain the values of the given Requirement.
	 * 
	 * @param requirement	The Requirement which contains the new values for the model ("currentRequirement").
	 * @param newMode		The new "mode"
	 */
	public void updateModel(Requirement requirement, Mode newMode){
		mode = newMode;
		currentRequirement = requirement;

		populateFields();


		// TODO: better comments here
		revalidate();
		layout.invalidateLayout(this);
		layout.layoutContainer(this);
		repaint();
	}

	/**
	 * Enables or disables a given JComponent and sets is color accordingly
	 * 
	 * @param box Box to be enabled / disabled
	 * @param enabled True for enable, false for disable
	 */
	public void toggleComponentEnabled(JComponent box, boolean enabled) {
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
	 * Updates the RequirementPanel's fields to match those in the current model (stored in "currentRequirement").
	 */
	private void populateFields(){

		if (mode == Mode.EDIT)//If we are editing an existing requirement
		{
			//Enable all fields
			//			setInputEnabled(true);

			//Set the fields to the values passed in with "requirement"
			txtName.setText(currentRequirement.getName());
			txtDescription.setText(currentRequirement.getDescription());
			txtReleaseNumber.setText( String.valueOf(currentRequirement.getReleaseNumber()) );
			txtEstimate.setText( String.valueOf(currentRequirement.getEstimate()) );
			txtActualEffort.setText(String.valueOf(currentRequirement.getActualEffort()) );

			String oldType = (currentRequirement.getType()).toString();//grab the string version of the type passed in with "requirement"
			String oldStatus = (currentRequirement.getStatus()).toString();//grab the string version of the status passed in with "requirement"
			String oldPriority = (currentRequirement.getPriority()).toString();//grab the string version of the priority passed in with "requirement"

			//Set the selected index of the typeBox to the correct value, based on the oldType
			if (oldType.equals("Epic"))
				typeBox.setSelectedIndex(1);
			else if (oldType.equals("Theme"))
				typeBox.setSelectedIndex(2);
			else if (oldType.equals("UserStory"))
				typeBox.setSelectedIndex(3);
			else if (oldType.equals("NonFunctional"))
				typeBox.setSelectedIndex(4);
			else if (oldType.equals("Scenario"))
				typeBox.setSelectedIndex(5);
			else// oldType = "NoType"
				typeBox.setSelectedIndex(0);

			//Set the selected index of the priorityBox to the correct value, based on the oldPriority
			if (oldPriority.equals("High"))
				priorityBox.setSelectedIndex(1);
			else if (oldPriority.equals("Medium"))
				priorityBox.setSelectedIndex(2);
			else if (oldPriority.equals("Low"))
				priorityBox.setSelectedIndex(3);
			else // oldPriority = "NoPriority"
				priorityBox.setSelectedIndex(0);

			updateStatusSettings(oldStatus);
		}

		// Set the status of the fields to unchanged because they just got populated
		setFieldsChanged(false);
	}

	/**
	 * This section limits the status changes available to the user
	 * in the JComboBox for status based on requirements
	 * 
	 * @param setStatus the status to set the boxes to
	 */
	public void updateStatusSettings(String setStatus){

		// Can't edit these fields when the Req has certain statuses
		if (setStatus.equals("Deleted") || setStatus.equals("Complete")){
			toggleComponentEnabled(txtName, false);
			toggleComponentEnabled(txtDescription, false);
			toggleComponentEnabled(typeBox, false);
			toggleComponentEnabled(priorityBox, false);
			toggleComponentEnabled(txtReleaseNumber, false);
			toggleComponentEnabled(txtEstimate, false);
			toggleComponentEnabled(txtActualEffort, false);
			toggleComponentEnabled(iterationBox, false);			
		}
		// Sets the selected entry to the first, which will be correct for each.
		DefaultComboBoxModel compbox = new DefaultComboBoxModel (RequirementStatus.getAvailableStatuses( setStatus));
		statusBox.setModel(compbox);
		statusBox.setSelectedIndex(0);

	}

	/** 
	 *  Gets the names of the current iterations 
	 *  and puts them into the Iteration selection combo box
	 *  if the iteration in question has a end date that is 
	 *  NOT before this date, OR has an id number of 0 (the backlog).  
	 *  Also sets the selected index appropriately.
	 */
	public void fillIterationSelectionBox() {
		// Iterations cannot be assigned when there is no estimate saved, so enable/disable the iteration box appropriately
		if (currentRequirement.getEstimate() <= 0){
			iterationBox.setEnabled(false);
		} else {
			iterationBox.setEnabled(true);
		}

		Iteration[] allIterations = parent.getAllIterations(); //Grab all the iterations in an array

		ArrayList<Iteration> iterationsToDisplay = new ArrayList<Iteration>();//This will hold all the iterations that we will display

		for (int i = 0; i < allIterations.length; ++i) //For all the iterations in the "allIterations" array
		{
			//If the iteration at this index (i) has a end date that is NOT before this date, OR has an id number of 0 (the backlog)
			if ( (!(allIterations[i].getEndDate().before(new Date()))) || (allIterations[i].getID() == 0)  )
				iterationsToDisplay.add(allIterations[i]);//add it to the list of iterations to display 
		}

		//Create and fill an array of the iteration names to display, to be used to create the combo box model
		String[] names = new String[iterationsToDisplay.size()];
		for (int i = 0; i < iterationsToDisplay.size(); ++i) {
			names[i] = iterationsToDisplay.get(i).getName();
		}

		DefaultComboBoxModel  valb = new DefaultComboBoxModel (names);//Create the new combo box model
		iterationBox.setModel(valb);//Give the iteration box the new model to use

		//Set the selected index of the itertionBox to the correct value, based on the oldPriority
		// First find the name of the iteration by ID
		for (int i = 0; i < iterationsToDisplay.size(); i++){
			// Figure out what position in the referenced iteration is at
			if (iterationsToDisplay.get(i).getID() ==  currentRequirement.getIteration()){
				// Set the index of the box to the current index
				iterationBox.setSelectedIndex(i);
			}
		}
	}

	/** When the selected value in the iteration box is changed, this method is 
	 * called by the listener watching the box. Under certain circumstances,
	 * this method changes the Requirement's status.
	 * 
	 * If not Complete or Deleted, check the following:
	 * 
	 * If changed to Backlog -> Open
	 * If changed to Iteration -> InProgress
	 * 
	 * @param e details about the event
	 */
	public void changeStatusWithIteration(ItemEvent e){
		System.out.println("The assigned iteration has been changed; the status will be changed accordingly.");

		// Only valid in EDIT mode
		if (this.getMode().equals(Mode.EDIT) ){
			RequirementStatus currentStatus = currentRequirement.getStatus(); 			

			// You can't change the status while Deleted or Complete anyways, so this is a check.
			if ( currentStatus == RequirementStatus.Complete 	|| currentStatus == RequirementStatus.Deleted ){
				return;
			}

			// If changed to the backlog, set the status to Open
			if ( ((JComboBox) e.getSource()).getSelectedIndex() == 0){
				// Special case: if the old status was New (and obviously isn't now
				if ( this.getCurrentRequirement().getStatus() == RequirementStatus.New){
					this.updateStatusSettings("New");
					return;
				}
				changeField(statusBox, 6, true); // Note that the status changed
				this.updateStatusSettings("Open");
				txtEstimate.setEnabled(true);
			} else {
				txtEstimate.setEnabled(false); 
				this.updateStatusSettings("InProgress");
			}
			// hack to make the status box change colors
			if (! this.getCurrentRequirement().getStatus().toString().equals(statusBox.getSelectedItem().toString())){
				System.out.println("Status was changed with the iteration.");
				changeField(statusBox, 6, true); // Note that the status changed
			} else {
				System.out.println("The status was not changed by the iteration.");
				changeField(statusBox, 6, false); // Note that the status changed
			}     
		}
	}

	/** Checks the Iteration box for changes and yellows that box appropriately
	 * 
	 */
	public void checkIterationChange() {
		// Get the name of the iteration that this Requirement used to be assigned to
		Iteration[] allIterations = parent.getAllIterations();

		String oldValue = "";
		for (int i = 0; i < allIterations.length; ++i) {
			if (currentRequirement.getIteration() == allIterations[i].getID()){
				oldValue = (allIterations[i].getName());
			}
		}


		// Check the old value and set the box yellow as necessary
		if (!iterationBox.getSelectedItem().toString().equals(oldValue + "")) {
			this.changeField(iterationBox, 8, true);
			System.out.println("  Result: activate");
		} else {
			System.out.println("  Result: deactivate");
			this.changeField(iterationBox, 8, false);
		}
	}

}
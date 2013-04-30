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
	/** The JLabel for the name text field ("txtName") */
	private  JLabel nameLabel; 

	/** The JLabel for the description text area ("txtDescription") */
	private  JLabel descriptionLabel;

	/** The JLabel for the type combo box ("typeBox") */
	private  JLabel typeLabel; 

	/** The JLabel for the status combo box ("statusBox") */
	private  JLabel statusLabel; 

	/** The JLabel for the priority combo box ("priorityBox") */
	private  JLabel priorityLabel; 

	/** The JLabel for the iteration combo box ("iterationBox") */
	private  JLabel iterationLabel; 

	/** The JLabel for the release number text field ("txtReleaseNum") */
	private  JLabel releaseNumLabel;

	/** The JLabel for the estimate text field ("txtEstimate") */
	private  JLabel estimateLabel; 

	/** The JLabel for the actual effort text field ("txtActualEffort") */
	private  JLabel actualEffortLabel;

	/** The JLabel for warning the user about invalid (blank or over 100 characters) names */
	private  JLabel warningName;

	/** The JLabel for warning the user about invalid (blank) descriptions */
	private  JLabel warningDescription;

	/** The JLabel for warning the user about invalid (blank) estimates */
	private  JLabel warningEstimate;

	/** The JLabel for warning the user about invalid (blank) actual efforts */
	private  JLabel warningActualEffort;

	//The fillable components
	/** The name text field */
	private  JTextField txtName;

	/** The description text area */
	private  JTextArea txtDescription;

	/** The type combo box */
	private  JComboBox typeBox;

	/** The status combo box */
	private  JComboBox statusBox;

	/** The priority combo box */
	private  JComboBox priorityBox;

	/** The iteration combo box */
	private  JComboBox iterationBox;

	/** The release number text field */
	private  JTextField txtReleaseNumber;

	/** The estimate text field */
	private  JNumberTextField txtEstimate;

	/** The actual effort text field */
	private  JNumberTextField txtActualEffort;

	//Other variables

	/** Stores the requirement currently open for editing or creation */
	private Requirement currentRequirement;

	/** Stores the RequirementTab that contains the panel */
	private RequirementTab parent;

	/** A boolean indicating if input is enabled on the form  */
	protected boolean inputEnabled;

	/** The variable to store the enum indicating whether or not you are creating at the time */
	private Mode mode;

	/** This array holds the boolean flags for what fields are changed as well as 
	 *  whether the save button is being saved or not
	 *  Index:      Component isChanged
	 *  0			Name field
	 *	1			Description area
	 *  2			Release number field
	 *  3			Estimate number field
	 *  4			Actual effort field
	 *  5			Type box  
	 *  6			Status box
	 *  7			Priority box 
	 *  8			*unused*
	 *  9			Notes 
	 *  10        	Save button is saving
	 *  11        	Acceptance Tests Title
	 *  12        	Acceptance Tests Body
	 */	
	private boolean[] fieldsChanged;

	/** A reference to the save button in the tool bar */
	private JButton saveButton;

	/** A boolean used to keep track of if the fields are valid or not */
	private Boolean validFields;

	/** a listener to watch the name and description boxes    */
	ValidNameDescriptionListener nameAndDescriptionValidityListener;

	/** The layout manager for this panel */
	protected GridBagLayout layout;

	String[] iterationArr = { "Backlog"};
	String[] iterationStrings = iterationArr;

	/** The constraints variable used to layout this panel */
	private GridBagConstraints attributePanelConstraints;

	public RequirementAttributePanel(RequirementTab parentTab, Requirement requirement, Mode editMode){
		parent = parentTab;//Set the RequirementView that contains this instance of this panel
		currentRequirement = requirement; //Set the requirement that is currently open for editing or creation
		mode = editMode;//Set the indicated mode

		inputEnabled = true; // Indicate that input is enabled
		fieldsChanged = new boolean[13];//Set up the fieldsChanged array
		setFieldsChanged(false);

		validFields = new Boolean(true);//Set up the validFields boolean

		//Create and set the layout manager that controls the positions of the components
		layout = new GridBagLayout();//Create the layout
		setLayout(layout); //Set the layout

		//add a border of blank space
		setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30)); 

		addComponents();//Add the components to the panel
		populateFields();//Update the fields to those given in the currentRequirement, if necessary.
	}

	/** Constructs and  adds the components to the panel and places
	 * constraints them for the GridBagLayout.
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
		warningEstimate = new JLabel("Estimate cannot be blank");
		warningActualEffort = new JLabel("ActualEffort cannot be blank");

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

		//Set character limit on description
		txtDescription.setDocument(new JTextFieldLimit(100000));

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
		warningEstimate.setForeground(Color.red);
		warningActualEffort.setForeground(Color.red);

		//Set the sizes for the warning labels
		warningName.setPreferredSize(new Dimension(410, 15));
		warningDescription.setPreferredSize(new Dimension(270, 15));
		warningEstimate.setPreferredSize(new Dimension(250, 15));
		warningActualEffort.setPreferredSize(new Dimension(280, 15));

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
		attributePanelConstraints.insets = new Insets(15,5,2,0);
		attributePanelConstraints.gridx = 1;
		attributePanelConstraints.gridy = 5;
		add(priorityBox, attributePanelConstraints);
		//end Priority

		//Release number:
		//Set the constraints for the "releaseNumLabel" and add it to the innerPanel
		attributePanelConstraints.weightx = 0.07;
		attributePanelConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
		attributePanelConstraints.insets = new Insets(10,15,0,0);
		attributePanelConstraints.gridx = 0;
		attributePanelConstraints.gridy = 7;
		add(releaseNumLabel, attributePanelConstraints);
		//Set the constraints for the "txtReleaseNum" and add it to the innerPanel
		attributePanelConstraints.weightx = 0.43;
		attributePanelConstraints.ipadx = 80;//This tells the layout to pad the interior of this field horizontally with 80 units of space
		attributePanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		attributePanelConstraints.insets = new Insets(10,5,0,0);
		attributePanelConstraints.gridx = 1;
		attributePanelConstraints.gridy = 7;
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

		//Estimate warning label
		attributePanelConstraints.anchor = GridBagConstraints.LINE_START;//This sets the anchor of the field, here we have told it to anchor the component to the top right of it's field
		attributePanelConstraints.insets = new Insets(0,5,0,0);
		attributePanelConstraints.ipadx = 0;//This tells the layout to reset the horizontal ipad from the previously defined 80 units to now 0 units
		attributePanelConstraints.gridx = 3; //set the x coord of the cell of the layout we are describing
		attributePanelConstraints.gridy = 6;//set the y coord of the cell of the layout we are describing
		add(warningEstimate, attributePanelConstraints);//Actually add the "warningEstimate" to the layout given the previous constraints

		//Actual effort:
		//Set the constraints for the "actualEffortLabel" and add it to the innerPanel
		attributePanelConstraints.weightx = 0.07;
		attributePanelConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
		attributePanelConstraints.insets = new Insets(10,5,0,0);
		attributePanelConstraints.gridx = 2;
		attributePanelConstraints.gridy = 7;
		add(actualEffortLabel, attributePanelConstraints);
		//Set the constraints for the "txtActualEffort" and add it to the innerPanel
		attributePanelConstraints.ipadx = 80;
		attributePanelConstraints.weightx = 0.43;
		attributePanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		attributePanelConstraints.gridx = 3;
		attributePanelConstraints.gridy = 7;
		add(txtActualEffort, attributePanelConstraints);
		//end Actual effort

		//Actual effort warning label
		attributePanelConstraints.anchor = GridBagConstraints.LINE_START;//This sets the anchor of the field, here we have told it to anchor the component to the top right of it's field
		attributePanelConstraints.insets = new Insets(0,5,0,0);
		attributePanelConstraints.ipadx = 0;//This tells the layout to reset the horizontal ipad from the previously defined 80 units to now 0 units
		attributePanelConstraints.gridx = 3; //set the x coord of the cell of the layout we are describing
		attributePanelConstraints.gridy = 8;//set the y coord of the cell of the layout we are describing
		add(warningActualEffort, attributePanelConstraints);//Actually add the "warningActualEffort" to the layout given the previous constraints

		//Iteration
		//Set the constraints for the "iterationLabel" and add it to the innerPanel
		attributePanelConstraints.weightx = 0.07;
		attributePanelConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
		attributePanelConstraints.insets = new Insets(10,5,0,0);
		attributePanelConstraints.gridx = 2;
		attributePanelConstraints.gridy = 9;
		add(iterationLabel, attributePanelConstraints);
		//Set the constraints for the "iterationBox" and add it to the innerPanel
		attributePanelConstraints.weightx = 0.43;
		attributePanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		attributePanelConstraints.gridx = 3;
		attributePanelConstraints.gridy = 9;
		add(iterationBox, attributePanelConstraints);
		//end Iteration

		//Warning name and description labels
		attributePanelConstraints.anchor = GridBagConstraints.LINE_START;//This sets the anchor of the field, here we have told it to anchor the component to the top right of it's field
		attributePanelConstraints.insets = new Insets(0,5,0,0);
		attributePanelConstraints.gridx = 1; //set the x coord of the cell of the layout we are describing
		attributePanelConstraints.gridwidth = 3; //This tells the layout that this component will be 2 cells wide
		attributePanelConstraints.gridy = 1;//set the y coord of the cell of the layout we are describing
		add(warningName, attributePanelConstraints);//Actually add the "warningName" to the layout given the previous constraints
		attributePanelConstraints.gridx = 1; //set the x coord of the cell of the layout we are describing
		attributePanelConstraints.gridy = 3;//set the y coord of the cell of the layout we are describing
		add(warningDescription, attributePanelConstraints);//Actually add the "warningDescription" to the layout given the previous constraints
		//end warning labels
	}

	/** Returns a boolean representing whether or not input is enabled for the RequirementPanel.
	 * @return the inputEnabled boolean 	A boolean representing whether or not input is enabled for the RequirementPanel.
	 */
	public boolean getInputEnabled() {
		return inputEnabled;
	}

	/**nThis returns the JTextField "txtName"
	 * @return the txtName JTextField
	 */
	public JTextField getRequirementName() {
		return txtName;
	}

	/** This returns the JTextArea "txtDescription"
	 * @return the txtDescription JTextArea
	 */
	public JTextArea getRequirementDescription() {
		return txtDescription;
	}


	/** This returns the JComboBox "typeBox"
	 * @return the typeBox JComboBox
	 */
	public JComboBox getRequirementType() {
		return typeBox;
	}

	/** This returns the JComboBox "statusBox"
	 * @return the statusBox JComboBox
	 */
	public JComboBox getRequirementStatus() {
		return statusBox;
	}

	/** This returns the JComboBox "priorityBox"
	 * @return the priorityBox JComboBox
	 */
	public JComboBox getRequirementPriority() {
		return priorityBox;
	}

	/** This returns the JTextField "txtReleaseNum"
	 * @return the txtReleaseNum JTextField
	 */
	public JTextField getRequirementReleaseNumber() {
		return txtReleaseNumber;
	}

	/** This returns the JTextField "txtEstimate"
	 * @return the txtEstimate JTextField
	 */
	public JTextField getRequirementEstimate() {
		return txtEstimate;
	}

	/** This returns the JTextField "txtActualEffort"
	 * @return the txtActualEffort JTextField
	 */
	public JTextField getRequirementActualEffort() {
		return txtActualEffort;
	}

	/** This returns the "mode" of this panel (Mode.EDIT or Mode.CREATE)
	 * @return the mode Mode
	 */
	public Mode getMode() {
		return mode;
	}

	/** This sets the Mode "mode" of this panel
	 * @param m Mode to set mode to (Mode.EDIT or Mode.CREATE)
	 */
	public void setMode(Mode m) {
		mode = m;
	}

	/** This returns the Requirement "currentRequirement" 
	 * @return the currentRequirement Requirement
	 */
	public Requirement getCurrentRequirement() {
		return currentRequirement;
	}

	/** This sets the Requirement "currentRequirement" 
	 * @param currentRequirement What to set the current requirement to
	 */
	public void setCurrentRequirement(Requirement currentRequirement) {
		this.currentRequirement = currentRequirement;
	}

	/** This returns the "parent" (a RequirementView) of this panel
	 * @return the parent RequirementTab
	 */
	public RequirementTab getParent() {
		return parent;
	}

	/** This gets the JComboBox "iterationBox"
	 * @return iterationBox The iterationBox JComboBox
	 */
	public JComboBox getIterationBox() {
		return iterationBox;
	}

	/** This sets the JComboBox "iterationBox"
	 * @param iterationBox The JComboBox iterationBox to set
	 */
	public void setIterationBox(JComboBox iterationBox) {
		this.iterationBox = iterationBox;
	}

	/** This returns a boolean indicating if any of the fields in this panel have changed or not
	 * @return true when any of the fieldsChanged booleans are true, false otherwise
	 */
	public boolean isFieldsChanged() {
		for (int i = 0; i < fieldsChanged.length; i++){
			if (fieldsChanged[i]){
				return true;
			}
		}
		return false;
	}

	/** This returns a boolean indicating if any of the fields  EXCEPT the note fields in this panel have changed or not
	 * @return true when any of the fieldsChanged booleans are true that are not associated with the note fields, false otherwise
	 */
	public boolean isNonMainRequirementFieldsChanged() {
		for (int i = 0; i < fieldsChanged.length; i++){
			if (fieldsChanged[i] && i != 9 && i != 11 && i != 12){
				return true;
			}
		}
		return false;
	}

	/** This sets all the booleans in the fieldsChanged array to a boolean
	 *  passed in as  "fieldsChanged"
	 * @param fieldsChanged The boolean to set all booleans in the fieldsChanged array to
	 */
	public void setFieldsChanged(boolean fieldsChanged) {
		for (int i = 0; i < this.fieldsChanged.length; ++i) {
			this.fieldsChanged[i] = fieldsChanged;
		}
	}
	
	
	/** This sets all the booleans in the fieldsChanged array to a boolean
	*  passed in as  "fieldsChanged", EXCEPT it only applies to the basic set of fields
	*  which are in the main part of the requirementAttributePanel
	* @param fieldsChanged The boolean to set all booleans in the fieldsChanged array to, EXCEPT those booleans in the fieldsChanged associated with the note fields
	*/
	public void setBasicFieldsChanged(boolean fieldsChanged) {
		for (int i = 0; i < 9; i++) {
			this.fieldsChanged[i] = fieldsChanged;
		}
	}
	

	/** Setter for the saveButton 
	 * @param saveButton The JButton that should be referenced as the save button. Located in tool bar.
	 */
	public void setSaveButton(JButton saveButton) {
		this.saveButton = saveButton;
	}

	/** Getter for the saveButton  
	 * @return saveButton The save button JButton
	 */
	public JButton getSaveButton() {
		return saveButton;
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
		} 
		else {
			toggleComponentEnabled(obj, obj.isEnabled());
			fieldsChanged[i] = false;
		}

		if (saveButton != null && nameAndDescriptionValidityListener != null){
			saveButton.setEnabled(isNonMainRequirementFieldsChanged() && setSaveButtonWhenFieldsAreValid());
		}
	}

	/** Sets up the controllers and listeners for this panel
	 */
	public void setupControllersAndListeners() {
		// Add a listener to check the Name and Description boxes for validity
		nameAndDescriptionValidityListener = new ValidNameDescriptionListener(this);
		txtName.addKeyListener(nameAndDescriptionValidityListener);
		txtDescription.addKeyListener(nameAndDescriptionValidityListener);

		// Add change listeners that turn fields yellow when changed
		txtName.addKeyListener(new FieldChangeListener(this, txtName,    "Name",0));
		txtDescription.addKeyListener(new FieldChangeListener(this, txtDescription,     "Description",1));
		txtReleaseNumber.addKeyListener(new FieldChangeListener(this, txtReleaseNumber, "ReleaseNumber",2));

		// Add a key listener that turns the txtEstimate field yellow when changed. Also performs a validity check and sets the warning labels and save button appropriately
		txtEstimate.addKeyListener(new KeyListener(){
			/** Unused, but required by interface */
			public void keyTyped(KeyEvent e) {}

			/** Unused, but required by interface */
			public void keyPressed(KeyEvent e) {}

			/** When an estimate is entered and is 0 or nonexistent, the iteration box is disabled
			 *  so that the user cannot assign the requirement to an iteration without first filling
			 *  out a valid estimate. Also call setSaveButtonWhenFieldsAreValid to validate the 
			 *  change and set the warning label and save button appropriately. This is called
			 *  whenever a key is released while entering into the txtEstimate field.
			 */
			public void keyReleased(KeyEvent e) {
				// When estimate is invalid, deactivate the iteration box
				if (txtEstimate.getText().equals("") || Integer.parseInt(txtEstimate.getText()) == 0  ){
					toggleComponentEnabled(iterationBox, false);
				} else {
					toggleComponentEnabled(iterationBox, true);
				}

				// Check the old value and set the box yellow as necessary
				if (txtEstimate.getText().equals("")){
					changeField(txtEstimate, 3, true);
				} else if (Integer.parseInt(txtEstimate.getText()) != currentRequirement.getEstimate()) {
					changeField(txtEstimate, 3, true);
				} else {
					changeField(txtEstimate, 3, false);
				}
				setSaveButtonWhenFieldsAreValid();//This is here because otherwise the estimate validity is only checked when the field has changed from it's stored value
			}
		});

		// Add a key listener that turns the txtActualEffort field yellow when changed. Also performs a validity check and sets the warning labels and save button appropriately
		txtActualEffort.addKeyListener(new KeyListener(){
			/** Unused, but required by interface */
			public void keyTyped(KeyEvent e) {  }

			/** Unused, but required by interface */
			public void keyPressed(KeyEvent e) {}

			/** Checks the actual effort box for changes and turns it yellow when changes are made.
			 * Also calls setSaveButtonWhenFieldsAreValid to validate the change and set the 
			 * warning label and save button appropriately. This is called whenever a key is released
			 * when entering into the txtActualEffort field.
			 */
			public void keyReleased(KeyEvent e) {
				// Check the old value and set the box yellow as necessary
				if (txtActualEffort.getText().equals("")){
					changeField(txtActualEffort, 4, true);
				} else if (Integer.parseInt(txtActualEffort.getText()) != currentRequirement.getActualEffort()) {
					changeField(txtActualEffort, 4, true);
				} else {
					changeField(txtActualEffort, 4, false);
				}
				setSaveButtonWhenFieldsAreValid();//This is here because otherwise the actual effort validity is only checked when the field has changed from it's stored value
			}	
		});

		typeBox.addPopupMenuListener(new BoxChangeListener(this, typeBox,  "Type",5 ));
		priorityBox.addPopupMenuListener(new BoxChangeListener(this, priorityBox,      "Priority",7 ));
		statusBox.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				// Check the old value and set the box yellow as necessary
				if (!statusBox.getSelectedItem().toString().equals(currentRequirement.getStatus() + "")) {
					changeField(statusBox, 6, true);
				} else {
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

	/** Checks the name, description, estimate, and actual effort fields for changes and sets the warning labels and 
	 *  save button status appropriately.
	 *  Name (the "txtName" JTextField) is considered valid when it is not blank and >= 100 characters.
	 *  Description (the "txtDescription" JTextArea), estimate (the "txtEstimate" JTextField), and actual effort
	 *  (the "txtActualEffort" JTextField), are considered valid when they are not blank.           
	 *  
	 *   @return True if the name, description, estimate, and actual effort fields are valid, false otherwise
	 */
	public boolean setSaveButtonWhenFieldsAreValid(){
		// Initialize flags
		/** Used to indicate the name field ("txtName") is valid */
		boolean nameGood = true;

		/** Used to indicate the description area ("txtDescription") is valid */
		boolean desGood = true;

		/** Used to indicate the estimate field ("txtEstimate") is valid */
		boolean estimateGood = true;

		/** Used to indicate the actual effort field ("txtActualEffort") is valid */
		boolean effortGood = true;

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

		// Check the estimate box
		if ((txtEstimate.getText().length()==0)&& txtEstimate.isEnabled()){
			warningEstimate.setText("Estimate cannot be blank");
			estimateGood = false;
		} else {
			// reset the warning if necessary
			warningEstimate.setText("");
		}

		// Check the actual effort box
		if ((txtActualEffort.getText().length()==0)&& txtActualEffort.isEnabled()){
			warningActualEffort.setText("ActualEffort cannot be blank");
			effortGood = false;
		} else {
			// reset the warning if necessary
			warningActualEffort.setText("");
		}

		// If any are false, set the save button disabled
		validFields = Boolean.valueOf(desGood && nameGood && effortGood && estimateGood);
		saveButton.setEnabled( validFields.booleanValue() && isFieldsChanged());	
		return validFields;
	}	

	/** Creates and returns a requirement from the current entries/selections in this RequirementAttribute panel.
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


	/** Sets whether input is enabled for this panel and its children. This should be used instead of 
	 * JComponent#setEnabled because setEnabled does not affect its children.
	 * Also disables the txtEstimate field when the status of this requirement is "InProgress",
	 * and refreshes (fills) the iteration selection box if the fields are being enabled
	 * @param enabled Whether or not input is enabled.
	 */
	protected void setInputEnabled(boolean enabled){
		inputEnabled = enabled;

		// Time to refresh this box if we are enabling all the boxes
		fillIterationSelectionBox();
		
		
		toggleComponentEnabled(txtName, enabled);
		toggleComponentEnabled(txtDescription, enabled);
		toggleComponentEnabled(typeBox, enabled);
		toggleComponentEnabled(statusBox, enabled);
		toggleComponentEnabled(priorityBox, enabled);
		toggleComponentEnabled(txtReleaseNumber, enabled);
		//Disables the txtEstimate field if appropriate
		if (currentRequirement.getStatus() == RequirementStatus.InProgress){
			toggleComponentEnabled(txtEstimate, false);
		}
		else
			toggleComponentEnabled(txtEstimate, enabled);
		toggleComponentEnabled(txtActualEffort, enabled);
		toggleComponentEnabled(iterationBox, enabled);


	}

	/** Sets the appropriate fields disabled upon creation
	 * (IE, this panel is in Mode.CREATE because it is being used to create
	 * a new requirement
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

	/** Updates the RequirementPanel's model ("currentRequirement") to contain the values of the given Requirement.
	 * 
	 * @param requirement	The Requirement which contains the new values for the model ("currentRequirement").
	 * @param newMode		The new "mode"
	 */
	public void updateModel(Requirement requirement, Mode newMode){
		mode = newMode;
		currentRequirement = requirement;

		populateFields();//Update the fields in this panel to match those in the "requirement" Requirement

		//Revalidate, re-layout, and repaint so that changes are made visible
		revalidate();
		layout.invalidateLayout(this);
		layout.layoutContainer(this);
		repaint();
	}

	/** Enables or disables a given JComponent and sets is color accordingly
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

	/** Updates the RequirementPanel's fields to match those in the current model (stored in "currentRequirement").
	 */
	private void populateFields(){

		if (mode == Mode.EDIT)//If we are editing an existing requirement
		{
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

			updateStatusSettings(oldStatus);//Limit the status' available to the user for selection in the statusBox JComboBox appropriately, and disable the appropriate fields/comments if the new status is "Complete" or "Deleted"
		}

		// Set the status of the fields to unchanged because they just got populated
		setBasicFieldsChanged(false);
	}

	/** This method limits the changes available to the user by disabling various fields/components
	 * when the current status of the requirement is "Deleted" or "Complete". It also sets the selection
	 * and status available in the statusBox JComboBox appropriately based on the current status of the
	 * requirement.
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
		//Make a new DefaultComboBoxModel to use to set the status available for selection in the statusBox appropriately
		DefaultComboBoxModel compbox = new DefaultComboBoxModel (RequirementStatus.getAvailableStatuses(setStatus));
		statusBox.setModel(compbox);
		statusBox.setSelectedItem(setStatus);// Sets the correct selected item

	}

	/**  Gets the names of the current iterations 
	 *  and puts them into the Iteration selection combo box,
	 *  IFF the iteration in question has a end date that is 
	 *  NOT before this date, OR has an id number of 0 (the backlog).  
	 *  Also sets the selected index appropriately.
	 */
	public void fillIterationSelectionBox() {
		// Iterations cannot be assigned when there is no estimate saved, so enable/disable the iteration box appropriately
		if (currentRequirement.getEstimate() <= 0 || currentRequirement.getStatus() == RequirementStatus.Complete ||  currentRequirement.getStatus() == RequirementStatus.Deleted){
			toggleComponentEnabled(iterationBox, false);
		} else {
			toggleComponentEnabled(iterationBox, true);
		}

		Iteration[] allIterations = parent.getAllIterations(); //Grab all the iterations in an array

		ArrayList<Iteration> iterationsToDisplay = new ArrayList<Iteration>();//This will hold all the iterations that we will display

		for (int i = 0; i < allIterations.length; ++i) //For all the iterations in the "allIterations" array
		{
			//If the iteration at this index (i) has a end date that is NOT before this date, OR has an id number of 0 (the backlog)
			if ( (!(allIterations[i].getEndDate().before(new Date()))) || (allIterations[i].getID() == 0)  ){
				iterationsToDisplay.add(allIterations[i]);//add it to the list of iterations to display 
			}
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
	 * called by the listener watching the box. Under appropriate circumstances,
	 * this method changes the Requirement's status.
	 * 
	 * If not Complete or Deleted, check the following:
	 * 
	 * If the iteration is changed to Backlog, set the status to Open
	 * If the iteration is changed to an Iteration, set the status to InProgress
	 * 
	 * @param e details about the event
	 */
	public void changeStatusWithIteration(ItemEvent e){
		// Only valid in EDIT mode
		if (this.getMode().equals(Mode.EDIT) ){
			RequirementStatus currentStatus = currentRequirement.getStatus(); 			

			// You can't change the status while Deleted or Complete anyways, so this is a check.
			if ( currentStatus == RequirementStatus.Complete 	|| currentStatus == RequirementStatus.Deleted ){
				return;
			}

			// If changed to the backlog, set the status to Open
			if ( ((JComboBox) e.getSource()).getSelectedIndex() == 0){
				// Special case: if the old status was New (and obviously isn't now)
				if ( this.getCurrentRequirement().getStatus() == RequirementStatus.New){
					this.updateStatusSettings("New");
					return;
				}
				changeField(statusBox, 6, true); // Note that the status changed
				this.updateStatusSettings("Open");
				toggleComponentEnabled(txtEstimate, true);
			} else {
				txtEstimate.setEnabled(false); 
				this.updateStatusSettings("InProgress");
			}
			// hack to make the status box change colors
			if (! this.getCurrentRequirement().getStatus().toString().equals(statusBox.getSelectedItem().toString())){
				changeField(statusBox, 6, true); // Note that the status changed
			} else {
				changeField(statusBox, 6, false); // Note that the status changed
			}     
		}

		//Revalidate and repaint to ensure changes are shown
		this.revalidate();
		this.repaint();
	}

	/** Checks the Iteration box for changes and yellows that box appropriately 
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
		} else {
			this.changeField(iterationBox, 8, false);
		}
	}

	/** Sets a boolean flag to say that the current requirement is being saved. This flag is checked
	 *  when other operations that could interrupt the save are called.
	 * @param isSaving True if the requirement is being saved, false otherwise
	 */
	public void setSaving(boolean isSaving) {
		fieldsChanged[10] = isSaving;
	}

	/** Returns a boolean indicating whether or not the current requirement is being saved.
	 * @return True if the requirement is being saved, false otherwise
	 */
	public boolean isSaving () {
		return fieldsChanged[10];
	}

}
/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Team 5
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.views.JNumberTextField;

/**
 * This class is a JPanel. 
 * It contains:
 * 		-an enum to indicate a "create" or "edit" mode
 * 		-a text field for entering a new name,
 * 		-a text area for entering a description,
 * 		-a combo box for entering a status,
 * 		-a combo box for entering a priority,
 * 		-a JNumber text field for entering a release number,
 * 		-a JNumber text field for entering an estimate,
 * 		-a JNumber text field for entering an actual effort,
 * @author Team 5
 *
 */
@SuppressWarnings("serial")
public class RequirementPanel extends JPanel {

	//An enum to store the mode of the current instance of this panel
	public enum Mode {
		CREATE,//When Mode is this value, we are creating a new requirement
		EDIT//When Mode is this value, we are editing an existing requirement
	}


	//The labels
	private final JLabel nameLabel; //The label for the name text field ("txtName")
	private final JLabel descriptionLabel;//The label for the description text area ("txtDescription")
	private final JLabel typeLabel; //The label for the type combo box ("typeBox")
	private final JLabel statusLabel; //The label for the status combo box ("statusBox")
	private final JLabel priorityLabel; //The label for the priority combo box ("priorityBox")
	private final JLabel releaseNumLabel; //The label for the release number text field ("txtReleaseNum")
	private final JLabel estimateLabel; //The label for the estimate text field ("txtEstimate")
	private final JLabel actualEffortLabel; //The label for the actual effort text field ("txtActualEffort")

	//The fillable components
	private final JTextField txtName;//The name text field 
	private final JTextArea txtDescription;//The description text area
	private final JComboBox<String> typeBox;//The type combo box
	private final JComboBox<String> statusBox;//The status combo box
	private final JComboBox<String> priorityBox;//The priority combo box
	private final JNumberTextField txtReleaseNum;//The release number text field
	private final JNumberTextField txtEstimate;//The estimate text field
	private final JNumberTextField txtActualEffort;//The actual effort text field

	//The variables to hold information about the current instance of the panel
	private Requirement currentRequirement;//Stores the requirement currently open for editing or creation
	@SuppressWarnings("unused")
	private RequirementView parent; //Stores the RequirementView that contains the panel
	



	private Mode mode;// The variable to store the enum indicating whether or not you are creating at the time

	/**
	 * The constructor for RequirementPanel;
	 * Construct the panel, the components, and add the
	 * components to the panel.
	 * @param view	The parent RequirementView that contains this panel
	 * @param requirement	The requirement that is currently open for editing or creation.
	 * @param editMode	The mode of the current panel. This is "CREATE" when we are creating a new requirement, and "EDIT" when we are editing an existing requirement.
	 */
	public RequirementPanel(RequirementView view, Requirement requirement, Mode editMode) {

		parent = view;//Set the RequirementView that contains this instance of this panel
		currentRequirement = requirement; //Set the requirement that is currently open for editing or creation
		mode = editMode;//Set the indicated mode

		//Construct the labels
		nameLabel = new JLabel("Name:");
		descriptionLabel = new JLabel("Description:");
		typeLabel = new JLabel("Type:");
		statusLabel = new JLabel("Status:");
		priorityLabel = new JLabel("Priority:");
		releaseNumLabel = new JLabel("ReleaseNum:");
		estimateLabel = new JLabel("Estimate:");
		actualEffortLabel = new JLabel("ActualEffort:");

		//Construct the misc components
		txtName = new JTextField(/*"Enter a name here."*/"");
		txtDescription = new JTextArea(/*"Enter a description here."*/"", 2, 2);
		txtReleaseNum = new JNumberTextField();
		txtEstimate = new JNumberTextField();
		txtActualEffort = new JNumberTextField();
		txtEstimate.setText("0");
		txtActualEffort.setText("0");
		txtReleaseNum.setAllowNegative(false);
		txtEstimate.setAllowNegative(false);
		txtActualEffort.setAllowNegative(false);

		//Set the following fields to be initially greyed out
		txtName.setEnabled(false);
		txtDescription.setEnabled(false);
		txtReleaseNum.setEnabled(false);
		txtEstimate.setEnabled(false);
		txtActualEffort.setEnabled(false);

		//Set the txtDescription component to wrap
		txtDescription.setLineWrap(true);
		txtDescription.setWrapStyleWord(true);

		//Create the strings for the boxes
		String[] typeStrings = { " ", "Epic", "Theme", "UserStory", "NonFunctional", "Scenario" };
		String[] statusStrings = { "New", "InProgress", "Open", "Complete", "Deleted" };
		String[] priorityStrings = { " ", "High", "Medium", "Low"};

        //Construct the boxes 
		typeBox = new JComboBox<String>(typeStrings);
		statusBox = new JComboBox<String>(statusStrings);
		priorityBox = new JComboBox<String>(priorityStrings);

		//Set the initial selections for the boxes
		typeBox.setSelectedIndex(0);
		typeBox.setEnabled(false);
		statusBox.setSelectedIndex(0);
		statusBox.setEnabled(false);
		priorityBox.setSelectedIndex(0);
		priorityBox.setEnabled(false);
		

		// Set the layout manager that controls the positions of the components
		setLayout(new GridBagLayout()); //set the layout
		GridBagConstraints reqPanelConstraints = new GridBagConstraints();//create the constraints variable

		//Set up the description scroll pane
		JScrollPane scrollPane = new JScrollPane(txtDescription);// Put the txtDescription in a scroll pane
		scrollPane.setPreferredSize(new Dimension(400,100)); //Set the initial size of the txtDescription scroll panel

		//In this last section we adjust the size and alignments of all the components and add them to the panel.
		//Please read all the comments in this section if you are having trouble understanding what is going on.

		//Name:
		//Set the constraints for "nameLabel" and add it to the view
		reqPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_END;//This sets the anchor of the field, here we have told it to anchor the component to the top right of it's field
		reqPanelConstraints.insets = new Insets(10,5,0,0);  //Set the top padding to 10 units of blank space
		reqPanelConstraints.gridx = 0; //set the x coord of the cell of the layout we are describing
		reqPanelConstraints.gridy = 0;//set the y coord of the cell of the layout we are describing
		add(nameLabel, reqPanelConstraints);//Actually add the "nameLabel" to the layout given the previous constraints
		//Set the constraints for "txtName" and add it to the view
		reqPanelConstraints.ipadx = 150;//This tells the layout to stretch this field horizontally by 250 units
		reqPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;//Anchor the component to the top left center of it's field
		reqPanelConstraints.gridx = 1;
		reqPanelConstraints.gridy = 0;
		add(txtName, reqPanelConstraints);
		//end Name

		//Description:		
		//Set the constraints for the "descriptionLabel" and add it to the view
		reqPanelConstraints.ipadx = 0;//This tells the layout to reset the horizontal ipad from the previously defined 150 units to now 0 units
		reqPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_END; 
		reqPanelConstraints.gridx = 0;
		reqPanelConstraints.gridy = 1;
		add(descriptionLabel, reqPanelConstraints);
		//Set the constraints for the "scrollPane" containing the "txtDescription" and add it to the view
		reqPanelConstraints.ipadx = 150;
		reqPanelConstraints.ipady = 150;//This tells the layout to stretch this field vertically by 150 units
		reqPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		reqPanelConstraints.gridx = 1;
		reqPanelConstraints.gridy = 1;
		add(scrollPane, reqPanelConstraints);
		//end Description

		//Type:
		//Set the constraints for the "typeLabel" and add it to the view
		reqPanelConstraints.ipadx = 0;
		reqPanelConstraints.ipady = 0;//This tells the layout to reset the vertical ipad from the previously defined 150 units to now 0 units
		reqPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
		reqPanelConstraints.gridx = 0;
		reqPanelConstraints.gridy = 2;
		add(typeLabel, reqPanelConstraints);
		//Set the constraints for the "typeBox"  and add it to the view
		reqPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		reqPanelConstraints.gridx = 1;
		reqPanelConstraints.gridy = 2;
		add(typeBox, reqPanelConstraints);
		//end Type

		//Status:		
		//Set the constraints for the "statusLabel" and add it to the view
		reqPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_END; 
		reqPanelConstraints.gridx = 0;
		reqPanelConstraints.gridy = 3;
		add(statusLabel, reqPanelConstraints);
		//Set the constraints for the "statusBox" and add it to the view
		reqPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		reqPanelConstraints.gridx = 1;
		reqPanelConstraints.gridy = 3;
		add(statusBox, reqPanelConstraints);
		//end Status

		//Priority:
		//Set the constraints for the "priorityLabel" and add it to the view
		reqPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
		reqPanelConstraints.gridx = 0;
		reqPanelConstraints.gridy = 4;
		add(priorityLabel, reqPanelConstraints);
		//Set the constraints for the "priorityBox" and add it to the view
		reqPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		reqPanelConstraints.gridx = 1;
		reqPanelConstraints.gridy = 4;
		add(priorityBox, reqPanelConstraints);
		//end Priority

		//Release number:
		//Set the constraints for the "releaseNumLabel" and add it to the view
		reqPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
		reqPanelConstraints.gridx = 0;
		reqPanelConstraints.gridy = 5;
		add(releaseNumLabel, reqPanelConstraints);
		//Set the constraints for the "txtReleaseNum" and add it to the view
		reqPanelConstraints.ipadx = 100;//This tells the layout to stretch this field horizontally by 90 units
		reqPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		reqPanelConstraints.gridx = 1;
		reqPanelConstraints.gridy = 5;
		add(txtReleaseNum, reqPanelConstraints);
		//end Release number

		//Estimate:		
		//Set the constraints for the "estimateLabel" and add it to the view
		reqPanelConstraints.ipadx = 0;//This tells the layout to reset the horizontal ipad from the previously defined 100 units to now 0 units
		reqPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
		reqPanelConstraints.gridx = 0;
		reqPanelConstraints.gridy = 6;
		add(estimateLabel, reqPanelConstraints);
		//Set the constraints for the "txtEstimate" and add it to the view
		reqPanelConstraints.ipadx = 90;
		reqPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		reqPanelConstraints.gridx = 1;
		reqPanelConstraints.gridy = 6;
		add(txtEstimate, reqPanelConstraints);
		//end Estimate

		//Actual effort:
		//Set the constraints for the "actualEffortLabel" and add it to the view
		reqPanelConstraints.ipadx = 0;//This tells the layout to reset the horizontal ipad from the previously defined 90 units to now 0 units
		reqPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
		reqPanelConstraints.gridx = 0;
		reqPanelConstraints.gridy = 7;
		add(actualEffortLabel, reqPanelConstraints);
		//Set the constraints for the "txtActualEffort" and add it to the view
		reqPanelConstraints.ipadx = 90;
		reqPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		reqPanelConstraints.gridx = 1;
		reqPanelConstraints.gridy = 7;
		add(txtActualEffort, reqPanelConstraints);
		//end Actual effort
		
		
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
	public JComboBox<String> getRequirementType() {
		return typeBox;
	}

	/**
	 * This returns the JComboBox "statusBox"
	 * @return the statusBox JComboBox
	 */
	public JComboBox<String> getRequirementStatus() {
		return statusBox;
	}

	/**
	 * This returns the JComboBox "priorityBox"
	 * @return the priorityBox JComboBox
	 */
	public JComboBox<String> getRequirementPriority() {
		return priorityBox;
	}

	/**
	 * This returns the JTextField "txtReleaseNum"
	 * @return the txtReleaseNum JTextField
	 */
	public JTextField getRequirementReleaseNumber() {
		return txtReleaseNum;
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
	 * This returns the "mode"
	 * @return the mode
	 */
	public Mode getMode() {
		return mode;
	}

	/**
	 * This sets the Mode 
	 * @param Mode m to set mode to
	 */
	public void setMode(Mode m) {
		mode = m;
	}

	/**
	 * @return the currentRequirement
	 */
	public Requirement getCurrentRequirement() {
		return currentRequirement;
	}

	/**
	 * @param currentRequirement the currentRequirement to set
	 */
	public void setCurrentRequirement(Requirement currentRequirement) {
		this.currentRequirement = currentRequirement;
	}

}
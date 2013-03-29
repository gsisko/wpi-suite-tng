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

import java.awt.Dimension;
import java.awt.FlowLayout;
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
 * 
 * It contains:
 * -a boolean indicating if input is enabled on the form
 * -a Requirement storing the requirement currently open for editing or creation
 * -a RequirementView storing the RequirementView that contains the panel
 * -an enum to indicate a "create" or "edit" mode
 * -an inner JPanel containing:
 * 		-a text field for entering a new name,
 * 		-a text area for entering a description,
 * 		-a combo box for entering a status,
 * 		-a combo box for entering a priority,
 * 		-a JNumber text field for entering a release number,
 * 		-a JNumber text field for entering an estimate,
 * 		-a JNumber text field for entering an actual effort
 * 		-Associated labels for each component
 * -Layout managers for both the inner panel (a GridBagLayout) and this panel (a FlowLayout)
 * -A constraints variable to store the constraints for the inner GridBagLayout
 * 
 * This class also contains getters and setters for many of the components and variables listed above
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
	private  JLabel nameLabel; //The label for the name text field ("txtName")
	private  JLabel descriptionLabel;//The label for the description text area ("txtDescription")
	private  JLabel typeLabel; //The label for the type combo box ("typeBox")
	private  JLabel statusLabel; //The label for the status combo box ("statusBox")
	private  JLabel priorityLabel; //The label for the priority combo box ("priorityBox")
	private  JLabel releaseNumLabel; //The label for the release number text field ("txtReleaseNum")
	private  JLabel estimateLabel; //The label for the estimate text field ("txtEstimate")
	private  JLabel actualEffortLabel; //The label for the actual effort text field ("txtActualEffort")

	//The fillable components
	private  JTextField txtName;//The name text field 
	private  JTextArea txtDescription;//The description text area
	private  JComboBox<String> typeBox;//The type combo box
	private  JComboBox<String> statusBox;//The status combo box
	private  JComboBox<String> priorityBox;//The priority combo box
	private  JNumberTextField txtReleaseNumber;//The release number text field
	private  JNumberTextField txtEstimate;//The estimate text field
	private  JNumberTextField txtActualEffort;//The actual effort text field

	//The variables to hold information about the current instance of the panel
	private Requirement currentRequirement;//Stores the requirement currently open for editing or creation
	private RequirementView parent; //Stores the RequirementView that contains the panel
	protected boolean inputEnabled;//A boolean indicating if input is enabled on the form 
	private Mode mode;// The variable to store the enum indicating whether or not you are creating at the time
	
	//The inner Jpanel	
	private JPanel innerPanel;//A JPanel to hold all the components. This allows for alignment of the components as a group
	
	//The layout managers
	protected GridBagLayout innerLayout; //The layout for the inner panel ("innerPanel")
	protected FlowLayout outerLayout;//The layout for the RequirementPanel (this holds the innerPanel)
	
	//The constraints
	private GridBagConstraints reqPanelConstraints;//The constraints variable for the layout of the innerPanel
	

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
		
		// Indicate that input is enabled
		inputEnabled = true;

		innerPanel = new JPanel();//Construct the innerPanel
		
		//Create and set the layout manager for the innerPanel that controls the positions of the components
		innerLayout = new GridBagLayout();//Create the innerLayout
		innerPanel.setLayout(innerLayout); //Set the layout for the innerPanel
		
		//Create and set the layout manager for the this RequirementPanel
		outerLayout = new FlowLayout();//Create the layout
		this.setLayout(outerLayout);//Set the layout of this panel (this instance of a RequirementPanel)
		outerLayout.setAlignment(FlowLayout.LEFT); //Set the alignment of the components of the outerLayout
		
		addComponents();//Add the components to the inner panel, and add the inner panel to this panel

		updateFields();//Update the fields to those given in the currentRequirement, if necessary.
		
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
		releaseNumLabel = new JLabel("ReleaseNumber:");
		estimateLabel = new JLabel("Estimate:");
		actualEffortLabel = new JLabel("ActualEffort:");

		//Construct the misc components
		txtName = new JTextField("");
		txtDescription = new JTextArea("", 2, 2);
		txtReleaseNumber = new JNumberTextField();
		txtEstimate = new JNumberTextField();
		txtActualEffort = new JNumberTextField();
		txtReleaseNumber.setAllowNegative(false);
		txtEstimate.setAllowNegative(false);
		txtActualEffort.setAllowNegative(false);
		
		//Set the txtDescription component to wrap
		txtDescription.setLineWrap(true);
		txtDescription.setWrapStyleWord(true);

		//Create the strings for the boxes
		String[] typeStrings = { "", "Epic", "Theme", "UserStory", "NonFunctional", "Scenario" };
		String[] statusStrings = { "New", "InProgress", "Open", "Complete", "Deleted" };
		String[] priorityStrings = { "", "High", "Medium", "Low"};

        //Construct the boxes 
		typeBox = new JComboBox<String>(typeStrings);
		statusBox = new JComboBox<String>(statusStrings);
		priorityBox = new JComboBox<String>(priorityStrings);

		
		if (mode == Mode.EDIT)//If we are editing an existing requirement
		{
			String oldStatus = (currentRequirement.getStatus()).toString();//grab the string version of the status passed in with "requirement"

			//if the oldStatus is InProgress or Completed, disable editing of the Estimate
			if (   (oldStatus.equals("InProgress"))    ||    (oldStatus.equals("Complete"))   )
				txtEstimate.setEnabled(false);
		}
		else//We are creating a new requirement
		{
			//Set the estimate and actual effort to 0 since this is a new requirement
			txtEstimate.setText("0");
			txtActualEffort.setText("0");
			
			//Set the initial selections for the boxes
			typeBox.setSelectedIndex(0);
			statusBox.setSelectedIndex(0);
			priorityBox.setSelectedIndex(0);
			
			//Set the following fields to be initially grayed out
			txtEstimate.setEnabled(false);
			txtActualEffort.setEnabled(false);
			statusBox.setEnabled(false);

		}
		
		//Set up the description scroll pane
		JScrollPane scrollPane = new JScrollPane(txtDescription);// Put the txtDescription in a scroll pane
		scrollPane.setPreferredSize(new Dimension(400,100)); //Set the initial size of the txtDescription scroll panel

		//In this last section we adjust the size and alignments of all the components and add them to the innerPanel.
		//Please read all the comments in this section if you are having trouble understanding what is going on.

		reqPanelConstraints = new GridBagConstraints();//create the constraints variable for the layout of the innerPanel
		
		//Name:
		//Set the constraints for "nameLabel" and add it to the innerPanel
		reqPanelConstraints.weightx = 0.07; //This is the weight of this field, which tells the layout manager how big this field should be in proportion to the other components
		reqPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_END;//This sets the anchor of the field, here we have told it to anchor the component to the top right of it's field
		reqPanelConstraints.insets = new Insets(15,5,0,0);  //Set the top padding to 10 units of blank space
		reqPanelConstraints.gridx = 0; //set the x coord of the cell of the layout we are describing
		reqPanelConstraints.gridy = 0;//set the y coord of the cell of the layout we are describing
		innerPanel.add(nameLabel, reqPanelConstraints);//Actually add the "nameLabel" to the layout given the previous constraints
		//Set the constraints for "txtName" and add it to the innerPanel
		reqPanelConstraints.ipadx = 150;//This tells the layout to stretch this field horizontally by 150 units
		reqPanelConstraints.weightx = 0.93;
		reqPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;//Anchor the component to the top left center of it's field
		reqPanelConstraints.gridx = 1;
		reqPanelConstraints.gridy = 0;
		innerPanel.add(txtName, reqPanelConstraints);
		//end Name

		//Description:		
		//Set the constraints for the "descriptionLabel" and add it to the innerPanel
		reqPanelConstraints.ipadx = 0;//This tells the layout to reset the horizontal ipad from the previously defined 150 units to now 0 units
		reqPanelConstraints.weightx = 0.07; 
		reqPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_END; 
		reqPanelConstraints.gridx = 0;
		reqPanelConstraints.gridy = 1;
		innerPanel.add(descriptionLabel, reqPanelConstraints);
		//Set the constraints for the "scrollPane" containing the "txtDescription" and add it to the innerPanel
		reqPanelConstraints.ipadx = 150;
		reqPanelConstraints.ipady = 150;//This tells the layout to stretch this field vertically by 150 units
		reqPanelConstraints.weightx = 0.93;
		reqPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		reqPanelConstraints.gridx = 1;
		reqPanelConstraints.gridy = 1;
		innerPanel.add(scrollPane, reqPanelConstraints);
		//end Description

		//Type:
		//Set the constraints for the "typeLabel" and add it to the innerPanel
		reqPanelConstraints.ipadx = 0;
		reqPanelConstraints.ipady = 0;//This tells the layout to reset the vertical ipad from the previously defined 150 units to now 0 units
		reqPanelConstraints.weightx = 0.07;
		reqPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
		reqPanelConstraints.gridx = 0;
		reqPanelConstraints.gridy = 2;
		innerPanel.add(typeLabel, reqPanelConstraints);
		//Set the constraints for the "typeBox"  and add it to the innerPanel
		reqPanelConstraints.weightx = 0.93;
		reqPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		reqPanelConstraints.gridx = 1;
		reqPanelConstraints.gridy = 2;
		innerPanel.add(typeBox, reqPanelConstraints);
		//end Type

		//Status:		
		//Set the constraints for the "statusLabel" and add it to the innerPanel
		reqPanelConstraints.weightx = 0.07;
		reqPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_END; 
		reqPanelConstraints.gridx = 0;
		reqPanelConstraints.gridy = 3;
		innerPanel.add(statusLabel, reqPanelConstraints);
		//Set the constraints for the "statusBox" and add it to the innerPanel
		reqPanelConstraints.weightx = 0.93;
		reqPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		reqPanelConstraints.gridx = 1;
		reqPanelConstraints.gridy = 3;
		innerPanel.add(statusBox, reqPanelConstraints);
		//end Status

		//Priority:
		//Set the constraints for the "priorityLabel" and add it to the innerPanel
		reqPanelConstraints.weightx = 0.07;
		reqPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
		reqPanelConstraints.gridx = 0;
		reqPanelConstraints.gridy = 4;
		innerPanel.add(priorityLabel, reqPanelConstraints);
		//Set the constraints for the "priorityBox" and add it to the innerPanel
		reqPanelConstraints.weightx = 0.93;
		reqPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		reqPanelConstraints.gridx = 1;
		reqPanelConstraints.gridy = 4;
		innerPanel.add(priorityBox, reqPanelConstraints);
		//end Priority

		//Release number:
		//Set the constraints for the "releaseNumLabel" and add it to the innerPanel
		reqPanelConstraints.weightx = 0.07;
		reqPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
		reqPanelConstraints.gridx = 0;
		reqPanelConstraints.gridy = 5;
		innerPanel.add(releaseNumLabel, reqPanelConstraints);
		//Set the constraints for the "txtReleaseNum" and add it to the innerPanel
		reqPanelConstraints.ipadx = 80;//This tells the layout to stretch this field horizontally by 90 units
		reqPanelConstraints.weightx = 0.93;
		reqPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		reqPanelConstraints.gridx = 1;
		reqPanelConstraints.gridy = 5;
		innerPanel.add(txtReleaseNumber, reqPanelConstraints);
		//end Release number

		//Estimate:		
		//Set the constraints for the "estimateLabel" and add it to the innerPanel
		reqPanelConstraints.ipadx = 0;//This tells the layout to reset the horizontal ipad from the previously defined 100 units to now 0 units
		reqPanelConstraints.weightx = 0.07;
		reqPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
		reqPanelConstraints.gridx = 0;
		reqPanelConstraints.gridy = 6;
		innerPanel.add(estimateLabel, reqPanelConstraints);
		//Set the constraints for the "txtEstimate" and add it to the innerPanel
		reqPanelConstraints.ipadx = 80;
		reqPanelConstraints.weightx = 0.93;
		reqPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		reqPanelConstraints.gridx = 1;
		reqPanelConstraints.gridy = 6;
		innerPanel.add(txtEstimate, reqPanelConstraints);
		//end Estimate

		//Actual effort:
		//Set the constraints for the "actualEffortLabel" and add it to the innerPanel
		reqPanelConstraints.ipadx = 0;//This tells the layout to reset the horizontal ipad from the previously defined 90 units to now 0 units
		reqPanelConstraints.weightx = 0.07;
		reqPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_END;
		reqPanelConstraints.gridx = 0;
		reqPanelConstraints.gridy = 7;
		innerPanel.add(actualEffortLabel, reqPanelConstraints);
		//Set the constraints for the "txtActualEffort" and add it to the innerPanel
		reqPanelConstraints.ipadx = 80;
		reqPanelConstraints.weightx = 0.93;
		reqPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		reqPanelConstraints.gridx = 1;
		reqPanelConstraints.gridy = 7;
		innerPanel.add(txtActualEffort, reqPanelConstraints);
		//end Actual effort
		
		this.add(innerPanel);//Add the innerPanel to this panel
		innerPanel.setMaximumSize(new Dimension(400,600));//Set the maximum size of the innerPanel
		
	}
	
	/**
	 * Sets whether input is enabled for this panel and its children. This should be used instead of 
	 * JComponent#setEnabled because setEnabled does not affect its children.
	 * 
	 * @param enabled Whether or not input is enabled.
	 */
	protected void setInputEnabled(boolean enabled){
		inputEnabled = enabled;
		
		txtName.setEnabled(enabled);
		txtDescription.setEnabled(enabled);
		typeBox.setEnabled(enabled);
		statusBox.setEnabled(enabled);
		priorityBox.setEnabled(enabled);
		txtReleaseNumber.setEnabled(enabled);
		txtEstimate.setEnabled(enabled);
		txtActualEffort.setEnabled(enabled);
		

	}
	
	/**
	 * Updates the RequirementPanel's model ("currentRequirement") to contain the values of the given Requirement and sets the 
	 * RequirementPanel's "mode" to {@link Mode#EDIT}.
	 * 
	 * @param requirement	The Requirement which contains the new values for the model ("currentRequirement").
	 */
	protected void updateModel(Requirement requirement) {
		updateModel(requirement, Mode.EDIT);
	}

	/**
	 * Updates the RequirementPanel's model ("currentRequirement") to contain the values of the given Requirement.
	 * 
	 * @param requirement	The Requirement which contains the new values for the model ("currentRequirement").
	 * @param newMode		The new "mode"
	 */
	protected void updateModel(Requirement requirement, Mode newMode){
		mode = newMode;
		
		currentRequirement.setId(requirement.getId());
		currentRequirement.setName(requirement.getName());
		currentRequirement.setDescription(requirement.getDescription());
		currentRequirement.setType(requirement.getType());
		currentRequirement.setStatus(requirement.getStatus());
		currentRequirement.setPriority(requirement.getPriority());
		currentRequirement.setReleaseNumber(requirement.getReleaseNumber());
		currentRequirement.setEstimate(requirement.getEstimate());
		currentRequirement.setActualEffort(requirement.getActualEffort());
		
		updateFields();
		innerPanel.revalidate();

		innerLayout.invalidateLayout(innerPanel);
		innerLayout.layoutContainer(innerPanel);
		innerPanel.repaint();
		
		this.revalidate();
		this.repaint();
		
		parent.refreshScrollPane();
	}
	
	/**
	 * Updates the RequirementPanel's fields to match those in the current model (stored in "currentRequirement").
	 */
	private void updateFields(){

		if (mode == Mode.EDIT)//If we are editing an existing requirement
		{
			//Enable all fields
			setInputEnabled(true);
			
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
			
			//Set the selected index of the statusBox to the correct value, based on the oldStatus
			if (oldStatus.equals("New"))
				statusBox.setSelectedIndex(0);
			else if (oldStatus.equals("InProgress"))
				statusBox.setSelectedIndex(1);
			else if (oldStatus.equals("Open"))
				statusBox.setSelectedIndex(2);
			else if (oldStatus.equals("Complete"))
				statusBox.setSelectedIndex(3);
			else // oldStatus = "Deleted"
				statusBox.setSelectedIndex(4);
			
			//Set the selected index of the priorityBox to the correct value, based on the oldPriority
			if (oldPriority.equals("High"))
				priorityBox.setSelectedIndex(1);
			else if (oldPriority.equals("Medium"))
				priorityBox.setSelectedIndex(2);
			else if (oldPriority.equals("Low"))
				priorityBox.setSelectedIndex(3);
			else // oldPriority = "NoPriority"
				priorityBox.setSelectedIndex(0);
			
			//if the oldStatus is InProgress or Completed, disable editing of the Estimate
			if (   (oldStatus.equals("InProgress"))    ||    (oldStatus.equals("Complete"))   )
				txtEstimate.setEnabled(false);
		}

		
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
	 * @param Mode m to set mode to (Mode.EDIT or Mode.CREATE)
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
	 * @param Requirement currentRequirement the currentRequirement to set
	 */
	public void setCurrentRequirement(Requirement currentRequirement) {
		this.currentRequirement = currentRequirement;
	}
	
	/**
	 * This returns the "parent" (a RequirementView) of this panel
	 * @return the parent RequirementView
	 */
	public RequirementView getParent() {
		return parent;
	}

}
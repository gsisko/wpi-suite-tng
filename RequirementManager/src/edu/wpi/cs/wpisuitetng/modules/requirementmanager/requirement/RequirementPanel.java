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
	
	//An enum to say whether or not you are creating at the time
	public enum Mode {
		CREATE,
		EDIT
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

	
	//TODO: Need some comments here. What are these? What do they do?
	private Requirement currentRequirement;
	private RequirementView parent; //Is this needed?
	
	
	private Mode mode;// The variable to store the enum indicating whether or not you are creating at the time
	
	/**
	 * The constructor for RequirementPanel;
	 * Construct the panel, the components, and add the
	 * components to the panel.
	 * TODO: add parameter descriptions
	 */
	public RequirementPanel(RequirementView view, Requirement requirement, Mode editMode) {
		
		//TODO: Need some comments here. What are these? What do they do?
		parent = view;
		currentRequirement = requirement;
		
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
		String[] typeStrings = { "NoType", "Epic", "Theme", "UserStory", "NonFunctional", "Scenario" };
		String[] statusStrings = { "New", "InProgress", "Open", "Complete", "Deleted" };
		String[] priorityStrings = { "NoPriority", "High", "Medium", "Low"};
		
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
		
		//In this last section we adjust the size and alignments of all the components and add them to the panel
		// Please read all the comments in this section if you are having trouble understanding what is going on.

		//Name:
		//Set the constraints for "nameLabel" and add it to the view
		reqPanelConstraints.fill = GridBagConstraints.HORIZONTAL; //This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		reqPanelConstraints.weightx = 0.25; //This is the weight of this field, which tells the layout manager how big this field should be in proportion to the other components
		reqPanelConstraints.anchor = GridBagConstraints.PAGE_START;//This sets the anchor of the field, here we have told it to anchor the component to the top center of it's field
		reqPanelConstraints.insets = new Insets(10,0,0,0);  //Set the top padding to 10 units of blank space
		reqPanelConstraints.gridx = 0; //set the x coord of the cell of the layout we are describing
		reqPanelConstraints.gridy = 0;//set the y coord of the cell of the layout we are describing
		add(nameLabel, reqPanelConstraints);//Actually add the "nameLabel" to the layout given the previous constraints
		//Set the constraints for "txtName" and add it to the view
		reqPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
		reqPanelConstraints.weightx = 0.75;
		reqPanelConstraints.insets = new Insets(10,5,0,0);  //Here, there will be 10 units of blank space padding on the top and 5 on the left side
		reqPanelConstraints.gridx = 1;
		reqPanelConstraints.gridwidth = 2; //Tell the layout that this field will fill 2 columns
		reqPanelConstraints.gridy = 0;
		add(txtName, reqPanelConstraints);
		//end Name
		
		//Description:		
		//Set the constraints for the "descriptionLabel" and add it to the view
		reqPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
		reqPanelConstraints.ipady = 20;//This tells the layout to stretch this field vertically by 20 units
		reqPanelConstraints.weightx = 0.25;
		reqPanelConstraints.anchor = GridBagConstraints.PAGE_START; 
		reqPanelConstraints.insets = new Insets(10,0,0,0); 
		reqPanelConstraints.gridx = 0;
		reqPanelConstraints.gridy = 1;
		add(descriptionLabel, reqPanelConstraints);
		//Set the constraints for the "scrollPane" containing the "txtDescription" and add it to the view
		reqPanelConstraints.fill = GridBagConstraints.BOTH;//This sets the constraints of this field so that the item will stretch both horizontally and vertically to fill it's area
		reqPanelConstraints.weightx = 0.75;
		reqPanelConstraints.insets = new Insets(10,5,0,0); 
		reqPanelConstraints.gridx = 1;
		reqPanelConstraints.gridwidth = 2;
		reqPanelConstraints.gridy = 1;
		add(scrollPane, reqPanelConstraints);
		//end Description
		
		//Type:
		//Set the constraints for the "typeLabel" and add it to the view
		reqPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
		reqPanelConstraints.ipady = 0;//This tells the layout to reset the vertical ipad from the previously defined 20 units to now 0 units
		reqPanelConstraints.weightx = 0.25;
		reqPanelConstraints.anchor = GridBagConstraints.PAGE_START;
		reqPanelConstraints.insets = new Insets(10,0,0,0); 
		reqPanelConstraints.gridx = 0;
		reqPanelConstraints.gridy = 2;
		add(typeLabel, reqPanelConstraints);
		//Set the constraints for the "typeBox"  and add it to the view
		reqPanelConstraints.fill = GridBagConstraints.BOTH;
		reqPanelConstraints.weightx = 0.75;
		reqPanelConstraints.insets = new Insets(10,5,0,0); 
		reqPanelConstraints.gridx = 1;
		reqPanelConstraints.gridwidth = 2;
		reqPanelConstraints.gridy = 2;
		add(typeBox, reqPanelConstraints);
		//end Type
		
		//Status:		
		//Set the constraints for the "statusLabel" and add it to the view
		reqPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
		reqPanelConstraints.weightx = 0.25;
		reqPanelConstraints.anchor = GridBagConstraints.PAGE_START; 
		reqPanelConstraints.insets = new Insets(10,0,0,0);
		reqPanelConstraints.gridx = 0;
		reqPanelConstraints.gridy = 3;
		add(statusLabel, reqPanelConstraints);
		//Set the constraints for the "statusBox" and add it to the view
		reqPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
		reqPanelConstraints.weightx = 0.75;
		reqPanelConstraints.insets = new Insets(10,5,0,0);
		reqPanelConstraints.gridx = 1;
		reqPanelConstraints.gridwidth = 2;
		reqPanelConstraints.gridy = 3;
		add(statusBox, reqPanelConstraints);
		//end Status
		
		//Priority:
		//Set the constraints for the "priorityLabel" and add it to the view
		reqPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
		reqPanelConstraints.weightx = 0.25;
		reqPanelConstraints.anchor = GridBagConstraints.PAGE_START;
		reqPanelConstraints.insets = new Insets(10,0,0,0); 
		reqPanelConstraints.gridx = 0;
		reqPanelConstraints.gridy = 4;
		add(priorityLabel, reqPanelConstraints);
		//Set the constraints for the "priorityBox" and add it to the view
		reqPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
		reqPanelConstraints.weightx = 0.75;
		reqPanelConstraints.insets = new Insets(10,5,0,0);
		reqPanelConstraints.gridx = 1;
		reqPanelConstraints.gridwidth = 2;
		reqPanelConstraints.gridy = 4;
		add(priorityBox, reqPanelConstraints);
		//end Priority
		
		//Release number:
		//Set the constraints for the "releaseNumLabel" and add it to the view
		reqPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
		reqPanelConstraints.weightx = 0.25;
		reqPanelConstraints.anchor = GridBagConstraints.PAGE_START;
		reqPanelConstraints.insets = new Insets(10,0,0,0);
		reqPanelConstraints.gridx = 0;
		reqPanelConstraints.gridy = 5;
		add(releaseNumLabel, reqPanelConstraints);
		//Set the constraints for the "txtReleaseNum" and add it to the view
		reqPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
		reqPanelConstraints.weightx = 0.75;
		reqPanelConstraints.insets = new Insets(10,5,0,0);
		reqPanelConstraints.gridx = 1;
		reqPanelConstraints.gridwidth = 2;
		reqPanelConstraints.gridy = 5;
		add(txtReleaseNum, reqPanelConstraints);
		//end Release number
		
		//Estimate:		
		//Set the constraints for the "estimateLabel" and add it to the view
		reqPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
		reqPanelConstraints.weightx = 0.25;
		reqPanelConstraints.anchor = GridBagConstraints.PAGE_START;
		reqPanelConstraints.insets = new Insets(10,0,0,0);
		reqPanelConstraints.gridx = 0;
		reqPanelConstraints.gridy = 6;
		add(estimateLabel, reqPanelConstraints);
		//Set the constraints for the "txtEstimate" and add it to the view
		reqPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
		reqPanelConstraints.weightx = 0.75;
		reqPanelConstraints.insets = new Insets(10,5,0,0);
		reqPanelConstraints.gridx = 1;
		reqPanelConstraints.gridwidth = 2;
		reqPanelConstraints.gridy = 6;
		add(txtEstimate, reqPanelConstraints);
		//end Estimate

		//Actual effort:
		//Set the constraints for the "actualEffortLabel" and add it to the view
		reqPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
		reqPanelConstraints.weightx = 0.25;
		reqPanelConstraints.anchor = GridBagConstraints.PAGE_START;
		reqPanelConstraints.insets = new Insets(10,0,0,0);
		reqPanelConstraints.gridx = 0;
		reqPanelConstraints.gridy = 7;
		add(actualEffortLabel, reqPanelConstraints);
		//Set the constraints for the "txtActualEffort" and add it to the view
		reqPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
		reqPanelConstraints.weightx = 0.75;
		reqPanelConstraints.insets = new Insets(10,5,0,0);
		reqPanelConstraints.gridx = 1;
		reqPanelConstraints.gridwidth = 2;
		reqPanelConstraints.gridy = 7;
		add(txtActualEffort, reqPanelConstraints);
		//end Actual effort

		
		//Save button:
		//Set the constraints for the "btnSave" and add it to the view
		leftConstraints.fill = GridBagConstraints.NONE;//This sets the constraints of this field so that the item will not stretch to fill it's area
		leftConstraints.weighty = 1;//This is the weight of this field, which tells the layout manager how big this field should be in proportion to the other components
		leftConstraints.anchor = GridBagConstraints.PAGE_END; //This sets the anchor of the field, here we have told it to anchor the component to the bottom right of it's field
		leftConstraints.insets = new Insets(10,0,0,0);//Set the top padding to 10 units
		leftConstraints.gridx = 2;//Set the x coord of the cell of the layout we are describing
		leftConstraints.gridy = 8;//Set the y coord of the cell of the layout we are describing
		leftPanel.add(btnSave, leftConstraints);//Actually add the "btnSave" to the layout given the previous constraints
		//end Save button
		
		//end LEFT PANEL
		
		
		//RIGHT PANEL:
		
		rightPanel = new JPanel();
		
		// Set the layout
		rightPanel.setLayout(new BorderLayout());
		
		// Construct the table model
		resultsTableModel = new ResultsTableModel();
		
		// Construct the table and configure it
		resultsTable = new JTable(resultsTableModel);
		resultsTable.setAutoCreateRowSorter(true);
		resultsTable.setFillsViewportHeight(true);
		resultsTable.setDefaultRenderer(Date.class, new DateTableCellRenderer());
		
		// Add a listener for row clicks
		//resultsTable.addMouseListener(new RetrieveRequirementController(this));
		
		// Put the table in a scroll pane
		JScrollPane resultsScrollPane = new JScrollPane(resultsTable);
		resultsScrollPane.setPreferredSize(new Dimension(700,400));
		
		btnNew = new JButton("New Requirement");
		btnRefresh = new JButton("Refresh");
		
		//controller = new RetrieveAllRequirementsController(this);
		btnRefresh.setAction(new RefreshRequirementsAction(controller));
		
		// Construct an action listener and add it to the create button
		btnNew.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
            	// set all of the UI fields appropriately when the "create requirement" button is clicked
            	mode = Mode.CREATE;
            	
            	txtName.setText("");
            	txtDescription.setText("");
            	typeBox.setSelectedIndex(0);
            	statusBox.setSelectedIndex(0);
            	priorityBox.setSelectedIndex(0);
            	txtReleaseNum.setText("");
            	txtEstimate.setText("0");
            	txtActualEffort.setText("0");
            	
            	btnSave.setText("Create");
            	btnSave.setEnabled(true);
            	
            	txtName.setEnabled(true);
            	txtDescription.setEnabled(true);
            	typeBox.setEnabled(true);
            	statusBox.setEnabled(false);
            	priorityBox.setEnabled(true);
            	txtReleaseNum.setEnabled(true);
            	txtEstimate.setEnabled(false);
            	txtActualEffort.setEnabled(false);
            }
        });
		
		//Actually add the list to the right panel
		rightPanel.add(resultsScrollPane, BorderLayout.PAGE_START);
		
		//Actually add the create button to the right panel
		rightPanel.add(btnNew, BorderLayout.LINE_START);
		
		//Actually add the refresh button to the right panel
		rightPanel.add(btnRefresh, BorderLayout.LINE_END);
		
		//end RIGHT PANEL
		
		//MAIN PANEL
		//Set the layout manager for the main requirement panel
		setLayout(new GridBagLayout()); //set the layout
		GridBagConstraints mainConstraints = new GridBagConstraints();//create the constraints variable
		
		//Add the two panels to the view
		mainConstraints.anchor = GridBagConstraints.WEST;
		add(leftPanel);

		JLabel blankLabel = new JLabel("                 "); //add a blank label to create space between the panels
		mainConstraints.anchor = GridBagConstraints.CENTER;
		add(blankLabel);
		
		mainConstraints.anchor = GridBagConstraints.EAST;
		add(rightPanel);
		
		
		//end MAIN PANEL
		

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

/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Paula Rudy
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controllers.SaveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.RefreshRequirementsAction;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.RetrieveAllRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.RetrieveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.ResultsTableModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.DateTableCellRenderer;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/**
 * This class is a JPanel. 
 * It contains 2 sub panels:
 * A left panel containing:
 * 		-a text field for entering a new name,
 * 		-a text area for entering a description,
 * 		-a combo box for entering a status,
 * 		-a combo box for entering a priority,
 * 		-a JNumber text field for entering a release number,
 * 		-a JNumber text field for entering an estimate,
 * 		-a JNumber text field for entering an actual effort,
 *	...and a save button for submitting.
 * A right panel containing:
 * 		-a table containing a selectable list of requirements
 * 		-a button to refresh that table
 * 	...and a create button to enable the left panel fields for creation of a new requirement
 * 
 * @author Paula Rudy
 *
 */
@SuppressWarnings("serial")
public class RequirementPanel extends JPanel {
	
	//The panels
	private JPanel leftPanel;
	private JPanel rightPanel;
	
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
	private final JComboBox typeBox;//The type combo box
	private final JComboBox statusBox;//The status combo box
	private final JComboBox priorityBox;//The priority combo box
	private final JNumberTextField txtReleaseNum;//The release number text field
	private final JNumberTextField txtEstimate;//The estimate text field
	private final JNumberTextField txtActualEffort;//The actual effort text field

	//The buttons
	private final JButton btnSave; //The "save" button
	private final JButton btnNew;//The "create" button
	private final JButton btnRefresh;//The "refresh" button
	
	private ResultsTableModel resultsTableModel;
	private JTable resultsTable;
	private RetrieveAllRequirementsController controller;
	
	private boolean createNew = false;
	
	private Requirement currentRequirement;
	
	/**
	 * The constructor for RequirementPanel;
	 * Construct the panel, the components, and add the
	 * components to the panel.
	 */
	@SuppressWarnings("unchecked")
	public RequirementPanel() {
		
		leftPanel = new JPanel();
		
		//LEFT PANEL:
		
		//Construct the labels
		nameLabel = new JLabel("Name:");
		descriptionLabel = new JLabel("Description:");
		typeLabel = new JLabel("Type:");
		statusLabel = new JLabel("Status:");
		priorityLabel = new JLabel("Priority:");
		releaseNumLabel = new JLabel("Release Number:");
		estimateLabel = new JLabel("Estimate:");
		actualEffortLabel = new JLabel("Actual Effort:");
		
		//Construct the misc components
		txtName = new JTextField(/*"Enter a name here."*/"");
		txtDescription = new JTextArea(/*"Enter a description here."*/"", 2, 2);
		btnSave = new JButton("Update");
		txtReleaseNum = new JNumberTextField();
		txtEstimate = new JNumberTextField();
		txtActualEffort = new JNumberTextField();
		txtEstimate.setText("0");
		txtActualEffort.setText("0");
		txtReleaseNum.setAllowNegative(false);
		txtEstimate.setAllowNegative(false);
		txtActualEffort.setAllowNegative(false);
		
		txtName.setEnabled(false);
		txtDescription.setEnabled(false);
		txtReleaseNum.setEnabled(false);
		txtEstimate.setEnabled(false);
		txtActualEffort.setEnabled(false);
		
		txtDescription.setLineWrap(true);
		txtDescription.setWrapStyleWord(true);
		
		//Create the strings for the boxes
		String[] typeStrings = { "NoType", "Epic", "Theme", "UserStory", "NonFunctional", "Scenario" };
		String[] statusStrings = { "New", "InProgress", "Open", "Complete", "Deleted" };
		String[] priorityStrings = { "NoPriority", "High", "Medium", "Low"};
		
        //Construct the boxes 
		typeBox = new JComboBox(typeStrings);
		statusBox = new JComboBox(statusStrings);
		priorityBox = new JComboBox(priorityStrings);
		
		//Set the initial selections for the boxes
		typeBox.setSelectedIndex(0);
		typeBox.setEnabled(false);
		statusBox.setSelectedIndex(0);
		statusBox.setEnabled(false);
		priorityBox.setSelectedIndex(0);
		priorityBox.setEnabled(false);
		
		btnSave.addActionListener(new SaveRequirementController(this));
		btnSave.setEnabled(false);
		
		// Set the layout manager that controls the positions of the components
		leftPanel.setLayout(new GridBagLayout()); //set the layout
		GridBagConstraints leftConstraints = new GridBagConstraints();//create the constraints variable
		
		//Set up the description scroll pane
		JScrollPane scrollPane = new JScrollPane(txtDescription);// Put the txtDescription in a scroll pane
		scrollPane.setPreferredSize(new Dimension(400,100)); //Set the initial size of the txtDescription scroll panel
		
		
		// Adjust the size and alignments of all the components (of the left panel) and add them to the left panel:

		btnSave.setAlignmentX(Component.CENTER_ALIGNMENT); //set the alignment of the "save" button to center in it's section

		//Name:
		//Set the constraints for "nameLabel" and add it to the view
		leftConstraints.fill = GridBagConstraints.HORIZONTAL; //This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		leftConstraints.weightx = 0.25; //This is the weight of this field, which tells the layout manager how big this field should be in proportion to the other components
		leftConstraints.anchor = GridBagConstraints.PAGE_START;//This sets the anchor of the field, here we have told it to anchor the component to the top center of it's field
		leftConstraints.insets = new Insets(10,0,0,0);  //Set the top padding to 10 units of blank space
		leftConstraints.gridx = 0; //set the x coord of the cell of the layout we are describing
		leftConstraints.gridy = 0;//set the y coord of the cell of the layout we are describing
		leftPanel.add(nameLabel, leftConstraints);//Actually add the "nameLabel" to the layout given the previous constraints
		//Set the constraints for "txtName" and add it to the view
		leftConstraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		leftConstraints.weightx = 0.75;//This is the weight of this field, which tells the layout manager how big this field should be in proportion to the other components
		leftConstraints.insets = new Insets(10,5,0,0);  //Set the padding; here, there will be 10 units of blank space padding on the top and 5 on the left side
		leftConstraints.gridx = 1;//Set the x coord of the cell of the layout we are describing
		leftConstraints.gridwidth = 2; //Tell the layout that this field will fill 2 columns
		leftConstraints.gridy = 0;//Set the y coord of the cell of the layout we are describing
		leftPanel.add(txtName, leftConstraints);//Actually add the "txtName" to the layout given the previous constraints
		//end Name
		
		//Description:		
		//Set the constraints for the "descriptionLabel" and add it to the view
		leftConstraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		leftConstraints.ipady = 20;//This tells the layout to stretch this field vertically by 20 units
		leftConstraints.weightx = 0.25;//This is the weight of this field, which tells the layout manager how big this field should be in proportion to the other components
		leftConstraints.anchor = GridBagConstraints.PAGE_START; //This sets the anchor of the field, here we have told it to anchor the component to the top center of it's field
		leftConstraints.insets = new Insets(10,0,0,0);  //Set the top padding to 10 units  of blank space
		leftConstraints.gridx = 0;//Set the x coord of the cell of the layout we are describing
		leftConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		leftPanel.add(descriptionLabel, leftConstraints);//Actually add the "descriptionLabel" to the layout given the previous constraints
		//Set the constraints for the "scrollPane" containing the "txtDescription" and add it to the view
		leftConstraints.fill = GridBagConstraints.BOTH;//This sets the constraints of this field so that the item will stretch both horizontally and vertically to fill it's area
		leftConstraints.weightx = 0.75;//This is the weight of this field, which tells the layout manager how big this field should be in proportion to the other components
		leftConstraints.insets = new Insets(10,5,0,0); //Set the padding; here, there will be 10 units of blank space padding on the top and 5 on the left side
		leftConstraints.gridx = 1;//Set the x coord of the cell of the layout we are describing
		leftConstraints.gridwidth = 2;   //Tell the layout that this field will fill 2 columns
		leftConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		leftPanel.add(scrollPane, leftConstraints);//Actually add the "scrollPane" to the layout given the previous constraints
		//end Description
		
		//Type:
		//Set the constraints for the "typeLabel" and add it to the view
		leftConstraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		leftConstraints.ipady = 0;//This tells the layout to reset the vertical ipad from the previously defined 20 units to now 0 units
		leftConstraints.weightx = 0.25;//This is the weight of this field, which tells the layout manager how big this field should be in proportion to the other components
		leftConstraints.anchor = GridBagConstraints.PAGE_START; //This sets the anchor of the field, here we have told it to anchor the component to the top center of it's field
		leftConstraints.insets = new Insets(10,0,0,0);  //Set the top padding to 10 units  of blank space
		leftConstraints.gridx = 0;//Set the x coord of the cell of the layout we are describing
		leftConstraints.gridy = 2;//Set the y coord of the cell of the layout we are describing
		leftPanel.add(typeLabel, leftConstraints);//Actually add the "descriptionLabel" to the layout given the previous constraints
		//Set the constraints for the "typeBox"  and add it to the view
		leftConstraints.fill = GridBagConstraints.BOTH;//This sets the constraints of this field so that the item will stretch both horizontally and vertically to fill it's area
		leftConstraints.weightx = 0.75;//This is the weight of this field, which tells the layout manager how big this field should be in proportion to the other components
		leftConstraints.insets = new Insets(10,5,0,0); //Set the padding; here, there will be 10 units of blank space padding on the top and 5 on the left side
		leftConstraints.gridx = 1;//Set the x coord of the cell of the layout we are describing
		leftConstraints.gridwidth = 2;   //Tell the layout that this field will fill 2 columns
		leftConstraints.gridy = 2;//Set the y coord of the cell of the layout we are describing
		leftPanel.add(typeBox, leftConstraints);//Actually add the "typeBox" to the layout given the previous constraints
		//end Type
		
		//Status:		
		//Set the constraints for the "statusLabel" and add it to the view
		leftConstraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		leftConstraints.weightx = 0.25;//This is the weight of this field, which tells the layout manager how big this field should be in proportion to the other components
		leftConstraints.anchor = GridBagConstraints.PAGE_START; //This sets the anchor of the field, here we have told it to anchor the component to the top center of it's field
		leftConstraints.insets = new Insets(10,0,0,0);  //Set the top padding to 10 units  of blank space
		leftConstraints.gridx = 0;//Set the x coord of the cell of the layout we are describing
		leftConstraints.gridy = 3;//Set the y coord of the cell of the layout we are describing
		leftPanel.add(statusLabel, leftConstraints);//Actually add the "statusLabel" to the layout given the previous constraints
		//Set the constraints for the "statusBox" and add it to the view
		leftConstraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		leftConstraints.weightx = 0.75;//This is the weight of this field, which tells the layout manager how big this field should be in proportion to the other components
		leftConstraints.insets = new Insets(10,5,0,0); //Set the padding; here, there will be 10 units of blank space padding on the top and 5 on the left side
		leftConstraints.gridx = 1;//Set the x coord of the cell of the layout we are describing
		leftConstraints.gridwidth = 2;   //Tell the layout that this field will fill 2 columns
		leftConstraints.gridy = 3;//Set the y coord of the cell of the layout we are describing
		leftPanel.add(statusBox, leftConstraints);//Actually add the "statusBox" to the layout given the previous constraints
		//end Status
		
		//Priority:
		//Set the constraints for the "priorityLabel" and add it to the view
		leftConstraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		leftConstraints.weightx = 0.25;//This is the weight of this field, which tells the layout manager how big this field should be in proportion to the other components
		leftConstraints.anchor = GridBagConstraints.PAGE_START; //This sets the anchor of the field, here we have told it to anchor the component to the top center of it's field
		leftConstraints.insets = new Insets(10,0,0,0);  //Set the top padding to 10 units  of blank space
		leftConstraints.gridx = 0;//Set the x coord of the cell of the layout we are describing
		leftConstraints.gridy = 4;//Set the y coord of the cell of the layout we are describing
		leftPanel.add(priorityLabel, leftConstraints);//Actually add the "priorityLabel" to the layout given the previous constraints
		//Set the constraints for the "priorityBox" and add it to the view
		leftConstraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		leftConstraints.weightx = 0.75;//This is the weight of this field, which tells the layout manager how big this field should be in proportion to the other components
		leftConstraints.insets = new Insets(10,5,0,0); //Set the padding; here, there will be 10 units of blank space padding on the top and 5 on the left side
		leftConstraints.gridx = 1;//Set the x coord of the cell of the layout we are describing
		leftConstraints.gridwidth = 2;   //Tell the layout that this field will fill 2 columns
		leftConstraints.gridy = 4;//Set the y coord of the cell of the layout we are describing
		leftPanel.add(priorityBox, leftConstraints);//Actually add the "priorityBox" to the layout given the previous constraints
		//end Priority
		
		//Release number:
		//Set the constraints for the "releaseNumLabel" and add it to the view
		leftConstraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		leftConstraints.weightx = 0.25;//This is the weight of this field, which tells the layout manager how big this field should be in proportion to the other components
		leftConstraints.anchor = GridBagConstraints.PAGE_START; //This sets the anchor of the field, here we have told it to anchor the component to the top center of it's field
		leftConstraints.insets = new Insets(10,0,0,0);  //Set the top padding to 10 units  of blank space
		leftConstraints.gridx = 0;//Set the x coord of the cell of the layout we are describing
		leftConstraints.gridy = 5;//Set the y coord of the cell of the layout we are describing
		leftPanel.add(releaseNumLabel, leftConstraints);//Actually add the "releaseNumLabel" to the layout given the previous constraints
		//Set the constraints for the "txtReleaseNum" and add it to the view
		leftConstraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		leftConstraints.weightx = 0.75;//This is the weight of this field, which tells the layout manager how big this field should be in proportion to the other components
		leftConstraints.insets = new Insets(10,5,0,0); //Set the padding; here, there will be 10 units of blank space padding on the top and 5 on the left side
		leftConstraints.gridx = 1;//Set the x coord of the cell of the layout we are describing
		leftConstraints.gridwidth = 2;   //Tell the layout that this field will fill 2 columns
		leftConstraints.gridy = 5;//Set the y coord of the cell of the layout we are describing
		leftPanel.add(txtReleaseNum, leftConstraints);//Actually add the "txtReleaseNum" to the layout given the previous constraints
		//end Release number
		
		//Estimate:		
		//Set the constraints for the "estimateLabel" and add it to the view
		leftConstraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		leftConstraints.weightx = 0.25;//This is the weight of this field, which tells the layout manager how big this field should be in proportion to the other components
		leftConstraints.anchor = GridBagConstraints.PAGE_START; //This sets the anchor of the field, here we have told it to anchor the component to the top center of it's field
		leftConstraints.insets = new Insets(10,0,0,0);  //Set the top padding to 10 units  of blank space
		leftConstraints.gridx = 0;//Set the x coord of the cell of the layout we are describing
		leftConstraints.gridy = 6;//Set the y coord of the cell of the layout we are describing
		leftPanel.add(estimateLabel, leftConstraints);//Actually add the "estimateLabel" to the layout given the previous constraints
		//Set the constraints for the "txtEstimate" and add it to the view
		leftConstraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		leftConstraints.weightx = 0.75;//This is the weight of this field, which tells the layout manager how big this field should be in proportion to the other components
		leftConstraints.insets = new Insets(10,5,0,0); //Set the padding; here, there will be 10 units of blank space padding on the top and 5 on the left side
		leftConstraints.gridx = 1;//Set the x coord of the cell of the layout we are describing
		leftConstraints.gridwidth = 2;   //Tell the layout that this field will fill 2 columns
		leftConstraints.gridy = 6;//Set the y coord of the cell of the layout we are describing
		leftPanel.add(txtEstimate, leftConstraints);//Actually add the "txtEstimate" to the layout given the previous constraints
		//end Estimate

		//Actual effort:
		//Set the constraints for the "actualEffortLabel" and add it to the view
		leftConstraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		leftConstraints.weightx = 0.25;//This is the weight of this field, which tells the layout manager how big this field should be in proportion to the other components
		leftConstraints.anchor = GridBagConstraints.PAGE_START; //This sets the anchor of the field, here we have told it to anchor the component to the top center of it's field
		leftConstraints.insets = new Insets(10,0,0,0);  //Set the top padding to 10 units  of blank space
		leftConstraints.gridx = 0;//Set the x coord of the cell of the layout we are describing
		leftConstraints.gridy = 7;//Set the y coord of the cell of the layout we are describing
		leftPanel.add(actualEffortLabel, leftConstraints);//Actually add the "actualEffortLabel" to the layout given the previous constraints
		//Set the constraints for the "txtActualEffort" and add it to the view
		leftConstraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		leftConstraints.weightx = 0.75;//This is the weight of this field, which tells the layout manager how big this field should be in proportion to the other components
		leftConstraints.insets = new Insets(10,5,0,0); //Set the padding; here, there will be 10 units of blank space padding on the top and 5 on the left side
		leftConstraints.gridx = 1;//Set the x coord of the cell of the layout we are describing
		leftConstraints.gridwidth = 2;   //Tell the layout that this field will fill 2 columns
		leftConstraints.gridy = 7;//Set the y coord of the cell of the layout we are describing
		leftPanel.add(txtActualEffort, leftConstraints);//Actually add the "txtActualEffort" to the layout given the previous constraints
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
		resultsTable.addMouseListener(new RetrieveRequirementController(this));
		
		// Put the table in a scroll pane
		JScrollPane resultsScrollPane = new JScrollPane(resultsTable);
		
		btnNew = new JButton("New Requirement");
		btnRefresh = new JButton("Refresh");
		
		controller = new RetrieveAllRequirementsController(this);
		btnRefresh.setAction(new RefreshRequirementsAction(controller));
		
		// Construct an action listener and add it to the create button
		btnNew.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
            	createNew = true;
            	
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
            	
                //Execute when button is pressed
                System.out.println("You clicked the create button");
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

		JLabel blankLabel = new JLabel("                 ");
		mainConstraints.anchor = GridBagConstraints.CENTER;
		add(blankLabel);
		
		mainConstraints.anchor = GridBagConstraints.EAST;
		add(rightPanel);
		
		
		//end MAIN PANEL
		
	}
	
//	/**
//	 * This returns the JTextField "txtName"
//	 * @return the txtName JTextField
//	 */
	public JTextField getRequirementName() {
		return txtName;
	}
	
//	/**
//	 * This returns the JTextArea "txtDescription"
//	 * @return the txtDescription JTextArea
//	 */
	public JTextArea getRequirementDescription() {
		return txtDescription;
	}
	
//	/**
//	 * This returns the JComboBox "typeBox"
//	 * @return the typeBox JComboBox
//	 */
	public JComboBox getRequirementType() {
		return typeBox;
	}

//	/**
//	 * This returns the JComboBox "statusBox"
//	 * @return the statusBox JComboBox
//	 */
	public JComboBox getRequirementStatus() {
		return statusBox;
	}

//	/**
//	 * This returns the JComboBox "priorityBox"
//	 * @return the priorityBox JComboBox
//	 */
	public JComboBox getRequirementPriority() {
		return priorityBox;
	}

//	/**
//	 * This returns the JTextField "txtReleaseNum"
//	 * @return the txtReleaseNum JTextField
//	 */
	public JTextField getRequirementReleaseNumber() {
		return txtReleaseNum;
	}

//	/**
//	 * This returns the JTextField "txtEstimate"
//	 * @return the txtEstimate JTextField
//	 */
	public JTextField getRequirementEstimate() {
		return txtEstimate;
	}

//	/**
//	 * This returns the JTextField "txtActualEffort"
//	 * @return the txtActualEffort JTextField
//	 */
	public JTextField getRequirementActualEffort() {
		return txtActualEffort;
	}
	
	public RetrieveAllRequirementsController getRefreshController() {
		return controller;
	}
	
	public JButton getSaveButton() {
		return btnSave;
	}
	
	public ResultsTableModel getModel() {
		return resultsTableModel;
	}
	
	public boolean getCreateNew() {
		return createNew;
	}

	public void setCreateNew(boolean b) {
		createNew = b;
	}
	
	public Requirement getCurrentRequirement() {
		return currentRequirement;
	}
	
	public void setCurrentRequirement(Requirement r) {
		currentRequirement = r;
	}
	
}

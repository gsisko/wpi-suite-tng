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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.RetrieveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.ResultsTableModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.DateTableCellRenderer;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ResultsPanel;

/**
 * This class is a JPanel. It contains a text field
 * for entering a new name, a text area for entering a description,
 * and a save button for submitting.
 * 
 * @author Paula Rudy
 *
 */
@SuppressWarnings("serial")
public class RequirementPanel extends JPanel {
	
	//The labels
	private final JLabel nameLabel; //The label for the name text field ("txtName")
	private final JLabel descriptionLabel;//The label for the description text area ("txtDescription")
	
	//The text field/areas
	private final JTextField txtName;//The name text field 
	private final JTextArea txtDescription;//The description text area

	//The buttons
	private final JButton btnSave; //The "save" button
	
	private ResultsTableModel resultsTableModel;
	private JTable resultsTable;
	
	/**
	 * The constructor for RequirementPanel;
	 * Construct the panel, the components, and add the
	 * components to the panel.
	 */
	public RequirementPanel() {
		
		JPanel leftPanel = new JPanel();
		ResultsPanel rightPanel = new ResultsPanel();
		
		// Construct the components to be displayed
		txtName = new JTextField("Enter a name here.");
		txtDescription = new JTextArea("Enter a description here.", 6, 6);
		btnSave = new JButton("Save");
		
		nameLabel = new JLabel("Name:");
		descriptionLabel = new JLabel("Description:");
		
		// Construct an action listener and add it to the save button
		btnSave.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                System.out.println("You clicked the button");//TODO: replace this with a real action
            }
        });      
		
		// Set the layout manager of this panel that controls the positions of the components
		leftPanel.setLayout(new GridBagLayout()); //set the layout
		GridBagConstraints constraints = new GridBagConstraints();//create the constraints variable
		
		
		JScrollPane scrollPane = new JScrollPane(txtDescription);// Put the txtDescription in a scroll pane
		scrollPane.setPreferredSize(new Dimension(500,400)); //Set the initial size of the txtDescription scroll pane
		
		// Clear the contents of the description text area when the user clicks on it
		txtDescription.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				txtDescription.setText("");
			}
		});
		
		// Clear the contents of the name text field when the user clicks on it
		txtName.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				txtName.setText("");
				}
		});
		
		// Adjust the size and alignments of all the components and add them to the view:
		btnSave.setAlignmentX(Component.CENTER_ALIGNMENT); //set the alignment of the "save" button to center in it's section

		//Set the constraints for "nameLabel" and add it to the view
		constraints.fill = GridBagConstraints.HORIZONTAL; //This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		constraints.weightx = 0.25; //This is the weight of this field, which tells the layout manager how big this field should be in proportion to the other components
		constraints.anchor = GridBagConstraints.PAGE_START;//This sets the anchor of the field, here we have told it to anchor the component to the top center of it's field
		constraints.insets = new Insets(10,0,0,0);  //Set the top padding to 10 units of blank space
		constraints.gridx = 0; //set the x coord of the cell of the layout we are describing
		constraints.gridy = 0;//set the y coord of the cell of the layout we are describing
		leftPanel.add(nameLabel, constraints);//Actually add the "nameLabel" to the layout given the previous constraints
		
		//Set the constraints for "txtName" and add it to the view
		constraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		constraints.weightx = 0.75;//This is the weight of this field, which tells the layout manager how big this field should be in proportion to the other components
		constraints.insets = new Insets(10,5,0,0);  //Set the padding; here, there will be 10 units of blank space padding on the top and 5 on the left side
		constraints.gridx = 1;//Set the x coord of the cell of the layout we are describing
		constraints.gridwidth = 2; //Tell the layout that this field will fill 2 columns
		constraints.gridy = 0;//Set the y coord of the cell of the layout we are describing
		leftPanel.add(txtName, constraints);//Actually add the "txtName" to the layout given the previous constraints
		
		//Set the constraints for the "descriptionLabel" and add it to the view
		constraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		constraints.ipady = 20;//This tells the layout to stretch this field vertically by 70 units
		constraints.weightx = 0.25;//This is the weight of this field, which tells the layout manager how big this field should be in proportion to the other components
		constraints.anchor = GridBagConstraints.PAGE_START; //This sets the anchor of the field, here we have told it to anchor the component to the top center of it's field
		constraints.insets = new Insets(10,0,0,0);  //Set the top padding to 10 units  of blank space
		constraints.gridx = 0;//Set the x coord of the cell of the layout we are describing
		constraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		leftPanel.add(descriptionLabel, constraints);//Actually add the "txtName" to the layout given the previous constraints
		
		//Set the constraints for the "scrollPane" containing the "txtDescription" and add it to the view
		constraints.fill = GridBagConstraints.BOTH;//This sets the constraints of this field so that the item will stretch both horizontally and vertically to fill it's area
		constraints.weightx = 0.75;//This is the weight of this field, which tells the layout manager how big this field should be in proportion to the other components
		constraints.insets = new Insets(10,5,0,0); //Set the padding; here, there will be 10 units of blank space padding on the top and 5 on the left side
		constraints.gridx = 1;//Set the x coord of the cell of the layout we are describing
		constraints.gridwidth = 2;   //Tell the layout that this field will fill 2 columns
		constraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		leftPanel.add(scrollPane, constraints);//Actually add the "scrollPane" to the layout given the previous constraints
		
		//Set the constraints for the "btnSave" and add it to the view
		constraints.fill = GridBagConstraints.NONE;//This sets the constraints of this field so that the item will not stretch to fill it's area
		constraints.ipady = 0;//This tells the layout to reset the vertical ipad from the previously defined 70 units to now 0 units
		constraints.weighty = 1;//This is the weight of this field, which tells the layout manager how big this field should be in proportion to the other components
		constraints.anchor = GridBagConstraints.PAGE_END; //This sets the anchor of the field, here we have told it to anchor the component to the bottom right of it's field
		constraints.insets = new Insets(10,0,0,0);//Set the top padding to 10 units
		constraints.gridx = 2;//Set the x coord of the cell of the layout we are describing
		constraints.gridy = 2;//Set the y coord of the cell of the layout we are describing
		leftPanel.add(btnSave, constraints);//Actually add the "btnSave" to the layout given the previous constraints
		
		
		
		
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
		resultsTable.addMouseListener(new RetrieveRequirementController(rightPanel));
		
		// Put the table in a scroll pane
		JScrollPane resultsScrollPane = new JScrollPane(resultsTable);
		
		rightPanel.add(resultsScrollPane);
		
		constraints.anchor = GridBagConstraints.WEST;
		add(leftPanel);
		
		constraints.anchor = GridBagConstraints.EAST;
		add(rightPanel);
		
	}
	
//	/**
//	 * This returns the contents of the JTextField "txtName"
//	 * @return the txtName JTextField
//	 */
	public String getName() {
		return txtName.getText();
	}
	
//	/**
//	 * This returns the contents of the JTextArea "txtDescription"
//	 * @return the txtDescription JTextArea
//	 */
	public String getDescription() {
		return txtDescription.getText();
	}
	
}

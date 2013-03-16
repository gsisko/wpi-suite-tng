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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;

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
	

	private final JTextArea txtDescription;
	private final JTextField txtName;


	private final JButton btnSave;
	
	private final JLabel nameLabel;
	private final JLabel descriptionLabel;


	/**
	 * Construct the panel, the components, and add the
	 * components to the panel.
	 */
	public RequirementPanel() {
		
		// Construct the components to be displayed
		txtDescription = new JTextArea("Enter a description here.", 6, 20);
		txtName = new JTextField("Enter a name here.");
		btnSave = new JButton("Save");
		
		nameLabel = new JLabel("Name:");
		descriptionLabel = new JLabel("Description:");
		
		// Construct an action listener and add it to the save button
		btnSave.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                System.out.println("You clicked the button");//to be replaced
            }
        });      
		
		// Set the layout manager of this panel that controls the positions of the components
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		// Put the txtDescription in a scroll pane
		JScrollPane scrollPane = new JScrollPane(txtDescription);
		scrollPane.setPreferredSize(new Dimension(500,400));
		
		// Clear the contents of the text field when the user clicks on it
		txtDescription.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				txtDescription.setText("");
			}
		});
		
		// Clear the contents of the text field when the user clicks on it
		txtName.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				txtName.setText("");
				}
		});
		
		// Adjust sizes and alignments
		btnSave.setAlignmentX(Component.CENTER_ALIGNMENT);


		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.25;
		constraints.anchor = GridBagConstraints.PAGE_START;
		constraints.insets = new Insets(10,0,0,0);  //top padding
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(nameLabel, constraints);
		
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.75;
		constraints.insets = new Insets(10,5,0,0);  //side padding
		constraints.gridx = 1;
		constraints.gridwidth = 2;   //2 columns wide
		constraints.gridy = 0;
		add(txtName, constraints);
		
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.ipady = 70;
		constraints.weightx = 0.25;
		constraints.anchor = GridBagConstraints.PAGE_START; 
		constraints.insets = new Insets(10,0,0,0);  //top padding
		constraints.gridx = 0;
		constraints.gridy = 1;
		add(descriptionLabel, constraints);
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 0.75;
		constraints.insets = new Insets(10,5,0,0);  //side padding
		constraints.gridx = 1;
		constraints.gridwidth = 2;   //2 columns wide
		constraints.gridy = 1;
		add(scrollPane, constraints);
		
		constraints.fill = GridBagConstraints.NONE;
		constraints.ipady = 0;
		constraints.weighty = 1;
		constraints.anchor = GridBagConstraints.PAGE_END; //bottom of space
		constraints.insets = new Insets(10,0,0,0);  //top padding
		constraints.gridx = 2;
		constraints.gridy = 2;
		add(btnSave, constraints);
		
	}
	
	/**
	 * @return the txtDescription JTextArea
	 */
	public JTextArea getTxtNewMessage() {
		return txtDescription;
	}
}

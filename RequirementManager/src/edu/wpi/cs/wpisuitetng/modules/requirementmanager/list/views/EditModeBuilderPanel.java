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
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class EditModeBuilderPanel extends JPanel {

	/** The name warning label, used to warn the user of an invalid fields in the EditMode */
	private JLabel invalidInput;

	/** Construct the panel and all of its components
	 * @param view The ListTab that this panel will live in
	 */
	public EditModeBuilderPanel(ListTab view) {

		//construct the panels
		invalidInput = new JLabel("Invalid changes in the fields.");

		//Set the color for the warning label
		invalidInput.setForeground(Color.red);
		
		//Set the text of the warning label
		invalidInput.setText("");

		//set the layout for this panel
		setLayout(new GridBagLayout());
		GridBagConstraints EditModeBuilderConstraints = new GridBagConstraints();

		//invalidInput
		//Set the constraints for the "invalidInput" and add it to the view
		EditModeBuilderConstraints.fill = GridBagConstraints.HORIZONTAL; //Tell the field to stretch horizontally to fit it's cell(s)
		EditModeBuilderConstraints.anchor = GridBagConstraints.CENTER; //This sets the anchor of the field, here we have told it to anchor the component to the center right of it's field
		EditModeBuilderConstraints.insets = new Insets(0,10,5,0); //Set the top padding to 0 units of blank space, set left padding to 10 units,right padding to 0 units, bottom padding to 5 units
		EditModeBuilderConstraints.gridx = 0;//Set the x coord of the cell of the layout we are describing
		//EditModeBuilderConstraints.gridwidth = 2; //Tell this component to fill 2 columns
		EditModeBuilderConstraints.gridy = 0;//Set the y coord of the cell of the layout we are describing
		add(invalidInput, EditModeBuilderConstraints);//Actually add the "invalidInput" to the layout given the previous constraints

	}
	
	public void setInvalidInputMessage() {
		invalidInput.setText("There are invalid changes in the fields. Please address errors before saving.");
	}
}

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
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

@SuppressWarnings("serial")
public class EditModeBuilderPanel extends JPanel {

	/** The panel for the legend */
	private JPanel legendPanel;

	/** The panel for the warnings */
	private JPanel warningPanel;

	/** The white label */
	private JTextField whiteLabel;
	/** The gray label */
	private JTextField grayLabel;
	/** The yellow label */
	private JTextField yellowLabel;
	/** The red label */
	private JTextField redLabel;

	/** The error 1 label */
	private JLabel err1Label;
	/** The error 2 label */
	private JLabel err2Label;
	/** The error 3 label */
	private JLabel err3Label;
	/** The enter label */
	private JLabel enterLabel;

	/** The layout manager for this panel */
	protected SpringLayout layout;

	/** Construct the panel and all of its components
	 * @param view The ListTab that this panel will live in
	 */
	public EditModeBuilderPanel(ListTab view) {

		//layout for this panel
		layout  = new SpringLayout();
		this.setLayout(layout);

		legendPanel = new JPanel();
		legendPanel.setPreferredSize(new Dimension(220, 40));
		warningPanel = new JPanel();

		// Legend Panel
		whiteLabel = new JTextField("Editable");
		whiteLabel.setHorizontalAlignment(JTextField.CENTER);
		whiteLabel.setEditable(false);
		whiteLabel.setPreferredSize(new Dimension(105, 17));
		whiteLabel.setBackground(Color.white);
		grayLabel = new JTextField("Non-Editable");
		grayLabel.setHorizontalAlignment(JTextField.CENTER);
		grayLabel.setEditable(false);
		grayLabel.setPreferredSize(new Dimension(105, 17));
		grayLabel.setBackground(Color.lightGray);
		yellowLabel = new JTextField("Modified");
		yellowLabel.setHorizontalAlignment(JTextField.CENTER);
		yellowLabel.setEditable(false);
		yellowLabel.setPreferredSize(new Dimension(105, 17));
		yellowLabel.setBackground(new Color(248,253,188));
		redLabel = new JTextField("Invalid");
		redLabel.setHorizontalAlignment(JTextField.CENTER);
		redLabel.setEditable(false);
		redLabel.setPreferredSize(new Dimension(105, 17));
		redLabel.setBackground(new Color(255,50,50));

		// Set legend constraints
		SpringLayout legendLayout = new SpringLayout();
		legendPanel.setLayout(legendLayout);
		
		// Setup constraints
		legendLayout.putConstraint(SpringLayout.NORTH, whiteLabel, 0, SpringLayout.NORTH, legendPanel);
		legendLayout.putConstraint(SpringLayout.WEST, whiteLabel, 0, SpringLayout.WEST, legendPanel);

		legendLayout.putConstraint(SpringLayout.NORTH, yellowLabel, 0, SpringLayout.NORTH, legendPanel);
		legendLayout.putConstraint(SpringLayout.EAST, yellowLabel, 0, SpringLayout.EAST, legendPanel);
		
		legendLayout.putConstraint(SpringLayout.SOUTH, grayLabel, 0, SpringLayout.SOUTH, legendPanel);
		legendLayout.putConstraint(SpringLayout.WEST, grayLabel, 0, SpringLayout.WEST, legendPanel);
		
		legendLayout.putConstraint(SpringLayout.SOUTH, redLabel, 0, SpringLayout.SOUTH, legendPanel);
		legendLayout.putConstraint(SpringLayout.EAST, redLabel, 0, SpringLayout.EAST, legendPanel);

		legendPanel.add(whiteLabel);
		legendPanel.add(yellowLabel);
		legendPanel.add(grayLabel);
		legendPanel.add(redLabel);


		// Warning panel
		err1Label = new JLabel();
		err1Label.setForeground(Color.red);
		err1Label.setFont(err1Label.getFont().deriveFont(11));
		err1Label.setText("");
		err2Label = new JLabel();
		err2Label.setForeground(Color.red);
		err2Label.setText("");
		err2Label.setFont(err1Label.getFont().deriveFont(11));
		err3Label = new JLabel();
		err3Label.setForeground(Color.red);
		err3Label.setText("");
		err3Label.setFont(err1Label.getFont().deriveFont(11));
		enterLabel = new JLabel();
		enterLabel.setText("Press enter to finalize a change.");
		enterLabel.setFont(enterLabel.getFont().deriveFont(13));

		// "Edit Mode" panel
		JLabel editModeLabel = new JLabel();
		editModeLabel.setText("Edit Mode");
		editModeLabel.setFont(new Font("Arial Bold",1,40));

		// Arrange all panels
		layout.putConstraint(SpringLayout.NORTH, editModeLabel, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, editModeLabel, 0, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, editModeLabel, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, warningPanel, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, warningPanel, 0, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.WEST, warningPanel, 70, SpringLayout.EAST, editModeLabel);
		layout.putConstraint(SpringLayout.EAST, warningPanel, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, legendPanel, 0, SpringLayout.VERTICAL_CENTER, this);
		layout.putConstraint(SpringLayout.EAST, legendPanel, -80, SpringLayout.WEST, editModeLabel);


		//Setting up layout of warning messages
		SpringLayout warningLayout = new SpringLayout();
		warningPanel.setLayout(warningLayout);
		// Set up vertically
		warningLayout.putConstraint(SpringLayout.NORTH, enterLabel, 0, SpringLayout.NORTH, warningPanel);
		warningLayout.putConstraint(SpringLayout.NORTH, err1Label, 10, SpringLayout.SOUTH, enterLabel);
		warningLayout.putConstraint(SpringLayout.NORTH, err2Label, 10, SpringLayout.SOUTH, err1Label);
		warningLayout.putConstraint(SpringLayout.NORTH, err3Label, 10, SpringLayout.SOUTH, err2Label);

		warningPanel.add(enterLabel);
		warningPanel.add(err1Label);
		warningPanel.add(err2Label);
		warningPanel.add(err3Label);


		this.add(legendPanel);
		this.add(editModeLabel);
		this.add(warningPanel);
	}

	/** Takes an array of strings and puts them into labels
	 * 
	 * @param errorMessages an array of messages to show
	 */
	public void setInvalidInputMessages(String[] errorMessages) {

		int errorSize;
		errorSize = errorMessages.length;

		String errorString = "";

		for(int i = 0; i < errorSize; i++) {
			errorString = errorMessages[i];
			if (i == 0) {
				err1Label.setText(errorString);
			}
			if (i == 1) {
				err2Label.setText(errorString);
			}
			if (i == 2) {
				err3Label.setText(errorString);
			}
		}

	}
}

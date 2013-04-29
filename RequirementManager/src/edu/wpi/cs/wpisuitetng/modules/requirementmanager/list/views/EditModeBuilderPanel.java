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
	
	/** The panel for the enter label */
	private JPanel enterPanel;
	
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
		
		enterPanel = new JPanel();
		enterPanel.setPreferredSize(new Dimension(100, 95));
		legendPanel = new JPanel();
		legendPanel.setPreferredSize(new Dimension(110, 95));
		warningPanel = new JPanel();
		

		
		// Legend Panel
		whiteLabel = new JTextField("    Editable field");
		whiteLabel.setEditable(false);
		whiteLabel.setPreferredSize(new Dimension(105, 17));
		whiteLabel.setBackground(Color.white);
		grayLabel = new JTextField("Non-editable field");
		grayLabel.setEditable(false);
		grayLabel.setPreferredSize(new Dimension(105, 17));
		grayLabel.setBackground(Color.lightGray);
		yellowLabel = new JTextField("    Modified field");
		yellowLabel.setEditable(false);
		yellowLabel.setPreferredSize(new Dimension(105, 17));
		yellowLabel.setBackground(new Color(248,253,188));
		redLabel = new JTextField("      Invalid field");
		redLabel.setEditable(false);
		redLabel.setPreferredSize(new Dimension(105, 17));
		redLabel.setBackground(new Color(255,50,50));
		legendPanel.add(grayLabel);
		legendPanel.add(whiteLabel);
		legendPanel.add(yellowLabel);
		legendPanel.add(redLabel);
		
		// Set legend constraints
		SpringLayout legendLayout = new SpringLayout();
		legendPanel.setLayout(legendLayout);
		// Setup vertical constraints
		legendLayout.putConstraint(SpringLayout.NORTH, whiteLabel, 0, SpringLayout.NORTH, legendPanel);
		legendLayout.putConstraint(SpringLayout.NORTH, grayLabel, 15, SpringLayout.SOUTH, whiteLabel);
		
		legendLayout.putConstraint(SpringLayout.NORTH, yellowLabel, 0, SpringLayout.NORTH, legendPanel);
		legendLayout.putConstraint(SpringLayout.NORTH, redLabel, 15, SpringLayout.SOUTH, yellowLabel);

		
		// Setup horizontal constraints
		legendLayout.putConstraint(SpringLayout.WEST, whiteLabel, 0, SpringLayout.WEST, legendPanel);
		legendLayout.putConstraint(SpringLayout.WEST, yellowLabel, 15, SpringLayout.EAST, whiteLabel);

		legendLayout.putConstraint(SpringLayout.WEST, grayLabel, 0, SpringLayout.EAST, legendPanel);
		legendLayout.putConstraint(SpringLayout.WEST, redLabel, 15, SpringLayout.EAST, grayLabel);

		
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
		warningPanel.add(enterLabel);
		warningPanel.add(err1Label);
		warningPanel.add(err2Label);
		warningPanel.add(err3Label);

		// "Edit Mode" panel
		JLabel editModeLabel = new JLabel();
		editModeLabel.setText("Edit Mode");
		editModeLabel.setFont(new Font("Arial Bold",3,40));

		//Setting up layout of warning messages
		SpringLayout warningLayout = new SpringLayout();
		warningPanel.setLayout(warningLayout);
		// Set up vertically
		warningLayout.putConstraint(SpringLayout.NORTH, enterLabel, 0, SpringLayout.NORTH, warningPanel);
		warningLayout.putConstraint(SpringLayout.NORTH, err1Label, 10, SpringLayout.SOUTH, enterLabel);
		warningLayout.putConstraint(SpringLayout.NORTH, err2Label, 10, SpringLayout.SOUTH, err1Label);
		warningLayout.putConstraint(SpringLayout.NORTH, err3Label, 10, SpringLayout.SOUTH, err2Label);

		
		// Arrange all panels
		layout.putConstraint(SpringLayout.NORTH, legendPanel, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, legendPanel, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, legendPanel, 0, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.WEST, editModeLabel, 15, SpringLayout.EAST, legendPanel);
		layout.putConstraint(SpringLayout.NORTH, editModeLabel, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, editModeLabel, 0, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.NORTH, warningPanel, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, warningPanel, 0, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.WEST, warningPanel, 50, SpringLayout.EAST, editModeLabel);
		layout.putConstraint(SpringLayout.EAST, warningPanel, 0, SpringLayout.EAST, this);
		
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

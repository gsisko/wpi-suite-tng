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
	
	/** The enter label */
	private JLabel enterLabel;
	
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
	/** The error 4 label */
	private JLabel err4Label;
	/** The error 5 label */
	private JLabel err5Label;
	
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
		
		// Enter Panel
		enterLabel = new JLabel();
		enterLabel.setText("<html>Press enter to <br> finalize a change.</html>");
		enterLabel.setFont(enterLabel.getFont().deriveFont(13));
		enterPanel.add(enterLabel);
		
		// Legend Panel
		whiteLabel = new JTextField("    Editable field");
		whiteLabel.setEditable(false);
		whiteLabel.setPreferredSize(new Dimension(105, 17));
		whiteLabel.setBackground(Color.white);
		grayLabel = new JTextField("Non-editable field");
		grayLabel.setEditable(false);
		grayLabel.setPreferredSize(new Dimension(105, 17));
		grayLabel.setBackground(Color.lightGray);
		yellowLabel = new JTextField("   Changed field");
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
		
		// Warning panel
		err1Label = new JLabel();
		err1Label.setForeground(Color.red);
		err1Label.setFont(err1Label.getFont().deriveFont(11));
		err1Label.setText("");
		err2Label = new JLabel();
		err2Label.setForeground(Color.red);
		err2Label.setText("");
		err3Label = new JLabel();
		err3Label.setForeground(Color.red);
		err3Label.setText("");
		err4Label = new JLabel();
		err4Label.setForeground(Color.red);
		err4Label.setText("");
		err5Label = new JLabel();
		err5Label.setForeground(Color.red);
		err5Label.setText("");
		warningPanel.add(err1Label);
		warningPanel.add(err2Label);
		warningPanel.add(err3Label);
		warningPanel.add(err4Label);
		warningPanel.add(err5Label);

		// Arrange all panels
		layout.putConstraint(SpringLayout.NORTH, legendPanel, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, legendPanel, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, legendPanel, 0, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.WEST, enterPanel, 15, SpringLayout.EAST, legendPanel);
		layout.putConstraint(SpringLayout.NORTH, enterPanel, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, enterPanel, 0, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.NORTH, warningPanel, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, warningPanel, 0, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.WEST, warningPanel, 50, SpringLayout.EAST, enterPanel);
		layout.putConstraint(SpringLayout.EAST, warningPanel, 0, SpringLayout.EAST, this);

		
		this.add(legendPanel);
		this.add(enterPanel);
		this.add(warningPanel);
	}
	
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
			if (i == 3) {
				err4Label.setText(errorString);
			}
			if (i == 4) {
				err5Label.setText(errorString);
			}
		}
		
	}
}

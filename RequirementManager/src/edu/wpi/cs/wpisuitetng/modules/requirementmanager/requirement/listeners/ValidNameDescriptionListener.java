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
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementAttributePanel;

/** This is a change listener that watches a name text field and a
 *  description text area. It is specifically made to be used in the
 *  RequirementAttributePanel and to take the two fields mentioned 
 *  previously.     */
public class ValidNameDescriptionListener implements KeyListener{

	/** The panel with the name and description fields being watched */
	RequirementAttributePanel thePanel;
	
	/** The name text box to watch 	 */
	JTextField txtName;
	/** The description text box to watch 	 */
	JTextArea txtDescription;
	/** The warning label to set when the name is not appropriately filled	 */
	JLabel warningName;
	/** The warning label to set when the description is not appropriately filled	 */
	JLabel warningDescription;
	/** The save button in the tool bar to set enabled/disabled */
	JButton saveButton;
	/** The state that the save button should be kept in */
	Boolean validNameAndDescription;

	/**	 This is the basic constructor constructor. It takes every reference that it needs in order to perform all of its actions
	 * 
	 * @param txtName         The name text box to watch 
	 * @param txtDescription  The description text box to watch
	 * @param warningLabel    The warning label to set when the name is not appropriately filled
	 * @param warningLabel2   The warning label to set when the description is not appropriately filled
	 * @param saveButton      The save button in the tool bar to set enabled/disabled
	 * @param validNameAndDescription    The state that the save button should be kept in
	 */
	public ValidNameDescriptionListener(JTextField txtName, JTextArea txtDescription, JLabel warningLabel, JLabel warningLabel2,JButton saveButton, Boolean keepDisabled ){
		this.txtName = txtName;
		this.txtDescription = txtDescription;
		this.warningName = warningLabel;
		this.warningDescription = warningLabel2;
		this.saveButton = saveButton;
		this.validNameAndDescription = keepDisabled;
	}

	public ValidNameDescriptionListener(RequirementAttributePanel thePanel){
		this.thePanel = thePanel;
	}
	
	/** Activates when changes are made to the text fields and checks them
	 *  to see if warnings should be put up, and does so.
	 */
	public void keyReleased(KeyEvent e) {
	//	System.out.println("Name/Description: Key released");
		thePanel.areNameAndDescriptionValid();
	}

	/** This method is unused but required by the interface   */
	public void keyTyped(KeyEvent e) {
//		System.out.println("Name/Description: Key typed");
	}

	/** This method is unused but required by the interface   */
	public void keyPressed(KeyEvent e) {
//		System.out.println("Name/Description: Key pressed");
	}
}

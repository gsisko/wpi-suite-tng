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
	* @param thePanel The panel with the two fields that this listener is watching
	*/
	public ValidNameDescriptionListener(RequirementAttributePanel thePanel){
		this.thePanel = thePanel;
	}
	
	/** Activates when changes are made to the text fields and checks them
	 *  to see if warnings should be put up, and does so.
	 */
	public void keyReleased(KeyEvent e) {
		thePanel.setSaveButtonWhenNameAndDescriptionAreValid();
	}

	/** This method is unused but required by the interface   */
	public void keyTyped(KeyEvent e) {
	}

	/** This method is unused but required by the interface   */
	public void keyPressed(KeyEvent e) {
	}
}

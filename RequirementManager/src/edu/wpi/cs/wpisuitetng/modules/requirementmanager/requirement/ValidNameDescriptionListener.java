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
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/** This is a change listener that watches a name text field and a
 *  description text area. It is specifically made to be used in the
 *  RequirementAttributePanel and to take the two fields mentioned 
 *  previously.     */
public class ValidNameDescriptionListener implements DocumentListener{

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
	Boolean keepDisabled;

	/**	 This is the basic constructor constructor. It takes every reference that it needs in order to perform all of its actions
	 * 
	 * @param txtName         The name text box to watch 
	 * @param txtDescription  The description text box to watch
	 * @param warningLabel    The warning label to set when the name is not appropriately filled
	 * @param warningLabel2   The warning label to set when the description is not appropriately filled
	 * @param saveButton      The save button in the tool bar to set enabled/disabled
	 * @param keepDisabled    The state that the save button should be kept in
	 */
	public ValidNameDescriptionListener(JTextField txtName, JTextArea txtDescription, JLabel warningLabel, JLabel warningLabel2,JButton saveButton, Boolean keepDisabled ){
		this.txtName = txtName;
		this.txtDescription = txtDescription;
		this.warningName = warningLabel;
		this.warningDescription = warningLabel2;
		this.saveButton = saveButton;
		this.keepDisabled = keepDisabled;
	}



	/** Activates when changes are made to the text fields and checks them
	 *  to see if warnings should be put up, and does so.
	 */
	public void changedUpdate(DocumentEvent e) {
		fieldCheck();
	}

	/** Activates when changes are made to the text fields and checks them
	 *  to see if warnings should be put up, and does so.
	 */
	public void insertUpdate(DocumentEvent e) {
		fieldCheck();
	}

	/** Activates when changes are made to the text fields and checks them
	 *  to see if warnings should be put up, and does so.
	 */
	public void removeUpdate(DocumentEvent e) {
		fieldCheck();
	}

	/** Checks the fields for changes and sets the warning labels and 
	 *  save button status appropriately             
	 */
	private void fieldCheck(){
		// Initialize flags
		boolean nameBad = false;
		boolean desBad = false;
		
		// Check the name box
		if ((txtName.getText().length()>=100)||(txtName.getText().length()<1)){
			warningName.setText("Name must be between 0 and 100 characters");
			nameBad = true;
		} else {
			// reset the warning if necessary
			warningName.setText("");
		}
		
		// Check the description box
		if (txtDescription.getText().length() < 1){
			warningDescription.setText("Description cannot be blank");
			desBad = true;
		} else {
			// reset the warning if necessary
			warningDescription.setText("");

		}
		
		// If either are false, keep it disabled
		keepDisabled = desBad | nameBad;
		
		// If the fields are not filled right, the save button definitely needs to be disabled
		if (keepDisabled){
			saveButton.setEnabled( false);
		}
	}
}

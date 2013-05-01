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
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.acceptancetest;

import javax.swing.JComboBox;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.AcceptanceTest;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.SaveRequirementController;

/** Listener for the drop-down menus (statusBox) in the list of acceptance test panels (ListOfAcceptanceTestPanel).
 * Automatically saves the test when the status is changed.
 */
public class AcceptanceTestStatusListener implements PopupMenuListener {

	/** The AcceptanceTestPanel with the box being watched */
	private AcceptanceTestPanel thePanel;
	
	/** The JComboBox to watch */
	@SuppressWarnings("rawtypes")
	private JComboBox toWatch;

	/** Constructor for a new AcceptanceTestStatusListener 
	 * @param thePanel       The AcceptanceTestPanel with the box being watched
	 */
	public AcceptanceTestStatusListener (AcceptanceTestPanel thePanel){
		this.thePanel = thePanel;
		toWatch = thePanel.getStatusBox();
	}

	/** Checks the JComboBox "toWatch" for changes, sets the background of the box to yellow
	 * when changes are detected, and white otherwise 
	 */
	private void checkForChanges(){
		// Check if the status has changed
		String inBox = toWatch.getSelectedItem().toString();
		String inTest = thePanel.getMyTest().getAcceptanceTestResult().toString();
		if (inTest.equals("None")) {
			inTest = " ";
		}
		if (!inBox.equals(inTest)) {
			// Create new acceptance test
			AcceptanceTestResult newResult = AcceptanceTestResult.None;
			if (!inBox.equals(" ")) {
				newResult = AcceptanceTestResult.toResult(inBox);
			}
			AcceptanceTest oldTest = thePanel.getMyTest();
			AcceptanceTest newTest = new AcceptanceTest(oldTest.getAcceptanceTestTitle(), oldTest.getDescription(), newResult);
			
			// Save it
			thePanel.getMyTest().setAcceptanceTestResult(newResult);
			AcceptanceTestTab tab = (AcceptanceTestTab) thePanel.getParent().getParent().getParent().getParent();	// Shortens the next line
			SaveRequirementController controller = tab.myGetParent().getParent().getController();
			
			controller.updateAcceptanceTest(oldTest, newTest);
		}
	}

	/** This method is unused but required by the interface   */
	public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
	}

	/** This is called when the user types then releases the key. If
	 *  changes are made, the new AcceptanceTest is saved. 
	 */
	public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
		checkForChanges();			
	}

	/** This is called when the user types then releases the key. If
	 *  changes are made, the new AcceptanceTest is saved. 
	 */
	public void popupMenuCanceled(PopupMenuEvent e) {
		checkForChanges();			
	}
}

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

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.SaveRequirementController;

/** Action that calls {@link SaveRequirementController#save}, default mnemonic key is S.
 */
@SuppressWarnings("serial")
public class SaveAcceptanceTestAction extends AbstractAction {

	private final SaveRequirementController controller;
	
	/** Create a SaveChangesAction
	 * @param controller When the action is performed, controller.save will be called
	 */
	public SaveAcceptanceTestAction(SaveRequirementController controller) {
		super("Add Acceptance Test");
		this.controller = controller;
		putValue(MNEMONIC_KEY, KeyEvent.VK_S);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		controller.saveAcceptanceTest();
	}

}

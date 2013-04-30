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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;


/** Action that calls {@link RetrieveAllRequirementsController#refreshData()}, default mnemonic key is R
 */
@SuppressWarnings("serial")
public class RefreshRequirementsAction extends AbstractAction {
	
	/** The controller to be called when this action is performed */
	protected final RetrieveAllRequirementsController controller;
	
	/** Construct a RefreshRequirementsAction
	 * @param controller when the action is performed this controller's refreshData() method will be called
	 */
	public RefreshRequirementsAction(RetrieveAllRequirementsController controller) {
		super("Refresh");
		this.controller = controller;
		putValue(MNEMONIC_KEY, KeyEvent.VK_R);
	}

	/** when R is pressed, this activates
	 * 
	 * It is permanently deactivated to prevent multiple refreshes
	 * 
	 * @param arg0 the action event that triggered this
	 * 
	 */
	public void actionPerformed(ActionEvent arg0) {
		//commented this line out to prevent multiple refreshes
		//controller.refreshData();
	}
}

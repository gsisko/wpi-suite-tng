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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** Watches the "New <Model>" button and toggles its settings as well
 *  as the associated Builder's settings when the button is pressed.
 *  When pressed, the information in the filter builder panel
 *  is reset and the fields are grayed out.          
 */
public class NewModelAction implements ActionListener {
	/**  The list view that this controller is watching */
	private final IListPanel listView;
	/**  The builder view that this controller must interact with */
	private final IBuilderPanel builderView;
	
	/** Constructor that takes the two panels to watch
	 * 
	 * @param listView ILitPanel for this list/builder
	 * @param builderView   IBuilderPanel for this list/builder
	 */
	public NewModelAction(IListPanel listView, IBuilderPanel builderView){
		this.listView = listView;
		this.builderView = builderView;
	}

	/** This is called whenever the "Cancel"/"New Filter" button is 
	 *  called. If the button mode is set to cancel mode, it is set 
	 *  to display "New Filter" as the button and disables and
	 *  resets the fields in the FilterBuilderPanel. If the mode is
	 *  set to New Filter, then the opposite happens.
	 *  
	 *  @param e The event that triggers these responses 
	 */
	public void actionPerformed(ActionEvent e) {
		// Toggle between the new and cancel modes
		builderView.toggleNewCancelMode();
		builderView.resetFields();
		listView.toggleNewCancelMode();
	}
}

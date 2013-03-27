/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Cassiopia Hudson
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.toolbar;


import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListRequirementsView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.Tab;

/**
 * Action that calls {@link MainTabController#addListRequirementsTab()}, default mnemonic key is D.
 */
@SuppressWarnings("serial")
public class ListRequirementsAction extends AbstractAction {
	
	private final MainTabController controller;
	
	/**
	 * Construct a list requirements action
	 * @param controller the controller to call when the action is performed
	 */
	public ListRequirementsAction(MainTabController controller) {
		super("List Requirements");
		this.controller = controller;
		putValue(MNEMONIC_KEY, KeyEvent.VK_D);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Tab tab = controller.addTab();
		ListRequirementsView view = new ListRequirementsView(controller, tab);
		tab.setComponent(view);
		view.requestFocus();
	}

}

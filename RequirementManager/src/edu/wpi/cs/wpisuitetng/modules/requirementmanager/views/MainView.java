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


package edu.wpi.cs.wpisuitetng.modules.requirementmanager.views;

import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementPanel;

/**
 * This panel fills the main content area of the tab for this module. It
 * contains one inner JPanel, the RequirementPanel.
 * 
 * @author Team 5
 *
 */
@SuppressWarnings("serial")
public class MainView extends JPanel {

	/** The panel containing the requirement panel */
	private final RequirementPanel reqPanel;
	
	/**
	 * Construct the requirement panel and add it to the current view.
	 */
	public MainView() {
		reqPanel = new RequirementPanel(null, null, null);//Actually create the panel
		add(reqPanel);// Add the panel to this view
	}
}

// THIS FILE WILL BE DELETED IN ITERATION 2!!!!
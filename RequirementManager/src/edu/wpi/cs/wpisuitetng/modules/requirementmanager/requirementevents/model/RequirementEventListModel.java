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


package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirementevents.model;

import javax.swing.DefaultListModel;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/**
 * A data model for a list of RequirementEvents.
 */
@SuppressWarnings({"serial", "rawtypes"})
public class RequirementEventListModel extends DefaultListModel {
	
	/**
	 * Construct a new model and populate with the RequirementEvents contained
	 * in the given Requirement
	 * 
	 * @param requirement the Requirement containing the events to populate this model
	 */
	public RequirementEventListModel(Requirement requirement) {
		update(requirement);
	}
	
	/**
	 * Replaces the contents of this model with the events in the given Requirement
	 * @param model the Requirement containing the events to populate this model
	 */
	public void update(Requirement model) {
		this.clear();
	}
}

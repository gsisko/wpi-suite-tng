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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.changeset;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

/**
 * Persistent Model that holds information about a set of changes to a Requirement.
 * Every time a Requirement is changed by a user, a RequirementChangeset should be created
 * containing the changes and the user responsible for making them.
 */
public class RequirementChangeset extends RequirementEvent {

	private Map<String, FieldChange<?>> changes;

	/**
	 * Construct a RequirementChangeset with default properties.
	 * 
	 * @param user the User responsible for this change
	 */
	public RequirementChangeset(String user) {
		type = EventType.CHANGESET;
		changes = new HashMap<String, FieldChange<?>>();
		this.userName = user;
	}


	/**
	 * @return the map of field names to changes (Assignee -> (Bob, Joe))
	 */
	public Map<String, FieldChange<?>> getChanges() {
		return changes;
	}

	/**
	 * @param changes the changes to set
	 */
	public void setChanges(Map<String, FieldChange<?>> changes) {
		this.changes = changes;
	}

	@Override
	public String toJSON() {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(this, RequirementChangeset.class);
		return json;
	}

	@Override
	public String getBodyString() {
		String content = "";
		boolean first = true;

		for (String fieldName : changes.keySet()) {
			if (!first) content += '\n';
			first = false;

			// Get the old and new field objects from the FieldChange
			Object oldValue = changes.get(fieldName).getOldValue();
			Object newValue = changes.get(fieldName).getNewValue();

			// Add the field name to the content label
			content += fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1) + " changed";

			content += " from \"";
			content += oldValue.toString();
			content += "\" to \"";
			content += newValue.toString();
			content += "\"";
		}

		return content;
	}

	@Override
	public String getLabelString() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm a");
		return "Changes made by " + userName + " on " + dateFormat.format(this.getDate());
	}

}

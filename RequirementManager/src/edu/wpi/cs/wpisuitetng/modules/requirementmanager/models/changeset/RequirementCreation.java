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

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Note;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementType;

/** Class for the history log event when a Requirement is created
 */
public class RequirementCreation extends RequirementEvent {

	/**
	 * Field reqName.
	 */
	private String reqName;
	/**
	 * Field reqDescription.
	 */
	private String reqDescription;
	/**
	 * Field reqType.
	 */
	private String reqType;
	/**
	 * Field reqPriority.
	 */
	private String reqPriority;
	/**
	 * Field reqReleaseNumber.
	 */
	private String reqReleaseNumber;
	
	/** Constructor for a RequirementCreation
	 * @param requirement Requirement
	 * @param theCreator String
	 */
	public RequirementCreation(Requirement requirement, String theCreator) {
		type = EventType.CREATION;
		reqName = requirement.getName();
		reqDescription = requirement.getDescription();
		reqType = RequirementType.toBlankString(requirement.getType());
		reqPriority = RequirementPriority.toBlankString(requirement.getPriority());
		reqReleaseNumber = requirement.getReleaseNumber();
		userName = theCreator;
	}
	
	/**
	 * @return the JSON string representation of the RequirementCreation
	 */
	@Override
	public String toJSON() {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(this, Note.class);
		return json;
	}

	/**
	 * @return the body string for the history log entry in the UI
	 */
	@Override
	public String getBodyString() {
		String content = "";
		
		content += "Name initialized to \"" + reqName + "\"\n";
		content += "Description initialized to \"" + reqDescription + "\"\n";
		content += "Type initialized to \"" + reqType + "\"\n";
		content += "Priority initialized to \"" + reqPriority + "\"\n";
		content += "ReleaseNumber initialized to \"" + reqReleaseNumber + "\"";
		
		return content;
	}

	/**
	 * @return the label string for the history log entry in the UI
	 */
	@Override
	public String getLabelString() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm a");
		return "Requirement created by " + userName + " on " + dateFormat.format(this.getDate());
	}

}

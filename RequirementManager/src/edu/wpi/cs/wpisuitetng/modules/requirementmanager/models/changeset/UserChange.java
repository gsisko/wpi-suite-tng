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
import java.util.ArrayList;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Note;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/** Class for history log event when changes are made to a Requirement's assigned users
 */
public class UserChange extends RequirementEvent {
	
	/**
	 * The list of old users
	 */
	ArrayList<String> oldUsers;
	/**
	 * The list of new users
	 */
	ArrayList<String> newUsers;
	
	/** Constructor for a UserChange
	 * @param oldReq Requirement
	 * @param newReq Requirement
	 * @param userName String
	 */
	public UserChange(Requirement oldReq, Requirement newReq, String userName) {
		type = EventType.USER;
		oldUsers = oldReq.getUserNames();
		newUsers = newReq.getUserNames();
		this.userName = userName;
	}
	
	/**
	 * @return the JSON string representation of the UserChange
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
		boolean first = true;
		
		// if there are more users now, find the ones that were added
		if (newUsers.size() > oldUsers.size()) {
			for (String u : newUsers) {
				if (!oldUsers.contains(u)) {
					if (!first) content += '\n';
					else first = false;
					
					content += u + " is now assigned to this requirement";
				}
			}
		}
		else { // there are less users nw, find the ones that were removed
			for (String u : oldUsers) {
				if (!newUsers.contains(u)) {
					if (!first) content += '\n';
					else first = false;
					
					content += u + " is no longer assigned to this requirement";
				}
			}
		}
		
		return content;
	}

	/**
	 * @return the label string for the history log entry in the UI
	 */
	@Override
	public String getLabelString() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm a");
		return "User changes made by " + userName + " on " + dateFormat.format(this.getDate());
	}

}

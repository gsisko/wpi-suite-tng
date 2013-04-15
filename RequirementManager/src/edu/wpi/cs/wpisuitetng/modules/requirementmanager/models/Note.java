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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;

public class Note extends RequirementEvent {
	private String message;
	
	/**
	 * Create a Note with given properties
	 * 
	 * @param message The message the user wishes to post
	 */
	public Note(String message)
	{
		this.type = EventType.NOTE;
		this.setMessage(message);
		this.setUser(new User("","","",-1));
		this.date = new Date();
	}

	public String toString() {
		// Format the date-time stamp
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm a");
				
		return "Note added by " + user.getName() +" on "+dateFormat.format(date) ;
	}
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toJSON() {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(this, Note.class);
		return json;
	}
	
	/**
	 * Converts the given JSON string into a Note
	 * @param json JSON string containing a serialized Note
	 * @return a Note deserialized from the given JSON string
	 */
	public static Note fromJson(String json) {
		Gson parser = new Gson();
		return parser.fromJson(json, Note.class);
	}
	
	@Override
	public String getBodyString() {
		return this.getMessage();
	}
	
	@Override
	public String getLabelString() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm a");
		return "Note added by " + this.getUser().getName() + " on " + dateFormat.format(this.getDate());
	}
	
}

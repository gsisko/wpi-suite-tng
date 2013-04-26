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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.changeset.RequirementEvent;

/** The model that stores a single note. The user name is set
 *  within the RequirementManager.   
 */
public class Note extends RequirementEvent {
	/** The note itself */
	private String message;	
	
	/**Create a Note with given properties
	 * @param message The message the user wishes to post
	 */
	public Note(String message)
	{
		type = EventType.NOTE;
		this.setMessage(message);
		date = new Date();
	}

	/** Converts the entirety of the message to a formatted string and returns it
	 * @return a string version of the whole note
	 */
	public String toString() {
		// Format the date-time stamp
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm a");
				
		return "Note added by " + userName +" on "+dateFormat.format(date) ;
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

	/** Converts the note to a JSON string and returns it 
	 * @return a JSON string of the note
	 */
	public String toJSON() {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(this, Note.class);
		return json;
	}
	
	/** Converts the given JSON string into a Note
	 * @param json JSON string containing a serialized Note
	 * @return a Note deserialized from the given JSON string
	 */
	public static Note fromJson(String json) {
		Gson parser = new Gson();
		return parser.fromJson(json, Note.class);
	}
	
	/** Gets the body of the note and returns it
	 * @return the body of the note
	 */
	public String getBodyString() {
		return this.getMessage();
	}
	
	/** Gets the label of the note and returns it
	 * @return the label of the note
	 */
	public String getLabelString() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm a");
		return "Note added by " + userName + " on " + dateFormat.format(this.getDate());
	}
	
}

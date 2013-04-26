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
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.acceptancetest.AcceptanceTestResult;

/** The model that stores a single acceptance test. The user name is set
 *  within the RequirementManager.   
 */
public class AcceptanceTest extends RequirementEvent {
	
	/** Internal private variables */
	private String title;
	private String description;	
	private AcceptanceTestResult result;

	/** Create a AcceptanceTest with given properties
	 * 
	 * @param title The title of the acceptance test
	 * @param description The description the user wishes to post with the acceptance test
	 * @param result The initial result of the acceptance test
	 */
	public AcceptanceTest(String title, String description, AcceptanceTestResult result)
	{
		type = EventType.ACCEPTANCETEST;
		this.setDescription(description);
		this.setAcceptanceTestTitle(title);
		date = new Date();
		this.result = result;
	}
	
	/**Create a AcceptanceTest with given properties
	 * 
	 * @param title The title of the acceptance test
	 * @param description The description the user wishes to post with the acceptance test
	 */
	public AcceptanceTest(String title, String description)
	{
		this(title, description, AcceptanceTestResult.NONE);
	}

	/** Converts the entirety of the message to a formatted string and returns it
	 * @return a string version of the whole acceptance test
	 */
	public String toString() {
		// Format the date-time stamp
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm a");
				
		return "Acceptance Test added by " + userName +" on "+dateFormat.format(date) ;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/** 
	 * @param result the result to set this acceptance test to
	 */
	public void setAcceptanceTestResult(AcceptanceTestResult result){
		this.result = result;
	}
	
	/**
	 * @return the result this acceptance test is set to
	 */
	public AcceptanceTestResult getAcceptanceTestResult(){
		return result;
	}
	
	/** 
	 * @param title the title to set this acceptance test to
	 */
	public void setAcceptanceTestTitle(String title){
		this.title = title;
	}
	
	/**
	 * @return the title of this acceptance test
	 */
	public String getAcceptanceTestTitle(){
		return title;
	}
	
	/** Converts the acceptance test to a JSON string and returns it 
	 * @return a JSON string of the acceptance test
	 */
	public String toJSON() {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(this, AcceptanceTest.class);
		return json;
	}
	
	/**Converts the given JSON string into a AcceptanceTest
	 * 
	 * @param json JSON string containing a serialized AcceptanceTest
	 * @return a AcceptanceTest deserialized from the given JSON string
	 */
	public static AcceptanceTest fromJson(String json) {
		Gson parser = new Gson();
		return parser.fromJson(json, AcceptanceTest.class);
	}
	
	/** Gets the body of the acceptance test and returns it
	 * @return the body of the acceptance test (the description of the acceptance test)
	 */
	public String getBodyString() {
		return this.getDescription();
	}
	
	/** Gets the label of the acceptance test and returns it
	 * @return the label of the acceptance test (the username and date it was added of the test)
	 */
	public String getLabelString() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm a");
		return "Acceptance Test added by " + userName + " on " + dateFormat.format(this.getDate());
	}
	
}

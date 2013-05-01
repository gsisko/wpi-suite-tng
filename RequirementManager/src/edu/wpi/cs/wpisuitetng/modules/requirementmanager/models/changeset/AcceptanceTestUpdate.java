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
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.acceptancetest.AcceptanceTestResult;

/** Class for the history log event when an AcceptanceTest is updated
 */
public class AcceptanceTestUpdate extends RequirementEvent {

	/**
	 * Field testName.
	 */
	private String testName;
	/**
	 * Field oldResult.
	 */
	private AcceptanceTestResult oldResult;
	/**
	 * Field newResult.
	 */
	private AcceptanceTestResult newResult;
	
	/** Constructor for an AcceptanceTestUpdate event
	 * @param oldReq Requirement
	 * @param newReq Requirement
	 * @param testNumber int
	 * @param userName String
	 */
	public AcceptanceTestUpdate(Requirement oldReq, Requirement newReq, int testNumber, String userName) {
		type = EventType.ACCEPTANCETESTUPDATE;
		testName = oldReq.getAcceptanceTests().get(testNumber).getAcceptanceTestTitle();
		oldResult = oldReq.getAcceptanceTests().get(testNumber).getAcceptanceTestResult();
		newResult = newReq.getAcceptanceTests().get(testNumber).getAcceptanceTestResult();
		this.userName = userName;
	}
	
	/**
	 * @return the JSON string representation of the AcceptanceTestUpdate
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
		
		content += "Result of acceptance test \"";
		content += testName;
		content += "\" changed from \"" + oldResult.toString();
		content += "\" to \"" + newResult.toString() + "\"";
		
		return content;
	}

	/**
	 * @return the label string for the history log entry in the UI
	 */
	@Override
	public String getLabelString() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm a");
		return "Acceptance Test updated by " + userName + " on " + dateFormat.format(this.getDate());
	}

}

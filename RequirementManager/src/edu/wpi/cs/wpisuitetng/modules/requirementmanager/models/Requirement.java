// $codepro.audit.disable unnecessaryOverride
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

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.changeset.RequirementEvent;

/** Requirement: Holds data that makes up a Requirement
 * @version $Revision: 1.0 $
 */
public class Requirement extends AbstractModel {
	/** Unique ID of the record- assigned by entity manager */
	private int id;           
	/** The name of the Requirement (100 chars) */
	private String name;         
	/** A description of the Requirement */
	private String description;    
	/** the type of the requirement */
	private RequirementType type;
	/** The status in the work flow- Default to NEW */
	private RequirementStatus status;     
	/** The priority set to the Requirement */
	private RequirementPriority priority;  
	/** Must be a release number of the current project */
	private String releaseNumber;    
	/** An estimate of what this Requirement will take */
	private int estimate;         
	/** The actual effort it took for this Requirement */
	private int actualEffort;
	/** The iteration that this requirement is in */
	private int iteration;
	/** This is the list of notes for this Requirement */
	private ArrayList<Note> notes; 
	/** This is the list of acceptance tests for this Requirement */
	private ArrayList<AcceptanceTest> acceptanceTests;
	/** This is the list of users associated with this Requirement */
	private ArrayList<String> userNames;
	/** This is the history log for the Requirement */
	private ArrayList<RequirementEvent> events;
	/** This is the list of subrequirements associated with this Requirement */
	private ArrayList<Integer> subrequirements;
	
	/** Basic constructor for a requirement */
	public Requirement(){
		this("", "", RequirementType.NoType, RequirementPriority.NoPriority, "", 0);
	}
	
	/** Full Constructor for Requirement.
	 * 
	 * @param name Name of the requirement 
	 * @param description Description of the requirement
	 * @param releaseNumber Release number of the requirement
	 * @param priority Priority of the requirement (NONE, LOW, MEDIUM, HIGH)
	 * @param iterationID The id of the iteration that this Req is assigned to
	 */
	public Requirement(String name, String description, RequirementType type, RequirementPriority priority, String releaseNumber, int iterationID) {
		this.setName(name);
		this.setDescription(description);
		this.setType(type);
		this.setPriority(priority); 			// Initialize priority
		this.setReleaseNumber(releaseNumber); 	// release number of current project
		this.setIteration(iterationID);
		this.setEstimate(0);
		this.setActualEffort(0);				// Initial actual effort set to zero
		this.setStatus(RequirementStatus.New);	// Initial status should be set to NEW
		this.setId(-1); 						// (-1) will be a flag to the server/database that this value needs to be set
		notes = new ArrayList<Note>();
		acceptanceTests = new ArrayList<AcceptanceTest>();
		userNames = new ArrayList<String>();
		events = new ArrayList<RequirementEvent>();
		this.setSubrequirements(new ArrayList<Integer>());
	}
	
	// The following functions come from the Model interface:
	
	/** Method save.
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
	 */
	@Override
	public void save() {
		
	}

	/** Method delete.
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#delete()
	 */
	@Override
	public void delete() {
		
	}
	
	/**Converts this requirement to a JSON string for sending across the network
	 * 
	 * @return a string in JSON representing this requirement 
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON() 
	 */
	public String toJSON() {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(this, Requirement.class);
		return json;
	}

	/** Method identify
	 * 
	 * @param o Object
	 * @return Boolean 
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(Object) 
	 */
	public Boolean identify(Object o) {
		Boolean returnValue = false;
		if(o instanceof Requirement && id == ((Requirement) o).getId()) {
			returnValue = true;
		}	
		if(o instanceof String && Integer.toString(id).equals(o)) {
			returnValue = true;
		}
		return returnValue;
	}
	
	// End of Model interface functions
	
	/** Converts the given list of Requirements to a JSON string
	 * 
	 * @param rlist a list of Requirements
	 * @return a string in JSON representing the list of Requirements 
	 */
	public static String toJSON(Requirement[] rlist) {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(rlist, Requirement.class);
		return json;
	}
	
	/** Method toString.  Outputs a JSON string.
	 * 
	 * @return String 
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#toString() 
	 */
	@Override
	public String toString() {
		return this.toJSON();
	}
	
	/**
	 * @param json Json string to parse containing Requirement
	 * @return The Requirement given by json 
	 */
	public static Requirement fromJSON(String json) {
		GsonBuilder builder = new GsonBuilder();
		addGsonDependencies(builder);
		return builder.create().fromJson(json, Requirement.class);
	}
	
	/**
	 * @param json Json string to parse containing Requirement array
	 * @return The Requirement array given by json 
	 */
	public static Requirement[] fromJSONArray(String json) {
		GsonBuilder builder = new GsonBuilder();
		addGsonDependencies(builder);
		return builder.create().fromJson(json, Requirement[].class);
	}
	
	/**Add dependencies necessary for Gson to interact with this class
	 * @param builder Builder to modify
	 */
	public static void addGsonDependencies(GsonBuilder builder) {
		RequirementEvent.addGsonDependencies(builder);
	}

	/**Method setProject.
	 * 
	 * @param project Project
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#setProject(Project) 
	 */
	public void setProject(Project project) {
		super.setProject(project);
	}
	
	/**
	 * @return id The "id" of the Requirement 
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id The "id" (an int) of this Requirement to set
	 */
	public void setId(int id) {
		this.id = id;
	}	
	
	/**
	 * @return actualEffort The "actualEffort" of the Requirement 
	 */
	public int getActualEffort() {
		return actualEffort;
	}

	/**
	 * @param actualEffort The "actualEffort" (an int) of this Requirement to set
	 */
	public void setActualEffort(int actualEffort) {
		this.actualEffort = actualEffort;
	}

	/**
	 * @return estimate The "estimate" of the Requirement 
	 */
	public int getEstimate() {
		return estimate;
	}

	/**
	 * @param setEstimate The "setEstimate" (an int) of this Requirement to set
	 */
	public void setEstimate(int estimate) {
		this.estimate = estimate;
	}

	/**
	 * @return description The "description" of the Requirement 
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description The "description" (a String) of this Requirement to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return name The "name" of the Requirement 
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name The "name" (a String) of this Requirement to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return releaseNumber The "releaseNumber" of the Requirement 
	 */
	public String getReleaseNumber() {
		return releaseNumber;
	}

	/**
	 * @param releaseNumber The "releaseNumber" (a String) of this Requirement to set
	 */
	public void setReleaseNumber(String releaseNumber) {
		this.releaseNumber = releaseNumber;
	}

	/**
	 * @return status The "status" RequirementStatus of the Requirement 
	 */
	public RequirementStatus getStatus() {
		return status;
	}

	/**
	 * @param status The "status" (an instance of RequirementStatus) to set for this Requirement
	 */
	public void setStatus(RequirementStatus status) {
		this.status = status;
	}

	/**
	 * @return priority The "priority" RequirementPriority of the Requirement
	 */
	public RequirementPriority getPriority() {
		return priority;
	}

	/**
	 * @param priority The "priority" (an instance of RequirementPriority) to set for this Requirement
	 */
	public void setPriority(RequirementPriority priority) {
		this.priority = priority;
	}
	
	/** Changes all fields in the current Requirement to equal the fields of the reqUpdate
	 * @param reqUpdate Requirement holding the updates
	 */
	public void updateReq(Requirement reqUpdate) {
		this.setReleaseNumber(reqUpdate.getReleaseNumber());
		this.setStatus(reqUpdate.getStatus());
		this.setPriority(reqUpdate.getPriority());
		this.setName(reqUpdate.getName());
		this.setDescription(reqUpdate.getDescription());
		this.setEstimate(reqUpdate.getEstimate());
		this.setActualEffort(reqUpdate.getActualEffort());
		this.setType(reqUpdate.getType());
		this.setNotes(reqUpdate.getNotes());
		this.setAcceptanceTests(reqUpdate.getAcceptanceTests());
		this.setUserNames(reqUpdate.getUserNames());
		this.setEvents(reqUpdate.getEvents());
		this.setIteration(reqUpdate.getIteration());
	}

	/**
	 * @return type The "type" RequirementType of the Requirement
	 */
	public RequirementType getType() {
		return type;
	}

	/**
	 * @param type The "type" (an instance of RequirementType) to set for this Requirement
	 */
	public void setType(RequirementType type) {
		this.type = type;
	}

	/**
	 * @return notes The "notes" ArrayList of Notes of the Requirement
	 */
	public ArrayList<Note> getNotes() {
		return notes;
	}

	/**
	 * @param notes The "notes" ArrayList of Notes of the Requirement to set
	 */
	public void setNotes(ArrayList<Note> notes) {
		this.notes = notes;
	}
	
	/**
	 * @return acceptanceTests The "acceptanceTests" ArrayList of AcceptanceTests of the Requirement
	 */
	public ArrayList<AcceptanceTest> getAcceptanceTests() {
		return acceptanceTests;
	}

	/**
	 * @param acceptanceTests The "acceptanceTests" ArrayList of AcceptanceTests of the Requirement to set
	 */
	public void setAcceptanceTests(ArrayList<AcceptanceTest> acceptanceTests) {
		this.acceptanceTests = acceptanceTests;
	}

	/**
	 * @return userNames The "userNames" ArrayList of user names (strings) assigned to this Requirement
	 */
	public ArrayList<String> getUserNames() {
		return userNames;
	}

	/**
	 * @param users ArrayList of user names (strings) to assign to this requirement
	 */
	public void setUserNames(ArrayList<String> users) {
		userNames = users;
	}

	/**
	 * @return iteration The "iteration" of the Requirement 
	 */
	public int getIteration() {
		return iteration;
	}

	/**
	 * @param iteration The "iteration" (an int representing the Iteration id number) of this Requirement to set
	 */
	public void setIteration(int assignedIteration) {
		iteration = assignedIteration;
	}

	/**
	 * @return events The "events" ArrayList of RequirementEvents associated with this Requirement
	 */
	public ArrayList<RequirementEvent> getEvents() {
		return events;
	}

	/**
	 * @param events ArrayList of RequirementEvents to associate with this requirement
	 */
	public void setEvents(ArrayList<RequirementEvent> events) {
		this.events = events;
	}
	
	/**
	 * @return subrequirements The "subrequirements" ArrayList of Integers (representing Requirement id numbers) of the Requirement
	 */
	public ArrayList<Integer> getSubrequirements() {
		return subrequirements;
	}

	/**
	 * @param subrequirements The "subrequirements" ArrayList of Integers (representing Requirement id numbers) of the Requirement to set
	 */
	public void setSubrequirements(ArrayList<Integer> subrequirements) {
		this.subrequirements = subrequirements;
	}
}

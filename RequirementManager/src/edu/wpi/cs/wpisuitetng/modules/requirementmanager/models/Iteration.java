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

import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

public class Iteration extends AbstractModel {   
	/** The name of the iteration */
	private String name;
	/** The date that the iteration will start */
	private Date startDate;
	/** The date that the iteration will end */
	private Date endDate;
	/** The unique identifier of the iteration */
	private int id;
	/** The requirements that are a part of this Requirement */
	private ArrayList<Integer> requirementsAssigned;
	/** The current running total estimate */
	private int totalEstimate;
	
	/** Basic constructor for an Iteration */
	public Iteration()
	{
		this.setName("");
		this.setStartDate(new Date());
		this.setEndDate(new Date());
		this.setID(-1);
		this.totalEstimate = 0;
		requirementsAssigned = new ArrayList<Integer>();
	}
	
	/**
	 * Create an Iteration with given properties
	 * 
	 * @param name the Name of the Iteration
	 * @param startDate the starting date of the Iteration
	 * @param endDate the ending date of the iteration
	 */
	public Iteration(String name, Date startDate, Date endDate)
	{
		this();
		this.setName(name);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
		this.totalEstimate = 0;
	}



	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toJSON() {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(this, Iteration.class);
		return json;
	}
	
	
	/** Converts the given list of Iterations to a JSON string
	 * 
	 * @param dlist A list of Iterations
	 * @return A string in JSON representing the list of Iterations
	 */
	public static String toJSON(Iteration[] dlist) {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(dlist, Iteration.class);
		return json;
	}
	
	
	
	/** Converts a given json string to an Iteration
	 * 
	 * @param json Json string to parse containing Iteration
	 * @return The Iteration given by Json
	 */
	public static Iteration fromJSON(String json) {
		GsonBuilder builder = new GsonBuilder();
		return builder.create().fromJson(json, Iteration.class);
	}
	
	/** Converts a JSON string of an array of iterations to
	 *  an array of iterations.
	 *  
	 * @param json JSON string to parse containing Iteration array
	 * @return The Iteration array given by JSON
	 */
	public static Iteration[] fromJSONArray(String json) {
		GsonBuilder builder = new GsonBuilder();
		return builder.create().fromJson(json, Iteration[].class);
	}
	

	@Override
	public Boolean identify(Object o) {
		Boolean returnValue = false;
		if(o instanceof Iteration && name == ((Iteration) o).getName()) {
			returnValue = true;
		}	
		if(o instanceof String && name.equals(o)) {
			returnValue = true;
		}
		return returnValue;
	}
	
	
	/** Changes all fields in the current Iteration to equal the fields of the iterationUpdate
	 * 
	 * @param iterationUpdate Iteration holding the updates
	 */
	public void updateIteration(Iteration iterationUpdate){	
		this.setName(iterationUpdate.getName());
		this.setStartDate(iterationUpdate.getStartDate());
		this.setEndDate(iterationUpdate.getEndDate());
		this.setRequirementsContained(iterationUpdate.getRequirementsContained());
		// id does not need to be set, as it cannot be changed anyways
	}
	
	
	/** Gets estimates from each associated requirement and sums up the total. 
	 * 
	 * @return The sum of all the requirements estimates
	 */
	public int getTotalEstimate(){
		/* TODO: ways of getting the total estimate
		  
		Option A
		- Send message from here to get all Req's, sort here to pick out proper ID's
		and sum up the estimates of the remaining
		- Local RetrieveAllRequirementsController will be necessary
		Option B
		- Send individual messages to get only the Req's we want
		- More messages
		- Local RetrieveRequirementController is needed
		Option C
		- Ask the current list panel for the requirements that are in this iteration
		Option D 
		- Have requirements tell the iteration when they are added/remove or 
		their estimates are updated
		- Keep track of a single local variable that tracks the total estimate			
		
		*/
		
		this.calculateTotalEstimate();
		
		return this.totalEstimate;
	}
	
	
	
	
// The following are Gettes and Setters
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the name
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param ID the id to set
	 */
	public void setID(int ID) {
		this.id = ID;
	}
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * @return the requirementsAssigned
	 */
	public ArrayList<Integer> getRequirementsContained() {
		return requirementsAssigned;
	}

	/**
	 * @param requirementsContained the requirementsAssigned to set
	 */
	public void setRequirementsContained(ArrayList<Integer> requirementsContained) {
		this.requirementsAssigned = requirementsContained;
	}
	
	/**
	 * Ask the controller to retrieve the total estimate and give it to us.
	 */
	private void calculateTotalEstimate(){
		//TODO
		this.totalEstimate = 0;
	}
}

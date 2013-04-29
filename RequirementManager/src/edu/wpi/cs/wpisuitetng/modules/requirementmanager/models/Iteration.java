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
		//Call the other constructor so we reuse code
		this("", new Date(), new Date());
	}
	
	/** Create an Iteration with given properties
	 * 
	 * @param name the Name of the Iteration
	 * @param startDate the starting date of the Iteration
	 * @param endDate the ending date of the iteration
	 */
	public Iteration(String name, Date startDate, Date endDate)
	{
		//Copy in pass parameters
		this.setName(name);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
		
		//Initialize our other private variables
		this.setID(-1);
		
		totalEstimate = 0;
		requirementsAssigned = new ArrayList<Integer>();
	}

	@Override
	public void save() {
		
	}

	@Override
	public void delete() {
		
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
		if(o instanceof Iteration && name.equals(((Iteration) o).getName())) {
			returnValue = true;
		}	
		if(o instanceof String && name.equals(o)) {
			returnValue = true;
		}
		return returnValue;
	}
	
	/** Changes all fields in the current Iteration to equal the fields of the iterationUpdate
	 * @param iterationUpdate Iteration holding the updates
	 */
	public void updateIteration(Iteration iterationUpdate){	
		this.setName(iterationUpdate.getName());
		this.setStartDate(iterationUpdate.getStartDate());
		this.setEndDate(iterationUpdate.getEndDate());
		this.setRequirementsContained(iterationUpdate.getRequirementsContained());
		this.setTotalEstimate(iterationUpdate.getTotalEstimate());
		// id does not need to be set, as it cannot be changed anyways
	}
	
	/** Gets estimates from each associated requirement and sums up the total. 
	 * @return The sum of all the requirements estimates
	 */
	public int getTotalEstimate(){				
		return totalEstimate;
	}
	
	/** Ask the controller to retrieve the total estimate and give it to us.
	 */
	public void setTotalEstimate(int newTotalEstimate){
		totalEstimate = newTotalEstimate;
	}	
	
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
		id = ID;
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
		requirementsAssigned = requirementsContained;
	}
	

}

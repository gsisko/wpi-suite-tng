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

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

public class Iteration extends AbstractModel {   
	private String name;
	private Date startDate;
	private Date endDate;
	private int LowercaseID;
	// Basic constructor for empty iteration
	public Iteration()
	{
		this.setName("");
		this.setStartDate(new Date());
		this.setEndDate(new Date());
		this.setID(-1);
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
		this.setName(name);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
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
		return LowercaseID;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param name the name to set
	 */
	public void setID(int ID) {
		this.LowercaseID = ID;
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
		// id does not need to be set, as it cannot be changed anyways
	}
	
}

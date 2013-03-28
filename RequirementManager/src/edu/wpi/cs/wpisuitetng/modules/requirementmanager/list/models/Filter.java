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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/** A filter is one set of constraints that a user can use to filter a list of requirements.
 * @author Team 5
 *
 */
public class Filter extends AbstractModel {
	/** Unique identifier for database extraction    */
	private int uniqueID;
	/**	What to filter by	*/
	private FilterType type;
	/**	Operator to use for comparison	*/
	private OperatorType comparator;
	/**	String, integer, or RequirementStatus, RequirementPriority	*/
	private String value;
	/**	Use filter? */
	private boolean useFilter;
	/** The owner of the filter */
	private User user;
	
	
	/**	Basic constructor
	 */
	public Filter () {
		new Filter(FilterType.Other, OperatorType.Other, "value", true);
	}
	
	
	/**	Full constructor for Filter. 
	 * 
	 * @param type
	 * @param comparator
	 * @param value     Could be anything, preferably Integer, String, RequirementStatus, RequirementType, RequirementPriority
	 * @param useFilter  Field that says whether or not to use the filter
	 */
	public Filter( FilterType type, OperatorType comparator, Object value, boolean useFilter) {
		this.setUniqueID(-1); 		// default as a flag to entity manager
		this.setType(type);
		this.setComparator(comparator);
		this.setValue(value);  		// Calls correctly overloaded setValue method
		this.setUseFilter(useFilter);
		this.setUser(null);         // User is defaulted to null and handled at the manager layer
	}
	
	
	/** Converts this Filter to a JSON string
	 * 
	 * @return a string in JSON representing this Filter
	 */
	public String toJSON() {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(this, Filter.class);
		return json;
	}
	
	/** Converts the given list of Filters to a JSON string
	 * 
	 * @param dlist A list of Filters
	 * @return A string in JSON representing the list of Filters
	 */
	public static String toJSON(Filter[] dlist) {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(dlist, Filter.class);
		return json;
	}
	
	@Override
	public String toString() {
		return toJSON();
	}
	
	/** Converts a given json string to a Filter
	 * 
	 * @param json Json string to parse containing Filter
	 * @return The Filter given by Json
	 */
	public static Filter fromJSON(String json) {
		GsonBuilder builder = new GsonBuilder();
		return builder.create().fromJson(json, Filter.class);
	}
	
	/** Converts a JSON string of an array of filters to
	 *  an array of filters.
	 *  
	 * @param json JSON string to parse containing Filter array
	 * @return The Filter array given by JSON
	 */
	public static Filter[] fromJSONArray(String json) {
		GsonBuilder builder = new GsonBuilder();
		return builder.create().fromJson(json, Filter[].class);
	}
	
	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(java.lang.Object)
	 */
	public Boolean identify(Object o) {
		Boolean returnValue = false;
		if(o instanceof Filter && uniqueID == ((Filter) o).getUniqueID()) {
			returnValue = true;
		}	
		if(o instanceof String && Integer.toString(uniqueID).equals(o)) {
			returnValue = true;
		}
		return returnValue;
	}
	
	/** Changes all fields in the current Filter to equal the fields of the filterUpdate
	 * 
	 * @param reqUpdate Filter holding the updates
	 */
	public void updateFilter(Filter filterUpdate){	
		this.setType(filterUpdate.getType());
		this.setComparator(filterUpdate.getComparator());
		this.setValue(filterUpdate.getValue());
		this.setUseFilter(filterUpdate.isUseFilter());
		// User does not need to be set, as it cannot be changed anyways
		// Unique ID does not need to be set, as it cannot be changed anyways
	}
	
	/** Compares two filters. Intended use in the makeEntity method
	 * 
	 * @param toCompareTo The Filter to compare to
	 * @return Whether the two Filters are equal or not
	 */
	public boolean equals(Filter toCompareTo){
		if (this.getType() != toCompareTo.getType()) return false;
		if (this.getComparator() != toCompareTo.getComparator()) return false;	
		if (!this.getValue().equals(toCompareTo.getValue())  ) return false;		
		return true;
	}	
	
	/** Determines whether a Requirement passes a filter
	*
	*	@param req The Requirement in question
	*
	*	@return True if the Requirement passes, false if it does not
	*/
	public boolean passesFilter(Requirement req){
		if (!this.isUseFilter()) return true; // If filter is turned off, the Requirement passes
	
		try{
			switch (this.type){	
			// The following two are strings
			case Name:
				return OperatorType.perform(this.comparator,this.value.toLowerCase(), req.getName().toLowerCase(), false);
			case Description:
				return OperatorType.perform(this.comparator, this.value.toLowerCase(), req.getDescription().toLowerCase(), false);
			
			// The following four are Integers
			case Id: 
				return OperatorType.perform(this.comparator, Integer.parseInt(this.value), req.getId());
			case ActualEffort:
				return OperatorType.perform(this.comparator, Integer.parseInt(this.value), req.getActualEffort());		
			case Estimate:
				return OperatorType.perform(this.comparator, Integer.parseInt(this.value), req.getEstimate());		
			case ReleaseNumber:
				return OperatorType.perform(this.comparator, Integer.parseInt(this.value), req.getReleaseNumber());
		
			// The following three are different enums
			case Status:
				return OperatorType.perform(this.comparator, this.value.toLowerCase(), req.getStatus().toString().toLowerCase(), true);
			case Type:
				return OperatorType.perform(this.comparator, this.value.toLowerCase(), req.getType().toString().toLowerCase(), true);
			case Priority:
				return OperatorType.perform(this.comparator, this.value.toLowerCase(), req.getPriority().toString().toLowerCase(), true);
		
			// Default
			default:
				return true;  // default to not filter out stuff
			}
		} catch (NumberFormatException nfe){
			return false; // If parseInt is given a string with no numbers, the filter is set to pass the filter
		}
	}
	
	
	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
	 */
	public void save() {
		// TODO Auto-generated method stub

	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#delete()
	 */
	public void delete() {
		// TODO Auto-generated method stub

	}
	
	
	/**
	 * @return the uniqueID
	 */
	public int getUniqueID() {
		return uniqueID;
	}

	/**
	 * @param uniqueID The uniqueID to set
	 */
	public void setUniqueID(int uniqueID) {
		this.uniqueID = uniqueID;
	}

	/**
	 * @return The type
	 */
	public FilterType getType() {
		return type;
	}

	/**
	 * @param type The type to set
	 */
	public void setType(FilterType type) {
		this.type = type;
	}

	/**
	 * @return The comparator
	 */
	public OperatorType getComparator() {
		return comparator;
	}

	/**
	 * @param comparator The comparator to set
	 */
	public void setComparator(OperatorType comparator) {
		this.comparator = comparator;
	}

	
	/**
	 * @return The useFilter
	 */
	public boolean isUseFilter() {
		return useFilter;
	}

	/**
	 * @param useFilter The useFilter to set
	 */
	public void setUseFilter(boolean useFilter) {
		this.useFilter = useFilter;
	}


	/**
	 * @return The user
	 */
	public User getUser() {
		return user;
	}


	/**
	 * @param user The user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	

	
	
	
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}


	/** Sets the value of the Filter when the input is Object
	 * 
	 * @param value the value to set
	 */
	public void setValue(Object o){
		this.value = o.toString();		
	}
}

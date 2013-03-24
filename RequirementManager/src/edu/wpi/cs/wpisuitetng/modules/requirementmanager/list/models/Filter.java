/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

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
	/**	String, integer, or enum only	*/
	private Object value;
	/**	Use filter? */
	private boolean useFilter;
	
	/** The owner of the filter */
	private User user;
	
	
	/**	Basic constructor
	 * 
	 */
	public Filter () {
		new Filter(FilterType.OTHER, OperatorType.OTHER, null, true);
	}
	
	
	/**	Full constructor for Filter.
	 * 
	 * @param type
	 * @param comparator
	 * @param value
	 * @param useFilter
	 */
	public Filter( FilterType type, OperatorType comparator,
			Object value, boolean useFilter) {
		this.setUniqueID(-1); // default as a flag to entity manager
		this.setType(type);
		this.setComparator(comparator);
		this.setValue(value);
		this.setUseFilter(useFilter);
		this.setUser(null);          // User is defaulted to null and handled at the manager layer
	}
	
	
	/**
	 * Converts this Filter to a JSON string
	 * @return a string in JSON representing this Filter
	 */
	public String toJSON() {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(this, Filter.class);
		return json;
	}
	
	/**
	 * Converts the given list of Filters to a JSON string
	 * @param dlist a list of Filters
	 * @return a string in JSON representing the list of Filters
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
	
	/**
	 * @param json Json string to parse containing Filter
	 * @return The Filter given by json
	 */
	public static Filter fromJSON(String json) {
		GsonBuilder builder = new GsonBuilder();
		return builder.create().fromJson(json, Filter.class);
	}
	
	/**
	 * @param json Json string to parse containing Filter array
	 * @return The Filter array given by json
	 */
	public static Filter[] fromJSONArray(String json) {
		GsonBuilder builder = new GsonBuilder();
		return builder.create().fromJson(json, Filter[].class);
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(java.lang.Object)
	 */
	@Override
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
	 * @param toCompareTo The Filter to compare to
	 * @return Whether the two Filters are equal or not
	 */
	public boolean equals(Filter toCompareTo){
		if (this.getType() != toCompareTo.getType()) return false;
		if (this.getComparator() != toCompareTo.getComparator()) return false;
		if (this.getValue() != toCompareTo.getValue()) return false;		
		return true;
	}	
	
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
	 */
	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#delete()
	 */
	@Override
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
	 * @param uniqueID the uniqueID to set
	 */
	public void setUniqueID(int uniqueID) {
		this.uniqueID = uniqueID;
	}

	/**
	 * @return the type
	 */
	public FilterType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(FilterType type) {
		this.type = type;
	}

	/**
	 * @return the comparator
	 */
	public OperatorType getComparator() {
		return comparator;
	}

	/**
	 * @param comparator the comparator to set
	 */
	public void setComparator(OperatorType comparator) {
		this.comparator = comparator;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * @return the useFilter
	 */
	public boolean isUseFilter() {
		return useFilter;
	}

	/**
	 * @param useFilter the useFilter to set
	 */
	public void setUseFilter(boolean useFilter) {
		this.useFilter = useFilter;
	}


	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}


	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

}

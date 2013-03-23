/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

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
	
	
	/**	Basic constructor
	 * 
	 */
	public Filter () {
		new Filter(-1, FilterType.OTHER, OperatorType.OTHER, null, true);
	}
	
	
	/**	Full constructor for Filter.
	 * 
	 * @param uniqueID2
	 * @param type2
	 * @param comparator2
	 * @param value2
	 * @param useFilter2
	 */
	public Filter(int uniqueID2, FilterType type2, OperatorType comparator2,
			Object value2, boolean useFilter2) {
		this.setUniqueID(uniqueID2);
		this.setType(type2);
		this.setComparator(comparator2);
		this.setValue(value2);
		this.setUseFilter(useFilter2);
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
		this.setUniqueID(filterUpdate.getUniqueID());
		this.setType(filterUpdate.getType());
		this.setComparator(filterUpdate.getComparator());
		this.setValue(filterUpdate.getValue());
		this.setUseFilter(filterUpdate.isUseFilter());		
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

}

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models;

/**	Possible values for the type of filter.
 * @author Team 5
 *
 */
public enum FilterType {
	ID,
	NAME,
	DESCRIPTION,
	TYPE,
	STATUS,
	PRIORITY,
	RELEASE_NUMBER,
	ESTIMATE,
	ACTUAL_EFFORT,
	OTHER;
	
	/** Converts a string to the proper FilterType
	 * 
	 * !NOTE: This method is designed to only take strings retrieved from a UE.dropdonwlist that contains
	 * only the enums listed as FilterType.
	 * 
	 * @param toConvert The string you want to convert
	 * @return the proper FilterType
	 */
	public static FilterType toType (String toConvert){
		return valueOf(toConvert);
	}
}

/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models;

/** Possible operations that a filter can perform
 * 
 * !NOTE: If filtering by a field that is an "enum", only use EqualTo or NotEqualTo
 * 
 * ?!?!? ALPHABETICAL?!?!
 * 
 * @author Team 5
 *
 */
public enum OperatorType {
	GreaterThan,
	GreaterThanOrEqualTo,
	LessThan,
	LessThanOrEqualTo,
	EqualTo,
	NotEqualTo,
	Contains,
	DoesNotContain,
	Other;
	
	
	
	/** Converts a string to the proper OperatorType
	 * 
	 * !NOTE: This method is designed to only take strings retrieved from a UE.dropdonwlist that contains
	 * only the enums listed as OperatorType.
	 * 
	 * @param toConvert The string you want to convert
	 * @return the proper OperatorType
	 */
	public static OperatorType toType (String toConvert){
		return valueOf(toConvert);
	}
}



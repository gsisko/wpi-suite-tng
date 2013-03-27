package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models;

/** this enum provides the requirement types for requirements. 
 * 
 * @author Team 5
 *
 */
public enum RequirementType {
	Epic,
	Theme,
	UserStory,
	NonFunctional,
	Scenario,
	NoType;
	

	/** Converts a string to the proper RequirementType 
	 * 
	 * !NOTE: This method is designed to only take strings retrieved from a UI.dropdownlist that contains
	 * only the enums listed as RequirementTypes's
	 * 
	 * @param toConvert The string you want to convert
	
	 * @return  The proper RequirementType */
	public static RequirementType toType(String toConvert){     
    	String blank = "";
    	if (toConvert.equals(blank))
    		return valueOf("NoType");
    	else
    		return valueOf(toConvert);
	}
}
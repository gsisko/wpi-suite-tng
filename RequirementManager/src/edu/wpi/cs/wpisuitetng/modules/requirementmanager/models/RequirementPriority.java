package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models;

/**
 * Possible values for the priority of a requirement. 
 * @author Dabrowski
 * @version $Revision: 1.0 $
 */
public enum RequirementPriority {
	High,
	Medium,
	Low,
	none;


	/** Converts a string to the proper RequirementPriority
	 * 
	 * !NOTE: This method is designed to only take strings retrieved from a UI.dropdownlist that containts
	 * only the enums listed as RequirementPriority's
	 * 
	 * @param toConvert The string you want to convert	
	 * @return  The proper RequirementPriority 
	 */
    public static RequirementPriority toPriority(String toConvert){     
       return valueOf(toConvert);
 
    }

}

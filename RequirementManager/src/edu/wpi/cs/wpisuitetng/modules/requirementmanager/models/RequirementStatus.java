package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models;

/**
 * Possible values for the status of a requirement.
 * @author Team 5
 * @version $Revision: 1.0 $
 */
public enum RequirementStatus {
	New,   // The initial status value of a requirement
	InProgress,  // The first value after being new. Also the value when a requirement is part of an iteration
	Open,     // The status of a requirement that is in the backlog
	Complete,  // A complete Requirement
	Deleted;	// Any of these status's may lead to DELETED, but DELETED may move to any but NEW
	 // Need to ask Marty about this, but some of the project descriptions hint at this
	

	/** Converts a string to the proper RequirementStatus 
	 * 
	 * !NOTE: This method is designed to only take strings retrieved from a UI.dropdownlist that contains
	 * only the enums listed as RequirementStatus's
	 * 
	 * @param toConvert The string you want to convert
	
	 * @return  The proper RequirementStatus */
    public static RequirementStatus toStatus(String toConvert){     
       return valueOf(toConvert);
 
    }
    
}    

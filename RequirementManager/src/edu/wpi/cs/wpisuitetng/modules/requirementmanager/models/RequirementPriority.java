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

/**
 * Possible values for the priority of a requirement. 
 * @version $Revision: 1.0 $
 */
public enum RequirementPriority {
	High,
	Medium,
	Low,
	NoPriority;


	/** Converts a string to the proper RequirementPriority
	 * 
	 * !NOTE: This method is designed to only take strings retrieved from a UI.dropdownlist that containts
	 * only the enums listed as RequirementPriority's
	 * 
	 * @param toConvert The string you want to convert	
	 * @return  The proper RequirementPriority 
	 */
    public static RequirementPriority toPriority(String toConvert){
    	String blank = "";
    	if (toConvert.equals(blank))
    		return valueOf("NoPriority");
    	else
    		return valueOf(toConvert);
    }
    
    /** Same as toString, but returns blank for NoPriority */
    public static String toBlankString(RequirementPriority toConvert){
    	return toConvert.toString();
    }
    
    /**
     * Overrides the default toString method
     * 
     * @return String representing this enum.  NoPriority will be displayed as an empty string
     */
    @Override
    public String toString() {
    	if (this.equals(NoPriority))
    		return "";
    	else
    		return super.toString();
    }

}

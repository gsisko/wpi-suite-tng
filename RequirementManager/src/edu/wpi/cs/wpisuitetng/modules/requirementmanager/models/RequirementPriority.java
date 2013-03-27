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

/**
 * Possible values for the priority of a requirement. 
 * @author Team 5
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

}

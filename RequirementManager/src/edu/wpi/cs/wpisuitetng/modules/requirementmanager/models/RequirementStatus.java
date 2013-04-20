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
 * Possible values for the status of a requirement.
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

	/** Takes the current status of a requirement and outputs an array of 
	 *  strings the represent the possible statuses that the requirement
	 *  can be switched to
	 * 
	 * @param currentStatus The current status of the requirement
	 * @return The possible statuses that a requirement can be changed to.
	 */
	public static String[] getAvailableStatuses(RequirementStatus currentStatus){
		switch(currentStatus){
		case New:
			return new String[] { "New", "Deleted" };
		case InProgress:
			return new String[] { "InProgress", "Open", "Deleted", "Complete" };
		case Open:
			return new String[] { "Open", "Deleted" };
		case Deleted:
			return new String[] { "Deleted", "Open"};
		case Complete:
			return new String[] { "Complete", "Open", "Deleted" };
		default:
			System.err.println("An unknown status was entered for the Requirement. Problem!");
			return new String[] {""};
		}
	}
	
	/** Takes the current status of a requirement and outputs an array of 
	 *  strings the represent the possible statuses that the requirement
	 *  can be switched to
	 * 
	 * @param currentStatus The current status of the requirement as a string
	 * @return The possible statuses that a requirement can be changed to.
	 */
	public static String[] getAvailableStatuses(String currentStatus){
		return getAvailableStatuses(RequirementStatus.toStatus(currentStatus));
	}
}    

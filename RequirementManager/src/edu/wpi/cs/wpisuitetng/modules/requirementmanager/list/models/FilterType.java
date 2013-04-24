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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models;

/**	Possible values for the type of filter.
 *
 */
public enum FilterType {
	Id,
	Name,
	Description,
	Type,
	Status,
	Priority,
	ReleaseNumber,
	Estimate,
	ActualEffort,
	Iteration,
	AssignedUsers,
	Other;
	
	/** Converts a string to the proper FilterType
	 * 
	 * NOTE: This method is designed to only take strings retrieved from a UE.dropdonwlist that contains
	 * only the enums listed as FilterType.
	 * 
	 * @param toConvert The string you want to convert
	 * 
	 * @return the proper FilterType
	 */
	public static FilterType toType (String toConvert){
		return valueOf(toConvert);
	}
	
	public String toString () {
		switch (FilterType.this) {
		case ActualEffort:
			return "ActualEffort";
		case Description:
			return "Description";
		case Estimate:
			return "Estimate";
		case Id:
			return "Id";
		case Name:
			return "Name";
		case Other:
			return "";
		case Priority:
			return "Priority";
		case ReleaseNumber:
			return "ReleaseNumber";
		case Status:
			return "Status";
		case Type:
			return "Type";
		case Iteration:
			return "Iteration";
		case AssignedUsers:
			return "AssignedUsers";
		default:
			return "";		
		}
	}
}

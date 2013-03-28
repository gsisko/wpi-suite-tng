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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementType;

/** Possible operations that a filter can perform
 * 
 * NOTE: If filtering by a field that is an "enum", only use EqualTo or NotEqualTo
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
	 * NOTE: This method is designed to only take strings retrieved from a UE.dropdonwlist that contains
	 * only the enums listed as OperatorType.
	 * 
	 * @param toConvert The string you want to convert
	 * 
	 * @return the proper OperatorType
	 */
	public static OperatorType toType (String toConvert){
		if (toConvert.equals("=")) {
			return EqualTo;
		} else if (toConvert.equals(">")) {
			return GreaterThan;
		} else if (toConvert.equals(">=")) {
			return GreaterThanOrEqualTo;
		} else if (toConvert.equals("<")) {
			return LessThan;
		} else if (toConvert.equals("=<")) {
			return LessThanOrEqualTo;
		} else if (toConvert.equals("!=")) {
			return NotEqualTo;
		} else {
			return valueOf(toConvert);
		}
	}

	public String toString () {
		switch (OperatorType.this) {
		case Contains:
			return "Contains";
		case DoesNotContain:
			return "DoesNotContain";
		case EqualTo:
			return "=";
		case GreaterThan:
			return ">";
		case GreaterThanOrEqualTo:
			return ">=";
		case LessThan:
			return "<";
		case LessThanOrEqualTo:
			return "<=";
		case NotEqualTo:
			return "!=";
		case Other:
		default:
			return "";
		}
	}


	/** Performs the operation described for integers. Returns false for 
	 *  any non-integer operations, like Contains etc.
	 * 
	 * @param sample The first argument- sample in filter
	 * @param fromReq The second argument- from the Requirement
	 * 
	 * @return the result
	 */
	public static boolean perform(OperatorType op,  Integer sample, Integer fromReq){
		switch(op){
		case EqualTo:
			return sample == fromReq;
		case GreaterThan:
			return sample < fromReq;
		case GreaterThanOrEqualTo:
			return sample <= fromReq;
		case LessThan:
			return sample > fromReq;
		case LessThanOrEqualTo:
			return sample >= fromReq;
		case NotEqualTo:
			return sample != fromReq;
		default:
			return false; // any other operator should be false
		}
	}


	/** Performs the operation described for Strings. Returns false 
	 *  for requested operations other than Contains/DoesNotContain/EqualTo/NotEqualTo
	 * 
	 * @param sample The first argument- sample in filter
	 * @param fromReq The second argument- from the Requirement
	 * 
	 * @return the result of the operation
	 */
	public static boolean perform(OperatorType op, String sample, String fromReq, boolean isEnum){
		String reqField = fromReq;
		
		if (isEnum){ // Special cases for comparing specific enums
			if (fromReq.toString().equals("nopriority") || fromReq.toString().equals("notype") || fromReq.toString().equals("") ){
				reqField = "";
			}			
		}
		
		
		
		
		switch(op){
		case Contains:
			if (isEnum) return false;// False because enums can't be compared by contain
			return reqField.contains(sample);
		case DoesNotContain:
			if (isEnum) return false; // False because enums can't be compared by contain
			return !reqField.contains(sample); // if it contains, returns false
		case EqualTo:
			return sample.equals(reqField);
		case NotEqualTo:
			return !sample.equals(reqField); // if not equal, return true
		default:
			return false; // any other operator should be false


		}	
	}




	/** Performs the operation described for RequirementType. Returns false 
	 *  for requested operations other than EqualTo/NotEqualTo
	 * 
	 * @param sample The first argument- sample in filter
	 * @param fromReq The second argument- from the Requirement
	 * 
	 * @return the result of the operation
	 */
	public static boolean perform(OperatorType op, RequirementType sample, RequirementType fromReq){
		switch(op){
		case EqualTo:
			return sample == fromReq;
		case NotEqualTo:
			return sample != fromReq;
		default:
			return false;	// any other operator should be false
		}		
	}

	/** Performs the operation described for RequirementStatus. Returns false 
	 *  for requested operations other than EqualTo/NotEqualTo
	 * 
	 * @param sample The first argument- sample in filter
	 * @param fromReq The second argument- from the Requirement
	 * 
	 * @return the result of the operation
	 */
	public static boolean perform(OperatorType op, RequirementStatus sample, RequirementStatus fromReq){
		switch(op){
		case EqualTo:
			return sample == fromReq;
		case NotEqualTo:
			return sample != fromReq;
		default:
			return false;	// any other operator should be false
		}		
	}

	/** Performs the operation described for RequirementPriority. Returns false 
	 *  for requested operations other than EqualTo/NotEqualTo
	 * 
	 * @param sample The first argument- sample in filter
	 * @param fromReq The second argument- from the Requirement
	 * 
	 * @return the result of the operation
	 */
	public static boolean perform(OperatorType op, RequirementPriority sample,RequirementPriority fromReq){
		switch(op){
		case EqualTo:
			return sample == fromReq;
		case NotEqualTo:
			return sample != fromReq;
		default:
			return false;	// any other operator should be false
		}		
	}
}

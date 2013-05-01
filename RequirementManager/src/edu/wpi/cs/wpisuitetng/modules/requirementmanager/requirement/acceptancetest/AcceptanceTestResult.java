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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.acceptancetest;

/** An enum describing the possible states of an Acceptance Test. 
 * AcceptanceTests are created with the state "None" 
 */
public enum AcceptanceTestResult {
	None,
	Passed,
	Failed;
	
	/**Converts a String (passed in as "toConvert") to the
	 * appropriate AcceptanceTestResult
	 * 
	 * @param toConvert The String to convert to an AcceptanceTestResult
	 * @return the finished AcceptanceTestResult 
	 */
	public static AcceptanceTestResult toResult (String toConvert) {
		return valueOf(toConvert);
	}
}

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
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers;


/**
 * It provides methods for controllers for models 
 *
 */
public interface IController {
	/**
	 * provide action listener or other ways to check whether a certain action is performed
	 * @param ActionEvent 
	 */
	public void perform();
	
	/**
	 * takes a string and checks whether the action is performed successfully
	 */
	public void success(String JSONString);
	
	/**
	 * perform certain actions if the intended actions failed
	 */
	public void fail();
	
	/**
	 * gives an error 
	 */
	public void error(String error);
	
}

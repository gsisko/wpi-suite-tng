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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.observers;

import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/** This interface provides methods for observers in the list
 */
public interface IObserver {
	/** Upon success, tell the controller to trigger a refresh 
	 * @param iReq The request response from the server 
	 */
	void responseSuccess(IRequest iReq);
	
	/**  Upon failure, prints to console
	 * @param iReq The request response from the server 
	 */
	void responseError(IRequest iReq) ;
	
	/** Upon failure, prints to console
	 * @param iReq The request response from the server 
	 * @param exception unused
	 */
	void fail(IRequest iReq, Exception exception);
}

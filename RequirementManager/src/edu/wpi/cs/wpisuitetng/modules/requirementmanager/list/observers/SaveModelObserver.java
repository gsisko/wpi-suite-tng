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

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.SaveModelController;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/** This observer is called when a response is received from a request
 * to the server to save a message. 
 */
public class SaveModelObserver implements RequestObserver {
	
	/** The controller managing the network request */
	private final SaveModelController rmcontroller;
	
	/** Constructor that takes a reference to the controller managing the request
	 *  
	 * @param saveModelController The controller managing the network request 
	 */
	public SaveModelObserver(SaveModelController saveModelController) {
		rmcontroller = saveModelController;
	}
	
	/** Parse the message that was received from the server and tells the controller
	 * @param iReq the request sent from the server
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	public void responseSuccess(IRequest iReq) {
		// Pass the messages back to the controller
		rmcontroller.saveSuccess();
	}
	
	/** This method responses when there is a save response error
	 * @param iReq the request sent from the server
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	public void responseError(IRequest iReq) {
		System.err.println("Cannot save the model.");
	}
	
	/** This method responses when the save action failed 
	 * 
	 * @param iReq the request sent from the server
	 * @param exception unused
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("Fail: Cannot save the model.");
	}

}

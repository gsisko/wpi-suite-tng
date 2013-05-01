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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement;

import java.util.ArrayList;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.observers.IObserver;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/** An observer for a request to retrieve all requirements
 */
public class RetrieveAllUsersObserver implements RequestObserver,IObserver{

	/** The user chooser managing the request */
	protected UserChooserTab userChooser;

	/** Construct the observer
	 * @param userChooser UserChooserTab The UserChooserTab that the response is for
	 */
	public RetrieveAllUsersObserver(UserChooserTab userChooser) {
		this.userChooser = userChooser;
	}

	/** Upon a successful retrieval of data, the value combo box is filled with the names of the users
	 *  retrieved from the database.
	 * @param iReq IRequest
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(IRequest)
	 */
	public void responseSuccess(IRequest iReq) {
		Request request = (Request) iReq;

		// get the response from the request
		ResponseModel response = request.getResponse();

		User[] users = User.fromJSONArray(response.getBody());
		
		ArrayList<User> userList = new ArrayList<User>();
		
		for (int i = 0; i < users.length; i++)
			userList.add(users[i]);
		
		userChooser.setUsers(userList);
		userChooser.resumeInitialization();
	}

	/** This method is unused, but is required by the RequestObserver interface
	 * @param iReq IRequest
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(IRequest)
	 */
	public void responseError(IRequest iReq) {
	}

	/** This method is unused, but is required by the RequestObserver interface
	 * @param iReq IRequest
	 * @param exception Exception
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(IRequest, Exception)
	 */
	public void fail(IRequest iReq, Exception exception) {
	}
}

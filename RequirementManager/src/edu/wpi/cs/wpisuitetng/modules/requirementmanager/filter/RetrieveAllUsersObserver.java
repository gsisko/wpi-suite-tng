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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter;

import javax.swing.DefaultComboBoxModel;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.observers.IObserver;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/** An observer for a request to retrieve all users specific to the Filter builder panel. Upon
 * a successful retrieval of data, the value combo box is filled with the names of the users
 * retrieved from the database.
 */
public class RetrieveAllUsersObserver implements RequestObserver,IObserver{

	/** The filterBuilderPanel managing the request */
	protected FilterBuilderPanel filterBuilderPanel;

	/** Construct the observer
	 * @param controller
	 */
	public RetrieveAllUsersObserver(FilterBuilderPanel filterBuilderPanel) {
		this.filterBuilderPanel = filterBuilderPanel;
	}

	/** Upon a successful retrieval of data, the value combo box is filled with the names of the users
	 *  retrieved from the database.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void responseSuccess(IRequest iReq) {
		Request request = (Request) iReq;

		// get the response from the request
		ResponseModel response = request.getResponse();

		User[] users = User.fromJSONArray(response.getBody());

		// Get the users' names
		String[] userList = new String[users.length + 1];
		userList[0] = "";
		for (int i = 0; i < users.length; i++) {
			userList[i+1] = users[i].getName();
		}

		// Set the user names to be the options in the combo box
		DefaultComboBoxModel  valb = new DefaultComboBoxModel (userList);
		filterBuilderPanel.getFilterValueBox().setModel(valb);
		
		// Must set the combo box selected index properly
		String oldName = filterBuilderPanel.getCurrentFilter().getValue();
		int i = 0; // the index
		for (String aName: userList){
			if (aName.equals(oldName)){
				filterBuilderPanel.getFilterValueBox().setSelectedIndex(i);
			}
			i++;
		}
	}

	/** unused */
	public void responseError(IRequest iReq) {	}

	/** unused */
	public void fail(IRequest iReq, Exception exception) {	}
}

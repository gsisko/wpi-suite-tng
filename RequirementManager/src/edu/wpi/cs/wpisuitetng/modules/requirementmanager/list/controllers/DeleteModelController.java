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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.observers.DeleteModelObserver;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IBuilderPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IListPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/** This controller handles the full process of deleting filters from 
 *  the press of the delete button to sending the delete message
 */
public class DeleteModelController implements ActionListener {
	
	/**  The list view that this controller is watching */
	private final IListPanel listView;
	/**  The builder view that this controller must interact with */
	private final IBuilderPanel builderView;
	/** The model name, in string form, which will be used for sending messages */
	private final String modelName;

	/** Constructs a controller with an action listener that can, on a button
	 *  press, delete the highlighted items in a list view.
	 * 
	 * @param listView The list view that this controller is watching
	 * @param builderView The builder view that this controller must interact with
	 * @param modelName  The model name, in string form, which will be used for sending messages
	 */
	public DeleteModelController(IListPanel listView, IBuilderPanel builderView, String modelName){
		this.listView = listView;
		this.modelName = modelName;
		this.builderView = builderView;
	}
	
	/** provide action listener or other ways to check whether a certain action is performed
	 */
	public void perform(){
		String[] uniqueIdentifiers = listView.getSelectedUniqueIdentifiers();
		// get array of row numbers, if there are any highlighted rows
		for(int i = 0; i < uniqueIdentifiers.length; i++){			
			perform(uniqueIdentifiers[i]);
		}

		// Remove anything in the builder panel whenever the delete button is pressed and also
		// set the "Cancel" button back to new
		builderView.resetFields();
		builderView.setInputEnabled(false);

		listView.setDeleteEnabled(false);
		listView.setCancelBtnToNew();
	}
	
	/** provide action listener or other ways to check whether a certain action is performed
	 * @param String
	 */
	public void perform(String uniqueIdentifier){
		// Create and send a request for the Model with the given ID
		// Send message for each Model ID for deletion
		Request request;
		request = Network.getInstance().makeRequest("requirementmanager/" + modelName + "/" + uniqueIdentifier, HttpMethod.DELETE);
		request.addObserver(new DeleteModelObserver(this));
		request.send();
	}
	
	/** takes a string and checks whether the action is performed successfully
	 */
	public void success(String JSONString)
	{
	}
	
	/** perform certain actions if the intended actions failed
	 */
	public void fail(){
		System.err.println("Fail: Cannot delete the " + this.getModelName());
	}
	
	/** gives an error 
	 */
	public void error(String error){
		System.err.println("Could not delete the " + this.getModelName());
	}

	/** Action listener for when the "Delete" button is pressed
	 *  AND more than one Filter in the list is highlighted.
	 * 
	 * NOTE: Doubles as a "Cancel" button
	 * 
	 * @param buttonPress The input that triggers the controller 
	 */
	public void actionPerformed(ActionEvent buttonPress) {		
		perform();
	}

	/** Triggers a refresh of all list views, starting with the view that holds this controller.
	 *  If that doesn't work, the builder panel is tried, if both fail, an error message is printed. 
	 */
	public void refreshListViews() {;
		// Try to refresh all from the list, if that doesn't work
		if (!listView.refreshAll())	{	
			System.err.println("Fail: cannot refresh views after deleting a" + modelName);
		}
	}

	/** Gets the model name in this controller
	 * @return the name that is being played with in this controller
	 */
	public String getModelName(){
		return modelName;
	}
}




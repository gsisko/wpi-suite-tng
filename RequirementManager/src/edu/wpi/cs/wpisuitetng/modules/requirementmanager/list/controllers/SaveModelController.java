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

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.observers.SaveModelObserver;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IBuilderPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IBuilderPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IListPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/** This controller manages the saving of one model at a time, as well as 
 *  acting on server responses caught by the observer
 * 
 */
public class SaveModelController implements ActionListener 
{
	/**  The list view that this controller is watching */
	private final IListPanel listView;
	
	/**  The builder view that this controller must interact with */
	private final IBuilderPanel builderView;
		
	/** The model name, in string form, which will be used for sending messages */
	private final String modelName;
	
	/** Constructs a controller with an action listener that can, on a button
	 *  press, save the model currently loaded into the builder view
	 * 
	 * @param listView The list view that this controller is watching
	 * @param builderView The builder view that this controller must interact with
	 * @param modelName  The model name, in string form, which will be used for sending messsages
	 */
	public SaveModelController(IListPanel listView, IBuilderPanel builderView, String modelName) 
	{
		this.listView = listView;
		this.modelName = modelName;
		this.builderView = builderView;
	}

	/** This controller is currently on a button, so when that button is pressed this activates and 
	 *  sends off the model
	 * @param event the event that triggered the action
	 */
	public void actionPerformed(ActionEvent event) 
	{
		final Request request;
		if ( builderView.getCurrentMode().equals(Mode.EDIT))
			request = Network.getInstance().makeRequest("requirementmanager/" + modelName, HttpMethod.POST); // post == update
		else 
			request = Network.getInstance().makeRequest("requirementmanager/" + modelName, HttpMethod.PUT); // PUT == create
		
		// make a PUT http request and let the observer get the response
		String body = builderView.convertCurrentModelToJSON();
		
		// Check to make sure the message is good, or at least not nothing
		if (body == null){
			System.err.println("Failed to get the model");
			return;
		}
		request.setBody(body); // put the new message in the body of the request
		request.addObserver(new SaveModelObserver(this)); // add an observer to process the response
		request.send();		
	}
	
	/** Simple success message for saving a model.  
	 */
	public void saveSuccess() {
		// If "getUniqueID" was added to Model, then overridden by models, that ID 
		// could be printed here and we would have a better save message

		builderView.resetFields();
		builderView.setInputEnabled(false);
		
		listView.setCancelBtnToNew();
		listView.refreshAll();
		builderView.getMyParent().getParent().getController().refreshData();
	}

	/** Triggers a refresh of all list views, starting with the view that holds this controller.
	 *  If that doesn't work, the builder panel is tried, if both fail, an error message is printed.
	 */
	public void refreshListViews() {
		// Try to refresh all from the list, if that doesn't work
		if (!listView.refreshAll())	{	
			System.err.println("Failed to refresh the list view after saving the " + modelName);
		}		
	}
}
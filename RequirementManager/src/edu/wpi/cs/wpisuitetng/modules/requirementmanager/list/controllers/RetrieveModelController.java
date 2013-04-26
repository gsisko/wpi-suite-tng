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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.observers.RetrieveModelObserver;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IBuilderPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IBuilderPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IListPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/** Controller to handle retrieving one requirement from the server triggered
 *  by double clicking on a model in the list of a list panel  
 */
public class RetrieveModelController extends MouseAdapter {
	/**  The list view that this controller is watching */
	private final IListPanel listView;
	/**  The builder view that this controller must interact with */
	private final IBuilderPanel builderView;	
	/** The model name, in string form, which will be used for sending messages */
	private final String modelName;
	
	/** Constructs a controller with an action listener that can load up a 
	 *  model when an item is double clicked in the list.
	 * 
	 * @param listView The list view that this controller is watching
	 * @param builderView The builder view that this controller must interact with
	 * @param modelName  The model name, in string form, which will be used for sending messages
	 */
	public RetrieveModelController(IListPanel listView, IBuilderPanel builderView, String modelName){
		this.listView = listView;
		this.modelName = modelName;
		this.builderView = builderView;
	}

	/** When a row is double clicked on in a JTable in a list view, this action
	 *  is triggered and full model data is retrieved from the server.
	 *  
	 * @see java.awt.event.MouseAdapter#mouseClicked(MouseEvent)
	 */
	public void mouseClicked(MouseEvent me) {
		if (me.getClickCount() >= 2) { /* respond to double clicks */	
			// Get the unique identifier of the item that was double clicked
			String modelID = listView.getSelectedUniqueIdentifier(me);
			
			// Create and send a request for the requirement with the given ID
			Request request;
			request = Network.getInstance().makeRequest("requirementmanager/" + modelName +"/" + modelID, HttpMethod.GET);
			request.addObserver(new RetrieveModelObserver(this));
			request.send();			
		}
	}

	/** Called by {@link RetrieveModelObserver} when the response
	 *  is received from the server. The Builder fields are set up and 
	 *  populated with the received model. The list panel button for 
	 *  new/cancel is set to cancel mode.
	 *  
	 * @param jsonArray JSON string holding an array of one filter the filter 
	 */
	public void showModel(String jsonArray) {
		// if a user has double-clicked on a filter, set UI fields appropriately
		builderView.displayModelFromJSONArray(jsonArray);
		builderView.setModeAndBtn(Mode.EDIT);
		builderView.setInputEnabled(true);
		
		listView.setNewBtnToCancel();
	}

	/** Called by {@link RetrieveModelObserver} when an error
	 *  occurred getting the model from the server.
	 */
	public void errorRetrievingModel(String error) {
		System.err.println("Error retrieving the model");
	}
}

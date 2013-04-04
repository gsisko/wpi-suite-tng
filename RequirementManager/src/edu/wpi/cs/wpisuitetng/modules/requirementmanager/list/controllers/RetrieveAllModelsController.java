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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.observers.RetrieveAllModelsObserver;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IBuilderPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IListPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/** Controller to handle retrieving all Models from the server and
 *  displaying them in the {@link SearchFiltersView} for a list panel */
public class RetrieveAllModelsController implements ActionListener{
	/**  The list view that this controller is watching */
	private final IListPanel listView;
	/**  The builder view that this controller must interact with */
	@SuppressWarnings("unused") // May be used later
	private final IBuilderPanel builderView;
	/** The model name, in string form, which will be used for sending messages */
	private final String modelName;

	/** Constructs a controller with an action listener that can, on a button
	 *  press or call, refresh all models in the listView
	 * 
	 * @param listView The list view that this controller is watching
	 * @param builderView The builder view that this controller must interact with
	 * @param modelName  The model name, in string form, which will be used for sending messages
	 */
	public RetrieveAllModelsController(IListPanel listView, IBuilderPanel builderView, String modelName){
		this.listView = listView;
		this.modelName = modelName;
		this.builderView = builderView;
	}

	/** Once the attached button is pressed, this action is triggered. */
	public void actionPerformed(ActionEvent arg0) {
		this.refreshData();		
	}
	
	/** Sends a request for all of the filters          */
	public void refreshData() {
		Request request;
		request = Network.getInstance().makeRequest("requirementmanager/" + modelName, HttpMethod.GET);
		request.addObserver(new RetrieveAllModelsObserver(this));
		request.send();
	}

	/** This method is called by the {@link RetrieveAllFiltersRequestObserver} when the
	 *  response is received
	 * 
	 * @param jsonString an array of filters returned by the server
	 */
	public void receivedData(String jsonString) {
		listView.showRecievedModels(jsonString);
		System.out.println("All " + modelName  + "s retrieved successfully.");
	}

	/** This method is called by the {@link RetrieveAllFiltersRequestObserver} when an
	 *  error occurs retrieving the filters from the server.
	 */
	public void errorReceivingData(String error) {
		System.err.println("An error occured while receiving data");
	}



}

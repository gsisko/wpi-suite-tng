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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Controller to handle retrieving one requirement from the server
 */
public class RetrieveModelController extends MouseAdapter {
	/**  The list view that this controller is watching */
	private final IListBuilder listView;
	/**  The builder view that this controller must interact with */
	private final IListBuilder builderView;
	
	
	/** The model name, in string form, which will be used for sending messsages */
	private final String modelName;

	
        
        
	/** Constructs a controller with an action listener that can load up a 
	 *  model when an item is double clicked in the list.
	 * 
	 * @param listView The list view that this controller is watching
	 * @param builderView The builder view that this controller must interact with
	 * @param modelName  The model name, in string form, which will be used for sending messsages
	 */
	public RetrieveModelController(IListBuilder listView, IListBuilder builderView, String modelName){
		this.listView = listView;
		this.modelName = modelName;
		this.builderView = builderView;
	}

	/**
	 * @see java.awt.event.MouseAdapter#mouseClicked(MouseEvent)
	 */
	public void mouseClicked(MouseEvent me) {
		if (me.getClickCount() >= 2) { /* respond to double clicks */
		
			// Get the unique identifier of the item that was double clicked
			String modelID = listView.getSelectedUniqueIdentifier();
			
			// Create and send a request for the requirement with the given ID
			Request request;
			request = Network.getInstance().makeRequest("requirementmanager/" + modelName +"/" + modelID, HttpMethod.GET);
			request.addObserver(new RetrieveModelObserver(this));
			request.send();			
		}
	}

	/**
	 * Called by {@link RetrieveFilterRequestObserver} when the response
	 * is received from the server.
	 * @param filter the filter that was retrieved
	 */
	public void showModel(String jsonArray) {
		// if a user has double-clicked on a filter, set UI fields appropriately
		builderView.translateAndDisplayModel(jsonArray);
		
		listView.clearAndReset();
	}

	/**
	 * Called by {@link RetrieveModelObserver} when an error
	 * occurred getting the model from the server.
	 */
	public void errorRetrievingModel(String error) {
		System.err.println("Error retrieving the model");
	}


}

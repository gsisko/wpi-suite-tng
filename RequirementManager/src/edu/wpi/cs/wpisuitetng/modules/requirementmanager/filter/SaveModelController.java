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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class SaveModelController implements ActionListener 
{
	/**  The list view that this controller is watching */
	private final IListBuilder listView;
	/**  The builder view that this controller must interact with */
	private final IListBuilder builderView;
		
	/** The model name, in string form, which will be used for sending messsages */
	private final String modelName;
	
	
	/** Constructs a controller with an action listener that can, on a button
	 *  press, save the model currently loaded into the builder view
	 * 
	 * @param listView The list view that this controller is watching
	 * @param builderView The builder view that this controller must interact with
	 * @param modelName  The model name, in string form, which will be used for sending messsages
	 */
	public SaveModelController(IListBuilder listView, IListBuilder builderView, String modelName) 
	{
		this.listView = listView;
		this.modelName = modelName;
		this.builderView = builderView;
	}


	public void actionPerformed(ActionEvent event) 
	{
		// make a PUT http request and let the observer get the response
		final Request request = Network.getInstance().makeRequest("requirementmanager/" + modelName, HttpMethod.PUT); // PUT == create
		request.setBody(builderView.getModelMessage()); // put the new message in the body of the request
		request.addObserver(new SaveModelObserver(this)); // add an observer to process the response
		request.send();		
	}


	/** Simple success message for saving a model.  
	 */
	public void saveSuccess() {
		// If "getUniqueID" was added to Model, then overridden by models, that ID 
		// could be printed here and we would have a better save message
		System.out.println(modelName + " saved successfully");

		builderView.clearAndReset();
		listView.setCancelBtnToNew();
	}

	/** Triggers a refresh of all list views, starting with the view that holds this controller.
	 *  If that doesn't work, the builder panel is tried, if both fail, an error message is printed.
	 * 
	 */
	public void refreshListViews() {
		// Try to refresh all from the list, if that doesn't work
		if (!listView.refreshAll())	{	
			// try to refresh from the builder
			if(!builderView.refreshAll()){
				// Print error message upon both failing
				System.err.println("Fail: cannot refresh views after deleting a Model.");
			}
		
		}
	}

}
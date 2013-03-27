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

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.Filter;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.FilterType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.OperatorType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.FilterBuilderPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.FilterBuilderPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.FilterListPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListRequirementsView;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class SaveFilterController implements ActionListener 
{
	@SuppressWarnings({ "unused" })
	private final FilterListPanel filterList;
    private final FilterBuilderPanel builder;
    private final ListRequirementsView view;
    
    public SaveFilterController(ListRequirementsView view) 
    {
    	this.filterList = view.getListPanel().getFilterPanel();
    	this.builder = view.getListPanel().getBuilderPanel();
    	this.view = view;
    }

    
    public void actionPerformed(ActionEvent event) 
    {
	    if (builder.getMode() == Mode.CREATE) { // if we are creating a new filter
		    // get the fields from the UI
			FilterType type = FilterType.toType(builder.getFilterType().getSelectedItem().toString());
			OperatorType comparator = OperatorType.toType(builder.getFilterOperator().toString());
			String value = builder.getFilterValue().toString();
			
			// make a PUT http request and let the observer get the response
		    final Request request = Network.getInstance().makeRequest("filtermanager/filter", HttpMethod.PUT); // PUT == create
		    request.setBody(new Filter(type, comparator, value, true).toJSON()); // put the new message in the body of the request
		    request.addObserver(new SaveFilterObserver(this)); // add an observer to process the response
		    request.send();
    	}
		else if (builder.getMode() == Mode.EDIT){ // we are updating an existing filter
			// make a new filter to store the updated data
			Filter updatedFilter = new Filter(); 
			
			Filter oldFilter = builder.getCurrentFilter();
			
			// Make sure to use the old ID
			updatedFilter.setUniqueID(oldFilter.getUniqueID());
			// Copy in the new value of the fields
			updatedFilter.setType(FilterType.toType(builder.getFilterType().getSelectedItem().toString()));
			updatedFilter.setComparator(OperatorType.toType(builder.getFilterOperator().toString()));
			updatedFilter.setValue(builder.getFilterValue().toString());
			
			// make a POST http request and let the observer get the response
		    final Request request = Network.getInstance().makeRequest("filtermanager/filter", HttpMethod.POST); // POST == update
		    request.setBody(updatedFilter.toJSON()); // put the new message in the body of the request
		    request.addObserver(new SaveFilterObserver(this)); // add an observer to process the response
		    request.send();
		}
		else {	// Mode was not set correctly
			
		}
    }
    
    
    /**
     * Simple success message for saving a new filter.  If we want the boxes to clear automatically,
     * this is probably where we would want to implement it.
     * @param newReq Filter that was saved.
     */
	public void saveSuccess(Filter newReq) {
		// if success, set all of the UI fields appropriately for post-save actions
		if (newReq != null) {
			System.out.print("Filter " +/* newReq.getId() +*/ " saved successfully\n");
			/*
			view.getFilterName().setText("");
			view.getFilterDescription().setText("");
			view.getFilterType().setSelectedIndex(0);
			view.getFilterStatus().setSelectedIndex(0);
			view.getFilterPriority().setSelectedIndex(0);
			view.getFilterReleaseNumber().setText("");
			view.getFilterEstimate().setText("");
			view.getFilterActualEffort().setText("");
			
			view.getSaveButton().setText("Update");
			view.getSaveButton().setEnabled(false);

			view.getFilterName().setEnabled(false);
			view.getFilterDescription().setEnabled(false);
			view.getFilterType().setEnabled(false);
			view.getFilterStatus().setEnabled(false);
			view.getFilterPriority().setEnabled(false);
			view.getFilterReleaseNumber().setEnabled(false);
			view.getFilterEstimate().setEnabled(false);
			view.getFilterActualEffort().setEnabled(false);*/
		}
		else {
			System.err.print("Undected error saving filter\n");
		}
	}
	
	
	public ListRequirementsView getView() {
		return view;
	}
}
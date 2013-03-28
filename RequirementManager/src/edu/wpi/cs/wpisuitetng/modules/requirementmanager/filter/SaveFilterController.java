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

import javax.swing.JOptionPane;

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
	private FilterListPanel filterList;
    private FilterBuilderPanel builder;
    private final ListRequirementsView view;
    
    public SaveFilterController(ListRequirementsView view) 
    {
    	
    	this.view = view;
    }

    
    public void actionPerformed(ActionEvent event) 
    {
    	this.builder = view.getListPanel().getBuilderPanel();
    	this.filterList = view.getListPanel().getFilterPanel();
    	
    	if (builder.getFilterValue().getText().length() == 0) {
    		JOptionPane.showMessageDialog(null, "Value cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
    		return;
    	}
    	
    	if (builder.getCurrentMode() == Mode.CREATE) { // if we are creating a new filter
	    	System.err.println("Creating a new filter");
		    // get the fields from the UI
			FilterType type = FilterType.toType(builder.getFilterType().getSelectedItem().toString());
			OperatorType comparator = OperatorType.toType(builder.getFilterOperator().getSelectedItem().toString());
			String value = builder.getFilterValue().getText();
			
			// make a PUT http request and let the observer get the response
		    final Request request = Network.getInstance().makeRequest("requirementmanager/filter", HttpMethod.PUT); // PUT == create
		    request.setBody(new Filter(type, comparator, value, true).toJSON()); // put the new message in the body of the request
		    request.addObserver(new SaveFilterObserver(this)); // add an observer to process the response
		    request.send();
    	}
		else if (builder.getCurrentMode() == Mode.EDIT){ // we are updating an existing filter
			System.err.println("Editing an existing filter");
			// make a new filter to store the updated data
			Filter updatedFilter = new Filter(); 
			
			Filter oldFilter = builder.getCurrentFilter();
			
			// Make sure to use the old ID
			updatedFilter.setUniqueID(oldFilter.getUniqueID());
			// Copy in the new value of the fields
			updatedFilter.setType(FilterType.toType(builder.getFilterType().getSelectedItem().toString()));
			updatedFilter.setComparator(OperatorType.toType(builder.getFilterOperator().getSelectedItem().toString()));
			updatedFilter.setValue(builder.getFilterValue().getText());
			
			// make a POST http request and let the observer get the response
		    final Request request = Network.getInstance().makeRequest("requirementmanager/filter", HttpMethod.POST); // POST == update
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
     * @param newFilter Filter that was saved.
     */
	public void saveSuccess(Filter newFilter) {
		if (newFilter != null) {
			System.out.print("Filter " + newFilter.getUniqueID() + " saved successfully\n");
		}
		else {
			System.err.print("Undected error saving filter\n");
		}
	}
	
	
	public ListRequirementsView getView() {
		return view;
	}


	/**
	 * @return the filterList
	 */
	public FilterListPanel getFilterList() {
		return filterList;
	}
}
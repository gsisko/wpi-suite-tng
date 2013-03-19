package edu.wpi.cs.wpisuitetng.modules.requirementmanager.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controllers.SaveRequirementObserver;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.*;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.views.RequirementPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class SaveRequirementController implements ActionListener 
{
    static private int count = 0;
    //Will probably change from mainView to something that fits the description, but is a starting point
    private final RequirementPanel view;

    public SaveRequirementController(RequirementPanel view) 
    {
    	this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent event) 
    {
	String name = view.getRequirementName().getText();
	String description = view.getRequirementDescription().getText();
	int releaseNumber = Integer.parseInt(view.getRequirementReleaseNumber().getText());
	//RequirementStatus status = RequirementStatus.toStatus(view.getRequirementStatus().getSelectedItem().toString());
	RequirementPriority priority = RequirementPriority.toPriority(view.getRequirementPriority().getSelectedItem().toString());
	int estimate = Integer.parseInt(view.getRequirementEstimate().getText());
	//int actualEffort = Integer.parseInt(view.getRequirementActualEffort().getText());
	
	int id = count;
	
	if((name.length() > 0 && name.length() <= 100) && (description.length() > 0))
	{
	    final Request request = Network.getInstance().makeRequest("requirementmanager/requirement/" + id, HttpMethod.PUT); // PUT == create
	    //final Request request = Network.getInstance().makeRequest("requirementmanager/requirement", HttpMethod.PUT); // PUT == create
	   
	    request.setBody(new Requirement(name, description, releaseNumber, priority, estimate).toJSON()); // put the new message in the body of the request
	   
	    request.addObserver(new SaveRequirementObserver(this)); // add an observer to process the response
	   
	    request.send(); // send the request
	    count++;
	}
	//else throw error for incorrect inputs
	
    }

}


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
	RequirementPriority priority = RequirementPriority.toPriority(view.getRequirementPriority().getSelectedItem().toString());
	int estimate = Integer.parseInt(view.getRequirementEstimate().getText());
	if((name.length() > 0 && name.length() <= 100) && (description.length() > 0))
	{
		if (view.getCreateNew()) {
		    final Request request = Network.getInstance().makeRequest("requirementmanager/requirement", HttpMethod.PUT); // PUT == create
		   
		    request.setBody(new Requirement(name, description, releaseNumber, priority, estimate).toJSON()); // put the new message in the body of the request
		   
		    request.addObserver(new SaveRequirementObserver(this)); // add an observer to process the response
		   
		    request.send();
		}
		else {
			
			// update existing requirement
		}
	}
	//else throw error for incorrect inputs
	
    }
    
    
    /**
     * Simple success message for saving a new requirement.  If we want the boxes to clear automatically,
     * this is probably where we would want to implement it.
     * @param newReq Requirement that was saved.
     */
	public void saveSuccess(Requirement newReq) {
		if (newReq != null) {
			System.out.print("Requirement " + newReq.getId() + " saved successfully\n");
			
			view.getRequirementName().setText("");
			view.getRequirementDescription().setText("");
			view.getRequirementStatus().setSelectedIndex(0);
			view.getRequirementPriority().setSelectedIndex(0);
			view.getRequirementReleaseNumber().setText("");
			view.getRequirementEstimate().setText("");
			view.getRequirementActualEffort().setText("");

			view.getRequirementName().setEnabled(false);
			view.getRequirementDescription().setEnabled(false);
			view.getRequirementStatus().setEnabled(false);
			view.getRequirementPriority().setEnabled(false);
			view.getRequirementReleaseNumber().setEnabled(false);
			view.getRequirementEstimate().setEnabled(false);
			view.getRequirementActualEffort().setEnabled(false);
		}
		else {
			System.err.print("Undected error saving requirement\n");
		}
	}
}


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

    if (view.getCreateNew()) {
	
    	String name = view.getRequirementName().getText();
		String description = view.getRequirementDescription().getText();
		int releaseNumber = Integer.parseInt((view.getRequirementReleaseNumber().getText().equals("")) ? "0" : (view.getRequirementReleaseNumber().getText()));
		RequirementPriority priority = RequirementPriority.toPriority(view.getRequirementPriority().getSelectedItem().toString());
		int estimate = Integer.parseInt((view.getRequirementEstimate().getText().equals("")) ? "0" : view.getRequirementEstimate().getText());
		RequirementType type = RequirementType.toType(view.getRequirementType().getSelectedItem().toString());
	
	    final Request request = Network.getInstance().makeRequest("requirementmanager/requirement/", HttpMethod.PUT); // PUT == create
	   
	    request.setBody(new Requirement(name, description, type, priority,  releaseNumber, estimate).toJSON()); // put the new message in the body of the request
	   
	    request.addObserver(new SaveRequirementObserver(this)); // add an observer to process the response
	   
	    request.send();
	}
	else {
		
		Requirement updatedRequirement = new Requirement(); 
		Requirement oldr = view.getCurrentRequirement();
		
		updatedRequirement.setId(oldr.getId());
		updatedRequirement.setName(view.getRequirementName().getText());
		updatedRequirement.setDescription(view.getRequirementDescription().getText());
		updatedRequirement.setType(RequirementType.toType(view.getRequirementType().getSelectedItem().toString()));
		updatedRequirement.setReleaseNumber(Integer.parseInt((view.getRequirementReleaseNumber().getText().equals("")) ? "0" : view.getRequirementReleaseNumber().getText()));
		updatedRequirement.setStatus(RequirementStatus.toStatus(view.getRequirementStatus().getSelectedItem().toString()));
		updatedRequirement.setPriority(RequirementPriority.toPriority(view.getRequirementPriority().getSelectedItem().toString()));
		updatedRequirement.setEstimate(Integer.parseInt((view.getRequirementEstimate().getText().equals("")) ? "0" : view.getRequirementEstimate().getText()));
		updatedRequirement.setActualEffort(Integer.parseInt((view.getRequirementActualEffort().getText().equals(""))? "0" : view.getRequirementActualEffort().getText()));
	
	    final Request request = Network.getInstance().makeRequest("requirementmanager/requirement/" + updatedRequirement.getId(), HttpMethod.POST); // PUT == create
	   
	    request.setBody(updatedRequirement.toJSON()); // put the new message in the body of the request
	   
	    request.addObserver(new SaveRequirementObserver(this)); // add an observer to process the response
	   
	    request.send();
		
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
			view.getRequirementType().setSelectedIndex(0);
			view.getRequirementStatus().setSelectedIndex(0);
			view.getRequirementPriority().setSelectedIndex(0);
			view.getRequirementReleaseNumber().setText("");
			view.getRequirementEstimate().setText("");
			view.getRequirementActualEffort().setText("");
			
			view.getSaveButton().setText("Update");
			view.getSaveButton().setEnabled(false);

			view.getRequirementName().setEnabled(false);
			view.getRequirementDescription().setEnabled(false);
			view.getRequirementType().setEnabled(false);
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


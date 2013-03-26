package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.SaveRequirementObserver;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.*;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementPanel;
import static edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementPanel.Mode.*;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class SaveRequirementController
{
    private final RequirementPanel view;

    public SaveRequirementController(RequirementView view) 
    {
    	this.view = view.getRequirementPanel();
    }

    public void save() 
    {
    	// check if any inputs are invalid, print an error message if one is
    	if (view.getRequirementName().getText().length() == 0) {
    		JOptionPane.showMessageDialog(null, "Name must be non-blank.", "Error", JOptionPane.ERROR_MESSAGE);
    		return;
    	}
    	if (view.getRequirementName().getText().length() > 100) {
    		JOptionPane.showMessageDialog(null, "Name cannot be greater than 100 characters.", "Error", JOptionPane.ERROR_MESSAGE);
    		return;
    	}
    	if (view.getRequirementDescription().getText().length() == 0) {
    		JOptionPane.showMessageDialog(null, "Description must be non-blank.", "Error", JOptionPane.ERROR_MESSAGE);
    		return;
    	}
    	if (view.getRequirementEstimate().getText().length() == 0) {
    		JOptionPane.showMessageDialog(null, "Estimate must be non-blank.", "Error", JOptionPane.ERROR_MESSAGE);
    		return;
    	}
    	if (view.getRequirementActualEffort().getText().length() == 0) {
    		JOptionPane.showMessageDialog(null, "ActualEffort must be non-blank.", "Error", JOptionPane.ERROR_MESSAGE);
    		return;
    	}
    	
    	
	    if (view.getMode() == CREATE) { // if we are creating a new requirement
		
	    	// get the fields from the UI
	    	String name = view.getRequirementName().getText();
			String description = view.getRequirementDescription().getText();
			int releaseNumber = Integer.parseInt((view.getRequirementReleaseNumber().getText().equals("")) ? "-1" : (view.getRequirementReleaseNumber().getText()));
			RequirementPriority priority = RequirementPriority.toPriority(view.getRequirementPriority().getSelectedItem().toString());
			RequirementType type = RequirementType.toType(view.getRequirementType().getSelectedItem().toString());
		
			// make a PUT http request and let the observer get the response
		    final Request request = Network.getInstance().makeRequest("requirementmanager/requirement", HttpMethod.PUT); // PUT == create
		    request.setBody(new Requirement(name, description, type, priority,  releaseNumber).toJSON()); // put the new message in the body of the request
		    request.addObserver(new SaveRequirementObserver(view.getParent())); // add an observer to process the response
		    request.send();
		}
		else { // we are updating an existing requirement
			
			Requirement oldRequirement = view.getCurrentRequirement();//grab the old requirement
			
			//Check to see if the status update is invalid because the user had tried to change the status from "InProgress" to "Deleted")
			RequirementStatus oldStatus = oldRequirement.getStatus(); //grab the old status
			RequirementStatus newStatus = RequirementStatus.toStatus(view.getRequirementStatus().getSelectedItem().toString()); //get the new status
			if (   ( oldStatus == RequirementStatus.InProgress) && (newStatus == RequirementStatus.Deleted) ) {//if user had tried to change the status from "InProgress" to "Deleted"...
				JOptionPane.showMessageDialog(null, "Description must be non-blank.", "Error", JOptionPane.ERROR_MESSAGE); //popup an error message
				return;//cancel the update
			}
   		 
			// make a new requirement to story the updated data
			Requirement updatedRequirement = new Requirement(); 
			
			// give the new requirement the correct ID number
			updatedRequirement.setId(oldRequirement.getId());
			
			// fill in the rest of the fields with the data from the UI
			updatedRequirement.setName(view.getRequirementName().getText());
			updatedRequirement.setDescription(view.getRequirementDescription().getText());
			updatedRequirement.setType(RequirementType.toType(view.getRequirementType().getSelectedItem().toString()));
			updatedRequirement.setReleaseNumber(Integer.parseInt((view.getRequirementReleaseNumber().getText().equals("")) ? "-1" : view.getRequirementReleaseNumber().getText()));
			updatedRequirement.setStatus(RequirementStatus.toStatus(view.getRequirementStatus().getSelectedItem().toString()));
			updatedRequirement.setPriority(RequirementPriority.toPriority(view.getRequirementPriority().getSelectedItem().toString()));
			updatedRequirement.setEstimate(Integer.parseInt(view.getRequirementEstimate().getText()));
			updatedRequirement.setActualEffort(Integer.parseInt(view.getRequirementActualEffort().getText()));
		
			// make a POST http request and let the observer get the response
		    final Request request = Network.getInstance().makeRequest("requirementmanager/requirement", HttpMethod.POST); // POST == update
		    request.setBody(updatedRequirement.toJSON()); // put the new message in the body of the request
		    request.addObserver(new SaveRequirementObserver(view.getParent())); // add an observer to process the response
		    request.send();
			
		}
	
    }
    
    
    /**
     * Simple success message for saving a new requirement.  If we want the boxes to clear automatically,
     * this is probably where we would want to implement it.
     * @param newReq Requirement that was saved.
     */
	public void saveSuccess(Requirement newReq) {
		// if success, set all of the UI fields appropriately for post-save actions
		if (newReq != null) {
			System.out.print("Requirement " + newReq.getId() + " saved successfully\n");
		}
		else {
			System.err.print("Undected error saving requirement\n");
		}
	}
	
	public RequirementPanel getView() {
		return view;
	}
}


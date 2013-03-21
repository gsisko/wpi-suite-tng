package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirementevents.model;

import javax.swing.DefaultListModel;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/**
 * A data model for a list of RequirementEvents.
 */
@SuppressWarnings({"serial", "rawtypes"})
public class RequirementEventListModel extends DefaultListModel {
	
	/**
	 * Construct a new model and populate with the RequirementEvents contained
	 * in the given Requirement
	 * 
	 * @param requirement the Requirement containing the events to populate this model
	 */
	public RequirementEventListModel(Requirement requirement) {
		update(requirement);
	}
	
	/**
	 * Replaces the contents of this model with the events in the given Requirement
	 * @param model the Requirement containing the events to populate this model
	 */
	public void update(Requirement model) {
		this.clear();
	}
}

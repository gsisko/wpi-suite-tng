package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.validators;

import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashSet;
import java.util.List;
//import java.util.Set;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementEvent;
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementStatus;

/**
 * Validates Requirements so that they fit in with the given Data implementation.
 * 
 * Note that Data could be something used client-side (e.g. a wrapper around a local cache of
 * Users so you can check assignee usernames as-you-type).
 */
public class RequirementValidator {
	
	private Data data;
	private Requirement lastExistingRequirement;
	
	/**
	 * Create a RequirementValidator
	 * 
	 * @param data The Data implementation to use
	 */
	public RequirementValidator(Data data) {
		//TODO: "strict" mode for returning *all* issues, rather than ignoring and overwriting?
		this.data = data;
	}
	
	/**
	 * @return the data
	 */
	public Data getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Data data) {
		this.data = data;
	}

	/**
	 * Return the User with the given username if they already exist in the database.
	 * 
	 * @param username the username of the User
	 * @param issues list of errors to add to if user doesn't exist
	 * @param fieldName name of field to use in error if necessary
	 * @return The User with the given username, or null if they don't exist
	 * @throws WPISuiteException 
	 */
	User getExistingUser(String username, List<ValidationIssue> issues, String fieldName) throws WPISuiteException {
		final List<Model> existingUsers = data.retrieve(User.class, "username", username);
		if(existingUsers.size() > 0 && existingUsers.get(0) != null) {
			return (User) existingUsers.get(0);
		} else {
			issues.add(new ValidationIssue("User doesn't exist", fieldName));
			return null;
		}
	}
	
	/**
	 * Return the Requirement with the given id if it already exists in the database.
	 * 
	 * @param id the id of the Requirement
	 * @param project the project this requirement belongs to
	 * @param issues list of errors to add to if requirement doesn't exist
	 * @param fieldName name of field to use in error if necessary
	 * @return The Requirement with the given id, or null if it doesn't exist
	 * @throws WPISuiteException 
	 */
	Requirement getExistingRequirement(int id, Project project, List<ValidationIssue> issues, String fieldName)
			throws WPISuiteException {
		List<Model> oldRequirements = data.retrieve(Requirement.class, "id", id, project);
		if(oldRequirements.size() < 1 || oldRequirements.get(0) == null) {
			issues.add(new ValidationIssue("Requirement with id does not exist in project", fieldName));
			return null;
		} else {
			return (Requirement) oldRequirements.get(0);
		}
	}
	
	/**
	 * Validate the given model such that any nested models point to appropriate existing models
	 * from the Data given in the constructor.
	 * 
	 * @param session The session to validate against
	 * @param requirement The requirement model to validate
	 * @param mode The mode to validate for
	 * @return A list of ValidationIssues (possibly empty)
	 * @throws WPISuiteException 
	 */
	public List<ValidationIssue> validate(Session session, Requirement requirement/*, Mode mode*/) throws WPISuiteException {
		List<ValidationIssue> issues = new ArrayList<ValidationIssue>();
		if(requirement == null) {
			issues.add(new ValidationIssue("Requirement cannot be null"));
			return issues;
		}
		
		// make sure title and description size are within constraints
		if(requirement.getName() == null || requirement.getName().length() > 100
				|| requirement.getName().length() < 1) {
			issues.add(new ValidationIssue("Required, must be 1-100 characters", "title"));
		}
		if(requirement.getDescription() == null) {
			issues.add(new ValidationIssue("Cannot be empty", "description"));
		}
		
		return issues;
	}

	/**
	 * @return The last existing requirement the validator fetched if in edit mode
	 */
	public Requirement getLastExistingRequirement() {
		return lastExistingRequirement;
	}
	
}

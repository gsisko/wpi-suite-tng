package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models;

import java.util.HashSet;
import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.old.Epic;
import static edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementStatus.*;
import static edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementPriority.*;
import com.google.gson.Gson;

public class Requirement extends AbstractModel {

	private int releaseNumber;
	private RequirementStatus status;
	private RequirementPriority priority;
	private String name;
	private String description;
	private int estimate;
	private int actualEffort;
// The id is not explicitly required.. but here is what it would be	
	private int id; // We need to discuss how to implement this.. possibly migrate to requirement... probably controlled on server end	
	
	private HashSet<Attachment> attachments;
	private HashSet<Note> notes;
	private HashSet<Task> tasks;

// Bare minimum constructor of required fields - Made for very basic functionality for iteration 1
	public Requirement(String name, String description) {
		this.setName(name);
		this.setDescription(description);		  
	// The rest are default values
		this.setActualEffort(0);    //Initial actual effort set to zero
		this.setEstimate(0);		//Initial effort set to zero
		this.setReleaseNumber(-1); // Initialized to zero if not specified
		this.setStatus(NEW);       // Status is always defaulted to NEW
		this.setPriority(LOW);     // Default status is LOW, priority should be overridden 
		this.setAttachments(new HashSet<Attachment>()); // Initializes an empty HashSet of attachments
		this.setNotes(new HashSet<Note>());             // Initializes an empty HashSet of notes
		this.setTasks(new HashSet<Task>());	         // Initializes an empty HashSet of tasks
		this.id = -1; // (-1) will be a flag to the server/database that this value needs to be set
	}	
	
	
// TODO: more constructors that allow for more fields to be filled in	
/*
	public Requirement(String name, String description, RequirementPriority priority) {
		this.setReleaseNumber(-1);  // Optional number  !!! requires attention later 
		this.setStatus(NEW);       // Status is always defaulted to NEW
		this.setPriority(priority);     // Default status is LOW, priority should be overridden 
		this.setName(name);
		this.setDescription(description);
		
		this.setAttachments(new HashSet<Attachment>()); // Initializes an empty HashSet of attachments
		this.setNotes(new HashSet<Note>());             // Initializes an empty HashSet of notes
		this.setTasks(new HashSet<Task>());	         // Initializes an empty HashSet of tasks
		this.id = -1; // (-1) will be a flag to the server/database that this value needs to be set
	}
	
	public Requirement(String name, String description, RequirementPriority priority, int releaseNumber) {
		this.setReleaseNumber(releaseNumber);  // Optional number  !!! requires attention later 
		this.setStatus(NEW);       // Status is always defaulted to NEW
		this.setPriority(priority);     // Default status is LOW, priority should be overridden 
		this.setName(name);
		this.setDescription(description);
		this.setActualEffort(releaseNumber);
		
		
		this.setAttachments(new HashSet<Attachment>()); // Initializes an empty HashSet of attachments
		this.setNotes(new HashSet<Note>());             // Initializes an empty HashSet of notes
		this.setTasks(new HashSet<Task>());	         // Initializes an empty HashSet of tasks
		this.id = -1; // (-1) will be a flag to the server/database that this value needs to be set
	}
*/	

	

	
// These were put into the epic class, Rob is not sure what they do.. but here they are
	/**
	 * Converts this Epic to a JSON string
	 * @return a string in JSON representing this Epic
	 */
	public String toJSON1() {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(this, Epic.class);
		return json;
	}

	public Boolean identify1(Object o) {
		Boolean returnValue = false;
		if(o instanceof Epic && id == ((Epic) o).getId()) {
			returnValue = true;
		}
		if(o instanceof String && Integer.toString(id).equals(o)) {
			returnValue = true;
		}
		return returnValue;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}	
	
	
	
	
	

// The following Functions come from the Model interface
	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

// The following are getters and setters
	/**
	 * @return the actual
	 */
	public int getActualEffort() {
		return actualEffort;
	}

	/**
	 * @param actual the actual to set
	 */
	public void setActualEffort(int actual) {
		this.actualEffort = actual;
	}

	/**
	 * @return the estimate
	 */
	public int getEstimate() {
		return estimate;
	}

	/**
	 * @param estimate the estimate to set
	 */
	public void setEstimate(int estimate) {
		this.estimate = estimate;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the releaseNumber
	 */
	public int getReleaseNumber() {
		return releaseNumber;
	}

	/**
	 * @param releaseNumber the releaseNumber to set
	 */
	public void setReleaseNumber(int releaseNumber) {
		this.releaseNumber = releaseNumber;
	}

	/**
	 * @return the status
	 */
	public RequirementStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(RequirementStatus status) {
		this.status = status;
	}

	/**
	 * @return the priority
	 */
	public RequirementPriority getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(RequirementPriority priority) {
		this.priority = priority;
	}

	/**
	 * @return the attachments
	 */
	public HashSet<Attachment> getAttachments() {
		return attachments;
	}

	/**
	 * @param attachments the attachments to set
	 */
	public void setAttachments(HashSet<Attachment> attachments) {
		this.attachments = attachments;
	}

	/**
	 * @return the notes
	 */
	public HashSet<Note> getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(HashSet<Note> notes) {
		this.notes = notes;
	}

	/**
	 * @return the tasks
	 */
	public HashSet<Task> getTasks() {
		return tasks;
	}

	/**
	 * @param tasks the tasks to set
	 */
	public void setTasks(HashSet<Task> tasks) {
		this.tasks = tasks;
	}

}

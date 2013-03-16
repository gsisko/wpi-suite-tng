package edu.wpi.cs.wpisuitetng.modules.requirementmanager.model;

import java.util.HashSet;
import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import static edu.wpi.cs.wpisuitetng.modules.requirementmanager.model.RequirementStatus.*;
import static edu.wpi.cs.wpisuitetng.modules.requirementmanager.model.RequirementPriority.*;


public abstract class Requirement extends AbstractModel {

	private int releaseNumber;
	private RequirementStatus status;
	private RequirementPriority priority;

	private HashSet<Attachment> attachments;
	private HashSet<Note> notes;
	private HashSet<Task> tasks;
	
	public Requirement(RequirementPriority priority) {
		this.releaseNumber = -1;  /** Optional number  !!! requires attention later */
		this.status = NEW;       // Status is always defaulted to NEW
		this.priority = priority;     // Default status is LOW, priority should be overridden 
		this.attachments = new HashSet<Attachment>(); // Initializes an empty HashSet of attachments
		this.notes = new HashSet<Note>();             // Initializes an empty HashSet of notes
		this.tasks = new HashSet<Task>();	         // Initializes an empty HashSet of tasks
	}
	
	public Requirement(RequirementPriority priority, int releaseNumber) {
		this.releaseNumber = releaseNumber;  /** Optional number  !!! requires attention later */
		this.status = NEW;       // Status is always defaulted to NEW
		this.priority = priority;     // Default status is LOW, priority should be overridden 
		this.attachments = new HashSet<Attachment>(); // Initializes an empty HashSet of attachments
		this.notes = new HashSet<Note>();             // Initializes an empty HashSet of notes
		this.tasks = new HashSet<Task>();	         // Initializes an empty HashSet of tasks
	}
	
	
	
	// The following are getters and setters
	
	
	public int getReleaseNumber() {
		return releaseNumber;
	}
	
	public void setReleaseNumber(int releaseNumber) {
		this.releaseNumber = releaseNumber;
	}

	public RequirementStatus getStatus() {
		return status;
	}

	public void setStatus(RequirementStatus status) {
		this.status = status;
	}

	public RequirementPriority getPriority() {
		return priority;
	}

	public void setPriority(RequirementPriority priority) {
		this.priority = priority;
	}

	public HashSet<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(HashSet<Attachment> attachments) {
		this.attachments = attachments;
	}

	public HashSet<Note> getNotes() {
		return notes;
	}

	public void setNotes(HashSet<Note> notes) {
		this.notes = notes;
	}

	public HashSet<Task> getTasks() {
		return tasks;
	}

	public void setTasks(HashSet<Task> tasks) {
		this.tasks = tasks;
	}

}

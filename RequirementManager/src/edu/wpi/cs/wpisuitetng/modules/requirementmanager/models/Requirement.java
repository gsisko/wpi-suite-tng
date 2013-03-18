package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import static edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementStatus.*;
import static edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementPriority.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Requirement extends AbstractModel {

	private int id;
	private int releaseNumber;
	private RequirementStatus status;
	private RequirementPriority priority;
	private String name;
	private String description;
	private int estimate;
	private int actualEffort;
	/*
	private HashSet<Note> notes;
	private HashSet<Attachment> attachments;
	private HashSet<Task> tasks;
	*/
	private List<RequirementEvent> events;
	
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
		/*
		this.setAttachments(new HashSet<Attachment>()); // Initializes an empty HashSet of attachments
		this.setNotes(new HashSet<Note>());             // Initializes an empty HashSet of notes
		this.setTasks(new HashSet<Task>());	         // Initializes an empty HashSet of tasks
		*/
		this.setEvents(new ArrayList<RequirementEvent>());
		this.id = -1; // (-1) will be a flag to the server/database that this value needs to be set
	}	
	
	// Probable constructor to be called from the user interface
	public Requirement(int releaseNumber, String name, String description, RequirementPriority priority) {
		this.setId(-1); // (-1) will be a flag to the server/database that this value needs to be set
		this.setReleaseNumber(releaseNumber);
		this.setPriority(priority);
		this.setName(name);
		this.setDescription(description);
		
		// The rest are default values
		this.setActualEffort(0);							// Initial actual effort set to zero
		this.setEstimate(0);								// Initial effort set to zero
		this.setStatus(NEW);								// Initial status set to NEW
		/*
		this.setAttachments(new HashSet<Attachment>());		// Initializes an empty HashSet of attachments
		this.setNotes(new HashSet<Note>());					// Initializes an empty HashSet of notes
		this.setTasks(new HashSet<Task>());					// Initializes an empty HashSet of task
		*/
		this.setEvents(new ArrayList<RequirementEvent>());	// Initializes an empty List of events
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


	// The following functions come from the Model interface
	
	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Converts this Epic to a JSON string
	 * @return a string in JSON representing this Epic
	 */
	public String toJSON() {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(this, Attachment.class);
		return json;
	}

	public Boolean identify(Object o) {
		Boolean returnValue = false;
		if(o instanceof Attachment && id == ((Attachment) o).getId()) {
			returnValue = true;
		}
		if(o instanceof String && Integer.toString(id).equals(o)) {
			returnValue = true;
		}
		return returnValue;
	}
	
	// End of Model interface functions
	
	
	/**
	 * Converts the given list of Requirements to a JSON string
	 * @param rlist a list of Requirements
	 * @return a string in JSON representing the list of Requirements
	 */
	public static String toJSON(Requirement[] rlist) {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(rlist, Requirement.class);
		return json;
	}
	
	@Override
	public String toString() {
		return toJSON();
	}
	
	/**
	 * @param json Json string to parse containing Defect
	 * @return The Defect given by json
	 */
	public static Requirement fromJSON(String json) {
		GsonBuilder builder = new GsonBuilder();
		addGsonDependencies(builder);
		return builder.create().fromJson(json, Requirement.class);
	}
	
	/**
	 * @param json Json string to parse containing Requirement array
	 * @return The Requirement array given by json
	 */
	public static Requirement[] fromJSONArray(String json) {
		GsonBuilder builder = new GsonBuilder();
		addGsonDependencies(builder);
		return builder.create().fromJson(json, Requirement[].class);
	}
	
	/**
	 * Add dependencies necessary for Gson to interact with this class
	 * @param builder Builder to modify
	 */
	public static void addGsonDependencies(GsonBuilder builder) {
		RequirementEvent.addGsonDependencies(builder);
	}
	
	@Override
	public void setProject(Project project) {
		super.setProject(project);
		// we need to make sure nested models get the correct project
		if(!notes.isEmpty()) {
			for(Note n : notes) {
				n.setProject(project);
			}
		}
		if(!tasks.isEmpty()) {
			for(Task t : tasks) {
				t.setProject(project);
			}
		}
		if(!attachments.isEmpty()) {
			for(Attachment a : attachments) {
				a.setProject(project);
			}
		}
		if(events != null) {
			for(RequirementEvent e : events) {
				e.setProject(project);
			}
		}
	}

	// The following are getters and setters
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}	
	
	/**
	 * @return the actualEffort
	 */
	public int getActualEffort() {
		return actualEffort;
	}

	/**
	 * @param actual the actual to set
	 */
	public void setActualEffort(int actualEffort) {
		this.actualEffort = actualEffort;
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
	
	
//	/**
//	 * @return the attachments
//	 */
//	public HashSet<Attachment> getAttachments() {
//		return attachments;
//	}
//
//	/**
//	 * @param attachments the attachments to set
//	 */
//	public void setAttachments(HashSet<Attachment> attachments) {
//		this.attachments = attachments;
//	}
//
//	/**
//	 * @return the notes
//	 */
//	public HashSet<Note> getNotes() {
//		return notes;
//	}
//
//	/**
//	 * @param notes the notes to set
//	 */
//	public void setNotes(HashSet<Note> notes) {
//		this.notes = notes;
//	}
//
//	/**
//	 * @return the tasks
//	 */
//	public HashSet<Task> getTasks() {
//		return tasks;
//	}
//
//	/**
//	 * @param tasks the tasks to set
//	 */
//	public void setTasks(HashSet<Task> tasks) {
//		this.tasks = tasks;
//	}
	
	
	/**
	 * @return the events
	 */
	public List<RequirementEvent> getEvents() {
		return events;
	}

	/**
	 * @param events the events to set
	 */
	public void setEvents(List<RequirementEvent> events) {
		this.events = events;
	}

	/** Changes all fields in the current Requirement to equal the fields of the reqUpdate
	 * 
	 * @param reqUpdate Requirement holding the updates
	 */
	public void updateReq(Requirement reqUpdate) {
		setId(reqUpdate.getId());
		setReleaseNumber(reqUpdate.getReleaseNumber());
		setStatus(reqUpdate.getStatus());
		setPriority(reqUpdate.getPriority());
		setName(reqUpdate.getName());
		setDescription(reqUpdate.getDescription());
		setEstimate(reqUpdate.getEstimate());
		setActualEffort(reqUpdate.getActualEffort());
		setEvents(reqUpdate.getEvents());
		/*
		setNotes(reqUpdate.getNotes());
		setAttachments(reqUpdate.getAttachments());
		setTasks(reqUpdate.getTasks());
		*/
	}

}

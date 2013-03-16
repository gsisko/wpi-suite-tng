package edu.wpi.cs.wpisuitetng.modules.requirementmanager.old;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementPriority;


public class Epic {

	private int id; // We need to discuss how to implement this.. possibly migrate to requirement... probably controlled on server end
	private String name;
	private String description;
	
	public Epic(RequirementPriority priority, String name, String description) {
	//	super(priority);
		this.id = -1; // (-1) will be a flag to the server/database that this value needs to be set
		this.setName(name);
		this.setDescription(description);
	}

	public Epic(RequirementPriority priority, int releaseNumber, String name, String description) {
	//	super(priority);
		this.id = -1; // (-1) will be a flag to the server/database that this value needs to be set
		this.setName(name);
		this.setDescription(description);
		// TODO Auto-generated constructor stub
	}

	public void save() {
		// TODO Auto-generated method stub

	}

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
		json = gson.toJson(this, Epic.class);
		return json;
	}

	public Boolean identify(Object o) {
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
	
	/**
	 *  
	 * @return The name of this epic
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name The name of this epic
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @return The description of this epic
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @param description The description of this epic
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}

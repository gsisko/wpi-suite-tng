package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models;

import com.google.gson.Gson;

public class Epic extends Requirement {

	private int id;
	private String name;
	private String description;

	public Epic(RequirementPriority priority, String name, String description) {
		super(priority);

		this.id = -1;
		this.setName(name);
		this.setDescription(description);
	}

	public Epic(RequirementPriority priority, int releaseNumber, String name, String description) {
		super(priority);

		this.id = -1;
		this.setName(name);
		this.setDescription(description);
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

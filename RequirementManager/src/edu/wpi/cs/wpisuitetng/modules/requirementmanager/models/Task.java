package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models;

import java.util.Date;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;


// Additional functionality will need to be added for this class before it is release worthy
public class Task extends AbstractModel {

	private int id;
	private Date date;
	private String message;

	public Task(Date date, String message) {
		this.id = -1;
		this.date = date;
		this.message = message;
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
	 * Converts this Task to a JSON string
	 * @return a string in JSON representing this Task
	 */
	public String toJSON() {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(this, Task.class);
		return json;
	}

	public Boolean identify(Object o) {
		Boolean returnValue = false;
		if(o instanceof Task && id == ((Task) o).getId()) {
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

	/**
	 * 
	 * @return The date when this task was made
	 */


	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	/**
	 * 
	 * @param date The date when this task was made
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * 
	 * @return The message that is the content of this task in the form of a string
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 
	 * @param message The message that is the content of this task in the form of a string
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}

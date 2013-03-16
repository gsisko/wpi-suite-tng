package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

import java.util.Date;

import com.google.gson.Gson;

public class Note extends AbstractModel {

	private int id;
	private Date date;
	private String message;

	public Note(Date date, String message) {
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
	 * Converts this Note to a JSON string
	 * @return a string in JSON representing this Note
	 */
	public String toJSON() {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(this, Note.class);
		return json;
	}

	public Boolean identify(Object o) {
		Boolean returnValue = false;
		if(o instanceof Note && id == ((Note) o).getId()) {
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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


	/** Returns the time-stamp and message contained in this note. Used when you want to take 
	 *  a note and print it to the user. 
	 * 
	 * @see java.lang.Object#toString()
	 * @return the date and message put together as a string   ~~ Similar functionality to the postboard
	 */
	@Override
	public String toString() {
		// Format the date-time stamp, then return it as a string with the message appended
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm a");		
		return dateFormat.format(date) + ":    " + message;
	}	
	
	
	
	/**
	 * 
	 * @return The date when this note was made
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * 
	 * @param date The date when this note was made
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * 
	 * @return The message that is the content of this note in the form of a string
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 
	 * @param message The message that is the content of this note in the form of a string
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}

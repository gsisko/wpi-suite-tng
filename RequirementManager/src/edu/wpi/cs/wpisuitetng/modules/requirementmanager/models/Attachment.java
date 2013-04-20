/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *		Robert Dabrowski
 *		Danielle LaRose
 *		Edison Jimenez
 *		Christian Gonzalez
 *		Mike Calder
 *		John Bosworth
 *		Paula Rudy
 *		Gabe Isko
 *		Bangyan Zhang
 *		Cassie Hudson
 *		Robert Smieja
 *		Alex Solomon
 *		Brian Hetherman
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.changeset.RequirementEvent;

public class Attachment extends RequirementEvent {
	//private User user; 
	//private Date date;
	
	private String fileName;
	private int fileSize;
	private ArrayList<Integer> attachmentPartIds;
	
	/**
	 * Create a Note with given properties
	 * 
	 * @param message The message the user wishes to post
	 */
	public Attachment(String fileName, int fileSize)
	{
		this.type = EventType.ATTACHMENT;
		this.date = new Date();
		
		this.fileName = fileName;
		this.fileSize=fileSize;
		this.attachmentPartIds = new ArrayList<Integer>();	
	}

	public String toString() {
		// Format the date-time stamp
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm a");
				
		return "Attachment added by " + userName +" on "+dateFormat.format(date) ;
	}
	
	/**
	 * @return the message
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param message the message to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the fileSize
	 */
	public int getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	@Override
	public String toJSON() {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(this, Attachment.class);
		return json;
	}

	@Override
	public String getBodyString() {
		return this.fileName;
	}

	@Override
	public String getLabelString() {	
		return "Attachment added by " + userName +" on "+ this.getDate().toString();
	}

	/**
	 * @return the attachmentPartIds
	 */
	public ArrayList<Integer> getAttachmentPartIds() {
		return attachmentPartIds;
	}

	/**
	 * @param attachmentPartIds the attachmentPartIds to set
	 */
	public void setAttachmentPartIds(ArrayList<Integer> attachmentPartIds) {
		this.attachmentPartIds = attachmentPartIds;
	}
	
	
}

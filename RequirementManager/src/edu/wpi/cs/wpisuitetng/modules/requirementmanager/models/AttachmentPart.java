/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team 5 D13
 * 
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

import org.apache.commons.codec.binary.Base64;

public class AttachmentPart extends AbstractModel {
	//private User user; 
	//private Date date;
	
	private int id;
	private int partSize;
	private String attachmentPartByteArray;
	private int partNumber;
	
	/**
	 * Create a Note with given properties
	 * 
	 * @param message The message the user wishes to post
	 */
	public AttachmentPart(int partSize, byte[] filePart, int partNumber)
	{
		id = -1;
		this.partSize = partSize;
		attachmentPartByteArray = Base64.encodeBase64String(filePart);
		this.partNumber = partNumber;
	}

	@Override
	public String toJSON() {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(this, AttachmentPart.class);
		return json;
	}
	
	public static AttachmentPart fromJson(String json) {
		Gson parser = new Gson();
		return parser.fromJson(json, AttachmentPart.class);
	}

	public static AttachmentPart[] fromJsonArray(String json) {
		GsonBuilder builder = new GsonBuilder();
		return builder.create().fromJson(json, AttachmentPart[].class);
	}
	
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
	 * @return the attachmentPartByteArray
	 */
	public byte[] getAttachmentPartByteArray() {
		return Base64.decodeBase64(attachmentPartByteArray);
	}

	/**
	 * @param attachmentPartByteArray the attachmentPartByteArray to set
	 */
	public void setAttachmentPartByteArray(byte[] attachmentPartByteArray) {
		this.attachmentPartByteArray = Base64.encodeBase64String(attachmentPartByteArray);
	}

	/**
	 * @return the partSize
	 */
	public int getPartSize() {
		return partSize;
	}

	/**
	 * @param partSize the partSize to set
	 */
	public void setPartSize(int partSize) {
		this.partSize = partSize;
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the partNumber
	 */
	public int getPartNumber() {
		return partNumber;
	}

	/**
	 * @param partNumber the partNumber to set
	 */
	public void setPartNumber(int partNumber) {
		this.partNumber = partNumber;
	}

	
	
}

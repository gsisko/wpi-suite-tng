/*******************************************************************************
 * Copyright (c) 2012 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    twack
 *    mpdelladonna
 *    bgaffey
 *******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.core.models;

import org.apache.commons.codec.binary.Base64;

import edu.wpi.cs.wpisuitetng.exceptions.SerializationException;
import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * The Data Model representation of a File. Offers
 * 	serialization and database interaction.
 * @author mdelladonna, twack, bgaffey, robertsmieja, bhetherman
 */


public class FilePartModel extends AbstractModel
{


	private String idNum; //ID of this part
	private String fileIdNum; //ID of the file in the Database. Has to be unique!
	private String fileName; //Name of the file
	private Integer fileSize; //Size in bytes
	private String filePart; //Part of a file in Base64 
	/**
	 * Primary constructor for a FileModel
	 * @param idNum - the project ID number as a string
	 * @param filePart - the base64 string representing the file
	 */
	//	public FileModel(String fileName, String idNum, User owner, User[] team, String fileData)
	public FilePartModel(String fileIdNum, String idNum, String fileName, Integer fileSize, String filePart)
	{
		this.idNum = idNum;
		this.fileIdNum = fileIdNum;
		this.fileName = fileName;
		this.fileSize = fileSize;

		if (Base64.isBase64(filePart)){
			this.filePart = filePart;
		} else {
			this.filePart = new String();
		}
	}
	
	/* database interaction */

	/**
	 * Implements Model-specific save logic
	 */
	public void save()
	{
		return; // TODO: implement saving during API - DB Layer Link up
	}

	/**
	 * Implements Model-specific delete logic
	 */
	public void delete()
	{
		return; // TODO: implement deleting during API - DB Layer Link up
	}

	/* Serializing */
	//XXX NOTE: We are serializing using Base64 instead...
	/**
	 * Serializes this Project's member variables into
	 * 	a JSON string.
	 * @return	the JSON string representation of this Project
	 */

	public String toJSON()
	{
		/*
		String json = null;

		json = "{";

		json += "\"name\":\"" + this.fileName +"\"";

		json += ",\"idNum\":\"" + this.idNum+"\"";

		if(this.owner != null)
		{
			json += ",\"owner\":" + this.owner.toJSON();
		}

		if(this.fileData != null && this.fileData.length > 0)
		{
			json += ",\"supportedModules\":[";

			for(String str : this.fileData)
			{
				json += "\"" + str + "\",";
			}

			//remove that last comma
			json = json.substring(0, json.length()-1);

			json += "]";
		}		

		if(this.team != null && this.team.size() > 0)
		{
			json += ",\"team\":[";

			for(User u : this.team)
			{
				json += u.toJSON() + ",";
			}
			//remove that last comma
			json = json.substring(0, json.length()-1);

			json += "]";
		}

		json += "}";
		 */
		return toString();
	}

	/**
	 * Static Project method that serializes a list of Projects
	 * 	into JSON strings.
	 * @param u	The list of Projects to serialize
	 * @return	a comma delimited list of Project JSON strings.
	 */
	public static String toJSON(FileModel[] u)
	{
		/*
		String json ="";

		Gson gson = new Gson();

		json = gson.toJson(u, FileModel[].class);

		return json;
		 */

		return "";
	}

	/**
	 * Deserializes the given JSON String into a Project's member variables
	 * @return	the Project from the given JSON string representation 
	 */
	public static FileModel fromJSON(String json)
	{
		/*
		Gson gson;

		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(FileModel.class, new ProjectDeserializer());

		gson = builder.create();

		return gson.fromJson(json, FileModel.class);
		 */
		return null;
	}

	/* Built-in overrides/overloads */

	/**
	 * Returns the Base64 representation of this object as 
	 * 	the toString.
	 * 
	 * @return	the String representation of this object in Base64
	 */
	public String toString()
	{
		String converted = "";

		//Covert fileSize to byteArray
//		byte[] fileSizeBytes = ByteBuffer.allocate(4).putInt(getFileSize()).array();
		
		//Convert variables to be sent as Base64
		converted += Base64.encodeBase64String(getIdNum().getBytes());
		converted += " "; //Delimiter
		converted += Base64.encodeBase64String(getFileIdNum().getBytes());
		converted += " ";
		converted += Base64.encodeBase64String(Integer.toString(getFileSize()).getBytes());
		converted += " ";
		converted += Base64.encodeBase64String(getFileName().getBytes());
		converted += " ";
		converted += getFilePart(); //This should already be in Base64

		return converted;
	}

	/**
	 * Converts the Base64 representation of this object 
	 * and sets the appropriate fields
	 *
	 * @param encoded the Base64 encoded representation of this object
	 * @throws SerializationException 
	 */
	static public FilePartModel fromString(String encoded) throws SerializationException{

		String[] parts = (new String(encoded)).split(" "); // split decoded token

		//TODO: Is this a good idea?
		//Check if all strings are Base64
		for(int i = 0; i < parts.length; i++){
			if (!Base64.isBase64(parts[i]))
				throw new SerializationException("Failed to serialize String into FileModel!: " + parts[i]);
		}

		//TODO: Double check this decoding works
		String idNum = new String((Base64.decodeBase64(parts[0])));
		String fileIdNum = new String((Base64.decodeBase64(parts[1])));
		Integer fileSize = Integer.parseInt(new String(Base64.decodeBase64(parts[2])));	
		String fileName = new String((Base64.decodeBase64(parts[3])));
		
		FilePartModel decoded = new FilePartModel(fileIdNum, idNum, fileName, fileSize, parts[4]);

		return decoded;
	}

	@Override
	public Boolean identify(Object o)
	{
		Boolean b  = false;

		if(o instanceof FileModel)
		{
			if(((FileModel) o).getIdNum().equalsIgnoreCase(this.idNum))
			{
				b = true;
			}
		}

		if(o instanceof String)
			if(((String) o).equalsIgnoreCase((this.idNum)))
				b = true;


		return b;
	}

	@Override
	public boolean equals(Object anotherModel) {
		if(anotherModel instanceof FilePartModel)
		{
			if( ((FilePartModel)anotherModel).idNum.equals(this.idNum))
			{
				//things that can be null
				if(this.fileName != null && !this.fileName.equals(((FilePartModel)anotherModel).fileName))
				{
					return false;
				}

				if(this.idNum != null && !this.idNum.equals(((FilePartModel)anotherModel).idNum))
				{
					return false;
				}

				return true;
			}
		}
		return false;
	}

	/* Getters and Setters */
	
	/**
	 * @return the filePart
	 */
	public String getFilePart()
	{
		return filePart;
	}

	/**
	 * @param filePart the filePart to set, in Base64
	 */
	public void setFilePart(String filePart)
	{
		this.filePart = filePart;
	}
	
	/**
	 * @return the idNum
	 */
	public String getIdNum()
	{
		return idNum;
	}

	/**
	 * @param idNum the idNum to set
	 */
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	
	/**
	 * @return the fileIdNum
	 */
	public String getFileIdNum() {
		return fileIdNum;
	}

	/**
	 * @param fileIdNum the fileIdNum to set
	 */
	public void setFileIdNum(String fileIdNum) {
		this.fileIdNum = fileIdNum;
	}

	/**
	 * @return the fileSize
	 */
	public Integer getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}


	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}

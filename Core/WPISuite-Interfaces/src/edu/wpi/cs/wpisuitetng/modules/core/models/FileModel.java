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

import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.Model;

/**
 * The Data Model representation of a File. Offers
 * 	serialization and database interaction.
 * @author mdelladonna, twack, bgaffey, robertsmieja, bhetherman
 */


public class FileModel extends AbstractModel
 {


	private String fileName;
	private String idNum;
	private String[] fileData;
	private User owner;
	private ArrayList<User> team;
	
	/**
	 * Primary constructor for a FileModel
	 * @param fileName - the name of the file
	 * @param idNum - the project ID number as a string
	 * @param owner - The User who owns this project
	 * @param team - The User[] who are associated with the project
	 * @param fileData - the base64 string representing the file
	 */
	public FileModel(String fileName, String idNum, User owner, User[] team, String[] fileData)
	{
		this.fileName = fileName;
		this.idNum = idNum;
		this.owner = owner;
		this.fileData = fileData;
		
		if(team != null)
		{
			this.team = new ArrayList<User>(Arrays.asList(team));
		}
		else
		{
			this.team = new ArrayList<User>();
		}
	}
	
	//TODO: Do we want this for files?
//	/**
//	 * Secondary constructor for a FileModel
//	 * @param fileName	the file name
//	 * @param idNum	the ID number to associate with this FileModel.
//	 */
//	public FileModel(String fileName, String idNum)
//	{
//		this.fileName = fileName;
//		this.idNum = idNum;
//	}
	
	/* Accessors */
	public String getName()
	{
		return fileName;
	}
	
	public String getIdNum()
	{
		return idNum;
	}
	
	/* Mutators */
	public void setName(String newName)
	{
		this.fileName = newName;
	}
	
//	private void setIdNum(String newId)
//	{
//		this.idNum = newId;
//	}
	
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
	
	public String getProjectName() {
		return this.fileName;
	}
	
	/* Serializing */
	//XXX: We are serializing using Base64 instead...
	/**
	 * Serializes this Project's member variables into
	 * 	a JSON string.
	 * @return	the JSON string representation of this Project
	 */
	
	public String toJSON()
	{
		
		String json = null;
		/*
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
		return json;
	}
	
	/**
	 * Static Project method that serializes a list of Projects
	 * 	into JSON strings.
	 * @param u	The list of Projects to serialize
	 * @return	a comma delimited list of Project JSON strings.
	 */
	public static String toJSON(FileModel[] u)
	{
		String json ="";
		
		Gson gson = new Gson();
		
		json = gson.toJson(u, FileModel[].class);
		
		
		return json;
		
	}
	
	/**
	 * Deserializes the given JSON String into a Project's member variables
	 * @return	the Project from the given JSON string representation 
	 */
	public static FileModel fromJSON(String json)
	{
		Gson gson;
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(FileModel.class, new ProjectDeserializer());

		gson = builder.create();
		
		return gson.fromJson(json, FileModel.class);
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
		//TODO: Fill out this method
		String converted = "";
		
		return converted;
		
//		return this.toJSON();
	}
	
	/**
	 * Converts the Base64 representation of this object 
	 * and sets the appropriate fields
	 *
	 * @param encoded the Base64 encoded representation of this object
	 */
	static public FileModel fromString(String encoded){
		
		FileModel decoded = null;
		
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
	public boolean equals(Object anotherProject) {
		if(anotherProject instanceof FileModel)
		{
			if( ((FileModel)anotherProject).idNum.equals(this.idNum))
			{
				//things that can be null
				if(this.fileName != null && !this.fileName.equals(((FileModel)anotherProject).fileName))
				{
					return false;
				}
				
				if(this.idNum != null && !this.idNum.equals(((FileModel)anotherProject).idNum))
				{
					return false;
				}
				
				return true;
			}
		}
		return false;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
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

	/**
	 * @return the fileData
	 */
	public String[] getFileData() {
		return fileData;
	}

	/**
	 * @param fileData the fileData to set
	 */
	public void setFileData(String[] fileData) {
		this.fileData = fileData;
	}

	/**
	 * @param idNum the idNum to set
	 */
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	/**
	 * @param team the team to set
	 */
	public void setTeam(ArrayList<User> team) {
		this.team = team;
	}
	
	public User[] getTeam() {
		User[] a = new User[1];
		return team.toArray(a);
	}
	
	/**
	 * adds a team member to the team
	 * @param u - the user to add to the team
	 * @return true if the user was added, false if the user was already in the team
	 */
	public boolean addTeamMember(User u)
	{
		if(!team.contains(u))
		{
			team.add(u);
			return true;
		}
		return false;
	}
	
	/**
	 * removes a team member from the team
	 * @param u - the team member to remove from the team
	 * @return - true if the member was removed, false if they were not in the team
	 */
	public boolean removeTeamMember(User u)
	{
		if(team.contains(u))
		{
			team.remove(u);
			return true;
		}
		return false;
	}

	
//	@Override
//	public FileModel getProject() {
//		return null;
//	}
//
//	@Override
//	public void setProject(FileModel aProject) {
//		//Can't set a project's project
//	}

}

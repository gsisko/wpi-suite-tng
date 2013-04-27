/*******************************************************************************
 * Copyright (c) 2012 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: mpdelladonna
 * rchamer
 * twack
 * bgaffey
 * robertsmieja
 * bhetherman
 *    
 *******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.core.entitymanagers;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.Base64;

import edu.wpi.cs.wpisuitetng.Permission;
import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.UnauthorizedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.AbstractEntityManager;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.core.models.FileModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.FilePartModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

//TODO: Do we want to make a FilePart class that extends Model?
public class FileManager implements EntityManager<FilePartModel>{

	//TODO: Replace with constant in FileRequest.java in Network!
	static int partSize = 32 * 1024; //32 kilobytes

	Class<FilePartModel> filePartModel = FilePartModel.class;
	Class<FileModel> fileModel = FileModel.class;
	Data data;
	private ArrayList<FileModel> fileArray; //Temp storage for file parts, in order to re-assemble files

	private static final Logger logger = Logger.getLogger(FileManager.class.getName());

	public FileManager(Data data)
	{
		this.data = data;
	}

	@Override
	public String advancedGet(Session s, String[] args)
			throws WPISuiteException {

		//TODO: Split this into parts here or somewhere else?
		//		return this.getEntity(s, s.getProject().getIdNum())[0].toString();
		return "";
	}

	@Override
	public FilePartModel makeEntity(Session s, String content) throws WPISuiteException {	
		User theUser = s.getUser();

		logger.log(Level.FINER, "Attempting new FilePartModel creation...");

		FilePartModel f;

		if (Base64.isBase64(content)){
			f = FilePartModel.fromString(content); //Reconstruct the file from the string received
		} else  {
			logger.log(Level.WARNING, "Invalid FilePartModel entity creation string.");
			throw new BadRequestException("The entity creation string had invalid format. Entity String: " + content);
		}

		//TODO: Add logging
		logger.log(Level.FINE, "New FilePartModel: "+ f.getFileName() +" submitted by: "+ theUser.getName() );
		//		f.setOwner(theUser);

		//Check ID here
		if(getEntity(s,f.getIdNum())[0] == null)
		{
			//TODO: Check Size here?
			//TODO: Check number of packets received based on expected size here?
			//TODO: Do we want to be able to store files with the same name?
			//			if(getEntityByName(s, f.getFileName())[0] == null)
			//			{
			//				save(s,f);
			//			}
			//			else
			//			{
			//				logger.log(Level.WARNING, "FilePartModel Name Conflict Exception during FilePartModel creation.");
			//				throw new ConflictException("A file with the given name already exists. Entity String: " + content);
			//			}
		}
		else
		{
			logger.log(Level.WARNING, "ID Conflict Exception during File creation.");
			throw new ConflictException("A file with the given ID already exists. Entity String: " + content); 
		}

		logger.log(Level.FINER, "File creation success!");
		return f;
	}

	@Override
	/** 
	 * Returns the array of FilePartModels associated with the requested file
	 * @param s The current session
	 * @param id The id of the file
	 * @return The array of FilePartModels for the File
	 * @throws WPISuiteException 
	 */
	public FilePartModel[] getEntity(Session s, String id) throws WPISuiteException 
	{
		FileModel[] m = new FileModel[1];
		if(id.equalsIgnoreCase(""))
		{
			return getAll(s);
		}
		else
		{
			data.retrieve(fileModel, "idNum", id).toArray(m);

			//Divide FileModel into FilePartModels
			return fileModelToParts(m[0]);
		}
	}

	/**
	 * returns a project without requiring a session, 
	 * specifically for the scenario where a session needs to be created.
	 * only ever returns one project, "" is not a valid argument;
	 * 
	 * @param id - the id of the user, in this case it's the idNum
	 * @return a list of matching files
	 * @throws NotFoundException if the project cannot be found
	 * @throws WPISuiteException if retrieve fails
	 */
	public FilePartModel[] getEntity(String id) throws NotFoundException, WPISuiteException
	{
		FileModel[]  m = new FileModel[1];

		if(id.equalsIgnoreCase(""))
		{
			throw new NotFoundException("No (blank) File id given.");
		}
		else
		{
			m = data.retrieve(fileModel, "idNum", id).toArray(m);

			if(m[0] == null)
			{
				throw new NotFoundException("File with id <" + id + "> not found.");
			}
			else
			{
				//Divide FileModel into FilePartModels
				return fileModelToParts(m[0]);
			}
		}
	}

	/**
	 * returns a project without requiring a session, 
	 * specifically for the scenario where a session needs to be created.
	 * only ever returns one project, "" is not a valid argument;
	 * 
	 * @param id - the id of the user, in this case it's the idNum
	 * @return a list of matching files
	 * @throws NotFoundException if the project cannot be found
	 * @throws WPISuiteException if retrieve fails
	 */
	public FilePartModel[] getEntityByName(Session s, String fileName) throws NotFoundException, WPISuiteException
	{
		FilePartModel[] m = new FilePartModel[1];
		if(fileName.equalsIgnoreCase(""))
		{
			throw new NotFoundException("No (blank) File name given.");
		}
		else
		{
			return data.retrieve(filePartModel, "name", fileName).toArray(m);
		}
	}

	@Override
	public FilePartModel[] getAll(Session s) {
		FilePartModel[] ret = new FilePartModel[1];
		ret = data.retrieveAll(new File("","")).toArray(ret);
		return ret;
	}

	@Override
	public void save(Session s, FilePartModel model) throws WPISuiteException {
		if(s == null){
			throw new WPISuiteException("Null Session.");
		}

		FileModel fileModel = fileArray.get(Integer.parseInt(model.getFileIdNum()));

		//Re-assemble a FileModel from FilePartModels...
		//TODO: Is there a way to do this without ArrayList and exceptions?
		//TODO: Abstract assembling parts into the FileModel class?
		try {
			fileModel = fileArray.get(Integer.parseInt(model.getFileIdNum()));

			//Check if exisitng data matches
			if (fileModel.getFileName().equals(model.getFileName()) && fileModel.getIdNum().equals(model.getFileIdNum())){
				//Update
				fileModel.getFileData().add(Integer.parseInt(model.getIdNum()), model.getFilePart());
				fileModel.setProject(model.getProject());

				fileArray.set(Integer.parseInt(model.getFileIdNum()), fileModel);
			} else {
				//Error, file mismatch!
				logger.log(Level.WARNING, "File part doesn't match the File at the given ID!: " + model.getFileIdNum());
			}

		} catch ( IndexOutOfBoundsException e ) { //If it doesn't exist

			//Prepare new fileData
			int numParts = (model.getFileSize() / partSize);
			String[] fileData = new String[numParts];
			fileData[Integer.parseInt(model.getIdNum())-1] = model.getFilePart();

			fileModel = new FileModel(model.getFileName(), model.getFileIdNum(), model.getFileSize(), fileData, null);
			fileModel.setProject(model.getProject());

			fileArray.set(Integer.parseInt(model.getFileIdNum()), fileModel);		
		}

		//permissions checking happens in update, create, and delete methods only
		/*User theUser = s.getUser();
		if(Role.ADMIN.equals(theUser.getRole()) || 
				Permission.WRITE.equals(model.getPermission(theUser))){*/

		//If FileModel, has all parts, save it!
		if (fileModel.hasAllFileParts()){
			if(data.save(fileModel))
			{
				logger.log(Level.FINE, "File Saved :" + model.getFileName());

				//Delete the file from array since we don't need it anymore and it has been saved...
				if ( !fileArray.remove(fileModel) ){
					logger.log(Level.WARNING, "Error removing FileModel from FileManager!" + fileModel.getFileName());
				}

				return ;
			}


			/*else
		{
			logger.log(Level.WARNING, "File Save Failure!");
			throw new DatabaseException("Save failure for File."); // Session User: " + s.getUsername() + " Project: " + model.getName());
		}*/
			/*}
		else
		{
			logger.log(Level.WARNING, "ProjectManager Save attempted by user with insufficient permission");
			throw new UnauthorizedException("You do not have the requred permissions to perform this action.");
		}*/
		} else {
			logger.log(Level.FINE, "Waiting for more parts to save File:" + model.getFileName());
		}
	}

	@Override
	public boolean deleteEntity(Session s1, String id) throws WPISuiteException
	{
		if(s1==null){
			throw new WPISuiteException("Null Session.");
		}

		User theUser = s1.getUser();
		FilePartModel[] model = this.getEntity(id);

		//TODO: Do we need a permission check?
		if(model[0].getPermission(theUser).equals(Permission.WRITE) || 
				theUser.getRole().equals(Role.ADMIN)){
			Model m = data.delete(data.retrieve(fileModel, "idNum", id).get(0));
			logger.log(Level.INFO, "FileManager deleting file <" + id + ">");

			return (m != null) ? true : false;
		}
		else{
			throw new UnauthorizedException("You do not have the required permissions to perform this action.");
		}
	}

	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		User theUser = s.getUser();
		logger.log(Level.INFO, "FileManager invoking DeleteAll...");
		if(theUser.getRole().equals(Role.ADMIN)){
			data.deleteAll(new FileModel("",""));
		}
		else
		{
			logger.log(Level.WARNING, "FileManager DeleteAll attempted by user with insufficient permission");
			throw new UnauthorizedException("You do not have the required permissions to perform this action.");
		}
	}

	@Override
	public int Count() {
		//TODO: Implement?
		return 0;
	}

	//TODO: Are we implementing updating of files?
	public FilePartModel update(Session s, FilePartModel toUpdate, String changeSet) throws WPISuiteException
	{
		//TODO: Rewrite this?
		deleteEntity(s, toUpdate.getFileIdNum());
		save(s, toUpdate);



		/*
		if(s == null){
			throw new WPISuiteException("Null session.");
		}

		//TODO: Do we need to do permissions checking?
//		User theUser = s.getUser();
//		if(theUser.equals(toUpdate.getOwner()) || 
//				theUser.getRole().equals(Role.ADMIN)){

			// convert updateString into a Map, then load into the User
			try
			{
				logger.log(Level.FINE, "Project update being attempted...");
//				File change = File.fromJSON(changeSet);
				byte[] bytes = Base64.decodeBase64(changeSet);


				// check if the changes contains each field of name
				if(changeSet.getName() != null && !changeSet.getName().equals(""))
				{
					// check for conflict for changing the project name
					FileModel isConflict = getEntityByName(s, changeSet.getName())[0];
					if(isConflict != null && !isConflict.getIdNum().equals(changeSet.getIdNum()))
					{
						throw new ConflictException("ProjectManager attempted to update a Project's name to be the same as an existing project");
					}

//					toUpdate.setName(change.getName());
				}

//				if(change.getOwner() != null)
//				{
//					toUpdate.setOwner(change.getOwner());
//				}
			}
			catch(ConflictException e)
			{
				logger.log(Level.WARNING, "ProjectManager attempted to update a Project's name to be the same as an existing project");
				throw e;
			}
			catch(Exception e)
			{
				logger.log(Level.WARNING, "ProjectManager.update() had a failure in the changeset mapper.");
				throw new DatabaseException("Failure in the ProjectManager.update() changeset mapper."); // on Mapping failure
			}

			// save the changes back
			this.save(s, toUpdate);
		 */
		// check for changes in each field
		return toUpdate;
	}
	//		else
	//		{
	//			logger.log(Level.WARNING, "Unauthorized Project update attempted.");
	//			throw new UnauthorizedException("You do not have the required permissions to perform this action.");
	//		}
	//	}

	//TODO: Are we going to handle updating files?
	@Override
	public FilePartModel update(Session s, String content) throws WPISuiteException {
		FilePartModel[] p = null;
		
		String id = AbstractEntityManager.parseFieldFromJSON(content, "idNum");

		if(id.equalsIgnoreCase(""))
		{
			throw new NotFoundException("No (blank) Project id given.");
		}
		else
		{
			p = data.retrieve(fileModel, "idNum", id).toArray(p);

			if(p[0] == null)
			{
				throw new NotFoundException("File with id <" + id + "> not found.");
			}
		}
		 
		return update(s, p[0], content);
	}

//	public void setAllModules(String[] mods)
//	{
//		//		this.allModules = mods;
//	}

	@Override
	public String advancedPut(Session s, String[] args, String content) throws WPISuiteException 
	{
		//TODO: Do we need this functionality? It doesn't seem like we will if we don't use users/projects
		/*
		FileModel p = getEntity(args[2])[0];
		String[] names = null;

		try{
			//TODO: Use our toString method?
//			names = gson.fromJson(content, String[].class);
		}catch(JsonSyntaxException j)
		{
			throw new BadRequestException("Could not parse String");
		}

		ArrayList<String> success = new ArrayList<String>();

//		UserManager u = ManagerLayer.getInstance().getUsers();

//		if(args.length > 3)
//		{
//			if("add".equals(args[3]))
//			{
//				for(String person : names)
//				{
//					if(p.addTeamMember(u.getEntity(s, person)[0]))
//						success.add(person);
//				}
//			}
//			else if("remove".equals(args[3]))
//			{
//				for(String person : names)
//				{
//					if(p.removeTeamMember(u.getEntity(s, person)[0]))
//						success.add(person);
//				}
//			}
//		}
		 */
		return "";
		//		return gson.toJson(success.toArray(names),String[].class );
	}

	@Override
	public String advancedPost(Session s, String string, String content) throws WPISuiteException 
	{
		//TODO: Implement?
		return "";
		//		return gson.toJson(allModules, String[].class);
	}


	/**
	 * Helper to divide a FileModel into FilePartModels
	 * @param model The FileModel to divide
	 * @return an array of FilePartModel made from the FileModel model
	 */
	private FilePartModel[] fileModelToParts(FileModel model){
		//Divide FileModel into FilePartModels
		int numParts = model.getFileSize()/partSize;

		FilePartModel[] mArray = new FilePartModel[numParts];

		for(int i = 0; i < numParts; i++){
			mArray[i] = new FilePartModel(new Integer(i).toString(), model.getIdNum(), model.getFileName(), model.getFileSize(), model.getFileData().get(i));
		}

		return mArray;
	}
}

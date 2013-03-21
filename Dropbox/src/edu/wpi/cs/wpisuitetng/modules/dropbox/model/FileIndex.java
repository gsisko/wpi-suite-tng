/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Chris Casola
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.dropbox.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

public class FileIndex extends AbstractModel {

	//private final Map<String, Integer> fileIndex;
	private final List<String> fileNames;
	
	private final List<Integer> fileIds;
	
	private int nextFree;
	
	@SuppressWarnings("unused")
	private final int id = 0;
	
	public FileIndex() {
		//this.fileIndex = new HashMap<String, Integer>();
		fileNames = new ArrayList<String>();
		fileIds = new ArrayList<Integer>();
		this.nextFree = 0;
	}

	public void addFile(FileBlob file) {
		//fileIndex.put(file.getFileName(), nextFree);
		fileNames.add(file.getFileName());
		fileIds.add(nextFree);
		file.setId(nextFree);
		nextFree++;
	}
	
	public int getFileId(String fileName) {
		//return fileIndex.get(fileName);
		return fileIds.get(fileNames.indexOf(fileName));
	}
	
	public List<String> getFileNames() {
		//return fileIndex.keySet();
		return fileNames;
	}
	
	public List<Integer> getFileIds() {
		return fileIds;
	}

	/*
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON()
	 */
	@Override
	public String toJSON() {
		Gson gson = new Gson();
		return gson.toJson(this, FileIndex.class);
	}
	
	public static FileIndex fromJSON(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, FileIndex.class);
	}
	
	public static FileIndex[] fromJSONArray(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, FileIndex[].class);
	}

	/*
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
	 */
	@Override
	public void save() {}

	/*
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#delete()
	 */
	@Override
	public void delete() {}
	
	/*
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(java.lang.Object)
	 */
	@Override
	public Boolean identify(Object o) {return null;}

}

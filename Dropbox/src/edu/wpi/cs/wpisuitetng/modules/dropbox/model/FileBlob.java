package edu.wpi.cs.wpisuitetng.modules.dropbox.model;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

public class FileBlob extends AbstractModel {

	private final String contents;
	
	private int id;
	
	private String fileName;
	
	public FileBlob(byte[] contents, String fileName) {
		this.contents = Base64.encodeBase64String(contents);
		id = -1;
		this.fileName = fileName;
	}
	
	@Override
	public String toJSON() {
		Gson gson = new Gson();
		return gson.toJson(this, FileBlob.class);
	}
	
	public static FileBlob fromJSON(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, FileBlob.class);
	}
	
	public static FileBlob[] fromJSONArray(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, FileBlob[].class);
	}
	
	public byte[] getContents() {
		return Base64.decodeBase64(contents);
	}
	
	public String getContentsAsString() {
		return contents;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	@Override
	public void save() {}

	@Override
	public void delete() {}

	@Override
	public Boolean identify(Object o) {return null;}

}

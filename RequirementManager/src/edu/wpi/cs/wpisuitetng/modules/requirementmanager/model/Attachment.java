package edu.wpi.cs.wpisuitetng.modules.requirementmanager.model;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

public class Attachment extends AbstractModel {
	
	private String name;
	private byte[] bytes;
	
	public Attachment(String name, byte[] bytes) {
		this.name = name;
		this.bytes = bytes;
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
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

}

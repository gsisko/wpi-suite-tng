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



package edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers.ModelMapper.MapCallback;
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementChangeset;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.FieldChange;

/**
 * Responsible for filling in a changeset after being passed to
 * {@link ModelMapper#map(Model, Model, MapCallback)} 
 * @author Team 5
 * @version $Revision: 1.0 $
 */
class ChangesetCallback implements MapCallback {
	
	private final RequirementChangeset changeset;
	//private boolean wasCalled = false;
	
	//don't add these fields as changes
	private static final Set<String> dontRecord =
			new HashSet<String>(Arrays.asList("events", "lastModifiedDate"));
	
	/**
	 * Create a callback that will fill in the given changeset.
	 * @param changeset The changeset to add changes to
	 */
	ChangesetCallback(RequirementChangeset changeset) {
		this.changeset = changeset;
	}
	
	/**
	 * Method call.
	 * @param source Model
	 * @param destination Model
	 * @param fieldName String
	 * @param sourceValue Object
	 * @param destinationValue Object
	 * @return Object
	 * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers.ModelMapper$MapCallback#call(Model, Model, String, Object, Object)
	 */
	@Override
	public Object call(Model source, Model destination, String fieldName,
			Object sourceValue, Object destinationValue) {
		if(!dontRecord.contains(fieldName)) {
			if(!objectsEqual(sourceValue, destinationValue)) {
				/*
				 * this field has changed - indicate the change in the changeset
				 * remember that fields from updated requirement are being copied to old one
				 * destinationValue is the old value
				 */
				changeset.getChanges().put(fieldName, new FieldChange<Object>(destinationValue, sourceValue));
			}
		}
		return sourceValue;
	}
	
	/**
	 * Method objectsEqual.
	 * @param a Object
	 * @param b Object
	 * @return boolean
	 */
	private boolean objectsEqual(Object a, Object b) {
		// Java 7 has Objects.equals... we're on Java 6
		if(a == b) {
			return true;
		}
		if(a == null || b == null) {
			return false;
		}
		return a.equals(b);
	}
	
}
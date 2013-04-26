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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.changeset;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementType;

/** Custom JSON deserializer for the RequirementChangeset class
 */
public class RequirementChangesetDeserializer implements JsonDeserializer<RequirementChangeset> {

	@Override
	public RequirementChangeset deserialize(JsonElement json, Type type,
			JsonDeserializationContext context) throws JsonParseException {
		
		/** A hash map to hold the deserialized FieldChange objects */
		HashMap<String, FieldChange<?>> changesMap = new HashMap<String, FieldChange<?>>();
		
		JsonObject changeSet = json.getAsJsonObject();
		if (changeSet.has("changes")) {
			JsonObject changes = changeSet.get("changes").getAsJsonObject();
			if (changes.has("name")) {
				JsonObject nameObj = changes.get("name").getAsJsonObject();
				String oldTitle = context.deserialize(nameObj.get("oldValue"), String.class);
				String newTitle = context.deserialize(nameObj.get("newValue"), String.class);
				changesMap.put("name", new FieldChange<String>(oldTitle, newTitle));
			}
			if (changes.has("description")) {
				JsonObject descriptionObj = changes.get("description").getAsJsonObject();
				String oldDesc = context.deserialize(descriptionObj.get("oldValue"), String.class);
				String newDesc = context.deserialize(descriptionObj.get("newValue"), String.class);
				changesMap.put("description", new FieldChange<String>(oldDesc, newDesc));
			}
			if (changes.has("type")) {
				JsonObject typeObj = changes.get("type").getAsJsonObject();
				RequirementType oldType = context.deserialize(typeObj.get("oldValue"), RequirementType.class);
				RequirementType newType = context.deserialize(typeObj.get("newValue"), RequirementType.class);
				changesMap.put("type", new FieldChange<RequirementType>(oldType, newType));
			}
			if (changes.has("status")) {
				JsonObject statusObj = changes.get("status").getAsJsonObject();
				RequirementStatus oldStatus = context.deserialize(statusObj.get("oldValue"), RequirementStatus.class);
				RequirementStatus newStatus = context.deserialize(statusObj.get("newValue"), RequirementStatus.class);
				changesMap.put("status", new FieldChange<RequirementStatus>(oldStatus, newStatus));
			}
			if (changes.has("priority")) {
				JsonObject priorityObj = changes.get("priority").getAsJsonObject();
				RequirementPriority oldPriority = context.deserialize(priorityObj.get("oldValue"), RequirementPriority.class);
				RequirementPriority newPriority = context.deserialize(priorityObj.get("newValue"), RequirementPriority.class);
				changesMap.put("priority", new FieldChange<RequirementPriority>(oldPriority, newPriority));
			}
			if (changes.has("releaseNumber")) {
				JsonObject releaseNumberObj = changes.get("releaseNumber").getAsJsonObject();
				String oldReleaseNumber = context.deserialize(releaseNumberObj.get("oldValue"), String.class);
				String newReleaseNumber = context.deserialize(releaseNumberObj.get("newValue"), String.class);
				changesMap.put("releaseNumber", new FieldChange<String>(oldReleaseNumber, newReleaseNumber));
			}
			if (changes.has("estimate")) {
				JsonObject estimateObj = changes.get("estimate").getAsJsonObject();
				Integer oldEstimate = context.deserialize(estimateObj.get("oldValue"), Integer.class);
				Integer newEstimate = context.deserialize(estimateObj.get("newValue"), Integer.class);
				changesMap.put("estimate", new FieldChange<Integer>(oldEstimate, newEstimate));
			}
			if (changes.has("actualEffort")) {
				JsonObject actualEffortObj = changes.get("actualEffort").getAsJsonObject();
				Integer oldActualEffort = context.deserialize(actualEffortObj.get("oldValue"), Integer.class);
				Integer newActualEffort = context.deserialize(actualEffortObj.get("newValue"), Integer.class);
				changesMap.put("actualEffort", new FieldChange<Integer>(oldActualEffort, newActualEffort));
			}
			if (changes.has("iteration")) {
				JsonObject iterationObj = changes.get("iteration").getAsJsonObject();
				String oldIteration = context.deserialize(iterationObj.get("oldValue"), String.class);
				String newIteration = context.deserialize(iterationObj.get("newValue"), String.class);
				changesMap.put("iteration", new FieldChange<String>(oldIteration, newIteration));
			}
			
			// reconstruct the RequirementChangeset
			RequirementChangeset retVal = new RequirementChangeset((String)(context.deserialize(changeSet.get("userName"), String.class)));
			retVal.setChanges(changesMap);
			retVal.setDate((Date)(context.deserialize(changeSet.get("date"), Date.class)));
			
			// return the RequirementChangeset
			return retVal;
		}
		else {
			throw new JsonParseException("RequirementChangeset type is unrecognized");
		}
	}
}

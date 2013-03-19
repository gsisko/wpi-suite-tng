package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * Custom JSON deserializer for the RequirementChangeset class
 * @author Dabrowski
 * @version $Revision: 1.0 $
 */
public class RequirementChangesetDeserializer implements JsonDeserializer<RequirementChangeset> {

	/**
	 * Method deserialize.
	 * @param json JsonElement
	 * @param type Type
	 * @param context JsonDeserializationContext
	 * @return RequirementChangeset
	 * @throws JsonParseException
	 * @see com.google.gson.JsonDeserializer#deserialize(JsonElement, Type, JsonDeserializationContext)
	 */
	@Override
	public RequirementChangeset deserialize(JsonElement json, Type type,
			JsonDeserializationContext context) throws JsonParseException {
		
		// hash map to hold the deserialized FieldChange objects
		HashMap<String, FieldChange<?>> changesMap = new HashMap<String, FieldChange<?>>();
		
		JsonObject changeSet = json.getAsJsonObject();
		if (changeSet.has("changes")) {
			JsonObject changes = changeSet.get("changes").getAsJsonObject();
			if (changes.has("id")) {
				JsonObject idObj = changes.get("id").getAsJsonObject();
				int oldName = context.deserialize(idObj.get("oldValue"), int.class);
				int newName = context.deserialize(idObj.get("newValue"), int.class);
				changesMap.put("id", new FieldChange<Integer>(oldName, newName));
			}
			if (changes.has("releaseNumber")) {
				JsonObject releaseNumberObj = changes.get("releaseNumber").getAsJsonObject();
				int oldName = context.deserialize(releaseNumberObj.get("oldValue"), int.class);
				int newName = context.deserialize(releaseNumberObj.get("newValue"), int.class);
				changesMap.put("releaseNumber", new FieldChange<Integer>(oldName, newName));
			}
			if (changes.has("priority")) {
				JsonObject priorityObj = changes.get("priority").getAsJsonObject();
				RequirementPriority oldName = context.deserialize(priorityObj.get("oldValue"), RequirementPriority.class);
				RequirementPriority newName = context.deserialize(priorityObj.get("newValue"), RequirementPriority.class);
				changesMap.put("priority", new FieldChange<RequirementPriority>(oldName, newName));
			}
			if (changes.has("name")) {
				JsonObject nameObj = changes.get("name").getAsJsonObject();
				String oldName = context.deserialize(nameObj.get("oldValue"), String.class);
				String newName = context.deserialize(nameObj.get("newValue"), String.class);
				changesMap.put("name", new FieldChange<String>(oldName, newName));
			}
			if (changes.has("description")) {
				JsonObject descriptionObj = changes.get("description").getAsJsonObject();
				String oldDesc = context.deserialize(descriptionObj.get("oldValue"), String.class);
				String newDesc = context.deserialize(descriptionObj.get("newValue"), String.class);
				changesMap.put("description", new FieldChange<String>(oldDesc, newDesc));
			}
			if (changes.has("actualEffort")) {
				JsonObject actualEffortObj = changes.get("actualEffort").getAsJsonObject();
				int oldName = context.deserialize(actualEffortObj.get("oldValue"), int.class);
				int newName = context.deserialize(actualEffortObj.get("newValue"), int.class);
				changesMap.put("actualEffort", new FieldChange<Integer>(oldName, newName));
			}
			if (changes.has("estimate")) {
				JsonObject estimateObj = changes.get("estimate").getAsJsonObject();
				int oldName = context.deserialize(estimateObj.get("oldValue"), int.class);
				int newName = context.deserialize(estimateObj.get("newValue"), int.class);
				changesMap.put("estimate", new FieldChange<Integer>(oldName, newName));
			}
			if (changes.has("status")) {
				JsonObject statusObj = changes.get("status").getAsJsonObject();
				RequirementStatus oldStatus = context.deserialize(statusObj.get("oldValue"), RequirementStatus.class);
				RequirementStatus newStatus = context.deserialize(statusObj.get("newValue"), RequirementStatus.class);
				changesMap.put("status", new FieldChange<RequirementStatus>(oldStatus, newStatus));
			}
			/*
			if (changes.has("attachments")) {
				JsonObject attachmentsObj = changes.get("attachments").getAsJsonObject();
				Attachment[] oldAttachments = context.deserialize(attachmentsObj.get("oldValue"), Attachment[].class);
				Attachment[] newAttachments = context.deserialize(attachmentsObj.get("newValue"), Attachment[].class);
				changesMap.put("attachments", new FieldChange<Set<Attachment>>(new HashSet<Attachment>(new ArrayList<Attachment>(Arrays.asList(oldAttachments))), new HashSet<Attachment>(new ArrayList<Attachment>(Arrays.asList(newAttachments)))));
			}
			if (changes.has("notes")) {
				JsonObject notesObj = changes.get("notes").getAsJsonObject();
				Note[] oldNotes = context.deserialize(notesObj.get("oldValue"), Note[].class);
				Note[] newNotes = context.deserialize(notesObj.get("newValue"), Note[].class);
				changesMap.put("notes", new FieldChange<Set<Note>>(new HashSet<Note>(new ArrayList<Note>(Arrays.asList(oldNotes))), new HashSet<Note>(new ArrayList<Note>(Arrays.asList(newNotes)))));
			}
			if (changes.has("tasks")) {
				JsonObject tasksObj = changes.get("tasks").getAsJsonObject();
				Task[] oldTasks = context.deserialize(tasksObj.get("oldValue"), Task[].class);
				Task[] newTasks = context.deserialize(tasksObj.get("newValue"), Task[].class);
				changesMap.put("tasks", new FieldChange<Set<Task>>(new HashSet<Task>(new ArrayList<Task>(Arrays.asList(oldTasks))), new HashSet<Task>(new ArrayList<Task>(Arrays.asList(newTasks)))));
			}
			*/
			if (changes.has("events")) {
				JsonObject eventsObj = changes.get("events").getAsJsonObject();
				RequirementEvent[] oldRequirementEvents = context.deserialize(eventsObj.get("oldValue"), RequirementEvent[].class);
				RequirementEvent[] newRequirementEvents = context.deserialize(eventsObj.get("newValue"), RequirementEvent[].class);
				changesMap.put("events", new FieldChange<Set<RequirementEvent>>(new HashSet<RequirementEvent>(new ArrayList<RequirementEvent>(Arrays.asList(oldRequirementEvents))), new HashSet<RequirementEvent>(new ArrayList<RequirementEvent>(Arrays.asList(newRequirementEvents)))));
			}
			
			// reconstruct the RequirementChangeset
			RequirementChangeset retVal = new RequirementChangeset();
			retVal.setChanges(changesMap);
			retVal.setDate((Date)(context.deserialize(changeSet.get("date"), Date.class)));
			retVal.setUser((User)(context.deserialize(changeSet.get("user"), User.class)));
			
			// return the RequirementChangeset
			return retVal;
		}
		else {
			throw new JsonParseException("RequirementChangeset type is unrecognized");
		}
	}
}

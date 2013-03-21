package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementEvent.EventType;

/**
 * Responsible for deserializing a RequirementEvent.
 * @author Team 5
 * @version $Revision: 1.0 $
 */
class RequirementEventDeserializer implements JsonDeserializer<RequirementEvent> {
	/**
	 * Method deserialize.
	 * @param element JsonElement
	 * @param type Type
	 * @param context JsonDeserializationContext
	 * @return RequirementEvent
	 * @throws JsonParseException
	 * @see com.google.gson.JsonDeserializer#deserialize(JsonElement, Type, JsonDeserializationContext)
	 */
	@Override
	public RequirementEvent deserialize(JsonElement element, Type type,
			JsonDeserializationContext context) throws JsonParseException {
		// we need to switch on the type field to figure out the concrete class to instantiate
		JsonObject object = element.getAsJsonObject();
		if(!object.has("type")) {
			throw new JsonParseException("RequirementEvent does not have type information");
		}
		EventType eType = context.deserialize(object.get("type"), EventType.class);
		if(eType != null) { // type could be any garbage string, eType null if not in enum
			switch(eType) {
			case CHANGESET:
				return context.deserialize(element, RequirementChangeset.class);
			}
		}
		throw new JsonParseException("RequirementEvent type is unrecognized");
	}
}
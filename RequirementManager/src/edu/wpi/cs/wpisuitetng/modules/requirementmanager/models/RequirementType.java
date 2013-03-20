package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models;

public enum RequirementType {
	Epic,
	Theme,
	UserStory,
	NonFunctional,
	Scenario,
	NoType;
	
	public static RequirementType toType(String toConvert){     
		return valueOf(toConvert);

	}
}
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models;

/**	Possible values for the type of filter.
 * @author Team 5
 *
 */
public enum FilterType {
	Id,
	Name,
	Description,
	Type,
	Status,
	Priority,
	ReleaseNumber,
	Estimate,
	ActualEffort,
	Other;
	
	/** Converts a string to the proper FilterType
	 * 
	 * NOTE: This method is designed to only take strings retrieved from a UE.dropdonwlist that contains
	 * only the enums listed as FilterType.
	 * 
	 * @param toConvert The string you want to convert
	 * 
	 * @return the proper FilterType
	 */
	public static FilterType toType (String toConvert){
		return valueOf(toConvert);
	}
	
	public String toString () {
		switch (FilterType.this) {
		case ActualEffort:
			return "Actual Effort";
		case Description:
			return "Description";
		case Estimate:
			return "Estimate";
		case Id:
			return "ID";
		case Name:
			return "Name";
		case Other:
			return "";
		case Priority:
			return "Priority";
		case ReleaseNumber:
			return "Release Number";
		case Status:
			return "Status";
		case Type:
			return "Type";
		default:
			return "";
		
		}
	}
}

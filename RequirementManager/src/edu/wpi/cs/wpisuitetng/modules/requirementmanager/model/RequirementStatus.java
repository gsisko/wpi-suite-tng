package edu.wpi.cs.wpisuitetng.modules.requirementmanager.model;

/**
 * Possible values for the status of a requirement.
 */
public enum RequirementStatus {
	NEW,   // The initial status value of a requirement
	IN_PROGRESS,  // The first value after being new. Also the value when a requirement is part of an iteration
	OPEN,     // The status of a requirement that is in the backlog
	COMPLETE,  // A complete Requirement
	DELETED	// Any of these status's may lead to DELETED, but DELETED may move to any but NEW
}     // Need to ask Marty about this, but some of the project descriptions hint at this

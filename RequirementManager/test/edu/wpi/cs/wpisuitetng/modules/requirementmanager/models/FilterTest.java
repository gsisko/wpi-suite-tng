package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.Filter;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.FilterType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.OperatorType;

public class FilterTest {
	// Filters
    Filter testFilter;
    Filter testFilter2;
    Filter doNotUse;
    Filter nameEquals;
    Filter descriptionContains;
    Filter idEquals;
    Filter iterationEquals;
    Filter actualEffortEquals;
    Filter estimateEquals;
    Filter releaseNumberEquals;
    Filter releaseNumberNoneEquals;
    Filter statusEquals;
    Filter typeEquals;
    Filter priorityEquals;
    Filter badInteger;
    Filter typeOther;
    
    // Requirements
    Requirement testRequirement;
    
    
    @Before
    public void setUp(){
	testFilter = new Filter(FilterType.Description, OperatorType.Contains, "something", false);
	testFilter2 = new Filter(FilterType.Id, OperatorType.GreaterThan, "2", false);

	// Set up a variety of filters
	// These are mainly designed for use in testing the passesFilter and
	// various perform methods
	doNotUse = new Filter();
	doNotUse.setUseFilter(false);
	nameEquals = new Filter(FilterType.Name, OperatorType.EqualTo, "a requirement", true);
	descriptionContains = new Filter(FilterType.Description, OperatorType.Contains, "some", true);
	idEquals = new Filter(FilterType.Id, OperatorType.EqualTo, "-1", true);
	iterationEquals = new Filter(FilterType.Iteration, OperatorType.EqualTo, "5", true);
	actualEffortEquals = new Filter(FilterType.ActualEffort, OperatorType.EqualTo, "10", true);
	estimateEquals = new Filter(FilterType.Estimate, OperatorType.EqualTo, "7", true);
	releaseNumberEquals = new Filter(FilterType.ReleaseNumber, OperatorType.EqualTo, "3", true);
	releaseNumberNoneEquals = new Filter(FilterType.ReleaseNumber, OperatorType.EqualTo, "none", true);
	statusEquals = new Filter(FilterType.Status, OperatorType.EqualTo, RequirementStatus.New, true);
	typeEquals = new Filter(FilterType.Type, OperatorType.EqualTo, RequirementType.NoType, true);
	priorityEquals = new Filter(FilterType.Priority, OperatorType.EqualTo, RequirementPriority.Medium, true);
	badInteger = new Filter(FilterType.Id, OperatorType.EqualTo, "not a number", true);
	typeOther = new Filter(FilterType.Other, OperatorType.EqualTo, "something", true);
	
	// Setup a basic test requirement
	testRequirement = new Requirement("A Requirement", "Some description", RequirementType.NoType, RequirementPriority.Medium, "3", 5);
	testRequirement.setId(-1);
	testRequirement.setActualEffort(10);
	testRequirement.setEstimate(7);
	testRequirement.setStatus(RequirementStatus.New);
	testRequirement.setType(RequirementType.NoType);
	testRequirement.setPriority(RequirementPriority.Medium);
    }

    @Test
    public void testId(){
	assertEquals(-1, testFilter.getUniqueID());
	testFilter.setUniqueID(8);
	assertEquals(8, testFilter.getUniqueID());
    }
    
    @Test
    public void testType(){
	FilterType typeId = FilterType.toType("Id");
	FilterType typeName = FilterType.toType("Name");
	FilterType typeDescription = FilterType.toType("Description");
	FilterType typeType = FilterType.toType("Type");
	FilterType typeStatus = FilterType.toType("Status");
	FilterType typePriority = FilterType.toType("Priority");
	FilterType typeRelease = FilterType.toType("ReleaseNumber");
	FilterType typeEstimate = FilterType.toType("Estimate");
	FilterType typeEffort = FilterType.toType("ActualEffort");
	FilterType typeOther = FilterType.toType("Other");
	
	assertEquals("Id", typeId.toString());
	assertEquals("Name", typeName.toString());
	assertEquals("Description", typeDescription.toString());
	assertEquals("Type", typeType.toString());
	assertEquals("Status", typeStatus.toString());
	assertEquals("Priority", typePriority.toString());
	assertEquals("ReleaseNumber", typeRelease.toString());
	assertEquals("Estimate", typeEstimate.toString());
	assertEquals("ActualEffort", typeEffort.toString());
	assertEquals("", typeOther.toString());
	
	testFilter.setType(typeId);
	assertFalse(typeName.equals(testFilter.getType()));
	assertEquals(typeId,testFilter.getType());
    }
    
    @Test
    public void testOperator(){
	OperatorType opGreaterThan = OperatorType.toType(">");
	OperatorType opGreaterThanOrEqualTo = OperatorType.toType(">=");
	OperatorType lessThan = OperatorType.toType("<");
	OperatorType lessThanOrEqualTo = OperatorType.toType("<=");
	OperatorType notEqualTo = OperatorType.toType("!=");
	OperatorType equals = OperatorType.toType("=");
	OperatorType contains = OperatorType.toType("Contains");
	OperatorType doesNotContain = OperatorType.toType("DoesNotContain");
	
	assertEquals(">", opGreaterThan.toString());
	assertEquals(">=", opGreaterThanOrEqualTo.toString());
	assertEquals("<", lessThan.toString());
	assertEquals("<=", lessThanOrEqualTo.toString());
	assertEquals("!=", notEqualTo.toString());
	assertEquals("=", equals.toString());
	assertEquals("Contains", contains.toString());
	assertEquals("DoesNotContain", doesNotContain.toString());

	assertTrue(OperatorType.perform(equals, new Integer(2), new Integer(2)));
	assertFalse(OperatorType.perform(equals, new Integer(1), new Integer(2)));
	
	assertTrue(OperatorType.perform(opGreaterThan, new Integer(2), new Integer(3)));
	assertFalse(OperatorType.perform(opGreaterThan, new Integer(2), new Integer(2)));
	
	assertTrue(OperatorType.perform(opGreaterThanOrEqualTo, new Integer(2), new Integer(2)));
	assertFalse(OperatorType.perform(opGreaterThanOrEqualTo, new Integer(2), new Integer(1)));
	
	assertTrue(OperatorType.perform(lessThan, new Integer(3), new Integer(2)));
	assertFalse(OperatorType.perform(lessThan, new Integer(2), new Integer(2)));
	
	assertTrue(OperatorType.perform(lessThanOrEqualTo, new Integer(2), new Integer(2)));
	assertFalse(OperatorType.perform(lessThanOrEqualTo, new Integer(1), new Integer(2)));
	
	assertTrue(OperatorType.perform(notEqualTo, new Integer(1), new Integer(2)));
	assertFalse(OperatorType.perform(notEqualTo, new Integer(2), new Integer(2)));
	
	//----String performs-----

	assertTrue(OperatorType.perform(equals, "Hello World", "Hello World", false));
	assertFalse(OperatorType.perform(equals, "Hello Wrld", "Hello World", false));

	assertTrue(OperatorType.perform(notEqualTo, "Hello Wold", "Hello World", false));
	assertFalse(OperatorType.perform(notEqualTo, "Hello World", "Hello World", false));


	assertFalse(OperatorType.perform(contains, "sda", "Hello World", true));
	assertFalse(OperatorType.perform(doesNotContain, "ello World", "Hello World", true));
	
	assertTrue(OperatorType.perform(OperatorType.Contains, "e", "Helloe", false));
	assertFalse(OperatorType.perform(contains, "sda", "Hello World", false));
	
	assertTrue(OperatorType.perform(doesNotContain, "fgd", "Hello World", false));
	assertFalse(OperatorType.perform(doesNotContain, "ello World", "Hello World", false));
	
    }
    
    @Test
    public void testJSON(){
	testFilter.setValue("Hello World");
	String json = testFilter.toString();
	assertEquals("Hello World", testFilter.getValue());
	assertTrue(testFilter.equals(Filter.fromJSON(json)));
    }
    
//    @Test
//    public void testToJSONArray () {
//    	testFilter  = new Filter(FilterType.Id, OperatorType.EqualTo, "1", false);
//    	testFilter.setUniqueID(1);
//    	testFilter2 = new Filter(FilterType.Iteration, OperatorType.EqualTo, "5", true);
//    	testFilter.setUniqueID(2);
//    	Filter[] filterList = new Filter[2];
//    	filterList[0] = testFilter;
//    	filterList[1] = testFilter2;
//    	
//    	// Put filters into a json and parse them out again
//    	String json = Filter.toJSON(filterList);
//    	Filter[] parsedList = Filter.fromJSONArray(json);
//    	
//    	assertTrue(parsedList[0].equals(filterList[0]));
//    	assertTrue(parsedList[1].equals(filterList[1]));
//    }
    
    @Test
    public void testUpdateArray () {
    	Filter newFilter = new Filter(FilterType.ActualEffort, OperatorType.EqualTo, "5", true);
    	newFilter.setUniqueID(1);
    	
    	// Show that only type, comparator, value and useFilter were changed
    	assertFalse(testFilter.equals(newFilter));
    	assertFalse(testFilter.isUseFilter() == newFilter.isUseFilter());
    	assertFalse(testFilter.getUniqueID() == newFilter.getUniqueID());
    	testFilter.updateFilter(newFilter);
    	assertTrue(testFilter.equals(newFilter));
    	assertTrue(testFilter.isUseFilter() == newFilter.isUseFilter());
    	assertFalse(testFilter.getUniqueID() == newFilter.getUniqueID());
    }
    
    @Test
    public void testCompare () {
    	Filter toCompare = new Filter(FilterType.Id, OperatorType.DoesNotContain, "something else", false);
    	
    	assertFalse(testFilter.equals(toCompare));
    	toCompare.setType(FilterType.Description);
    	assertFalse(testFilter.equals(toCompare));
    	toCompare.setComparator(OperatorType.Contains);
    	assertFalse(testFilter.equals(toCompare));
    	toCompare.setValue("something");
    	assertTrue(testFilter.equals(toCompare));
    }
    
    
    /**
     * This assumes that the
     * perform method works
     */
    @Test
    public void testPassesFilter() {
    	// Tests that all perform methods can be called
    	assertTrue(doNotUse.passesFilter(testRequirement));
    	assertTrue(nameEquals.passesFilter(testRequirement));
    	assertTrue(descriptionContains.passesFilter(testRequirement));
    	assertTrue(idEquals.passesFilter(testRequirement));
    	assertTrue(iterationEquals.passesFilter(testRequirement));
    	assertTrue(actualEffortEquals.passesFilter(testRequirement));
    	assertTrue(estimateEquals.passesFilter(testRequirement));
    	assertTrue(releaseNumberEquals.passesFilter(testRequirement));
    	assertFalse(releaseNumberNoneEquals.passesFilter(testRequirement));
    	assertTrue(statusEquals.passesFilter(testRequirement));
    	assertTrue(typeEquals.passesFilter(testRequirement));
    	assertTrue(priorityEquals.passesFilter(testRequirement));
    	assertFalse(badInteger.passesFilter(testRequirement));
    	assertTrue(typeOther.passesFilter(testRequirement));
    }
    
    @Test
    public void testIdentify () {
    	Filter tmpFilter = new Filter();
    	nameEquals.setUniqueID(1);
    	tmpFilter.setUniqueID(nameEquals.getUniqueID());
    	assertTrue(nameEquals.identify(nameEquals));
    	assertTrue(nameEquals.identify(tmpFilter));
    	assertTrue(nameEquals.identify(new Integer(nameEquals.getUniqueID()).toString()));
    	
    	assertFalse(priorityEquals.identify(nameEquals));
    	assertFalse(priorityEquals.identify(new Integer(nameEquals.getUniqueID()).toString()));
    }
}

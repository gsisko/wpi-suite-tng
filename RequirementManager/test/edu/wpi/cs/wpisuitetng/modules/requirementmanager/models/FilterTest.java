package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.Filter;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.FilterType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.OperatorType;

public class FilterTest {
    Filter testFilter;
    @Before
    public void setUp(){
	testFilter = new Filter();
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
}

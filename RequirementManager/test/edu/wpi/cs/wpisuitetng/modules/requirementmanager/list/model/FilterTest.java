package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.Filter;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.FilterType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.OperatorType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementStatus;


/**
 * The class <code>FilterTest</code> contains tests for the class {@link
 * <code>Filter</code>}
 *
 * @pattern JUnit Test Case
 * @author Team5
 */
public class FilterTest {

	// This is the data that will be used during testing
	
	
	Filter existingFilter;
	Filter otherFilter;
	Filter goodFilter;
	Filter newFilter;
	Filter badFilter;
	



	/**
	 * Perform pre-test initialization
	 *
	 * @throws Exception
	 *
	 * @see TestCase#setUp()
	 */
	@Before
	protected void setUp() throws Exception {
		
		
		// Requirements to sort
		
		
		
		
		
		// Filters to sort with -- UniqueID and User fields do not matter here
		existingFilter    = new Filter(FilterType.ActualEffort, OperatorType.EqualTo, 2, true);    // has user
		otherFilter       = new Filter(FilterType.Description, OperatorType.Contains, "Cat", true); // has user
		newFilter         = new Filter(FilterType.Status, OperatorType.NotEqualTo, RequirementStatus.New, false); // no user
		goodFilter        = new Filter(FilterType.Name, OperatorType.EqualTo, "Random Name", true); // has user - not special until history logs
		badFilter         = new Filter(FilterType.Estimate, OperatorType.EqualTo, "Random Name", true);
		
	}

	/**
	 * Run the boolean equals(Filter) method test
	 */
	@Test
	public void testEquals() {
		fail("Newly generated method - fix or disable");
		// add test code here
		Filter fixture = new Filter();
		Filter toCompareTo = null;
		boolean result = fixture.equals(toCompareTo);
		assertTrue(false);
	}

	/**
	 * Run the Filter fromJSON(String) method test
	 */
	@Test
	public void testFromJSON() {
		fail("Newly generated method - fix or disable");
		// add test code here
		String json = null;
		Filter result = Filter.fromJSON(json);
		assertTrue(false);
	}

	/**
	 * Run the Filter[] fromJSONArray(String) method test
	 */
	@Test
	public void testFromJSONArray() {
		fail("Newly generated method - fix or disable");
		// add test code here
		String json = null;
		Filter[] result = Filter.fromJSONArray(json);
		assertTrue(false);
	}

	/**
	 * Run the boolean passesFilter(Requirement) method test
	 */
	@Test
	public void testPassesFilter() {
		fail("Newly generated method - fix or disable");
		// add test code here
		Filter fixture = new Filter();
		Requirement req = null;
		boolean result = fixture.passesFilter(req);
		assertTrue(false);
	}

	/**
	 * Run the String toJSON() method test
	 */
	@Test
	public void testToJSON() {
		fail("Newly generated method - fix or disable");
		// add test code here
		Filter fixture = new Filter();
		String result = fixture.toJSON();
		assertTrue(false);
	}

	/**
	 * Run the String toString() method test
	 */
	@Test
	public void testToString() {
		fail("Newly generated method - fix or disable");
		// add test code here
		Filter fixture = new Filter();
		String result = fixture.toString();
		assertTrue(false);
	}

	/**
	 * Run the void updateFilter(Filter) method test
	 */
	@Test
	public void testUpdateFilter() {
		fail("Newly generated method - fix or disable");
		// add test code here
		Filter fixture = new Filter();
		Filter filterUpdate = null;
		fixture.updateFilter(filterUpdate);
		assertTrue(false);
	}

}



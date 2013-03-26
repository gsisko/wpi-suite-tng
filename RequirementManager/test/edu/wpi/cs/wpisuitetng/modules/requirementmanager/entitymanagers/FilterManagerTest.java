package edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers;

import java.util.HashSet;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.MockData;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.Filter;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.FilterType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.OperatorType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementStatus;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;

/**
 * The class <code>FilterManagerTest</code> contains tests for the class {@link
 * <code>FilterManager</code>}
 *
 * @pattern JUnit Test Case
 *
 * @author Team 5
 */
public class FilterManagerTest extends TestCase {

	MockData db;
	User existingUser;
	Filter existingFilter;
	Session defaultSession;
	String mockSsid;
	FilterManager manager;
	Filter newFilter;
	User bob;
	Filter goodUpdatedFilter;
	Session adminSession;
	Project testProject;
	Project otherProject;
	Filter otherFilter;
	
	@Before
	public void setUp() throws Exception {
	// Setting up background stuff
		// Users
		User admin = new User("admin", "admin", "1234", 27);
		admin.setRole(Role.ADMIN);
		// Project
		testProject = new Project("test", "1");
		otherProject = new Project("other", "2");
		mockSsid = "abc123";
		// Starts up admin session to activate core stuff
		adminSession = new Session(admin, testProject, mockSsid);
		
		// Another user
		existingUser = new User("rob", "rob", "1234", 2);
		// Session for the user
		defaultSession = new Session(existingUser, testProject, mockSsid);

		
		// New instances of the model for the manager that we are testing
		existingFilter    = new Filter(FilterType.Actual_Effort, OperatorType.EqualTo, 2, true);    // has user
		otherFilter       = new Filter(FilterType.Description, OperatorType.Contains, "Cat", true); // has user
		newFilter         = new Filter(FilterType.Status, OperatorType.NotEqualTo, RequirementStatus.New, false); // no user
		goodUpdatedFilter = new Filter(FilterType.Name, OperatorType.EqualTo, "Random Name", true); // has user - not special until history logs
		
		// Set ID's before saving into db directly (since manager would normally handle that
		existingFilter.setUniqueID(1);
		otherFilter.setUniqueID(2);
		
		// User assignment - specific to filters 
		existingFilter.setUser(existingUser);
		otherFilter.setUser(existingUser);
		goodUpdatedFilter.setUser(existingUser);
		
		
		
		// Set up and store stuff in the database
		db = new MockData(new HashSet<Object>());
		db.save(existingFilter, testProject); 
		db.save(existingUser);
		db.save(otherFilter, otherProject);
		db.save(admin);
		manager = new FilterManager(db);
	}

//	System.out.println("this:      :"+this.getValue() +"/");
//	System.out.println("toCompareTo:"+toCompareTo.getValue()+"/");
//	System.out.println(this.getValue().equals(toCompareTo.getValue()));	
	

	@Test
	public void testMakeEntity() throws WPISuiteException {
		System.out.println("test 1");
		Filter created = manager.makeEntity(defaultSession, newFilter.toJSON());
		assertEquals(3, created.getUniqueID()); // IDs are unique across all Filters currently, and not checked by .equals()
		assertTrue(created.equals(newFilter)); // Tests to see if the filter put in is the one that comes out
		assertTrue(db.retrieve(Filter.class, "UniqueID", 3).get(0).equals( created));
	}
	
//	@Test(expected = ConflictException.class)
//	public void testBadMakeEntity() throws WPISuiteException {
//		System.out.println("test 2");
//		@SuppressWarnings("unused") // we are expecting and exception to be thrown here
//		Filter created = manager.makeEntity(defaultSession, existingFilter.toJSON());
//	}
	
//	@Test
//	public void testGetEntity() throws WPISuiteException, NotFoundException {
//		Filter[] gotten = manager.getEntity(defaultSession, "1");
//		assertSame(existingFilter, gotten[0]);
//	}
//
//	@Test(expected=NotFoundException.class)
//	public void testGetBadId() throws NotFoundException, WPISuiteException {
//		manager.getEntity(defaultSession, "-1");
//	}
//
//	@Test(expected=NotFoundException.class)
//	public void testGetMissingEntity() throws NotFoundException, WPISuiteException  {
//		manager.getEntity(defaultSession, "7");
//	}
	
	@Test
	public void testGetAll() throws WPISuiteException {
		Filter[] gotten = manager.getAll(defaultSession);
		assertEquals(2, gotten.length);  // Not sure if tests are persistent..  either 2 or 3
		assertTrue(existingFilter.equals(gotten[0])
				|| existingFilter.equals(gotten[1]));  // The order is not guranteed
		assertTrue(otherFilter.equals( gotten[1])
				|| otherFilter.equals(gotten[0]));     // The order is not guranteed
	}
	
//	@Test
//	public void testSave() throws WPISuiteException {
//		Filter newFilter2 = new Filter(FilterType.Priority, OperatorType.EqualTo, RequirementPriority.High, true);
//		manager.save(defaultSession, newFilter);
//		assertSame(newFilter2, db.retrieve(Filter.class, "id", 3).get(0)); // could be 4 or 5...
//		assertSame(testProject, newFilter.getProject());
//	}
/*	
	@Test
	public void testDelete() throws WPISuiteException {
		assertSame(existingFilter, db.retrieve(Filter.class, "id", 1).get(0));
		assertTrue(manager.deleteEntity(adminSession, "1"));
		assertEquals(0, db.retrieve(Filter.class, "id", 1).size());
	}
	
	@Test(expected=NotFoundException.class)
	public void testDeleteMissing() throws WPISuiteException {
		manager.deleteEntity(adminSession, "4534");
	}
	
	@Test(expected=NotFoundException.class)
	public void testDeleteFromOtherProject() throws WPISuiteException {
		manager.deleteEntity(adminSession, Integer.toString(otherFilter.getId()));
	}
	
	@Test(expected=UnauthorizedException.class)
	public void testDeleteNotAllowed() throws WPISuiteException {
		manager.deleteEntity(defaultSession, Integer.toString(existingFilter.getId()));
	}
	
	@Test
	public void testDeleteAll() throws WPISuiteException {
		Filter anotherFilter = new Filter(-1, "a title", "a description", existingUser);
		manager.makeEntity(defaultSession, anotherFilter.toJSON());
		assertEquals(2, db.retrieveAll(new Filter(), testProject).size());
		manager.deleteAll(adminSession);
		assertEquals(0, db.retrieveAll(new Filter(), testProject).size());
		// otherFilter should still be around
		assertEquals(1, db.retrieveAll(new Filter(), otherProject).size());
	}
	
	@Test(expected=UnauthorizedException.class)
	public void testDeleteAllNotAllowed() throws WPISuiteException {
		manager.deleteAll(defaultSession);
	}
	
	@Test
	public void testDeleteAllWhenEmpty() throws WPISuiteException {
		manager.deleteAll(adminSession);
		manager.deleteAll(adminSession);
		// no exceptions
	}
	
	@Test
	public void testCount() {
		assertEquals(2, manager.Count());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testUpdate() throws WPISuiteException {
		Filter updated = manager.update(defaultSession, goodUpdatedFilter.toJSON());
		assertSame(existingFilter, updated);
		assertEquals(goodUpdatedFilter.getType(), updated.getType()); // make sure ModelMapper is used
		assertEquals(1, updated.getEvents().size());
		
		FilterChangeset changeset = (FilterChangeset) updated.getEvents().get(0);
		assertSame(existingUser, changeset.getUser());
		assertEquals(updated.getLastModifiedDate(), changeset.getDate());
		
		Map<String, FieldChange<?>> changes = changeset.getChanges();
		// these fields shouldn't be recorded in the changeset
		// creator was different in goodUpdatedFilter, but should be ignored
		assertFalse(changes.keySet().containsAll(Arrays.asList("events", "lastModifiedDate", "creator")));
		
		FieldChange<String> titleChange = (FieldChange<String>) changes.get("title");
		assertEquals("An existing Filter", titleChange.getOldValue());
		assertEquals("A changed title", titleChange.getNewValue());
		
		// make sure events are being saved explicitly to get around a bug
		// TODO: remove this when said bug is fixed
		assertSame(updated.getEvents(), db.retrieveAll(new ArrayList<FilterEvent>()).get(0));
	}
	
	@Test(expected=BadRequestException.class)
	public void testBadUpdate() throws WPISuiteException {
		goodUpdatedFilter.setTitle("");
		manager.update(defaultSession, goodUpdatedFilter.toJSON());
	}
	
	@Test
	public void testNoUpdate() throws WPISuiteException {
		Date origLastModified = existingFilter.getLastModifiedDate();
		Filter updated = manager.update(defaultSession, existingFilter.toJSON());
		assertSame(existingFilter, updated);
		// there were no changes - make sure lastModifiedDate is same, no new events
		assertEquals(origLastModified, updated.getLastModifiedDate());
		assertEquals(0, updated.getEvents().size());
	}
	
	@Test
	public void testProjectChangeIgnored() throws WPISuiteException {
		Filter existingFilterCopy = new Filter(1, "An existing Filter", "", existingUser);
		existingFilterCopy.setProject(otherProject);
		Filter updated = manager.update(defaultSession, existingFilterCopy.toJSON());
		assertEquals(0, updated.getEvents().size());
		assertSame(testProject, updated.getProject());
	}
	
	@Test(expected=NotImplementedException.class)
	public void testAdvancedGet() throws WPISuiteException {
		manager.advancedGet(defaultSession, new String[0]);
	}
	
	@Test(expected=NotImplementedException.class)
	public void testAdvancedPost() throws WPISuiteException {
		manager.advancedPost(defaultSession, "", "");
	}
	
	@Test(expected=NotImplementedException.class)
	public void testAdvancedPut() throws WPISuiteException {
		manager.advancedPut(defaultSession, new String[0], "");
	}

	
	
	
	
	
	
	
	// TODO : Validator = future user story
	@Test(expected=BadRequestException.class)
	public void testMakeBadEntity() throws WPISuiteException {
		
		// make sure it's being passed through the validator
		manager.makeEntity(defaultSession, newFilter.toJSON());
	}
	
	
	
	*/
	
	
	
	

	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//// Generated by CodePro 	
//	
//	/**
//	 * Construct new test instance
//	 *
//	 * @param name the test name
//	 */
//	public FilterManagerTest(String name) {
//		super(name);
//	}
//
//	/**
//	 * Run the int Count() method test
//	 */
//	public void testCount() {
//		fail("Newly generated method - fix or disable");
//		// add test code here
//		// This class does not have a public, no argument constructor,
//		// so the Count() method can not be tested
//		assertTrue(false);
//	}
//
//	/**
//	 * Run the void deleteAll(Session) method test
//	 */
//	public void testDeleteAll() {
//		fail("Newly generated method - fix or disable");
//		// add test code here
//		Session s = null;
//		// This class does not have a public, no argument constructor,
//		// so the deleteAll() method can not be tested
//		assertTrue(false);
//	}
//
//	/**
//	 * Run the boolean deleteEntity(Session, String) method test
//	 */
//	public void testDeleteEntity() {
//		fail("Newly generated method - fix or disable");
//		// add test code here
//		Session s = null;
//		String id = null;
//		// This class does not have a public, no argument constructor,
//		// so the deleteEntity() method can not be tested
//		assertTrue(false);
//	}
//
//	/**
//	 * Run the Filter[] getAll(Session) method test
//	 */
//	public void testGetAll() {
//		fail("Newly generated method - fix or disable");
//		// add test code here
//		Session s = null;
//		// This class does not have a public, no argument constructor,
//		// so the getAll() method can not be tested
//		assertTrue(false);
//	}
//
//	/**
//	 * Run the Filter[] getEntity(Session, String) method test
//	 */
//	public void testGetEntity() {
//		fail("Newly generated method - fix or disable");
//		// add test code here
//		Session s = null;
//		String id = null;
//		// This class does not have a public, no argument constructor,
//		// so the getEntity() method can not be tested
//		assertTrue(false);
//	}
//
//	/**
//	 * Run the Filter makeEntity(Session, String) method test
//	 */
//	public void testMakeEntity() {
//		fail("Newly generated method - fix or disable");
//		// add test code here
//		Session s = null;
//		String content = null;
//		// This class does not have a public, no argument constructor,
//		// so the makeEntity() method can not be tested
//		assertTrue(false);
//	}
//
//	/**
//	 * Run the void save(Session, Filter) method test
//	 */
//	public void testSave() {
//		fail("Newly generated method - fix or disable");
//		// add test code here
//		Session s = null;
//		Filter model = null;
//		// This class does not have a public, no argument constructor,
//		// so the save() method can not be tested
//		assertTrue(false);
//	}
//
//	/**
//	 * Run the Filter update(Session, String) method test
//	 */
//	public void testUpdate() {
//		fail("Newly generated method - fix or disable");
//		// add test code here
//		Session s = null;
//		String content = null;
//		// This class does not have a public, no argument constructor,
//		// so the update() method can not be tested
//		assertTrue(false);
//	}
}

/*$CPS$ This comment was generated by CodePro. Do not edit it.
 * patternId = com.instantiations.assist.eclipse.pattern.testCasePattern
 * strategyId = com.instantiations.assist.eclipse.pattern.testCasePattern.junitTestCase
 * additionalTestNames = 
 * assertTrue = false
 * callTestMethod = true
 * createMain = false
 * createSetUp = false
 * createTearDown = false
 * createTestFixture = false
 * createTestStubs = false
 * methods = Count(),deleteAll(QSession;),deleteEntity(QSession;!QString;),getAll(QSession;),getEntity(QSession;!QString;),makeEntity(QSession;!QString;),save(QSession;!QFilter;),update(QSession;!QString;)
 * package = edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers
 * package.sourceFolder = RequirementManager/src
 * superclassType = junit.framework.TestCase
 * testCase = FilterManagerTest
 * testClassType = edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers.FilterManager
 */
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers;

import org.easymock.EasyMock;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.database.Data;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>RequirementManagerTest</code> contains tests for the class <code>{@link RequirementManager}</code>.
 *
 * @generatedBy CodePro at 3/19/13 9:56 PM
 * @author Robert Smieja
 * @version $Revision: 1.0 $
 */
public class RequirementManagerTest {
	/**
	 * Run the RequirementManager(Data) constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@Test
	public void testRequirementManager_1()
		throws Exception {
		Data data = EasyMock.createMock(Data.class);
		// add mock object expectations here

		EasyMock.replay(data);

		RequirementManager result = new RequirementManager(data);

		// add additional test code here
		EasyMock.verify(data);
		assertNotNull(result);
	}

	/**
	 * Run the int Count() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@Test
	public void testCount_1()
		throws Exception {
		RequirementManager fixture = new RequirementManager(EasyMock.createNiceMock(Data.class));

		int result = fixture.Count();

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers.RequirementManager.Count(RequirementManager.java:104)
		assertEquals(0, result);
	}

	/**
	 * Run the String advancedGet(Session,String[]) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@Test
	public void testAdvancedGet_1()
		throws Exception {
		RequirementManager fixture = new RequirementManager(EasyMock.createNiceMock(Data.class));
		Session s = new Session(User.fromJSON(""), "");
		String[] args = new String[] {};

		String result = fixture.advancedGet(s, args);

		// add additional test code here
		assertEquals(null, result);
	}

	/**
	 * Run the String advancedPost(Session,String,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@Test
	public void testAdvancedPost_1()
		throws Exception {
		RequirementManager fixture = new RequirementManager(EasyMock.createNiceMock(Data.class));
		Session s = new Session(User.fromJSON(""), "");
		String string = "";
		String content = "";

		String result = fixture.advancedPost(s, string, content);

		// add additional test code here
		assertEquals(null, result);
	}

	/**
	 * Run the String advancedPut(Session,String[],String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@Test
	public void testAdvancedPut_1()
		throws Exception {
		RequirementManager fixture = new RequirementManager(EasyMock.createNiceMock(Data.class));
		Session s = new Session(User.fromJSON(""), "");
		String[] args = new String[] {};
		String content = "";

		String result = fixture.advancedPut(s, args, content);

		// add additional test code here
		assertEquals(null, result);
	}

	/**
	 * Run the void assignUniqueID(Requirement) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@Test
	public void testAssignUniqueID_1()
		throws Exception {
		RequirementManager fixture = new RequirementManager(EasyMock.createNiceMock(Data.class));
		Requirement req = new Requirement();
		req.setId(-1);

		fixture.assignUniqueID(req);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers.RequirementManager.Count(RequirementManager.java:104)
		//       at edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers.RequirementManager.assignUniqueID(RequirementManager.java:90)
	}

	/**
	 * Run the void assignUniqueID(Requirement) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@Test
	public void testAssignUniqueID_2()
		throws Exception {
		RequirementManager fixture = new RequirementManager(EasyMock.createNiceMock(Data.class));
		Requirement req = new Requirement();
		req.setId(-1);

		fixture.assignUniqueID(req);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers.RequirementManager.Count(RequirementManager.java:104)
		//       at edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers.RequirementManager.assignUniqueID(RequirementManager.java:90)
	}

	/**
	 * Run the void assignUniqueID(Requirement) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@Test
	public void testAssignUniqueID_3()
		throws Exception {
		RequirementManager fixture = new RequirementManager(EasyMock.createNiceMock(Data.class));
		Requirement req = new Requirement();
		req.setId(1);

		fixture.assignUniqueID(req);

		// add additional test code here
	}

	/**
	 * Run the void deleteAll(Session) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@Test
	public void testDeleteAll_1()
		throws Exception {
		RequirementManager fixture = new RequirementManager(EasyMock.createNiceMock(Data.class));
		Session s = new Session(User.fromJSON(""), Project.fromJSON(""), "");

		fixture.deleteAll(s);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers.RequirementManager.deleteAll(RequirementManager.java:221)
	}

	/**
	 * Run the void deleteAll(Session) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@Test
	public void testDeleteAll_2()
		throws Exception {
		RequirementManager fixture = new RequirementManager(EasyMock.createNiceMock(Data.class));
		Session s = new Session(User.fromJSON(""), Project.fromJSON(""), "");

		fixture.deleteAll(s);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers.RequirementManager.deleteAll(RequirementManager.java:221)
	}

	/**
	 * Run the void deleteAll(Session) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@Test
	public void testDeleteAll_3()
		throws Exception {
		RequirementManager fixture = new RequirementManager(EasyMock.createNiceMock(Data.class));
		Session s = new Session(User.fromJSON(""), Project.fromJSON(""), "");

		fixture.deleteAll(s);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers.RequirementManager.deleteAll(RequirementManager.java:221)
	}

	/**
	 * Run the boolean deleteEntity(Session,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@Test
	public void testDeleteEntity_1()
		throws Exception {
		RequirementManager fixture = new RequirementManager(EasyMock.createNiceMock(Data.class));
		Session s = new Session(User.fromJSON(""), Project.fromJSON(""), "");
		String id = "";

		boolean result = fixture.deleteEntity(s, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers.RequirementManager.deleteEntity(RequirementManager.java:195)
		assertTrue(result);
	}

	/**
	 * Run the boolean deleteEntity(Session,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@Test
	public void testDeleteEntity_2()
		throws Exception {
		RequirementManager fixture = new RequirementManager(EasyMock.createNiceMock(Data.class));
		Session s = new Session(User.fromJSON(""), Project.fromJSON(""), "");
		String id = "0";

		boolean result = fixture.deleteEntity(s, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers.RequirementManager.deleteEntity(RequirementManager.java:195)
		assertTrue(result);
	}

	/**
	 * Run the boolean deleteEntity(Session,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@Test
	public void testDeleteEntity_3()
		throws Exception {
		RequirementManager fixture = new RequirementManager(EasyMock.createNiceMock(Data.class));
		Session s = new Session(User.fromJSON(""), Project.fromJSON(""), "");
		String id = "0";

		boolean result = fixture.deleteEntity(s, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers.RequirementManager.deleteEntity(RequirementManager.java:195)
		assertTrue(result);
	}

	/**
	 * Run the boolean deleteEntity(Session,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@Test
	public void testDeleteEntity_4()
		throws Exception {
		RequirementManager fixture = new RequirementManager(EasyMock.createNiceMock(Data.class));
		Session s = new Session(User.fromJSON(""), Project.fromJSON(""), "");
		String id = "";

		boolean result = fixture.deleteEntity(s, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers.RequirementManager.deleteEntity(RequirementManager.java:195)
		assertTrue(result);
	}

	/**
	 * Run the boolean deleteEntity(Session,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@Test
	public void testDeleteEntity_5()
		throws Exception {
		RequirementManager fixture = new RequirementManager(EasyMock.createNiceMock(Data.class));
		Session s = new Session(User.fromJSON(""), Project.fromJSON(""), "");
		String id = "0";

		boolean result = fixture.deleteEntity(s, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers.RequirementManager.deleteEntity(RequirementManager.java:195)
		assertTrue(result);
	}

	/**
	 * Run the Requirement[] getAll(Session) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@Test
	public void testGetAll_1()
		throws Exception {
		RequirementManager fixture = new RequirementManager(EasyMock.createNiceMock(Data.class));
		Session s = new Session(User.fromJSON(""), Project.fromJSON(""), "");

		Requirement[] result = fixture.getAll(s);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers.RequirementManager.getAll(RequirementManager.java:117)
		assertNotNull(result);
	}

	/**
	 * Run the Requirement[] getEntity(Session,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@Test
	public void testGetEntity_1()
		throws Exception {
		RequirementManager fixture = new RequirementManager(EasyMock.createNiceMock(Data.class));
		Session s = new Session(User.fromJSON("a"), "a");
		String id = "0";

		Requirement[] result = fixture.getEntity(s, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    com.google.gson.JsonSyntaxException: java.lang.IllegalStateException: Not a JSON Object: "a"
		//       at com.google.gson.Gson.fromJson(Gson.java:806)
		//       at com.google.gson.Gson.fromJson(Gson.java:761)
		//       at com.google.gson.Gson.fromJson(Gson.java:710)
		//       at com.google.gson.Gson.fromJson(Gson.java:682)
		//       at edu.wpi.cs.wpisuitetng.modules.core.models.User.fromJSON(User.java:246)
		assertNotNull(result);
	}

	/**
	 * Run the Requirement[] getEntity(Session,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@Test
	public void testGetEntity_2()
		throws Exception {
		RequirementManager fixture = new RequirementManager(EasyMock.createNiceMock(Data.class));
		Session s = new Session(User.fromJSON("a"), Project.fromJSON(""), "");
		String id = "0";

		Requirement[] result = fixture.getEntity(s, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    com.google.gson.JsonSyntaxException: java.lang.IllegalStateException: Not a JSON Object: "a"
		//       at com.google.gson.Gson.fromJson(Gson.java:806)
		//       at com.google.gson.Gson.fromJson(Gson.java:761)
		//       at com.google.gson.Gson.fromJson(Gson.java:710)
		//       at com.google.gson.Gson.fromJson(Gson.java:682)
		//       at edu.wpi.cs.wpisuitetng.modules.core.models.User.fromJSON(User.java:246)
		assertNotNull(result);
	}

	/**
	 * Run the Requirement[] getEntity(Session,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@Test
	public void testGetEntity_3()
		throws Exception {
		RequirementManager fixture = new RequirementManager(EasyMock.createNiceMock(Data.class));
		Session s = new Session(User.fromJSON("a"), Project.fromJSON(""), "");
		String id = "0";

		Requirement[] result = fixture.getEntity(s, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    com.google.gson.JsonSyntaxException: java.lang.IllegalStateException: Not a JSON Object: "a"
		//       at com.google.gson.Gson.fromJson(Gson.java:806)
		//       at com.google.gson.Gson.fromJson(Gson.java:761)
		//       at com.google.gson.Gson.fromJson(Gson.java:710)
		//       at com.google.gson.Gson.fromJson(Gson.java:682)
		//       at edu.wpi.cs.wpisuitetng.modules.core.models.User.fromJSON(User.java:246)
		assertNotNull(result);
	}

	/**
	 * Run the Requirement[] getEntity(Session,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@Test
	public void testGetEntity_4()
		throws Exception {
		RequirementManager fixture = new RequirementManager(EasyMock.createNiceMock(Data.class));
		Session s = new Session(User.fromJSON("a"), Project.fromJSON(""), "");
		String id = "0";

		Requirement[] result = fixture.getEntity(s, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    com.google.gson.JsonSyntaxException: java.lang.IllegalStateException: Not a JSON Object: "a"
		//       at com.google.gson.Gson.fromJson(Gson.java:806)
		//       at com.google.gson.Gson.fromJson(Gson.java:761)
		//       at com.google.gson.Gson.fromJson(Gson.java:710)
		//       at com.google.gson.Gson.fromJson(Gson.java:682)
		//       at edu.wpi.cs.wpisuitetng.modules.core.models.User.fromJSON(User.java:246)
		assertNotNull(result);
	}

	/**
	 * Run the Requirement[] getEntity(Session,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@Test
	public void testGetEntity_5()
		throws Exception {
		RequirementManager fixture = new RequirementManager(EasyMock.createNiceMock(Data.class));
		Session s = new Session(User.fromJSON("a"), Project.fromJSON(""), "");
		String id = "0";

		Requirement[] result = fixture.getEntity(s, id);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    com.google.gson.JsonSyntaxException: java.lang.IllegalStateException: Not a JSON Object: "a"
		//       at com.google.gson.Gson.fromJson(Gson.java:806)
		//       at com.google.gson.Gson.fromJson(Gson.java:761)
		//       at com.google.gson.Gson.fromJson(Gson.java:710)
		//       at com.google.gson.Gson.fromJson(Gson.java:682)
		//       at edu.wpi.cs.wpisuitetng.modules.core.models.User.fromJSON(User.java:246)
		assertNotNull(result);
	}

	/**
	 * Run the Requirement[] getEntity(Session,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@Test(expected = java.lang.NumberFormatException.class)
	public void testGetEntity_6()
		throws Exception {
		RequirementManager fixture = new RequirementManager(EasyMock.createNiceMock(Data.class));
		Session s = new Session(User.fromJSON(""), "");
		String id = "";

		Requirement[] result = fixture.getEntity(s, id);

		// add additional test code here
		assertNotNull(result);
	}

	/**
	 * Run the Requirement makeEntity(Session,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@Test
	public void testMakeEntity_1()
		throws Exception {
		RequirementManager fixture = new RequirementManager(EasyMock.createNiceMock(Data.class));
		Session s = new Session(User.fromJSON(""), "");
		String content = "";

		Requirement result = fixture.makeEntity(s, content);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers.RequirementManager.assignUniqueID(RequirementManager.java:89)
		//       at edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers.RequirementManager.save(RequirementManager.java:74)
		//       at edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers.RequirementManager.makeEntity(RequirementManager.java:59)
		assertNotNull(result);
	}

	/**
	 * Run the Requirement makeEntity(Session,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@Test
	public void testMakeEntity_2()
		throws Exception {
		RequirementManager fixture = new RequirementManager(EasyMock.createNiceMock(Data.class));
		Session s = new Session(User.fromJSON(""), "");
		String content = "";

		Requirement result = fixture.makeEntity(s, content);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers.RequirementManager.assignUniqueID(RequirementManager.java:89)
		//       at edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers.RequirementManager.save(RequirementManager.java:74)
		//       at edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers.RequirementManager.makeEntity(RequirementManager.java:59)
		assertNotNull(result);
	}

	/**
	 * Run the void save(Session,Requirement) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@Test(expected = edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException.class)
	public void testSave_1()
		throws Exception {
		RequirementManager fixture = new RequirementManager(EasyMock.createNiceMock(Data.class));
		Session s = new Session(User.fromJSON(""), "");
		Requirement model = new Requirement();

		fixture.save(s, model);

		// add additional test code here
	}

	/**
	 * Run the void save(Session,Requirement) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@Test(expected = edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException.class)
	public void testSave_2()
		throws Exception {
		RequirementManager fixture = new RequirementManager(EasyMock.createNiceMock(Data.class));
		Session s = new Session(User.fromJSON(""), Project.fromJSON(""), "");
		Requirement model = new Requirement();

		fixture.save(s, model);

		// add additional test code here
	}

	/**
	 * Run the void save(Session,Requirement) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@Test(expected = edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException.class)
	public void testSave_3()
		throws Exception {
		RequirementManager fixture = new RequirementManager(EasyMock.createNiceMock(Data.class));
		Session s = new Session(User.fromJSON(""), Project.fromJSON(""), "");
		Requirement model = new Requirement();

		fixture.save(s, model);

		// add additional test code here
	}

	/**
	 * Run the Requirement update(Session,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@Test
	public void testUpdate_1()
		throws Exception {
		RequirementManager fixture = new RequirementManager(EasyMock.createNiceMock(Data.class));
		Session s = new Session(User.fromJSON(""), "");
		String content = "";

		Requirement result = fixture.update(s, content);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers.RequirementManager.update(RequirementManager.java:164)
		assertNotNull(result);
	}

	/**
	 * Run the Requirement update(Session,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@Test
	public void testUpdate_2()
		throws Exception {
		RequirementManager fixture = new RequirementManager(EasyMock.createNiceMock(Data.class));
		Session s = new Session(User.fromJSON(""), "");
		String content = "";

		Requirement result = fixture.update(s, content);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers.RequirementManager.update(RequirementManager.java:164)
		assertNotNull(result);
	}

	/**
	 * Run the Requirement update(Session,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@Test
	public void testUpdate_3()
		throws Exception {
		RequirementManager fixture = new RequirementManager(EasyMock.createNiceMock(Data.class));
		Session s = new Session(User.fromJSON(""), "");
		String content = "";

		Requirement result = fixture.update(s, content);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers.RequirementManager.update(RequirementManager.java:164)
		assertNotNull(result);
	}

	/**
	 * Run the Requirement update(Session,String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@Test
	public void testUpdate_4()
		throws Exception {
		RequirementManager fixture = new RequirementManager(EasyMock.createNiceMock(Data.class));
		Session s = new Session(User.fromJSON(""), "");
		String content = "";

		Requirement result = fixture.update(s, content);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.NullPointerException
		//       at edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers.RequirementManager.update(RequirementManager.java:164)
		assertNotNull(result);
	}

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *         if the initialization fails for some reason
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@Before
	public void setUp()
		throws Exception {
		// add additional set up code here
	}

	/**
	 * Perform post-test clean-up.
	 *
	 * @throws Exception
	 *         if the clean-up fails for some reason
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	@After
	public void tearDown()
		throws Exception {
		// Add additional tear down code here
	}

	/**
	 * Launch the test.
	 *
	 * @param args the command line arguments
	 *
	 * @generatedBy CodePro at 3/19/13 9:56 PM
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(RequirementManagerTest.class);
	}
}
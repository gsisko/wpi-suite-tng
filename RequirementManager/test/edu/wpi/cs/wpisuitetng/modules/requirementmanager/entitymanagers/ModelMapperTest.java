package edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers;

import java.util.Set;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementChangeset;
import org.junit.*;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.Model;
import static org.junit.Assert.*;

/**
 * The class <code>ModelMapperTest</code> contains tests for the class <code>{@link ModelMapper}</code>.
 *
 * @generatedBy CodePro at 3/20/13 2:22 PM
 * @author Robert Smieja
 * @version $Revision: 1.0 $
 */
public class ModelMapperTest {
	/**
	 * Run the ModelMapper() constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/20/13 2:22 PM
	 */
	@Test
	public void testModelMapper_1()
		throws Exception {

		ModelMapper result = new ModelMapper();

		// add additional test code here
		assertNotNull(result);
	}

	/**
	 * Run the Set<String> getBlacklist() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/20/13 2:22 PM
	 */
	@Test
	public void testGetBlacklist_1()
		throws Exception {
		ModelMapper fixture = new ModelMapper();

		Set<String> result = fixture.getBlacklist();

		// add additional test code here
		assertNotNull(result);
		assertEquals(1, result.size());
		assertTrue(result.contains("permission"));
	}

	/**
	 * Run the void map(Model,Model) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/20/13 2:22 PM
	 */
	@Test
	public void testMap_1()
		throws Exception {
		ModelMapper fixture = new ModelMapper();
		Model source = new Requirement();
		Model destination = new Requirement();

		fixture.map(source, destination);

		// add additional test code here
	}

	/**
	 * Run the void map(Model,Model) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/20/13 2:22 PM
	 */
	@Test
	public void testMap_2()
		throws Exception {
		ModelMapper fixture = new ModelMapper();
		Model source = new Requirement();
		Model destination = new Requirement();

		fixture.map(source, destination);

		// add additional test code here
	}

	/**
	 * Run the void map(Model,Model,MapCallback) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/20/13 2:22 PM
	 */
	@Test
	public void testMap_3()
		throws Exception {
		ModelMapper fixture = new ModelMapper();
		Model source = new Requirement();
		Model destination = new Requirement();
		ModelMapper.MapCallback callback = new ChangesetCallback(new RequirementChangeset());

		fixture.map(source, destination, callback);

		// add additional test code here
	}

	/**
	 * Run the void map(Model,Model,MapCallback) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/20/13 2:22 PM
	 */
	@Test
	public void testMap_4()
		throws Exception {
		ModelMapper fixture = new ModelMapper();
		Model source = new Requirement();
		Model destination = new Requirement();
		ModelMapper.MapCallback callback = new ChangesetCallback(new RequirementChangeset());

		fixture.map(source, destination, callback);

		// add additional test code here
	}

	/**
	 * Run the void map(Model,Model,MapCallback) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/20/13 2:22 PM
	 */
	@Test
	public void testMap_5()
		throws Exception {
		ModelMapper fixture = new ModelMapper();
		Model source = new Requirement();
		Model destination = new Requirement();
		ModelMapper.MapCallback callback = new ChangesetCallback(new RequirementChangeset());

		fixture.map(source, destination, callback);

		// add additional test code here
	}

	/**
	 * Run the void map(Model,Model,MapCallback) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/20/13 2:22 PM
	 */
	@Test
	public void testMap_6()
		throws Exception {
		ModelMapper fixture = new ModelMapper();
		Model source = new Requirement();
		Model destination = new Requirement();
		ModelMapper.MapCallback callback = new ChangesetCallback(new RequirementChangeset());

		fixture.map(source, destination, callback);

		// add additional test code here
	}

	/**
	 * Run the void map(Model,Model,MapCallback) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/20/13 2:22 PM
	 */
	@Test
	public void testMap_7()
		throws Exception {
		ModelMapper fixture = new ModelMapper();
		Model source = new Requirement();
		Model destination = new Requirement();
		ModelMapper.MapCallback callback = new ChangesetCallback(new RequirementChangeset());

		fixture.map(source, destination, callback);

		// add additional test code here
	}

	/**
	 * Run the void map(Model,Model,MapCallback) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/20/13 2:22 PM
	 */
	@Test
	public void testMap_8()
		throws Exception {
		ModelMapper fixture = new ModelMapper();
		Model source = new Requirement();
		Model destination = new Requirement();
		ModelMapper.MapCallback callback = new ChangesetCallback(new RequirementChangeset());

		fixture.map(source, destination, callback);

		// add additional test code here
	}

	/**
	 * Run the void map(Model,Model,MapCallback) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/20/13 2:22 PM
	 */
	@Test
	public void testMap_9()
		throws Exception {
		ModelMapper fixture = new ModelMapper();
		Model source = new Requirement();
		Model destination = new Requirement();
		ModelMapper.MapCallback callback = new ChangesetCallback(new RequirementChangeset());

		fixture.map(source, destination, callback);

		// add additional test code here
	}

	/**
	 * Run the void map(Model,Model,MapCallback) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/20/13 2:22 PM
	 */
	@Test
	public void testMap_10()
		throws Exception {
		ModelMapper fixture = new ModelMapper();
		Model source = new Requirement();
		Model destination = new Requirement();
		ModelMapper.MapCallback callback = new ChangesetCallback(new RequirementChangeset());

		fixture.map(source, destination, callback);

		// add additional test code here
	}

	/**
	 * Run the void map(Model,Model,MapCallback) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/20/13 2:22 PM
	 */
	@Test
	public void testMap_11()
		throws Exception {
		ModelMapper fixture = new ModelMapper();
		Model source = new Requirement();
		Model destination = new Requirement();
		ModelMapper.MapCallback callback = new ChangesetCallback(new RequirementChangeset());

		fixture.map(source, destination, callback);

		// add additional test code here
	}

	/**
	 * Run the void map(Model,Model,MapCallback) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/20/13 2:22 PM
	 */
	@Test
	public void testMap_12()
		throws Exception {
		ModelMapper fixture = new ModelMapper();
		Model source = new Requirement();
		Model destination = new Requirement();
		ModelMapper.MapCallback callback = new ChangesetCallback(new RequirementChangeset());

		fixture.map(source, destination, callback);

		// add additional test code here
	}

	/**
	 * Run the void map(Model,Model,MapCallback) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/20/13 2:22 PM
	 */
	@Test
	public void testMap_13()
		throws Exception {
		ModelMapper fixture = new ModelMapper();
		Model source = new Requirement();
		Model destination = new Requirement();
		ModelMapper.MapCallback callback = new ChangesetCallback(new RequirementChangeset());

		fixture.map(source, destination, callback);

		// add additional test code here
	}

	/**
	 * Run the void map(Model,Model,MapCallback) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/20/13 2:22 PM
	 */
	@Test
	public void testMap_14()
		throws Exception {
		ModelMapper fixture = new ModelMapper();
		Model source = new Requirement();
		Model destination = new Requirement();
		ModelMapper.MapCallback callback = new ChangesetCallback(new RequirementChangeset());

		fixture.map(source, destination, callback);

		// add additional test code here
	}

	/**
	 * Run the void map(Model,Model,MapCallback) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/20/13 2:22 PM
	 */
	@Test
	public void testMap_15()
		throws Exception {
		ModelMapper fixture = new ModelMapper();
		Model source = new Requirement();
		Model destination = new Requirement();
		ModelMapper.MapCallback callback = new ChangesetCallback(new RequirementChangeset());

		fixture.map(source, destination, callback);

		// add additional test code here
	}

	/**
	 * Run the void map(Model,Model,MapCallback) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/20/13 2:22 PM
	 */
	@Test
	public void testMap_16()
		throws Exception {
		ModelMapper fixture = new ModelMapper();
		Model source = new Requirement();
		Model destination = new Requirement();
		ModelMapper.MapCallback callback = new ChangesetCallback(new RequirementChangeset());

		fixture.map(source, destination, callback);

		// add additional test code here
	}

	/**
	 * Run the void map(Model,Model,MapCallback) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/20/13 2:22 PM
	 */
	@Test
	public void testMap_17()
		throws Exception {
		ModelMapper fixture = new ModelMapper();
		Model source = new Requirement();
		Model destination = new Requirement();
		ModelMapper.MapCallback callback = new ChangesetCallback(new RequirementChangeset());

		fixture.map(source, destination, callback);

		// add additional test code here
	}

	/**
	 * Run the void map(Model,Model,MapCallback) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/20/13 2:22 PM
	 */
	@Test
	public void testMap_18()
		throws Exception {
		ModelMapper fixture = new ModelMapper();
		Model source = new Requirement();
		Model destination = new Requirement();
		ModelMapper.MapCallback callback = new ChangesetCallback(new RequirementChangeset());

		fixture.map(source, destination, callback);

		// add additional test code here
	}

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *         if the initialization fails for some reason
	 *
	 * @generatedBy CodePro at 3/20/13 2:22 PM
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
	 * @generatedBy CodePro at 3/20/13 2:22 PM
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
	 * @generatedBy CodePro at 3/20/13 2:22 PM
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(ModelMapperTest.class);
	}
}
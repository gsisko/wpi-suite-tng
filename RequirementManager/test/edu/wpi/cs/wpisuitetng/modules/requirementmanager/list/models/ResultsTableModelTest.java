/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.lang.Object;
import java.util.HashSet;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.MockData;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementType;

/** JUnit test for our ResultsTableModel
 * 
 * @author Robert Smieja
 *
 */
public class ResultsTableModelTest {
	
	//Variables to be shared between tests
	Requirement existingRequirement, otherRequirement, goodUpdatedRequirement, newRequirement;
	ResultsTableModel testTable;
	Object[][] goodData, badData;
//	MockData goodData;
//	MockData badData;
//	MockData differentData;

	/** Sets up data and runs functions needed before test methods are called.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		//Create tables that will persist between tests
		testTable = new ResultsTableModel();
		assertNotNull(testTable);
		
		Object[][] goodData = {
				{"0,0", "0,1", new Integer(1)},
				{"1,0", "1,1", new Integer(2)},
				{"2,0", "2,1", new Integer(3)}
		};
		
		Object[][] badData = {
				{null, null, null},
				{null, null, null},
				{null, null, null}
		};
		
		//TODO: Fill the rest of the data, and make tests that do not use existing tables
	}

	/** Cleans up data and runs functions needed after test methods are called.
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/** Set the table with data.
	 * 
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.ResultsTableModel#setData(java.lang.Object[][])}.
	 */
	@Test
	public final void testSetData() {
//		testTable.setData(goodData);
		testTable.setData(goodData);
		assertNotNull(testTable);

//		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.ResultsTableModel#getColumnCount()}.
	 */
	@Test
	public final void testGetColumnCount() {
		assertEquals(testTable.getColumnCount(), 3);
//		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.ResultsTableModel#getRowCount()}.
	 */
	@Test
	public final void testGetRowCount() {
		assertEquals(testTable.getRowCount(), 3);
//		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.ResultsTableModel#ResultsTableModel()}.
	 */
	@Test
	public final void testResultsTableModel() {
		fail("Not yet implemented"); // TODO
	}


	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.ResultsTableModel#setColumnNames(java.lang.String[])}.
	 */
	@Test
	public final void testSetColumnNames() {
		fail("Not yet implemented"); // TODO
	}



	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.ResultsTableModel#getValueAt(int, int)}.
	 */
	@Test
	public final void testGetValueAt() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.ResultsTableModel#getColumnName(int)}.
	 */
	@Test
	public final void testGetColumnNameInt() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.ResultsTableModel#getColumnClass(int)}.
	 */
	@Test
	public final void testGetColumnClassInt() {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.ResultsTableModel#isCellEditable(int, int)}.
	 */
	@Test
	public final void testIsCellEditable() {
		fail("Not yet implemented"); // TODO
	}

}

/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Edison Jimenez
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.ResultsTableModel;

/**
 * Tests the ResultsTableModel that contains the data to be
 * displayed in the requirements JTable.
 */
public class TestResultsTableModel {

	ResultsTableModel rtm1;
	ResultsTableModel rtm2;

	@Before
	public void setUp() throws Exception {
		rtm1 = new ResultsTableModel();
		rtm2 = new ResultsTableModel();
	}

	@Test
	public void dataAndHeaderFieldsInitialized() {
		assertTrue(rtm1.getColumnNames() != null);
		assertTrue(rtm1.getData() != null);
		assertEquals(0, rtm1.getColumnNames().length);
		assertEquals(0, rtm1.getData().length);
		
		assertTrue(rtm2.getColumnNames() !=null);
		assertTrue(rtm2.getData() != null);
		assertEquals(0, rtm2.getColumnNames().length);
		assertEquals(0, rtm2.getData().length);
	}

	@Test
	public void columnNamesCanBeSet() {
		String[] columnNames = {"Col A", "Col B", "Col C"};
		String[] columnNames2 = {"Test A", "Test B", "Test C"};
		rtm1.setColumnNames(columnNames);
		rtm2.setColumnNames(columnNames2);

		assertEquals(3, rtm1.getColumnCount());
		assertEquals("Col B", rtm1.getColumnName(1));
		
		assertEquals(3, rtm2.getColumnCount());
		assertEquals("Test B", rtm2.getColumnName(1));
		assertEquals("Test A", rtm2.getColumnName(0));
	}

	@Test
	public void dataCanBeReplaced() {
		insertTestData();

		assertEquals(3, rtm1.getRowCount());
		assertEquals("0,0", rtm1.getValueAt(0, 0));
		assertEquals("1,1", rtm1.getValueAt(1, 1));
		assertEquals(3, rtm1.getValueAt(2, 2));
		
		assertEquals(3, rtm2.getRowCount());
		assertEquals("0,0", rtm2.getValueAt(0,0));
		assertEquals("1,1", rtm2.getValueAt(1,1));
		assertEquals(3, rtm2.getValueAt(2,2));
	}

	@Test
	public void cellsCannotBeEdited() {		
		insertTestData();
		
		for (int i = 0; i < rtm1.getRowCount(); i++) {
			for (int j = 0; j < rtm1.getColumnCount(); j++) {
				assertFalse(rtm1.isCellEditable(i, j));
			}
		}
	}
	
	@Test
	public void columnClassTypesAreMaintained() {
		insertTestData();
		
		assertEquals(String.class, rtm1.getColumnClass(0));
		assertEquals(String.class, rtm1.getColumnClass(1));
		assertEquals(Integer.class, rtm1.getColumnClass(2));
	}
	
	private void insertTestData() {
		String[] columnNames = {"Col A", "Col B", "Col C"};
		String[] columnNames2 = {"Test A", "Test B", "Test C"};

		Object[][] newData = {
				{"0,0", "0,1", new Integer(1)},
				{"1,0", "1,1", new Integer(2)},
				{"2,0", "2,1", new Integer(3)}
		};
		
		Object[][] newData2 = {
				{"0,0", "0,1", new Integer(1)},
				{"1,0", "1,1", new Integer(2)},
				{"2,0", "2,1", new Integer(3)}
		};
		
		rtm1.setColumnNames(columnNames);
		rtm1.setData(newData);
		
		rtm2.setColumnNames(columnNames2);
		rtm2.setData(newData2);
	}
}

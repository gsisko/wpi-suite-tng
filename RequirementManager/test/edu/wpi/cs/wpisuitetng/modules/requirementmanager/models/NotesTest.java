package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NotesTest {
	
	Requirement r1;
	Note n1;
	
	@Before
	public void setUp() throws Exception{
		//seting up new requirement
		r1 = new Requirement("Tester Requirement", "Testing Description", RequirementType.Theme, RequirementPriority.NoPriority, 0);
		r1.setPriority(RequirementPriority.Medium);
		r1.setReleaseNumber(8000);
		r1.setEstimate(15);
		r1.setActualEffort(18);
		r1.setStatus(RequirementStatus.InProgress);
		r1.setId(50);
		//set first note to index 0
		r1.getNotes().add(new Note("I am testing notes."));
		n1 = r1.getNotes().get(r1.getNotes().size() - 1);
		// adding multiple notes
		r1.getNotes().add(new Note("!@#$#$%^*&**()")); // note at index 1
		r1.getNotes().add(new Note("ASADSFBassfkh")); // note at index 2
		r1.getNotes().add(new Note("1234abcd")); // note at index 3
		r1.getNotes().add(new Note("No ho fo lo bro")); // note at index 4
	}
	
	@Test

	public void testGetMessage(){
		// does getMessage work?
		assertEquals(n1.getMessage(), "I am testing notes.");
	}
	
	@Test
	public void testSetMessage(){
		// does setMessage work?
		n1.setMessage("Still testing");
		assertEquals(n1.getMessage(), "Still testing");
	}
	
	@Test
	public void testMultipleNotes(){
		// did the multiple notes get added correctly?
		assertEquals(r1.getNotes().get(1).getMessage(), "!@#$#$%^*&**()");
		assertEquals(r1.getNotes().get(2).getMessage(), "ASADSFBassfkh");
		assertEquals(r1.getNotes().get(3).getMessage(), "1234abcd");
		assertEquals(r1.getNotes().get(4).getMessage(), "No ho fo lo bro");
	}
	
	@Test
	public void testfromJSON() {
		String json = r1.toJSON();
		Requirement newRequirement = Requirement.fromJSON(json);
		// did editing and altering notes affect other elements in requirement?
		assertEquals(newRequirement.getName(), "Tester Requirement");
		assertEquals(newRequirement.getPriority(), RequirementPriority.Medium);
		assertEquals(newRequirement.getDescription(), "Testing Description");
		assertEquals(newRequirement.getType(), RequirementType.Theme);
		assertEquals(newRequirement.getReleaseNumber(), 8000);
		assertEquals(newRequirement.getEstimate(), 15);
		assertEquals(newRequirement.getActualEffort(), 18);
		assertEquals(newRequirement.getStatus(), RequirementStatus.InProgress);
		assertEquals(newRequirement.getId(), 50);
		assertEquals(newRequirement.getNotes(), r1.getNotes());
	}

}

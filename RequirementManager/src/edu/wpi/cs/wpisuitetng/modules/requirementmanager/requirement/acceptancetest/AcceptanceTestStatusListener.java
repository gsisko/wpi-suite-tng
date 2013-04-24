package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.acceptancetest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JComboBox;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.AcceptanceTest;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementAttributePanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementTab;

/**
 * Listener for the drop-down menus in the list of acceptance tests.
 * Automatically saves the test when the status is changed
 * 
 * @author Team 5
 *
 */
public class AcceptanceTestStatusListener implements PopupMenuListener {

	/** The panel with the box being watched */
	private AcceptanceTestPanel thePanel;
	/** The box to watch */
	@SuppressWarnings("rawtypes")
	private JComboBox toWatch;

	/** Constructor that takes all the references it needs
	 * 
	 * @param thePanel       The panel with the box being watched
	 * @param toWatch        The box to watch
	 * @param fieldToCheck   The box of the reference to watch
	 * @param indexOfBoolean The index in the boolean array of the current item being watched
	 */
	public AcceptanceTestStatusListener (AcceptanceTestPanel thePanel){
//		System.out.println("Listener was created");
		this.thePanel = thePanel;
		this.toWatch = thePanel.getStatusBox();
	}

	/** Checks the field for changes and sets it to yellow on changes or white otherwise */
	private void checkForChanges(){
		// Check if the status has changed
		String inBox = toWatch.getSelectedItem().toString();
		String inTest = thePanel.getMyTest().getAcceptanceTestResult().toString();
		if (inTest.equals("NONE")) {
			inTest = " ";
		}
		if (!inBox.equals(inTest)) {
//			System.out.println("Something changed");
//			System.out.println("\"" + inBox + "\"");
//			System.out.println("\"" + inTest + "\"");
		} else {
//			System.out.println("Nothing changed");
		}
	}

	/** This method is unused but required by the interface   */
	public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
	}

	/** This is called when the user types then releases the key. If
	 *  changes are made, the new AcceptanceTest is saved. 
	 */
	public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
		checkForChanges();			
	}

	/** This is called when the user types then releases the key. If
	 *  changes are made, the new AcceptanceTest is saved. 
	 */
	public void popupMenuCanceled(PopupMenuEvent e) {
		checkForChanges();			
	}
}

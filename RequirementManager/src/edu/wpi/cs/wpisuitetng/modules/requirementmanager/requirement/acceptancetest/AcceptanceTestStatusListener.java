package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.acceptancetest;

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.AcceptanceTest;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementTab;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.SaveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.SaveRequirementObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

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
			// Create new acceptance test
			AcceptanceTestResult newResult = AcceptanceTestResult.NONE;
			if (!inBox.equals(" ")) {
				newResult = AcceptanceTestResult.toResult(inBox);
			}
			AcceptanceTest oldTest = this.thePanel.getMyTest();
			AcceptanceTest newTest = new AcceptanceTest(oldTest.getAcceptanceTestTitle(), oldTest.getDescription(), newResult);
			
			// Save it
			AcceptanceTestTab tab = (AcceptanceTestTab) thePanel.getParent().getParent().getParent().getParent();	// Shortens the next line
			SaveRequirementController controller = tab.myGetParent().getParent().getController();
			
			controller.updateAcceptanceTest(oldTest, newTest);
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

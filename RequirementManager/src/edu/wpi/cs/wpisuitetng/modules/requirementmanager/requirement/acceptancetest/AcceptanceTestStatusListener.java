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
		this.thePanel = thePanel;
		this.toWatch = thePanel.getStatusBox();
	}

	/** Checks the field for changes and sets it to yellow on changes or white otherwise */
	private void checkForChanges(){
//		System.out.println("The listener was called");
//		if (thePanel.getStatusBox() == null) {
//			System.out.println("bugger");
//		} else if (thePanel.getStatusBox().getSelectedItem() == null) {
//			System.out.println("fuck");
//		} else if (thePanel.getAcceptanceTest() == null) {
//			System.out.println("wtf");
//		} else if (thePanel.getAcceptanceTest().getAcceptanceTestResult() == null) {
//			System.out.println("FML");
//		}
//		if (!thePanel.getStatusBox().getSelectedItem().toString().equals(thePanel.getAcceptanceTest().getAcceptanceTestResult().toString())) {
//			thePanel.getAcceptanceTest().setAcceptanceTestResult((AcceptanceTestResult)thePanel.getStatusBox().getSelectedItem());
//			((AcceptanceTestTab)thePanel.getParent().getParent()).getParent().getParent().getController().saveAcceptanceTest();
//		}
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

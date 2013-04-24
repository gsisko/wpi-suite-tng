/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team 5 D13
 * 
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.listeners;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JComboBox;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementAttributePanel;

/** This listener is used on combo boxes and turns those
 *  boxes yellow when changes are detected.
 * 
 */
@SuppressWarnings("rawtypes")
public class BoxChangeListener implements PopupMenuListener{

	/** The panel with the box being watched */
	private RequirementAttributePanel thePanel;
	/** The box to watch */
	private JComboBox toWatch;
	/** The box of the reference to watch */
	private String fieldToCheck ;

	/** Index in the array of booleans*/
	int indexOfBoolean;

	/** The getter method that is used on the reference to get the appropriate value to check against */
	private Method getOldFieldValue;

	/** Constructor that takes all the references it needs
	 * 
	 * @param thePanel       The panel with the box being watched
	 * @param toWatch        The box to watch
	 * @param fieldToCheck   The box of the reference to watch
	 * @param indexOfBoolean The index in the boolean array of the current item being watched
	 */
	public BoxChangeListener(RequirementAttributePanel thePanel,JComboBox toWatch, 
			String fieldToCheck, int indexOfBoolean ){
		this.thePanel = thePanel;
		this.toWatch = toWatch;
		this.fieldToCheck = fieldToCheck;
		this.indexOfBoolean =  indexOfBoolean;
		
		// Get the getter method for the requirement
		Method[] allMethods = (Requirement.class).getMethods();
		for(Method m: allMethods){//Cycles through all of the methods in the requirement Class
			if(m.getName().equalsIgnoreCase("get"+fieldToCheck)){
				getOldFieldValue = m; //saves the method called "get" + aFieldName
			}
		}
	}

	/** Checks the field for changes and sets it to yellow on changes or white otherwise */
	private void checkForChanges(){
		// The value to compare against
		Object oldValue;
		// Since the invoke stuff can throw exceptions, we check and print here as necessary
		try {
			oldValue = getOldFieldValue.invoke(thePanel.getCurrentRequirement());
		} catch (IllegalArgumentException iae) {
			System.err.println("FieldChangeListener problem: "+ fieldToCheck);
			return;
		} catch (IllegalAccessException iae2) {
			System.err.println("FieldChangeListener problem: "+ fieldToCheck);
			return;
		} catch (InvocationTargetException ite) {
			System.err.println("FieldChangeListener problem: "+ fieldToCheck);
			return;         
		}
		oldValue = oldValue.toString();
		// Special cases
		if (oldValue.equals("NoType") || oldValue.toString().equals("NoPriority") ){
			oldValue = "";
		}

		// Check the old value and set the box yellow as necessary
		if (!toWatch.getSelectedItem().toString().equals(oldValue + "")) {
			thePanel.changeField(toWatch, indexOfBoolean, true);
		} else {
			thePanel.changeField(toWatch, indexOfBoolean, false);
		}
	}

	/** This method is unused but required by the interface   */
	public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
	}

	/** This is called when the user types then releases the key. If
	 *  changes are made, the box is turned yellow. 
	 */
	public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
		checkForChanges();			
	}

	/** This is called when the user types then releases the key. If
	 *  changes are made, the box is turned yellow. 
	 */
	public void popupMenuCanceled(PopupMenuEvent e) {
		checkForChanges();			
	}
}

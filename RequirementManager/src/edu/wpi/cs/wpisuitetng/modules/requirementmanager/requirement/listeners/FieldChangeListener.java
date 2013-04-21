/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *		Robert Dabrowski
 *		Danielle LaRose
 *		Edison Jimenez
 *		Christian Gonzalez
 *		Mike Calder
 *		John Bosworth
 *		Paula Rudy
 *		Gabe Isko
 *		Bangyan Zhang
 *		Cassie Hudson
 *		Robert Smieja
 *		Alex Solomon
 *		Brian Hetherman
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.text.JTextComponent;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementAttributePanel;

/** This listener is used on text fields/areas and turns those
 *  boxes yellow when changes are detected.
 * 
 */
public class FieldChangeListener implements KeyListener{

	/** The panel with the field being watched */
	private RequirementAttributePanel thePanel;
	/** The field to watch */
	private JTextComponent toWatch;
	/** The field of the reference to watch */
	private String fieldToCheck ;

	/** Index in the array of booleans*/
	int indexOfBoolean;

	/** The getter method that is used on the reference to get the appropriate value to check against */
	private Method getOldFieldValue;

	/** Constructor that takes all the references it needs
	 * 
	 * @param thePanel
	 * @param toWatch
	 * @param reference
	 * @param fieldToCheck
	 */
	public FieldChangeListener(RequirementAttributePanel thePanel,JTextComponent toWatch, 
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
		
		// Check the old value and set the box yellow as necessary
		if (!toWatch.getText().equals(oldValue + "")) {
			thePanel.changeField(toWatch, indexOfBoolean, true);
		} else {
			thePanel.changeField(toWatch, indexOfBoolean, false);
		}
	}


	/** This is called when the user types then releases the key. If
	 *  changes are made, the box is turned yellow. 
	 */
	public void keyReleased(KeyEvent e) {
		checkForChanges();		
	}


	/** This method is unused but required by the interface   */
	public void keyTyped(KeyEvent e) {		
	}


	/** This method is unused but required by the interface    */
	public void keyPressed(KeyEvent e) {
	}
}

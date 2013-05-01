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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.iteration;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.lowagie.text.Font;
import com.toedter.calendar.JDateChooser;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.SaveModelController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IBuilderPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListTab;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Iteration;

/** This is the builder panel for Iterations. It is located in the list view on the 
 *  RequirementManager module above the list of requirements and right of the list 
 *  of iterations. This builder will be switched to when the Iteration list view
 *  tab is selected.  
 * @author Team 5 D13
 */
@SuppressWarnings("serial")
public class IterationBuilderPanel extends JPanel implements  IBuilderPanel {

	/** The "Name" label, located before the Iteration name box*/
	private final JLabel nameLabel;
	/** The "Start Date" label, located before the first calendar */
	private final JLabel startDateLabel;
	/** The "End Date" label, located before the second calendar */
	private final JLabel endDateLabel;
	/** The "Total Estimate" label, located before the displayed total estimate */
	private final JLabel totalEstimateLabel;
	/** The total estimate for the current iteration is displayed here */
	private final JLabel totalEstimate;

	/** The name warning label, used to warn the user of an invalid name on the iteration currently being built*/
	private JLabel nameWarning;
	/** The date warning label, used to warn the user of an invalid date on the iteration currently being built*/
	private JLabel dateWarning;

	/** The text box for filling in an Iteration's name */
	private JTextField nameValue;
	/** The calendar for choosing the start date of the current iteration */
	private JDateChooser startDateChooser;
	/** the calendar for choosing the end date of the current iteration */
	private JDateChooser endDateChooser;

	/** EDIT or CREATE mode */
	private Mode currentMode;
	/** The current Iteration that is build built */
	private Iteration currentIteration;
	/** The status of the builder, active/inactive */
	private boolean isBuilderActive;

	/** The save/create button */
	private final JButton btnCreate;
	/** This controller is activated when the save button is pressed */
	private SaveModelController saveController;
	/** The "parent" that this builder lives in */
	private final ListTab parent;

	/** Construct the panel and all of its components
	 * @param view The ListTab that this panel will live in
	 */
	public IterationBuilderPanel(ListTab view) {
		parent = view;
		currentMode = Mode.CREATE;
		currentIteration = null;
		isBuilderActive = false;

		//construct the panels
		nameLabel = new JLabel("Name:");
		startDateLabel = new JLabel("Start Date:");
		endDateLabel = new JLabel("End Date:");
		totalEstimateLabel = new JLabel("Total Estimate:");
		nameWarning = new JLabel("Name cannot be \"Backlog\"");
		dateWarning = new JLabel("Start and end dates cannot overlap with another iteration");

		//Set the color for the warnings
		nameWarning.setForeground(Color.red);
		dateWarning.setForeground(Color.red);

		//Set the font size for the warnings to 9 point
		nameWarning.setFont(nameWarning.getFont().deriveFont(9));
		dateWarning.setFont(dateWarning.getFont().deriveFont(9));

		//Set the preferred size of the warnings to avoid shifts in the layout when the text is changed
		nameWarning.setPreferredSize(nameWarning.getPreferredSize());
		dateWarning.setPreferredSize(dateWarning.getPreferredSize());

		//construct the components
		btnCreate = new JButton("Create");
		nameValue = new JTextField();
		enable(nameValue, false);
		startDateChooser = new JDateChooser(trim(new Date()));
		endDateChooser = new JDateChooser(trim(new Date()));
		totalEstimate = new JLabel("0");

		//Add key listener to nameValue to toggle warnings and create button appropriately
		nameValue.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {
				isIterationValid();//Set the save button enabled if there is something to save, disabled if not, set warnings if needed
			}
		});

		// The action listener for these are below
		btnCreate.setEnabled(false);
		startDateChooser.setEnabled(false);
		endDateChooser.setEnabled(false);

		totalEstimate.setFont(totalEstimateLabel.getFont().deriveFont(Font.NORMAL));//Make the totalEstimate non-bold

		//Add a titled boarder to this panel
		setBorder(BorderFactory.createTitledBorder("Iteration Builder"));

		//set the layout for this panel
		setLayout(new GridBagLayout());
		GridBagConstraints IterationBuilderConstraints = new GridBagConstraints();

		//Set the preferred sizes of the components
		startDateChooser.setPreferredSize(new Dimension (125,20));
		endDateChooser.setPreferredSize(new Dimension (125,20));
		btnCreate.setPreferredSize(new Dimension (btnCreate.getPreferredSize().width,30));

		//Total Estimate
		//Set the constraints for the "totalEstimateLabel" and add it to the view
		IterationBuilderConstraints.anchor = GridBagConstraints.LINE_END; //This sets the anchor of the field, here we have told it to anchor the component to the center right of it's field
		IterationBuilderConstraints.insets = new Insets(8,10,2,0); //Set the top padding to 8 units of blank space, set left padding to 10 units, bottom padding to 2 units
		IterationBuilderConstraints.gridx = 0;//Set the x coord of the cell of the layout we are describing
		IterationBuilderConstraints.gridy = 0;//Set the y coord of the cell of the layout we are describing
		add(totalEstimateLabel, IterationBuilderConstraints);//Actually add the "totalEstimateLabel" to the layout given the previous constraints
		//Set the constraints for the "totalEstimate" and add it to the view
		IterationBuilderConstraints.anchor = GridBagConstraints.LINE_START;//This sets the anchor of the field, here we have told it to anchor the component to the center left of it's field
		IterationBuilderConstraints.insets = new Insets(8,10,2,25); //Set the top padding to 8 units of blank space, set left padding to 10 units, bottom padding to 2 units, right padding to 25 units
		IterationBuilderConstraints.ipadx= 30;//stretch this field horizontally by 30 units
		IterationBuilderConstraints.gridx = 1;
		IterationBuilderConstraints.gridy = 0;
		add(totalEstimate, IterationBuilderConstraints);//Actually add the "totalEstimate" to the layout given the previous constraints

		//Iteration Name
		//Set the constraints for the "nameLabel" and add it to the view
		IterationBuilderConstraints.anchor = GridBagConstraints.LINE_END; //This sets the anchor of the field, here we have told it to anchor the component to the center right of it's field
		IterationBuilderConstraints.ipadx=0;//This resets the horizontal padding from the previously defined 30 units back to 0 units
		IterationBuilderConstraints.insets = new Insets(8,10,2,0); //Set the top padding to 8 units of blank space, set left padding to 10 units, bottom padding to 2 units
		IterationBuilderConstraints.gridx = 2;//Set the x coord of the cell of the layout we are describing
		IterationBuilderConstraints.gridy = 0;//Set the y coord of the cell of the layout we are describing
		add(nameLabel, IterationBuilderConstraints);//Actually add the "nameLabel" to the layout given the previous constraints
		//Set the constraints for the "nameValue" and add it to the view
		IterationBuilderConstraints.anchor = GridBagConstraints.LINE_START;//This sets the anchor of the field, here we have told it to anchor the component to the center left of it's field
		IterationBuilderConstraints.insets = new Insets(8,10,2,25); //Set the top padding to 8 units of blank space, set left padding to 10 units, bottom padding to 2 units, right padding to 25 units
		IterationBuilderConstraints.ipadx=80;//stretch this field horizontally by 80 units
		IterationBuilderConstraints.gridx = 3;
		IterationBuilderConstraints.gridy = 0;
		add(nameValue, IterationBuilderConstraints);//Actually add the "nameValue" to the layout given the previous constraints

		//Start Date
		//Set the constraints for the "startDateLabel" and add it to the view
		IterationBuilderConstraints.anchor = GridBagConstraints.LINE_END;
		IterationBuilderConstraints.insets = new Insets(8,10,2,0);
		IterationBuilderConstraints.ipadx=0;//This resets the horizontal padding from the previously defined 80 units back to 0 units
		IterationBuilderConstraints.gridx = 4;
		IterationBuilderConstraints.gridy = 0;
		add(startDateLabel, IterationBuilderConstraints);
		//Set the constraints for the "startDateChooser" and add it to the view
		IterationBuilderConstraints.anchor = GridBagConstraints.LINE_START;
		IterationBuilderConstraints.insets = new Insets(8,10,2,25);
		IterationBuilderConstraints.gridx = 5;
		IterationBuilderConstraints.gridy = 0;
		add(startDateChooser, IterationBuilderConstraints);

		//End Date
		//Set the constraints for the "endDateLabel" and add it to the view
		IterationBuilderConstraints.anchor = GridBagConstraints.LINE_END;
		IterationBuilderConstraints.insets = new Insets(8,10,2,0);
		IterationBuilderConstraints.gridx = 6;
		IterationBuilderConstraints.gridy = 0;
		add(endDateLabel, IterationBuilderConstraints);
		//Set the constraints for the "endDateChooser" and add it to the view
		IterationBuilderConstraints.anchor = GridBagConstraints.LINE_START;
		IterationBuilderConstraints.insets = new Insets(8,10,2,40);
		IterationBuilderConstraints.gridx = 7;
		IterationBuilderConstraints.gridy = 0;
		add(endDateChooser, IterationBuilderConstraints);

		//Save button:
		//Set the constraints for the "Create" button and add it to the view
		IterationBuilderConstraints.anchor = GridBagConstraints.LINE_END;
		IterationBuilderConstraints.gridx = 8;
		IterationBuilderConstraints.gridy = 0;
		add(btnCreate, IterationBuilderConstraints);//Actually add the "Create" button to the layout given the previous constraints

		//Name warning
		//Set the constraints for the "nameWarning" and add it to the view
		IterationBuilderConstraints.fill = GridBagConstraints.HORIZONTAL; //Tell the field to stretch horizontally to fit it's cell(s)
		IterationBuilderConstraints.anchor = GridBagConstraints.CENTER; //This sets the anchor of the field, here we have told it to anchor the component to the center right of it's field
		IterationBuilderConstraints.insets = new Insets(0,10,5,0); //Set the top padding to 0 units of blank space, set left padding to 10 units,right padding to 0 units, bottom padding to 5 units
		IterationBuilderConstraints.gridx = 2;//Set the x coord of the cell of the layout we are describing
		IterationBuilderConstraints.gridwidth = 2; //Tell this component to fill 2 columns
		IterationBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		add(nameWarning, IterationBuilderConstraints);//Actually add the "nameWarning" to the layout given the previous constraints

		//Date warning
		//Set the constraints for the "dateWarning" and add it to the view
		IterationBuilderConstraints.gridx = 4;//Set the x coord of the cell of the layout we are describing
		IterationBuilderConstraints.gridwidth = 4;//Tell this component to fill 4 columns
		IterationBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		add(dateWarning, IterationBuilderConstraints);//Actually add the "dateWarning" to the layout given the previous constraints

	}
	
	/** Get the create button
	 * @return btnCreate The "btnCreate" JButton
	 */
	public JButton getButton()
	{
		return btnCreate;
	}
	
	/** Get the "nameValue" JTextField
	 * @return nameValue The "nameValue" JTextField
	 */
	public JTextField getIterationValue()
	{
		return nameValue;
	}

	/** Get the "startDateChooser" JDateChooser
	 * @return startDateChooser The "startDateChooser" JDateChooser
	 */
	public JDateChooser getStartDateChooser() {
		return startDateChooser;
	}

	/** Set the "startDateChooser" JDateChooser
	 * @param startDateChooser The "startDateChooser" (an instance of JDateChooser) to set
	 */
	public void setStartDateChooser(JDateChooser startDateChooser) {
		this.startDateChooser = startDateChooser;
	}

	/** Get the "endDateChooser" JDateChooser
	 * @return endDateChooser The "endDateChooser" JDateChooser
	 */
	public JDateChooser getEndDateChooser() {
		return endDateChooser;
	}

	/** Set the "endDateChooser" JDateChooser
	 * @param endDateChooser The "endDateChooser" (an instance of JDateChooser) to set
	 */
	public void setEndDateChooser(JDateChooser endDateChooser) {
		this.endDateChooser = endDateChooser;
	}

	/** Get the "currentMode" of this panel (Mode.EDIT or Mode.CREATE)
	 * @return currentMode The currentMode Mode of this panel
	 * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IBuilderPanel#getCurrentMode()
	 */
	public Mode getCurrentMode() {
		return currentMode;
	}

	/** Set the "currentMode" of this panel (Mode.EDIT or Mode.CREATE)
	 * @param currentMode The "currentMode" (an instance of Mode) to set
	 */
	public void setCurrentMode(Mode currentMode) {
		this.currentMode = currentMode;
	}

	/** Gets the current iteration
	 * @return the current iteration
	 */
	public Iteration getCurrentIteration()
	{
		return currentIteration;
	}

	/** Sets the iteration  
	 * @param newIteration the iteration to set currentIteration to
	 */
	public void setCurrentIteration(Iteration newIteration)
	{
		currentIteration = newIteration;
	}

	/** Enables or disables all fields in the builder panel.
	 * @param setTo Boolean to activate/disable the fields in the builder panel
	 * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IBuilderPanel#setInputEnabled(boolean)
	 */
	public void setInputEnabled(boolean setTo) {
		isBuilderActive = setTo;
		enable(nameValue, setTo);
		startDateChooser.setEnabled(setTo);
		endDateChooser.setEnabled(setTo);
		btnCreate.setEnabled(setTo);
		if (!setTo){
			nameValue.setText("");
		}
		if (currentMode == Mode.CREATE) {
			totalEstimate.setVisible(false);
			totalEstimateLabel.setVisible(false);
		}
		else {
			totalEstimate.setVisible(setTo);
			totalEstimateLabel.setVisible(setTo);
		}
	}

	/** Toggles between active and inactive modes mode 
	 * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IBuilderPanel#toggleNewCancelMode()
	 */
	public void toggleNewCancelMode() {
		currentMode = Mode.CREATE; // default for this function
		isBuilderActive = !isBuilderActive;
		setInputEnabled(isBuilderActive);
	}

	/** Restore all fields to their initial values 
	 * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IBuilderPanel#resetFields()
	 */
	public void resetFields() {
		nameValue.setText("");
		startDateChooser.setDate(new Date());	// Set the two date-choosers to today
		endDateChooser.setDate(new Date());
		currentIteration = null;				// No current iteration when we are creating a new iteration

		btnCreate.setText("Create");
	}

	/** Sets the mode of the builder panel to the given mode (Mode.CREATE or Mode.EDIT).
	 * ALSO changes the text in the button 
	 * 
	 * @param mode The mode that the builder panel should be in
	 * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IBuilderPanel#setModeAndBtn(Mode)
	 */
	public void setModeAndBtn(Mode mode) {
		currentMode = mode;
		// Ensure that the button text is set correctly
		if (currentMode == Mode.CREATE) {
			btnCreate.setText("Create");
		}
		else if (mode == Mode.EDIT) {
			btnCreate.setText("Save");
		}
	}

	/** Gets the model from the panel in the form of a JSON string 
	 * that is ready to be sent as a message over the network
	 * 
	 * NOTE: This method can be used for passing messages between views!
	 * 
	 * @return JSON string of the model to be sent, Returns null on failure
	 * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IBuilderPanel#convertCurrentModelToJSON()
	 */
	public String convertCurrentModelToJSON(){
		Iteration toSend = new Iteration();

		if (this.getCurrentMode() == Mode.EDIT) toSend.setID(currentIteration.getID());

		toSend.setName(nameValue.getText());
		toSend.setStartDate(trim(startDateChooser.getDate()));
		toSend.setEndDate(trim(endDateChooser.getDate()));
		toSend.setTotalEstimate(Integer.parseInt(totalEstimate.getText()));

		return toSend.toJSON();
	}

	/** A validate function that checks to make sure that the dates and name of the iteration are valid,
	 * and sets the warning labels ("nameWarning" and "dateWarning") appropriately.
	 * Also sets the "btnCreate" button disabled if any fields are invalid, enabled if all fields are valid.
	 */
	public void isIterationValid(){

		if (!isBuilderActive){//If the builder is inactive, there is no need to check the validity of the iteration, and the warning labels should be blank
			nameWarning.setText("");
			dateWarning.setText("");
			return;
		}

		ArrayList<Iteration> iters = parent.getTabPanel().getIterationList().getIterations();//Grab the list of iterations from the parent

		//Create booleans to flag if errors are found
		boolean nameErrorFound = false;
		boolean dateErrorFound = false;

		if (nameValue.getText().length() <= 0){//If the nameValue is blank...
			nameWarning.setText("Name cannot be blank");
			nameErrorFound = true;
		}

		//Grab the dates in the builder
		Date newStart = trim(startDateChooser.getDate());
		Date newEnd = trim(endDateChooser.getDate());

		for (int i = 0; i < iters.size(); i++)//For every iteration in the list
		{
			//Grab the dates from the iteration at this index in the list
			Date oldStart = trim(iters.get(i).getStartDate());
			Date oldEnd = trim(iters.get(i).getEndDate());

			boolean errorOnThis = false;//Used to avoid indicating a date error more than once

			if (currentIteration != null && (currentIteration.getID() == iters.get(i).getID()))//We should not be comparing an iteration to it's own entry in the list, so continue
				continue;

			//Compare the names
			//  If we have not already found an error in the name,
			//   AND the nameValue is currently "backlog" (ignoring case)
			if (!nameErrorFound && nameValue.getText().toLowerCase().equals("backlog")) {
				nameWarning.setText("Name cannot be \"Backlog\"");
				nameErrorFound = true;
			}

			//If the name of the iteration being constructed matches the name of the iteration at this index (i) in the list,
			// AND  we have not already found an error in the name,
			// AND we are not comparing the iteration under construction to it's own entry in the list,
			// AND the nameValue is not blank 
			if (!nameErrorFound && nameValue.getText().equals(iters.get(i).getName()) && !nameValue.getText().equals("")){
				nameWarning.setText("Name must be unique");
				nameErrorFound = true;
			}

			//Compare the dates
			if (newStart.equals(newEnd) && oldStart.equals(oldEnd)) {				// Both are 1 day iterations
				if (newStart.equals(oldStart))										// They fall on the same day
					errorOnThis = true;	
			} else if (newStart.equals(newEnd)) {									// New is a 1 day iteration and old is not
				if (oldStart.before(newStart) && oldEnd.after(newEnd)) {			// 1 day iteration contained within a larger iteration
					errorOnThis = true;
				}
			} else if (oldStart.equals(oldEnd)) {									// Old is a 1 day iteration and new is not
				if (newStart.before(oldStart) && newEnd.after(oldEnd)) {			// 1 day iteration contained within a larger iteration
					errorOnThis = true;
				}
			} else if (!(newStart.equals(newEnd) || oldStart.equals(oldEnd))) {		// Neither is a one-day iteration
				if (newStart.equals(oldStart) ||									// Same start date
						newEnd.equals(oldEnd) ||									// Same end date
						(newStart.before(oldStart) && oldStart.before(newEnd)) ||	// Overlap where new date starts first
						(oldStart.before(newStart) && newStart.before(oldEnd)) ||	// Overlap where old date starts first
						(newStart.before(oldStart) && newEnd.after(oldEnd)) ||		// Old contained within new
						(oldStart.before(newStart) && oldEnd.after(newEnd))) {		// New contained within old
					errorOnThis = true;
				}
			} else if (newStart.equals(newEnd) || oldStart.equals(oldEnd)) {		// one is a one-day iteration
				if (newStart.equals(oldStart) ||  newEnd.equals(oldEnd) ||			// Same start date OR Same end date								// Same end date
						(oldStart.before(newStart) && newEnd.before(oldEnd)) ||		// new inside of old
						(newStart.before(oldStart) && oldEnd.before(newEnd))) {		// old inside of new
					errorOnThis = true;
				}
			}
			//If this is the first time we have found an iteration whose dates overlap with the iteration under construction/editing
			if (errorOnThis && !dateErrorFound)
			{
				dateWarning.setText("Start and end dates cannot overlap with another iteration");
				dateErrorFound = true;
			}
		}

		//If no name error has been found, clear the nameWarning label
		if (!nameErrorFound)
			nameWarning.setText("");

		//If no date error has been found, clear the dateWarning label
		if (!dateErrorFound)
			dateWarning.setText("");

		//Revalidate and repaint the builder panel to ensure changes are shown
		this.revalidate();
		this.repaint();

		if ( nameErrorFound || dateErrorFound){//If any errors were found
			btnCreate.setEnabled(false);//disable the create button
			return;
		}
		else{
			btnCreate.setEnabled(true);//enable the create button
			return;
		}

	}

	/** Takes a JSON string that holds an array of models and uploads them
	 *  to the builder panel.
	 *  
	 * @param jsonArray An array of models in JSON string form
	 * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IBuilderPanel#displayModelFromJSONArray(String)
	 */
	public void displayModelFromJSONArray(String jsonArray) {
		Iteration toDisplay = Iteration.fromJSONArray(jsonArray)[0];

		currentIteration = toDisplay;

		nameValue.setText(toDisplay.getName());
		startDateChooser.setDate(toDisplay.getStartDate());
		endDateChooser.setDate(toDisplay.getEndDate());
		totalEstimate.setText(Integer.toString(toDisplay.getTotalEstimate()));

		setInputEnabled(true);
	}

	/** Sets up the controllers and action listeners. This should be where all
	 *  controllers and action listeners are initialized because the controllers
	 *  require references that are not not fully initialized when the 
	 *  constructor for this class is called.
	 * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IBuilderPanel#setupControllersAndListeners()
	 */
	public void setupControllersAndListeners() {
		saveController = new SaveModelController(parent.getTabPanel().getIterationList(),this,"iteration");
		btnCreate.addActionListener(saveController);

		startDateChooser.getDateEditor().addPropertyChangeListener(
				new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent e) {
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {

								if (startDateChooser.getDate().compareTo(endDateChooser.getDate()) > 0) {
									endDateChooser.setDate(startDateChooser.getDate());
									endDateChooser.setMinSelectableDate(startDateChooser.getDate());
								} else {
									endDateChooser.setMinSelectableDate(startDateChooser.getDate());
								}
							}
						});

						SwingUtilities.invokeLater(new Runnable() { 
							public void run() {
								isIterationValid();
							}
						});
					}
				});

		endDateChooser.getDateEditor().addPropertyChangeListener(
				new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent e) {
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								isIterationValid();
							}
						});
					}
				});

	}

	/** Set the given JTextField to the given enable status as well 
	 *  set the field's background to the correct color
	 * 
	 * @param field   The field that needs enabling and colors
	 * @param enabled  True to enable and False to disable
	 */
	public void enable(JTextField field, boolean enabled) {
		if (enabled) {
			field.setEnabled(true);
			field.setBackground(Color.WHITE);
		}
		else {
			field.setEnabled(false);
			field.setBackground(new Color(238,238,238));
		}
	}

	/** Trims a date by setting the time to 1 millisecond before midnight on the given day
	 * 
	 * @param date The Date to trim
	 * @return The trimmed date.
	 */
	public static synchronized Date trim(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);

		return cal.getTime();
	}
	
	/** Gets the ListTab "parent"  of this tab
	 * @return the ListTab that this IterationBuilderPanel is in 
	 * @see edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IBuilderPanel#getMyParent()
	 */
	public ListTab getMyParent() {
		return parent;
	}
}
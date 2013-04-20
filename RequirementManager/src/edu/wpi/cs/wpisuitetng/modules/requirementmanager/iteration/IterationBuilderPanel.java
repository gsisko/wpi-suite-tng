/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Robert Dabrowski
 * Danielle LaRose
 * Edison Jimenez
 * Christian Gonzalez
 * Mike Calder
 * John Bosworth
 * Paula Rudy
 * Gabe Isko
 * Bangyan Zhang
 * Cassie Hudson
 * Robert Smieja
 * Alex Solomon
 * Brian Hetherman
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.iteration;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
 *  tab is selected.  */
@SuppressWarnings("serial")
public class IterationBuilderPanel extends JPanel implements ActionListener, IBuilderPanel {

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
	 *
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

		//construct the components
		btnCreate = new JButton("Create");
		nameValue = new JTextField();
		enable(nameValue, false);
		startDateChooser = new JDateChooser(trim(new Date()));
		endDateChooser = new JDateChooser(trim(new Date()));
		totalEstimate = new JLabel("0");

		// The action listener for these are below
		btnCreate.setEnabled(false);
		startDateChooser.setEnabled(false);
		endDateChooser.setEnabled(false);

		totalEstimate.setFont(totalEstimateLabel.getFont().deriveFont(Font.NORMAL));

		//Add a titled boarder to this panel
		setBorder(BorderFactory.createTitledBorder("Iteration Builder"));

		//set the layout for this panel
		setLayout(new GridBagLayout());
		GridBagConstraints IterationBuilderConstraints = new GridBagConstraints();

		//Set the preferred sizes of the components
		startDateChooser.setPreferredSize(new Dimension (125,20));
		endDateChooser.setPreferredSize(new Dimension (125,20));
		btnCreate.setPreferredSize(new Dimension (75,30));

		//Total Estimate
		//Set the constraints for the "totalEstimateLabel" and add it to the view
		IterationBuilderConstraints.anchor = GridBagConstraints.LINE_END; //This sets the anchor of the field, here we have told it to anchor the component to the center right of it's field
		IterationBuilderConstraints.insets = new Insets(10,10,10,0); //Set the top padding to 10 units of blank space, set left padding to 10 units, bottom padding to 10 units
		IterationBuilderConstraints.gridx = 0;//Set the x coord of the cell of the layout we are describing
		IterationBuilderConstraints.gridy = 0;//Set the y coord of the cell of the layout we are describing
		add(totalEstimateLabel, IterationBuilderConstraints);//Actually add the "totalEstimateLabel" to the layout given the previous constraints
		//Set the constraints for the "totalEstimate" and add it to the view
		IterationBuilderConstraints.anchor = GridBagConstraints.LINE_START;//This sets the anchor of the field, here we have told it to anchor the component to the center left of it's field
		IterationBuilderConstraints.insets = new Insets(10,10,10,25); //Set the top padding to 10 units of blank space, set left padding to 10 units, bottom padding to 10 units, right padding to 25 units
		IterationBuilderConstraints.ipadx= 30;//stretch this field horizontally by 30 units
		IterationBuilderConstraints.gridx = 1;
		IterationBuilderConstraints.gridy = 0;
		add(totalEstimate, IterationBuilderConstraints);//Actually add the "totalEstimate" to the layout given the previous constraints



		//Iteration Name
		//Set the constraints for the "nameLabel" and add it to the view
		IterationBuilderConstraints.anchor = GridBagConstraints.LINE_END; //This sets the anchor of the field, here we have told it to anchor the component to the center right of it's field
		IterationBuilderConstraints.ipadx=0;//This resets the horizontal padding from the previously defined 30 units back to 0 units
		IterationBuilderConstraints.insets = new Insets(10,10,10,0); //Set the top padding to 10 units of blank space, set left padding to 10 units, bottom padding to 10 units
		IterationBuilderConstraints.gridx = 2;//Set the x coord of the cell of the layout we are describing
		IterationBuilderConstraints.gridy = 0;//Set the y coord of the cell of the layout we are describing
		add(nameLabel, IterationBuilderConstraints);//Actually add the "nameLabel" to the layout given the previous constraints
		//Set the constraints for the "nameValue" and add it to the view
		IterationBuilderConstraints.anchor = GridBagConstraints.LINE_START;//This sets the anchor of the field, here we have told it to anchor the component to the center left of it's field
		IterationBuilderConstraints.insets = new Insets(10,10,10,25); //Set the top padding to 10 units of blank space, set left padding to 10 units, bottom padding to 10 units, right padding to 25 units
		IterationBuilderConstraints.ipadx=80;//stretch this field horizontally by 80 units
		IterationBuilderConstraints.gridx = 3;
		IterationBuilderConstraints.gridy = 0;
		add(nameValue, IterationBuilderConstraints);//Actually add the "nameValue" to the layout given the previous constraints

		//Start Date
		//Set the constraints for the "startDateLabel" and add it to the view
		IterationBuilderConstraints.anchor = GridBagConstraints.LINE_END;
		IterationBuilderConstraints.insets = new Insets(10,10,10,0);
		IterationBuilderConstraints.ipadx=0;//This resets the horizontal padding from the previously defined 80 units back to 0 units
		IterationBuilderConstraints.gridx = 4;
		IterationBuilderConstraints.gridy = 0;
		add(startDateLabel, IterationBuilderConstraints);
		//Set the constraints for the "startDateChooser" and add it to the view
		IterationBuilderConstraints.anchor = GridBagConstraints.LINE_START;
		IterationBuilderConstraints.insets = new Insets(10,10,10,25);
		IterationBuilderConstraints.gridx = 5;
		IterationBuilderConstraints.gridy = 0;
		add(startDateChooser, IterationBuilderConstraints);

		//End Date
		//Set the constraints for the "endDateLabel" and add it to the view
		IterationBuilderConstraints.anchor = GridBagConstraints.LINE_END;
		IterationBuilderConstraints.insets = new Insets(10,10,10,0);
		IterationBuilderConstraints.gridx = 6;
		IterationBuilderConstraints.gridy = 0;
		add(endDateLabel, IterationBuilderConstraints);
		//Set the constraints for the "endDateChooser" and add it to the view
		IterationBuilderConstraints.anchor = GridBagConstraints.LINE_START;
		IterationBuilderConstraints.insets = new Insets(10,10,10,40);
		IterationBuilderConstraints.gridx = 7;
		IterationBuilderConstraints.gridy = 0;
		add(endDateChooser, IterationBuilderConstraints);


		//Save button:
		//Set the constraints for the "Create" button and add it to the view
		IterationBuilderConstraints.anchor = GridBagConstraints.LINE_END;
		IterationBuilderConstraints.gridx = 8;
		IterationBuilderConstraints.gridy = 0;
		add(btnCreate, IterationBuilderConstraints);//Actually add the "Create" button to the layout given the previous constraints
	}

	public JButton getButton()
	{
		return btnCreate;
	}

	public JTextField getIterationValue()
	{
		return nameValue;
	}

	/**
	 * @return the currentMode
	 */
	public Mode getCurrentMode() {
		return currentMode;
	}

	/**
	 * @param currentMode the currentMode to set
	 */
	public void setCurrentMode(Mode currentMode) {
		this.currentMode = currentMode;
	}

	public Iteration getCurrentIteration()
	{
		return currentIteration;
	}

	public void setCurrentIteration(Iteration newIteration)
	{
		currentIteration = newIteration;
	}

	// TODO why is this here?
	@Override
	public void actionPerformed(ActionEvent e) {

	}

	/** Enables or disables all fields in the builder panel.
	 * 
	 * @param setTo True activates the fields and false deactivates them
	 */
	public void setInputEnabled(boolean setTo) {
		isBuilderActive = setTo;
		enable(this.nameValue, setTo);
		this.startDateChooser.setEnabled(setTo);
		this.endDateChooser.setEnabled(setTo);
		this.btnCreate.setEnabled(setTo);
		if (!setTo){
			this.nameValue.setText("");
		}
		if (this.currentMode == Mode.CREATE) {
			this.totalEstimate.setVisible(false);
			this.totalEstimateLabel.setVisible(false);
		}
		else {
			this.totalEstimate.setVisible(setTo);
			this.totalEstimateLabel.setVisible(setTo);
		}
	}


	/** Toggles between active and inactive modes mode */
	public void toggleNewCancelMode() {
		currentMode = Mode.CREATE; // default for this function
		isBuilderActive = !isBuilderActive;
		setInputEnabled(isBuilderActive);
	}


	/** Restore all fields to their initial values */
	public void resetFields() {
		this.nameValue.setText("");
		startDateChooser.setDate(new Date());	// Set the two date-choosers to today
		endDateChooser.setDate(new Date());

		btnCreate.setText("Create");
	}

	/** Sets the mode of the builder panel to the given mode. ALSO changes
	 *  the text in the button 
	 *  Mode.CREATE or Mode.EDIT
	 * 
	 * @param mode The mode that the builder panel should be in
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
	 *  that is ready to be sent as a message over the network
	 * 
	 * *NOTE: can be used for passing messages between views!
	 * 
	 * @return JSON string of the model to be sent, Returns null on failure
	 */
	public String convertCurrentModelToJSON(){
		Iteration toSend = new Iteration();

		if (this.getCurrentMode() == Mode.EDIT) toSend.setID(currentIteration.getID());

		if(!isIterationValid())
			return null;

		toSend.setName(this.nameValue.getText());
		toSend.setStartDate(trim(this.startDateChooser.getDate()));
		toSend.setEndDate(trim(this.endDateChooser.getDate()));
		toSend.setTotalEstimate(Integer.parseInt(this.totalEstimate.getText()));

		System.out.println(toSend.toJSON());

		return toSend.toJSON();
	}

	/**A validator function that checks to make sure that the dates and name of the iteration are valid
	 * 
	 * @return true if the iteration update/save is valid, false otherwise
	 */
	public boolean isIterationValid(){

		ArrayList<Iteration> iters = parent.getTabPanel().getIterationList().getIterations();

		String error = "";

		if (this.nameValue.getText().length() <= 0){
			error += "The name field of the iteration must be non-blank.\n";
		}

		boolean nameErrorFound = false;
		boolean dateErrorFound = false;

		for (int i = 0; i < iters.size(); i++)
		{
			Date newStart = trim(this.startDateChooser.getDate());
			Date newEnd = trim(this.endDateChooser.getDate());
			Date oldStart = trim(iters.get(i).getStartDate());
			Date oldEnd = trim(iters.get(i).getEndDate());
			boolean errorOnThis = false;

			if (this.currentIteration != null && (this.currentIteration.getID() == iters.get(i).getID()))
				continue;

			if (!nameErrorFound && this.nameValue.getText().toLowerCase().equals("backlog")) {
				error += "The name field of the iteration cannot be \"Backlog\".\n";
				nameErrorFound = true;
			}

			if (!nameErrorFound && this.nameValue.getText().equals(iters.get(i).getName()))
			{
				error += "The name field of the iteration cannot be the same as other iterations.\n";
				nameErrorFound = true;
			}

			if (newStart.equals(newEnd) && oldStart.equals(oldEnd)) {				// Both are 1 day iterations
				if (newStart.equals(oldStart))										// They fall on the same day
					errorOnThis = true;	
			} else if (!(newStart.equals(newEnd) || oldStart.equals(oldEnd))) {		// Neither is a one-day iteration
				if (newStart.equals(oldStart) ||									// Same start date
						newEnd.equals(oldEnd) ||									// Same end date
						(newStart.before(oldStart) && oldStart.before(newEnd)) ||	// Overlap where new date starts first
						(oldStart.before(newStart) && newStart.before(oldEnd))) {	// Overlap where old date starts first
					errorOnThis = true;
				}
			}
			if (errorOnThis && !dateErrorFound)
			{
				error += "The start date and end date of the iteration cannot fall within another iteration's dates.\n";
				dateErrorFound = true;
			}
		}


		if (error.length() > 0) {
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	/** Checks if there is an overlap between a one day iteration and a multi-day iteration
	 * 
	 * @param oneDay
	 * @param start
	 * @param end
	 * @return
	 */
	private boolean oneDayOverlap(Date oneDay, Date start, Date end) {
		return !oneDay.equals(start);
	}

	/** Takes a JSON string that holds an array of models and uploads them
	 *  to the builder panel.
	 *  
	 * @param jsonArray An array of models in JSON string form
	 */
	public void displayModelFromJSONArray(String jsonArray) {
		Iteration toDisplay = Iteration.fromJSONArray(jsonArray)[0];

		currentIteration = toDisplay;

		this.nameValue.setText(toDisplay.getName());
		this.startDateChooser.setDate(toDisplay.getStartDate());
		this.endDateChooser.setDate(toDisplay.getEndDate());
		this.totalEstimate.setText(Integer.toString(toDisplay.getTotalEstimate()));

		setInputEnabled(true);
	}

	/** Sets up the controllers and action listeners. This should be where all
	 *  controllers and action listeners are initialized because the controllers
	 *  require references that are not not fully initialized when the 
	 *  constructor for this class is called.
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
					}
				});

	}

	/** Set the given box to the given enable status as well 
	 *  set the box to the correct color
	 * 
	 * @param box   The box that needs enabling and colors
	 * @param enabled  True to enable and False to disable
	 */
	public void enable(JTextField box, boolean enabled) {
		if (enabled) {
			box.setEnabled(true);
			box.setBackground(Color.WHITE);
		}
		else {
			box.setEnabled(false);
			box.setBackground(new Color(238,238,238));
		}
	}

	/**
	 * Trims a date by setting the time to 1 millisecond after midnight on the given day
	 * 
	 * @param date The Date to trim
	 * @return The trimmed date.
	 */
	public static Date trim(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DAY_OF_YEAR, 1); //Set it a day forward
		cal.add(Calendar.MILLISECOND, -1); //Go a day backward so we reach the last millisecond of the day

		return cal.getTime();
	}
}
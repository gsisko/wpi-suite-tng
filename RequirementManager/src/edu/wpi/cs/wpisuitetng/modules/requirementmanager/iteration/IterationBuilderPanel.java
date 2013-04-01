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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.iteration;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.*;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.SaveModelController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IBuilderPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Iteration;


@SuppressWarnings("serial")
public class IterationBuilderPanel extends JPanel implements ActionListener, IBuilderPanel {
	public enum Mode {
		CREATE,
		EDIT
	}

	//the labels
	private final JLabel startDateLabel; 
	private final JLabel endDateLabel;
	private final JLabel nameLabel;

	//the fillable components
	private JTextField nameValue;
	
	//button
	private final JButton btnSave;
	private final ListPanel parent;

	private IBuilderPanel.Mode currentMode;

	private String curType = "Id";
	
	private Iteration currentIteration;
	private boolean isBuilderActive;
	
	private SaveModelController saveController;
	
	Calendar cal = Calendar.getInstance();
	JSpinner startDate;
	JSpinner endDate;

	/**
	 * Construct the panel
	 */
	public IterationBuilderPanel(ListPanel view) {
		parent = view;
		currentMode = IBuilderPanel.Mode.CREATE;
		currentIteration = null;
		isBuilderActive = false;
		
		//create title
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBorder(BorderFactory.createTitledBorder("Iteration Builder"));

		//construct the panels
		nameLabel = new JLabel("Name:");
		startDateLabel = new JLabel("Start Date:(mm/dd/yyyy)");
		endDateLabel = new JLabel("End Date:(mm/dd/yyyy)");
		btnSave = new JButton("Save");

		//construct the components
		nameValue = new JTextField();
		nameValue.setEnabled(false);
		
		Date now = cal.getTime();		// Get dates for start and end of range
		cal.add(Calendar.YEAR, -10);
		Date dateStart = cal.getTime();
		cal.add(Calendar.YEAR, 110);
		Date dateEnd = cal.getTime();
		SpinnerModel start = new SpinnerDateModel(now, dateStart, dateEnd, Calendar.DAY_OF_MONTH);	// Create the spinner-models
		SpinnerModel end = new SpinnerDateModel(now, dateStart, dateEnd, Calendar.DAY_OF_MONTH);
		this.startDate = new JSpinner(start);		// Create the spinners
		this.endDate = new JSpinner(end);
		
		JSpinner.DateEditor startDE = new JSpinner.DateEditor(this.startDate, "dd/MM/yyyy");
		JSpinner.DateEditor endDE = new JSpinner.DateEditor(this.startDate, "dd/MM/yyyy");
		this.startDate.setEditor(startDE);
		this.endDate.setEditor(endDE);
		
		
		// The action listener for these are below
		btnSave.setEnabled(false);
		this.startDate.setEnabled(false);
		this.endDate.setEnabled(false);

		//set the layout
		setLayout(new GridBagLayout());
		GridBagConstraints IterationBuilderConstraints = new GridBagConstraints();
		IterationBuilderConstraints.anchor= GridBagConstraints.NORTH;

		//adjust location
		btnSave.setAlignmentX(Component.RIGHT_ALIGNMENT);

		
		//Iteration Name
		//Set the constraints for the "nameLabel" and add it to the view
		IterationBuilderConstraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		IterationBuilderConstraints.ipady = 0;//This tells the layout to reset the vertical ipad from the previously defined 20 units to now 0 units
		IterationBuilderConstraints.anchor = GridBagConstraints.CENTER; //This sets the anchor of the field, here we have told it to anchor the component to the top center of it's field
		IterationBuilderConstraints.insets = new Insets(0,15,0,0);  //Set the top padding to 10 units  of blank space
		IterationBuilderConstraints.gridx = 0;//Set the x coord of the cell of the layout we are describing
		IterationBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		add(nameLabel, IterationBuilderConstraints);
		IterationBuilderConstraints.fill = GridBagConstraints.CENTER;//This sets the constraints of this field so that the item will stretch both horizontally and vertically to fill it's area
		IterationBuilderConstraints.gridx = 1;//Set the x coord of the cell of the layout we are describing
		IterationBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		IterationBuilderConstraints.ipadx=80;
		add(nameValue, IterationBuilderConstraints);

		//Start Date
		//Set the constraints for the "startDateLabel" and add it to the view
		IterationBuilderConstraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		IterationBuilderConstraints.ipady = 0;//This tell	s the layout to reset the vertical ipad from the previously defined 20 units to now 0 units
		IterationBuilderConstraints.anchor = GridBagConstraints.CENTER; //This sets the anchor of the field, here we have told it to anchor the component to the top center of it's field
		IterationBuilderConstraints.insets = new Insets(0,15,0,0);
		IterationBuilderConstraints.gridx = 2;//Set the x coord of the cell of the layout we are describing
		IterationBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		add(startDateLabel, IterationBuilderConstraints);//Actually add the "typenLabel" to the layout given the previous constraints
		IterationBuilderConstraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		IterationBuilderConstraints.ipady = 0;//This tells the layout to reset the vertical ipad from the previously defined 20 units to now 0 units
		IterationBuilderConstraints.anchor = GridBagConstraints.CENTER; //This sets the anchor of the field, here we have told it to anchor the component to the top center of it's field
		IterationBuilderConstraints.insets = new Insets(0,15,0,0);  //Set the top padding to 10 units  of blank space
		IterationBuilderConstraints.gridx = 3;//Set the x coord of the cell of the layout we are describing
		IterationBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		add(startDate, IterationBuilderConstraints);
	
		//End Date
		//Set the constraints for the "typeLabel" and add it to the view
		IterationBuilderConstraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		IterationBuilderConstraints.anchor = GridBagConstraints.CENTER; //This sets the anchor of the field, here we have told it to anchor the component to the top center of it's field
		IterationBuilderConstraints.insets = new Insets(0,15,0,0);
		IterationBuilderConstraints.gridx = 4;//Set the x coord of the cell of the layout we are describing
		IterationBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		add(endDateLabel, IterationBuilderConstraints);//Actually add the "typenLabel" to the layout given the previous constraints
		IterationBuilderConstraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		IterationBuilderConstraints.ipady = 0;//This tells the layout to reset the vertical ipad from the previously defined 20 units to now 0 units
		IterationBuilderConstraints.anchor = GridBagConstraints.CENTER; //This sets the anchor of the field, here we have told it to anchor the component to the top center of it's field
		IterationBuilderConstraints.insets = new Insets(0,15,0,0);  //Set the top padding to 10 units  of blank space
		IterationBuilderConstraints.gridx = 5;//Set the x coord of the cell of the layout we are describing
		IterationBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		add(endDate, IterationBuilderConstraints);
		
		//Save button:
		//Set the constraints for the "Save" and add it to the view
		IterationBuilderConstraints.fill = GridBagConstraints.NONE;//This sets the constraints of this field so that the item will not stretch to fill it's area
		IterationBuilderConstraints.anchor = GridBagConstraints.CENTER; //This sets the anchor of the field, here we have told it to anchor the component to the bottom right of it's field
		IterationBuilderConstraints.gridx = 8;//Set the x coord of the cell of the layout we are describing
		IterationBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		btnSave.setPreferredSize(new Dimension (10,30));
		IterationBuilderConstraints.fill = GridBagConstraints.CENTER;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		IterationBuilderConstraints.gridx = 9;//Set the x coord of the cell of the layout we are describing
		IterationBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		add(btnSave, IterationBuilderConstraints);//Actually add the "Save" to the layout given the previous constraints
		//end Save button
	}
	
	
	public JButton getButton()
	{
		return btnSave;
	}

	public JTextField getIterationValue()
	{
		return nameValue;
	}

	/**
	 * @return the currentMode
	 */
	public IBuilderPanel.Mode getCurrentMode() {
		return currentMode;
	}

	/**
	 * @param currentMode the currentMode to set
	 */
	public void setCurrentMode(IBuilderPanel.Mode currentMode) {
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
	/**
	 * @return the grandpa
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Tried to do an action");
		
	}

	
	@Override
	public void setInputEnabled(boolean setTo) {
		System.out.println("Input set to " + setTo);
		this.nameValue.setEnabled(setTo);
		this.startDate.setEnabled(setTo);
		this.endDate.setEnabled(setTo);
		this.btnSave.setEnabled(setTo);
	}

	
	@Override
	public void toggleNewCancelMode() {
		System.out.println("Mode toggled to " + !isBuilderActive);
		currentMode = IBuilderPanel.Mode.CREATE; // default for this function
		isBuilderActive = !isBuilderActive;
		setInputEnabled(isBuilderActive);
	}

	
	
	// New methods for the refactor.
	public void resetFields() {
		System.out.println("Values reset");
		this.nameValue.setText("");
		this.startDate.setValue(this.cal.getTime());
		this.endDate.setValue(this.cal.getTime());
	}
	
	
	public void setModeAndBtn(IBuilderPanel.Mode mode) {
		System.out.println("Mode and button set to " + mode);
		this.currentMode = mode;
		if (mode == IBuilderPanel.Mode.CREATE) {
			this.btnSave.setText("Create");
		}
		else if (mode == IBuilderPanel.Mode.EDIT) {
			this.btnSave.setText("Save");
		}
	}
	
	
	public String convertCurrentModelToJSON() {
		System.out.println("JSON created");
		Iteration toSend = new Iteration();
		Date start = new Date();
		Date end = new Date();
		long startTime = 0;
		long endTime = 0;
		
		toSend.setName(this.nameValue.getText());
		toSend.setStartDate((Date) this.startDate.getValue());
		toSend.setEndDate((Date) this.endDate.getValue());
		
		return toSend.toJSON();
	}
	
	
	public void displayModelFromJSONArray(String jsonArray) {
		System.out.println("JSON read");
		Iteration toDisplay = Iteration.fromJSONArray(jsonArray)[0];
		
		this.startDate.setValue(toDisplay.getStartDate());
		this.endDate.setValue(toDisplay.getEndDate());
		
		
		this.nameValue.setText(toDisplay.getName());
		
		setInputEnabled(true);
	}
	
	
	public void setupControllersAndListeners() {
		System.out.println("Controller setup");
		this.saveController = new SaveModelController(parent.getTabPanel().getIterationList(),this,"iteration");
		this.btnSave.addActionListener(saveController);
		
		ChangeListener listener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// Set max and min years
				SpinnerDateModel tmpStart = (SpinnerDateModel) startDate.getModel();
				SpinnerDateModel tmpEnd = (SpinnerDateModel) endDate.getModel();
				tmpStart.setEnd((Comparable) endDate.getValue());
				tmpEnd.setStart((Comparable) startDate.getValue());
			}
		};
		this.startDate.addChangeListener(listener);
		this.endDate.addChangeListener(listener);
	}
	

}


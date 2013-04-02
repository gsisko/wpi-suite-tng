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

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.SaveModelController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IBuilderPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Iteration;


@SuppressWarnings({"serial","unused"})
public class IterationBuilderPanel extends JPanel implements ActionListener, IBuilderPanel {

	//the labels
	private final JLabel startDateLabel; 
	private final JLabel endDateLabel;
	private final JLabel nameLabel;

	//the fillable components
	private JTextField nameValue;
	private JDateChooser startDateChooser;
	private JDateChooser endDateChooser;
	
	//button
	private final JButton btnSave;

	private final ListPanel parent;

	private Mode currentMode;

	private String curType = "Id";
	
	private Iteration currentIteration;
	private boolean isBuilderActive;
	
	private SaveModelController saveController;

	/**
	 * Construct the panel
	 */
	public IterationBuilderPanel(ListPanel view) {
		parent = view;
		currentMode = Mode.CREATE;
		currentIteration = null;
		isBuilderActive = false;
		
		//create title
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBorder(BorderFactory.createTitledBorder("Iteration Builder"));

		//construct the panels
		nameLabel = new JLabel("Name:");
		startDateLabel = new JLabel("Start Date:");
		endDateLabel = new JLabel("End Date:");
		btnSave = new JButton("Save");

		//construct the components
		nameValue = new JTextField();
		enable(nameValue, false);
		
		startDateChooser = new JDateChooser(new Date());
		endDateChooser = new JDateChooser(new Date());

		// The action listener for these are below
		btnSave.setEnabled(false);
		startDateChooser.setEnabled(false);
		endDateChooser.setEnabled(false);

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
		IterationBuilderConstraints.fill = GridBagConstraints.CENTER;//This sets the constraints of this field so that the item will stretch both horizontally and vertically to fill it's area
		IterationBuilderConstraints.gridx = 3;//Set the x coord of the cell of the layout we are describing
		IterationBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		add(startDateChooser, IterationBuilderConstraints);
	
		//End Date
		//Set the constraints for the "typeLabel" and add it to the view
		IterationBuilderConstraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		IterationBuilderConstraints.anchor = GridBagConstraints.CENTER; //This sets the anchor of the field, here we have told it to anchor the component to the top center of it's field
		IterationBuilderConstraints.insets = new Insets(0,15,0,0);
		IterationBuilderConstraints.gridx = 4;//Set the x coord of the cell of the layout we are describing
		IterationBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		add(endDateLabel, IterationBuilderConstraints);//Actually add the "typenLabel" to the layout given the previous constraints
		IterationBuilderConstraints.fill = GridBagConstraints.CENTER;//This sets the constraints of this field so that the item will stretch both horizontally and vertically to fill it's area
		IterationBuilderConstraints.gridx = 5;//Set the x coord of the cell of the layout we are describing
		IterationBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		add(endDateChooser, IterationBuilderConstraints);

		
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
	
	public void setUp() {
		setupControllersAndListeners();
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
	/**
	 * @return the grandpa
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	@Override
	public void setInputEnabled(boolean setTo) {
		isBuilderActive = setTo;
		enable(this.nameValue, setTo);
		this.startDateChooser.setEnabled(setTo);
		this.endDateChooser.setEnabled(setTo);
		this.btnSave.setEnabled(setTo);
		if (!setTo){
			this.nameValue.setText("");
		}
	}


	@Override
	public void toggleNewCancelMode() {
		currentMode = Mode.CREATE; // default for this function
		isBuilderActive = !isBuilderActive;
		setInputEnabled(isBuilderActive);
	}

	
	
	// New methods for the refactor.
	public void resetFields() {
		this.nameValue.setText("");
		startDateChooser.setDate(new Date());	// Set the two date-choosers to today
		endDateChooser.setDate(new Date());
	}
	
	
	public void setModeAndBtn(Mode mode) {
		this.currentMode = mode;
		if (mode == Mode.CREATE) {
			this.btnSave.setText("Create");
		}
		else if (mode == Mode.EDIT) {
			this.btnSave.setText("Save");
		}
	}

	
	public String convertCurrentModelToJSON() {
		Iteration toSend = new Iteration();
		
		toSend.setName(this.nameValue.getText());
		toSend.setStartDate(this.startDateChooser.getDate());
		toSend.setEndDate(this.endDateChooser.getDate());
		
		System.out.println(toSend.toJSON());
		
		return toSend.toJSON();
	}
	
	
	public void displayModelFromJSONArray(String jsonArray) {
		Iteration toDisplay = Iteration.fromJSONArray(jsonArray)[0];
		
		this.nameValue.setText(toDisplay.getName());
		this.startDateChooser.setDate(toDisplay.getStartDate());
		this.endDateChooser.setDate(toDisplay.getEndDate());
		
		setInputEnabled(true);
	}

	
	public void setupControllersAndListeners() {
		saveController = new SaveModelController(parent.getTabPanel().getIterationList(),this,"iteration");
		btnSave.addActionListener(saveController);

		startDateChooser.getDateEditor().addPropertyChangeListener("date",
				new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent e) {
						if (startDateChooser.getDate().compareTo(new Date()) > 0) {
							endDateChooser.setMinSelectableDate(startDateChooser.getDate());
						} else {
							endDateChooser.setMinSelectableDate(new Date());
						}
					}
				}
		);
		
		endDateChooser.getDateEditor().addPropertyChangeListener(
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent e) {
						startDateChooser.setMaxSelectableDate(endDateChooser.getDate());
					}
				}
		);
	}
	
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
}


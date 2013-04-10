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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.toedter.calendar.JDateChooser;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.SaveModelController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IBuilderPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListTab;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListTabPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IBuilderPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Iteration;


@SuppressWarnings({"serial","unused"})
public class IterationBuilderPanel extends JPanel implements ActionListener, IBuilderPanel {

	//the labels
	private final JLabel startDateLabel;
	private final JLabel endDateLabel;
	private final JLabel nameLabel;
	private final JLabel totalEstimateLabel;
	private final JLabel totalEstimate;

	//the fillable components
	private JTextField nameValue;
	private JDateChooser startDateChooser;
	private JDateChooser endDateChooser;

	//button
	private final JButton btnCreate;

	private final ListTab parent;

	private Mode currentMode;

	private String curType = "Id";

	private Iteration currentIteration;
	private boolean isBuilderActive;

	private SaveModelController saveController;

	/**
	 * Construct the panel
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
		startDateChooser = new JDateChooser(new Date());
		endDateChooser = new JDateChooser(new Date());
		totalEstimate = new JLabel("0");

		// The action listener for these are below
		btnCreate.setEnabled(false);
		startDateChooser.setEnabled(false);
		endDateChooser.setEnabled(false);

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

	public void setUp() {
		setupControllersAndListeners();
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

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	@Override
	public void setInputEnabled(boolean setTo) {
		isBuilderActive = setTo;
		enable(this.nameValue, setTo);
		this.startDateChooser.setEnabled(setTo);
		this.endDateChooser.setEnabled(setTo);
		this.btnCreate.setEnabled(setTo);
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

	public String convertCurrentModelToJSON(){
		Iteration toSend = new Iteration();
		
		if (this.getCurrentMode() == Mode.EDIT) toSend.setID(currentIteration.getID());

		if(!isIterationValid())
			return null;

		toSend.setName(this.nameValue.getText());
		toSend.setStartDate(this.startDateChooser.getDate());
		toSend.setEndDate(this.endDateChooser.getDate());

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
			
			if (this.currentIteration != null && (this.currentIteration.getID() == iters.get(i).getID()))
			    continue;
			
			if (!nameErrorFound && this.nameValue.getText().equals("Backlog")) {
				error += "The name field of the iteration cannot be \"Backlog\".\n";
				nameErrorFound = true;
			}
			
			if (!nameErrorFound && this.nameValue.getText().equals(iters.get(i).getName()))
			{
				error += "The name field of the iteration cannot be the same as other iterations.\n";
				nameErrorFound = true;
			}

			if(	((this.startDateChooser.getDate().before(iters.get(i).getEndDate()) 
					&& this.startDateChooser.getDate().after(iters.get(i).getStartDate()))
				|| (this.endDateChooser.getDate().before(iters.get(i).getEndDate()) 
					&& this.endDateChooser.getDate().after(iters.get(i).getStartDate())))
				|| (iters.get(i).getStartDate().after(this.startDateChooser.getDate()) 
					&& iters.get(i).getStartDate().before(this.endDateChooser.getDate())) 
				|| (iters.get(i).getEndDate().after(this.startDateChooser.getDate())
					&& iters.get(i).getEndDate().before(this.endDateChooser.getDate()))
				|| (this.startDateChooser.getDate().equals(iters.get(i).getStartDate())
					&& this.endDateChooser.getDate().equals(iters.get(i).getEndDate())))
			{
				if (!dateErrorFound)
				{
					error += "The start date and end date of the iteration cannot fall within another iteration's dates.\n";
					dateErrorFound = true;
				}
			}
		}

		
	    if (error.length() > 0) {
	    	JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
	    	return false;
	    }
		
		return true;
	}


	public void displayModelFromJSONArray(String jsonArray) {
		Iteration toDisplay = Iteration.fromJSONArray(jsonArray)[0];
		
		currentIteration = toDisplay;

		this.nameValue.setText(toDisplay.getName());
		this.startDateChooser.setDate(toDisplay.getStartDate());
		this.endDateChooser.setDate(toDisplay.getEndDate());

		setInputEnabled(true);
	}


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
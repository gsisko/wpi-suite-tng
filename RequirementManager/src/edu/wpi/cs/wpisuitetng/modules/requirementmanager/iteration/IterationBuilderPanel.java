package edu.wpi.cs.wpisuitetng.modules.requirementmanager.iteration;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.jdatepicker.JDatePicker;
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
	private final JLabel StartDate; 
	private final JLabel EndDate;
	private final JLabel Name;

	//the fillable components
	private JTextField txtValue;
	private final JDatePicker enddatePicker;
	private final JDatePicker startdatePicker;

	//button
	private final JButton btnSave;

	private final ListPanel parent;

	private Mode currentMode;

	private String curType = "Id";
	
	private Iteration currentIteration;

	/**
	 * Construct the panel
	 */
	public IterationBuilderPanel(ListPanel view) {
		parent = view;
		currentMode = Mode.CREATE;
		currentIteration = null;
		
		//create title
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBorder(BorderFactory.createTitledBorder("Iteration Builder"));

		//construct the panels
		JLabel NameLabel = new JLabel("Name:");
		JLabel startdateLabel = new JLabel("Start Date:");
		JLabel nddateLabel = new JLabel("End Date:");
		btnSave= new JButton("Save");

		//construct the components
		txtValue = new JTextField();
		txtValue.setEnabled(false);

		//set initial conditions
		JDatePicker 
		
		// The action listener for this is below

		btnSave.addActionListener(new SaveModelController(parent.getTabPanel().getIterationList(), this, "Iteration"));
		btnSave.setEnabled(false);

		//set the layout
		setLayout(new GridBagLayout());
		GridBagConstraints IterationBuilderConstraints = new GridBagConstraints();
		IterationBuilderConstraints.anchor= GridBagConstraints.NORTH;

		//adjust location
		btnSave.setAlignmentX(Component.RIGHT_ALIGNMENT);

		//Start Date
		//Set the constraints for the "typeLabel" and add it to the view
		IterationBuilderConstraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		IterationBuilderConstraints.ipady = 0;//This tells the layout to reset the vertical ipad from the previously defined 20 units to now 0 units
		IterationBuilderConstraints.anchor = GridBagConstraints.CENTER; //This sets the anchor of the field, here we have told it to anchor the component to the top center of it's field
		IterationBuilderConstraints.insets = new Insets(0,25,0,0);  //Set the top padding to 10 units  of blank space
		IterationBuilderConstraints.gridx = 0;//Set the x coord of the cell of the layout we are describing
		IterationBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		add(typeLabel, IterationBuilderConstraints);//Actually add the "typenLabel" to the layout given the previous constraints
		//Set the constraints for the "endBox"  and add it to the view
		IterationBuilderConstraints.fill = GridBagConstraints.CENTER;//This sets the constraints of this field so that the item will stretch both horizontally and vertically to fill it's area
		IterationBuilderConstraints.gridx = 1;//Set the x coord of the cell of the layout we are describing
		IterationBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		add(typeBox, IterationBuilderConstraints);//Actually add the "typeBox" to the layout given the previous constraints
		//end Start Date

		//End Date
		//Set the constraints for the "typeLabel" and add it to the view
		IterationBuilderConstraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		IterationBuilderConstraints.ipady = 0;//This tells the layout to reset the vertical ipad from the previously defined 20 units to now 0 units
		IterationBuilderConstraints.anchor = GridBagConstraints.CENTER; //This sets the anchor of the field, here we have told it to anchor the component to the top center of it's field
		IterationBuilderConstraints.insets = new Insets(0,25,0,0);  //Set the top padding to 10 units  of blank space
		IterationBuilderConstraints.gridx = 0;//Set the x coord of the cell of the layout we are describing
		IterationBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		add(typeLabel, IterationBuilderConstraints);//Actually add the "typenLabel" to the layout given the previous constraints
		//Set the constraints for the "endBox"  and add it to the view
		IterationBuilderConstraints.fill = GridBagConstraints.CENTER;//This sets the constraints of this field so that the item will stretch both horizontally and vertically to fill it's area
		IterationBuilderConstraints.gridx = 1;//Set the x coord of the cell of the layout we are describing
		IterationBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		add(typeBox, IterationBuilderConstraints);//Actually add the "typeBox" to the layout given the previous constraints
		//end End Date

		
		//value:
		//Set the constraints for the "value" and add it to the view
		IterationBuilderConstraints.fill = GridBagConstraints.HORIZONTAL;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		IterationBuilderConstraints.anchor = GridBagConstraints.CENTER; //This sets the anchor of the field, here we have told it to anchor the component to the top center of it's field
		IterationBuilderConstraints.insets = new Insets(0,25,0,0);
		IterationBuilderConstraints.gridx = 4;//Set the x coord of the cell of the layout we are describing
		IterationBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		add(valueLabel, IterationBuilderConstraints);//Actually add the "valueLabel" to the layout given the previous constraints
		//Set the constraints for the "value" and add it to the view
		IterationBuilderConstraints.fill = GridBagConstraints.CENTER;//This sets the constraints of this field so that the item will stretch horizontally to fill it's area
		IterationBuilderConstraints.gridx = 5;//Set the x coord of the cell of the layout we are describing
		IterationBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		IterationBuilderConstraints.ipadx=80;
		add(txtValue, IterationBuilderConstraints);//Actually add the "txtValue" to the layout given the previous constraints
		add(valueBox, IterationBuilderConstraints);//Actually add the "valueBox" to the layout given the previous constraints
		//end value

		//Search button:
		//Set the constraints for the "Save" and add it to the view
		IterationBuilderConstraints.fill = GridBagConstraints.NONE;//This sets the constraints of this field so that the item will not stretch to fill it's area
		IterationBuilderConstraints.anchor = GridBagConstraints.CENTER; //This sets the anchor of the field, here we have told it to anchor the component to the bottom right of it's field
		IterationBuilderConstraints.gridx = 8;//Set the x coord of the cell of the layout we are describing
		IterationBuilderConstraints.gridy = 1;//Set the y coord of the cell of the layout we are describing
		btnSave.setPreferredSize(new Dimension (10,30));
		add(btnSave, IterationBuilderConstraints);//Actually add the "Save" to the layout given the previous constraints
		//end Save button

	}
	public JButton getButton()
	{
		return btnSave;
	}
	public JComboBox<String> getIterationType()
	{
		return typeBox;
	}

	public JComboBox<String> getIterationOperator()
	{
		return comparatorBox;
	}

	public JTextField getIterationValue()
	{
		return txtValue;
	}

	public JComboBox<String> getIterationValueBox()
	{
		return valueBox;
	}


	public JComboBox<String> getStatus()
	{
		return userIterationBox;
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
		// TODO Auto-generated method stub
		
	}
	@Override
	public void clearAndReset() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setInputEnabled(boolean setTo) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getModelMessage() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void translateAndDisplayModel(String jsonArray) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void toggleNewCancelMode() {
		// TODO Auto-generated method stub
		
	}
	
}


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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.acceptancetest;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.AcceptanceTest;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.JTextFieldLimit;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementTab;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementTab.Mode;

/** This panel is added to the RequirementTabPanel and 
 * contains all the GUI components involving acceptance tests:
 * -a panel to display the list of acceptance tests (each in their own AcceptanceTestPanel)
 * -a text field for a user to input a new acceptance test name
 * -a text area for a user to input a new acceptance test description
 * -a save button to save the new acceptance test
 */
@SuppressWarnings({"serial"})
public class AcceptanceTestTab extends JPanel implements ActionListener {

	//The labels
	/** The label for the name text field ("txtName") */
	private JLabel nameLabel;
	
	/** The label for the description text area ("txtDescription") */
	private JLabel descriptionLabel;

	//The fillable components
	/** The name text field  */
	private JTextField txtName;
	
	/** The description text area  */
	private JTextArea txtDescription;
	
	/** ScrollPane that the txtDescription box will be held in  */
	private JScrollPane scrollDescription;

	/** The button to add a new test */
	private JButton saveButton;

	/** An inner panel to hold the name label and field, and the description label and the scrollDescription holding the txtDescription area (enables the proper layout of the panel) */
	private JPanel nameAndDescriptionPanel;

	//The variables to hold information about the current instance of the panel
	/** Stores the acceptance test currently undergoing creation in this AcceptanceTestTab*/
	private AcceptanceTest currentAcceptanceTest;

	/** Stores the RequirementPanel that contains the panel */
	private RequirementTab parent; 

	/** A boolean indicating if input is enabled on the form  */
	protected boolean inputEnabled;

	/** The acceptanceTestListModel. This holds the acceptance tests to be displayed in the "acceptanceTestList" panel */
	private AcceptanceTestListModel acceptanceTestListModel;

	/** The panel to hold all the AcceptanceTestPanels (containing all the acceptance tests) to display */
	private ListOfAcceptanceTestPanel acceptanceTestList;

	/** A scroll pane to hold the "acceptanceTestList" */
	JScrollPane listScrollPane;

	/** The layout manager for the nameAndDescriptionPanel */
	protected GridBagLayout layout; 

	/** The constraints variable for the layout of the nameAndDescriptionPanel */
	private GridBagConstraints nameAndDescriptionPanelConstraints;

	/** The constructor for AcceptanceTestTab;
	 * Construct the panel, the components, and add the
	 * components to the panel.
	 * @param reqPanelParent The RequirementTab parent of this AcceptanceTestTab
	 */
	public AcceptanceTestTab(RequirementTab reqPanelParent) {
		parent = reqPanelParent; //Set the RequirementPanel that contains this instance of this panel

		// Create and set the layout manager
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		//Set an empty border for spacing
		setBorder(BorderFactory.createEmptyBorder(5, 3, 3, 3)); 

		// Construct the acceptanceTestListModel
		acceptanceTestListModel = new AcceptanceTestListModel();

		//Add the tests from the parent requirement to the acceptanceTestListModel
		ArrayList<AcceptanceTest> acceptanceTests = parent.getCurrentRequirement().getAcceptanceTests();
		for (int i = 0; i < acceptanceTests.size(); i++) {
			acceptanceTestListModel.addAcceptanceTest(acceptanceTests.get(i));
		}

		//Construct the acceptanceTestList, using the previously defined empty model
		acceptanceTestList  = new ListOfAcceptanceTestPanel(acceptanceTestListModel);		

		// Put the acceptanceTestList in a scroll pane
		listScrollPane = new JScrollPane(acceptanceTestList);

		// Construct the other components to be displayed:
		//Construct the labels
		nameLabel = new JLabel("Title:");
		descriptionLabel = new JLabel("Body:");

		//Construct the fillable components
		txtName = new JTextField("");
		txtDescription = new JTextArea("", 1, 1);

		//Construct the buttons
		saveButton = new JButton("Add Acceptance Test");//Construct the saveButton

		//Set the character limit for the txtName field
		txtName.setDocument(new JTextFieldLimit(100));

		// Add key listener to txtName
		txtName.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {
				if (!txtName.getText().equals("")) {
					parent.getAttributePanel().changeField(txtName, 11, true);
				} else {
					parent.getAttributePanel().changeField(txtName, 11, false);
				}
				setSaveButtonWhenMessageIsValid();//Set the save button enabled if there is something to save, disabled if not
			}
		});

		// Set the txtMessage component to wrap
		txtDescription.setLineWrap(true);
		txtDescription.setWrapStyleWord(true);

		// Put txtDescription in a scroll pane
		scrollDescription = new JScrollPane(txtDescription);

		// Add key listener to txtDescription
		txtDescription.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {
				if (!txtDescription.getText().equals("")) {
					parent.getAttributePanel().changeField(txtDescription, 12, true);
				} else {
					parent.getAttributePanel().changeField(txtDescription, 12, false);
				}
				setSaveButtonWhenMessageIsValid();//Set the save button enabled if there is something to save, disabled if not
			}
		});

		// Set the dimensions of the panel elements
		listScrollPane.setPreferredSize(new Dimension(580, 300));
		scrollDescription.setPreferredSize(new Dimension(1, 100));
		saveButton.setPreferredSize(new Dimension(120,40));

		//Disable the txtMessage and saveButton if this is a new requirement
		if ((parent.getMode()) == Mode.CREATE)
		{
			saveButton.setEnabled(false);
			getTxtName().setEnabled(false);
			getAcceptanceTestDescription().setEnabled(false);
		}
		else
			setSaveButtonWhenMessageIsValid();//Set the save button enabled if there is something to save, disabled if not

		//Construct and Layout the inner panel to hold the name label, name field, description label, and the scrollDescription holding the txtDescription area
		nameAndDescriptionPanel = new JPanel(); //Create the nameAndDescriptionPanel

		//Create and set the layout manager that controls the positions of the components in the nameAndDescriptionPanel
		layout = new GridBagLayout();//Create the layout
		nameAndDescriptionPanel.setLayout(layout); //Set the layout

		//In this section we adjust the size and alignments of the components to be added to the nameAndDescriptionPanel and add them to the nameAndDescriptionPanel.
		//Please read all the comments in this section if you are having trouble understanding what is going on.

		//create the constraints variable for the layout of the nameAndDescriptionPanel
		nameAndDescriptionPanelConstraints = new GridBagConstraints(); 

		//A quick note about constraints:
		//A constraint variable is a single instance of an object that stores constraints,
		//  so if you set variables within it, they persist to the next time it is used.
		//This means you *must* reset variables within the constraint if you do not want
		//  the previously stored value to effect the next component that uses it.

		//Name:
		//Set the constraints for "nameLabel" and add it to the nameAndDescriptionPanel
		nameAndDescriptionPanelConstraints.weightx = 0.07;//This sets the horizontal (x axis) "weight" of the component, which tells the layout how big to make this component in respect to the other components on it's line
		nameAndDescriptionPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_END;//This sets the anchor of the field, here we have told it to anchor the component to the top right of it's field
		nameAndDescriptionPanelConstraints.insets = new Insets(15,5,0,0);  //Set the top padding to 15 units of blank space, left padding to 5 units of space
		nameAndDescriptionPanelConstraints.gridx = 0; //set the x coord of the cell of the layout we are describing
		nameAndDescriptionPanelConstraints.gridy = 0;//set the y coord of the cell of the layout we are describing
		nameAndDescriptionPanel.add(nameLabel, nameAndDescriptionPanelConstraints);//Actually add the "nameLabel" to the layout given the previous constraints
		//Set the constraints for "txtName" and add it to the nameAndDescriptionPanel
		nameAndDescriptionPanelConstraints.weightx = 0.77;
		nameAndDescriptionPanelConstraints.fill = GridBagConstraints.HORIZONTAL;//This tells the layout to stretch this field horizontally to fit it's cell
		nameAndDescriptionPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;//Anchor the component to the top left center of it's field
		nameAndDescriptionPanelConstraints.gridx = 1;
		nameAndDescriptionPanelConstraints.gridy = 0;
		nameAndDescriptionPanel.add(txtName, nameAndDescriptionPanelConstraints);
		//end Name

		//Description:
		//Set the constraints for "descriptionLabel" and add it to the nameAndDescriptionPanel
		nameAndDescriptionPanelConstraints.weightx = 0.07;//This sets the horizontal (x axis) "weight" of the component, which tells the layout how big to make this component in respect to the other components on it's line
		nameAndDescriptionPanelConstraints.fill = GridBagConstraints.NONE;//This tells the layout to not stretch this field horizontally or vertically to fit it's cell
		nameAndDescriptionPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_END;//This sets the anchor of the field, here we have told it to anchor the component to the top right of it's field
		nameAndDescriptionPanelConstraints.insets = new Insets(15,5,0,0);  //Set the top padding to 15 units of blank space, left padding to 5 units of space
		nameAndDescriptionPanelConstraints.gridx = 0; //set the x coord of the cell of the layout we are describing
		nameAndDescriptionPanelConstraints.gridy = 1;//set the y coord of the cell of the layout we are describing
		nameAndDescriptionPanel.add(descriptionLabel, nameAndDescriptionPanelConstraints);//Actually add the "descriptionLabel" to the layout given the previous constraints
		//Set the constraints for the "scrollDescription" JScrollPane containing the "txtDescription" JTextArea and add it to the nameAndDescriptionPanel
		nameAndDescriptionPanelConstraints.weightx = 0.77;
		nameAndDescriptionPanelConstraints.fill = GridBagConstraints.HORIZONTAL;//This tells the layout to stretch this field horizontally to fit it's cell
		nameAndDescriptionPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;//Anchor the component to the top left center of it's field
		nameAndDescriptionPanelConstraints.gridx = 1;
		nameAndDescriptionPanelConstraints.gridy = 1;
		nameAndDescriptionPanel.add(scrollDescription, nameAndDescriptionPanelConstraints);
		//end Description

		nameAndDescriptionPanel.setMaximumSize(new Dimension(1000, 135));//set the size of the nameAndDescriptionPanel to keep it from stretching vertically
		//end nameAndDescriptionPanel

		// Add the components in their respective inner panels to this panel
		this.add(listScrollPane); //add the acceptanceTestList, in the listScrollPane, to the panel
		this.add(Box.createRigidArea(new Dimension(0,6))); //Put some vertical space between these components
		this.add(nameAndDescriptionPanel); // add the nameAndDescriptionPanel to the panel
		this.add(Box.createRigidArea(new Dimension(0,6)));
		this.add(saveButton); // add the saveButton to the panel
		this.add(Box.createRigidArea(new Dimension(0,6)));
		
		saveButton.setAlignmentX(CENTER_ALIGNMENT);
	}

	/** This method sets up the controller for the "saveButton" JButton
	 */
	public void setUp() {
		// Set controller for save button
		Boolean restoreEnableStateBool = saveButton.isEnabled(); //store the enable state of the save button, since adding an action defaults the enable to true
		saveButton.setAction(new SaveAcceptanceTestAction(parent.getParent().getController()));
		saveButton.addActionListener(this);	// Tells fields that a save has occurred
		saveButton.setEnabled(restoreEnableStateBool);//restore the previously stored enable state
	}

	/** Sets whether input is enabled for this panel and its children. This should be used instead of 
	 * JComponent#setEnabled because setEnabled does not affect its children.
	 * 
	 * @param enabled Whether or not input is enabled.
	 */
	protected void setInputEnabled(boolean enabled){
		inputEnabled = enabled;
		saveButton.setEnabled(enabled);
		txtName.setEnabled(enabled);
		txtDescription.setEnabled(enabled);
	}

	/** Returns a boolean representing whether or not input is enabled for the AcceptanceTestPanel.
	 * @return inputEnabled A boolean representing whether or not input is enabled for the AcceptanceTestPanel.
	 */
	public boolean getInputEnabled() {
		return inputEnabled;
	}

	/** Checks the txtDescription and txtName for validity (non-emptiness) and sets the save button appropriately     
	 * @return True if the txtDescription and txtName fields are valid (non-empty), false otherwise
	 */
	public boolean setSaveButtonWhenMessageIsValid(){
		boolean messageGood = true;	// Initialize flag

		if ((txtName.getText().length()==0) || (txtDescription.getText().length()==0) )// Check if the txtDescription and txtName are empty
			messageGood = false;
				
		saveButton.setEnabled( messageGood);//Set the save button enabled/disabled appropriately
		return messageGood;
	}
	
	/** This returns the JTextArea "txtDescription"
	 * @return the txtDescription JTextArea
	 */
	public JTextArea getAcceptanceTestDescription() {
		JViewport viewport = scrollDescription.getViewport(); 
		return (JTextArea) viewport.getView();
	}

	/** This returns the JButton saveButton
	 * @return the saveButton JButton
	 */
	public JButton getSaveButton() {
		return saveButton;
	}

	/** This returns the AcceptanceTest "currentAcceptanceTest" 
	 * @return the currentAcceptanceTest AcceptanceTest
	 */
	public AcceptanceTest getCurrentAcceptanceTest() {
		return currentAcceptanceTest;
	}

	/** This sets the current acceptance test
	 * @param acceptanceTest What to set the current acceptance test to
	 */
	public void setCurrentAcceptanceTest(AcceptanceTest acceptanceTest) {
		currentAcceptanceTest = acceptanceTest;
	}

	/** This returns the ListOfAcceptanceTestPanel that displays the stored acceptance tests,
	 * each in their own acceptanceTestPanel.
	 * @return the acceptanceTestList ListOfAcceptanceTestPanel
	 */
	public ListOfAcceptanceTestPanel getAcceptanceTestList() {
		return acceptanceTestList;
	}
	/** This returns the AcceptanceTestListModel "acceptanceTestListModel",
	 * which stores the saved acceptance tests that are associated with the
	 * current requirement being displayed
	 * @return the acceptanceTestListModel AcceptanceTestListModel
	 */
	public AcceptanceTestListModel getAcceptanceTestListModel() {
		return acceptanceTestListModel;
	}

	/** This adds a new acceptance test to the acceptanceTestListModel,
	 * and then recreates and redisplays the acceptanceTestList
	 * panel.
	 * @param newAcceptanceTest the acceptance test to be added
	 */
	public void addAcceptanceTestToList(AcceptanceTest newAcceptanceTest){
		this.removeAll();
		acceptanceTestListModel.addAcceptanceTest(newAcceptanceTest);
		acceptanceTestList  = new ListOfAcceptanceTestPanel(acceptanceTestListModel);

		listScrollPane = new JScrollPane(acceptanceTestList);

		listScrollPane.setPreferredSize(new Dimension(580, 300));		

		// Add components
		this.add(listScrollPane); //add the acceptanceTestList, in the listScrollPane, to the panel
		this.add(Box.createRigidArea(new Dimension(0,6))); //Put some vertical space between these components
		this.add(nameAndDescriptionPanel); // add the nameAndDescriptionPanel to the panel
		this.add(Box.createRigidArea(new Dimension(0,6)));
		this.add(saveButton); // add the saveButton to the panel
		this.add(Box.createRigidArea(new Dimension(0,6)));
	}

	/**
	 * @return txtName The "txtName" JTextField
	 */
	public JTextField getTxtName() {
		return txtName;
	}

	/**
	 * @param txtName The "txtName" (an instance of JTextField) to set
	 */
	public void setTxtName(JTextField txtName) {
		this.txtName = txtName;
	}

	/** Tell fields that they should not have changes
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		parent.getAttributePanel().changeField(txtDescription, 12, false);
		parent.getAttributePanel().changeField(txtName, 11, false);
	}

	/**
	 * @return parent The RequirementTab that contains this AcceptanceTestTab
	 */
	public RequirementTab myGetParent() {
		return parent;
	}
}

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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementType;

/**
 * This class is a JPanel. 
 * 
 * It contains:
 * -a boolean indicating if input is enabled on the form
 * -a Requirement storing the requirement currently open for editing or creation
 * -a RequirementView storing the RequirementView that contains the panel
 * -an enum to indicate a "create" or "edit" mode
 * -an inner JPanel containing:
 * 		-a text field for entering a new name,
 * 		-a text area for entering a description,
 * 		-a combo box for entering a status,
 * 		-a combo box for entering a priority,
 * 		-a JNumber text field for entering a release number,
 * 		-a JNumber text field for entering an estimate,
 * 		-a JNumber text field for entering an actual effort
 * 		-Associated labels for each component
 * -Layout managers for both the inner panel (a GridBagLayout) and this panel (a SpringLayout)
 * -A constraints variable to store the constraints for the inner GridBagLayout
 * 
 * This class also contains getters and setters for many of the components and variables listed above
 *
 */
@SuppressWarnings({"serial","rawtypes"})
public class RequirementTab extends JPanel {

	//An enum to store the mode of the current instance of this panel
	public enum Mode {
		CREATE,//When Mode is this value, we are creating a new requirement
		EDIT//When Mode is this value, we are editing an existing requirement
	}

	/** layout for the whole panel */
	protected JSplitPane splitPane;
	/** Panel containing requirement attribute panel  */
	protected JPanel rightPanel;
	/** Panel containing note tabs */
	protected JScrollPane leftPanel;

	//The variables to hold information about the current instance of the panel
	private Requirement currentRequirement;//Stores the requirement currently open for editing or creation
	private RequirementView parent; //Stores the RequirementView that contains the panel
	protected boolean inputEnabled;//A boolean indicating if input is enabled on the form 
	private Mode mode;// The variable to store the enum indicating whether or not you are creating at the time

	//The inner panels
	private RequirementAttributePanel attributePanel;//A JPanel to hold all the components. This allows for alignment of the components as a group
	private RequirementTabPanel tabPanel; //Notes, etc

	//The layout manager
	protected SpringLayout layout; //The layout for the RequirementPanel (this holds the innerPanel)
	protected ScrollPaneLayout leftLayout; //The layout for the LeftPanel (inner left panel)
	protected SpringLayout rightLayout; //The layout for the RightPanel (inner right panel)

	/**
	 * The constructor for RequirementPanel;
	 * Construct the panel, the components, and add the
	 * components to the panel.
	 * @param view	The parent RequirementView that contains this panel
	 * @param requirement	The requirement that is currently open for editing or creation.
	 * @param editMode	The mode of the current panel. This is "CREATE" when we are creating a new requirement, and "EDIT" when we are editing an existing requirement.
	 */
	public RequirementTab(RequirementView view, Requirement requirement, Mode editMode) {

		parent = view;//Set the RequirementView that contains this instance of this panel
		currentRequirement = requirement; //Set the requirement that is currently open for editing or creation
		mode = editMode;//Set the indicated mode

		// Indicate that input is enabled
		inputEnabled = true;
		
		// Set the layout manager of this panel
		this.layout = new SpringLayout();
		this.setLayout(layout);
		
		//Instantiate the panels
		splitPane = new JSplitPane();
		
		tabPanel = new RequirementTabPanel(this);
		attributePanel = new RequirementAttributePanel(this,requirement, mode);//Construct the innerPanel
		
		leftPanel = new JScrollPane(attributePanel);
		leftPanel.setMinimumSize(new Dimension (605, 500));
		rightPanel = new JPanel();
		rightPanel.setMinimumSize(new Dimension(370, 500));

		this.splitPane.setOneTouchExpandable(false);
		this.splitPane.setDividerLocation(650);
		this.splitPane.setContinuousLayout(true);

		// Construct the layout manager and add constraints
		this.rightLayout = new SpringLayout();
		rightPanel.setLayout(rightLayout);
	/*	this.leftLayout = new ScrollPaneLayout();
		leftPanel.setLayout(leftLayout);*/
		
		

		// Constrain the tabPanel
		rightLayout.putConstraint(SpringLayout.NORTH, tabPanel, 0, SpringLayout.NORTH, rightPanel);
		rightLayout.putConstraint(SpringLayout.WEST, tabPanel, 10, SpringLayout.WEST, rightPanel);
		rightLayout.putConstraint(SpringLayout.EAST, tabPanel, 0, SpringLayout.EAST, rightPanel);
		rightLayout.putConstraint(SpringLayout.SOUTH, tabPanel, 0, SpringLayout.SOUTH, rightPanel);

		/*// Constrain the attributePanel
		leftLayout.putConstraint(SpringLayout.NORTH, attributePanel, 0, SpringLayout.NORTH, leftPanel);
		leftLayout.putConstraint(SpringLayout.WEST, attributePanel, 0, SpringLayout.WEST, leftPanel);
		leftLayout.putConstraint(SpringLayout.EAST, attributePanel, 0, SpringLayout.EAST, leftPanel);
		leftLayout.putConstraint(SpringLayout.SOUTH, attributePanel, 0, SpringLayout.SOUTH, leftPanel);
*/
		// Constrain the splitPane
		layout.putConstraint(SpringLayout.NORTH, splitPane, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, splitPane, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, splitPane, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, splitPane, 0, SpringLayout.SOUTH, this);


		addComponents();//Add the panels, enabling/disabling fields appropriately

	}

	/**
	 * Adds the panels ("attributePanel" and "tabPanel"),
	 * enabling/disabling fields as indicated by the mode variable
	 */
	protected void addComponents(){

		//leftPanel.add(attributePanel);//Add the attributePanel to this panel

		rightPanel.add(tabPanel);//Add the tabPanel to this panel
		if (mode == Mode.CREATE) {
			//Enables the fields upon creation
			setInputEnabled(inputEnabled);

			//Disables the notePanel upon creation
			toggleEnabled(tabPanel.getNotePanel().getNoteMessage(), false);
			tabPanel.getNotePanel().getSaveButton().setEnabled(false);

			tabPanel.getNotePanel().setEnabled(false);

			//Disables the appropriate fields in the attributePanel upon creation
			attributePanel.toggleCreationDisable();

		}

		splitPane.setLeftComponent(leftPanel);
		splitPane.setRightComponent(rightPanel);
		this.add(splitPane);
	}

	/**
	 * Sets whether input is enabled for this panel and its children. This should be used instead of 
	 * JComponent#setEnabled because setEnabled does not affect its children.
	 * 
	 * @param enabled Whether or not input is enabled.
	 */
	protected void setInputEnabled(boolean enabled){
		inputEnabled = enabled;

		attributePanel.setInputEnabled(enabled);

		// toggles note panel boxes
		toggleEnabled(tabPanel.getNotePanel().getNoteMessage(), enabled);
		tabPanel.getNotePanel().getSaveButton().setEnabled(enabled);
		
	}

	/**
	 * Updates the RequirementPanel's model ("currentRequirement") to contain the values of the given Requirement and sets the 
	 * RequirementPanel's "mode" to {@link Mode#EDIT}.
	 * 
	 * @param requirement	The Requirement which contains the new values for the model ("currentRequirement").
	 */
	protected void updateModel(Requirement requirement) {
		updateModel(requirement, Mode.EDIT);
	}

	/**
	 * Updates the RequirementPanel's model ("currentRequirement") to contain the values of the given Requirement.
	 * 
	 * @param requirement	The Requirement which contains the new values for the model ("currentRequirement").
	 * @param newMode		The new "mode"
	 */
	protected void updateModel(Requirement requirement, Mode newMode){
		mode = newMode;

		if (mode == Mode.EDIT)
			setInputEnabled(true);

		attributePanel.updateModel(requirement, newMode);

		tabPanel.revalidate();
		tabPanel.repaint();

		this.revalidate();
		this.repaint();

		parent.refreshScrollPane();
	}


	/**
	 * Enables or disables a given JComponent and sets is color accordingly
	 * 
	 * @param box Box to be enabled / disabled
	 * @param enabled True for enable, false for disable
	 */
	public void toggleEnabled(JComponent box, boolean enabled) {
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
	 * Returns a boolean representing whether or not input is enabled for the RequirementPanel.
	 * @return the inputEnabled boolean 	A boolean representing whether or not input is enabled for the RequirementPanel.
	 */
	public boolean getInputEnabled() {
		return inputEnabled;
	}

	/**
	 * This returns the JTextField "txtName"
	 * @return the txtName JTextField
	 */
	public JTextField getRequirementName() {
		return attributePanel.getRequirementName();
	}

	/**
	 * This returns the JTextArea "txtDescription"
	 * @return the txtDescription JTextArea
	 */
	public JTextArea getRequirementDescription() {
		return attributePanel.getRequirementDescription();
	}


	/**
	 * This returns the JComboBox "typeBox"
	 * @return the typeBox JComboBox
	 */
	public JComboBox getRequirementType() {
		return attributePanel.getRequirementType();
	}

	/**
	 * This returns the JComboBox "statusBox"
	 * @return the statusBox JComboBox
	 */
	public JComboBox getRequirementStatus() {
		return attributePanel.getRequirementStatus();
	}

	/**
	 * This returns the JComboBox "priorityBox"
	 * @return the priorityBox JComboBox
	 */
	public JComboBox getRequirementPriority() {
		return attributePanel.getRequirementPriority();
	}

	/**
	 * This returns the JTextField "txtReleaseNum"
	 * @return the txtReleaseNum JTextField
	 */
	public JTextField getRequirementReleaseNumber() {
		return attributePanel.getRequirementReleaseNumber();
	}

	/**
	 * This returns the JTextField "txtEstimate"
	 * @return the txtEstimate JTextField
	 */
	public JTextField getRequirementEstimate() {
		return attributePanel.getRequirementEstimate();
	}

	/**
	 * This returns the JTextField "txtActualEffort"
	 * @return the txtActualEffort JTextField
	 */
	public JTextField getRequirementActualEffort() {
		return attributePanel.getRequirementActualEffort();
	}
	/**
	 * This returns the JTextArea "txtNote"
	 * @return the txtNote JTextArea
	 */
	public JTextArea getRequirementNote() {
		return tabPanel.getNotePanel().getNoteMessage();
	}

	public RequirementTabPanel getTabPanel() {
		return tabPanel;
	}
	/**
	 * Gets a requirement from the current text fields
	 * TODO error check
	 * 
	 * @return Requiremnt made from the text fields
	 */
	public Requirement getRequirement()
	{
		// get the fields from the UI
		String name = this.getRequirementName().getText();
		String description = this.getRequirementDescription().getText();
		int releaseNumber = Integer.parseInt((this.getRequirementReleaseNumber().getText().equals("")) ? "-1" : (this.getRequirementReleaseNumber().getText()));
		RequirementPriority priority = RequirementPriority.toPriority(this.getRequirementPriority().getSelectedItem().toString());
		RequirementType type = RequirementType.toType(this.getRequirementType().getSelectedItem().toString());

		return new Requirement(name, description, type, priority,  releaseNumber, 0);

	}

	/**
	 * This returns the "mode" of this panel (Mode.EDIT or Mode.CREATE)
	 * @return the mode Mode
	 */
	public Mode getMode() {
		return mode;
	}

	/**
	 * This sets the Mode "mode" of this panel
	 * @param m Mode to set the current mode to (Mode.EDIT or Mode.CREATE)
	 */
	public void setMode(Mode m) {
		attributePanel.setMode(m);
		mode = m;
	}

	/**
	 * This returns the Requirement "currentRequirement" 
	 * @return the currentRequirement Requirement
	 */
	public Requirement getCurrentRequirement() {
		return currentRequirement;
	}

	/**
	 * This sets the Requirement "currentRequirement" 
	 * @param currentRequirement Requirement to set the currentRequirement to
	 */
	public void setCurrentRequirement(Requirement currentRequirement) {
		attributePanel.setCurrentRequirement(currentRequirement);
		this.currentRequirement = currentRequirement;
	}

	/**
	 * This returns the "parent" (a RequirementView) of this panel
	 * @return the parent RequirementView
	 */
	public RequirementView getParent() {
		return parent;
	}

	/**
	 * @return the iterationBox
	 */
	public JComboBox getIterationBox() {
		return attributePanel.getIterationBox();
	}

	/**
	 * @param iterationBox the iterationBox to set
	 */
	public void setIterationBox(JComboBox iterationBox) {
		attributePanel.setIterationBox(iterationBox);
	}
	
	/**
	 * get the iteration names in
	 */
	public void getIterationName(){
		attributePanel.fillIterationSelectionBox();
	}
	
	/** Get the requirement attribute panel
	 * 
	 * @return a RequirementAttributePanel
	 */
	public RequirementAttributePanel getAttributePanel(){
		return attributePanel;
	}
	
	/** Gets the iterations for the current project.
	 * 
	 * @return All of the iterations for the current project
	 */
	public Iteration[] getAllIterations(){
		return ((ListView) this.getParent().getTabController().getView().getComponentAt(0)).getAllIterations();
		
	}

}
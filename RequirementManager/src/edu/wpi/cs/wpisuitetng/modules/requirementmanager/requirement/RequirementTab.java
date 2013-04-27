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
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.AcceptanceTest;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementType;

/** This class is a JPanel that holds all the GUI components for editing or creating a Requirement.
 * It contains:
 * -a boolean indicating if input is enabled on the form
 * -a Requirement storing the requirement currently open for editing or creation
 * -a RequirementView storing the RequirementView that contains the panel
 * -an enum to indicate a "create" or "edit" mode
 * -a RequirementAttributePanel called "attributePanel" containing:
 * 		-a text field for entering a new name,
 * 		-a text area for entering a description,
 * 		-a combo box for entering a status,
 * 		-a combo box for entering a priority,
 * 		-a JNumber text field for entering a release number,
 * 		-a JNumber text field for entering an estimate,
 * 		-a JNumber text field for entering an actual effort
 * 		-Associated labels for each component
 * -a RequirementTabPanel ("tabPanel") holding the tabs that hold the GUIs used for Requirement Notes (a NoteTab), 
 * 		Events (a HistoryTab), Assigning Users (a UserChooserTab), Acceptance Tests (a AcceptanceTestTab),
 * 		and Attachments (a AttachementTab)
 * -a left JScrollPane ("leftPanel")  to hold the "attributePanel" to enable positioning/scrolling of the attribute panel as a whole
 * -a right JPanel ("rightPanel") to hold the "tabPanel" to enable positioning of the tab panel as a whole
 * -A JSplitPane ("splitPane") to hold the leftPanel and rightPanel to enable the resizing of both panels individually
 * -Layout managers for both the attribute panel (a ScrollPaneLayout), the tab panel (a SpringLayout), and  this panel (a SpringLayout)
 * -A constraints variable to store the constraints for the attribute panel SpringLayout
 */
@SuppressWarnings({"serial","rawtypes"})
public class RequirementTab extends JPanel {

	/** An enum to store the mode of the current instance of this panel */
	public enum Mode {
		/** When Mode is this value, we are creating a new requirement */
		CREATE,
		/** When Mode is this value, we are editing an existing requirement */
		EDIT
	}

	/** JSplitPane to hold the two halves of the panel */
	protected JSplitPane splitPane;
	
	/** Panel containing requirement attribute panel */
	protected JPanel rightPanel;
	
	/** Panel containing tabs */
	protected JScrollPane leftPanel;

	//The variables to hold information about the current instance of the panel
	/** Stores the requirement currently open for editing or creation */
	private Requirement currentRequirement;
	
	/** Stores the RequirementView that contains this RequirementTab panel */
	private RequirementView parent;
	
	/** A boolean indicating if input is enabled on the form */
	protected boolean inputEnabled;
	
	/** The variable to store the enum indicating whether or not you are creating at the time */
	private Mode mode; 

	//The attribute and tab panels
	/** A JPanel to hold all the components for recording/displaying requirement attributes. This allows for alignment of the components as a group */
	private RequirementAttributePanel attributePanel;
	
	/** A tabbed pane that holds the tabs that hold the GUIs for Requirement Notes (NoteTab), Events (HistoryTab), Assigning Users (UserChooserTab), Acceptance Tests (AcceptanceTestTab), and Attachments (AttachementTab) */
	private RequirementTabPanel tabPanel; 

	//The layout managers
	/** The layout for the RequirementTab overall (this holds the JSplitPane "splitPane") */
	protected SpringLayout layout; 
	
	/** The layout for the LeftPanel (the left "attributePanel") */
	protected ScrollPaneLayout leftLayout; 
	
	/** The layout for the RightPanel (the right "tabPanel") */
	protected SpringLayout rightLayout; 

	/** The constructor for RequirementTab;
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
		layout = new SpringLayout();
		this.setLayout(layout);
		
		//Instantiate the panels and splitPane
		splitPane = new JSplitPane();
		tabPanel = new RequirementTabPanel(this);
		attributePanel = new RequirementAttributePanel(this,requirement, mode);//Construct the attributePanel
		
		//Instantiate and set the left and right panels with minimum sizes
		leftPanel = new JScrollPane(attributePanel);
		leftPanel.setMinimumSize(new Dimension (605, 500));
		rightPanel = new JPanel();
		rightPanel.setMinimumSize(new Dimension(370, 500));

		splitPane.setOneTouchExpandable(false);
		splitPane.setDividerLocation(650);
		splitPane.setContinuousLayout(true);

		// Construct the layout manager for the right
		rightLayout = new SpringLayout();
		rightPanel.setLayout(rightLayout);

		// Constrain the tabPanel
		rightLayout.putConstraint(SpringLayout.NORTH, tabPanel, 0, SpringLayout.NORTH, rightPanel);
		rightLayout.putConstraint(SpringLayout.WEST, tabPanel, 10, SpringLayout.WEST, rightPanel);
		rightLayout.putConstraint(SpringLayout.EAST, tabPanel, 0, SpringLayout.EAST, rightPanel);
		rightLayout.putConstraint(SpringLayout.SOUTH, tabPanel, 0, SpringLayout.SOUTH, rightPanel);

		// Constrain the splitPane
		layout.putConstraint(SpringLayout.NORTH, splitPane, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, splitPane, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, splitPane, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, splitPane, 0, SpringLayout.SOUTH, this);

		addComponents();//Add the panels, enabling/disabling fields appropriately
	}

	/** Adds the panels ("attributePanel" and "tabPanel"),
	 * enabling/disabling fields appropriately as indicated by the mode variable
	 */
	protected void addComponents(){

		rightPanel.add(tabPanel);//Add the tabPanel to this panel
		if (mode == Mode.CREATE) {
			//Enables the fields upon creation
			setInputEnabled(inputEnabled);

			//Disables the notePanel upon creation
			toggleEnabled(tabPanel.getNotePanel().getNoteMessage(), false);
			tabPanel.getNotePanel().getSaveButton().setEnabled(false);
			tabPanel.getNotePanel().setEnabled(false);
			
			//Disables the userChooserTab upon creation
			tabPanel.getUserChooserPanel().setInputEnabled(false);

			//Disables the acceptanceTestPanel upon creation
			toggleEnabled(tabPanel.getAcceptanceTestPanel().getAcceptanceTestDescription(), false);
			toggleEnabled(tabPanel.getAcceptanceTestPanel().getTxtName(), false);
			tabPanel.getAcceptanceTestPanel().getSaveButton().setEnabled(false);
			tabPanel.getAcceptanceTestPanel().setEnabled(false);
			
			//Disables the appropriate fields in the attributePanel upon creation
			attributePanel.disableFieldsOnCreation();

		}
		
		//If deleted, we can't add notes or tests, and we can't add/remove users
		if (currentRequirement.getStatus() == RequirementStatus.Deleted) {
			this.toggleEnabled(tabPanel.getNotePanel().getNoteMessage(), false);
			tabPanel.getNotePanel().getSaveButton().setEnabled(false);
			tabPanel.getNotePanel().setEnabled(false);
			
			toggleEnabled(tabPanel.getAcceptanceTestPanel().getAcceptanceTestDescription(), false);
			toggleEnabled(tabPanel.getAcceptanceTestPanel().getTxtName(), false);
			tabPanel.getAcceptanceTestPanel().getSaveButton().setEnabled(false);
			tabPanel.getAcceptanceTestPanel().setEnabled(false);
			tabPanel.getUserChooserPanel().setInputEnabled(false);
		}

		// add the panels to the splitPane and add the splitPane to the RequirementTab
		splitPane.setLeftComponent(leftPanel);
		splitPane.setRightComponent(rightPanel);
		this.add(splitPane);
	}

	/** Sets whether input is enabled for this panel and its children. This should be used instead of 
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

		//Toggle acceptance tests boxes
		toggleEnabled(tabPanel.getAcceptanceTestPanel().getAcceptanceTestDescription(), enabled);
		toggleEnabled(tabPanel.getAcceptanceTestPanel().getTxtName(), enabled);
		tabPanel.getAcceptanceTestPanel().getSaveButton().setEnabled(enabled);
		tabPanel.getAcceptanceTestPanel().setEnabled(enabled);

		//toggles the UserChooserTab
		tabPanel.getUserChooserPanel().setInputEnabled(enabled);
	}

	/** Updates the RequirementTab's model ("currentRequirement") to contain the values of the given Requirement.
	 * 
	 * @param requirement	The Requirement which contains the new values for the model ("currentRequirement").
	 * @param newMode		The new "mode"
	 */
	protected void updateModel(Requirement requirement, Mode newMode){
		mode = newMode;

		if (mode == Mode.EDIT)
			setInputEnabled(true);

		attributePanel.updateModel(requirement, newMode);

		//Revalidate and repaint to ensure changes are shown
		tabPanel.revalidate();
		tabPanel.repaint();

		this.revalidate();
		this.repaint();

		parent.refreshScrollPane();
	}

	/** Enables or disables a given JComponent and sets is color accordingly
	 * 
	 * @param box JComponent to be enabled / disabled
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

	/** Returns a boolean representing whether or not input is enabled for the RequirementTab.
	 * @return the inputEnabled boolean 	A boolean representing whether or not input is enabled for the RequirementTab.
	 */
	public boolean getInputEnabled() {
		return inputEnabled;
	}

	/** This returns the JTextField "txtName"
	 * @return the txtName JTextField
	 */
	public JTextField getRequirementName() {
		return attributePanel.getRequirementName();
	}

	/** This returns the JTextArea "txtDescription"
	 * @return the txtDescription JTextArea
	 */
	public JTextArea getRequirementDescription() {
		return attributePanel.getRequirementDescription();
	}


	/** This returns the JComboBox "typeBox"
	 * @return the typeBox JComboBox
	 */
	public JComboBox getRequirementType() {
		return attributePanel.getRequirementType();
	}

	/** This returns the JComboBox "statusBox"
	 * @return the statusBox JComboBox
	 */
	public JComboBox getRequirementStatus() {
		return attributePanel.getRequirementStatus();
	}

	/** This returns the JComboBox "priorityBox"
	 * @return the priorityBox JComboBox
	 */
	public JComboBox getRequirementPriority() {
		return attributePanel.getRequirementPriority();
	}

	/** This returns the JTextField "txtReleaseNum"
	 * @return the txtReleaseNum JTextField
	 */
	public JTextField getRequirementReleaseNumber() {
		return attributePanel.getRequirementReleaseNumber();
	}

	/** This returns the JTextField "txtEstimate"
	 * @return the txtEstimate JTextField
	 */
	public JTextField getRequirementEstimate() {
		return attributePanel.getRequirementEstimate();
	}

	/** This returns the JTextField "txtActualEffort"
	 * @return the txtActualEffort JTextField
	 */
	public JTextField getRequirementActualEffort() {
		return attributePanel.getRequirementActualEffort();
	}
	
	/** This returns the JTextArea "txtNote" from the NotePanel (an instance of NoteTab)
	 * @return the txtNote JTextArea
	 */
	public JTextArea getRequirementNote() {
		return tabPanel.getNotePanel().getNoteMessage();
	}

	/** This returns the AcceptanceTest currently under construction in the Acceptance Test tab
	 * @return the AcceptanceTest currently under construction in the Acceptance Test tab
	 */
	public AcceptanceTest getRequirementAcceptanceTest() {
		AcceptanceTest newTest = new AcceptanceTest(tabPanel.getAcceptanceTestPanel().getTxtName().getText(), tabPanel.getAcceptanceTestPanel().getAcceptanceTestDescription().getText());
		return newTest;
	}
	
	/** This returns the RequirementTabPanel "tabPanel"; 
	 * A RequirementTabPanel that holds the tabs that hold the GUIs for 
	 * Requirement Notes (NoteTab), Events (HistoryTab), 
	 * Assigning Users (UserChooserTab), 
	 * Acceptance Tests (AcceptanceTestTab), 
	 * and Attachments (AttachementTab).
	 * 
	 * @return tabPanel The RequirementTabPanel "tabPanel"
	 */
	public RequirementTabPanel getTabPanel() {
		return tabPanel;
	}
	
	/** Creates and returns a requirement from the information entered into the input components/fields at the time this method is invoked.
	 * TODO error check
	 * 
	 * @return Requirement made from the information entered into the input components/fields at the time this method is invoked.
	 */
	public Requirement getRequirement()
	{
		// get the fields from the UI
		String name = this.getRequirementName().getText();
		String description = this.getRequirementDescription().getText();
		String releaseNumber = this.getRequirementReleaseNumber().getText();
		RequirementPriority priority = RequirementPriority.toPriority(this.getRequirementPriority().getSelectedItem().toString());
		RequirementType type = RequirementType.toType(this.getRequirementType().getSelectedItem().toString());

		return new Requirement(name, description, type, priority,  releaseNumber, 0);
	}

	/** This returns the "mode" of this panel (Mode.EDIT or Mode.CREATE)
	 * @return the mode Mode
	 */
	public Mode getMode() {
		return mode;
	}

	/** This sets the Mode "mode" of this panel
	 * @param m Mode to set the current mode to (Mode.EDIT or Mode.CREATE)
	 */
	public void setMode(Mode m) {
		attributePanel.setMode(m);
		mode = m;
	}

	/** This returns the Requirement "currentRequirement" 
	 * @return the currentRequirement Requirement
	 */
	public Requirement getCurrentRequirement() {
		return currentRequirement;
	}

	/** This sets the Requirement "currentRequirement" 
	 * @param currentRequirement Requirement to set the currentRequirement to
	 */
	public void setCurrentRequirement(Requirement currentRequirement) {
		attributePanel.setCurrentRequirement(currentRequirement);
		this.currentRequirement = currentRequirement;
	}

	/** This returns the "parent" (a RequirementView) of this panel
	 * @return the parent RequirementView
	 */
	public RequirementView getParent() {
		return parent;
	}

	/** This returns the "iterationBox" JComboBox
	 * @return iterationBox The JComboBox "iterationBox"
	 */
	public JComboBox getIterationBox() {
		return attributePanel.getIterationBox();
	}

	/** This sets the "iterationBox" JComboBox
	 * @param iterationBox The iterationBox to set
	 */
	public void setIterationBox(JComboBox iterationBox) {
		attributePanel.setIterationBox(iterationBox);
	}
	
	/** This fills the iterationBox with the appropriate iteration names
	 * by calling "fillIterationSelectionBox"
	 */
	public void getIterationName(){
		attributePanel.fillIterationSelectionBox();
	}
	
	/** This returns the requirement attribute panel ("attributePanel")
	 * @return attributePanel The RequirementAttributePanel "attributePanel"
	 */
	public RequirementAttributePanel getAttributePanel(){
		return attributePanel;
	}
	
	/** This returns an array of all the iterations of the current project.
	 * @return All of the iterations for the current project, in an array of Iteration
	 */
	public Iteration[] getAllIterations(){
		return ((ListView) this.getParent().getTabController().getView().getComponentAt(0)).getAllIterations();
		
	}

}
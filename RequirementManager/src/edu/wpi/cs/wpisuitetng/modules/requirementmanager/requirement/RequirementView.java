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

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.IToolbarGroupProvider;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementTab.Mode;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.DummyTab;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.Tab;

/**
 * This view is responsible for showing the form for creating or viewing a new requirement.
 */
@SuppressWarnings("serial")
public class RequirementView extends JPanel implements IToolbarGroupProvider {

	private ToolbarGroupView buttonGroup;
	private JButton saveButton;
	private RequirementTab mainPanel;
	private SaveRequirementController controller;
	final JScrollPane mainPanelScrollPane;
	private Tab containingTab;
	private boolean inputEnabled = true;
	private MainTabController tabController;
	protected Requirement[] subRequirements = {new Requirement()};
	protected Requirement[] displayedRequirements;

	/**
	 * Constructs a new CreateRequirementView where the user can enter the data for a new requirement.
	 */
	public RequirementView(MainTabController tabController) {
		this(new Requirement(), Mode.CREATE, null, tabController);
		
	}

	/**
	 * Constructs a new RequirementView where the user can view (and edit) a requirement.
	 * 
	 * @param requirement	The requirement to show.
	 * @param editMode	The editMode for editing the Requirement
	 * @param tab		The Tab holding this RequirementView (can be null)
	 */
	public RequirementView(Requirement requirement, Mode editMode, Tab tab, MainTabController tabController) {
		this.tabController = tabController;
		
		containingTab = tab;
		if(containingTab == null) {
			containingTab = new DummyTab();
		}
		
		// Instantiate the button panel
		buttonGroup = new ToolbarGroupView("Create Requirement");
		
		containingTab.setIcon(new ImageIcon());
		if(editMode == Mode.CREATE) {
			containingTab.setTitle("Create Requirement");
			containingTab.setToolTipText("Create a new requirement");
		} else {
			setEditModeDescriptors(requirement);
		}
		

		// Instantiate the main create requirement panel
		RequirementTab requirementTab = new RequirementTab(this,requirement, editMode);
		mainPanel = requirementTab;
		this.setLayout(new BorderLayout());
		mainPanelScrollPane = new JScrollPane(mainPanel);
		mainPanelScrollPane.getVerticalScrollBar().setUnitIncrement(10);
		
		// Prevent content of scroll pane from smearing (credit: https://gist.github.com/303464)
		mainPanelScrollPane.getVerticalScrollBar().addAdjustmentListener(new java.awt.event.AdjustmentListener(){
			public void adjustmentValueChanged(java.awt.event.AdjustmentEvent ae){
				SwingUtilities.invokeLater(new Runnable(){
					public void run(){
						mainPanelScrollPane.repaint();
					}
				});
			}
		});
		
		
		this.add(mainPanelScrollPane, BorderLayout.CENTER);
		controller = new SaveRequirementController(this);
		mainPanel.getTabPanel().getNotePanel().setUp();
		mainPanel.getTabPanel().getAcceptanceTestPanel().setUp();
		mainPanel.getTabPanel().getAttachmentPanel().setUp();
		mainPanel.getIterationName();

		// Instantiate the save button and add it to the button panel
		saveButton = new JButton();
		saveButton.setEnabled(false);
		saveButton.setAction(new SaveChangesAction(controller));
		buttonGroup.getContent().add(saveButton);
		buttonGroup.setPreferredWidth(150);
		
		requirementTab.getAttributePanel().setSaveButton(saveButton);
		requirementTab.getAttributePanel().setSaveButtonWhenNameAndDescriptionAreValid();
		requirementTab.getAttributePanel().setupControllersAndListeners();
	}
	
	/**
	 * Sets whether input is enabled for this panel and its children. This should be used instead of 
	 * JComponent#setEnabled because setEnabled does not affect its children.
	 * 
	 * @param enabled	Whether or not input is enabled.
	 */
	public void setInputEnabled(boolean enabled) {
		inputEnabled = enabled;
	}
	
	public void setsaveEnabled(boolean enabled) {

		saveButton.setEnabled(enabled);
	}
	
	/**
	 * Returns whether or not input is enabled.
	 * 
	 * @return whether or not input is enabled.
	 */
	public boolean getInputEnabled() {
		return inputEnabled;
	}

	/**
	 * Returns the main panel with the data fields
	 * 
	 * @return the main panel with the data fields
	 */
	public RequirementTab getRequirementPanel() {
		return mainPanel;
	}
	
	/**
	 * Returns the tab panel with the data fields
	 * 
	 * @return the tab panel with the data fields
	 */
	public RequirementTabPanel getRequirementTabPanel() {
		return mainPanel.getTabPanel();
	}
	
	@Override
	public ToolbarGroupView getGroup() {
		return buttonGroup;
	}
	
	/**
	 * @param requirement Set the tab title, tooltip, and group name according to this Requirement
	 */
	protected void setEditModeDescriptors(Requirement requirement) {
		containingTab.setTitle("Requirement #" + requirement.getId());
		containingTab.setToolTipText("View requirement " + requirement.getName());
		buttonGroup.setName("Edit Requirement");
	}
	
	/**
	 * Scrolls the scroll pane containing the main panel to the bottom
	 */
	public void scrollToBottom() {
		JScrollBar vBar = mainPanelScrollPane.getVerticalScrollBar();
		vBar.setValue(vBar.getMaximum());
	}

	/**
	 * Revalidates and repaints the scroll pane containing the RequirementPanel
	 */
	public void refreshScrollPane() {
		mainPanelScrollPane.revalidate();
		mainPanelScrollPane.repaint();
	}
	
	public SaveRequirementController getController() {
		return controller;
	}

	/**
	 * @return the tabController
	 */
	public MainTabController getTabController() {
		return tabController;
	}
	
	/**
	 * @return the tabController
	 */
	public void setSaveButtonEnable(boolean isEnabled) {
		saveButton.setEnabled(isEnabled);
	}

	public void setSubRequirements(Requirement[] requirements) {
	
		this.subRequirements = requirements;
		
	}
	public void setDisplayedRequirements(Requirement[] displayedRequirements) {
		this.displayedRequirements = displayedRequirements;
	}
	
}

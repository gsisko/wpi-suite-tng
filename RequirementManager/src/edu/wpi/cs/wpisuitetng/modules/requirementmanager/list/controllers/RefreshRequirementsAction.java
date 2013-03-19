package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;


/**
 * Action that calls {@link RetrieveAllRequirementsController#refreshData()}, default mnemonic key is R
 */
@SuppressWarnings("serial")
public class RefreshRequirementsAction extends AbstractAction {
	
	/** The controller to be called when this action is performed */
	protected final RetrieveAllRequirementsController controller;
	
	/**
	 * Construct a RefreshRequirementsAction
	 * @param controller when the action is performed this controller's refreshData() method will be called
	 */
	public RefreshRequirementsAction(RetrieveAllRequirementsController controller) {
		super("Refresh");
		this.controller = controller;
		putValue(MNEMONIC_KEY, KeyEvent.VK_R);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		controller.refreshData();
	}

}

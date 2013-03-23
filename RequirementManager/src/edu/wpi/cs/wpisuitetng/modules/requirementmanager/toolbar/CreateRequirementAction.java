

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.toolbar;

/**
 * Contributors:
 * AHurle
 * JPage
 */

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabController;

/**
 * Action that calls {@link MainTabController#addCreateRequirementTab()}, default mnemonic key is C. 
 */
@SuppressWarnings("serial")
public class CreateRequirementAction extends AbstractAction {

	private final MainTabController controller;
	
	/**
	 * Create a CreateRequirementAction
	 * @param controller When the action is performed, controller.addCreateRequirementTab() is called
	 */
	public CreateRequirementAction(MainTabController controller) {
		super("Create Requirement");
		this.controller = controller;
		putValue(MNEMONIC_KEY, KeyEvent.VK_C);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		controller.addCreateRequirementTab();
	}

}

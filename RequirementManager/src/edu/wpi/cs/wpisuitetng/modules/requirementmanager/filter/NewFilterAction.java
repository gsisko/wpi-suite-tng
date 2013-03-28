package edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.FilterBuilderPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.FilterListPanel;

public class NewFilterAction implements ActionListener {
	/** The button to watch */
	JButton filterListPanelButton;
	/** The panel with the button to watch button to watch */
	FilterListPanel inPanel;
	/** THE filter builder panel that will be set when this button is pressed*/
	FilterBuilderPanel  builder;
	
	
	public NewFilterAction(FilterListPanel inPanel, FilterBuilderPanel builder) {
		this.inPanel = inPanel;
		filterListPanelButton = inPanel.getBtnCreate();
		this.builder = builder;
	}
	
	/** Constructor to be used by "CancelFilterAction" - completes a loop between 
	 * the filters
	 * 
	 * @param theButton The button to watch
	 * @param builder   The filter builder panel to do work on
	 */
	public NewFilterAction(JButton theButton, FilterBuilderPanel builder){
		this.filterListPanelButton = theButton;
		this.builder = builder;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Changes the button label
		filterListPanelButton.setText("Cancel");  
		// Sets a new action listener to the button: Allows for cancel
		filterListPanelButton.addActionListener(new CancelFilterAction(filterListPanelButton, builder));
		
		builder.getFilterType().setEnabled(true);
		builder.getFilterOperator().setEnabled(true);
		builder.getStatus().setEnabled(true);
		builder.getFilterValue().setEnabled(true);
		builder.getButton().setText("Create");
		builder.getButton().setEnabled(true);

	}

}

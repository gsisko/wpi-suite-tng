package edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.FilterBuilderPanel;

public class CancelFilterAction implements ActionListener{
	/** The button to watch */
	JButton filterListPanelButton;
	/** THE filter builder panel that will be reset when this button is pressed*/
	FilterBuilderPanel  builder;
	
	
	public CancelFilterAction(JButton cancelButton, FilterBuilderPanel builder) {
		filterListPanelButton = cancelButton;
		this.builder = builder;
	}

	/** When pressed, the information in the filter builder panel
	 *  is reset and the fields are greyed out.
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		filterListPanelButton.setText("New Filter");
		filterListPanelButton.addActionListener(new NewFilterAction(filterListPanelButton, builder));
		
		builder.getFilterType().setEnabled(false);
		builder.getFilterOperator().setEnabled(false);
		builder.getStatus().setEnabled(false);
		builder.getFilterValue().setEnabled(false);
		builder.getFilterValue().setText(" ");
		builder.getButton().setText("Create");
		builder.getButton().setEnabled(false);
		
		
		
		
	}

}

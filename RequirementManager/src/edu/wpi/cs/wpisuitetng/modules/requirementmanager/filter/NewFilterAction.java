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
	
 
	/** When pressed, the information in the filter builder panel
     *  is reset and the fields are grayed out.
	 * 
	 * @param theButton The button to watch
	 * @param builder   The filter builder panel to do work on
	 */
	public NewFilterAction(FilterListPanel thePanel, FilterBuilderPanel builder){
					this.inPanel = thePanel;

		this.filterListPanelButton = inPanel.getBtnCreate();
		this.builder = builder;		
		inPanel.setBtnCreateIsCancel(false);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		    if (inPanel.isBtnCreateIsCancel()){
			      builder.getFilterType().setEnabled(false);
			      builder.getFilterOperator().setEnabled(false);
		      builder.getStatus().setEnabled(false);
			      builder.getFilterValue().setEnabled(false);
			      builder.getFilterValue().setText(" ");
			      builder.getButton().setText("Create");
			      builder.getButton().setEnabled(false);
			      filterListPanelButton.setText("New Filter");  
			    } else {
			      builder.getFilterType().setEnabled(true);
			      builder.getFilterOperator().setEnabled(true);
			      builder.getStatus().setEnabled(true);
			      builder.getFilterValue().setEnabled(true);
			      builder.getButton().setText("Create");
			      builder.getButton().setEnabled(true);
			      filterListPanelButton.setText("Cancel");  
			    }
			    inPanel.setBtnCreateIsCancel(!inPanel.isBtnCreateIsCancel());
	}

}

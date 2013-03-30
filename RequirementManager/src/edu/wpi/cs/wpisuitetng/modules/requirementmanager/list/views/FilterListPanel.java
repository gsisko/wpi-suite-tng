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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter.DeleteModelController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter.IListBuilder;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter.NewModelAction;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter.RetrieveAllModelsController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter.RetrieveModelController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.Filter;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.ResultsTableModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.FilterBuilderPanel.Mode;

/**
 * Panel to contain the list of filters that have been saved by the user
 */
@SuppressWarnings("serial")
public class FilterListPanel extends JPanel implements IListBuilder{
	
	/** The table of results */
	protected JTable resultsTable;
	
	protected JButton btnCreate;
	protected JButton btnDelete;
	private boolean btnCreateIsCancel;
	
	private Filter[] localFilters = {};
	
	/** The model containing the data to be displayed in the results table */
	protected ResultsTableModel resultsTableModel;
	
	private DeleteModelController deleteController;
	private RetrieveModelController retrieveController;
	private RetrieveAllModelsController retrieveAllController;
	
	private final ListPanel parent;
	/**
	 * Construct the panel
	 */

	public FilterListPanel(ListPanel view) {
		parent = view;
		this.setBtnCreateIsCancel(false);
		// Set the layout manager
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		// Construct the table model
		resultsTableModel = new ResultsTableModel();

		// Construct the table and configure it
		resultsTable = new JTable(resultsTableModel);
		resultsTable.setAutoCreateRowSorter(true);
		resultsTable.setFillsViewportHeight(true);
		resultsTable.setDefaultRenderer(Date.class, new DateTableCellRenderer());

		// Put the table in a scroll pane
		JScrollPane resultsScrollPane = new JScrollPane(resultsTable);
		resultsScrollPane.setPreferredSize(new Dimension(175,250));
		this.add(resultsScrollPane);
		resultsScrollPane.setAlignmentX(CENTER_ALIGNMENT);
		
		// TODO implement the rest of the controls to display saved filters
		// and store saved filters in the ConfigManager
		
		this.add(Box.createRigidArea(new Dimension(0,6)));
		
		btnCreate = new JButton ("New Filter");
		btnDelete = new JButton ("Delete");
		
		btnCreate.setMaximumSize(new Dimension(120, 40));
		btnCreate.setMinimumSize(new Dimension(120, 40));
		btnDelete.setMaximumSize(new Dimension(120, 40));
		btnDelete.setMinimumSize(new Dimension(120, 40));
		
		this.add(btnCreate);
		this.add(Box.createRigidArea(new Dimension(0,6)));
		this.add(btnDelete);
		this.add(Box.createRigidArea(new Dimension(0,6)));
		btnCreate.setAlignmentX(CENTER_ALIGNMENT);
		btnDelete.setAlignmentX(CENTER_ALIGNMENT);

		deleteController = new DeleteModelController(this, parent.getBuilderPanel(),"filter");
		retrieveController = new RetrieveModelController(this, parent.getBuilderPanel(),"filter");
		setRetrieveAllController(new RetrieveAllModelsController(this, parent.getBuilderPanel(),"filter"));
		
		// Add a listener for row clicks
		resultsTable.addMouseListener(retrieveController);
				
		// Sets up listener system. Once pressed, changes to CancelFilterAction listener, then back to this.
		btnCreate.addActionListener(new NewModelAction(this, parent.getBuilderPanel()));
		
		btnDelete.addActionListener(deleteController);
	}
	
	/**This method returns an ArrayList of active filters
	 * 
	 * @return activeFilters An ArrayList of the active filters
	 */
	public ArrayList<Filter> getActiveFilters() {
		ArrayList<Filter> activeFilters = new ArrayList<Filter>();
	    
	    for(int i = 0; i < localFilters.length; i++){
	    	if(localFilters[i].isUseFilter()) activeFilters.add(localFilters[i]);
	    }
		
		return activeFilters;
	}
	
	/**
	 * @return the data model for the table
	 */
	public ResultsTableModel getModel() {
		return resultsTableModel;
	}
	
	/**
	 * @return the results table
	 */
	public JTable getResultsTable() {
		return resultsTable;
	}
	
	/**
	 * Replace the results table with the given table
	 * @param newTable the new results table
	 */
	public void setResultsTable(JTable newTable) {
		resultsTable = newTable;
	}

	/**
	 * @return the parent
	 */
	public ListPanel getParent() {
		return parent;
	}

	/**
	 * @return the localFilters
	 */
	public Filter[] getLocalFilters() {
		return localFilters;
	}

	/**
	 * @param localFilters the localFilters to set
	 */
	public void setLocalFilters(Filter[] localFilters) {
		this.localFilters = localFilters;
	}
	
	public JButton getBtnCreate(){
		return btnCreate;
	}

	/**
	 * @return the btnCreateIsCancel
	 */
	public boolean isBtnCreateIsCancel() {
		return btnCreateIsCancel;
	}

	/**
	 * @param btnCreateIsCancel the btnCreateIsCancel to set
	 */
	public void setBtnCreateIsCancel(boolean btnCreateIsCancel) {
		this.btnCreateIsCancel = btnCreateIsCancel;
	}

	@Override
	public String[] getUniqueIdentifiers() {
		
		JTable filters = parent.getFilterPanel().getResultsTable();
		
		// get highlighted rows 
		int[] rowNumbers = filters.getSelectedRows();
		
		String[] ids = new String [rowNumbers.length];
		for(int i=0; i<rowNumbers.length;i++){
			ids[i] = (String) filters.getValueAt(rowNumbers[i], 0);
		}
		return ids;
	}

	@Override
	public void clearAndReset() {
		// Remove anything in the filter builder panel whenever the delete button is pressed
		FilterBuilderPanel resetBuilderFields = parent.getBuilderPanel();
		resetBuilderFields.setInputEnabled(false);
		resetBuilderFields.setCurrentMode(Mode.CREATE);

		// Set the cancel button back to New Filter if it was in cancel mode 
		FilterListPanel resetListCancelButtons = parent.getFilterPanel();
		resetListCancelButtons.getBtnCreate().setText("New Filter"); 
		resetListCancelButtons.setBtnCreateIsCancel(false);
		
	}

	@Override
	public void setCancelBtnToNew() {
		// Set the cancel button back to New Filter if it was in cancel mode 
		FilterListPanel resetListCancelButtons = parent.getFilterPanel();
		resetListCancelButtons.getBtnCreate().setText("New Filter"); 
		resetListCancelButtons.setBtnCreateIsCancel(false);
		
	}

	@Override
	public boolean refreshAll() {
		// TODO make this work....
		return false;
	}

	@Override
	public String getModelMessage() {
		// TODO check later
		return null;
	}

	@Override
	public void toggleNewCancalMode() {
		FilterListPanel resetListCancelButtons = parent.getFilterPanel();
		if(resetListCancelButtons.isBtnCreateIsCancel())
			resetListCancelButtons.getBtnCreate().setText("New Filter");
		else
			resetListCancelButtons.getBtnCreate().setText("Cancel"); 
		resetListCancelButtons.setBtnCreateIsCancel(!resetListCancelButtons.isBtnCreateIsCancel());
	}

	@Override
	public void translateAndDisplayModel(String jsonArray) {
		// TODO no

	}

	@Override
	public String getSelectedUniqueIdentifier(MouseEvent me) {
		// TODO Auto-generated method stub
		
		JTable filters = parent.getFilterPanel().getResultsTable();
		
		int row = filters.rowAtPoint(me.getPoint());

		String filterId=null;
		// make sure the user actually clicked on a row
		if (row > -1) {
			filterId = (String) resultsTable.getValueAt(row, 0);
		}
		
		return filterId;
	}

	@Override
	public void showRecievedModels(String jsonString) {
		// TODO Auto-generated method stub
		// empty the table
		String[] emptyColumns = {};
		Object[][] emptyData = {};
		
		Filter[] filters = Filter.fromJSONArray(jsonString);
		
		this.getModel().setColumnNames(emptyColumns);
		this.getModel().setData(emptyData);
		this.getModel().fireTableStructureChanged();
		
		// Add the list of filters to the FilterListPanel object
		this.setLocalFilters(filters);
		
		if (filters.length > 0) {
			
			// set the column names
			String[] columnNames = {"Id", "Type", "Op", "Value", "Active"};
			
			// put the data in the table
			Object[][] entries = new Object[filters.length][columnNames.length];
			for (int i = 0; i < filters.length; i++) {
				entries[i][0] = String.valueOf(filters[i].getUniqueID());
				entries[i][1] = filters[i].getType().toString();
				if (filters[i].getComparator().toString().equals("Contains")) {
					entries[i][2] = "c";
				} else if (filters[i].getComparator().toString().equals("DoesNotContain")) {
					entries[i][2] = "!c";
				} else {
					entries[i][2] = filters[i].getComparator().toString();
				}
				entries[i][3] = filters[i].getValue();
				if (filters[i].isUseFilter()) {
					entries[i][4] = "yes";
				} else {
					entries[i][4] = "no";
				}
			}
			
			// fill the table
			this.getModel().setColumnNames(columnNames);
			this.getModel().setData(entries);
			this.getModel().fireTableStructureChanged();
		}
		else {
			// do nothing, there are no filters
		}
	}

	/**
	 * @return the retrieveAllController
	 */
	public RetrieveAllModelsController getRetrieveAllController() {
		return retrieveAllController;
	}

	/**
	 * @param retrieveAllController the retrieveAllController to set
	 */
	public void setRetrieveAllController(RetrieveAllModelsController retrieveAllController) {
		this.retrieveAllController = retrieveAllController;
	}
}


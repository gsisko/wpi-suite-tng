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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

import edu.wpi.cs.wpisuitetng.janeway.gui.widgets.KeyboardShortcut;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.IEditableListPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.ListSaveModelController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.ResultsTableModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.SaveIterationObserver;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.views.JNumberTextField;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/** Panel to hold the results of a list of requirements
 */
@SuppressWarnings({"serial","unchecked","rawtypes"})
public class RequirementListPanel extends JPanel implements IEditableListPanel {

	/** The table of results */
	protected JTable resultsTable;

	/** The model containing the data to be displayed in the results table */
	protected ResultsTableModel resultsTableModel;

	/** The main tab controller */
	protected final MainTabController tabController;

	/** Array of Boolean flags for whether or not Requirements need saving */
	private Boolean[][] needsSaving;

	/** Array of Boolean flags for whether or not cells are valid */
	private Boolean[][] isValid;

	/** Array of Boolean flags for whether or not the cells are editable */
	private Boolean[][] isEditable;

	/** Boolean for whether or not the table is in edit mode */
	private boolean inEditMode;

	/** Count of refirings for estimate and iteration updates */
	private int fireCount;

	/** ArrayList of listeners on resultsTable column heads */
	private ArrayList<MouseListener> columnHeadListeners;

	/** The SaveListModelController that does the saving */
	private ListSaveModelController listSaveModelController;

	/** the parent that holds this panel */
	ListTab parent;

	/**Construct the panel
	 * @param tabController The main tab controller
	 */
	public RequirementListPanel(MainTabController tabController, final ListTab parent) {
		this.tabController = tabController;
		columnHeadListeners = new ArrayList<MouseListener>();
		fireCount = 0;

		this.parent = parent;
		// Set the layout
		this.setLayout(new BorderLayout());

		// Construct the table model
		resultsTableModel = new ResultsTableModel();

		// Construct the table and configure it
		resultsTable = new JTable(resultsTableModel);
		resultsTable.setAutoCreateRowSorter(true);
		resultsTable.setFillsViewportHeight(true);
		resultsTable.setDefaultRenderer(String.class, new ResultsTableCellRenderer(null, null, null));


		//		resultsTable.addKeyListener( new KeyAdapter(   ){
		//
		//			@SuppressWarnings("deprecation")
		//			public void keyPressed(KeyEvent e){
		//				// VK_ENTER
		//				if (e.getKeyCode() == KeyEvent.VK_ENTER ){
		//					System.err.println("SHIT HAPPENED");
		//					e.setModifiers(KeyEvent.VK_SHIFT);
		//				//	e.notifyAll();
		//					super.keyPressed(e);
		//					//	new KeyEvent(KeyEvent.VK_ENTER + KeyEvent.VK_SHIFT);
		//
		//				}				
		//			}		
		//		});


		//	resultsTable.addKeyListener(new KeyboardShortcut(KeyStroke.getKeyStroke("return"), new AbstractAction))

		//	resultsTable.putValue(MNEMONIC_KEY, KeyEvent.VK_ENTER); // probably wrong?

		resultsTable.getModel().addTableModelListener(new TableModelListener() {
			//	resultsTable.addKeyboardShortcut(new KeyboardShortcut(KeyStroke.getKeyStroke("control TAB"), new AbstractAction() {


			@Override
			public void tableChanged(TableModelEvent e) {
				if (resultsTableModel.isEditable()) {

					Requirement[] reqs = parent.getParent().getDisplayedRequirements();
					int row = e.getFirstRow();
					int modelRow = resultsTable.convertRowIndexToModel(row);

					Requirement requirement = null;
					for (int i = 0; i < reqs.length; i++) {
						if ((reqs[i].getId() + "").equals(resultsTableModel.getValueAt(row, getOriginalColumnIndex("ID")))) {
							requirement = reqs[i];
						}
					}

					int column = e.getColumn();
					String columnName = resultsTableModel.getColumnName(column);
					Object data = resultsTableModel.getValueAt(row, column);
					boolean isChanged = false;
					boolean isInvalid = false;

					Requirement currentRequirement = getCurrentRequirement(row);

					if (columnName.equals("Name")) {
						String name = (String) data;
						if (!requirement.getName().equals(name)) {
							isChanged = true;
						}
						if ((name).equals("") || name.length() > 100 )
							isInvalid = true;
					}

					else if (columnName.equals("Iteration")) {
						String iteration = (String) data;
						int statusColumn = getColumnIndex("Status");
						int estimateColumn = getColumnIndex("Estimate");
						int originalEstimateColumn = getOriginalColumnIndex("Estimate");
						if (!getIterationName(requirement.getIteration()).equals(iteration)) {
							isChanged = true;

						}

						if (iteration.equals("")) {
							if (requirement.getStatus() == RequirementStatus.New)
								resultsTable.setValueAt("New", modelRow, statusColumn);
							else
								resultsTable.setValueAt("Open", modelRow, statusColumn);
						} else {
							resultsTable.setValueAt("InProgress", modelRow, statusColumn);
						}

						isEditable[modelRow][estimateColumn] = iteration.equals("");
						// This is for making sure the un-editable settings fire correctly
						if (fireCount++ < 1)
							resultsTableModel.setValueAt(resultsTableModel.getValueAt(modelRow, originalEstimateColumn), modelRow, originalEstimateColumn);
						else 
							fireCount = 0;
					}

					else if (columnName.equals("Type")) {
						isChanged = requirement.getType() != RequirementType.toType((String)data);					
					}

					else if (columnName.equals("Status")) {
						String status = (String)data;
						if (requirement.getStatus() != RequirementStatus.valueOf(status)){
							isChanged = true;

							if (status.equals("New") && requirement.getStatus() != RequirementStatus.New) {
								isInvalid = true;
							} else if (status.equals("InProgress") || status.equals("Complete")) {
								isInvalid = currentRequirement.getIteration() == 0;// Set the iteration to invalid because there is no iteration
							}
						}
					}

					else if (columnName.equals("Priority")) {
						if (requirement.getPriority() != RequirementPriority.toPriority((String)data)) {
							isChanged = true;
						}
					}

					else if (columnName.equals("ReleaseNumber")) {
						if (!requirement.getReleaseNumber().equals((String)data)) {
							isChanged = true;
						}
					}

					else if (columnName.equals("Estimate")) {
						int iterationColumn = getColumnIndex("Iteration");
						int originalIterationColumn = getOriginalColumnIndex("Iteration");
						String estimate = (String) data;
						if (estimate.equals("")){
							isInvalid = true;
							isEditable[modelRow][iterationColumn] = false;						
						} else {
							int estimateVal = Integer.parseInt(estimate);
							isEditable[row][iterationColumn] = (estimateVal > 0);
							isChanged = requirement.getEstimate() != estimateVal;
						} 
						// This is for making sure the un-editable settings fire correctly
						if (fireCount++ < 1)
							resultsTableModel.setValueAt(resultsTableModel.getValueAt(row, originalIterationColumn), row, originalIterationColumn);
						else 
							fireCount = 0;			
					}

					else if (columnName.equals("ActualEffort")) {
						String actualEffort = (String) data;
						if (actualEffort.equals("")){
							isInvalid = true;
						} else {
							isChanged = requirement.getActualEffort() != Integer.parseInt((String)data);
						}
					}

					else {
						System.err.println("Table change listener failed fatally");
						return;
					}

					needsSaving[row][getColumnIndex(resultsTableModel.getColumnName(column))] = isChanged;
					isValid[row][getColumnIndex(resultsTableModel.getColumnName(column))] = !isInvalid;
					parent.getEditModeBuilderPanel().setInvalidInputMessages(prepareErrorMessages());

					updateSaveButton();

					Boolean[][] originalNeedsSaving = new Boolean[resultsTable.getRowCount()][resultsTable.getColumnCount()];
					for (int i = 0; i < resultsTable.getRowCount(); i++)
						for (int j = 0; j < resultsTable.getColumnCount(); j++)
							originalNeedsSaving[i][j] = needsSaving[resultsTable.convertRowIndexToModel(i)][j];

					Boolean[][] originalIsValid = new Boolean[resultsTable.getRowCount()][resultsTable.getColumnCount()];
					for (int i = 0; i < resultsTable.getRowCount(); i++)
						for (int j = 0; j < resultsTable.getColumnCount(); j++)
							originalIsValid[i][j] = isValid[resultsTable.convertRowIndexToModel(i)][j];

					resultsTable.setDefaultRenderer(String.class, new ResultsTableCellRenderer(originalNeedsSaving, originalIsValid, isEditable));
					Boolean[][] originalIsEditable = new Boolean[resultsTable.getRowCount()][resultsTable.getColumnCount()];
					for (int i = 0; i < resultsTable.getRowCount(); i++)
						for (int j = 0; j < resultsTable.getColumnCount(); j++)
							originalIsEditable[resultsTable.convertRowIndexToModel(i)][j] = isEditable[i][getColumnIndex(resultsTableModel.getColumnName(j))];
					getModel().setIsEditable(originalIsEditable);
				}
			}

		});


		// Put the table in a scroll pane
		JScrollPane resultsScrollPane = new JScrollPane(resultsTable);

		this.add(resultsScrollPane, BorderLayout.CENTER);

		// Setup controller
		listSaveModelController = new ListSaveModelController(this, "requirement");
	}

	/** Prints the error messags to console for testing */
	@SuppressWarnings("unused")
	private void printMessages(String[] messages){
		for (String toPrint: messages)
			System.out.println(toPrint);		
	}


	/** Prepares an array of 5 strings that represent the following errors
	 *  or invalid cell states. 
	 * 
	 * @return an array of strings representing error states
	 */
	private String[] prepareErrorMessages(){
		// Initialize the string to be all null strings
		String[] messages = {"","",""};

		// These variables represent indices in the array of messages
		final int blankField    = 0;
		final int bigName       = 1;		

		// Save the column indices for later use
		int estimateColumn      = this.getColumnIndex("Estimate");
		int actualEffortColumn  = this.getColumnIndex("ActualEffort");
		int nameColumn          = this.getColumnIndex("Name");
		int originalNameColumn  = this.getOriginalColumnIndex("Name");



		// Check for validity and disables the save button if there is invalidity
		for (int column =0; column < resultsTable.getColumnCount(); column++){
			for (int row = 0;  row < resultsTable.getRowCount(); row ++  ){
				if (!isValid[row][column].booleanValue()){
					// Different errors depending on the column

					if (column ==  estimateColumn){
						// If the message isn't set at all, set it
						if (messages[blankField].equals("") )
							messages[blankField] = "The following fields cannot be blank: Estimate";
						// If the message is partially set, but doesn't have name, add name
						else if (!messages[blankField].contains("Estimate") )
							messages[blankField] += ", Estimate";	


					} else if (column == actualEffortColumn){
						// If the message isn't set at all, set it
						if (messages[blankField].equals("") )
							messages[blankField] = "The following fields cannot be blank: ActualEffort";
						// If the message is partially set, but doesn't have name, add name
						else if (!messages[blankField].contains("ActualEffort") )
							messages[blankField] += ", ActualEffort";

					} else if (column == nameColumn){
						String name = (String) resultsTableModel.getValueAt(row,originalNameColumn);
						if (name.equals("")){
							// If the message isn't set at all, set it
							if (messages[blankField].equals("") ){
								messages[blankField] = "The following fields cannot be blank: Name";
								// If the message is partially set, but doesn't have name, add name
							} else if (!messages[blankField].contains("Name") ){
								messages[blankField] += ", Name";
							} 	
						} else {
							messages[bigName] = "The name cannot exceed 100 characters.";
						}

					} else { 
						System.err.println("Unknown invalid case.");
					}						
				}
			}			
		}
		int numMessags = 0, i =0 ;
		int[] nums = {63,25,10000,100000,8,8};
		for (String check: messages){
			if (check.length() > nums[i]){
				numMessags++;
			}
			i++; 
		}
		if (numMessags == 2){
			messages[0] ="";
			messages[1]= "";
			messages[2] = "You took too many shots. Go home, you're drunk.";
		}

		return messages;
	}


	/**
	 * @return the main tab controller
	 */
	public MainTabController getTabController() {
		return tabController;
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

	/** Replace the results table with the given table
	 * @param newTable the new results table
	 */
	public void setResultsTable(JTable newTable) {
		resultsTable = newTable;
	}


	/** 
	 * place comboBox for type
	 */
	public void setComboxforType()
	{
		int typeColumnNum = this.getColumnIndex("Type", getTableName());

		TableColumn typeColumn = resultsTable.getColumnModel().getColumn(typeColumnNum);

		JComboBox typebox = new JComboBox();
		typebox.addItem("");
		typebox.addItem("Epic");
		typebox.addItem("Theme");
		typebox.addItem("UserStory");
		typebox.addItem("NonFunctional");
		typebox.addItem("Scenario");
		typebox.setBackground(Color.white);
		typeColumn.setCellEditor(new DefaultCellEditor(typebox));
	}

	/** 
	 * place comboBox for iteration
	 */
	public void setComboxforIteration()
	{
		int typeColumnNum = this.getColumnIndex("Iteration");

		TableColumn typeColumn = resultsTable.getColumnModel().getColumn(typeColumnNum);
		JComboBox typebox = new JComboBox();


		Iteration[] allIterations = parent.getParent().getAllIterations();
		for (Iteration anIter: allIterations){
			// Add the iteration to the list if backlog or not closed
			if( anIter.getEndDate().after(new Date())  || anIter.getName().equals("") ) {  
				typebox.addItem(anIter.getName());
			}
		}
		typebox.setBackground(Color.white);
		typeColumn.setCellEditor(new DefaultCellEditor(typebox));
	}


	/** 
	 * place comboBox for type
	 */
	public void setComboxforStatus()
	{
		int statusColumn = this.getColumnIndex("Status", getTableName());

		TableColumn typeColumn = resultsTable.getColumnModel().getColumn(statusColumn);

		JComboBox typebox = new JComboBox();

		typebox.addItem("New");
		typebox.addItem("Open");
		typebox.addItem("InProgress");
		typebox.addItem("Complete");
		typebox.addItem("Deleted");
		typebox.setBackground(Color.white);
		typeColumn.setCellEditor(new DefaultCellEditor(typebox) {

			@Override
			public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)  {
				Requirement[] reqs = parent.getParent().getDisplayedRequirements();
				Requirement requirement = null;
				for (Requirement r : reqs) {
					if ((r.getId() + "").equals(resultsTableModel.getValueAt(row, getOriginalColumnIndex("ID"))))
						requirement = r;	
				}
				Requirement currentRequirement = getCurrentRequirement(row);

				if (currentRequirement.getIteration() == 0 && requirement.getStatus() == RequirementStatus.New)
					editorComponent = new JComboBox(RequirementStatus.getAvailableStatuses(requirement.getStatus()));
				else if (currentRequirement.getIteration() == 0)
					editorComponent = new JComboBox(RequirementStatus.getAvailableStatuses(RequirementStatus.Open));
				else {
					editorComponent = new JComboBox(RequirementStatus.getAvailableStatuses(RequirementStatus.InProgress));
				}
				editorComponent.setBackground(Color.white);
//				editorComponent.addKeyListener( new KeyAdapter(   ){
//
//					public void keyPressed(KeyEvent e){
//
//						if (e.getKeyCode() == KeyEvent.VK_ENTER ){
//							SwingUtilities.invokeLater(new Runnable() {
//								@Override
//								public void run() {
//									try {
//										Robot robot = new Robot(); 
//										robot.keyPress(KeyEvent.VK_ENTER | KeyEvent.VK_SHIFT);
//									} catch (AWTException enter){
//										System.err.println("My robot died");
//									}
//								}	
//							});
//						}
//					}		
//				});
				return editorComponent;
			}

			@Override
			public Object getCellEditorValue() {
				return ((JComboBox)editorComponent).getSelectedItem();
			}

		});


	}

	/** 
	 * place comboBox for type
	 */
	public void setComboxforPriority()
	{
		int priorityColumn = this.getColumnIndex("Priority", getTableName());

		TableColumn typeColumn = resultsTable.getColumnModel().getColumn(priorityColumn);

		JComboBox typebox = new JComboBox();
		typebox.addItem("");
		typebox.addItem("Low");
		typebox.addItem("Medium");
		typebox.addItem("High");
		typebox.setBackground(Color.white);
		typeColumn.setCellEditor(new DefaultCellEditor(typebox));

	}

	/** 
	 * place numberBox for type
	 */
	public void setNumberBoxForEstimate()
	{
		int estimateColumn = this.getColumnIndex("Estimate", getTableName());

		TableColumn typeColumn = resultsTable.getColumnModel().getColumn(estimateColumn);

		JNumberTextField estimateBox = new JNumberTextField();
		estimateBox.setAllowNegative(false);

		typeColumn.setCellEditor(new DefaultCellEditor(estimateBox));

	}

	/** 
	 * place numberBox for type
	 */
	public void setNumberBoxForActualEffort()
	{
		int actualEffortColumn = this.getColumnIndex("ActualEffort", getTableName());

		TableColumn typeColumn = resultsTable.getColumnModel().getColumn(actualEffortColumn);

		JNumberTextField actualEffortBox = new JNumberTextField();
		actualEffortBox.setAllowNegative(false);

		typeColumn.setCellEditor(new DefaultCellEditor(actualEffortBox));

	}

	/** Disables the sorting of the JTable */
	public void disableSorting(){
		columnHeadListeners.clear();
		// Removes mouse listeners from table header
		for(MouseListener listener : resultsTable.getTableHeader().getMouseListeners()) {
			columnHeadListeners.add(listener);
			resultsTable.getTableHeader().removeMouseListener(listener);
		}
		resultsTable.getTableHeader().setResizingAllowed(false); // Disables column resizing
		resultsTable.getTableHeader().setReorderingAllowed(false); // Disables column ordering
	}

	/** Enables the sorting of the JTable */
	public void enableSorting() {
		// Adds mouse listeners back to table header
		for (MouseListener listener : columnHeadListeners) {
			resultsTable.getTableHeader().addMouseListener(listener);
		}
		resultsTable.getTableHeader().setResizingAllowed(true); // Allows columns to be resized
		resultsTable.getTableHeader().setReorderingAllowed(true); // Allows columns to be reordered
	}

	/** Gets the array of boolean flags of what models
	 *  need saving
	 * 
	 * @return a Boolean array of what models need saving
	 */
	public Boolean[][] getNeedsSaveFlags() {
		return needsSaving;
	}

	/** Gets the JSOn version of the model at 
	 *  the given index
	 *  
	 * @param rowNumber The index of the model
	 * @return  The JSON version of the model
	 */
	public String getModelAsJson(int rowNumber) {
		// Get the names of the columns
		ArrayList<String> columnNames = this.getTableName();

		// Get the ID of the requirement being edited
		String id = (String) resultsTable.getValueAt(rowNumber, this.getColumnIndex("ID", columnNames));

		// Get the original version of the requirement
		Requirement[] reqs = parent.getParent().getDisplayedRequirements();
		Requirement toUpdate = null;
		for (Requirement aReq: reqs){
			if (Integer.toString(aReq.getId()).equals(id) ){
				toUpdate = aReq;
			}
		}

		// Check to see if the retrieve worked
		if (toUpdate == null){
			System.err.print("Fatal Save Problem: Unable to get the model.");
			return "";
		}

		// Get the iteration and status and estimate for special cases
		RequirementStatus updatedStatus = RequirementStatus.toStatus((String) resultsTable.getValueAt(rowNumber, this.getColumnIndex("Status", columnNames)));
		int updatedEstimate = Integer.parseInt( (String) resultsTable.getValueAt(rowNumber, this.getColumnIndex("Estimate", columnNames)));
		int updatedAssignedIterationID;
		if (updatedStatus == RequirementStatus.Deleted || RequirementStatus.Open == updatedStatus){
			updatedAssignedIterationID = 0;
		} else {	
			updatedAssignedIterationID = this.getIterationID((String) resultsTable.getValueAt(rowNumber, this.getColumnIndex("Iteration", columnNames)));
		}

		//If we changed the assigned iteration or estimate... no reason to spam the server otherwise
		//This should reduce the number of requests the server gets sent
		if (updatedAssignedIterationID != toUpdate.getIteration() || updatedEstimate != toUpdate.getEstimate()){
			//!!! Assuming Iteration will be set above !!!

			/** Update oldIteration */
			Iteration oldIteration = null;

			for (Iteration i : parent.getParent().getAllIterations()) {
				if (toUpdate.getIteration() == i.getID()) {
					oldIteration = i;
				}
			}

			//Update totalEstimate
			oldIteration.setTotalEstimate(oldIteration.getTotalEstimate() - toUpdate.getEstimate());

			//Remove id from the list
			ArrayList<Integer> requirementList = oldIteration.getRequirementsContained();
			if(requirementList.size() != 0){ //Only update if there are requirements saved...
				requirementList.remove((Integer)toUpdate.getId());
			}
			oldIteration.setRequirementsContained(requirementList);

			//Save the oldIteration on the server. There is no observer because we don't care about the responses
			Request saveOldIterationRequest = Network.getInstance().makeRequest("requirementmanager/iteration", HttpMethod.POST);
			saveOldIterationRequest.setBody(oldIteration.toJSON());
			saveOldIterationRequest.send();

			/** Update updatedIteration*/
			Iteration updatedIteration = null;

			for (Iteration i : parent.getParent().getAllIterations()) {
				if (  updatedAssignedIterationID  ==  i.getID()) {
					updatedIteration = i;
				}
			}

			//Add id to the list
			ArrayList<Integer> updatedRequirementList = updatedIteration.getRequirementsContained();
			updatedRequirementList.add((Integer)toUpdate.getId());
			updatedIteration.setRequirementsContained(updatedRequirementList);

			//Update totalEstimate
			updatedIteration.setTotalEstimate(updatedIteration.getTotalEstimate() + updatedEstimate);

			//Save the updatedIteration on the server. There is no observer because we don't care about the responses
			Request saveUpdatedIterationRequest = Network.getInstance().makeRequest("requirementmanager/iteration", HttpMethod.POST);
			saveUpdatedIterationRequest.setBody(updatedIteration.toJSON());
			saveUpdatedIterationRequest.addObserver(new SaveIterationObserver());
			saveUpdatedIterationRequest.clearAsynchronous();
			saveUpdatedIterationRequest.send();
		}

		// Start saving the rest of the fields
		toUpdate.setIteration(updatedAssignedIterationID);
		toUpdate.setEstimate(updatedEstimate);
		toUpdate.setStatus( updatedStatus);
		toUpdate.setName((String) resultsTable.getValueAt(rowNumber, this.getColumnIndex("Name", columnNames)));
		toUpdate.setType( RequirementType.toType((String) resultsTable.getValueAt(rowNumber, this.getColumnIndex("Type", columnNames))));
		toUpdate.setPriority(RequirementPriority.toPriority((String) resultsTable.getValueAt(rowNumber, this.getColumnIndex("Priority", columnNames))));
		toUpdate.setReleaseNumber((String)resultsTable.getValueAt(rowNumber, this.getColumnIndex("ReleaseNumber", columnNames)));
		toUpdate.setActualEffort(Integer.parseInt((String) resultsTable.getValueAt(rowNumber, this.getColumnIndex("ActualEffort", columnNames))));

		return toUpdate.toJSON();

	}

	/** Gets the requirement that would be made if the columns from the given row were read.
	 * 
	 *  Invalid estimates and actual efforts are set to -1
	 * 
	 * @param row The row to read
	 * @return A requirement with the values from the fields read
	 */
	protected Requirement getCurrentRequirement(int row) {

		Requirement currentRequirement = new Requirement();


		// Start saving the rest of the fields
		currentRequirement.setName((String) resultsTableModel.getValueAt(row, this.getOriginalColumnIndex("Name")));
		currentRequirement.setType( RequirementType.toType((String) resultsTableModel.getValueAt(row, this.getOriginalColumnIndex("Type"))));
		currentRequirement.setStatus( RequirementStatus.toStatus((String) resultsTableModel.getValueAt(row, this.getOriginalColumnIndex("Status"))));
		currentRequirement.setPriority(RequirementPriority.toPriority((String) resultsTableModel.getValueAt(row, this.getOriginalColumnIndex("Priority"))));
		currentRequirement.setReleaseNumber((String)resultsTableModel.getValueAt(row, this.getOriginalColumnIndex("ReleaseNumber")));
		try {
			currentRequirement.setEstimate( Integer.parseInt( (String) resultsTableModel.getValueAt(row, this.getOriginalColumnIndex("Estimate"))));
		} catch (Exception e) {
			currentRequirement.setEstimate(-1);
		}
		try {
			currentRequirement.setActualEffort(Integer.parseInt((String) resultsTableModel.getValueAt(row, this.getOriginalColumnIndex("ActualEffort"))));
		} catch (Exception e) {
			currentRequirement.setActualEffort(-1);
		}

		int iterationID = this.getIterationID((String) resultsTableModel.getValueAt(row, this.getOriginalColumnIndex("Iteration")));
		currentRequirement.setIteration(iterationID);

		return currentRequirement;
	}

	/** Gets the id of the iteration with the given name
	 * 
	 * @param iterName the name of the iteration
	 * @return the id of the iteration
	 */
	private int getIterationID(String iterName){
		Iteration[] allIterations = parent.getParent().getAllIterations();
		for (Iteration anIter: allIterations){
			if (anIter.getName().equals(iterName)){
				return anIter.getID();
			}
		}
		System.err.print("Failed to find the iteration");
		return -1; // failure
	}


	/** Gets the name of the iteration with the given id
	 * 
	 * @param iterId the id of the iteration
	 * @return the name of the iteration
	 */
	private String getIterationName(int iterId){
		Iteration[] allIterations = parent.getParent().getAllIterations();
		for (Iteration anIter: allIterations){
			if (anIter.getID() == iterId){
				return anIter.getName();
			}
		}
		System.err.print("Failed to find the iteration");
		return ""; // failure
	}

	/** Gets the column index of the column with the given name
	 * 
	 * @param name The name to check for
	 * @return the column index, returns -1 upon failure
	 */
	private int getColumnIndex(String name, ArrayList<String> columnNames){
		for (int Column = 0; Column < columnNames.size(); Column++ ){
			if (columnNames.get(Column).equals(name)){
				return Column;
			}
		}
		System.err.println("That column doesn't exist!");
		return -2;
	}


	/** Gets the column index of the column with the given name
	 * 
	 * @param name The name to check for
	 * @return the column index, returns -1 upon failure
	 */
	private int getColumnIndex(String name){
		return getColumnIndex(name, getTableName());
	}

	/** Gets the original column index of the column with the given name
	 * 
	 * @param name The name to check for
	 * @return the original column index, returns -1 upon failure
	 */
	private int getOriginalColumnIndex(String name){
		return getColumnIndex(name, getOriginalTableName());
	}


	/** Sets up any arrays of flags or other settings needed
	 *  before editing can start 
	 */
	public void setUpForEditing(){
		// Clear the boolean arrays
		needsSaving = new Boolean[resultsTable.getRowCount()][resultsTable.getColumnCount()];
		for (int i = 0; i < resultsTable.getRowCount(); i++){
			for (int j = 0; j < resultsTable.getColumnCount(); j++){
				needsSaving[i][j] = Boolean.valueOf(false); // Set entries to false
			}		
		}
		isValid = new Boolean[resultsTable.getRowCount()][resultsTable.getColumnCount()];
		for (int i = 0; i < resultsTable.getRowCount(); i++){
			for (int j = 0; j < resultsTable.getColumnCount(); j++){
				isValid[i][j] = Boolean.valueOf(true); // Set entries to true
			}		
		}
		isEditable = new Boolean[resultsTable.getRowCount()][resultsTable.getColumnCount()];
		for (int i = 0; i < resultsTable.getRowCount(); i++){
			for (int j = 0; j < resultsTable.getColumnCount(); j++){
				isEditable[i][j] = Boolean.valueOf(true); // Set entries to true
			}		
		}
		for (int i = 0; i < resultsTable.getRowCount(); i++) {
			isEditable[i][getColumnIndex("ID")] = Boolean.valueOf(false);

			RequirementStatus status = RequirementStatus.toStatus((String)resultsTable.getValueAt(i, getColumnIndex("Status")));
			if (status == RequirementStatus.Complete || status == RequirementStatus.Deleted) {
				for (int j = 0; j < resultsTable.getColumnCount(); j++) {
					if (j != getColumnIndex("Status"))
						isEditable[i][j] = Boolean.valueOf(false);
				}
			}
			else if (status == RequirementStatus.InProgress) {
				isEditable[i][getColumnIndex("Estimate")] = Boolean.valueOf(false);
			}
			else if (resultsTable.getValueAt(i, getColumnIndex("Estimate")).equals("0")) {
				isEditable[i][getColumnIndex("Iteration")] = Boolean.valueOf(false);
			}

		}
		Boolean[][] originalIsEditable = new Boolean[resultsTable.getRowCount()][resultsTable.getColumnCount()];
		for (int i = 0; i < resultsTable.getRowCount(); i++)
			for (int j = 0; j < resultsTable.getColumnCount(); j++)
				originalIsEditable[resultsTable.convertRowIndexToModel(i)][j] = isEditable[i][getColumnIndex(resultsTableModel.getColumnName(j))];

		getModel().setEditable(true);
		getModel().setIsEditable(originalIsEditable);
		resultsTable.setDefaultRenderer(String.class, new ResultsTableCellRenderer(needsSaving, isValid, isEditable));
		setComboxforType();
		setComboxforStatus();
		setComboxforPriority();
		setComboxforIteration();
		setNumberBoxForEstimate();
		setNumberBoxForActualEffort();
		disableSorting();
		parent.getParent().getBtnSave().setEnabled(false);
	}



	/** Gets the unique identifier of the model at 
	 *  the given index
	 * 
	 * @param i The index of the model
	 * @return  The unique identifier of the model
	 */
	public String getUniqueIdAtIndex(int i) {
		return (String) resultsTable.getValueAt(i, this.getColumnIndex("ID"));
	}

	/** Change settings of table to indicate that the 
	 *  save was completed and normal operations 
	 *  should resume.
	 */
	public void savesComplete() {
	}

	/** Way to trigger a pop-up or enable/disable certain 
	 *  buttons when a  save is not successful.
	 */
	public void failedToSave() {
	}

	/**
	 * @return the listSaveModelController
	 */
	public ListSaveModelController getListSaveModelController() {
		return listSaveModelController;
	}

	/** A getter to get the current column headers of the table 
	 * @return columnHeader the ArrayList of headers for the table
	 */
	public ArrayList<String> getTableName(){
		ArrayList<String> columnHeader = new ArrayList<String>();

		// Set each value of the column header
		for (int i = 0; i < 9; i++){
			columnHeader.add(resultsTable.getColumnName(i));
		}

		return columnHeader;
	}

	/** A getter to get the original column headers of the table 
	 * @return columnHeader the ArrayList of original headers for the table
	 */
	public ArrayList<String> getOriginalTableName(){
		ArrayList<String> columnHeader = new ArrayList<String>();
		String[] columnNames = {"ID", "Name", "Iteration", "Type", "Status", "Priority", "ReleaseNumber", "Estimate", "ActualEffort"};

		// Set each value of the column header
		for (int i = 0; i < 9; i++){
			columnHeader.add(columnNames[i]);
		}

		return columnHeader;
	}


	/** Trigger a reset of all lists	 */
	public void refreshAll() {
		parent.getParent().refreshData();
	}


	/**
	 * @return the isValid
	 */
	public Boolean[][] getIsValid() {
		return isValid;
	}

	/**
	 * @param isValid the isValid to set
	 */
	public void setIsValid(Boolean[][] isValid) {
		this.isValid = isValid;
	}

	/**
	 * @return the isEditable
	 */
	public Boolean[][] getIsEditable() {
		return isEditable;
	}

	/**
	 * @param isEditable the isEditable to set
	 */
	public void setIsEditable(Boolean[][] isEditable) {
		this.isEditable = isEditable;
	}

	/** Turns on the save button when all cells are
	 *  valid and at least one has been changed. 
	 */
	public void updateSaveButton() {
		// Check for validity and disables the save button if there is invalidity
		for (Boolean[] Vrows : isValid)
			for (Boolean Vcells: Vrows)
				if (!Vcells.booleanValue()){
					parent.getParent().getBtnSave().setEnabled(false);
					// The save button has been disabled because there is invalidity in the table
					return;
				}
		// Check for Changes, and turn the save button in if changes were made
		for (Boolean[] Nrows : needsSaving)
			for (Boolean Ncells: Nrows)
				if (Ncells.booleanValue()){
					parent.getParent().getBtnSave().setEnabled(true);
					// The save button has been disabled because there is invalidity in the table
					return;			
				}
		parent.getParent().getBtnSave().setEnabled(false);
	}

	/**
	 * @return the inEditMode
	 */
	public boolean isInEditMode() {
		return inEditMode;
	}

	/**
	 * @param inEditMode the inEditMode to set
	 */
	public void setInEditMode(boolean inEditMode) {
		this.inEditMode = inEditMode;
	}
}

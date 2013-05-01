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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.charts;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.IToolbarGroupProvider;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.Filter;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListTab;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/** View that contains all the charts
 */
@SuppressWarnings("serial")
public class ChartView extends JPanel implements IToolbarGroupProvider{

	/** The panel containing buttons for the tool bar */
	protected ToolbarGroupView buttonGroup;

	/** The refresh button that reloads the results of the list/filter */
	protected JButton btnRefresh;

	/** The panel containing the actual status pie chart */
	private PieChartPanel statusPiePanel;

	/** The panel containing the actual iteration pie chart */
	private PieChartPanel iterationPiePanel;

	/** The panel containing the actual status pie chart */
	private PieChartPanel requirementCountPiePanel;

	/** The panel containing the actual iteration pie chart */
	private PieChartPanel requirementEstimatePiePanel;

	/** The panel containing the actual status bar chart */
	private BarChartPanel statusBarPanel;

	/** The panel containing the actual iteration bar chart */
	private BarChartPanel iterationBarPanel;

	/** The panel containing the actual requirement count bar chart */
	private BarChartPanel requirementCountBarPanel;

	/** The panel containing the actual iteration bar chart */
	private BarChartPanel requirementEstimateBarPanel;

	/** The panel containing various chart options on the left of the view */
	private ChartOptionsPanel optionsPanel; 

	/** Boolean indicated if the data to be displayed is to be taken from the dataset with the active filters applied or not */
	private boolean isFiltered;

	/** The type of data displayed in the chart */
	private String chartDataType;

	/** The type of chart to dispay (bar chart or pie chart)*/
	private String chartType;

	/** The parent ListTab that contains this ChartView */
	private ListTab view;


	/** Construct the view and initialize necessary internal variables
	 * @param view A ListTab that this view will be used in
	 */
	public ChartView(ListTab view) {

		//Initialize all the variables:
		//Charts
		statusPiePanel = new PieChartPanel();
		iterationPiePanel = new PieChartPanel();
		requirementCountPiePanel = new PieChartPanel();
		requirementEstimatePiePanel = new PieChartPanel();

		statusBarPanel = new BarChartPanel();
		iterationBarPanel = new BarChartPanel();
		requirementCountBarPanel = new BarChartPanel();
		requirementEstimateBarPanel = new BarChartPanel();

		//The buttons (including toolbar buttons)
		buttonGroup = new ToolbarGroupView("All Charts");
		btnRefresh = new JButton("Refresh");
		buttonGroup.getContent().add(btnRefresh);
		buttonGroup.setPreferredWidth(150);

		//Internal variables
		this.setView(view);
		isFiltered = false;
		chartDataType = "Requirement Status";
		chartType = "Pie Chart";

		//Add an action listener to the refresh button so that it will refresh the data being displayed when it is pushed
		btnRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refreshDataFromButton();
			}
		});

		//Set up the options panel to configure the charts
		optionsPanel = new ChartOptionsPanel(this);		

		// Construct the layout 
		this.setLayout(new BorderLayout());

		//Initial set up:

		//Bar Charts
		this.add(iterationBarPanel, BorderLayout.CENTER);
		this.add(statusBarPanel, BorderLayout.CENTER);

		//Pie Charts
		this.add(iterationPiePanel, BorderLayout.CENTER);
		this.add(statusPiePanel, BorderLayout.CENTER);

		//Visibility for all charts
		requirementCountBarPanel.setVisible(true);
		requirementEstimateBarPanel.setVisible(true);
		iterationBarPanel.setVisible(true);
		statusBarPanel.setVisible(true);
		requirementCountPiePanel.setVisible(true);
		requirementEstimatePiePanel.setVisible(true);
		iterationPiePanel.setVisible(true);
		statusPiePanel.setVisible(true);

		//Options
		this.add(optionsPanel, BorderLayout.WEST);
		optionsPanel.setVisible(true);

		//Initial visibility and data
		this.reloadData();
		this.refreshChartVisibility();
	}

	/** Refresh and reload data in the pie chart */
	public void refreshData() {
		//Ask parent to refresh data
		getView().getParent().refreshData();
		//Request data from parent
		this.reloadData();
		//update filter table
		optionsPanel.buildTable();
	}

	/** Refresh and reload data in the pie chart */
	public void refreshDataFromButton() {
		//Ask parent to refresh data
		getView().getParent().refreshData();
		//update filter table
		updateCharts();
		optionsPanel.buildTable();
	}

	/** Function to reload data from "List Requirements" tab without refreshing **/
	public void reloadData(){
		// Updates charts
		updateCharts();
		optionsPanel.buildTable();

		// Updates the dropdown lists
		this.setListOptions();
	}

	/** Update the charts to display new data 
	 */
	private void updateCharts() {		
		//Grab requirements
		Requirement[] requirements;
		if(!isFiltered){
			requirements = getView().getParent().getAllRequirements();
		}else{
			requirements = getView().getParent().getDisplayedRequirements();
		}

		//Grab iterations
		Iteration[] iterations = getView().getParent().getAllIterations();

		//Pass the status of the filter to each chart
		iterationPiePanel.enableFilter(isFiltered);
		statusPiePanel.enableFilter(isFiltered);
		requirementCountPiePanel.enableFilter(isFiltered);
		requirementEstimatePiePanel.enableFilter(isFiltered);
		iterationBarPanel.enableFilter(isFiltered);
		statusBarPanel.enableFilter(isFiltered);
		requirementCountBarPanel.enableFilter(isFiltered);
		requirementEstimateBarPanel.enableFilter(isFiltered);

		//Refresh the chart data
		statusPiePanel.refreshStatusChart(requirements);
		statusBarPanel.refreshStatusChart(requirements);
		requirementCountPiePanel.refreshAssignedUsersCountChart(requirements);
		requirementCountBarPanel.refreshAssignedUsersCountChart(requirements);
		requirementEstimatePiePanel.refreshAssignedUsersEstimateChart(requirements);
		requirementEstimateBarPanel.refreshAssignedUsersEstimateChart(requirements);
		iterationPiePanel.refreshIterationChart(requirements, iterations);
		iterationBarPanel.refreshIterationChart(requirements, iterations);
	}

	/** Sets the type of chart to use
	 * @param chartType the type of chart to use
	 */
	public void setChartType(String chartType) {
		this.chartType = chartType;
		//So it starts displaying immediately without a delay
		this.refreshChartVisibility();
	}

	/** Sets the type of data visible
	 * @param dataType the type of data to make visible
	 */
	public void setDataTypeVisible(String dataType) {
		chartDataType = dataType;
		//So it starts displaying immediately without a delay
		this.refreshChartVisibility();
	}

	/** Sets whether the data is filtered
	 * @param filterStatus the status of the filter, "Applied" or "Not Applied"
	 */
	public void setDataFiltered(String filterStatus) {
		if(filterStatus.equals("Applied"))
			isFiltered = true;
		else if(filterStatus.equals("Not Applied"))
			isFiltered = false;
		this.setListOptionsOnAppliedChange();
		this.refreshChartVisibility();
	}

	/**	
	 * @return the button group to place on the top of the toolbar
	 * @see edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.IToolbarGroupProvider#getGroup()
	 */
	@Override
	public ToolbarGroupView getGroup() {
		return buttonGroup;
	}

	/** Sets visibility of charts based on chartDataType and chartType */
	private void refreshChartVisibility(){
		//Remove all charts then add the one we want
		iterationBarPanel.setVisible(false);
		statusBarPanel.setVisible(false);
		requirementCountBarPanel.setVisible(false);
		requirementEstimateBarPanel.setVisible(false);
		iterationPiePanel.setVisible(false);
		statusPiePanel.setVisible(false);
		requirementCountPiePanel.setVisible(false);
		requirementEstimatePiePanel.setVisible(false);

		//Pie Chart
		if (chartType.equals("Pie Chart")){
			if(chartDataType.equals("Requirement Status")){
				this.add(statusPiePanel, BorderLayout.CENTER);
				statusPiePanel.setVisible(true);
			}
			else if(chartDataType.equals("Requirement Iteration")){
				this.add(iterationPiePanel, BorderLayout.CENTER);
				iterationPiePanel.setVisible(true);
			}
			else if(chartDataType.equals("Requirements per User")){
				this.add(requirementCountPiePanel, BorderLayout.CENTER);
				requirementCountPiePanel.setVisible(true);
			} else if (chartDataType.equals("Estimate per User")){
				this.add(requirementEstimatePiePanel, BorderLayout.CENTER);
				requirementEstimatePiePanel.setVisible(true);
			}

			//BarChart	
		} else if (chartType.equals("Bar Chart")){
			if(chartDataType.equals("Requirement Status")){
				this.add(statusBarPanel, BorderLayout.CENTER);
				statusBarPanel.setVisible(true);
			}
			else if(chartDataType.equals("Requirement Iteration")){
				this.add(iterationBarPanel, BorderLayout.CENTER);
				iterationBarPanel.setVisible(true);
			}
			else if(chartDataType.equals("Requirements per User")){
				this.add(requirementCountBarPanel, BorderLayout.CENTER);
				requirementCountBarPanel.setVisible(true);
			} else if (chartDataType.equals("Estimate per User")){
				this.add(requirementEstimateBarPanel, BorderLayout.CENTER);
				requirementEstimateBarPanel.setVisible(true);
			}

		}
		// Update the charts.
		updateCharts();

		//Always repaint!
		this.repaint();
	}

	/** Returns the parent ListTab that contains this view
	 * @return view The parent ListTab that contains this view
	 */
	public ListTab getView() {
		return view;
	}

	/** Sets the parent ListTab that contains this view
	 * @param view The parent ListTab that contains this view to set
	 */
	public void setView(ListTab view) {
		this.view = view;
	}

	public void setListOptions () {
		ArrayList<String> choices = new ArrayList<String>();
		ArrayList<String> filterChoices = new ArrayList<String>();

		String cdb = (String) this.optionsPanel.getChartDataBox().getSelectedItem();

		filterChoices.add("Not Applied");
		choices.add("Requirement Status");
		choices.add("Requirement Iteration");
		if (this.isFiltered) {
			if (requirementHasUsersWithFilters()) {
				choices.add("Requirements per User");
			}
			if (requirementHasUsersEstimateWithFilters()) {
				choices.add("Estimate per User");
			}
		} else {
			if (requirementHasUsers()) {
				choices.add("Requirements per User");
			}
			if (requirementUserHasEstimate()) {
				choices.add("Estimate per User");
			}
		}

		if (this.getView().getParent().getDisplayedRequirements().length > 0 && this.activeFilters()) {
			filterChoices.add("Applied");
		}

		String[] tmp1 = new String[choices.size()];
		for (int i = 0; i < choices.size(); i++) {
			tmp1[i] = choices.get(i);
		}
		String[] tmp2 = new String[filterChoices.size()];
		for (int i = 0; i < filterChoices.size(); i++) {
			tmp2[i] = filterChoices.get(i);
		}
		this.optionsPanel.setChartData(tmp1);
		try{
			this.optionsPanel.getChartDataBox().setSelectedItem(cdb);
		}
		catch(Error e){
			this.optionsPanel.getChartDataBox().setSelectedIndex(0);
		}
		String fob = (String) this.optionsPanel.getFiltersOptionsBox().getSelectedItem();
		this.optionsPanel.setFiltersOptions(tmp2);
		try{
			this.optionsPanel.getFiltersOptionsBox().setSelectedItem(fob);
		}
		catch(Error e){
			this.optionsPanel.getFiltersOptionsBox().setSelectedIndex(0);
		}

		try {
			this.setDataFiltered(this.optionsPanel.getFiltersOptionsBox().getSelectedItem().toString());
		} catch(Error e) {
			this.setDataFiltered(filterChoices.get(0));
		}

		try {
			this.setDataTypeVisible(this.optionsPanel.getChartDataBox().getSelectedItem().toString());
		} catch(Error e) {
			this.setDataTypeVisible(choices.get(0));
		}
		this.refreshChartVisibility();
	}

	public void setListOptionsOnAppliedChange () {
		ArrayList<String> choices = new ArrayList<String>();

		String cdb = (String) this.optionsPanel.getChartDataBox().getSelectedItem();

		choices.add("Requirement Status");
		choices.add("Requirement Iteration");
		if (this.isFiltered) {
			if (requirementHasUsersWithFilters()) {
				choices.add("Requirements per User");
			}
			if (requirementHasUsersEstimateWithFilters()) {
				choices.add("Estimate per User");
			}
		} else {
			if (requirementHasUsers()) {
				choices.add("Requirements per User");
			}
			if (requirementUserHasEstimate()) {
				choices.add("Estimate per User");
			}
		}

		String[] tmp1 = new String[choices.size()];
		for (int i = 0; i < choices.size(); i++) {
			tmp1[i] = choices.get(i);
		}
		this.optionsPanel.setChartData(tmp1);
		try{
			this.optionsPanel.getChartDataBox().setSelectedItem(cdb);
		}
		catch(Error e){
			this.optionsPanel.getChartDataBox().setSelectedIndex(0);
		}

		try {
			this.setDataTypeVisible(this.optionsPanel.getChartDataBox().getSelectedItem().toString());
		} catch(Error e) {
			this.setDataTypeVisible(choices.get(0));
		}

		this.refreshChartVisibility();
	}

	/** Helper that returns true if there are active filters, false otherwise
	 * @return boolean
	 */
	private boolean activeFilters() {
		Filter[] filters = this.view.getParent().getAllFilters();
		for (int i = 0; i < filters.length; i++) {
			if (filters[i].isUseFilter()) {
				return true;
			}
		}
		return false;
	}

	/** Helper that returns true if there are users that are assigned to 
	 * the filtered list of requirements, false otherwise
	 * @return boolean
	 */
	private boolean requirementHasUsersWithFilters() {
		Requirement[] reqList = this.getView().getParent().getDisplayedRequirements();
		Filter[] filters = this.getView().getParent().getAllFilters();
		for (int i = 0; i < reqList.length; i++) {
			for (int j = 0; j < filters.length; j++) {
				if (filters[j].passesFilter(reqList[i]) && reqList[i].getUserNames().size() > 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**  Helper that returns true if there are estimates for users
	 * that are assigned to the filtered list of requirements, false otherwise
	 * @return boolean
	 */
	private boolean requirementHasUsersEstimateWithFilters() {
		Requirement[] reqList = this.getView().getParent().getDisplayedRequirements();
		Filter[] filters = this.getView().getParent().getAllFilters();
		for (int i = 0; i < reqList.length; i++) {
			for (int j = 0; j < filters.length; j++) {
				if (filters[j].passesFilter(reqList[i]) && reqList[i].getUserNames().size() > 0 && reqList[i].getEstimate() > 0) {
					return true;
				}
			}
		}
		return false;
	}

	/** Helper that returns true if there are requirements that has users assigned,
	 * false otherwise
	 * @return boolean
	 */
	private boolean requirementHasUsers() {
		Requirement[] reqList = this.getView().getParent().getAllRequirements();
		for (int i = 0; i < reqList.length; i++) {
			if (reqList[i].getUserNames().size() > 0) {
				return true;
			}
		}
		return false;
	}

	/** Helper that returns true if there are estimates for users
	 * that are assigned to the list of requirements, false otherwise
	 * @return boolean
	 */
	private boolean requirementUserHasEstimate() {
		Requirement[] reqList = this.getView().getParent().getAllRequirements();
		for (int i = 0; i < reqList.length; i++) {
			if (reqList[i].getUserNames().size() > 0 && reqList[i].getEstimate() > 0) {
				return true;
			}
		}
		return false;
	}
}

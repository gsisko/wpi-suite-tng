package edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.swing.JOptionPane;
//
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter.SaveFilterObserver;
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.*;
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.*;
//import edu.wpi.cs.wpisuitetng.network.Network;
//import edu.wpi.cs.wpisuitetng.network.Request;
//import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
//
//public class SaveFilterController implements ActionListener 
//{
//    private final FilterListPanel panel;
//    private final FilterBuilderPanel builder;
//    private final ListRequirementsView view;
//    
//    public SaveFilterController(ListRequirementsView view) 
//    {
//    	this.panel = view.getListPanel().getFilterPanel();
//    	this.builder = view.getListPanel().getBuilderPanel();
//    	this.view = view;
//    }
//
//
//    public void actionPerformed(ActionEvent event) 
//    {
//	/*
//    	// check if any inputs are invalid, print an error message if one is
//    	if (view.getFilterName().getText().length() == 0) {
//    		JOptionPane.showMessageDialog(null, "Name must be non-blank.", "Error", JOptionPane.ERROR_MESSAGE);
//    		return;
//    	}
//    	if (view.getFilterName().getText().length() > 100) {
//    		JOptionPane.showMessageDialog(null, "Name cannot be greater than 100 characters.", "Error", JOptionPane.ERROR_MESSAGE);
//    		return;
//    	}
//    	if (view.getFilterDescription().getText().length() == 0) {
//    		JOptionPane.showMessageDialog(null, "Description must be non-blank.", "Error", JOptionPane.ERROR_MESSAGE);
//    		return;
//    	}
//    	if (view.getFilterEstimate().getText().length() == 0) {
//    		JOptionPane.showMessageDialog(null, "Estimate must be non-blank.", "Error", JOptionPane.ERROR_MESSAGE);
//    		return;
//    	}
//    	if (view.getFilterActualEffort().getText().length() == 0) {
//    		JOptionPane.showMessageDialog(null, "ActualEffort must be non-blank.", "Error", JOptionPane.ERROR_MESSAGE);
//    		return;
//    	}*/
//
//    	
//	    //if (view.getMode() == CREATE) { // if we are creating a new filter
//		
//	    	// get the fields from the UI
//	    	String name = builder.getFilterName().toString();
//		String description = builder.getFilterDescription().getText();
//		Object value = builder.getValue();
//		OperatorType comparator = OperatorType.toType(builder.getOperatorType().toString());
//		FilterType type = FilterType.toType(builder.getFilterType().getSelectedItem().toString());
//		
//			// make a PUT http request and let the observer get the response
//		    final Request request = Network.getInstance().makeRequest("filtermanager/filter", HttpMethod.PUT); // PUT == create
//		    request.setBody(new Filter(type, comparator, value, true).toJSON()); // put the new message in the body of the request
//		    request.addObserver(new SaveFilterObserver(this)); // add an observer to process the response
//		    request.send();
//		//}
//		/*
//		else { // we are updating an existing filter
//			
//			// make a new filter to store the updated data
//			Filter updatedFilter = new Filter(); 
//			Filter oldFilter = builder.getListPanel();
//			
//			// give the new filter the correct ID number
//			updatedFilter.setId(oldFilter.getId());
//			
//			// fill in the rest of the fields with the data from the UI
//			updatedFilter.setName(view.getFilterName().getText());
//			updatedFilter.setDescription(view.getFilterDescription().getText());
//			updatedFilter.setType(FilterType.toType(view.getFilterType().getSelectedItem().toString()));
//			updatedFilter.setReleaseNumber(Integer.parseInt((view.getFilterReleaseNumber().getText().equals("")) ? "-1" : view.getFilterReleaseNumber().getText()));
//			updatedFilter.setStatus(FilterStatus.toStatus(view.getFilterStatus().getSelectedItem().toString()));
//			updatedFilter.setPriority(FilterPriority.toPriority(view.getFilterPriority().getSelectedItem().toString()));
//			updatedFilter.setEstimate(Integer.parseInt(view.getFilterEstimate().getText()));
//			updatedFilter.setActualEffort(Integer.parseInt(view.getFilterActualEffort().getText()));
//		
//			// make a POST http request and let the observer get the response
//		    final Request request = Network.getInstance().makeRequest("filtermanager/filter", HttpMethod.POST); // POST == update
//		    request.setBody(updatedFilter.toJSON()); // put the new message in the body of the request
//		    request.addObserver(new SaveFilterObserver(this)); // add an observer to process the response
//		    request.send();
//			
//		}*/
//	
//    }
//    
//    
//    /**
//     * Simple success message for saving a new filter.  If we want the boxes to clear automatically,
//     * this is probably where we would want to implement it.
//     * @param newReq Filter that was saved.
//     */
//	public void saveSuccess(Filter newReq) {
//		// if success, set all of the UI fields appropriately for post-save actions
//		if (newReq != null) {
//			System.out.print("Filter " +/* newReq.getId() +*/ " saved successfully\n");
//			/*
//			view.getFilterName().setText("");
//			view.getFilterDescription().setText("");
//			view.getFilterType().setSelectedIndex(0);
//			view.getFilterStatus().setSelectedIndex(0);
//			view.getFilterPriority().setSelectedIndex(0);
//			view.getFilterReleaseNumber().setText("");
//			view.getFilterEstimate().setText("");
//			view.getFilterActualEffort().setText("");
//			
//			view.getSaveButton().setText("Update");
//			view.getSaveButton().setEnabled(false);
//
//			view.getFilterName().setEnabled(false);
//			view.getFilterDescription().setEnabled(false);
//			view.getFilterType().setEnabled(false);
//			view.getFilterStatus().setEnabled(false);
//			view.getFilterPriority().setEnabled(false);
//			view.getFilterReleaseNumber().setEnabled(false);
//			view.getFilterEstimate().setEnabled(false);
//			view.getFilterActualEffort().setEnabled(false);*/
//		}
//		else {
//			System.err.print("Undected error saving filter\n");
//		}
//	}
//	
//	/*
//	public FilterListPanel getView() {
//		return view;
//	}*/
//}
//

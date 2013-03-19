package edu.wpi.cs.wpisuitetng.modules.requirementmanager.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controllers.SaveRequirementObserver;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.*;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.views.MainView;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class RefreshRequirementController implements ActionListener {

    private final MainView view;
    protected Requirement[] data = null;
    
    public RefreshRequirementController(MainView view) {
	this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	final RequestObserver requestObserver = new RefreshRequirementObserver(this);
	final Request request = Network.getInstance().makeRequest("requirementmanager/requirement", HttpMethod.GET);
	
	request.addObserver(requestObserver);
	request.send();
	
    }
    
    public void reviecedData(Requirement[] requirements){
	this.data = requirements;
	
	/*
	 * for loop here to create string that will be displayed in the Panel
	 * requires more information
	 */
	
	/*
	 * view... actually set the values in the view here.
	 */
    }

}

/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Chris Casola
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.dropbox.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;

import edu.wpi.cs.wpisuitetng.modules.dropbox.model.FileListModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * 
 *
 */
public class GetFileController extends MouseAdapter {
	
	private final FileListModel lstModel;
	
	public GetFileController(FileListModel lstModel) {
		this.lstModel = lstModel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			JList lstFiles = (JList)e.getSource();
			int index = lstFiles.locationToIndex(e.getPoint());
			String fileName = (String)lstFiles.getModel().getElementAt(index);
			
			int id = lstModel.getModel().getFileId(fileName);
			System.out.println("Clicked on file: " + fileName + " of ID " + id);
			
			Request request = Network.getInstance().makeRequest("dropbox/fileblob/" + id, HttpMethod.GET);
			request.addObserver(new GetFileObserver());
			request.send();
		}
	}

}

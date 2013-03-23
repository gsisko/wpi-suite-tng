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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JFileChooser;
import javax.swing.JList;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.JanewayFrame;
import edu.wpi.cs.wpisuitetng.modules.dropbox.model.FileBlob;
import edu.wpi.cs.wpisuitetng.modules.dropbox.model.FileListModel;
import edu.wpi.cs.wpisuitetng.modules.dropbox.view.DropboxPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * 
 *
 */
public class GetFileController extends MouseAdapter implements ActionListener {
	
	private final FileListModel model;
	private final DropboxPanel view;
	private final JFileChooser fileChooser;
	
	public GetFileController(FileListModel model, DropboxPanel view) {
		this.model = model;
		this.view = view;
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			JList lstFiles = (JList)e.getSource();
			int index = view.getFilesList().locationToIndex(e.getPoint());
			String fileName = (String)lstFiles.getModel().getElementAt(index);
			
			getFile(model.getModel().getFileId(fileName));
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int index = view.getFilesList().getSelectedIndex();
		String fileName = (String) view.getFilesList().getSelectedValue();
		getFile(model.getModel().getFileId(fileName));
	}
	
	public void receivedFile(FileBlob file) {
		final int retVal = fileChooser.showDialog(JanewayFrame.getInstance(), "Save");
		
		if (retVal == JFileChooser.APPROVE_OPTION) {
			final File destDir = fileChooser.getSelectedFile();
			final File destFile = new File(destDir.getAbsolutePath() + File.separator + file.getFileName());
			
			try {
				writeFile(destFile, file);
			}
			catch (IOException e) {
				// TODO notify user
				System.out.println("Error writing file!");
			}
			/*
			byte[] fileContents = null;
			
			try {
				fileContents = readFile(file);
			}
			catch (IOException e) {
				System.out.println("Error occurred reading file!");
			}
			
			if (fileContents != null) {
				Request request = null;
				request = Network.getInstance().makeRequest("dropbox/fileblob", HttpMethod.PUT);
				request.setBody(new FileBlob(fileContents, file.getName()).toJSON());
				request.send();
			}*/
		}
		
		System.out.println(file.getContentsAsString());
		// TODO something with the received file
	}
	
	private void writeFile(File destFile, FileBlob srcFile) throws IOException {
		final OutputStream oStream = new FileOutputStream(destFile);
		oStream.write(srcFile.getContents());
		oStream.close();
	}

	private void getFile(int id) {
		System.out.println("Sending request to: dropbox/fileblob/" + id);
		Request request = Network.getInstance().makeRequest("dropbox/fileblob/" + id, HttpMethod.GET);
		request.addObserver(new GetFileObserver(this));
		request.send();
	}
}

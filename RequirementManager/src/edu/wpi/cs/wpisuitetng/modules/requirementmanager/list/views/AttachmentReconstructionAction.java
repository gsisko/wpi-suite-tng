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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.observers.RetrieveAttachmentPartObserver;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.AttachmentPart;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/** Watches the "New <Model>" button and toggles its settings as well
 *  as the associated Builder's settings when the button is pressed.
 *  
 *  When pressed, the information in the filter builder panel
 *  is reset and the fields are grayed out.          */
public class AttachmentReconstructionAction implements ActionListener {
	private ArrayList<Integer> parts;
	private String fileName;
	private ArrayList<AttachmentPart> attachmentParts;

	/** Constructor that takes the two panels to watch
	 * 
	 * @param listView ILitPanel for this list/builder
	 * @param builderView   IBuilderPanel for this list/builder
	 */
	public AttachmentReconstructionAction(String fileName, ArrayList<Integer> parts){
		this.fileName = fileName;
		this.parts = parts;
	}

	/** This is called whenever the "Cancel"/"New Filter" button is 
	 *  called. If the button mode is set to cancel mode, it is set 
	 *  to display "New Filter" as the button and disables and
	 *  resets the fields in the FilterBuilderPanel. If the mode is
	 *  set to New Filter, then the opposite happens.
	 *  
	 *  @param e The event that triggers these responses 
	 */
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		fc.setSelectedFile(new File(fileName));
		int returnVal = fc.showDialog(null,"Download Attachment");

		attachmentParts = new ArrayList<AttachmentPart>();

		//Process the results.
		boolean overwrite = true;
		if(fc.getSelectedFile().exists()){
			if (JOptionPane.showConfirmDialog(null, "A file with that name already exists, would you like to overwrite it?", "Request", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION)
				overwrite = false;
		}
		if (returnVal == JFileChooser.APPROVE_OPTION && overwrite) {

			File newFile = new File(fc.getSelectedFile().getAbsolutePath());

			OutputStream destination;
			try {
				newFile.createNewFile();
				destination = new FileOutputStream(newFile);
				//int offset = 0;
				try {  
					for(Integer partId : parts){
						//getPartSuccess = false;

						Request request;
						request = Network.getInstance().makeRequest("requirementmanager/attachmentpart/" + partId, HttpMethod.GET);
						request.addObserver(new RetrieveAttachmentPartObserver(this));
						request.send();	

						//while(!getPartSuccess);
						//String str = new String(attachmentPartBytes);
						//destination.write(attachmentPartBytes);//.write(attachmentPartBytes, offset, attachmentPartBytes.length);
						//offset += attachmentPartBytes.length;
					}
				} finally {  

					boolean finished = false;
					while (!finished) {
						try {
							Thread.sleep(500);
						} catch (InterruptedException ex) {
							// TODO Auto-generated catch block
							ex.printStackTrace();
						}
						if (attachmentParts.size() == parts.size())
							finished = true;
					}
					
					AttachmentPart[] orderedAttachmentParts = new AttachmentPart[attachmentParts.size()];
					for (AttachmentPart attachmentPart : attachmentParts) {
						orderedAttachmentParts[attachmentPart.getPartNumber()] = attachmentPart;
					}
					
					for (int i = 0; i < orderedAttachmentParts.length; i++) {
						destination.write(orderedAttachmentParts[i].getAttachmentPartByteArray());
					}

					try {
						destination.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}  
				}  
			} catch (FileNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			//ByteArrayOutputStream destination = null;
			catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

		}
		fc.setSelectedFile(null);
	}

	/**
	 * @param getPartSuccess the getPartSuccess to set
	 */
	public synchronized void getPartSuccess(AttachmentPart attachmentPart) {
		this.attachmentParts.add(attachmentPart);
	}
}

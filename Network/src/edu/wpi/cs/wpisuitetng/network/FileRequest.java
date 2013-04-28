/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    JPage
 *    Andrew Hurle
 *    Chris Casola
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.network;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetng.modules.core.models.FilePartModel;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.RequestModel;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;


/**
 * This class represents a Request. It can be observed by one or more RequestObservers.
 * 
 * A Request can be sent synchronously or asynchronously. By default, a Request is asynchronous upon 
 * construction. When a synchronous Request is sent, it will block, causing the current thread to pause 
 * while the Request is sent and while waiting for a Response. The RequestObservers that have been added to 
 * the Request will not be notified. In most cases, you will not want to send a synchronous Request. When a 
 * Request is sent asynchronously, a new thread is created which sends the Request, generates a Response, 
 * and notifies any RequestObservers that have been added to the Request.
 * 
 * TODO add equals method
 */
public class FileRequest{
	private String body;
	private NetworkConfiguration networkConfiguration;
	private String path;
	private File file;
	private HttpMethod httpMethod;
	private List<RequestObserver> observers;
	protected boolean running = false;
	protected static int FilePartSize  = 32768;

	/**
	 * Constructor.
	 * 
	 * @param networkConfiguration	The NetworkConfiguration to use.
	 * @param path					The path to append to the API URL.
	 * @param httpMethod			The HttpMethod to use.
	 * 
	 * @throw RuntimeException		If a MalformedURLException is received while constructing the URL.
	 * @throw NullPointerException	If the networkConfiguration or requestMethod is null.
	 */
	public FileRequest(NetworkConfiguration networkConfiguration, String path, HttpMethod httpMethod, File file) {
		this.networkConfiguration = networkConfiguration;
		this.path = path;
		this.httpMethod = httpMethod;
		this.file = file;
	}

	/**
	 * Sends the Request by creating a new RequestActor and starting it as a new Thread.
	 * Note: If Request.isAsynchronous is false, RequestObservers for this Request will not be updated.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * 
	 * @throws IllegalStateException	If the Request is being sent.
	 */
	public void send() throws IOException {
		InputStream source = null;
		ArrayList<ByteArrayOutputStream> destinations = new ArrayList<ByteArrayOutputStream>();

		try {
			source = new FileInputStream(file);// = new InputStream().getChannel();

			byte[] buffer = new byte[8192];


			int read = 0;
			while ( (read = source.read(buffer)) != -1 ) {
				ByteArrayOutputStream newDestination = new ByteArrayOutputStream();
				newDestination.write(buffer, 0, read);
				destinations.add(newDestination);
			}
		}
		finally {
			if(source != null) {
				source.close();
			}
			for(ByteArrayOutputStream destination : destinations){
				if(destination != null) {
					destination.close();
				}
			}   
		}
		int n = 0;
		ArrayList<FilePartModel> parts = new ArrayList<FilePartModel>();
		for(ByteArrayOutputStream destination : destinations){
			FilePartModel part = new FilePartModel("fileid", ""+n, file.getName(), (int)file.getTotalSpace(), destination.toByteArray().toString());//destination.size(), destination.toByteArray(), n);

			parts.add(part);

			final Request request = Network.getInstance().makeRequest("requirementmanager/attachmentpart", HttpMethod.PUT); // PUT == create
			request.setBody(part.toString()); // put the new message in the body of the request
			for(RequestObserver obs : observers){
				request.addObserver(obs); // add an observer to process the response
			}
			request.send();

			n++;
		}

		/*boolean finished = false;
				while(!finished) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (currentAttachment.getAttachmentPartIds().size() == destinations.size())
						finished = true;
				}*/
}

	/**
	 * @see edu.wpi.cs.wpisuitetng.network.models.RequestModel#addHeader(String, String)
	 * 
	 * @throws IllegalStateException	If the Request is being sent.
	 */
	public void addHeader(String key, String value) {
	}
	
	/**
	 * @see edu.wpi.cs.wpisuitetng.network.models.RequestModel#addQueryData(String, String)
	 * 
	 * @throws IllegalStateException	If the Request is being sent.
	 */
	public void addQueryData(String key, String value) {
	}

	/**
	 * Makes this Request synchronous.
	 * 
	 * @throws IllegalStateException	If the Request is being sent.
	 */
	public void clearAsynchronous(){
	}

	/**
	 * Makes this Request asynchronous.
	 * 
	 * @throws IllegalStateException	If the Request is being sent.
	 */
	public void setAsynchronous(){
	}

	/**
	 * Sets the timeout (in milliseconds) for connecting to the server.
	 * 
	 * @param connectTimeout the timeout (in milliseconds) for connecting to the server.
	 * 
	 * @throws IllegalStateException	If the Request is being sent.
	 */
	public void setConnectTimeout(int connectTimeout) throws IllegalStateException {
	}
	
	/**
	 * Sets the timeout (in milliseconds) for reading the response body.
	 * 
	 * @param readTimeout the timeout (in milliseconds) for reading the response body
	 * 
	 * @throws IllegalStateException	If the Request is being sent.
	 */
	public void setReadTimeout(int readTimeout) throws IllegalStateException {
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.network.models.RequestModel#setBody(String)
	 * 
	 * @throws IllegalStateException	If the Request is being sent.
	 */
	public void setBody(String body) throws IllegalStateException, NullPointerException {
		this.body = body;
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.network.models.RequestModel#setHttpMethod(HttpMethod)
	 * 
	 * @throws IllegalStateException	If the Request is being sent.
	 */
	public void setHttpMethod(HttpMethod httpMethod) throws IllegalStateException, NullPointerException {
		this.httpMethod = httpMethod;
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.network.models.RequestModel#setResponse(ResponseModel)
	 * 
	 * @throws	IllegalStateException	If the Request is being sent.
	 */
	protected void setResponse(ResponseModel response) throws IllegalStateException {
	}
	/**
	 * Adds a RequestObserver to this Observable.
	 * 
	 * @param o The RequestObserver to add.
	 * 
	 * @throws	IllegalStateException	If the Request is being sent.
	 */
	public void addObserver(RequestObserver o) throws IllegalStateException {
		// check to see if the request has been sent yet
		if (running) {
			throw new IllegalStateException("Request is being sent.");
		}

		observers.add(o);
	}

	/**
	 * Returns the number of RequestObservers for this Observable.
	 * 
	 * @return The number of RequestObservers for this Observable.
	 */
	public int countObservers() {
		return observers.size();
	}

	/**
	 * Notifies RequestObservers of a response with a status code indicating success (2xx).
	 * 
	 * @throws	IllegalStateException	If the Request is being sent.
	 */
	public void notifyObserversResponseSuccess() throws IllegalStateException {
		// check to see if the request has been sent yet
		if (running) {
			throw new IllegalStateException("Request is being sent.");
		}

		for (RequestObserver obs : observers) {
			obs.responseSuccess((IRequest) this);
		}
	}

	/**
	 * Notifies RequestObservers of response with a status code indicating a client error (4xx) for server error (5xx).
	 * 
	 * @throws	IllegalStateException	If the Request is being sent.
	 */
	public void notifyObserversResponseError() throws IllegalStateException {
		// check to see if the request has been sent yet
		if (running) {
			throw new IllegalStateException("Request is being sent.");
		}

		for (RequestObserver obs : observers) {
			obs.responseError((IRequest) this);
		}
	}

	/**
	 * Notifies RequestObservers of a failure in sending a request.
	 * 
	 * @param exception An exception.
	 * 
	 * @throws	IllegalStateException	If the Request is being sent.
	 */
	public void notifyObserversFail(Exception exception) throws IllegalStateException {
		// check to see if the request has been sent yet
		if (running) {
			throw new IllegalStateException("Request is being sent.");
		}

		for (RequestObserver obs : observers) {
			obs.fail((IRequest) this, exception);
		}
	}
}

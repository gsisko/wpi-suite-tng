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

import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * provides methods for observers in the list
 * 
 */
public interface IObserver {
	/** Upon success, tell the controller to trigger a refresh 
	 * @param iReq The request response from the server 
	 */
	public void responseSuccess(IRequest iReq);
	/**  Upon failure, prints to console
	 * @param iReq The request response from the server 
	 */
	public void responseError(IRequest iReq) ;
	/** Upon failure, prints to console
	 * @param iReq The request response from the server 
	 */
	public void fail(IRequest iReq, Exception exception);
}

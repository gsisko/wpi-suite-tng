package edu.wpi.cs.wpisuitetng.modules.dropbox.controller;

import edu.wpi.cs.wpisuitetng.modules.dropbox.model.FileIndex;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * 
 *
 */
public class GetFileIndexObserver implements RequestObserver {

	private final GetFileIndexController controller;
	
	public GetFileIndexObserver(GetFileIndexController controller) {
		this.controller = controller;
	}

	@Override
	public void responseSuccess(IRequest iReq) {
		FileIndex[] response = FileIndex.fromJSONArray(iReq.getResponse().getBody());
		if (response.length == 1) {
			controller.responseSuccess(response[0]);
		}
		else {
			System.out.println("Too many FileIndices");
		}
	}

	@Override
	public void responseError(IRequest iReq) {
		System.out.println("resp error: " + iReq.getResponse().getStatusCode() + " " + iReq.getResponse().getStatusMessage());
		
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.out.println("resp fail: " + iReq.getResponse().getStatusCode() + " " + iReq.getResponse().getStatusMessage());
		
	}

}

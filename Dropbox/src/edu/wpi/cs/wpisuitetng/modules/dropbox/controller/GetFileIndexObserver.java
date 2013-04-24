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
		controller.responseSuccess(response[0]);
	}

	@Override
	public void responseError(IRequest iReq) {
		// TODO deal with error
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		// TODO deal with failure
	}

}

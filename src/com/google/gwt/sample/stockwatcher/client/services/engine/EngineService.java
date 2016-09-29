package com.google.gwt.sample.stockwatcher.client.services.engine;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("engine")
public interface EngineService extends RemoteService {

	/**
	 * Runs the Engine.
	 * 
	 * @return Just a string for logging purposes since this is a post method
	 */
	String postEngine();
	
}
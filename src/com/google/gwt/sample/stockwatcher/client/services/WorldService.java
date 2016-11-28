package com.google.gwt.sample.stockwatcher.client.services;

import com.google.gwt.sample.stockwatcher.client.data.World;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("template")
public interface WorldService extends RemoteService {

	/**
	 * @return Returns the World object
	 */
	public World getWorld();
	
}
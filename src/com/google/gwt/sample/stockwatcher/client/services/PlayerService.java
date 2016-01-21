package com.google.gwt.sample.stockwatcher.client.services;

import com.google.gwt.sample.stockwatcher.client.data.Business;
import com.google.gwt.sample.stockwatcher.client.data.Player;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("player")
public interface PlayerService extends RemoteService {

	/**
	 * Get a Player object by its entity ID (which originated as a GAE-assigned userId)
	 */
	Player getPlayer(String userId);
	
	/**
	 * Provides a convenience method equivalent to calling getPlayer(name) with the Id of the current user
	 * @return Player of currently logged in user (if exists) 
	 */
	Player getUserPlayer();
}
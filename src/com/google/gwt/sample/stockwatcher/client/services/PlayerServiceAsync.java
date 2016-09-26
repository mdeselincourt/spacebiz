package com.google.gwt.sample.stockwatcher.client.services;

import com.google.gwt.sample.stockwatcher.client.data.Player;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.objectify.Key;

public interface PlayerServiceAsync {
	
	void getPlayer(String userId, AsyncCallback<Player> callback);
	
	// Returns void because it's just the async CALL. When (if) the response has come
	// the AsyncCallback<Player> will be invoked.
	void getUserPlayer(AsyncCallback<Player> callback);
	
	/**
	 * 
	 * @param player Initialised Player object, but devoid of gaeUser data,
	 * as the SERVER will fetch the user's gaeUser data and store it.
	 * @param key 
	 * @param callback Callback of functionality to run upon success/failure of creation
	 */
	void putPlayer(Player player, String key, AsyncCallback<Key<Player>> callback);
}

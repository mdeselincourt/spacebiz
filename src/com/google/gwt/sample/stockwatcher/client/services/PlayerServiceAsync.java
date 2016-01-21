package com.google.gwt.sample.stockwatcher.client.services;

import com.google.gwt.sample.stockwatcher.client.data.Player;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PlayerServiceAsync {
	
	void getPlayer(String userId, AsyncCallback<Player> callback);
	
	void getUserPlayer(AsyncCallback<Player> callback);
}

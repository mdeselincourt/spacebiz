package com.google.gwt.sample.stockwatcher.client.services;

import com.google.gwt.sample.stockwatcher.client.data.World;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface WorldServiceAsync {
	
	void getWorld(AsyncCallback<World> callback);
	
}

package com.google.gwt.sample.stockwatcher.client.services.engine;

import com.google.gwt.sample.stockwatcher.client.data.Business;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface EngineServiceAsync {
	
	void postEngine(AsyncCallback<String> callback);
	
}

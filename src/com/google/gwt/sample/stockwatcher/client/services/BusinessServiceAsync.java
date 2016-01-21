package com.google.gwt.sample.stockwatcher.client.services;

import com.google.gwt.sample.stockwatcher.client.data.Business;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface BusinessServiceAsync {
	
	void postBusiness(Business b, AsyncCallback<String> callback);
	
}

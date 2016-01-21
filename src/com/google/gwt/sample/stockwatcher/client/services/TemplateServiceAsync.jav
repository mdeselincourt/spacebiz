package com.google.gwt.sample.stockwatcher.client.services;

import com.google.gwt.sample.stockwatcher.client.data.Business;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TemplateServiceAsync {
	
	void methodTemplate(Business b, AsyncCallback<String> callback);
	
}

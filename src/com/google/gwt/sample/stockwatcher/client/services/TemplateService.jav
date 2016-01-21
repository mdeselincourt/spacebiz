package com.google.gwt.sample.stockwatcher.client.services;

import com.google.gwt.sample.stockwatcher.client.data.Business;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("template")
public interface TemplateService extends RemoteService {

	/**
	 * Stores a business.
	 * 
	 * TODO: Current assumption is that you can bundle in other objects (loans, stakes) 
	 * and the service will automatically reference them in the world.
	 * 
	 * @return Just a string for logging purposes since this is a post method
	 */
	String methodTemplate(Template newTemplate);
	
}
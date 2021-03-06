package com.google.gwt.sample.stockwatcher.server;

import java.util.logging.Logger;

import com.google.gwt.sample.stockwatcher.client.data.Business;
import com.google.gwt.sample.stockwatcher.client.data.World;
import com.google.gwt.sample.stockwatcher.client.services.BusinessService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Result;
import com.googlecode.objectify.VoidWork;
import com.googlecode.objectify.Work;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class TemplateServiceImpl extends RemoteServiceServlet implements TemplateService {

	private static final Logger log = Logger.getLogger(TemplateServiceImpl.class.getName());
	
	/**
	 * Needs to be registered in web.xml
	 */
	@Override
	public String methodTemplate(Template newTemplate) {
		
		// log.info("TemplateServiceImpl.methodTemplate() is executing");

		return resultString;
	}
}



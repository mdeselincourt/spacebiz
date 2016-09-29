package com.google.gwt.sample.stockwatcher.server.engine;

import java.util.logging.Logger;

import com.google.gwt.sample.stockwatcher.client.data.Business;
import com.google.gwt.sample.stockwatcher.client.data.World;
import com.google.gwt.sample.stockwatcher.client.services.BusinessService;
import com.google.gwt.sample.stockwatcher.client.services.engine.EngineService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Result;
import com.googlecode.objectify.VoidWork;
import com.googlecode.objectify.Work;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class EngineServiceImpl extends RemoteServiceServlet implements EngineService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3528225269319923698L;
	private static final Logger log = Logger.getLogger(EngineServiceImpl.class.getName());
	
	/**
	 * Needs to be registered in web.xml
	 */
	@Override
	public String postEngine() {
		
		log.info("EngineServiceImpl.postEngine() is executing");

		return "Engine has run";
	}
}



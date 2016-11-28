package com.google.gwt.sample.stockwatcher.server;

import java.util.logging.Logger;

import com.google.gwt.sample.stockwatcher.client.data.World;
import com.google.gwt.sample.stockwatcher.client.services.WorldService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Result;
import com.googlecode.objectify.VoidWork;
import com.googlecode.objectify.Work;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class WorldServiceImpl extends RemoteServiceServlet implements WorldService {

	private static final Logger log = Logger.getLogger(Logger.class.getName());
	
	/**
	 * Needs to be registered in web.xml
	 */
	@Override
	public World getWorld() {
		
		// log.info("TemplateServiceImpl.methodTemplate() is executing");

		// TODO: Derp
		return null;
	}
}



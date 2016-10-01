package com.google.gwt.sample.stockwatcher.server.engine;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Game engine servlet (it's a servlet so you can trigger it by Cron job or URL for testing)
 * @author Mike
 *
 */
public class EngineServlet extends HttpServlet {
	
	Logger logger = Logger.getLogger("SpaceBiz");
	
	
	  @Override
	  public void doGet(HttpServletRequest req, HttpServletResponse resp)
	          throws IOException {
	    resp.setContentType("text/plain");
	    resp.getWriter().println("EngineServlet.doGet() running");
	    logger.info("EngineServlet.doGet() running");
	  }
	}
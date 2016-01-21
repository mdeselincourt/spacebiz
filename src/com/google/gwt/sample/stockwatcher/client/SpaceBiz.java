package com.google.gwt.sample.stockwatcher.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;

// New main Class for EntryPoint
public class SpaceBiz implements EntryPoint {

	final static String CONTENT_DIV = "content"; // Formalise a reference to the div in the page template
	
	public void onModuleLoad() {
		
		// Start a logger
		Logger logger = Logger.getLogger("SpaceBiz");
	    logger.log(Level.INFO, "Client code executing in browser. SpaceBiz.onModuleLoad() is running.");
	    
	    // Navigation controller
	    History.addValueChangeHandler(new NavigationHandler());
	    
	    // Fire up the history system. I don't know why we need to check the length...
	    String initToken = History.getToken();
	    if (initToken.length() == 0) {
	    	logger.log(Level.INFO, "Defaulting history state to homepage");
	    	History.newItem("homepage");
	    }
	    
		// Use the ContentContainer singleton to draw the HomePage
		// I'm having some trouble with types, I'd prefer not to instantiate this anonymously here :\
	    
	    //logger.log(Level.INFO, "Instructing the Content Container to draw the HomePage");
	    //ContentContainer.getInstance().setDivContent(new HomePage(), CONTENT_DIV);
	    
	}
	
}

package com.google.gwt.sample.stockwatcher.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

public class NavigationHandler implements ValueChangeHandler<String> {

	final FoundPage foundpage = new FoundPage();
	final HomePage homepage = new HomePage();
	final MarketPage marketpage = new MarketPage();
	final Logger logger = Logger.getLogger("NavigationHandler");
	
	String appPage;
	String[] args;
	Content appPageReference;
	
	String command = "";
	
	/**
	 * Currently supports:
	 * 
	 * found!{command}!... where command can be "newplayer" to show new player form
	 * homepage
	 * market
	 * TODO: manual
	 */
	@Override
	public void onValueChange(ValueChangeEvent<String> event) 
	{
		// We've extended ValueChangeEvent<String> so we can be sure event is a String (right?)
        String historyToken = event.getValue();
        
        logger.log(Level.INFO, "NavigationHandler.onValueChange(" + historyToken + ") running");
        
        appPage = "";
        args = new String[] {};
        appPageReference = null;
        command = "";
        
        if (historyToken.contains("!"))
        {
        	String[] parts = historyToken.split("!");
        	appPage = parts[0];
    
        	command = parts[1];
        }
        else
        {
        	appPage = historyToken;
        	command = "";
        }
        
        logger.log(Level.INFO, "appPage = " + appPage);
        logger.log(Level.INFO, "command = " + command);
	    
	    
        // Get a reference to the singleton ContentController
        ContentContainer contentContainer = ContentContainer.getInstance(); 
        
        // Parse the history token
        if (appPage == "found") { 
        	
        	logger.log(Level.INFO, "loading found page");
        	
        	if (command == "newplayer")
        	{
        		logger.log(Level.INFO, "Revealing new player form");
        		foundpage.showNewPlayer();
        		
        	}
        	else
        	{
        		logger.log(Level.INFO, "Hiding new player form");
        		foundpage.hideNewPlayer();
        	}
        	
        	appPageReference = foundpage;
        }
        else if (historyToken == "homepage") { appPageReference = homepage; } // TODO: UN NULL THIS
        else if (historyToken == "manual") { } // TODO: UN NULL THIS
        else if (historyToken == "market") { appPageReference = marketpage; }
        
        logger.log(Level.INFO, "NavigationHandler redrawing content div");
        contentContainer.setDivContent(appPageReference, "content");
	}
		
}

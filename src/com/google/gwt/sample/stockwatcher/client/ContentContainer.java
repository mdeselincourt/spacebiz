package com.google.gwt.sample.stockwatcher.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.client.ui.RootPanel;

public class ContentContainer {
	
	private static ContentContainer instance = null;
	
	protected ContentContainer() {} // Don't instantiate me unless I'm myself
	
	public static ContentContainer getInstance() {
		if(instance == null) {
			instance = new ContentContainer();
		}
		return instance;
	}
	
	// 
	public void setDivContent(Content c, String div) {
		
		// So c would be an implementation of Content, which is an implementation of Composite, which is actually a widget
		// and we just need to put that widget into the appropriate place of the static HTML framework...
		
		// Get the div we need to redraw
		RootPanel appContentDivPanel = RootPanel.get(div);
		
		// Log a severe code error if we've attempted to draw into a div that doesn't exist!
		if (appContentDivPanel == null) { 
			Logger logger = Logger.getLogger("SpaceBiz");
		    logger.log(Level.SEVERE, "ContentContainer.setDivContent() used on a div id not found (" + div + ")");
		} 
		
		// Let's start by just attempting to wipe the content div
		appContentDivPanel.clear(); 
		
		// Draw the content
		appContentDivPanel.add(c);
	}
	
}

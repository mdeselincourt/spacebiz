package com.google.gwt.sample.stockwatcher.client;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

// TODO: IS THIS USED AT ALL or is it leftover code from before I understood
//  we could use GAE's native login

// Class
public class LoginPage extends Content {
	
	//// Instantiate the widgets that live in this page ////
	
	// A label
	private Label loginPageHeader = new Label("Login panel");
	
	// Constructor. 
	public LoginPage() {
		
		//// INSTANTIATE WIDGETS

		// MarketPage's root is a vertical panel
		VerticalPanel pageRootPanel = new VerticalPanel();
		
		// User access stuff
		
		// TODO: Derp
		
		//// ASSEMBLE THE PAGE ////
		pageRootPanel.add(loginPageHeader);
		
		//// AND WE'RE DONE ////
		initWidget(pageRootPanel);
	}
	
	
}

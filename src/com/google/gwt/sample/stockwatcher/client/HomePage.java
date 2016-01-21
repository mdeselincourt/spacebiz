package com.google.gwt.sample.stockwatcher.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.stockwatcher.client.data.Player;
import com.google.gwt.sample.stockwatcher.client.services.PlayerService;
import com.google.gwt.sample.stockwatcher.client.services.PlayerServiceAsync;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * HomePage
 * @author LONOMS
 *
 */
public class HomePage extends Content {
	
	// Declare widgets
	VerticalPanel pageRootPanel;	// Create a vertical panel to be our outermost container
	Hyperlink manualLink; 	// Main menu items
	Hyperlink joinLink;
	Hyperlink marketLink;
	String userId;
	// Services
	private PlayerServiceAsync playerServiceAsync = GWT.create(PlayerService.class);
	//
	Logger logger;
	
	public HomePage() {	

		logger = Logger.getLogger(HomePage.class.toString());
		
		logger.info("HomePage() constructing");
		
		// Instantiate widgets
		// This may need to call the server for some user information later?
		instantiateWidgets();
		
		// We'll need the user's account details to send them to the right place
		adaptToUserState();
		
		// Assemble page
		pageRootPanel.add(joinLink);
		pageRootPanel.add(manualLink);
		pageRootPanel.add(marketLink);
		
		// Draw page
		initWidget(pageRootPanel);
	}
	
	/**
	 * 
	 */
	private void adaptToUserState() {
		
		logger.info("HomePage.adaptToUserState() executing");
		
		// 1. Initialise the service proxy
		if (playerServiceAsync == null) {
			playerServiceAsync = GWT.create(PlayerService.class);
		}
		// 2. Set up the callback object (GWT standard, but uses generics for my stuff)
		AsyncCallback<Player> playerCallback = new AsyncCallback<Player>() {

			// Start a logger
			Logger logger = Logger.getLogger(HomePage.class.toString());
			
			// 2.a() react to failures
			public void onFailure(Throwable exception) {
				logger.log(Level.SEVERE, "Failure returned by the Player service!");
			}

			// 2.b() react to success
			public void onSuccess(Player player) {
				
				if (player == null) {
					// We found no player! need to create one
					logger.log(Level.INFO, "No Player found");
					
					joinLink.setText(StringLiterals.NEW_PLAYER);
					joinLink.setTargetHistoryToken("found!newplayer");
				}
				else
				{ 
					// Yes we found a player 
					userId = player.getUserName();
					logger.log(Level.INFO, "Player service returned Player " + userId);
					
					joinLink.setText(StringLiterals.RESUME_GAME);
				}
			}
			
		}; // End of inline AsyncCallback
		
		// 3. Call the service, by calling the method on the class that "proxies" the servic
		playerServiceAsync.getUserPlayer(playerCallback);
		
	}
	

	private void instantiateWidgets() {
		
		// Draw the page in its pre-service-response form

		// These are HISTORY MODULE "hyperlinks" which rewrites the # element of the URL, I believe.  
		
		pageRootPanel = new VerticalPanel();
		joinLink = new Hyperlink("-JOIN-", "found");
		manualLink = new Hyperlink("-MANUAL-", "manual");
		marketLink = new Hyperlink("-MARKET (test!!)-", "market"); // TODO: This is a test!
	}
	
}

package com.google.gwt.sample.stockwatcher.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.mortbay.log.Log;

import com.google.gwt.core.client.GWT;
import com.google.gwt.sample.stockwatcher.client.data.Player;
import com.google.gwt.sample.stockwatcher.client.services.PlayerService;
import com.google.gwt.sample.stockwatcher.client.services.PlayerServiceAsync;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * MainGamePage view
 * 
 * @extends Content so that the navigation handler can load it 
 * @implements PlayerDisplayer so it can be instructed to display a Player 
 * 
 * @author LONOMS
 *
 */
public class MainGamePage extends Content implements PlayerDisplayer {
	
	// Declare widgets
	VerticalPanel pageRootPanel;	// Create a vertical panel to be our outermost container
	Label userGreetingLabel;
	Hyperlink manualLink; 	// Main menu items
	Hyperlink joinLink;
	Hyperlink marketLink;
	Label devNotesLabel;
	// Properties of the player's Player.User
	String gaeUserEmail; // a GAE User property referenced by Player
	String userHandle; // likewise 
	// Services
	private PlayerServiceAsync playerServiceAsync = GWT.create(PlayerService.class);
	// 
	Logger logger;
	
	/**
	 * This is the CONSTRUCTOR, it only fires the FIRST time the page is loaded and shown. 
	 * 
	 * For functions to be executed upon showing the page, use the refresh() method you designed.
	 */
	public MainGamePage() {	

		logger = Logger.getLogger(MainGamePage.class.toString());
				
		// Instantiate widgets
		// This may need to call the server for some user information later?
		instantiateWidgets();
		
		// Assemble page
		pageRootPanel.add(userGreetingLabel);
		pageRootPanel.add(joinLink);
		pageRootPanel.add(manualLink);
		pageRootPanel.add(marketLink);
		pageRootPanel.add(devNotesLabel);
		
		// Now the page is ready, run the logic to make sure the content is right before we draw it
		// 19-9-2016 For some reason this is getting called twice which is odd since it's my own method and not a standard part of GWT...
		refresh();
		
		// Draw page
		initWidget(pageRootPanel);
	}
	
	/**
	 * Exposing a public "refresh" method which I might make part of all Content classes 
	 */
	public void refresh() {
		
		logger.info("MainGamePage.refresh() executing");
		
		adaptToUserState();
	}
	
	/**
	 * This specifically contains functions that handle the /user state part/ of refreshing
	 * 
	 *  Current design: to call the server Player service and get as much information about the 
	 *  Player as possible, then show it on the page.
	 */
	private void adaptToUserState() {
		
		logger.info("HomePage.adaptToUserState() executing");
		
		// 1. Initialise the service proxy
		if (playerServiceAsync == null) {
			playerServiceAsync = GWT.create(PlayerService.class);
		}
		
		// 2. Set up a callback object
		AsyncCallback<Player> playerCallback = new GetPlayerCallbackHandler(this);
		
		// 3. Call the service
		logger.info("HomePage.adaptToUserState(): Calling GETUserPlayer() method");
		playerServiceAsync.getUserPlayer(playerCallback);
		
//		TODO: OLDER, WORKING IMPLEMENTATION OF THE SERVICE HANDLING FOLLOWS! REMOVE IF NEW IMPL IS WORKING
//
//		// PART ONE - GET THE PLAYER DATA FROM THE SERVER
//		
//		// 1. Initialise the service proxy
//		if (playerServiceAsync == null) {
//			playerServiceAsync = GWT.create(PlayerService.class);
//		}
//		
//		// Get a reference to ourself so we can put it in our anonymous inner callback 
//		// object. 
//		PlayerDisplayer homepage = this;
//		
//		// 2. Set up the callback object (GWT standard, but uses generics for my stuff)
//		// !!! This callback object defines what to do when the get player information call completes !!!
//		AsyncCallback<Player> playerCallback = new AsyncCallback<Player>() {
//			
//			// Start a logger
//			Logger logger = Logger.getLogger(HomePage.class.toString());
//					
//			// 2.a() react to failures
//			public void onFailure(Throwable exception) {
//				logger.log(Level.SEVERE, "Callback: Failure returned by the Player.getUserPlayer() service!");
//				
//				logger.log(Level.SEVERE, "Exception messge = " + exception.getMessage());
//			}
//
//			// 2.b() react to success of the service call. ***
//			public void onSuccess(Player player) {
//				
//				/** 
//				 * Upon success, call back to the "parent" page with the results.
//				 */
//				homepage.displayPlayer(player); 
//				
//  				logger.log(Level.INFO, "Callback: Success returned by the Player.getUserPlayer() service!");
// 
//				Response handling code moved out, in theory, to homepage.displayPlayer()
//  			Preserved during testing
//  				
//				if (player == null) {
//					// We found no player!
//					logger.log(Level.INFO, "adaptToUserState().anonymous-callback-handler: Player search returned null - there isn't one");
//					
//					gaeUserEmail = "unidentified user";
//					
//					userGreetingLabel.setText("Welcome, '" + gaeUserEmail + "'");
//					joinLink.setText(StringLiterals.NEW_PLAYER);
//					joinLink.setTargetHistoryToken("found!newplayer");
//				}
//				else
//				{
//					// Yes we found a player
//					gaeUserEmail = player.getGaeUserEmail();
//					userHandle = player.getHandle();
//					logger.log(Level.INFO, "Player service returned Player with User with email " + gaeUserEmail + " and handle " + userHandle);
//					
//					userGreetingLabel.setText("Welcome, '" + userHandle + "' (" + gaeUserEmail + ")");
//					joinLink.setText(StringLiterals.RESUME_GAME);
//				}
//			}
//			
//		}; // End of inline AsyncCallback
//		
//		logger.info("HomePage.adaptToUserState() Calling getUserPlayer() method");
//		
//		// 3. Call the service, by calling the method on the class that "proxies" the service
//		playerServiceAsync.getUserPlayer(playerCallback);
//		
//		// REMEMBER: This is async, the code that subsequently fires is ABOVE.
	}

	/**
	 * 
	 * First attempt at implementing a model where pages can be instructed 
	 * to display new objects; the theory being that the activity calling
	 * the methods would often be callback listeners who have just recieved
	 * responses to service calls.
	 * 
	 * The theory is that this method will implement an interface PlayerDisplayer 
	 * which identifies UI objects (pages in this case) capable of carrying out such 
	 * an action. 
	 * 
	 * @param p Player object to display
	 */
	
	public void displayPlayer(Player player) {
		
		logger.log(Level.INFO, "MainGamePage.displayPlayer() invoked and executing");
		
		if (player == null) {
			// We've been asked to display a null Player!
			// In the current design (22 Sep 2016) this implies we are probably
			// dealing with a user who is logged in but has no Player object
			// created so hasn't "created an account" with US, only with Google (to whom
			// we have delegated login, see elsewhere)
			
			userGreetingLabel.setText("¿¿¿Welcome unknown user");
			joinLink.setText(StringLiterals.NEW_PLAYER);
			joinLink.setTargetHistoryToken("found!newplayer");
		}
		else
		{
			// We've been asked to display a player
			gaeUserEmail = player.getGaeUserEmail();
			userHandle = player.getHandle();
			
			logger.log(Level.INFO, "MainGamePage displaying Player: email = " + gaeUserEmail + " and handle = " + userHandle);
			
			userGreetingLabel.setText("¿¿¿Welcome, '" + userHandle + "' (" + gaeUserEmail + ")");
			joinLink.setText(StringLiterals.RESUME_GAME);
			joinLink.setTargetHistoryToken("maingamepage");
		}
		
	}
	
	/**
	 * This is designed to be an encapsulated part of the page construction i.e. on the first time you view the page only.
	 * 
	 * You designed the class with a refresh() method for just updating.
	 */
	private void instantiateWidgets() {
		
		// These are HISTORY MODULE "hyperlinks" which rewrites the # element of the URL, I believe.  
		
		pageRootPanel = new VerticalPanel();
		
		gaeUserEmail = "¿¿¿*LOGGING YOU IN...*"; // TODO: This is a bit of a cheap hack since if this ever gets checked against the datastore it will obviously fail 
		userGreetingLabel = new Label("¿¿¿Welcome, user Id '" + gaeUserEmail + "'");
		joinLink = new Hyperlink("¿¿¿-JOIN-", "found");
		manualLink = new Hyperlink("¿¿¿-MANUAL-", "manual");
		marketLink = new Hyperlink("¿¿¿-MARKET (test!!)-", "market"); // TODO: This is a test!
		devNotesLabel = new Label("¿¿¿DEV NOTES: THIS IS DUMMY CODE BORROW FROM HOMEPAGE");
	}
}

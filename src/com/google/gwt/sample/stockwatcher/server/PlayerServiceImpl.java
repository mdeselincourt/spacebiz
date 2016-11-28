package com.google.gwt.sample.stockwatcher.server;

import java.util.logging.Logger;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import com.google.apphosting.api.ApiProxy.LogRecord.Level;

import com.google.appengine.api.users.UserServiceFactory;

import com.google.gwt.sample.stockwatcher.client.data.Business;
import com.google.gwt.sample.stockwatcher.client.data.Player;
import com.google.gwt.sample.stockwatcher.client.data.World;
import com.google.gwt.sample.stockwatcher.client.services.BusinessService;
import com.google.gwt.sample.stockwatcher.client.services.PlayerService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Result;
import com.googlecode.objectify.VoidWork;
import com.googlecode.objectify.Work;




import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * 
 * @author MichaelDeSelincourt
 *
 * @see #getPlayer(String)
 * @see #getUserPlayer() 
 *
 */
public class PlayerServiceImpl extends RemoteServiceServlet implements PlayerService {

	/** Best practice solution for registering classes in Objectify: 
	 * This ensures the timing for registering is optimal (only once, when needed)
	 * (A static block is executed when the class is loaded into memory)
	 */
	static {
		ObjectifyService.register(Player.class);
	}
	
	private static final Logger log = Logger.getLogger(PlayerServiceImpl.class.getName());

	/**
	 * Fetch a Player from the Datastore
	 * 
	 * Needs to be registered in web.xml
	 * 
	 * Returns a Player object.
	 */
	@Override
	public Player getPlayer(final String userId) {

		Work<Player> retrievePlayerWork = new Work<Player>() {
			public Player run() {
				log.info("PlayerServiceImpl.getPlayer(" + userId + ") is executing; that is, userId = '" + userId + "'");

				// THIS --> SHOULD BE OBSOLETE thanks to the static block at the top: ObjectifyService.register(Player.class); 

				String id = userId;
				Class kind = Player.class;

				Key<Player> playerKey = Key.create(null, kind, id); // (parent, class, name)

				Result<Player> playerResult = ofy().load().key(playerKey);

				return playerResult.now();
			} // end of run()	
		}; // end of Work
		
		log.info("Server getPlayer(userId) returning result...");
		return ObjectifyService.run(retrievePlayerWork);
	}

	@Override
	public Player getUserPlayer() {

		Work<Player> retrievePlayerWork = new Work<Player>() {
			public Player run() {

				log.info("PlayerServiceImpl.getUserPlayer() is executing...");

				// THIS SHOULD BE OBSOLETE thanks to the static block at the top: ObjectifyService.register(Player.class); 

				UserService userService = UserServiceFactory.getUserService();
				User gaeUser = userService.getCurrentUser();
				
				if (gaeUser != null)
				{
					String gaeUserEmail = gaeUser.getEmail();
					
					log.info("Current gaeUser is not null. Fetching Player for @Id gaeUserEmail '" + gaeUserEmail + "'");
					
					Class kind = Player.class;

					Key<Player> userPlayerKey = Key.create(null, kind, gaeUserEmail); // Construct key for query

					Result<Player> userPlayerResult = ofy().load().key(userPlayerKey); // Query with key

					log.info("Executing query for current user's Player.");
					
					Player p = userPlayerResult.now(); // ... execute query, return a Player. Synchronously?!?
					
					// Code to be conducted on the player following retrieval from objectify
					if (p != null) {
						
						// There isn't any
						
					}
					
					log.info("PlayerServiceImpl.getUserPlayer() User's Player retrieved, in theory");
					
					return p; // Might be null; am intentionally NOT catching this here but leaving it for
					// the calling function to decide what to do with it.
				}
				else
				{
					log.warning("User's Player is Null! getUserPlayer() returning null");

					return null;
				}
			} // end of run()
		}; // end of work
		
		log.info("PlayerServiceImpl.getUserPlayer() is executing player retrieval query (code above)");
		
		return ObjectifyService.run(retrievePlayerWork);
		
	} // end of getUserPlayer()
	
	/**
	 * Store THE CURRENT player in the datastore, by invoking client.services.playerService
	 * 
	 * @param key Key to store player in datastore
	 * @param player Initialised Player object
	 *  User object and stored.
	 * @return The key of the new player
	 */
	public Key<Player> putPlayer(final Player player, final String key) {
		
		// Fetch the current user's user details
		UserService userService = UserServiceFactory.getUserService();
		User thisGaeUser = userService.getCurrentUser();
		
		// Store the current user's ID as the ID of the Player object that we are creating
		player.setGaeUserEmail(thisGaeUser.getEmail());
		
		// An item of put player work, which result in the key of the new player
		Work<Key<Player>> putPlayerWork = new Work<Key<Player>>() {
		
			// Run the put player work
			public Key<Player> run() {
			
				log.info("Method to store Player object is executing...");
						
				Result<Key<Player>> newPlayerKeyResult = ofy().save().entity(player); // This is where the magic happens
				 
				return newPlayerKeyResult.now();
			} // end of run()
		}; // end of work
		
		return ObjectifyService.run(putPlayerWork); // Run the transaction and end the method, returning the result (key<Player> TODO: might want to be Player?)
	}
}



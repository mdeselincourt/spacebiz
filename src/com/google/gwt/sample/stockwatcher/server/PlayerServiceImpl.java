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

public class PlayerServiceImpl extends RemoteServiceServlet implements PlayerService {

	private static final Logger log = Logger.getLogger(PlayerServiceImpl.class.getName());

	/**
	 * Fetch a Player from the Datastore
	 * 
	 * Needs to be registered in web.xml
	 */
	@Override
	public Player getPlayer(final String userId) {

		Work<Player> retrievePlayerWork = new Work<Player>() {
			public Player run() {
				log.info("PlayerServiceImpl.getPlayer(" + userId + ") is executing...");

				ObjectifyService.register(Player.class); 

				String id = userId;
				Class kind = Player.class;

				Key<Player> playerKey = Key.create(null, kind, id); // (parent, class, name)

				Result<Player> playerResult = ofy().load().key(playerKey);

				return playerResult.now();
			} // end of run()	
		}; // end of Work
		
		return ObjectifyService.run(retrievePlayerWork);
	}

	@Override
	public Player getUserPlayer() {

		Work<Player> retrievePlayerWork = new Work<Player>() {
			public Player run() {

				log.info("PlayerServiceImpl.getUserPlayer() is executing...");

				ObjectifyService.register(Player.class); 

				UserService userService = UserServiceFactory.getUserService();

				User user = userService.getCurrentUser();

				if (user != null)
				{
					String userId = user.getUserId();
					Class kind = Player.class;

					Key<Player> userPlayerKey = Key.create(null, kind, userId);

					Result<Player> userPlayerResult = ofy().load().key(userPlayerKey);

					return userPlayerResult.now();
				}
				else
				{
					log.warning("User's Player is Null! getUserPlayer() returning null");

					return null;
				}
			} // end of run()
		}; // end of work
		
		return ObjectifyService.run(retrievePlayerWork);
	}
}



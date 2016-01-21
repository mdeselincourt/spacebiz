package com.google.gwt.sample.stockwatcher.server;

import java.util.logging.Logger;

import com.google.gwt.sample.stockwatcher.client.data.Business;
import com.google.gwt.sample.stockwatcher.client.data.World;
import com.google.gwt.sample.stockwatcher.client.services.BusinessService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Result;
import com.googlecode.objectify.VoidWork;
import com.googlecode.objectify.Work;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class BusinessServiceImpl extends RemoteServiceServlet implements BusinessService {

	private static final Logger log = Logger.getLogger(BusinessServiceImpl.class.getName());
	
	/**
	 * Create a business
	 * 
	 * 1. EXPAT activity: check whether world exists
	 * 2. If not, create world
	 * 3. EXPAT activity: check whether entrepreneur exists in world for this player
	 * 4. If not, create entrepreneur
	 * 5. Check whether company exists
	 * 6. If not, create it
	 */
	@Override
	public String postBusiness(Business newBusiness) {
		
		log.info("BusinessServiceImpl.postBusiness() is executing");
		
		// 1. Check whether the world exists
		
		// Retrieve the world
		
		////////////////// MAKE THIS A FUNCTION //////////////////
		
		Work<World> retrieveWorldWork = new Work<World>() {
			public World run() {
				log.info("Registering class with ObjectifyService");
				
				ObjectifyService.register(World.class);
				 
				log.info("Creating a Key");
				
				Key<World> world1Key = Key.create(World.class, "1");
				
				log.info("Loading the result");
				
				// TODO: THIS is where it goes wrong!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				Result<World> worldResult = ofy().load().key(world1Key);
				
				log.info("Materialising the World from the Result");
				
				World w = worldResult.now();

				return w;
			}
		};

		World world = ObjectifyService.run(retrieveWorldWork);
				
		String resultString;
		
		if (world == null) 
		{
			log.info("World is null!");
			resultString = "World is null...";
		}
		else
		{
			log.info("World is not null!");
			resultString = "World is not null!";
		}
		
		// TODO: Finish implementing this!
		return resultString;
	}

	
	
}



package com.google.gwt.sample.stockwatcher.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.sample.stockwatcher.client.data.Player;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Class that will wait for a method response then call views with the results.
 * Usage: instantiate this with a reference to a page that implements PlayerDisplayer,
 * and when the call is complete displayPlayer(player) will be called on the page Currently only supports one view, but there's no reason why it couldn't iterate through a collection
 * 
 * @author MichaelDeSelincourt
 *
 */
public class GetPlayerCallbackHandler implements AsyncCallback<Player> {

	// Hold reference to (first) view which should display this
	PlayerDisplayer view1; 
	
	// Always have a logger
	Logger logger = Logger.getLogger(GetPlayerCallbackHandler.class.toString());
		
	/**
	 * Marks a page (or anything that implements PlayerDisplayer) for sync callback update
	 * 
	 * Usage: Construct this object with a reference to the 'page' (anything that
	 * implements PlayerDisplayer) that you want to have updated when a response comes in
	 * and when that response arrives, displayPlayer(player) will be invoked on it 
	 * 
	 * Currently only supports one view but in future could be extended to iterate through a collection
	 * 
	 * @param playerDisplayer1
	 */
	public GetPlayerCallbackHandler(PlayerDisplayer playerDisplayer1) {
		super();
		this.view1 = playerDisplayer1;
	}
	
	// React to failures
	@Override
	public void onFailure(Throwable exception) {
		logger.log(Level.SEVERE, "Callback: Failure returned by the Player.getUserPlayer() service!");
		logger.log(Level.SEVERE, "Exception messge = " + exception.getMessage());
		
		// Send a null to the view, as if we got a success but it found nothing. 
		// TODO: Showing the error would be good! Maybe I could have a view Interface called ErrorDisplayer!
		view1.displayPlayer(null);	
	}

	// React to success.
	@Override
	public void onSuccess(Player result) {
		view1.displayPlayer(result);
	}
	
}

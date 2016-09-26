package com.google.gwt.sample.stockwatcher.client;

import com.google.gwt.sample.stockwatcher.client.data.Player;

/**
 * An interface intended to unify all (UI) objects that are capable of displaying
 * a <this app>.client.data.Player object. 
 * 
 * The purpose is to allow callback listeners to be created and assigned links
 * to UI objects to call when they fire, without having to hard-code exactly which
 * UI objects those are into the listener class.
 * 
 * @author MichaelDeSelincourt
 *
 */
public interface PlayerDisplayer {

	public void displayPlayer(Player p);
	
}

package com.google.gwt.sample.stockwatcher.client.data;

import java.io.Serializable;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity // Necessary for serialisation?
public class Player implements Serializable {
	
	private static final long serialVersionUID = -564507070948451718L; // Necessary for serialisation
	
	@Id String gaeUserEmail; // @Id necessary for GWT-type serialisation?
	// User gaeUser; // The GAE User class. Removed because it can't be serialised!
	Key<Business> activeBusiness;
	String handle;
	
	/**
	 * You need to call setGaeUser(User x) for this class to serialise correctly 
	 */
	public Player() {
		super();// This blank constructor is needed for Serialisation, (but it's risky because you have to set the ID after building or there'll be a runtime failure upon serialisation!)
	}
		
	public void setGaeUserEmail(String gue) {
		gaeUserEmail = gue;
	}
	
	public String getGaeUserEmail() {
		return gaeUserEmail;
	}
	
	/**
	 * Never yet truly implemented
	 * @return
	 */
	public Key<Business> getActiveBusiness() {
		return activeBusiness;
	}
	
	/**
	 * Never yet truly implemented
	 * @param activeBusiness
	 */
	public void setActiveBusiness(Key<Business> activeBusiness) {
		this.activeBusiness = activeBusiness;
	}
	
	public String getHandle() {
		return handle;
	}

	/**
	 * Sets the game-local 'handle', not to be confused with the GAE-general 
	 *  user "nickname" or userId or email or otherwise
	 * @param handle
	 */
	public void setHandle(String handle) {
		this.handle = handle;
	}


}
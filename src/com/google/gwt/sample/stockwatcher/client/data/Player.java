package com.google.gwt.sample.stockwatcher.client.data;

import java.io.Serializable;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Player implements Serializable {
	@Id String userName;
	Key<Business> activeBusiness;
	String handle;
	
	// This is needed for Serialisation
	public Player() {
		super();
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Key<Business> getActiveBusiness() {
		return activeBusiness;
	}
	public void setActiveBusiness(Key<Business> activeBusiness) {
		this.activeBusiness = activeBusiness;
	}
	public String getHandle() {
		return handle;
	}
	public void setHandle(String handle) {
		this.handle = handle;
	}
}
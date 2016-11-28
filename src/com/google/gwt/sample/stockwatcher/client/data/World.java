package com.google.gwt.sample.stockwatcher.client.data;

import java.io.Serializable;
import java.util.HashMap;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;


@Entity
public class World implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6510553326616512417L;
	@Id Long id;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashMap<String, Double> getMarketPrices() {
		return marketPrices;
	}

	public void setMarketPrices(HashMap<String, Double> marketPrices) {
		this.marketPrices = marketPrices;
	}

	String name = "The Only World";
	HashMap<String, Double> marketPrices;
	
	public World() {
		super();
		
		// TODO: Dummy code that initialises some static world attributes
		marketPrices = new HashMap<String, Double>();
		marketPrices.put("ANR:NBN", 5.0);
	}
		
}

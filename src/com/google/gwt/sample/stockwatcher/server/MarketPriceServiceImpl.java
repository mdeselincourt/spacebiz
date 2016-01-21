package com.google.gwt.sample.stockwatcher.server;

import com.google.gwt.sample.stockwatcher.client.MarketPrice;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class MarketPriceServiceImpl extends RemoteServiceServlet implements com.google.gwt.sample.stockwatcher.client.MarketPriceService {

	// My constructor for logging purposes only
	public MarketPriceServiceImpl() {
		super();
		System.out.println("Simple logging: MarketPriceServiceImpl instantiated");
	}
	
	@Override
	public MarketPrice[] getPrices() {
		
		System.out.println("Simple logging: MarketPriceServiceImpl instantiated");
		
		// TODO: This will need to get prices out of an actual server-side persistent storage service! But for now...
		
		MarketPrice mp1 = new MarketPrice("Water", 1);
		MarketPrice mp2 = new MarketPrice("Oxygen", 4.5);
		MarketPrice mp3 = new MarketPrice("Hydrogen", 2.25);
		
		MarketPrice[] prices = new MarketPrice[] {mp1, mp2, mp3};
		
		return prices;
	}
	
}

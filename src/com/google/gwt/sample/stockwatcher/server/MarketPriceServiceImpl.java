package com.google.gwt.sample.stockwatcher.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

import com.google.gwt.sample.stockwatcher.client.MarketPrice;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class MarketPriceServiceImpl extends RemoteServiceServlet implements com.google.gwt.sample.stockwatcher.client.MarketPriceService {

	private static final Logger log = Logger.getLogger(PlayerServiceImpl.class.getName());
	
	// My constructor for logging purposes only
	public MarketPriceServiceImpl() {
		super();
		System.out.println("Simple logging: MarketPriceServiceImpl instantiated");
	}
	
	@Override
	public MarketPrice[] getPrices() {
		
		System.out.println("Simple logging: MarketPriceServiceImpl instantiated");
		
		// TODO: THIS IS THE PLACE I've decided to test URL
		
		try {
		
		URL currencyServiceUrl;
		
		currencyServiceUrl = new URL("https://openexchangerates.org/api/latest.json?app_id=2e730c86725a4572bf6e622df0bbd18c");
		
		// Open the URL
		URLConnection currencyServiceUrlConnection = currencyServiceUrl.openConnection();
		
		// Process the stream
		BufferedReader inReader = new BufferedReader(new InputStreamReader(currencyServiceUrlConnection.getInputStream()));
		
		String inputLine;
		
		while ((inputLine = inReader.readLine()) != null)
			{ log.info(inputLine); }
		
		inReader.close();
		
		} catch (IOException e) {
			
			log.severe(e.getMessage());
		}
		
		
		
		
		
		// TODO: This will need to get prices out of an actual server-side persistent storage service! But for now...
		MarketPrice mp1 = new MarketPrice("Water", 1);
		MarketPrice mp2 = new MarketPrice("Oxygen", 4.5);
		MarketPrice mp3 = new MarketPrice("Hydrogen", 2.25);
		
		MarketPrice[] prices = new MarketPrice[] {mp1, mp2, mp3};
		
		return prices;
	}
}
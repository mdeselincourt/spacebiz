package com.google.gwt.sample.stockwatcher.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.gargoylesoftware.htmlunit.javascript.host.Console;
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
		
		/**
		// JSON PARSING PART 1
		URI uri = new URI("https://www.google.com/finance/info?q=LON:PNN,NYSE:ACN");
		JSONTokener tokener = new JSONTokener(uri.toURL().openStream());
		JSONObject responseJsonRoot = new JSONObject(tokener); // WAIT THIS WON'T WORK DUE TO GOOGLE JSON BEING COMMENTED OUT
		
		JSONArray responseResults = responseJsonRoot;
		
		String gf_t = responseJsonRoot.getString("t");
		String gf_l = responseJsonRoot.getString("l");
		*/
		
		// MANUAL LINE BY LINE STREAM PROCESSING {needs to all be in a try-catch)

		// Get a URL
		URL currencyServiceUrl;
		currencyServiceUrl = new URL("https://www.google.com/finance/info?q=LON:PNN,NYSE:ACN");
		
		// Open the URL
		URLConnection currencyServiceUrlConnection = currencyServiceUrl.openConnection();
		
		// Prepare a string to recieve all the data
		String input = "";
		
		// Get the stream
		BufferedReader inReader = new BufferedReader(new InputStreamReader(currencyServiceUrlConnection.getInputStream()));
		String inputLine;

		// Process the stream line by line
		while ((inputLine = inReader.readLine()) != null) { 
		//	log.info(inputLine);
			input += inputLine;
		}
		
		inReader.close();
		
		//log.info("All input:" + input);
		
		// If the first three characters of the input are "// ", remove it and the following space; this is Google being awkward
		if(input.substring(0,3).equals("// ")) { 
			input = input.substring(3); 
			//log.info("Trimming input to:" + input); 
		}
		
		//log.info("new JSONArray(" + input + ");");
		JSONArray resultsArray = new JSONArray(input);
		
		JSONObject first = resultsArray.getJSONObject(0);
		
		String ticker = first.getString("t");
		
		log.info("Ticker:" + ticker);
		
		// JSONArray resultsArray = new JSONArray(resultsRoot.getString(""));
				
		// end of try
		} catch (Exception e) {
			
			log.severe(e.getMessage());
		}// end of try-catch
		
		// TODO: This will need to get prices out of an actual server-side persistent storage service! But for now...
		MarketPrice mp1 = new MarketPrice("Water", 1);
		MarketPrice mp2 = new MarketPrice("Oxygen", 4.5);
		MarketPrice mp3 = new MarketPrice("Hydrogen", 2.25);
		
		MarketPrice[] prices = new MarketPrice[] {mp1, mp2, mp3};
		
		return prices;
	}
}
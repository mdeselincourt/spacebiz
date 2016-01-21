package com.google.gwt.sample.stockwatcher.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

// This is an interface for a service; you have to implement it with an Impl
@RemoteServiceRelativePath("marketPrices")
public interface MarketPriceService extends RemoteService {
	
	// Implement this method to allow the service to getPrices
	MarketPrice[] getPrices();
	
}

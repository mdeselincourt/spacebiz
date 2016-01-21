package com.google.gwt.sample.stockwatcher.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MarketPriceServiceAsync {
	void getPrices(AsyncCallback<MarketPrice[]> callback);
}

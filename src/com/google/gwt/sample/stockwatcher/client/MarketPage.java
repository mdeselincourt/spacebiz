package com.google.gwt.sample.stockwatcher.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

// By implementing Content this also extends/implements Composite, which is designed to compose multiple 
// atomic widgets into a UI element
public class MarketPage extends Content {

	//// Create the widgets that live in this page ////
	private Label marketPageHeader = new Label("Market Page");

	// 0. RPC.html "Create the service proxy class"
	private MarketPriceServiceAsync marketPriceService = GWT.create(MarketPriceService.class);

	// FlexTable for prices
	
	FlexTable pricesFlexTable = new FlexTable(); 
	
	// Constructor for the page. Instantiate elements and "init" itself
	public MarketPage() {

		//// INSTANTIATE WIDGETS

		// MarketPage's root is a vertical panel
		VerticalPanel pageRootPanel = new VerticalPanel();

		pricesFlexTable.setText(0,0,"Commodity");
		pricesFlexTable.setText(0,1,"Price");
		// Style the table
		pricesFlexTable.addStyleName("bordered");

		//// Assemble the contents of the page's rootpanel

		// Add the header
		pageRootPanel.add(marketPageHeader);
		// Add the table
		pageRootPanel.add(pricesFlexTable);

		// TODO: Move this function into a scheduled loop or something instead of a here
		refreshPrices(); // Make an Async call

		// TODO: I'm offline so I'm attempting to mimic the code from the greeting service


		//// ASYNCHRONOUSLY REQUEST A PRICE FOR DISPLAY

		//...

		// Since we are being constructed, init this as a widget; this is a function we inherit from Composite.
		initWidget(pageRootPanel);
	}

	// /RPC.html section 2 :
	// TODO: NOT SURE WHERE TO DO THIS SO AM DOING IT HERE
	// Initialize the service proxy class, 
	// "set up the callback object" and 
	// Call the RP

	private void refreshPrices() {

		// 1. Initialise the service proxy
		if (marketPriceService == null) {
			marketPriceService = GWT.create(MarketPriceService.class);
		}

		// 2. Set up the callback object (GWT standard, but uses generics for my stuff)
		AsyncCallback<MarketPrice[]> callbackMarketPrices = new AsyncCallback<MarketPrice[]>() {

			// 2.a() react to failures
			public void onFailure(Throwable exception) {
				// TODO: Doesn't handle errors
			}

			// 2.b() react to success
			public void onSuccess(MarketPrice[] marketPriceArray) {
				updateTable(marketPriceArray);
			}
		}; // End of inline AsyncCallback

		// 3. Call the service, by calling the method on the class that "proxies" the service
		marketPriceService.getPrices(callbackMarketPrices);
	}

	private void updateTable(MarketPrice[] newPrices) {

		for (int i = 0; i < newPrices.length; i++) {
			pricesFlexTable.setText(i+1, 0, newPrices[i].getCommodityUniqueName());
			pricesFlexTable.setText(i+1, 1, String.valueOf(newPrices[i].getPrice()));
		}
	}
}

package com.google.gwt.sample.stockwatcher.client;

import java.io.Serializable;

/**
 * Encapsulates a reference to a commodity and a price for it. 
 *
 */
public class MarketPrice implements Serializable {
	
	// This is needed for deserialisation
	public MarketPrice() {
		super();
	}
	
	public MarketPrice(String CommodityUniqueName, double price) {
		super();
		this.CommodityUniqueName = CommodityUniqueName;
		this.price = price;
	}
	
	private String CommodityUniqueName;
	private double price;
	
	public String getCommodityUniqueName() {
		return CommodityUniqueName;
	}

	public void setCommodityUniqueName(String commodityUniqueName) {
		CommodityUniqueName = commodityUniqueName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
}

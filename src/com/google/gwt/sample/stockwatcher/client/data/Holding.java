package com.google.gwt.sample.stockwatcher.client.data;

import java.io.Serializable;

/**
 * Identifies a certain number of shares in a certain stock (e.g. 500 of NYSE:ACN) 
 * 
 * @author MichaelDeSelincourt
 *
 */
public class Holding implements Serializable, Rowable {

	public Holding(String marketCode, String tickerCode, int shares) {
		super();
		this.marketCode = marketCode;
		this.tickerCode = tickerCode;
		this.shares = shares;
	}
	public String getMarketCode() {
		return marketCode;
	}
	public void setMarketCode(String marketCode) {
		this.marketCode = marketCode;
	}
	public String getTickerCode() {
		return tickerCode;
	}
	public void setTickerCode(String tickerCode) {
		this.tickerCode = tickerCode;
	}
	public int getShares() {
		return shares;
	}
	public void setShares(int shares) {
		this.shares = shares;
	}
	/**
	 * Serialisation 
	 */
	private static final long serialVersionUID = 7223802107785044416L;

	String marketCode; // E.g. NYSE, usable to e.g. search Google Finance API
	String tickerCode; // E.g. ACN, usable to e.g. search Google Finance API
	int shares; // Number of shares this holding represents
	// No link to owner at the moment, because it will exist as a child/member of a Player
	@Override
	public String getColumnString(int c) {
		
		switch (c) {
			case 0:
				return marketCode;
			case 1:
				return tickerCode;
			case 2:
				return String.valueOf(shares);
			default:
				return null;
		}
				
	}
}


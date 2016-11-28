package com.google.gwt.sample.stockwatcher.client.data;

	/**
	*	Interface designed for 1D data objects that can be turned into a row on a grid  
	*
	*/
public interface Rowable {

		String getColumnString(int c);
	
}

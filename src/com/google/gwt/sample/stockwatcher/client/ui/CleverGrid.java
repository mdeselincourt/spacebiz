package com.google.gwt.sample.stockwatcher.client.ui;

import java.util.Vector;

import com.google.gwt.sample.stockwatcher.client.data.Rowable;
import com.google.gwt.user.client.ui.Grid;

public class CleverGrid extends Grid {

	int rows;
	int columns;
	
	public CleverGrid(int r, int c) {
		super(r, c);
		rows = r;
		columns = c;
	}
	
	public void gridifyVector(Vector<Rowable> v) {
		// Put some values in the grid cells.
		
		for (int r = 0; r < v.size(); ++r) {
			
			// If we need to add a row
			if (r == this.numRows) { this.insertRow(0); }
			
			for (int c = 0; c < this.numColumns; ++c) {
				// this.setText(r,c,"Derp " + r + "," + c);
				
				this.setText(r,c,v.get(r).getColumnString(c));
				
	    	}
		}
	}
}

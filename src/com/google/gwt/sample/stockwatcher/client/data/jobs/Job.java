package com.google.gwt.sample.stockwatcher.client.data.jobs;

import java.io.Serializable;

import com.google.gwt.sample.stockwatcher.client.data.cards.Card;
import com.google.gwt.sample.stockwatcher.client.data.cards.CardSet;

public class Job implements Serializable {

	private String description;
	private long pay;
	private int duration; 
	private CardSet cardset;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2204897242695796491L;

	public static enum Exemplar {MILK_RUN};
	
	public Job() {
		
	}
	
	public Job(Exemplar e) {
		
		Job j = new Job();
		
		switch (e) { 
			case MILK_RUN:
				j.setDescription("Earth Orbital Courier");
				//j.setPay(1.0);
				j.setDuration(1);
				CardSet s = new CardSet();
				//s.add(new Card());
				
				break;
			
		}
		
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getPay() {
		return pay;
	}

	public void setPay(long pay) {
		this.pay = pay;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
}

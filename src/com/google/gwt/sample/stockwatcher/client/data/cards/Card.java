package com.google.gwt.sample.stockwatcher.client.data.cards;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

// ENUMS are "inherently serializable"
public enum Card {
	
	//private static final long serialVersionUID = 1866560258328829065L;

	NOTHING ("Nothing happens", )
	
	private String name;
	private Map<CardVal, Integer> effects;
	
	// PRIVATE constructor for assembling from the enum
	private Card(String n, Map<CardVal, Integer> e)
	{
		this.name = n;
		this.effects = e;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<CardVal, Integer> getEffects() {
		return effects;
	}
	public void setEffects(Map<CardVal, Integer> effects) {
		this.effects = effects;
	}
	
	public enum cardExemplars  { NOTHING };
	
	/**
	 * Empty constructor (to easier support serialization)
	 */
	public Card() {
		super();
	}
	
	/**
	 * Exemplar constructor - will return an instance that you choose
	 * by passing an option from the enumeration Card.cardExemplars; 
	 * 
	 * @param e
	 */
	public Card(cardExemplars e) {
		
		switch (e) {
			
			case NOTHING:
				name = "Nothing happens";
				effects = new EnumMap<CardVal, Integer>(CardVal.class);
				break;
				
			default:
			break;
		}
	}
	
	
	// A card needs to map a fixed enum value (e.g. FLIGHT) to an integer
	// effects = new EnumMap<CardVal, Integer>(CardVal.class); 
	
	
}

package com.google.gwt.sample.stockwatcher.client.data.cards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity // needed for Objectify serialisation
public class CharacterClass implements Serializable {

	/**
	 * 
	 */
	// Metadata
	private static final long serialVersionUID = -1513907534720714189L;
	@Id String CharacterClassId; // Needed for Objectify
	// Data
	private String name;
	private String description;
	// private CardSet cardSet;
	
	/**
	 * Empty constructor for serialisation 
	 */
	public CharacterClass() {
		super();
	}
	
	

	/**
	 * This class stores its own hard coded instances in this static method
	 * 
	 * List is an interface; you have to instantiate the type you want 
	 * 
	 * @return
	 */
	public static ArrayList<CharacterClass> getCharacterClassData() {
		
		ArrayList<CharacterClass> characterClassData = new ArrayList<CharacterClass>();
		
		// RELIABLE_FIXER
		CharacterClass cc = new CharacterClass();
		cc.setAll("Reliable Fixer", "Can be relied upon to fix problems without taking undue risks.");
		characterClassData.add(cc);
		// SHOWOFF_PILOT 
		cc = new CharacterClass();
		cc.setAll("Showoff Pilot", "Pushes his or her ship to its limits.");
		characterClassData.add(cc);
		
		return characterClassData;
	}


	public void setAll(String name, String description)
	{
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}
	
}


package com.google.gwt.sample.stockwatcher.server.engine.data;

public class VesselMindState {

	private boolean remembersContact; // Single value for awareness of adversary
	private AiGoal goal; // Single value for the AI's current goal. Distinguishes "find and kill" from "kill" but not "kill with missiles" from "lure into trap" or whatever
	private double intendedCourse; // +ve = "east" = towards second fleet
	
	// placeholder: memory of opponent's properties/capabilities
	
	public boolean isRemembersContact() {
		return remembersContact;
	}
	public void setRemembersContact(boolean remembersContact) {
		this.remembersContact = remembersContact;
	}
	public AiGoal getGoal() {
		return goal;
	}
	public void setGoal(AiGoal goal) {
		this.goal = goal;
	}
	public double getIntendedCourse() {
		return intendedCourse;
	}
	public void setIntendedCourse(double intendedCourse) {
		this.intendedCourse = intendedCourse;
	}
	
}

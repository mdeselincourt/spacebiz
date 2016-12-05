package com.google.gwt.sample.stockwatcher.server.engine.data;

public class VesselMindState {

	private AIIntention intention;
	
	public VesselMindState() {
		setIntention(AIIntention.IDLE);
	}

	public AIIntention getIntention() {
		return intention;
	}

	public void setIntention(AIIntention intention) {
		this.intention = intention;
	}
	
}

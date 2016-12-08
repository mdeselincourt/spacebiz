package com.google.gwt.sample.stockwatcher.server.engine.data;

public class Mission {

	Vessel vessel;
	StandingOrders standingOrders;
	
	public Mission(Vessel v, StandingOrders s) {
		vessel = v;
		standingOrders = s;
	}

	public Vessel getVessel() {
		return vessel;
	}

	public void setVessel(Vessel vessel) {
		this.vessel = vessel;
	}

	public StandingOrders getStandingOrders() {
		return standingOrders;
	}

	public void setStandingOrders(StandingOrders stance) {
		this.standingOrders = standingOrders;
	}

}

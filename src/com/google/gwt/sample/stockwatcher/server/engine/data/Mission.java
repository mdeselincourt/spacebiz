package com.google.gwt.sample.stockwatcher.server.engine.data;

public class Mission {

	Vessel vessel;
	AiGoal standingOrders;
	
	public Mission(Vessel v, AiGoal s) {
		vessel = v;
		standingOrders = s;
	}

	public Vessel getVessel() {
		return vessel;
	}

	public void setVessel(Vessel vessel) {
		this.vessel = vessel;
	}

	public AiGoal getStandingOrders() {
		return standingOrders;
	}

	public void setStandingOrders(AiGoal stance) {
		this.standingOrders = standingOrders;
	}

}

package com.google.gwt.sample.stockwatcher.server.engine.data;

public class Mission {

	Vessel vessel;
	Stance stance;
	
	public Mission(Vessel v, Stance s) {
		vessel = v;
		stance = s;
	}

	public Vessel getVessel() {
		return vessel;
	}

	public void setVessel(Vessel vessel) {
		this.vessel = vessel;
	}

	public Stance getStance() {
		return stance;
	}

	public void setStance(Stance stance) {
		this.stance = stance;
	}

}

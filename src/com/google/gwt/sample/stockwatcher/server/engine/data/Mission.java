package com.google.gwt.sample.stockwatcher.server.engine.data;

public class Mission {

	Vessel vessel;
	Stance stance;
	AIState aiState;
	
	public Mission(Vessel v, Stance s, AIState a) {
		vessel = v;
		stance = s;
		aiState = a;
	}
	
	public AIState getAiState() {
		return aiState;
	}

	public void setAiState(AIState aiState) {
		this.aiState = aiState;
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

package com.google.gwt.sample.stockwatcher.server.engine;

import java.util.logging.Logger;

import com.google.gwt.sample.stockwatcher.server.engine.data.AIState;
import com.google.gwt.sample.stockwatcher.server.engine.data.Mission;
import com.google.gwt.sample.stockwatcher.server.engine.data.Stance;
import com.google.gwt.sample.stockwatcher.server.engine.data.Vessel;

public class Encounter {

	private static final Logger log = Logger.getLogger(Encounter.class.getName());

	Mission missionA;
	Mission missionB;
	Vessel a;
	Vessel b;
	
	// Encounter simulation data
	double separation = 100000;
	double parting = 0.0; // Speed moving apart
	
	public Encounter(Mission a, Mission b) {

		this.missionA = a;
		this.missionB = b;
	}
	
	public static void main(String[] args) {
		
		// TWR (speed) 30 m/s (about motorway speed)
		// Therefore emittance = 390,000 thrust units for a small craft like this
		// Receiver sensitivity = 0.001, so it can be felt at about 20km
		// Emitter amp = 4,000,000 which with that sensitivity gives radar range 31km with
		// high energy 1-metre waves.
		
		// A motorway car goes at 30 m/s
		// The Lakon Type 9 cruises at 131 m/s
		// The fastest ship in Elite is a Viper Mk III at boost at 400 m/s
		
		Vessel va = new Vessel("Lunar CSM A", 13000.0, 30.0, 0.001, 4000000, 1);
		Vessel vb = new Vessel("Lunar CSM B", 13000.0, 30.0, 0.001, 4000000, 1);
		
		Mission mA = new Mission(va, Stance.EVADE, AIState.WANDERING);
		Mission mB = new Mission(vb, Stance.PURSUE, AIState.WANDERING);
		
		Encounter e = new Encounter(mA, mB);
		
		e.run();
	}
	
	/**
	 * - Simulation click:
	 * 	- Update state
	 *  -  Make ship decisions
	 *  -  Determine next event
	 * 
	 */			
	public void run() {
				
		log.info("run()");
		
		a = missionA.getVessel();
		b = missionB.getVessel();
		
		// STARTING DISPOSITIONS
		// The two vessels start unwittingly closing at 0.7 ( close to 1/sqrt(2) ) of their combined speeds
		separation = 1000000.0;
		parting = -(0.7 * a.speed + 0.7 * b.speed);
		
		// Run the simulation
		tick();
		
		// FIRST EVENT should always be First Contact
		double fcDistA = contact(a, b);
		double fcDistB = contact(b, a);
		
		// log.info("Ranges are " + fcDistA + " and " + fcDistB);
		
		
		
		// 
	}
	
	private void tick() {
		
		log.info("tick()");
		
		/////////////////////////////
		// What do the fleets KNOW //
		/////////////////////////////
		double fcDistA = contact(a, b);
		double fcDistB = contact(b, a);
		
		log.info("fcDists: " + fcDistA + "," + fcDistB);
		
		boolean aSeesB = (fcDistA >= separation);
		boolean bSeesA = (fcDistB >= separation);
		
		log.info("sees: " + aSeesB + "," + bSeesA);
		
		if (!(aSeesB || bSeesA)) {

			// Both ships are clueless
			// Find the first contact distance
			double fcDistMax = Math.max(fcDistA, fcDistB);
			double fcDistLeft = separation - fcDistMax; 
			double fcTimeLeft = fcDistLeft / -parting;
			
			log.info("fcDistMax = " + fcDistMax + ", fcDistLeft = " + fcDistLeft + ", fcTimeLeft = " + fcTimeLeft);
		}
		

		// What do the fleets DECIDE & what CHANGES
		
		
		// What's the next event?
		
	}
	
	private double contact(Vessel a, Vessel b) {
		
		// Passive detection
		double aPassiveDetectBDistance = Math.sqrt(b.emits/a.detectionThreshold);
		
		log.info("A 'hears' B at range " + aPassiveDetectBDistance);
		
		// Active detection
		double aReflection = Math.min(a.reflectionArea/b.radarWavelength, 1);

		// log.info("a reflectivity = " + aReflection);
		
		double aActiveDetectBDistance = 0.5 * Math.sqrt((a.power * aReflection) / a.detectionThreshold);

		log.info("A 'sees' B at range " + aActiveDetectBDistance);
		
		// Passive-of-active detection
		double aPassiveDetectBRadarDistance = Math.sqrt(b.power/a.detectionThreshold);
		
		log.info("A 'hears' B's RADAR at " + aPassiveDetectBRadarDistance);
		
		double passiveRange = Math.max(aPassiveDetectBDistance, aActiveDetectBDistance);
		double range = Math.max(passiveRange, aPassiveDetectBRadarDistance);
		
		return range;
	}

	
	
}

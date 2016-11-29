package com.google.gwt.sample.stockwatcher.server.engine;

import java.util.logging.Logger;

import com.google.gwt.sample.stockwatcher.server.engine.data.Mission;
import com.google.gwt.sample.stockwatcher.server.engine.data.Stance;
import com.google.gwt.sample.stockwatcher.server.engine.data.Vessel;

public class Encounter {

	private static final Logger log = Logger.getLogger(Encounter.class.getName());

	Mission missionA;
	Mission missionB;
	
	// Encounter simulation data
	double separation = 100000;
	double parting = 0.0; // Speed moving apart
	
	public Encounter(Mission a, Mission b) {

		this.missionA = a;
		this.missionB = b;
	}
	
	public static void main(String[] args) {
		
		Vessel va = new Vessel("Lunar CSM A", 13000.0, 1.0, 0.0001, 400000, 1);
		Vessel vb = new Vessel("Lunar CSM B", 13000.0, 1.0, 0.0001, 400000, 1);
		
		Mission mA = new Mission(va, Stance.EVADE);
		Mission mB = new Mission(vb, Stance.PURSUE);
		
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
				
		Vessel a = missionA.getVessel();
		Vessel b = missionB.getVessel();
		 
		// STARTING DISPOSITIONS
		// The two vessels start closing at 0.7 ( close to 1/sqrt(2) ) of their combined speeds
		separation = 1000000.0;
		parting = -(0.7 * a.speed + 0.7 * b.speed);
		
		// FIRST EVENT should always be First Contact
		double fcDistA = contact(a, b);
		double fcDistB = contact(b, a);
		
		log.info("Ranges are " + fcDistA + " and " + fcDistB);
	}
	
	private double contact(Vessel a, Vessel b) {
		
		// Passive detection
		double aPassiveDetectBDistance = Math.sqrt(b.emits/a.detectionThreshold);
		
		log.info("A 'hears' B at range " + aPassiveDetectBDistance);
		
		// Active detection
		double aReflection = Math.min(a.reflectionArea/b.radarWavelength, 1);

		log.info("a reflects " + aReflection);
		
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

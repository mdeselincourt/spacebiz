package com.google.gwt.sample.stockwatcher.server.engine;

import java.util.logging.Logger;

import com.google.gwt.sample.stockwatcher.server.engine.data.Vessel;

public class Encounter {

	private static final Logger log = Logger.getLogger(Encounter.class.getName());

	Vessel a;
	Vessel b;
	
	public Encounter(Vessel a, Vessel b) {
		// TODO Auto-generated constructor stub
		this.a = a;
		this.b = b;
	}
	
	public static void main(String[] args) {
		Encounter e = new Encounter(new Vessel("Lunar CSM", 13000, 0.0001, 400000, 1), new Vessel("Lunar CSM", 13000, 0.0001, 400000, 1));
		e.run();
	}
	
	public void run() {
		
		// Passive detection
		double aPassiveDetectBDistance = Math.sqrt(b.emits/a.detectionThreshold);
		double bPassiveDetectADistance = Math.sqrt(a.emits/b.detectionThreshold);
		
		// Active detection
		double aReflection = Math.min(a.reflectionArea/b.radarWavelength, 1);
		double bReflection = Math.min(b.reflectionArea/a.radarWavelength, 1);
		
		log.info("a Reflects " + aReflection);
		log.info("b Reflects " + bReflection);
		
		double aActiveDetectBDistance = 0.5 * Math.sqrt((a.power * aReflection) / a.detectionThreshold);
		double bActiveDetectADistance = 0.5 * Math.sqrt((b.power * bReflection) / b.detectionThreshold);
		
		log.info("A 'hears' B at range " + aPassiveDetectBDistance);
		log.info("A 'sees' B at range " + aActiveDetectBDistance);
		
		log.info("B 'hears' A at range " + bPassiveDetectADistance);
		log.info("B 'sees' A at range " + bActiveDetectADistance);
		
		double aPassiveDetectBRadarDistance = Math.sqrt(b.power/a.detectionThreshold);
		double bPassiveDetectARadarDistance = Math.sqrt(a.power/b.detectionThreshold);
		
		log.info("A 'hears' B's RADAR at " + aPassiveDetectBRadarDistance);
		log.info("B 'hears' A's RADAR at " + bPassiveDetectARadarDistance);
	}

}

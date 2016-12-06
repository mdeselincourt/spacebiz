package com.google.gwt.sample.stockwatcher.server.engine;

import java.util.LinkedList;
import java.util.Vector;
import java.util.logging.Logger;

import com.google.gwt.sample.stockwatcher.server.engine.data.AIState;
import com.google.gwt.sample.stockwatcher.server.engine.data.Mission;
import com.google.gwt.sample.stockwatcher.server.engine.data.Stance;
import com.google.gwt.sample.stockwatcher.server.engine.data.Vessel;
import com.google.gwt.sample.stockwatcher.server.engine.data.VesselMindState;

public class Encounter {

	private static final Logger log = Logger.getLogger(Encounter.class.getName());

	Mission missionA;
	Mission missionB;
	Vessel[] vessels;
	
	Vector<String> encounterLog = new Vector<String>();
	
	// Encounter simulation dat	
	long elapsed = 0; // Simulation-objective clock 
	
	int tickLimit = 3; // Safety timer to manage infinite loops
		
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
		
		// 100t 100-speed ship
		Vessel va = new Vessel("Lakon Type 9", 100000.0, 100.0, 0.001, 4000000, 1);
		Mission mA = new Mission(va, Stance.EVADE);
		
		// 25t 300-speed ship
		Vessel vb = new Vessel("Viper Mk III", 25000.0, 300.0, 0.001, 4000000, 1);
		Mission mB = new Mission(vb, Stance.PURSUE);
		
		Encounter e = new Encounter(mA, mB);
		
		e.run();
	}
	
	public void run() {
				
		log.info("run()ing...");
		
		// Load up Encounter globals for ease of access
		vessels[0] = missionA.getVessel();
		vessels[1] = missionB.getVessel();
		
		// STARTING DISPOSITIONS
		// The two vessels start unwittingly closing at 0.7 ( close to 1/sqrt(2) ) of their combined speeds
		
		// Start 20km apart
		vessels[0].setX(-10000.0);
		vessels[1].setX(10000.0);
		
		vessels[0].setCourse((0.7 * vessels[0].speed)); // As if closing on a diagonal with Y irrelevant
		vessels[1].setCourse(-(0.7 * vessels[1].speed)); 
		
		elapsed = 0;
		
		boolean simulationContinues = true;
		
		while (simulationContinues && tickLimit > 0) {
			simulationContinues = tick();
			tickLimit--;
		}
		
		log.info("Ending run() of encounter with " + tickLimit + " tick-limit left");
		
		System.out.print(encounterLog.toString());
	}
	
	/**
	 * 1. Using intentions, update world for the just-passed interval
	 * 2. Using world, evaluate knowledge
	 * 3. Using knowledge, evaluate intentions
	 */
	private boolean tick() {
		
		log.info("tick(), " + tickLimit + " left");
		
		boolean anotherTick = true; // The safety control in Run() will protect us 
		
		////////////////////////////////////////
		// I - USING INTENTIONS, UPDATE WORLD //
		////////////////////////////////////////
		
		// Ia : apply intentions to world
		
		// I think I will need two levels of intention 
			// One single GOAL (e.g. flee) 
			// And multiple intentions like order/commands
		
		// Ib : let time pass
		
		/////////////////////////////////////////
		// II - USING WORLD, UPDATE MIND STATE //
		/////////////////////////////////////////
		
		// II : Populate mind state by iterating through world

		//////////////////////////////////////////
		// III - USING MIND, UPDATE INTENTIONS  //
		//////////////////////////////////////////
		
		// III : Determine intentions using mind state
		for (int i = 0; i < vessels.length; i++ ) {
			
			Vessel vessel = vessels[i];
			VesselMindState mind = vessel.getMindState();
			
			// There is no "right way" to structure this.
			
			if (mind.isRemembersContact()) {
				// Vessel knows it is not alone
				
				// Figure out which way is "forwards"
				// ... oh dear
				
				// Make decision				
				switch (mind.getGoal())
				{
				case ESCAPE:
					
					break;
				case KILL:
					
					break;
				case TRAVEL:
					
					break;
				default:
					
					break;
				}
				
			}
			else
			{
				// Vessel thinks it is alone

				// Idle; no changes to mind state...
			}
			
		}
		
		// End tick
		return anotherTick;
	}
	
//	private void think() {
//		
//		// A's decision
//		if (aSeesB) {
//			
//			log.info("A thinking about B");
//			
//			// 1. Set course
//			switch (missionA.getStance()) {
//				case PURSUE:
//					missionA.setAiState(AIState.INTERCEPTING);
//					aCourse = -a.speed; // Full speed towards target!
//					encounterLog.add("E" + (elapsed ) + a.name + " set course to intercept");
//					break;
//				case EVADE:
//					missionA.setAiState(AIState.FLEEING);
//					aCourse = a.speed; // Full speed away from target!
//					encounterLog.add("E" + (elapsed ) + a.name + " set course to escape");
//					break;
//				default:
//					log.info("No decision!");
//					break;
//			}
//			
//		}
//
//		// B's decision		
//		if (bSeesA) {
//
//			log.info("B thinking about A");
//			
//			// 1. Set course
//			switch (missionB.getStance()) {
//			
//				case PURSUE:
//					missionB.setAiState(AIState.INTERCEPTING);
//					bCourse = -b.speed; // Full speed towards target!
//					encounterLog.add("E" + (elapsed) + b.name + " set course to intercept");
//					break;
//				case EVADE:
//					missionB.setAiState(AIState.FLEEING);
//					bCourse = b.speed; // Full speed away from target!
//					encounterLog.add("E" + (elapsed) + b.name + " set course to escape");
//					break;
//				default:
//					log.info("No decision!");
//					break;
//			}
//
//		}
//		
//		// End of AI decisions
//	}
//	
//	private double contact(Vessel a, Vessel b) {
//		
//		// Passive detection
//		double aPassiveDetectBDistance = Math.sqrt(b.emits/a.detectionThreshold);
//		
//		//log.info("'hears' opponent at range " + aPassiveDetectBDistance);
//		
//		// Active detection
//		double aReflection = Math.min(a.reflectionArea/b.radarWavelength, 1);
//
//		// log.info("a reflectivity = " + aReflection);
//		// Note that it's based on power not amplitude, hence the benefit of lower w/ls for the same amp 
//		double aActiveDetectBDistance = 0.5 * Math.sqrt((a.power * aReflection) / a.detectionThreshold);
//
//		//log.info("'sees' opponent at range " + aActiveDetectBDistance);
//		
//		// Passive-of-active detection
//		double aPassiveDetectBRadarDistance = Math.sqrt(b.power/a.detectionThreshold);
//		
//		//log.info("'hears' opponent's RADAR at " + aPassiveDetectBRadarDistance);
//		
//		double passiveRange = Math.max(aPassiveDetectBDistance, aActiveDetectBDistance);
//		double range = Math.max(passiveRange, aPassiveDetectBRadarDistance);
//		
//		return range;
//	}

}

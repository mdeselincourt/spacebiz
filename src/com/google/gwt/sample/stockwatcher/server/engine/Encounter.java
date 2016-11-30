package com.google.gwt.sample.stockwatcher.server.engine;

import java.util.Vector;
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
	
	Vector<String> encounterLog = new Vector<String>();
	
	// Encounter simulation data
	double separation = 100000;
	
	double parting = 0.0; // Speed moving apart
	
	double aCourse = 0.0; // Course is vector in same sign as separation i.e. movement AWAY from other ship
	double bCourse = 0.0;
	
	long elapsed = 0; // Simulation-objective clock
	long incidentStart = -1; // Offset for participant-subjective clock; -1 means not set yet
	long interrupt = 0; // Using long because we will be converting from 'double's 
	
	int tickLimit = 3;
	
	// Awareness globals...
	boolean aSeesB;
	boolean bSeesA;
	
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
		Mission mA = new Mission(va, Stance.EVADE, AIState.WANDERING);
		
		Vessel vb = new Vessel("Viper Mk III", 13000.0, 300.0, 0.001, 4000000, 1);
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
		
		aCourse = -(0.7 * a.speed); // Closing on a diagonal
		bCourse = -(0.7 * b.speed); // Closing on a diagonal
		
		parting = aCourse + bCourse;
		
		elapsed = 0;
		
		boolean simulationContinues = true;
		
		while (simulationContinues && tickLimit > 0) {
			tickLimit--;
			simulationContinues = tick();
		}
		
		log.info("Ending run() of encounter");
		
		System.out.print(encounterLog.toString());
	}
	
	/**
	 * Execute the game state and identify the next interrupt.
	 * 
	 * The interrupt model is intended to save the need to evaluate unintersting game states.
	 * 
	 * It is NOT intended to save the need to re-evaluate the game state each time.
	 * The assumption should be that the entire game state is re-evaluated every interrupt UNLESS
	 * some exception applies.
	 */
	private boolean tick() {
		
		log.info("tick() #" + tickLimit);
		
		// Flag for whether simulation needs to continue
		boolean anotherTick = false;
		
		/////////////////////////////
		// Update simulation state //
		/////////////////////////////		
		
		// Update the missions' positions
		parting = aCourse + bCourse;
		separation = separation + (parting * interrupt);

		log.info("New separation = " + separation);
		
		////////////////////////////////
		// Finalise simulation update //
		////////////////////////////////
		
		// Update clock
		elapsed = elapsed + interrupt;
		
		log.info("Elapsed is " + elapsed + " and interrupt period was " + interrupt);
		
		// Reset interrupt 'timer'
		interrupt = 0;
		
		/////////////////////////////
		// What do the fleets KNOW //
		/////////////////////////////
		
		boolean bothWandering = (missionA.getAiState() == AIState.WANDERING && missionB.getAiState() == AIState.WANDERING);
		
		// Determine their contact ranges (contact() evaluates both active and passive) 
		double fcDistA = contact(a, b);
		double fcDistB = contact(b, a);
		
		log.info("fcDists: " + fcDistA + "," + fcDistB);
		
		// Interpret the data; can they see each other?
		aSeesB = (fcDistA >= separation);
		bSeesA = (fcDistB >= separation);
		log.info("sees: " + aSeesB + "," + bSeesA);
		
		if (bothWandering) {
			// We are in pre-contact
			
			log.info("Both missions are wandering");
			
			if (!(aSeesB || bSeesA)) {
				// Neither sees the other
			
				log.info("Both missions are wandering and do not see each other");
				
				// Reassert a "coincidental" collision course to avoid infinite loop 
				aCourse = -0.7 * a.speed;
				bCourse = -0.7 * b.speed;
				parting = aCourse + bCourse;
				
				// Calculate first contact time
				double fcDistMax = Math.max(fcDistA, fcDistB);
				double fcDistLeft = separation - fcDistMax; 
				double fcTimeLeft = fcDistLeft / -parting;
				
				log.info("fcDistMax = " + fcDistMax + ", fcDistLeft = " + fcDistLeft + ", fcTimeLeft = " + fcTimeLeft);
				
				// Set interrupt for first contact (rounding up)
				// Yes you need this, ceil is an arithmetic not a type operation
				interrupt = Math.round(Math.ceil(fcTimeLeft));
				
				// No point assessing the rest of the game state since in theory there should be nothing else
				return true;
			}
			else
			{
				// At least one mission sees the other
				log.info("At least one mission sees the other");
				
				// If first time, log it
				if (incidentStart == -1) {
					incidentStart = elapsed;
					if (aSeesB) {
						encounterLog.add("E" + (elapsed) + a.name + " detected " + b.name + " at elapsed " + elapsed);
					}
					if (bSeesA) {
						encounterLog.add("E" + (elapsed) + b.name + " detected " + a.name + " at elapsed " + elapsed);
					}
				}
				
				
				// Make AI decisions
				think();
			}
		
		}
		
		// What's the next event?
		
		// Vessels arrived in close proximity?
		
		if (separation < 1000)
		{
			log.info("Separation = " + separation + " currently that's game over.");
			
			return false;
		}
		
		// Vessels closing/in successful pursuit?
		parting = aCourse + bCourse;
		
		log.info("Parting now = " + parting);
		
		if (parting < 0) { 
			
			long timeToRendezvous = Math.round(Math.ceil(separation / parting)); 
			interrupt = timeToRendezvous;
			anotherTick = true;
			log.info("T" + (elapsed - incidentStart) + "Vessels converging");
		}
		else
		{
			log.info("T" + (elapsed - incidentStart) + "Vessels not converging");
		}
		
		// End tick
		return anotherTick;
	}
	
	private void think() {
		
		// A's decision
		if (aSeesB) {
			
			log.info("A thinking about B");
			
			// 1. Set course
			switch (missionA.getStance()) {
			
				case PURSUE:
					missionA.setAiState(AIState.INTERCEPTING);
					aCourse = -a.speed; // Full speed towards target!
					encounterLog.add("E" + (elapsed ) + a.name + " set course to intercept");
					break;
				case EVADE:
					missionA.setAiState(AIState.FLEEING);
					aCourse = a.speed; // Full speed away from target!
					encounterLog.add("E" + (elapsed ) + a.name + " set course to escape");
					break;
				default:
					log.info("No decision!");
					break;
			}
			
		}

		// B's decision		
		if (bSeesA) {

			log.info("B thinking about A");
			
			// 1. Set course
			switch (missionB.getStance()) {
			
				case PURSUE:
					missionB.setAiState(AIState.INTERCEPTING);
					bCourse = -b.speed; // Full speed towards target!
					encounterLog.add("E" + (elapsed) + b.name + " set course to intercept");
					break;
				case EVADE:
					missionB.setAiState(AIState.FLEEING);
					bCourse = b.speed; // Full speed away from target!
					encounterLog.add("E" + (elapsed) + b.name + " set course to escape");
					break;
				default:
					log.info("No decision!");
					break;
			}

		}
		
		// End of AI decisions
	}
	
	private double contact(Vessel a, Vessel b) {
		
		// Passive detection
		double aPassiveDetectBDistance = Math.sqrt(b.emits/a.detectionThreshold);
		
		log.info("'hears' opponent at range " + aPassiveDetectBDistance);
		
		// Active detection
		double aReflection = Math.min(a.reflectionArea/b.radarWavelength, 1);

		// log.info("a reflectivity = " + aReflection);
		// Note that it's based on power not amplitude, hence the benefit of lower w/ls for the same amp 
		double aActiveDetectBDistance = 0.5 * Math.sqrt((a.power * aReflection) / a.detectionThreshold);

		log.info("'sees' opponent at range " + aActiveDetectBDistance);
		
		// Passive-of-active detection
		double aPassiveDetectBRadarDistance = Math.sqrt(b.power/a.detectionThreshold);
		
		log.info("'hears' opponent's RADAR at " + aPassiveDetectBRadarDistance);
		
		double passiveRange = Math.max(aPassiveDetectBDistance, aActiveDetectBDistance);
		double range = Math.max(passiveRange, aPassiveDetectBRadarDistance);
		
		return range;
	}

	
	
}

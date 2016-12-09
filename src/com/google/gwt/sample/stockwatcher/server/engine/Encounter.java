package com.google.gwt.sample.stockwatcher.server.engine;

import java.util.Vector;
import java.util.logging.Logger;

import com.google.gwt.sample.stockwatcher.server.engine.data.AiGoal;
import com.google.gwt.sample.stockwatcher.server.engine.data.SensorType;
import com.google.gwt.sample.stockwatcher.server.engine.data.Vessel;
import com.google.gwt.sample.stockwatcher.server.engine.data.VesselMindState;

public class Encounter {

	// Inner data class for tracking radar thresholds
	public class Threshold {
		public double radius; //Radius in distance (so, relative) 
		public SensorType sensorType; // ACTIVE or PASSIVE, because that information might be useful...
	}

	private static final Logger log = Logger.getLogger(Encounter.class.getName());

	Vessel[] vessels;
	
	Vector<String> encounterLog = new Vector<String>(); 
	
	// Encounter simulation data	
	
	long elapsed = 0; // Simulation-objective clock 
	long incidentStart = -1; // Offset for participant-subjective clock; -1 means not set yet
	long interrupt = 0; // Using long because we will be converting from 'double's 
	
	int tickLimit = 40;
		
	public Encounter(Vessel[] newVessels) {
		vessels = newVessels;
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
		
		Vessel[] vs = new Vessel[2];
		
		// 100t 100-speed ship
		vs[0] = new Vessel("Lakon Type 9", 100000.0, 100.0, 0.001, 4000000, 1, AiGoal.ESCAPE);
		
		// 25t 300-speed ship
		vs[1] = new Vessel("Viper Mk III", 25000.0, 300.0, 0.001, 4000000, 1, AiGoal.KILL);
		
		Encounter e = new Encounter(vs);
		
		e.run();
	}
	
	public void run() {
				
		log.info("run()ing...");
		log.warning("Orientation is currently hard-coded");
		
		// Load up Encounter globals for ease of access
		//vessels[0] = missionA.getVessel();
		//vessels[1] = missionB.getVessel();
		
		// STARTING DISPOSITIONS
		// The two vessels start unwittingly closing at 0.7 ( close to 1/sqrt(2) ) of their combined speeds
		
		// You should start at first contact range
		
		double fcRange = Math.max(contact(vessels[0], vessels[1]), contact(vessels[1], vessels[0])); 
		
		// Position vessels; round in favour of proximity
		vessels[0].setX(Math.ceil(-fcRange / 2));
		vessels[1].setX(Math.floor(fcRange / 2));
		
		vessels[0].setCourse((0.7 * vessels[0].getTopSpeed())); // As if closing on a diagonal with Y irrelevant
		vessels[1].setCourse(-(0.7 * vessels[1].getTopSpeed())); 
		
		log.info("e.g. vessel 0 has top speed " + vessels[0].getTopSpeed() + " goes on course " + vessels[0].getCourse());
		
		elapsed = 0;
		
		// Build semi-static factors
		// Ships' visibility to each other will be semi-static
		
		// For each side
			// For each ship
				// Add a contact profile for that ship to that side if there isn't a matching profile already on that side
		
				// For each sensor
					// Add a sensor profile for that sensor if there isn't a matching profile already
		
		// You now have semi-static tables of contact profiles and sensor profiles
		
		// For each side
			// For each contact profile
				// For each ENEMY sensor profile
					// Calculate the threshold
		
		// THEN:
		
		// For each ship
			// For each RELEVANT sensing body 
				// Look up the thresholds (THRESHOLDS<OurShip><OpponentProfile>) and find the next one we'll cross
		
		// BUT I RECKON I CAN GET AWAY WITH OMITTING PROFILES AS AN OPTIMISATION FOR NOW
		
		
		boolean simulationContinues = true;
		
		while (simulationContinues && tickLimit > 0) {
			simulationContinues = tick();
			tickLimit--;
			elapsed++;
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
		
		/////////////////////////////////
		// Ia : apply intentions to world
		for (int i = 0; i < vessels.length; i++ ) {
			VesselMindState mind = vessels[i].getMindState();
			Vessel vessel = vessels[i];
			
			// Change course
			vessel.setCourse(mind.getIntendedCourse());
		}
			
		/////////////////////
		// Ib : let time pass
		for (int i = 0; i < vessels.length; i++ ) {
			VesselMindState mind = vessels[i].getMindState();
			Vessel vessel = vessels[i];
			
			Vessel opponent = vessels[1-i]; // TODO: Only supports 1 ship each side
			
			log.info(vessel.getName() + " is on course " + vessel.getCourse());
			// Move vessel
			vessel.setX(vessel.getX() + vessel.getCourse());
			
			// Check for an escape victory
			AiGoal vg = mind.getGoal();
			double vts = vessel.getTopSpeed();
			double ots = opponent.getTopSpeed();
			double ocr = contact(opponent,vessel);
			double sep = Math.abs(vessel.getX() - opponent.getX());
			
			if(vg == AiGoal.ESCAPE && vts > ots && ocr < sep) {
				log.info(vessel.getName() + " successfully escapes!");
				encounterLog.add(elapsed + ": " + vessel.getName() + " successfully fled!");
			}
			
			// Check for a boarding victory
			
		}
		
		/////////////////////
		// Ic: check for world actions including resolution of encounter
		

		
		///////////////////////////////////////////////////
		// II - USING WORLD, UPDATE MIND STATE INC GOAL? //
		///////////////////////////////////////////////////
		
		log.warning("TODO: Sensory code only supports 2 vessels");
		double separation = Math.abs(vessels[0].getX() - vessels[1].getX());
		
		// II : Populate mind state by iterating through world
		for (int i = 0; i < vessels.length; i++ ) {
			
			int otherShipIndex = 1 - i;
			double sightRange = contact(vessels[i], vessels[otherShipIndex]); 
			
			if (sightRange > separation)
			{
				if (vessels[i].getMindState().isRemembersContact() == false) {
					encounterLog.add(elapsed + ": " + vessels[i].getName() + " detects " + vessels[otherShipIndex].getName());
				}
				// Make vessel aware of the opponent.
				vessels[i].getMindState().setRemembersContact(true);
			}
			else
			{
				log.info(vessels[i].getName() + " can't see " + vessels[otherShipIndex].getName());
			}
			
		}
		
		

		/////////////////////////////////////////////////////////
		// III - USING KNOWLEDGE, UPDATE GOALS AND INTENTIONS  //
		/////////////////////////////////////////////////////////
		
		// III : Determine intentions using mind state
		for (int i = 0; i < vessels.length; i++ ) {
			
			Vessel vessel = vessels[i];
			VesselMindState mind = vessel.getMindState();
			
			log.info(vessel.getName() + " thinking...");
			
			// Figure out which way is forwards for use later 
			int awayFromOpponentUnitVector;
			
			if (i == 0) {
				// 0 will have started to the west (-ve) so away is -ve
				awayFromOpponentUnitVector = -1;
			}
			else
			{
				// We must be ship 1 started east (+ve) so away is +ve
				awayFromOpponentUnitVector = 1;
			}
			
			// Does the vessel realise it's in an engagement?
			if (mind.isRemembersContact()) {
				// Vessel knows it is not alone
				log.info(vessel.getName() + " knows it has company!");
				
				// Update goal GIVEN we know we're in an engagement
				if (mind.getStandingOrders() == AiGoal.KILL) {

					if (mind.getGoal() != AiGoal.KILL) {
						encounterLog.add(elapsed + ": " + vessel.getName() + " initiates an attack!");
					}
					
					// Obey orders unquestioningly; kill
					vessel.getMindState().setGoal(AiGoal.KILL);
				}
				if (vessel.getMindState().getStandingOrders() == AiGoal.ESCAPE) {
					
					if (mind.getGoal() != AiGoal.ESCAPE) {
						encounterLog.add(elapsed + ": " + vessel.getName() + " decides to flee!");
					}
					
					// Obey unquestioningly orders; escape
					vessel.getMindState().setGoal(AiGoal.ESCAPE);
				}
				
				// Convert goal into intentions				
				switch (mind.getGoal())
				{
				case ESCAPE:
					// Head away at top speed
					mind.setIntendedCourse(vessel.getTopSpeed() * awayFromOpponentUnitVector);
					log.info(vessel.getName() + " trying to escape...");
					break;
				case KILL:
					// Head -away (towards) at top speed
					mind.setIntendedCourse(vessel.getTopSpeed() * -awayFromOpponentUnitVector);
					log.info(vessel.getName() + " trying to kill...");
					break;
				case TRAVEL:
					// Set a normal course (which accidentally just happens to be a collision course!)
					mind.setIntendedCourse(vessel.getTopSpeed() * (0.71) *  -awayFromOpponentUnitVector );
					log.info(vessel.getName() + " trying to travel...");
					break;
				default:
					// No change
					log.warning(vessel.getName() + " apparently has no goal..?");
					break;
				}
				
			}
			else
			{
				// Vessel thinks it is alone
				log.info(vessel.getName() + " thinks it is alone...");
				
				// Set off on what is conveniently a collision course
				mind.setIntendedCourse(vessel.getTopSpeed() * (0.71) *  -awayFromOpponentUnitVector );
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
	
	private double contact(Vessel a, Vessel b) {
		
		String aName = a.getName();
		String bName = b.getName();
		
		// Passive detection
		double aPassiveDetectBDistance = Math.sqrt(b.getEmits()/a.getDetectionThreshold());
		
		//log.info(aName + " 'hears' " + bName + " at range " + aPassiveDetectBDistance);
		
		// Active detection
		double aReflection = Math.min(a.getReflectionArea()/b.getRadarWavelength(), 1);

		//log.info(aName + " reflectivity = " + aReflection);
		// Note that it's based on power not amplitude, hence the benefit of lower w/ls for the same amp 
		double aActiveDetectBDistance = 0.5 * Math.sqrt((a.getPower() * aReflection) / a.getDetectionThreshold());

		//log.info(aName + " 'sees' " + bName + " at range " + aActiveDetectBDistance);
		
		// Passive-of-active detection
		double aPassiveDetectBRadarDistance = Math.sqrt(b.getPower()/a.getDetectionThreshold());
		
		//log.info(aName + " 'hears' " + bName + "'s RADAR at " + aPassiveDetectBRadarDistance);
		
		double passiveRange = Math.max(aPassiveDetectBDistance, aActiveDetectBDistance);
		double range = Math.max(passiveRange, aPassiveDetectBRadarDistance);
		
		
		
		return range;
	}
}

package com.google.gwt.sample.stockwatcher.server.engine.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

public class VesselClass {
	
	private static final Logger log = Logger.getLogger(VesselClass.class.getName());
	
	private HashMap<VesselModuleType,ArrayList<VesselModule>> modulesListMap;
	
	// Class name
	protected String classname; 
	
	// Physical properties. 
	// Because Vessel inherits VesselClass, these are both the properties of the
	//  class and of the instances themselves.
	
	// Matter
	protected double mass = 0.0;
	protected double length = 0.0;
	
	/**
	 * Average isometric surface area for the purposes of radar detection
	 */
	protected double reflectionArea = 0.0;
	
	// Energy
	protected double emits = 0.0;
	
	protected double detectionThreshold = 0.0;
	protected double radarAmplitude = 0.0;
	protected double radarWavelength = 1;
	protected double power = 0.0;
	
	// Motion
	protected double thrust = 0.0;
	protected double twr = 0.0;
	protected double topSpeed = 0.0;
	
	public HashMap<VesselModuleType, ArrayList<VesselModule>> getModulesListMap() {
		return modulesListMap;
	}

	public void setModulesListMap(HashMap<VesselModuleType, ArrayList<VesselModule>> modulesListMap) {
		this.modulesListMap = modulesListMap;
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public double getReflectionArea() {
		return reflectionArea;
	}

	public void setReflectionArea(double reflectionArea) {
		this.reflectionArea = reflectionArea;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getThrust() {
		return thrust;
	}

	public void setThrust(double thrust) {
		this.thrust = thrust;
	}

	public double getTwr() {
		return twr;
	}

	public void setTwr(double twr) {
		this.twr = twr;
	}

	public double getTopSpeed() {
		return topSpeed;
	}

	public void setTopSpeed(double topSpeed) {
		this.topSpeed = topSpeed;
	}

	public double getEmits() {
		return emits;
	}

	public void setEmits(double emits) {
		this.emits = emits;
	}
	
	// Trying out having an enum for hard coded test classes
	
	/**
	 * This public enumeration is a key for some 'default instances'
	 * 
	 * e.g. BOAT, VIPER, LAKON, NIMITZ
	 * 
	 * @author michael.deselincourt
	 */
	public enum VesselClassExamples {
		BOAT, VIPER, LAKON, NIMITZ
	}
	
	/**
	 * Instantiates a VesselClass. 
	 * 
	 * Vessels EXTEND VesselClass so this is ALSO the constructor for a physical vessel.
	 * 
	 * The ship is constructed of a HashMap from module types (e.g. engine) to an ArrayList of all the modules of that type in the ship
	 * 
	 * @param template Using a value from the enum VesselClass.VesselClassExamples, sets certain hard-coded values
	 */
	public VesselClass(VesselClassExamples template ) {
		
		log.warning("TODO: This examples/template implementation smells bad");
		log.warning("Currently just creates defaults");

		// Creates a Patrol Boat modelled on a roughly lifeboat or F-14 sized thing 
		//
		// 2 tons of reactor
		// 2 tons of engine (like the Shannon class's 2 C9 diesel engines)
		// 1 ton of habitation
		// 1 ton of fuel stores
		
		// 1 ton of life support
		// 1 ton of RADAR
		// 1 ton of command space
		
		// 1 ton of supplies space 
		//
		//        CHsRE
		//        SLFRE
		//
		// = 10 tons
		// => 20 tons, big for a lifeboat but that's all
		// 16 metres long
		
		//
		// An AIM-9 is close enough to 0.1t
		//  We could say a hard-pt is equal mass to its capacity
		//  We could say a reloadable silo is ... 8x? 
		//		4x to hold it and another 4x of retrieval? Maybe 10x is easier 
		
		ArrayList<VesselModule> reactorsList = new ArrayList<VesselModule>(
				Arrays.asList(
						new VesselModule(VesselModuleType.REACTOR, 2000.0)
				)
		); 
		
		ArrayList<VesselModule> enginesList = new ArrayList<VesselModule>(
				Arrays.asList(
						new VesselModule(VesselModuleType.ENGINE, 2000.0)
				)
		);
		
		ArrayList<VesselModule> habitationsList = new ArrayList<VesselModule>(
				Arrays.asList(
						new VesselModule(VesselModuleType.HABITATION, 1000.0)
				)
		);
		
		ArrayList<VesselModule> fuelStoresList = new ArrayList<VesselModule>(
				Arrays.asList(
						new VesselModule(VesselModuleType.FUELSTORES, 1000.0)
				)
		);
		
		ArrayList<VesselModule> lifeSupportList = new ArrayList<VesselModule>(
				Arrays.asList(
						new VesselModule(VesselModuleType.LIFESUPPORT, 1000.0)
				)
		);
		
		ArrayList<VesselModule> radarList = new ArrayList<VesselModule>(
				Arrays.asList(
						new VesselModule(VesselModuleType.RADAR, 2000.0)
				)
		);
		
		ArrayList<VesselModule> commandsList = new ArrayList<VesselModule>(
				Arrays.asList(
						new VesselModule(VesselModuleType.COMMAND, 1000.0)
				)
		);
		
		ArrayList<VesselModule> storesList = new ArrayList<VesselModule>(
				Arrays.asList(
						new VesselModule(VesselModuleType.STORES, 1000.0)
				)
		);
		
		// The ship is a hash-map of array-maps of modules
		
		modulesListMap = new HashMap<VesselModuleType,ArrayList<VesselModule>>();
		
		// Put the ArrayMaps of same-type modules into the hash-map  
		modulesListMap.put(VesselModuleType.REACTOR, reactorsList);
		modulesListMap.put(VesselModuleType.ENGINE, enginesList);
		modulesListMap.put(VesselModuleType.HABITATION, habitationsList);
		modulesListMap.put(VesselModuleType.FUELSTORES, fuelStoresList);
		modulesListMap.put(VesselModuleType.LIFESUPPORT, lifeSupportList);
		modulesListMap.put(VesselModuleType.RADAR, radarList);
		modulesListMap.put(VesselModuleType.COMMAND, commandsList);
		modulesListMap.put(VesselModuleType.STORES, storesList);
		
		refreshProperties();
	}
	
	/**
	 * VesselClass.refreshProperties() 
	 * 
	 * Calculates a class's overall properties from its modules e.g. summing mass etc.
	 * 
	 * Currently recalculates:
	 * 
	 * mass
	 * reflectionArea - from mass
	 * length - from mass
	 * thrust - from engine module outputs 
	 * twr - from thrust and mass (obv)
	 * topSpeed - currently = twr for transnewtonian/cartoon physics
	 * 
	 * Protected so Vessels which extend it can use it
	 */
	protected void refreshProperties() {
		
		// This method updates 'new' variables then updates its official vars at the end. 
		
		// Prepare to aggregate module properties
		double newMass = 0.0;
		double newThrust = 0.0;
		
		// Aggregate module properties
		Iterator<Entry<VesselModuleType, ArrayList<VesselModule>>> mapIterator = modulesListMap.entrySet().iterator();
		
		// Go through every module type
		while (mapIterator.hasNext()) {
			
			Map.Entry<VesselModuleType, ArrayList<VesselModule>> pair = (Map.Entry<VesselModuleType, ArrayList<VesselModule>>)mapIterator.next(); 

			ArrayList<VesselModule> list = pair.getValue();
			
			Iterator<VesselModule> listIterator = list.iterator();
			
			// Go through every module in the type
			while (listIterator.hasNext()) {
				
				VesselModule module = listIterator.next();
				
				newMass = newMass + module.getMass();
				
				//log.info("module.toString() = " + module.toString());
				//log.info("module.getType().toString() = " + module.getType().toString());
				
				// Accumulate other properties as appropriate
				switch (module.getType()) {
					
					// Engine's outputs contribute to vessel thrust 
					case ENGINE:
						newThrust = newThrust + module.getOutput();
						break;
					default:
						break;
				}
				
			}
		}
		
		// Derive derived properties
		mass = newMass;
		log.info("Vessel/VesselClass's new mass = " + mass);
		this.reflectionArea = Math.pow(mass, 2/3); // Assuming 1 face of a cube
		this.length = Math.pow(mass/2, 1/3) * 2; // Assuming it's 2x as long as high and wide
		this.thrust = newThrust;
		this.twr = thrust / mass;
		this.topSpeed = twr; // Cartoon physics		
		this.emits = this.thrust;
		
		// TODO: Needs to be set by modules
		
		// BASELINE VALUES:
		// Receiver sensitivity = 0.001, so it can be felt at about 20km
		// Emitter amp = 4,000,000 which with that sensitivity gives radar range 31km with
		// high energy 1-metre waves.
		
		log.severe("Radar properties are not yet derived from modules");
		detectionThreshold = 0.001;
		radarAmplitude = 4000000.0;
		radarWavelength = 1;
		power = 0.0;
		
		return;
	}
}

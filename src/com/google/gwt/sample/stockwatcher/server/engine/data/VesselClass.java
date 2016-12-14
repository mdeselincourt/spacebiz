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
	
	private double mass;
	
	public VesselClass() {
		
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
		//  We could say a reloadable launcher is ... 8x? 
		//		4x to hold it and another 4x of retrieval? Maybe 10x is easier 
		
		ArrayList<VesselModule> reactorsList = new ArrayList<VesselModule>(
				Arrays.asList(
						new VesselModule(VesselModuleType.REACTOR, 2.0)
				)
		); 
		
		ArrayList<VesselModule> enginesList = new ArrayList<VesselModule>(
				Arrays.asList(
						new VesselModule(VesselModuleType.ENGINE, 2.0)
				)
		);
		
		ArrayList<VesselModule> habitationsList = new ArrayList<VesselModule>(
				Arrays.asList(
						new VesselModule(VesselModuleType.HABITATION, 1.0)
				)
		);
		
		ArrayList<VesselModule> fuelStoresList = new ArrayList<VesselModule>(
				Arrays.asList(
						new VesselModule(VesselModuleType.FUELSTORES, 1.0)
				)
		);
		
		ArrayList<VesselModule> lifeSupportList = new ArrayList<VesselModule>(
				Arrays.asList(
						new VesselModule(VesselModuleType.LIFESUPPORT, 1.0)
				)
		);
		
		ArrayList<VesselModule> radarList = new ArrayList<VesselModule>(
				Arrays.asList(
						new VesselModule(VesselModuleType.RADAR, 2.0)
				)
		);
		
		ArrayList<VesselModule> commandsList = new ArrayList<VesselModule>(
				Arrays.asList(
						new VesselModule(VesselModuleType.COMMAND, 1.0)
				)
		);
		
		ArrayList<VesselModule> storesList = new ArrayList<VesselModule>(
				Arrays.asList(
						new VesselModule(VesselModuleType.STORES, 1.0)
				)
		);
		
		modulesListMap.put(VesselModuleType.REACTOR, reactorsList);
		modulesListMap.put(VesselModuleType.ENGINE, enginesList);
		modulesListMap.put(VesselModuleType.HABITATION, habitationsList);
		modulesListMap.put(VesselModuleType.FUELSTORES, fuelStoresList);
		modulesListMap.put(VesselModuleType.LIFESUPPORT, lifeSupportList);
		modulesListMap.put(VesselModuleType.RADAR, radarList);
		modulesListMap.put(VesselModuleType.COMMAND, commandsList);
		modulesListMap.put(VesselModuleType.STORES, storesList);
		
		refreshMass();
	}
	
	private double refreshMass() {
		
		double newMass = 0.0;
		
		Iterator<Entry<VesselModuleType, ArrayList<VesselModule>>> mapIterator = modulesListMap.entrySet().iterator();
		
		while (mapIterator.hasNext()) {
			
			Map.Entry<VesselModuleType, ArrayList<VesselModule>> pair = (Map.Entry<VesselModuleType, ArrayList<VesselModule>>)mapIterator.next(); 

			ArrayList<VesselModule> list = pair.getValue();
			
			Iterator<VesselModule> listIterator = list.iterator();
			
			while (listIterator.hasNext()) {
				
				VesselModule module = listIterator.next();
				
				newMass = newMass + module.getMass();
				
			}
		}
		
		// log.warning("Currently arbitrary return value");
		mass = newMass;
		return newMass;
	}
}

package com.google.gwt.sample.stockwatcher.server.engine.data;

import java.util.logging.Logger;

public class VesselClass {
	
	private static final Logger log = Logger.getLogger(VesselClass.class.getName());
	
	private VesselModule reactors[];
	private VesselModule engines[];

	private VesselModule[] habs;

	private VesselModule[] fuelStores;

	private VesselModule[] lifeSupports;

	private VesselModule[] sensors;

	private VesselModule[] commands;

	private VesselModule[] stores;

	
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
		
		 reactors = new VesselModule[1];
		 reactors[0] = new VesselModule(VesselModuleType.REACTOR, 2.0);
		 
		 engines = new VesselModule[1];
		 engines[0] = new VesselModule(VesselModuleType.ENGINE, 2.0);
		 
		 habs = new VesselModule[1];
		 habs[0] = new VesselModule(VesselModuleType.HABITATION, 1.0);
		 
		 fuelStores = new VesselModule[1];
		 fuelStores[0] = new VesselModule(VesselModuleType.FUELSTORES, 1.0);
		 
		 lifeSupports = new VesselModule[1];
		 lifeSupports[0] = new VesselModule(VesselModuleType.LIFESUPPORT, 1.0);
		 
		 sensors = new VesselModule[1];
		 sensors[0] = new VesselModule(VesselModuleType.RADAR, 1.0);
		 
		 commands = new VesselModule[1];
		 commands[0] = new VesselModule(VesselModuleType.COMMAND, 1.0);
		 
		 stores = new VesselModule[1];
		 stores[0] = new VesselModule(VesselModuleType.STORES, 1.0);
	}
}

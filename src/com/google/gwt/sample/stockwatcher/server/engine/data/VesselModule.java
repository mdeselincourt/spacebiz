package com.google.gwt.sample.stockwatcher.server.engine.data;

import java.util.logging.Logger;

public class VesselModule {
	
	private static final Logger log = Logger.getLogger(VesselModule.class.getName());

	VesselModuleType type; 
	double mass;
	double output;
	
	// Radar specifics
	double amplitude;
	double wavelength;
	
	public VesselModule(VesselModuleType t, double m) {
	
	mass = m;
		
	// Derive output from mass //

	double outputUnitMass;  	
	
	switch (t) { 
		REACTOR:
		ENGINE:
		RADAR:
			outputUnitMass = 1.0;
			break;
		HABITATION:
		COMMAND:
		STORES:
			break;
	}
	
	output = mass/outputUnitMass;

}
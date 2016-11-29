package com.google.gwt.sample.stockwatcher.server.engine.data;
import java.util.logging.Logger;

import com.google.gwt.sample.stockwatcher.server.engine.Encounter;

public class Vessel {

	private static final Logger log = Logger.getLogger(Vessel.class.getName());
	
	public String name;

	// Physical attributes
	public double mass;
	public double reflectionArea;
	public double emits;
	
	public double twr;
	public double speed;
	public double thrust;
	
	// Detection equipment
	public double detectionThreshold;
	public double radarAmplitude;
	public double radarWavelength;
	public double power;
	
	/**
	 * Radar amplitude is going to need to be high because even at a "short" 1m wavelength it's going to need to be
	 * 4x as powerful as a ship's natural emissions even to match passive sensors.
	 * 
	 * Current default detection is 0.0001 which can passively detect a small ship at 11km
	 */
	public Vessel(String name, double mass, double twr, double detectionThreshold, double radarAmplitude,
			double radarWavelength) {
		
		super();
		
		this.name = name;
		this.mass = mass;
		this.twr = twr;
		this.speed = twr; // Dirty
		this.thrust = mass * twr;
		this.emits = this.thrust;
		
		log.info(name + " mass = " + mass);
		
		this.reflectionArea = Math.pow(mass, 2/3); // Assuming 1 face of a cube
		
		double density = 300; // 300 kg per m^3 is denser than a fully laden lunar CSM yet still easily floats 
		
		// 48x27x15 = 19000 m3 => 155(267 laden) 
		// = 8-14 kg / m3 (T-6)
		
		// CSM bounding 7.6(NOT? inc 3.8 bell)x3.9x3.9 = 115 m3
		// CSM body cylinder = pi x 1.9^2 x 7.6 = 86 m^3
		
		// CSM empty = 6100 kg
		// CSM full = 24500 kg
		
		// CSM box empty = 53 kg/m3
		// CSM box full = 213 kg/m3
		
		// CSM cylinder empty = 70 kg/m3
		// CSM cylinder full = 284 kg/m3
		
		// T6 48 x 27 x 15 bounding (but it's pretty square) = 20,000 m3
		
		// T6 mass = 155000 to 267000
		
		// T6 density = 7 to 13 kg/m3 - ridiculously light it appears
		
		// conclusion: airtight the CSM would float like a balloon even fully fuelled
		
		double volume = mass / density; 
		
		log.info(name + " volume = " + volume);
		
		double edge = Math.cbrt(volume);
		
		log.info(name + " edge = " + edge);
		
		this.detectionThreshold = detectionThreshold;
		this.radarAmplitude = radarAmplitude;
		this.radarWavelength = radarWavelength;
		this.power = radarAmplitude/radarWavelength;
	}


	

}

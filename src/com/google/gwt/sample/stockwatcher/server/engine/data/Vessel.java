package com.google.gwt.sample.stockwatcher.server.engine.data;
import java.util.logging.Logger;

import com.google.gwt.sample.stockwatcher.server.engine.Encounter;

public class Vessel extends VesselClass implements SpaceObject {

	private static final Logger log = Logger.getLogger(Vessel.class.getName());
	
	private String name;

	// Semi-static physical attributes	
	private double topSpeed;

	// Physical attributes
	private double mass;
	private double reflectionArea;
	private double emits;
	
	private double twr;
	private double speed;
	private double thrust;
	
	// Mental state
	private VesselMindState mindState;
	
	// Physical state
	private double x;
	
	// Actual current 1D vector during an encounter
	private double course;
	
	// Detection equipment
	private double detectionThreshold;
	private double radarAmplitude;
	private double radarWavelength;
	private double power;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public double getEmits() {
		return emits;
	}

	public void setEmits(double emits) {
		this.emits = emits;
	}

	public double getTwr() {
		return twr;
	}

	public void setTwr(double twr) {
		this.twr = twr;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getThrust() {
		return thrust;
	}

	public void setThrust(double thrust) {
		this.thrust = thrust;
	}

	public double getDetectionThreshold() {
		return detectionThreshold;
	}

	public void setDetectionThreshold(double detectionThreshold) {
		this.detectionThreshold = detectionThreshold;
	}

	public double getRadarAmplitude() {
		return radarAmplitude;
	}

	public void setRadarAmplitude(double radarAmplitude) {
		this.radarAmplitude = radarAmplitude;
	}

	public double getRadarWavelength() {
		return radarWavelength;
	}

	public void setRadarWavelength(double radarWavelength) {
		this.radarWavelength = radarWavelength;
	}

	public double getPower() {
		return power;
	}

	public void setPower(double power) {
		this.power = power;
	}

	/**
	 * Intended (but doesn't 
	 * 
	 * @param name
	 * @param standingOrders
	 */
	
	public Vessel(String name, AiGoal standingOrders, VesselClass ignoreMeForNow) {
		
		super(); // At time of writing the VesselClass constructor has hard-coded values
		
		log.warning("This constructor doesn't support choosing a class yet!");
		
		// Use the choice of VesselClass to instantiate this derp
		
		this.name = name;
		
		this.mindState = new VesselMindState();
		mindState.setStandingOrders(standingOrders);
		mindState.setGoal(AiGoal.TRAVEL);
		mindState.setIntendedCourse(0.0);
		mindState.setRemembersContact(false);
		
		log.severe("!!! ! ! Rewriting of Vessel class not complete ! ! !!!");
		
		// this.mass = TODO: CONTINUE FRMO HERE! 
	}
	
	/**
	 * name, mass, twr (speed), detectionThreshold, radarAmplitude, radarWavelength
	 * 
	 * Radar amplitude is going to need to be high because even at a "short" 1m wavelength it's going to need to be
	 * 4x as powerful as a ship's natural emissions even to match passive sensors.
	 * 
	 * Current default detection is 0.0001 which can passively detect a small ship at 11km
	 */
	public Vessel(String name, double mass, double twr, double detectionThreshold, double radarAmplitude,
			double radarWavelength, AiGoal standingOrders) {
		
		super(); // Initialise class properties
		
		log.warning("This constructor doesn't build the Vessel from a VesselClass so probably needs retiring");
		
		this.mindState = new VesselMindState();
		mindState.setStandingOrders(standingOrders);
		mindState.setGoal(AiGoal.TRAVEL);
		mindState.setIntendedCourse(0.0);
		mindState.setRemembersContact(false);
		
		this.name = name;
		this.mass = mass;
		this.twr = twr;
		this.topSpeed = twr; // Dirty
		this.thrust = mass * twr;
		this.emits = this.thrust;
		log.info(name + " thrust & emit = " + this.thrust);
		
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
		
		//log.info(name + " volume = " + volume);
		
		double edge = Math.cbrt(volume);
		
		//log.info(name + " edge = " + edge);
		
		this.detectionThreshold = detectionThreshold;
		this.radarAmplitude = radarAmplitude;
		this.radarWavelength = radarWavelength;
		this.power = radarAmplitude/radarWavelength;
	}
	
	public enum VesselTestEnum {
		BOAT
	}
	
	@Override
	public double getCourse() {
		return course;
	}

	public double getTopSpeed() {
		return topSpeed;
	}

	public void setTopSpeed(double topSpeed) {
		this.topSpeed = topSpeed;
	}

	@Override
	public void setCourse(double course) {
		this.course = course;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		log.info("Moving " + this.name + " from " + this.x + " to " + x);
		this.x = x;
	}

	public VesselMindState getMindState() {
		return mindState;
	}

	public void setMindState(VesselMindState mindState) {
		this.mindState = mindState;
	}

	

}

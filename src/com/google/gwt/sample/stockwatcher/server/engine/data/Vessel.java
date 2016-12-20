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
	
	public Vessel(String name, AiGoal standingOrders, VesselClass.VesselClassExamples template) {
		
		super(template); // At time of writing the VesselClass constructor has hard-coded values
		
		log.warning("TODO: This examples implementation smells bad");
		
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

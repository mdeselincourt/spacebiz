package com.google.gwt.sample.stockwatcher.server.engine.data;

public class Sensor {
	private SensorType sensorType;
	private double detectionThreshold;
	
	public SensorType getSensorType() {
		return sensorType;
	}
	public void setSensorType(SensorType sensorType) {
		this.sensorType = sensorType;
	}
	public double getDetectionThreshold() {
		return detectionThreshold;
	}
	public void setDetectionThreshold(double detectionThreshold) {
		this.detectionThreshold = detectionThreshold;
	}
	
	public double getDetectionRange(Vessel target) {
		return Math.sqrt(target.getEmits()/detectionThreshold);
	}
}

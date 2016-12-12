package com.google.gwt.sample.stockwatcher.server.engine.data;
/**
 * 
 * @author MichaelDeSelincourt
 * Vessel subject, SignatureType signatureType, double displacement, double rcs, double power
 */
public class Signature {

	/**
	 * Vessel subject, SignatureType signatureType, double displacement, double rcs, double power
	 * @param subject
	 * @param signatureType
	 * @param displacement
	 * @param rcs
	 * @param power
	 */
	public Signature(Vessel subject, SignatureType signatureType, double displacement, double rcs, double power) {
		super();
		this.subject = subject;
		this.signatureType = signatureType;
		this.displacement = displacement;
		this.rcs = rcs;
		this.power = power;
	}
	Vessel subject;
	SignatureType signatureType;
	double displacement; // Signed distance
	
	double rcs; // If it's a RADAR reflection, a radar cross-section
	double power; // If it's a radar 
}

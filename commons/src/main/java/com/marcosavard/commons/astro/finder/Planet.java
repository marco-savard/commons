package com.marcosavard.commons.astro.finder;

import static com.marcosavard.commons.astro.AstroMath.range;

public enum Planet {
	MERCURY(new MercuryOrbit()),
	VENUS(new VenusOrbit());

	private Orbit orbit; 

	Planet(Orbit orbit) {
		this.orbit = orbit; 
	}

	double getMeanDistance(double jd) {
		double a = orbit.absoluteMeanDistance + (orbit.relativeMeanDistance * jd);
		return a;
	}
	
	//in deg
	double getMeanAnomaly(double jd) {
		double ma = orbit.absoluteMeanAnomaly + (orbit.relativeMeanAnomaly * jd);
		ma = range(ma, 0, 360);
		return ma;
	}
	
	double getEccentricity(double jd) {
		double e = orbit.absoluteEccentricity + (orbit.relativeEccentricity * jd);
		return e;
	}
	
	//in deg
	double getInclination(double jd) {
		double i = orbit.absoluteInclination + (orbit.relativeInclination * jd);
		i = range(i, 0, 360);
		return i;
	}
	
	//in deg
	double getLongitudeAscendingNode(double jd) {
		double n = orbit.absoluteLongitudeAscNode + (orbit.relativeLongitudeAscNode * jd);
		n = range(n, 0, 360); 
		return n;
	}
	
	double getArgumentOfPerihelion(double jd) {
		double w = orbit.absoluteArgumentOfPerihelion + (orbit.relativeArgumentOfPerihelion * jd);
		w = range(w, 0, 360); 
		return w;
	}
	
	public static abstract class Orbit {
		private double absoluteMeanDistance = 1.0, relativeMeanDistance = 0.0;    //a, or semi major axis
		private double absoluteMeanAnomaly, relativeMeanAnomaly = 0.0;    //ma
		private double absoluteEccentricity, relativeEccentricity;     //e
		private double absoluteInclination, relativeInclination;      //i
		private double absoluteLongitudeAscNode, relativeLongitudeAscNode; //n 
		private double absoluteArgumentOfPerihelion, relativeArgumentOfPerihelion;     //w	
		
		protected void defineMeanDistance(double absoluteMeanDistance, double relativeMeanDistance) {
			this.absoluteMeanDistance = absoluteMeanDistance;
			this.relativeMeanDistance = relativeMeanDistance;
		}
		
		protected void defineEccentricity(double absoluteEccentricity, double relativeEccentricity) {
			this.absoluteEccentricity = absoluteEccentricity;
			this.relativeEccentricity = relativeEccentricity;
		}
		
		protected void defineMeanAnomaly(double absoluteMeanAnomaly, double relativeMeanAnomaly) {
			this.absoluteMeanAnomaly = absoluteMeanAnomaly;
			this.relativeMeanAnomaly = relativeMeanAnomaly;
		}
		
		protected void defineArgumentOfPerihelion(double absoluteArgumentOfPerihelion, double relativeArgumentOfPerihelion) {
			this.absoluteArgumentOfPerihelion = absoluteArgumentOfPerihelion;
			this.relativeArgumentOfPerihelion = relativeArgumentOfPerihelion;
		}
		
		protected void defineInclination(double absoluteInclination, double relativeInclination) {
			this.absoluteInclination = absoluteInclination;
			this.relativeInclination = relativeInclination;
		}
		
		protected void defineLongitudeAscNode(double absoluteLongitudeAscNode, double relativeLongitudeAscNode) {
			this.absoluteLongitudeAscNode = absoluteLongitudeAscNode;
			this.relativeLongitudeAscNode = relativeLongitudeAscNode;
		}
	}
	
	private static class MercuryOrbit extends Orbit {
		private MercuryOrbit() {
			super.defineMeanDistance(0.387098, 0.0); //a
			super.defineEccentricity(0.205635, 5.59E-10 ); //e
			super.defineMeanAnomaly(168.6562, 4.0923344368); //ma
			super.defineArgumentOfPerihelion(29.1241, 1.01444E-5);  //w
			super.defineInclination(7.0047, 5.00E-8); //i
			super.defineLongitudeAscNode(48.3313, 3.24587E-5);  //n	
		}
	}
	
	public static class VenusOrbit extends Orbit {
		private VenusOrbit() {
			super.defineMeanDistance(0.723330, 0.0); //a
			super.defineEccentricity(0.006773, - 1.302E-9); //e
			super.defineMeanAnomaly(48.0052, 1.6021302244); //ma
			super.defineArgumentOfPerihelion(54.8910, 1.38374E-5);  //w
			super.defineInclination(3.3946, 2.75E-8); //i
			super.defineLongitudeAscNode(76.6799, 2.46590E-5);  //n	
		}
	}
}

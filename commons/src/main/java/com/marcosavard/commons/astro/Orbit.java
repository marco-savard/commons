package com.marcosavard.commons.astro;

import com.marcosavard.commons.astro.unit.*;

import java.time.temporal.ChronoUnit;

import static com.marcosavard.commons.astro.unit.Constant.G;

//https://www.physicsclassroom.com/class/circles/Lesson-4/Mathematics-of-Satellite-Motion
public class Orbit {
	private static OrbitBuilder orbitAroundEarth, orbitAroundSun;

	private double centerBodyMass;
	private double meanDistance;
	private double period;

	private double centerBodyRadius = 0.0; //center body has no radius
	private double eccentricity = 0.0; //perfect circle

	public static Orbit of(double distance, LengthUnit lengthUnit, double period, ChronoUnit chronoUnit) {
		return new Orbit(distance, lengthUnit, period, chronoUnit);
	}

	protected Orbit(double mass, MassUnit massUnit, double distance, LengthUnit lengthUnit) {
		this.meanDistance = distance * lengthUnit.toMeters();
		this.centerBodyMass = mass * massUnit.toKilograms();
		double r3 = meanDistance * meanDistance * meanDistance;
		double t2 = (4 * Constant.PI2 * r3) / (Constant.G * centerBodyMass);
		this.period = Math.sqrt(t2);
	}

	public Orbit(double mass, MassUnit massUnit, double value, ChronoUnit chronoUnit) {
		this.period = value * chronoUnit.getDuration().getSeconds();
		this.centerBodyMass = mass * massUnit.toKilograms();
		double distance3 = (period * period * G * mass) / (4 * Math.PI * Math.PI);
		this.meanDistance = Math.cbrt(distance3);
	}

	protected Orbit(double distance, LengthUnit lengthUnit, double period, ChronoUnit chronoUnit) {
		this.meanDistance = distance * lengthUnit.toMeters();
		this.period = period * chronoUnit.getDuration().getSeconds();
		double distance3 = distance * distance * distance;
		double period2 = period * period;
		this.centerBodyMass = (4 * Math.PI * Math.PI * distance3) / (period2 * G);
	}

	public double getOrbitPeriod(ChronoUnit unit) {
		return period / unit.getDuration().getSeconds();
	}

	public double getMeanDistanceFromCenter(LengthUnit unit) {
		return meanDistance / unit.toMeters();
	}

	public double getOrbitAltitude(LengthUnit unit) {
		return (meanDistance - centerBodyRadius) / unit.toMeters();
	}

	public double getCenterBodyMass(MassUnit massUnit) {
		return centerBodyMass / massUnit.toKilograms();
	}

	private void setBodyRadius(double bodyRadius) {
		this.centerBodyRadius = bodyRadius;
	}

	public static OrbitBuilder aroundEarth() {
		if (orbitAroundEarth == null) {
			double mass = Mass.of(1, MassUnit.EARTH).toKg();
			double bodyRadius = Length.of(1, LengthUnit.EARTH_RADIUS).toMeters();
			orbitAroundEarth = new OrbitBuilder(mass, bodyRadius);
		}

		return orbitAroundEarth;
	}

	public static OrbitBuilder aroundSun() {
		if (orbitAroundSun == null) {
			double mass = Mass.of(1, MassUnit.SUN_MASS_VALUE).toKg();
			double bodyRadius = Length.of(1, LengthUnit.SUN_RADIUS_VALUE).toMeters();
			orbitAroundSun = new OrbitBuilder(mass, bodyRadius);
		}

		return orbitAroundSun;
	}

	public static OrbitBuilder aroundMass(int value, MassUnit unit) {
		double mass = value * unit.toKilograms();
		return new OrbitBuilder(mass, 0);
	}

	public double getOrbitalSpeed(LengthUnit lengthUnit, ChronoUnit chronoUnit) {
		double speedMperSec = Math.sqrt((Constant.G * centerBodyMass) / meanDistance);
		return speedMperSec * (chronoUnit.getDuration().getSeconds() / lengthUnit.toMeters());
	}

	public double getOrbitalAcceleration() {
		double accMperSec2 = (Constant.G * centerBodyMass) / (meanDistance * meanDistance);
		return accMperSec2;
	}

	public double getDistanceAtPeriaxis(LengthUnit lengthUnit) {
		double distanceAtPeriaxis = meanDistance * (1.0 - eccentricity);
		return distanceAtPeriaxis / lengthUnit.toMeters();
	}

	public double getDistanceAtApoaxis(LengthUnit lengthUnit) {
		double distanceAtApoaxis = meanDistance * (1.0 + eccentricity);
		return distanceAtApoaxis / lengthUnit.toMeters();
	}

	public Orbit withEccentricity(double eccentricity) {
		this.eccentricity = eccentricity;
		return this;
	}

	public static class SunOrbit extends Orbit {

		public static SunOrbit ofMeanDistance(double distance, LengthUnit lengthUnit) {
			return new SunOrbit(distance, lengthUnit);
		}

		private SunOrbit(double distance, LengthUnit lengthUnit) {
			super(1.0, MassUnit.SUN_MASS_VALUE, distance, lengthUnit);
		}

		public double getDistanceAtPerihelion(LengthUnit lengthUnit) {
			return super.getDistanceAtPeriaxis(lengthUnit);
		}

		public double getDistanceAtAphelion(LengthUnit lengthUnit) {
			return super.getDistanceAtApoaxis(lengthUnit);
		}
	}

	public static class OrbitBuilder {
		private double mass;

		private double bodyRadius;
		private double distance;
		private double period;

		private OrbitBuilder(double mass, double bodyRadius) {
			this.mass = mass;
			this.bodyRadius = bodyRadius;
		}

		public OrbitBuilder withRadius(double value, LengthUnit unit) {
			this.bodyRadius = value * unit.toMeters();
			return this;
		}

		public Orbit atAltitude(long value, LengthUnit unit) {
			double altitude = value * unit.toMeters();
			double distance = bodyRadius + altitude;
			Orbit orbit = new Orbit(mass, MassUnit.KG, distance, LengthUnit.M);
			orbit.setBodyRadius(bodyRadius);
			return orbit;
		}

		public Orbit ofMeanDistance(double distance, LengthUnit lengthUnit) {
			Orbit orbit = new Orbit(mass, MassUnit.KG, distance, lengthUnit);
			orbit.setBodyRadius(bodyRadius);
			return orbit;
		}

		public Orbit ofOrbitPeriod(double period, ChronoUnit chronoUnit) {
			Orbit orbit = new Orbit(mass, MassUnit.KG, period, chronoUnit);
			orbit.setBodyRadius(bodyRadius);
			return orbit;
		}

	}
}

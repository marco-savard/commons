package com.marcosavard.commons.astro;

import com.marcosavard.commons.astro.unit.LengthUnit;
import com.marcosavard.commons.astro.unit.MassUnit;

import java.time.temporal.ChronoUnit;

import static com.marcosavard.commons.astro.unit.UnitConstant.G;
import static com.marcosavard.commons.astro.unit.UnitConstant.EARTH_RADIUS_VALUE;
import static com.marcosavard.commons.astro.unit.UnitConstant.EARTH_MASS_VALUE;


public class Orbit {
	private double centerBodyMass;
	private double centerBodyRadius;
	private double meanDistance;
	private double period;

	public static Orbit of(double distance, LengthUnit lengthUnit, double period, ChronoUnit chronoUnit) {
		distance = distance * lengthUnit.toMeters();
		period = period * chronoUnit.getDuration().getSeconds();
		double distance3 = distance * distance * distance;
		double period2 = period * period;
		double mass = (4 * Math.PI * Math.PI * distance3) / (period2 * G);
		return new Orbit(mass, 0, distance, period);
	}

	protected Orbit(double mass, double radius, double meanDistance, double period) {
		this.centerBodyMass = mass;
		this.centerBodyRadius = radius;
		this.meanDistance = meanDistance;
		this.period = period;
	}

	public double getOrbitPeriod(ChronoUnit unit) {
		return period / unit.getDuration().getSeconds();
	}

	public double getMeanDistanceFromCenter(LengthUnit unit) {
		return meanDistance / unit.toMeters();
	}

	public Object getOrbitAltitude(LengthUnit unit) {
		return (meanDistance - centerBodyRadius) / unit.toMeters();
	}

	public double getCenterBodyMass(MassUnit massUnit) {
		return centerBodyMass / massUnit.toKilograms();
	}

	public static OrbitBuilder aroundEarth() {
		return OrbitBuilder.EARTH_ORBIT_BUILDER;
	}

	public static OrbitBuilder aroundMass(int value, MassUnit unit) {
		double mass = value * unit.toKilograms();
		return new OrbitBuilder(mass, 0);
	}


	public static class OrbitBuilder {
		private double mass;

		private double radius;
		private double distance;
		private double period;

		private OrbitBuilder(double mass, double radius) {
			this.mass = mass;
			this.radius = radius;
		}

		public OrbitBuilder withRadius(double value, LengthUnit unit) {
			this.radius = value * unit.toMeters();
			return this;
		}

		private static final OrbitBuilder EARTH_ORBIT_BUILDER = new OrbitBuilder(EARTH_MASS_VALUE, EARTH_RADIUS_VALUE);

		public Orbit atAltitude(long value, LengthUnit unit) {
			double altitude = value * unit.toMeters();
			double distance = radius + altitude;
			double period2 = 4 * Math.PI * Math.PI * (distance * distance * distance) / (mass * G);
			double period = Math.sqrt(period2);
			return new Orbit(mass, radius, distance, period);
		}

		public Orbit withOrbitPeriod(double value, ChronoUnit unit) {
			double period = value * unit.getDuration().getSeconds();
			double distance3 = (period * period * G * mass) / (4 * Math.PI * Math.PI);
			double distance = Math.cbrt(distance3);
			return new Orbit(mass, radius, distance, period);
		}


	}
}

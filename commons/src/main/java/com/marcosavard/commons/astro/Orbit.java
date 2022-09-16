package com.marcosavard.commons.astro;

import com.marcosavard.commons.astro.unit.Length;
import com.marcosavard.commons.astro.unit.LengthUnit;
import com.marcosavard.commons.astro.unit.Mass;
import com.marcosavard.commons.astro.unit.MassUnit;

import java.time.temporal.ChronoUnit;

import static com.marcosavard.commons.astro.unit.Constant.G;


public class Orbit {
	private static OrbitBuilder orbitAroundEarth;

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
		if (orbitAroundEarth == null) {
			double mass = Mass.of(1, MassUnit.EARTH).toKg();
			double bodyRadius = Length.of(1, LengthUnit.EARTH_RADIUS).toMeters();
			orbitAroundEarth = new OrbitBuilder(mass, bodyRadius);
		}

		return orbitAroundEarth;
	}

	public static OrbitBuilder aroundMass(int value, MassUnit unit) {
		double mass = value * unit.toKilograms();
		return new OrbitBuilder(mass, 0);
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
			double period2 = 4 * Math.PI * Math.PI * (distance * distance * distance) / (mass * G);
			double period = Math.sqrt(period2);
			return new Orbit(mass, bodyRadius, distance, period);
		}

		public Orbit withOrbitPeriod(double value, ChronoUnit unit) {
			double period = value * unit.getDuration().getSeconds();
			double distance3 = (period * period * G * mass) / (4 * Math.PI * Math.PI);
			double distance = Math.cbrt(distance3);
			return new Orbit(mass, bodyRadius, distance, period);
		}


	}
}

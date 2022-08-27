package com.marcosavard.commons.astro;

import com.marcosavard.commons.astro.unit.LengthUnit;
import com.marcosavard.commons.astro.unit.MassUnit;

import java.time.temporal.ChronoUnit;

public class Orbit {
	private static final double G = 6.673e-11; // in  N•m2/kg2

	private double mass;
	private double radius;
	private double distance;
	private double period;

	protected Orbit(double mass, double radius, double distance, double period) {
		this.mass = mass;
		this.radius = radius;
		this.distance = distance;
		this.period = period;
	}

	public static OrbitBuilder aroundEarth() {
		return OrbitBuilder.EARTH_ORBIT_BUILDER;
	}

	public static OrbitBuilder aroundMass(int value, MassUnit unit) {
		double mass = value * unit.toKilograms();
		return new OrbitBuilder(mass, 0);
	}

	public double getOrbitPeriod(ChronoUnit unit) {
		return period / unit.getDuration().getSeconds();
	}

	public double getOrbitRadius(LengthUnit unit) {
		return distance / unit.toMeters();
	}

	public Object getOrbitAltitude(LengthUnit unit) {
		return (distance - radius) / unit.toMeters();
	}


	public static class OrbitBuilder {
		private static final double EARTH_MASS = 5.98e24;
		private static final double EARTH_RADIUS = 6.373e6;

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

		private static final OrbitBuilder EARTH_ORBIT_BUILDER = new OrbitBuilder(EARTH_MASS, EARTH_RADIUS);

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

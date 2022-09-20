package com.marcosavard.commons.astro;

import com.marcosavard.commons.astro.space.SpaceCoordinate;
import com.marcosavard.commons.astro.time.JulianDay;
import com.marcosavard.commons.astro.unit.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static com.marcosavard.commons.astro.AstroMath.*;
import static com.marcosavard.commons.astro.unit.Constant.G;
import static java.lang.Math.PI;
import static java.lang.Math.sqrt;

//https://www.physicsclassroom.com/class/circles/Lesson-4/Mathematics-of-Satellite-Motion
public class Orbit {
	private static OrbitBuilder orbitAroundEarth, orbitAroundSun;

	private double centerBodyMass;
	private double meanDistance;
	private double period;

	private double centerBodyRadius = 0.0; //center body has no radius
	private double eccentricity = 0.0, eccentricityVariation = 0.0; //perfect circle

	private double meanAnomaly = 0.0, meanAnomalyVariation = 0.0; // in degrees

	private double longitudeOfPerihelion = 0.0, longitudeOfPerihelionVariation = 0.0; //in degrees

	private double obliquityOfEcliptic = 0.0, obliquityOfEclipticVariation = 0.0; // in degrees

	public static Orbit of(double distance, LengthUnit lengthUnit, double period, ChronoUnit chronoUnit) {
		return new Orbit(distance, lengthUnit, period, chronoUnit);
	}

	protected Orbit(double mass, MassUnit massUnit, double distance, LengthUnit lengthUnit) {
		this.meanDistance = distance * lengthUnit.toMeters();
		this.centerBodyMass = mass * massUnit.toKilograms();
		double r3 = meanDistance * meanDistance * meanDistance;
		double t2 = (4 * Constant.PI2 * r3) / (Constant.G * centerBodyMass);
		this.period = sqrt(t2);
	}

	public Orbit(double mass, MassUnit massUnit, double value, ChronoUnit chronoUnit) {
		this.period = value * chronoUnit.getDuration().getSeconds();
		this.centerBodyMass = mass * massUnit.toKilograms();
		double distance3 = (period * period * G * mass) / (4 * Constant.PI2);
		this.meanDistance = Math.cbrt(distance3);
	}

	protected Orbit(double distance, LengthUnit lengthUnit, double period, ChronoUnit chronoUnit) {
		this.meanDistance = distance * lengthUnit.toMeters();
		this.period = period * chronoUnit.getDuration().getSeconds();
		double distance3 = distance * distance * distance;
		double period2 = period * period;
		this.centerBodyMass = (4 * Constant.PI2 * distance3) / (period2 * G);
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

	public double getCenterBodyMass(MassUnit massUnit) {
		return centerBodyMass / massUnit.toKilograms();
	}

	public double getEccentricity() {
		return eccentricity;
	}

	public double getMeanDistanceFromCenter(LengthUnit unit) {
		return meanDistance / unit.toMeters();
	}
	public double getOrbitalAcceleration() {
		double accMperSec2 = (Constant.G * centerBodyMass) / (meanDistance * meanDistance);
		return accMperSec2;
	}

	public double getOrbitAltitude(LengthUnit unit) {
		return (meanDistance - centerBodyRadius) / unit.toMeters();
	}

	public double getOrbitPeriod(ChronoUnit unit) {
		return period / unit.getDuration().getSeconds();
	}

	public double getOrbitalSpeed(LengthUnit lengthUnit, ChronoUnit chronoUnit) {
		double speedMperSec = sqrt((Constant.G * centerBodyMass) / meanDistance);
		return speedMperSec * (chronoUnit.getDuration().getSeconds() / lengthUnit.toMeters());
	}

	public double getDistanceAtPeriaxis(LengthUnit lengthUnit) {
		double distanceAtPeriaxis = meanDistance * (1.0 - eccentricity);
		return distanceAtPeriaxis / lengthUnit.toMeters();
	}

	public double getDistanceAtApoaxis(LengthUnit lengthUnit) {
		double distanceAtApoaxis = meanDistance * (1.0 + eccentricity);
		return distanceAtApoaxis / lengthUnit.toMeters();
	}

	private void setBodyRadius(double bodyRadius) {
		this.centerBodyRadius = bodyRadius;
	}

	public Orbit withEccentricity(double eccentricity) {
		return withEccentricity(eccentricity, 0.0);
	}

	public Orbit withEccentricity(double eccentricity, double eccentricityVariation) {
		this.eccentricity = eccentricity;
		this.eccentricityVariation = eccentricityVariation;
		return this;
	}

	public Orbit withLongitudeOfPerihelion(double longitudeOfPerihelion) {
		return withLongitudeOfPerihelion(longitudeOfPerihelion, 0.0);
	}

	public Orbit withLongitudeOfPerihelion(double longitudeOfPerihelion, double longitudeOfPerihelionVariation) {
		this.longitudeOfPerihelion = longitudeOfPerihelion;
		this.longitudeOfPerihelionVariation = longitudeOfPerihelionVariation;
		return this;
	}

	public Orbit withObliquityOfEcliptic(double obliquityOfEcliptic) {
		return withObliquityOfEcliptic(obliquityOfEcliptic, 0.0);
	}

	public Orbit withObliquityOfEcliptic(double obliquityOfEcliptic, double obliquityOfEclipticVariation) {
		this.obliquityOfEcliptic = obliquityOfEcliptic;
		this.obliquityOfEclipticVariation = obliquityOfEclipticVariation;
		return this;
	}

	public Orbit withMeanAnomaly(double meanAnomaly) {
		return withMeanAnomaly(meanAnomaly, 0.0);
	}

	public Orbit withMeanAnomaly(double meanAnomaly, double meanAnomalyVariation) {
		this.meanAnomaly = meanAnomaly;
		this.meanAnomalyVariation = meanAnomalyVariation;
		return this;
	}

	public SpaceCoordinate findEclipticCoordinateOn(LocalDate date) {
		JulianDay jd = JulianDay.of(date);
		double d =  jd.getValue() - JulianDay.JD2000;
		double e = eccentricity + eccentricityVariation * d;
		double w = range(longitudeOfPerihelion + longitudeOfPerihelionVariation  * d, 360);
		double ma = range(meanAnomaly + meanAnomalyVariation * d, 360);
		double ml = range(w + ma, 360);
		double ea = ma + (180/PI) * e * sind(ma) * (1 + e * cosd(ma));
		double x = cosd(ea) - e;
		double y = sind(ea) * sqrt(1 - e*e);
		double z = 0.0;
		SpaceCoordinate location = SpaceCoordinate.rectangleOf(x, y, z);
		return location;
	}

	public SpaceCoordinate findEquatorialCoordinateOn(LocalDate date) {
		JulianDay jd = JulianDay.of(date);
		double d =  jd.getValue() - JulianDay.JD2000;
		double w = range(longitudeOfPerihelion + longitudeOfPerihelionVariation  * d, 360);
		double oblecl = range(obliquityOfEcliptic + obliquityOfEclipticVariation * d, 360);

		SpaceCoordinate ecliptic = findEclipticCoordinateOn(date);
		double distance = ecliptic.getDistance();
		double trueAnomaly = ecliptic.getRightAscensionDegree();
		double lon = range(trueAnomaly + w, 360);
		double x = distance * cosd(lon);
		double y = distance * sind(lon);
		double z = 0.0;
		double xequat = x;
		double yequat = y * cosd(oblecl) - z * sind(oblecl);
		double zequat = y * sind(oblecl) + z * cosd(oblecl);
		SpaceCoordinate equatorial = SpaceCoordinate.rectangleOf(xequat, yequat, zequat);

		return equatorial;
	}


	public double findObliquityOfEcliptic(LocalDate date) {
		JulianDay jd = JulianDay.of(date);
		double d =  jd.getValue() - JulianDay.JD2000;
		double oblecl = range(obliquityOfEcliptic + obliquityOfEclipticVariation * d, 360);
		return oblecl;
	}

	public double findLongitudeOn(double trueAnomaly, LocalDate date) {
		JulianDay jd = JulianDay.of(date);
		double d =  jd.getValue() - JulianDay.JD2000;
		double w = range(longitudeOfPerihelion + longitudeOfPerihelionVariation  * d, 360);
		double lon = range(trueAnomaly + w, 360);
		return lon;
	}


	public static class SolarOrbit extends Orbit {

		public static SolarOrbit ofMeanDistance(double distance, LengthUnit lengthUnit) {
			return new SolarOrbit(distance, lengthUnit);
		}

		public static SolarOrbit ofOrbitalPeriod(double duration, ChronoUnit chronoUnit) {
			return new SolarOrbit(duration, chronoUnit);
		}

		private SolarOrbit(double distance, LengthUnit lengthUnit) {
			super(1.0, MassUnit.SUN_MASS_VALUE, distance, lengthUnit);
		}

		private SolarOrbit(double duration, ChronoUnit chronoUnit) {
			super(1.0, MassUnit.SUN_MASS_VALUE, duration, chronoUnit);
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

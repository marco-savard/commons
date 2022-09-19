package com.marcosavard.commons.astro;

import com.marcosavard.commons.astro.unit.LengthUnit;
import com.marcosavard.commons.astro.unit.MassUnit;

import java.text.MessageFormat;
import java.time.temporal.ChronoUnit;

public class OrbitDemo {

	public static void main(String[] args) {
		Orbit orbit = Orbit.aroundEarth().atAltitude(100, LengthUnit.KM);
		double orbitPeriod = orbit.getOrbitPeriod(ChronoUnit.MINUTES);
		System.out.println(MessageFormat.format("Orbital Period : {0} min", orbitPeriod));

		orbit = Orbit.aroundEarth().ofOrbitPeriod(orbitPeriod, ChronoUnit.MINUTES);
		System.out.println(MessageFormat.format("Orbit Distance from center : {0} km", orbit.getMeanDistanceFromCenter(LengthUnit.KM)));
		System.out.println(MessageFormat.format("Orbit Altitude : {0} km", orbit.getOrbitAltitude(LengthUnit.KM)));

		orbit = Orbit.aroundMass(1, MassUnit.EARTH).withRadius(1, LengthUnit.EARTH_RADIUS).atAltitude(100, LengthUnit.KM);
		double orbitPeriod3 = orbit.getOrbitPeriod(ChronoUnit.MINUTES);
		System.out.println(MessageFormat.format("Orbital Period : {0} min", orbitPeriod3));

		orbit = Orbit.aroundEarth().ofOrbitPeriod(27.2, ChronoUnit.DAYS);
		System.out.println(MessageFormat.format("Moon''s distance from Earth : {0} km", orbit.getMeanDistanceFromCenter(LengthUnit.KM)));

		orbit = Orbit.of(1.87e8, LengthUnit.M, 23, ChronoUnit.HOURS);
		System.out.println(MessageFormat.format("Saturn''s mass is: {0} kg", orbit.getCenterBodyMass(MassUnit.KG)));
	}

}

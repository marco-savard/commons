package com.marcosavard.commons.astro;

import com.marcosavard.commons.astro.unit.LengthUnit;
import com.marcosavard.commons.astro.unit.MassUnit;

import java.text.MessageFormat;
import java.time.temporal.ChronoUnit;

public class OrbitDemo {

	public static void main(String[] args) {
		Orbit earthOrbit = Orbit.aroundEarth().atAltitude(100, LengthUnit.KM);
		double orbitPeriod = earthOrbit.getOrbitPeriod(ChronoUnit.MINUTES);
		System.out.println(MessageFormat.format("Orbital Period : {0} min", orbitPeriod));

		Orbit earthOrbit2 = Orbit.aroundEarth().withOrbitPeriod(orbitPeriod, ChronoUnit.MINUTES);
		System.out.println(MessageFormat.format("Orbit Radius : {0} km", earthOrbit2.getOrbitRadius(LengthUnit.KM)));
		System.out.println(MessageFormat.format("Orbit Altitude : {0} km", earthOrbit2.getOrbitAltitude(LengthUnit.KM)));

		Orbit earthOrbit3 = Orbit.aroundMass(1, MassUnit.EARTH).withRadius(1, LengthUnit.EARTH).atAltitude(100, LengthUnit.KM);
		double orbitPeriod3 = earthOrbit3.getOrbitPeriod(ChronoUnit.MINUTES);
		System.out.println(MessageFormat.format("Orbital Period : {0} min", orbitPeriod3));
	}

}

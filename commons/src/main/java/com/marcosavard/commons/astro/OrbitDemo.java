package com.marcosavard.commons.astro;

import com.marcosavard.commons.astro.space.SpaceCoordinate;
import com.marcosavard.commons.astro.unit.LengthUnit;
import com.marcosavard.commons.astro.unit.MassUnit;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class OrbitDemo {

  public static void main(String[] args) {
    // demoSatelliteOrbit();
    // demoMoonOrbit();
    demoSunPosition();
    // demoSaturnMoonOrbit();
  }

  private static void demoSatelliteOrbit() {
    Orbit orbit = Orbit.aroundEarth().atAltitude(100, LengthUnit.KM);
    double orbitPeriod = orbit.getOrbitPeriod(ChronoUnit.MINUTES);
    System.out.println(MessageFormat.format("Orbital Period : {0} min", orbitPeriod));

    orbit = Orbit.aroundEarth().ofOrbitPeriod(orbitPeriod, ChronoUnit.MINUTES);
    System.out.println(
        MessageFormat.format(
            "Orbit Distance from center : {0} km", orbit.getMeanDistanceFromCenter(LengthUnit.KM)));
    System.out.println(
        MessageFormat.format("Orbit Altitude : {0} km", orbit.getOrbitAltitude(LengthUnit.KM)));

    orbit =
        Orbit.aroundMass(1, MassUnit.EARTH)
            .withRadius(1, LengthUnit.EARTH_RADIUS)
            .atAltitude(100, LengthUnit.KM);
    double orbitPeriod3 = orbit.getOrbitPeriod(ChronoUnit.MINUTES);
    System.out.println(MessageFormat.format("Orbital Period : {0} min", orbitPeriod3));
  }

  private static void demoMoonOrbit() {
    Orbit orbit = Orbit.aroundEarth().ofOrbitPeriod(27.2, ChronoUnit.DAYS);
    System.out.println(
        MessageFormat.format(
            "Moon''s distance from Earth : {0} km",
            orbit.getMeanDistanceFromCenter(LengthUnit.KM)));
  }

  private static void demoSunPosition() {
    LocalDate date = LocalDate.of(1990, 4, 19);

    // define Earth orbit
    Orbit earthOrbit =
        Orbit.SolarOrbit.ofMeanDistance(1.0, LengthUnit.ASTRONOMICAL_UNIT)
            .withEccentricity(0.016709, 1.151E-9)
            .withMeanAnomaly(356.0470, 0.9856002585)
            .withLongitudeOfPerihelion(282.9404, 4.70935E-5)
            .withObliquityOfEcliptic(23.4393, -3.563E-7);

    SpaceCoordinate ecliptic = earthOrbit.findEclipticCoordinateOn(date);
    SpaceCoordinate equatorial = earthOrbit.findEquatorialCoordinateOn(date);

    System.out.println(equatorial.toString());
    // double r = ecliptic.
  }

  private static void demoSaturnMoonOrbit() {
    Orbit orbit = Orbit.of(1.87e8, LengthUnit.M, 23, ChronoUnit.HOURS);
    System.out.println(
        MessageFormat.format("Saturn''s mass is: {0} kg", orbit.getCenterBodyMass(MassUnit.KG)));
  }
}

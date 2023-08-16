package com.marcosavard.commons.astro;

import com.marcosavard.commons.astro.unit.LengthUnit;
import junit.framework.Assert;
import org.junit.Test;

import java.time.temporal.ChronoUnit;

// examples from
// https://www.physicsclassroom.com/class/circles/Lesson-4/Mathematics-of-Satellite-Motion
public class OrbitTest {

  @Test
  public void givenEarthOrbit_whenAltitudeIs100Km_thenPeriodIs1h44min() {
    Orbit orbit = Orbit.aroundEarth().atAltitude(100, LengthUnit.KM);
    double period = orbit.getOrbitPeriod(ChronoUnit.HOURS);
    System.out.println("  period : " + period);
    Assert.assertEquals(1.44, period, 0.01);

    double speedMs = orbit.getOrbitalSpeed(LengthUnit.M, ChronoUnit.SECONDS);
    System.out.println("  speed : " + speedMs + " m/s");
    Assert.assertEquals(7852, speedMs, 10);

    double speedKmh = orbit.getOrbitalSpeed(LengthUnit.KM, ChronoUnit.HOURS);
    System.out.println("  speed : " + speedKmh + " m/s");
    Assert.assertEquals(28267, speedKmh, 10);

    double acceleration = orbit.getOrbitalAcceleration();
    System.out.println("  acceleration : " + acceleration + " m/s2");
    Assert.assertEquals(9.53, acceleration, 0.01);

    System.out.println();
  }

  // Orbit altitude of a geo-synced satelite
  @Test
  public void givenEarthOrbit_whenPeriodIs24Hr_thenRadiusIs3e8() {
    Orbit orbit = Orbit.aroundEarth().ofOrbitPeriod(24, ChronoUnit.HOURS);
    double radius = orbit.getMeanDistanceFromCenter(LengthUnit.KM);
    System.out.println("  radius : " + radius);
    Assert.assertEquals(4.23e4, radius, 1000);

    double altitude = orbit.getOrbitAltitude(LengthUnit.KM);
    System.out.println("  altitude : " + altitude);
    Assert.assertEquals(3.59e4, altitude, 1000);

    System.out.println();
  }

  // Orbit altitude of the Moon
  @Test
  public void givenEarthOrbit_whenPeriodIs27days_thenRadiusIs3e8() {
    Orbit orbit = Orbit.aroundEarth().ofOrbitPeriod(27.2, ChronoUnit.DAYS);
    double radius = orbit.getMeanDistanceFromCenter(LengthUnit.KM);
    System.out.println("  radius : " + radius);
    Assert.assertEquals(3.82e5, radius, 1000);

    double speed = orbit.getOrbitalSpeed(LengthUnit.M, ChronoUnit.SECONDS);
    System.out.println("  speed : " + speed);
    Assert.assertEquals(1020, speed, 10);
    System.out.println();
  }

  // Orbit of Earth around Sun
  @Test
  public void givenEarthOrbitAroundSun_whenDistanceIsOneAstroUnit_thenPeriodIsOneYear() {
    Orbit orbit = Orbit.aroundSun().ofMeanDistance(1, LengthUnit.ASTRONOMICAL_UNIT);
    double orbitPeriod = orbit.getOrbitPeriod(ChronoUnit.DAYS);
    Assert.assertEquals(365.25, orbitPeriod, 0.1);
    System.out.println("  Orbital period of Earth in days: " + orbitPeriod);

    orbit = Orbit.aroundSun().ofOrbitPeriod(1, ChronoUnit.YEARS);
    double meanDistance = orbit.getMeanDistanceFromCenter(LengthUnit.ASTRONOMICAL_UNIT);
    Assert.assertEquals(1.0, meanDistance, 0.1);
    System.out.println("  Earth's mean distance in AU : " + meanDistance);

    orbit = Orbit.aroundSun().ofOrbitPeriod(1, ChronoUnit.YEARS).withEccentricity(0.0167);
    System.out.println(
        "  Mean distance of Earth in AU: "
            + orbit.getMeanDistanceFromCenter(LengthUnit.ASTRONOMICAL_UNIT));
    System.out.println(
        "  Mean distance of Earth in millions of km: "
            + orbit.getMeanDistanceFromCenter(LengthUnit.GM));
    System.out.println(
        "  Minimal distance of Earth in MKm: " + orbit.getDistanceAtPeriaxis(LengthUnit.GM));
    System.out.println(
        "  Maximal distance of Earth in MKm: " + orbit.getDistanceAtApoaxis(LengthUnit.GM));
    System.out.println();
  }

  // Orbit of Earth around Sun
  @Test
  public void givenMarsOrbitAroundSun_whenDistanceIsOneAstroUnit_thenPeriodIsOneYear() {
    Orbit orbit =
        Orbit.aroundSun()
            .ofMeanDistance(1.524, LengthUnit.ASTRONOMICAL_UNIT)
            .withEccentricity(0.0934);

    double orbitPeriod = orbit.getOrbitPeriod(ChronoUnit.DAYS);
    System.out.println("  Orbital period of Mars in days: " + orbitPeriod);
    System.out.println(
        "  Minimal distance of Mars in millions of km: "
            + orbit.getDistanceAtPeriaxis(LengthUnit.ASTRONOMICAL_UNIT));
    System.out.println(
        "  Maximal distance of Mars in millions of km: "
            + orbit.getDistanceAtApoaxis(LengthUnit.ASTRONOMICAL_UNIT));
    System.out.println();

    Orbit.SolarOrbit sunOrbit =
        Orbit.SolarOrbit.ofMeanDistance(1.524, LengthUnit.ASTRONOMICAL_UNIT);
    sunOrbit.withEccentricity(0.0934);

    double orbitPeriod2 = sunOrbit.getOrbitPeriod(ChronoUnit.DAYS);
    System.out.println("  Orbital period of Mars in days: " + orbitPeriod2);
    System.out.println(
        "  Minimal distance of Mars in millions of km: "
            + sunOrbit.getDistanceAtPerihelion(LengthUnit.ASTRONOMICAL_UNIT));
    System.out.println(
        "  Maximal distance of Mars in millions of km: "
            + sunOrbit.getDistanceAtAphelion(LengthUnit.ASTRONOMICAL_UNIT));
    System.out.println();
  }
}

package com.marcosavard.commons.astro;

import com.marcosavard.commons.astro.time.JulianDay;
import com.marcosavard.commons.geog.GeoLocation;
import com.marcosavard.commons.math.trigonometry.Angle;
import com.marcosavard.commons.math.trigonometry.Angle.Unit;

import java.text.MessageFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static com.marcosavard.commons.astro.AstroMath.range;

public class SunPosition {
  private static final double J1980 = JulianDay.of(LocalDate.of(1980, 1, 1)).getValue() - 1;

  // Ecliptic longitude of the Sun at epoch 1980.0.
  private static final double SUN_ELONG_J1980 = 278.833540;

  // Ecliptic longitude of the Sun at perigee
  private static final double SUN_ELONG_PERIGEE = 282.596403;

  // Eccentricity of Earth's orbit.
  public static final double ECCENT_EARTH_ORBIT = 0.016718;

  // Accurancy of the Kepler equation.
  public static final double KEPLER_EPSILON = 1E-6;

  // semi-major axis of Earth's orbit, km
  private static final double SUN_SEMI_MAJOR = 1.495985e8D;

  // sun's angular size, degrees, at semi-major axis distance
  private static final double SUN_ANG_SIZE = 0.533128;

  // The altitude of the sun (solar elevation angle) at the moment of sunrise or sunset: -0.833
  public static final double SUN_ALTITUDE_SUNRISE_SUNSET = -0.833;

  private double julianDay;
  private double sunAnomaly;
  private double sunLongitude;
  private double orbitalDistanceFactor;

  public static SunPosition at(ZonedDateTime moment) {
    JulianDay jd = JulianDay.of(moment);
    SunPosition position = SunPosition.at(jd.getValue());
    return position;
  }

  public static SunPosition at(double julianDay) {
    SunPosition position = new SunPosition(julianDay);
    return position;
  }

  private SunPosition(double julianDay) {
    this.julianDay = julianDay;

    double day = julianDay - J1980; // date within epoch
    double n = degRange((360 / 365.2422) * day); // mean anomaly of the Sun

    // convert from perigee co-ordinates to epoch 1980.0
    this.sunAnomaly = degRange(n + SUN_ELONG_J1980 - SUN_ELONG_PERIGEE);

    // solve equation of Kepler
    double ec = kepler(sunAnomaly, ECCENT_EARTH_ORBIT);
    ec = Math.sqrt((1 + ECCENT_EARTH_ORBIT) / (1 - ECCENT_EARTH_ORBIT)) * Math.tan(ec / 2);
    ec = 2 * Math.toDegrees(Math.atan(ec)); // true anomaly

    // Sun's geocentric ecliptic longitude
    this.sunLongitude = degRange(ec + SUN_ELONG_PERIGEE);

    // Orbital distance factor.
    this.orbitalDistanceFactor =
        ((1 + ECCENT_EARTH_ORBIT * Math.cos(Math.toRadians(ec)))
            / (1 - ECCENT_EARTH_ORBIT * ECCENT_EARTH_ORBIT));
  }

  public double getJulianDay() {
    return julianDay;
  }

  public double getSunLongitude() {
    return sunLongitude;
  }

  public double getSunAnomaly() {
    return sunAnomaly;
  }

  public double getSunDistance() {
    double sunDistance = SUN_SEMI_MAJOR / orbitalDistanceFactor; // distance to Sun in km
    return sunDistance;
  }

  public double getSunAngularSize() {
    double sunAngularSize = orbitalDistanceFactor * SUN_ANG_SIZE; // Sun's angular size in degrees
    return sunAngularSize;
  }

  @Override
  public String toString() {
    String str =
        MessageFormat.format(
            "lon={0} dist={1} AU", //
            String.format("%.2f", sunLongitude), //
            String.format("%.3f", 1 / orbitalDistanceFactor));
    return str;
  }

  private static double degRange(double d) {
    return range(d, 0, 360);
  }

  // kepler - solve the equation of Kepler
  private static double kepler(double degrees, double ecc) {
    double rads = Math.toRadians(degrees);
    double e, delta;

    e = rads;
    do {
      delta = e - ecc * Math.sin(e) - rads;
      e -= delta / (1 - ecc * Math.cos(e));
    } while (Math.abs(delta) > KEPLER_EPSILON);
    return e;
  }

  private static final long SECONDS_PER_DAY = 60 * 60 * 24;
  private static final double SYN_YEAR = 365.24;

  public static ZonedDateTime findNextSpringEquinox(LocalDate date) {
    ZonedDateTime moment = findNextTimeAtLongitude(date, 0);
    return moment;
  }

  public static ZonedDateTime findNextSummerSolstice(LocalDate date) {
    ZonedDateTime moment = findNextTimeAtLongitude(date, 90);
    return moment;
  }

  public static ZonedDateTime findNextFallEquinox(LocalDate date) {
    ZonedDateTime moment = findNextTimeAtLongitude(date, 180);
    return moment;
  }

  public static ZonedDateTime findNextWinterSolstice(LocalDate date) {
    ZonedDateTime moment = findNextTimeAtLongitude(date, 270);
    return moment;
  }

  public static ZonedDateTime findNextTimeAtLongitude(LocalDate date, int longitudeToFind) {
    ZonedDateTime moment0 = ZonedDateTime.of(date, LocalTime.MIDNIGHT, ZoneOffset.UTC);
    Instant instant0 = moment0.toInstant();
    Angle angleToFind = Angle.of(longitudeToFind, Unit.DEG);

    SunPosition position0 = SunPosition.at(moment0);
    double longitude0 = position0.getSunLongitude();
    double longitudeDelta = degRange(longitudeToFind - longitude0);
    long approxTime = (long) ((longitudeDelta / 360) * SYN_YEAR * SECONDS_PER_DAY);
    Instant instant1 = instant0.plus(approxTime, ChronoUnit.SECONDS);
    ZonedDateTime moment1 = instant1.atZone(ZoneOffset.UTC);
    SunPosition position1 = SunPosition.at(moment1);
    Angle angle1 = Angle.of(position1.getSunLongitude(), Unit.DEG);

    double angleDiff = 0;
    int i = 0;

    // Refine time of moment1
    do {
      // compare angles
      angleDiff = angle1.minus(angleToFind);
      boolean before = angleDiff < 0;
      long deltaTime = (long) (SECONDS_PER_DAY / Math.pow(2, i++));
      instant1 =
          before
              ? //
              instant1.plus(deltaTime, ChronoUnit.SECONDS)
              : //
              instant1.minus(deltaTime, ChronoUnit.SECONDS);
      moment1 = instant1.atZone(ZoneOffset.UTC);
      position1 = SunPosition.at(moment1);
      angle1 = Angle.of(position1.getSunLongitude(), Unit.DEG);
    } while (Math.abs(angleDiff) > 0.01);

    return moment1;
  }

  public static ZonedDateTime findNextApogee(LocalDate date) {
    ZonedDateTime summerSolstice = findNextSummerSolstice(date);
    ZonedDateTime apogee = summerSolstice.plus(14, ChronoUnit.DAYS);
    return apogee;
  }

  public static ZonedDateTime findNextPerigee(LocalDate date) {
    ZonedDateTime winterSolstice = findNextWinterSolstice(date);
    ZonedDateTime perigee = winterSolstice.plus(14, ChronoUnit.DAYS);
    return perigee;
  }

  public ZonedDateTime findLocalNoonAt(GeoLocation location) {
    ZonedDateTime localNoon = findLocalNoonAt(location.getLongitude().getValue());
    return localNoon;
  }

  public ZonedDateTime findLocalNoonAt(double longitude) {
    SunLocalPosition localPosition = SunLocalPosition.at(julianDay, longitude);
    double localNoonJd = localPosition.jtransit;
    ZonedDateTime localNoon = JulianDay.of(localNoonJd).toDateTime();
    return localNoon;
  }

  public ZonedDateTime[] findSunriseSunsetAt(GeoLocation location) {
    double latitude = location.getLatitude().getValue();
    double longitude = location.getLongitude().getValue();
    ZonedDateTime[] sunriseSunset = findSunriseSunsetAt(latitude, longitude);
    return sunriseSunset;
  }

  public ZonedDateTime[] findSunriseSunsetAt(double latitude, double longitude) {
    SunLocalPosition localPosition = SunLocalPosition.at(julianDay, longitude);
    double latitudeRad = Math.toRadians(latitude);

    // Hour angle
    final double omega =
        Math.acos(
            (Math.sin(Math.toRadians(SUN_ALTITUDE_SUNRISE_SUNSET))
                    - Math.sin(latitudeRad) * Math.sin(localPosition.delta))
                / (Math.cos(latitudeRad) * Math.cos(localPosition.delta)));
    System.out.println("  omega = " + omega);

    ZonedDateTime[] sunriseSunset = new ZonedDateTime[] {};

    if (!Double.isNaN(omega)) {
      long n = localPosition.n;
      double m = localPosition.meanAnomaly;
      double l = localPosition.eclipticLong;
      double jtransit = localPosition.jtransit;

      // Sunset
      final double jset =
          SunLocalPosition.J2000
              + 0.0009
              + ((Math.toDegrees(omega) - longitude) / 360
                  + n
                  + 0.0053 * Math.sin(m)
                  - 0.0069 * Math.sin(2 * l));

      // Sunrise
      final double jrise = jtransit - (jset - jtransit);

      ZonedDateTime sunrise = JulianDay.of(jrise).toDateTime();
      ZonedDateTime sunset = JulianDay.of(jset).toDateTime();

      sunriseSunset = new ZonedDateTime[] {sunrise, sunset};
    }

    return sunriseSunset;
  }

  // inner class
  private static class SunLocalPosition {
    private static final double J2000 = 2451545.0;

    public static SunLocalPosition at(double julianDay, double longitude) {
      double fraction = -longitude / 360.0;

      // Calculate current Julian cycle (number of days since 2000-01-01).
      double nstar = julianDay - J2000 - fraction - 0.0009;
      long n = Math.round(nstar);

      // Approximate solar noon
      double jstar = J2000 + n + fraction + 0.0009;

      // Solar mean anomaly
      final double m = Math.toRadians((357.5291 + 0.98560028 * (jstar - J2000)) % 360.0);

      // Equation of center
      final double c = 1.9148 * Math.sin(m) + 0.0200 * Math.sin(2 * m) + 0.0003 * Math.sin(3 * m);

      // Ecliptic longitude
      final double lambda = Math.toRadians((Math.toDegrees(m) + 102.9372 + c + 180) % 360.0);

      // Solar transit (hour angle for solar noon)
      final double jtransit = jstar + 0.0053 * Math.sin(m) - 0.0069 * Math.sin(2 * lambda);

      // Declination of the sun.
      final double delta = Math.asin(Math.sin(lambda) * Math.sin(Math.toRadians(23.439)));

      SunLocalPosition localPosition = new SunLocalPosition(n, lambda, m, delta, jtransit);
      return localPosition;
    }

    private SunLocalPosition(long n, double lambda, double m, double delta, double jtransit) {
      this.n = n;
      this.eclipticLong = lambda;
      this.meanAnomaly = m;
      this.delta = delta;
      this.jtransit = jtransit;
    }

    private long n;
    private double meanAnomaly;
    private double eclipticLong;
    private double delta;
    private double jtransit;
  }
}

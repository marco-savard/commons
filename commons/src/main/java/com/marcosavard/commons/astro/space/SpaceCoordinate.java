package com.marcosavard.commons.astro.space;

import java.text.MessageFormat;
import java.time.ZonedDateTime;

import static com.marcosavard.commons.math.SafeMath.*;
import static java.lang.Math.sqrt;

// // see https://stjarnhimlen.se/comp/ppcomp.html
public class SpaceCoordinate {
  public static final SpaceCoordinate VERNAL_POINT = SpaceCoordinate.sphereOf(0, 0);
  private double x, y, z; // rectangular
  private double ra, dec, distance; // spheric

  private SpaceCoordinateFormatter defaultFormatter = new SpaceCoordinateFormatter();

  public static SpaceCoordinate rectangleOf(double x, double y, double z) {
    double distance = sqrt(x * x + y * y + z * z);
    double ra = atan2d(y, x);
    double dec = asind(z / distance);
    return new SpaceCoordinate(x, y, z, distance, ra, dec);
  }

  public static SpaceCoordinate sphereOf(double raDeg, double dec) {
    return sphereOf(raDeg, dec, Double.MAX_VALUE);
  }

  public static SpaceCoordinate sphereOf(double raDeg, double dec, double distance) {
    double x = distance * cosd(raDeg) * cosd(dec);
    double y = distance * sind(raDeg) * cosd(dec);
    double z = distance * sind(dec);
    return new SpaceCoordinate(x, y, z, distance, raDeg, dec);
  }

  public static SpaceCoordinate of(RightAscension ascension, Declination declination) {
    return sphereOf(ascension.toDegrees(), declination.toDegrees());
  }

  private SpaceCoordinate(double x, double y, double z, double distance, double ra, double dec) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.distance = distance;
    this.ra = ra;
    this.dec = dec; //deg
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getZ() {
    return z;
  }

  public double getRightAscensionDegree() {
    return ra;
  }

  public double getRightAscensionHour() {
    return ra / 15.0;
  }

  public double getDeclinationDegree() {
    return dec;
  }

  public double getDistance() {
    return distance;
  }

  /**
   * Compute the angular distance, in degrees, between that location.
   *
   * @param other to which distance is computed
   * @return the distance in degrees
   */
  public double distanceFrom(SpaceCoordinate other) {
    double dec1 = dec;
    double ra1 = ra;
    double dec2 = other.dec;
    double ra2 = other.ra;

    double cosDist = sind(dec1) * sind(dec2) + cosd(dec1) * cosd(dec2) * cosd(ra1 - ra2);
    double dist = acosd(cosDist);
    return dist;
  }

  public double[] findZenithAt(ZonedDateTime moment) {
    double n = moment.getDayOfYear();
    int h = moment.getHour();
    int m = moment.getMinute();
    int s = moment.getSecond();
    double decimalHour = h + (m / 60.0) + (s / 3600.0);
    double a = 0.98563 * n;
    double b = 15.0405 * decimalHour;
    double latitude = dec;
    double longitude = ra - a - b - 98.971;
    double[] coordinates = new double[] {latitude, longitude};
    return coordinates;
  }

  @Override
  public String toString() {
    return defaultFormatter.format(this);
  }

  /*
  //celestrial north, south poles
  public static final SpaceCoordinate NORTH_POLE = SpaceCoordinate.of(0, 90);
  public static final SpaceCoordinate SOUTH_POLE = SpaceCoordinate.of(0, -90);




  public SpaceCoordinate rotate(double lon) {
    double rx = distance * cosd(lon);
    double ry = distance * sind(lon);
    double rz = 0.0;
    return SpaceCoordinate.rectangleOf(rx, ry, rz);
  }


  public enum Unit {
    DEGREE, HOUR
  };

  public enum Format {
    HMS, DMS
  };

  private double rightAscension; // [0-24 hr]
  private double declination; // [0-360 degrees]
  private double distance;
  private double x, y, z;

  public static SpaceCoordinate of(int ra1, int ra2, int ra3, Unit raUnit, int dec1, int dec2,
                                   int dec3, Unit declUnit) {
    double ra = ra1 + (ra2 / 60.0) + (ra3 / 3600.0);
    double dec = dec1 + (dec2 / 60.0) + (dec3 / 3600.0);
    double rightAscensionHours = (raUnit == Unit.HOUR) ? ra : ra / 15.0;
    double declinationDegrees = (declUnit == Unit.DEGREE) ? dec : dec * 15.0;
    SpaceCoordinate location = SpaceCoordinate.of(rightAscensionHours, declinationDegrees);
    return location;
  }





  public static SpaceCoordinate of(double rightAscensionInHours, double declinationInDegrees, double distance) {
    SpaceCoordinate position = new SpaceCoordinate(rightAscensionInHours, declinationInDegrees, distance);
    return position;
  }






  //rotate around x-axis
  public SpaceCoordinate rotateX(double angle) {
  double x1 = x;
  double y1 = y * cosd(angle) - z * sind(angle);
  double z1 = y * sind(angle) + z * cosd(angle);
  SpaceCoordinate rotated = rectangleOf(x1, y1, z1);
  return rotated;
  }


  public static SpaceCoordinate findSpaceLocation(SpaceCoordinate location1, LocalDate date1,
                                                  LocalDate date2) {
    double n = getDecimalYear(date2) - getDecimalYear(date1);
    double ra1 = location1.getRightAscensionDegrees();
    double decl1 = location1.getDeclination();
    double da =
        n * (3.07327 + 1.33617 * Math.sin(Math.toRadians(ra1)) * Math.tan(Math.toRadians(decl1)));
    double dd = n * (20.0468 * Math.cos(Math.toRadians(ra1)));
    double daDeg = da / 240.0;
    double ddDeg = dd / 3600.0;

    double ra2 = ra1 + daDeg;
    double decl2 = decl1 + ddDeg;
    SpaceCoordinate location2 = SpaceCoordinate.of(ra2 / 15.0, decl2);
    return location2;
  }

  private static double getDecimalYear(LocalDate date) {
    int nbDays = date.isLeapYear() ? 366 : 365;
    double decimalYear = date.getYear() + ((date.getDayOfYear() - 1) / (double) nbDays);
    return decimalYear;
  }

  public static SpaceCoordinate findZenithPositionAbove(double[] coordinates,
                                                        ZonedDateTime moment) {
    double declination = coordinates[0]; //latitude
    double n = moment.getDayOfYear();
    int h = moment.getHour();
    int m = moment.getMinute();
    int s = moment.getSecond();
    double decimalHour = h + (m / 60.0) + (s / 3600.0);
    double a = 0.98563 * n;
    double b = 15.0405 * decimalHour;

    double longitude =coordinates[1];
    double rightAscensionDegrees = longitude + a + b + 98.971;
    Angle ra = Angle.of(rightAscensionDegrees, Angle.Unit.DEG);
    double rightAscensionHours = (ra.degrees() / 360) * 24;
    SpaceCoordinate position = SpaceCoordinate.of(rightAscensionHours, declination);
    return position;
  }

  private SpaceCoordinate(double rightAscension, double declination, double distanceFromCenter) {
    this.rightAscension = rightAscension;
    this.declination = declination;
    this.distance = distanceFromCenter;
    this.x = distanceFromCenter * cosd(rightAscension * 15) * cosd(declination);
    this.y = distanceFromCenter * sind(rightAscension * 15) * cosd(declination);
    this.z = distanceFromCenter * sind(declination);
  }




  public ZonedDateTime getMomentZenithAt(LocalDate date, double longitude) {
    double ra = rightAscension * 15.0;
    double n = date.getDayOfYear();
    double a = 0.985635 * n;
    double h = (ra - longitude - a - 98.9715) / 15.0405;
    h = range(h, 0, 24); // ramener h dans 0..24
    int hour = (int) Math.floor(h);
    int minute = (int) Math.floor((h - hour) * 60);
    LocalTime time = LocalTime.of(hour, minute);
    ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneOffset.UTC);
    return moment;
  }

   */

  //
  // inner classes
  //
  public static class RightAscension {
    private final double hour; // range 0..24

    public static RightAscension ofDegrees(double degrees) {
      return new RightAscension(degrees / 15.0);
    }

    public static RightAscension ofHours(double hours) {
      return new RightAscension(hours);
    }

    // todo validate hours <= 24, min <= 60
    public static RightAscension of(int hour, int minute, double second) {
      return new RightAscension(hour + (minute / 60.0) + (second / 3600.0));
    }

    private RightAscension(double hour) {
      this.hour = hour;
    }

    @Override
    public String toString() {
      double hour = Math.floor(this.hour);
      double minute = Math.floor((this.hour - hour) * 60);
      double second = Math.round(((this.hour - hour) * 60 - minute) * 60);
      String str = MessageFormat.format("{0}h {1}m {2}s", hour, minute, second);
      return str;
    }

    public double toHour() {
      return this.hour;
    }

    public double toDegrees() {
      return (this.hour * 15.0);
    }
  }

  public static class Declination {
    private static final char DEGREE = '\u00B0';
    private static final char MINUTE = '\u2032';
    private static final char SECOND = '\u2033';

    private final double value; // range -90..+90

    // validate all positive or all negative
    public static Declination of(int degree, int minute, double second) {
      boolean allPositive = (degree >= 0) && (minute >= 0) && (second >= 0);
      boolean allNegative = (degree <= 0) && (minute <= 0) && (second <= 0);
      boolean valid = allPositive || allNegative;

      if (!valid) {
        ArithmeticException ex = new InvalidDeclinationException();
        throw ex;
      }

      return new Declination(degree + (minute / 60.0) + (second / 3600.0));
    }

    public static Declination of(double degree) {
      return new Declination(degree);
    }

    private Declination(double value) {
      this.value = value;
    }

    @Override
    public String toString() {
      double degree = Math.floor(value);
      double minute = Math.floor((value - degree) * 60);
      double second = Math.round(((value - degree) * 60 - minute) * 60);
      String str =
          MessageFormat.format(
              "{0}{1} {2}{3} {4}{5}", degree, DEGREE, minute, MINUTE, second, SECOND);
      return str;
    }

    public double toDegrees() {
      return this.value;
    }
  }

  public static class InvalidDeclinationException extends ArithmeticException {}

  /* *
     * Compute the angular distance, in radians, between that location.
     *
     * @param other to which distance is computed
     * @return the distance in radians
     * /
    public double distanceFromOld(SpaceCoordinate other) {
      if (other == null) {
        return 0;
      }

      double ra0 = Math.toRadians(rightAscension * 15.0);
      double ra1 = Math.toRadians(other.rightAscension * 15.0);

      double decl0 = Math.toRadians(declination);
      double decl1 = Math.toRadians(other.declination);

      double deltaLon = Math.abs(ra0 - ra1);
      double greatCircle = (Math.sin(decl0) * Math.sin(decl1))
          + (Math.cos(decl0) * Math.cos(decl1) * Math.cos(deltaLon));
      double deltaAngle = Math.acos(greatCircle);
      return deltaAngle;
    }

    @SuppressWarnings("serial")
    private static class InvalidDeclinationException extends ArithmeticException {
      private static final String MESSAGE =
          "Degrees, minutes, seconds must be all positive or all negative";

      InvalidDeclinationException() {
        super(MESSAGE);
      }

    }

    public SpaceCoordinate addTo(SpaceCoordinate addend) {
  	  double x1 = this.x + addend.x;
  	  double y1 = this.y + addend.y;
  	  double z1 = this.z + addend.z;
  	  SpaceCoordinate summation = SpaceCoordinate.rectangleOf(x1, y1, z1);
  	  return summation;
    }


  */

}

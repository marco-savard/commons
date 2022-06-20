package com.marcosavard.commons.astro;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

//import com.marcosavard.commons.geog.GeoLocation;
import static com.marcosavard.commons.astro.AstroMath.sind; 
import static com.marcosavard.commons.astro.AstroMath.cosd; 
import static com.marcosavard.commons.astro.AstroMath.asind; 
import static com.marcosavard.commons.astro.AstroMath.acosd; 
import static com.marcosavard.commons.astro.AstroMath.atan2d; 
import static com.marcosavard.commons.astro.AstroMath.range;

import com.marcosavard.commons.math.trigonometry.Angle;
import com.marcosavard.commons.math.arithmetic.Base;

// in equatorial coordinates
public class SpaceLocation {
  //celestrial north, south poles
  public static final SpaceLocation NORTH_POLE = SpaceLocation.of(0, 90); 
  public static final SpaceLocation SOUTH_POLE = SpaceLocation.of(0, -90); 
  public static final SpaceLocation VERNAL_POINT = SpaceLocation.of(0, 0); 
  
  private static final char DEGREE_SIGN = '\u00B0';
  private static final char MINUTE_SIGN = '\u2032'; 
  private static final char SECOND_SIGN = '\u2033';
  
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

  public static SpaceLocation of(int ra1, int ra2, int ra3, Unit raUnit, int dec1, int dec2,
      int dec3, Unit declUnit) {
    double ra = ra1 + (ra2 / 60.0) + (ra3 / 3600.0);
    double dec = dec1 + (dec2 / 60.0) + (dec3 / 3600.0);
    double rightAscensionHours = (raUnit == Unit.HOUR) ? ra : ra / 15.0;
    double declinationDegrees = (declUnit == Unit.DEGREE) ? dec : dec * 15.0;
    SpaceLocation location = SpaceLocation.of(rightAscensionHours, declinationDegrees);
    return location;
  }

  public static SpaceLocation of(RightAscension ascension, Declination declination) {
    return of(ascension.toHour(), declination.toDegrees());
  }
  
  public static SpaceLocation of(double rightAscensionInHours, double declinationInDegrees) {
	 return of(rightAscensionInHours, declinationInDegrees, 1);
  }

  public static SpaceLocation of(double rightAscensionInHours, double declinationInDegrees, double distance) {
    SpaceLocation position = new SpaceLocation(rightAscensionInHours, declinationInDegrees, distance);
    return position;
  }
  
  public static SpaceLocation rectangleOf(double x, double y, double z) {
	  double r = Math.sqrt( x*x + y*y + z*z );	
	  double ra = atan2d(y, x);
	  double dec = asind( z / r );
	  SpaceLocation location = SpaceLocation.of(ra/15, dec, r); 
	  return location;
  }

  public double getRightAscensionDegrees() {
    return rightAscension * 15;
  }

  public double getRightAscensionHour() {
    return rightAscension;
  }

  public double getDeclination() {
    return declination;
  }
  
  public double getX() {
		return x;
  }

  public double getY() {
		return y;
  }

  //rotate around x-axis
  public SpaceLocation rotateX(double angle) {
		double x1 = x;
		double y1 = y * cosd(angle) - z * sind(angle);
		double z1 = y * sind(angle) + z * cosd(angle);
		SpaceLocation rotated = rectangleOf(x1, y1, z1);
		return rotated;
  }
  

  public static SpaceLocation findSpaceLocation(SpaceLocation location1, LocalDate date1,
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
    SpaceLocation location2 = SpaceLocation.of(ra2 / 15.0, decl2);
    return location2;
  }

  private static double getDecimalYear(LocalDate date) {
    int nbDays = date.isLeapYear() ? 366 : 365;
    double decimalYear = date.getYear() + ((date.getDayOfYear() - 1) / (double) nbDays);
    return decimalYear;
  }

  public static SpaceLocation findZenithPositionAbove(double[] coordinates,
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
    SpaceLocation position = SpaceLocation.of(rightAscensionHours, declination);
    return position;
  }

  private SpaceLocation(double rightAscension, double declination, double distanceFromCenter) {
    this.rightAscension = rightAscension;
    this.declination = declination;
    this.distance = distanceFromCenter;
    this.x = distanceFromCenter * cosd(rightAscension * 15) * cosd(declination);
    this.y = distanceFromCenter * sind(rightAscension * 15) * cosd(declination);
    this.z = distanceFromCenter * sind(declination);
  }

  public double[] getZenithAt(ZonedDateTime moment) {
    double rightAscensionDegrees = rightAscension * 15.0;
    double n = moment.getDayOfYear();
    int h = moment.getHour();
    int m = moment.getMinute();
    int s = moment.getSecond();
    double decimalHour = h + (m / 60.0) + (s / 3600.0);
    double a = 0.98563 * n;
    double b = 15.0405 * decimalHour;
    double latitude = declination;
    double longitude = rightAscensionDegrees - a - b - 98.971;
    double[] coordinates = new double[] {latitude, longitude};
    return coordinates;
  }

  @Override
  public String toString() {
    Base hms = Base.of(24, 60, 60);
    String ra = Double.toString(Math.round(rightAscension * 15.0)) + DEGREE_SIGN;
    long[] rae = hms.encode((long) (rightAscension * 3600));
    String h = String.format("%02d", rae[0]);
    String m = String.format("%02d", rae[1]);
    String s = String.format("%02d", rae[2]);
    String raStr = h + "h" + m + "m" + s + "s";

    Base dms = Base.of(90, 60, 60);
    long[] de = dms.encode((long) (declination * 3600));
    String d = String.format("%02d", de[0]);
    m = String.format("%02d", de[1]);
    s = String.format("%02d", de[2]);
    String declStr = d + DEGREE_SIGN + m + MINUTE_SIGN + s + SECOND_SIGN;

    String str = MessageFormat.format("ra={0} ({1}), dec={2}", raStr, ra, declStr);
    return str;
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
      String str = MessageFormat.format("{0}{1} {2}{3} {4}{5}", degree, DEGREE, minute, MINUTE,
          second, SECOND);
      return str;
    }

    public double toDegrees() {
      return this.value;
    }
  }
  
  /**
   * Compute the angular distance, in degrees, between that location.
   * 
   * @param other to which distance is computed
   * @return the distance in degrees
   */
  public double distanceFrom(SpaceLocation other) {
	  double dec1 = declination; 
	  double ra1 = rightAscension; 
	  double dec2 = other.declination; 
	  double ra2 = other.declination; 
	  
	  double cosDist = sind(dec1) * sind(dec2) + cosd(dec1) * cosd(dec2) * cosd(ra1 - ra2);
	  double dist = acosd(cosDist); 
	  return dist;
  }

  /**
   * Compute the angular distance, in radians, between that location.
   * 
   * @param other to which distance is computed
   * @return the distance in radians
   */
  public double distanceFromOld(SpaceLocation other) {
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

  public SpaceLocation addTo(SpaceLocation addend) {
	  double x1 = this.x + addend.x;
	  double y1 = this.y + addend.y;
	  double z1 = this.z + addend.z;
	  SpaceLocation summation = SpaceLocation.rectangleOf(x1, y1, z1);
	  return summation;
  }







}

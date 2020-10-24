package com.marcosavard.commons.geog;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import com.marcosavard.commons.math.Maths;

/**
 * A class that represents geographical coordinates (latitude and longitude). It provides methods to
 * display coordinates in decimal or degrees/minutes/seconds formats. It computes distance to
 * another geographical coordinate. This class is immutable (values are set at construction, and
 * cannot be modified during the lifetime of an instance.
 * 
 * Ref: https://www.movable-type.co.uk/scripts/latlong.html
 * http://www.edwilliams.org/avform.htm#Intermediate
 * 
 * @author Marco
 *
 */
@SuppressWarnings("serial")
public class GeoLocation implements Serializable {
  public static final GeoLocation NORTH_POLE = GeoLocation.of(90, 0);
  public static final GeoLocation SOUTH_POLE = GeoLocation.of(-90, 0);
  public static final GeoLocation GREENWICH = GeoLocation.of(51.48, 0);

  private static final char DEGREE = '\u00B0';
  private static final char MINUTE = '\u2032';
  private static final char SECOND = '\u2033';
  private static final double EARTH_RADIUS = 6371.01; // Earth's mean radius in km
  private static final double EPSILON = 0.01;

  public enum Format {
    DECIMAL, DEG_MIN_SEC, DEG_MIN_SEC_HTML
  };

  public enum LatitudeHemisphere {
    NORTH, SOUTH
  };

  public enum LongitudeHemisphere {
    EAST, WEST
  };

  private final Latitude latitude;
  private final Longitude longitude;

  // required by GWT
  @SuppressWarnings("unused")

  /**
   * Get coordinate by decimal latitude and longitude
   * 
   * @param latitude
   * @param longitude
   */
  public static GeoLocation of(double latitude, double longitude) {
    return GeoLocation.of(Latitude.of(latitude), Longitude.of(longitude));
  }

  public static GeoLocation of(int deg1, int min1, LatitudeHemisphere lat, int deg2, int min2,
      LongitudeHemisphere lon) {
    Latitude latitude = Latitude.of(deg1, min1, lat);
    Longitude longitude = Longitude.of(deg2, min2, lon);
    return of(latitude, longitude);
  }

  private static GeoLocation of(Latitude latitude, Longitude longitude) {
    return new GeoLocation(latitude, longitude);
  }

  private GeoLocation(Latitude latitude, Longitude longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public Latitude getLatitude() {
    return latitude;
  }

  public Longitude getLongitude() {
    return longitude;
  }

  public double[] toCoordinates() {
    double[] coordinates = new double[] {latitude.value, longitude.value};
    return coordinates;
  }

  @Override
  public String toString() {
    String msg = MessageFormat.format("{0}, {1}", toLatitudeString(), toLongitudeString());
    return msg;
  }

  /**
   * Return textual display of a coordinate, according a given format
   * 
   * @param format one of DECIMAL, DEG_MIN_SEC and DEG_MIN_SEC_HTML
   * @return textual display of a coordinate
   */
  public String toDisplayString(Format format) {
    String ns = getLatHemisphere();
    String ew = getLonHemisphere();
    String pat;

    if (format == Format.DECIMAL) {
      return toString();
    } else if (format == Format.DEG_MIN_SEC) {
      pat = "{0}\u00B0{1}''{2}\" {3}";
    } else if (format == Format.DEG_MIN_SEC_HTML) {
      pat = "{0}&deg;{1}&prime;{2}&Prime; {3}";
    } else {
      pat = "Undefined Format";
    }

    String lat = toDegMinSecString(pat, latitude.getValue(), ns);
    String lon = toDegMinSecString(pat, longitude.getValue(), ew);
    String str = MessageFormat.format("{0}, {1}", lat, lon);

    return str;
  }

  public String toDisplayString() {
    return toDisplayString(Format.DEG_MIN_SEC);
  }

  public String toLatitudeString() {
    String ns = latitude.getValue() > 0 ? "N" : "S";
    String text = String.format("%2.2f ", Math.abs(latitude.getValue())) + ns;
    return text;
  }

  public String toLongitudeString() {
    String ew = longitude.getValue() > 0 ? "E" : "W";
    String text = String.format("%2.2f ", Math.abs(longitude.getValue())) + ew;
    return text;
  }

  @Override
  public boolean equals(Object other) {
    boolean equal = false;

    if (other instanceof GeoLocation) {
      GeoLocation otherCoordinate = (GeoLocation) other;
      equal =
          Maths.equal(otherCoordinate.getLatitude().getValue(), getLatitude().getValue(), EPSILON);
      equal &= Maths.equal(otherCoordinate.getLongitude().getValue(), getLongitude().getValue(),
          EPSILON);
    }
    return equal;
  }

  @Override
  public int hashCode() {
    int hash = (int) (getLatitude().getValue() + getLongitude().getValue());
    return hash;
  }

  /**
   * Compute the distance, in kilometers, between that location on the Earth.
   * 
   * @param location to which distance is computed
   * @return the distance in kilometers
   */
  public double findDistanceFrom(GeoLocation location) {
    return findDistanceFrom(location, EARTH_RADIUS);
  }

  /**
   * Compute the distance, in kilometers, between that location.
   * 
   * @param location to which distance is computed
   * @param radius of the planet (Earth's radius by default)
   * @return the distance in kilometers
   */
  public double findDistanceFrom(GeoLocation location, double radius) {
    double angularDistance = findRadianDistanceFrom(location);
    double distance = radius * angularDistance;
    return distance;
  }

  public double findDegreeDistanceFrom(GeoLocation location) {
    return Math.toDegrees(findRadianDistanceFrom(location));
  }

  /**
   * Compute the angular distance, in radians, between that location.
   * 
   * @param location to which distance is computed
   * @return the distance in radians
   */
  public double findRadianDistanceFrom(GeoLocation location) {
    if (location == null) {
      return 0;
    }

    double lat0 = toRadians(location.getLatitude().getValue());
    double lon0 = toRadians(location.getLongitude().getValue());
    double lat1 = toRadians(getLatitude().getValue());
    double lon1 = toRadians(getLongitude().getValue());

    double deltaLon = Math.abs(lon0 - lon1);
    double greatCircle =
        (Math.sin(lat0) * Math.sin(lat1)) + (Math.cos(lat0) * Math.cos(lat1) * Math.cos(deltaLon));
    double deltaAngle = Math.acos(greatCircle);
    return deltaAngle;
  }

  public double findInitialBearingTo(GeoLocation destination) {
    double lat1 = Math.toRadians(this.getLatitude().getValue());
    double lon1 = Math.toRadians(this.getLongitude().getValue());
    double lat2 = Math.toRadians(destination.getLatitude().getValue());
    double lon2 = Math.toRadians(destination.getLongitude().getValue());

    double y = Math.sin(lon2 - lon1) * Math.cos(lat2);
    double x =
        Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1);
    double theta = Math.atan2(y, x);
    double bearing = Math.toDegrees(theta);
    bearing = (bearing + 360) % 360;
    return bearing;
  }

  public double findTerminalBearingTo(GeoLocation destination) {
    double oppposite = destination.findInitialBearingTo(this);
    double bearing = (oppposite + 180) % 360;
    return bearing;
  }

  public GeoLocation findMidpointTo(GeoLocation destination) {
    double lat1 = Math.toRadians(this.getLatitude().getValue());
    double lon1 = Math.toRadians(this.getLongitude().getValue());
    double lat2 = Math.toRadians(destination.getLatitude().getValue());
    double lon2 = Math.toRadians(destination.getLongitude().getValue());

    double x = Math.cos(lat2) * Math.cos(lon2 - lon1);
    double by = Math.cos(lat2) * Math.sin(lon2 - lon1);

    double lat = Math.atan2(Math.sin(lat1) + Math.sin(lat2), //
        Math.sqrt((Math.cos(lat1) + x) * (Math.cos(lat1) + x) + by * by));
    double lon = lon1 + Math.atan2(by, Math.cos(lat1) + x);

    double latitude = Math.toDegrees(lat);
    double longitude = Math.toDegrees(lon);
    GeoLocation point = GeoLocation.of(latitude, longitude);
    return point;
  }

  public GeoLocation findIntermediatePointTo(GeoLocation destination, double f) {
    double lat1 = Math.toRadians(this.getLatitude().getValue());
    double lon1 = Math.toRadians(this.getLongitude().getValue());
    double lat2 = Math.toRadians(destination.getLatitude().getValue());
    double lon2 = Math.toRadians(destination.getLongitude().getValue());

    double d = 2 * Math.asin(Math.sqrt(Math.pow((Math.sin((lat1 - lat2) / 2)), 2)
        + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin((lon1 - lon2) / 2), 2)));

    double a = Math.sin((1 - f) * d) / Math.sin(d);
    double b = Math.sin(f * d) / Math.sin(d);

    double x = a * Math.cos(lat1) * Math.cos(lon1) + b * Math.cos(lat2) * Math.cos(lon2);
    double y = a * Math.cos(lat1) * Math.sin(lon1) + b * Math.cos(lat2) * Math.sin(lon2);
    double z = a * Math.sin(lat1) + b * Math.sin(lat2);
    double lat = Math.atan2(z, Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
    double lon = Math.atan2(y, x);

    double latitude = Math.toDegrees(lat);
    double longitude = Math.toDegrees(lon);
    GeoLocation point = GeoLocation.of(latitude, longitude);
    return point;
  }

  //
  // private methods
  //

  private String getLatHemisphere() {
    String ns = (latitude.getValue() >= 0) ? "N" : "S";
    return ns;
  }

  private String getLonHemisphere() {
    return getLonHemisphere(Locale.getDefault());
  }

  private String getLonHemisphere(Locale locale) {
    String ew;

    if (locale.getLanguage().equals(Locale.FRENCH)) {
      ew = (longitude.getValue() >= 0) ? "E" : "O";
    } else if (locale.getLanguage().equals(Locale.GERMAN)) {
      ew = (longitude.getValue() >= 0) ? "O" : "W";
    } else {
      ew = (longitude.getValue() >= 0) ? "E" : "W";
    }

    return ew;
  }

  private String toDegMinSecString(String pat, double coord, String hemis) {
    coord = Math.abs(coord);
    int deg = (int) Math.floor(coord);
    int min = (int) Math.floor((60 * coord) - (60 * deg));
    int sec = (int) Math.floor((60 * 60 * coord) - (60 * 60 * deg) - (60 * min));
    String str = MessageFormat.format(pat, deg, min, sec, hemis);
    return str;
  }


  private double toRadians(double degs) {
    double rads = degs * Math.PI / 180.0;
    return rads;
  }

  public static class Latitude {
    private final double value; // range -90..+90

    public static Latitude of(double degree) {
      return new Latitude(degree);
    }

    public static Latitude of(int degree, int minute, LatitudeHemisphere hemisphere) {
      return of(degree, minute, 0.0, hemisphere);
    }

    public static Latitude of(int degree, int minute, double second,
        LatitudeHemisphere hemisphere) {
      double absDegrees = degree + (minute / 60.0) + (second / 3600.0);
      double sign = hemisphere.equals(LatitudeHemisphere.NORTH) ? 1 : -1;
      return Latitude.of(absDegrees * sign);
    }

    private Latitude(double value) {
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

    public double getValue() {
      return this.value;
    }
  }

  public static class Longitude {
    private final double value; // range -180..+180

    public static Longitude of(double degree) {
      return new Longitude(degree);
    }

    public static Longitude of(int degree, int minute, LongitudeHemisphere hemisphere) {
      return Longitude.of(degree, minute, 0, hemisphere);
    }

    public static Longitude of(int degree, int minute, double second,
        LongitudeHemisphere hemisphere) {
      double absDegrees = degree + (minute / 60.0) + (second / 3600.0);
      double sign = hemisphere.equals(LongitudeHemisphere.EAST) ? 1 : -1;
      return Longitude.of(absDegrees * sign);
    }

    private Longitude(double value) {
      // normalize to -180 .. + 180
      this.value = (value + 540) % 360 - 180;
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

    public double getValue() {
      return this.value;
    }
  }



}

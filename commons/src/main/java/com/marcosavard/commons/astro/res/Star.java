package com.marcosavard.commons.astro.res;

import com.marcosavard.commons.astro.Constellation;
import com.marcosavard.commons.astro.space.SpaceCoordinate;

import java.text.MessageFormat;

public class Star {
  private String rank;
  private String constell;
  private String name;
  private Double magn;
  private Double distance;
  private RightAscension rightAscension;
  private Declination declination;

  @Override
  public String toString() {
    Constellation constellation = Constellation.of(constell);
    String starname = constellation.getDisplayName(rank);
    SpaceCoordinate spaceLocation =
        SpaceCoordinate.sphereOf(rightAscension.toDegrees(), declination.degrees);
    String str = MessageFormat.format("{0} ({1} {2})", name, starname, spaceLocation);
    return str;
  }

  public String getConstellation() {
    return constell;
  }

  public double getMagnitude() {
    return magn;
  }

  public double getRightAscension() {
    return rightAscension.hours;
  }

  public double getDeclination() {
    return declination.degrees;
  }

  public double findDistanceFrom(double ra, double dec) {
    double distance2 = findDistance2From(ra, dec);
    double distance = Math.sqrt(distance2);
    return distance;
  }

  public double findDistance2From(double ra, double dec) {
    double ra2 = (ra - rightAscension.hours) * (ra - rightAscension.hours) * 15;
    double dec2 = (dec - declination.degrees) * (dec - declination.degrees);
    double distance2 = ra2 + dec2;
    return distance2;
  }

  private static String[] stripNonNumeric(String[] coordinates) {
    String[] stripped = new String[coordinates.length];

    for (int i = 0; i < coordinates.length; i++) {
      stripped[i] = coordinates[i].replaceAll("[^0-9.-]", "");
    }

    return stripped;
  }

  public static class RightAscension {
    private double hours;

    public static RightAscension valueOf(String value) {
      String[] coordinates = value.split("\\s+");
      coordinates = stripNonNumeric(coordinates);
      int deg = Integer.parseInt(coordinates[0].trim());
      int min = Integer.parseInt(coordinates[1].trim());
      double sec = Double.parseDouble(coordinates[2].trim());
      RightAscension ra = new RightAscension(deg, min, sec);
      return ra;
    }

    private RightAscension(int hour, int min, double sec) {
      hours = hour + (min / 60.0) + (sec / 3600.0);
    }

    public double toDegrees() {
      return hours * 15;
    }

    @Override
    public String toString() {
      String str = String.format("%.2f", hours);
      return str;
    }
  }

  public static class Declination {
    private double degrees;

    public static Declination valueOf(String value) {
      String[] coordinates = value.split("\\s+");
      coordinates = stripNonNumeric(coordinates);

      int deg = Integer.parseInt(coordinates[0].trim());
      int min = Integer.parseInt(coordinates[1].trim());
      double sec = Double.parseDouble(coordinates[2].trim());
      Declination ra = new Declination(deg, min, sec);
      return ra;
    }

    private Declination(int deg, int min, double sec) {
      degrees = deg;
      degrees += Math.signum(deg) * (min / 60.0);
      degrees += Math.signum(deg) * (sec / 3600.0);
    }

    @Override
    public String toString() {
      String str = String.format("%.2f", degrees);
      return str;
    }
  }
}

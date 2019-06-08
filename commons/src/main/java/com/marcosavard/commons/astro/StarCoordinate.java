package com.marcosavard.commons.astro;

import java.text.MessageFormat;

public class StarCoordinate {
  // some famous stars
  public static final StarCoordinate ANTARES =
      StarCoordinate.of(RightAscension.of(16, 29, 24.5), Declination.of(-26, 25, 55.2));
  public static final StarCoordinate BETELGEUSE =
      StarCoordinate.of(RightAscension.of(5, 55, 10.3), Declination.of(7, 24, 25.4));
  public static final StarCoordinate CENTAURI_ALPHA =
      StarCoordinate.of(RightAscension.of(14, 39, 36.5), Declination.of(-60, 50, 2.3));
  public static final StarCoordinate CRUX_ALPHA =
      StarCoordinate.of(RightAscension.of(12, 26, 35.9), Declination.of(-63, 5, 56.7));
  public static final StarCoordinate M13 =
      StarCoordinate.of(RightAscension.of(16, 41, 42), Declination.of(36, 28, 0));
  public static final StarCoordinate POLARIS =
      StarCoordinate.of(RightAscension.of(2, 31, 48.7), Declination.of(89, 15, 51));
  public static final StarCoordinate SIRIUS =
      StarCoordinate.of(RightAscension.of(6, 45, 8.9), Declination.of(-16, 42, 58));
  public static final StarCoordinate URSA_MAJOR_EPSILON =
      StarCoordinate.of(RightAscension.of(12, 54, 1.6), Declination.of(55, 57, 34.4));

  final RightAscension rightAscension;
  final Declination declination;

  public static StarCoordinate of(RightAscension ascension, Declination declination) {
    return new StarCoordinate(ascension, declination);
  }

  private StarCoordinate(RightAscension rightAscension, Declination declination) {
    this.rightAscension = rightAscension;
    this.declination = declination;
  }

  public RightAscension getRightAscension() {
    return rightAscension;
  }

  public Declination getDeclination() {
    return declination;
  }

  @Override
  public String toString() {
    return rightAscension.toString() + ", " + declination.toString();
  }

  public static class RightAscension {
    private final double value; // range 0..24

    public static RightAscension of(int hour, int minute, double second) {
      return new RightAscension(hour + (minute / 60.0) + (second / 3600.0));
    }

    private RightAscension(double value) {
      this.value = value;
    }

    @Override
    public String toString() {
      double hour = Math.floor(value);
      double minute = Math.floor((value - hour) * 60);
      double second = Math.round(((value - hour) * 60 - minute) * 60);
      String str = MessageFormat.format("{0}h {1}m {2}s", hour, minute, second);
      return str;
    }

    public double toHms() {
      return this.value;
    }

    public double toDegrees() {
      return (this.value * 15.0);
    }
  }

  public static class Declination {
    private static final char DEGREE = '\u00B0';
    private static final char MINUTE = '\u2032';
    private static final char SECOND = '\u2033';

    private final double value; // range -90..+90

    public static Declination of(int degree, int minute, double second) {
      return new Declination(degree + (minute / 60.0) + (second / 3600.0));
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

}

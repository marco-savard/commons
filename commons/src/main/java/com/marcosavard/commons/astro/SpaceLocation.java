package com.marcosavard.commons.astro;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import com.marcosavard.commons.geog.GeoCoordinate;
import com.marcosavard.commons.math.Angle;
import com.marcosavard.commons.math.Angle.Unit;
import com.marcosavard.commons.math.InRange;

// in equatorial coordinates
public class SpaceLocation {
  private double rightAscension; // [0-24 hr]
  private double declination; // [0-360 degrees]

  public static SpaceLocation of(RightAscension ascension, Declination declination) {
    return of(ascension.toHour(), declination.toDegrees());
  }

  public static SpaceLocation of(double rightAscensionInHours, double declinationInDegrees) {
    SpaceLocation position = new SpaceLocation(rightAscensionInHours, declinationInDegrees);
    return position;
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

  public static SpaceLocation findZenithPositionAbove(GeoCoordinate coordinate,
      ZonedDateTime moment) {
    double declination = coordinate.getLatitude().getValue();
    double n = moment.getDayOfYear();
    int h = moment.getHour();
    int m = moment.getMinute();
    int s = moment.getSecond();
    double decimalHour = h + (m / 60.0) + (s / 3600.0);
    double a = 0.98563 * n;
    double b = 15.0405 * decimalHour;

    double longitude = coordinate.getLongitude().getValue();
    double rightAscensionDegrees = longitude + a + b + 98.971;
    Angle ra = Angle.of(rightAscensionDegrees, Unit.DEGREES);
    double rightAscensionHours = (ra.degrees() / 360) * 24;
    SpaceLocation position = new SpaceLocation(rightAscensionHours, declination);
    return position;
  }

  private SpaceLocation(double rightAscension, double declination) {
    this.rightAscension = rightAscension;
    this.declination = declination;
  }

  public GeoCoordinate getZenithAt(ZonedDateTime moment) {
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
    GeoCoordinate coordinate = GeoCoordinate.of(latitude, longitude);
    return coordinate;
  }

  @Override
  public String toString() {
    String str = MessageFormat.format("asc={0} hr decl={1} degrees", rightAscension, declination);
    return str;
  }

  public ZonedDateTime getMomentZenithAt(LocalDate date, double longitude) {
    double ra = rightAscension * 15.0;
    double n = date.getDayOfYear();
    double a = 0.985635 * n;
    double h = (ra - longitude - a - 98.9715) / 15.0405;
    h = InRange.range(0, 24, h); // ramener h dans 0..24
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

    // TODO validate all positive or all negative
    public static Declination of(int degree, int minute, double second) {
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



}

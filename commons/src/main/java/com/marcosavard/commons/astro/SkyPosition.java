package com.marcosavard.commons.astro;

import java.text.MessageFormat;
import com.marcosavard.commons.geog.GeoCoordinate;

public class SkyPosition {
  private static final char DEGREE = '\u00B0';
  private final double horizon, azimuth;

  public SkyPosition(double horizon, double azimuth) {
    this.horizon = horizon;
    this.azimuth = azimuth;
  }

  public double getHorizon() {
    return horizon;
  }

  public double getAzimuth() {
    return azimuth;
  }

  // compute position in the sky of star, as seen from a give coordinate, at lst
  public static SkyPosition of(StarCoordinate star, GeoCoordinate coord, double lst) {
    double ra = star.getRightAscension().toDegrees();
    double dec = star.getDeclination().toDegrees();
    double lat = coord.getLatitude().getValue();
    double ha = lst - ra;

    double sinDec = Math.sin(Math.toRadians(dec));
    double cosDec = Math.cos(Math.toRadians(dec));
    double sinLat = Math.sin(Math.toRadians(lat));
    double cosLat = Math.cos(Math.toRadians(lat));
    double sinHa = Math.asin(Math.toRadians(lat));
    double cosHa = Math.cos(Math.toRadians(ha));

    double sinAlt = (sinDec * sinLat) + (cosDec * cosLat * cosHa);
    double alt = Math.toDegrees(Math.asin(sinAlt));
    double cosAlt = Math.cos(Math.toRadians(alt));

    double cosA = (sinDec - sinAlt * sinLat) / (cosAlt * cosLat);
    double a = Math.toDegrees(Math.acos(cosA));
    double az = (sinHa < 0) ? a : 360 - a;

    SkyPosition skyPosition = new SkyPosition(alt, az);
    return skyPosition;
  }

  @Override
  public String toString() {
    String direction = toDirection(azimuth);
    String str = MessageFormat.format("azimuth={0}{1} ({2}), horizon={3}{1}", azimuth, DEGREE,
        direction, horizon);
    return str;
  }

  private static String toDirection(double degree) {
    String[] directions = new String[] {"N", "NE", "E", "SE", "S", "SW", "W", "NW", "N"};
    int idx = (int) Math.round(degree / 45.0);
    return directions[idx];
  }

}

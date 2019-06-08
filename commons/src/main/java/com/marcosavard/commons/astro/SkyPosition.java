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
  public static SkyPosition compute(StarCoordinate star, GeoCoordinate coord, double lst) {
    double lha = lst - star.getRightAscension().toHms();
    double dec = Math.toRadians(star.getDeclination().toDegrees());
    double lat = Math.toRadians(coord.getLatitude().getValue());
    double har = Math.toRadians(lha);

    double sinh = Math.sin(dec) * Math.sin(lat) + Math.cos(dec) * Math.cos(lat) * Math.cos(har);
    double h = Math.asin(sinh);
    double x = (Math.sin(dec) - Math.sin(h) * Math.sin(lat));
    double y = (Math.cos(h) * Math.cos(lat));
    double cosAz = x / y;
    double az = Math.acos(cosAz);
    az = (sinh < 0) ? az : Math.PI * 2 - az;

    SkyPosition skyPosition = new SkyPosition(Math.toDegrees(h), Math.toDegrees(az));
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

package com.marcosavard.commons.astro;

import java.text.MessageFormat;

import static com.marcosavard.commons.astro.AstroMath.sind;
import static com.marcosavard.commons.astro.AstroMath.cosd;
import static com.marcosavard.commons.astro.AstroMath.asind;
import static com.marcosavard.commons.astro.AstroMath.acosd;

public class SkyPosition {
  public static final SkyPosition ZENITH = SkyPosition.of(90, 0);
  public static final SkyPosition NADIR = SkyPosition.of(-90, 0);

  private static final char DEGREE = '\u00B0';
  private final double horizon, azimuth;

  public static SkyPosition of(double horizon, double azimuth) {
    return new SkyPosition(horizon, azimuth);
  }

  private SkyPosition(double horizon, double azimuth) {
    this.horizon = horizon;
    this.azimuth = AstroMath.range(azimuth, 360);
  }

  public double getHorizon() {
    return horizon;
  }

  public double getAzimuth() {
    return azimuth;
  }
  
  public double distanceFrom(SkyPosition that) {
	  double cosDist = sind(horizon) * sind(that.horizon) + cosd(horizon) * cosd(that.horizon) * cosd(azimuth - that.azimuth); 
	  double dist = acosd(cosDist); 
	  return dist;
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

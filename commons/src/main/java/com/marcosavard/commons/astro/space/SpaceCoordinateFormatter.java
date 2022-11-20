package com.marcosavard.commons.astro.space;

import com.marcosavard.commons.math.arithmetic.Base;

import java.text.MessageFormat;

public class SpaceCoordinateFormatter {
  private static final char DEGREE_SIGN = '\u00B0';
  private static final char MINUTE_SIGN = '\u2032';
  private static final char SECOND_SIGN = '\u2033';

  public String format(SpaceCoordinate spaceCoordinate) {
    Base hms = Base.of(24, 60, 60);
    double rightAscensionDeg = spaceCoordinate.getRightAscensionDegree();
    double declination = spaceCoordinate.getDeclinationDegree();

    String ra = Double.toString(Math.round(rightAscensionDeg)) + DEGREE_SIGN;
    long[] rae = hms.encode((long) ((rightAscensionDeg / 15) * 3600));
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
}

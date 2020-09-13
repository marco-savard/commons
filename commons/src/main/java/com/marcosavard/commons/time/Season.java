package com.marcosavard.commons.time;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import com.marcosavard.commons.astro.JulianDay;
import com.marcosavard.commons.math.Angle;
import com.marcosavard.commons.math.Angle.Unit;

public class Season {
  private static final double JULIAN_ERA = 1_721_141; // JulianDate.of(0, 3, 24);
  private static final double DAYS_IN_YEAR = 365.242199;
  private static final double EXCENTRICITY = 0.03345; // Earth's excentricity
  private static final ZoneId UTC = ZoneOffset.UTC;
  private SeasonOf seasonOf;
  private ZonedDateTime seasonStart;

  public enum SeasonOf {
    SPRING, SUMMER, AUTOMN, WINTER
  }

  public static Season of(SeasonOf seasonOf, int year) {
    int k = seasonOf.ordinal();
    double jj = (year + k / 4.0) * DAYS_IN_YEAR + JULIAN_ERA;
    double ji = jj;
    double dsi[] = new double[2];


    for (int i = 0; i < 2; i++) {
      double dj = (ji - 2_415_020) / 36525.0;
      double lm = 279.69668 + (36000.76892 * dj);
      double m = 358.47583 + (35999.04975 * dj);
      Angle lma = Angle.of(lm, Unit.DEGREES);
      Angle ma = Angle.of(m, Unit.DEGREES);
      double c = 0.01396 * (year - 1950);
      double sin = Math.sin(ma.rads());
      double ls = lma.degrees() + EXCENTRICITY * sin - c;
      double cd = 58 * Math.sin(Math.toRadians(90 * k - ls));
      dsi[i] = jj + cd;
      ji = dsi[i];
    }

    double ds = (dsi[0] + dsi[1]) / 2;
    long startDayPart = (long) Math.floor(ds);
    double startTimePart = ds - startDayPart;

    JulianDay start = JulianDay.of(ds);

    // JulianDay start = JulianDay.of(startTimePart);
    LocalDate localDate = start.toLocalDate();
    LocalTime localTime = TimeConversion.toLocalTime(startTimePart);
    ZonedDateTime startTime = ZonedDateTime.of(localDate, localTime, UTC);
    Season season = new Season(seasonOf, startTime);
    return season;
  };

  private Season(SeasonOf seasonOf, ZonedDateTime seasonStart) {
    this.seasonOf = seasonOf;
    this.seasonStart = seasonStart;
  }

  public ZonedDateTime getSeasonStart() {
    return seasonStart;
  }

  @Override
  public String toString() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MMM dd HH:mm:ss");
    String formatted = seasonStart.format(formatter);
    String msg = MessageFormat.format("{0} {1} (GMT)", seasonOf, formatted);
    return msg;
  }



}

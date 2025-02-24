package com.marcosavard.commons.astro.moon;

import com.marcosavard.commons.astro.sun.SunPosition;
import com.marcosavard.commons.astro.time.JulianDay;
import com.marcosavard.commons.lang.StringUtil;
import com.marcosavard.commons.math.trigonometry.Angle;
import com.marcosavard.commons.math.trigonometry.Angle.Unit;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import static com.marcosavard.commons.math.Maths.range;

public class MoonPosition {

  public enum Phase {
    NEW(0x1F311),
    WAXING_CRESCENT(0x1F312),
    FIRST_QUARTER(0x1F313),
    WAXING_GIBBOUS(0x1F314),
    FULL(0x1F315),
    WANING_GIBBOUS(0x1F316),
    THIRD_QUARTER(0x1F317),
    WANING_CRESCENT(0x1F318);

    private int codePoint;

    Phase(int codePoint) {
      this.codePoint = codePoint;
    }

    public int getCodePoint() {
      return codePoint;
    }

    public static Phase findPhaseAt(int moonAge) {
      int idx = (int) Math.round((moonAge / 360.0) * 8.0);
      idx = (idx + 8) & 7; // & 7 equivalent to % 8
      return Phase.values()[idx];
    }

    public String getDisplayName(Locale display) {
      if (display.getLanguage().equals("fr")) {
        return getDisplayNameFr();
      } else {
        String displayName = this.name().toLowerCase().replace('_', ' ');
        return StringUtil.capitalize(displayName);
      }
    }

    private String getDisplayNameFr() {
      if (this == Phase.NEW) {
        return "Nouvelle lune";
      } else if (this == Phase.WAXING_CRESCENT) {
        return "Premier croissant";
      } else if (this == Phase.FIRST_QUARTER) {
        return "Premier quartier";
      } else if (this == Phase.WAXING_GIBBOUS) {
        return "Lune gibbeuse croissante";
      } else if (this == Phase.FULL) {
        return "Pleine lune";
      } else if (this == Phase.WANING_GIBBOUS) {
        return "Lune gibbeuse décroissante";
      } else if (this == Phase.THIRD_QUARTER) {
        return "Dernier quartier";
      } else if (this == Phase.WANING_CRESCENT) {
        return "Dernier croissant";
      } else {
        return "?";
      }
    }


  };

  private static final double EPOCH = JulianDay.of(LocalDate.of(1980, 1, 1)).getValue() - 1;

  // synodic month (new Moon to new Moon)
  private static final double SYN_MONTH = 29.53058868D;

  // Eccentricity of Earth's orbit.
  public static final double ECCENT_EARTH_ORBIT = 0.016718;

  // Accurancy of the Kepler equation.
  public static final double KEPLER_EPSILON = 1E-6;

  // Moon's mean longitude at the epoch.
  public static final double MOON_MEAN_LONGITUDE_EPOCH = 64.975464;

  // Mean longitude of the perigee at the epoch.
  public static final double MOON_MEAN_LONGITUDE_PERIGREE = 349.383063;

  // semi-major axis of Moon's orbit in km
  private static final double MOON_SEMI_MAJOR = 384401.0D;

  // eccentricity of the Moon's orbit
  private static final double MOON_ECCENT = 0.054900D;

  // moon's angular size at distance a from Earth
  private static final double MOON_ANG_SIZE = 0.5181D;

  private static final long SECONDS_PER_DAY = 60 * 60 * 24;
  private static final LocalDate REFERENCE_DATE = LocalDate.of(1975, 1, 1);

  private double moonAge;
  private double moonDist;

  public static MoonPosition atMinightUTC(LocalDate date) {
    ZonedDateTime moment = ZonedDateTime.of(date, LocalTime.MIDNIGHT, ZoneOffset.UTC);
    MoonPosition position = at(moment);
    return position;
  }

  public static MoonPosition at(ZonedDateTime moment) {
    double julianDate = JulianDay.of(moment).getValue();
    MoonPosition position = at(julianDate);
    return position;
  }

  // https://github.com/AndrewComeloff/MoonPhase/blob/master/src/gs/moonphase/Phase.java
  private static MoonPosition at(double julianDate) {
    double day = julianDate - EPOCH; // date within epoch

    // Calculation of the Sun's position.
    SunPosition sunPosition = SunPosition.at(julianDate);
    double sunLongitude = sunPosition.getSunLongitude();
    double sunAnomaly = sunPosition.getSunAnomaly();

    // Calculation of the Moon's position.

    // Moon's mean longitude.
    double ml = degRange(13.1763966 * day + MOON_MEAN_LONGITUDE_EPOCH);

    // Moon's mean anomaly.
    double mm = degRange(ml - 0.1114041 * day - MOON_MEAN_LONGITUDE_PERIGREE);

    // Evection.
    double ev = 1.2739 * Math.sin(torad(2 * (ml - sunLongitude) - mm));

    // Annual equation.
    double ae = 0.1858 * Math.sin(torad(sunAnomaly));

    // Correction term.
    double a3 = 0.37 * Math.sin(torad(sunAnomaly));

    // Corrected anomaly.
    double mmP = mm + ev - ae - a3;

    // Correction for the equation of the centre.
    double mEc = 6.2886 * Math.sin(torad(mmP));

    // Another correction term.
    double a4 = 0.214 * Math.sin(torad(2 * mmP));

    // Corrected longitude.
    double lp = ml + ev + mEc - ae + a4;

    // Variation.
    double v = 0.6583 * Math.sin(torad(2 * (lp - sunLongitude)));

    // True longitude.
    double lpp = lp + v;

    // Age of the Moon in degrees.
    double moonAge = degRange(lpp - sunLongitude);

    // Calculate distance of moon from the centre of the Earth.
    double me = MOON_ECCENT;
    double moonDist = (MOON_SEMI_MAJOR * (1 - me * me)) / (1 + me * Math.cos(torad(mmP + mEc)));

    MoonPosition position = new MoonPosition(moonAge, moonDist);
    return position;
  }

  // from 0 (new Moon) to 360 (next new Moon)
  public double getMoonAge() {
    return moonAge;
  }

  // from 0 days to 29.53 days
  public double getMoonAgeDays() {
    double moonAgeDays = SYN_MONTH * (degRange(moonAge) / 360.0);
    return moonAgeDays;
  }

  // from 356,500 km at perigee to 406,700 km at apogee
  public double getMoonDistance() {
    return moonDist;
  }

  // illuminated fraction of Moon's disk, from 0.0 (new Moon) to 1.0 (full Moon)
  public double getMoonIllumination() {
    double moonPhase = (1 - Math.cos(torad(moonAge))) / 2;
    return moonPhase;
  }

  // Calculate Moon's angular diameter.
  public double getMoonAngularDiameter() {
    double moonDFrac = moonDist / MOON_SEMI_MAJOR;
    double moonAng = MOON_ANG_SIZE / moonDFrac;
    return moonAng;
  }

  public Phase getPhase() {
    int idx = (int) Math.round((moonAge / 360.0) * 8.0);
    idx = (idx + 8) & 7; // & 7 equivalent to % 8
    Phase phaseName = Phase.values()[idx];
    return phaseName;
  }

  public static ZonedDateTime findNextNewMoon(LocalDate date) {
    return findNextMoonAge(date, 0);
  }

  public static ZonedDateTime findNextFirstQuarter(LocalDate date) {
    return findNextMoonAge(date, 90);
  }

  public static ZonedDateTime findNextFullMoon(LocalDate date) {
    return findNextMoonAge(date, 180);
  }

  public static ZonedDateTime findNextLastQuarter(LocalDate date) {
    return findNextMoonAge(date, 270);
  }

  public static ZonedDateTime findNextMoonAge(LocalDate date, double moonAgeToFind) {
    ZonedDateTime moment0 = ZonedDateTime.of(date, LocalTime.MIDNIGHT, ZoneOffset.UTC);
    Instant instant0 = moment0.toInstant();
    Angle angleToFind = Angle.of(moonAgeToFind, Unit.DEG);

    MoonPosition position0 = MoonPosition.at(moment0);
    double moonAge = position0.getMoonAge();
    double ageDelta = degRange(moonAgeToFind - moonAge);
    long approxTime = (long) ((ageDelta / 360) * SYN_MONTH * SECONDS_PER_DAY);
    Instant instant1 = instant0.plus(approxTime, ChronoUnit.SECONDS);
    ZonedDateTime moment1 = instant1.atZone(ZoneOffset.UTC);
    MoonPosition position1 = MoonPosition.at(moment1);
    Angle angle1 = Angle.of(position1.moonAge, Unit.DEG);

    double angleDiff = 0;
    int i = 0;

    // Refine time of moment1
    do {
      // compare angles
      angleDiff = angle1.minus(angleToFind);
      boolean before = angleDiff < 0;
      long deltaTime = (long) (SECONDS_PER_DAY / Math.pow(2, i++));
      instant1 =
          before
              ? //
              instant1.plus(deltaTime, ChronoUnit.SECONDS)
              : //
              instant1.minus(deltaTime, ChronoUnit.SECONDS);
      moment1 = instant1.atZone(ZoneOffset.UTC);
      position1 = MoonPosition.at(moment1);
      angle1 = Angle.of(position1.moonAge, Unit.DEG);
    } while (Math.abs(angleDiff) > 0.01);

    return moment1;
  }

  public static MoonPosition atSimplified(ZonedDateTime moment) {
    double fractionalDayOfYear = getFractionalDayOfYear(moment);
    int nbDaysInYear = moment.toLocalDate().isLeapYear() ? 366 : 355;
    long days = REFERENCE_DATE.until(moment, ChronoUnit.DAYS) + 1;
    double ms = 0.98563 * fractionalDayOfYear - 3.4689;

    double m1 = range(13.17634 * days, 0, 360);
    double m2 = range(0.11137 * days, 0, 360);
    double mm = range(m1 - m2 - 21.0845, 0, 360);

    double t = moment.getYear() - 1900 + (fractionalDayOfYear / (double) nbDaysInYear);
    double t1 = range(4452.67114 * t, 0, 360);

    double t2 = 0.0000144 * t * t;
    double e1 = t1 - t2;
    e1 = range(e1, 0, 360);
    double e = 350.7375 + e1;
    e = range(e, 0, 360);

    double sinMm = Math.sin(Math.toRadians(mm));
    double sin2Mm = Math.sin(Math.toRadians(2 * mm));
    double sinMs = Math.sin(Math.toRadians(ms));
    double sin2E = Math.sin(Math.toRadians(2 * e));
    double sinE = Math.sin(Math.toRadians(e));
    double sin2EMm = Math.sin(Math.toRadians(2 * e - mm));

    double angle =
        180
            - e
            - (6.289 * sinMm)
            - (0.214 * sin2Mm)
            + (2.1 * sinMs)
            - (0.658 * sin2E)
            - (0.112 * sinE)
            - (1.1274 * sin2EMm);
    angle = range(angle, 0, 360);
    MoonPosition position = new MoonPosition(angle, 0);
    return position;
  }

  private MoonPosition(double moonAge, double moonDist) {
    this.moonAge = moonAge;
    this.moonDist = moonDist;
  }

  @Override
  public String toString() {
    String msg = MessageFormat.format("age = {0} deg; dist = {1} km", moonAge, moonDist);
    return msg;
  }

  private static double torad(double degs) {
    return Math.toRadians(degs);
  }

  private static double degRange(double d) {
    return range(d, 0, 360);
  }

  private static double getFractionalDayOfYear(ZonedDateTime moment) {
    int dayOfYear = moment.getDayOfYear();
    Instant midnight = moment.toLocalDate().atStartOfDay(moment.getZone()).toInstant();
    Duration duration = Duration.between(midnight, moment);
    double seconds = duration.getSeconds();
    double fractionalDayOfYear = dayOfYear + (seconds / SECONDS_PER_DAY);
    return fractionalDayOfYear;
  }
}

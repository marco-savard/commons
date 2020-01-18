package com.marcosavard.commons.astro;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * File: MoonCalculation.java Author: Angus McIntyre angus@pobox.com Date: 31.05.96 Updated:
 * 01.06.96
 * 
 * Java class to calculate the phase of the moon, given a date. The 'moonPhase' method is a Java
 * port of some public domain C source which was apparently originally part of the Decus C
 * distribution (whatever that was). Details of the algorithm are given below.
 * 
 * To use this in a program, create an object of class 'MoonCalculation'.
 * 
 * I'm not convinced that the algorithm is entirely accurate, but I don't know enough to confirm
 * whether it is or not.
 * 
 * HISTORY -------
 * 
 * 31.05.96 SLAM Converted from C 01.06.96 SLAM Added 'phaseName' to return the name of a phase.
 * Fixed leap year test in 'daysInMonth'.
 * 
 * LEGAL -----
 * 
 * This software is free. It can be used and modified in any way you choose. The author assumes no
 * liability for any loss or damage you may incur through use of or inability to use this software.
 * This disclaimer must appear on any modified or unmodified version of the software in which the
 * name of the author also appears.
 * 
 * based on:
 * http://raingod.com/raingod/resources/Programming/Java/Software/Moon/javafiles/MoonCalculation.java
 * 
 **/
public class MoonEvent {
  private static final int JULIAN_DATES[] =
      {-1, 30, 58, 89, 119, 150, 180, 211, 241, 272, 303, 333};
  private static int SIX_PERIODS = (int) (6 * MoonPhase.MOON_PERIOD);

  public static MoonPhase getPhaseOnDate(LocalDate date) {
    int year = date.getYear();
    int monthIdx = date.getMonthValue() - 1;
    int day = date.getDayOfMonth();

    int julianDay = day + JULIAN_DATES[monthIdx];

    // leap year
    if ((monthIdx > 2) && isLeapYear(year)) {
      julianDay++;
    }

    MoonPhase phase = getPhaseOnDate(year, julianDay);
    return phase;
  }

  public static MoonPhase getPhaseOnDate(Date date) {
    MoonPhase phase = getPhaseOnDate(toLocalDate(date));
    return phase;
  }

  public static LocalDate findNextFullMoon(LocalDate date) {
    return findNextMoonPhase(date, MoonPhase.PhaseName.FULL);
  }

  public static LocalDate findNextNewMoon(LocalDate date) {
    return findNextMoonPhase(date, MoonPhase.PhaseName.NEW);
  }

  public static Date findNextMoonPhase(Date date, MoonPhase.PhaseName moonPhase) {
    return toDate(findNextMoonPhase(toLocalDate(date), moonPhase));
  }

  public static LocalDate findNextMoonPhase(LocalDate localDate, MoonPhase.PhaseName moonPhase) {
    LocalDate foundDate = null;

    for (int i = 0; i < MoonPhase.MOON_PERIOD; i++) {
      LocalDate dateInFuture = localDate.plusDays(i);
      MoonPhase.PhaseName phase = getPhaseOnDate(dateInFuture).getPhaseName();
      if (moonPhase.equals(phase)) {
        foundDate = dateInFuture;
        break;
      }
    }

    return foundDate;
  }

  public static LocalDate toLocalDate(Date date) {
    return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
  }

  public static Date toDate(LocalDate localDate) {
    return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
  }
  //
  // private methods
  //

  private static MoonPhase getPhaseOnDate(int year, int julianDay) {
    // Century number (1979 = 20)
    int cent = (year / 100) + 1;

    // Moon's golden number
    int golden = (year % 19) + 1;

    // Age of the moon on Jan. 1
    int epact = ((11 * golden) + 20 // Golden number
        + (((8 * cent) + 5) / 25) - 5 // 400 year cycle
        - (((3 * cent) / 4) - 12)) % 30; // Leap year correction

    // Age range is 1 .. 30
    if (epact <= 0) {
      epact += 30;
    }

    if ((epact == 25 && golden > 11) || epact == 24) {
      epact++;
    }

    // Calculate the phase, using the magic numbers defined above.
    // Note that (phase and 7) is equivalent to (phase mod 8) and
    // is needed on two days per year (when the algorithm yields 8).

    double d = (((julianDay + epact) * 6) + 11) % SIX_PERIODS; // range [0..176]
    double percent = (d / 177d) * 100.0; // phase in range [0..100]
    MoonPhase moonPhase = MoonPhase.of(percent);
    return moonPhase;
  }

  // private methods
  private static boolean isLeapYear(int year) {
    boolean leap = ((year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0)));
    return leap;
  }



}

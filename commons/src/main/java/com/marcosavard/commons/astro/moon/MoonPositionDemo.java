package com.marcosavard.commons.astro.moon;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.time.TextCalendar;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.*;
import java.util.Locale;

// validate w/ https://mooncalendar.astro-seek.com/moon-phases-calendar-february-1984
public class MoonPositionDemo {

  public static void main(String[] args) {
    LocalDate date = LocalDate.now();
    LocalDateTime now = LocalDateTime.now();
    Locale display = Locale.FRENCH;

    //demoPositionAt(date, display);
    // demoFebruary1984();
     demoFindPhaseTime(date);
    // demoMonth(2020, 10);
   // demoCalendarMonth(2025, 1);
  }

  private static void demoPositionAt(LocalDate date, Locale display) {
    //LocalDate date = LocalDate.of(1984, 2, 2);
    LocalTime time = LocalTime.MIDNIGHT;
    ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneOffset.UTC);
    MoonPosition position = MoonPosition.at(moment);
    double moonAge = position.getMoonAge();
    long distance = (long)position.getMoonDistance();
    double diameter = position.getMoonAngularDiameter();
    double percent = 100.0 * distance / MoonPosition.MOON_APOGEE;

    DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(display);
    String distanceStr = df.format(distance);
    String percentStr = Double.toString(Math.round(percent*10) / 10.0);
    String diameterStr = Double.toString(Math.round(diameter*1000) / 1000.0);

    Console.println("  Moon Age at {0} : {1} degrees", moment, moonAge);
    Console.println("  Moon Distance at : {0} km", distanceStr);
    Console.println("                   : {0} % of apogee", percentStr);
    Console.println("  Moon Diameter    : {0} degrees", diameterStr);
  }

  private static void demoFebruary1984() {
    System.out.println("Moon Phases February1984");

    // jan
    for (int d = 30; d <= 31; d++) {
      LocalDate date = LocalDate.of(1984, 1, d);

      for (int h = 0; h < 6; h++) {
        LocalTime time = LocalTime.of(h * 4, 0);
        ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneOffset.UTC);
        double moonAngle = MoonPosition.at(moment).getMoonAge();
        String msg = MessageFormat.format("  {0} : {1}", moment, moonAngle);
        System.out.println(msg);
      }
    }

    // feb
    for (int d = 1; d <= 2; d++) {
      LocalDate date = LocalDate.of(1984, 2, d);

      for (int h = 0; h < 6; h++) {
        LocalTime time = LocalTime.of(h * 4, 0);
        ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneOffset.UTC);
        double moonAngle = MoonPosition.at(moment).getMoonAge();
        String msg = MessageFormat.format("  {0} : {1}", moment, moonAngle);
        System.out.println(msg);
      }
    }

    System.out.println();
  }

  private static void demoFindPhaseTime(LocalDate date) {
//   / LocalDate date = LocalDate.of(1984, 2, 1);

    System.out.println(" next new moon : " + MoonPosition.findNextNewMoon(date));
    System.out.println(" next first quarter : " + MoonPosition.findNextFirstQuarter(date));
    System.out.println(" next full moon : " + MoonPosition.findNextFullMoon(date));
    System.out.println(" next last quarter : " + MoonPosition.findNextLastQuarter(date));
  }

  private static void demoYear(int year) {
    System.out.println("Moon Phases " + year);
    LocalTime time = LocalTime.MIDNIGHT;

    for (int d = 1; d <= 366; d++) {
      LocalDate date = LocalDate.ofYearDay(year, d);
      ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneOffset.UTC);
      MoonPosition position = MoonPosition.at(moment);
      // double moonAngle = position.getPhaseAngle();
      // double illumination = position.getIllumination();

      // if ((moonAngle > 173) && (moonAngle < 187)) {
      // String msg =
      // MessageFormat.format(" {0} : new moon {1} {2}", moment, moonAngle, illumination);
      // System.out.println(msg);
      // }
    }
  }

  private static void demoMonth(int year, int month) {
    LocalDate firstOfMonth = LocalDate.of(year, month, 1);
    String[] phases = new String[] {"New Moon", "First Quarter", "Full Moon", "Last Quarter"};

    for (int i = 0; i < 4; i++) {
      ZonedDateTime moment = MoonPosition.findNextMoonAge(firstOfMonth, i * 90);
      System.out.println(phases[i] + " :  " + moment);
    }

    System.out.println();
  }

  private static void demoCalendarMonth(int year, int month) {
    LocalDate firstOfMonth = LocalDate.of(year, month, 1);
    TextCalendar calendar = TextCalendar.of(year, month);
    String[] phaseAbbreviation = new String[] {"NM", "FQ", "FM", "LQ"};

    for (int i = 0; i < 4; i++) {
      ZonedDateTime moment = MoonPosition.findNextMoonAge(firstOfMonth, i * 90);
      int dayOfMonth = moment.getDayOfMonth();
      String fmt = String.format("%2d", dayOfMonth);
      calendar.replace(fmt, phaseAbbreviation[i]);
    }

    calendar.print(System.out);
  }
}

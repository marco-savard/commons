package com.marcosavard.commons.astro.moon;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import com.marcosavard.commons.time.TextCalendar;

// validate w/ https://mooncalendar.astro-seek.com/moon-phases-calendar-february-1984
public class MoonPositionDemo {

  public static void main(String[] args) {
    // demoFebruary2nd1984();
    // demoFebruary1984();
    // demoFindPhaseTime();
    // demoMonth(2020, 10);
    demoCalendarMonth(2020, 10);
  }

  private static void demoFebruary2nd1984() {
    LocalDate date = LocalDate.of(1984, 2, 2);
    LocalTime time = LocalTime.MIDNIGHT;
    ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneOffset.UTC);
    double moonAge = MoonPosition.at(moment).getMoonAge();

    String msg = MessageFormat.format("  {0} : {1}", moment, moonAge);
    System.out.println(msg);
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

  private static void demoFindPhaseTime() {
    LocalDate date = LocalDate.of(1984, 2, 1);

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

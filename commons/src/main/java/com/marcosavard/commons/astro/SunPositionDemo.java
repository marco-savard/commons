package com.marcosavard.commons.astro;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import com.marcosavard.commons.geog.GeoLocation;

public class SunPositionDemo {

  public static void main(String[] args) {
    // printSunPositionIn2020();
    // findEquinoxSolstice();
    // findApogeePerigee();
    findSunriseSunset();
  }

  public static void printSunPositionIn2020() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd");

    for (int d = 1; d < 365; d += 7) {
      LocalDate date = LocalDate.ofYearDay(2020, d);
      LocalTime time = LocalTime.MIDNIGHT;
      ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneOffset.UTC);
      SunPosition sunPosition = SunPosition.at(moment);
      String dateFmt = date.format(formatter);
      System.out.println(dateFmt + " " + sunPosition);
    }
  }

  private static void findEquinoxSolstice() {
    LocalDate date = LocalDate.of(2020, 1, 1);
    ZonedDateTime springEquinox = SunPosition.findNextSpringEquinox(date);
    ZonedDateTime summerSolstice = SunPosition.findNextSummerSolstice(date);
    ZonedDateTime fallEquinox = SunPosition.findNextFallEquinox(date);
    ZonedDateTime winterSolstice = SunPosition.findNextWinterSolstice(date);

    System.out.println("springEquinox at =  " + springEquinox);
    System.out.println("summerSolstice at =  " + summerSolstice);
    System.out.println("fallEquinox at =  " + fallEquinox);
    System.out.println("winterSolstice at =  " + winterSolstice);
  }

  private static void findApogeePerigee() {
    LocalDate date = LocalDate.of(2020, 1, 1);
    ZonedDateTime apogee = SunPosition.findNextApogee(date);
    System.out.println("apogee at =  " + apogee);
  }

  private static void findSunriseSunset() {
    LocalDate date = LocalDate.of(1983, 3, 1);
    LocalTime time = LocalTime.MIDNIGHT;
    ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneOffset.UTC);
    SunPosition position = SunPosition.at(moment);

    GeoLocation ajaccio = GeoLocation.of(41.9, 8.7);
    ZonedDateTime localNoon = position.findLocalNoonAt(ajaccio);
    String msg = MessageFormat.format(" Local noon at {0}", localNoon);
    System.out.println(msg);

    ZonedDateTime[] sunriseSunset = position.findSunriseSunsetAt(ajaccio);
    msg = MessageFormat.format(" Sun rises at {0}", sunriseSunset[0]);
    System.out.println(msg);
  }


}

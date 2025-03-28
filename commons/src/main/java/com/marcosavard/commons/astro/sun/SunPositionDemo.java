package com.marcosavard.commons.astro.sun;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.geog.GeoLocation;
import com.marcosavard.commons.time.StandardZoneId;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class SunPositionDemo {

  public static void main(String[] args) {
    LocalDate date = LocalDate.of(2025, 1, 13);
    GeoLocation quebec = GeoLocation.of(46.8131, -71.2075);
    GeoLocation montreal = GeoLocation.of(45.5019, -73.56745);

    Console.println("Sunrise, sunset for Quebec");
    printSunriseSunset(date, quebec);

    Console.println("Sunrise, sunset for Montreal");
    printSunriseSunset(date, montreal);

    printApogeePerigee(date);
    printSunPositionIn2020();

    //
    // findEquinoxSolstice();
  }

  private static void printSunriseSunset(LocalDate date, GeoLocation location) {
    LocalTime time = LocalTime.MIDNIGHT;
    ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneOffset.UTC);
    SunPosition position = SunPosition.at(moment);
    ZonedDateTime[] sunriseSunset = position.findSunriseSunsetAt(location);

    ZoneId est = StandardZoneId.AMERICA_NEW_YORK.getZoneId();
    LocalDateTime localSunrise = LocalDateTime.ofInstant(sunriseSunset[0].toInstant(), est);
    LocalDateTime localNoon = LocalDateTime.ofInstant(sunriseSunset[1].toInstant(), est);
    LocalDateTime localSunset = LocalDateTime.ofInstant(sunriseSunset[2].toInstant(), est);

    Console.println("  sun rises at {0}", localSunrise.toLocalTime());
    Console.println("  local noon at {0}", localNoon.toLocalTime());
    Console.println("  sun sets at {0}", localSunset.toLocalTime());
    Console.println();
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

  private static void printApogeePerigee(LocalDate date) {
    ZonedDateTime apogee = SunPosition.findNextApogee(date);
    ZonedDateTime perigee = SunPosition.findNextPerigee(date);
    Console.println("  apogee at {0}",  apogee);
    Console.println("  perigee at {0}",  perigee);
  }


}

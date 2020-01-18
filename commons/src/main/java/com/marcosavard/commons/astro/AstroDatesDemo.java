package com.marcosavard.commons.astro;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

public class AstroDatesDemo {

  public static void main(String[] args) {
    // convert to and from localDate
    LocalDate localDate = LocalDate.of(2020, Month.FEBRUARY, 29);
    Date date = AstroDates.toDate(localDate);
    localDate = AstroDates.toLocalDate(date);
    System.out.println("localdate : " + localDate);
    System.out.println("date : " + date);

    // get day of year (Julian day)
    int julianDay1 = AstroDates.getDayOfYear(localDate);
    int julianDay2 = AstroDates.getDayOfYear(date);
    System.out.println("julianDay1 : " + julianDay1);
    System.out.println("julianDay2 : " + julianDay2);

    // compute julian day
    ZoneId utc = ZoneOffset.UTC;
    ZonedDateTime y2k = ZonedDateTime.of(2000, Month.JANUARY.getValue(), 1, 18, 0, 0, 0, utc);
    double julianDay = AstroDates.toJulianDay(y2k);
    System.out.println("Y2K : " + y2k);
    System.out.println("julianDay : " + julianDay);
  }

}

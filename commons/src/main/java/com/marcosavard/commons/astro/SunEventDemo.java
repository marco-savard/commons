package com.marcosavard.commons.astro;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import com.marcosavard.commons.geog.GeoCoordinate;

public class SunEventDemo {

  public static void main(String[] args) {
    LocalDate startDate = LocalDate.of(2019, Month.JUNE, 1);
    GeoCoordinate qc = GeoCoordinate.of(46.8, -71.2);
    TimeZone timezone = TimeZone.getTimeZone("America/Montreal");

    for (int i = 0; i <= 28; i++) {
      LocalDate currentDate = startDate.plusDays(i);
      SunEvent sunEvent = GeoPosition.ofSunEvent(currentDate, qc, timezone);
      ZonedDateTime sunrise = sunEvent.getSunrise();
      ZonedDateTime sunset = sunEvent.getSunset();

      String day = currentDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
      String sr = sunrise.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
      String ss = sunset.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
      String tz = timezone.getDisplayName();

      String msg =
          MessageFormat.format("  {0} sunrise at {1}, sunset at {2} ({3})", day, sr, ss, tz);
      System.out.println(msg);
    }
  }
}

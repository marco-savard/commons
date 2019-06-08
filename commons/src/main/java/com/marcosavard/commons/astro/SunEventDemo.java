package com.marcosavard.commons.astro;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.TimeZone;
import com.marcosavard.commons.geog.GeoCoordinate;
import com.marcosavard.commons.time.Dates;

public class SunEventDemo {

  public static void main(String[] args) {
    GeoCoordinate qc = GeoCoordinate.of(46.8, -71.2);
    TimeZone timezone = TimeZone.getTimeZone("America/Montreal");

    DateFormat dayFormatter = new SimpleDateFormat("dd MMM yyyy");
    DateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
    DateFormat tzFormatter = new SimpleDateFormat("zzzz");
    LocalDate startDate = LocalDate.of(2019, Month.JUNE, 1);

    for (int i = 0; i <= 28; i++) {
      Date date = Dates.toDate(startDate.plusDays(i));
      SunEvent sunEvent = new SunEvent(qc, timezone, date);
      Date sunrise = sunEvent.getSunrise();
      Date sunset = sunEvent.getSunset();

      String day = dayFormatter.format(date);
      String sr = timeFormatter.format(sunrise);
      String ss = timeFormatter.format(sunset);
      String tz = tzFormatter.format(date);

      String msg =
          MessageFormat.format("  {0} sunrise at {1}, sunset at {2} ({3})", day, sr, ss, tz);
      System.out.println(msg);
    }
  }
}

package com.marcosavard.commons.astro;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import com.marcosavard.commons.geog.GeoCoordinate;
import com.marcosavard.commons.geog.GeoCoordinate.Latitude;
import com.marcosavard.commons.geog.GeoCoordinate.Longitude;

public class LocalSideralTimeDemo {

  public static void main(String[] args) {
    // position of the star M13
    StarCoordinate coordinate = StarCoordinate.M13;

    // at this time
    LocalDateTime ldt = LocalDateTime.of(1998, 8, 10, 23, 10, 0); // 2310 UT, 10th August 1998

    // as seen at this location
    GeoCoordinate city = GeoCoordinate.of(Latitude.of(52, 30), Longitude.of(1, 55));

    ZoneId utc = ZoneId.of("UTC");
    ZonedDateTime zdt = ZonedDateTime.of(ldt, utc);
    Date moment = dateTimeToDate(zdt);

    double lst = LocalSideralTime.getLst(moment, city.getLongitude().getValue());
    SkyPosition skyPosition = SkyPosition.compute(coordinate, city, lst);
    System.out.println(skyPosition);
  }

  private static Date dateTimeToDate(ZonedDateTime dt) {
    int y = dt.getYear();
    int m = dt.getMonthValue();
    int d = dt.getDayOfMonth();
    Date date = new Date(y - 1900, m - 1, d, dt.getHour(), dt.getMinute(), dt.getSecond());
    return date;
  }

}

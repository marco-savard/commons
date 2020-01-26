package com.marcosavard.commons.astro;

import static com.marcosavard.commons.geog.GeoCoordinate.LatitudeHemisphere.NORTH;
import static com.marcosavard.commons.geog.GeoCoordinate.LongitudeHemisphere.WEST;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import com.marcosavard.commons.geog.GeoCoordinate;
import com.marcosavard.commons.geog.GeoCoordinate.Latitude;
import com.marcosavard.commons.geog.GeoCoordinate.Longitude;
import com.marcosavard.commons.time.Dates;

public class LocalSideralTimeDemo {

  // see http://www.stargazing.net/kepler/altaz.html

  public static void main(String[] args) {
    // position of the star M13..
    StarCoordinate coordinate = StarCoordinate.M13;

    // ..as seen from this location (Birmingham UK)
    GeoCoordinate city = GeoCoordinate.of(Latitude.of(52, 30, NORTH), Longitude.of(1, 55, WEST));

    // .. at this moment
    LocalDateTime ldt = LocalDateTime.of(1998, 8, 10, 23, 10, 0); // 2310 UT, 10th August 1998
    ZoneId utc = ZoneOffset.UTC;
    ZonedDateTime zdt = ZonedDateTime.of(ldt, utc);

    double latitude = city.getLatitude().getValue();
    double longitude = city.getLongitude().getValue();
    double lst = LocalSideralTime.of(zdt, longitude);
    SkyPosition skyPosition = SkyPosition.of(coordinate, latitude, lst);
    System.out.println("using zoned date time: " + skyPosition);

    Date moment = Dates.toDate(zdt);
    lst = LocalSideralTime.of(moment, longitude);
    skyPosition = SkyPosition.of(coordinate, latitude, lst);
    System.out.println("using date" + skyPosition);
  }



}

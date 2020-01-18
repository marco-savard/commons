package com.marcosavard.commons.astro;

import static com.marcosavard.commons.geog.GeoCoordinate.LatitudeHemisphere.NORTH;
import static com.marcosavard.commons.geog.GeoCoordinate.LongitudeHemisphere.WEST;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import com.marcosavard.commons.geog.GeoCoordinate;
import com.marcosavard.commons.geog.GeoCoordinate.Latitude;
import com.marcosavard.commons.geog.GeoCoordinate.Longitude;

public class LocalSideralTimeDemo {

  // see http://www.stargazing.net/kepler/altaz.html

  public static void main(String[] args) {
    // position of the star M13
    StarCoordinate coordinate = StarCoordinate.M13;

    // at this time
    LocalDateTime ldt = LocalDateTime.of(1998, 8, 10, 23, 10, 0); // 2310 UT, 10th August 1998

    // as seen at this location (Birmingham UK)
    GeoCoordinate city = GeoCoordinate.of(Latitude.of(52, 30, NORTH), Longitude.of(1, 55, WEST));

    ZoneId utc = ZoneOffset.UTC;
    ZonedDateTime zdt = ZonedDateTime.of(ldt, utc);
    double longitude = city.getLongitude().getValue();
    double lst = LocalSideralTime.of(zdt, longitude);
    SkyPosition skyPosition = SkyPosition.of(coordinate, city, lst);


    System.out.println(skyPosition);
  }



}

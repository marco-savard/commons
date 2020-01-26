package com.marcosavard.commons.astro;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.TimeZone;
import com.marcosavard.commons.geog.GeoCoordinate;

public class GeoPosition {

  public static SkyPosition ofSkyPosition(StarCoordinate starCoordinate,
      GeoCoordinate geoCoordinate, ZonedDateTime zdt) {
    double latitude = geoCoordinate.getLatitude().getValue();
    double longitude = geoCoordinate.getLongitude().getValue();
    double lst = LocalSideralTime.of(zdt, longitude);
    SkyPosition skyPosition = SkyPosition.of(starCoordinate, latitude, lst);
    return skyPosition;
  }

  public static SunEvent ofSunEvent(LocalDate currentDate, GeoCoordinate geoCoordinate,
      TimeZone timezone) {
    SunEvent sunEvent = SunEvent.of(currentDate, geoCoordinate.toCoordinates(), timezone);
    return sunEvent;
  }

}

package com.marcosavard.commons.astro;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import com.marcosavard.commons.geog.GeoLocation;

public class AstronomyDemo {

  public static void main(String[] args) {
    // gives asc=12.28 hr decl=43.658 degrees
    // find equatorial coordinates, from sky position
    SkyPosition austin = SkyPosition.of(14, 320);
    GeoLocation clairmontFerrand = GeoLocation.of(46, 3);
    LocalDate date = LocalDate.of(1984, 9, 5);
    LocalTime time = LocalTime.of(21, 6);
    ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneId.of("Europe/Paris"));
    SpaceLocation location = findSpaceLocation(austin, clairmontFerrand, moment);
    findSkyPosition(location, clairmontFerrand, moment);

    // gives asc=6.57 hr decl=41 degrees
    // GeoCoordinate madrid = GeoCoordinate.of(41, -3);
    // date = LocalDate.of(1983, 2, 1);
    // time = LocalTime.of(22, 0);
    // moment = ZonedDateTime.of(date, time, ZoneOffset.UTC);
    // location = findSpaceLocation(SkyPosition.ZENITH, madrid, moment);
    // findSkyPosition(location, madrid, moment);

    GeoLocation besancon = GeoLocation.of(47, 6);
    SpaceLocation polaris = StarAlmanach.POLARIS;
    date = LocalDate.of(1982, 1, 20);
    moment = Astronomy.findTimeAtMeridian(polaris, besancon, date);

  }



  private static SpaceLocation findSpaceLocation(SkyPosition position, GeoLocation place,
      ZonedDateTime moment) {
    double[] coordinates = place.toCoordinates();
    SpaceLocation spaceLocation = Astronomy.findSpaceLocationOf(position, moment, coordinates);
    String msg = MessageFormat.format("Q: Which star is seen at {0} from {1} when {2}", position,
        place, moment);
    System.out.println(msg);
    System.out.println("A: " + spaceLocation);
    System.out.println();
    return spaceLocation;
  }

  private static SkyPosition findSkyPosition(SpaceLocation spaceLocation, GeoLocation place,
      ZonedDateTime moment) {
    double[] coordinates = place.toCoordinates();
    SkyPosition position = Astronomy.findSkyPositionOf(spaceLocation, moment, coordinates);
    String msg = MessageFormat.format("Q: Where to see star {0} from {1} where {2}", spaceLocation,
        place, moment);
    System.out.println(msg);
    System.out.println("A: " + position);
    System.out.println();
    return position;
  }

}

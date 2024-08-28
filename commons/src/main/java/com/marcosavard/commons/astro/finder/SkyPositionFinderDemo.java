package com.marcosavard.commons.astro.finder;

import com.marcosavard.commons.astro.StarAlmanach;
import com.marcosavard.commons.astro.space.SpaceCoordinate;
import com.marcosavard.commons.geog.GeoLocation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static com.marcosavard.commons.geog.GeoLocation.LatitudeHemisphere.NORTH;
import static com.marcosavard.commons.geog.GeoLocation.LongitudeHemisphere.WEST;

public class SkyPositionFinderDemo {

  public static void main(String[] args) {
    // findSkyPositionOfAustinComet();

    // findVernalPointAtSpring();
    // findVenusPositionAtAjaccio();
    // findMarsPositionAtAvignon();
    // findSunPositionInCentralScandinavia();
    // findM13PositionInBirmingham();
    // findStarsInQuebecCity();

    GeoLocation qcCity = GeoLocation.of(46, 49, NORTH, 71, 13, WEST);
    LocalDate date = LocalDate.of(2021, 5, 29);
    LocalTime time = LocalTime.of(21, 30);
    ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneId.of("America/Montreal"));
    findPlanetsSeenFrom(qcCity, moment);
  }

  private static void findPlanetsSeenFrom(GeoLocation location, ZonedDateTime moment) {
    // TODO Auto-generated method stub

  }

  /*
  private static void findSkyPositionOfAustinComet() {
  	  // find equatorial coordinates, from sky position
  	  LocalDate date = LocalDate.of(1982, 9, 5);
  	  LocalTime time = LocalTime.of(21, 6);
  	  ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneId.of("Europe/Paris"));

  	  // find location of austin at clairmontFerrand
  	  SkyPosition austin = SkyPosition.of(14, 320);
  	  GeoLocation clairmontFerrand = GeoLocation.of(46, 3);
  	  SpaceCoordinate location = findSpaceLocation(austin, clairmontFerrand, moment);

  	  // gives asc=12.28 hr decl=43.658 degrees
  	  findSkyPosition(location, clairmontFerrand, moment);
  }
   */
  /*
  private static SpaceCoordinate findSpaceLocation(SkyPosition position, GeoLocation place,
  												 ZonedDateTime moment) {
  	    double[] coordinates = place.toCoordinates();
  	    SpaceCoordinate spaceLocation = SpaceLocationFinder.find(position, coordinates, moment);
  	    SpaceCoordinate spaceLocation2 = Astronomy.findSpaceLocationOf(position, moment, coordinates);

  	    Star star = StarRepository.findStarNearestFromLocation(spaceLocation);
  	    Constellation constellation = Constellation.of(star.getConstellation());

  	    Console.println("Q: Which star is seen at {0} from {1} when {2}", position, place, moment);
  	    Console.println("A1: star near {0} (in {1})", spaceLocation, constellation.getName());
  	    Console.println("A2: " + spaceLocation2);
  	    Console.println();

  	    return spaceLocation;
  	  }
  */

  /*
  private static SkyPosition findSkyPosition(SpaceCoordinate spaceLocation, GeoLocation place,
  										   ZonedDateTime moment) {
  	    double[] coordinates = place.toCoordinates();
  	    SkyPosition position = Astronomy.findSkyPositionOf(spaceLocation, moment, coordinates);
  	    SkyPosition position2 = SkyPositionFinder.findPosition(spaceLocation, moment, coordinates);

  	    Console.println("Q: Where to see star {0} from {1} where {2}", spaceLocation, place, moment);
  	    Console.println("A1: " + position);
  	    Console.println("A2: " + position2);
  	    Console.println();
  	    return position;
  	  }
  */

  private static void findVernalPointAtSpring() {
    LocalDate date = LocalDate.of(2021, 3, 20); // 20th mars
    LocalTime time = LocalTime.NOON;
    ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneOffset.UTC);

    SpaceCoordinate vernalPoint = SpaceCoordinate.VERNAL_POINT;

    /*
    SkyPosition position = SkyPositionFinder.findPosition(vernalPoint, moment, GeoLocation.NULL_ISLAND.toCoordinates());
    //the vernal point is located at Zenith for Null Island point of view
    Console.println("Null Island : " + position);  */
  }

  /*
  private static void findVenusPositionAtAjaccio() {
  	// 31 july 1981, at 21:30
  	LocalDate date = LocalDate.of(1981, 7, 31); //31 july
      LocalTime time = LocalTime.of(21, 30); //21:30
      ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneId.of("Europe/Paris"));

      //at ajaccio
      GeoLocation ajaccio = GeoLocation.of(42, 9);

      SpaceCoordinate venusLocation = SpaceCoordinate.of(10.667, 10);
      Star star = StarRepository.findStarNearestFromLocation(venusLocation);
      Constellation constellation = Constellation.of(star.getConstellation());
      Console.println("Venus can be seen in : " + constellation);

      SkyPosition position = SkyPositionFinder.findPosition(venusLocation, moment, ajaccio.toCoordinates());

      //should give azimuth=278� (NW), horizon=6.2�
      //according http://www.convertalot.com/celestial_horizon_co-ordinates_calculator.html
      Console.println(position);
  }*/

  /*
  private static void findMarsPositionAtAvignon() {
  	// 1st july 1981, at 05:00
  	LocalDate date = LocalDate.of(1981, 7, 1); //1 july
  	LocalTime time = LocalTime.of(5, 0); //5 am
  	ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneId.of("Europe/Paris"));

  	//at avignon
      GeoLocation avignon = GeoLocation.of(44, 5);

      SpaceCoordinate marsLocation = SpaceCoordinate.of(5.138, 23.2);
      Star star = StarRepository.findStarNearestFromLocation(marsLocation);
      Constellation constellation = Constellation.of(star.getConstellation());
      Console.println("Mars can be seen in : " + constellation);

      SkyPosition position = SkyPositionFinder.findPosition(marsLocation, moment, avignon.toCoordinates());

      //should give azimuth=61.2� (NE), horizon=4.0�
      Console.println(position);
  }*/
  /*
  private static void findSunPositionInCentralScandinavia() {
  	// 19 april 1990, at midnight UTC
  	LocalDate date = LocalDate.of(1990, 4, 19); //19 april
  	LocalTime time = LocalTime.MIDNIGHT;
  	ZonedDateTime moment = ZonedDateTime.of(date, time, ZoneOffset.UTC);

  	//in central scandinavia
      GeoLocation scandinavia = GeoLocation.of(60, 15);

      SpaceCoordinate sunLocation = SpaceCoordinate.of(1.77720, 11.0084);
      SkyPosition position = SkyPositionFinder.findPosition(sunLocation, moment, scandinavia.toCoordinates());

      //should give azimuth=15.68� (N), horizon=-17.96�
      Console.println(position);
  }*/

  // http://www.stargazing.net/kepler/altaz.html
  private static void findM13PositionInBirmingham() {
    // position of the star M13..
    SpaceCoordinate m13 = StarAlmanach.M13;

    // ..as seen from this location
    GeoLocation birminghamUK = GeoLocation.of(52, 30, NORTH, 1, 55, WEST);

    // ..at this moment
    LocalDateTime localTime = LocalDateTime.of(1998, 8, 10, 23, 10, 0); // 2310 UT, 10th August 1998
    ZonedDateTime moment = ZonedDateTime.of(localTime, ZoneOffset.UTC);

    /*
      SkyPosition position = SkyPositionFinder.findPosition(m13, moment, birminghamUK.toCoordinates());

      Console.println("Position of star M13 above Birmingham, UK on August 10st, 1998 at 23:10");
      Console.println("  ..position of M13: " + position);
      Console.println();

    */

    // Assert.assertEquals(position.getAzimuth(), 269.1, 0.5);
    // Assert.assertEquals(position.getHorizon(), 49.2, 0.5);
  }

  private static void findStarsInQuebecCity() {
    System.out.println("Position of some stars above Quebec City on January 1st, 2019 at 6PM");

    // as seen at this location
    GeoLocation qcCity = GeoLocation.of(46, 49, NORTH, 71, 13, WEST);

    // at this moment
    LocalDateTime localTime = LocalDateTime.of(2019, Month.JANUARY, 1, 18, 0, 0); // Jan1st, 6PM
    ZonedDateTime moment = localTime.atZone(ZoneId.of("America/New_York"));

    // get sky position
    /*
        SkyPosition position;
        position = SkyPositionFinder.findPosition(StarAlmanach.POLARIS, moment, qcCity.toCoordinates());
        System.out.println("  ..position of Polaris: " + position);

        position = SkyPositionFinder.findPosition(StarAlmanach.URSA_MAJOR_EPSILON, moment, qcCity.toCoordinates());
        System.out.println("  ..position of Ursa Major Epsilon: " + position);

        position = SkyPositionFinder.findPosition(StarAlmanach.SIRIUS, moment, qcCity.toCoordinates());
        System.out.println("  ..position of Sirius: " + position);

        position = SkyPositionFinder.findPosition(StarAlmanach.ANTARES, moment, qcCity.toCoordinates());
        System.out.println("  ..position of Antares: " + position);

        position = SkyPositionFinder.findPosition(StarAlmanach.CRUX_ALPHA, moment, qcCity.toCoordinates());
        System.out.println("  ..position of Crux Alpha: " + position);
    */

    System.out.println();
  }
}

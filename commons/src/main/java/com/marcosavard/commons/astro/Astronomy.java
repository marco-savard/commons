package com.marcosavard.commons.astro;

import com.marcosavard.commons.astro.space.SpaceCoordinate;
import com.marcosavard.commons.astro.space.SpaceCoordinate.Declination;
import com.marcosavard.commons.astro.space.SpaceCoordinate.RightAscension;
import com.marcosavard.commons.astro.time.LocalSideralTime;
import com.marcosavard.commons.geog.GeoLocation;
import com.marcosavard.commons.math.SafeMath;

import java.time.ZonedDateTime;

// Ref: http://www-star.st-and.ac.uk/~fv/webnotes/index.html
public class Astronomy {

  public static SpaceCoordinate findSpaceLocationOf(
      SkyPosition position, ZonedDateTime moment, double[] coordinates) {

    double lat = coordinates[0];
    double lon = coordinates[1];
    double h = position.getHorizon();
    double az = position.getAzimuth();

    LocalSideralTime lst = LocalSideralTime.of(moment, lon);

    double a1 = Math.sin(Math.toRadians(h)) * Math.sin(Math.toRadians(lat));
    double a2 =
        Math.cos(Math.toRadians(h)) * Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(az));
    double a = a1 + a2;
    double d = Math.toDegrees(Math.asin(a));
    Declination decl = Declination.of(d);

    double b1 =
        Math.sin(Math.toRadians(h)) - Math.sin(Math.toRadians(lat)) * Math.sin(Math.toRadians(d));
    double b2 = Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(d));
    double b = b1 / b2;
    b = SafeMath.equal(b, 1, 0.01) ? 1.0 : b;
    double c = Math.toDegrees(Math.acos(b));
    double hr = lst.hours() - (c / 15);
    RightAscension ra = RightAscension.ofHours(hr);

    SpaceCoordinate location = SpaceCoordinate.of(ra, decl);
    return location;
  }

  /*
    public static SkyPosition findSkyPositionOf(SpaceCoordinate spaceLocation, ZonedDateTime moment,
                                                GeoLocation place) {
      return findSkyPositionOf(spaceLocation, moment, place.toCoordinates());
    }
  */

  // compute position in the sky of star, as seen from a given coordinate, at lst
  public static SkyPosition findSkyPositionOf(
      SpaceCoordinate spaceLocation, ZonedDateTime moment, double[] coordinates) {
    double ra = spaceLocation.getRightAscensionDegree();
    double decl = spaceLocation.getDeclinationDegree();
    double lat = coordinates[0];
    double lon = coordinates[1];
    LocalSideralTime lst = LocalSideralTime.of(moment, lon);
    double ha = lst.degrees() - ra;

    double sinDec = Math.sin(Math.toRadians(decl));
    double cosDec = Math.cos(Math.toRadians(decl));
    double sinLat = Math.sin(Math.toRadians(lat));
    double cosLat = Math.cos(Math.toRadians(lat));
    double sinHa = Math.asin(Math.toRadians(lat));
    double cosHa = Math.cos(Math.toRadians(ha));

    double sinAlt = (sinDec * sinLat) + (cosDec * cosLat * cosHa);
    double alt = Math.toDegrees(Math.asin(sinAlt));
    double cosAlt = Math.cos(Math.toRadians(alt));

    double cosA = (sinDec - sinAlt * sinLat) / (cosAlt * cosLat);
    double a = Math.toDegrees(Math.acos(cosA));
    double az = (sinHa < 0) ? a : 360 - a;

    SkyPosition position = SkyPosition.of(alt, az);
    return position;
  }

  /*
  // less accurate
  public static SkyPosition findSkyPositionOfOld(SpaceCoordinate spaceLocation, ZonedDateTime moment,
                                                 double[] coordinates) {
    double ra = spaceLocation.getRightAscensionDegrees();
    double decl = spaceLocation.getDeclination();
    double lat = coordinates[0];
    double lon = coordinates[1];
    LocalSideralTime lst = LocalSideralTime.of(moment, lon);
    System.out.println(lst);

    double ha = lst.degrees() - ra; // localHourAngle
    double sinH = Math.sin(Math.toRadians(decl)) * Math.sin(Math.toRadians(lat)) + //
        Math.cos(Math.toRadians(decl)) * Math.cos(Math.toRadians(lat))
            * Math.cos(Math.toRadians(ha));
    double h = Math.toDegrees(Math.asin(sinH));
    double sinAz = -Math.sin(Math.toRadians(ha)) * Math.cos(Math.toRadians(decl))
        / Math.cos(Math.toRadians(h));
    double az = Math.toDegrees(Math.asin(sinAz));

    SkyPosition position = SkyPosition.of(h, az);
    return position;
  }*/

  private static SpaceCoordinate findSpaceLocationForZenithOf(
      GeoLocation coordinate2, ZonedDateTime moment) {
    // TODO Auto-generated method stub
    return null;
  }

  public static GeoLocation findGeoCoordinateForZenithOf(
      SpaceCoordinate spaceCoordinate, ZonedDateTime moment) {
    // TODO Auto-generated method stub
    return null;
  }
}

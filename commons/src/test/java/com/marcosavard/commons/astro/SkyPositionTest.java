package com.marcosavard.commons.astro;

import com.marcosavard.commons.astro.space.SpaceCoordinate;
import com.marcosavard.commons.geog.GeoLocation;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static com.marcosavard.commons.geog.GeoLocation.LatitudeHemisphere.NORTH;
import static com.marcosavard.commons.geog.GeoLocation.LongitudeHemisphere.WEST;

public class SkyPositionTest {

    // http://www.stargazing.net/kepler/altaz.html
    @Test
    public void displayLondonUK() {
        // position of the star M13..
        SpaceCoordinate coordinate = StarAlmanach.M13;

        // ..as seen from this location
        GeoLocation birminghamUK = GeoLocation.of(52, 30, NORTH, 1, 55, WEST);

        // ..at this moment
        LocalDateTime localTime = LocalDateTime.of(1998, 8, 10, 23, 10, 0); // 2310 UT, 10th August 1998
        ZonedDateTime moment = ZonedDateTime.of(localTime, ZoneOffset.UTC);

        /*
        // get sky position
        SkyPosition skyPosition =
                Astronomy.findSkyPositionOf(coordinate, moment, birminghamUK.toCoordinates());
        Assert.assertEquals(skyPosition.getAzimuth(), 269.146, 0.01);
        Assert.assertEquals(skyPosition.getHorizon(), 49.169, 0.01);

        System.out.println("Position of star M13 above Birmingham, UK on August 10st, 1998 at 23:10");
        System.out.println("  ..position of M13: " + skyPosition);
        System.out.println();

        SkyPosition skyPosition2 =
                Astronomy.findSkyPositionOfOld(coordinate, moment, birminghamUK.toCoordinates());
        System.out.println("  ..position2 of M13: " + skyPosition2);
        System.out.println();

         */
    }
}

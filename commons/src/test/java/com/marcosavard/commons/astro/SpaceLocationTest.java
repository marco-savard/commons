package com.marcosavard.commons.astro;

import com.marcosavard.commons.astro.space.SpaceCoordinate;
import com.marcosavard.commons.geog.GeoLocation;
import org.junit.jupiter.api.Test;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static com.marcosavard.commons.geog.GeoLocation.LatitudeHemisphere.NORTH;
import static com.marcosavard.commons.geog.GeoLocation.LongitudeHemisphere.WEST;

public class SpaceLocationTest {

    @Test
    public void testBirminghamUK() {
        // position of the star M13..
        SpaceCoordinate starM13 = StarAlmanach.M13;

        // ..as seen from this location
        GeoLocation birminghamUK = GeoLocation.of(52, 30, NORTH, 1, 55, WEST);

        // ..at this moment
        LocalDateTime localTime = LocalDateTime.of(1998, 8, 10, 23, 10, 0); // 2310 UT, 10th August 1998
        ZonedDateTime moment = ZonedDateTime.of(localTime, ZoneOffset.UTC);

        /*
        // get sky position
        SkyPosition skyPosition2 =
                Astronomy.findSkyPositionOf(starM13, moment, birminghamUK.toCoordinates());

        SkyPosition skyPosition = Astronomy.findSkyPositionOf(starM13, moment, birminghamUK);
        Assert.assertEquals(skyPosition.getAzimuth(), 269.146, 0.01);
        Assert.assertEquals(skyPosition.getHorizon(), 49.169, 0.01);

        System.out.println("Position of star M13 above Birmingham, UK on August 10st, 1998 at 23:10");
        System.out.println("  ..position of M13: " + skyPosition);
        System.out.println();

         */

        /*
        double[] coordinates = starM13.getZenithAt(moment);
        GeoLocation location = GeoLocation.of(coordinates[0], coordinates[1]);
        String msg = MessageFormat.format("({0}) is above ({1}) at {2}", starM13, location, moment);
        System.out.println(msg);
*/
    }
}

package com.marcosavard.commons.astro.sun;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.geog.GeoLocation;
import com.marcosavard.commons.time.StandardZoneId;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;

import java.time.*;

//https://www.timeanddate.com/sun/canada/quebec

public class SunPositionTest {
    private static final long EPSILON = 15 * 60; // 15 minutes error margin

    @Test
    void test() {
        LocalDate date = LocalDate.of(2025, Month.MARCH, 26);
        GeoLocation quebec = GeoLocation.of(46.8131, -71.2075);
        long epsilon = 15 * 60; // 15 minutes
        LocalTime time = LocalTime.NOON;
        ZoneId est = StandardZoneId.AMERICA_NEW_YORK.getZoneId();

        validateSunEvents(date, quebec, est, LocalTime.of(6, 35), LocalTime.of(19, 7));
    }

    private void validateSunEvents(LocalDate date, GeoLocation location, ZoneId zoneId, LocalTime expectedSunriseTime, LocalTime expectedSunsetTime) {
        ZonedDateTime moment = ZonedDateTime.of(date, LocalTime.NOON, zoneId);
        SunPosition position = SunPosition.at(moment);
        ZonedDateTime[] sunriseSunset = position.findSunriseSunsetAt(location);

        LocalDateTime localSunriseTime = LocalDateTime.ofInstant(sunriseSunset[0].toInstant(), zoneId);
        LocalDateTime localNoon = LocalDateTime.ofInstant(sunriseSunset[1].toInstant(), zoneId);
        LocalDateTime localSunsetTime = LocalDateTime.ofInstant(sunriseSunset[2].toInstant(), zoneId);

        LocalDateTime expectedSunriseDateTime = LocalDateTime.of(date, expectedSunriseTime);
        LocalDateTime expectedSunsetDateTime = LocalDateTime.of(date, expectedSunsetTime);

        Instant localSunrise = localSunriseTime.toInstant(zoneId.getRules().getOffset(localSunriseTime));
        Instant expectedSunrise = expectedSunriseDateTime.toInstant(zoneId.getRules().getOffset(expectedSunriseDateTime));

        Instant localSunset = localSunsetTime.toInstant(zoneId.getRules().getOffset(localSunsetTime));
        Instant expectedSunset = expectedSunsetDateTime.toInstant(zoneId.getRules().getOffset(expectedSunsetDateTime));

        Console.println("Sunrise : {0} ({1} expected)", localSunriseTime, expectedSunriseDateTime);
        Console.println("Sunset : {0} ({1} expected)", localSunsetTime, expectedSunsetDateTime);

        Assert.assertEquals(expectedSunrise.getEpochSecond(), localSunrise.getEpochSecond(), EPSILON);
        Assert.assertEquals(expectedSunset.getEpochSecond(), localSunset.getEpochSecond(), EPSILON);
    }
}

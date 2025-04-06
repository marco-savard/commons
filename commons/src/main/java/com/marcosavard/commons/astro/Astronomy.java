package com.marcosavard.commons.astro;

import com.marcosavard.commons.astro.space.SpaceCoordinate;
import com.marcosavard.commons.astro.time.LocalSideralTime;
import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.math.SafeMath;
import com.marcosavard.commons.time.JulianDay;

import java.time.ZonedDateTime;

import static com.marcosavard.commons.math.SafeMath.*;

public class Astronomy {

    public static SkyPosition findSkyPositionOf(SpaceCoordinate spaceCoord, ZonedDateTime moment, double[] coord) {
        double ra = spaceCoord.getRightAscensionDegree();
        double dec = spaceCoord.getDeclinationDegree();
        double lat = coord[0];
        double lon = coord[1];
        LocalSideralTime lst = LocalSideralTime.of(moment, lon);
        double ha = lst.degrees() - ra;
        double sinHa = sind(ha);

        //compute altitude
        double sinAlt = (sind(dec) * sind(lat)) + (cosd(dec) * cosd(lat) * cosd(ha));
        double alt = asind(sinAlt);

        //compute azimuth
        double cosA = (sind(dec) - sind(alt) * sind(lat)) / (cosd(alt) *  cosd(lat));
        double a = acosd(cosA);
        double azimuth = sinHa < 0 ? a : 360 - a;

        return SkyPosition.of(alt, azimuth);
    }

    public static SpaceCoordinate findSpaceCoordinateOf(SkyPosition skyPosition, ZonedDateTime moment, double[] coordinates) {
        double lat = coordinates[0];
        double lon = coordinates[1];
        double h = skyPosition.getHorizon();
        double az = skyPosition.getAzimuth();
        LocalSideralTime lst = LocalSideralTime.of(moment, lon);

        double a1 = sind(h) * sind(lat);
        double a2 = cosd(h) * cosd(lat) * cosd(az);
        double a = a1 + a2;
        double d = Math.toDegrees(Math.asin(a));

        double b1 = sind(h) - sind(lat) * sind(d);
        double b2 = cosd(lat) * cosd(d);
        double b = b1 / b2;
        b = SafeMath.equal(b, 1, 0.01) ? 1.0 : b;
        double c = Math.toDegrees(Math.acos(b));
        double hr = lst.hours() - (c / 15);

        SpaceCoordinate.RightAscension ra = SpaceCoordinate.RightAscension.ofHours(hr);
        SpaceCoordinate.Declination decl = SpaceCoordinate.Declination.of(d);
        SpaceCoordinate spaceCoordinate = SpaceCoordinate.of(ra, decl);
        return spaceCoordinate;
    }
}

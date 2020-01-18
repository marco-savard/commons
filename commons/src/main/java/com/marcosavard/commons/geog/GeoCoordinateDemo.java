package com.marcosavard.commons.geog;

import static com.marcosavard.commons.geog.GeoCoordinate.LatitudeHemisphere.NORTH;
import static com.marcosavard.commons.geog.GeoCoordinate.LongitudeHemisphere.WEST;
import java.text.MessageFormat;
import com.marcosavard.commons.geog.GeoCoordinate.Latitude;
import com.marcosavard.commons.geog.GeoCoordinate.Longitude;

public class GeoCoordinateDemo {

  public static void main(String[] args) {
    GeoCoordinate qc = GeoCoordinate.of(Latitude.of(46, 49, NORTH), Longitude.of(71, 13, WEST));
    GeoCoordinate mtl = GeoCoordinate.of(Latitude.of(45, 30, NORTH), Longitude.of(73, 34, WEST));
    double distance = qc.computeDistanceFrom(mtl);
    GeoCoordinate.Format format = GeoCoordinate.Format.DEG_MIN_SEC;

    String msg = MessageFormat.format("Quebec City ({0}) is located at {1} km from Montreal ({2})",
        qc.toDisplayString(format), distance, mtl.toDisplayString(format));
    System.out.println(msg);
  }

}

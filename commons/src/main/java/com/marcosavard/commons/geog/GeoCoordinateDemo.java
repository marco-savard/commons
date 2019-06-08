package com.marcosavard.commons.geog;

import java.text.MessageFormat;
import com.marcosavard.commons.geog.GeoCoordinate.Latitude;
import com.marcosavard.commons.geog.GeoCoordinate.Longitude;

public class GeoCoordinateDemo {

  public static void main(String[] args) {
    GeoCoordinate qc = GeoCoordinate.of(Latitude.of(46, 49), Longitude.of(-71, 13));
    GeoCoordinate mtl = GeoCoordinate.of(Latitude.of(45, 30), Longitude.of(-73, 34));
    double distance = qc.computeDistanceFrom(mtl);

    String msg = MessageFormat.format("Quebec City ({0}) is located at {1} km from Montreal ({2})",
        qc.toDisplayString(GeoCoordinate.Format.DEG_MIN_SEC), distance,
        mtl.toDisplayString(GeoCoordinate.Format.DEG_MIN_SEC));
    System.out.println(msg);
  }

}

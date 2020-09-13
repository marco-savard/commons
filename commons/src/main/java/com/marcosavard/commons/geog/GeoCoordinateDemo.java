package com.marcosavard.commons.geog;

import static com.marcosavard.commons.geog.GeoCoordinate.LatitudeHemisphere.NORTH;
import static com.marcosavard.commons.geog.GeoCoordinate.LongitudeHemisphere.EAST;
import static com.marcosavard.commons.geog.GeoCoordinate.LongitudeHemisphere.WEST;
import java.text.MessageFormat;
import org.junit.Assert;
import com.marcosavard.commons.geog.GeoCoordinate.Latitude;
import com.marcosavard.commons.geog.GeoCoordinate.Longitude;

public class GeoCoordinateDemo {

  public static void main(String[] args) {
    demoQuebecMtl();
    demoParisNice();
    demoBearing();
    demoMoon();
  }

  private static void demoQuebecMtl() {
    GeoCoordinate qc = GeoCoordinate.of(Latitude.of(46, 49, NORTH), Longitude.of(71, 13, WEST));
    GeoCoordinate mtl = GeoCoordinate.of(Latitude.of(45, 30, NORTH), Longitude.of(73, 34, WEST));
    double distance = qc.findDistanceFrom(mtl);
    double ad = qc.findDegreeDistanceFrom(mtl);
    double initial = qc.findInitialBearingTo(mtl);
    double terminal = qc.findTerminalBearingTo(mtl);
    GeoCoordinate midPoint = qc.findMidpointTo(mtl);
    GeoCoordinate midPoint2 = qc.findIntermediatePointTo(mtl, 0.5);
    Assert.assertEquals(midPoint, midPoint2);

    GeoCoordinate.Format format = GeoCoordinate.Format.DEG_MIN_SEC;

    System.out.println(
        MessageFormat.format("Quebec City ({0}) is located at ", qc.toDisplayString(format)));
    System.out.println(
        MessageFormat.format(" {0} km from Montreal ({1})", distance, mtl.toDisplayString(format)));
    System.out.println(MessageFormat.format(" {0} degreees from Montreal", ad));
    System.out.println(MessageFormat.format(" initial bearing to {0} deg to Montreal", initial));
    System.out.println(MessageFormat.format(" terminal bearing to {0} deg to Montreal", terminal));
    System.out.println(MessageFormat.format(" midpoint = {0}", midPoint));
    System.out.println(MessageFormat.format(" midPoint2 = {0}", midPoint2));

    System.out.println();
  }

  private static void demoMoon() {
    double moonRadius = 1738;
    GeoCoordinate apollo11 = GeoCoordinate.of(Latitude.of(0.73), Longitude.of(23.4));
    GeoCoordinate luna15 = GeoCoordinate.of(Latitude.of(17.0), Longitude.of(60.0));
    double distance = apollo11.findDistanceFrom(luna15, moonRadius);

    GeoCoordinate.Format format = GeoCoordinate.Format.DEG_MIN_SEC;

    System.out.println(
        MessageFormat.format("Appolo 11 ({0}) is located at ", apollo11.toDisplayString(format)));
    System.out.println(MessageFormat.format(" {0} km from Luna 15 ({1})", distance,
        luna15.toDisplayString(format)));
    System.out.println();

  }

  private static void demoParisNice() {
    GeoCoordinate paris = GeoCoordinate.of(Latitude.of(48, 73, NORTH), Longitude.of(2, 38, EAST));
    GeoCoordinate nice = GeoCoordinate.of(Latitude.of(43, 67, NORTH), Longitude.of(7, 21, EAST));
    double distance = paris.findDistanceFrom(nice);
    GeoCoordinate.Format format = GeoCoordinate.Format.DEG_MIN_SEC;

    System.out
        .println(MessageFormat.format("Paris({0}) is located at ", paris.toDisplayString(format)));
    System.out.println(
        MessageFormat.format(" {0} km from Nice ({1})", distance, nice.toDisplayString(format)));
    System.out.println();
  }



  private static void demoBearing() {
    GeoCoordinate baghdad = GeoCoordinate.of(Latitude.of(35), Longitude.of(45));
    GeoCoordinate osaka = GeoCoordinate.of(Latitude.of(35), Longitude.of(135));
    double initial = baghdad.findInitialBearingTo(osaka);
    double terminal = baghdad.findTerminalBearingTo(osaka);

    Assert.assertEquals(60, initial, 1.0); // initial heading of 60°
    Assert.assertEquals(120, terminal, 1.0); // terminal heading of 120°
  }

}

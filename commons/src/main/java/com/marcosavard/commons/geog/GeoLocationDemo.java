package com.marcosavard.commons.geog;

import static com.marcosavard.commons.geog.GeoLocation.LatitudeHemisphere.NORTH;
import static com.marcosavard.commons.geog.GeoLocation.LongitudeHemisphere.WEST;
import java.text.MessageFormat;
//import org.junit.Assert;

public class GeoLocationDemo {

  public static void main(String[] args) {
    demoQuebecMtl();
    demoParisNice();
    demoMoon();
  }

  private static void demoQuebecMtl() {
    GeoLocation qc = GeoLocation.of(46, 49, NORTH, 71, 13, WEST);
    GeoLocation mtl = GeoLocation.of(45, 30, NORTH, 73, 34, WEST);
    double distance = qc.findDistanceFrom(mtl);
    double ad = qc.findDegreeDistanceFrom(mtl);
    double initial = qc.findInitialBearingTo(mtl);
    double terminal = qc.findTerminalBearingTo(mtl);
    GeoLocation midPoint = qc.findMidpointTo(mtl);
    GeoLocation midPoint2 = qc.findIntermediatePointTo(mtl, 0.5);

    GeoLocation.Format format = GeoLocation.Format.DEG_MIN_SEC;

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

  private static void demoParisNice() {
    GeoLocation paris = GeoLocation.of(48, 73, NORTH, 2, 38, WEST);
    GeoLocation nice = GeoLocation.of(43, 67, NORTH, 7, 21, WEST);
    double distance = paris.findDistanceFrom(nice);
    GeoLocation.Format format = GeoLocation.Format.DEG_MIN_SEC;

    System.out
        .println(MessageFormat.format("Paris({0}) is located at ", paris.toDisplayString(format)));
    System.out.println(
        MessageFormat.format(" {0} km from Nice ({1})", distance, nice.toDisplayString(format)));
    System.out.println();
  }

  private static void demoMoon() {
    double moonRadius = 1738;
    GeoLocation appolo11 = GeoLocation.of(0.73, 23.4);
    GeoLocation luna15 = GeoLocation.of(17.0, 60.0);
    double distance = appolo11.findDistanceFrom(luna15, moonRadius);

    GeoLocation.Format format = GeoLocation.Format.DEG_MIN_SEC;

    System.out.println(
        MessageFormat.format("Appolo 11 ({0}) is located at ", appolo11.toDisplayString(format)));
    System.out.println(MessageFormat.format(" {0} km from Luna 15 ({1})", distance,
        luna15.toDisplayString(format)));
    System.out.println();
  }

}

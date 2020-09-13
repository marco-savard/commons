package com.marcosavard.commons.phy;

import java.text.MessageFormat;
import org.junit.Assert;

public class AtmosphericPressureDemo {

  public static void main(String[] args) {
    AtmosphericPressure p0 = AtmosphericPressure.atSeaLevel();

    int meters = 1050;
    AtmosphericPressure p1 = AtmosphericPressure.atAltitude(meters);
    double differenceMb = p0.toMb() - p1.toMb();

    String msg = MessageFormat.format("Difference of pressure at {0} meters of altitude : {1} mb", //
        meters, String.format("%.2f", differenceMb));
    System.out.println(msg);
    System.out.println("  Sea level : " + p0);
    System.out.println("  At " + meters + " : " + p1);
    System.out.println();
    double expected = 120;
    Assert.assertEquals(expected, differenceMb, 1);

    double startMb = 922; // 922 mb
    double startAlt = 700; // 700 m
    double endMb = 885; // 885 mb
    double elevation = AtmosphericPressure.findElevation(startMb, startAlt, endMb);

    msg = MessageFormat.format(
        "Given {0} mb at {1} meters, if pressure is {2} mb then altitude is {3} m", //
        startMb, startAlt, endMb, elevation);
    System.out.println(msg);

    expected = 1034;
    Assert.assertEquals(expected, elevation, 1);


  }

}

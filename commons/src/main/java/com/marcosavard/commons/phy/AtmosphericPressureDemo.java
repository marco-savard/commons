package com.marcosavard.commons.phy;

import com.marcosavard.commons.debug.Console;

import java.text.MessageFormat;
//import org.junit.Assert;


public class AtmosphericPressureDemo {

  public static void main(String[] args) {
    demoPressureConversion();
    demoPressureAtAltitude();
    demoFindPressureCorrection();
    demoFindAltitudeFromPressureLoss();
  }

  private static void demoPressureConversion() {
    AtmosphericPressure pressure = AtmosphericPressure.atSeaLevel();
    Console.println(pressure);
    Console.println("  " + pressure.toKpa() + " kPa");
    Console.println("  " + pressure.toMmHg() + " mmHg");
    Console.println("  " + pressure.toPsi() + " psi");
    Console.println();

  }

  private static void demoPressureAtAltitude() {
    Console.println("Atmospheric Pressure vs Altitude");

    for (int altitude = 0; altitude <= 9000; altitude += 1000) {
      AtmosphericPressure pa = AtmosphericPressure.atAltitude(altitude);
      double boilingPoint = pa.computeWaterBoilingTemperature();
      AtmosphericPressure pap = AtmosphericPressure.computeWaterBoilingPressure(boilingPoint);
      Console.println("  at {0} meters, pressure is {1} kPa and water boils at {2} C, {3} kPa",
          altitude, pa.toKpa(), boilingPoint, pap.toKpa());
    }

    System.out.println();
  }

  private static void demoFindPressureCorrection() {
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
  }

  private static void demoFindAltitudeFromPressureLoss() {
    double startMb = 922; // 922 mb
    double startAlt = 700; // 700 m
    double endMb = 885; // 885 mb
    double elevation = AtmosphericPressure.findElevation(startMb, startAlt, endMb);
    double endAlt = startAlt + elevation;

    String msg = MessageFormat.format("Given {0} mb at {1} meters:", startMb, startAlt);
    System.out.println(msg);

    msg = MessageFormat.format("  if pressure is {0} mb then new altitude is {1} m", endMb, endAlt);
    System.out.println(msg);
  }



}

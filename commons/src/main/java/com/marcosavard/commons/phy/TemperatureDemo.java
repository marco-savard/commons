package com.marcosavard.commons.phy;

import com.marcosavard.commons.debug.Console;

public class TemperatureDemo {

  public static void main(String[] args) {
    demoConversion();
  }

  private static void demoConversion() {
    Temperature freezingPoint = Temperature.of(0);
    Console.println("Freezing point : {0}", freezingPoint);
    Console.println("Freezing point : {0} F", freezingPoint.toFahrenheit());
  }


}

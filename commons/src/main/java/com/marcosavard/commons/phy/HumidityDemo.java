package com.marcosavard.commons.phy;

import com.marcosavard.commons.debug.Console;

public class HumidityDemo {

  public static void main(String[] args) {
    double temperature = 29.4;
    double dewPoint = 18.3;

    double rh = Humidity.computeRelativeHumidity(temperature, dewPoint);
    Console.println("t={0} dew={1} rh={2}", temperature, dewPoint, rh);

    dewPoint = Humidity.computeDewPoint(temperature, rh);
    Console.println("t={0} dew={1} rh={2}", temperature, dewPoint, rh);

    temperature = Humidity.computeTemperature(rh, dewPoint);
    Console.println("t={0} dew={1} rh={2}", temperature, dewPoint, rh);

    double ah1 = Humidity.computeAbsoluteHumidity(25, 60);
    // double ah2 = Humidity.computeAbsoluteHumidity(dewPoint);
    Console.println("ah1={0}", ah1, dewPoint, rh);

  }

}

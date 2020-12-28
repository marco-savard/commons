package com.marcosavard.commons.phy;

import java.text.MessageFormat;

// temperature conversion
// compute dew point
// compure relative humidity
// thermal conductivity, thermal
// https://www.ajdesigner.com/phphumidity/relative_humidity_equation.php
// https://www.ajdesigner.com/phpthermal/thermal_conductivity_equation_heat_transfer_rate.php

public class Temperature {
  private static final String DEGREE_SIGN = "\u00B0";
  private double degreesCelcius;

  public static Temperature of(double degreesCelcius) {
    Temperature temperature = new Temperature(degreesCelcius);
    return temperature;
  }

  public static Temperature ofFahrenheit(double fahrenheit) {
    double degreesCelcius = (fahrenheit - 32) * 5 / 9;
    Temperature temperature = new Temperature(degreesCelcius);
    return temperature;
  }

  public static Temperature ofKelvin(double kelvin) {
    double degreesCelcius = kelvin - 273.15;
    Temperature temperature = new Temperature(degreesCelcius);
    return temperature;
  }

  private Temperature(double degreesCelcius) {
    this.degreesCelcius = degreesCelcius;
  }

  @Override
  public boolean equals(Object other) {
    boolean equal = false;

    if (other instanceof Temperature) {
      Temperature otherTemperature = (Temperature) other;
      double delta = Math.abs(this.degreesCelcius - otherTemperature.degreesCelcius);
      equal = (delta <= 0.01);
    }
    return equal;
  }

  @Override
  public int hashCode() {
    return (int) this.degreesCelcius;
  }

  @Override
  public String toString() {
    String str = MessageFormat.format("{0} {1}C", degreesCelcius, DEGREE_SIGN);
    return str;
  }

  public double toCelcius() {
    return degreesCelcius;
  }

  public double toFahrenheit() {
    double fahrenheit = (degreesCelcius * 9 / 5) + 32;
    return fahrenheit;
  }

  public double toKelvin() {
    double kelvin = degreesCelcius + 273.15;
    return kelvin;
  }

}

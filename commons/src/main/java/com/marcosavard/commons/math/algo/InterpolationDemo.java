package com.marcosavard.commons.math.algo;

import java.text.MessageFormat;

public class InterpolationDemo {

  public static void main(String[] args) {
    // define converters
    Interpolation mileToKmConverter = new MileToKmConverter();
    Interpolation celciusToFarhenheitConverter = new CelciusToFarhenheitConverter();

    // examples
    double miles = 150;
    double km = mileToKmConverter.interpolate(miles);

    String msg = MessageFormat.format(" {0} miles equals {1} km", miles, km);
    System.out.println(msg);

    double celcius = 37;
    double farhenheit = celciusToFarhenheitConverter.interpolate(celcius);

    msg = MessageFormat.format(" {0} Celcius equals {1} Farhenheit", celcius, farhenheit);
    System.out.println(msg);
  }

  private static class MileToKmConverter extends Interpolation {
    MileToKmConverter() {
      define(65.0, 104.60); // 65 miles equal to 104.6 km
    }
  }

  private static class CelciusToFarhenheitConverter extends Interpolation {
    CelciusToFarhenheitConverter() {
      super(0, 32); // 0 C = 32 F
      define(100, 212); // 100 C = 212 F
    }
  }

}

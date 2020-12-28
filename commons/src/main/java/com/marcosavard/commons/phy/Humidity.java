package com.marcosavard.commons.phy;

import com.marcosavard.commons.math.method.NewtonSolver;
import com.marcosavard.commons.math.method.NewtonSolver.SolverException;

public class Humidity {

  // return vapor pressure in mBar
  public static double computeStandardVaporPressure(double temperature) {
    double log = (7.5 * temperature) / (237.3 + temperature);
    double es = 6.11 * Math.pow(10.0, log);
    return es;
  }

  // August-Roche-Magnus approximation.
  public static double computeRelativeHumidity(double temperature, double dewPoint) {
    double es = computeStandardVaporPressure(temperature);
    double e = computeStandardVaporPressure(dewPoint);
    double rh = 100 * (e / es);
    return rh;
  }

  // TD: =243.04*(LN(RH/100)+((17.625*T)/(243.04+T)))/(17.625-LN(RH/100)-((17.625*T)/(243.04+T)))

  public static double computeDewPoint(double temperature, double rh) {
    double t = temperature;
    double td = 243.04 * (Math.log(rh / 100) + ((17.625 * t) / (243.04 + t)))
        / (17.625 - Math.log(rh / 100) - ((17.625 * t) / (243.04 + t)));
    return td;
  }

  public static double computeTemperature(double rh, double dewPoint) {
    double td = dewPoint;
    double t = 243.04 * (((17.625 * td) / (243.04 + td)) - Math.log(rh / 100))
        / (17.625 + Math.log(rh / 100) - ((17.625 * td) / (243.04 + td)));

    return t;
  }

  // https://carnotcycle.wordpress.com/2012/08/04/how-to-convert-relative-humidity-to-absolute-humidity/
  public static double computeAbsoluteHumidity(double temperature, double rh) {
    double t = temperature;
    double log = (17.625 * t) / (243.04 + t);
    double ah = (6.112 * Math.pow(10, log) * rh * 2.1674) / (273.15 + t);
    return ah;
  }

  private static final double FREEZING_POINT = 273.15; // kelvin

  // Ice saturation vapor pressure coefficients
  private static final double K0 = -5.8666426e3;
  private static final double K1 = 2.232870244e1;
  private static final double K2 = 1.39387003e-2;
  private static final double K3 = -3.4262402e-5;
  private static final double K4 = 2.7040955e-8;
  private static final double K5 = 6.7063522e-1;

  // Water saturation vapor pressure coefficients
  private static final double N1 = 0.11670521452767e4;
  private static final double N6 = 0.14915108613530e2;
  private static final double N2 = -0.72421316703206e6;
  private static final double N7 = -0.48232657361591e4;
  private static final double N3 = -0.17073846940092e2;
  private static final double N8 = 0.40511340542057e6;
  private static final double N4 = 0.12020824702470e5;
  private static final double N9 = -0.23855557567849;
  private static final double N5 = -0.32325550322333e7;
  private static final double N10 = 0.65017534844798e3;

  // https://pygmalion.nitri.org/dew-point-calculation-in-java-1143.html
  public double computeSaturationVaporPressure(double kelvin) throws IllegalArgumentException {
    if (kelvin < FREEZING_POINT) {
      return computeSaturationVaporPressureIce(kelvin);
    } else {
      return computeSaturationVaporPressureWater(kelvin);
    }
  }

  private double computeSaturationVaporPressureIce(double kelvin) {
    double t = kelvin;
    double lnP = K0 / t + K1 + (K2 + (K3 + (K4 * t)) * t) * t + K5 * Math.log(t);
    return Math.exp(lnP);
  }

  private double computeSaturationVaporPressureWater(double kelvin) {
    double th = kelvin + N9 / (kelvin - N10);
    double a = (th + N1) * th + N2;
    double b = (N3 * th + N4) * th + N5;
    double c = (N6 * th + N7) * th + N8;

    double p = 2 * c / (-b + Math.sqrt(b * b - 4 * a * c));
    p *= p;
    p *= p;
    return p * 1e6;
  }

  private final NewtonSolver newtonSolver = new NewtonSolver();

  public double dewPoint(double relativeHumidity, double kelvin)
      throws SolverException, IllegalArgumentException {
    double t = kelvin;

    double dewPoint = newtonSolver.solve((double x) -> computeSaturationVaporPressure(x),
        relativeHumidity / 100.0 * computeSaturationVaporPressure(t), t);
    return dewPoint;
  }



}

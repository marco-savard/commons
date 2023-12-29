package com.marcosavard.commons.math.geom;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.debug.StopWatch;

import java.util.Random;

public class DistanceDemo {
  public static void main(String[] args) {
    int count = 10_000_000;
    Point2D origin = Point2D.of(0, 0);
    double[] xs = new double[count];
    double[] ys = new double[count];

    Random r = new Random();
    Console.println("building points c1");

    for (int i = 0; i < count; i++) {
      double x = r.nextDouble() * 20 - 10;
      double y = r.nextDouble() * 20 - 10;
      xs[i] = x;
      ys[i] = y;
    }

    DistanceStrategy strategy1 = new CustomDistanceStrategy();
    double[] closest = strategy1.findClosest(xs, ys);

    Console.println("starting c1");
    double fx = 0, fy = 0;
    double closestDistance = Double.MAX_VALUE;
    StopWatch sw1 = new StopWatch();
    sw1.start();
    for (int i = 0; i < count; i++) {
      double x = xs[i];
      double y = ys[i];
      double x2 = x * x;
      double y2 = y * y;
      double distance = squareRoot(x2 + y2);
      fx = (distance < closestDistance) ? x : fx;
      fy = (distance < closestDistance) ? y : fy;
      closestDistance = (distance < closestDistance) ? distance : closestDistance;
    }
    sw1.end();

    Console.println("Closest : x={0}, y={1}", fx, fy);
    Console.println("  duration : {0} ms", sw1.getTime());

    Console.println("starting c2");
    closestDistance = Double.MAX_VALUE;
    StopWatch sw2 = new StopWatch();
    sw2.start();
    for (int i = 0; i < count; i++) {
      double x = xs[i];
      double y = ys[i];
      double x2 = x * x;
      double y2 = y * y;
      double distance2D = x2 + y2;
      fx = (distance2D < closestDistance) ? x : fx;
      fy = (distance2D < closestDistance) ? y : fy;
      closestDistance = (distance2D < closestDistance) ? distance2D : closestDistance;
    }
    sw2.end();

    Console.println("Closest : x={0}, y={1}", fx, fy);
    Console.println("  duration : {0} ms", sw1.getTime());

    Console.println("Closest : x={0}, y={1}", fx, fy);
    Console.println("  duration : {0} ms", sw2.getTime());
  }

  public static double squareRoot(double num) {
    double tmp;
    double sqrtroot = num / 2;
    do {
      tmp = sqrtroot;
      sqrtroot = (tmp + (num / tmp)) / 2;
    } while ((tmp - sqrtroot) != 0);

    return sqrtroot;
  }

  private abstract static class DistanceStrategy {

    public double[] findClosest(double[] xs, double[] ys) {
      double fx = 0, fy = 0;
      double closestDistance = Double.MAX_VALUE;
      StopWatch sw1 = new StopWatch();
      sw1.start();
      for (int i = 0; i < xs.length; i++) {
        double x = xs[i];
        double y = ys[i];
        double x2 = x * x;
        double y2 = y * y;
        double distance = squareRoot(x2 + y2);
        fx = (distance < closestDistance) ? x : fx;
        fy = (distance < closestDistance) ? y : fy;
        closestDistance = (distance < closestDistance) ? distance : closestDistance;
      }

      return new double[] {fx, fy};
    }
  }

  private static class CustomDistanceStrategy extends DistanceStrategy {}
}

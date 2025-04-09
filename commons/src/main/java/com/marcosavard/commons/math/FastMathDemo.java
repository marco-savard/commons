package com.marcosavard.commons.math;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.util.PseudoRandom;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FastMathDemo {
  private static final long NB_ITERATIONS = 10_000_000;
  private static final int BOUND = 100_000;

  public static void main(String[] args) {
    demoSpeed();
    demoAccuracy();
    demoDistance();
  }

  public static void demoSpeed() {
    long start, duration;
    double number = 0;

    start = System.currentTimeMillis();
    for (int i = 0; i < NB_ITERATIONS; i++) {
      number = 10 + Math.sqrt(number);
    }

    duration = System.currentTimeMillis() - start;
    Console.println("duration Math.sqrt : {0} ms", duration);
    number = 0;

    start = System.currentTimeMillis();
    for (int i = 0; i < NB_ITERATIONS; i++) {
      number = 10 + FastMath.sqrt(i * 10);
    }
    duration = System.currentTimeMillis() - start;
    Console.println("duration FastMath.sqrt : {0} ms", duration);
  }

  private static void demoAccuracy() {
    for (int i = 0; i < 16; i++) {
      Console.println("sqrt({0})) = {1} (classical) vs {2} (fast)", i, Math.sqrt(i), FastMath.sqrt(i));
    }
  }

  private static void demoDistance() {
    Random random = new PseudoRandom(0);
    List<Point> points = new ArrayList<>();
    Point midPoint = new Point(BOUND/2, BOUND/2);
    long start, duration;
    Console.println("Computing the nearest point in an array of {0} points", Long.toString(NB_ITERATIONS));
    Console.indent();

    for (int i=0; i<NB_ITERATIONS; i++) {
      int x = random.nextInt(BOUND), y = random.nextInt(BOUND);
      points.add(new Point(x, y));
    }

    //distance()
    start = System.currentTimeMillis();
    Point nearestPoint = computeDistance(points, midPoint);
    duration = System.currentTimeMillis() - start;
    Console.println("nearest point to center using distance() : {0} ({1} ms)", nearestPoint, duration);

    //distance2d()
    start = System.currentTimeMillis();
    nearestPoint = computeDistanceSq(points, midPoint);
    duration = System.currentTimeMillis() - start;
    Console.println("nearest point to center using distanceSq() : {0} ({1} ms)", nearestPoint, duration);

    //distanceFast()
    start = System.currentTimeMillis();
    nearestPoint = computeDistanceFast(points, midPoint);
    duration = System.currentTimeMillis() - start;
    Console.println("nearest point to center using distanceFast() : {0} ({1} ms)", nearestPoint, duration);
    Console.unindent();
  }

  private static Point computeDistance(List<Point> points, Point midPoint) {
    double shortestDistance = Double.MAX_VALUE;
    Point nearestPoint = null;

    for (Point point : points) {
      double dist = midPoint.distance(point);
      nearestPoint = (dist < shortestDistance) ? point : nearestPoint;
      shortestDistance = Math.min(shortestDistance, dist);
    }

    return nearestPoint;
  }

  private static Point computeDistanceSq(List<Point> points, Point midPoint) {
    double shortestDistance = Double.MAX_VALUE;
    Point nearestPoint = null;

    for (Point point : points) {
      double dist = midPoint.distanceSq(point);
      nearestPoint = (dist < shortestDistance) ? point : nearestPoint;
      shortestDistance = Math.min(shortestDistance, dist);
    }

    return nearestPoint;
  }

  private static Point computeDistanceFast(List<Point> points, Point midPoint) {
    double shortestDistance = Double.MAX_VALUE;
    double midX = midPoint.x, midY = midPoint.y;
    Point nearestPoint = null;

    for (Point point : points) {
      double px = point.x - midX;
      double py = point.y - midY;
      double distSq = (px * px + py * py);
      nearestPoint = (distSq < shortestDistance) ? point : nearestPoint;
      shortestDistance = (distSq < shortestDistance) ? distSq : shortestDistance;
    }

    return nearestPoint;
  }


}

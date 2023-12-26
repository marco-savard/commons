package com.marcosavard.commons.math.geom;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.debug.StopWatch;
import com.marcosavard.commons.math.arithmetic.PseudoRandom;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Point2DDemo {

  public static void main(String[] args) {
    // basic();
    optimization();
  }

  private static void basic() {
    // create a point in Cartesian coordinates, at x=4, y=3
    int x = 4, y = 3;
    Point2D pt = Point2D.of(x, y);

    // get its radius and angle
    double radius = pt.getRadius();
    double angle = pt.getAngle();

    // create a new point in polar coordinates
    Point2D pt2 = Point2D.ofPolarCoordinates(radius, angle);

    // verify if they are equal
    boolean equal = pt.equals(pt2);
    String msg = MessageFormat.format("{0} equal to {1} : {2}", pt, pt2, equal);
    Console.println(msg);

    Point2D pole = Point2D.of(0, 0);
    msg = MessageFormat.format("distance of {0} from pole : {1}", pt, pt.distanceFrom(pole));
    Console.println(msg);
  }

  private static void optimization() {

    Point2D origin = Point2D.of(0, 0);
    int count = 200_000_000;
    Comparator<Point2D> distanceComparator = new DistanceComparator(origin);
    Comparator<Point2D> distance2DComparator = new Distance2DComparator(origin);
    List<Point2D> points = createPoints(count);
    StopWatch sw1 = new StopWatch();
    StopWatch sw2 = new StopWatch();

    sw1.start();
    Point2D closest = points.stream().min(distanceComparator).get();
    sw1.end();

    sw2.start();
    Point2D closest2 = points.stream().min(distance2DComparator).get();
    sw2.end();

    long duration1 = sw1.getTime();
    long duration2 = sw2.getTime();

    Console.println("{0}", closest);
    Console.println("duration1 = {0} ms", duration1);
    Console.println("duration2 = {0} ms", duration2);
  }

  private static List<Point2D> createPoints(int count) {
    PseudoRandom pr = new PseudoRandom(0);
    List<Point2D> points = new ArrayList<>();

    for (int i = 0; i < count; i++) {
      double x = pr.nextDouble() * 20 - 10;
      double y = pr.nextDouble() * 20 - 10;
      points.add(Point2D.of(x, y));
    }

    return points;
  }

  private static class DistanceComparator implements Comparator<Point2D> {
    Point2D src;

    public DistanceComparator(Point2D src) {
      this.src = src;
    }

    @Override
    public int compare(Point2D p1, Point2D p2) {
      return (int) (p1.distanceFrom(src) - p2.distanceFrom(src));
    }
  }

  private static class Distance2DComparator implements Comparator<Point2D> {
    Point2D src;

    public Distance2DComparator(Point2D src) {
      this.src = src;
    }

    @Override
    public int compare(Point2D p1, Point2D p2) {
      return (int) (p1.distance2DFrom(src) - p2.distance2DFrom(src));
    }
  }
}

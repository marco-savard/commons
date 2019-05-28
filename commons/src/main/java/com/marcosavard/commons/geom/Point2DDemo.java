package com.marcosavard.commons.geom;

import java.text.MessageFormat;

public class Point2DDemo {
	
	public static void main(String[] args) {
		//create a point in Cartesian coordinates, at x=4, y=3
		int x = 4, y = 3;
		Point2D pt = Point2D.of(x, y);
		 
		//get its radius and angle 
		double radius = pt.getRadius();
		double angle = pt.getAngle(); 
		
		//create a new point in polar coordinates
		Point2D pt2 = Point2D.ofPolarCoordinates(radius, angle); 
		
		//verify if they are equal
		boolean equal = pt.equals(pt2); 
		String msg = MessageFormat.format("{0} equal to {1} : {2}", pt, pt2, equal); 
		System.out.println(msg);
		
		Point2D pole = Point2D.of(0, 0); 
		msg = MessageFormat.format("distance of {0} from pole : {1}", pt, pt.distanceFrom(pole)); 
		System.out.println(msg);
	
	}

}

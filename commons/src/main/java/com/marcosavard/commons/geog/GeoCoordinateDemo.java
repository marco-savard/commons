package com.marcosavard.commons.geog;

import java.text.MessageFormat;

public class GeoCoordinateDemo {

	public static void main(String[] args) {
		GeoCoordinate qc = new GeoCoordinate(46, 49, 0, -71, 13, 0);
		GeoCoordinate mtl = new GeoCoordinate(45, 30, 0, -73, 34, 0);
		double distance = qc.computeDistanceFrom(mtl);
		
		String msg = MessageFormat.format("Quebec City ({0}) is located at {1} km from Montreal ({2})", 
			qc.toDisplayString(GeoCoordinate.Format.DEG_MIN_SEC), 
			distance,
			mtl.toDisplayString(GeoCoordinate.Format.DEG_MIN_SEC)
			);
		System.out.println(msg);
	}

}

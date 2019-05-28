package com.marcosavard.commons.geom;

public abstract class Shape implements HasArea, HasBounds {
	
	public abstract boolean contains(double x, double y); 
	
	public boolean containsPoint(Point2D pt) {
		return contains(pt.getX(), pt.getY()); 
	}

}

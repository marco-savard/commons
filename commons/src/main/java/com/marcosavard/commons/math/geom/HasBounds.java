package com.marcosavard.commons.math.geom;

/**
 * Used to qualify shapes that have outer bounds. Bounds is the smallest rectangle
 * that contains the whole shape. For a rectangle shape, bounds is 
 * the rectangle itself.
 * 
 * @author Marco
 *
 */
public interface HasBounds {
	public Rectangle getBounds(); 
}

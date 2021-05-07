package com.marcosavard.commons.ui.res;

import com.marcosavard.commons.ui.Color;

public class NamedColor {
	private Integer colorCode;
	private String colorName; 
	
	@Override
	public String toString() {
		String str = colorName + " (" + colorCode + ")";
		return str;
	}

	public Color getColor() {
		Color color = Color.of(colorCode); 
		return color;
	}
	

}

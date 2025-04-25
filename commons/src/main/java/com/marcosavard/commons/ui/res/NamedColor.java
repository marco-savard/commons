package com.marcosavard.commons.ui.res;

import com.marcosavard.commons.ui.color.GwtColor;

public class NamedColor {
	private Integer colorCode;
	private String colorName; 
	
	@Override
	public String toString() {
		String str = colorName + " (" + colorCode + ")";
		return str;
	}

	public GwtColor getColor() {
		GwtColor color = GwtColor.of(colorCode);
		return color;
	}
	

}

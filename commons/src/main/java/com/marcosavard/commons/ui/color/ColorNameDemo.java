package com.marcosavard.commons.ui.color;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ColorNameDemo {

    // display locale-sensible color names
    // eg ffffff -> white, blanc, etc.
    // algo to find the correct color (RGB -> HSB -> color names)
	public static void main(String[] args) {
		Locale locale = Locale.FRENCH;
		
		List<Color> colors = Arrays.asList(
			//Color.RED,Color.GREEN, Color.BLUE, //
			//Color.YELLOW, Color.CYAN, Color.MAGENTA, //
			//Color.BLACK, Color.WHITE, //
			Color.GRAY, //
			Color.LIGHT_GRAY, //
			Color.DARK_GRAY, //
			Color.ORANGE, Color.GRAY, Color.PINK
		); 
		
		for (Color color : colors) {
			String name = ColorName.of(color).toString(locale); 
			System.out.println(name);
		}
	}

}

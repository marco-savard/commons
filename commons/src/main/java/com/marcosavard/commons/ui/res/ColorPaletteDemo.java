package com.marcosavard.commons.ui.res;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.marcosavard.commons.res.CsvResourceFile;
import com.marcosavard.commons.ui.color.GwtColor.Hue;

public class ColorPaletteDemo {
	private static Predicate<NamedColor> isRed = c -> c.getColor().getSaturation() > 10 && c.getColor().getHueCode() == Hue.RED; 
	private static Predicate<NamedColor> isOrange = c -> c.getColor().getSaturation() > 10 && c.getColor().getHueCode() == Hue.ORANGE;  
	private static Predicate<NamedColor> isYellow = c -> c.getColor().getSaturation() > 10 && c.getColor().getHueCode() == Hue.YELLOW;  
	private static Predicate<NamedColor> isGreen = c -> c.getColor().getSaturation() > 10 && c.getColor().getHueCode() == Hue.GREEN; 
	private static Predicate<NamedColor> isBlue = c -> c.getColor().getSaturation() > 10 && c.getColor().getHueCode() == Hue.BLUE;  
	private static Predicate<NamedColor> isPurple = c -> c.getColor().getSaturation() > 10 && c.getColor().getHueCode() == Hue.PURPLE;
	
	private static Predicate<NamedColor> isWhite = c -> c.getColor().getSaturation() <= 10 && c.getColor().getBrightness() >= 85; 
	private static Predicate<NamedColor> isGrey = c -> c.getColor().getSaturation() <= 10 && c.getColor().getBrightness() > 15 && c.getColor().getBrightness() < 85; 
	private static Predicate<NamedColor> isBlack = c -> c.getColor().getSaturation() <= 10 && c.getColor().getBrightness() <= 15; 
	
	public static void main(String[] args) {
		//read data
		CsvResourceFile<NamedColor> colorPalette = ColorPalette.ofType(NamedColor.class); 
		List<NamedColor> allColors = colorPalette.readAll(); 
		colorPalette.close();  
		
		//print categories
		printColors(allColors, "Red colors", isRed); 
		printColors(allColors, "Orange colors", isOrange); 
		printColors(allColors, "Yellow colors", isYellow); 
		printColors(allColors, "Green colors", isGreen); 
		printColors(allColors, "Blue colors", isBlue);
		printColors(allColors, "Purple colors", isPurple);
		
		printColors(allColors, "White colors", isWhite);
		printColors(allColors, "Grey colors", isGrey);
		printColors(allColors, "Black colors", isBlack);
	}

	private static void printColors(List<NamedColor> allColors, String name, Predicate<NamedColor> predicate) {
		System.out.println(name);
		List<NamedColor> colors = getColors(allColors, predicate); 
		
		for (NamedColor color : colors) { 
			System.out.println("  " + color); 
		}
		System.out.println();
	}

	private static List<NamedColor> getColors(List<NamedColor> allColors, Predicate<NamedColor> predicate) {
		List<NamedColor> colors = allColors.stream().filter(predicate).collect(Collectors.toList()); 
		return colors;
	}

}

package com.marcosavard.commons.ui;

import java.text.MessageFormat;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import com.marcosavard.commons.ui.Color.Hue;
import com.marcosavard.commons.ui.ColorPalette.NamedColor;

public class ColorPaletteDemo {
	
	public static void main(String[] args) {
		//get color palette
		ColorPalette palette = ColorPalette.getColorPalette(); 
		String msg; 
		
		List<ColorPalette.NamedColor> reds = getReds(palette); 
		printColors(reds); 
		msg = MessageFormat.format("Found {0} reds", reds.size()); 
		System.out.println(msg);
		System.out.println();
		
		List<ColorPalette.NamedColor> oranges = getOranges(palette); 
		printColors(oranges); 
		msg = MessageFormat.format("Found {0} oranges", oranges.size()); 
		System.out.println(msg);
		System.out.println();
		
		List<ColorPalette.NamedColor> yellows = getYellows(palette); 
		printColors(yellows); 
		msg = MessageFormat.format("Found {0} yellows", yellows.size()); 
		System.out.println(msg);
		System.out.println();
		
		List<ColorPalette.NamedColor> greens = getGreens(palette); 
		printColors(greens); 
		msg = MessageFormat.format("Found {0} greens", greens.size()); 
		System.out.println(msg);
		System.out.println();
		
		List<ColorPalette.NamedColor> cyans = getCyans(palette); 
		printColors(cyans); 
		msg = MessageFormat.format("Found {0} cyans", cyans.size()); 
		System.out.println(msg);
		System.out.println();
		
		List<ColorPalette.NamedColor> blues = getBlues(palette); 
		printColors(blues); 
		msg = MessageFormat.format("Found {0} blues", blues.size()); 
		System.out.println(msg);
		System.out.println();
		
		List<ColorPalette.NamedColor> purples = getPurples(palette); 
		printColors(purples); 
		msg = MessageFormat.format("Found {0} purples", blues.size()); 
		System.out.println(msg);
		System.out.println();
		
		List<ColorPalette.NamedColor> whites = getWhites(palette); 
		printColors(whites); 
		msg = MessageFormat.format("Found {0} whites", whites.size()); 
		System.out.println(msg);
		System.out.println();
		
		List<ColorPalette.NamedColor> greys = getGreys(palette); 
		printColors(greys); 
		msg = MessageFormat.format("Found {0} greys", greys.size()); 
		System.out.println(msg);
		System.out.println();
		
		List<ColorPalette.NamedColor> blacks = getBlacks(palette); 
		printColors(blacks); 
		msg = MessageFormat.format("Found {0} blacks", blacks.size()); 
		System.out.println(msg);
		System.out.println();
	}

	private static List<NamedColor> getReds(ColorPalette palette) {
		List<ColorPalette.NamedColor> namedColors = palette.getNamedColors(); 
		Predicate<ColorPalette.NamedColor> p = c -> c.getColor().getSaturation() > 10 && c.getColor().getHueCode() == Hue.RED; 
		List<ColorPalette.NamedColor> reds = namedColors.stream().filter(p).collect(Collectors.toList()); 
		return reds;
	}

	private static List<NamedColor> getOranges(ColorPalette palette) {
		List<ColorPalette.NamedColor> namedColors = palette.getNamedColors(); 
		Predicate<ColorPalette.NamedColor> p = c -> c.getColor().getSaturation() > 10 && c.getColor().getHueCode() == Hue.ORANGE; 
		List<ColorPalette.NamedColor> oranges = namedColors.stream().filter(p).collect(Collectors.toList()); 
		return oranges;
	}
	
	private static List<NamedColor> getYellows(ColorPalette palette) {
		List<ColorPalette.NamedColor> namedColors = palette.getNamedColors(); 
		Predicate<ColorPalette.NamedColor> p = c -> c.getColor().getSaturation() > 10 && c.getColor().getHueCode() == Hue.YELLOW; 
		List<ColorPalette.NamedColor> yellows = namedColors.stream().filter(p).collect(Collectors.toList()); 
		return yellows;
	}
	
	private static List<NamedColor> getGreens(ColorPalette palette) {
		List<ColorPalette.NamedColor> namedColors = palette.getNamedColors(); 
		Predicate<ColorPalette.NamedColor> p = c -> c.getColor().getSaturation() > 10 && c.getColor().getHueCode() == Hue.GREEN; 
		List<ColorPalette.NamedColor> greens = namedColors.stream().filter(p).collect(Collectors.toList()); 
		return greens;
	}
	
	private static List<NamedColor> getCyans(ColorPalette palette) {
		List<ColorPalette.NamedColor> namedColors = palette.getNamedColors(); 
		Predicate<ColorPalette.NamedColor> p = c -> c.getColor().getSaturation() > 10 && c.getColor().getHueCode() == Hue.CYAN; 
		List<ColorPalette.NamedColor> cyans = namedColors.stream().filter(p).collect(Collectors.toList()); 
		return cyans;
	}
	
	private static List<NamedColor> getBlues(ColorPalette palette) {
		List<ColorPalette.NamedColor> namedColors = palette.getNamedColors(); 
		Predicate<ColorPalette.NamedColor> p = c -> c.getColor().getSaturation() > 10 && c.getColor().getHueCode() == Hue.BLUE; 
		List<ColorPalette.NamedColor> blues = namedColors.stream().filter(p).collect(Collectors.toList()); 
		return blues;
	}
	
	private static List<NamedColor> getPurples(ColorPalette palette) {
		List<ColorPalette.NamedColor> namedColors = palette.getNamedColors(); 
		Predicate<ColorPalette.NamedColor> p = c -> c.getColor().getSaturation() > 10 && c.getColor().getHueCode() == Hue.PURPLE; 
		List<ColorPalette.NamedColor> blues = namedColors.stream().filter(p).collect(Collectors.toList()); 
		return blues;
	}
	
	private static List<NamedColor> getWhites(ColorPalette palette) {
		List<ColorPalette.NamedColor> namedColors = palette.getNamedColors(); 
		Predicate<ColorPalette.NamedColor> p = c -> c.getColor().getSaturation() <= 10 && c.getColor().getBrightness() >= 85; 
		List<ColorPalette.NamedColor> whites = namedColors.stream().filter(p).collect(Collectors.toList()); 
		return whites;
	}
	
	private static List<NamedColor> getGreys(ColorPalette palette) {
		List<ColorPalette.NamedColor> namedColors = palette.getNamedColors(); 
		Predicate<ColorPalette.NamedColor> p = c -> c.getColor().getSaturation() <= 10 && c.getColor().getBrightness() > 15 && c.getColor().getBrightness() < 85; 
		List<ColorPalette.NamedColor> greys = namedColors.stream().filter(p).collect(Collectors.toList()); 
		return greys;
	}

	private static List<NamedColor> getBlacks(ColorPalette palette) {
		List<ColorPalette.NamedColor> namedColors = palette.getNamedColors(); 
		Predicate<ColorPalette.NamedColor> p = c -> c.getColor().getSaturation() <= 10 && c.getColor().getBrightness() <= 15; 
		List<ColorPalette.NamedColor> blacks = namedColors.stream().filter(p).collect(Collectors.toList()); 
		return blacks;
	}

	private static void printColors(List<NamedColor> whites) {
		for (ColorPalette.NamedColor white : whites) {
			System.out.println("  .." + white);
		}
	}

}

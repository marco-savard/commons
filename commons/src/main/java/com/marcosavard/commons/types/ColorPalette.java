package com.marcosavard.commons.types;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.marcosavard.commons.io.CsvReader;

/**
 * A color palette is simply a set of colors identified by a name. 
 * 
 * @author Marco
 *
 */
public class ColorPalette {
	//named colors: //http://html-color-codes.info/color-names/	
	//named colors: //http://html-color-codes.info/color-names/
	private static ColorPalette singleInstance; 

	public static ColorPalette getColorPalette() {
		if (singleInstance == null) {
			singleInstance = new ColorPalette(); 
		}
		
		return singleInstance; 
	}

	private Map<String, Color> colorMap = new HashMap<>(); 
	private List<NamedColor> namedColors = new ArrayList<>();
	
	public List<NamedColor> getNamedColors() {
		return namedColors;
	}
	
	private ColorPalette() {
		try {
			//read csv
			InputStream input = ColorPalette.class.getResourceAsStream("ColorPalette.csv"); 
			Reader reader = new InputStreamReader(input);
			this.namedColors = readColors(reader);
		} catch (IOException e) {
			//File is missing
			e.printStackTrace();
		}
	}
	
	private List<NamedColor> readColors(Reader reader) throws IOException {
		CsvReader cr = new CsvReader(reader, 1, ';'); 
		cr.readHeaderColumns(); 
		List<NamedColor> namedColors = new ArrayList<>();  
		
		while (cr.hasNext()) {
			List<String> line = cr.readLine();
			
			if (! line.isEmpty()) {
				NamedColor namedColor = readColor(line); 
				namedColors.add(namedColor); 
			}
		}
		
		return namedColors; 
	}
	
	private NamedColor readColor(List<String> line) {
		String code = line.get(0); 
		String name = line.get(1); 
		Color color = new Color(Integer.decode(code)); 
		NamedColor namedColor = new NamedColor(color, name); 
		return namedColor;
	}
	
	public void addNamedColor(String colorName, int colorCode) {
		colorMap.put(colorName, new Color(colorCode)); 
	}
	
	public static class NamedColor {
		private Color color; 
		private String name; 
		
		public NamedColor(Color color, String name) {
			this.color = color; 
			this.name = name; 
		} 
		
		public Color getColor() {
			return color;
		}
		
		@Override
		public String toString() {
			String msg = MessageFormat.format("{0} {1}", name, color.toRGBString()); 
			return msg;
		}	
	}
}

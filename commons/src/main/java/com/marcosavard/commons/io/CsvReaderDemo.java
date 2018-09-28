package com.marcosavard.commons.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.marcosavard.commons.types.Color;
import com.marcosavard.commons.types.ColorPalette;

public class CsvReaderDemo {
	
	public static void main(String[] args) {

		try {
			//read csv
			InputStream input = ColorPalette.class.getResourceAsStream("ColorPalette.csv"); 
			Reader reader = new InputStreamReader(input);
			List<NamedColor> colors = readColors(reader);
			
			for (NamedColor color : colors) {
				System.out.println(color); 
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	private static List<NamedColor> readColors(Reader reader) throws IOException {
		List<NamedColor> namedColors = new ArrayList<>(); 
		CsvReader cr = new CsvReader(reader, 1, ';'); 
		cr.readHeaders();
		
		while (cr.hasNext()) {
			List<String> values = cr.readLine();
			
			if (! values.isEmpty()) {
				System.out.println(values); 
				
				NamedColor namedColor = readColor(values); 
				namedColors.add(namedColor); 
			}
		}

		return namedColors; 
	}
	
	private static NamedColor readColor(List<String> row) {
		String code = row.get(0); 
		String name = row.get(1); 
		Color color = new Color(Integer.decode(code)); 
		NamedColor namedColor = new NamedColor(color, name); 
		return namedColor;
	}

	private static class NamedColor {
		private Color color; 
		private String name; 
		
		public NamedColor(Color color, String name) {
			this.color = color; 
			this.name = name; 
		} 
		
		@Override
		public String toString() {
			String msg = MessageFormat.format("{0} {1}", name, color.toRGBString()); 
			return msg;
		}
		
	}
}

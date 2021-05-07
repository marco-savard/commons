package com.marcosavard.commons.ui.res;

import java.nio.charset.StandardCharsets;

import com.marcosavard.commons.res.CsvResourceFile;

// named colors: //http://html-color-codes.info/color-names/
public class ColorPalette extends CsvResourceFile<NamedColor> {

	  public static ColorPalette ofType(Class<NamedColor> type) {
			return new ColorPalette(type); 
	  }
		
	  private ColorPalette(Class type) {
	    super("ColorPalette.csv", StandardCharsets.ISO_8859_1, type);
	    super.withSeparator(';');
	  }

}

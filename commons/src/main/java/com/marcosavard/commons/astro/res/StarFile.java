package com.marcosavard.commons.astro.res;

import java.nio.charset.StandardCharsets;

import com.marcosavard.commons.res.CsvResourceFile;

public class StarFile extends CsvResourceFile<Star> {
	
	public static StarFile ofType(Class<Star> type) {
		return new StarFile(type); 
	}
	
	private StarFile(Class type) {
		super("StarFile.csv", StandardCharsets.UTF_8, type); 
		super.withSeparator(';'); 
		super.withQuoteChar('\"');
	    super.withCommentPrefix("#");
		
	    //super.withNbHeaders(1);
	}


}

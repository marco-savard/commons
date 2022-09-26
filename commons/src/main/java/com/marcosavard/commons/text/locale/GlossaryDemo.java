package com.marcosavard.commons.text.locale;

import java.util.Locale;

public class GlossaryDemo {

	public static void main(String[] args) {
		Locale[] locales = new Locale[] {
		  Locale.ENGLISH,
		  Locale.FRENCH,
		  new Locale("ff"),
		  new Locale("ln"),
		  new Locale("pt")
		}; 
		
		for (Locale locale : locales) { 
			Glossary glossary = Glossary.of(locale); 
			glossary.printStats(Locale.FRENCH);
		}
	}

}

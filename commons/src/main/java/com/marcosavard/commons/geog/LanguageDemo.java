package com.marcosavard.commons.geog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LanguageDemo {

	public static void main(String[] args) {
		Map<String, Locale> languagesByCode = new HashMap<>(); 
		
		String[] languages = Locale.getISOLanguages();
		
        Map<String, List<Locale>> countryByCode = new HashMap<>(); 
		
		List<String> displayLocales = new ArrayList<>();
		Locale[] locales = Locale.getAvailableLocales(); 
		for (Locale locale : locales) { 
			String language = locale.getDisplayLanguage(); 
			String country = locale.getDisplayCountry(); 
			String script = locale.getDisplayScript();
			String displayLocale = language + "; " + country + "; (" + script + ")"; 
			displayLocales.add(displayLocale); 
			
			if (country.length() > 1) {
				List<Locale> countries = countryByCode.get(language); 
				
				if (countries == null) { 
					countries = new ArrayList<>();
					countryByCode.put(language, countries);
				}
				
				countries.add(locale);
			}
		}
		
		for (String language : languages) { 
			Locale locale = Locale.forLanguageTag(language); 
			String script = findScript(locale);
			
			if (script.equals("LATIN")) {
		 	  List<Locale> countries = countryByCode.get(language);
		 	  int nb = (countries == null) ? 0 : countries.size() ; 
		 	  
		 	  String display = language + " - " + script + "; " + locale.getDisplayLanguage();
		 	  display += " (" + nb + ")"; 
			  System.out.println(display);
			  languagesByCode.put(language, locale);
			}
		}
		
		
		
		Collections.sort(displayLocales);
		
		for (String displayLocale : displayLocales) { 
			//System.out.println(displayLocale);
		}
		
	}

	private static String findScript(Locale locale) {
		String display = locale.getDisplayName(locale); 
		char firstLetter = display.charAt(0); 
		
		Character.UnicodeScript script = Character.UnicodeScript.of(firstLetter);
		Character.UnicodeBlock block = Character.UnicodeBlock.of(firstLetter); 

		//System.out.println("  " + script); 
		return script.toString();
	}

}

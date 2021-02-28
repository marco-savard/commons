package com.marcosavard.commons.geog.res;

import java.text.MessageFormat;
import java.util.Locale;

public class CountryDependencyDemo {

	public static void main(String[] args) {
		String[] countries = Locale.getISOCountries();  
		printStat(countries); 
	}

	private static void printStat(String[] countries) {
		System.out.println("Nb countries : " + countries.length); 
		
		for (String country : countries) { 
			Locale locale = new Locale("", country); 
			String displayName = locale.getDisplayCountry(); 
			
			String dependency = CountryDependency.of(country); 
			Locale dependantLocale = (dependency == null) ? null : new Locale("", dependency); 
			String dependant = (dependantLocale == null) ? "" : "(" + dependantLocale.getDisplayCountry() + ")"; 
			
			String msg = MessageFormat.format("  {0} : {1} {2}", country, displayName, dependant); 
			System.out.println(msg); 
		}
		
	}

}

package com.marcosavard.commons.geog.res;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class CountryTimeZonesDemo {

	public static void main(String[] args) {
		String[] countries = new String[] {"CA", "US", "MX", "RU", "NZ", "AU", "ES"}; 
		displayCountries(countries, Locale.CANADA_FRENCH);
	}

	private static void displayCountries(String[] countries, Locale displayLocale) {
		String areasStr = CountryTimeZoneBundle.getString("Areas", displayLocale); 
		String timeZonesStr = CountryTimeZoneBundle.getString("TimeZones", displayLocale); 
		String timeZoneRangeStr = CountryTimeZoneBundle.getString("TimeZone_Range", displayLocale); 
		String citiesStr = CountryTimeZoneBundle.getString("Cities", displayLocale); 
		
		for (String country : countries) { 
			CountryTimeZones countryTimeZones = CountryTimeZones.of(country); 
			List<TimeZone> timezones = countryTimeZones.getTimeZones();
			String areas = getDisplayNames(countryTimeZones.getAreas(), displayLocale); 
			List<String> cities = countryTimeZones.getCities(); 
						
			System.out.println(countryTimeZones.getDisplayName(displayLocale));  
			System.out.println("  " + areasStr + " : " + areas);  
			System.out.println("  " + timeZonesStr + " : " + CountryTimeZones.getDisplayNames(timezones, displayLocale));
			System.out.println("  " + timeZoneRangeStr + " : " + CountryTimeZones.getTimeRange(timezones)); 
			System.out.println("  " + citiesStr + " : " + String.join(", ", cities));
			System.out.println(); 
		}
	}

	private static String getDisplayNames(List<String> areas, Locale displayLocale) {
		List<String> displayNames = new ArrayList<>();  
		for (String area : areas) { 
			String displayName = WorldArea.of(area).getDisplayName(displayLocale); 
			displayNames.add(displayName); 
		}
		
		String joined = String.join(", ", displayNames) ;
		return joined;
	}

}

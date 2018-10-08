package com.marcosavard.commons.geog;

import java.text.MessageFormat;
import java.util.List;

import com.marcosavard.commons.geog.TimeZoneCity.Continent;

public class TimeZoneCityDemo {
	
	public static void main(String[] args) {
		//Get cities in Americas
		List<TimeZoneCity> americaCities = TimeZoneCity.getWorldCitiesByContinent(Continent.AMERICA); 
		
		//for each time zone, from Greenwich to west
		for (int i=0; i<12; i++) {
			List<TimeZoneCity> cities = TimeZoneCity.filterByHourOffset(americaCities, -i); 
			
			if (! cities.isEmpty()) {
				String msg = MessageFormat.format("Cities in timezone {0}", -i); 
				System.out.println(msg);
				
				for (TimeZoneCity city : cities) {
					System.out.println("  .." + city);
				}
				
				System.out.println();
			}
		}		
	}

}

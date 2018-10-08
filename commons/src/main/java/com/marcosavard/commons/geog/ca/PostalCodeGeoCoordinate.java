package com.marcosavard.commons.geog.ca;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.marcosavard.commons.geog.GeoCoordinate;
import com.marcosavard.commons.io.CsvReader;

public class PostalCodeGeoCoordinate {
	private static Map<String, GeoCoordinate> locations = new HashMap<>();
	
	public static GeoCoordinate findLocation(PostalCode code) {
		if (locations.isEmpty()) {
			loadLocations(); 
		}
		
		String prefix = code.toString().substring(0, 3); 
		return locations.get(prefix);
	}

	private static void loadLocations() {
		try {
			InputStream input = PostalCodeGeoCoordinate.class.getResourceAsStream("postalCodes.csv");
			Reader r = new InputStreamReader(input, "UTF-8");
			CsvReader cr = new CsvReader(r, 0, ';'); 
			
			while (cr.hasNext()) {
				List<String> values = cr.readLine(); 
				
				if (! values.isEmpty()) {
					readLine(values); 
				}
			}
		} catch (IOException e) {
			//ignore
		}
		
	}

	private static void readLine(List<String> values) {
		String prefix = values.get(0); 
		double lat = Double.parseDouble(values.get(1));
		double lon = Double.parseDouble(values.get(2));
		GeoCoordinate coord = new GeoCoordinate(lat, lon); 
		locations.put(prefix, coord); 
	}

}

package com.marcosavard.commons.geog.ca;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import com.marcosavard.commons.geog.GeoCoordinate;
import com.marcosavard.commons.geog.GeoCoordinate.Latitude;
import com.marcosavard.commons.geog.GeoCoordinate.Longitude;
import com.marcosavard.commons.io.csv.CsvReader;

/**
 * Find a approximate location (latitude/longitude) for a given postal code.
 * 
 * @author Marco
 *
 */
public class SimplePostalCodeLocator extends PostalCodeLocator {
  private Map<String, GeoCoordinate> locations = new HashMap<>();

  public GeoCoordinate findLocation(PostalCode code) {
    if (locations.isEmpty()) {
      loadLocations();
    }

    String prefix = code.toString().substring(0, 3);
    return locations.get(prefix);
  }

  private void loadLocations() {
    try {
      InputStream input = SimplePostalCodeLocator.class.getResourceAsStream("postalCodes.csv");
      Reader r = new InputStreamReader(input, "UTF-8");
      CsvReader cr = CsvReader.of(r).withHeader(0, ';');

      while (cr.hasNext()) {
        String[] values = cr.readNext();

        if (values.length > 0) {
          readLine(values);
        }
      }
    } catch (IOException e) {
      // ignore
    }
  }

  private void readLine(String[] values) {
    String prefix = values[0];
    double lat = Double.parseDouble(values[1]);
    double lon = Double.parseDouble(values[2]);
    GeoCoordinate coord = GeoCoordinate.of(Latitude.of(lat), Longitude.of(lon));
    locations.put(prefix, coord);
  }

}

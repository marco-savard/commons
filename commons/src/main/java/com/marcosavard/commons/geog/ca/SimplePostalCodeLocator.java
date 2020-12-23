package com.marcosavard.commons.geog.ca;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;
import java.util.TreeMap;
import com.marcosavard.commons.geog.GeoLocation;
import com.marcosavard.commons.io.csv.CsvReader;

/**
 * Find a approximate location (latitude/longitude) for a given postal code.
 * 
 * @author Marco
 *
 */
public class SimplePostalCodeLocator extends PostalCodeLocator {
  private Map<String, GeoLocation> locationsByPostalCode = null;

  @Override
  public GeoLocation findLocation(PostalCode code) {
    Map<String, GeoLocation> locations = getLocations();
    String prefix = code.toString().substring(0, 3);
    return locations.get(prefix);
  }

  @Override
  public PostalCode findPostalCode(GeoLocation location) {
    Map<String, GeoLocation> locations = getLocations();
    double nearestDistance = Double.MAX_VALUE;
    String nearestPrefix = null;

    for (String prefix : locations.keySet()) {
      GeoLocation loc = locations.get(prefix);
      double distance = loc.findDistanceFrom(location);

      if (distance < nearestDistance) {
        nearestDistance = distance;
        nearestPrefix = prefix;
      }
    }

    PostalCode postalCode = PostalCode.of(nearestPrefix + "1A1");
    return postalCode;
  }


  private Map<String, GeoLocation> getLocations() {
    if (locationsByPostalCode == null) {
      loadLocations();
    }

    return locationsByPostalCode;
  }

  private void loadLocations() {
    locationsByPostalCode = new TreeMap<>();

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
    GeoLocation coord = GeoLocation.of(lat, lon);
    locationsByPostalCode.put(prefix, coord);
  }



}

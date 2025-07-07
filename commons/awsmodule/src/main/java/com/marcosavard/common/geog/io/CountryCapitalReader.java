package com.marcosavard.common.geog.io;

import com.marcosavard.common.geog.Country;
import com.marcosavard.common.geog.CountryInfo;
import com.marcosavard.common.geog.GeoLocation;
import com.marcosavard.common.io.reader.ResourceReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.format.TextStyle;
import java.util.*;

public class CountryCapitalReader {
    private BufferedReader lineReader;
    private Map<String, String[]> dataByCountry;

    public CountryCapitalReader() {
        Reader reader = new ResourceReader(CountryCapitalReader.class, "countryCapital.txt", StandardCharsets.UTF_8);
        lineReader = new BufferedReader(reader);
    }

    public String getDisplayCountry(String countryCode, Locale display, TextStyle textStyle) {
        String[] data = readCountryData(countryCode);

        if ((data == null) || (data[1] == null)) {
            throw new RuntimeException("No data for countryCode = " + countryCode);
        }

        Locale locale = Country.forCountryTag(countryCode);
        String displayCountry;

        if ((textStyle == TextStyle.NARROW) || (textStyle == TextStyle.NARROW_STANDALONE)) {
            displayCountry = locale.getCountry(); //eg US
        } else if (textStyle == TextStyle.SHORT_STANDALONE) {
            displayCountry = locale.getDisplayCountry(display);
        } else if (textStyle == TextStyle.SHORT) {
            displayCountry = data[0];
        } else if ((textStyle == TextStyle.FULL) || (textStyle == TextStyle.FULL_STANDALONE)) {
            displayCountry = data[1];
        } else {
            displayCountry = locale.getDisplayCountry(display); //short standalone
        }

        return displayCountry;
    }

    public List<CountryInfo.Capital> getCapitalCities(String countryCode) {
        String[] data = readCountryData(countryCode);
        List<String> capitalCities = new ArrayList<>();
        List<CountryInfo.Capital> capitals = new ArrayList<>();

        if (! List.of("AQ", "BV", "HM", "TF", "BUR", "HVO", "RHO", "DHY", "VDR").contains(countryCode)) {

            try {
                GeoLocation location = GeoLocation.parse(data[3]);
                CountryInfo.Capital capital = new CountryInfo.Capital(data[2], location);
                capitals.add(capital);

                if (data.length > 4) {
                    location = GeoLocation.parse(data[5]);
                    capital = new CountryInfo.Capital(data[4], location);
                    capitals.add(capital);
                }
            } catch (IllegalArgumentException ex) {
                //ignore
            }





            // String capital = readCapital();

            capitalCities.add(data[2]);

            if (data.length > 3) {
                capitalCities.add(data[3]);
            }
        }

        return capitals;
    }

    public String[] readCountryData(String countryCode) {
        Map<String, String[]> dataByCountry = readAll();
        return dataByCountry.get(countryCode.toUpperCase());
    }

    private Map<String, String[]> readAll() {
        if (dataByCountry == null) {
            dataByCountry = new HashMap<>();
        }

        String line;

        try {
            do {
                line = lineReader.readLine();

                if ((line != null) && (! line.trim().isEmpty())) {
                    readLine(line);
                }
            } while (line != null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return dataByCountry;
    }

    private void readLine(String line) {
        try {
            List<String> data = new ArrayList<>();
            String[] values = line.split(";");
            String country = values[0];
            String shortName = toShortName(values[1]);
            String longName = values[2].isEmpty() ? shortName : values[2];

            data.add(shortName);
            data.add(longName);

            for (int i=3; i<values.length; i++) {
                data.add(values[i]);
            }

            dataByCountry.put(country, data.toArray(new String[0]));
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new RuntimeException("Error on :" + line);
        }
    }

    private String toShortName(String value) {
        int idx1 = value.indexOf('(');
        int idx2 = value.indexOf(')');
        String shortName = value;

        if ((idx1 > 0) && (idx2 > 0)) {
            String article = value.substring(idx1+1, idx2);
            String name = value.substring(0, idx1).trim();
            char ch = value.charAt(idx2-1);
            shortName = (ch == '\'') ? article + name : article + " " + name;
        }

        return shortName;
    }




}

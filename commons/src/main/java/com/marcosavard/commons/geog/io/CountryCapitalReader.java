package com.marcosavard.commons.geog.io;

import com.marcosavard.commons.io.reader.ResourceReader;
import com.marcosavard.commons.util.LocaleUtil;

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
        Locale locale = LocaleUtil.forCountryTag(countryCode);
        String displayCountry;

        if (textStyle == TextStyle.SHORT) {
            displayCountry = data[0];
        } else if (textStyle == TextStyle.FULL) {
            displayCountry = data[1];
        } else {
            displayCountry = locale.getDisplayCountry(display);
        }

        return displayCountry;
    }

    public List<String> getCapitalCities(String countryCode) {
        String[] data = readCountryData(countryCode);
        List<String> capitalCities = new ArrayList<>();
        capitalCities.add(data[2]);

        if (data.length > 3) {
            capitalCities.add(data[3]);
        }
        return capitalCities;
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
            String[] values = line.split(";");
            String country = values[0];
            String shortName = toShortName(values[1]);
            String longName = values[2].isEmpty() ? shortName : values[2];
            String capital = values[3];

            String[] data = values.length == 4 ? new String[] {shortName, longName, capital} : new String[] {shortName, longName, capital, values[4]} ;
            dataByCountry.put(country, data);
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

package com.marcosavard.commons.geog.io;

import com.marcosavard.commons.io.reader.ResourceReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class TimezoneByCountryReader {
    private BufferedReader lineReader;
    private Map<String, List<TimeZone>> timezonesByCountry;

    public TimezoneByCountryReader() {
        Reader reader = new ResourceReader(TimezoneByCountryReader.class, "timezoneByCountry.txt", StandardCharsets.UTF_8);
        lineReader = new BufferedReader(reader);
    }

    public List<TimeZone> readTimezones(String countryCode) {
        Map<String, List<TimeZone>> timezonesByCountry = readAll();
        return timezonesByCountry.get(countryCode.toUpperCase());
    }

    private Map<String, List<TimeZone>> readAll() {
        if (timezonesByCountry == null) {
            timezonesByCountry = new HashMap<>();
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
        }

        return timezonesByCountry; 
    }

    private void readLine(String line) {
        String[] values = line.split(";");
        String country = values[0];
        List<TimeZone> timezones = timezonesByCountry.get(country);

        if (timezones == null) {
            timezones = new ArrayList<>();
            timezonesByCountry.put(country, timezones);
        }

        TimeZone timeZone = TimeZone.getTimeZone(values[2]);
        timezones.add(timeZone);
    }


}

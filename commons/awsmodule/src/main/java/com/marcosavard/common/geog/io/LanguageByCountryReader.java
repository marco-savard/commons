package com.marcosavard.common.geog.io;

import com.marcosavard.common.io.reader.ResourceReader;
import com.marcosavard.common.lang.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class LanguageByCountryReader {
    private BufferedReader lineReader;
    private Map<String, Locale[][]> languagesByCountry;

    public LanguageByCountryReader() {
        Reader reader = new ResourceReader(LanguageByCountryReader.class, "languageByCountry.txt", StandardCharsets.UTF_8);
        lineReader = new BufferedReader(reader);
    }

    public Map<String, Locale[][]> readAll() {
        if (languagesByCountry == null) {
            languagesByCountry = new HashMap<>();
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

        return languagesByCountry;
    }

    private void readLine(String line) {
        String[] values = line.split(":");
        String countryCode = StringUtil.unquote(values[0].trim()).toString();
        String languageCodes = values[1].substring(0, values[1].lastIndexOf(','));
        languageCodes = StringUtil.unquote(languageCodes.trim()).toString();
        int idx = languageCodes.indexOf(";");
        String offical = (idx > 0) ? languageCodes.substring(0, idx) : languageCodes;
        String common = (idx > 0) ? languageCodes.substring(idx+1) : "";

        Locale[] officialLanguages = toLocales(offical);
        Locale[] commonLanguages = toLocales(common);
        languagesByCountry.put(countryCode, new Locale[][] {officialLanguages, commonLanguages});
    }

    public Locale[][] read(String countryCode) {
        Map<String, Locale[][]> languagesByCountry = readAll();
        return languagesByCountry.get(countryCode.toUpperCase());
    }

    private Locale[] toLocales(String text) {
        String[] values = text.isEmpty() ? new String[] {} : text.split(",");
        Locale[] locales = new Locale[values.length];

        for (int i=0; i<values.length; i++) {
            Locale locale =  Locale.forLanguageTag(values[i]);
            locales[i] = locale;
        }

        return locales;
    }
}

package com.marcosavard.commons.geog.io;

import com.marcosavard.commons.io.reader.ResourceReader;
import com.marcosavard.commons.util.LocaleUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class DevisesNationalesReader {
    private BufferedReader lineReader;
    private Map<Locale, List<String>> allMottos;

    public DevisesNationalesReader() {
        Reader reader = new ResourceReader(DevisesNationalesReader.class, "devisesNationales.txt", StandardCharsets.UTF_8);
        lineReader = new BufferedReader(reader);
    }

    public List<String> getMottos(Locale country) {
        Map<Locale, List<String>> allMottos = readAll();
        return allMottos.get(country);
    }

    private Map<Locale, List<String>> readAll() {
        if (allMottos == null) {
            allMottos = new HashMap<>();
            String line;

            try {
                do {
                    line = lineReader.readLine();

                    if ((line != null) && (! line.trim().isEmpty())) {
                        readLine(line.trim());
                    }
                } while (line != null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            
        }

        return allMottos;
    }

    private void readLine(String line) {
        Locale locale0 = LocaleUtil.forCountryName("Etats-Unis", Locale.FRENCH);

        if (line.length() > 2) {
            String[] values = line.split(";");
            Locale locale = LocaleUtil.forCountryName(values[0], Locale.FRENCH);
            List<String> mottos = allMottos.get(locale);

            if (mottos == null) {
                mottos = new ArrayList<>();
                allMottos.put(locale, mottos);
            }

            String normalized = normalize(values[1]);
            mottos.add(normalized);
        }
    }

    private String normalize(String raw) {
        String normalized = raw.replace('«', ' ');
        normalized = normalized.replace('»', ' ');
        return normalized.trim();
    }
}

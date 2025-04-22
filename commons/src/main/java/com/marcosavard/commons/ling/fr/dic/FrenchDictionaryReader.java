package com.marcosavard.commons.ling.fr.dic;

import com.marcosavard.commons.io.reader.ResourceReader;
import com.marcosavard.commons.lang.StringComparator;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class FrenchDictionaryReader extends BufferedReader {
    private static final List<String> REMOVED = List.of("ac", "ag", "ah", "al", "am", "at", "ar",//
            "br", "cs", "ct", "dg", "er", "in", "ir", "li", "na", "no", "oh", "ut", //
            "and", "fco");

    public FrenchDictionaryReader() {
        super(new ResourceReader(FrenchDictionaryReader.class, "fr.dic", StandardCharsets.ISO_8859_1));
    }

    @Override
    public String readLine() throws IOException {
        String line = super.readLine();

        if (line != null) {
            int idx = line.indexOf('.');
            line = line.substring(idx+1).trim();
            line = line.replace('’', ' ');
            line = line.replace('\'', ' ');
            line = line.replace('-', ' ');
            line = line.contains(" ") ? "" : line;
            line = line.length() < 2 || Character.isUpperCase(line.charAt(0)) ? "" : line;
        }

        if ((line != null) && REMOVED.contains(line)) {
            line = "";
        }

        if (line != null) {
            line = line.isEmpty() ? readLine() : line;
        }

        return line;
    }

    public List<String> readAll() throws IOException {
        List<String> words = new ArrayList<>();
        String line;

        do {
            line = readLine();
            if (line != null) {
                words.add(line);
            }
        } while (line != null);
        close();

        Collections.sort(words, StringComparator.collatorOrder(Locale.FRENCH));
        return words;

    }
}

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

public class FrenchWordFrequencyReader extends BufferedReader {
    private static final List<String> REMOVED = List.of("av", "éd", "in", "made", "centers", "www");

    public FrenchWordFrequencyReader() {
        super(new ResourceReader(FrenchWordFrequencyReader.class, "french-word-frequency.txt", StandardCharsets.UTF_8));
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

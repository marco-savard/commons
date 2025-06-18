package com.marcosavard.common.ling.fr.dic;

import com.marcosavard.common.io.reader.ResourceReader;
import com.marcosavard.common.lang.StringUtil;
import com.marcosavard.common.util.StringComparator;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class FrDictionaryReader extends ResourceReader {
    private static final Charset WINDOWS_1252 = Charset.forName("windows-1252");

    public FrDictionaryReader() {
        super(FrDictionaryReader.class, "fr.dic", WINDOWS_1252);
    }

    public List<String> readWordsMatching(String pattern) {
        pattern = StringUtil.stripAccents(pattern.toLowerCase());
        List<String> candidates = readWordsOfLength(pattern.length());
        List<String> words = new ArrayList<>();

        for (String candidate : candidates) {
            String stripped = StringUtil.stripAccents(candidate.toLowerCase());
            if (matchesPattern(stripped, pattern)) {
                words.add(candidate);
            }
        }

        Collections.sort(words, StringComparator.collatorOrder(Locale.FRENCH));

        return words;
    }

    private List<String> readWordsOfLength(int length) {
        List<String> allWords = readAll();
        List<String> words = allWords.stream().filter(s -> s.length() == length).toList();
        return words;
    }

    private List<String> readAll() {
        BufferedReader br = new BufferedReader(this);
        List<String> lines = new ArrayList<>();
        String line;

        try {
            do {
                line = br.readLine();
                if (line != null) {
                    lines.add(line);
                }
            } while (line != null);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return lines;
    }

    /**
     * Checks if the candidate string matches the pattern.
     * The pattern may contain letters and dots ('.').
     * A dot matches any single character.
     *
     * @param candidate the string to test
     * @param pattern the pattern to match against
     * @return true if candidate matches the pattern, false otherwise
     */
    private boolean matchesPattern(String candidate, String pattern) {
        // If lengths are not equal, they can't match
        if (candidate.length() != pattern.length()) {
            return false;
        }

        // Compare each character
        for (int i = 0; i < pattern.length(); i++) {
            char pc = pattern.charAt(i);
            char cc = candidate.charAt(i);

            // If pattern char is not a dot and doesn't match candidate char
            if (pc != '.' && pc != cc) {
                return false;
            }
        }

        return true;
    }
}

package com.marcosavard.common.ling.fr.dic;

import com.marcosavard.common.io.reader.ResourceReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AnagramReader extends ResourceReader {

    public AnagramReader() {
        super(AnagramReader.class, "anagrams.txt", StandardCharsets.UTF_8);
    }

    public List<String> readAll() {
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

    public String[] chooseAnagram(List<String> allAnagrams, Random random, int rowIdx) {
        int len = rowIdx + 2;
        List<String> sizedAnagrams = allAnagrams.stream().filter(s -> s.indexOf(',') == len).toList();
        String anagramLine = sizedAnagrams.get(random.nextInt(sizedAnagrams.size()));
        List<String> anagrams = new ArrayList<>(Arrays.asList(anagramLine.split(",")));
        String first = anagrams.remove(random.nextInt(anagrams.size()));
        String second = anagrams.remove(random.nextInt(anagrams.size()));
        return new String[] {first, second};
    }

}

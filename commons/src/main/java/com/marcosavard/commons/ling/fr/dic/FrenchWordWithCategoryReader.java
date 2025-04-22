package com.marcosavard.commons.ling.fr.dic;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.io.reader.ResourceReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FrenchWordWithCategoryReader {
    private BufferedReader br;

    public FrenchWordWithCategoryReader() {
        Class claz = FrenchWordWithCategoryReader.class;
        Reader reader = new ResourceReader(claz, "FrenchWordWithCategory.dic", StandardCharsets.UTF_8);
        br = new BufferedReader(reader);
    }

    public List<Word> readAll() throws IOException {
        List<Word> words = new ArrayList<>();
        String line;

        do {
            line = br.readLine();

            if (line != null) {
                readLine(words, line);
            }
        } while (line != null);

        //print stats
        String termination = "graphe"; //"isme"; //"age"; //iste graphe
        //String termination = "sion";
        //String termination = "tion";

        List<Word> cat1 = words.stream().filter(w -> w.getText().endsWith(termination)).toList();
        List<Word> feminineWords = cat1.stream().filter(w -> w.isFeminine()).toList();
        List<Word> masculineWords = cat1.stream().filter(w -> ! w.isFeminine()).toList();

     //   Console.println("Feminine words : {0}", Integer.toString(feminineWords.size()));
     //   Console.println("Total : {0}", Integer.toString(cat1.size()));

        List<Word> exceptions = feminineWords.size() < masculineWords.size() ? feminineWords : masculineWords;
       // Console.println("Exception : {0}", exceptions);

        return words;
    }

    private void readLine(List<Word> words, String line) {
        int idx = line.indexOf('/');
        char first = line.charAt(0);
        boolean accept = Character.isLetter(first) && ! isBetween(first, 'µ', 'Ω');
        accept = accept && Character.isLowerCase(first);
        accept = accept && line.contains("po:nom");

        if ((idx > 0) && accept) {
            String text = line.substring(0, idx);

            if (text.length() > 1) {
                Word.Gender gender = Word.Gender.EPICENE;
                gender = line.contains("is:fem") ?  Word.Gender.FEMININE : gender;
                gender = line.contains("is:mas") ?  Word.Gender.MASCULINE : gender;
                Word word = Word.of(text, gender);
                words.add(word);
            }
        }
    }

    private boolean isBetween(char ch, char lower, char upper) {
        return (ch >= lower) && (ch <= upper);
    }

}

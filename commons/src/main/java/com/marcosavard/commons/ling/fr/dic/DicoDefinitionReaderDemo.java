package com.marcosavard.commons.ling.fr.dic;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.lang.StringUtil;
import com.marcosavard.commons.quiz.fr.dic.LongestWordFinder;

import java.io.IOException;
import java.util.*;

public class DicoDefinitionReaderDemo {

    public static void main(String[] args) throws IOException {
        DicoDefinitionReader reader = new DicoDefinitionReader();
        Random random = new Random();
       // pickWords(reader, random);
        guessWord(reader, random);
    }

    private static void pickWords(DicoDefinitionReader reader, Random random) throws IOException {
        List<Word> pickedWords = reader.pickWords(10, random);

        for (Word word : pickedWords) {
            List<String> definitions =  word.getDefinitions();
            String definition = definitions.get(random.nextInt(definitions.size()));
            Console.println("Word : {0}", word);
            Console.println("  defs : {0}",definition);
        }
    }

    private static void guessWord(DicoDefinitionReader reader, Random random) throws IOException {
        LongestWordFinder finder = new LongestWordFinder();
        String letters = finder.pickLetters(random);
        List<String> bestScores = finder.findBestScores();
        List<Word> candidateWords = finder.getCandidateWords();

        Console.println("Find word with : {0}", letters);

        for (String s : bestScores) {
            Word word = candidateWords.stream().filter(w -> w.getText().equals(s)).findFirst().orElse(null);
            Console.println("..{0} lettres : {1}, {2}", s.length(), s, word.getDefinitions().get(0));
        }
    }






}

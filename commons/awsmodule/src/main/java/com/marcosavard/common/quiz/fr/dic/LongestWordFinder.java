package com.marcosavard.common.quiz.fr.dic;

import com.marcosavard.common.lang.StringUtil;
import com.marcosavard.common.ling.fr.dic.DicoDefinitionReader;
import com.marcosavard.common.ling.fr.dic.Word;

import java.io.IOException;
import java.util.*;

public class LongestWordFinder {
    private static final String VOWELS = "aaaeeeiioouy";
    private static final String CONSONENTS = "bbccddffgghhjjklllmmmnnnppqqrrrssstttvvwxz";
    private DicoDefinitionReader reader = new DicoDefinitionReader();
    private String pickedLetters;
    private List<Word> allWords;
    private List<Word> candidateWords;
    private List<String> bestScores;

    public LongestWordFinder() {
    }

    public String pickLetters(Random random) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            char ch = VOWELS.charAt(random.nextInt(VOWELS.length()));
            builder.append(ch);
        }

        for (int i = 0; i < 5; i++) {
            char ch = CONSONENTS.charAt(random.nextInt(CONSONENTS.length()));
            builder.append(ch);
        }

        pickedLetters = builder.toString();
        return pickedLetters;
    }

    public List<String> findBestScores() {
        try {
            candidateWords = new ArrayList<>();
            List<Word> allWords = readAllWords();
            List<String> candidates = new ArrayList<>();

            for (Word word : allWords) {
                String target = StringUtil.stripAccents(word.getText()).toLowerCase();

                if (target.length() > 2) {
                    boolean match = canFormString(pickedLetters, target);

                    if (match && ! candidateWords.contains(word)) {
                        candidates.add(word.getText());
                        candidateWords.add(word);
                    }
                }
            }

            Collections.sort(candidates, Comparator.comparing(String::length).reversed());
            bestScores = candidates.subList(0, Math.min(3, candidates.size()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return bestScores;
    }

    private List<Word> readAllWords() throws IOException {
        if (allWords == null) {
            allWords = reader.readAll();
        }

        return allWords;
    }

    public static boolean canFormString(String source, String target) {
        int[] letterCount = new int[26]; // Tableau pour stocker la fréquence des lettres

        // Remplir le tableau avec la fréquence des lettres de source
        for (char c : source.toCharArray()) {
            letterCount[c - 'a']++;
        }

        // Vérifier si target peut être formé
        try {
            for (char c : target.toCharArray()) {
                if (letterCount[c - 'a'] == 0) { // Plus de cette lettre disponible
                    return false;
                }
                letterCount[c - 'a']--; // Utilisation d'une occurrence de la lettre
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            //ignore
        }

        return true;
    }

    public List<String> getBestScores() {
        return bestScores;
    }

    public List<Word> getCandidateWords() { return candidateWords;
    }

    public String toUppercaseLetters() {
        StringBuilder builder = new StringBuilder();

        for (int i=0; i<pickedLetters.length(); i++) {
            char ch = Character.toUpperCase((pickedLetters.charAt(i)));
            builder.append(ch + " ");
        }

        return builder.toString();
    }
}

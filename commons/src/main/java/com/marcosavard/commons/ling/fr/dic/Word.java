package com.marcosavard.commons.ling.fr.dic;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Word {
    public enum Gender {
        FEMININE, MASCULINE, EPICENE
    }

    private String text;
    private Gender gender;
    private List<String> definitions = new ArrayList<>();

    private Word(String text, Gender gender) {
        this.text = text;
        this.gender = gender;
    }

    public static Word of(String text, Gender gender) {
        return new Word(text, gender);
    }

    public String getText() {
        return text;
    }

    public boolean isFeminine() {
        return gender == Gender.FEMININE;
    }

    @Override
    public boolean equals(Object other) {
        boolean equal = false;

        if (other instanceof Word otherWord) {
            equal = text.equals(otherWord.getText());
        }

        return equal;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0} : {1}", text, definitions);
    }

    public void addDefinitions(List<String> definitions) {
        this.definitions.addAll(definitions);
    }

    public List<String> getDefinitions() {
        return definitions;
    }

    public boolean hasDefinitionStartingWith(String start) {
        return definitions.stream().anyMatch(d -> d.toLowerCase().startsWith(start.toLowerCase()));
    }

    public boolean containsAny(String... words) {
        return definitions.stream().anyMatch(d -> containsAnyWord(d.toLowerCase(), Arrays.asList(words)));
    }

    private boolean containsAnyWord(String definition, List<String> words) {
        return words.stream().anyMatch(w -> definition.contains(w.toLowerCase()));
    }
}

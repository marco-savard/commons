package com.marcosavard.commons.pseudo;

import java.util.ArrayList;
import java.util.List;
import com.marcosavard.commons.math.type.PseudoRandom;
import com.marcosavard.commons.text.SyllableSplitter;

public class PseudoLanguage {
  private static final String VOYELS = "aeiouy";
  private String languageName;
  private List<String> consonants = new ArrayList<>();
  private List<String> vowels = new ArrayList<>();
  private List<String> patterns = new ArrayList<>();
  private List<String> terminaisons = new ArrayList<>();
  private List<String> commonWords = new ArrayList<>();

  public static PseudoLanguage create(String languageName) {
    PseudoLanguage pseudoLanguage = new PseudoLanguage(languageName);
    return pseudoLanguage;
  }

  private PseudoLanguage(String languageName) {
    this.languageName = languageName;
  }

  public String getLanguageName() {
    return languageName;
  }

  public PseudoLanguage withLetters(String letters) {
    for (char ch : letters.toCharArray()) {
      boolean isVowel = VOYELS.indexOf(ch) >= 0;
      List<String> letterList = isVowel ? vowels : consonants;
      letterList.add(Character.toString(ch));
    }

    return this;
  }

  public PseudoLanguage withDiphtongs(String... diphtongs) {
    for (String diphtong : diphtongs) {
      char ch = diphtong.charAt(0);
      boolean isVowel = VOYELS.indexOf(ch) >= 0;
      List<String> letterList = isVowel ? vowels : consonants;
      letterList.add(diphtong);
    }

    return this;
  }

  public PseudoLanguage withPatterns(String... patterns) {
    for (String pattern : patterns) {
      this.patterns.add(pattern);
    }

    return this;
  }


  public PseudoLanguage withTerminaisons(String... terminaisons) {
    for (String terminaison : terminaisons) {
      this.terminaisons.add(terminaison);
    }

    return this;
  }

  public PseudoLanguage withCommonWords(String... words) {
    for (String word : words) {
      this.commonWords.add(word);
    }

    return this;
  }

  public String translate(String original) {
    String[] elements = original.split("\\s+");
    List<String> translatedWords = new ArrayList<>();

    for (String element : elements) {
      String translatedWord = translateElement(element);
      translatedWords.add(translatedWord);
    }

    String translation = String.join(" ", translatedWords);
    return translation;
  }

  private String translateElement(String element) {
    List<Object> tokens = splitTokens(element);
    String translatedElement = "";

    for (Object token : tokens) {
      String translatedWord;

      if (token instanceof Character) {
        translatedWord = Character.toString((Character) token);
      } else {
        String word = token.toString();
        boolean capitalized = word.isEmpty() ? false : Character.isUpperCase(word.charAt(0));
        translatedWord = translateWord(word);
        translatedWord = capitalized ? capitalize(translatedWord) : translatedWord;
      }

      translatedElement += translatedWord;
    }

    return translatedElement;
  }

  private List<Object> splitTokens(String element) {
    List<Object> tokens = new ArrayList<>();
    String currentWord = "";

    for (char ch : element.toCharArray()) {
      boolean isPonctuation = isPonctuation(ch);

      if (isPonctuation) {
        if (!currentWord.isEmpty()) {
          tokens.add(currentWord);
        }

        tokens.add(ch);
        currentWord = "";
      } else {
        currentWord += ch;
      }
    }

    if (!currentWord.isEmpty()) {
      tokens.add(currentWord);
    }

    return tokens;
  }

  private boolean isPonctuation(char ch) {
    boolean isPonctuation = ",;:.!?«»".indexOf(ch) >= 0;
    return isPonctuation;
  }

  private String capitalize(String word) {
    char firstLetter = Character.toUpperCase(word.charAt(0));
    String capitalized = firstLetter + word.substring(1);
    return capitalized;
  }

  private String[] splitElements(String original) {
    String[] splitWords = original.split("\\s+");
    return splitWords;
  }



  private String translateWord(String word) {
    PseudoRandom r = new PseudoRandom(word.hashCode());
    SyllableSplitter splitter = new SyllableSplitter();
    List<String> syllables = splitter.split(word.toString());
    String translatedWord;

    if (syllables.size() == 1) {
      translatedWord = translateShortWord(word.toString(), r);
    } else {
      translatedWord = translateLongWord(syllables, r);
    }

    return translatedWord;
  }

  private String translateShortWord(String word, PseudoRandom r) {
    boolean commonWord = r.nextDouble() < 0.5;
    String translatedWord;

    if (commonWord) {
      int cwi = (int) Math.floor(commonWords.size() * r.nextSquaredDouble());
      translatedWord = commonWords.get(cwi);
    } else {
      translatedWord = translateSyllable(word, r);
    }


    return translatedWord;
  }

  private String translateLongWord(List<String> syllables, PseudoRandom r) {
    StringBuffer sb = new StringBuffer();

    for (int i = 0; i < syllables.size(); i++) {
      String syllable = syllables.get(i);
      String translatedSyllable;

      if (i == 0) {
        translatedSyllable = translateSyllable(syllable, r);
      } else if (i < syllables.size() - 1) {
        translatedSyllable = translateSyllable(syllable, r);
      } else {
        translatedSyllable = translateFinalSyllable(syllable, r);
      }

      sb.append(translatedSyllable);
    }

    return sb.toString();
  }

  private String translateSyllable(String syllable, PseudoRandom r) {
    int pi = (int) Math.floor(patterns.size() * r.nextSquaredDouble());
    String pattern = patterns.get(pi);
    String translatedSyllable = "";

    for (char letterType : pattern.toCharArray()) {
      boolean isVowel = (letterType == 'V');
      List<String> letterList = isVowel ? vowels : consonants;
      int li = (int) Math.floor(letterList.size() * r.nextSquaredDouble());
      String ch = letterList.get(li);
      translatedSyllable += ch;
    }

    return translatedSyllable;
  }

  private String translateFinalSyllable(String syllable, PseudoRandom r) {
    String translatedSyllable = "";
    int ti = (int) Math.floor(terminaisons.size() * r.nextSquaredDouble());
    translatedSyllable = terminaisons.get(ti);

    return translatedSyllable;
  }



}

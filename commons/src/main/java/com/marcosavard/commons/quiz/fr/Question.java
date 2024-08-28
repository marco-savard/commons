package com.marcosavard.commons.quiz.fr;

import com.marcosavard.commons.lang.StringUtil;
import com.marcosavard.commons.math.arithmetic.PseudoRandom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Question implements Comparable<Question> {

  private String word;
  private String hint;

  public Question(String word, String hint) {
    this.word = StringUtil.stripAccents(word).toLowerCase();
    this.hint = hint;
  }

  public static List<Question> shuffle(List<Question> allQuestions, PseudoRandom pr) {
    Map<Integer, List<Question>> questionsByLength = sortByLength(allQuestions);
    List<Question> shuffledQuestions = new ArrayList<>();

    for (int i = Byte.MAX_VALUE; i > 1; i--) {
      List<Question> questions = questionsByLength.get(i);

      if (questions != null) {
        List<Question> shuffled = pr.shuffle(questions);
        shuffledQuestions.addAll(shuffled);
      }
    }

    return shuffledQuestions;
  }

  public static List<Question> sort(List<Question> allQuestions) {
    Map<Integer, List<Question>> questionsByLength = sortByLength(allQuestions);
    List<Question> sortedQuestions = new ArrayList<>();

    for (int i = Byte.MAX_VALUE; i > 1; i--) {
      List<Question> questions = questionsByLength.get(i);

      if (questions != null) {
        Collections.sort(questions);
        sortedQuestions.addAll(questions);
      }
    }

    return sortedQuestions;
  }

  private static Map<Integer, List<Question>> sortByLength(List<Question> allQuestions) {
    Map<Integer, List<Question>> questionsByLength = new HashMap<>();
    int maxLength = 0;

    for (Question question : allQuestions) {
      String word = question.getWord();
      int length = word.length();
      maxLength = Math.max(length, maxLength);
      List<Question> questions = questionsByLength.get(length);

      if (questions == null) {
        questions = new ArrayList<>();
        questionsByLength.put(length, questions);
      }

      questions.add(question);
    }

    return questionsByLength;
  }

  public String getWord() {
    return word;
  }

  @Override
  public String toString() {
    return hint + " : " + word;
  }

  public String getHint() {
    return hint;
  }

  @Override
  public int compareTo(Question other) {
    return getWord().compareTo(other.getWord());
  }
}

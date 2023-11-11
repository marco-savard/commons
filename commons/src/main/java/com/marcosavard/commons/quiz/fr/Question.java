package com.marcosavard.commons.quiz.fr;

import com.marcosavard.commons.lang.StringUtil;
import com.marcosavard.commons.math.arithmetic.PseudoRandom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Question {

  private String word;
  private String hint;

  public Question(String word, String hint) {
    this.word = StringUtil.stripAccents(word).toLowerCase();
    this.hint = hint;
  }

  public static List<Question> shuffle(List<Question> allQuestions, PseudoRandom pr) {
    Map<Integer, List<Question>> questionsByLength = new HashMap<>();
    List<Question> schuffledQuestions = new ArrayList<>();
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

      Question foundQuestion =
          questions.stream().filter(q -> word.equals(q.getWord())).findFirst().orElse(null);
      if (foundQuestion == null) {
        questions.add(question);
      }
    }

    for (int i = maxLength; i > 1; i--) {
      List<Question> questions = questionsByLength.get(i);

      if (questions != null) {
        List<Question> shuffled = pr.shuffle(questions);
        schuffledQuestions.addAll(shuffled);
      }
    }

    return schuffledQuestions;
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
}

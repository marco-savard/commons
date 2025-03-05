package com.marcosavard.commons.quiz.fr;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.util.PseudoRandom;

import java.util.List;
import java.util.Locale;
import java.util.Random;

public class QuestionListDemo {

  public static void main(String[] args) {
    Locale display = Locale.FRENCH;
    displayQuestions(display);
  }

  private static void displayQuestions(Locale display) {
    QuestionList questionList = new QuestionList();
    int seed = 15;
    Random random = new PseudoRandom(seed);
    questionList.generateQuestions(display, random);
    // questionList.shuffle(seed);
    questionList.sort();

    List<Question> questions = questionList.getQuestions();
    int count = 1;

    for (Question question : questions) {
      String msg = Integer.toString(count++) + ") " + question;
      Console.println(msg);
    }
  }
}

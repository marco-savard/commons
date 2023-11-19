package com.marcosavard.commons.quiz.fr;

import com.marcosavard.commons.debug.Console;

import java.util.List;
import java.util.Locale;

public class QuestionListDemo {

  public static void main(String[] args) {
    QuestionList questionList = new QuestionList();
    Locale display = Locale.FRENCH;
    questionList.generateQuestions(display, 15);
    List<Question> questions = questionList.getQuestions();
    int count = 1;

    for (Question question : questions) {
      String msg = Integer.toString(count++) + ") " + question;
      Console.println(msg);
    }
  }
}

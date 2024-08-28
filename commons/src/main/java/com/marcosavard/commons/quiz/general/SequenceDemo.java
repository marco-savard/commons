package com.marcosavard.commons.quiz.general;

import com.marcosavard.commons.debug.Console;

import java.util.List;

public class SequenceDemo {

  public static void main(String[] args) {
    List<String[]> pairs = Sequence.getSequencePairs();

    for (String[] pair : pairs) {
      Console.println("{0} : {1}", pair[0], pair[1]);
    }
  }
}

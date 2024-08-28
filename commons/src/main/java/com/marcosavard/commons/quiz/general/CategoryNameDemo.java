package com.marcosavard.commons.quiz.general;

import com.marcosavard.commons.debug.Console;

import java.util.List;

public class CategoryNameDemo {

  public static void main(String[] args) {
    List<String[]> categories = CategoryName.getCategories();

    for (int i = 0; i < categories.size(); i++) {
      String[] words = categories.get(i);
      Console.println("{0} : {1}", words[0], words[1]);
    }
  }
}

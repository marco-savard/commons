package com.marcosavard.commons.ui;

import com.marcosavard.commons.debug.Console;

import java.util.Locale;

public class EnumDemo {

  public static void main(String[] args) {
    Locale display = Locale.FRENCH;

    for (Affirmation item : Affirmation.values()) {
      Console.println(item.getDisplayName(display));
    }

    for (ColorProperty item : ColorProperty.values()) {
      Console.println(item.getDisplayName(display));
    }

    for (Direction item : Direction.values()) {
      Console.println(item.getDisplayName(display));
    }

    for (Operation item : Operation.values()) {
      Console.println(item.getDisplayName(display));
    }

    for (Collection item : Collection.values()) {
      Console.println(item.getDisplayName(display));
    }

    for (FileAttribute item : FileAttribute.values()) {
      Console.println(item.getDisplayName(display));
    }

    for (FileOperation item : FileOperation.values()) {
      Console.println(item.getDisplayName(display));
    }

    for (WindowOperation item : WindowOperation.values()) {
      Console.println(item.getDisplayName(display));
    }
  }
}

package com.marcosavard.commons.geog.us;

import com.marcosavard.commons.debug.Console;

import java.time.format.TextStyle;
import java.util.Locale;

public class StateDemo {
  public static void main(String[] args) {
    Locale display = Locale.FRENCH;

    for (State state : State.values()) {
      String name = state.getDisplayName(display, TextStyle.FULL);
      Console.println("{0} : {1}", state.name(), name);
    }
  }
}

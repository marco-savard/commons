package com.marcosavard.commons.game.chess;

import com.marcosavard.commons.debug.Console;

import java.util.Locale;

public class ChessDemo {
  public static void main(String[] args) {
    Locale display = Locale.FRENCH;

    for (Chess sign : Chess.values()) {
      String name = sign.getDisplayName(display);
      Console.println("{0} : {1}", name, sign.getSymbol());
    }
  }
}

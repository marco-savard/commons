package com.marcosavard.commons.game.chess;

import java.util.Locale;
import java.util.ResourceBundle;

public enum Chess {
  KING,
  QUEEN,
  ROOK,
  BISHOP,
  KNIGHT,
  PAWN;

  private static final int KING_SYMBOL = 0x2654;

  public String getDisplayName(Locale locale) {
    Class claz = Chess.class;
    String basename = claz.getName().replace('.', '/');
    ResourceBundle bundle = ResourceBundle.getBundle(basename, locale);
    String displayName = bundle.getString(this.name());
    return displayName;
  }

  public char getSymbol() {
    return (char) (this.ordinal() + KING_SYMBOL);
  }
}

package com.marcosavard.commons.game.sport;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public enum Sport {
  BADMINGTON(0x1f3f8, "UK"),
  BASEBALL(0x26be, "JP", "US"),
  BASKETBALL(0x1f3c0, "US"),
  CURLING(0x1f94c, "CA", "UK"),
  FOOTBALL(0x1f3c8, "US"),
  HANDBALL(0x1f93e, "DE"),
  HOCKEY(0x1f3d2, "CA", "FI"),
  RUGBY(0x1f3c9, "NZ", "UK"),
  SOCCER(0x26bd, "AR", "BR", "DE", "FR", "IT", "MX"),
  SOFTBALL(0x1f94e, "US"),
  TENNIS(0x1f3be, "US"),
  VOLLEYBALL(0x1f3d0, "US");

  private int symbol;
  private List<String> countries = new ArrayList<>();

  Sport(int symbol, String... countries) {
    this.symbol = symbol;
    this.countries.addAll(List.of(countries));
  }

  // private static final int KING_SYMBOL = 0x2654;

  public String getDisplayName(Locale locale) {
    Class claz = Sport.class;
    String basename = claz.getName().replace('.', '/');
    ResourceBundle bundle = ResourceBundle.getBundle(basename, locale);
    String displayName = bundle.getString(this.name());
    return displayName;
  }

  public String getSymbol() {
    return Character.toString(symbol);
  }

  public List<String> getCountries() {
    return countries;
  }
}

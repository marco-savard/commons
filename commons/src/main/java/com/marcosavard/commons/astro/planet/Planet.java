package com.marcosavard.commons.astro.planet;

import java.util.Locale;
import java.util.ResourceBundle;

public enum Planet {
  MERCURY(Category.OFFICIAL, 0x263f),
  VENUS(Category.OFFICIAL, 0x2640),
  EARTH(Category.OFFICIAL, 0x2641),
  MARS(Category.OFFICIAL, 0x2642),
  JUPITER(Category.OFFICIAL, 0x2643),
  SATURN(Category.OFFICIAL, 0x2644),
  URANUS(Category.OFFICIAL, 0x2645),
  NEPTUNE(Category.OFFICIAL, 0x2646),
  PLUTO(Category.TRANS_NEPTUNIAN, 0x2647),
  CERES(Category.ASTEROID, 0x26b3),
  PALLAS(Category.ASTEROID, 0x26b4),
  JUNO(Category.ASTEROID, 0x26b5),
  VESTA(Category.ASTEROID, 0x26b6),
  CHIRON(Category.TRANS_NEPTUNIAN, 0x26b7),
  ERIS(Category.TRANS_NEPTUNIAN, 0x2bf2),
  SEDNA(Category.TRANS_NEPTUNIAN, 0x2bf2);

  public Category getCategory() {
    return category;
  }

  public enum Category {
    OFFICIAL,
    TRANS_NEPTUNIAN,
    ASTEROID
  };

  private Category category;
  private int symbol;

  Planet(Category category, int symbol) {
    this.category = category;
    this.symbol = symbol;
  }

  public String getDisplayName(Locale locale) {
    Class claz = Planet.class;
    String basename = claz.getName().replace('.', '/');
    ResourceBundle bundle = ResourceBundle.getBundle(basename, locale);
    String displayName = bundle.getString(this.name());
    return displayName;
  }

  public int getSymbol() {
    return symbol;
  }
}
